package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面面板定义
 * @author wayne
 *
 */
public enum PanelEnum implements EnumIntf {
	
	gts2xdtj("GTS2下单途径比例", "gts2xdtj", ""),
	xkh("新开户客户途径比例", "xkh", ""),
	xjh("新激活客户途径比例", "xjh", ""),
	ml("当月毛利", "ml", ""),
	jy("当月结余", "jy", ""),
	jyss("当月交易手数", "jyss", ""),
	jrj("当月净入金", "jrj", ""),
	mt4jyk("当天净盈亏小时图-MT4", "mt4jyk", ""),
	gts2jyk("GTS2当天净盈亏小时图-GTS2", "gts2jyk", ""),
	;
	
	private final String value;
	private final String labelKey;
	private final String url;
	PanelEnum(String value, String labelKey, String url) {
		this.value = value;
		this.labelKey = labelKey;
		this.url = url;
	}

	public static List<PanelEnum> getList(){
		List<PanelEnum> result = new ArrayList<PanelEnum>();
		for(PanelEnum ae : PanelEnum.values()){
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

	public String getUrl() {
		return url;
	}
	
}
