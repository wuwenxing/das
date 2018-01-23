package com.gw.das.dao.website.bean;

/**
 * das_behavior_event_channel_effect_d
 */
public class DasBehaviorEventChannelEffect {

	private String rowKey;
	// 日期(yyyy-MM-dd)分区字段
	private String dateTime;
	// 周数
	private String weeks;
	// 设备类型
	private String deviceType;
	// 渠道
	private String channel;
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
	// 马甲包
	private String platformName;
	// 版本号
	private String platformVersion;
	// 渠道分组
	private String channelGrp;
	// 渠道级别
	private String channelLevel;
	// 页面地址
	private String landingPage;
	// 新增设备数
	private Integer newDeviceCount = 0;
	// 活跃设备数
	private Integer activeDeviceCount = 0;
	// 登陆设备数
	private Integer deviceLoginCount = 0;
	// 登陆账号数
	private Integer accountLoginCount = 0;
	// 总停留时间
	private Integer timeLen = 0;
	// 会话数
	private Integer visitCount = 0;
	// 会话平均使用时间
	private Double avgVisitTimeLen = 0D;
	// 进入咨询会话数
	private Integer eventAdvisoryCount = 0;
	// 进入模拟开户会话数
	private Integer eventDemoCount = 0;
	// 进入真实开户会话数
	private Integer eventRealCount = 0;
	// 进入入金流程会话数
	private Integer eventActiveCount = 0;
	// 进入咨询设备数
	private Integer eventDeviceAdvisoryCount = 0;
	// 进入模拟开户设备数
	private Integer eventDeviceDemoCount = 0;
	// 进入真实开户设备数
	private Integer eventDeviceRealCount = 0;
	// 进入入金流程设备数
	private Integer eventCeviceActiveCount = 0;
	// 模拟开户数
	private Integer demoCount = 0;
	// 真实开户数
	private Integer realCount = 0;
	// 入金账号数
	private Integer activeCount = 0;
	// 模拟开户率
	private Double demoRate = 0D;
	// 真实开户率
	private Double realRate = 0D;
	// 入金率
	private Double activeRate = 0D;
	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getWeeks() {
		return weeks;
	}
	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
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
	public String getLandingPage() {
		return landingPage;
	}
	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}
	public Integer getNewDeviceCount() {
		return newDeviceCount;
	}
	public void setNewDeviceCount(Integer newDeviceCount) {
		this.newDeviceCount = newDeviceCount;
	}
	public Integer getActiveDeviceCount() {
		return activeDeviceCount;
	}
	public void setActiveDeviceCount(Integer activeDeviceCount) {
		this.activeDeviceCount = activeDeviceCount;
	}
	public Integer getDeviceLoginCount() {
		return deviceLoginCount;
	}
	public void setDeviceLoginCount(Integer deviceLoginCount) {
		this.deviceLoginCount = deviceLoginCount;
	}
	public Integer getAccountLoginCount() {
		return accountLoginCount;
	}
	public void setAccountLoginCount(Integer accountLoginCount) {
		this.accountLoginCount = accountLoginCount;
	}
	public Integer getTimeLen() {
		return timeLen;
	}
	public void setTimeLen(Integer timeLen) {
		this.timeLen = timeLen;
	}
	public Integer getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}
	public Double getAvgVisitTimeLen() {
		return avgVisitTimeLen;
	}
	public void setAvgVisitTimeLen(Double avgVisitTimeLen) {
		this.avgVisitTimeLen = avgVisitTimeLen;
	}
	public Integer getEventAdvisoryCount() {
		return eventAdvisoryCount;
	}
	public void setEventAdvisoryCount(Integer eventAdvisoryCount) {
		this.eventAdvisoryCount = eventAdvisoryCount;
	}
	public Integer getEventDemoCount() {
		return eventDemoCount;
	}
	public void setEventDemoCount(Integer eventDemoCount) {
		this.eventDemoCount = eventDemoCount;
	}
	public Integer getEventRealCount() {
		return eventRealCount;
	}
	public void setEventRealCount(Integer eventRealCount) {
		this.eventRealCount = eventRealCount;
	}
	public Integer getEventActiveCount() {
		return eventActiveCount;
	}
	public void setEventActiveCount(Integer eventActiveCount) {
		this.eventActiveCount = eventActiveCount;
	}
	public Integer getEventDeviceAdvisoryCount() {
		return eventDeviceAdvisoryCount;
	}
	public void setEventDeviceAdvisoryCount(Integer eventDeviceAdvisoryCount) {
		this.eventDeviceAdvisoryCount = eventDeviceAdvisoryCount;
	}
	public Integer getEventDeviceDemoCount() {
		return eventDeviceDemoCount;
	}
	public void setEventDeviceDemoCount(Integer eventDeviceDemoCount) {
		this.eventDeviceDemoCount = eventDeviceDemoCount;
	}
	public Integer getEventDeviceRealCount() {
		return eventDeviceRealCount;
	}
	public void setEventDeviceRealCount(Integer eventDeviceRealCount) {
		this.eventDeviceRealCount = eventDeviceRealCount;
	}
	public Integer getEventCeviceActiveCount() {
		return eventCeviceActiveCount;
	}
	public void setEventCeviceActiveCount(Integer eventCeviceActiveCount) {
		this.eventCeviceActiveCount = eventCeviceActiveCount;
	}
	public Integer getDemoCount() {
		return demoCount;
	}
	public void setDemoCount(Integer demoCount) {
		this.demoCount = demoCount;
	}
	public Integer getRealCount() {
		return realCount;
	}
	public void setRealCount(Integer realCount) {
		this.realCount = realCount;
	}
	public Integer getActiveCount() {
		return activeCount;
	}
	public Double getDemoRate() {
		return demoRate;
	}
	public void setDemoRate(Double demoRate) {
		this.demoRate = demoRate;
	}
	public Double getRealRate() {
		return realRate;
	}
	public void setRealRate(Double realRate) {
		this.realRate = realRate;
	}
	public Double getActiveRate() {
		return activeRate;
	}
	public void setActiveRate(Double activeRate) {
		this.activeRate = activeRate;
	}
	public void setActiveCount(Integer activeCount) {
		this.activeCount = activeCount;
	}
	public String getBusinessPlatform() {
		return businessPlatform;
	}
	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}
	
}
