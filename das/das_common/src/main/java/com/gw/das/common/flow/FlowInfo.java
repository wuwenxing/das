package com.gw.das.common.flow;

import com.gw.das.common.enums.FlowPackageEnum;

/**
 * 流量实体对象
 * 
 * @author wayne
 */
public class FlowInfo {
	/**
	 * 流量充值记录ID-对应一次充值接口调用
	 */
	private Long flowLogId;
	/**
	 * 11位手机号码，多个手机号，号分隔
	 */
	private String phones;
	/**
	 * 充值流量包-对应亿美
	 */
	private FlowPackageEnum flowPackage;
	/**
	 * 充值流量编码-对应乐免
	 */
	private String flowSizeLm;
	/**
	 * 充值流量第三方交易id-对应容联
	 */
	private String customIdRl;
	/**
	 * 充值流量档位编码-对应容联
	 */
	private String flowNoRl;
	/**
	 * 充值流量大小-对应容联
	 */
	private String flowSizeRl;
	/**
	 * 业务平台
	 */
	private Long companyId;

	public Long getFlowLogId() {
		return flowLogId;
	}

	public void setFlowLogId(Long flowLogId) {
		this.flowLogId = flowLogId;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public FlowPackageEnum getFlowPackage() {
		return flowPackage;
	}

	public void setFlowPackage(FlowPackageEnum flowPackage) {
		this.flowPackage = flowPackage;
	}

	public String getFlowSizeLm() {
		return flowSizeLm;
	}

	public void setFlowSizeLm(String flowSizeLm) {
		this.flowSizeLm = flowSizeLm;
	}

	public String getCustomIdRl() {
		return customIdRl;
	}

	public void setCustomIdRl(String customIdRl) {
		this.customIdRl = customIdRl;
	}

	public String getFlowNoRl() {
		return flowNoRl;
	}

	public void setFlowNoRl(String flowNoRl) {
		this.flowNoRl = flowNoRl;
	}

	public String getFlowSizeRl() {
		return flowSizeRl;
	}

	public void setFlowSizeRl(String flowSizeRl) {
		this.flowSizeRl = flowSizeRl;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

}
