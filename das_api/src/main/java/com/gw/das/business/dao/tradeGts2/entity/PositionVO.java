package com.gw.das.business.dao.tradeGts2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 开仓交易表VO类
 * 
 * @author darren
 * @since 2017-03-17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionVO extends BaseModel {

	/* HBase中的rowkey，分页查询时用 */
	@Column(name = "rowkey")
	private String rowkey;
	
	/*  止损设置 */
	@Column(name = "stoploss")
	private String stoploss;
	
	/* 账户 */
	@Column(name = "accountno")
	private String accountno;
	
	/* 开仓时间 */
	@Column(name = "opentime")
	private String opentime;
	
	/* 开仓方向 */
	@Column(name = "opendirection")
	private String opendirection;
	
	/*  交易货币 */
	@Column(name = "symbol")
	private String symbol;
	
	/* 开仓手数 */
	@Column(name = "openvolume")
	private String openvolume;
	
	/* 剩余手数 */
	@Column(name = "volume")
	private String volume;
	
	/* 开仓价格*/
	@Column(name = "openprice")
	private String openprice;
	
	/* 持仓号 */
	@Column(name = "positionid")
	private String positionid;
	
	/* 止盈设置  */
	@Column(name = "takeprofit")
	private String takeprofit;
	
	/* 佣金 */
	@Column(name = "commission")
	private String commission;
	
	/*过夜利息 */
	@Column(name = "swap")
	private String swap;
	
	/* 交易类型  */
	@Column(name = "reason")
	private String reason;
	
	/* 平仓单号 */
	@Column(name = "openorderid")
	private String openorderid;
	
	/* 平仓时间 */
	@Column(name = "closetime")
	private String closetime;
	
	/* 平台(GTS, MT4, MT5 ,...) */
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

	public String getStoploss() {
		return stoploss;
	}

	public void setStoploss(String stoploss) {
		this.stoploss = stoploss;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getOpentime() {
		return opentime;
	}

	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}

	public String getOpendirection() {
		return opendirection;
	}

	public void setOpendirection(String opendirection) {
		this.opendirection = opendirection;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getOpenvolume() {
		return openvolume;
	}

	public void setOpenvolume(String openvolume) {
		this.openvolume = openvolume;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getOpenprice() {
		return openprice;
	}

	public void setOpenprice(String openprice) {
		this.openprice = openprice;
	}

	public String getPositionid() {
		return positionid;
	}

	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}

	public String getTakeprofit() {
		return takeprofit;
	}

	public void setTakeprofit(String takeprofit) {
		this.takeprofit = takeprofit;
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

	public String getClosetime() {
		return closetime;
	}

	public void setClosetime(String closetime) {
		this.closetime = closetime;
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
