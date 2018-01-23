package com.gw.das.business.dao.dataSourceList.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 官网行为数据源
 * 
 * @author darren
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebsiteBehaviorDataSource extends BaseModel {

	private String rowkey;
	
	/* 会话标识 */
	private String sessionId;
	
	/* 设备浏览器标识ID*/
	private String userId;
	
	/* 标识业务平台*/
	private String businessPlatform;
	
	/* 平台名称 */
	private String platformName;
	
	/* 平台类型 （例：标识是手机还是pc访问，0和空代表pc访问，1代表手机m站访问）*/
	private String platformType;
	
	/* 平台产品的版本 */
	private String platformVersion;
	
	/* 设备标识ID */
	private String deviceId;
	
	/* 标记关键词 */
	private String markWords;
	
	/* 用户输入的关键词 */
	private String utmctr;
	
	/* 广告系列 */
	private String utmccn;
	
	/* 广告组*/
	private String utmcct;
	
	/* 广告媒介 */
	private String utmcmd;
	
	/* 广告来源 */
	private String utmcsr;
	
	/* 用户输入的关键词2 */
	private String utmctr2;
	
	/* 广告系列2*/
	private String utmccn2;
	
	/* 广告组2 */
	private String utmcct2;
	
	/* 广告媒介2 */
	private String utmcmd2;
	
	/* 广告来源2 */
	private String utmcsr2;
	
	/* 行为明细 */
	private String behaviorDetail;
	
	/* 行为类型 */
	private String behaviorType;
	
	/* 咨询类型 */
	private String advisoryType;
	
	/* 访问的上一个URL地址 */
	private String prevUrl;
	
	/* 通常是用户与之互动的对象 */
	private String eventCategory;
	
	/* 互动类型 */
	private String eventAction;
	
	/* 访问时间 */
	private String visitTime;
	
	/* 浏览器信息 */
	private String browser;
	
	/* 用户访问url */
	private String url;
	
	/* 访问设备ip */
	private String ip;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMarkWords() {
		return markWords;
	}

	public void setMarkWords(String markWords) {
		this.markWords = markWords;
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

	public String getUtmctr2() {
		return utmctr2;
	}

	public void setUtmctr2(String utmctr2) {
		this.utmctr2 = utmctr2;
	}

	public String getUtmccn2() {
		return utmccn2;
	}

	public void setUtmccn2(String utmccn2) {
		this.utmccn2 = utmccn2;
	}

	public String getUtmcct2() {
		return utmcct2;
	}

	public void setUtmcct2(String utmcct2) {
		this.utmcct2 = utmcct2;
	}

	public String getUtmcmd2() {
		return utmcmd2;
	}

	public void setUtmcmd2(String utmcmd2) {
		this.utmcmd2 = utmcmd2;
	}

	public String getUtmcsr2() {
		return utmcsr2;
	}

	public void setUtmcsr2(String utmcsr2) {
		this.utmcsr2 = utmcsr2;
	}

	public String getBehaviorDetail() {
		return behaviorDetail;
	}

	public void setBehaviorDetail(String behaviorDetail) {
		this.behaviorDetail = behaviorDetail;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public String getAdvisoryType() {
		return advisoryType;
	}

	public void setAdvisoryType(String advisoryType) {
		this.advisoryType = advisoryType;
	}

	public String getPrevUrl() {
		return prevUrl;
	}

	public void setPrevUrl(String prevUrl) {
		this.prevUrl = prevUrl;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public String getEventAction() {
		return eventAction;
	}

	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
