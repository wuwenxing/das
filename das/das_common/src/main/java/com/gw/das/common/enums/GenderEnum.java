package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 性别类型
 */
public enum GenderEnum implements EnumIntf {

	male("男","male"),
	female("女","female");
	
	private final String value;
	private final String labelKey;
	GenderEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<GenderEnum> getList(){
		List<GenderEnum> result = new ArrayList<GenderEnum>();
		for(GenderEnum ae : GenderEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static Map<String, String> getMap(){
		Map<String, String> map = new HashMap<String, String>();
		for(GenderEnum ae : GenderEnum.values()){
			map.put(ae.getLabelKey(), ae.getValue());
		}
		return map;
	}

	public String getValue() {
        return this.value;
    }
	public String getLabelKey() {
        return this.labelKey;
    }
	
}
