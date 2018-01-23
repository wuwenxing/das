package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 摘要：沉默行为定义
 */
public enum SilenceEnum implements EnumIntf {
	
	notRealAccount("D类：?天内未开真实账户", "notRealAccount", "D"),
	noOperation("D类：?天内无任何操作（模拟交易、开真实账户）", "noOperation", "D_"),
	noDeposit("N类：?天内未激活（即未入金）", "noDeposit", "N"),
	untraded_1("A类：?天内没有交易（且历史没有任何交易）", "untraded_1", "A_"),
	untraded_2("A类：?天内没有交易（且历史有交易）", "untraded_2", "A_"),
	untraded_3("A类：?天内没有交易（且历史有交易，整体赔钱的）", "untraded_3", "A_"),
	untraded_4("A类：?天内没有交易（且历史有交易，整体盈利的）", "untraded_4", "A_");
	
	private final String value;
	private final String labelKey;
	private final String type; // 沉默类型（D类、N类、A类）
	SilenceEnum(String _operator, String labelKey, String type) {
		this.value = _operator;
		this.labelKey = labelKey;
		this.type = type;
	}
	
	public static List<SilenceEnum> getList(){
		List<SilenceEnum> result = new ArrayList<SilenceEnum>();
		for(SilenceEnum ae : SilenceEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	/**
	 * 根据属性behaviorType类型，获取对应集合
	 * @param behaviorType
	 * @return
	 */
	public static List<SilenceEnum> getList(String behaviorType){
		String type = "";
		if(BehaviorTypeEnum.demo.getLabelKey().equals(behaviorType)){
			type = "D";
		}else if(BehaviorTypeEnum.real.getLabelKey().equals(behaviorType)){
			type = "N";
		}else if(BehaviorTypeEnum.depesit.getLabelKey().equals(behaviorType)){
			type = "A";
		}
		List<SilenceEnum> result = new ArrayList<SilenceEnum>();
		if(StringUtils.isNotBlank(type)){
			for(SilenceEnum ae : SilenceEnum.values()){
				if(ae.getType().equals(type)){
					result.add(ae);
				}
			}
		}
		return result;
	}

	public static String format(String labelKey){
		for(SilenceEnum ae : SilenceEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public static SilenceEnum find(String labelKey){
		for(SilenceEnum ae : SilenceEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae;
			}
		}
		return null;
	}
	
	public static String toJson(List<SilenceEnum> list){
		StringBuffer sb = new StringBuffer();
		if(null != list && list.size() > 0){
			sb.append("[");
			for(int i=0; i<list.size(); i++){
				SilenceEnum silenceEnum = list.get(i);
				sb.append("{");
				sb.append("\"value\":\"" + silenceEnum.getValue() + "\",");
				sb.append("\"labelKey\":\"" + silenceEnum.getLabelKey() + "\",");
				sb.append("\"type\":\"" + silenceEnum.getType() + "\"");
				sb.append("}");
				if(i != list.size() - 1){
					sb.append(",");
				}
			}
			sb.append("]");
		}
		return sb.toString();
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public String getType() {
		return type;
	}
	
}
