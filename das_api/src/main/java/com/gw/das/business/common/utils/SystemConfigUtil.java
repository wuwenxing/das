package com.gw.das.business.common.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统配置参数获取工具类
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
	private static final String netty = "/conf-netty.properties";
	private static final String rpc_website = "/rpc-website.properties";
	private static final String rpc_room = "/rpc-room.properties";
	private static final String rpc_trade = "/rpc-trade.properties";
	private static final String rpc_app = "/rpc-app.properties";
	private static final String jdbc = "/conf-jdbc.properties";
	private static final String elasticsearch = "/conf-elasticsearch.properties";
	private static final String rpc_dataSource = "/rpc-dataSource.properties";
	private static final String rpc_operateStatistics = "/rpc-operateStatistics.properties";
	/**
	 * 不允许创建实例
	 */
	private SystemConfigUtil() {

	}
	
	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static{
		try {
			logger.info("加载配置文件,并为全局变量初始化赋值开始...");
			// 配置文件加载初始化到properties
			properties.load(SystemConfigUtil.class.getResourceAsStream(netty));
			properties.load(SystemConfigUtil.class.getResourceAsStream(rpc_website));
			properties.load(SystemConfigUtil.class.getResourceAsStream(rpc_room));
			properties.load(SystemConfigUtil.class.getResourceAsStream(rpc_trade));
			properties.load(SystemConfigUtil.class.getResourceAsStream(rpc_app));
			properties.load(SystemConfigUtil.class.getResourceAsStream(jdbc));
			properties.load(SystemConfigUtil.class.getResourceAsStream(elasticsearch));
			properties.load(SystemConfigUtil.class.getResourceAsStream(rpc_dataSource));
			properties.load(SystemConfigUtil.class.getResourceAsStream(rpc_operateStatistics));
			logger.info("加载配置文件,并为全局变量初始化赋值结束...");
		} catch (Exception e) {
			logger.error("获取系统配置参数error", e);
			throw new RuntimeException(e);
		}
	}
	
	public static Properties getProperties(){
		return properties;
	}
	
}
