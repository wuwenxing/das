package com.gw.das.dao.trade.bean;

/**
 * 交易人数/次数VO类
 * 
 * @author darren
 * @since 2017-04-13
 */
public class DealprofitdetailUseraAndDealamountVO {

	/* rowkey */
	private String rowkey;
	
	/* 执行时间 */
	private String exectime;
	
	/* 交易人次 */
	private String useramount;
	
	/* 交易次數 */
	private String dealamount;
	
	/* mt4交易人次 */
	private String mt4useramount;
	
	/* mt4交易次數 */
	private String mt4dealamount;
	
	/* gts2交易人次 */
	private String gts2useramount;
	
	/* gts2交易次數 */
	private String gts2dealamount;
	
	/* 新旧平台标识 */
	private String source;
	
	/* 平台类型 */
	private String platform;
	
	/* 公司ID */
	private String companyid;
	
	/* 业务标识 */
	private String businessplatform;
	
	/* 分区字段(按年分) */
	private String partitionfield;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getExectime() {
		return exectime;
	}

	public void setExectime(String exectime) {
		this.exectime = exectime;
	}

	public String getUseramount() {
		return useramount;
	}

	public void setUseramount(String useramount) {
		this.useramount = useramount;
	}

	public String getDealamount() {
		return dealamount;
	}

	public void setDealamount(String dealamount) {
		this.dealamount = dealamount;
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

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
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

	public String getMt4useramount() {
		return mt4useramount;
	}

	public void setMt4useramount(String mt4useramount) {
		this.mt4useramount = mt4useramount;
	}

	public String getMt4dealamount() {
		return mt4dealamount;
	}

	public void setMt4dealamount(String mt4dealamount) {
		this.mt4dealamount = mt4dealamount;
	}

	public String getGts2useramount() {
		return gts2useramount;
	}

	public void setGts2useramount(String gts2useramount) {
		this.gts2useramount = gts2useramount;
	}

	public String getGts2dealamount() {
		return gts2dealamount;
	}

	public void setGts2dealamount(String gts2dealamount) {
		this.gts2dealamount = gts2dealamount;
	}

}
