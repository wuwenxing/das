package com.gw.das.dao.website.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 对应表das_user_info_base
 * 
 * @author wayne
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasUserInfo {

	private String rowKey;

	// 用户id
	private String userId;

	// 性别 0：男 1:女
	private String gender;

	// 出生年月日
	private Date birthDate;

	// 账户创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;

	// 客户姓名
	private String guestName;

	// 电话
	private String tel;

	// 邮箱
	private String email;

	// 广告来源
	private String utmcsr;

	// 广告媒介
	private String utmcmd;

	// 行为明细
	private String behaviorDetail;

	// 行为 访问(1)、咨询(2)、模拟开户(3)、真实开户(4)、首次入金(5)
	private String behaviorType;

	// 标识开户来源,0和空代表是pc开户,1移动端开户
	private Integer platformType;

	// 开户浏览器信息
	private String browser;

	// 操作ip
	private String ip;

	// 归属地
	private String ipHomeJson;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	private int businessPlatform;
	
	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getBehaviorDetail() {
		return behaviorDetail;
	}

	public void setBehaviorDetail(String behaviorDetail) {
		this.behaviorDetail = behaviorDetail;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpHomeJson() {
		return ipHomeJson;
	}

	public void setIpHomeJson(String ipHomeJson) {
		this.ipHomeJson = ipHomeJson;
	}
	
	public int getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(int businessPlatform) {
		this.businessPlatform = businessPlatform;
	}
	
}
