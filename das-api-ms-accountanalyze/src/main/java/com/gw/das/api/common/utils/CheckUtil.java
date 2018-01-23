package com.gw.das.api.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检验校验工具类 
 * 
 * @author darren
 *
 */
public class CheckUtil {

    /**
     * 校验身份证
     * 
     * @param IDNumber
     * @return
     */
	public static boolean checkCardNo(String IDNumber) {
		// 定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		Matcher idNumMatcher = idNumPattern.matcher(IDNumber);
		if (idNumMatcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(CheckUtil.checkCardNo("440802197809141378"));
	}
	
}
