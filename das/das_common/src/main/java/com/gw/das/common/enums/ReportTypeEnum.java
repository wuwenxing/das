package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 统计报表类型定义
 */
public enum ReportTypeEnum implements EnumIntf {

	hours("时统计", "hours"),
	days("日统计", "days"),
	weeks("周统计", "weeks"),
	months("月统计", "months");
	
	private final String value;
	private final String labelKey;
	
	ReportTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<ReportTypeEnum> getList() {
		List<ReportTypeEnum> result = new ArrayList<ReportTypeEnum>();
		for (ReportTypeEnum ae : ReportTypeEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static List<ReportTypeEnum> getList2() {
		List<ReportTypeEnum> result = new ArrayList<ReportTypeEnum>();
		result.add(ReportTypeEnum.days);
		result.add(ReportTypeEnum.weeks);
		result.add(ReportTypeEnum.months);
		return result;
	}
	
	public static List<ReportTypeEnum> getList3() {
		List<ReportTypeEnum> result = new ArrayList<ReportTypeEnum>();
		result.add(ReportTypeEnum.days);
		result.add(ReportTypeEnum.months);
		return result;
	}
	
	public static List<ReportTypeEnum> getList4() {
		List<ReportTypeEnum> result = new ArrayList<ReportTypeEnum>();
		result.add(ReportTypeEnum.hours);
		result.add(ReportTypeEnum.days);
		return result;
	}

	public static String format(String labelKey) {
		if (null != labelKey) {
			for (ReportTypeEnum ae : ReportTypeEnum.values()) {
				if (ae.getLabelKey().equals(labelKey)) {
					return ae.getValue();
				}
			}
		}
		return labelKey;
	}

	public String getValue() {
		return this.value;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
