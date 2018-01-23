package com.gw.das.dao.market.entity;

import java.util.Date;

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


@Entity
@Table(name = "t_account_analyze")
public class AccountAnalyzeEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "batch_id", nullable = false)
	private Long batchId;
	
	@Column(name = "batch_no", length = 100)
	private String batchNo;// 批次号
	
	@Column(name = "account_no", length = 100)
	private String accountNo;// 账号
	
	@Column(name = "platform", length = 100)
	private String platform;// 账号所在平台
	
	@Column(name = "email", length = 100)
	private String email;// 邮箱
	
	@Column(name = "start_date")
	private String startDate;// 诊断开始时间
	
	@Column(name = "end_date")
	private String endDate;// 诊断结束时间
	
	@Column(name = "generate_status", length = 20)
	private String generateStatus;// 诊断报告生成状态,N未生成，Y生成
	
	@Column(name = "generate_time", length = 20)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date generateTime;// 诊断报告生成时间
	
	@Column(name = "send_status", length = 20)
	private String sendStatus;// 最后一次邮件发送状态sendFail/sendSuccess/sendRecSuccess
	
	@Column(name = "send_time", length = 20)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;// 最后一次邮件发送时间
	
	@Column(name = "path", length = 200)
	private String path;// 诊断报告所在相对路径,完整路径=Constants.accountAnalyzePath+"/"+path

	@Column(name = "url", length = 200)
	private String url;// 诊断报告访问路径,完整访问路径=域名+Constants.accountAnalyzePath+"/"+path

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date generateTimeStart; // 开始时间-查询条件

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date generateTimeEnd; // 结束时间-查询条件
	
	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTimeStart; // 开始时间-查询条件

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTimeEnd; // 结束时间-查询条件
	
	@Transient
	private String content;
	
	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getGenerateStatus() {
		return generateStatus;
	}

	public void setGenerateStatus(String generateStatus) {
		this.generateStatus = generateStatus;
	}

	public Date getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getGenerateTimeStart() {
		return generateTimeStart;
	}

	public void setGenerateTimeStart(Date generateTimeStart) {
		this.generateTimeStart = generateTimeStart;
	}

	public Date getGenerateTimeEnd() {
		return generateTimeEnd;
	}

	public void setGenerateTimeEnd(Date generateTimeEnd) {
		this.generateTimeEnd = generateTimeEnd;
	}

	public Date getSendTimeStart() {
		return sendTimeStart;
	}

	public void setSendTimeStart(Date sendTimeStart) {
		this.sendTimeStart = sendTimeStart;
	}

	public Date getSendTimeEnd() {
		return sendTimeEnd;
	}

	public void setSendTimeEnd(Date sendTimeEnd) {
		this.sendTimeEnd = sendTimeEnd;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
