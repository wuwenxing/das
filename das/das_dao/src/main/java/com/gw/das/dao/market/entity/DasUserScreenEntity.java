package com.gw.das.dao.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;

@Entity
@Table(name = "t_das_user_screen")
public class DasUserScreenEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "screen_id", nullable = false)
	private Long screenId;

	@Column(name = "screen_name", length = 100)
	private String screenName;// 筛选名称

	@Column(name = "plan_time_type", length = 100)
	private String planTimeType;// 筛选时间-计划时间类型

	@Column(name = "start_time", length = 100)
	private String startTime;// 筛选时间-开始

	@Column(name = "end_time", length = 100)
	private String endTime;// 筛选时间-结束

	@Column(name = "gender", length = 100)
	private String gender;// 性别 0：男 1:女

	@Column(name = "behavior_type", length = 100)
	private String behaviorType;// 行为 访问(1)、咨询(2)、模拟开户(3)、真实开户(4)、首次入金(5)

	@Column(name = "silence", length = 100)
	private String silence;// 沉默无行为

	@Column(name = "start_silence_days", length = 100)
	private String startSilenceDays;// 沉默无行为,天数-大于

	@Column(name = "silence_days", length = 100)
	private String silenceDays;// 沉默无行为,天数-小于

	@Column(name = "channel_ids", length = 100)
	private String channelIds;// 渠道,（备注：20170216，改为存渠道名称，根据名称查询对应的渠道id）

	@Column(name = "tag_ids", length = 100)
	private String tagIds;// 标签

	@Column(name = "is_depesit", length = 20)
	private String isDepesit;// 排重-已入金

	@Column(name = "is_real", length = 20)
	private String isReal;// 排重-已开真实

	@Column(name = "is_demo", length = 20)
	private String isDemo;// 排重-已开模拟

	@Column(name = "is_backlist", length = 20)
	private String isBacklist;// 排重-黑名单

	@Column(name = "is_free_angry", length = 20)
	private String isFreeAngry;// 排重-免恼

	@Column(name = "result_sms_count")
	private Long resultSmsCount;// 统计后-手机号数量

	@Column(name = "result_email_count")
	private Long resultEmailCount;// 统计后-邮箱数量

	@Column(name = "areas", length = 100)
	private String areas;// 地域-多个逗号隔开

	@Column(name = "client", length = 100)
	private String client;// 开户客户端类型

	@Column(name = "browsers", length = 100)
	private String browsers;// 开户浏览器信息-多个逗号隔开

	@Column(name = "visit_client", length = 100)
	private String visitClient;// 访问客户端类型

	@Column(name = "visit_count_gt")
	private Long visitCountGt;// 访问次数-大于-gt

	@Column(name = "visit_count_lt")
	private Long visitCountLt;// 访问次数-小于-lt

	@Column(name = "download_client", length = 100)
	private String downloadClient;// 下载客户端类型

	@Column(name = "download_count_gt")
	private Long downloadCountGt;// 下载次数-大于-gt

	@Column(name = "download_count_lt")
	private Long downloadCountLt;// 下载次数-小于-lt

	@Column(name = "advisory", length = 100)
	private String advisory;// 咨询类型

	@Column(name = "advisory_count_gt")
	private Long advisoryCountGt;// 咨询次数-大于-gt

	@Column(name = "advisory_count_lt")
	private Long advisoryCountLt;// 咨询次数-小于-lt

	@Column(name = "sms_send_status")
	private String smsSendStatus;// 短信发送行为-发送状态

	@Column(name = "sms_send_count_gt")
	private Long smsSendCountGt;// 短信发送行为-大于-gt

	@Column(name = "sms_send_count_lt")
	private Long smsSendCountLt;// 短信发送行为-小于-lt

	@Column(name = "email_send_count_gt")
	private Long emailSendCountGt;// 邮件发送行为-大于-gt

	@Column(name = "email_send_count_lt")
	private Long emailSendCountLt;// 邮件发送行为-小于-lt

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getPlanTimeType() {
		return planTimeType;
	}

	public void setPlanTimeType(String planTimeType) {
		this.planTimeType = planTimeType;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public String getSilence() {
		return silence;
	}

	public void setSilence(String silence) {
		this.silence = silence;
	}

	public String getStartSilenceDays() {
		return startSilenceDays;
	}

	public void setStartSilenceDays(String startSilenceDays) {
		this.startSilenceDays = startSilenceDays;
	}

	public String getSilenceDays() {
		return silenceDays;
	}

	public void setSilenceDays(String silenceDays) {
		this.silenceDays = silenceDays;
	}

	public String getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getIsDepesit() {
		return isDepesit;
	}

	public void setIsDepesit(String isDepesit) {
		this.isDepesit = isDepesit;
	}

	public String getIsReal() {
		return isReal;
	}

	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}

	public String getIsDemo() {
		return isDemo;
	}

	public void setIsDemo(String isDemo) {
		this.isDemo = isDemo;
	}

	public String getIsBacklist() {
		return isBacklist;
	}

	public void setIsBacklist(String isBacklist) {
		this.isBacklist = isBacklist;
	}

	public String getIsFreeAngry() {
		return isFreeAngry;
	}

	public void setIsFreeAngry(String isFreeAngry) {
		this.isFreeAngry = isFreeAngry;
	}

	public Long getResultSmsCount() {
		return resultSmsCount;
	}

	public void setResultSmsCount(Long resultSmsCount) {
		this.resultSmsCount = resultSmsCount;
	}

	public Long getResultEmailCount() {
		return resultEmailCount;
	}

	public void setResultEmailCount(Long resultEmailCount) {
		this.resultEmailCount = resultEmailCount;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getBrowsers() {
		return browsers;
	}

	public void setBrowsers(String browsers) {
		this.browsers = browsers;
	}

	public String getVisitClient() {
		return visitClient;
	}

	public void setVisitClient(String visitClient) {
		this.visitClient = visitClient;
	}

	public Long getVisitCountGt() {
		return visitCountGt;
	}

	public void setVisitCountGt(Long visitCountGt) {
		this.visitCountGt = visitCountGt;
	}

	public Long getVisitCountLt() {
		return visitCountLt;
	}

	public void setVisitCountLt(Long visitCountLt) {
		this.visitCountLt = visitCountLt;
	}

	public String getDownloadClient() {
		return downloadClient;
	}

	public void setDownloadClient(String downloadClient) {
		this.downloadClient = downloadClient;
	}

	public Long getDownloadCountGt() {
		return downloadCountGt;
	}

	public void setDownloadCountGt(Long downloadCountGt) {
		this.downloadCountGt = downloadCountGt;
	}

	public Long getDownloadCountLt() {
		return downloadCountLt;
	}

	public void setDownloadCountLt(Long downloadCountLt) {
		this.downloadCountLt = downloadCountLt;
	}

	public String getAdvisory() {
		return advisory;
	}

	public void setAdvisory(String advisory) {
		this.advisory = advisory;
	}

	public Long getAdvisoryCountGt() {
		return advisoryCountGt;
	}

	public void setAdvisoryCountGt(Long advisoryCountGt) {
		this.advisoryCountGt = advisoryCountGt;
	}

	public Long getAdvisoryCountLt() {
		return advisoryCountLt;
	}

	public void setAdvisoryCountLt(Long advisoryCountLt) {
		this.advisoryCountLt = advisoryCountLt;
	}

	public String getSmsSendStatus() {
		return smsSendStatus;
	}

	public void setSmsSendStatus(String smsSendStatus) {
		this.smsSendStatus = smsSendStatus;
	}

	public Long getSmsSendCountGt() {
		return smsSendCountGt;
	}

	public void setSmsSendCountGt(Long smsSendCountGt) {
		this.smsSendCountGt = smsSendCountGt;
	}

	public Long getSmsSendCountLt() {
		return smsSendCountLt;
	}

	public void setSmsSendCountLt(Long smsSendCountLt) {
		this.smsSendCountLt = smsSendCountLt;
	}

	public Long getEmailSendCountGt() {
		return emailSendCountGt;
	}

	public void setEmailSendCountGt(Long emailSendCountGt) {
		this.emailSendCountGt = emailSendCountGt;
	}

	public Long getEmailSendCountLt() {
		return emailSendCountLt;
	}

	public void setEmailSendCountLt(Long emailSendCountLt) {
		this.emailSendCountLt = emailSendCountLt;
	}
	
}
