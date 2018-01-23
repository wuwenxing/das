package com.gw.das.dao.market.bean;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 
 * @ClassName: AccountProfitStaticstics
 * @Description: 收益统计
 * @author kidy.zhao
 * @date 2017年7月10日
 *
 */
public class AccountProfitStaticstics implements Serializable {

	private static final long serialVersionUID = -7626236216446672778L;
	private BigDecimal cumulativeProfit;// 累计收益
	private BigDecimal profit;// 当天收益
	private String floatingprofit;// 浮动盈亏
	private String execdate; // 日期
	private String accountno;// 账号
	private String execFormatdate;

	public String getFloatingprofit() {
		return floatingprofit;
	}

	public void setFloatingprofit(String floatingprofit) {
		this.floatingprofit = floatingprofit;
	}

	public String getExecFormatdate() {
		return execFormatdate;
	}

	public void setExecFormatdate(String execFormatdate) {
		this.execFormatdate = execFormatdate;
	}

	public String getExecdate() {
		return execdate;
	}

	public void setExecdate(String execdate) {
		this.execdate = execdate;
	}

	public BigDecimal getCumulativeProfit() {
		return cumulativeProfit;
	}

	public void setCumulativeProfit(BigDecimal cumulativeProfit) {
		this.cumulativeProfit = cumulativeProfit;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

}
