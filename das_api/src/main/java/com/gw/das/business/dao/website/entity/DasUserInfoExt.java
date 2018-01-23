package com.gw.das.business.dao.website.entity;

import com.gw.das.business.common.orm.Column;

/**
 * 对应表das_user_info_ext
 * 
 * @author wayne
 */
public class DasUserInfoExt {

	@Column(name = "rowkey")
	private String rowKey;

	@Column(name = "userid")
	private String userId;

	@Column(name = "advisorycountlive800")
	private String advisoryCountLive800;

	@Column(name = "advisorycountqq")
	private String advisoryCountQQ;

	@Column(name = "visitcountpc")
	private String visitCountPC;

	@Column(name = "visitcountmobile")
	private String visitCountMobile;

	@Column(name = "downloadcountpc")
	private String downloadCountPc;

	@Column(name = "downloadcountmobile")
	private String downloadCountMobile;

	@Column(name = "smssuccess")
	private String smsSuccess;

	@Column(name = "smsfail")
	private String smsFail;

	@Column(name = "tagurllist")
	private String tagUrlList;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessplatform")
	private int businessPlatform;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdvisoryCountLive800() {
		return advisoryCountLive800;
	}

	public void setAdvisoryCountLive800(String advisoryCountLive800) {
		this.advisoryCountLive800 = advisoryCountLive800;
	}

	public String getAdvisoryCountQQ() {
		return advisoryCountQQ;
	}

	public void setAdvisoryCountQQ(String advisoryCountQQ) {
		this.advisoryCountQQ = advisoryCountQQ;
	}

	public String getVisitCountPC() {
		return visitCountPC;
	}

	public void setVisitCountPC(String visitCountPC) {
		this.visitCountPC = visitCountPC;
	}

	public String getVisitCountMobile() {
		return visitCountMobile;
	}

	public void setVisitCountMobile(String visitCountMobile) {
		this.visitCountMobile = visitCountMobile;
	}

	public String getDownloadCountPc() {
		return downloadCountPc;
	}

	public void setDownloadCountPc(String downloadCountPc) {
		this.downloadCountPc = downloadCountPc;
	}

	public String getDownloadCountMobile() {
		return downloadCountMobile;
	}

	public void setDownloadCountMobile(String downloadCountMobile) {
		this.downloadCountMobile = downloadCountMobile;
	}

	public String getSmsSuccess() {
		return smsSuccess;
	}

	public void setSmsSuccess(String smsSuccess) {
		this.smsSuccess = smsSuccess;
	}

	public String getSmsFail() {
		return smsFail;
	}

	public void setSmsFail(String smsFail) {
		this.smsFail = smsFail;
	}

	public String getTagUrlList() {
		return tagUrlList;
	}

	public void setTagUrlList(String tagUrlList) {
		this.tagUrlList = tagUrlList;
	}

	public int getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(int businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
