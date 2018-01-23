package com.gw.das.common.response;

/**
 * 公共返回类
 * 
 * @author kirin.guan
 *
 */
public class CommonResponse {
	// 返回码
	private String ret_code = "0";

	// 返回描述
	private String ret_msg = "successful";

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

}
