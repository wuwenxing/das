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
@Table(name = "t_sms_template_log")
public class SmsTemplateLogEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "log_id", nullable = false)
	private Long logId;

	@Column(name = "template_id")
	private Long templateId;// 模板id

	@Column(name = "param", columnDefinition="MEDIUMTEXT")
	private String param;// 提交的参数(参数转json格式)

	@Column(name = "phones", columnDefinition="MEDIUMTEXT")
	private String phones;// 提交的手机号集合

	@Column(name = "legal_phones", columnDefinition="MEDIUMTEXT")
	private String legalPhones; // 合法手机号码

	@Column(name = "illegal_phones", columnDefinition="MEDIUMTEXT")
	private String illegalPhones; // 非法手机号码

	@Column(name = "input_num")
	private Long inputNum;// 总输入的数量

	@Column(name = "legal_num")
	private Long legalNum;// 合法手机号码数量

	@Column(name = "illegal_num")
	private Long illegalNum;// 非法手机号码数量

	@Column(name = "status_code", length = 100)
	private String statusCode;// 此次请求状态码(成功/失败/参数不正确/等等)

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate; // 开始时间-查询条件

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate; // 结束时间-查询条件

	/**
	 * 发送人账号(接口如果传入，则记录，实现需求推荐发送短信次数计数功能)
	 */
	@Transient
	private String sendNo;
	
	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
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

	public String getLegalPhones() {
		return legalPhones;
	}

	public void setLegalPhones(String legalPhones) {
		this.legalPhones = legalPhones;
	}

	public String getIllegalPhones() {
		return illegalPhones;
	}

	public void setIllegalPhones(String illegalPhones) {
		this.illegalPhones = illegalPhones;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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

	public String getSendNo() {
		return sendNo;
	}

	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
	}
	
}
