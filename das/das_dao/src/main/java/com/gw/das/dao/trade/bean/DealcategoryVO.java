package com.gw.das.dao.trade.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 交易类别数据VO类
 * 
 * @author darren
 * @since 2017-04-22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DealcategoryVO {

	/* rowkey */
	private String rowkey;
	
	/* MT4倫敦金平倉手數 */
	private String mt4volumeaxu;
	
	/* MT4倫敦銀平倉手數 */
	private String mt4volumeaxg;
	
	/* MT4人民幣金平倉手數 */
	private String mt4volumeaxucnh;
	
	/* MT4人民幣銀平倉手數*/
	private String mt4volumeaxgcnh;
	
	/* GTS2倫敦金平倉手數 */
	private String gts2volumeaxu;
	
	/* GTS2倫敦銀平倉手數 */
	private String gts2volumeaxg;
	
	/* GTS2人民幣金平倉手數 */
	private String gts2volumeaxucnh;
	
	/* GTS2人民幣銀平倉手數 */
	private String gts2volumeaxgcnh;
	
	/* 倫敦金平倉手數 */
	private String volumeaxu;
	
	/* 倫敦銀平倉手數 */
	private String volumeaxg;
	
	/* 人民幣金平倉手數 */
	private String volumeaxucnh;
	
	/* 人民幣銀平倉手數*/
	private String volumeaxgcnh;
	
	/* MT4浮盈 */
	private String mt4floatingprofit;
	
	/* GTS2浮盈 */
	private String gts2floatingprofit;
	
	/* MT4結餘*/
	private String mt4balance;
	
	/* GTS2結餘 */
	private String gts2balance;
	
	/* MT4人數*/
	private String mt4useramount;
	
	/*GTS2人數*/
	private String gts2useramount;
	
	/* MT4次數*/
	private String mt4dealamount;
	
	/*GTS2次數*/
	private String gts2dealamount;
	
	/* 货币 */
	private String currency;
	
	/* 公司ID */
	private String companyid;
	
	/* 执行时间 */
	private String exectime;
	
	/* 业务标识 */
	private String businessplatform;
	
	/* 平台类型 */
	private String platform;
	
	/* 分区字段(按年分) */
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVolumeaxu() {
		return volumeaxu;
	}

	public void setVolumeaxu(String volumeaxu) {
		this.volumeaxu = volumeaxu;
	}

	public String getVolumeaxg() {
		return volumeaxg;
	}

	public void setVolumeaxg(String volumeaxg) {
		this.volumeaxg = volumeaxg;
	}

	public String getVolumeaxucnh() {
		return volumeaxucnh;
	}

	public void setVolumeaxucnh(String volumeaxucnh) {
		this.volumeaxucnh = volumeaxucnh;
	}

	public String getVolumeaxgcnh() {
		return volumeaxgcnh;
	}

	public void setVolumeaxgcnh(String volumeaxgcnh) {
		this.volumeaxgcnh = volumeaxgcnh;
	}

}
