package com.gw.das.dao.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;

@Entity
@Table(name = "t_user_group_log")
public class UserGroupLogEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "log_id", nullable = false)
	private Long logId;

	@Column(name = "group_id")
	private Long groupId;// 分组ID

	@Column(name = "param", length = 5000)
	private String param;// 提交的参数(参数转json格式)

	@Column(name = "phones", length = 2000)
	private String phones;// 提交的手机号集合
	@Column(name = "legal_phones", length = 2000)
	private String legalPhones; // 合法手机号码
	@Column(name = "illegal_phones", length = 2000)
	private String illegalPhones; // 非法手机号码

	@Column(name = "input_phone_num")
	private Long inputPhoneNum;// 总输入的手机数量
	@Column(name = "legal_phone_num")
	private Long legalPhoneNum;// 总合法手机号码数量
	@Column(name = "illegal_phone_num")
	private Long illegalPhoneNum;// 总非法手机号码数量

	@Column(name = "emails", length = 2000)
	private String emails;// 提交的邮箱集合
	@Column(name = "legal_emails", length = 2000)
	private String legalEmails; // 合法邮箱
	@Column(name = "illegal_emails", length = 2000)
	private String illegalEmails; // 非法邮箱

	@Column(name = "input_email_num")
	private Long inputEmailNum;// 总输入的邮箱数量
	@Column(name = "legal_email_num")
	private Long legalEmailNum;// 总合法邮箱数量
	@Column(name = "illegal_email_num")
	private Long illegalEmailNum;// 总非法邮箱数量

	@Column(name = "status_code", length = 100)
	private String statusCode;// 此次请求状态码(成功/失败/参数不正确/等等)

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
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

	public Long getInputPhoneNum() {
		return inputPhoneNum;
	}

	public void setInputPhoneNum(Long inputPhoneNum) {
		this.inputPhoneNum = inputPhoneNum;
	}

	public Long getLegalPhoneNum() {
		return legalPhoneNum;
	}

	public void setLegalPhoneNum(Long legalPhoneNum) {
		this.legalPhoneNum = legalPhoneNum;
	}

	public Long getIllegalPhoneNum() {
		return illegalPhoneNum;
	}

	public void setIllegalPhoneNum(Long illegalPhoneNum) {
		this.illegalPhoneNum = illegalPhoneNum;
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

	public Long getInputEmailNum() {
		return inputEmailNum;
	}

	public void setInputEmailNum(Long inputEmailNum) {
		this.inputEmailNum = inputEmailNum;
	}

	public Long getLegalEmailNum() {
		return legalEmailNum;
	}

	public void setLegalEmailNum(Long legalEmailNum) {
		this.legalEmailNum = legalEmailNum;
	}

	public Long getIllegalEmailNum() {
		return illegalEmailNum;
	}

	public void setIllegalEmailNum(Long illegalEmailNum) {
		this.illegalEmailNum = illegalEmailNum;
	}

}
