package com.gw.das.dao.trade.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CusTransVO {

	/* 交易类型 */
	private String code;
	/* id */
	private String fcusTransId;
	/* 时间 */
	private String joinTime;
	/* 账号 */
	private String loginName;
	/* 备注 */
	private String remark;
	/* 订单号 */
	private String tradeNo;
	/* 变动金额 */
	private Double transAmount;
	/* HBase中的rowkey，分页查询时用 */
	private String rowkey;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFcusTransId() {
		return fcusTransId;
	}

	public void setFcusTransId(String fcusTransId) {
		this.fcusTransId = fcusTransId;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

}
