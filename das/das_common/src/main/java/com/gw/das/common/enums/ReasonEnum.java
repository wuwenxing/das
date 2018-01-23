package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：交易类型定义
 * 
 * @author darren
 *
 */
public enum ReasonEnum implements EnumIntf{
	X01("市价建仓", "1"),
	X02("限价建仓", "2"),
	X04("停损建仓", "4"),
	X08("市场平仓","8"),
	X16("止损","16"),
	X32("止盈","32"),
	X64("强平","64"),
	X81("结余","81"),
	X128("部分平仓","128"),
	X129("额度","129"),	
	X130("信用额度","130"),
	X131("charge","131"),
	X132("系统清零","132"),
	X134("赠金","134"),
	X135("手续费","135"),
	X136("调整清零","136"),
	X160("到期","160"),
	X161("Admin平仓","161"),
	X162("结算平仓","162");
	
	private final String value;
	private final String labelKey;
	ReasonEnum(String value, String labelKey) {
		this.value = value;
		this.labelKey = labelKey;
	}
	
	public static List<ReasonEnum> getList(){
		List<ReasonEnum> result = new ArrayList<ReasonEnum>();
		for(ReasonEnum ae : ReasonEnum.values()){
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

	public static String getReason(String labelKey){
		for(ReasonEnum ae : ReasonEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}
	
}
