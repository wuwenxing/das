package com.gw.das.common.email;

/**
 * 邮件实体对象
 * 
 * @author wayne
 */
public class EmailInfo {

	/**
	 * 邮件模板ID-生成的唯一主键
	 */
	private Long templateId;
	/**
	 * 邮件详细ID-生成的唯一主键
	 */
	private Long templateDetailId;
	/**
	 * 业务平台
	 */
	private Long companyId;
	/**
	 * 发件人
	 */
	private String from;
	/**
	 * 收件人
	 */
	private String[] to;
	/**
	 * 抄送的人
	 */
	private String[] cc;
	/**
	 * 邮件内容
	 */
	private String content;
	/**
	 * 邮件标题
	 */
	private String title;
	/**
	 * 更新人
	 */
	private String loginName;
	/**
	 * 更新IP
	 */
	private String loginIp;
	/**
	 * 邮件地址标示；1-使用注册服务邮件地址发送；2-使用推广邮件地址发送；默认使用推广邮件地址
	 */
	private String urlFlag = "2";
	
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getUrlFlag() {
		return urlFlag;
	}

	public void setUrlFlag(String urlFlag) {
		this.urlFlag = urlFlag;
	}

}
