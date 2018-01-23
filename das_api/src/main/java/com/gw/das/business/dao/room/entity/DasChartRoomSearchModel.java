package com.gw.das.business.dao.room.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.dao.base.BaseSearchModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartRoomSearchModel extends BaseSearchModel {

	// 报表类型: (hours:时段统计、days:日统计、weeks:周统计、months:月统计)
	private String reportType;

	// 根据用户类型进行统计: (1:登录次/人数、2:访问次/人数、3:发言次/人数)
	private String userTypeStatistics;

	// 根据发言类型进行统计: (1:发言天数、2:发言次数)
	private String speakTypeStatistics;

	// 发言类型(所以-all,公聊-public,私聊-private)
	private String speakType;

	// 访问累加表rowKey
	private String rowKey;
	
	// 游客Id
	private String touristId;

	// 上线时间-开始时间
	private String upStartTime;

	// 上线时间-结束时间
	private String upEndTime;

	// 下线时间-开始时间
	private String downStartTime;

	// 下线时间-结束时间
	private String downEndTime;

	// 注册时间-开始时间
	private String regStartTime;

	// 注册时间-结束时间
	private String regEndTime;

	// 在线时长-大于或等于
	private String timeLengthStart;

	// 在线时长-小于或等于
	private String timeLengthEnd;

	// 日期-开始日期
	private String startTime;

	// 日期-结束日期
	private String endTime;

	// 小时-开始时段
	private String startHours;

	// 小时-结束时段
	private String endHours;

	// 房间名称
	private String roomName;

	// 房间名称勾选标示
	private boolean roomNameChecked;

	// 课程名称
	private String courseName;

	// 课程名称勾选标示
	private boolean courseNameChecked;

	// 老师名称
	private String teacherName;

	// 老师名称勾选标示
	private boolean teacherNameChecked;

	// 用户昵称
	private String nickName;
	
	// 用户名
	private String userName;
	
	// 手机号码
	private String userTel;

	// 用户级别-1.游客、2.注册、3：模拟用户、4：真实A用户、5：真实N用户、6：VIP用户
	private String userType;

	// 用户来源
	private String userSource;

	// 操作来源
	private String operateEntrance;

	// 用户设备
	private String useEquipment;

	// 访问客户端
	private String platformType;

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getUserTypeStatistics() {
		return userTypeStatistics;
	}

	public void setUserTypeStatistics(String userTypeStatistics) {
		this.userTypeStatistics = userTypeStatistics;
	}

	public String getSpeakTypeStatistics() {
		return speakTypeStatistics;
	}

	public void setSpeakTypeStatistics(String speakTypeStatistics) {
		this.speakTypeStatistics = speakTypeStatistics;
	}

	public String getSpeakType() {
		return speakType;
	}

	public void setSpeakType(String speakType) {
		this.speakType = speakType;
	}

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

	public String getUpStartTime() {
		return upStartTime;
	}

	public void setUpStartTime(String upStartTime) {
		this.upStartTime = upStartTime;
	}

	public String getUpEndTime() {
		return upEndTime;
	}

	public void setUpEndTime(String upEndTime) {
		this.upEndTime = upEndTime;
	}

	public String getDownStartTime() {
		return downStartTime;
	}

	public void setDownStartTime(String downStartTime) {
		this.downStartTime = downStartTime;
	}

	public String getDownEndTime() {
		return downEndTime;
	}

	public void setDownEndTime(String downEndTime) {
		this.downEndTime = downEndTime;
	}

	public String getRegStartTime() {
		return regStartTime;
	}

	public void setRegStartTime(String regStartTime) {
		this.regStartTime = regStartTime;
	}

	public String getRegEndTime() {
		return regEndTime;
	}

	public void setRegEndTime(String regEndTime) {
		this.regEndTime = regEndTime;
	}

	public String getTimeLengthStart() {
		return timeLengthStart;
	}

	public void setTimeLengthStart(String timeLengthStart) {
		this.timeLengthStart = timeLengthStart;
	}

	public String getTimeLengthEnd() {
		return timeLengthEnd;
	}

	public void setTimeLengthEnd(String timeLengthEnd) {
		this.timeLengthEnd = timeLengthEnd;
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

	public String getStartHours() {
		return startHours;
	}

	public void setStartHours(String startHours) {
		this.startHours = startHours;
	}

	public String getEndHours() {
		return endHours;
	}

	public void setEndHours(String endHours) {
		this.endHours = endHours;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public boolean isRoomNameChecked() {
		return roomNameChecked;
	}

	public void setRoomNameChecked(boolean roomNameChecked) {
		this.roomNameChecked = roomNameChecked;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public boolean isCourseNameChecked() {
		return courseNameChecked;
	}

	public void setCourseNameChecked(boolean courseNameChecked) {
		this.courseNameChecked = courseNameChecked;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public boolean isTeacherNameChecked() {
		return teacherNameChecked;
	}

	public void setTeacherNameChecked(boolean teacherNameChecked) {
		this.teacherNameChecked = teacherNameChecked;
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

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getOperateEntrance() {
		return operateEntrance;
	}

	public void setOperateEntrance(String operateEntrance) {
		this.operateEntrance = operateEntrance;
	}

	public String getUseEquipment() {
		return useEquipment;
	}

	public void setUseEquipment(String useEquipment) {
		this.useEquipment = useEquipment;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}


}
