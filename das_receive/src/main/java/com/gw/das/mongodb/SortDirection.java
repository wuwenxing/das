package com.gw.das.mongodb;

/**
 * 摘要：排序对象
 * @author Gavin.guo
 * @date   2015年2月4日
 */
public enum SortDirection {
	ASC("asc"),
	DESC("desc");
	private String value;
	SortDirection(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
