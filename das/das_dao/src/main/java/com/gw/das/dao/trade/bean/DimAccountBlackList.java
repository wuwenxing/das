package com.gw.das.dao.trade.bean;

/**
 * 账户黑名单列表
 */
public class DimAccountBlackList {
	/** rowKey */
	private String rowKey;
	/** 公司名 */
	private String companyId;
	/** 公司名 */
	private String companyName;
	/** 平台 */
	private String platform;
	/** 账户id */
	private String accountId;
	/** 账户No */
	private String accountNo;
	/** 账户名中文 */
	private String accountNameCn;
	/** 手机 */
	private String mobile;
	/** 身份证MD5 */
	private String idCardMd5;
	/** 身份证加密 */
	private String idCardEncrypt;
	/** ip */
	private String createIp;
	/** 行为 */
	private String behavior;
	/** 标记日期 */
	private String markTime;
	/** 行为日期 */
	private String behaviorDate;
	/** 行为备注 */
	private String behaviorRemark;
	/** 风险因素 */
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
