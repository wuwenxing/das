package com.gw.das.common.sms;

/**
 * 恒信接口-返回实体
 * 
 * @author wayne
 *
 */
public class HxApiResponse {

	private String status;
	private String data;
	private String msg;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
