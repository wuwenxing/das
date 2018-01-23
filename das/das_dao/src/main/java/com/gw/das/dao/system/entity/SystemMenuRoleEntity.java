package com.gw.das.dao.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;

@Entity
@Table(name = "t_system_menu_role")
public class SystemMenuRoleEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "menu_role_id", nullable = false)
	private Long menuRoleId; // 菜单角色关联ID

	@Column(name = "menu_id")
	private Long menuId;

	@Column(name = "role_id")
	private Long roleId;

	public Long getMenuRoleId() {
		return menuRoleId;
	}

	public void setMenuRoleId(Long menuRoleId) {
		this.menuRoleId = menuRoleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
}
