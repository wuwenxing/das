package com.gw.das.api.cache;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * redis缓存实现
 * 
 * @author darren
 *
 */
@Service
public class RedisCacheManager implements ApiCacheManager {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 放入缓存，默认时间5分钟
	 * 
	 * @param key
	 * @param data
	 * @param expire
	 */
	@Override
	public void put(String key, Object data) {
		long expire = 5 * 60 * 1000;
		redisTemplate.boundValueOps(key).set(data, expire, TimeUnit.MILLISECONDS);
	}

	/**
	 * 放入缓存
	 * 
	 * @param key
	 * @param data
	 * @param expire
	 */
	@Override
	public void put(String key, Object data, long expire) {
		redisTemplate.boundValueOps(key).set(data, expire, TimeUnit.MILLISECONDS);
	}

	/**
	 * 放入缓存
	 * 
	 * @param key
	 * @param data
	 * @param expire
	 * @param timeUnit
	 */
	@Override
	public void put(String key, Object data, long expire, TimeUnit timeUnit) {
		redisTemplate.boundValueOps(key).set(data, expire, timeUnit);
	}

	/**
	 * 获取已缓存的值
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Object get(String key) {
		return redisTemplate.boundValueOps(key).get();
	}
}
