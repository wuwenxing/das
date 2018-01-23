package com.gw.das.common.sms;

/**
 * 发财鱼-短信发送-返回实体
 * @author wayne
 *
 */
public class FcySmsResponse {
	
	private String tradeNo;
	private String resCode;
	private String resMsg;
	private int status;
	private String liuliuOrderId;
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLiuliuOrderId() {
		return liuliuOrderId;
	}
	public void setLiuliuOrderId(String liuliuOrderId) {
		this.liuliuOrderId = liuliuOrderId;
	}
	
	
}
