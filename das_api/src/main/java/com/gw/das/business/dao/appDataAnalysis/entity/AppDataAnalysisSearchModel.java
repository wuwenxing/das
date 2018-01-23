package com.gw.das.business.dao.appDataAnalysis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gw.das.business.dao.base.BaseSearchModel;

/**
 * App报表查询VO
 * @author darren
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppDataAnalysisSearchModel extends BaseSearchModel {

	// 报表类型: (days:日统计、months:周统计)
		private String reportType;

		// 日期-开始日期
		private String startTime;

		// 日期-结束日期
		private String endTime;
		
		/* 月 */
		private String dtMonth;
		
		/* 日 */
		private String dtDay;
		
		/* 周 */
		private String dtWeek;
		
		private String dataTime;
		
		// 访问客户端
		private String platformType;
		
		private String mobiletype;
		
		//渠道
		private String channel;
		
		private String tableType;
		
		private int channeltype;
		
		//属性
		private String field;
		
		private boolean channelChecked;
		
		private boolean devicetypeChecked;
		
		// 账号
		private String account;
		
		private String userType;
		
		private String accountType;
		
		private String deviceType;
		
		private String operationType;		
		
		private String idfa;
		
		private String deviceid;
		
		private String carrier;
		
		private String model;
		
		private String platformName;
		
		private String platformVersion;
		
		private String eventCategory;
		
		private String eventAction;
		
		private String eventLabel;
		
		private String eventValue;
		
		// 版本复选框
		private boolean platformVersionChecked;
		// 设备平台复选框
		private boolean deviceTypeChecked;
		// 交易平台复选框
		private boolean platformTypeChecked;
		//  马甲包复选框
		private boolean platformNameChecked;
		// 客户类型字复选框
		private boolean userTypeChecked;
				
		//事件类别复选框
		private boolean eventCategoryChecked;
		// 事件操作复选框
		private boolean eventActionChecked;
		//  事件标签复选框
		private boolean eventLabelChecked;
		// 事件参数复选框
		private boolean eventValueChecked;

		public String getReportType()
		{
			return reportType;
		}

		
		public void setReportType(String reportType)
		{
			this.reportType = reportType;
		}

		
		public String getStartTime()
		{
			return startTime;
		}

		
		public void setStartTime(String startTime)
		{
			this.startTime = startTime;
		}

		
		public String getEndTime()
		{
			return endTime;
		}

		
		public void setEndTime(String endTime)
		{
			this.endTime = endTime;
		}


		
		public String getDtMonth() {
			return dtMonth;
		}


		public void setDtMonth(String dtMonth) {
			this.dtMonth = dtMonth;
		}


		public String getDtDay() {
			return dtDay;
		}


		public void setDtDay(String dtDay) {
			this.dtDay = dtDay;
		}


		public String getDtWeek() {
			return dtWeek;
		}


		public void setDtWeek(String dtWeek) {
			this.dtWeek = dtWeek;
		}


		public String getDataTime() {
			return dataTime;
		}


		public void setDataTime(String dataTime) {
			this.dataTime = dataTime;
		}


		public String getPlatformType()
		{
			return platformType;
		}


		public void setPlatformType(String platformType)
		{
			this.platformType = platformType;
		}


		
		
		public String getMobiletype() {
			return mobiletype;
		}


		public void setMobiletype(String mobiletype) {
			this.mobiletype = mobiletype;
		}


		public String getChannel()
		{
			return channel;
		}


		
		public void setChannel(String channel)
		{
			this.channel = channel;
		}


		public String getTableType()
		{
			return tableType;
		}


		
		public void setTableType(String tableType)
		{
			this.tableType = tableType;
		}


		
		public int getChanneltype()
		{
			return channeltype;
		}


		
		public void setChanneltype(int channeltype)
		{
			this.channeltype = channeltype;
		}


		public String getField() {
			return field;
		}


		public void setField(String field) {
			this.field = field;
		}


		public boolean isChannelChecked() {
			return channelChecked;
		}


		public void setChannelChecked(boolean channelChecked) {
			this.channelChecked = channelChecked;
		}


		


		public boolean isDevicetypeChecked() {
			return devicetypeChecked;
		}


		public void setDevicetypeChecked(boolean devicetypeChecked) {
			this.devicetypeChecked = devicetypeChecked;
		}


		public String getAccount() {
			return account;
		}


		public void setAccount(String account) {
			this.account = account;
		}


		public String getUserType() {
			return userType;
		}


		public void setUserType(String userType) {
			this.userType = userType;
		}


		public String getAccountType() {
			return accountType;
		}


		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}


		public String getDeviceType() {
			return deviceType;
		}


		public void setDeviceType(String deviceType) {
			this.deviceType = deviceType;
		}


		public String getOperationType() {
			return operationType;
		}


		public void setOperationType(String operationType) {
			this.operationType = operationType;
		}


		public String getIdfa() {
			return idfa;
		}


		public void setIdfa(String idfa) {
			this.idfa = idfa;
		}


		public String getDeviceid() {
			return deviceid;
		}


		public void setDeviceid(String deviceid) {
			this.deviceid = deviceid;
		}


		public String getCarrier() {
			return carrier;
		}


		public void setCarrier(String carrier) {
			this.carrier = carrier;
		}


		public String getModel() {
			return model;
		}


		public void setModel(String model) {
			this.model = model;
		}


		public String getPlatformName() {
			return platformName;
		}


		public void setPlatformName(String platformName) {
			this.platformName = platformName;
		}


		public String getPlatformVersion() {
			return platformVersion;
		}


		public void setPlatformVersion(String platformVersion) {
			this.platformVersion = platformVersion;
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


		public boolean isPlatformVersionChecked() {
			return platformVersionChecked;
		}


		public void setPlatformVersionChecked(boolean platformVersionChecked) {
			this.platformVersionChecked = platformVersionChecked;
		}


		public boolean isDeviceTypeChecked() {
			return deviceTypeChecked;
		}


		public void setDeviceTypeChecked(boolean deviceTypeChecked) {
			this.deviceTypeChecked = deviceTypeChecked;
		}


		public boolean isPlatformTypeChecked() {
			return platformTypeChecked;
		}


		public void setPlatformTypeChecked(boolean platformTypeChecked) {
			this.platformTypeChecked = platformTypeChecked;
		}


		public boolean isPlatformNameChecked() {
			return platformNameChecked;
		}


		public void setPlatformNameChecked(boolean platformNameChecked) {
			this.platformNameChecked = platformNameChecked;
		}


		public boolean isUserTypeChecked() {
			return userTypeChecked;
		}


		public void setUserTypeChecked(boolean userTypeChecked) {
			this.userTypeChecked = userTypeChecked;
		}


		public boolean isEventCategoryChecked() {
			return eventCategoryChecked;
		}


		public void setEventCategoryChecked(boolean eventCategoryChecked) {
			this.eventCategoryChecked = eventCategoryChecked;
		}


		public boolean isEventActionChecked() {
			return eventActionChecked;
		}


		public void setEventActionChecked(boolean eventActionChecked) {
			this.eventActionChecked = eventActionChecked;
		}


		public boolean isEventLabelChecked() {
			return eventLabelChecked;
		}


		public void setEventLabelChecked(boolean eventLabelChecked) {
			this.eventLabelChecked = eventLabelChecked;
		}


		public boolean isEventValueChecked() {
			return eventValueChecked;
		}


		public void setEventValueChecked(boolean eventValueChecked) {
			this.eventValueChecked = eventValueChecked;
		}	
		

}
