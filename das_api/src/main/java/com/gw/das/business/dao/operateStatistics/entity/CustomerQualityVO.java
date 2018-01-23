package com.gw.das.business.dao.operateStatistics.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 获客质量（web、client）记录VO类
 * 
 * @author darren
 * @since 2017-10-20
 */
public class CustomerQualityVO extends BaseModel {

	/* rowkey */
	@Column(name = "rowkey")
	private String rowkey;
	
	/* 日期 */
	@Column(name = "datetime")
	private String dateTime;
	
	/* 日期 */
	@Column(name = "week_of_year")
	private String weekOfYear;
	
	/* 月 */
	@Column(name = "month")
	private String month;
	
	/* 业务平台 */
	@Column(name = "business_platform")
	private String businessPlatform;
	
	/*  web,client */
	@Column(name = "src_type")
	private String srcType;
	
	/* 设备类型 */
	@Column(name = "device_type")
	private String deviceType;

	/* 渠道分组 */
	@Column(name = "channel_grp")
	private String channelGrp;
	
	/* 渠道分级*/
	@Column(name = "channel_level")
	private String channelLevel;
	
	/*渠道*/
	@Column(name = "channel")
	private String channel;
	
	/*来源 */
	@Column(name = "utmcsr")
	private String utmcsr;
	
	/* 媒介 */
	@Column(name = "utmcmd")
	private String utmcmd;
	
	/* 系列*/
	@Column(name = "utmccn")
	private String utmccn;
	
	/* 组*/
	@Column(name = "utmcct")
	private String utmcct;
	
	/* 关键词 */
	@Column(name = "utmctr")
	private String utmctr;
	
	/*LandingPage*/
	@Column(name = "landing_page")
	private String landingPage;
	
	/* 版本号 */
	@Column(name = "platform_version")
	private String platformVersion;
	
	/* 马甲包 */
	@Column(name = "platform_name")
	private String platformName;
	
	/* 模拟开户数*/
	@Column(name = "demo_open_cnt")
	private String demoOpenCnt;
	
	/*真实开户数*/
	@Column(name = "account_open_cnt")
	private String accountOpenCnt;
	
	/* 激活账户数 */
	@Column(name = "account_active_cnt")
	private String accountActiveCnt;
	
	/* 模拟开户登录数 */
	@Column(name = "demo_open_login_cnt")
	private String demoOpenLoginCnt;
	
	/* 模拟开户登录率 */
	@Column(name = "demo_open_login_ratio")
	private String demoOpenLoginRatio;
	
	/* 模拟开户转真实数 */
	@Column(name = "demo_to_real_cnt")
	private String demoToRealCnt;
	
	/* 模拟开户转真实率*/
	@Column(name = "demo_to_real_ratio")
	private String demoToRealRatio;
	
	/* 模拟开户转激活数 */
	@Column(name = "demo_to_active_cnt")
	private String demoToActiveCnt;
	
	/* 模拟开户转激活率 */
	@Column(name = "demo_to_active_ratio")
	private String demoToActiveRatio;
	
	/* 真实开户登录数 */
	@Column(name = "account_open_login_cnt")
	private String accountOpenLoginCnt;
	
	/* 真实开户登录率 */
	@Column(name = "account_open_login_ratio")
	private String accountOpenLoginRatio;
	
	/* 真实开户激活数 */
	@Column(name = "account_open_active_cnt")
	private String accountOpenActiveCnt;
	
	/* 真实开户激活率 */
	@Column(name = "account_open_active_ratio")
	private String accountOpenActiveRatio;
	
	/* 真实开户交易数 */
	@Column(name = "account_open_order_cnt")
	private String accountOpenOrderCnt;
	
	/* 真实开户交易率 */
	@Column(name = "account_open_order_ratio")
	private String accountOpenOrderRatio;

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

	public String getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(String weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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

	public String getDemoOpenCnt() {
		return demoOpenCnt;
	}

	public void setDemoOpenCnt(String demoOpenCnt) {
		this.demoOpenCnt = demoOpenCnt;
	}

	public String getAccountOpenCnt() {
		return accountOpenCnt;
	}

	public void setAccountOpenCnt(String accountOpenCnt) {
		this.accountOpenCnt = accountOpenCnt;
	}

	public String getAccountActiveCnt() {
		return accountActiveCnt;
	}

	public void setAccountActiveCnt(String accountActiveCnt) {
		this.accountActiveCnt = accountActiveCnt;
	}

	public String getDemoOpenLoginCnt() {
		return demoOpenLoginCnt;
	}

	public void setDemoOpenLoginCnt(String demoOpenLoginCnt) {
		this.demoOpenLoginCnt = demoOpenLoginCnt;
	}

	public String getDemoOpenLoginRatio() {
		return demoOpenLoginRatio;
	}

	public void setDemoOpenLoginRatio(String demoOpenLoginRatio) {
		this.demoOpenLoginRatio = demoOpenLoginRatio;
	}

	public String getDemoToRealCnt() {
		return demoToRealCnt;
	}

	public void setDemoToRealCnt(String demoToRealCnt) {
		this.demoToRealCnt = demoToRealCnt;
	}

	public String getDemoToRealRatio() {
		return demoToRealRatio;
	}

	public void setDemoToRealRatio(String demoToRealRatio) {
		this.demoToRealRatio = demoToRealRatio;
	}

	public String getDemoToActiveCnt() {
		return demoToActiveCnt;
	}

	public void setDemoToActiveCnt(String demoToActiveCnt) {
		this.demoToActiveCnt = demoToActiveCnt;
	}

	public String getDemoToActiveRatio() {
		return demoToActiveRatio;
	}

	public void setDemoToActiveRatio(String demoToActiveRatio) {
		this.demoToActiveRatio = demoToActiveRatio;
	}

	public String getAccountOpenLoginCnt() {
		return accountOpenLoginCnt;
	}

	public void setAccountOpenLoginCnt(String accountOpenLoginCnt) {
		this.accountOpenLoginCnt = accountOpenLoginCnt;
	}

	public String getAccountOpenLoginRatio() {
		return accountOpenLoginRatio;
	}

	public void setAccountOpenLoginRatio(String accountOpenLoginRatio) {
		this.accountOpenLoginRatio = accountOpenLoginRatio;
	}

	public String getAccountOpenActiveCnt() {
		return accountOpenActiveCnt;
	}

	public void setAccountOpenActiveCnt(String accountOpenActiveCnt) {
		this.accountOpenActiveCnt = accountOpenActiveCnt;
	}

	public String getAccountOpenActiveRatio() {
		return accountOpenActiveRatio;
	}

	public void setAccountOpenActiveRatio(String accountOpenActiveRatio) {
		this.accountOpenActiveRatio = accountOpenActiveRatio;
	}

	public String getAccountOpenOrderCnt() {
		return accountOpenOrderCnt;
	}

	public void setAccountOpenOrderCnt(String accountOpenOrderCnt) {
		this.accountOpenOrderCnt = accountOpenOrderCnt;
	}

	public String getAccountOpenOrderRatio() {
		return accountOpenOrderRatio;
	}

	public void setAccountOpenOrderRatio(String accountOpenOrderRatio) {
		this.accountOpenOrderRatio = accountOpenOrderRatio;
	}
}
