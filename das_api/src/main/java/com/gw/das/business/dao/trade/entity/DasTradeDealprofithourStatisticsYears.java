package com.gw.das.business.dao.trade.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 公司盈亏
 */
public class DasTradeDealprofithourStatisticsYears extends BaseModel {
	/** rowkey */
	@Column(name = "rowkey")
	private String rowKey;

	/** 盈亏 */
	@Column(name = "profit")
	private Double profit;

	/** 佣金 */
	@Column(name = "commission")
	private Double commission;

	/** 利息 */
	@Column(name = "swap")
	private Double swap;

	/** 公司盈亏 */
	@Column(name = "companyprofit")
	private Double companyprofit;

	/** 当前小时 */
	@Column(name = "hour")
	private Long hour;

	/** 货币类型 */
	@Column(name = "currency")
	private String currency;

	/** 执行时间 */
	@Column(name = "exectime")
	private String exectime;

	/** 新旧平台标识 */
	@Column(name = "source")
	private String source;

	/** 平台类型 */
	@Column(name = "platformtype")
	private String platformtype;

	/** companyid */
	@Column(name = "companyid")
	private String companyid;

	/** 业务类型 */
	@Column(name = "businessplatform")
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
