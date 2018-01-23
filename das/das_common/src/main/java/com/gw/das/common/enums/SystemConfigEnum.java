package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：系统配置参数类型定义
 * @author wayne
 */
public enum SystemConfigEnum implements EnumIntf {
	// 与SysConfig.properties对应
	systemIntranetUrl("系统内网访问地址", "system.intranet.url"),
	systemDomainName("系统官方域名", "system.domainName"),
	systemVersion("当前系统版本号", "system.version"),
	systemPort("当前系统端口号", "system.port"),
	systemActive("当前系统环境", "system.active"),
	webLinkUrl("网站链接地址", "web.link.url"),
	systemLinkUrl("本系统地址", "system.link.url"),
	cboardLinkUrl("自助报表地址", "cboard.link.url"),
	
	SmsFcyUrl("发财鱼短信接口url", "sms.fcy.url"),
	SmsFcyUsername("发财鱼短信用户名", "sms.fcy.username"),
	SmsFcyPassword("发财鱼短信密码", "sms.fcy.password"),
	SmsFcyKey("发财鱼短信密匙", "sms.fcy.key"),
	
	SmsMdkjUrl("秒嘀科技短信接口url", "sms.mdkj.url"),
	SmsMdkjSid("秒嘀科技短信sid", "sms.mdkj.sid"),
	SmsMdkjToken("秒嘀科技短信token", "sms.mdkj.token"),
	SmsMdkjResDataType("秒嘀科技短信返回数据类型", "sms.mdkj.resDataType"),

	hxApiUrl("hx短信接口url", "hx.api.url"),
	hxApiUsername("hx短信接口用户名", "hx.api.username"),
	hxApiPassword("hx短信接口密码", "hx.api.password"),

	flowYmUrl("亿美流量接口url", "flow.ym.url"),
	flowYmAppid("亿美流量接口用户名", "flow.ym.appid"),
	flowYmToken("亿美流量接口密码", "flow.ym.token"),

	flowLdUrl("联动流量接口url", "flow.ld.url"),
	flowLdAccount("联动流量接口用户名", "flow.ld.account"),
	flowLdKpi("联动流量KPI", "flow.ld.kpi"),

	flowLmUrl("乐免流量接口url", "flow.lm.url"),
	flowLmNo("乐免流量接口用户编号", "flow.lm.no"),
	flowLmAccount("乐免流量接口用户名", "flow.lm.account"),
	flowLmPassword("乐免流量接口密码", "flow.lm.password"),
	flowLmNo2("乐免流量接口用户编号2", "flow.lm.no2"),
	flowLmAccount2("乐免流量接口用户名2", "flow.lm.account2"),
	flowLmPassword2("乐免流量接口密码2", "flow.lm.password2"),

	flowRlUrl("容联流量接口url", "flow.rl.url"),
	flowRlAppid("容联流量接口appid", "flow.rl.appid"),
	flowRlSid("容联流量接口用户名", "flow.rl.sid"),
	flowRlToken("容联流量接口密码", "flow.rl.token"),
	
	FxGoldEmailApiUrl("FX邮件接口地址-推广", "fx.gold.email.api.url"),
	FxWebLinks("FX网站链接", "fx.web.links"),
	FxMailUserName("PM发送邮箱用户名", "fx.mail.smtp.username"),
	FxMailDisplayUserName("FX发送邮箱用户名昵称", "fx.mail.smtp.display.username"),
	PmWebLinks("PM网站链接", "pm.web.links"),
	PmGoldEmailApiUrl("PM邮件接口地址-推广", "pm.gold.email.api.url"),
	PmMailUserName("PM发送邮箱用户名", "pm.mail.smtp.username"),
	PmMailDisplayUserName("PM发送邮箱用户名昵称", "pm.mail.smtp.display.username"),
	FxGoldEmailApiUrl2("FX邮件接口地址", "fx.gold.email.api.url2"),
	PmGoldEmailApiUrl2("PM邮件接口地址", "pm.gold.email.api.url2"),
	
	smsZqhlMainUrl("至臻互联短信接口主url", "sms.zqhl.main.url"),
	smsZqhlSpareUrl("至臻互联短信接口备url", "sms.zqhl.spare.url"),
	smsPmZqhlUsername("pm至臻互联短信用户名", "sms.pm.zqhl.username"),
	smsPmZqhlPassword("pm至臻互联短信密码", "sms.pm.zqhl.password"),
	smsFxZqhlUsername("fx至臻互联短信用户名", "sms.fx.zqhl.username"),
	smsFxZqhlPassword("fx至臻互联短信密码", "sms.fx.zqhl.password"),

	UploadFileAccessurl("文件服务器文件访问地址", "upload.file.accessurl"),

	accountAnalyzeDomainName("账户诊断报告访问域名", "accountAnalyze.domainName"),
	accountAnalyzeApiUrl("账户诊断API接口地址", "accountAnalyze.api.url"),
	dasApiUrl("API接口地址", "das_api_url"),
	
	serviceScretUrl("通用基础服务平台URL", "service.scret.url"),
	serviceScretType("数据平台从通用基础服务平台URL获取Token的type值", "service.scret.type"),
	fxAppid("FX", "fx.appid"),
	fxAppScret("FX", "fx.appScret"),
	hxAppid("HX", "hx.appid"),
	hxAppScret("HX", "hx.appScret"),
	pmAppid("PM", "pm.appid"),
	pmAppScret("PM", "pm.appScret"),
	cfAppid("CF", "cf.appid"),
	cfAppScret("CF", "cf.appScret"),
	gwAppid("gw", "gw.appid"),
	gwAppScret("gw", "gw.appScret"),

	phantomjsPath("phantomjs路径", "phantomjs.path"),
	idCardDecryptUrl("身份证解密URL", "idCard.decrypt.url")
	
	;
	
	private final String value;
	private final String labelKey;
	SystemConfigEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SystemConfigEnum> getList(){
		List<SystemConfigEnum> result = new ArrayList<SystemConfigEnum>();
		for(SystemConfigEnum ae : SystemConfigEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
