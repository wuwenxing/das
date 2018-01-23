package com.gw.das.dao.website.bean;

public class DasFlowStatisticsSearchBean {

	// 来源
	private String utmcsr;
	// 广告媒介
	private String utmcmd;
	// 系列
	private String utmccn;
	// 组
	private String utmcct;
	// 关键字
	private String utmctr;

	//
	private String dataTime;

	// 开始时间
	private String startTime;

	// 结束时间
	private String endTime;

	// 开始环比时间
	private String startTimeCompare;

	// 结束环比时间
	private String endTimeCompare;

	//
	private String searchType;

	//
	private String behaviorType;

	//
	private String sortName = "dataTime";

	//
	private String sortDirection = "desc";

	// utmcsr勾选标示
	private boolean utmcsrChecked;
	// utmcmd勾选标示
	private boolean utmcmdChecked;
	// 系列复选框勾选标示
	private boolean utmccnChecked;
	// 组复选框勾选标示
	private boolean utmcctChecked;
	// 关键字复选框勾选标示
	private boolean utmctrChecked;

	//
	private String utmcsrList;

	//
	private String utmcmdList;

	private String channelIds;// 渠道名称

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;

	// 访问客户端
	private String platformType;

	private String utmcsrAndUtmcmd;

	public String getUtmcsr() {
		return utmcsr;
	}

	public void setUtmcsr(String utmcsr) {
		this.utmcsr = utmcsr;
	}

	public String getUtmcmd() {
		return utmcmd;
	}

	public void setUtmcmd(String utmcmd) {
		this.utmcmd = utmcmd;
	}

	public String getUtmccn() {
		return utmccn;
	}

	public void setUtmccn(String utmccn) {
		this.utmccn = utmccn;
	}

	public String getUtmcct() {
		return utmcct;
	}

	public void setUtmcct(String utmcct) {
		this.utmcct = utmcct;
	}

	public String getUtmctr() {
		return utmctr;
	}

	public void setUtmctr(String utmctr) {
		this.utmctr = utmctr;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
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

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public boolean isUtmcsrChecked() {
		return utmcsrChecked;
	}

	public void setUtmcsrChecked(boolean utmcsrChecked) {
		this.utmcsrChecked = utmcsrChecked;
	}

	public boolean isUtmcmdChecked() {
		return utmcmdChecked;
	}

	public void setUtmcmdChecked(boolean utmcmdChecked) {
		this.utmcmdChecked = utmcmdChecked;
	}

	public boolean isUtmccnChecked() {
		return utmccnChecked;
	}

	public void setUtmccnChecked(boolean utmccnChecked) {
		this.utmccnChecked = utmccnChecked;
	}

	public boolean isUtmcctChecked() {
		return utmcctChecked;
	}

	public void setUtmcctChecked(boolean utmcctChecked) {
		this.utmcctChecked = utmcctChecked;
	}

	public boolean isUtmctrChecked() {
		return utmctrChecked;
	}

	public void setUtmctrChecked(boolean utmctrChecked) {
		this.utmctrChecked = utmctrChecked;
	}

	public String getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getUtmcsrList() {
		return utmcsrList;
	}

	public void setUtmcsrList(String utmcsrList) {
		this.utmcsrList = utmcsrList;
	}

	public String getUtmcmdList() {
		return utmcmdList;
	}

	public void setUtmcmdList(String utmcmdList) {
		this.utmcmdList = utmcmdList;
	}

	public String getStartTimeCompare() {
		return startTimeCompare;
	}

	public void setStartTimeCompare(String startTimeCompare) {
		this.startTimeCompare = startTimeCompare;
	}

	public String getEndTimeCompare() {
		return endTimeCompare;
	}

	public void setEndTimeCompare(String endTimeCompare) {
		this.endTimeCompare = endTimeCompare;
	}

	public String getUtmcsrAndUtmcmd() {
		return utmcsrAndUtmcmd;
	}

	public void setUtmcsrAndUtmcmd(String utmcsrAndUtmcmd) {
		this.utmcsrAndUtmcmd = utmcsrAndUtmcmd;
	}

}
