package com.gw.das.netty;

/**
 * 调用接口失败类型定义
 * @author wayne
 *
 */
public enum RpcFailEnum {
	
	interfaceUrlError("调用接口地址错误，请检查"),
	paramIsNull("请求参数为空，请检查"),
	paramIsError("请求参数错误，请检查"),
	signCheckError("签权验证失败")
	;
	
	private final String value;

	RpcFailEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
