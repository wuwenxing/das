package com.gw.das.common.response;

/**
 * utm系统-客户分组消息-请求参数实体
 * 
 * @author wayne
 */
public class UtmCustomerGroupRequest {

	private String timestamp; // 时间戳，如："yyyyMMddHHmmss"，验证请求用户是否合法
	private String accountSid; // 开发者主账号
	private String sign; // 加密后的签名串，验证请求用户是否合法
	private String groupCode;// 客户分组code，utm系统对应的客户分组编号
	private String type;// 操作类型，add/remove
	private String phones;// 手机号，多个手机号逗号隔开
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

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

}
