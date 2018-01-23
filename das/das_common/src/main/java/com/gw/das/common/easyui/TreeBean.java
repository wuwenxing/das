package com.gw.das.common.easyui;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 树形菜单Bean对象
 * @author wayne
 */
public class TreeBean{
	
	private String id;
	private String parentId;
	private String text;
	private Boolean checked;
	private JSONObject attributes;
	private List<TreeBean> child; // 设置功能类型的子节点
	private List<TreeBean> children; // 设置子节点
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public JSONObject getAttributes() {
		return attributes;
	}
	public void setAttributes(JSONObject attributes) {
		this.attributes = attributes;
	}
	public List<TreeBean> getChild() {
		return child;
	}
	public void setChild(List<TreeBean> child) {
		this.child = child;
	}
	public List<TreeBean> getChildren() {
		return children;
	}
	public void setChildren(List<TreeBean> children) {
		this.children = children;
	}
	
}