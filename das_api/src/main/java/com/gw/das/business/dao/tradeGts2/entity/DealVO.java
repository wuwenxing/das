package com.gw.das.business.dao.tradeGts2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 平仓交易表VO类
 * 
 * @author darren
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DealVO extends BaseModel {
	
	/* 佣金 */
	@Column(name = "rowkey")
	private String rowkey;
	
	/* 平仓时间 */
	@Column(name = "closetime")
	private String closetime;
	
	/* 账号 */
	@Column(name = "accountno")
	private String accountno;
	
	/* 开仓价 */
	@Column(name = "openprice")
	private String openprice;
	
	/* 开仓时间 */
	@Column(name = "opentime")
	private String opentime;
	
	/* 交易货币*/
	@Column(name = "symbol")
	private String symbol;	
	
	/* 开仓方向 */
	@Column(name = "opendirection")
	private String opendirection;
	
	/* 开仓手数 */
	@Column(name = "openvolume")
	private String openvolume;
	
	/*平仓手数 */
	@Column(name = "closevolume")
	private String closevolume;
	
	/* 剩余手数 */
	@Column(name = "volume")
	private String volume;
	
	/* 止损设置 */
	@Column(name = "stoploss")
	private String stoploss;
	
	/* 止盈设置 */
	@Column(name = "takeprofit")
	private String takeprofit;
	
	/* 持仓号 */
	@Column(name = "positionid")
	private String positionid;
	
	/* 平仓方向 */
	@Column(name = "closedirection")
	private String closedirection;
	
	/* 平仓价格*/
	@Column(name = "closeprice")
	private String closeprice;
	
	/* 佣金 */
	@Column(name = "commission")
	private String commission;
	
	/* 过夜利息*/
	@Column(name = "swap")
	private String swap;
	
	/* 盈亏 */
	@Column(name = "profit")
	private String profit;
	
	/* 交易类型 */
	@Column(name = "reason")
	private String reason;
	
	/* 开仓单号 */
	@Column(name = "openorderid")
	private String openorderid;
	
	/* 平仓单号 */
	@Column(name = "closeorderid")
	private String closeorderid;
	
	/*  盈亏比例*/
	@Column(name = "profitrase")
	private String profitrase;
	
	/*平台(GTS, MT4, MT5 ,...) */
	@Column(name = "platform")
	private String platform;
	
	/* 业务类型 */
	@Column(name = "businessplatform")
	private String businessplatform;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getPositionid() {
		return positionid;
	}

	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getOpenprice() {
		return openprice;
	}

	public void setOpenprice(String openprice) {
		this.openprice = openprice;
	}

	public String getOpentime() {
		return opentime;
	}

	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getOpendirection() {
		return opendirection;
	}

	public void setOpendirection(String opendirection) {
		this.opendirection = opendirection;
	}

	public String getOpenvolume() {
		return openvolume;
	}

	public void setOpenvolume(String openvolume) {
		this.openvolume = openvolume;
	}

	public String getClosevolume() {
		return closevolume;
	}

	public void setClosevolume(String closevolume) {
		this.closevolume = closevolume;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getStoploss() {
		return stoploss;
	}

	public void setStoploss(String stoploss) {
		this.stoploss = stoploss;
	}

	public String getTakeprofit() {
		return takeprofit;
	}

	public void setTakeprofit(String takeprofit) {
		this.takeprofit = takeprofit;
	}

	public String getClosetime() {
		return closetime;
	}

	public void setClosetime(String closetime) {
		this.closetime = closetime;
	}

	public String getClosedirection() {
		return closedirection;
	}

	public void setClosedirection(String closedirection) {
		this.closedirection = closedirection;
	}

	public String getCloseprice() {
		return closeprice;
	}

	public void setCloseprice(String closeprice) {
		this.closeprice = closeprice;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getSwap() {
		return swap;
	}

	public void setSwap(String swap) {
		this.swap = swap;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOpenorderid() {
		return openorderid;
	}

	public void setOpenorderid(String openorderid) {
		this.openorderid = openorderid;
	}

	public String getCloseorderid() {
		return closeorderid;
	}

	public void setCloseorderid(String closeorderid) {
		this.closeorderid = closeorderid;
	}

	public String getProfitrase() {
		return profitrase;
	}

	public void setProfitrase(String profitrase) {
		this.profitrase = profitrase;
	}

	

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getBusinessplatform() {
		return businessplatform;
	}

	public void setBusinessplatform(String businessplatform) {
		this.businessplatform = businessplatform;
	}
	
	
}
