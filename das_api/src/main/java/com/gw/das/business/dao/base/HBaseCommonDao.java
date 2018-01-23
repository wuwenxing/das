package com.gw.das.business.dao.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HBase连接通用类
 * 
 * @author silvers
 * @since 2016-12-08
 */
public class HBaseCommonDao {

	private static final Logger logger = LoggerFactory.getLogger(HBaseCommonDao.class);
	private static Connection conn;
	private static Configuration config;
	private static Admin admin;
	private static final String RESOURCE_PATH = "/main/resources/hbase-site.xml";
	public static final String DEFAULT_FAMILY_NAME = "f1";
	public static final String MIN_CHAR = " ";
	public static final String MAX_CHAR = "{";
	public static final String CHARSET = "UTF-8";
	public static final SimpleDateFormat sdf14 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat sdf19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static {
		config = HBaseConfiguration.create();
		config.addResource(new Path(RESOURCE_PATH));
		try {
			conn = ConnectionFactory.createConnection(config);
			admin = conn.getAdmin();
		} catch (IOException e) {
			logger.error("创建HBase连接时发生异常： " + e.getMessage());
		}
	}

	/**
	 * 得到HBaseAdmin对象
	 * 
	 * @return
	 */
	public static Admin getAdmin() {
		return admin;
	}

	/**
	 * 得到HConnection对象
	 * 
	 * @return
	 */
	public static Connection getConn() {
		return conn;
	}

	/**
	 * 获得指定长度的最小字符串，用于占位
	 * 
	 * @param length
	 *            长度
	 * @return
	 */
	public static String getMinString(int length) {
		return StringUtils.repeat(MIN_CHAR, length);
	}

	/**
	 * 获得指定长度的最小字符串，用于占位
	 * 
	 * @param length
	 *            长度
	 * @return
	 */
	public static String getMaxString(int length) {
		return StringUtils.repeat(MAX_CHAR, length);
	}

	/**
	 * 将byte数组转化为字符串（使用默认字符集）
	 * 
	 * @param arr
	 *            byte数组
	 * @return 字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String byte2Str(byte[] arr) throws UnsupportedEncodingException {
		return arr == null ? "" : new String(arr, CHARSET);
	}

	/**
	 * 将字符串转化为byte数组
	 *
	 * @param str
	 * @return
	 */
	public static byte[] toByte(String str) {
		return Bytes.toBytes(str == null ? "" : str);
	}

	/**
	 * 通过Result对象和列名获取该列的值（列族为默认列族名）
	 * 
	 * @param result
	 *            Result对象
	 * @param qualifier
	 *            列名
	 * @return 值
	 * @throws UnsupportedEncodingException
	 */
	public static String getValueByResult(Result result, String qualifier) throws UnsupportedEncodingException {
		return byte2Str(result.getValue(toByte(DEFAULT_FAMILY_NAME), toByte(qualifier)));
	}

	/**
	 * 通过Result对象、列族和列名获取该列的值
	 * 
	 * @param result
	 *            Result对象
	 * @param family
	 *            列族
	 * @param qualifier
	 *            列
	 * @return 值
	 * @throws UnsupportedEncodingException
	 */
	public static String getValueByResult(Result result, String family, String qualifier)
			throws UnsupportedEncodingException {
		return byte2Str(result.getValue(toByte(family), toByte(qualifier)));
	}

	/**
	 * 关闭ResultScanner和Table方法，查询后finally中调用
	 * 
	 * @param rs
	 *            ResultScanner对象
	 * @param table
	 *            HTableInterface对象
	 */
	public static void closeRsAndTable(ResultScanner rs, Table table) {
		if (rs != null) {
			rs.close();
		}
		if (table != null) {
			try {
				table.close();
			} catch (IOException e) {
				logger.error("HBaseCommonDao--close--1:" + e.getMessage());
			}
		}
	}

}
