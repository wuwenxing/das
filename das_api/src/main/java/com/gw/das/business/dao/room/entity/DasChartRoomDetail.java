package com.gw.das.business.dao.room.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 直播间流量表
 * 
 * @author kirin.guan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartRoomDetail extends BaseModel {
	@Column(name = "rowkey")
	private String rowKey;

	// 用户Id 和网站打通的标记
	@Column(name = "userid")
	private String userId;

	// 游客Id
	@Column(name = "touristid")
	private String touristId;
	
	// 用户昵称
	@Column(name = "nickname")
	private String nickName;
	
	// 用户名
	@Column(name = "username")
	private String userName;

	// 手机号码
	@Column(name = "usertel")
	private String userTel;

	// 1.游客、2.注册、3：模拟用户、4：真实A用户、5：真实N用户、6：VIP用户
	@Column(name = "usertype")
	private String userType;

	// 用户IP
	@Column(name = "userip")
	private String userIp;

	// ip归属地
	@Column(name = "iphome")
	private String ipHome;

	// 使用设备
	@Column(name = "useequipment")
	private String useEquipment;

	// 用户来源
	@Column(name = "usersource")
	private String userSource;

	// 上线时间
	@Column(name = "starttime")
	private String startTime;

	// 下线时间
	@Column(name = "endtime")
	private String endTime;

	// 在线时长
	@Column(name = "timelength")
	private String timeLength;

	// 房间id
	@Column(name = "roomid")
	private String roomId;

	// 房间名称
	@Column(name = "roomname")
	private String roomName;

	// 公聊发言次数
	@Column(name = "publicspeakcount")
	private int publicSpeakCount;

	// 私聊发言次数
	@Column(name = "privatespeakcount")
	private int privateSpeakCount;

	// 登录次数
	@Column(name = "logincount")
	private int loginCount;

	// 标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	@Column(name = "platformtype")
	private String platformType;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessplatform")
	private int businessPlatform;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTouristId() {
		return touristId;
	}

	public void setTouristId(String touristId) {
		this.touristId = touristId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getIpHome() {
		return ipHome;
	}

	public void setIpHome(String ipHome) {
		this.ipHome = ipHome;
	}

	public String getUseEquipment() {
		return useEquipment;
	}

	public void setUseEquipment(String useEquipment) {
		this.useEquipment = useEquipment;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
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

	public String getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getPublicSpeakCount() {
		return publicSpeakCount;
	}

	public void setPublicSpeakCount(int publicSpeakCount) {
		this.publicSpeakCount = publicSpeakCount;
	}

	public int getPrivateSpeakCount() {
		return privateSpeakCount;
	}

	public void setPrivateSpeakCount(int privateSpeakCount) {
		this.privateSpeakCount = privateSpeakCount;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public int getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(int businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
