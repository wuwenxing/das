package com.gw.das.dao.room.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 对应表das_flow_detail
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartFlowDetail{
	
	private String rowKey;

	// 用户id
	private String userId;

	// 访问时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;

	// 结束时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

	// 格式化的时间 startTime hdfs分区字段
	private String formatTime;

	// 小时 存贮格式化startTime的小时
	private Integer hours;

	// 时长
	private String timeLength;

	// 假如是模拟开户、真实开户、首次入金就往里面插入对应的帐号
	private String behaviorDetail;

	// 行为 访问(1)、咨询(2)、模拟开户(3)、真实开户(4)、首次入金(5)
	private String behaviorType;

	// 访问ip
	private String ip;
	
	// 归属地
	private String ipHomeJson;
	
	// 归属地
	private String ipHome;

	// 广告来源
	private String utmcsr;

	// 用户输入关键词
	private String utmctr;

	// 标记关键词
	private String markWords;

	// 广告系列
	private String utmccn;

	// 广告组
	private String utmcct;

	// 广告媒介
	private String utmcmd;

	// 访问次数
	private int visitCount;

	// 是否已经发送邮件 0：发送 1：未发送
	private int isSend=0;

	// 标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	private int devicetype;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private int businessPlatform;

	// 是否归因过，0或null表示未归因，1表示已归因，默认未归因
	private int isAttributed;

	// 开户电话
	private String operationTel;

	// 开户邮箱
	private String operationEmail;

	// 咨询类型 qq或者live800 1表示qq 2表示live800
	private String advisoryType;

	// 操作描述 记录操作时间帐号等等
	private String operationDescription;

	// 性别 0：男 1:女
	private String gender;

	// 出生年月日
	private Date birthDate;

	// 客户姓名
	private String guestName;

	// 浏览器信息
	private String browser;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public String getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpHomeJson() {
		return ipHomeJson;
	}

	public void setIpHomeJson(String ipHomeJson) {
		this.ipHomeJson = ipHomeJson;
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

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

    

	public int getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(int devicetype) {
		this.devicetype = devicetype;
	}

	public int getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(int businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public int getIsAttributed() {
		return isAttributed;
	}

	public void setIsAttributed(int isAttributed) {
		this.isAttributed = isAttributed;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
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

	public String getIpHome() {
		return ipHome;
	}

	public void setIpHome(String ipHome) {
		this.ipHome = ipHome;
	}
	
}
