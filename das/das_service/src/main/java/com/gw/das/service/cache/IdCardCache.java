package com.gw.das.service.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.gw.das.common.utils.SystemCacheUtil;

/**
 * 身份证解密缓存处理
 * @author wayne
 *
 */
public class IdCardCache {

	private static final Logger logger = LoggerFactory.getLogger(IdCardCache.class);

	private static RedisTemplate<String, Object> redisTemplate = null;
	
	/**
	 * 缓存身份证号
	 * Map<companyId, LazyRefreshable<Map<菜单Url,菜单Code>> dictMap
	 */
	private static Map<String, String> idCardMap = new HashMap<String, String>();
	
	/**
	 * key
	 */
	private static String idCardKey = "idCardMap";

	static {
		redisTemplate = (RedisTemplate<String, Object>)SystemCacheUtil.springContext.getBean("redisTemplate");
		Object map = redisTemplate.boundValueOps(idCardKey).get();
		if(null != map){
			idCardMap = (Map<String, String>)map;
		}
	}

	/**
	 * 当有新值时调用此方法重新设值
	 */
	public static void refresh() throws Exception{
		try{
			redisTemplate.delete(idCardKey);
			redisTemplate.boundValueOps(idCardKey).set(idCardMap);
		}catch(Exception e){
			logger.error("redis重新设值异常", e);
		}
	}
	
	public static String get(String idCardEncrypt){
		return idCardMap.get(idCardEncrypt);
	}

	public static String put(String idCardEncrypt, String value){
		return idCardMap.put(idCardEncrypt, value);
	}
}
