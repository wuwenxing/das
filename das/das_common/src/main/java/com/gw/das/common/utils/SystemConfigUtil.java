package com.gw.das.common.utils;

import java.util.Properties;

import org.csource.fastdfs.ClientGlobal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.gw.das.common.enums.SystemConfigEnum;

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
	private static final String database = "/database.properties";
	private static final String sysConfig = "/sysConfig.properties";

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
			properties.load(SystemConfigUtil.class.getResourceAsStream(database));
			properties.load(SystemConfigUtil.class.getResourceAsStream(sysConfig));
			
			// 上传文件配置文件加载初始化-BaseController.upload()建立连接用到
			ClassPathResource cpr = new ClassPathResource(sysConfig);
			ClientGlobal.init(cpr.getClassLoader().getResource(sysConfig).getPath());
		} catch (Exception e) {
			logger.error("获取系统配置参数error", e);
			throw new RuntimeException(e);
		}
	}

	public static Properties getProperties() {
		return properties;
	}

	public static String getProperty(SystemConfigEnum systemConfigEnum) {
		return properties.getProperty(systemConfigEnum.getLabelKey());
	}

}
