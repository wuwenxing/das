package com.gw.das.dao.trade.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 交易表VO类
 * 
 * @author silvers
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeDetailVO {
	/* 佣金 */
	private Double charge;
	/* 平仓订单号 */
	private String closeOrderId;
	/* 平仓方向(0,买;1,卖) */
	private Integer closeOrderType;
	/* 平仓价格 */
	private Double closePrice;
	/* 平仓时间 */
	private String closeTime;
	/* 开仓委托订单号 */
	private String entrustOpenOrderId;
	/* 止盈设置 */
	private Double gainLimit;
	/* 过夜利息 */
	private Double interest;
	/* 账户名 */
	private String loginName;
	/* 止损设置 */
	private Double lossLimit;
	/* 手数 */
	private Double lot;
	/* 开仓订单号 */
	private String openOrderId;
	/* 开仓方向(0,买;1,卖) */
	private Integer openOrderType;
	/* 开仓价格 */
	private Double openPrice;
	/* 开仓时间 */
	private String openTime;
	/* 交易货币(LLG,伦敦金;LLS,伦敦银;XAUCNH,人民币黄金;XAGCNH,人民币白银;......) */
	private String productId;
	/* 盈亏 */
	private Double profit;
	/* 盈亏点数 */
	private Long profitPoint;
	/* 平台(GTS, MT4, MT5 ,...) */
	private String sys;
	/* 平仓类型（例：普通市场价 强制平仓 止盈 止损） */
	private String remark;
	/* HBase中的rowkey，分页查询时用 */
	private String rowkey;

	public Double getCharge() {
		return charge;
	}

	public void setCharge(Double charge) {
		this.charge = charge;
	}

	public String getCloseOrderId() {
		return closeOrderId;
	}

	public void setCloseOrderId(String closeOrderId) {
		this.closeOrderId = closeOrderId;
	}

	public Integer getCloseOrderType() {
		return closeOrderType;
	}

	public void setCloseOrderType(Integer closeOrderType) {
		this.closeOrderType = closeOrderType;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getEntrustOpenOrderId() {
		return entrustOpenOrderId;
	}

	public void setEntrustOpenOrderId(String entrustOpenOrderId) {
		this.entrustOpenOrderId = entrustOpenOrderId;
	}

	public Double getGainLimit() {
		return gainLimit;
	}

	public void setGainLimit(Double gainLimit) {
		this.gainLimit = gainLimit;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Double getLossLimit() {
		return lossLimit;
	}

	public void setLossLimit(Double lossLimit) {
		this.lossLimit = lossLimit;
	}

	public Double getLot() {
		return lot;
	}

	public void setLot(Double lot) {
		this.lot = lot;
	}

	public String getOpenOrderId() {
		return openOrderId;
	}

	public void setOpenOrderId(String openOrderId) {
		this.openOrderId = openOrderId;
	}

	public Integer getOpenOrderType() {
		return openOrderType;
	}

	public void setOpenOrderType(Integer openOrderType) {
		this.openOrderType = openOrderType;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Long getProfitPoint() {
		return profitPoint;
	}

	public void setProfitPoint(Long profitPoint) {
		this.profitPoint = profitPoint;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

}
