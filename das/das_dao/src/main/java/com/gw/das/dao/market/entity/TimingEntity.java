package com.gw.das.dao.market.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gw.das.dao.base.BaseEntity;

/**
 * 定时实体
 */
@Entity
@Table(name = "t_timing")
public class TimingEntity extends BaseEntity {

	private static final long serialVersionUID = 2815256358753944839L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "timing_id", nullable = false)
	private Long timingId;

	@Column(name = "sms_id")
	private Long smsId;// 短信id-外键

	@Column(name = "email_id")
	private Long emailId;// 邮件id-外键

	@Column(name = "time_switch", length = 1)
	private String timeSwitch;// 定时开关Y/N

	@Column(name = "start_date")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@Column(name = "end_date")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	@Column(name = "hour")
	private int hour;

	@Column(name = "minute")
	private int minute;

	public Long getTimingId() {
		return timingId;
	}

	public void setTimingId(Long timingId) {
		this.timingId = timingId;
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

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}

	public String getTimeSwitch() {
		return timeSwitch;
	}

	public void setTimeSwitch(String timeSwitch) {
		this.timeSwitch = timeSwitch;
	}

}
