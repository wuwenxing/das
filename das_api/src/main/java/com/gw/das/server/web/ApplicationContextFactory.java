package com.gw.das.server.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Sprint ApplicationContext Factory
 */
public class ApplicationContextFactory {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextFactory.class);

	private static ApplicationContext ctx;
	private static boolean flag = false;

	public static ApplicationContext getApplicationContext() {
		if (ctx == null) {
			logger.info("init ApplicationContext ");
			return _getApplicationContext();
		}
		return ctx;
	}

	private synchronized static ApplicationContext _getApplicationContext() {
		if (ctx == null) {
			if (flag == true) {
				return null;
			}
			flag = true;
			ctx = new ClassPathXmlApplicationContext(new String[] { "spring-api.xml", "spring-dao.xml" });
			flag = false;
		}
		return ctx;
	}
}