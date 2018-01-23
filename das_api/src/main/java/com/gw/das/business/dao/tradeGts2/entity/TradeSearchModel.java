package com.gw.das.business.dao.tradeGts2.entity;

import com.gw.das.business.dao.base.BaseSearchModel;

public class TradeSearchModel extends BaseSearchModel {

	// 账号
	private String accountno;

	// 日期-指定日期
	private String dateTime;
	
	// 日期-开始日期
	private String startTime;

	// 日期-结束日期
	private String endTime;

	/** 类型(0：十大赢家、1：十大亏损) */
	/** 类型(0：十大存款、1：十大取款) */
	private String type;

	// 访问客户端
	private String platformType;

	// 公司id
	private String companyid;
	
	// 详细
	private String detailed;
	
	// 导出标识
	private String reportType;
	
	private String accounttype;	
	
	//平台
	private String platform;
	
	//设备类型
	private String deviceType;
	
	//渠道分组
	private String channelgroup;
	
	//渠道分级
	private String channellevel;
	
	//渠道
	private String channel;
	
	//来源
	private String utmsource;
	
	//媒件
	private String utmmedium;
	
	//系列
	private String utmcampaign;
	
	//组
	private String utmcontent;
	
	//关键字
	private String utmterm;
	
	//平台复选框
	private boolean deviceTypeChecked;
	
	//平台复选框
	private boolean platformChecked;
	
	//渠道分组复选框
	private boolean channelgroupChecked;
	
	//渠道分级复选框
	private boolean channellevelChecked;
	
	//渠道复选框
	private boolean channelChecked;	
	
	//来源复选框
	private boolean utmsourceChecked;
	
	//媒件复选框
	private boolean utmmediumChecked;
	
	//系列复选框
	private boolean utmcampaignChecked;
	
	//组复选框
	private boolean utmcontentChecked;
	
	//关键字复选框
	private boolean utmtermChecked;
	

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getDetailed() {
		return detailed;
	}

	public void setDetailed(String detailed) {
		this.detailed = detailed;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public boolean isPlatformChecked() {
		return platformChecked;
	}

	public void setPlatformChecked(boolean platformChecked) {
		this.platformChecked = platformChecked;
	}

	public boolean isChannelgroupChecked() {
		return channelgroupChecked;
	}

	public void setChannelgroupChecked(boolean channelgroupChecked) {
		this.channelgroupChecked = channelgroupChecked;
	}

	public boolean isChannellevelChecked() {
		return channellevelChecked;
	}

	public void setChannellevelChecked(boolean channellevelChecked) {
		this.channellevelChecked = channellevelChecked;
	}

	public boolean isChannelChecked() {
		return channelChecked;
	}

	public void setChannelChecked(boolean channelChecked) {
		this.channelChecked = channelChecked;
	}

	public boolean isUtmsourceChecked() {
		return utmsourceChecked;
	}

	public void setUtmsourceChecked(boolean utmsourceChecked) {
		this.utmsourceChecked = utmsourceChecked;
	}

	public boolean isUtmmediumChecked() {
		return utmmediumChecked;
	}

	public void setUtmmediumChecked(boolean utmmediumChecked) {
		this.utmmediumChecked = utmmediumChecked;
	}

	public boolean isUtmcampaignChecked() {
		return utmcampaignChecked;
	}

	public void setUtmcampaignChecked(boolean utmcampaignChecked) {
		this.utmcampaignChecked = utmcampaignChecked;
	}

	public boolean isUtmcontentChecked() {
		return utmcontentChecked;
	}

	public void setUtmcontentChecked(boolean utmcontentChecked) {
		this.utmcontentChecked = utmcontentChecked;
	}

	public boolean isUtmtermChecked() {
		return utmtermChecked;
	}

	public void setUtmtermChecked(boolean utmtermChecked) {
		this.utmtermChecked = utmtermChecked;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getChannelgroup() {
		return channelgroup;
	}

	public void setChannelgroup(String channelgroup) {
		this.channelgroup = channelgroup;
	}

	public String getChannellevel() {
		return channellevel;
	}

	public void setChannellevel(String channellevel) {
		this.channellevel = channellevel;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUtmsource() {
		return utmsource;
	}

	public void setUtmsource(String utmsource) {
		this.utmsource = utmsource;
	}

	public String getUtmmedium() {
		return utmmedium;
	}

	public void setUtmmedium(String utmmedium) {
		this.utmmedium = utmmedium;
	}

	public String getUtmcampaign() {
		return utmcampaign;
	}

	public void setUtmcampaign(String utmcampaign) {
		this.utmcampaign = utmcampaign;
	}

	public String getUtmcontent() {
		return utmcontent;
	}

	public void setUtmcontent(String utmcontent) {
		this.utmcontent = utmcontent;
	}

	public String getUtmterm() {
		return utmterm;
	}

	public void setUtmterm(String utmterm) {
		this.utmterm = utmterm;
	}

	public boolean isDeviceTypeChecked() {
		return deviceTypeChecked;
	}

	public void setDeviceTypeChecked(boolean deviceTypeChecked) {
		this.deviceTypeChecked = deviceTypeChecked;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	
}
