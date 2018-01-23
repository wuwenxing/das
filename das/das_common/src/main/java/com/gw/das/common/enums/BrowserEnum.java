package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：浏览器类型定义
 */
public enum BrowserEnum implements EnumIntf {
	IE11("IE11", "IE11", "rv:11.0"),
	IE10("IE10", "IE10", "MSIE 10.0"),
	IE9("IE9", "IE9", "MSIE 9.0"),
	IE8("IE8", "IE8", "MSIE 8.0"),
	IE7("IE7", "IE7", "MSIE 7.0"),
	IE6("IE6", "IE6", "MSIE 6.0"),
	Chrome("Chrome", "Chrome", "Chrome"),
	Firefox("Firefox", "Firefox", "Firefox"),
	Safari("Safari", "Safari", ""), // 不包含Chrome但包含Safari字符串
	Opera("Opera", "Opera", "Opera"),
	QiTa("其他", "Other", ""); // 不包含以上字符串
	
	private final String value;
	private final String labelKey;
	private final String condition;//模糊查询条件
	BrowserEnum(String _operator, String labelKey, String condition) {
		this.value = _operator;
		this.labelKey = labelKey;
		this.condition = condition;
	}
	
	public static List<BrowserEnum> getList(){
		List<BrowserEnum> result = new ArrayList<BrowserEnum>();
		for(BrowserEnum ae : BrowserEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public String getCondition() {
		return condition;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
