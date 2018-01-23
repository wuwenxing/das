package com.gw.das.common.flow;

/**
 * 容联流量充值-返回实体
 * 
 * @author wayne
 *
 */
public class RlFlowResponse {
	// 请求状态码，取值000000（成功）
	private String statusCode;
	// 对状态码的描述
	private String statusMsg;
	// 流量网关产生的唯一业务标识id
	private String rechargeId;
	// 流量充值的结果1,充值中4,失败（测试档位返回3.成功）
	private int status;
	// 第三方交易id
	private String customId;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

}
