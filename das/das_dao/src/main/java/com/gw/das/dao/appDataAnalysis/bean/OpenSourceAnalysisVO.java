package com.gw.das.dao.appDataAnalysis.bean;

/**
 * 开户来源分析报表VO类
 * 
 * @author Darren
 * @since 2017-03-22
 */
public class OpenSourceAnalysisVO {

	/* 月 */
	private String dtMonth;
	
	/* 登录的广告位*/
	private Integer dtDay = 0;
	
	/* 内嵌开户页面 */
	private Integer partitionfield = 0;
	
	/* 直播间 */
	private Integer channel = 0;
	
	/* 登录的广告位*/
	private Integer mobiletype = 0;
	
	/* 内嵌开户页面 */
	private Integer activeuser = 0;
	
	/* 直播间*/
	private Integer newuser = 0;
	
	/* 业务类型 */
	private String businessplatform;

	public String getDtMonth() {
		return dtMonth;
	}

	public void setDtMonth(String dtMonth) {
		this.dtMonth = dtMonth;
	}

	public Integer getDtDay() {
		return dtDay;
	}

	public void setDtDay(Integer dtDay) {
		this.dtDay = dtDay;
	}

	public Integer getPartitionfield() {
		return partitionfield;
	}

	public void setPartitionfield(Integer partitionfield) {
		this.partitionfield = partitionfield;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Integer getMobiletype() {
		return mobiletype;
	}

	public void setMobiletype(Integer mobiletype) {
		this.mobiletype = mobiletype;
	}

	public Integer getActiveuser() {
		return activeuser;
	}

	public void setActiveuser(Integer activeuser) {
		this.activeuser = activeuser;
	}

	public Integer getNewuser() {
		return newuser;
	}

	public void setNewuser(Integer newuser) {
		this.newuser = newuser;
	}

	public String getBusinessplatform() {
		return businessplatform;
	}

	public void setBusinessplatform(String businessplatform) {
		this.businessplatform = businessplatform;
	}
	
	
	
	
}
