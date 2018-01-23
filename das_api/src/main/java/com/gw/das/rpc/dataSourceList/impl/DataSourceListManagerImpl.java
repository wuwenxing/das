package com.gw.das.rpc.dataSourceList.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.dataSourceList.DataSourceListDao;
import com.gw.das.business.dao.dataSourceList.entity.DataSourceListModel;
import com.gw.das.business.dao.dataSourceList.entity.RoomDataSource;
import com.gw.das.business.dao.dataSourceList.entity.WebsiteBehaviorDataSource;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.dataSourceList.DataSourceListManager;

/**
 * 数据源列表接口实现类
 * 
 * @author darren
 *
 */
public class DataSourceListManagerImpl extends ManagerImpl implements DataSourceListManager {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceListManagerImpl.class);
	
	/**
	 * 
	 * 官网行为数据源记录查询-分页-elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findWebsiteBehaviorDataSourcePageList(String jsonStr) throws Exception{
		try {
			PageGrid<DataSourceListModel> pg = new PageGrid<DataSourceListModel>();
			DataSourceListModel model = JacksonUtil.readValue(jsonStr, DataSourceListModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			
			DataSourceListDao dataSourceListDao = getService(DataSourceListDao.class);
			pg = dataSourceListDao.findWebsiteBehaviorDataSourcePageList(model);
			List<WebsiteBehaviorDataSource> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("分页查询所官网行为数据源记录失败:" + e.getMessage(), e);
			throw new Exception("分页查询所官网行为数据源记录失败:" + e.getMessage());
		}
	}
	
	/**
	 * 
	 *  官网行为数据源记录查询-不分页-elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findWebsiteBehaviorDataSourceList(String jsonStr) throws Exception{
		try {
			DataSourceListModel model = JacksonUtil.readValue(jsonStr, DataSourceListModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			
			DataSourceListDao dataSourceListDao = getService(DataSourceListDao.class);
			List<WebsiteBehaviorDataSource> list = dataSourceListDao.findWebsiteBehaviorDataSourceList(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("不分页查询所官网行为数据源记录失败:" + e.getMessage(), e);
			throw new Exception("不分页查询所官网行为数据源记录失败:" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * 直播间数据源记录查询-分页-elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findRoomDataSourcePageList(String jsonStr) throws Exception{
		try {
			PageGrid<DataSourceListModel> pg = new PageGrid<DataSourceListModel>();
			DataSourceListModel model = JacksonUtil.readValue(jsonStr, DataSourceListModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			
			DataSourceListDao dataSourceListDao = getService(DataSourceListDao.class);
			pg = dataSourceListDao.findRoomDataSourcePageList(model);
			List<RoomDataSource> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("分页查询直播间数据源记录失败:" + e.getMessage(), e);
			throw new Exception("分页查询直播间数据源记录失败:" + e.getMessage());
		}
	}
	
	/**
	 * 
	 *  直播间数据源记录查询-不分页-elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findRoomDataSourceList(String jsonStr) throws Exception{
		try {
			DataSourceListModel model = JacksonUtil.readValue(jsonStr, DataSourceListModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			
			DataSourceListDao dataSourceListDao = getService(DataSourceListDao.class);
			List<RoomDataSource> list = dataSourceListDao.findRoomDataSourceList(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("不分页查询直播间数据源记录失败:" + e.getMessage(), e);
			throw new Exception("不分页查询直播间数据源记录失败:" + e.getMessage());
		}
	}

	
}
