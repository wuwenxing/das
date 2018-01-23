package com.gw.das.common.easyui;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形字典Bean对象
 * 
 * @author wayne
 */
public class DictTreeBean {

	/** 主键 */
	private String id;

	/** 父节点code */
	private String parentDictCode;

	/** 字典编码 */
	private String dictCode;

	/** 字典名称 */
	private String dictName;

	/** 字典启用禁用状态 */
	private String enableFlag;

	/** 字典折叠状态 */
	private String state;

	/** 排序 */
	private Integer sort;

	/** 字典类型： 分数字字典 1、数据字典分类2 两种 */
	private String type;

	/** 子字典 */
	private List<DictTreeBean> children = new ArrayList<DictTreeBean>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentDictCode() {
		return parentDictCode;
	}

	public void setParentDictCode(String parentDictCode) {
		this.parentDictCode = parentDictCode;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<DictTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<DictTreeBean> children) {
		this.children = children;
	}



}