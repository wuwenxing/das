package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信通道类型配置枚举
 */
public enum SmsChannelEnum implements EnumIntf {
	
	fcy("发财鱼", "fcy"),
	mdkj("秒嘀科技", "mdkj"),
	zqhl("至臻互联", "zqhl"),
	hx("恒信接口", "hx");
	
	private final String value;
	private final String labelKey;
	SmsChannelEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SmsChannelEnum> getList(Long companyId){
		List<SmsChannelEnum> result = new ArrayList<SmsChannelEnum>();
		if(companyId == 1){
			result.add(SmsChannelEnum.fcy);
			result.add(SmsChannelEnum.mdkj);
			result.add(SmsChannelEnum.zqhl);
		}else if(companyId == 2){
			result.add(SmsChannelEnum.fcy);
			result.add(SmsChannelEnum.mdkj);
			result.add(SmsChannelEnum.zqhl);
		}else if(companyId == 3){
			result.add(SmsChannelEnum.hx);
		}else if(companyId == 4){
			result.add(SmsChannelEnum.mdkj);
		}
		return result;
	}

	public static String format(String labelKey){
		for(SmsChannelEnum ae : SmsChannelEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
