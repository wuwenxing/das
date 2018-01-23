package com.gw.das.dao.market.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gw.das.dao.base.BaseEntity;

/**
 * 邮件实体
 * @author wayne
 *
 */
@Entity
@Table(name = "t_email")
public class EmailEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "email_id", nullable = false)
	private Long emailId;

	@Column(name = "title", length = 100)
	private String title;// 邮件标题
	
	@Column(name = "content", columnDefinition="MEDIUMTEXT")
	private String content;// 邮件内容

	@Column(name = "emails", columnDefinition="MEDIUMTEXT")
	private String emails;// 提交的邮箱集合

	@Column(name = "legal_emails", columnDefinition="MEDIUMTEXT")
	private String legalEmails; // 合法邮箱

	@Transient
	private boolean failEmailsFlag = false; // 给发送失败的邮箱再次发送标示
	@Transient
	private String failEmails; // 发送失败的邮箱号码

	@Column(name = "illegal_emails", columnDefinition="MEDIUMTEXT")
	private String illegalEmails; // 非法邮箱

	@Column(name = "input_num")
	private Long inputNum;// 总输入的数量

	@Column(name = "legal_num")
	private Long legalNum;// 合法邮箱数量

	@Column(name = "illegal_num")
	private Long illegalNum;// 非法邮箱数量

	@Column(name = "total_num")
	private Long totalNum;// 发送总数量

	@Column(name = "success_num")
	private Long successNum;// 发送成功数量

	@Column(name = "fail_num")
	private Long failNum;// 发送失败数量
	
	@Column(name = "source_type", length = 100)
	private String sourceType;// 添加邮箱的来源类型

	@Column(name = "screen_ids", length = 100)
	private String screenIds;// 用户筛选对象ID,多个逗号隔开

	@Column(name = "group_ids", length = 100)
	private String groupIds;// 客户分组条件对象ID,多个逗号隔开

	@Column(name = "send_type", length = 100)
	private String sendType; // 邮箱发送类型

	@Column(name = "time_switch", length = 1)
	private String timeSwitch;// 定时开关Y/N,(冗余字段，关键看定时实体的值)

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate; // 开始时间-查询条件

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate; // 结束时间-查询条件

	@Transient
	private List<TimingEntity> timingList; // 设置的定时时间实体

	public EmailEntity() {
	};

	public EmailEntity(Long emailId, String title, String content, Long inputNum, Long legalNum, Long illegalNum,
			String sourceType, String screenIds, String groupIds, String sendType, String timeSwitch, Date updateDate) {
		super();
		this.emailId = emailId;
		this.title = title;
		this.content = content;
		this.inputNum = inputNum;
		this.legalNum = legalNum;
		this.illegalNum = illegalNum;
		this.sourceType = sourceType;
		this.screenIds = screenIds;
		this.groupIds = groupIds;
		this.sendType = sendType;
		this.timeSwitch = timeSwitch;
		super.setUpdateDate(updateDate);
	}
	
	public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getLegalEmails() {
		return legalEmails;
	}

	public void setLegalEmails(String legalEmails) {
		this.legalEmails = legalEmails;
	}

	public String getIllegalEmails() {
		return illegalEmails;
	}

	public void setIllegalEmails(String illegalEmails) {
		this.illegalEmails = illegalEmails;
	}

	public Long getInputNum() {
		return inputNum;
	}

	public void setInputNum(Long inputNum) {
		this.inputNum = inputNum;
	}

	public Long getLegalNum() {
		return legalNum;
	}

	public void setLegalNum(Long legalNum) {
		this.legalNum = legalNum;
	}

	public Long getIllegalNum() {
		return illegalNum;
	}

	public void setIllegalNum(Long illegalNum) {
		this.illegalNum = illegalNum;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Long getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Long successNum) {
		this.successNum = successNum;
	}

	public Long getFailNum() {
		return failNum;
	}

	public void setFailNum(Long failNum) {
		this.failNum = failNum;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getScreenIds() {
		return screenIds;
	}

	public void setScreenIds(String screenIds) {
		this.screenIds = screenIds;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getTimeSwitch() {
		return timeSwitch;
	}

	public void setTimeSwitch(String timeSwitch) {
		this.timeSwitch = timeSwitch;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<TimingEntity> getTimingList() {
		return timingList;
	}

	public void setTimingList(List<TimingEntity> timingList) {
		this.timingList = timingList;
	}

	public boolean isFailEmailsFlag() {
		return failEmailsFlag;
	}

	public void setFailEmailsFlag(boolean failEmailsFlag) {
		this.failEmailsFlag = failEmailsFlag;
	}

	public String getFailEmails() {
		return failEmails;
	}

	public void setFailEmails(String failEmails) {
		this.failEmails = failEmails;
	}
	
}
