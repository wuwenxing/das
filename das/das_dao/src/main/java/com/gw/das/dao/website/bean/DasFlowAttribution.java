package com.gw.das.dao.website.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 归因统计表 对应表das_flow_attribution
 * 
 * @author wayne
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasFlowAttribution {

	private String rowKey;

	// 日期(yyyy-MM-dd)分区字段
	private String dataTime;

	// 广告来源
	private String utmcsr;

	// 广告媒介
	private String utmcmd;

	// 模拟开户总数
	private Double demoCount;

	// 真实开户总数
	private Double realCount;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;

	// 标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	private String platformType;

	// 开始时间 查询用
	private Date dataTimeStart;

	// 结束时间 查询用
	private Date dataTimeEnd;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

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

	public Double getDemoCount() {
		return demoCount;
	}

	public void setDemoCount(Double demoCount) {
		this.demoCount = demoCount;
	}

	public Double getRealCount() {
		return realCount;
	}

	public void setRealCount(Double realCount) {
		this.realCount = realCount;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public Date getDataTimeStart() {
		return dataTimeStart;
	}

	public void setDataTimeStart(Date dataTimeStart) {
		this.dataTimeStart = dataTimeStart;
	}

	public Date getDataTimeEnd() {
		return dataTimeEnd;
	}

	public void setDataTimeEnd(Date dataTimeEnd) {
		this.dataTimeEnd = dataTimeEnd;
	}
}
