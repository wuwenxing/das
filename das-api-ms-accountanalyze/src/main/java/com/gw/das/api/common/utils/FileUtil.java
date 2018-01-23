package com.gw.das.api.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;

/**
 * 文件读取工具类
 * 
 * @author darren
 *
 */
public class FileUtil
{
	
	private static Log log = LogFactory.getLog(FileUtil.class);
	
	/**
	 * 读取指定文件的内容
	 * 
	 * @param classpathFile
	 * @param encoding
	 * @return
	 */
	public static String readFromClasspathAsString(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			File file = ResourceUtils.getFile("classpath:"+fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
		} catch (Exception e) {
			log.error("文件" + fileName + "读取失败:" + e.getMessage());
		}
		return sb.toString();
	}

	
}
