package com.gw.das.business.dao.tradeGts2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 账户余额日结算表VO类
 * 
 * @author darren
 * @since 2017-03-17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountbalanceVO extends BaseModel {

	/* HBase中的rowkey，分页查询时用 */
	@Column(name = "rowkey")
	private String rowkey;
	
	/* 订单号 */
	@Column(name = "orderid")
	private String orderid;
	
	/* 账户 */
	@Column(name = "accountno")
	private String accountno;
	
	/* 时间*/
	@Column(name = "datetime")
	private String datetime;
	
	/* 提案类型 */
	@Column(name = "proposaltype")
	private String proposaltype;
	
	/* 交易类型 （例：取款 入金 赠金 返佣）*/
	@Column(name = "reason")
	private String reason;
	
	/* 报表类型 */
	@Column(name = "reporttype")
	private String reporttype;
	
	/* 金额 */
	@Column(name = "amount")
	private String amount;
	
	/* 平台(GTS, MT4, MT5 ,...) */
	@Column(name = "platform")
	private String platform;
	
	/* 业务类型 */
	@Column(name = "businessplatform")
	private String businessplatform;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getProposaltype() {
		return proposaltype;
	}

	public void setProposaltype(String proposaltype) {
		this.proposaltype = proposaltype;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReporttype() {
		return reporttype;
	}

	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getBusinessplatform() {
		return businessplatform;
	}

	public void setBusinessplatform(String businessplatform) {
		this.businessplatform = businessplatform;
	}

	
}
