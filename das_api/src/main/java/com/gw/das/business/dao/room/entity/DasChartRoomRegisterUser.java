package com.gw.das.business.dao.room.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 直播间注册用户表
 * 
 * @author kirin.guan
 *
 */
public class DasChartRoomRegisterUser extends BaseModel {
	@Column(name = "rowKey")
	private String rowKey;

	// 游客Id
	@Column(name = "touristId")
	private String touristId;

	// 用户来源
	@Column(name = "userSource")
	private String userSource;

	// 手机号码
	@Column(name = "userTel")
	private String userTel;

	// 1.游客、2.注册、3：模拟用户、4：真实A用户、5：真实N用户、6：VIP用户
	@Column(name = "userType")
	private String userType;

	// 用户昵称
	@Column(name = "userName")
	private String userName;

	// 注册时间 yyyy-MM-dd HH:mm:ss
	@Column(name = "registerTime")
	private String registerTime;

	// 1:外汇 2:贵金属 3：恒信
	@Column(name = "businessPlatform")
	private String businessPlatform;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getTouristId() {
		return touristId;
	}

	public void setTouristId(String touristId) {
		this.touristId = touristId;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}
}
