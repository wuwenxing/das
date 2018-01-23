package com.gw.das.business.dao.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.dao.base.BaseSearchModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DasWebLandingpageDetailSearchModel extends BaseSearchModel {

	// 日期-开始日期
	private String startTime;

	// 日期-结束日期
	private String endTime;

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
	// 设备类型复选框勾选标示
	private boolean deviceTypeChecked;
	// 渠道复选框勾选标示
	private boolean channelChecked;
	
	// 设备类型
	private String deviceType;
	
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

}
