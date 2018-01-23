package com.gw.das.dao.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;

@Entity
@Table(name = "t_system_menu")
public class SystemMenuEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "menu_id", nullable = false)
	private Long menuId; // 菜单ID

	@Column(name = "parent_menu_code", length = 25)
	private String parentMenuCode; // 父菜单编码

	@Column(name = "menu_code", length = 25)
	private String menuCode; // 菜单编码

	@Column(name = "menu_type", length = 25)
	private String menuType; // 菜单类型(0顶部页签\1菜单\2功能)

	@Column(name = "menu_name", length = 50)
	private String menuName; // 菜单名称

	@Column(name = "menu_url", length = 255)
	private String menuUrl; // 链接URL

	@Column(name = "order_code")
	private Long orderCode; // 排序号

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getParentMenuCode() {
		return parentMenuCode;
	}

	public void setParentMenuCode(String parentMenuCode) {
		this.parentMenuCode = parentMenuCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Long getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Long orderCode) {
		this.orderCode = orderCode;
	}
	
}
