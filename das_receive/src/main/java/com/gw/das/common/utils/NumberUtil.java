package com.gw.das.common.utils;

import java.math.BigDecimal;

public class NumberUtil {

	/**
	 * double四舍五入取整
	 * 
	 * @param f
	 * @return
	 */
	public static Long doubleToLong(Double num) {
		// double四舍五入
		BigDecimal b = new BigDecimal(num);
		num = b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num.longValue();
	}

	/**
	 * double四舍五入，保留两位有效数字
	 * 
	 * @param f
	 * @return
	 */
	public static double m2(double num) {
		// double四舍五入
		BigDecimal b = new BigDecimal(num);
		num = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}
	
	public static void main(String[] args) {
		Long s = NumberUtil.doubleToLong(304.99999999999545);
		System.out.println(s);
		
		System.out.println(NumberUtil.m2(42.74999999999999));
	}
	
}
