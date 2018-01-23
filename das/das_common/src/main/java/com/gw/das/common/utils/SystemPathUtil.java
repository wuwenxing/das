package com.gw.das.common.utils;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目路径工具类
 */
public class SystemPathUtil {

	/**
	 * 获取项目顶层根目录
	 */
	public static String getTopDirectoryPath() {
		String path = "";
		String osName = System.getProperties().getProperty("os.name");
		if (osName.toLowerCase().startsWith("win")) {
			// 用户的当前工作目录
			String realPath = SystemCacheUtil.servletContext.getRealPath("/");
			path = realPath.substring(0, realPath.indexOf("\\") + 1);
		} else {
			path = "";
		}

		if ("\\".equals(File.separator)) {
			// windows
			return path.replaceFirst("file:/", "");
		} else if ("/".equals(File.separator)) {
			return path.replaceFirst("file:", "");
		} else {
			// windows
			return path.replaceFirst("file:/", "");
		}
	}

	/**
	 * 获取tomcat下webapp目录路径
	 */
	public static String getWebAppPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	/**
	 * 获得请求路径
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI();
		if (requestPath.indexOf("?") > -1) { // 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("?"));
		}
		if (requestPath.indexOf("&") > -1) { // 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1); // 去掉项目路径
		return requestPath;
	}

}
