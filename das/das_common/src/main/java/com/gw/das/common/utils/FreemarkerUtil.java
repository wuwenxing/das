package com.gw.das.common.utils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

/**
 * Freemarker工具类-动态格式化内容
 * @author wayne
 */
public class FreemarkerUtil {

	private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

	/**
	 * FreemarkerUtil实例
	 */
	private static FreemarkerUtil freemarker;
	
	/**
	 * 模板引擎配置
	 */
	private static Configuration configuration;

	/**
	 * 模板的存放位置
	 */
	private static final String TEMPLATE_PATH = "/template";

	/**
	 * freemarker版本号
	 */
	private static final String FREEMARKER_VERSION = "2.3.22";

	/**
	 * 模板缓存
	 */
	private static final Map<String, Template> TEMPLATE_CACHE = new HashMap<String, Template>();

	/**
	 * 不允许创建实例
	 */
	private FreemarkerUtil() {

	}

	public static FreemarkerUtil getInstance() {
		if (null == freemarker) {
			configuration = new Configuration(new Version(FREEMARKER_VERSION));
			configuration.setServletContextForTemplateLoading(SystemCacheUtil.servletContext, TEMPLATE_PATH);
			configuration.setEncoding(Locale.getDefault(), "UTF-8");
			configuration.setDateFormat("yyyy-MM-dd HH:mm:ss");
			freemarker = new FreemarkerUtil();
		}
		return freemarker;
	}

	public String getText(String templateFile, Map<Object, Object> parameters) {
		try {
			Template template = TEMPLATE_CACHE.get(templateFile);
			if (template == null) {
				template = configuration.getTemplate(templateFile);
				TEMPLATE_CACHE.put(templateFile, template);
			}
			StringWriter stringWriter = new StringWriter();
			template.process(parameters, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			logger.error("FreemarkerUtil获取内容error", e);
			throw new RuntimeException(e);
		}
	}
	
}
