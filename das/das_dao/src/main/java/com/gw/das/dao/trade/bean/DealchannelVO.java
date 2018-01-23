package com.gw.das.dao.trade.bean;

/**
 * 下单途径比例VO类
 * 
 * @author darren
 * @since 2017-04-10
 */
public class DealchannelVO  {

	/* rowkey */
	private String rowkey;
	
	/* 执行时间 */
	private String exectime;
	
	/* 安卓下单数量 */
	private Double androidamount = 0D;
	
	/* IOS下单数量 */
	private Double iosamount = 0D;
	
	/* 系统下单数量 */
	private Double systemamount = 0D;
	
	/* PC下单数量 */
	private Double pcamount = 0D;
	
	/* 其他下单数量	 */
	private Double otheramount = 0D;
	
	/* 总下单数量 */
	private Double totalamount = 0D;
	
	/* 新旧平台标识 */
	private String source;
	
	/*  平台类型 */
	private String platform;
	
	/* 分区字段(按年分) */
	private String partitionfield;
	
	/* 业务类型 */
	private String businessplatform;
	
	/* 公司ID */
	private String companyid;

	public String getRowkey() {
		return rowkey;
	}

	public String getExectime() {
		return exectime;
	}

	public void setExectime(String exectime) {
		this.exectime = exectime;
	}

	public Double getAndroidamount() {
		return androidamount;
	}

	public void setAndroidamount(Double androidamount) {
		this.androidamount = androidamount;
	}

	public Double getIosamount() {
		return iosamount;
	}

	public void setIosamount(Double iosamount) {
		this.iosamount = iosamount;
	}

	public Double getSystemamount() {
		return systemamount;
	}

	public void setSystemamount(Double systemamount) {
		this.systemamount = systemamount;
	}

	public Double getPcamount() {
		return pcamount;
	}

	public void setPcamount(Double pcamount) {
		this.pcamount = pcamount;
	}

	public Double getOtheramount() {
		return otheramount;
	}

	public void setOtheramount(Double otheramount) {
		this.otheramount = otheramount;
	}

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPartitionfield() {
		return partitionfield;
	}

	public void setPartitionfield(String partitionfield) {
		this.partitionfield = partitionfield;
	}

	public String getBusinessplatform() {
		return businessplatform;
	}

	public void setBusinessplatform(String businessplatform) {
		this.businessplatform = businessplatform;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	
	

}
