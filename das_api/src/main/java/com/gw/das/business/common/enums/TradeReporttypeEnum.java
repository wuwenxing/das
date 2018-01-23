package com.gw.das.business.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 摘要：交易类型定义
 */
public enum TradeReporttypeEnum {
	ALL("", "所有"),
	DEPOSIT("100", "存款"),
	ARTIFICIALDEPOSIT("102", "人工存款"),
	WITHDRAW("4", "取款"),
	PROFITANDLOSS("8", "盈亏"),
	ZERO("10", "清零"),
	GIFT("20", "赠金"),
	EVENTGIFT("40", "活动赠金"),
	QUOTAADJUSTMENT("80", "额度调整"),
	TRANSACTIONCODECHARGEBACK("80", "交易编码扣费"),
	ENCODINGREFUNDTRANSACTION("80", "交易编码退费");

	
	private final String labelKey;
	private final String value;
	TradeReporttypeEnum(String labelKey,String _operator) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<TradeReporttypeEnum> getList(){
		List<TradeReporttypeEnum> result = new ArrayList<TradeReporttypeEnum>();
		for(TradeReporttypeEnum ae : TradeReporttypeEnum.values()){
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
	
	public static String getReportType(String reporttype){
		String resultStr = TradeReporttypeEnum.ALL.getValue();
		if(StringUtils.isBlank(reporttype)){
			return resultStr;
		}
		String reasonHex = Integer.toHexString(Integer.valueOf(reporttype));
		if(TradeReporttypeEnum.DEPOSIT.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.DEPOSIT.getValue();
		}else if(TradeReporttypeEnum.ARTIFICIALDEPOSIT.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.ARTIFICIALDEPOSIT.getValue();
		}else if(TradeReporttypeEnum.WITHDRAW.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.WITHDRAW.getValue();
		}else if(TradeReporttypeEnum.PROFITANDLOSS.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.PROFITANDLOSS.getValue();
		}else if(TradeReporttypeEnum.ZERO.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.ZERO.getValue();
		}else if(TradeReporttypeEnum.GIFT.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.GIFT.getValue();
		}else if(TradeReporttypeEnum.EVENTGIFT.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.EVENTGIFT.getValue();
		}else if(TradeReporttypeEnum.QUOTAADJUSTMENT.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.QUOTAADJUSTMENT.getValue();
		}else if(TradeReporttypeEnum.TRANSACTIONCODECHARGEBACK.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.TRANSACTIONCODECHARGEBACK.getValue();
		}else if(TradeReporttypeEnum.ENCODINGREFUNDTRANSACTION.getLabelKey().equals(reasonHex)){
			resultStr = TradeReporttypeEnum.ENCODINGREFUNDTRANSACTION.getValue();
		}
	    return resultStr;    			
	}
	
	
}
