package com.gw.das.api.common.response;

/**
 * 调用接口失败枚举类型
 * @author wayne
 */
public enum ApiFailEnum {

	signCheckError("签权验证失败"),
	signCheckError1("签权验证失败-参数companyId为空"),
	signCheckError2("签权验证失败-参数companyId无效"),
	signCheckError3("签权验证失败-参数token为空"),
	signCheckError4("签权验证失败-参数token无效"),
	;
	
	private final String value;

	ApiFailEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
