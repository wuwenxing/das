
package com.gw.das.common.utils;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * 
 * <p>Title: UUID工具类</p>
 * <p>Description: 生成一个32位的UUID</p>
 * <p>Copyright:Copyright(c)2016</p>
 * <p>Company:Gateway Technology</p>
 * @date 2016年2月2日
 * @author hugo
 * @version 1.0
 */
public final class UUIDUtil {

	private static final TimeBasedGenerator TG = Generators.timeBasedGenerator();

	/**
	 * 构造函数
	 */
	private UUIDUtil() {
	}

	/**
	 * 获取uuid（版本为v1，即time格式）<br>
	 * uuid标准格式，字符串-相连
	 * 
	 * @return UUID
	 */
	public static String generateHexUUID() {
		return TG.generate().toString();
	}

	/**
	 * 生成一个32位的UUID（版本为v1，即time格式）<br>
	 * 字符串中不带-
	 * 
	 * @return 生成的UUID
	 */
	public static String getUUID() {
		return generateHexUUID().replaceAll("-", "");
	}

	/**
	 * 生成一个32位的UUID（版本为v4，即随机字符串）<br>
	 * 字符串中不带-
	 * 
	 * @return 生成的UUID
	 */
	public static String getRandomUUID() {
		return Generators.randomBasedGenerator().generate().toString().replaceAll("-", "");
	}
}
