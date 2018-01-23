package com.gw.das.netty;

import java.io.Serializable;

/**
 * 调用接口返回对象
 * @author wayne
 *
 */
public class RpcResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status; // 请求状态
	private String msg; // 请求消息
	private String result; // 请求结果

	public RpcResult() {

	}

	public RpcResult(String errorMsg) {
		this.status = RpcStatusEnum.exception.getLabelKey();
		this.msg = errorMsg;
		this.result = "";
	}

	public RpcResult(RpcFailEnum failEnum) {
		this.status = RpcStatusEnum.fail.getLabelKey();
		this.msg = failEnum.getValue();
		this.result = "";
	}

	public RpcResult(RpcStatusEnum statusEnum, String result) {
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
