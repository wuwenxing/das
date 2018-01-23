package com.gw.das.business.dao.room.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 流量月平均统计表
 * 
 */
public class DasChartFlowStatistics extends BaseModel {

	@Column(name = "rowkey")
	private String rowKey;

	// 日期(yyyy-MM-DD)
	@Column(name = "datatime")
	private String dataTime;

	// 日期(yyyy-MM-DD HH)
	@Column(name = "formattime")
	private String formatTime;

	// 小时数
	@Column(name = "hours")
	private String hours;

	// 来源
	@Column(name = "utmcsr")
	private String utmcsr;
	// 媒介
	@Column(name = "utmcmd")
	private String utmcmd;
	// 系列
	@Column(name = "utmccn")
	private String utmccn;
	// 组
	@Column(name = "utmcct")
	private String utmcct;
	// 关键字
	@Column(name = "utmctr")
	private String utmctr;
	
	@Column(name = "advisorycountqq")
	private int advisoryCountQQ = 0;

	@Column(name = "advisorycountlive800")
	private int advisoryCountLIVE800 = 0;

	@Column(name = "visitcount")
	private int visitCount = 0;

	@Column(name = "democount")
	private int demoCount = 0;

	@Column(name = "realcount")
	private int realCount = 0;

	@Column(name = "depositcount")
	private int depositCount = 0;

	// 标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	@Column(name = "devicetype")
	private String devicetype;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessplatform")
	private String businessPlatform;

	// utmcsr勾选标示
	private boolean utmcsrChecked;
	// utmcmd勾选标示
	private boolean utmcmdChecked;
	// 系列复选框勾选标示
	private boolean utmccnChecked;
	// 组复选框勾选标示
	private boolean utmcctChecked;
	// 关键字复选框勾选标示
	private boolean utmctrChecked;

	// utmcsr
	private String[] utmcsrList;
	// utmcmd
	private String[] utmcmdList;

	// 日期(开始-查询用)
	private String dataTimeStart;

	// 日期(结束-查询用)
	private String dataTimeEnd;

	// 排序字段(查询用)
	private String sortName = "dataTime";

	// 排序字段(查询用)
	private String sortDirection = "desc";

	// 查询类型(behavior\time\media)
	private String searchType;

	// 开始环比时间
	private String startTimeCompare;

	// 结束环比时间
	private String endTimeCompare;
	
	//设备次数
	@Column(name = "devicecount")
	private Integer devicecount = 0;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
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

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public int getAdvisoryCountQQ() {
		return advisoryCountQQ;
	}

	public void setAdvisoryCountQQ(int advisoryCountQQ) {
		this.advisoryCountQQ = advisoryCountQQ;
	}

	public int getAdvisoryCountLIVE800() {
		return advisoryCountLIVE800;
	}

	public void setAdvisoryCountLIVE800(int advisoryCountLIVE800) {
		this.advisoryCountLIVE800 = advisoryCountLIVE800;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public int getDemoCount() {
		return demoCount;
	}

	public void setDemoCount(int demoCount) {
		this.demoCount = demoCount;
	}

	public int getRealCount() {
		return realCount;
	}

	public void setRealCount(int realCount) {
		this.realCount = realCount;
	}

	public int getDepositCount() {
		return depositCount;
	}

	public void setDepositCount(int depositCount) {
		this.depositCount = depositCount;
	}

	public boolean isUtmcsrChecked() {
		return utmcsrChecked;
	}

	public void setUtmcsrChecked(boolean utmcsrChecked) {
		this.utmcsrChecked = utmcsrChecked;
	}

	public boolean isUtmcmdChecked() {
		return utmcmdChecked;
	}

	public void setUtmcmdChecked(boolean utmcmdChecked) {
		this.utmcmdChecked = utmcmdChecked;
	}

	public boolean isUtmccnChecked() {
		return utmccnChecked;
	}

	public void setUtmccnChecked(boolean utmccnChecked) {
		this.utmccnChecked = utmccnChecked;
	}

	public boolean isUtmcctChecked() {
		return utmcctChecked;
	}

	public void setUtmcctChecked(boolean utmcctChecked) {
		this.utmcctChecked = utmcctChecked;
	}

	public boolean isUtmctrChecked() {
		return utmctrChecked;
	}

	public void setUtmctrChecked(boolean utmctrChecked) {
		this.utmctrChecked = utmctrChecked;
	}

	public String getDataTimeStart() {
		return dataTimeStart;
	}

	public void setDataTimeStart(String dataTimeStart) {
		this.dataTimeStart = dataTimeStart;
	}

	public String getDataTimeEnd() {
		return dataTimeEnd;
	}

	public void setDataTimeEnd(String dataTimeEnd) {
		this.dataTimeEnd = dataTimeEnd;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

	public String[] getUtmcsrList() {
		return utmcsrList;
	}

	public void setUtmcsrList(String[] utmcsrList) {
		this.utmcsrList = utmcsrList;
	}

	public String[] getUtmcmdList() {
		return utmcmdList;
	}

	public void setUtmcmdList(String[] utmcmdList) {
		this.utmcmdList = utmcmdList;
	}

	public String getStartTimeCompare() {
		return startTimeCompare;
	}

	public void setStartTimeCompare(String startTimeCompare) {
		this.startTimeCompare = startTimeCompare;
	}

	public String getEndTimeCompare() {
		return endTimeCompare;
	}

	public void setEndTimeCompare(String endTimeCompare) {
		this.endTimeCompare = endTimeCompare;
	}

	public Integer getDevicecount() {
		return devicecount;
	}

	public void setDevicecount(Integer devicecount) {
		this.devicecount = devicecount;
	}
	
	

}
