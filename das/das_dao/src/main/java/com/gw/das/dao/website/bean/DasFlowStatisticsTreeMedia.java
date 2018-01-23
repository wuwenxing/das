package com.gw.das.dao.website.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author darren
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasFlowStatisticsTreeMedia{
	
	private int id;
	
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
	
	//标识业务平台访问，1:外汇网站 2:贵金属网站
	private String businessPlatform;
	
	//标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	private String platformType;
	
	private String advisoryCountQQ ;

	private String advisoryCountLIVE800 ;
	
	private String visitCount;
	
	private String demoCount ;
	
	private String realCount ;
	
	private String depositCount;
	 
	// utmcsr勾选标示
	private boolean utmcsrChecked;
	// utmcmd勾选标示
	private boolean utmcmdChecked;
		
	// 日期(开始-查询用)
	private String dataTimeStart;
    
	// 日期(结束-查询用)
	private String dataTimeEnd;
	
	// 查询类型
	private String searchType;
	
	private String utmcsrAndUtmcmd;
	
	private List<DasFlowStatisticsTreeMedia> children;
	
	/** 字典折叠状态 */
	private String state;
	
	
	public int getId()
	{
		return id;
	}

	
	public void setId(int id)
	{
		this.id = id;
	}

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

	public String getAdvisoryCountQQ() {
		return advisoryCountQQ;
	}

	public void setAdvisoryCountQQ(String advisoryCountQQ) {
		this.advisoryCountQQ = advisoryCountQQ;
	}

	public String getAdvisoryCountLIVE800() {
		return advisoryCountLIVE800;
	}

	public void setAdvisoryCountLIVE800(String advisoryCountLIVE800) {
		this.advisoryCountLIVE800 = advisoryCountLIVE800;
	}

	public String getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(String visitCount) {
		this.visitCount = visitCount;
	}

	public String getDemoCount() {
		return demoCount;
	}

	public void setDemoCount(String demoCount) {
		this.demoCount = demoCount;
	}

	public String getRealCount() {
		return realCount;
	}

	public void setRealCount(String realCount) {
		this.realCount = realCount;
	}

	public String getDepositCount() {
		return depositCount;
	}

	public void setDepositCount(String depositCount) {
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

	
	public String getUtmcsrAndUtmcmd()
	{
		return utmcsrAndUtmcmd;
	}

	
	public void setUtmcsrAndUtmcmd(String utmcsrAndUtmcmd)
	{
		this.utmcsrAndUtmcmd = utmcsrAndUtmcmd;
	}

	
	public String getState()
	{
		return state;
	}

	
	public void setState(String state)
	{
		this.state = state;
	}

	
	public List<DasFlowStatisticsTreeMedia> getChildren()
	{
		return children;
	}

	
	public void setChildren(List<DasFlowStatisticsTreeMedia> children)
	{
		this.children = children;
	}

	@Override
	public int hashCode()
	{
		return (utmcsr+utmcmd).hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		DasFlowStatisticsTreeMedia mediaBean = (DasFlowStatisticsTreeMedia) obj;
		return utmcsr.equals(mediaBean.getUtmcsr()) && utmcmd.equals(mediaBean.getUtmcmd());
	}
	

}
