package com.gw.das.common.token;

/**
 * 验证Token时返回的结果对象
 * 
 * @author wayne
 *
 */
public class VerifyTokenResult {

	private String code;// (0表示成功，-1表示失败,其他则为对应错误号)
	private String msg;// "ok!",
	private Object data;// {}或者[]或者没有data属性

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
