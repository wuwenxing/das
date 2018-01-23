package com.gw.das.common.sms;

/**
 * 秒嘀科技-短信发送-返回实体
 * 
 * @author wayne
 *
 */
public class MdkjSmsResponseDetail {

	private String phone;
	private String respCode;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

}
