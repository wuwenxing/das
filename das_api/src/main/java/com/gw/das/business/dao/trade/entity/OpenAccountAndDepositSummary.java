package com.gw.das.business.dao.trade.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 开户及存款总结
 */
public class OpenAccountAndDepositSummary extends BaseModel {

	/** rowkey */
	@Column(name = "rowkey")
	private String rowkey;
	
	/** 日期 */
	@Column(name = "datetime")
	private String datetime;
	
	/** 当日激活的客戶数(包括當天開戶和非當天開戶)(A) */
	@Column(name = "sumactdayall")
	private Long sumactdayall = 0L;

	/** 当日激活的客戶数(當天開戶而且激活的客戶)(當天A)  */
	@Column(name = "sumactday")
	private Long sumactday = 0L;

	/** 累计激活的客戶数(當天開戶而且激活的客戶)(當天A)  */
	@Column(name = "sumactall")
	private Long sumactall = 0L;
	
	/** 当日真实开户数(當天開戶)(當天A+N)  */
	@Column(name = "sumopendayreal")
	private Long sumopendayreal = 0L;

	/** 累计真实开户数(當天開戶)(當天A+N)  */
	@Column(name = "sumopenallreal")
	private Long sumopenallreal = 0L;
	
	/** 累计模拟开户数(包括當天開戶和非當天開戶)(A)  */
	@Column(name = "sumopenalldemo")
	private Long sumopenalldemo = 0L;

	/** 当日激活账户存款(當天開戶而且激活的客戶)(當天A)  */
	@Column(name = "sumnewaccountday")
	private Double sumnewaccountday = 0D;

	/** 当月激活账户存款(當天開戶而且激活的客戶)(當天A)  */
	@Column(name = "sumnewaccountmonth")
	private Double sumnewaccountmonth = 0D;
	
	/** 总存款 */
	@Column(name = "summdeposit")
	private Double summdeposit = 0D;
	
	/** 总取款 */
	@Column(name = "sumwithdraw")
	private Double sumwithdraw = 0D;
	
	/** 净存款 */
	@Column(name = "equitymdeposit")
	private Double equitymdeposit = 0D;
	
	/** 在线支付  */
	@Column(name = "onlinepayment")
	private Double onlinepayment = 0D;
	
	/** 收还款项  */
	@Column(name = "reimbursement")
	private Double reimbursement = 0D;
	
	/** 结余  */
	@Column(name = "balance")
	private Double balance = 0D;

	/** 平台(GTS, MT4, MT5 ,...) */
	@Column(name = "platform")
	private String platform;

	/** companyid */
	@Column(name = "companyid")
	private String companyid;
	
	/** 业务类型 */
	@Column(name = "businessplatform")
	private String businessPlatform;

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
	
}
