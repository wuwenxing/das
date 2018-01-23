package com.gw.das.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 基础数据表
 * 
 * @author kirin.guan
 */
@Document(collection = "utm_data_basic")
public class UserTrackBasic {

	@Id
	private String id;

	// 用户ID
	private String userId;

	// 访问时间
	@Indexed
	private Date visitTime;

	// 行为明细(behavior_detail_type)
	private String behaviorDetail;

	// 行为类型：行为包括：访问(1)、咨询(2)、模拟开户(3)、真实开户(4)、首次入金(5) 直播间(6) 等等
	@Indexed
	private String behaviorType;

	// 操作设备Ip
	private String ip;

	// 广告来源
	private String utmcsr;

	// 用户输入的关键词
	private String utmctr;

	// 标记关键词
	private String markWords;

	// 广告系列
	private String utmccn;

	// 广告组
	private String utmcct;

	// 广告媒介
	private String utmcmd;

	// 访问URL
	private String url;

	// 标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	@Indexed
	private Integer platformType;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	@Indexed
	private Integer businessPlatform;

	// 操作描述 记录操作时间帐号等等
	private String operationDescription;

	// 开户电话
	private String operationTel;

	// 开户邮箱
	private String operationEmail;

	// 咨询类型 qq或者live800 1表示qq 2表示live800
	private String advisoryType;

	// 性别 0：男 1:女
	private String gender;

	// 出生年月日
	private String birthDate;

	// 客户姓名
	private String guestName;

	// 浏览器信息
	@Indexed
	private String browser;

	// 访问的上一个URL地址
	private String prevUrl;

	// 事件类型标识 1直播间按钮
	private String eventType;

	// 开户类型
	private String accountType;

	// 开户平台
	private String accountPlatform;

	// 外部请求参数
	private String requestParams;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
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

	public String getUtmcsr() {
		return utmcsr;
	}

	public void setUtmcsr(String utmcsr) {
		this.utmcsr = utmcsr;
	}

	public String getUtmctr() {
		return utmctr;
	}

	public void setUtmctr(String utmctr) {
		this.utmctr = utmctr;
	}

	public String getMarkWords() {
		return markWords;
	}

	public void setMarkWords(String markWords) {
		this.markWords = markWords;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public Integer getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(Integer businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getOperationTel() {
		return operationTel;
	}

	public void setOperationTel(String operationTel) {
		this.operationTel = operationTel;
	}

	public String getOperationEmail() {
		return operationEmail;
	}

	public void setOperationEmail(String operationEmail) {
		this.operationEmail = operationEmail;
	}

	public String getAdvisoryType() {
		return advisoryType;
	}

	public void setAdvisoryType(String advisoryType) {
		this.advisoryType = advisoryType;
	}

	public String getBehaviorDetail() {
		return behaviorDetail;
	}

	public void setBehaviorDetail(String behaviorDetail) {
		this.behaviorDetail = behaviorDetail;
	}

	public String getOperationDescription() {
		return operationDescription;
	}

	public void setOperationDescription(String operationDescription) {
		this.operationDescription = operationDescription;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getPrevUrl() {
		return prevUrl;
	}

	public void setPrevUrl(String prevUrl) {
		this.prevUrl = prevUrl;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountPlatform() {
		return accountPlatform;
	}

	public void setAccountPlatform(String accountPlatform) {
		this.accountPlatform = accountPlatform;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

}
