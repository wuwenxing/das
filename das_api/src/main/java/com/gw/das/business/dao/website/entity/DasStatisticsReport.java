package com.gw.das.business.dao.website.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 官网统计-日、月
 */
public class DasStatisticsReport extends BaseModel
{
	
	

	@Column(name = "rowkey")
	private String rowKey;
	
	// 按月统计
	@Column(name = "dt_month")
	private String dateMonth;
	
	// 按天统计
	@Column(name = "dt_day")
	private String dateDay;
	
	// 整站访问
	@Column(name = "all_visit")
	private int allVisit;
	
	// 整站咨询
	@Column(name = "all_advisory")
	private int allAdvisory;
	
	// 整站真实
	@Column(name = "all_real")
	private int allReal;
	
	// 整站模拟
	@Column(name = "all_demo")
	private int allDemo;
	
	// 整站入金
	@Column(name = "all_deposit")
	private int allDeposit;
	
	// PC访问
	@Column(name = "dev_pc_visit")
	private int devpcVisit;
	
	// PC咨询
	@Column(name = "dev_pc_advisory")
	private int devpcAdvisory;
	
	// PC真实
	@Column(name = "dev_pc_real")
	private int devpcReal;
	
	// PC模拟
	@Column(name = "dev_pc_demo")
	private int devpcDemo;
	
	// PC入金
	@Column(name = "dev_pc_deposit")
	private int devpcDeposit;
	
	// Android访问
	@Column(name = "dev_android_visit")
	private int devAndroidVisit;
	
	// Android咨询
	@Column(name = "dev_android_advisory")
	private int devAndroidAdvisory;
	
	// Android真实
	@Column(name = "dev_android_real")
	private int devAndroidReal;
	
	// Android模拟
	@Column(name = "dev_android_demo")
	private int devAndroidDemo;
	
	// Android入金
	@Column(name = "dev_android_deposit")
	private int devAndroidDeposit;
	
	// IOS访问
	@Column(name = "dev_ios_visit")
	private int devIosVisit;
	
	// IOS咨询
	@Column(name = "dev_ios_advisory")
	private int devIosAdvisory;
	
	// IOS真实
	@Column(name = "dev_ios_real")
	private int devIosReal;
	
	// IOS模拟
	@Column(name = "dev_ios_demo")
	private int devIosDemo;
	
	// IOS入金
	@Column(name = "dev_ios_deposit")
	private int devIosDeposit;
	
	// 付费访问
	@Column(name = "channel_pay_visit")
	private int channelPayVisit;
	
	// 付费咨询
	@Column(name = "channel_pay_advisory")
	private int channelPayAdvisory;
	
	// 付费真实
	@Column(name = "channel_pay_real")
	private int channelPayReal;
	
	// 付费模拟
	@Column(name = "channel_pay_demo")
	private int channelPayDemo;
	
	// 付费入金
	@Column(name = "channel_pay_deposit")
	private int channelPayDeposit;
	
	// 免费访问
	@Column(name = "channel_free_visit")
	private int channelFreeVisit;
	
	// 免费咨询
	@Column(name = "channel_free_advisory")
	private int channelFreeAdvisory;
	
	// 免费真实
	@Column(name = "channel_free_real")
	private int channelFreeReal;
	
	// 免费模拟
	@Column(name = "channel_free_demo")
	private int channelFreeDemo;
	
	// 免费入金
	@Column(name = "channel_free_deposit")
	private int channelFreeDeposit;
	
	// 其它访问
	@Column(name = "channel_other_visit")
	private int channelOtherVisit;
	
	// 其它咨询
	@Column(name = "channel_other_advisory")
	private int channelOtherAdvisory;
	
	// 其它真实
	@Column(name = "channel_other_real")
	private int channelOtherReal;
	
	// 其它模拟
	@Column(name = "channel_other_demo")
	private int channelOtherDemo;
	
	// 其它入金
	@Column(name = "channel_other_deposit")
	private int channelOtherDeposit;

	
	public String getRowKey()
	{
		return rowKey;
	}

	
	public void setRowKey(String rowKey)
	{
		this.rowKey = rowKey;
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


	public int getAllVisit()
	{
		return allVisit;
	}

	
	public void setAllVisit(int allVisit)
	{
		this.allVisit = allVisit;
	}

	
	public int getAllAdvisory()
	{
		return allAdvisory;
	}

	
	public void setAllAdvisory(int allAdvisory)
	{
		this.allAdvisory = allAdvisory;
	}

	
	public int getAllReal()
	{
		return allReal;
	}

	
	public void setAllReal(int allReal)
	{
		this.allReal = allReal;
	}

	
	public int getAllDemo()
	{
		return allDemo;
	}

	
	public void setAllDemo(int allDemo)
	{
		this.allDemo = allDemo;
	}

	
	public int getAllDeposit()
	{
		return allDeposit;
	}

	
	public void setAllDeposit(int allDeposit)
	{
		this.allDeposit = allDeposit;
	}

	
	public int getDevpcVisit()
	{
		return devpcVisit;
	}

	
	public void setDevpcVisit(int devpcVisit)
	{
		this.devpcVisit = devpcVisit;
	}

	
	public int getDevpcAdvisory()
	{
		return devpcAdvisory;
	}

	
	public void setDevpcAdvisory(int devpcAdvisory)
	{
		this.devpcAdvisory = devpcAdvisory;
	}

	
	public int getDevpcReal()
	{
		return devpcReal;
	}

	
	public void setDevpcReal(int devpcReal)
	{
		this.devpcReal = devpcReal;
	}

	
	public int getDevpcDemo()
	{
		return devpcDemo;
	}

	
	public void setDevpcDemo(int devpcDemo)
	{
		this.devpcDemo = devpcDemo;
	}

	
	public int getDevpcDeposit()
	{
		return devpcDeposit;
	}

	
	public void setDevpcDeposit(int devpcDeposit)
	{
		this.devpcDeposit = devpcDeposit;
	}

	
	public int getDevAndroidVisit()
	{
		return devAndroidVisit;
	}

	
	public void setDevAndroidVisit(int devAndroidVisit)
	{
		this.devAndroidVisit = devAndroidVisit;
	}

	
	public int getDevAndroidAdvisory()
	{
		return devAndroidAdvisory;
	}

	
	public void setDevAndroidAdvisory(int devAndroidAdvisory)
	{
		this.devAndroidAdvisory = devAndroidAdvisory;
	}

	
	public int getDevAndroidReal()
	{
		return devAndroidReal;
	}

	
	public void setDevAndroidReal(int devAndroidReal)
	{
		this.devAndroidReal = devAndroidReal;
	}

	
	public int getDevAndroidDemo()
	{
		return devAndroidDemo;
	}

	
	public void setDevAndroidDemo(int devAndroidDemo)
	{
		this.devAndroidDemo = devAndroidDemo;
	}

	
	public int getDevAndroidDeposit()
	{
		return devAndroidDeposit;
	}

	
	public void setDevAndroidDeposit(int devAndroidDeposit)
	{
		this.devAndroidDeposit = devAndroidDeposit;
	}

	
	public int getDevIosVisit()
	{
		return devIosVisit;
	}

	
	public void setDevIosVisit(int devIosVisit)
	{
		this.devIosVisit = devIosVisit;
	}

	
	public int getDevIosAdvisory()
	{
		return devIosAdvisory;
	}

	
	public void setDevIosAdvisory(int devIosAdvisory)
	{
		this.devIosAdvisory = devIosAdvisory;
	}

	
	public int getDevIosReal()
	{
		return devIosReal;
	}

	
	public void setDevIosReal(int devIosReal)
	{
		this.devIosReal = devIosReal;
	}

	
	public int getDevIosDemo()
	{
		return devIosDemo;
	}

	
	public void setDevIosDemo(int devIosDemo)
	{
		this.devIosDemo = devIosDemo;
	}

	
	public int getDevIosDeposit()
	{
		return devIosDeposit;
	}

	
	public void setDevIosDeposit(int devIosDeposit)
	{
		this.devIosDeposit = devIosDeposit;
	}

	
	public int getChannelPayVisit()
	{
		return channelPayVisit;
	}

	
	public void setChannelPayVisit(int channelPayVisit)
	{
		this.channelPayVisit = channelPayVisit;
	}

	
	public int getChannelPayAdvisory()
	{
		return channelPayAdvisory;
	}

	
	public void setChannelPayAdvisory(int channelPayAdvisory)
	{
		this.channelPayAdvisory = channelPayAdvisory;
	}

	
	public int getChannelPayReal()
	{
		return channelPayReal;
	}

	
	public void setChannelPayReal(int channelPayReal)
	{
		this.channelPayReal = channelPayReal;
	}

	
	public int getChannelPayDemo()
	{
		return channelPayDemo;
	}

	
	public void setChannelPayDemo(int channelPayDemo)
	{
		this.channelPayDemo = channelPayDemo;
	}

	
	public int getChannelPayDeposit()
	{
		return channelPayDeposit;
	}

	
	public void setChannelPayDeposit(int channelPayDeposit)
	{
		this.channelPayDeposit = channelPayDeposit;
	}

	
	public int getChannelFreeVisit()
	{
		return channelFreeVisit;
	}

	
	public void setChannelFreeVisit(int channelFreeVisit)
	{
		this.channelFreeVisit = channelFreeVisit;
	}

	
	public int getChannelFreeAdvisory()
	{
		return channelFreeAdvisory;
	}

	
	public void setChannelFreeAdvisory(int channelFreeAdvisory)
	{
		this.channelFreeAdvisory = channelFreeAdvisory;
	}

	
	public int getChannelFreeReal()
	{
		return channelFreeReal;
	}

	
	public void setChannelFreeReal(int channelFreeReal)
	{
		this.channelFreeReal = channelFreeReal;
	}

	
	public int getChannelFreeDemo()
	{
		return channelFreeDemo;
	}

	
	public void setChannelFreeDemo(int channelFreeDemo)
	{
		this.channelFreeDemo = channelFreeDemo;
	}

	
	public int getChannelFreeDeposit()
	{
		return channelFreeDeposit;
	}

	
	public void setChannelFreeDeposit(int channelFreeDeposit)
	{
		this.channelFreeDeposit = channelFreeDeposit;
	}

	
	public int getChannelOtherVisit()
	{
		return channelOtherVisit;
	}

	
	public void setChannelOtherVisit(int channelOtherVisit)
	{
		this.channelOtherVisit = channelOtherVisit;
	}

	
	public int getChannelOtherAdvisory()
	{
		return channelOtherAdvisory;
	}

	
	public void setChannelOtherAdvisory(int channelOtherAdvisory)
	{
		this.channelOtherAdvisory = channelOtherAdvisory;
	}

	
	public int getChannelOtherReal()
	{
		return channelOtherReal;
	}

	
	public void setChannelOtherReal(int channelOtherReal)
	{
		this.channelOtherReal = channelOtherReal;
	}

	
	public int getChannelOtherDemo()
	{
		return channelOtherDemo;
	}

	
	public void setChannelOtherDemo(int channelOtherDemo)
	{
		this.channelOtherDemo = channelOtherDemo;
	}

	
	public int getChannelOtherDeposit()
	{
		return channelOtherDeposit;
	}

	
	public void setChannelOtherDeposit(int channelOtherDeposit)
	{
		this.channelOtherDeposit = channelOtherDeposit;
	}
	
	
	
	
}
