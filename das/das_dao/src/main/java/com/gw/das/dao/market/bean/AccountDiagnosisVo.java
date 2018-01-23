package com.gw.das.dao.market.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @ClassName: AccountDiagnosisVo
 * @Description: 账户诊断报告model
 * @author kidy.zhao
 * @date 2017年7月7日
 *
 */
public class AccountDiagnosisVo implements Serializable {
	private static final long serialVersionUID = -4224584457838495629L;
	private String accountno; // 账号
	private Integer tradesnumber;// 交易操作次数
	private BigDecimal totalprofit;// 平仓总盈利
	private Integer profitnumber;// 盈利次数
	private BigDecimal totalloss;// 平仓总亏损
	private Integer lossnumber;// 亏损次数
	private BigDecimal closeprofit;// 平仓盈亏
	private BigDecimal swap;// 过夜利息
	private BigDecimal actualprofit;// 实际盈亏
	private Integer strongnumber;// 强平次数
	private Integer strongflatloss;// 强平亏损
	private Integer netdeposit;// 净入金
	private BigDecimal floatingprofit;// 浮动盈亏
	private String maxprofitratio;// 最大盈亏比
	private String winrate;// 胜率
	private String tradeprofitRatio;// 交易盈亏比

	public String getTradeprofitRatio() {
		return tradeprofitRatio;
	}

	public void setTradeprofitRatio(String tradeprofitRatio) {
		this.tradeprofitRatio = tradeprofitRatio;
	}

	public String getAvgProfitLossRatio() {
		return avgProfitLossRatio;
	}

	public void setAvgProfitLossRatio(String avgProfitLossRatio) {
		this.avgProfitLossRatio = avgProfitLossRatio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String avgProfitLossRatio;// 平均盈亏比

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public Integer getTradesnumber() {
		return tradesnumber;
	}

	public void setTradesnumber(Integer tradesnumber) {
		this.tradesnumber = tradesnumber;
	}

	public BigDecimal getTotalprofit() {
		return totalprofit;
	}

	public void setTotalprofit(BigDecimal totalprofit) {
		this.totalprofit = totalprofit;
	}

	public Integer getProfitnumber() {
		return profitnumber;
	}

	public void setProfitnumber(Integer profitnumber) {
		this.profitnumber = profitnumber;
	}

	public BigDecimal getTotalloss() {
		return totalloss;
	}

	public void setTotalloss(BigDecimal totalloss) {
		this.totalloss = totalloss;
	}

	public Integer getLossnumber() {
		return lossnumber;
	}

	public void setLossnumber(Integer lossnumber) {
		this.lossnumber = lossnumber;
	}

	public BigDecimal getCloseprofit() {
		return closeprofit;
	}

	public void setCloseprofit(BigDecimal closeprofit) {
		this.closeprofit = closeprofit;
	}

	public BigDecimal getSwap() {
		return swap;
	}

	public void setSwap(BigDecimal swap) {
		this.swap = swap;
	}

	public BigDecimal getActualprofit() {
		return actualprofit;
	}

	public void setActualprofit(BigDecimal actualprofit) {
		this.actualprofit = actualprofit;
	}

	public Integer getStrongnumber() {
		return strongnumber;
	}

	public void setStrongnumber(Integer strongnumber) {
		this.strongnumber = strongnumber;
	}

	public Integer getStrongflatloss() {
		return strongflatloss;
	}

	public void setStrongflatloss(Integer strongflatloss) {
		this.strongflatloss = strongflatloss;
	}

	public Integer getNetdeposit() {
		return netdeposit;
	}

	public void setNetdeposit(Integer netdeposit) {
		this.netdeposit = netdeposit;
	}

	public BigDecimal getFloatingprofit() {
		return floatingprofit;
	}

	public void setFloatingprofit(BigDecimal floatingprofit) {
		this.floatingprofit = floatingprofit;
	}

	public String getMaxprofitratio() {
		return maxprofitratio;
	}

	public void setMaxprofitratio(String maxprofitratio) {
		this.maxprofitratio = maxprofitratio;
	}

	public String getWinrate() {
		return winrate;
	}

	public void setWinrate(String winrate) {
		this.winrate = winrate;
	}

}
