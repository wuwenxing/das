package com.gw.das.common.response;

import com.gw.das.common.enums.ResCodeEnum;

/**
 * utm系统-返回json消息实体对象
 * 
 * @author wayne
 */
public class UtmResult {

	private String respCode;
	private String respMsg;
	private String result; // 请求结果

	/**
	 * 构造函数
	 */
	public UtmResult() {
		
	}
	
	/**
	 * 构造函数
	 */
	public UtmResult(String respCode, String respMsg) {
		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	/**
	 * 构造函数
	 */
	public UtmResult(ResCodeEnum resCodeEnum) {
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
