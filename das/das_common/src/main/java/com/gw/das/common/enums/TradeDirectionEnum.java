package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易方向
 */
public enum TradeDirectionEnum implements EnumIntf {

	buy("1", "买"),
	sell("2", "卖");

	private final String value;
	private final String labelKey;

	TradeDirectionEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<TradeDirectionEnum> getList() {
		List<TradeDirectionEnum> result = new ArrayList<TradeDirectionEnum>();
		for (TradeDirectionEnum ae : TradeDirectionEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		for(TradeDirectionEnum ae : TradeDirectionEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public static String getValue(String labelKey){
		for(TradeDirectionEnum ae : TradeDirectionEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return "";
	}

	public static String getKey(String value){
		for(TradeDirectionEnum ae : TradeDirectionEnum.values()){
			if(ae.getValue().equals(value)){
				return ae.getLabelKey();
			}
		}
		return "";
	}
	
	public String getValue() {
		return this.value;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
