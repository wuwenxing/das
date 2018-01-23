package com.gw.das.business.dao.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.dao.base.BaseSearchModel;

/**
 * 账户黑名单列表
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DimAccountBlackListSearchBean extends BaseSearchModel {

	// 账号
	private String accountNo;
	
	// 日期-开始日期
	private String startTime;

	// 日期-结束日期
	private String endTime;
	
	// 标记日期-开始日期
	private String startMarkTime;

	// 标记日期-结束日期
	private String endMarkTime;
	
	// 客户姓名
	private String accountName;

	// 手机号码
	private String mobile;

	// 身份证号
	private String idCard;
	
	// IP
	private String createIp;
	
	// 平台标示
	private String platform;

	// 公司id
	private String companyId;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartMarkTime() {
		return startMarkTime;
	}

	public void setStartMarkTime(String startMarkTime) {
		this.startMarkTime = startMarkTime;
	}

	public String getEndMarkTime() {
		return endMarkTime;
	}

	public void setEndMarkTime(String endMarkTime) {
		this.endMarkTime = endMarkTime;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}
