package com.gw.das.dao.trade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gw.das.dao.base.BaseEntity;


@Entity
@Table(name = "t_risk_blacklist")
public class RiskBlacklistEntity extends BaseEntity {

	private static final long serialVersionUID = -3260436475224543365L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "risk_blacklist_id", nullable = false)
	private Long riskBlacklistId;
	
	@Column(name = "md5", length = 50)
	private String md5;// 唯一，用于上传判断去重,MD5（所有列拼接）
	
	@Column(name = "risk_type", length = 100)
	private String riskType;// 风险类型
	
	@Column(name = "risk_time", length = 100)
	private String riskTime;// 风险时间
	
	@Column(name = "risk_remark", length = 1000)
	private String riskRemark;// 风险备注
	
	@Column(name = "risk_reason", length = 100)
	private String riskReason;// 风险原因
	
	@Column(name = "platform", length = 100)
	private String platform;// 平台
	
	@Column(name = "account_no", length = 100)
	private String accountNo;// 账号
	
	@Column(name = "mobile", length = 100)
	private String mobile;// 电话
	
	@Column(name = "email", length = 100)
	private String email;// 邮箱
	
	@Column(name = "id_card", length = 100)
	private String idCard;// 证件号码
	
	@Column(name = "ip", length = 100)
	private String ip;// IP
	
	@Column(name = "device_type", length = 100)
	private String deviceType;// 设备类型

	@Column(name = "device_info", length = 100)
	private String deviceInfo;// 设备信息
	
	@Transient
	private String startDate; // 开始时间-查询条件

	@Transient
	private String endDate; // 结束时间-查询条件

	public Long getRiskBlacklistId() {
		return riskBlacklistId;
	}

	public void setRiskBlacklistId(Long riskBlacklistId) {
		this.riskBlacklistId = riskBlacklistId;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public String getRiskTime() {
		return riskTime;
	}

	public void setRiskTime(String riskTime) {
		this.riskTime = riskTime;
	}

	public String getRiskReason() {
		return riskReason;
	}

	public void setRiskReason(String riskReason) {
		this.riskReason = riskReason;
	}

	public String getRiskRemark() {
		return riskRemark;
	}

	public void setRiskRemark(String riskRemark) {
		this.riskRemark = riskRemark;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
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


}
