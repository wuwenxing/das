package com.gw.das.business.dao.website.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * Landingpage
 */
public class DasWebLandingpageDetail extends BaseModel {

	@Column(name = "rowkey")
	private String rowKey;
	// 日期(yyyy-MM-dd)分区字段
	@Column(name = "datatime")
	private String dataTime;
	// 广告来源
	@Column(name = "utmcsr")
	private String utmcsr;
	// 广告媒介
	@Column(name = "utmcmd")
	private String utmcmd;
	// 系列
	@Column(name = "utmccn")
	private String utmccn;
	// 组
	@Column(name = "utmcct")
	private String utmcct;
	// 关键字
	@Column(name = "utmctr")
	private String utmctr;
	// 访问总数
	@Column(name = "visitcount")
	private Integer visitCount;
	// 模拟开户总数
	@Column(name = "democount")
	private Integer demoCount;
	// 真实开户总数
	@Column(name = "realcount")
	private Integer realCount;
	// 模拟按钮次数
	@Column(name = "demobtncount")
	private Integer demobtnCount;
	// 真实按钮次数
	@Column(name = "realbtncount")
	private Integer realbtnCount;
	// 激活数
	@Column(name = "depositcount")
	private Integer depositCount;
	// lp页面咨询数
	@Column(name = "lp_advisory_cnt")
	private Integer lpAdvisoryCnt;
	// 会话内的咨询次数
	@Column(name = "advisorycount")
	private Integer advisoryCount;
	// 咨询次数-qq
	@Column(name = "advisorycountqq")
	private Integer advisoryCountqq;
	// 咨询次数-live800
	@Column(name = "advisorycountlive800")
	private Integer advisoryCountlive800;
	  
	// 页面地址
	@Column(name = "landingpage")
	private String landingPage;
	// 页面地址-组
	@Column(name = "landingpage_grp")
	private String landingPage_grp;
	// 设备类型
	@Column(name = "devicetype")
	private String deviceType;
	// 渠道
	@Column(name = "channel")
	private String channel;
	// 访问平均时长
	@Column(name = "avg_timelen")
	private String avgTimelen;
	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessplatform")
	private String businessPlatform;

	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
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
	public Integer getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
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
	public Integer getDemobtnCount() {
		return demobtnCount;
	}
	public void setDemobtnCount(Integer demobtnCount) {
		this.demobtnCount = demobtnCount;
	}
	public Integer getRealbtnCount() {
		return realbtnCount;
	}
	public void setRealbtnCount(Integer realbtnCount) {
		this.realbtnCount = realbtnCount;
	}
	public Integer getDepositCount() {
		return depositCount;
	}
	public void setDepositCount(Integer depositCount) {
		this.depositCount = depositCount;
	}
	public Integer getAdvisoryCount() {
		return advisoryCount;
	}
	public void setAdvisoryCount(Integer advisoryCount) {
		this.advisoryCount = advisoryCount;
	}
	public Integer getAdvisoryCountqq() {
		return advisoryCountqq;
	}
	public void setAdvisoryCountqq(Integer advisoryCountqq) {
		this.advisoryCountqq = advisoryCountqq;
	}
	public Integer getAdvisoryCountlive800() {
		return advisoryCountlive800;
	}
	public void setAdvisoryCountlive800(Integer advisoryCountlive800) {
		this.advisoryCountlive800 = advisoryCountlive800;
	}
	public String getLandingPage() {
		return landingPage;
	}
	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getBusinessPlatform() {
		return businessPlatform;
	}
	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
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
	public String getLandingPage_grp() {
		return landingPage_grp;
	}
	public void setLandingPage_grp(String landingPage_grp) {
		this.landingPage_grp = landingPage_grp;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getAvgTimelen() {
		return avgTimelen;
	}
	public void setAvgTimelen(String avgTimelen) {
		this.avgTimelen = avgTimelen;
	}
	public Integer getLpAdvisoryCnt() {
		return lpAdvisoryCnt;
	}
	public void setLpAdvisoryCnt(Integer lpAdvisoryCnt) {
		this.lpAdvisoryCnt = lpAdvisoryCnt;
	}

}
