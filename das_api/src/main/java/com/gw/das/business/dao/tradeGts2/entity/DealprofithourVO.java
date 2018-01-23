package com.gw.das.business.dao.tradeGts2.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 公司盈亏VO类
 * 
 * @author darren
 * @since 2017-04-10
 */
public class DealprofithourVO extends BaseModel {

	/* rowkey */
	@Column(name = "rowkey")
	private String rowkey;
	
	/*  盈亏 */
	@Column(name = "profit")
	private Double profit = 0D;
	
	/* 佣金 */
	@Column(name = "commission")
	private Double commission = 0D;
	
	/* 利息 */
	@Column(name = "swap")
	private Double swap = 0D;
	
	/* 公司盈亏 */
	@Column(name = "companyprofit")
	private Double companyprofit = 0D;
	
	/*  当前小时 */
	@Column(name = "hour")
	private Integer hour;
	
	/* 货币类型 */
	@Column(name = "currency")
	private String currency;
	
	/* 执行时间 */
	@Column(name = "exectime")
	private String exectime;
	
	/* 新旧平台标识*/
	@Column(name = "source")
	private String source;
	
	/* 平台类型 */
	@Column(name = "platform")
	private String platform;
	
	/* 商业平台标识  */
	@Column(name = "businessplatform")
	private String businessplatform;
	
	/* 分区字段(按年分) */
	@Column(name = "partitionfield")
	private String partitionfield;
	
	/* 公司ID */
	@Column(name = "companyid")
	private String companyid;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
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

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
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

	public String getPartitionfield() {
		return partitionfield;
	}

	public void setPartitionfield(String partitionfield) {
		this.partitionfield = partitionfield;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	
	
	

}
