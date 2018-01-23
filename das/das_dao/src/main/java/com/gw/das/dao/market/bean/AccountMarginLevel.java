package com.gw.das.dao.market.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @ClassName: AccountMarginLevel
 * @Description: 获取保证金水平走势
 * @author kidy.zhao
 * @date 2017年7月10日
 *
 */
@SuppressWarnings("serial")
public class AccountMarginLevel implements Serializable {
	private String companyid;// 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	private String accountno;// 交易账号
	private String platform;// 账户所在平台
	private String currency; // 币种
	private BigDecimal balance;// 结余

	private BigDecimal floatingprofit;// 浮动盈亏
	private BigDecimal margin;// 保证金
	private BigDecimal marginratio;// 保证金水平
	private String execdaterpt;// 时间

	private String execFormatdate;

	public String getExecFormatdate() {
		return execFormatdate;
	}

	public void setExecFormatdate(String execFormatdate) {
		this.execFormatdate = execFormatdate;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getFloatingprofit() {
		return floatingprofit;
	}

	public void setFloatingprofit(BigDecimal floatingprofit) {
		this.floatingprofit = floatingprofit;
	}

	public BigDecimal getMargin() {
		return margin;
	}

	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}

	public BigDecimal getMarginratio() {
		return marginratio;
	}

	public void setMarginratio(BigDecimal marginratio) {
		this.marginratio = marginratio;
	}

	public String getExecdaterpt() {
		return execdaterpt;
	}

	public void setExecdaterpt(String execdaterpt) {
		this.execdaterpt = execdaterpt;
	}

}
