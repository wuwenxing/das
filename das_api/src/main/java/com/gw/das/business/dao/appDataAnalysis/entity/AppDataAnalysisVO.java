package com.gw.das.business.dao.appDataAnalysis.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * App数据分析报表VO
 * 
 * @author Darren
 * @since 2017-03-22
 */
public class AppDataAnalysisVO extends BaseModel {

	@Column(name = "rowkey")
	private String rowKey;
	
	/* 月 */
	@Column(name = "dt_month")
	private String dtMonth;
	/* 日 */
	@Column(name = "dt_day")
	private String dtDay;
	
	/* 日 */
	@Column(name = "dt_week")
	private String dtWeek;
	
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
	private Integer activeuser;
	
	/* 新用户 */
	@Column(name = "newuser")
	private Integer newuser;
	
	/* 老客户-D */
	@Column(name = "olduser_demo")
	private Integer olduserDemo;
	
	/* 老客户-R */
	@Column(name = "olduser_real")
	private Integer olduserReal;
	
	/* 老客户-N */
	@Column(name = "olduser_activate")
	private Integer olduserActivate;
	
	/* 新开户 */
	@Column(name = "demo_account")
	private Integer demoAccount;
	
	/* 新客户-登录 */
	@Column(name = "demo_newuser_login")
	private Integer demoNewuserLogin;
	
	/* 新客户-交易 */
	@Column(name = "demo_newuser_trade")
	private Integer demoNewuserTrade;
	
	/* 老客户-登录*/
	@Column(name = "demo_olduser_login")
	private Integer demoOlduserLogin;
	
	/* 老客户-交易 */
	@Column(name = "demo_olduser_trade")
	private Integer demoOlduserTrade;
	
	/* 登录总人数 */
	@Column(name = "demo_all_login")
	private Integer demoAllLogin;
	
	/* 交易总人数*/
	@Column(name = "demo_all_trade")
	private Integer demoAllTrade;
	
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
	private Integer realAccount;
	
	/* 新客登录 */
	@Column(name = "real_newuser_login")
	private Integer realNewuserLogin;
	
	/* 新客入金 */
	@Column(name = "real_newuser_deposit")
	private Integer realNewuserDeposit;
	
	/* 新客户交易 */
	@Column(name = "real_newuser_trade")
	private Integer realNewuserTrade;
	
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

	public String getDtDay() {
		return dtDay;
	}

	public void setDtDay(String dtDay) {
		this.dtDay = dtDay;
	}
	
	

	public String getDtWeek() {
		return dtWeek;
	}

	public void setDtWeek(String dtWeek) {
		this.dtWeek = dtWeek;
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

	public Integer getActiveuser() {
		return activeuser;
	}

	public void setActiveuser(Integer activeuser) {
		this.activeuser = activeuser;
	}

	public Integer getNewuser() {
		return newuser;
	}

	public void setNewuser(Integer newuser) {
		this.newuser = newuser;
	}

	public Integer getOlduserDemo() {
		return olduserDemo;
	}

	public void setOlduserDemo(Integer olduserDemo) {
		this.olduserDemo = olduserDemo;
	}

	public Integer getOlduserReal() {
		return olduserReal;
	}

	public void setOlduserReal(Integer olduserReal) {
		this.olduserReal = olduserReal;
	}

	public Integer getOlduserActivate() {
		return olduserActivate;
	}

	public void setOlduserActivate(Integer olduserActivate) {
		this.olduserActivate = olduserActivate;
	}

	public Integer getDemoAccount() {
		return demoAccount;
	}

	public void setDemoAccount(Integer demoAccount) {
		this.demoAccount = demoAccount;
	}

	public Integer getDemoNewuserLogin() {
		return demoNewuserLogin;
	}

	public void setDemoNewuserLogin(Integer demoNewuserLogin) {
		this.demoNewuserLogin = demoNewuserLogin;
	}

	public Integer getDemoNewuserTrade() {
		return demoNewuserTrade;
	}

	public void setDemoNewuserTrade(Integer demoNewuserTrade) {
		this.demoNewuserTrade = demoNewuserTrade;
	}

	public Integer getDemoOlduserLogin() {
		return demoOlduserLogin;
	}

	public void setDemoOlduserLogin(Integer demoOlduserLogin) {
		this.demoOlduserLogin = demoOlduserLogin;
	}

	public Integer getDemoOlduserTrade() {
		return demoOlduserTrade;
	}

	public void setDemoOlduserTrade(Integer demoOlduserTrade) {
		this.demoOlduserTrade = demoOlduserTrade;
	}

	public Integer getDemoAllLogin() {
		return demoAllLogin;
	}

	public void setDemoAllLogin(Integer demoAllLogin) {
		this.demoAllLogin = demoAllLogin;
	}

	public Integer getDemoAllTrade() {
		return demoAllTrade;
	}

	public void setDemoAllTrade(Integer demoAllTrade) {
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

	public Integer getRealAccount() {
		return realAccount;
	}

	public void setRealAccount(Integer realAccount) {
		this.realAccount = realAccount;
	}

	public Integer getRealNewuserLogin() {
		return realNewuserLogin;
	}

	public void setRealNewuserLogin(Integer realNewuserLogin) {
		this.realNewuserLogin = realNewuserLogin;
	}

	public Integer getRealNewuserDeposit() {
		return realNewuserDeposit;
	}

	public void setRealNewuserDeposit(Integer realNewuserDeposit) {
		this.realNewuserDeposit = realNewuserDeposit;
	}

	public Integer getRealNewuserTrade() {
		return realNewuserTrade;
	}

	public void setRealNewuserTrade(Integer realNewuserTrade) {
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
