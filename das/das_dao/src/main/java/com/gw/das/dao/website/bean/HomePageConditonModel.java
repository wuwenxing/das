
package com.gw.das.dao.website.bean;

/**
 * 首页图表条件model
 */
public class HomePageConditonModel {
    
	private String startTime;//查询条件：起始时间
	private String endTime;//查询条件：结束时间
	private String dataType;//数据类型（0:访问数据，1：咨询数据，2：模拟开户数据，3：真实开户数据，4：首次入金数据）
//	private String countType="0";//统计类型（默认0，0：按小时统计，1：按日统计，2：按月统计，3：按年统计...）
	private Integer businessPlatform = 1;//标识业务平台访问，1:外汇网站 2:贵金属网站
	private String utmcsr;//过滤条件：来源
	private String utmcmd;//过滤条件：媒介
	
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getBusinessPlatform() {
		return businessPlatform;
	}
	public void setBusinessPlatform(Integer businessPlatform) {
		this.businessPlatform = businessPlatform;
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
	
}
