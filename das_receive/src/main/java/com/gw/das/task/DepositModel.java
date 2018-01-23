package com.gw.das.task;

import java.util.Date;

public class DepositModel {
	private Date depositTime;
	private String accountNo;
	private String businessPlatform;
	public Date getDepositTime() {
		return depositTime;
	}
	public void setDepositTime(Date depositTime) {
		this.depositTime = depositTime;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBusinessPlatform() {
		return businessPlatform;
	}
	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}
}
