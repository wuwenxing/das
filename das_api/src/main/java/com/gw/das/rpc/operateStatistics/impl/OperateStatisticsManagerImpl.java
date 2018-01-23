package com.gw.das.rpc.operateStatistics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.operateStatistics.ChannelActiveWideDao;
import com.gw.das.business.dao.operateStatistics.ChannelEffectWideDao;
import com.gw.das.business.dao.operateStatistics.CustomerQualityWideDao;
import com.gw.das.business.dao.operateStatistics.entity.ChannelActiveEffectVO;
import com.gw.das.business.dao.operateStatistics.entity.CustomerQualityVO;
import com.gw.das.business.dao.operateStatistics.entity.OperateStatisticsModel;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.operateStatistics.OperateStatisticsManager;

public class OperateStatisticsManagerImpl extends ManagerImpl implements OperateStatisticsManager {

	private static final Logger logger = LoggerFactory.getLogger(OperateStatisticsManagerImpl.class);

	/**
	 * 分页查询渠道新增、活跃报表（client）记录
	 */
	@Override
	public Map<String, String> findChannelActivePageList(String jsonStr) throws Exception {
		try {
			PageGrid<OperateStatisticsModel> pg = new PageGrid<OperateStatisticsModel>();
			OperateStatisticsModel model = JacksonUtil.readValue(jsonStr, OperateStatisticsModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			ChannelActiveWideDao channelActiveWideDao = getService(ChannelActiveWideDao.class);
			pg = channelActiveWideDao.queryForPage(pg, new ChannelActiveEffectVO());
			List<ChannelActiveEffectVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询渠道新增、活跃报表（client）记录数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询渠道新增、活跃报表（client）记录数据接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询渠道新增、活跃报表（client）记录
	 */
	@Override
	public Map<String, String> findChannelActiveList(String jsonStr) throws Exception {
		try {
			OperateStatisticsModel model = JacksonUtil.readValue(jsonStr, OperateStatisticsModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			ChannelActiveWideDao channelActiveWideDao = getService(ChannelActiveWideDao.class);
			List<ChannelActiveEffectVO> list = channelActiveWideDao.queryForList(model,new ChannelActiveEffectVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询渠道新增、活跃报表（client）记录数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询渠道新增、活跃报表（client）记录数据异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分页查询渠道新增、活跃、效果报表（web）记录
	 */
	@Override
	public Map<String, String> findChannelEffectPageList(String jsonStr) throws Exception {
		try {
			PageGrid<OperateStatisticsModel> pg = new PageGrid<OperateStatisticsModel>();
			OperateStatisticsModel model = JacksonUtil.readValue(jsonStr, OperateStatisticsModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			ChannelEffectWideDao channelEffectWideDao = getService(ChannelEffectWideDao.class);
			pg = channelEffectWideDao.queryForPage(pg, new ChannelActiveEffectVO());
			List<ChannelActiveEffectVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询渠道新增、活跃、效果报表（web）记录数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询渠道新增、活跃、效果报表（web）记录数据接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询渠道新增、活跃、效果报表（web）记录
	 */
	@Override
	public Map<String, String> findChannelEffectList(String jsonStr) throws Exception {
		try {
			OperateStatisticsModel model = JacksonUtil.readValue(jsonStr, OperateStatisticsModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			ChannelEffectWideDao channelEffectWideDao = getService(ChannelEffectWideDao.class);
			List<ChannelActiveEffectVO> list = channelEffectWideDao.queryForList(model,new ChannelActiveEffectVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询渠道新增、活跃、效果报表（web）记录数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询渠道新增、活跃、效果报表（web）记录数据异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> findCustomerQualityPageList(String jsonStr) throws Exception {
		try {
			PageGrid<OperateStatisticsModel> pg = new PageGrid<OperateStatisticsModel>();
			OperateStatisticsModel model = JacksonUtil.readValue(jsonStr, OperateStatisticsModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			CustomerQualityWideDao customerQualityWideDao = getService(CustomerQualityWideDao.class);
			pg = customerQualityWideDao.queryForPage(pg, new CustomerQualityVO());
			List<CustomerQualityVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询获客质量（web、client）记录数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询获客质量（web、client）记录数据接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> findCustomerQualityList(String jsonStr) throws Exception {
		try {
			OperateStatisticsModel model = JacksonUtil.readValue(jsonStr, OperateStatisticsModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			CustomerQualityWideDao customerQualityWideDao = getService(CustomerQualityWideDao.class);
			List<CustomerQualityVO> list = customerQualityWideDao.queryForList(model,new CustomerQualityVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询获客质量（web、client）记录数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询获客质量（web、client）记录数据异常:" + e.getMessage());
		}
	}
}
