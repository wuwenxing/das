package com.gw.das.dao.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CBoard对应的用户实体表
 * 
 * @author wayne
 *
 */
@Entity
@Table(name = "dashboard_user")
public class DashboardUserEntity implements java.io.Serializable{

	private static final long serialVersionUID = 6112775526354625646L;

	@Id
	@Column(name = "user_id", length = 50, nullable = false)
	private String userId;

	@Column(name = "login_name", length = 100)
	private String loginName;

	@Column(name = "user_name", length = 100)
	private String userName;

	@Column(name = "user_password", length = 100)
	private String userPassword;

	@Column(name = "user_status", length = 100)
	private String userStatus;

	@Column(name = "company_ids", length = 100)
	private String companyIds;// 该用户拥有的业务类型，多个逗号隔开

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}

}
