package com.gw.das.business.dao.appDataAnalysis.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * App数据分析详情报表VO
 * 
 * @author Darren
 * @since 2017-03-22
 */
public class AppDataAnalysisDetailsVO extends BaseModel{

	@Column(name = "rowkey")
	private String rowKey;
	
	/* 月 */
	@Column(name = "dt_month")
	private String dtMonth;
	
	/* 分区字段 */
	@Column(name = "partitionfield")
	private String partitionfield;
	
	/* 渠道 */
	@Column(name = "channel")
	private String channel;
	
	/* 系统类型 */
	@Column(name = "devicetype")
	private String devicetype;
	
	/* 活跃用户 */
	@Column(name = "activeuser")
	private Double activeuser;
	
	/* 新用户 */
	@Column(name = "newuser")
	private Double newuser;
	
	/* 老客户-D */
	@Column(name = "olduser_demo")
	private Double olduserDemo;
	
	/* 老客户-R */
	@Column(name = "olduser_real")
	private Double olduserReal;
	
	/* 老客户-N */
	@Column(name = "olduser_activate")
	private Double olduserActivate;
	
	/* 新开户 */
	@Column(name = "demo_account")
	private Double demoAccount;
	
	/* 新客户-登录 */
	@Column(name = "demo_newuser_login")
	private Double demoNewuserLogin;
	
	/* 新客户-交易 */
	@Column(name = "demo_newuser_trade")
	private Double demoNewuserTrade;
	
	/* 老客户-登录*/
	@Column(name = "demo_olduser_login")
	private Double demoOlduserLogin;
	
	/* 老客户-交易 */
	@Column(name = "demo_olduser_trade")
	private Double demoOlduserTrade;
	
	/* 登录总人数 */
	@Column(name = "demo_all_login")
	private Double demoAllLogin;
	
	/* 交易总人数*/
	@Column(name = "demo_all_trade")
	private Double demoAllTrade;
	
	/* 新客登录率 */
	@Column(name = "demo_newuser_login_rate")
	private Double demoNewuserLoginRate;
	
	/* 新客交易率 */
	@Column(name = "demo_newuser_trade_rate")
	private Double demoNewuserTradeRate;
	
	/* 老客交易率 */
	@Column(name = "demo_olduser_trade_rate")
	private Double demoOlduserTradeRate;
	
	/* 总交易率 */
	@Column(name = "demo_all_trade_rate")
	private Double demoAllTradeRate;
	
	/* 新开户 */
	@Column(name = "real_account")
	private Double realAccount;
	
	/* 新客登录 */
	@Column(name = "real_newuser_login")
	private Double realNewuserLogin;
	
	/* 新客入金 */
	@Column(name = "real_newuser_deposit")
	private Double realNewuserDeposit;
	
	/* 新客户交易 */
	@Column(name = "real_newuser_trade")
	private Double realNewuserTrade;
	
	/* 新客入金率*/
	@Column(name = "real_newuser_deposit_rate")
	private Double realNewuserDepositRate;

	/* 新客交易率  */
	@Column(name = "real_newuser_trade_rate")
	private Double realNewuserTradeRate;
	
	/*   新客登录率*/
	@Column(name = "real_newuser_login_rate")
	private Double realNewuserLoginRate;
	
	/* 业务类型 */
	@Column(name = "businessplatform")
	private String businessplatform;
	
	

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getDtMonth() {
		return dtMonth;
	}

	public void setDtMonth(String dtMonth) {
		this.dtMonth = dtMonth;
	}


	public String getPartitionfield() {
		return partitionfield;
	}

	public void setPartitionfield(String partitionfield) {
		this.partitionfield = partitionfield;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}



	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public Double getActiveuser() {
		return activeuser;
	}

	public void setActiveuser(Double activeuser) {
		this.activeuser = activeuser;
	}

	public Double getNewuser() {
		return newuser;
	}

	public void setNewuser(Double newuser) {
		this.newuser = newuser;
	}

	public Double getOlduserDemo() {
		return olduserDemo;
	}

	public void setOlduserDemo(Double olduserDemo) {
		this.olduserDemo = olduserDemo;
	}

	public Double getOlduserReal() {
		return olduserReal;
	}

	public void setOlduserReal(Double olduserReal) {
		this.olduserReal = olduserReal;
	}

	public Double getOlduserActivate() {
		return olduserActivate;
	}

	public void setOlduserActivate(Double olduserActivate) {
		this.olduserActivate = olduserActivate;
	}

	public Double getDemoAccount() {
		return demoAccount;
	}

	public void setDemoAccount(Double demoAccount) {
		this.demoAccount = demoAccount;
	}

	public Double getDemoNewuserLogin() {
		return demoNewuserLogin;
	}

	public void setDemoNewuserLogin(Double demoNewuserLogin) {
		this.demoNewuserLogin = demoNewuserLogin;
	}

	public Double getDemoNewuserTrade() {
		return demoNewuserTrade;
	}

	public void setDemoNewuserTrade(Double demoNewuserTrade) {
		this.demoNewuserTrade = demoNewuserTrade;
	}

	public Double getDemoOlduserLogin() {
		return demoOlduserLogin;
	}

	public void setDemoOlduserLogin(Double demoOlduserLogin) {
		this.demoOlduserLogin = demoOlduserLogin;
	}

	public Double getDemoOlduserTrade() {
		return demoOlduserTrade;
	}

	public void setDemoOlduserTrade(Double demoOlduserTrade) {
		this.demoOlduserTrade = demoOlduserTrade;
	}

	public Double getDemoAllLogin() {
		return demoAllLogin;
	}

	public void setDemoAllLogin(Double demoAllLogin) {
		this.demoAllLogin = demoAllLogin;
	}

	public Double getDemoAllTrade() {
		return demoAllTrade;
	}

	public void setDemoAllTrade(Double demoAllTrade) {
		this.demoAllTrade = demoAllTrade;
	}

	public Double getDemoNewuserLoginRate() {
		return demoNewuserLoginRate;
	}

	public void setDemoNewuserLoginRate(Double demoNewuserLoginRate) {
		this.demoNewuserLoginRate = demoNewuserLoginRate;
	}

	public Double getDemoNewuserTradeRate() {
		return demoNewuserTradeRate;
	}

	public void setDemoNewuserTradeRate(Double demoNewuserTradeRate) {
		this.demoNewuserTradeRate = demoNewuserTradeRate;
	}

	public Double getDemoOlduserTradeRate() {
		return demoOlduserTradeRate;
	}

	public void setDemoOlduserTradeRate(Double demoOlduserTradeRate) {
		this.demoOlduserTradeRate = demoOlduserTradeRate;
	}

	public Double getDemoAllTradeRate() {
		return demoAllTradeRate;
	}

	public void setDemoAllTradeRate(Double demoAllTradeRate) {
		this.demoAllTradeRate = demoAllTradeRate;
	}

	public Double getRealAccount() {
		return realAccount;
	}

	public void setRealAccount(Double realAccount) {
		this.realAccount = realAccount;
	}

	public Double getRealNewuserLogin() {
		return realNewuserLogin;
	}

	public void setRealNewuserLogin(Double realNewuserLogin) {
		this.realNewuserLogin = realNewuserLogin;
	}

	public Double getRealNewuserDeposit() {
		return realNewuserDeposit;
	}

	public void setRealNewuserDeposit(Double realNewuserDeposit) {
		this.realNewuserDeposit = realNewuserDeposit;
	}

	public Double getRealNewuserTrade() {
		return realNewuserTrade;
	}

	public void setRealNewuserTrade(Double realNewuserTrade) {
		this.realNewuserTrade = realNewuserTrade;
	}

	public Double getRealNewuserDepositRate() {
		return realNewuserDepositRate;
	}

	public void setRealNewuserDepositRate(Double realNewuserDepositRate) {
		this.realNewuserDepositRate = realNewuserDepositRate;
	}

	public Double getRealNewuserTradeRate() {
		return realNewuserTradeRate;
	}

	public void setRealNewuserTradeRate(Double realNewuserTradeRate) {
		this.realNewuserTradeRate = realNewuserTradeRate;
	}

	public Double getRealNewuserLoginRate() {
		return realNewuserLoginRate;
	}

	public void setRealNewuserLoginRate(Double realNewuserLoginRate) {
		this.realNewuserLoginRate = realNewuserLoginRate;
	}

	public String getBusinessplatform() {
		return businessplatform;
	}

	public void setBusinessplatform(String businessplatform) {
		this.businessplatform = businessplatform;
	}
	
	
}
