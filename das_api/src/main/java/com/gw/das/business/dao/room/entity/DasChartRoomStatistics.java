package com.gw.das.business.dao.room.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 直播间-时、日、周、月-行为统计
 */
public class DasChartRoomStatistics extends BaseModel {

	@Column(name = "rowkey")
	private String rowKey;

	// 日期
	@Column(name = "datetime")
	private String dateTime;

	// 小时数
	@Column(name = "hours")
	private String hours;

	// 周数
	@Column(name = "weeks")
	private String weeks;

	// 房间名称
	@Column(name = "roomname")
	private String roomName;

	// 课程名称
	@Column(name = "coursename")
	private String courseName;

	// 老师名称
	@Column(name = "teachername")
	private String teacherName;

	// 公聊发言次数
	@Column(name = "publicspeakcount")
	private int publicSpeakCount;

	// 公聊发言人数
	@Column(name = "publicspeaknumber")
	private int publicSpeakNumber;

	// 私聊发言次数
	@Column(name = "privatespeakcount")
	private int privateSpeakCount;

	// 私聊发言人数
	@Column(name = "privatespeaknumber")
	private int privateSpeakNumber;

	// 访问次数
	@Column(name = "visitcount")
	private int visitCount;

	// 访问人数
	@Column(name = "visitnumber")
	private int visitNumber;

	// 登录次数
	@Column(name = "logincount")
	private int loginCount;

	// 登录人数
	@Column(name = "loginnumber")
	private int loginNumber;

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

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
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

	public int getPublicSpeakCount() {
		return publicSpeakCount;
	}

	public void setPublicSpeakCount(int publicSpeakCount) {
		this.publicSpeakCount = publicSpeakCount;
	}

	public int getPublicSpeakNumber() {
		return publicSpeakNumber;
	}

	public void setPublicSpeakNumber(int publicSpeakNumber) {
		this.publicSpeakNumber = publicSpeakNumber;
	}

	public int getPrivateSpeakCount() {
		return privateSpeakCount;
	}

	public void setPrivateSpeakCount(int privateSpeakCount) {
		this.privateSpeakCount = privateSpeakCount;
	}

	public int getPrivateSpeakNumber() {
		return privateSpeakNumber;
	}

	public void setPrivateSpeakNumber(int privateSpeakNumber) {
		this.privateSpeakNumber = privateSpeakNumber;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public int getVisitNumber() {
		return visitNumber;
	}

	public void setVisitNumber(int visitNumber) {
		this.visitNumber = visitNumber;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public int getLoginNumber() {
		return loginNumber;
	}

	public void setLoginNumber(int loginNumber) {
		this.loginNumber = loginNumber;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
