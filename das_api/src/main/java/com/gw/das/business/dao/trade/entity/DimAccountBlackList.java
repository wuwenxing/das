package com.gw.das.business.dao.trade.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 账户黑名单列表
 */
public class DimAccountBlackList extends BaseModel {

	/** rowKey */
	@Column(name = "rowkey")
	private String rowKey;
	/** 公司名 */
	@Column(name = "companyid")
	private String companyId;
	/** 公司名 */
	@Column(name = "companyname")
	private String companyName;
	/** 平台 */
	@Column(name = "platform")
	private String platform;
	/** 账户id */
	@Column(name = "accountid")
	private String accountId;
	/** 账户No */
	@Column(name = "accountno")
	private String accountNo;
	/** 账户名中文 */
	@Column(name = "accountnamecn")
	private String accountNameCn;
	/** 手机 */
	@Column(name = "mobile")
	private String mobile;
	/** 身份证MD5 */
	@Column(name = "idcardmd5")
	private String idCardMd5;
	/** 身份证加密 */
	@Column(name = "idcardencrypt")
	private String idCardEncrypt;
	/** ip */
	@Column(name = "createip")
	private String createIp;
	/** 行为 */
	@Column(name = "behavior")
	private String behavior;
	/** 标记日期 */
	@Column(name = "markTime")
	private String markTime;
	/** 行为日期 */
	@Column(name = "behaviordate")
	private String behaviorDate;
	/** 行为备注 */
	@Column(name = "behaviorremark")
	private String behaviorRemark;
	/** 风险因素 */
	@Column(name = "riskfactors")
	private String riskFactors;
	
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountNameCn() {
		return accountNameCn;
	}
	public void setAccountNameCn(String accountNameCn) {
		this.accountNameCn = accountNameCn;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdCardMd5() {
		return idCardMd5;
	}
	public void setIdCardMd5(String idCardMd5) {
		this.idCardMd5 = idCardMd5;
	}
	public String getIdCardEncrypt() {
		return idCardEncrypt;
	}
	public void setIdCardEncrypt(String idCardEncrypt) {
		this.idCardEncrypt = idCardEncrypt;
	}
	public String getCreateIp() {
		return createIp;
	}
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	public String getBehavior() {
		return behavior;
	}
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	public String getMarkTime() {
		return markTime;
	}
	public void setMarkTime(String markTime) {
		this.markTime = markTime;
	}
	public String getBehaviorDate() {
		return behaviorDate;
	}
	public void setBehaviorDate(String behaviorDate) {
		this.behaviorDate = behaviorDate;
	}
	public String getBehaviorRemark() {
		return behaviorRemark;
	}
	public void setBehaviorRemark(String behaviorRemark) {
		this.behaviorRemark = behaviorRemark;
	}
	public String getRiskFactors() {
		return riskFactors;
	}
	public void setRiskFactors(String riskFactors) {
		this.riskFactors = riskFactors;
	}
	
}
