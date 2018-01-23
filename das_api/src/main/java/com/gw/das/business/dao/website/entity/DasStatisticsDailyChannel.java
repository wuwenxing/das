package com.gw.das.business.dao.website.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 日报渠道-日、月
 */
public class DasStatisticsDailyChannel extends BaseModel
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
	private double onwvisit;
	
	// PC咨询
	@Column(name = "on_w_advisory")
	private double onwadvisory;
	
	// PC真实
	@Column(name = "on_w_real")
	private double onwreal;
	
	// PC模拟
	@Column(name = "on_w_demo")
	private double onwdemo;
	
	// PC入金
	@Column(name = "on_w_deposit")
	private double onwdeposit;
	
	// Android访问
	@Column(name = "on_m_visit")
	private double onmvisit;
	
	// Android咨询
	@Column(name = "on_m_advisory")
	private double onmadvisory;
	
	// Android真实
	@Column(name = "on_m_real")
	private double onmreal;
	
	// Android模拟
	@Column(name = "on_m_demo")
	private double onmdemo;
	
	// Android入金
	@Column(name = "on_m_deposit")
	private double onmdeposit;

	
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

	
	public double getOnwvisit()
	{
		return onwvisit;
	}

	
	public void setOnwvisit(double onwvisit)
	{
		this.onwvisit = onwvisit;
	}

	
	public double getOnwadvisory()
	{
		return onwadvisory;
	}

	
	public void setOnwadvisory(double onwadvisory)
	{
		this.onwadvisory = onwadvisory;
	}

	
	public double getOnwreal()
	{
		return onwreal;
	}

	
	public void setOnwreal(double onwreal)
	{
		this.onwreal = onwreal;
	}

	
	public double getOnwdemo()
	{
		return onwdemo;
	}

	
	public void setOnwdemo(double onwdemo)
	{
		this.onwdemo = onwdemo;
	}

	
	public double getOnwdeposit()
	{
		return onwdeposit;
	}

	
	public void setOnwdeposit(double onwdeposit)
	{
		this.onwdeposit = onwdeposit;
	}

	
	public double getOnmvisit()
	{
		return onmvisit;
	}

	
	public void setOnmvisit(double onmvisit)
	{
		this.onmvisit = onmvisit;
	}

	
	public double getOnmadvisory()
	{
		return onmadvisory;
	}

	
	public void setOnmadvisory(double onmadvisory)
	{
		this.onmadvisory = onmadvisory;
	}

	
	public double getOnmreal()
	{
		return onmreal;
	}

	
	public void setOnmreal(double onmreal)
	{
		this.onmreal = onmreal;
	}

	
	public double getOnmdemo()
	{
		return onmdemo;
	}

	
	public void setOnmdemo(double onmdemo)
	{
		this.onmdemo = onmdemo;
	}

	
	public double getOnmdeposit()
	{
		return onmdeposit;
	}

	
	public void setOnmdeposit(double onmdeposit)
	{
		this.onmdeposit = onmdeposit;
	}
	
	
	
	
}
