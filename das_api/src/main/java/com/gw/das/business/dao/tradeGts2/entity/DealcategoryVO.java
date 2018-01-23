package com.gw.das.business.dao.tradeGts2.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 交易类别数据VO类
 * 
 * @author darren
 * @since 2017-04-22
 */
public class DealcategoryVO extends BaseModel {

	/* rowkey */
	@Column(name = "rowkey")
	private String rowkey;
	
	/* MT4倫敦金平倉手數 */
	@Column(name = "mt4volumeaxu")
	private String mt4volumeaxu;
	
	/* MT4倫敦銀平倉手數 */
	@Column(name = "mt4volumeaxg")
	private String mt4volumeaxg;
	
	/* GTS2倫敦銀平倉手數 */
	@Column(name = "gts2volumeaxu")
	private String gts2volumeaxu;
	
	/* GTS2倫敦金平倉手數 */
	@Column(name = "gts2volumeaxg")
	private String gts2volumeaxg;
	
	/* MT4人民幣金平倉手數 */
	@Column(name = "mt4volumeaxucnh")
	private String mt4volumeaxucnh;
	
	/* MT4人民幣銀平倉手數*/
	@Column(name = "mt4volumeaxgcnh")
	private String mt4volumeaxgcnh;
	
	/* GTS2人民幣金平倉手數 */
	@Column(name = "gts2volumeaxucnh")
	private String gts2volumeaxucnh;
	
	/* GTS2人民幣銀平倉手數 */
	@Column(name = "gts2volumeaxgcnh")
	private String gts2volumeaxgcnh;
	
	/* MT4浮盈 */
	@Column(name = "mt4floatingprofit")
	private String mt4floatingprofit;
	
	/* GTS2浮盈 */
	@Column(name = "gts2floatingprofit")
	private String gts2floatingprofit;
	
	/* MT4結餘*/
	@Column(name = "mt4balance")
	private String mt4balance;
	
	/* GTS2結餘 */
	@Column(name = "gts2balance")
	private String gts2balance;
	
	/* MT4人數*/
	@Column(name = "mt4useramount")
	private String mt4useramount;
	
	/*GTS2人數*/
	@Column(name = "gts2useramount")
	private String gts2useramount;
	
	/* MT4次數*/
	@Column(name = "mt4dealamount")
	private String mt4dealamount;
	
	/*GTS2次數*/
	@Column(name = "gts2dealamount")
	private String gts2dealamount;
	
	/* 货币 */
	@Column(name = "currency")
	private String currency;
	
	/* 公司ID */
	@Column(name = "companyid")
	private String companyid;
	
	/* 执行时间 */
	@Column(name = "exectime")
	private String exectime;
	
	/* 业务标识 */
	@Column(name = "businessplatform")
	private String businessplatform;
	
	/* 分区字段(按年分) */
	@Column(name = "partitionfield")
	private String partitionfield;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getMt4volumeaxu() {
		return mt4volumeaxu;
	}

	public void setMt4volumeaxu(String mt4volumeaxu) {
		this.mt4volumeaxu = mt4volumeaxu;
	}

	public String getMt4volumeaxg() {
		return mt4volumeaxg;
	}

	public void setMt4volumeaxg(String mt4volumeaxg) {
		this.mt4volumeaxg = mt4volumeaxg;
	}

	public String getGts2volumeaxu() {
		return gts2volumeaxu;
	}

	public void setGts2volumeaxu(String gts2volumeaxu) {
		this.gts2volumeaxu = gts2volumeaxu;
	}

	public String getGts2volumeaxg() {
		return gts2volumeaxg;
	}

	public void setGts2volumeaxg(String gts2volumeaxg) {
		this.gts2volumeaxg = gts2volumeaxg;
	}

	public String getMt4volumeaxucnh() {
		return mt4volumeaxucnh;
	}

	public void setMt4volumeaxucnh(String mt4volumeaxucnh) {
		this.mt4volumeaxucnh = mt4volumeaxucnh;
	}

	public String getMt4volumeaxgcnh() {
		return mt4volumeaxgcnh;
	}

	public void setMt4volumeaxgcnh(String mt4volumeaxgcnh) {
		this.mt4volumeaxgcnh = mt4volumeaxgcnh;
	}

	public String getGts2volumeaxucnh() {
		return gts2volumeaxucnh;
	}

	public void setGts2volumeaxucnh(String gts2volumeaxucnh) {
		this.gts2volumeaxucnh = gts2volumeaxucnh;
	}

	public String getGts2volumeaxgcnh() {
		return gts2volumeaxgcnh;
	}

	public void setGts2volumeaxgcnh(String gts2volumeaxgcnh) {
		this.gts2volumeaxgcnh = gts2volumeaxgcnh;
	}

	public String getMt4floatingprofit() {
		return mt4floatingprofit;
	}

	public void setMt4floatingprofit(String mt4floatingprofit) {
		this.mt4floatingprofit = mt4floatingprofit;
	}

	public String getGts2floatingprofit() {
		return gts2floatingprofit;
	}

	public void setGts2floatingprofit(String gts2floatingprofit) {
		this.gts2floatingprofit = gts2floatingprofit;
	}

	public String getMt4balance() {
		return mt4balance;
	}

	public void setMt4balance(String mt4balance) {
		this.mt4balance = mt4balance;
	}

	public String getGts2balance() {
		return gts2balance;
	}

	public void setGts2balance(String gts2balance) {
		this.gts2balance = gts2balance;
	}

	public String getMt4useramount() {
		return mt4useramount;
	}

	public void setMt4useramount(String mt4useramount) {
		this.mt4useramount = mt4useramount;
	}

	public String getGts2useramount() {
		return gts2useramount;
	}

	public void setGts2useramount(String gts2useramount) {
		this.gts2useramount = gts2useramount;
	}

	public String getMt4dealamount() {
		return mt4dealamount;
	}

	public void setMt4dealamount(String mt4dealamount) {
		this.mt4dealamount = mt4dealamount;
	}

	public String getGts2dealamount() {
		return gts2dealamount;
	}

	public void setGts2dealamount(String gts2dealamount) {
		this.gts2dealamount = gts2dealamount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getExectime() {
		return exectime;
	}

	public void setExectime(String exectime) {
		this.exectime = exectime;
	}

	public String getBusinessplatform() {
		return businessplatform;
	}

	public void setBusinessplatform(String businessplatform) {
		this.businessplatform = businessplatform;
	}

	public String getPartitionfield() {
		return partitionfield;
	}

	public void setPartitionfield(String partitionfield) {
		this.partitionfield = partitionfield;
	}

}
