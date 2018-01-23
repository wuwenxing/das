package com.gw.das.common.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * 乐免流量充值-返回实体
 * 
 * @author wayne
 *
 */
public class LmFlowResponse {
	/**
	 * result:{ "status": "1", "description": "Ok", "reports": [ { "mobile":
	 * "15510331875", "msgid": "1501081533100114", "time": "2015-01-08 15:33:10"
	 * , "status": "00000" }, { "mobile": "15510331875", "msgid":
	 * "1501081533080113", "time": "2015-01-08 15:33:09", "status": "00000" } ]
	 * }
	 */
	// 状态码-1：成功;其他：失败
	private String status;
	// 结果订单-任务编号
	private String msgid;
	// 描述信息
	private String description;
	// 订购结果列表
	private List<LmFlowResponseDetail> reports = new ArrayList<LmFlowResponseDetail>();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<LmFlowResponseDetail> getReports() {
		return reports;
	}

	public void setReports(List<LmFlowResponseDetail> reports) {
		this.reports = reports;
	}

}
