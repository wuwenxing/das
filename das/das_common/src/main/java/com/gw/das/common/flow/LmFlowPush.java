package com.gw.das.common.flow;

/**
 * 乐免调用本系统-推送消息实体
 * 
 * @author wayne
 *
 */
public class LmFlowPush {
	/**
	 * [ { "mobile": "15510331875", "msgid": "1501081533100114", "time":
	 * "2015-01-08 15:33:10", "status": "00000" }, { "mobile": "15510331875",
	 * "msgid": "1501081533080113", "time": "2015-01-08 15:33:09", "status":
	 * "00000" } ]
	 */
	// 手机号
	private String mobile;
	// 结果订单-任务编号
	private String msgid;
	// 时间
	private String time;
	// 状态码-00000为成功
	private String status;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
