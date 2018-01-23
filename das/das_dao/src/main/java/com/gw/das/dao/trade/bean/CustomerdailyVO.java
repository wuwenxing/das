package com.gw.das.dao.trade.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 账户结算日报表VO类
 * 
 * @author darren
 * 
 */
public class CustomerdailyVO {
	
	/* 佣金 */
	private String rowkey;	
	
	/* 当日平仓盈亏 */
	private String balancePreviousbalance;
	
	/* 当日平仓盈亏 */
	private String balancepreviousbalance;
	
	/* 账号 */
	private String accountno;
	
	/* 账户余额 */
	private String balance;
	
	/* 账户净值 */
	private String equity;
	
	/* 存取款*/
	private String depositWithdraw;
	
	/* 日期*/
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String reportdate;
	
	/* 浮动盈亏 */
	private String floatingprofit;
	
	/*占用保证金 */
	private String margin;
	
	/* 可用保证金 */
	private String freemargin;
	
	/*平台(GTS, MT4, MT5 ,...) */
	private String platform;
	
	/* 业务类型*/
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String businessplatform;
	
	
	/*存款*/
	private String deposit;
	
	/* 取款*/
	private String withdraw;
	
	/* 日期*/
	private String execdaterpt;


	public String getRowkey() {
		return rowkey;
	}

	public String getBalancepreviousbalance() {
		return balancepreviousbalance;
	}



	public void setBalancepreviousbalance(String balancepreviousbalance) {
		this.balancepreviousbalance = balancepreviousbalance;
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

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}

	public String getExecdaterpt() {
		return execdaterpt;
	}

	public void setExecdaterpt(String execdaterpt) {
		this.execdaterpt = execdaterpt;
	}
    
}
