package com.gw.das.api.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * companyId枚举类型 外汇=1 恒信=2 贵金属=3 创富=8
 * 
 * @author wayne
 *
 */
public enum CompanyEnum implements EnumIntf {

	fx("外汇", "1"),
	hx("恒信", "2"),
	pm("贵金属", "3"),
	cf("创富", "8"),
	hxfx("hxfx", "9");
	
	private final String value;
	private final String labelKey;

	CompanyEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<CompanyEnum> getList() {
		List<CompanyEnum> result = new ArrayList<CompanyEnum>();
		for (CompanyEnum ae : CompanyEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static List<CompanyEnum> getList(String companyIds) {
		List<CompanyEnum> result = new ArrayList<CompanyEnum>();
		if (!StringUtils.isEmpty(companyIds)) {
			String[] companyIdAry = companyIds.split(",");
			for (String companyIdStr : companyIdAry) {
				CompanyEnum ce = CompanyEnum.find(companyIdStr);
				if (null != ce) {
					result.add(ce);
				}
			}
		}
		return result;
	}

	public static String getAllCompanyIds() {
		String companyIds = "";
		for (CompanyEnum ae : CompanyEnum.values()) {
			companyIds += ae.getLabelKey() + ",";
		}
		if (!StringUtils.isEmpty(companyIds)) {
			companyIds = companyIds.substring(0, companyIds.length() - 1);
		}
		return companyIds;
	}

	public static CompanyEnum find(String labelKey) {
		for (CompanyEnum ae : CompanyEnum.values()) {
			if (ae.getLabelKey().equals(labelKey)) {
				return ae;
			}
		}
		return null;
	}

	public static String format(String labelKey) {
		for (CompanyEnum ae : CompanyEnum.values()) {
			if (ae.getLabelKey().equals(labelKey)) {
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public Long getLabelKeyLong() {
		return Long.parseLong(labelKey);
	}

}
