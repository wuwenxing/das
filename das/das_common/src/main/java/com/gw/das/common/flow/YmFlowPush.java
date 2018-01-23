package com.gw.das.common.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * 亿美调用本系统-推送消息实体
 * 
 * @author wayne
 *
 */
public class YmFlowPush {

	private String batchNo; // 批次号,与接收的批次号一致
	private int successCount; // 成功数量
	private int failCount; // 失败数量
	// 号码列表 如没有失败 errorlist为空
	private List<YmFlowPushDetail> errorlist = new ArrayList<YmFlowPushDetail>();

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public List<YmFlowPushDetail> getErrorlist() {
		return errorlist;
	}

	public void setErrorlist(List<YmFlowPushDetail> errorlist) {
		this.errorlist = errorlist;
	}
}
