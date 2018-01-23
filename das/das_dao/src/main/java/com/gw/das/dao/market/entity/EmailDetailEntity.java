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

/**
 * 邮件发送详细实体
 * @author wayne
 *
 */
@Entity
@Table(name = "t_email_detail")
public class EmailDetailEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "detail_id", nullable = false)
	private Long detailId;

	@Column(name = "email_id")
	private Long emailId;// 邮件实体id

	@Column(name = "send_email", length = 100)
	private String sendEmail;// 发送的邮箱

	@Column(name = "rec_email", length = 100)
	private String recEmail;// 接收的邮箱

	@Column(name = "commit_status", length = 100)
	private String commitStatus;// 邮件提交状态-在本系统是否提交成功

	@Column(name = "send_status", length = 100)
	private String sendStatus;// 发送状态-3种（成功/失败/成功接收）

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate; // 开始时间-查询条件

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate; // 结束时间-查询条件

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getRecEmail() {
		return recEmail;
	}

	public void setRecEmail(String recEmail) {
		this.recEmail = recEmail;
	}

	public String getCommitStatus() {
		return commitStatus;
	}

	public void setCommitStatus(String commitStatus) {
		this.commitStatus = commitStatus;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
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
	
}
