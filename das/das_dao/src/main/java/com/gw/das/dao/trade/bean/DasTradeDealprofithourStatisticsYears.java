package com.gw.das.dao.trade.bean;

/**
 * 公司盈亏
 */
public class DasTradeDealprofithourStatisticsYears {
	/** rowkey */
	private String rowKey;

	/** 盈亏 */
	private Double profit;

	/** 佣金 */
	private Double commission;

	/** 利息 */
	private Double swap;

	/** 公司盈亏 */
	private Double companyprofit;

	/** 当前小时 */
	private Long hour;

	/** 货币类型 */
	private String currency;

	/** 执行时间 */
	private String exectime;

	/** 新旧平台标识 */
	private String source;

	/** 平台类型 */
	private String platformtype;

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

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getSwap() {
		return swap;
	}

	public void setSwap(Double swap) {
		this.swap = swap;
	}

	public Double getCompanyprofit() {
		return companyprofit;
	}

	public void setCompanyprofit(Double companyprofit) {
		this.companyprofit = companyprofit;
	}

	public Long getHour() {
		return hour;
	}

	public void setHour(Long hour) {
		this.hour = hour;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getExectime() {
		return exectime;
	}

	public void setExectime(String exectime) {
		this.exectime = exectime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
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
