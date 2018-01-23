package com.gw.das.dao.website.bean;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 日报渠道-日、月
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasStatisticsDailyChannel
{	

	@Column(name = "rowkey")
	private String rowKey;
	
	@Column(name = "businessplatform")
	private String businessplatform;
	
	@Column(name = "platformtype")
	private String platformtype;	
	
	// 按月统计
	@Column(name = "dt_month")
	private String dateMonth;
	
	// 按天统计
	@Column(name = "dt_day")
	private String dateDay;
	
	
	@Column(name = "partitionfield")
	private String partitionfield;
	
	@Column(name = "channel")
	private String channel;
	
	// 整站访问
	@Column(name = "visit")
	private double visit;
	
	// 整站咨询
	@Column(name = "advisory")
	private double advisory;
	
	// 整站真实
	@Column(name = "real")
	private double real;
	
	// 整站模拟
	@Column(name = "demo")
	private double demo;
	
	// 整站入金
	@Column(name = "deposit")
	private double deposit;
	
	// PC访问
	@Column(name = "on_w_visit")
	private String onwvisit;
	
	// PC咨询
	@Column(name = "on_w_advisory")
	private String onwadvisory;
	
	// PC真实
	@Column(name = "on_w_real")
	private String onwreal;
	
	// PC模拟
	@Column(name = "on_w_demo")
	private String onwdemo;
	
	// PC入金
	@Column(name = "on_w_deposit")
	private String onwdeposit;
	
	// Android访问
	@Column(name = "on_m_visit")
	private String onmvisit;
	
	// Android咨询
	@Column(name = "on_m_advisory")
	private String onmadvisory;
	
	// Android真实
	@Column(name = "on_m_real")
	private String onmreal;
	
	// Android模拟
	@Column(name = "on_m_demo")
	private String onmdemo;
	
	// Android入金
	@Column(name = "on_m_deposit")
	private String onmdeposit;

	
	public String getRowKey()
	{
		return rowKey;
	}

	
	public void setRowKey(String rowKey)
	{
		this.rowKey = rowKey;
	}

	
	public String getBusinessplatform()
	{
		return businessplatform;
	}

	
	public void setBusinessplatform(String businessplatform)
	{
		this.businessplatform = businessplatform;
	}

	
	public String getPlatformtype()
	{
		return platformtype;
	}

	
	public void setPlatformtype(String platformtype)
	{
		this.platformtype = platformtype;
	}

	
	public String getDateMonth()
	{
		return dateMonth;
	}

	
	public void setDateMonth(String dateMonth)
	{
		this.dateMonth = dateMonth;
	}

	
	public String getDateDay()
	{
		return dateDay;
	}

	
	public void setDateDay(String dateDay)
	{
		this.dateDay = dateDay;
	}

	
	public String getPartitionfield()
	{
		return partitionfield;
	}

	
	public void setPartitionfield(String partitionfield)
	{
		this.partitionfield = partitionfield;
	}

	
	public String getChannel()
	{
		return channel;
	}

	
	public void setChannel(String channel)
	{
		this.channel = channel;
	}

	
	public double getVisit()
	{
		return visit;
	}

	
	public void setVisit(double visit)
	{
		this.visit = visit;
	}

	
	public double getAdvisory()
	{
		return advisory;
	}

	
	public void setAdvisory(double advisory)
	{
		this.advisory = advisory;
	}

	
	public double getReal()
	{
		return real;
	}

	
	public void setReal(double real)
	{
		this.real = real;
	}

	
	public double getDemo()
	{
		return demo;
	}

	
	public void setDemo(double demo)
	{
		this.demo = demo;
	}

	
	public double getDeposit()
	{
		return deposit;
	}

	
	public void setDeposit(double deposit)
	{
		this.deposit = deposit;
	}

	
	public String getOnwvisit()
	{
		return onwvisit;
	}

	
	public void setOnwvisit(String onwvisit)
	{
		this.onwvisit = onwvisit;
	}

	
	public String getOnwadvisory()
	{
		return onwadvisory;
	}

	
	public void setOnwadvisory(String onwadvisory)
	{
		this.onwadvisory = onwadvisory;
	}

	
	public String getOnwreal()
	{
		return onwreal;
	}

	
	public void setOnwreal(String onwreal)
	{
		this.onwreal = onwreal;
	}

	
	public String getOnwdemo()
	{
		return onwdemo;
	}

	
	public void setOnwdemo(String onwdemo)
	{
		this.onwdemo = onwdemo;
	}

	
	public String getOnwdeposit()
	{
		return onwdeposit;
	}

	
	public void setOnwdeposit(String onwdeposit)
	{
		this.onwdeposit = onwdeposit;
	}

	
	public String getOnmvisit()
	{
		return onmvisit;
	}

	
	public void setOnmvisit(String onmvisit)
	{
		this.onmvisit = onmvisit;
	}

	
	public String getOnmadvisory()
	{
		return onmadvisory;
	}

	
	public void setOnmadvisory(String onmadvisory)
	{
		this.onmadvisory = onmadvisory;
	}

	
	public String getOnmreal()
	{
		return onmreal;
	}

	
	public void setOnmreal(String onmreal)
	{
		this.onmreal = onmreal;
	}

	
	public String getOnmdemo()
	{
		return onmdemo;
	}

	
	public void setOnmdemo(String onmdemo)
	{
		this.onmdemo = onmdemo;
	}

	
	public String getOnmdeposit()
	{
		return onmdeposit;
	}

	
	public void setOnmdeposit(String onmdeposit)
	{
		this.onmdeposit = onmdeposit;
	}
	
	
}
