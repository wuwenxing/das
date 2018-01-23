package com.gw.das.business.dao.website.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 对应表mongo_das_flow_detail
 * 
 * @author wayne
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasFlowDetail extends BaseModel{
	
	@Column(name = "_id")
	private String id;
	
	@Column(name = "rowkey")
	private String rowKey;

	// 用户id
	@Column(name = "userid")
	private String userId;

	// 访问时间
	@Column(name = "starttime")
	private Date startTime;

	// 结束时间
	@Column(name = "endtime")
	private Date endTime;

	// 格式化的时间 startTime hdfs分区字段
	@Column(name = "formattime")
	private String formatTime;

	// 小时 存贮格式化startTime的小时
	@Column(name = "hours")
	private Integer hours;

	// 时长
	@Column(name = "timelength")
	private String timeLength;

	// 假如是模拟开户、真实开户、首次入金就往里面插入对应的帐号
	@Column(name = "behaviordetail")
	private String behaviorDetail;

	// 行为 访问(1)、咨询(2)、模拟开户(3)、真实开户(4)、首次入金(5)
	@Column(name = "behaviortype")
	private String behaviorType;

	// 访问ip
	@Column(name = "ip")
	private String ip;
	
	// 归属地
	@Column(name = "iphome")
	private String ipHomeJson;
	
	// 归属地
	private String ipHome;

	// 广告来源
	@Column(name = "utmcsr")
	private String utmcsr;

	// 用户输入关键词
	@Column(name = "utmctr")
	private String utmctr;

	// 标记关键词
	@Column(name = "markwords")
	private String markWords;

	// 广告系列
	@Column(name = "utmccn")
	private String utmccn;

	// 广告组
	@Column(name = "utmcct")
	private String utmcct;

	// 广告媒介
	@Column(name = "utmcmd")
	private String utmcmd;

	// 访问次数
	@Column(name = "visitcount")
	private int visitCount;

	// 是否已经发送邮件 0：发送 1：未发送
	@Column(name = "issend")
	private int isSend=0;

	// 标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	@Column(name = "platformtype")
	private int platformType;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessplatform")
	private int businessPlatform;

	// 是否归因过，0或null表示未归因，1表示已归因，默认未归因
	@Column(name = "isattributed")
	private int isAttributed=0;

	// 开户电话
	@Column(name = "operationtel")
	private String operationTel;

	// 开户邮箱
	@Column(name = "operationemail")
	private String operationEmail;

	// 咨询类型 qq或者live800 1表示qq 2表示live800
	@Column(name = "advisorytype")
	private String advisoryType;

	// 操作描述 记录操作时间帐号等等
	@Column(name = "operationdescription")
	private String operationDescription;

	// 性别 0：男 1:女
	@Column(name = "gender")
	private String gender;

	// 出生年月日
	@Column(name = "birthdate")
	private Date birthDate;

	// 客户姓名
	@Column(name = "guestname")
	private String guestName;

	// 浏览器信息
	@Column(name = "browser")
	private String browser;

	// 以下为查询条件相关字段-start
	private String url;
	// 以下为查询条件相关字段-end
	
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

	public int getPlatformType() {
		return platformType;
	}

	public void setPlatformType(int platformType) {
		this.platformType = platformType;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIpHomeJson() {
		return ipHomeJson;
	}

	public void setIpHomeJson(String ipHomeJson) {
		this.ipHomeJson = ipHomeJson;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIpHome() {
		return ipHome;
	}

	public void setIpHome(String ipHome) {
		this.ipHome = ipHome;
	}

}
