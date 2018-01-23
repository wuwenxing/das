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
@Table(name = "t_sms_template_detail")
public class SmsTemplateDetailEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "detail_id", nullable = false)
	private Long detailId;

	@Column(name = "template_id")
	private Long templateId;// 模板id

	@Column(name = "send_no", length = 50)
	private String sendNo;// 发送人账号(接口如果传入，则记录，实现需求推荐发送短信次数计数功能)

	@Column(name = "phone", length = 100)
	private String phone;// 发送的手机号

	@Column(name = "content", length = 1000)
	private String content;// 发送的短信内容

	@Column(name = "interface_type", length = 100)
	private String interfaceType;// 使用的第三方短信接口商(秒嘀/至臻)

	@Column(name = "commit_status", length = 100)
	private String commitStatus;// 短信提交状态-在本系统是否提交成功

	@Column(name = "smsid", length = 100)
	private String smsid;// 短信返回smsid-短信第三方接口返回smsid

	@Column(name = "res_code", length = 100)
	private String resCode;// 短信返回状态码-短信第三方接口返回状态码

	@Column(name = "send_status", length = 100)
	private String sendStatus;// 短信发送状态-3种（成功/失败/成功接收）
	
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

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getSendNo() {
		return sendNo;
	}

	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getCommitStatus() {
		return commitStatus;
	}

	public void setCommitStatus(String commitStatus) {
		this.commitStatus = commitStatus;
	}

	public String getSmsid() {
		return smsid;
	}

	public void setSmsid(String smsid) {
		this.smsid = smsid;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
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
