package com.gw.das.dao.trade.bean;

/**
 * 公司盈亏VO类
 * 
 * @author darren
 * @since 2017-04-10
 */
public class DealprofithourVO {

	/* rowkey */
	private String rowkey;
	
	/*  盈亏 */
	private String profit;
	
	/* 佣金 */
	private String commission;
	
	/* 利息 */
	private String swap;
	
	/* 公司盈亏 */
	private String companyprofit;
	
	/*  当前小时 */
	private Integer hour;
	
	/* 货币类型 */
	private String currency;
	
	/* 执行时间 */
	private String exectime;
	
	/* 新旧平台标识*/
	private String source;
	
	/* 平台类型 */
	private String platform;
	
	/* 商业平台标识  */
	private String businessplatform;
	
	/* 分区字段(按年分) */
	private String partitionfield;
	
	/* 公司ID */
	private String companyid;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
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

	public String getCompanyprofit() {
		return companyprofit;
	}

	public void setCompanyprofit(String companyprofit) {
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
