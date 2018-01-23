package com.gw.das.business.dao.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.dao.base.BaseSearchModel;

/**
 * 风险要素列表
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DimBlackListSearchBean extends BaseSearchModel {

	// 日期-开始日期
	private String startTime;

	// 日期-结束日期
	private String endTime;

	// 标记日期-开始日期
	private String startMarkTime;

	// 标记日期-结束日期
	private String endMarkTime;

	// 来源标记(1自动,2手工,3自动&手工)
	private String source;

	// 类型(account_no,account_name,mobile,id_card_md5,email,ip)
	private String type;

	// 类型值(手机号时去掉前缀)
	private String value;

	// 风险类型(白名单:0;封禁:1;风险:2;查询无信息:3)
	private String riskType;

	// 风险备注
	private String remark;

	// 风险备注
	private String remarkEn;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartMarkTime() {
		return startMarkTime;
	}

	public void setStartMarkTime(String startMarkTime) {
		this.startMarkTime = startMarkTime;
	}

	public String getEndMarkTime() {
		return endMarkTime;
	}

	public void setEndMarkTime(String endMarkTime) {
		this.endMarkTime = endMarkTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemarkEn() {
		return remarkEn;
	}

	public void setRemarkEn(String remarkEn) {
		this.remarkEn = remarkEn;
	}

}
