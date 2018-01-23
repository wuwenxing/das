package com.gw.das.business.dao.room.entity;

import java.util.Date;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 直播间-注册用户列表统计
 * 
 */
public class DasChartRoomRegTouristUserStatistics extends BaseModel {

	@Column(name = "rowkey")
	private String rowKey;

	// 日期
	@Column(name = "datetime")
	private Date dateTime;
	
	// 周数
	@Column(name = "weeks")
	private String weeks;

	// 课程名称
	@Column(name = "coursename")
	private String courseName;

	// 老师名称
	@Column(name = "teacherName")
	private String teacherName;

	// 房间名称
	@Column(name = "roomName")
	private String roomName;

	// vip用户-人数
	@Column(name = "vipnumbers")
	private int vipNumbers;

	// 真实A用户-人数
	@Column(name = "realanumbers")
	private int realANumbers;

	// 真实N用户-人数
	@Column(name = "realnnumbers")
	private int realNNumbers;

	// 模拟用户-人数
	@Column(name = "demonumbers")
	private int demoNumbers;

	// 注册用户-人数
	@Column(name = "registnumbers")
	private int registNumbers;

	// 游客用户-人数
	@Column(name = "touristnumbers")
	private int touristNumbers;

	// 平台类型
	@Column(name = "platformtype")
	private String platFormtype;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessplatform")
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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
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

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
