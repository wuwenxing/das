package com.gw.das.common.response;

import com.gw.das.common.enums.ErrorCodeEnum;

/**
 * 返回的错误消息对象
 * @author wayne
 */
public class ErrorCode {
	
	private String errorCode;
	private String errorMsg;

	/**
	 * 构造函数1
	 * @param resultCodeEnum
	 */
	public ErrorCode(String errorCode, String errorMsg){
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	/**
	 * 构造函数2
	 * @param errorCodeEnum
	 */
	public ErrorCode(ErrorCodeEnum errorCodeEnum) {
		this.errorCode = errorCodeEnum.getLabelKey();
		this.errorMsg = errorCodeEnum.getValue();
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
