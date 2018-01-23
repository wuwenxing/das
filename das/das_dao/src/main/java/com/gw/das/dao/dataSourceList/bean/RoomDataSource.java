package com.gw.das.dao.dataSourceList.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 直播间数据源
 * 
 * @author darren
 *
 */
public class RoomDataSource {

	private String rowkey;
	
	/* 会话标识 */
	private String sessionId;
	
	/* 设备浏览器标识ID*/
	private String userId;
	
	/* 标识业务平台*/
	private String businessPlatform;
	
	/* 平台名称 */
	private String platformName;
	
	/* 平台类型 （例：标识是手机还是pc访问，0和空代表pc访问，1代表手机m站访问）*/
	private String platformType;
	
	/* 平台产品的版本 */
	private String platformVersion;
	
	/* 设备标识ID */
	private String deviceId;
	
	/* 标记关键词 */
	private String markWords;
	
	/* 用户输入的关键词 */
	private String utmctr;
	
	/* 广告系列 */
	private String utmccn;
	
	/* 广告组*/
	private String utmcct;
	
	/* 广告媒介 */
	private String utmcmd;
	
	/* 广告来源 */
	private String utmcsr;
	
	/* 用户输入的关键词2 */
	private String utmctr2;
	
	/* 广告系列2*/
	private String utmccn2;
	
	/* 广告组2 */
	private String utmcct2;
	
	/* 广告媒介2 */
	private String utmcmd2;
	
	/* 广告来源2 */
	private String utmcsr2;
	
	/* 电话号码 */
	private String userTel;
	
	/* 行为类型 */
	private String userType;
	
	/* 用户名称 */
	private String userName;
	
	/* 房间名称 */
	private String roomName;
	
	/* 来源 */
	private String userSource;
	
	/* 使用设备 */
	private String useEquipment;
	
	/* 交易帐号 */
	private String tradingAccount;
	
	/* 交易平台 */
	private String tradingPlatform;
	
	/* 用户入口 */
	private String operateEntrance;
	
	/* 操作类型 */
	private String operationType;
	
	/* 访客ID */
	private String touristId;
	
	/* 用户昵称 */
	private String nickName;
	
	/* 用户邮箱 */
	private String email;
	
	/* 教学视频名称 */
	private String videoName;
	
	/* 课程名称 */
	private String courseName;
	
	/* 老师名称 */
	private String teacherName;
	
	/* 事件对象 */
	private String eventCategory;
	
	/* 互动类型*/
	private String eventAction;
	
	/* 访问IP*/
	private String userIp;
	
	/* 访问时间*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String operationTime;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMarkWords() {
		return markWords;
	}

	public void setMarkWords(String markWords) {
		this.markWords = markWords;
	}

	public String getUtmctr() {
		return utmctr;
	}

	public void setUtmctr(String utmctr) {
		this.utmctr = utmctr;
	}

	public String getUtmccn() {
		return utmccn;
	}

	public void setUtmccn(String utmccn) {
		this.utmccn = utmccn;
	}

	public String getUtmcct() {
		return utmcct;
	}

	public void setUtmcct(String utmcct) {
		this.utmcct = utmcct;
	}

	public String getUtmcmd() {
		return utmcmd;
	}

	public void setUtmcmd(String utmcmd) {
		this.utmcmd = utmcmd;
	}

	public String getUtmcsr() {
		return utmcsr;
	}

	public void setUtmcsr(String utmcsr) {
		this.utmcsr = utmcsr;
	}

	public String getUtmctr2() {
		return utmctr2;
	}

	public void setUtmctr2(String utmctr2) {
		this.utmctr2 = utmctr2;
	}

	public String getUtmccn2() {
		return utmccn2;
	}

	public void setUtmccn2(String utmccn2) {
		this.utmccn2 = utmccn2;
	}

	public String getUtmcct2() {
		return utmcct2;
	}

	public void setUtmcct2(String utmcct2) {
		this.utmcct2 = utmcct2;
	}

	public String getUtmcmd2() {
		return utmcmd2;
	}

	public void setUtmcmd2(String utmcmd2) {
		this.utmcmd2 = utmcmd2;
	}

	public String getUtmcsr2() {
		return utmcsr2;
	}

	public void setUtmcsr2(String utmcsr2) {
		this.utmcsr2 = utmcsr2;
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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getUseEquipment() {
		return useEquipment;
	}

	public void setUseEquipment(String useEquipment) {
		this.useEquipment = useEquipment;
	}

	public String getTradingAccount() {
		return tradingAccount;
	}

	public void setTradingAccount(String tradingAccount) {
		this.tradingAccount = tradingAccount;
	}

	public String getTradingPlatform() {
		return tradingPlatform;
	}

	public void setTradingPlatform(String tradingPlatform) {
		this.tradingPlatform = tradingPlatform;
	}

	public String getOperateEntrance() {
		return operateEntrance;
	}

	public void setOperateEntrance(String operateEntrance) {
		this.operateEntrance = operateEntrance;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public String getEventAction() {
		return eventAction;
	}

	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	

	
	
}
