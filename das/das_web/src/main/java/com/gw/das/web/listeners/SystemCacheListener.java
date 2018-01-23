package com.gw.das.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gw.das.common.utils.SystemCacheUtil;

/**
 * 参数缓存监听器-作用：缓存数据到内存
 * 
 * @author wayne
 */
public class SystemCacheListener implements ServletContextListener {

	private final static Logger logger = Logger.getLogger(SystemCacheListener.class);

	// 获取spring应用上下文
	private WebApplicationContext springContext;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			SystemCacheUtil.servletContext = arg0.getServletContext();
			springContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
			if (springContext != null) {
				SystemCacheUtil.springContext = springContext;
				// 缓存数据
				cacheData();
			}
		} catch (Exception e) {
			logger.error("获取spring应用上下文失败!");
		}
	}

	/**
	 * 缓存数据
	 */
	public void cacheData() {

	}

}
