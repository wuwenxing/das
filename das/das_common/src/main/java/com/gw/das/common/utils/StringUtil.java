package com.gw.das.common.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static String array2String(String[] arrays) {
		String resultString = "";
		if (arrays != null && arrays.length != 0) {
			StringBuffer tmpstring = new StringBuffer();
			boolean flag = false;
			for (String tmps : arrays) {
				if (flag)
					tmpstring.append(",");
				tmpstring.append(tmps.trim());
				flag = true;
			}
			resultString = tmpstring.toString();
		}
		return resultString;
	}

	public static String list2String(List<String> stringlist) {
		String resultString = "";
		if (stringlist != null && stringlist.size() != 0) {
			StringBuffer tmpstring = new StringBuffer();
			boolean flag = false;
			for (String tmps : stringlist) {
				if (flag)
					tmpstring.append(",");
				tmpstring.append(tmps.trim());
				flag = true;
			}
			resultString = tmpstring.toString();
		}
		return resultString;
	}

	public static String[] string2Array(String string) {
		String[] tmpArray = null;
		if (string != null && !"".equals(string.trim())) {
			tmpArray = string.split(",");
		}
		return tmpArray;
	}

	public static List<String> string2List(String string) {
		List<String> tmpList = null;
		if (string != null && !"".equals(string.trim())) {
			tmpList = Arrays.asList(string.split(","));
		}
		return tmpList;
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：helloWorld->hello_world 
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		if (StringUtils.isBlank(name)) {
			return name;
		}
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成大写
			result.append(name.substring(0, 1));
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s);
			}
		}
		return result.toString().toLowerCase();
	}

	
	/**
	 * 格式手机号码，中间使用*号代替
	 * @param value
	 * @return
	 */
	public static String formatPhone(String value){
		if(StringUtils.isNotBlank(value) && value.length() > 8){
			value = value.substring(0, value.length()-8)
					+ "****"
					+ value.substring(value.length()-4, value.length());
		}
		return value;
	}
	
	/**
	 * 格式邮箱，中间使用*号代替
	 * @param value
	 * @return
	 */
	public static String formatEmail(String value){
		if(StringUtils.isNotBlank(value) && value.indexOf("@") != -1){
			String emailPrefix = value.split("@")[0];
			String emailSuffix = value.split("@")[1];
			if(StringUtils.isNotBlank(emailPrefix) && emailPrefix.length() > 4){
				emailPrefix = emailPrefix.substring(0, emailPrefix.length()-4) + "****";
			}else{
				emailPrefix = "****";
			}
			value = emailPrefix + "@" + emailSuffix;
		}
		return value;
	}

	
	/**
	 * 格式身份证号，只显示前3和后3位数
	 * @param value
	 * @return
	 */
	public static String formatIdCard(String value){
		if(StringUtils.isNotBlank(value)){
			if(value.length() > 6){
				value = value.substring(0, 3)
						+ "******"
						+ value.substring(value.length()-3, value.length());
			}else{
				value = "******";
			}
		}
		return value;
	}
	
	public static void main(String[] args) {
		String value = StringUtil.formatIdCard("2234");
		logger.info(value);
	}
}
