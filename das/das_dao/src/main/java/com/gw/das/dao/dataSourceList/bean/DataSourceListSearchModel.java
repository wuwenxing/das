package com.gw.das.dao.dataSourceList.bean;

import com.gw.das.dao.base.BaseSearchModel;

/**
 * 数据源报表查询类
 * 
 * @author darren
 *
 */
public class DataSourceListSearchModel extends BaseSearchModel {

	private String startTime;// 日期-开始日期

	private String endTime;// 日期-结束日期

	private String userId; //用户
	
	private String sessionId; //会话标识
	
	private String platformType; //终端类型
	
	private String deviceId; //终端标识
	
	private String utmcsr; //来源(站外)
	
	private String utmcmd; //媒介(站外)
	
	private String utmccn; //系列(站外)
	
	private String utmcct; //组(站外)
	
	private String utmctr; //关键词(站外)
	
	private String utmcsr2; //来源(站内)
	
	private String utmcmd2; //媒介(站内)
	
	private String utmccn2; //系列(站内)
	
	private String utmcct2; //组(站内)
	
	private String utmctr2; //关键词(站内)
	
	private String behaviorDetail; //行为明细
	
	private String behaviorType; //行为类型
	
	private String eventCategory; //事件对象
	
	private String eventAction; //互动类型
	
	private String browser; //浏览器信息
	
	private String prevUrl; //上一次URL地址
	
	private String url; //访问URL
	
	private String ip; //终端IP
	
	private String userTel; //电话号码
	
	private String userType;
	
	private String userName; //用户名称
	
	private String roomName; //房间名称
	
	private String userSource; //来源
	
	private String useEquipment; //使用设备
	
	private String tradingAccount; //交易帐号
	
	private String tradingPlatform; //交易平台
	
	private String operateEntrance; ///用户入口
	
	private String operationType; //操作类型
	
	private String touristId; //访客ID
	
	private String nickName; //用户昵称
	
	private String email; //用户邮箱
	
	private String videoName; //教学视频名称
	
	private String courseName; //课程名称
	
	private String teacherName; //老师名称
	
	private String userIp; //访问IP
	

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUtmcsr() {
		return utmcsr;
	}

	public void setUtmcsr(String utmcsr) {
		this.utmcsr = utmcsr;
	}

	public String getUtmcmd() {
		return utmcmd;
	}

	public void setUtmcmd(String utmcmd) {
		this.utmcmd = utmcmd;
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

	public String getUtmctr() {
		return utmctr;
	}

	public void setUtmctr(String utmctr) {
		this.utmctr = utmctr;
	}

	public String getUtmcsr2() {
		return utmcsr2;
	}

	public void setUtmcsr2(String utmcsr2) {
		this.utmcsr2 = utmcsr2;
	}

	public String getUtmcmd2() {
		return utmcmd2;
	}

	public void setUtmcmd2(String utmcmd2) {
		this.utmcmd2 = utmcmd2;
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

	public String getUtmctr2() {
		return utmctr2;
	}

	public void setUtmctr2(String utmctr2) {
		this.utmctr2 = utmctr2;
	}

	public String getBehaviorDetail() {
		return behaviorDetail;
	}

	public void setBehaviorDetail(String behaviorDetail) {
		this.behaviorDetail = behaviorDetail;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
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

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getPrevUrl() {
		return prevUrl;
	}

	public void setPrevUrl(String prevUrl) {
		this.prevUrl = prevUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	

}
