package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号类型
 */
public enum AccountTypeEnum implements EnumIntf {
	
	phone("手机号码", "phone"),
	email("邮箱", "email");
	
	private final String value;
	private final String labelKey;
	AccountTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<AccountTypeEnum> getList(){
		List<AccountTypeEnum> result = new ArrayList<AccountTypeEnum>();
		for(AccountTypeEnum ae : AccountTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(AccountTypeEnum ae : AccountTypeEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
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
	
}
