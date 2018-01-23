package com.gw.das.dao.system.entity;

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
 * 系统线程执行记录表
 * @author kirin.guan
 *
 */
@Entity
@Table(name = "t_system_thread_log")
public class SystemThreadLogEntity extends BaseEntity{

	private static final long serialVersionUID = 6149522651907124760L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "log_id", nullable = false)
	private Long logId;
	
	@Column(name = "code", length = 100)
	private String code;// 线程code
	
	@Column(name = "status", length = 100)
	private String status;// 线程执行状态，Y/N
	
	@Column(name = "remark", length = 500)
	private String remark;// 执行备注
	
	@Column(name = "start_execute_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startExecuteTime;// 线程执行开始时间
	
	@Column(name = "end_execute_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endExecuteTime;// 线程执行完毕时间

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate; // 开始时间-查询条件

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate; // 结束时间-查询条件
	
	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStartExecuteTime() {
		return startExecuteTime;
	}

	public void setStartExecuteTime(Date startExecuteTime) {
		this.startExecuteTime = startExecuteTime;
	}

	public Date getEndExecuteTime() {
		return endExecuteTime;
	}

	public void setEndExecuteTime(Date endExecuteTime) {
		this.endExecuteTime = endExecuteTime;
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