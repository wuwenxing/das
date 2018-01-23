package com.gw.das.service.dataSourceList;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.dataSourceList.bean.DataSourceListSearchModel;
import com.gw.das.dao.dataSourceList.bean.RoomDataSource;
import com.gw.das.dao.dataSourceList.bean.WebsiteBehaviorDataSource;

/**
 * 数据源列表接口
 * 
 * @author darren
 *
 */
public interface DataSourceListService {

	/**
	 * 分页查询官网行为数据源记录-elasticsearch
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<DataSourceListSearchModel> findWebsiteBehaviorDataSourcePageList(PageGrid<DataSourceListSearchModel> pageGrid)throws Exception;
	
	/**
	 * 不分页查询官网行为数据源记录-elasticsearch
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public List<WebsiteBehaviorDataSource> findWebsiteBehaviorDataSourceList(DataSourceListSearchModel searchBean)throws Exception;
	
	/**
	 * 分页查询直播间数据源记录-elasticsearch
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<DataSourceListSearchModel> findRoomDataSourcePageList(PageGrid<DataSourceListSearchModel> pageGrid)throws Exception;
	
	/**
	 * 不分页查询直播间数据源记录-elasticsearch
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public List<RoomDataSource> findRoomDataSourceList(DataSourceListSearchModel searchBean)throws Exception;
	
}
