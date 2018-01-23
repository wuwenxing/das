package com.gw.das.dao.trade.bean;

/**
 * 平仓交易表VO类
 * 
 * @author darren
 * 
 */
public class DealVO {
	
	/* 佣金 */
	private String rowkey;
	
	private String orderid;
	
	private String dealid;
	
	private String accountid;
	
	private String companyid;
	
	private String companyname;
	
	private String rate;
	
	private String currency;
	
	private String symbolname;
	
	private String symbolshortname;
	
	private String symboltype;
	
	private String opentimegmt8;
	
	private String closetimegmt8;
	
	private String opentimerpt;
	
	private String closetimerpt;
	
	private String proposalno;
	
	private String proposaltype;
	
	private String clienttype;
	
	private String accounttype;
	
	private String direction;
	
	private String status;
	
	private String ordertype;
	
	private String sl;
	
	private String tp;
	
	private String margin;
	
	private String marginmaintenance;
	
	private String marginstopout;
	
	private String volumeaxu;
	
	private String volumeaxg;
	
	private String volumeaxucnh;
	
	private String volumeaxgcnh;
	
	private String bonus;
	
	private String bonusclear;
	
	private String clearzero;
	
	private String adjustamt;
	
	private String profitaxu;
	
	private String profitaxg;
	
	private String profitaxucnh;
	
	private String profitaxgcnh;
	
	private String surplusvolume;
	
	/* 平仓时间 */
	private String closetime;
	
	/* 账号 */
	private String accountno;
	
	/* 开仓价 */
	private String openprice;
	
	/* 平仓时间 */
	private String opentime;
	
	/* 交易货币*/
	private String symbol;	
	
	/* 开仓方向 */
	private String opendirection;
	
	/* 开仓手数 */
	private String openvolume;
	
	/*平仓手数 */
	private String closevolume;
	
	/* 剩余手数 */
	private String volume;
	
	/* 止损设置 */
	private String stoploss;
	
	/* 止盈设置 */
	private String takeprofit;
	
	/* 持仓号 */
	private String positionid;
	
	/* 平仓方向 */
	private String closedirection;
	
	/* 平仓价格*/
	private String closeprice;
	
	/* 佣金 */
	private String commission;
	
	/* 过夜利息*/
	private String swap;
	
	/* 盈亏 */
	private String profit;
	
	/* 交易类型 */
	private String reason;
	
	/* 开仓单号 */
	private String openorderid;
	
	/* 平仓单号 */
	private String closeorderid;
	
	/*  盈亏比例*/
	private String profitrase;
	
	/*平台(GTS, MT4, MT5 ,...) */
	private String platform;
	
	/* 业务类型 */
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

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getDealid() {
		return dealid;
	}

	public void setDealid(String dealid) {
		this.dealid = dealid;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSymbolname() {
		return symbolname;
	}

	public void setSymbolname(String symbolname) {
		this.symbolname = symbolname;
	}

	public String getSymbolshortname() {
		return symbolshortname;
	}

	public void setSymbolshortname(String symbolshortname) {
		this.symbolshortname = symbolshortname;
	}

	public String getSymboltype() {
		return symboltype;
	}

	public void setSymboltype(String symboltype) {
		this.symboltype = symboltype;
	}

	public String getOpentimegmt8() {
		return opentimegmt8;
	}

	public void setOpentimegmt8(String opentimegmt8) {
		this.opentimegmt8 = opentimegmt8;
	}

	public String getClosetimegmt8() {
		return closetimegmt8;
	}

	public void setClosetimegmt8(String closetimegmt8) {
		this.closetimegmt8 = closetimegmt8;
	}

	public String getOpentimerpt() {
		return opentimerpt;
	}

	public void setOpentimerpt(String opentimerpt) {
		this.opentimerpt = opentimerpt;
	}

	public String getClosetimerpt() {
		return closetimerpt;
	}

	public void setClosetimerpt(String closetimerpt) {
		this.closetimerpt = closetimerpt;
	}

	public String getProposalno() {
		return proposalno;
	}

	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}

	public String getProposaltype() {
		return proposaltype;
	}

	public void setProposaltype(String proposaltype) {
		this.proposaltype = proposaltype;
	}

	public String getClienttype() {
		return clienttype;
	}

	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getMarginmaintenance() {
		return marginmaintenance;
	}

	public void setMarginmaintenance(String marginmaintenance) {
		this.marginmaintenance = marginmaintenance;
	}

	public String getMarginstopout() {
		return marginstopout;
	}

	public void setMarginstopout(String marginstopout) {
		this.marginstopout = marginstopout;
	}

	public String getVolumeaxu() {
		return volumeaxu;
	}

	public void setVolumeaxu(String volumeaxu) {
		this.volumeaxu = volumeaxu;
	}

	public String getVolumeaxg() {
		return volumeaxg;
	}

	public void setVolumeaxg(String volumeaxg) {
		this.volumeaxg = volumeaxg;
	}

	public String getVolumeaxucnh() {
		return volumeaxucnh;
	}

	public void setVolumeaxucnh(String volumeaxucnh) {
		this.volumeaxucnh = volumeaxucnh;
	}

	public String getVolumeaxgcnh() {
		return volumeaxgcnh;
	}

	public void setVolumeaxgcnh(String volumeaxgcnh) {
		this.volumeaxgcnh = volumeaxgcnh;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getBonusclear() {
		return bonusclear;
	}

	public void setBonusclear(String bonusclear) {
		this.bonusclear = bonusclear;
	}

	public String getClearzero() {
		return clearzero;
	}

	public void setClearzero(String clearzero) {
		this.clearzero = clearzero;
	}

	public String getAdjustamt() {
		return adjustamt;
	}

	public void setAdjustamt(String adjustamt) {
		this.adjustamt = adjustamt;
	}

	public String getProfitaxu() {
		return profitaxu;
	}

	public void setProfitaxu(String profitaxu) {
		this.profitaxu = profitaxu;
	}

	public String getProfitaxg() {
		return profitaxg;
	}

	public void setProfitaxg(String profitaxg) {
		this.profitaxg = profitaxg;
	}

	public String getProfitaxucnh() {
		return profitaxucnh;
	}

	public void setProfitaxucnh(String profitaxucnh) {
		this.profitaxucnh = profitaxucnh;
	}

	public String getProfitaxgcnh() {
		return profitaxgcnh;
	}

	public void setProfitaxgcnh(String profitaxgcnh) {
		this.profitaxgcnh = profitaxgcnh;
	}

	public String getSurplusvolume() {
		return surplusvolume;
	}

	public void setSurplusvolume(String surplusvolume) {
		this.surplusvolume = surplusvolume;
	}
	
	
}
