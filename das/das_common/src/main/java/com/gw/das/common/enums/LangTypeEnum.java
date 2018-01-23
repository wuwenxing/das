package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典语言类型langType-子类型定义
 * @author wayne显示于前台
 */
public enum LangTypeEnum implements EnumIntf {
	zh_cn("中文-简体", "zh_cn"),
	zh_tw("中文-繁体", "zh_tw");
	
	private final String value;
	private final String labelKey;
	LangTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<LangTypeEnum> getList(){
		List<LangTypeEnum> result = new ArrayList<LangTypeEnum>();
		for(LangTypeEnum ae : LangTypeEnum.values()){
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
	
}
