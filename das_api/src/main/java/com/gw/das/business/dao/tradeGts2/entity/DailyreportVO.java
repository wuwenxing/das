package com.gw.das.business.dao.tradeGts2.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 结余图表报表VO类
 * 
 * @author darren
 * @since 2017-04-10
 */
public class DailyreportVO extends BaseModel {

	/* rowkey */
	@Column(name = "rowkey")
	private String rowkey;
	
	/*  浮动盈亏 */
	@Column(name = "floatingprofit")
	private String floatingprofit;
	
	/* 结余 */
	@Column(name = "balance")
	private String balance;
	
	/* 保证金 */
	@Column(name = "margin")
	private String margin;
	
	/*  浮动盈亏 */
	@Column(name = "mt4floatingprofit")
	private String mt4floatingprofit;
	
	/* 结余 */
	@Column(name = "mt4balance")
	private String mt4balance;
	
	/* 保证金 */
	@Column(name = "mt4margin")
	private String mt4margin;
	
	/*  浮动盈亏 */
	@Column(name = "gts2floatingprofit")
	private String gts2floatingprofit;
	
	/* 结余 */
	@Column(name = "gts2balance")
	private String gts2balance;
	
	/* 保证金 */
	@Column(name = "gts2margin")
	private String gts2margin;
	
	/* 货币种类 */
	@Column(name = "currency")
	private String currency;
	
	/*  执行时间 */
	@Column(name = "exectime")
	private String exectime;
	
	/* 新旧平台标识 */
	@Column(name = "source")
	private String source;
	
	/* 平台类型 */
	@Column(name = "platform")
	private String platform;
	
	/* 公司ID */
	@Column(name = "companyid")
	private String companyid;
	
	/* 商业平台标识 */
	@Column(name = "businessplatform")
	private String businessplatform;
	
	/* 分区字段(按年分) */
	@Column(name = "partitionfield")
	private String partitionfield;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getFloatingprofit() {
		return floatingprofit;
	}

	public void setFloatingprofit(String floatingprofit) {
		this.floatingprofit = floatingprofit;
	}	

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
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

	public String getMt4floatingprofit() {
		return mt4floatingprofit;
	}

	public void setMt4floatingprofit(String mt4floatingprofit) {
		this.mt4floatingprofit = mt4floatingprofit;
	}

	public String getMt4balance() {
		return mt4balance;
	}

	public void setMt4balance(String mt4balance) {
		this.mt4balance = mt4balance;
	}

	public String getMt4margin() {
		return mt4margin;
	}

	public void setMt4margin(String mt4margin) {
		this.mt4margin = mt4margin;
	}

	public String getGts2floatingprofit() {
		return gts2floatingprofit;
	}

	public void setGts2floatingprofit(String gts2floatingprofit) {
		this.gts2floatingprofit = gts2floatingprofit;
	}

	public String getGts2balance() {
		return gts2balance;
	}

	public void setGts2balance(String gts2balance) {
		this.gts2balance = gts2balance;
	}

	public String getGts2margin() {
		return gts2margin;
	}

	public void setGts2margin(String gts2margin) {
		this.gts2margin = gts2margin;
	}

	

}
