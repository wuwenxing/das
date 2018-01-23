package com.gw.das.business.dao.appDataAnalysis.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseSearchModel;

/**
 * 开户来源分析报表VO
 * 
 * @author Darren
 * @since 2017-03-22
 */
public class OpenSourceAnalysisVO extends BaseSearchModel{

	/* 月 */
	@Column(name = "dt_month")
	private String dtMonth;
	
	/* 登录的广告位*/
	@Column(name = "dt_month")
	private Integer dtDay;
	
	/* 内嵌开户页面 */
	@Column(name = "dt_month")
	private Integer partitionfield;
	
	/* 直播间 */
	@Column(name = "dt_month")
	private Integer channel;
	
	/* 登录的广告位*/
	@Column(name = "dt_month")
	private Integer mobiletype;
	
	/* 内嵌开户页面 */
	@Column(name = "dt_month")
	private Integer activeuser;
	
	/* 直播间*/
	@Column(name = "dt_month")
	private Integer newuser;
	
	/* 业务类型 */
	@Column(name = "dt_month")
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
