package com.gw.das.dao.room.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 直播间-登录次/人数、访问次/人数、公聊次/人数、私聊次/人数 根据用户级别进行统计
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartRoomUserTypeStatistics {

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

	// vip用户-次数
	private int vipCounts;

	// vip用户-人数
	private int vipNumbers;

	// 真实A用户-次数
	private int realACounts;

	// 真实A用户-人数
	private int realANumbers;

	// 真实N用户-次数
	private int realNCounts;

	// 真实N用户-人数
	private int realNNumbers;

	// 注册用户-次数
	private int registCounts;

	// 注册用户-人数
	private int registNumbers;

	// 游客用户-次数
	private int touristCounts;

	// 游客用户-人数
	private int touristNumbers;

	// 模拟用户-次数
	private int demoCounts;

	// 模拟用户-人数
	private int demoNumbers;

	// 分析师-次数
	private int analystCounts;

	// 分析师-人数
	private int analystNumbers;

	// 管理员-次数
	private int adminCounts;

	// 管理员-人数
	private int adminNumbers;

	// 客服-次数
	private int cServiceCounts;

	// 客服-人数
	private int cServiceNumbers;

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

	public int getVipCounts() {
		return vipCounts;
	}

	public void setVipCounts(int vipCounts) {
		this.vipCounts = vipCounts;
	}

	public int getVipNumbers() {
		return vipNumbers;
	}

	public void setVipNumbers(int vipNumbers) {
		this.vipNumbers = vipNumbers;
	}

	public int getRealACounts() {
		return realACounts;
	}

	public void setRealACounts(int realACounts) {
		this.realACounts = realACounts;
	}

	public int getRealANumbers() {
		return realANumbers;
	}

	public void setRealANumbers(int realANumbers) {
		this.realANumbers = realANumbers;
	}

	public int getRealNCounts() {
		return realNCounts;
	}

	public void setRealNCounts(int realNCounts) {
		this.realNCounts = realNCounts;
	}

	public int getRealNNumbers() {
		return realNNumbers;
	}

	public void setRealNNumbers(int realNNumbers) {
		this.realNNumbers = realNNumbers;
	}

	public int getRegistCounts() {
		return registCounts;
	}

	public void setRegistCounts(int registCounts) {
		this.registCounts = registCounts;
	}

	public int getRegistNumbers() {
		return registNumbers;
	}

	public void setRegistNumbers(int registNumbers) {
		this.registNumbers = registNumbers;
	}

	public int getTouristCounts() {
		return touristCounts;
	}

	public void setTouristCounts(int touristCounts) {
		this.touristCounts = touristCounts;
	}

	public int getTouristNumbers() {
		return touristNumbers;
	}

	public void setTouristNumbers(int touristNumbers) {
		this.touristNumbers = touristNumbers;
	}

	public int getDemoCounts() {
		return demoCounts;
	}

	public void setDemoCounts(int demoCounts) {
		this.demoCounts = demoCounts;
	}

	public int getDemoNumbers() {
		return demoNumbers;
	}

	public void setDemoNumbers(int demoNumbers) {
		this.demoNumbers = demoNumbers;
	}

	public int getAnalystCounts() {
		return analystCounts;
	}

	public void setAnalystCounts(int analystCounts) {
		this.analystCounts = analystCounts;
	}

	public int getAnalystNumbers() {
		return analystNumbers;
	}

	public void setAnalystNumbers(int analystNumbers) {
		this.analystNumbers = analystNumbers;
	}

	public int getAdminCounts() {
		return adminCounts;
	}

	public void setAdminCounts(int adminCounts) {
		this.adminCounts = adminCounts;
	}

	public int getAdminNumbers() {
		return adminNumbers;
	}

	public void setAdminNumbers(int adminNumbers) {
		this.adminNumbers = adminNumbers;
	}

	public int getcServiceCounts() {
		return cServiceCounts;
	}

	public void setcServiceCounts(int cServiceCounts) {
		this.cServiceCounts = cServiceCounts;
	}

	public int getcServiceNumbers() {
		return cServiceNumbers;
	}

	public void setcServiceNumbers(int cServiceNumbers) {
		this.cServiceNumbers = cServiceNumbers;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
