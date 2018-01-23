package com.gw.das.api.common.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.api.common.enums.SystemConfigEnum;

/**
 * 系统配置参数获取工具类
 * 
 * @author wayne
 */
public class SystemConfigUtil {

	private static final Logger logger = LoggerFactory.getLogger(SystemConfigUtil.class);

	/**
	 * Properties类,保存设置的参数
	 */
	private static final Properties properties = new Properties();

	/**
	 * 配置文件路径
	 */
	private static final String application = "/application.properties";
	
	/**
	 * 配置文件路径-前缀
	 */
	private static final String application_prefix = "/application-";
	
	/**
	 * 配置文件路径-后缀
	 */
	private static final String application_suffix = ".properties";
	
	/**
	 * 不允许创建实例
	 */
	private SystemConfigUtil() {

	}

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		try {
			// 配置文件加载初始化到properties
			properties.load(SystemConfigUtil.class.getResourceAsStream(application));
			String springProfilesActive = properties.getProperty(SystemConfigEnum.springProfilesActive.getLabelKey());
			String path = application_prefix + springProfilesActive + application_suffix;
			properties.load(SystemConfigUtil.class.getResourceAsStream(path));
		} catch (Exception e) {
			logger.error("获取系统配置参数error", e);
			throw new RuntimeException(e);
		}
	}

	public static Properties getProperties() {
		return properties;
	}

	public static String getProperty(SystemConfigEnum sysConfigEnum) {
		return properties.getProperty(sysConfigEnum.getLabelKey());
	}

}
