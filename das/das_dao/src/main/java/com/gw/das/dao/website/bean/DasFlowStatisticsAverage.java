package com.gw.das.dao.website.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 开户数平均统计
 * 
 * @author kirin.guan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasFlowStatisticsAverage {
	
	// 日期
	private String dataTime;

	// 广告媒介
	private String utmcmd;

	// 开户数
	private Integer accountCount;

	// 上月平均数
	private Double lastMonthAvgCount = 0d;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;

	// 外汇行为类型 3，4，5
	private String behaviorType;

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getUtmcmd() {
		return utmcmd;
	}

	public void setUtmcmd(String utmcmd) {
		this.utmcmd = utmcmd;
	}

	public Integer getAccountCount() {
		return accountCount;
	}

	public void setAccountCount(Integer accountCount) {
		this.accountCount = accountCount;
	}

	public Double getLastMonthAvgCount() {
		return lastMonthAvgCount;
	}

	public void setLastMonthAvgCount(Double lastMonthAvgCount) {
		this.lastMonthAvgCount = lastMonthAvgCount;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}
	
}
