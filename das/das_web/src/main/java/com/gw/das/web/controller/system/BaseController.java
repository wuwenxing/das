package com.gw.das.web.controller.system;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.DictCodeEnum;
import com.gw.das.common.enums.SessionKeyEnum;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.JsonUtil;
import com.gw.das.common.utils.SignUtil;
import com.gw.das.common.utils.SystemPathUtil;
import com.gw.das.dao.system.entity.SystemMenuEntity;
import com.gw.das.dao.system.entity.SystemUserEntity;
import com.gw.das.service.base.LogService;
import com.gw.das.service.cache.DictCache;
import com.gw.das.web.sessionbean.Client;

/**
 * controller层公共方法
 * 
 * @author wayne
 */
public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private LogService logService;
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

	// 排除不需执行的URL
	public static Map<String, String> urls = new HashMap<String, String>();
	// 系统接口的URL集合
	public static Map<String, String> interfaceUrls = new HashMap<String, String>();
	// 不记录日志的Url集合
	public static Map<String, String> notInUrls = new HashMap<String, String>();
	
	static{
		// 排除不需执行的URL
		urls.put("login", "");
		urls.put("LoginController/login", "");
		urls.put("LoginController/loginOut", "");
		urls.put("LoginController/captcha", "");
		
		// 短信发送接口
		interfaceUrls.put("smsTemplate/send", "");
		interfaceUrls.put("SmsTemplateController/send", "");
		// 获取短信模板内容，获取短信发送次数
		interfaceUrls.put("smsTemplate/getContent", "");
		interfaceUrls.put("smsTemplate/getSendNum", "");
		interfaceUrls.put("SmsTemplateController/getContent", "");
		interfaceUrls.put("SmsTemplateController/getSendNum", "");
		// 邮件发送接口
		interfaceUrls.put("emailTemplate/send", "");
		interfaceUrls.put("EmailTemplateController/send", "");
		// 客户分组接口
		interfaceUrls.put("customerGroup/updateUser", "");
		interfaceUrls.put("customerGroup/updateCustomer", "");
		interfaceUrls.put("UserGroupController/updateUser", "");
		interfaceUrls.put("UserGroupController/updateCustomer", "");
		// 短信同步
		interfaceUrls.put("SmsController/syncSmsStatusByFcy", "");
		interfaceUrls.put("SmsController/syncSmsStatusByMdkj", "");
		interfaceUrls.put("SmsController/syncSmsStatusByZzhl", "");
		// 流量充值
		interfaceUrls.put("FlowLogController/send", "");
		// 流量充值状态同步
		interfaceUrls.put("FlowLogController/syncFlowStatusByYm", "");
		interfaceUrls.put("FlowLogController/syncFlowStatusByLm", "");
		interfaceUrls.put("FlowLogController/syncFlowStatusByRl", "");
		// 服务端生成图表图片的回调方法
		interfaceUrls.put("AccountAnalyzeController/persistCallback", "");

		// 不记录日志的Url集合
		notInUrls.put("HomePageChartController/hourChart", "");
		notInUrls.put("HomePageChartController/homeHourChart", "");
		notInUrls.put("HomePageChartController/findDayBehavior", "");
		notInUrls.put("AccountAnalyzeController/persistCallback", "");
	}
	
	/**
	 * ModelAttribute的作用：表示请求该类的每个Controller前都会首先执行它
	 * 
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void initHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", "3.2.1");
		response.setHeader("P3P", "CP=CAO PSA OUR");//处理ie跨域问题
		// 获得请求路径
		String requestPath = SystemPathUtil.getRequestPath(request);
		if (StringUtils.isNotBlank(requestPath)) {
			if (null != urls.get(requestPath)) {
				return;
			} else if (null != interfaceUrls.get(requestPath)) {
				this.interfaceHandle(request, response);
			} else {
				// 从session中获取登录的用户对象
				Client client = this.getLoginClient(request);
				if (null != client) {
					SystemUserEntity userEntity = client.getUser();
					// 登录设置当前用户
					String loginIp = client.getIp();
					String loginNo = userEntity.getUserNo();
					String loginName = userEntity.getUserName();
					Date loginDate = client.getLoginDate();
					String sessionId = client.getSessionId();
					String clientType = client.getClientType();
					Long companyId = this.getCompanyId(request);
					UserContext.setSystemUser(loginIp, loginNo, loginName, loginDate, sessionId, clientType, companyId);
					
					// 登录以后-记录日志
					if(null == notInUrls.get(requestPath)){
						Map<String, String> paramMap = new HashMap<String, String>();
						@SuppressWarnings("rawtypes")
						Enumeration enu = request.getParameterNames();
						while (enu.hasMoreElements()) {
							String paramName = (String) enu.nextElement();
							String paramValue = request.getParameter(paramName);
							// 如果是修改密码，则改成***号代替；
							if("oldPassword".equals(paramName)
									||"newPassword".equals(paramName)
									||"newPasswordAgin".equals(paramName)){
								// logger.info("paramName=" + paramValue);
								paramValue = "***";
							}
							// 参数超过50长度，日志记录，数据库省略
							else if (paramValue.length() > 50) {
								logger.info("paramName=" + paramValue);
								paramValue = paramValue.substring(0, 50) + "...";
							}
							paramMap.put(paramName, paramValue);
						}
						logService.addLog(requestPath, JacksonUtil.toJSon(paramMap));
					}
					
				} else {
					// 未登录默认跳转到登录页面
					response.sendRedirect("login");
					return;
				}
			}
		}
	}

	/**
	 * 调用系统接口-统一处理
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void interfaceHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String accountSid = request.getParameter("accountSid");
		if (StringUtils.isNotBlank(accountSid)) {
			String loginIp = this.getRemoteAddr(request);
			Long companyId = null;
			// 设置当前用户-系统接口
			if (SignUtil.GW_SID.equals(accountSid)) {
				companyId = 0L;
			} else if (SignUtil.FX_SID.equals(accountSid)) {
				companyId = 1L;
			} else if (SignUtil.PM_SID.equals(accountSid)) {
				companyId = 2L;
			} else if (SignUtil.HX_SID.equals(accountSid)) {
				companyId = 3L;
			} else if (SignUtil.CF_SID.equals(accountSid)) {
				companyId = 4L;
			} else {
				companyId = null;
			}
			UserContext.setSystemInterface(loginIp, companyId);
		}
	}

	/**
	 * 加载数据字典数据
	 */
	public void loadDict(HttpServletRequest request, DictCodeEnum... dictCodeEnum) {
		try {
			for (DictCodeEnum dict : dictCodeEnum) {
				request.setAttribute(dict.getLabelKey(), DictCache.getSubDictList(dict.getLabelKey()));
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 获取当前登录Client对象
	 * 
	 * @param request
	 */
	public Client getLoginClient(HttpServletRequest request) throws Exception {
		// 从session中获取登录的Client对象
		Object obj = request.getSession().getAttribute(SessionKeyEnum.client.getLabelKey());
		return (Client) obj;
	}

	/**
	 * 获取当前设置的业务类型
	 * 
	 * @param request
	 */
	public Long getCompanyId(HttpServletRequest request) throws Exception {
		// 从session中获取登录的Client对象
		String companyIdStr = request.getSession().getAttribute(SessionKeyEnum.companyId.getLabelKey()) + "";
		Long companyId = StringUtils.isNotBlank(companyIdStr) ? Long.parseLong(companyIdStr) : null;
		return companyId;
	}

	/**
	 * 获取当前登录用户SystemUserEntity对象
	 * 
	 * @param request
	 */
	public SystemUserEntity getLoginUser(HttpServletRequest request) throws Exception {
		Client client = getLoginClient(request);
		if(null != client){
			return client.getUser();
		}
		return null;
	}

	/**
	 * 获取当前登录用户是否超级管理员
	 * 
	 * @param request
	 */
	public boolean isSuperAdmin(HttpServletRequest request) throws Exception {
		String isSuperAdmin = request.getSession().getAttribute(SessionKeyEnum.superAdminFlag.getLabelKey()) + "";
		if ("Y".equals(isSuperAdmin)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前登录用户,拥有的菜单权限
	 * 
	 * @param request
	 */
	public Map<String, SystemMenuEntity> getMenuMapByLoginUser(HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, SystemMenuEntity> map = (Map<String, SystemMenuEntity>)request.getSession().getAttribute(SessionKeyEnum.menuMap.getLabelKey());
		return map;
	}

	/**
	 * 获取当前登录用户,是否拥有指定的菜单权限
	 * 
	 * @param request
	 * @param url
	 */
	public boolean checkMenuUrlByLoginUser(HttpServletRequest request, String url) throws Exception {
		Map<String, SystemMenuEntity> map = this.getMenuMapByLoginUser(request);
		if(null == map || null == map.get(url)){
			return false;
		}
		return true;
	}
	
	/**
	 * 功能：分页查询时构造公共的查询条件
	 */
	protected <T> PageGrid<T> createPageGrid(HttpServletRequest request, T t) throws Exception {
		PageGrid<T> pageGrid = new PageGrid<T>();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		pageGrid.setPageNumber(null == page ? 0 : Integer.parseInt(page));
		pageGrid.setPageSize(null == rows ? 10 : Integer.parseInt(rows));
		pageGrid.setSort(null == sort ? "" : sort);
		pageGrid.setOrder(null == order ? "" : order);
		pageGrid.setSearchModel(t);
		return pageGrid;
	}

	/**
	 * form 提交返回json数据,采用此方法,可解决IE兼容性提示下载问题
	 * 
	 * @param response
	 * @param obj
	 */
	protected void outWrite(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(JsonUtil.toJSONString(obj));
		out.flush();
	}

	/**
	 * 获取请求上下文中的真实IP地址
	 */
	protected String getRemoteAddr(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP
			int index = ip.indexOf(',');
			if (index > -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}

		ip = request.getHeader("Proxy-Client-IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("WL-Proxy-Client-IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("HTTP_CLIENT_IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("X-Cluster-Client-IP");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
}
