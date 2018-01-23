package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum PlatformtypeEnum implements EnumIntf {
	
	MT4("MT4", "MT4"),
	GTS2("GTS2", "GTS2"),
	MT5("MT5", "MT5"),
	GTS("GTS", "GTS");

	private final String value;
	private final String labelKey;
	PlatformtypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<PlatformtypeEnum> getList(){
		List<PlatformtypeEnum> result = new ArrayList<PlatformtypeEnum>();
		result.add(PlatformtypeEnum.MT4);
		result.add(PlatformtypeEnum.GTS2);
		return result;
	}
	
	/**
	 * 根据参数返回对应平台
	 * @param companyId
	 * @return
	 */
	public static List<PlatformtypeEnum> getList(Long companyId){
		List<PlatformtypeEnum> result = new ArrayList<PlatformtypeEnum>();
		if(CompanyEnum.pm.getLabelKeyLong() == companyId){
			result.add(PlatformtypeEnum.MT4);
			result.add(PlatformtypeEnum.MT5);
			result.add(PlatformtypeEnum.GTS);
		}else{
			result.add(PlatformtypeEnum.MT4);
			result.add(PlatformtypeEnum.GTS2);
		}
		return result;
	}
	
	public static List<PlatformtypeEnum> getAllList(){
		List<PlatformtypeEnum> result = new ArrayList<PlatformtypeEnum>();
		for(PlatformtypeEnum ae : PlatformtypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(PlatformtypeEnum ae : PlatformtypeEnum.values()){
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
