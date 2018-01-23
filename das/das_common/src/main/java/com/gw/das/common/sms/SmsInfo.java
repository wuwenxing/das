package com.gw.das.common.sms;

/**
 * 短信实体对象
 * 
 * @author wayne
 */
public class SmsInfo {
	/**
	 * 短信模板ID
	 */
	private Long templateId;
	/**
	 * 短信详细ID
	 */
	private Long templateDetailId;
	/**
	 * 业务平台
	 */
	private Long companyId;
	/**
	 * 渠道交易订单号，由渠道系统自己保持在本渠道内唯一
	 */
	private String tradeNo;
	/**
	 * 11位用户手机号码，多个手机号，号分隔
	 */
	private String phones;
	/**
	 * 短信内容
	 */
	private String content;
	/**
	 * 缓存的sid
	 */
	private String hxSid;
	/**
	 * 发送人账号(接口如果传入，则记录，实现需求推荐发送短信次数计数功能)
	 */
	private String sendNo;

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemplateDetailId() {
		return templateDetailId;
	}

	public void setTemplateDetailId(Long templateDetailId) {
		this.templateDetailId = templateDetailId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHxSid() {
		return hxSid;
	}

	public void setHxSid(String hxSid) {
		this.hxSid = hxSid;
	}

	public String getSendNo() {
		return sendNo;
	}

	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
	}

}
