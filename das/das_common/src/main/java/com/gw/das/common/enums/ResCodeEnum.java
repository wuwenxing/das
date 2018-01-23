package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * ResCode类型定义-仅用于第三方接口
 * @author wayne
 */
public enum ResCodeEnum implements EnumIntf {
	Fail("失败", "Fail", "all"),
	Success("成功", "Success", "all"),
	Exception("系统异常", "Exception", "all"),
	MoreThanValid("超过有效期", "MoreThanValid", "all"),
	
	AccountSidIsNull("开发者账号为空", "AccountSidIsNull", "all"),
	AccountSidError("开发者账号错误", "AccountSidError", "all"),
	TimestampIsNull("请求时间戳为空", "TimestampIsNull", "all"),
	SignError("签名验证错误", "SignError", "all"),
	
	TemplateCodeIsNull("短信模板编号为空", "TemplateCodeIsNull", "smsTemplate"),
	TemplateCodeNotExist("短信模板编号不存在", "TemplateCodeNotExist", "smsTemplate"),
	TemplateIsDisabled("短信模板被禁用", "TemplateIsDisabled", "smsTemplate"),
	TemplateParamNotMatch("短信模板参数不匹配", "TemplateParamNotMatch", "smsTemplate"),
	PhoneIsNull("手机号为空", "PhoneIsNull", "smsTemplate"),
	NoValidPhone("不存在合法的手机号", "NoValidPhone", "smsTemplate"),
	
	EmailTemplateCodeIsNull("邮件模板编号为空", "EmailTemplateCodeIsNull", "emailTemplate"),
	EmailTemplateCodeNotExist("邮件模板编号不存在", "EmailTemplateCodeNotExist", "emailTemplate"),
	EmailTemplateIsDisabled("邮件模板被禁用", "EmailTemplateIsDisabled", "emailTemplate"),
	EmailTemplateParamNotMatch("邮件模板参数不匹配", "EmailTemplateParamNotMatch", "emailTemplate"),
	EmailIsNull("邮箱为空", "EmailIsNull", "emailTemplate"),
	NoValidEmail("不存在合法的邮箱", "NoValidEmail", "emailTemplate"),
	
	GroupCodeIsNull("客户分组编号为空", "GroupCodeIsNull", "userGroup"),
	GroupCodeNotExist("客户分组编号不存在", "GroupCodeNotExist", "userGroup"),
	GroupCodeIsDisabled("客户分组被禁用", "GroupCodeIsDisabled", "userGroup"),
	TypeIsError("操作类型错误", "TypeIsError", "userGroup"),
	NoValidPhoneOrEmails("不存在合法的手机号或邮箱", "NoValidPhoneOrEmails", "userGroup"),
	
	flowChannelIsNull("utm未设置流量充值通道", "flowChannelIsNull", "flow"),
	// 需为（20M,30M,50M,70M,100M,120M,150M,200M,300M,500M,800M,1G,2G）其中一个
	flowSizeError("充值的流量大小有误", "flowSizeError", "flow"),
	
	dateFormatError("日期格式错误", "dateFormatError", "")
	
	
	;
	
	private final String value;
	private final String labelKey;
	private final String type;
	ResCodeEnum(String _operator, String labelKey, String type) {
		this.value = _operator;
		this.labelKey = labelKey;
		this.type = type;
	}
	
	public static List<ResCodeEnum> getList(){
		List<ResCodeEnum> result = new ArrayList<ResCodeEnum>();
		for(ResCodeEnum ae : ResCodeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static List<ResCodeEnum> getList(String type){
		List<ResCodeEnum> result = new ArrayList<ResCodeEnum>();
		result.add(ResCodeEnum.Fail);
		result.add(ResCodeEnum.Success);
		result.add(ResCodeEnum.Exception);
		result.add(ResCodeEnum.AccountSidIsNull);
		result.add(ResCodeEnum.AccountSidError);
		result.add(ResCodeEnum.TimestampIsNull);
		result.add(ResCodeEnum.SignError);
		if("smsTemplate".equals(type)){
			result.add(ResCodeEnum.TemplateCodeIsNull);
			result.add(ResCodeEnum.TemplateCodeNotExist);
			result.add(ResCodeEnum.TemplateIsDisabled);
			result.add(ResCodeEnum.TemplateParamNotMatch);
			result.add(ResCodeEnum.PhoneIsNull);
			result.add(ResCodeEnum.NoValidPhone);
		}else if("emailTemplate".equals(type)){
			result.add(ResCodeEnum.EmailTemplateCodeIsNull);
			result.add(ResCodeEnum.EmailTemplateCodeNotExist);
			result.add(ResCodeEnum.EmailTemplateIsDisabled);
			result.add(ResCodeEnum.EmailTemplateParamNotMatch);
			result.add(ResCodeEnum.EmailIsNull);
			result.add(ResCodeEnum.NoValidEmail);
		}else if("userGroup".equals(type)){
			result.add(ResCodeEnum.GroupCodeIsNull);
			result.add(ResCodeEnum.GroupCodeNotExist);
			result.add(ResCodeEnum.GroupCodeIsDisabled);
			result.add(ResCodeEnum.TypeIsError);
			result.add(ResCodeEnum.NoValidPhoneOrEmails);
		}else if("flow".equals(type)){
			result.add(ResCodeEnum.PhoneIsNull);
			result.add(ResCodeEnum.NoValidPhone);
			result.add(ResCodeEnum.flowChannelIsNull);
			result.add(ResCodeEnum.flowSizeError);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(ResCodeEnum ae : ResCodeEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public String getType() {
		return type;
	}
	
}
