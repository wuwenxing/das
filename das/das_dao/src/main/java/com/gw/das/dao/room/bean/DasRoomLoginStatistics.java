package com.gw.das.dao.room.bean;

/**
 * 恒信直播间登陆统计
 */
public class DasRoomLoginStatistics {

	private String rowKey;

	// 日期
	private String dateTime;

	// 交易帐号
	private String account;

	// 用户类型
	private String userType;

	// 开户时间
	private String openAccountTime;

	// 激活时间
	private String activeTime;

	// 最后交易时间
	private String latestTradeTime;

	// 直播大厅-pv数
	private int liveRoomCount;

	// 直播大厅-累计次数
	private int liveRoomCountMin;

	// VIP房间-pv数
	private int vipRoomCount;

	// VIP房间-累计次数
	private int vipRoomCountMin;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

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

	public String getOpenAccountTime() {
		return openAccountTime;
	}

	public void setOpenAccountTime(String openAccountTime) {
		this.openAccountTime = openAccountTime;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getLatestTradeTime() {
		return latestTradeTime;
	}

	public void setLatestTradeTime(String latestTradeTime) {
		this.latestTradeTime = latestTradeTime;
	}

	public int getLiveRoomCount() {
		return liveRoomCount;
	}

	public void setLiveRoomCount(int liveRoomCount) {
		this.liveRoomCount = liveRoomCount;
	}

	public int getLiveRoomCountMin() {
		return liveRoomCountMin;
	}

	public void setLiveRoomCountMin(int liveRoomCountMin) {
		this.liveRoomCountMin = liveRoomCountMin;
	}

	public int getVipRoomCount() {
		return vipRoomCount;
	}

	public void setVipRoomCount(int vipRoomCount) {
		this.vipRoomCount = vipRoomCount;
	}

	public int getVipRoomCountMin() {
		return vipRoomCountMin;
	}

	public void setVipRoomCountMin(int vipRoomCountMin) {
		this.vipRoomCountMin = vipRoomCountMin;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}
	
}
