package com.gw.das.dao.market.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * @ClassName: ProfitAndLossAmountAndTime
 * @Description: 平仓单的盈亏金额与持仓时间
 * @author kidy.zhao
 * @date 2017年7月11日
 *
 */
@SuppressWarnings("serial")
public class ProfitAndLossAmountAndTime implements Serializable {
	private Integer companyid; // 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	private String accountno;// 交易账号
	private String platform;// 账户所在平台
	private String accountid;// 账号ID
	private Date closetime;// 平仓时间
	private String currency;// 账户货币
	private BigDecimal jyk;// 盈亏
	private Integer positionintervaltime; // 持仓时间

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public Date getClosetime() {
		return closetime;
	}

	public void setClosetime(Date closetime) {
		this.closetime = closetime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getJyk() {
		return jyk;
	}

	public void setJyk(BigDecimal jyk) {
		this.jyk = jyk;
	}

	public Integer getPositionintervaltime() {
		return positionintervaltime;
	}

	public void setPositionintervaltime(Integer positionintervaltime) {
		this.positionintervaltime = positionintervaltime;
	}

}
