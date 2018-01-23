package com.gw.das.dao.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;


@Entity
@Table(name = "t_channel")
public class ChannelEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "channel_id", nullable = false)
	private Long channelId;
	
	@Column(name = "channel_name", length = 100)
	private String channelName;// 渠道名称
	
	@Column(name = "channel_type", length = 100)
	private String channelType;// 渠道类型
	
	@Column(name = "channel_group", length = 100)
	private String channelGroup;// 渠道分组
	
	@Column(name = "channel_level", length = 100)
	private String channelLevel;// 渠分分级
	
	@Column(name = "utmcsr", length = 100)
	private String utmcsr;// 来源
	
	@Column(name = "utmcmd", length = 100)
	private String utmcmd;// 媒介
	
	@Column(name = "isPay")
	private Long isPay;// 1免费、2付费、3其他

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelGroup() {
		return channelGroup;
	}

	public void setChannelGroup(String channelGroup) {
		this.channelGroup = channelGroup;
	}

	public String getChannelLevel() {
		return channelLevel;
	}

	public void setChannelLevel(String channelLevel) {
		this.channelLevel = channelLevel;
	}

	public String getUtmcsr() {
		return utmcsr;
	}

	public void setUtmcsr(String utmcsr) {
		this.utmcsr = utmcsr;
	}

	public String getUtmcmd() {
		return utmcmd;
	}

	public void setUtmcmd(String utmcmd) {
		this.utmcmd = utmcmd;
	}

	public Long getIsPay() {
		return isPay;
	}

	public void setIsPay(Long isPay) {
		this.isPay = isPay;
	}
	
}
