package com.gw.das.service;

public class ApplicationUtil {

	public static <T> T getService(Class<T> t) {
		return ApplicationContextFactory.getApplicationContext().getBean(t);
	}
	
}
