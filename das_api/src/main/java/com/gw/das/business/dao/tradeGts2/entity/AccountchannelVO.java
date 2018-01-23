package com.gw.das.business.dao.tradeGts2.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 新开户_激活途径比例VO类
 * 
 * @author darren
 * @since 2017-05-19
 */
public class AccountchannelVO extends BaseModel {

	/* rowkey */
	@Column(name = "rowkey")
	private String rowkey;
	
	/* 新开账户途径webPC数量 */
	@Column(name = "webpc")
	private Double webpc = 0D;
	
	/* 新开账户途径Android数量 */
	@Column(name = "android")
	private Double android = 0D;
	
	/* 新开账户途径IOS数量 */
	@Column(name = "webios")
	private Double webios = 0D;
	
	/* 新开账户途径移动其他数量 */
	@Column(name = "webother")
	private Double webother = 0D;
	
	/* 新开账户途径移动其他数量 */
	@Column(name = "webothers")
	private Double webothers = 0D;
	
	/* 新开账户途径后台数量 */
	@Column(name = "backoffice")
	private Double backoffice = 0D;
	
	/* 新开账户途径转移数量	 */
	@Column(name = "trans")
	private Double trans = 0D;
	
	/* 新开账户途径金管家网站数量 */
	@Column(name = "kinweb")
	private Double kinweb = 0D;
	
	/* 新开账户途径资料丢失数量 */
	@Column(name = "loseinfo")
	private Double loseinfo = 0D;
	
	/*  激活账户PC途径数量*/
	@Column(name = "pc")
	private Double pc = 0D;
	
	/* 激活账户MOBILE途径数量 */
	@Column(name = "mobile")
	private Double mobile = 0D;
	
	/* 激活账户其他途径数量*/
	@Column(name = "other")
	private Double other = 0D;
	
	/* 激活账户其他途径数量*/
	@Column(name = "others")
	private Double others = 0D;
	
	/* 公司ID */
	@Column(name = "companyid")
	private String companyid;
	
	/* 业务标识 */
	@Column(name = "businessplatform")
	private String businessplatform;
	
	/* 平台类型 */
	@Column(name = "platform")
	private String platform;
	
	/* 新旧平台标识 */
	@Column(name = "source")
	private String source;
	
	/* 执行时间*/
	@Column(name = "exectime")
	private String exectime;
	
	/* 分区字段(按年分)*/
	@Column(name = "partitionfield")
	private String partitionfield;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public Double getWebpc() {
		return webpc;
	}

	public void setWebpc(Double webpc) {
		this.webpc = webpc;
	}

	public Double getAndroid() {
		return android;
	}

	public void setAndroid(Double android) {
		this.android = android;
	}

	public Double getWebios() {
		return webios;
	}

	public void setWebios(Double webios) {
		this.webios = webios;
	}

	public Double getWebother() {
		return webother;
	}

	public void setWebother(Double webother) {
		this.webother = webother;
	}

	public Double getBackoffice() {
		return backoffice;
	}

	public void setBackoffice(Double backoffice) {
		this.backoffice = backoffice;
	}

	public Double getTrans() {
		return trans;
	}

	public void setTrans(Double trans) {
		this.trans = trans;
	}

	public Double getKinweb() {
		return kinweb;
	}

	public void setKinweb(Double kinweb) {
		this.kinweb = kinweb;
	}

	public Double getLoseinfo() {
		return loseinfo;
	}

	public void setLoseinfo(Double loseinfo) {
		this.loseinfo = loseinfo;
	}

	public Double getPc() {
		return pc;
	}

	public void setPc(Double pc) {
		this.pc = pc;
	}

	public Double getMobile() {
		return mobile;
	}

	public void setMobile(Double mobile) {
		this.mobile = mobile;
	}

	public Double getOther() {
		return other;
	}

	public void setOther(Double other) {
		this.other = other;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getExectime() {
		return exectime;
	}

	public void setExectime(String exectime) {
		this.exectime = exectime;
	}

	public String getPartitionfield() {
		return partitionfield;
	}

	public void setPartitionfield(String partitionfield) {
		this.partitionfield = partitionfield;
	}

	public Double getWebothers() {
		return webothers;
	}

	public void setWebothers(Double webothers) {
		this.webothers = webothers;
	}

	public Double getOthers() {
		return others;
	}

	public void setOthers(Double others) {
		this.others = others;
	}

	
	

}
