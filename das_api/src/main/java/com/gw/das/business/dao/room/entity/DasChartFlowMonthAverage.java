package com.gw.das.business.dao.room.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 流量月平均统计表
 *
 */
public class DasChartFlowMonthAverage extends BaseModel {
	
	@Column(name = "rowkey")
	private String rowKey;
	
	// 日期(yyyy-MM)
	@Column(name = "dataTime")
	private String dataTime;
	
	//格式化的时间 startTime yyyy-MM-dd
	@Column(name = "formatTime")
	private String formatTime;
	
	// 来源
	@Column(name = "utmcsr")
	private String utmcsr;
	
	// 广告媒介
	@Column(name = "utmcmd")
	private String utmcmd;
	
	//标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessPlatform")
	private String businessPlatform;
	
	//标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	@Column(name = "devicetype")
	private String devicetype;
	
	@Column(name = "visitAvgCount")
	private double visitAvgCount;

	@Column(name = "advisoryQQAvgCount")
	private double advisoryQQAvgCount;
	
	@Column(name = "advisoryLive800AvgCount")
	private double advisoryLive800AvgCount;
	
	@Column(name = "demoAvgCount")
	private double demoAvgCount;
	
	@Column(name = "realAvgCount")
	private double realAvgCount;
	
	@Column(name = "depositAvgCount")
	private double depositAvgCount;
	
	//设备次数
	@Column(name = "deviceAvgCount ")
	private Integer deviceAvgCount  = 0;

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

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
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

	public double getVisitAvgCount() {
		return visitAvgCount;
	}

	public void setVisitAvgCount(double visitAvgCount) {
		this.visitAvgCount = visitAvgCount;
	}

	public double getAdvisoryQQAvgCount() {
		return advisoryQQAvgCount;
	}

	public void setAdvisoryQQAvgCount(double advisoryQQAvgCount) {
		this.advisoryQQAvgCount = advisoryQQAvgCount;
	}

	public double getAdvisoryLive800AvgCount() {
		return advisoryLive800AvgCount;
	}

	public void setAdvisoryLive800AvgCount(double advisoryLive800AvgCount) {
		this.advisoryLive800AvgCount = advisoryLive800AvgCount;
	}

	public double getDemoAvgCount() {
		return demoAvgCount;
	}

	public void setDemoAvgCount(double demoAvgCount) {
		this.demoAvgCount = demoAvgCount;
	}

	public double getRealAvgCount() {
		return realAvgCount;
	}

	public void setRealAvgCount(double realAvgCount) {
		this.realAvgCount = realAvgCount;
	}

	public double getDepositAvgCount() {
		return depositAvgCount;
	}

	public void setDepositAvgCount(double depositAvgCount) {
		this.depositAvgCount = depositAvgCount;
	}

	public Integer getDeviceAvgCount() {
		return deviceAvgCount;
	}

	public void setDeviceAvgCount(Integer deviceAvgCount) {
		this.deviceAvgCount = deviceAvgCount;
	}

    
	
	
}
