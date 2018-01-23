package com.gw.das.business.dao.room.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 直播间-用户在线时长统计
 * 
 */
public class DasChartRoomOnlineHours extends BaseModel {

	@Column(name = "rowkey")
	private String rowKey;

	// 日期
	@Column(name = "datetime")
	private String dateTime;

	// 课程名称
	@Column(name = "coursename")
	private String courseName;

	// 老师名称
	@Column(name = "teacherName")
	private String teacherName;

	// 房间名称
	@Column(name = "roomName")
	private String roomName;

	// 用户在线时长(1分钟以内)
	@Column(name = "seconds1")
	private int seconds1;

	// 用户在线时长(1-5分钟)
	@Column(name = "seconds2")
	private int seconds2;

	// 用户在线时长(5-30分钟)
	@Column(name = "seconds3")
	private int seconds3;

	// 用户在线时长(30-1小时)
	@Column(name = "seconds4")
	private int seconds4;

	// 用户在线时长(1-2小时)
	@Column(name = "seconds5")
	private int seconds5;

	// 用户在线时长(2小时以上)
	@Column(name = "seconds6")
	private int seconds6;

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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getSeconds1() {
		return seconds1;
	}

	public void setSeconds1(int seconds1) {
		this.seconds1 = seconds1;
	}

	public int getSeconds2() {
		return seconds2;
	}

	public void setSeconds2(int seconds2) {
		this.seconds2 = seconds2;
	}

	public int getSeconds3() {
		return seconds3;
	}

	public void setSeconds3(int seconds3) {
		this.seconds3 = seconds3;
	}

	public int getSeconds4() {
		return seconds4;
	}

	public void setSeconds4(int seconds4) {
		this.seconds4 = seconds4;
	}

	public int getSeconds5() {
		return seconds5;
	}

	public void setSeconds5(int seconds5) {
		this.seconds5 = seconds5;
	}

	public int getSeconds6() {
		return seconds6;
	}

	public void setSeconds6(int seconds6) {
		this.seconds6 = seconds6;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
