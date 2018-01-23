package com.gw.das.dao.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;


@Entity
@Table(name = "t_blacklist")
public class BlacklistEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "blacklist_id", nullable = false)
	private Long blacklistId;
	
	@Column(name = "account", length = 100)
	private String account;// 黑名单账户
	
	@Column(name = "account_type", length = 100)
	private String accountType;// 账户类型
	
	@Column(name = "blacklist_type", length = 100)
	private String blacklistType;// 黑名单类型

	public Long getBlacklistId() {
		return blacklistId;
	}

	public void setBlacklistId(Long blacklistId) {
		this.blacklistId = blacklistId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBlacklistType() {
		return blacklistType;
	}

	public void setBlacklistType(String blacklistType) {
		this.blacklistType = blacklistType;
	}
	
}
