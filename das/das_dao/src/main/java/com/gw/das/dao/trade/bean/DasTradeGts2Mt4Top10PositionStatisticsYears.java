package com.gw.das.dao.trade.bean;

/**
 * 交易统计-持仓时间
 */
public class DasTradeGts2Mt4Top10PositionStatisticsYears {

	/** rowkey */
	private String rowKey;

	/** 时间 */
	private String datetime;

	/** 时间范围 */
	private String timeRange;

	/** 次数 */
	private Double dealamount = 0D;

	/** 百分比 */
	private Double percent = 0D;

	/** 当天净盈亏 */
	private Double cleanProfit = 0D;

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

	public String getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}

	public Double getDealamount() {
		return dealamount;
	}

	public void setDealamount(Double dealamount) {
		this.dealamount = dealamount;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Double getCleanProfit() {
		return cleanProfit;
	}

	public void setCleanProfit(Double cleanProfit) {
		this.cleanProfit = cleanProfit;
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
