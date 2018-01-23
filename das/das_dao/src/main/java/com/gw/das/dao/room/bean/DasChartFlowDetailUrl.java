package com.gw.das.dao.room.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 对应表das_flow_detail_url
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartFlowDetailUrl {

	private String rowKey;

	// 详细汇总表ID
	private String flowDetailId;

	// 访问URL
	private String flowDetailUrl;

	// 标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	private String devicetype;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;

	// 广告媒介
	private String utmcmd;

	// 来源
	private String utmcsr;

	// 访问时间
	private String visitTime;

	// 页面查询条件
	private String visitTimeStart;

	// 页面查询条件
	private String visitTimeEnd;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getFlowDetailId() {
		return flowDetailId;
	}

	public void setFlowDetailId(String flowDetailId) {
		this.flowDetailId = flowDetailId;
	}

	public String getFlowDetailUrl() {
		return flowDetailUrl;
	}

	public void setFlowDetailUrl(String flowDetailUrl) {
		this.flowDetailUrl = flowDetailUrl;
	}

    

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
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

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getVisitTimeStart() {
		return visitTimeStart;
	}

	public void setVisitTimeStart(String visitTimeStart) {
		this.visitTimeStart = visitTimeStart;
	}

	public String getVisitTimeEnd() {
		return visitTimeEnd;
	}

	public void setVisitTimeEnd(String visitTimeEnd) {
		this.visitTimeEnd = visitTimeEnd;
	}

}
