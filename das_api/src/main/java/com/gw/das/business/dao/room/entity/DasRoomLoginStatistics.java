package com.gw.das.business.dao.room.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 恒信直播间登陆统计
 * 对应das_room_login_statistics_hx_d表
 */
public class DasRoomLoginStatistics extends BaseModel {

	@Column(name = "rowkey")
	private String rowKey;

	// 日期
	@Column(name = "datetime")
	private String dateTime;

	// 交易帐号
	@Column(name = "account")
	private String account;

	// 用户类型
	@Column(name = "usertype")
	private String userType;

	// 开户时间
	@Column(name = "open_account_time")
	private String openAccountTime;

	// 激活时间
	@Column(name = "active_time")
	private String activeTime;

	// 最后交易时间
	@Column(name = "latest_trade_time")
	private String latestTradeTime;

	// 直播大厅-pv数
	@Column(name = "live_room_count")
	private int liveRoomCount;

	// 直播大厅-累计次数
	@Column(name = "live_room_count_min")
	private int liveRoomCountMin;

	// VIP房间-pv数
	@Column(name = "vip_room_count")
	private int vipRoomCount;

	// VIP房间-累计次数
	@Column(name = "vip_room_count_min")
	private int vipRoomCountMin;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessplatform")
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
