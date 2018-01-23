package com.gw.das.dao.operateStatistics.bean;

import com.gw.das.dao.base.BaseSearchModel;

public class OperateStatisticsModel extends BaseSearchModel {

	// 账号
	private String accountno;
	
	private String srcType;

	// 日期-指定日期
	private String dateTime;
	
	// 日期-开始日期
	private String startTime;

	// 日期-结束日期
	private String endTime;
	
	//平台
	private String platform;

	// 访问客户端
	private String platformType;

	// 公司id
	private String companyid;
	
	// 导出标识
	private String reportType;
	
	//设备类型
	private String deviceType;
	
	//渠道分组
	private String channelGroup;
	
	//渠道分级
	private String channelLevel;
	
	//渠道
	private String channel;
	
	//版本
	private String platformVersion;
	
	//马甲包
	private String platformName;
	
	//来源
	private String utmcsr;
	
	//媒件
	private String utmcmd;
	
	//系列
	private String utmccn;
	
	//组
	private String utmcct;
	
	//关键字
	private String utmctr;
	
	//landingPage
	private String landingPage;
		
	//设备类型复选框
	private boolean deviceTypeChecked;
	
	//渠道分组复选框
	private boolean channelGroupChecked;
	
	//渠道分级复选框
	private boolean channelLevelChecked;
	
	//渠道复选框
	private boolean channelChecked;	
	
	//版本复选框
	private boolean platformVersionChecked;
	
	//马甲包复选框	
	private boolean platformNameChecked;
	
	//来源复选框
	private boolean utmcsrChecked;
	
	//媒件复选框
	private boolean utmcmdChecked;
	
	//系列复选框
	private boolean utmccnChecked;
	
	//组复选框
	private boolean utmcctChecked;
	
	//关键字复选框
	private boolean utmctrChecked;
	
	//landingPageSearch复选框
	private boolean landingPageChecked;

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	
	public String getSrcType() {
		return srcType;
	}

	public void setSrcType(String srcType) {
		this.srcType = srcType;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getChannelGroup() {
		return channelGroup;
	}

	public void setChannelGroup(String channelGroup) {
		this.channelGroup = channelGroup;
	}

	public String getChannelLevel() {
		return channelLevel;
	}

	public void setChannelLevel(String channelLevel) {
		this.channelLevel = channelLevel;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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

	public String getLandingPage() {
		return landingPage;
	}

	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}

	public boolean isDeviceTypeChecked() {
		return deviceTypeChecked;
	}

	public void setDeviceTypeChecked(boolean deviceTypeChecked) {
		this.deviceTypeChecked = deviceTypeChecked;
	}

	public boolean isChannelGroupChecked() {
		return channelGroupChecked;
	}

	public void setChannelGroupChecked(boolean channelGroupChecked) {
		this.channelGroupChecked = channelGroupChecked;
	}

	public boolean isChannelLevelChecked() {
		return channelLevelChecked;
	}

	public void setChannelLevelChecked(boolean channelLevelChecked) {
		this.channelLevelChecked = channelLevelChecked;
	}

	public boolean isChannelChecked() {
		return channelChecked;
	}

	public void setChannelChecked(boolean channelChecked) {
		this.channelChecked = channelChecked;
	}

	public boolean isPlatformVersionChecked() {
		return platformVersionChecked;
	}

	public void setPlatformVersionChecked(boolean platformVersionChecked) {
		this.platformVersionChecked = platformVersionChecked;
	}

	public boolean isPlatformNameChecked() {
		return platformNameChecked;
	}

	public void setPlatformNameChecked(boolean platformNameChecked) {
		this.platformNameChecked = platformNameChecked;
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

	public boolean isLandingPageChecked() {
		return landingPageChecked;
	}

	public void setLandingPageChecked(boolean landingPageChecked) {
		this.landingPageChecked = landingPageChecked;
	}

}
