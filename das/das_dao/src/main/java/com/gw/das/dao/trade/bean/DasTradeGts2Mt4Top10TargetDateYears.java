package com.gw.das.dao.trade.bean;

/**
 * 十大 赢家输家、存取款、交易量 汇总数据
 */
public class DasTradeGts2Mt4Top10TargetDateYears {

	/** rowkey */
	private String rowKey;

	/** 时间 */
	private String datetime;

	/** 账户 */
	private String accountNo;

	/** 中文名 */
	private String chineseName;

	/** 当天净盈亏 */
	private Double cleanProfit;

	/** 开户总净盈亏 */
	private Double accountCleanProfit;

	/** 当天存款 */
	private Double cashin;
	
	/** 当天取款 */
	private Double cashout;
	
	/** 开户总存款 */
	private Double accountCashin;
	
	/** 开户总取款 */
	private Double accountCashout;
	
	/** 平仓手数 */
	private Double closedvolume;
	
	/** 开户总平仓手数 */
	private Double accountClosedVolume;
	
	/** 来源 */
	private String source;

	/** 平台(GTS, MT4, MT5 ,...) */
	private String platform;

	/** companyid */
	private String companyid;

	/** 业务类型 */
	private String businessPlatform;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public Double getCleanProfit() {
		return cleanProfit;
	}

	public void setCleanProfit(Double cleanProfit) {
		this.cleanProfit = cleanProfit;
	}

	public Double getAccountCleanProfit() {
		return accountCleanProfit;
	}

	public void setAccountCleanProfit(Double accountCleanProfit) {
		this.accountCleanProfit = accountCleanProfit;
	}

	public Double getCashin() {
		return cashin;
	}

	public void setCashin(Double cashin) {
		this.cashin = cashin;
	}

	public Double getCashout() {
		return cashout;
	}

	public void setCashout(Double cashout) {
		this.cashout = cashout;
	}

	public Double getAccountCashin() {
		return accountCashin;
	}

	public void setAccountCashin(Double accountCashin) {
		this.accountCashin = accountCashin;
	}

	public Double getAccountCashout() {
		return accountCashout;
	}

	public void setAccountCashout(Double accountCashout) {
		this.accountCashout = accountCashout;
	}

	public Double getClosedvolume() {
		return closedvolume;
	}

	public void setClosedvolume(Double closedvolume) {
		this.closedvolume = closedvolume;
	}

	public Double getAccountClosedVolume() {
		return accountClosedVolume;
	}

	public void setAccountClosedVolume(Double accountClosedVolume) {
		this.accountClosedVolume = accountClosedVolume;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
