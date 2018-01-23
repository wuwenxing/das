package com.gw.das.dao.room.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 直播间-用户注册分析统计
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartRoomRegTouristUserStatistics {

	private String rowKey;

	// 日期
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateTime;
	
	private String weeks;

	// vip用户-人数
	private int vipNumbers;

	// 真实A用户-人数
	private int realANumbers;

	// 真实N用户-人数
	private int realNNumbers;

	// 模拟用户-人数
	private int demoNumbers;

	// 注册用户-人数
	private int registNumbers;

	// 游客用户-人数
	private int touristNumbers;

	// 平台类型
	private String platFormtype;

	// 课程名称
	private String courseName;

	// 老师名称
	private String teacherName;

	// 房间名称
	private String roomName;

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
	
	

	
	public String getWeeks()
	{
		return weeks;
	}

	
	public void setWeeks(String weeks)
	{
		this.weeks = weeks;
	}

	public int getVipNumbers() {
		return vipNumbers;
	}

	public void setVipNumbers(int vipNumbers) {
		this.vipNumbers = vipNumbers;
	}

	public int getRealANumbers() {
		return realANumbers;
	}

	public void setRealANumbers(int realANumbers) {
		this.realANumbers = realANumbers;
	}

	public int getRealNNumbers() {
		return realNNumbers;
	}

	public void setRealNNumbers(int realNNumbers) {
		this.realNNumbers = realNNumbers;
	}

	public int getDemoNumbers() {
		return demoNumbers;
	}

	public void setDemoNumbers(int demoNumbers) {
		this.demoNumbers = demoNumbers;
	}

	public int getRegistNumbers() {
		return registNumbers;
	}

	public void setRegistNumbers(int registNumbers) {
		this.registNumbers = registNumbers;
	}

	public int getTouristNumbers() {
		return touristNumbers;
	}

	public void setTouristNumbers(int touristNumbers) {
		this.touristNumbers = touristNumbers;
	}

	public String getPlatFormtype() {
		return platFormtype;
	}

	public void setPlatFormtype(String platFormtype) {
		this.platFormtype = platFormtype;
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

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
