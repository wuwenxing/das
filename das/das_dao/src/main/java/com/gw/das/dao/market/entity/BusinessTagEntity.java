package com.gw.das.dao.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;


@Entity
@Table(name = "t_businessTag")
public class BusinessTagEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tag_id", nullable = false)
	private Long tagId;
	
	@Column(name = "tagContent", length = 10)
	private Integer tagContent;// 标签内容
	
	@Column(name = "tagType", length = 10)
	private Integer tagType;// 标签内容
	
	@Column(name = "tagUrl", length = 500)
	private String tagUrl;// 标签链接

	@Column(name = "eventCategory", length = 100)
	private String eventCategory;// 标签链接
	
	@Column(name = "eventAction", length = 100)
	private String eventAction;// 标签链接
	
	@Column(name = "eventLabel", length = 100)
	private String eventLabel;// 标签链接
	
	@Column(name = "eventValue", length = 100)
	private String eventValue;// 标签链接
	
	private String tagEvent; // 标签链接

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Integer getTagContent() {
		return tagContent;
	}

	public void setTagContent(Integer tagContent) {
		this.tagContent = tagContent;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public String getTagUrl() {
		return tagUrl;
	}

	public void setTagUrl(String tagUrl) {
		this.tagUrl = tagUrl;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public String getEventAction() {
		return eventAction;
	}

	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}

	public String getEventLabel() {
		return eventLabel;
	}

	public void setEventLabel(String eventLabel) {
		this.eventLabel = eventLabel;
	}

	public String getEventValue() {
		return eventValue;
	}

	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}

	public String getTagEvent() {
		return tagEvent;
	}

	public void setTagEvent(String tagEvent) {
		this.tagEvent = tagEvent;
	}

	

	
	
}
