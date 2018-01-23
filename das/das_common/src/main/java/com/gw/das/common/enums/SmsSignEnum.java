package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信签名配置枚举
 */
public enum SmsSignEnum implements EnumIntf {
	
	jd("【金道】", "jd"),
	jdgwfx("【金道GWFX】", "jdgwfx"),
	jdgjs("【金道贵金属】", "jdgjs"),
	hxgjs("【恒信贵金属】", "hxgjs"),
	cf("【创富】", "cf")
	;
	
	private final String value;
	private final String labelKey;
	SmsSignEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SmsSignEnum> getList(Long companyId){
		List<SmsSignEnum> result = new ArrayList<SmsSignEnum>();
		if(companyId == 1){
			result.add(SmsSignEnum.jd);
			result.add(SmsSignEnum.jdgwfx);
		}else if(companyId == 2){
			result.add(SmsSignEnum.jdgjs);
		}else if(companyId == 3){
			result.add(SmsSignEnum.hxgjs);
		}else if(companyId == 4){
			result.add(SmsSignEnum.cf);
		}
		return result;
	}

	public static String format(String labelKey){
		for(SmsSignEnum ae : SmsSignEnum.values()){
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
