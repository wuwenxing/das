package com.gw.das.dao.trade.bean;

/**
 * 交易统计-手数
 */
public class DasTradeGts2Mt4Top10VolumeStatisticsYears {

	/** rowkey */
	private String rowKey;

	/** 时间 */
	private String datetime;

	/** 手数范围 */
	private String volumeRange;

	/** 平仓手数 */
	private Double closedvolume = 0D;

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

	public String getVolumeRange() {
		return volumeRange;
	}

	public void setVolumeRange(String volumeRange) {
		this.volumeRange = volumeRange;
	}

	public Double getClosedvolume() {
		return closedvolume;
	}

	public void setClosedvolume(Double closedvolume) {
		this.closedvolume = closedvolume;
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
