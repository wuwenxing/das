package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * ErrorCode类型定义
 * @author wayne
 */
public enum ErrorCodeEnum implements EnumIntf {
	// 初始数据校验相关
	initDataTip("该数据为系统初始数据，不可修改与删除", "initDataTip"),
	superAdminResetPassword("该数据只可由superAdmin自己重置", "superAdminResetPassword"),

	// 编号校验相关
	userCodeExists("用户账号已经存在,请重新输入", "userCodeExists"),
	codeExists("编号已经存在,请重新输入", "codeExists"),
	blacklistAccountExists("黑名单账户已经存在", "blacklistAccountExists"),
	tagExists("标签已经存在", "tagExists"),
	channelTypeError("该渠道类型错误", "channelTypeError"),
	channelExists("渠道来源并媒介已经存在", "channelExists"),
	templateCodeExists("模板编号已经存在", "templateCodeExists"),
	groupCodeExists("分组编号已经存在", "groupCodeExists"),
	childNodeExists("删除的节点存在子节点，请先删除子节点", "childNodeExists"),
	custConsultationExists("的新客有效咨询数据已经存在", "custConsultationExists"),
	
	// 登录校验相关
	userNameNotEmpty("用户名不能为空", "userNameNotEmpty"),
	passwordNotEmpty("密码不能为空", "passwordNotEmpty"),
	companyNotEmpty("请选择公司", "companyNotEmpty"),
	validateCodeNotEmpty("验证码不能为空", "validateCodeNotEmpty"),
	validateCodeError("验证码错误", "validateCodeError"),
	userNameError("用户名错误", "userNameError"),
	passwordError("密码错误", "passwordError"),
	userDisable("此用户被禁用", "userDisable"),
	roleEmpty("此用户未分配角色", "roleEmpty"),
	roleNotExists("此用户分配的角色不存在", "roleNotExists"),
	roleDisable("此用户分配的角色被禁用", "roleDisable"),
	roleNotCompanyIds("此用户分配的角色未分配业务权限", "roleNotCompanyIds"),
	roleCompanyIdsError("此用户分配的角色的业务权限错误", "roleCompanyIdsError"),
	notCompanyIds("此用户未分配业务权限", "notCompanyIds"),
	companyIdsError("此用户业务权限错误", "companyIdsError"),
	error("用户名或密码错误", "error"),
	sessionTimeOut("session超时", "sessionTimeOut"),
	noPermission("此用户无权限", "noPermission"),

	// 修改密码相关
	oldPasswordError("原密码错误", "oldPasswordError"),
	passwordTheSame("新密码与原密码相同", "passwordTheSame"),
	
	// 短信发送相关
	smsSourceTypeError("手机号筛选条件设置错误", "smsSourceTypeError"),
	smsSendTypeError("短信发送类型设置错误", "smsSendTypeError"),
	smsPhoneError("不存在合法的手机号", "smsPhoneError"),
	smsSendError_1("即时发送短信的记录，不可修改", "smsSendError_1"),
	smsUploadSizeError("一次上传最多上传50000个手机号码", "smsUploadSizeError"),
	smsContentError("短信内容中不需要加签名及退订回T，请检查", "smsContentError"),
	
	// 邮件发送相关
	emailTemplateNotExists("邮件模板不存在", "emailTemplateNotExists"),
	emailSourceTypeError("邮箱筛选条件设置错误", "smsSourceTypeError"),
	emailSendTypeError("邮件发送类型设置错误", "smsSendTypeError"),
	emailPhoneError("不存在合法的邮箱", "smsPhoneError"),
	emailSendError_1("即时发送邮件的记录，不可修改", "smsSendError_1"),
	emailUploadSizeError("一次上传最多上传50000个邮箱", "emailUploadSizeError"),
	
	// 手工录入交易指标
	dateTimeIsExists("日期已经存在，不能重复", "dateTimeIsExists")
	;
	
	private final String value;
	private final String labelKey;
	ErrorCodeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<ErrorCodeEnum> getList(){
		List<ErrorCodeEnum> result = new ArrayList<ErrorCodeEnum>();
		for(ErrorCodeEnum ae : ErrorCodeEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
