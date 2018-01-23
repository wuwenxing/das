package com.gw.das.business.dao.trade.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 存取款及账户
 */
public class DasTradeGts2cashandaccountdetailStatisticsYears extends BaseModel {

	/** rowkey */
	@Column(name = "rowkey")
	private String rowKey;
	
	/** 当日激活账户 */
	@Column(name = "sumactday")
	private Long sumactday = 0L;
	
	/** 累计激活账户 */
	@Column(name = "sumactall")
	private Long sumactall = 0L;
	
	/** 累计开户未激活*/
	@Column(name = "sumnotall")
	private Long sumnotall = 0L;
	
	/** 累计总开户 */
	@Column(name = "sumopenall")
	private Long sumopenall = 0L;
	
	/** 当日净存款 */
	@Column(name = "equitymdeposit")
	private Double equitymdeposit = 0D;

	/** 当日总存款 */
	@Column(name = "summdeposit")
	private Double summdeposit = 0D;
	
	/** 当日总取款 */
	@Column(name = "sumwithdraw")
	private Double sumwithdraw = 0D;
	
	/** 当日激活账户存款 */
	@Column(name = "sumnewaccount")
	private Double sumnewaccount = 0D;
	
	/** 日结余 */
	@Column(name = "previousbalance")
	private Double previousbalance = 0D;
	
	/** 日推广贈金 */
	@Column(name = "bonus")
	private Double bonus = 0D;
	
	/** 执行时间 */
	@Column(name = "exectime")
	private String exectime;
	
	/** 货币类型 */
	@Column(name = "currency")
	private String currency;
	
	/** 来源 */
	@Column(name = "source")
	private String source;
	
	/** 平台(GTS, MT4, MT5 ,...) */
	@Column(name = "platformtype")
	private String platformtype;

	/** companyid */
	@Column(name = "companyid")
	private String companyid;
	
	/** 业务类型 */
	@Column(name = "businessplatform")
	private String businessPlatform;

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
	
}
