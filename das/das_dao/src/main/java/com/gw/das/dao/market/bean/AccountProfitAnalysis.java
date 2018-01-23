package com.gw.das.dao.market.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @ClassName: AccountDiagnosisVo
 * @Description: 账户诊断报告model收益分析
 * @author kidy.zhao
 * @date 2017年7月7日
 *
 */
public class AccountProfitAnalysis implements Serializable {
	private static final long serialVersionUID = 1L;

	private String accountno;// 交易账号
	private String tradeprofitRatio;// 交易盈亏比
	private String avgprofitlossratio;// 平均盈亏比
	private String profitRatio;// 盈单占比
	private Integer buynumber;// 多单单数
	private Integer sellnumber;// 空单单数
	private String buyprofitratio;// 多单盈单比率
	private String sellprofitratio;// 空单盈单比率
	private Integer buyprofitnumber;// 多单赢单单数
	private Integer sellprofitnumber;// 空单赢单单数
	private Integer buylossnumber;// 多单亏单单数
	private String selllossnumber;// 空单亏单单数
	private String buytallprofit;// 多单盈亏额
	private String selltallprofit;// 空单盈亏额
	private Date positionintervaltime;// 盈利单平均持仓时间
	private Date lossintervaltime;// 亏损单平均持仓

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getTradeprofitRatio() {
		return tradeprofitRatio;
	}

	public void setTradeprofitRatio(String tradeprofitRatio) {
		this.tradeprofitRatio = tradeprofitRatio;
	}

	public String getAvgprofitlossratio() {
		return avgprofitlossratio;
	}

	public void setAvgprofitlossratio(String avgprofitlossratio) {
		this.avgprofitlossratio = avgprofitlossratio;
	}

	public String getProfitRatio() {
		return profitRatio;
	}

	public void setProfitRatio(String profitRatio) {
		this.profitRatio = profitRatio;
	}

	public Integer getBuynumber() {
		return buynumber;
	}

	public void setBuynumber(Integer buynumber) {
		this.buynumber = buynumber;
	}

	public Integer getSellnumber() {
		return sellnumber;
	}

	public void setSellnumber(Integer sellnumber) {
		this.sellnumber = sellnumber;
	}

	public String getBuyprofitratio() {
		return buyprofitratio;
	}

	public void setBuyprofitratio(String buyprofitratio) {
		this.buyprofitratio = buyprofitratio;
	}

	public String getSellprofitratio() {
		return sellprofitratio;
	}

	public void setSellprofitratio(String sellprofitratio) {
		this.sellprofitratio = sellprofitratio;
	}

	public Integer getBuyprofitnumber() {
		return buyprofitnumber;
	}

	public void setBuyprofitnumber(Integer buyprofitnumber) {
		this.buyprofitnumber = buyprofitnumber;
	}

	public Integer getSellprofitnumber() {
		return sellprofitnumber;
	}

	public void setSellprofitnumber(Integer sellprofitnumber) {
		this.sellprofitnumber = sellprofitnumber;
	}

	public Integer getBuylossnumber() {
		return buylossnumber;
	}

	public void setBuylossnumber(Integer buylossnumber) {
		this.buylossnumber = buylossnumber;
	}

	public String getSelllossnumber() {
		return selllossnumber;
	}

	public void setSelllossnumber(String selllossnumber) {
		this.selllossnumber = selllossnumber;
	}

	public String getBuytallprofit() {
		return buytallprofit;
	}

	public void setBuytallprofit(String buytallprofit) {
		this.buytallprofit = buytallprofit;
	}

	public String getSelltallprofit() {
		return selltallprofit;
	}

	public void setSelltallprofit(String selltallprofit) {
		this.selltallprofit = selltallprofit;
	}

	public Date getPositionintervaltime() {
		return positionintervaltime;
	}

	public void setPositionintervaltime(Date positionintervaltime) {
		this.positionintervaltime = positionintervaltime;
	}

	public Date getLossintervaltime() {
		return lossintervaltime;
	}

	public void setLossintervaltime(Date lossintervaltime) {
		this.lossintervaltime = lossintervaltime;
	}

}
