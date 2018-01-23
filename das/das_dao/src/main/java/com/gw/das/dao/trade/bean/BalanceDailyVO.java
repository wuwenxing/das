package com.gw.das.dao.trade.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 账户余额日结算表VO类
 * 
 * @author silvers
 * @since 2016-12-12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceDailyVO {

	/* 账号 */
	private String loginName;
	/* 日期 */
	private String bizdate;
	/* 账户余额 */
	private Double balance;
	/* 账户净值 */
	private Double netBalance;
	/* 存取款 */
	private Double cashInOut;
	/* 当日平仓盈亏 */
	private Double realProfit;
	/* 浮动盈亏 */
	private Double floatingProfit;
	/* 占用保证金 */
	private Double depositFrozen;
	/* 可用保证金 */
	private Double depositAvailable;
	/* HBase中的rowkey，分页查询时用 */
	private String rowkey;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getBizdate() {
		return bizdate;
	}

	public void setBizdate(String bizdate) {
		this.bizdate = bizdate;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getNetBalance() {
		return netBalance;
	}

	public void setNetBalance(Double netBalance) {
		this.netBalance = netBalance;
	}

	public Double getCashInOut() {
		return cashInOut;
	}

	public void setCashInOut(Double cashInOut) {
		this.cashInOut = cashInOut;
	}

	public Double getRealProfit() {
		return realProfit;
	}

	public void setRealProfit(Double realProfit) {
		this.realProfit = realProfit;
	}

	public Double getFloatingProfit() {
		return floatingProfit;
	}

	public void setFloatingProfit(Double floatingProfit) {
		this.floatingProfit = floatingProfit;
	}

	public Double getDepositFrozen() {
		return depositFrozen;
	}

	public void setDepositFrozen(Double depositFrozen) {
		this.depositFrozen = depositFrozen;
	}

	public Double getDepositAvailable() {
		return depositAvailable;
	}

	public void setDepositAvailable(Double depositAvailable) {
		this.depositAvailable = depositAvailable;
	}

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

}
