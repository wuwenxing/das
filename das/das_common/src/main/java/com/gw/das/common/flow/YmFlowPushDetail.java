package com.gw.das.common.flow;

/**
 * 亿美调用本系统-推送消息实体 -失败号码列表 code对应代码描述 M0001 成功 M0002 无法解析请求错误 M0003 套餐选择错误 M0004
 * IP未注册错误 M0005 APPID不识别错误 M0006 解密校验失败错误 M0007 余额不足错误 M0008 无可用号码错误 M0009
 * 请求被拒绝错误 M9999 其他错误
 * 
 * @author wayne
 *
 */
public class YmFlowPushDetail {

	private String mobile; // 手机号
	private String code; // 状态码
	private String message; // 错误信息

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
