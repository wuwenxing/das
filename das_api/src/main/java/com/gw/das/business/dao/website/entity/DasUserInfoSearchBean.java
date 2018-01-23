package com.gw.das.business.dao.website.entity;

import com.gw.das.business.dao.base.BaseSearchModel;

/**
 * 用户刷选接口查询bean
 * 
 * @author wayne
 */
public class DasUserInfoSearchBean extends BaseSearchModel{

	// 用户id
	private String userId;

	// 性别 0：男 1:女
	private String gender;
	
	// 账户创建时间-开始,格式如：2016-10-27 18:53:03
	private String startTime;
	
	// 账户创建时间-结束,格式如：2016-10-27 18:53:03
	private String endTime;

	// 客户姓名
	private String guestName;
	
	// 来源
	private String utmcsr;
	
	// 媒介
	private String utmcmd;
	
	// 渠道[广告来源，广告媒介]-如:[{"utmcsr":"xxx", "utmcmd":"xxx"}, {"utmcsr":"xxx", "utmcmd":"xxx"}]
	private String channel;

	// 电话
	private String tel;

	// 邮箱
	private String email;
	
	// 账户
	private String behaviorDetail;
	
	// 行为 访问(1)、咨询(2)、模拟开户(3)、真实开户(4)、首次入金(5)
	private String behaviorType;
	
	// 地域，多个地域逗号隔开
	private String area;
	
	// 标识开户来源,0和空代表是pc开户,1移动端开户
	private String platformType;

	// 开户浏览器信息,多个条件逗号隔开,如:IE6,IE7,IE8
	private String browser;

	// 归属地
	private String ipHomeJson;
  	
  	// 排重条件-已开模拟
  	private String isDemo;
  	
  	// 排重条件-已开真实
  	private String isReal;
	
  	// 排重条件-已入金
  	private String isDepesit;
	
  	// 排重条件-手机号,多个手机号逗号隔开
  	private String disPhones;

	// 访问链接，多个链接逗号隔开
	private String url;

	// 访问客户端类型
    private String visitClient;
	// 访问次数-大于-gt
  	private String visitCountGt;
	// 访问次数-小于-lt
  	private String visitCountLt;

	// 下载客户端类型
    private String downloadClient;
	// 下载次数-大于-gt
  	private String downloadCountGt;
	// 下载次数-小于-lt
  	private String downloadCountLt;

	//咨询类型
    private String advisory;
	// 咨询次数-大于-gt
  	private String advisoryCountGt;
	// 咨询次数-小于-lt
  	private String advisoryCountLt;

	// 短信发送行为-发送状态
    private String smsSendStatus;
	// 短信发送行为-大于-gt
    private String smsSendCountGt;
	// 短信发送行为-小于-lt
  	private String smsSendCountLt;

	// 邮件发送行为-大于-gt
    private String emailSendCountGt;
	// 邮件发送行为-小于-lt
  	private String emailSendCountLt;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getIpHomeJson() {
		return ipHomeJson;
	}

	public void setIpHomeJson(String ipHomeJson) {
		this.ipHomeJson = ipHomeJson;
	}

	public String getIsDemo() {
		return isDemo;
	}

	public void setIsDemo(String isDemo) {
		this.isDemo = isDemo;
	}

	public String getIsReal() {
		return isReal;
	}

	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}

	public String getIsDepesit() {
		return isDepesit;
	}

	public void setIsDepesit(String isDepesit) {
		this.isDepesit = isDepesit;
	}

	public String getDisPhones() {
		return disPhones;
	}

	public void setDisPhones(String disPhones) {
		this.disPhones = disPhones;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVisitClient() {
		return visitClient;
	}

	public void setVisitClient(String visitClient) {
		this.visitClient = visitClient;
	}

	public String getVisitCountGt() {
		return visitCountGt;
	}

	public void setVisitCountGt(String visitCountGt) {
		this.visitCountGt = visitCountGt;
	}

	public String getVisitCountLt() {
		return visitCountLt;
	}

	public void setVisitCountLt(String visitCountLt) {
		this.visitCountLt = visitCountLt;
	}

	public String getDownloadClient() {
		return downloadClient;
	}

	public void setDownloadClient(String downloadClient) {
		this.downloadClient = downloadClient;
	}

	public String getDownloadCountGt() {
		return downloadCountGt;
	}

	public void setDownloadCountGt(String downloadCountGt) {
		this.downloadCountGt = downloadCountGt;
	}

	public String getDownloadCountLt() {
		return downloadCountLt;
	}

	public void setDownloadCountLt(String downloadCountLt) {
		this.downloadCountLt = downloadCountLt;
	}

	public String getAdvisory() {
		return advisory;
	}

	public void setAdvisory(String advisory) {
		this.advisory = advisory;
	}

	public String getAdvisoryCountGt() {
		return advisoryCountGt;
	}

	public void setAdvisoryCountGt(String advisoryCountGt) {
		this.advisoryCountGt = advisoryCountGt;
	}

	public String getAdvisoryCountLt() {
		return advisoryCountLt;
	}

	public void setAdvisoryCountLt(String advisoryCountLt) {
		this.advisoryCountLt = advisoryCountLt;
	}

	public String getSmsSendStatus() {
		return smsSendStatus;
	}

	public void setSmsSendStatus(String smsSendStatus) {
		this.smsSendStatus = smsSendStatus;
	}

	public String getSmsSendCountGt() {
		return smsSendCountGt;
	}

	public void setSmsSendCountGt(String smsSendCountGt) {
		this.smsSendCountGt = smsSendCountGt;
	}

	public String getSmsSendCountLt() {
		return smsSendCountLt;
	}

	public void setSmsSendCountLt(String smsSendCountLt) {
		this.smsSendCountLt = smsSendCountLt;
	}

	public String getEmailSendCountGt() {
		return emailSendCountGt;
	}

	public void setEmailSendCountGt(String emailSendCountGt) {
		this.emailSendCountGt = emailSendCountGt;
	}

	public String getEmailSendCountLt() {
		return emailSendCountLt;
	}

	public void setEmailSendCountLt(String emailSendCountLt) {
		this.emailSendCountLt = emailSendCountLt;
	}
	
	
}
