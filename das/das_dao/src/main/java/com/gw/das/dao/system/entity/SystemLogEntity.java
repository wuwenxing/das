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
 * 系统日志
 * 
 * @author wayne
 */
@Entity
@Table(name = "t_system_log")
public class SystemLogEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "system_log_id", nullable = false)
	private Long systemLogId; // ID
	
	@Column(name = "log_type", length = 500)
	private String logType; // 系统模板日志类型

	@Column(name = "param", length = 2000)
	private String param;// 提交的参数(参数转json格式)
	
	@Column(name = "url", length = 500)
	private String url; // 访问地址

	@Column(name = "remark", length = 500)
	private String remark; // 备注

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate; // 开始时间-查询条件

	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate; // 结束时间-查询条件

	public Long getSystemLogId() {
		return systemLogId;
	}

	public void setSystemLogId(Long systemLogId) {
		this.systemLogId = systemLogId;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
