package com.gw.das.common.sms;

import java.util.ArrayList;
import java.util.List;

/**
 * 秒嘀科技-短信发送-返回实体
 * 
 * @author wayne
 *
 */
public class MdkjSmsResponse {

	private String respCode;
	private String failCount;
	private List<MdkjSmsResponseDetail> failList = new ArrayList<MdkjSmsResponseDetail>();
	private String smsId;

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getFailCount() {
		return failCount;
	}

	public void setFailCount(String failCount) {
		this.failCount = failCount;
	}

	public List<MdkjSmsResponseDetail> getFailList() {
		return failList;
	}

	public void setFailList(List<MdkjSmsResponseDetail> failList) {
		this.failList = failList;
	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

}
