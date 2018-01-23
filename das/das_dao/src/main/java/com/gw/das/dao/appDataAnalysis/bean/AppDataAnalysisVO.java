package com.gw.das.dao.appDataAnalysis.bean;

/**
 * App数据分析表VO类
 * 
 * @author Darren
 * @since 2017-03-22
 */
public class AppDataAnalysisVO {
	
	private String rowKey;

	/* 月 */
	private String dtMonth;
	
	/* 日 */
	private String dtDay;
	
	/* 周 */
	private String dtWeek;
	
	/* 分区字段 */
	private String partitionfield;
	
	/* 渠道 */
	private String channel;
	
	/* 系统类型 */
	private String devicetype;
	
	/* 活跃用户 */
	private Integer activeuser = 0;
	
	/* 新用户 */
	private Integer newuser= 0;
	
	/* 老客户-D */
	private Integer olduserDemo= 0;
	
	/* 老客户-R */
	private Integer olduserReal= 0;
	
	/* 老客户-N */
	private Integer olduserActivate= 0;
	
	/* 新开户 */
	private Integer demoAccount= 0;
	
	/* 新客户-登录 */
	private Integer demoNewuserLogin= 0;
	
	/* 新客户-交易 */
	private Integer demoNewuserTrade= 0;
	
	/* 老客户-登录*/
	private Integer demoOlduserLogin= 0;
	
	/* 老客户-交易 */
	private Integer demoOlduserTrade= 0;
	
	/* 登录总人数 */
	private Integer demoAllLogin= 0;
	
	/* 交易总人数*/
	private Integer demoAllTrade= 0;
	
	/* 新客登录率 */
	private String demoNewuserLoginRate;
	
	/* 新客交易率 */
	private String demoNewuserTradeRate ;
	
	/* 老客交易率 */
	private String demoOlduserTradeRate ;
	
	/* 总交易率 */
	private String demoAllTradeRate ;
	
	/* 新开户 */
	private Integer realAccount = 0;
	
	/* 新客登录 */
	private Integer realNewuserLogin = 0;
	
	/* 新客入金 */
	private Integer realNewuserDeposit = 0 ;
	
	/* 新客户交易 */
	private Integer realNewuserTrade = 0 ;
	
	/* 新客入金率*/
	private String realNewuserDepositRate ;

	/* 新客交易率  */
	private String realNewuserTradeRate ;
	
	/*   新客登录率*/
	private String realNewuserLoginRate ;
	
	/* 业务类型 */
	private String businessplatform;
	
	private String footer;

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
	
	

	public String getDtWeek() {
		return dtWeek;
	}

	public void setDtWeek(String dtWeek) {
		this.dtWeek = dtWeek;
	}

	public void setDtDay(String dtDay) {
		this.dtDay = dtDay;
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

	public String getDemoNewuserLoginRate() {
		return demoNewuserLoginRate;
	}

	public void setDemoNewuserLoginRate(String demoNewuserLoginRate) {
		this.demoNewuserLoginRate = demoNewuserLoginRate;
	}

	public String getDemoNewuserTradeRate() {
		return demoNewuserTradeRate;
	}

	public void setDemoNewuserTradeRate(String demoNewuserTradeRate) {
		this.demoNewuserTradeRate = demoNewuserTradeRate;
	}

	public String getDemoOlduserTradeRate() {
		return demoOlduserTradeRate;
	}

	public void setDemoOlduserTradeRate(String demoOlduserTradeRate) {
		this.demoOlduserTradeRate = demoOlduserTradeRate;
	}

	public String getDemoAllTradeRate() {
		return demoAllTradeRate;
	}

	public void setDemoAllTradeRate(String demoAllTradeRate) {
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

	public String getRealNewuserDepositRate() {
		return realNewuserDepositRate;
	}

	public void setRealNewuserDepositRate(String realNewuserDepositRate) {
		this.realNewuserDepositRate = realNewuserDepositRate;
	}

	public String getRealNewuserTradeRate() {
		return realNewuserTradeRate;
	}

	public void setRealNewuserTradeRate(String realNewuserTradeRate) {
		this.realNewuserTradeRate = realNewuserTradeRate;
	}

	public String getRealNewuserLoginRate() {
		return realNewuserLoginRate;
	}

	public void setRealNewuserLoginRate(String realNewuserLoginRate) {
		this.realNewuserLoginRate = realNewuserLoginRate;
	}

	public String getBusinessplatform() {
		return businessplatform;
	}

	public void setBusinessplatform(String businessplatform) {
		this.businessplatform = businessplatform;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	
	
	
}
