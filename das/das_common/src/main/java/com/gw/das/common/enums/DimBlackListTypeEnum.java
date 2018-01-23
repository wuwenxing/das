package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 风控要素类型
 */
public enum DimBlackListTypeEnum implements EnumIntf {
	
	mobile("手机号码", "mobile"),
	email("邮箱", "email"),
	accountNo("账户", "account_no"),
	accountName("账户名", "account_name"),
	idCard("身份证号", "id_card"),
	idCardMd5("身份证号MD5", "id_card_md5"),
	ip("IP", "ip");
	
	private final String value;
	private final String labelKey;
	DimBlackListTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<DimBlackListTypeEnum> getList(){
		List<DimBlackListTypeEnum> result = new ArrayList<DimBlackListTypeEnum>();
		for(DimBlackListTypeEnum ae : DimBlackListTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(DimBlackListTypeEnum ae : DimBlackListTypeEnum.values()){
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
