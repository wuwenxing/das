package com.gw.das.dao.trade.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 开户及存款总结
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAccountAndDepositSummary {

	/** rowkey */
	private String rowkey;
	
	/** 日期 */
	private String datetime;
	
	/** 当日激活的客戶数(包括當天開戶和非當天開戶)(A) */
	private Long sumactdayall = 0L;

	/** 当日激活的客戶数(當天開戶而且激活的客戶)(當天A)  */
	private Long sumactday = 0L;

	/** 累计激活的客戶数(當天開戶而且激活的客戶)(當天A)  */
	private Long sumactall = 0L;
	
	/** 当日真实开户数(當天開戶)(當天A+N)  */
	private Long sumopendayreal = 0L;

	/** 累计真实开户数(當天開戶)(當天A+N)  */
	private Long sumopenallreal = 0L;
	
	/** 累计模拟开户数(包括當天開戶和非當天開戶)(A)  */
	private Long sumopenalldemo = 0L;

	/** 当日激活账户存款(當天開戶而且激活的客戶)(當天A)  */
	private Double sumnewaccountday = 0D;

	/** 当月激活账户存款(當天開戶而且激活的客戶)(當天A)  */
	private Double sumnewaccountmonth = 0D;
	
	/** 总存款 */
	private Double summdeposit = 0D;
	
	/** 总取款 */
	private Double sumwithdraw = 0D;
	
	/** 净存款 */
	private Double equitymdeposit = 0D;
	
	/** 在线支付  */
	private Double onlinepayment = 0D;
	
	/** 收还款项  */
	private Double reimbursement = 0D;
	
	/** 结余  */
	private Double balance = 0D;

	/** 平台(GTS, MT4, MT5 ,...) */
	private String platform;

	/** companyid */
	private String companyid;
	
	/** 业务类型 */
	private String businessPlatform;

	/**客户状况*/
	private Long advisoryCustomerCount = 0L;// 当日客户对话量-客服電話回訪量
	private Long advisoryCount = 0L;// 当日对话总数-客戶咨詢量
	/**客户状况*/
	/**20171020新增*/
	private Long customerPhoneCount = 0L;// 取得客户电话数目
	
	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Long getSumactdayall() {
		return sumactdayall;
	}

	public void setSumactdayall(Long sumactdayall) {
		this.sumactdayall = sumactdayall;
	}

	public Long getSumactday() {
		return sumactday;
	}

	public void setSumactday(Long sumactday) {
		this.sumactday = sumactday;
	}

	public Long getSumactall() {
		return sumactall;
	}

	public void setSumactall(Long sumactall) {
		this.sumactall = sumactall;
	}

	public Long getSumopenallreal() {
		return sumopenallreal;
	}

	public void setSumopenallreal(Long sumopenallreal) {
		this.sumopenallreal = sumopenallreal;
	}

	public Long getSumopenalldemo() {
		return sumopenalldemo;
	}

	public void setSumopenalldemo(Long sumopenalldemo) {
		this.sumopenalldemo = sumopenalldemo;
	}

	public Double getSumnewaccountday() {
		return sumnewaccountday;
	}

	public void setSumnewaccountday(Double sumnewaccountday) {
		this.sumnewaccountday = sumnewaccountday;
	}

	public Double getSumnewaccountmonth() {
		return sumnewaccountmonth;
	}

	public void setSumnewaccountmonth(Double sumnewaccountmonth) {
		this.sumnewaccountmonth = sumnewaccountmonth;
	}

	public Double getSummdeposit() {
		return summdeposit;
	}

	public void setSummdeposit(Double summdeposit) {
		this.summdeposit = summdeposit;
	}

	public Double getSumwithdraw() {
		return sumwithdraw;
	}

	public void setSumwithdraw(Double sumwithdraw) {
		this.sumwithdraw = sumwithdraw;
	}

	public Double getEquitymdeposit() {
		return equitymdeposit;
	}

	public void setEquitymdeposit(Double equitymdeposit) {
		this.equitymdeposit = equitymdeposit;
	}

	public Double getOnlinepayment() {
		return onlinepayment;
	}

	public void setOnlinepayment(Double onlinepayment) {
		this.onlinepayment = onlinepayment;
	}

	public Double getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(Double reimbursement) {
		this.reimbursement = reimbursement;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public Long getSumopendayreal() {
		return sumopendayreal;
	}

	public void setSumopendayreal(Long sumopendayreal) {
		this.sumopendayreal = sumopendayreal;
	}

	public Long getAdvisoryCustomerCount() {
		return advisoryCustomerCount;
	}

	public void setAdvisoryCustomerCount(Long advisoryCustomerCount) {
		this.advisoryCustomerCount = advisoryCustomerCount;
	}

	public Long getAdvisoryCount() {
		return advisoryCount;
	}

	public void setAdvisoryCount(Long advisoryCount) {
		this.advisoryCount = advisoryCount;
	}

	public Long getCustomerPhoneCount() {
		return customerPhoneCount;
	}

	public void setCustomerPhoneCount(Long customerPhoneCount) {
		this.customerPhoneCount = customerPhoneCount;
	}
	
}
