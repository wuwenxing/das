package com.gw.das.dao.room.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.dao.base.BaseSearchModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartFlowDetailSearchBean extends BaseSearchModel{

	// 用户id
	private String userId;

	// 访问时间
	private String startTime;

	// 结束时间
	private String endTime;

	// 行为 访问(1)、咨询(2)、模拟开户(3)、真实开户(4)、首次入金(5)
	private String behaviorType;

	// 访问ip
	private String ip;

	// 访问url
	private String url;

	// 广告来源
	private String utmcsr;

	// 广告媒介
	private String utmcmd;

	// 用户输入关键词
	private String utmctr;

	// 广告系列
	private String utmccn;

	// 广告组
	private String utmcct;

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

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getUtmctr() {
		return utmctr;
	}

	public void setUtmctr(String utmctr) {
		this.utmctr = utmctr;
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
