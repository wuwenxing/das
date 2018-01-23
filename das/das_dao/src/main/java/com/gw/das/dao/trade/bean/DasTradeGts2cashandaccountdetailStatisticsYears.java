package com.gw.das.dao.trade.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 存取款及账户
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasTradeGts2cashandaccountdetailStatisticsYears {

	/** rowkey */
	private String rowKey;
	
	/** 当日激活账户 */
	private Long sumactday;
	
	/** 累计激活账户 */
	private Long sumactall;
	
	/** 累计开户未激活*/
	private Long sumnotall;
	
	/** 累计总开户 */
	private Long sumopenall;
	
	/** 当日净存款 */
	private Double equitymdeposit;

	/** 当日总存款 */
	private Double summdeposit;
	
	/** 当日总取款 */
	private Double sumwithdraw;
	
	/** 当日激活账户存款 */
	private Double sumnewaccount;
	
	/** 日结余 */
	private Double previousbalance;
	
	/** 日推广贈金 */
	private Double bonus;
	
	/** 执行时间 */
	private String exectime;
	
	/** 货币类型 */
	private String currency;
	
	/** 来源 */
	private String source;
	
	/** 平台(GTS, MT4, MT5 ,...) */
	private String platformtype;

	/** companyid */
	private String companyid;
	
	/** 业务类型 */
	private String businessPlatform;

	/**手工录入数据相关*/
	/**客户状况*/
	private Long advisoryCustomerCount = 0L;// 当日客户对话量
	private Long advisoryCount = 0L;// 当日对话总数
	/**客户状况*/
	/**活动推广赠金（其它）*/
	private Double bonusOtherGts2 = 0D;// 活动推广赠金（其它）GTS2
	private Double bonusOtherMt4 = 0D;// 活动推广赠金（其它）MT4
	private Double bonusOtherGts = 0D;// 活动推广赠金（其它）GTS
	private Double bonusOtherMt5 = 0D;// 活动推广赠金（其它）MT5
	/**活动推广赠金（其它）*/
	
	
	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
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

	public Long getSumnotall() {
		return sumnotall;
	}

	public void setSumnotall(Long sumnotall) {
		this.sumnotall = sumnotall;
	}

	public Long getSumopenall() {
		return sumopenall;
	}

	public void setSumopenall(Long sumopenall) {
		this.sumopenall = sumopenall;
	}

	public Double getEquitymdeposit() {
		return equitymdeposit;
	}

	public void setEquitymdeposit(Double equitymdeposit) {
		this.equitymdeposit = equitymdeposit;
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

	public Double getSumnewaccount() {
		return sumnewaccount;
	}

	public void setSumnewaccount(Double sumnewaccount) {
		this.sumnewaccount = sumnewaccount;
	}

	public Double getPreviousbalance() {
		return previousbalance;
	}

	public void setPreviousbalance(Double previousbalance) {
		this.previousbalance = previousbalance;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public String getExectime() {
		return exectime;
	}

	public void setExectime(String exectime) {
		this.exectime = exectime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
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

	public Double getBonusOtherGts2() {
		return bonusOtherGts2;
	}

	public void setBonusOtherGts2(Double bonusOtherGts2) {
		this.bonusOtherGts2 = bonusOtherGts2;
	}

	public Double getBonusOtherMt4() {
		return bonusOtherMt4;
	}

	public void setBonusOtherMt4(Double bonusOtherMt4) {
		this.bonusOtherMt4 = bonusOtherMt4;
	}

	public Double getBonusOtherGts() {
		return bonusOtherGts;
	}

	public void setBonusOtherGts(Double bonusOtherGts) {
		this.bonusOtherGts = bonusOtherGts;
	}

	public Double getBonusOtherMt5() {
		return bonusOtherMt5;
	}

	public void setBonusOtherMt5(Double bonusOtherMt5) {
		this.bonusOtherMt5 = bonusOtherMt5;
	}
	
}
