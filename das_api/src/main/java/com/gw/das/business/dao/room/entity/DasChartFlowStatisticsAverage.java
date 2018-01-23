package com.gw.das.business.dao.room.entity;

import java.math.BigDecimal;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;


/**
 * 开户数平均统计
 *
 */
public class DasChartFlowStatisticsAverage extends BaseModel{
	//日期
	@Column(name = "dataTime")
    private String dataTime;
	
    // 广告媒介
	@Column(name = "utmcmd")
    private String utmcmd;
    
    //开户数
	@Column(name = "accountCount")
  	private Integer accountCount;
  	
  	//上月平均数
	@Column(name = "lastMonthAvgCount")
  	private Double lastMonthAvgCount = 0.0000d;
  	
   //标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessPlatform")
   private String businessPlatform;

	//外汇行为类型 3，4，5
	@Column(name = "behaviorType")
    private String behaviorType;
	
	// 访问客户端
	private String devicetype;
	
	//设备次数
	@Column(name = "deviceAvgCount ")
	private Integer deviceAvgCount =0;
	   
	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getUtmcmd() {
		return utmcmd;
	}

	public void setUtmcmd(String utmcmd) {
		this.utmcmd = utmcmd;
	}

	public Integer getAccountCount() {
		return accountCount;
	}

	public void setAccountCount(Integer accountCount) {
		this.accountCount = accountCount;
	}

	public Double getLastMonthAvgCount() {
		return lastMonthAvgCount;
	}

	public void setLastMonthAvgCount(Double lastMonthAvgCount) {
		lastMonthAvgCount = new BigDecimal(lastMonthAvgCount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		this.lastMonthAvgCount = lastMonthAvgCount;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public Integer getDeviceAvgCount() {
		return deviceAvgCount;
	}

	public void setDeviceAvgCount(Integer deviceAvgCount) {
		this.deviceAvgCount = deviceAvgCount;
	}

    
	
	
	
}
