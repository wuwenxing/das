package com.gw.das.business.dao.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseSearchModel;

/**
 * 页面访问明细表
 * @author kirin.guan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasFlowDetailUrl extends BaseSearchModel{
	
	@Column(name = "rowkey")
	private String rowKey;
	
	//详细汇总表ID
	@Column(name = "flowDetailId")
	private String flowDetailId;
	
	//访问URL
	@Column(name = "flowDetailUrl")
	private String flowDetailUrl;
	
	//标识是手机还是pc访问，0和空代表pc访问，1代表手机访问（默认为0）
	@Column(name = "platformType")
    private String platformType;
    
    //标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessPlatform")
	private String businessPlatform;
    
    //广告媒介
	@Column(name = "utmcmd")
    private String utmcmd;
      
    //来源
	@Column(name = "utmcsr")
    private String utmcsr;
  	
	//访问时间
	@Column(name = "visitTime")
	private String visitTime;
	
	//页面查询条件
	private String visitTimeStart;
	
	//页面查询条件
	private String visitTimeEnd;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getFlowDetailId() {
		return flowDetailId;
	}

	public void setFlowDetailId(String flowDetailId) {
		this.flowDetailId = flowDetailId;
	}

	public String getFlowDetailUrl() {
		return flowDetailUrl;
	}

	public void setFlowDetailUrl(String flowDetailUrl) {
		this.flowDetailUrl = flowDetailUrl;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getUtmcmd() {
		return utmcmd;
	}

	public void setUtmcmd(String utmcmd) {
		this.utmcmd = utmcmd;
	}

	public String getUtmcsr() {
		return utmcsr;
	}

	public void setUtmcsr(String utmcsr) {
		this.utmcsr = utmcsr;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getVisitTimeStart() {
		return visitTimeStart;
	}

	public void setVisitTimeStart(String visitTimeStart) {
		this.visitTimeStart = visitTimeStart;
	}

	public String getVisitTimeEnd() {
		return visitTimeEnd;
	}

	public void setVisitTimeEnd(String visitTimeEnd) {
		this.visitTimeEnd = visitTimeEnd;
	}
	
}
