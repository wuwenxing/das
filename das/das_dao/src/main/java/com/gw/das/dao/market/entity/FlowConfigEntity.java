package com.gw.das.dao.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;


@Entity
@Table(name = "t_flow_config")
public class FlowConfigEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "flow_config_id", nullable = false)
	private Long flowConfigId;
	
	@Column(name = "flow_channel", length = 100)
	private String flowChannel;// 流量通道

	public Long getFlowConfigId() {
		return flowConfigId;
	}

	public void setFlowConfigId(Long flowConfigId) {
		this.flowConfigId = flowConfigId;
	}

	public String getFlowChannel() {
		return flowChannel;
	}

	public void setFlowChannel(String flowChannel) {
		this.flowChannel = flowChannel;
	}
	
}
