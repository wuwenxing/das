package com.gw.das.common.response;

import com.gw.das.common.enums.ResCodeEnum;

/**
 * utm系统-返回json消息实体对象
 * 
 * @author wayne
 */
public class UtmResponse {

	private String respCode;
	private String respMsg;

	/**
	 * 构造函数
	 */
	public UtmResponse() {
		
	}
	
	/**
	 * 构造函数
	 */
	public UtmResponse(String respCode, String respMsg) {
		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	/**
	 * 构造函数
	 */
	public UtmResponse(ResCodeEnum resCodeEnum) {
		this.respCode = resCodeEnum.getLabelKey();
		this.respMsg = resCodeEnum.getValue();
	}
	
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

}
