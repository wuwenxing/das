package com.gw.das.business.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum BusinessPlatformEnum {

	gw("集团", "0"),
	Fx("外汇", "1"),
	Pm("贵金属", "2"),
	Hx("恒信", "3"),
	Cf("创富", "4");
	
	private final String value;
	private final String labelKey;
	BusinessPlatformEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<BusinessPlatformEnum> getList(){
		List<BusinessPlatformEnum> result = new ArrayList<BusinessPlatformEnum>();
		for(BusinessPlatformEnum ae : BusinessPlatformEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		for(BusinessPlatformEnum ae : BusinessPlatformEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	/**
	 * 获取账户诊断API对应的companyId,即转换
	 * @return
	 */
	public static String getAPICompanyId(String companyId){
		if(BusinessPlatformEnum.gw.getLabelKey().equals(companyId)){
			return APICompanyEnum.gw.getLabelKey();
		}else if(BusinessPlatformEnum.Fx.getLabelKey().equals(companyId)){
			return APICompanyEnum.fx.getLabelKey();
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(companyId)){
			return APICompanyEnum.hx.getLabelKey();
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(companyId)){
			return APICompanyEnum.pm.getLabelKey();
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(companyId)){
			return APICompanyEnum.cf.getLabelKey();
		}
		return companyId;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public Integer getLabelKeyInt() {
		return Integer.parseInt(labelKey);
	}
	
}
