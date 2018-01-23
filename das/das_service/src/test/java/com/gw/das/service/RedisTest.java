package com.gw.das.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest {

	private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);
	
	private RedisTemplate<String, Object> redisTemplate;

	private String key = "rightList";

	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		redisTemplate = ApplicationUtil.getService(RedisTemplate.class);
	}

	/**
	 * 添加队列消息
	 * 获取队列消息
	 */
	@Test
	public void add() {
		// list队列
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.rightPush(key, "1");
		list.rightPush(key, "2");
		list.rightPush(key, "3");
	}

	/**
	 * 获取队列消息
	 */
	@Test
	public void get() {
		// list队列
		ListOperations<String, Object> list = redisTemplate.opsForList();

		String value1 = list.leftPop(key) + "";
		String value2 = list.leftPop(key) + "";
		String value3 = list.leftPop(key) + "";

		logger.info(value1);
		logger.info(value2);
		logger.info(value3);
	}
	
	
	
}
