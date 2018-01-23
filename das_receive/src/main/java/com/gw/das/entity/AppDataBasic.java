package com.gw.das.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * APP操作日志入库
 */
@Document(collection = "app_data_basic")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppDataBasic {

	@Id
	private String id;
	// 设备唯一标识
	private String deviceid;
	// 设备(终端)类型:Android,IOS,PCUI
	private String deviceType;
	// 用户类型（1.游客、2.模拟、3：真实(不确定有没有激活，由数据平台判断)）
	private String userType;
	// 访问IP
	private String userIp;
	// 账户平台: MT4,MT5,GTS,GTS2
	private String platform;
	// 账号类型：0迷你，1标准，2:VIP
	private String accountType;
	// 用户帐号
	private String account;
	// 渠道
	private String channel;
	// 手机型号
	private String model;
	// 手机厂商
	private String carrier;
	// 设备idfa，一个标示
	private String idfa;
	// 标识业务平台访问，1:外汇 2:贵金属、3恒信
	private String businessPlatform;
	//操作类型 1：启动 、2：登陆 、3：交易、4：注销、5:退出
	private String operationType;
	// 操作时间
	private String operationTime;
	// app版本号
	private String version;

	// 事件类别
	private String eventCategory;
	// 事件操作
	private String eventAction;
	// 事件标签
	private String eventLabel;
	// 事件参数
	private String eventValue;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

}
