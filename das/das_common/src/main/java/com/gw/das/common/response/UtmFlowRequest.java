package com.gw.das.common.response;

/**
 * utm系统-流量充值-请求参数实体
 * 
 * @author wayne
 */
public class UtmFlowRequest {

	private String timestamp; // 时间戳，如："yyyyMMddHHmmss"，验证请求用户是否合法
	private String accountSid; // 开发者主账号
	private String sign; // 加密后的签名串，验证请求用户是否合法
	private String flowSize;// 流量大小
	private String phones;// 手机号，多个手机号逗号隔开

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

	public String getFlowSize() {
		return flowSize;
	}

	public void setFlowSize(String flowSize) {
		this.flowSize = flowSize;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

}
