package com.gw.das.mongodb;

/**
 * 摘要：自定义序列枚举
 * @author Gavin.guo
 * @date   2015-06-21
 */
public enum IdSeq {
    Member("M",1000000),
	Role("R",1000000),
	Log("L",1000000),
	Commodity("C",1000000),
	CommodityType("CT",1000000),
	Order("O",1000000),
	OrderDetail("OD",1000000),
    ChatGroup("",1),
    UtmChannel("UCH",1000000),
    UtmTag("TAG",1000000),
    UtmUserScreen("UUS",1000000),
    UtmSmsScreen("USS",1000000),
    UserTrackDataDetail("UD",1000000),
    UserTrackDataDetailAccessPageInfo("UI",1000000),
    UserTrackDataAverage("UA",1000000),
    UserTrackDataAttribution("UT",1000000),
    UserTrackDataStatistics("US",1000000),
    UserTrackDataException("UD",1000000),
    UserTrackUserInformationData("UID",1000000),
    UserTrackBasic("UB",1000000),
    UserTrackChartRoomBasic("UCB",1000000),
    UtmSmsRecordMain("USRM",1000000),
    UserTrackDepositInfoHis("USD",1000000),
    UtmSmsRecordDetail("USRD",1000000),
    UtmSmsBlacklist("BLACKLIST",1000000),
    UtmSmsEmail("USEmail",1000000),
    UtmSmsEmailDetail("USEmailD",1000000),
    UtmSmsTemplate("SmsTemplate",1000000),
    UtmSmsTemplateSendDetail("SmsTemplateSendDetail",1000000),
    UtmSmsTemplateReqLog("SmsTemplateReqLog",1000000),
    UtmEmailTemplate("EmailTemplate",1000000),
    UtmEmailTemplateSendDetail("EmailTemplateSendDetail",1000000),
    UtmEmailTemplateReqLog("EmailTemplateReqLog",1000000),
    UtmCustomerGroup("CustomerGroup",1000000),
    UtmCustomerGroupDetail("CustomerGroupDetail",1000000),
    UtmCustomerGroupReqLog("CustomerGroupReqLog",1000000),
    SystemUser("USER",1000000),
    SystemRole("ROLE",1000000),
    SystemUserRole("SYSTEMUSERROLE",1000000),
    SystemMenu("MENU",1000000),
    SystemRoleMenu("SYSTEMROLEMENU",1000000),
    SystemEmailSet("EMAIL",1000000),
    SystemThread("SYSTEMTHREAD",1000000),
    SystemSmsConfig("SystemSmsConfig",1000000),
    AppOperLog("AppOperLog",1000000),
    ;
	public static final char[] charArray = new char[] { 'A', 'B', 'C', 'D',
		'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S',
		'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private String prefix;
	private long startNum;

	IdSeq(String prefix,long startNum){
		this.prefix = prefix;
		this.startNum = startNum;
	}

	public String getPrefix() {
		return prefix;
	}
	
	public long getStartNum() {
		return startNum;
	}
}
