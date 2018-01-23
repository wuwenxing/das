package com.gw.das.common.sms;

/**
 * 发财鱼调用本系统-推送消息实体
 * @author wayne
 *
 */
public class FcySmsPush {
	
	private String tradeNo;// 交易流水
	// 订单id,格式S146666222222,13760291377,4;S146666222223,13760291378,5
	// [4为成功，5为失败]
	private String liuliuOrderId;
	private String mgs;// 响应描述
	private int type;// 推送类型[1-流量][2-话费][3-短信][4-上行短信]
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getLiuliuOrderId() {
		return liuliuOrderId;
	}
	public void setLiuliuOrderId(String liuliuOrderId) {
		this.liuliuOrderId = liuliuOrderId;
	}
	public String getMgs() {
		return mgs;
	}
	public void setMgs(String mgs) {
		this.mgs = mgs;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
