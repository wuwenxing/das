package com.gw.das.common.utils;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

/**
 * 系统缓存数据工具类
 * 缓存数据-统一在SystemCacheListener里初始化
 * @author wayne
 */
public class SystemCacheUtil {

	/**
	 * 缓存servletContext
	 */
	public static ServletContext servletContext;
	
	/**
	 * 缓存springContext
	 */
	public static WebApplicationContext springContext;
	
}
