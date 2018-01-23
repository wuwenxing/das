package com.gw.das.api.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号类型(N:真实用户 ,A:激活用户)
 * 
 * @author darren
 *
 */
public enum AccountTypeEnum implements EnumIntf {

	N("N", "真实"),
	D("D", "模拟"),
	A("A", "激活");
	
	private final String value;
	private final String labelKey;

	AccountTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<AccountTypeEnum> getList() {
		List<AccountTypeEnum> result = new ArrayList<AccountTypeEnum>();
		for (AccountTypeEnum ae : AccountTypeEnum.values()) {
			result.add(ae);
		}
		return result;
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
