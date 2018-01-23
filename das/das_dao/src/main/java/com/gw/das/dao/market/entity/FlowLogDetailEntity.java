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
@Table(name = "t_flow_log_detail")
public class FlowLogDetailEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "detail_id", nullable = false)
	private Long detailId;

	@Column(name = "flow_log_id")
	private Long flowLogId;// 充值的记录id

	@Column(name = "phone", length = 100)
	private String phone;// 充值的手机号

	@Column(name = "flow_size", length = 10)
	private String flowSize;// 充值流量大小

	@Column(name = "interface_type", length = 100)
	private String interfaceType;// 使用的第三方短信接口商(亿美/...)

	@Column(name = "commit_status", length = 100)
	private String commitStatus;// 提交状态-在本系统是否提交成功

	@Column(name = "res_batch_no", length = 100)
	private String resBatchNo;// 返回批次号-第三方接口返回批次号

	@Column(name = "res_code", length = 100)
	private String resCode;// 返回状态码-第三方接口返回状态码

	@Column(name = "send_status", length = 100)
	private String sendStatus;// 状态-3种（成功/失败/成功充值）

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

	public Long getFlowLogId() {
		return flowLogId;
	}

	public void setFlowLogId(Long flowLogId) {
		this.flowLogId = flowLogId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFlowSize() {
		return flowSize;
	}

	public void setFlowSize(String flowSize) {
		this.flowSize = flowSize;
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

	public String getResBatchNo() {
		return resBatchNo;
	}

	public void setResBatchNo(String resBatchNo) {
		this.resBatchNo = resBatchNo;
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
