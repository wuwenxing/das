package com.gw.das.dao.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gw.das.dao.base.BaseEntity;


@Entity
@Table(name = "t_system_user")
public class SystemUserEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "user_no", length = 50)
	private String userNo;
	
	@Column(name = "user_name", length = 50)
	private String userName;
	
	@Column(name = "password", length = 50)
	private String password;

	@Column(name = "remark", length = 200)
	private String remark; // 备注

	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "company_ids", length = 100)
	private String companyIds;// 该用户拥有的业务类型，多个逗号隔开
	
	@Transient
	private boolean checked = false;
	@Transient
	private boolean roleIsNull = false;
	@Transient
	private Long menuId;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isRoleIsNull() {
		return roleIsNull;
	}

	public void setRoleIsNull(boolean roleIsNull) {
		this.roleIsNull = roleIsNull;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}

}
