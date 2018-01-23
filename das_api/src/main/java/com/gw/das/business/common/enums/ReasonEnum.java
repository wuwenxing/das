package com.gw.das.business.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 摘要：交易类型定义
 * 
 * @author darren
 *
 */
public enum ReasonEnum {
	X01("1", "市价建仓"),
	X02("2", "限价建仓"),
	X04("4", "停损建仓 "),
	X08("8", "市场平仓"),
	X10("10", "止损"),
	X20("20", "止盈"),
	X40("40", "强平"),
	X80("80", "部分平仓");

	private final String labelKey;
	private final String value;
	ReasonEnum(String labelKey,String _operator) {
		this.value = _operator;
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
	
	public static String getReason(String reason){
		String resultStr = "";
		if(StringUtils.isBlank(reason)){
			return resultStr;
		}
		String reasonHex = Integer.toHexString(Integer.valueOf(reason));
		if(ReasonEnum.X01.getLabelKey().equals(reasonHex)){
			resultStr = ReasonEnum.X01.getValue();
		}else if(ReasonEnum.X02.getLabelKey().equals(reasonHex)){
			resultStr = ReasonEnum.X02.getValue();
		}else if(ReasonEnum.X04.getLabelKey().equals(reasonHex)){
			resultStr = ReasonEnum.X04.getValue();
		}else if(ReasonEnum.X08.getLabelKey().equals(reasonHex)){
			resultStr = ReasonEnum.X08.getValue();
		}else if(ReasonEnum.X10.getLabelKey().equals(reasonHex)){
			resultStr = ReasonEnum.X10.getValue();
		}else if(ReasonEnum.X20.getLabelKey().equals(reasonHex)){
			resultStr = ReasonEnum.X20.getValue();
		}else if(ReasonEnum.X40.getLabelKey().equals(reasonHex)){
			resultStr = ReasonEnum.X40.getValue();
		}else if(ReasonEnum.X80.getLabelKey().equals(reasonHex)){
			resultStr = ReasonEnum.X80.getValue();
		}
	    return resultStr;    			
	}
	
}
