package com.gw.das.dao.room.bean;

import com.gw.das.dao.base.BaseSearchModel;

public class DasChartFlowDetailUrlSearchBean extends BaseSearchModel{

	// 用户id
	private String userId;
	
	// 详细汇总表ID
	private String flowDetailId;

	// 广告媒介
	private String utmcmd;

	// 来源
	private String utmcsr;

	// 访问URL
	private String flowDetailUrl;

	// 行为开始时间,格式如：2016-10-27 18:53:03
	private String startTime;

	// 行为结束时间,格式如：2016-10-27 18:53:03
	private String endTime;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;
	
	// 访问客户端
	private String devicetype;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFlowDetailId() {
		return flowDetailId;
	}

	public void setFlowDetailId(String flowDetailId) {
		this.flowDetailId = flowDetailId;
	}

	public String getUtmcmd() {
		return utmcmd;
	}

	public void setUtmcmd(String utmcmd) {
		this.utmcmd = utmcmd;
	}

	public String getUtmcsr() {
		return utmcsr;
	}

	public void setUtmcsr(String utmcsr) {
		this.utmcsr = utmcsr;
	}

	public String getFlowDetailUrl() {
		return flowDetailUrl;
	}

	public void setFlowDetailUrl(String flowDetailUrl) {
		this.flowDetailUrl = flowDetailUrl;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

}
