package com.gw.das.business.dao.tradeGts2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 账户结算日报表VO类
 * 
 * @author darren
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerdailyVO extends BaseModel {
	
	/* 佣金 */
	@Column(name = "rowkey")
	private String rowkey;	
	
	/* 当日平仓盈亏 */
	@Column(name = "balance_previousbalance")
	private String balancePreviousbalance;
	
	/* 账号 */
	@Column(name = "accountno")
	private String accountno;
	
	/* 账户余额 */
	@Column(name = "balance")
	private String balance;
	
	/* 账户净值 */
	@Column(name = "equity")
	private String equity;
	
	/* 存取款*/
	@Column(name = "deposit_withdraw")
	private String depositWithdraw;
	
	/* 日期*/
	@Column(name = "reportdate")
	private String reportdate;
	
	/* 浮动盈亏 */
	@Column(name = "floatingprofit")
	private String floatingprofit;
	
	/*占用保证金 */
	@Column(name = "margin")
	private String margin;
	
	/* 可用保证金 */
	@Column(name = "freemargin")
	private String freemargin;
	
	/*平台(GTS, MT4, MT5 ,...) */
	@Column(name = "platform")
	private String platform;
	
	/* 业务类型*/
	@Column(name = "businessplatform")
	private String businessplatform;

	public String getRowkey() {
		return rowkey;
	}


	public String getBalancePreviousbalance() {
		return balancePreviousbalance;
	}

	public void setBalancePreviousbalance(String balancePreviousbalance) {
		this.balancePreviousbalance = balancePreviousbalance;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getEquity() {
		return equity;
	}

	public void setEquity(String equity) {
		this.equity = equity;
	}
	
	public String getDepositWithdraw() {
		return depositWithdraw;
	}

	public void setDepositWithdraw(String depositWithdraw) {
		this.depositWithdraw = depositWithdraw;
	}

	public String getReportdate() {
		return reportdate;
	}

	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}

	public String getFloatingprofit() {
		return floatingprofit;
	}

	public void setFloatingprofit(String floatingprofit) {
		this.floatingprofit = floatingprofit;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getFreemargin() {
		return freemargin;
	}

	public void setFreemargin(String freemargin) {
		this.freemargin = freemargin;
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

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

}
