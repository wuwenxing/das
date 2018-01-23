package com.gw.das.dao.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;

@Entity
@Table(name = "t_email_template_detail_content")
public class EmailTemplateDetailContentEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "content_id", nullable = false)
	private Long contentId;

	@Column(name = "content", columnDefinition="MEDIUMTEXT")
	private String content;// 发送的邮件内容

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
