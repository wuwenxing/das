package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 账户诊断API对应公司枚举
 * companyId枚举类型 外汇=1 恒信=2 贵金属=3 创富=8
 * 
 * @author wayne
 *
 */
public enum APICompanyEnum implements EnumIntf {

	gw("集团", "0"),
	fx("外汇", "1"),
	hx("恒信", "2"),
	pm("贵金属", "3"),
	cf("创富", "8");
	
	private final String value;
	private final String labelKey;

	APICompanyEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<APICompanyEnum> getList() {
		List<APICompanyEnum> result = new ArrayList<APICompanyEnum>();
		for (APICompanyEnum ae : APICompanyEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static List<APICompanyEnum> getList(String companyIds) {
		List<APICompanyEnum> result = new ArrayList<APICompanyEnum>();
		if (!StringUtils.isEmpty(companyIds)) {
			String[] companyIdAry = companyIds.split(",");
			for (String companyIdStr : companyIdAry) {
				APICompanyEnum ce = APICompanyEnum.find(companyIdStr);
				if (null != ce) {
					result.add(ce);
				}
			}
		}
		return result;
	}

	public static String getAllCompanyIds() {
		String companyIds = "";
		for (APICompanyEnum ae : APICompanyEnum.values()) {
			companyIds += ae.getLabelKey() + ",";
		}
		if (!StringUtils.isEmpty(companyIds)) {
			companyIds = companyIds.substring(0, companyIds.length() - 1);
		}
		return companyIds;
	}

	public static APICompanyEnum find(String labelKey) {
		for (APICompanyEnum ae : APICompanyEnum.values()) {
			if (ae.getLabelKey().equals(labelKey)) {
				return ae;
			}
		}
		return null;
	}

	public static String format(String labelKey) {
		for (APICompanyEnum ae : APICompanyEnum.values()) {
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
