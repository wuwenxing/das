package com.gw.das.common.enums;

/**
 * session key枚举类型
 * @author wayne
 */
public enum SessionKeyEnum implements EnumIntf {

	companyId("业务类型", "companyId"),
	client("客戶端对象", "client"),
	captcha("验证码", "captcha"),
	superAdminFlag("是否是超级管理员标示(Y/N)", "superAdminFlag"),
	menuMap("拥有权限的菜单Map集合", "menuMap");
	
	private final String value;
	private final String labelKey;
	
	SessionKeyEnum(String value, String labelKey) {
		this.value = value;
		this.labelKey = labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

}
