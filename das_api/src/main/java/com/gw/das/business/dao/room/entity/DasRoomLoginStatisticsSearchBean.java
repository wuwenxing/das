package com.gw.das.business.dao.room.entity;

import com.gw.das.business.dao.base.BaseSearchModel;

public class DasRoomLoginStatisticsSearchBean extends BaseSearchModel {

	// 交易帐号
	private String account;
	// 用户类型
	private String userType;

	// 日期
	private String startTime;
	// 日期
	private String endTime;
	// 开户时间
	private String openAccountTimeStart;
	// 开户时间
	private String openAccountTimeEnd;
	// 激活时间
	private String activeTimeStart;
	// 激活时间
	private String activeTimeEnd;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getOpenAccountTimeStart() {
		return openAccountTimeStart;
	}

	public void setOpenAccountTimeStart(String openAccountTimeStart) {
		this.openAccountTimeStart = openAccountTimeStart;
	}

	public String getOpenAccountTimeEnd() {
		return openAccountTimeEnd;
	}

	public void setOpenAccountTimeEnd(String openAccountTimeEnd) {
		this.openAccountTimeEnd = openAccountTimeEnd;
	}

	public String getActiveTimeStart() {
		return activeTimeStart;
	}

	public void setActiveTimeStart(String activeTimeStart) {
		this.activeTimeStart = activeTimeStart;
	}

	public String getActiveTimeEnd() {
		return activeTimeEnd;
	}

	public void setActiveTimeEnd(String activeTimeEnd) {
		this.activeTimeEnd = activeTimeEnd;
	}
	
}
