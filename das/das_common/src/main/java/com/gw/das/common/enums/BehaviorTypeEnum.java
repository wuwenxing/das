package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 行为类型
 */
public enum BehaviorTypeEnum implements EnumIntf {
	
	visit("访问", "visit"),
	live("咨询", "live"),
	demo("模拟", "demo"),
	real("真实", "real"),
	depesit("入金", "depesit");
	
	private final String value;
	private final String labelKey;
	BehaviorTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<BehaviorTypeEnum> getList(){
		List<BehaviorTypeEnum> result = new ArrayList<BehaviorTypeEnum>();
		for(BehaviorTypeEnum ae : BehaviorTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static List<BehaviorTypeEnum> getList(String type){
		List<BehaviorTypeEnum> result = new ArrayList<BehaviorTypeEnum>();
		if("dasUserInfo".equals(type)){
			result.add(BehaviorTypeEnum.demo);
			result.add(BehaviorTypeEnum.real);
			result.add(BehaviorTypeEnum.depesit);
		}else if("dasUserScreen".equals(type)){
			result.add(BehaviorTypeEnum.demo);
			result.add(BehaviorTypeEnum.real);
			result.add(BehaviorTypeEnum.depesit);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(BehaviorTypeEnum ae : BehaviorTypeEnum.values()){
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
