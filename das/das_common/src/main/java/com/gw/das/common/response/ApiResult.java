package com.gw.das.common.response;

import java.io.Serializable;

/**
 * 账户诊断接口返回对象
 * @author wayne
 */
public class ApiResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status; // 请求状态
	private String msg; // 请求消息
	private Object result; // 请求结果

	public ApiResult() {

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

}
