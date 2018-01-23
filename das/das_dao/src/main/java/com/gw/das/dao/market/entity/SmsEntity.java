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
 * 短信实体
 * 
 * @author wayne
 *
 */
@Entity
@Table(name = "t_sms")
public class SmsEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sms_id", nullable = false)
	private Long smsId;

	@Column(name = "sms_sign", length = 100)
	private String smsSign;// 短信签名

	@Column(name = "content", length = 1000)
	private String content;// 短信内容

	@Column(name = "phones", columnDefinition = "MEDIUMTEXT")
	private String phones;// 提交的手机号集合

	@Column(name = "legal_phones", columnDefinition = "MEDIUMTEXT")
	private String legalPhones; // 合法手机号码

	@Transient
	private boolean failPhonesFlag = false; // 给发送失败的手机再次发送标示
	@Transient
	private String failPhones; // 发送失败的手机号码

	@Column(name = "illegal_phones", columnDefinition = "MEDIUMTEXT")
	private String illegalPhones; // 非法手机号码

	@Column(name = "input_num")
	private Long inputNum;// 总输入的数量

	@Column(name = "legal_num")
	private Long legalNum;// 合法手机号码数量

	@Column(name = "illegal_num")
	private Long illegalNum;// 非法手机号码数量

	@Column(name = "total_num")
	private Long totalNum;// 发送总数量

	@Column(name = "success_num")
	private Long successNum;// 发送成功数量

	@Column(name = "fail_num")
	private Long failNum;// 发送失败数量

	@Column(name = "source_type", length = 100)
	private String sourceType;// 添加手机号的来源类型

	@Column(name = "screen_ids", length = 100)
	private String screenIds;// 用户筛选对象ID,多个逗号隔开

	@Column(name = "group_ids", length = 100)
	private String groupIds;// 客户分组条件对象ID,多个逗号隔开

	@Column(name = "send_type", length = 100)
	private String sendType; // 短信发送类型

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

	public SmsEntity() {
	};

	public SmsEntity(Long smsId, String smsSign, String content, Long inputNum, Long legalNum, Long illegalNum,
			String sourceType, String screenIds, String groupIds, String sendType, String timeSwitch, Date updateDate) {
		super();
		this.smsId = smsId;
		this.smsSign = smsSign;
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

	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public String getSmsSign() {
		return smsSign;
	}

	public void setSmsSign(String smsSign) {
		this.smsSign = smsSign;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public boolean isFailPhonesFlag() {
		return failPhonesFlag;
	}

	public void setFailPhonesFlag(boolean failPhonesFlag) {
		this.failPhonesFlag = failPhonesFlag;
	}

	public String getFailPhones() {
		return failPhones;
	}

	public void setFailPhones(String failPhones) {
		this.failPhones = failPhones;
	}

}
