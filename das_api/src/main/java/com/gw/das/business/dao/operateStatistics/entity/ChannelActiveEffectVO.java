package com.gw.das.business.dao.operateStatistics.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 渠道新增、活跃、效果报表（web）记录VO类
 * 
 * @author darren
 * @since 2017-10-20
 */
public class ChannelActiveEffectVO extends BaseModel {

	/* rowkey */
	@Column(name = "rowkey")
	private String rowkey;
	
	/* 日期 */
	@Column(name = "datetime")
	private String dateTime;
	
	/* 业务平台*/
	@Column(name = "business_platform")
	private String businessPlatform;
	
	/* web,client*/
	@Column(name = "src_type")
	private String srcType;
	
	/* 设备类型 */
	@Column(name = "device_type")
	private String deviceType;
	
	/* 渠道分组 */
	@Column(name = "channel_grp")
	private String channelGrp;
	
	/* 渠道级别 */
	@Column(name = "channel_level")
	private String channelLevel;
	
	/* 渠道 */
	@Column(name = "channel")
	private String channel;
	
	/* 版本号 */
	@Column(name = "platform_version")
	private String platformVersion;
	
	/* 马甲包 */
	@Column(name = "platform_name")
	private String platformName;
	
	/* landing page */
	@Column(name = "landing_page")
	private String landingPage;
	
	/* 媒介 */
	@Column(name = "utmcmd")
	private String utmcmd;
	
	/*来源 */
	@Column(name = "utmcsr")
	private String utmcsr;
	
	/* 系列*/
	@Column(name = "utmccn")
	private String utmccn;
	
	/* 广告组*/
	@Column(name = "utmcct")
	private String utmcct;
	
	/* 关键字 */
	@Column(name = "utmctr")
	private String utmctr;	
	
	/* 新增设备数 */
	@Column(name = "new_device_count")
	private String newDeviceCount;
	
	/*活跃设备数*/
	@Column(name = "active_device_count")
	private String activeDeviceCount;
	
	/* 登陆设备数 */
	@Column(name = "device_login_count")
	private String deviceLoginCount;
	
	/* 登陆账号数 */
	@Column(name = "account_login_count")
	private String accountLoginCount;
	
	/* 总停留时间 */
	@Column(name = "time_len")
	private String timeLen;
	
	/* 会话数*/
	@Column(name = "visit_count")
	private String visitCount;
	
	/* 会话平均使用时间*/
	@Column(name = "avg_visit_time_len")
	private String avgVisitTimeLen;
	
	/* 进入咨询会话数 */
	@Column(name = "event_advisory_count")
	private String eventAdvisoryCount;
	
	/* 进入模拟开户会话数 */
	@Column(name = "event_demo_count")
	private String eventDemoCount;
	
	/* 进入真实开户会话数 */
	@Column(name = "event_real_count")
	private String eventRealCount;
	
	/* 进入入金流程会话数 */
	@Column(name = "event_active_count")
	private String eventActiveCount;
	
	/* 进入咨询设备数  */
	@Column(name = "event_device_advisory_count")
	private String eventDeviceAdvisoryCount;
	
	/* 进入模拟开户设备数 */
	@Column(name = "event_device_demo_count")
	private String eventDeviceDemoCount;
	
	/* 进入真实开户设备数 */
	@Column(name = "event_device_real_count")
	private String eventDeviceRealCount;
	
	/* 进入入金流程设备数*/
	@Column(name = "event_device_active_count")
	private String eventDeviceActiveCount;
	
	/* 模拟开户数 */
	@Column(name = "demo_count")
	private String demoCount;
	
	/* 真实开户数 */
	@Column(name = "real_count")
	private String realCount;
	
	/* 入金账号数 */
	@Column(name = "active_count")
	private String activeCount;
	
	/* 设备登录率 */
	@Column(name = "device_login_rate")
	private String deviceLoginRate;
	
	/* 模拟开户率 */
	@Column(name = "demo_count_rate")
	private String demoCountRate;
	
	/* 真实开户率 */
	@Column(name = "real_count_rate")
	private String realCountRate;
	
	/* 模拟会话率*/
	@Column(name = "demo_visit_rate")
	private String demoVisitRate;
	
	/* 真实会话率 */
	@Column(name = "real_visit_rate")
	private String realVisitRate;
	
	/* 入金率 */
	@Column(name = "active_rate")
	private String activeRate;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getSrcType() {
		return srcType;
	}

	public void setSrcType(String srcType) {
		this.srcType = srcType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

	public String getLandingPage() {
		return landingPage;
	}

	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
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

	public String getNewDeviceCount() {
		return newDeviceCount;
	}

	public void setNewDeviceCount(String newDeviceCount) {
		this.newDeviceCount = newDeviceCount;
	}

	public String getActiveDeviceCount() {
		return activeDeviceCount;
	}

	public void setActiveDeviceCount(String activeDeviceCount) {
		this.activeDeviceCount = activeDeviceCount;
	}

	public String getDeviceLoginCount() {
		return deviceLoginCount;
	}

	public void setDeviceLoginCount(String deviceLoginCount) {
		this.deviceLoginCount = deviceLoginCount;
	}

	public String getAccountLoginCount() {
		return accountLoginCount;
	}

	public void setAccountLoginCount(String accountLoginCount) {
		this.accountLoginCount = accountLoginCount;
	}

	public String getDeviceLoginRate() {
		return deviceLoginRate;
	}

	public void setDeviceLoginRate(String deviceLoginRate) {
		this.deviceLoginRate = deviceLoginRate;
	}

	public String getTimeLen() {
		return timeLen;
	}

	public void setTimeLen(String timeLen) {
		this.timeLen = timeLen;
	}

	public String getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(String visitCount) {
		this.visitCount = visitCount;
	}

	public String getAvgVisitTimeLen() {
		return avgVisitTimeLen;
	}

	public void setAvgVisitTimeLen(String avgVisitTimeLen) {
		this.avgVisitTimeLen = avgVisitTimeLen;
	}

	public String getEventAdvisoryCount() {
		return eventAdvisoryCount;
	}

	public void setEventAdvisoryCount(String eventAdvisoryCount) {
		this.eventAdvisoryCount = eventAdvisoryCount;
	}

	public String getEventDemoCount() {
		return eventDemoCount;
	}

	public void setEventDemoCount(String eventDemoCount) {
		this.eventDemoCount = eventDemoCount;
	}

	public String getEventRealCount() {
		return eventRealCount;
	}

	public void setEventRealCount(String eventRealCount) {
		this.eventRealCount = eventRealCount;
	}

	public String getEventActiveCount() {
		return eventActiveCount;
	}

	public void setEventActiveCount(String eventActiveCount) {
		this.eventActiveCount = eventActiveCount;
	}

	public String getEventDeviceAdvisoryCount() {
		return eventDeviceAdvisoryCount;
	}

	public void setEventDeviceAdvisoryCount(String eventDeviceAdvisoryCount) {
		this.eventDeviceAdvisoryCount = eventDeviceAdvisoryCount;
	}

	public String getEventDeviceDemoCount() {
		return eventDeviceDemoCount;
	}

	public void setEventDeviceDemoCount(String eventDeviceDemoCount) {
		this.eventDeviceDemoCount = eventDeviceDemoCount;
	}

	public String getEventDeviceRealCount() {
		return eventDeviceRealCount;
	}

	public void setEventDeviceRealCount(String eventDeviceRealCount) {
		this.eventDeviceRealCount = eventDeviceRealCount;
	}

	public String getEventDeviceActiveCount() {
		return eventDeviceActiveCount;
	}

	public void setEventDeviceActiveCount(String eventDeviceActiveCount) {
		this.eventDeviceActiveCount = eventDeviceActiveCount;
	}

	public String getDemoCount() {
		return demoCount;
	}

	public void setDemoCount(String demoCount) {
		this.demoCount = demoCount;
	}

	public String getRealCount() {
		return realCount;
	}

	public void setRealCount(String realCount) {
		this.realCount = realCount;
	}

	public String getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(String activeCount) {
		this.activeCount = activeCount;
	}

	public String getDemoCountRate() {
		return demoCountRate;
	}

	public void setDemoCountRate(String demoCountRate) {
		this.demoCountRate = demoCountRate;
	}

	public String getRealCountRate() {
		return realCountRate;
	}

	public void setRealCountRate(String realCountRate) {
		this.realCountRate = realCountRate;
	}

	public String getDemoVisitRate() {
		return demoVisitRate;
	}

	public void setDemoVisitRate(String demoVisitRate) {
		this.demoVisitRate = demoVisitRate;
	}

	public String getRealVisitRate() {
		return realVisitRate;
	}

	public void setRealVisitRate(String realVisitRate) {
		this.realVisitRate = realVisitRate;
	}

	public String getActiveRate() {
		return activeRate;
	}

	public void setActiveRate(String activeRate) {
		this.activeRate = activeRate;
	}
	
}
