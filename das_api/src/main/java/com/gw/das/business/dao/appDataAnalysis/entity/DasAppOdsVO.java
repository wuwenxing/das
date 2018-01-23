package com.gw.das.business.dao.appDataAnalysis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * app数据源报表VO
 * 
 * @author Darren
 * @since 2017-04-28
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasAppOdsVO extends BaseModel{

	@Column(name = "rowkey")
	private String rowKey;
	
	/* 操作时间 */
	@Column(name = "datetime")
	private String datetime;
	
	/* 操作时间 */
	@Column(name = "hour")
	private String hour;
	
	/* 操作时间 */
	@Column(name = "weeks")
	private String weeks;
	
	/* 设备(终端)类型:Android,IOS,PCUI    */
	@Column(name = "deviceType")
	private String deviceType;
	
	/* 账号类型：0迷你，1标准，2:VIP */
	@Column(name = "accountType")
	private String accountType;
	
	/* 渠道 */
	@Column(name = "channel")
	private String channel;
	
	/* 设备唯一标识 */
	@Column(name = "deviceid")
	private String deviceid;
	
	/* 操作时间 */
	@Column(name = "operationTime")
	private String operationTime;
	
	/*设备厂商  */
	@Column(name = "carrier")
	private String carrier;
	
	// 设备idfa，一个标示
	private String idfa;
	
	/*标识业务平台访问，1:外汇 2:贵金属、3恒信 */
	@Column(name = "businessPlatform")
	private String businessPlatform;
	
	/*访问IP  */
	@Column(name = "userIp")
	private String userIp;
	
	/* 设备型号 */
	@Column(name = "model")
	private String model;
	
	/* 操作类型 1：启动 、2：登陆 、3：交易、4：注销、5:退出 */
	@Column(name = "operationType")
	private Integer operationType;
	
	/* id（前端页面 忽略该字段）*/
	@Column(name = "id")
	private String id;
	
	/* 用户类型（1.游客、2.模拟、3：真实(不确定有没有激活，由数据平台判断)） */
	@Column(name = "userType")
	private String userType;
	
	/*  用户帐号  */
	@Column(name = "account")
	private String account;
	
	/*  账户平台: MT4,MT5,GTS,GTS2 */
	@Column(name = "platformType")
	private String platformType;
	
	/* app版本号 */
	@Column(name = "platformVersion")
	private String platformVersion;
	
	//应用名称
	@Column(name = "platformName")
	private String platformName;
	
	//事件类别
	@Column(name = "eventCategory")
	private String eventCategory;
	
	//事件操作
	@Column(name = "eventAction")
	private String eventAction;
	
	//事件标签
	@Column(name = "eventLabel")
	private String eventLabel;
	
	//事件参数
	@Column(name = "eventValue")
	private String eventValue;
	
	//设备次数
	@Column(name = "deviceidNum")
	private Long deviceidNum;
	
	//触发设备数
	@Column(name = "deviceidCount")
	private Integer deviceidCount;


	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
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

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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

	public Long getDeviceidNum() {
		return deviceidNum;
	}

	public void setDeviceidNum(Long deviceidNum) {
		this.deviceidNum = deviceidNum;
	}

	public Integer getDeviceidCount() {
		return deviceidCount;
	}

	public void setDeviceidCount(Integer deviceidCount) {
		this.deviceidCount = deviceidCount;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	
}
