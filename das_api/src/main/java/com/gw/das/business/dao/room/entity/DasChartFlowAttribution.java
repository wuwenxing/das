package com.gw.das.business.dao.room.entity;

import java.util.Date;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.common.orm.UnColumn;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 归因统计表
 */
public class DasChartFlowAttribution extends BaseModel {

	@Column(name = "rowkey")
	private String rowKey;

	// 日期(yyyy-MM-dd)分区字段
	@Column(name = "datatime")
	private String dataTime;

	// 广告来源
	@Column(name = "utmcsr")
	private String utmcsr;
	
	// 广告媒介
	@Column(name = "utmcmd")
	private String utmcmd;
	
	// 模拟开户总数
	@Column(name = "demoCount")
	private Double demoCount;
	
	// 真实开户总数
	@Column(name = "realCount")
	private Double realCount;
	
	//标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessPlatform")
	private String businessPlatform;

	//标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	@Column(name = "devicetype")
	private String devicetype;
	
	//开始时间  查询用
	@UnColumn
	private Date dataTimeStart;
	
	//结束时间  查询用
	@UnColumn
	private Date dataTimeEnd;
	
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

	public Double getDemoCount() {
		return demoCount;
	}

	public void setDemoCount(Double demoCount) {
		this.demoCount = demoCount;
	}

	public Double getRealCount() {
		return realCount;
	}

	public void setRealCount(Double realCount) {
		this.realCount = realCount;
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

	public Date getDataTimeStart() {
		return dataTimeStart;
	}

	public void setDataTimeStart(Date dataTimeStart) {
		this.dataTimeStart = dataTimeStart;
	}

	public Date getDataTimeEnd() {
		return dataTimeEnd;
	}

	public void setDataTimeEnd(Date dataTimeEnd) {
		this.dataTimeEnd = dataTimeEnd;
	}

	public Integer getDevicecount() {
		return devicecount;
	}

	public void setDevicecount(Integer devicecount) {
		this.devicecount = devicecount;
	}
	
	
}
