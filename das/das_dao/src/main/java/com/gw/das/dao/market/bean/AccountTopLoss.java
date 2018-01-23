package com.gw.das.dao.market.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @ClassName: AccountDiagnosisVo
 * @Description: 账户诊断报告model
 * @author kidy.zhao
 * @date 2017年7月7日
 *
 */
public class AccountTopLoss implements Serializable {
	private static final long serialVersionUID = 630625240928084849L;
	private Integer companyid;// 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	private String accountno;// 交易账号
	private String platform;// 账户所在平台
	private String accountid;// 账号ID
	private String dealid;// 订单号
	private String positionid;// 持仓单号
	private Date opentime;// 开仓时间
	private String direction;// 开仓方向
	private BigDecimal openprice;// 开仓价格
	private Date closetime;// 平仓时间
	private BigDecimal closeprice;// 平仓价格
	private String currency;// 账户货币
	private String symbolname;// 产品名称
	private String reason;// 交易类型
	private BigDecimal volume;// 手数
	private BigDecimal sl;// 止损设置
	private BigDecimal tp;// 止盈设置
	private BigDecimal commission;// 佣金
	private BigDecimal swap;// 过夜利息
	private BigDecimal jyk; // 盈亏
	private BigDecimal ykds;// 盈亏点数

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

	public String getDealid() {
		return dealid;
	}

	public void setDealid(String dealid) {
		this.dealid = dealid;
	}

	public String getPositionid() {
		return positionid;
	}

	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}

	public Date getOpentime() {
		return opentime;
	}

	public void setOpentime(Date opentime) {
		this.opentime = opentime;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public BigDecimal getOpenprice() {
		return openprice;
	}

	public void setOpenprice(BigDecimal openprice) {
		this.openprice = openprice;
	}

	public Date getClosetime() {
		return closetime;
	}

	public void setClosetime(Date closetime) {
		this.closetime = closetime;
	}

	public BigDecimal getCloseprice() {
		return closeprice;
	}

	public void setCloseprice(BigDecimal closeprice) {
		this.closeprice = closeprice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSymbolname() {
		return symbolname;
	}

	public void setSymbolname(String symbolname) {
		this.symbolname = symbolname;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getSl() {
		return sl;
	}

	public void setSl(BigDecimal sl) {
		this.sl = sl;
	}

	public BigDecimal getTp() {
		return tp;
	}

	public void setTp(BigDecimal tp) {
		this.tp = tp;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getSwap() {
		return swap;
	}

	public void setSwap(BigDecimal swap) {
		this.swap = swap;
	}

	public BigDecimal getJyk() {
		return jyk;
	}

	public void setJyk(BigDecimal jyk) {
		this.jyk = jyk;
	}

	public BigDecimal getYkds() {
		return ykds;
	}

	public void setYkds(BigDecimal ykds) {
		this.ykds = ykds;
	}

}
