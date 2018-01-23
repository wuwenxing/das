package com.gw.das.dao.website.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DasFlowAttributionSearchBean{

	// 广告来源
	private String utmcsr;
	
	// 广告媒介
	private String utmcmd;
	
	//开始时间
	private String startTime;
	
	//结束时间
	private String endTime;
	
	//标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;
	
	// 访问客户端
	private String platformType;

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

	
	public String getPlatformType()
	{
		return platformType;
	}

	
	public void setPlatformType(String platformType)
	{
		this.platformType = platformType;
	}
	
	
}
