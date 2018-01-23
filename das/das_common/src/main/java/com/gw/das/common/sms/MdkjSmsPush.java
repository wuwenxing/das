package com.gw.das.common.sms;

import java.util.ArrayList;
import java.util.List;

/**
 * 秒嘀科技调用本系统-推送消息实体
 * 
 * @author wayne
 *
 */
public class MdkjSmsPush {

	private List<MdkjSmsPushDetail> smsResult = new ArrayList<MdkjSmsPushDetail>();

	public List<MdkjSmsPushDetail> getSmsResult() {
		return smsResult;
	}

	public void setSmsResult(List<MdkjSmsPushDetail> smsResult) {
		this.smsResult = smsResult;
	}

}
