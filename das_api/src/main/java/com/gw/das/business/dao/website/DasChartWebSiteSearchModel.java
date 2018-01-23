package com.gw.das.business.dao.website;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.dao.base.BaseSearchModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DasChartWebSiteSearchModel extends BaseSearchModel {

	// 广告来源
	private String utmcsr;
	
	// 广告媒介
	private String utmcmd;

	//开始时间  查询用
	private Date dataTimeStart;
	
	//结束时间  查询用
	private Date dataTimeEnd;

	// 访问客户端
	private String platformType;

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

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	
}
