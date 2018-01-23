package com.gw.das.api.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gw.das.api.common.enums.AccountTypeEnum;
import com.gw.das.api.common.enums.EnumIntf;

public enum ProfilesActiveEnum implements EnumIntf {

	DEV("dev", "dev"),
	PROD("prod", "prod");
	
	private final String value;
	private final String labelKey;

	ProfilesActiveEnum(String _operator, String labelKey) {
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
