package com.gw.das.common.response;

/**
 * utm系统-邮件消息-请求参数实体
 * 
 * @author wayne
 */
public class UtmEmailRequest {

	private String timestamp; // 时间戳，如："yyyyMMddHHmmss"，验证请求用户是否合法
	private String accountSid; // 开发者主账号
	private String sign; // 加密后的签名串，验证请求用户是否合法
	private String templateCode;// 邮件模板code，utm系统对应的短信模板编号
	private String templateParam;// 邮件参数，内容为：map形式的参数转化为json格式的字符串,如：{"name":"张三"}
	private String emails;// 邮箱，多个邮箱逗号隔开

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateParam() {
		return templateParam;
	}

	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

}
