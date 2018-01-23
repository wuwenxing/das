package com.gw.das.dao.website.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.dao.base.BaseSearchModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DasStatisticsReportSearchBean  extends BaseSearchModel{

	// 报表类型: (days:日统计、months:周统计)
	private String reportType;

	// 日期-开始日期
	private String startTime;

	// 日期-结束日期
	private String endTime;
	
	// 访问客户端
	private String platformType;
	
	private String channel;// 渠道名称
	
	private int channeltype;

	
	public String getReportType()
	{
		return reportType;
	}

	
	public void setReportType(String reportType)
	{
		this.reportType = reportType;
	}

	
	public String getStartTime()
	{
		return startTime;
	}

	
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	
	public String getEndTime()
	{
		return endTime;
	}

	
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}


	
	public String getPlatformType()
	{
		return platformType;
	}


	public void setPlatformType(String platformType)
	{
		this.platformType = platformType;
	}


	
	public String getChannel()
	{
		return channel;
	}


	
	public void setChannel(String channel)
	{
		this.channel = channel;
	}


	
	public int getChanneltype()
	{
		return channeltype;
	}


	
	public void setChanneltype(int channeltype)
	{
		this.channeltype = channeltype;
	}


	
	

}
