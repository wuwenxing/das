package com.gw.das.dao.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.room.bean.DasChartRoomSearchModel;

/**
 * 
 * @author wayne
 *
 */
public class BeanToMapTest {

	private static final Logger logger = LoggerFactory.getLogger(BeanToMapTest.class);
	
	@Test
	public void test() {
		DasChartRoomSearchModel bean = new DasChartRoomSearchModel();
		bean.setPageNumber(1);
		bean.setPageSize(20);
		bean.setSid("qqqq");
		bean.setTimestamp("20170118175300");
		bean.setSign("wwwww");
		
		String jsonStr = JacksonUtil.toJSon(bean);
		logger.info(jsonStr);
		
		Map<String, String> map = BeanToMapUtil.toMap(bean);
		logger.info(map.get("pageNumber"));
		logger.info(map.get("pageSize"));
		logger.info(map.get("sid"));
		logger.info(map.get("timestamp"));
		logger.info(map.get("sign"));
		logger.info(map.get("roomNameChecked"));
		
		Map<String, String> paramMap = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>(){});
		logger.info(map.get("pageNumber"));
		logger.info(map.get("pageSize"));
		logger.info(paramMap.get("sid"));
		logger.info(paramMap.get("timestamp"));
		logger.info(paramMap.get("sign"));
		logger.info(paramMap.get("roomNameChecked"));
		
		DasChartRoomSearchModel bean2 = BeanToMapUtil.toBean(DasChartRoomSearchModel.class, paramMap);
		logger.info(bean2.getPageNumber()+"");
		logger.info(bean2.getPageSize()+"");
		logger.info(bean2.getSid());
		logger.info(bean2.getTimestamp());
		logger.info(bean2.getSign());
		
	}


	@Test
	public void test2() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageNumber", "1");
		map.put("pageSize", "20");
		map.put("sid", "qqqq");
		map.put("timestamp", "20170118175300");
		map.put("sign", "wwwww");
		map.put("roomNameChecked", "true");
		
		String jsonStr = JacksonUtil.toJSon(map);
		logger.info(jsonStr);
		
		DasChartRoomSearchModel bean = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
		logger.info(bean.getPageNumber()+"");
		logger.info(bean.getPageSize()+"");
		logger.info(bean.getSid());
		logger.info(bean.getTimestamp());
		logger.info(bean.getSign());
		
	}
	
	
}
