package com.gw.das.common.utils;

import java.math.BigDecimal;

/**
 * 数学工具类
 * @author kirin.guan
 *
 */
public class MathUtils {
	
	/**
	 * 格式化数字
	 * @param obj
	 * @return
	 */
	public static double numberFormat(double obj,int digits){
		BigDecimal bd =	new BigDecimal(obj).setScale(digits,BigDecimal.ROUND_HALF_UP);
		return Double.parseDouble(bd.toString()); 
	}
	
	/**
	 * 格式化数字
	 * @param obj
	 * @return
	 */
	public static double numberFormat2(double obj){
		return  Double.parseDouble(String.format("%.2f",obj));
	}
	
	public static void main(String[] args) {
		System.out.println(MathUtils.numberFormat(1.222+8.66666666666666, 4));
	}
}
