package com.gw.das.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "utm_chart_room_basic")
public class ChartRoomBasic {
	// 用户ID
	private String userId;

	// 手机号码
	private String userTel;

	// <c:when test="${item.userInfoDemoData.userInfoChartRoomData.customerType
	// == 'active'}">
	// <td >真实A用户</td>
	// </c:when>
	// <c:when test="${item.userInfoDemoData.userInfoChartRoomData.customerType
	// == 'visitor'}">
	// <td >游客</td>
	// </c:when>
	// <c:when test="${item.userInfoDemoData.userInfoChartRoomData.customerType
	// == 'vip'}">
	// <td >VIP用户</td>
	// </c:when>
	// <c:when test="${item.userInfoDemoData.userInfoChartRoomData.customerType
	// == 'simulate'}">
	// <td >模拟用户</td>
	// </c:when>
	// <c:when test="${item.userInfoDemoData.userInfoChartRoomData.customerType
	// == 'register'}">
	// <td >注册用户</td>
	// </c:when>
	// <c:when test="${item.userInfoDemoData.userInfoChartRoomData.customerType
	// == 'noActive'}">
	// <td >真实N用户</td>
	// </c:when>

	// 用户类型1.游客、2.注册、3：模拟用户、4：真实A用户、5：真实N用户、6：VIP用户
	private String userType;

	// 用户名称
	private String userName;

	// 用户昵称
	private String nickName;

	// 用户邮箱
	private String email;

	// 房间名称
	private String roomName;

	// 房间ID
	private String roomId;

	// 游客ID
	private String touristId;

	// 操作类型 1：上线 、2：公聊 、3：注册、4：登录、5:退出 、6:下线、7:视频 8：私聊
	private String operationType;

	// 操作时间
	private String operationTime;

	// 用户ip
	private String userIp;

	// 用户来源
	private String userSource;

	// 使用设备 0：pc访问，1代表手机访问
	private String platformType;

	// 1:外汇 2:贵金属 3：恒信
	private String businessPlatform;

	// 使用设备
	private String useEquipment;

	// 交易帐号
	private String tradingAccount;

	// 交易平台 GTS2、MT4
	private String tradingPlatform;

	// 操作入口
	private String operateEntrance;

	private String sessionId;

	private String videoId;

	private String videoName;

	// 课程名称
	private String courseName;

	// 课程Id
	private String courseId;

	// 老师ID
	private String teacherId;

	// 老师名称
	private String teacherName;

	// 外部请求参数
	private String requestParams;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getTouristId() {
		return touristId;
	}

	public void setTouristId(String touristId) {
		this.touristId = touristId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
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

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperateEntrance() {
		return operateEntrance;
	}

	public void setOperateEntrance(String operateEntrance) {
		this.operateEntrance = operateEntrance;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

}
