package com.gw.das.business.dao.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.dao.base.BaseSearchModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DasBehaviorEventChannelEffectSearchModel extends BaseSearchModel {

	// 报表类型: (hours:时段统计、days:日统计、weeks:周统计、months:月统计)
	private String reportType;
	// 日期-开始日期
	private String startTime;
	// 日期-结束日期
	private String endTime;
	// 设备类型
	private String deviceType;
	// 渠道分组
	private String channelGrp;
	// 渠道分级
	private String channelLevel;
	// 马甲包
	private String platformName;
	// 版本号
	private String platformVersion;
	// 广告来源
	private String utmcsr;
	// 广告媒介
	private String utmcmd;
	// 系列
	private String utmccn;
	// 组
	private String utmcct;
	// 关键字
	private String utmctr;
	// 渠道
	private String channel;
	// loadingPage
	private String landingPage;
	// 设备类型复选框勾选标示
	private boolean deviceTypeChecked;
	// 渠道分组复选框勾选标示
	private boolean channelGrpChecked;
	// 渠道级别复选框勾选标示
	private boolean channelLevelChecked;
	// 马甲包复选框勾选标示
	private boolean platformNameChecked;
	// 版本号复选框勾选标示
	private boolean platformVersionChecked;
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
	// 渠道复选框勾选标示
	private boolean channelChecked;
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
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

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

	public boolean isDeviceTypeChecked() {
		return deviceTypeChecked;
	}

	public void setDeviceTypeChecked(boolean deviceTypeChecked) {
		this.deviceTypeChecked = deviceTypeChecked;
	}

	public String getLandingPage() {
		return landingPage;
	}

	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public boolean isChannelChecked() {
		return channelChecked;
	}

	public void setChannelChecked(boolean channelChecked) {
		this.channelChecked = channelChecked;
	}

	public String getChannelGrp() {
		return channelGrp;
	}

	public void setChannelGrp(String channelGrp) {
		this.channelGrp = channelGrp;
	}

	public String getChannelLevel() {
		return channelLevel;
	}

	public void setChannelLevel(String channelLevel) {
		this.channelLevel = channelLevel;
	}

	public boolean isChannelGrpChecked() {
		return channelGrpChecked;
	}

	public void setChannelGrpChecked(boolean channelGrpChecked) {
		this.channelGrpChecked = channelGrpChecked;
	}

	public boolean isChannelLevelChecked() {
		return channelLevelChecked;
	}

	public void setChannelLevelChecked(boolean channelLevelChecked) {
		this.channelLevelChecked = channelLevelChecked;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public boolean isPlatformNameChecked() {
		return platformNameChecked;
	}

	public void setPlatformNameChecked(boolean platformNameChecked) {
		this.platformNameChecked = platformNameChecked;
	}

	public boolean isPlatformVersionChecked() {
		return platformVersionChecked;
	}

	public void setPlatformVersionChecked(boolean platformVersionChecked) {
		this.platformVersionChecked = platformVersionChecked;
	}

}
