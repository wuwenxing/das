package com.gw.das.business.common.enums;
/**
 * 行为枚举类型
 * @author kirin.guan
 *
 */
public enum TrackBehaviorType implements EnumIntf {
	
	VISIT("1","1"),
	LIVE("2","2"),
	DEMO("3","3"),
	REAL("4","4"),
	DEPESIT("5","5"),
	DEMOPRE("D","D"),
	REALPRE("N","N"),
	DEPESITPRE("R","R"),
	submitTypeAccount("1","1"),
	submitTypePage("0","0"),
	DEMOSTR("demo","demo"),
	REALSTR("real","real"),
	DEPOSITSTR("depisit","depisit");

	private final String value;
	private final String labelKey;
	TrackBehaviorType(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public String getValue() {
        return this.value;
    }
	public String getLabelKey() {
        return this.labelKey;
    }
	
	/**
	 * 公共方法
	 * @param labelKey
	 * @return
	 */
	public static String getObjectName(String labelKey){
		String objectName = "";
		if(labelKey.equals(TrackBehaviorType.DEMO.getValue())){//模拟
			objectName = "userInfoDemoData";
		}else if(labelKey.equals(TrackBehaviorType.REAL.getValue())){//真实
			objectName = "userInfoRealData";
		}else if(labelKey.equals(TrackBehaviorType.DEPESIT.getValue())){//入金
			objectName = "userInfoDepesitData";
		}
		return objectName;
	}
	
}
