package com.gw.das.dao.website.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 流量月平均统计表
 * 
 * @author kirin.guan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasFlowStatistics {

	private String rowKey;

	// 日期(yyyy-MM-DD)
	private String dataTime;

	// 日期(yyyy-MM-DD hh)
	private String formatTime;

	private String hours;

	// 来源
	private String utmcsr;
	// 广告媒介
	private String utmcmd;
	// 系列
	private String utmccn;
	// 组
	private String utmcct;
	// 关键字
	private String utmctr;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;

	// 标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	private String platformType;

	private int advisoryCountQQ = 0;

	private int advisoryCountLIVE800 = 0;

	private int visitCount = 0;

	private int demoCount = 0;

	private int realCount = 0;

	private int depositCount = 0;

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

	// 日期(开始-查询用)
	private String dataTimeStart;

	// 日期(结束-查询用)
	private String dataTimeEnd;

	// 查询类型
	private String searchType;

	private String utmcsrAndUtmcmd;

	/** 字典折叠状态 */
	private String state;

	/** 子字典 */
	private List<DasFlowStatistics> children = new ArrayList<DasFlowStatistics>();

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

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
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

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
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

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getUtmcsrAndUtmcmd() {
		return utmcsrAndUtmcmd;
	}

	public void setUtmcsrAndUtmcmd(String utmcsrAndUtmcmd) {
		this.utmcsrAndUtmcmd = utmcsrAndUtmcmd;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<DasFlowStatistics> getChildren() {
		return children;
	}

	public void setChildren(List<DasFlowStatistics> children) {
		this.children = children;
	}

}
