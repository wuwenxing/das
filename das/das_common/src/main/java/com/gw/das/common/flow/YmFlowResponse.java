package com.gw.das.common.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * 亿美流量充值-返回实体
 * 
 * @author wayne
 *
 */
public class YmFlowResponse {
	/**
	 * result:{"code":"M0006","msg":"解密校验失败","batchNo":null,
	 * "mobileNumber":null,"errorMobiles":null}
	 */
	// 状态码
	private String code;
	// 错误信息，成功的话，此处为空
	private String msg;
	// 批次号，我方放回的批次号，回调以此为准
	private String batchNo;
	// 能够处理的号码总数
	private int mobileNumber;
	// 错误号码，检测到提供的错误不能处理的号码
	private List<String> errorMobiles = new ArrayList<String>();
	
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public List<String> getErrorMobiles() {
		return errorMobiles;
	}

	public void setErrorMobiles(List<String> errorMobiles) {
		this.errorMobiles = errorMobiles;
	}

	public int getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
