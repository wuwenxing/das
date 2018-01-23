package com.gw.das.api.common.response;

import java.io.Serializable;

import com.gw.das.api.common.utils.JacksonUtil;

/**
 * 接口返回对象
 * @author wayne
 */
public class ApiResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status; // 请求状态
	private String msg; // 请求消息
	private Object result; // 请求结果

	public ApiResult() {

	}

	public ApiResult(String errorMsg) {
		this.status = ApiStatusEnum.exception.getLabelKey();
		this.msg = errorMsg;
		this.result = "";
	}

	public ApiResult(ApiFailEnum failEnum) {
		this.status = ApiStatusEnum.fail.getLabelKey();
		this.msg = failEnum.getValue();
		this.result = "";
	}
	
	public ApiResult(ApiStatusEnum statusEnum) {
		this.status = statusEnum.getLabelKey();
		this.msg = statusEnum.getValue();
		this.result = "";
	}

	public ApiResult(ApiStatusEnum statusEnum, Object result) {
		this.status = statusEnum.getLabelKey();
		this.msg = statusEnum.getValue();
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {		
		return JacksonUtil.toJSon(this);
	}
   
	
}
