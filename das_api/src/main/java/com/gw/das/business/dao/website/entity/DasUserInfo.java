package com.gw.das.business.dao.website.entity;

import java.util.Date;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 对应表das_user_info_base
 * 
 * @author wayne
 */
public class DasUserInfo extends BaseModel{

	@Column(name = "rowkey")
	private String rowKey;

	// 用户id
	@Column(name = "userid")
	private String userId;

	// 性别 0：男 1:女
	@Column(name = "gender")
	private String gender;

	// 出生年月日
	@Column(name = "birthdate")
	private Date birthDate;

	// 账户创建时间
	@Column(name = "createdate")
	private Date createDate;

	// 客户姓名
	@Column(name = "guestname")
	private String guestName;

	// 电话
	@Column(name = "tel")
	private String tel;

	// 邮箱
	@Column(name = "email")
	private String email;

	// 广告来源
	@Column(name = "utmcsr")
	private String utmcsr;

	// 广告媒介
	@Column(name = "utmcmd")
	private String utmcmd;

	// 行为明细
	@Column(name = "behaviordetail")
	private String behaviorDetail;

	// 行为 访问(1)、咨询(2)、模拟开户(3)、真实开户(4)、首次入金(5)
	@Column(name = "behaviortype")
	private String behaviorType;

	// 标识开户来源,0和空代表是pc开户,1移动端开户
	@Column(name = "platformtype")
	private Integer platformType = 0;

	// 开户浏览器信息
	@Column(name = "browser")
	private String browser;

	// 操作ip
	@Column(name = "ip")
	private String ip;

	// 归属地
	@Column(name = "iphome")
	private String ipHomeJson;

	// 标识业务平台访问，1:外汇网站 2:贵金属网站
	@Column(name = "businessplatform")
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
