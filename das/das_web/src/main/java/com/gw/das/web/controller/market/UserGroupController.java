package com.gw.das.web.controller.market;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.email.EmailUtil;
import com.gw.das.common.enums.AccountTypeEnum;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ResCodeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.response.UtmCustomerGroupRequest;
import com.gw.das.common.response.UtmResponse;
import com.gw.das.common.sms.SmsUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SignUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.UserGroupDetailEntity;
import com.gw.das.dao.market.entity.UserGroupEntity;
import com.gw.das.dao.market.entity.UserGroupLogEntity;
import com.gw.das.service.market.UserGroupDetailService;
import com.gw.das.service.market.UserGroupLogService;
import com.gw.das.service.market.UserGroupService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping({"/UserGroupController", "/customerGroup"})
public class UserGroupController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private UserGroupDetailService userGroupDetailService;
	@Autowired
	private UserGroupLogService userGroupLogService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			return "/market/userGroup/userGroup";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<UserGroupEntity> pageList(HttpServletRequest request,
			@ModelAttribute UserGroupEntity userGroupEntity) {
		try {
			PageGrid<UserGroupEntity> pageGrid = userGroupService.findPageList(super.createPageGrid(request, userGroupEntity));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<UserGroupEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public UserGroupEntity findById(Long groupId) {
		try {
			return userGroupService.findById(groupId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new UserGroupEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute UserGroupEntity userGroupEntity) {
		try {
			// 1、校验编号不能重复
			if (userGroupService.checkUserGroup(userGroupEntity.getCode(), userGroupEntity.getGroupId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.groupCodeExists));
				return resultCode;
			}

			// 2、新增操作
			userGroupService.saveOrUpdate(userGroupEntity);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 根据id删除
	 */
	@RequestMapping(value = "/deleteById", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode deleteById(String groupIdArray) {
		try {
			userGroupService.deleteByIdArray(groupIdArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute UserGroupEntity userGroupEntity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.userGroupListInfo.getPath())));
			// 2、需要导出的数据
			List<UserGroupEntity> recordList = userGroupService.findList(userGroupEntity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<UserGroupEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, UserGroupEntity param) {
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.userGroupListInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 更新客户分组信息
	 * 调用该接口，添加或者移除分组中的用户
	 * 根据开发者账号区分不同业务平台
	 */
	@RequestMapping(value = {"/updateUser", "/updateCustomer"}, method = { RequestMethod.POST })
	@ResponseBody
	public UtmResponse updateUser(HttpServletRequest request) {
		return updateUserHandler(request);
	}
	
	/**
	 * 添加或者移除分组中的用户
	 * 统一处理
	 */
	private UtmResponse updateUserHandler(HttpServletRequest request) {
		UtmResponse utmResponse = null;
		String postStr = "";
		Long groupId = null;
		// 参数定义
		Long companyId = null;
		String accountSid = "";
		String timestamp = "";
		String sign = "";
		String groupCode = "";
		String type = "";
		String phones = "";
		String emails = "";
		try {
			// 1.获取post过来的数据
			accountSid = request.getParameter("accountSid");
			timestamp = request.getParameter("timestamp");
			sign = request.getParameter("sign");
			groupCode = request.getParameter("groupCode");
			type = request.getParameter("type");
			phones = request.getParameter("phones");
			emails = request.getParameter("emails");

//			accountSid = "fa573c78eaa8402cb6c84dabfcce7158";
//			timestamp = "20161008140800";
//			sign = "e9ccb14450d1e7d03014defbf6d401e8";
//			groupCode = "group001";
//			type = "add";
//			phones = "13760291376,13760291376,eeeeee,54545454";
//			emails = "wuwenxing-nc@qq.com,wayne.wu@gwtsz.net,wayne.wu@gwtsz.com";
			
			UtmCustomerGroupRequest customerGroupRequest = new UtmCustomerGroupRequest();
			customerGroupRequest.setTimestamp(timestamp);
			customerGroupRequest.setAccountSid(accountSid);
			customerGroupRequest.setSign(sign);
			customerGroupRequest.setGroupCode(groupCode);
			customerGroupRequest.setType(type);
			customerGroupRequest.setPhones(phones);
			customerGroupRequest.setEmails(emails);
			postStr = JacksonUtil.toJSon(customerGroupRequest);

			// 1、开发者账号校验-验证
			if(StringUtils.isBlank(accountSid)){
				logger.error("开发者账号为空");
				utmResponse = new UtmResponse(ResCodeEnum.AccountSidIsNull);
				this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.AccountSidIsNull);
				return utmResponse;
			}else{
				if(SignUtil.FX_SID.equals(accountSid)){
					companyId = 1L;
				}else if(SignUtil.PM_SID.equals(accountSid)){
					companyId = 2L;
				}else if(SignUtil.HX_SID.equals(accountSid)){
					companyId = 3L;
				}else if(SignUtil.CF_SID.equals(accountSid)){
					companyId = 4L;
				}else{
					logger.error("开发者账号错误");
					utmResponse = new UtmResponse(ResCodeEnum.AccountSidError);
					this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.AccountSidError);
					return utmResponse;
				}
			}
			
			// 2、查询短信模板-验证
			if(StringUtils.isBlank(groupCode)){
				logger.error("客户分组编号为空");
				utmResponse = new UtmResponse(ResCodeEnum.GroupCodeIsNull);
				this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.GroupCodeIsNull);
				return utmResponse;
			}
			UserGroupEntity customerGroup = userGroupService.findByCode(groupCode, companyId);
			if (null == customerGroup) {
				logger.error("客户分组编号不存在[groupCode=" + groupCode + "]");
				utmResponse = new UtmResponse(ResCodeEnum.GroupCodeNotExist);
				this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.GroupCodeNotExist);
				return utmResponse;
			}else{
				groupId = customerGroup.getGroupId();
				if(!"Y".equals(customerGroup.getEnableFlag())){
					logger.error("客户分组被禁用[groupCode=" + groupCode + "]");
					utmResponse = new UtmResponse(ResCodeEnum.GroupCodeIsDisabled);
					this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.GroupCodeIsDisabled);
					return utmResponse;
				}
			}
			
			// 3、签名校验-验证
			if(StringUtils.isBlank(timestamp)){
				logger.error("请求时间戳为空");
				utmResponse = new UtmResponse(ResCodeEnum.TimestampIsNull);
				this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.TimestampIsNull);
				return utmResponse;
			}
			if(StringUtils.isBlank(sign)||!SignUtil.validate(timestamp, sign, accountSid)){
				logger.error("签名校验错误");
				utmResponse = new UtmResponse(ResCodeEnum.SignError);
				this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.SignError);
				return utmResponse;
			}
			
			// 4、操作类型-校验及验证
			if("add".equals(type)){
			}else if("remove".equals(type)){
			}else{
				logger.error("操作类型错误[groupCode=" + groupCode + "]");
				utmResponse = new UtmResponse(ResCodeEnum.TypeIsError);
				this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.TypeIsError);
				return utmResponse;
			}
			
			// 5、账户-手机号码格式校验及统计-校验及验证
			int phoneNum = 0;
			List<String> legalPhones = new ArrayList<String>();
			List<String> illegalPhones = new ArrayList<String>();
			if(StringUtils.isNotBlank(phones)){
				String[] phoneAry = phones.split(",");
				phoneNum = phoneAry.length;
				for (int i = 0; i < phoneAry.length; i++) {
					String tempPhone = phoneAry[i];
					if (!SmsUtil.checkMobileNum(tempPhone)) {
						logger.error("第" + (i + 1) + "个的手机号：" + tempPhone + "格式错误;");
						illegalPhones.add(tempPhone);
						continue;
					}
					if (legalPhones.contains(tempPhone)) {
						logger.error("第" + (i + 1) + "个的手机号重复;");
						illegalPhones.add(tempPhone);
						continue;
					}
					legalPhones.add(tempPhone);
				}
			}
			// 6、账户-邮箱格式校验及统计-校验及验证
			int emailNum = 0;
			List<String> legalEmails = new ArrayList<String>();
			List<String> illegalEmails = new ArrayList<String>();
			if(StringUtils.isNotBlank(emails)){
				String[] emailAry = emails.split(",");
				emailNum = emailAry.length;
				for (int i = 0; i < emailAry.length; i++) {
					String tempEmail = emailAry[i];
					if (!EmailUtil.checkEmail(tempEmail)) {
						logger.error("第" + (i + 1) + "个的邮箱：" + tempEmail + "格式错误;");
						illegalEmails.add(tempEmail);
						continue;
					}
					if (legalEmails.contains(tempEmail)) {
						logger.error("第" + (i + 1) + "个的邮箱重复;");
						illegalEmails.add(tempEmail);
						continue;
					}
					legalEmails.add(tempEmail);
				}
			}
			if(legalPhones.size() <= 0 && legalEmails.size() <= 0){
				logger.error("不存在合法的手机号或邮箱");
				utmResponse = new UtmResponse(ResCodeEnum.NoValidPhoneOrEmails);
				this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.NoValidPhoneOrEmails);
				return utmResponse;
			}

			// 7、成功-保存提交请求日志
			UserGroupLogEntity reqLog = new UserGroupLogEntity();
			reqLog.setParam(postStr);
			reqLog.setGroupId(groupId);
			reqLog.setStatusCode(ResCodeEnum.Success.getLabelKey());
			reqLog.setPhones(phones);
			reqLog.setInputPhoneNum(Long.parseLong(phoneNum+""));
			reqLog.setLegalPhoneNum(Long.parseLong(legalPhones.size()+""));
			reqLog.setIllegalPhoneNum(Long.parseLong(illegalPhones.size()+""));
			reqLog.setLegalPhones(StringUtil.list2String(legalPhones));
			reqLog.setIllegalPhones(StringUtil.list2String(illegalPhones));
			reqLog.setEmails(emails);
			reqLog.setInputEmailNum(Long.parseLong(emailNum+""));
			reqLog.setLegalEmailNum(Long.parseLong(legalEmails.size()+""));
			reqLog.setIllegalEmailNum(Long.parseLong(illegalEmails.size()+""));
			reqLog.setLegalEmails(StringUtil.list2String(legalEmails));
			reqLog.setIllegalEmails(StringUtil.list2String(illegalEmails));
			reqLog.setCreateDate(new Date());
			reqLog.setCompanyId(companyId);
			userGroupLogService.saveOrUpdate(reqLog);

			// 8、更新客户分组信息
			if("add".equals(type)){
				for(String phone: legalPhones){
					UserGroupDetailEntity detail = userGroupDetailService.findOne(groupId, phone, companyId);
					if(null != detail){
						detail.setEnableFlag("Y");
						detail.setUpdateDate(new Date());
						userGroupDetailService.saveOrUpdate(detail);
					}else{
						detail = new UserGroupDetailEntity();
						detail.setAccount(phone);
						detail.setAccountType(AccountTypeEnum.phone.getLabelKey());
						detail.setGroupId(groupId);
						detail.setEnableFlag("Y");
						detail.setCreateDate(new Date());
						detail.setUpdateDate(new Date());
						detail.setCompanyId(companyId);
						userGroupDetailService.saveOrUpdate(detail);
					}
				}
				for(String email: legalEmails){
					UserGroupDetailEntity detail = userGroupDetailService.findOne(groupId, email, companyId);
					if(null != detail){
						detail.setEnableFlag("Y");
						detail.setUpdateDate(new Date());
						userGroupDetailService.saveOrUpdate(detail);
					}else{
						detail = new UserGroupDetailEntity();
						detail.setAccount(email);
						detail.setAccountType(AccountTypeEnum.email.getLabelKey());
						detail.setGroupId(groupId);
						detail.setEnableFlag("Y");
						detail.setCreateDate(new Date());
						detail.setUpdateDate(new Date());
						detail.setCompanyId(companyId);
						userGroupDetailService.saveOrUpdate(detail);
					}
				}
			}else if("remove".equals(type)){
				for(String phone: legalPhones){
					UserGroupDetailEntity detail = userGroupDetailService.findOne(groupId, phone, companyId);
					if(null != detail){
						userGroupDetailService.deleteByIdArray(detail.getDetailId()+"");
					}else{
						logger.error("该客户组的["+groupId+"]的手机号["+phone+"]系统不存在");
					}
				}
				for(String email: legalEmails){
					UserGroupDetailEntity detail = userGroupDetailService.findOne(groupId, email, companyId);
					if(null != detail){
						userGroupDetailService.deleteByIdArray(detail.getDetailId()+"");
					}else{
						logger.error("该客户组的["+groupId+"]的邮箱["+email+"]系统不存在");
					}
				}
			}

			// 9、执行完毕，返回成功消息
			utmResponse = new UtmResponse(ResCodeEnum.Success);
			return utmResponse;
		} catch (Exception e) {
			logger.error("调用更新客户分组信息接口[" + postStr + "]出现异常" + e.getMessage(), e);
			utmResponse = new UtmResponse(ResCodeEnum.Exception);
			this.saveReqLog(postStr, phones, groupId, companyId, ResCodeEnum.Exception);
			return utmResponse;
		}
	}

	/**
	 * 验证失败时，保存请求日志处理
	 * @param postStr
	 * @param phones
	 * @param companyId
	 * @param resCodeEnum
	 */
	private void saveReqLog(String postStr, String phones, Long groupId, Long companyId, ResCodeEnum resCodeEnum){
		try{
			UserGroupLogEntity reqLog = new UserGroupLogEntity();
			reqLog.setParam(postStr);
			reqLog.setGroupId(groupId);
			reqLog.setPhones(phones);
			reqLog.setStatusCode(resCodeEnum.getLabelKey());
			reqLog.setCreateDate(new Date());
			reqLog.setCompanyId(companyId);
			userGroupLogService.saveOrUpdate(reqLog);
		}catch(Exception e){
			logger.error("保存提交请求日志error" + e.getMessage(), e);
		}
	}
	
}
