package com.gw.das.dao.room.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 直播间-发言天/次数统计
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartRoomSpeakStatistics {

	private String rowKey;

	// 日期
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateTime;
	
	private String weeks;

	// 课程名称
	private String courseName;

	// 老师名称
	private String teacherName;

	// 房间名称
	private String roomName;

	// 发言天/次数(0天)
	private int u0;

	// 发言天/次数(1天)
	private int u1;

	// 发言天/次数(1-5天)
	private int u2;

	// 发言天/次数(5-10天)
	private int u3;

	// 发言天/次数(10-20天)
	private int u4;

	// 发言天/次数(20-30天)
	private int u5;

	// 发言天/次数(30天以上)
	private int u6;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getWeeks(){
		return weeks;
	}
	
	public void setWeeks(String weeks){
		this.weeks = weeks;
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

	public int getU0() {
		return u0;
	}

	public void setU0(int u0) {
		this.u0 = u0;
	}

	public int getU1() {
		return u1;
	}

	public void setU1(int u1) {
		this.u1 = u1;
	}

	public int getU2() {
		return u2;
	}

	public void setU2(int u2) {
		this.u2 = u2;
	}

	public int getU3() {
		return u3;
	}

	public void setU3(int u3) {
		this.u3 = u3;
	}

	public int getU4() {
		return u4;
	}

	public void setU4(int u4) {
		this.u4 = u4;
	}

	public int getU5() {
		return u5;
	}

	public void setU5(int u5) {
		this.u5 = u5;
	}

	public int getU6() {
		return u6;
	}

	public void setU6(int u6) {
		this.u6 = u6;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
