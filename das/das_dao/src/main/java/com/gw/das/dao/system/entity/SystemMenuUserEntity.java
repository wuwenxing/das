package com.gw.das.dao.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;

@Entity
@Table(name = "t_system_menu_user")
public class SystemMenuUserEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "menu_user_id", nullable = false)
	private Long menuUserId; // 菜单与用户关联ID

	@Column(name = "menu_id")
	private Long menuId;

	@Column(name = "user_id")
	private Long userId;

	public Long getMenuUserId() {
		return menuUserId;
	}

	public void setMenuUserId(Long menuUserId) {
		this.menuUserId = menuUserId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
