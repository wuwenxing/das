package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 摘要：手机运营商定义
 */
public enum MobileOperatorEnum implements EnumIntf {

	cmcc("中国移动", "cmcc"),
	cucc("中国联通", "cucc"),
	ctcc("中国电信", "ctcc");
	
	private final String value;
	private final String labelKey;
	MobileOperatorEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<MobileOperatorEnum> getList(){
		List<MobileOperatorEnum> result = new ArrayList<MobileOperatorEnum>();
		for(MobileOperatorEnum ae : MobileOperatorEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(MobileOperatorEnum ae : MobileOperatorEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	/**
	 * 验证手机号为哪个运营商
	 * 移动：134|135|136|137|138|139|147|150|151|152|157|158|159|178|182|183|184|187|188|1705
	 * 联通：130|131|132|145|155|156|176|185|186|1709|1708|1707
	 * 电信：180|181|189|133|153|1700|177|173|1701|1702
	 * @return
	 */
	public static MobileOperatorEnum checkOpeator(String mobile){
		String cmcc = "(134|135|136|137|138|139|147|150|151|152|157|158|159|178|182|183|184|187|188|1705).*";
		String cucc = "(130|131|132|145|155|156|176|185|186|1709|1708|1707).*";
		String ctcc = "(180|181|189|133|153|1700|177|173|1701|1702).*";
		
		Pattern pCmcc = Pattern.compile(cmcc);
		Matcher mCmcc = pCmcc.matcher(mobile);
		
		Pattern pCucc = Pattern.compile(cucc);
		Matcher mCucc = pCucc.matcher(mobile);
		
		Pattern pCtcc = Pattern.compile(ctcc);
		Matcher mCtcc = pCtcc.matcher(mobile);

		if(mCmcc.matches()){
			return MobileOperatorEnum.cmcc;
		}
		if(mCucc.matches()){
			return MobileOperatorEnum.cucc;
		}
		if(mCtcc.matches()){
			return MobileOperatorEnum.ctcc;
		}
		return MobileOperatorEnum.cmcc;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
	public static void main(String[] args) {
		MobileOperatorEnum s = MobileOperatorEnum.checkOpeator("13060291376");
		System.out.println(s.getLabelKey());
	}
}
