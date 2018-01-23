package com.gw.das.dao.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;


@Entity
@Table(name = "t_sms_config")
public class SmsConfigEntity extends BaseEntity {

	private static final long serialVersionUID = -526223092850259345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sms_config_id", nullable = false)
	private Long smsConfigId;
	
	@Column(name = "sign", length = 100)
	private String sign;// 短信签名
	
	@Column(name = "sms_channel", length = 100)
	private String smsChannel;// 短信通道

	public Long getSmsConfigId() {
		return smsConfigId;
	}

	public void setSmsConfigId(Long smsConfigId) {
		this.smsConfigId = smsConfigId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSmsChannel() {
		return smsChannel;
	}

	public void setSmsChannel(String smsChannel) {
		this.smsChannel = smsChannel;
	}

}
