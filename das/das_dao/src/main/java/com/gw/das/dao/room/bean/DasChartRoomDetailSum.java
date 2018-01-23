package com.gw.das.dao.room.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 直播间流量累加表
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartRoomDetailSum {

	private String rowKey;

	// 游客Id
	private String touristId;

	// 用户来源
	private String userSource;

	// 手机号码
	private String userTel;

	// 1.游客、2.注册、3：模拟用户、4：真实A用户、5：真实N用户、6：VIP用户
	private String userType;
	
	// 用户昵称
	private String nickName;
	
	// 用户名
	private String userName;

	// 注册时间 yyyy-MM-dd HH:mm:ss
	private String registerTime;

	// 开始时间（上线时间）
	private String startTime;

	// 结束时间（下线时间）
	private String endTime;

	// 上次上线时间
	private String lastStartTime;

	// 上次下线时间
	private String lastEndTime;

	// 在线时长
	private String timeLength;

	// 访问次数(一次上下线)
	private int visitCount;

	// 公聊发言次数
	private int publicSpeakCount;

	// 公聊发言天数
	private int publicSpeakDay;

	// 私聊累计次数
	private int privateSpeakCount;

	// 私聊发言天数
	private int privateSpeakDay;

	// 登录时间
	private String loginTime;

	// 上次登录时间
	private String lastLoginTime;

	// 退出登录时间
	private String logoutTime;

	// 用户ip
	private String userIp;

	// ip归属地
	private String ipHome;

	// 1:外汇 2:贵金属 3：恒信
	private String businessPlatform;

	// 0：pc访问，1代表手机访问（默认为0）
	private String platformType;

	// 房间名称
	private String roomName;

	// 房间Id
	private String roomId;

	// 操作入口
	private String operateEntrance;

	// 视频Id
	private String videoId;

	// 视频名称集合 用，分开
	private String videoName;

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

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
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

	public String getLastStartTime() {
		return lastStartTime;
	}

	public void setLastStartTime(String lastStartTime) {
		this.lastStartTime = lastStartTime;
	}

	public String getLastEndTime() {
		return lastEndTime;
	}

	public void setLastEndTime(String lastEndTime) {
		this.lastEndTime = lastEndTime;
	}

	public String getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public int getPublicSpeakCount() {
		return publicSpeakCount;
	}

	public void setPublicSpeakCount(int publicSpeakCount) {
		this.publicSpeakCount = publicSpeakCount;
	}

	public int getPublicSpeakDay() {
		return publicSpeakDay;
	}

	public void setPublicSpeakDay(int publicSpeakDay) {
		this.publicSpeakDay = publicSpeakDay;
	}

	public int getPrivateSpeakDay() {
		return privateSpeakDay;
	}

	public void setPrivateSpeakDay(int privateSpeakDay) {
		this.privateSpeakDay = privateSpeakDay;
	}

	public int getPrivateSpeakCount() {
		return privateSpeakCount;
	}

	public void setPrivateSpeakCount(int privateSpeakCount) {
		this.privateSpeakCount = privateSpeakCount;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
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

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getOperateEntrance() {
		return operateEntrance;
	}

	public void setOperateEntrance(String operateEntrance) {
		this.operateEntrance = operateEntrance;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

}
