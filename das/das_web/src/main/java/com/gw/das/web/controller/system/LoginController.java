package com.gw.das.web.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.TreeBean;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.LoginClientEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.enums.SessionKeyEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.AesUtil;
import com.gw.das.common.utils.CookieUtil;
import com.gw.das.common.utils.ImageUtil;
import com.gw.das.common.utils.MD5;
import com.gw.das.common.utils.SignUtil;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gw.das.common.utils.ValidateCodeUtil;
import com.gw.das.dao.system.entity.SystemMenuEntity;
import com.gw.das.dao.system.entity.SystemUserEntity;
import com.gw.das.service.cache.MenuCache;
import com.gw.das.service.system.SystemMenuService;
import com.gw.das.service.system.SystemUserService;
import com.gw.das.web.sessionbean.Client;

@Controller
@RequestMapping({"/LoginController", ""})
public class LoginController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private SystemMenuService systemMenuService;
	
	/**
	 * 加载配置数据
	 */
	private void loadProperties(HttpServletRequest request) {
		try{
			request.setAttribute("systemDomainName", SystemConfigUtil.getProperty(SystemConfigEnum.systemDomainName));
			request.setAttribute("systemVersion", SystemConfigUtil.getProperty(SystemConfigEnum.systemVersion));
			request.setAttribute("systemPort", SystemConfigUtil.getProperty(SystemConfigEnum.systemPort));
			request.setAttribute("webLinkUrl", SystemConfigUtil.getProperty(SystemConfigEnum.webLinkUrl));
			request.setAttribute("cboardLinkUrl", SystemConfigUtil.getProperty(SystemConfigEnum.cboardLinkUrl));
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 设置cookie,用于跳转CBoard进行校验
	 */
	private void setCookie(HttpServletRequest request, HttpServletResponse response) {
		try{
			SystemUserEntity userEntity = this.getLoginUser(request);
			if(null != userEntity){
				String userNo = userEntity.getUserNo();
				String timestamp = SignUtil.getTimestamp();
				Long companyId = this.getCompanyId(request);
				String content = userNo + "," + timestamp + "," + companyId;
				byte[] encryptResult = AesUtil.encrypt(content);
				String key = AesUtil.parseByte2HexStr(encryptResult) + Constants.END_WITH_STR;
				String sign = SignUtil.getSign(timestamp, userNo, userEntity.getPassword());
				// 设置cookie为无生命周期，即随着浏览器的关闭即消失的cookie
				CookieUtil.addCookie(response, "key", key, 0);
				CookieUtil.addCookie(response, "sign", sign, 0);
			}
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 退出后，则清空cookie
	 */
	private void delCookie(HttpServletRequest request, HttpServletResponse response) {
		try{
			CookieUtil.addCookie(response, "key", "", 0);
			CookieUtil.addCookie(response, "sign", "", 0);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转登陆页面
	 */
	@RequestMapping(value = {"/login", ""}, method = { RequestMethod.GET })
	public String login(HttpServletRequest request) {
		try {
			this.loadProperties(request);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
		return "login/login";
	}

	/**
	 * 登陆验证
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode login(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam String loginName, @RequestParam String password
//			, @RequestParam String captcha
			) {
		try{
			if (StringUtils.isBlank(loginName)) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.userNameNotEmpty));
				return resultCode;
			}
			if (StringUtils.isBlank(password)) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.passwordNotEmpty));
				return resultCode;
			}
//			if (StringUtils.isBlank(captcha)) {
//				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
//				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.validateCodeNotEmpty));
//				return resultCode;
//			}
//			String code = (String)request.getSession().getAttribute(SessionKeyEnum.captcha.getLabelKey());
//			if(!captcha.toUpperCase().equals(code)){
//				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
//				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.validateCodeError));
//				return resultCode;
//			}
			// 登录名及密码校验
			SystemUserEntity userEntity = systemUserService.findByUserNo(loginName);
			if(null == userEntity){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.userNameError));
				return resultCode;
			}else{
				if(!MD5.getMd5(password).equals(userEntity.getPassword())){
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.passwordError));
					return resultCode;
				}
				if(!Constants.superAdmin.equals(userEntity.getUserNo())){
					// 不为超级管理员
					// 验证用户是否被禁用
					if(!"Y".equals(userEntity.getEnableFlag())){
						ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
						resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.userDisable));
						return resultCode;
					}
					// 验证用户是否设置业务类型
					// 根据顺序[外汇\贵金属\恒信\创富]设置业务类型
					String companyIds = userEntity.getCompanyIds();
					if(StringUtils.isNotBlank(companyIds)){
						List<CompanyEnum> list = CompanyEnum.getList(companyIds);
						if(null != list && list.size()>0){
							Long companyId = list.get(0).getLabelKeyLong();
							request.getSession().setAttribute(SessionKeyEnum.companyId.getLabelKey(), companyId);
						}else{
							ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
							resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.companyIdsError));
							return resultCode;
						}
					}else{
						ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
						resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.notCompanyIds));
						return resultCode;
					}
				}else{
					// 为超级管理员-默认设置选择外汇业务类型
					Long companyId = CompanyEnum.fx.getLabelKeyLong();
					request.getSession().setAttribute(SessionKeyEnum.companyId.getLabelKey(), companyId);
				}
			}
			
			// 验证ok,存入session
			Client client = new Client();
			client.setClientType(LoginClientEnum.systemUserLogin.getLabelKey());
			client.setUser(userEntity);
			client.setLoginDate(new Date());
			client.setSessionId(session.getId());
			client.setIp(getRemoteAddr(request));
			request.getSession().setAttribute(SessionKeyEnum.client.getLabelKey(), client);
			
			// 登录设置当前用户
			UserContext.setSystemUser(client.getIp(), userEntity.getUserNo(), 
					userEntity.getUserName(), client.getLoginDate(), client.getSessionId(), 
					client.getClientType(), this.getCompanyId(request));
			
			// 其他信息存入session
			if(Constants.superAdmin.equals(userEntity.getUserNo())){
				// 为超级管理员
				request.getSession().setAttribute(SessionKeyEnum.superAdminFlag.getLabelKey(), "Y");
				request.getSession().setAttribute(SessionKeyEnum.menuMap.getLabelKey(), null);
			}else{
				request.getSession().setAttribute(SessionKeyEnum.superAdminFlag.getLabelKey(), "N");
				Map<String, SystemMenuEntity> map = systemMenuService.findMenuMapByUserId(userEntity.getUserId(), this.getCompanyId(request));
				request.getSession().setAttribute(SessionKeyEnum.menuMap.getLabelKey(), map);
			}
			
			// 返回成功
			return new ResultCode(ResultCodeEnum.success);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 跳转-首页
	 * @return
	 */
	@RequestMapping(value = "/home", method = { RequestMethod.GET })
	public String home(HttpServletRequest request, HttpServletResponse response) {
		try{
			this.setCookie(request, response);
			this.loadProperties(request);
			// 顶部页签菜单列表
			boolean hasTopTag = true;// 是否包含顶部页签
			boolean hasMenu = false;//是否包含菜单
			boolean hasFun = false;// 是否包含功能
			boolean showFlag = false;// 没有权限，是否显示
			List<SystemMenuEntity> menuList = null;
			SystemUserEntity loginUser = this.getLoginUser(request);
			if(null == loginUser){
				return "home/home";
			}
			Long userId = loginUser.getUserId();
			String topTagMenuCode = null;// 查询指定顶部页签节点下的菜单,为空不过滤
			if(super.isSuperAdmin(request)){
				if(CompanyEnum.gw.getLabelKey().equals(this.getCompanyId(request)+"")){
					// 集团业务下调转至统计分析
					return "redirect:index";
				}
				showFlag = true;// 没有权限，是否显示
				menuList = systemMenuService.getMenuList(hasTopTag, hasMenu, hasFun, showFlag, userId, topTagMenuCode);
				request.setAttribute("menuList", menuList);
				request.setAttribute("companyEnum", CompanyEnum.getList());
			}else{
				// 验证(LoginController/home)是否有权限
				if(!this.checkMenuUrlByLoginUser(request, "LoginController/home")){
					// 无权限
					return "redirect:index";
				}
				menuList = systemMenuService.getMenuList(hasTopTag, hasMenu, hasFun, showFlag, userId, topTagMenuCode);
				request.setAttribute("menuList", menuList);
				request.setAttribute("companyEnum", CompanyEnum.getList(this.getLoginUser(request).getCompanyIds()));
			}
			request.setAttribute("topTagMenuCode", MenuCache.getMenuUrlList().get("LoginController/home"));
			request.setAttribute("pageType", "home");
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
		return "home/home";
	}
	
	/**
	 * 跳转-统计分析
	 * @return
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(HttpServletRequest request, HttpServletResponse response) {
		try{
			this.setCookie(request, response);
			this.loadProperties(request);
			// 顶部页签菜单列表
			boolean hasTopTag = true;// 是否包含顶部页签
			boolean hasMenu = false;//是否包含菜单
			boolean hasFun = false;// 是否包含功能
			boolean showFlag = false;// 没有权限，是否显示
			List<SystemMenuEntity> menuList = null;
			SystemUserEntity loginUser = this.getLoginUser(request);
			if(null == loginUser){
				return "login/index";
			}
			Long userId = loginUser.getUserId();
			String topTagMenuCode = null;// 查询指定顶部页签节点下的菜单,为空不过滤
			if(super.isSuperAdmin(request)){
				showFlag = true;// 没有权限，是否显示
				menuList = systemMenuService.getMenuList(hasTopTag, hasMenu, hasFun, showFlag, userId, topTagMenuCode);
				request.setAttribute("menuList", menuList);
				request.setAttribute("companyEnum", CompanyEnum.getList());
			}else{
				// 验证(LoginController/index)是否有权限
				if(!this.checkMenuUrlByLoginUser(request, "LoginController/index")){
					// 无权限
					return "redirect:system";
				}
				// 验证是否有官网报表权限(HomePageChartController/page)
				if(!this.checkMenuUrlByLoginUser(request, "HomePageChartController/page")){
					// 无权限,则不显示
					request.setAttribute("showGWBBPage", "N");
				}
				menuList = systemMenuService.getMenuList(hasTopTag, hasMenu, hasFun, showFlag, userId, topTagMenuCode);
				request.setAttribute("menuList", menuList);
				request.setAttribute("companyEnum", CompanyEnum.getList(this.getLoginUser(request).getCompanyIds()));
			}
			request.setAttribute("topTagMenuCode", MenuCache.getMenuUrlList().get("LoginController/index"));
			request.setAttribute("pageType", "index");
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
		return "login/index";
	}

	/**
	 * 跳转-系统管理
	 * @return
	 */
	@RequestMapping(value = "/system", method = { RequestMethod.GET })
	public String system(HttpServletRequest request, HttpServletResponse response) {
		try{
			this.setCookie(request, response);
			this.loadProperties(request);
			// 顶部页签菜单列表
			boolean hasTopTag = true;// 是否包含顶部页签
			boolean hasMenu = false;//是否包含菜单
			boolean hasFun = false;// 是否包含功能
			boolean showFlag = false;// 没有权限，是否显示
			List<SystemMenuEntity> menuList = null;
			SystemUserEntity loginUser = this.getLoginUser(request);
			if(null == loginUser){
				return "home/home";
			}
			Long userId = loginUser.getUserId();
			String topTagMenuCode = null;// 查询指定顶部页签节点下的菜单,为空不过滤
			if(super.isSuperAdmin(request)){
				showFlag = true;// 没有权限，是否显示
				menuList = systemMenuService.getMenuList(hasTopTag, hasMenu, hasFun, showFlag, userId, topTagMenuCode);
				request.setAttribute("menuList", menuList);
				request.setAttribute("companyEnum", CompanyEnum.getList());
			}else{
				menuList = systemMenuService.getMenuList(hasTopTag, hasMenu, hasFun, showFlag, userId, topTagMenuCode);
				request.setAttribute("menuList", menuList);
				request.setAttribute("companyEnum", CompanyEnum.getList(this.getLoginUser(request).getCompanyIds()));
			}
			// 官网报表不显示
			request.setAttribute("showGWBBPage", "N");
			request.setAttribute("topTagMenuCode", MenuCache.getMenuUrlList().get("LoginController/system"));
			request.setAttribute("pageType", "system");
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
		return "login/system";
	}
	
	/**
	 * 登陆后提取顶部页签菜单列表
	 */
	@RequestMapping(value = "/loadTopMenuList", method = { RequestMethod.POST })
	@ResponseBody
	public List<SystemMenuEntity> loadTopMenuList(HttpServletRequest request) {
		try{
			boolean hasTopTag = true;// 是否包含顶部页签
			boolean hasMenu = false;//是否包含菜单
			boolean hasFun = false;// 是否包含功能
			boolean showFlag = false;// 没有权限，是否显示
			Long userId = this.getLoginUser(request).getUserId();
			String topTagMenuCode = null;// 查询指定顶部页签节点下的菜单,为空不过滤
			List<SystemMenuEntity> list = systemMenuService.getMenuList(hasTopTag, hasMenu, hasFun, showFlag, userId, topTagMenuCode);
			return list;
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<SystemMenuEntity>();
		}
	}
	
	/**
	 * 登陆后加载menuAccordion
	 * @return
	 */
	@RequestMapping(value = "/loadMenuAccordion/{topTagMenuCode}", method = { RequestMethod.POST })
	@ResponseBody
	public List<TreeBean> loadMenuAccordion(@PathVariable String topTagMenuCode, HttpServletRequest request) {
		try{
			boolean hasTopTag = false;// 是否包含顶部页签
			boolean hasMenu = true;//是否包含菜单
			boolean hasFun = false;// 是否包含功能
			boolean showFlag = false;// 没有权限，是否显示
			Long userId = this.getLoginUser(request).getUserId();
			if(super.isSuperAdmin(request)){
				return systemMenuService.getMenuTreeJson(hasTopTag, hasMenu, hasFun, topTagMenuCode);
			}else{
				return systemMenuService.getMenuTreeJsonByUserId(hasTopTag, hasMenu, hasFun, showFlag, userId, userId, topTagMenuCode);
			}
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<TreeBean>();
		}
	}

	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value = "/loginOut", method = { RequestMethod.GET })
	public String loginOut(HttpServletRequest request, HttpServletResponse response) {
		delCookie(request, response);
		request.getSession().removeAttribute(SessionKeyEnum.client.getLabelKey());
		this.loadProperties(request);
		return "login/login";
	}

	/**
	 * 生成验证码
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/png");
		try {
			String code = ValidateCodeUtil.captcha();
			request.getSession().setAttribute(SessionKeyEnum.captcha.getLabelKey(), code);
			response.getOutputStream().write(ValidateCodeUtil.getBytes(ImageUtil.identifying(code)));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/updatePassword", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode updatePassword(HttpServletRequest request, 
			@RequestParam String oldPassword,
			@RequestParam String newPassword) {
		try{
			if(oldPassword.equals(newPassword)){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.passwordTheSame));
				return resultCode;
			}
			SystemUserEntity userEntity =  this.getLoginUser(request);
			if(MD5.getMd5(oldPassword).equals(userEntity.getPassword())){
				systemUserService.updatePassword(userEntity.getUserId(), newPassword);
				userEntity.setPassword(MD5.getMd5(newPassword));
				return new ResultCode(ResultCodeEnum.success);
			}else{
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.oldPasswordError));
				return resultCode;
			}
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	
	/**
	 * 业务权限change事件
	 */
	@RequestMapping(value = "/changeCompanyId", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode changeCompanyId(HttpServletRequest request, @RequestParam Long companyId) {
		try{
			// 查询拥有的业务权限
			List<CompanyEnum> list = null;
			SystemUserEntity loginUserEntity = this.getLoginUser(request);
			if(super.isSuperAdmin(request)){
				list = CompanyEnum.getList();
			}else{
				list = CompanyEnum.getList(loginUserEntity.getCompanyIds());
			}
			
			// 具有该业务权限标示
			boolean temp = false;
			if(null != list && list.size()>0){
				for(CompanyEnum companyEnum: list){
					if(companyEnum.getLabelKey().equals(companyId + "")){
						temp = true;
						break;
					}
				}
			}
			
			// 验证是否具有该业务权限
			if(!temp){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.passwordTheSame));
				return resultCode;
			}
			
			// 改变当前业务类型
			request.getSession().setAttribute(SessionKeyEnum.companyId.getLabelKey(), companyId);
			
			// 需重新设置菜单权限存入session
			if(super.isSuperAdmin(request)){
				request.getSession().setAttribute(SessionKeyEnum.menuMap.getLabelKey(), null);
			}else{
				Map<String, SystemMenuEntity> map = systemMenuService.findMenuMapByUserId(loginUserEntity.getUserId(), companyId);
				request.getSession().setAttribute(SessionKeyEnum.menuMap.getLabelKey(), map);
			}
			
			// 返回成功
			return new ResultCode(ResultCodeEnum.success);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
}
