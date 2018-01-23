package com.gw.das.service.dataSourceList.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.dataSourceList.bean.DataSourceListSearchModel;
import com.gw.das.dao.dataSourceList.bean.RoomDataSource;
import com.gw.das.dao.dataSourceList.bean.WebsiteBehaviorDataSource;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.dataSourceList.DataSourceListService;

/**
 * 数据源列表接口实现
 * @author darren
 *
 */
@Service
public class DataSourceListServiceImpl extends BaseService implements DataSourceListService {

	/**
	 * 分页查询官网行为数据源记录-elasticsearch
	 */
	@Override
	public PageGrid<DataSourceListSearchModel> findWebsiteBehaviorDataSourcePageList(PageGrid<DataSourceListSearchModel> pageGrid)
			throws Exception {
		DataSourceListSearchModel detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		
		if(StringUtils.isNotBlank(detail.getPlatformType())){
			if(ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())){
				detail.setPlatformType("1");
			}else{
				detail.setPlatformType("0");
			}	
		}
		
		RpcResult rpcResult = RpcUtils.post(Constants.findWebsiteBehaviorDataSourcePageList, BeanToMapUtil.toMap(detail), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");
		List<WebsiteBehaviorDataSource> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<WebsiteBehaviorDataSource>>(){});		
		for (WebsiteBehaviorDataSource websiteBehaviorDataSource : rowsList) {
			String visitTime = websiteBehaviorDataSource.getVisitTime();
			websiteBehaviorDataSource.setVisitTime(DateUtil.DateString2formatString(String.valueOf(visitTime)));
		}
		
		PageGrid<DataSourceListSearchModel> page = new PageGrid<DataSourceListSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 
		return page;
	}
	
	/**
	 * 不分页查询官网行为数据源记录-elasticsearch
	 */
	@Override
	public List<WebsiteBehaviorDataSource> findWebsiteBehaviorDataSourceList(DataSourceListSearchModel searchBean)
			throws Exception {	
		if(StringUtils.isNotBlank(searchBean.getPlatformType())){
			if(ClientEnum.mobile.getLabelKey().equals(searchBean.getPlatformType())){
				searchBean.setPlatformType("1");
			}else{
				searchBean.setPlatformType("0");
			}
		}
		
		
		RpcResult rpcResult = RpcUtils.post(Constants.findWebsiteBehaviorDataSourceList, BeanToMapUtil.toMap(searchBean), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		return JacksonUtil.readValue(rows, new TypeReference<List<WebsiteBehaviorDataSource>>(){});			
	}
	
	/**
	 * 分页查询直播间数据源记录-elasticsearch
	 */
	@Override
	public PageGrid<DataSourceListSearchModel> findRoomDataSourcePageList(PageGrid<DataSourceListSearchModel> pageGrid)
			throws Exception {
		DataSourceListSearchModel detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		if(StringUtils.isNotBlank(detail.getPlatformType())){
			if(ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())){
				detail.setPlatformType("1");
			}else{
				detail.setPlatformType("0");
			}
		}
		
		RpcResult rpcResult = RpcUtils.post(Constants.findRoomDataSourcePageList, BeanToMapUtil.toMap(detail), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");
		List<RoomDataSource> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<RoomDataSource>>(){});
		
		for (RoomDataSource roomDataSource : rowsList) {
			String operationTime = roomDataSource.getOperationTime();
			roomDataSource.setOperationTime(DateUtil.DateString2formatString(String.valueOf(operationTime)));
			roomDataSource.setUserTel(StringUtil.formatPhone(roomDataSource.getUserTel()));
			roomDataSource.setEmail(StringUtil.formatEmail(roomDataSource.getEmail()));
		}
		
		PageGrid<DataSourceListSearchModel> page = new PageGrid<DataSourceListSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 
		return page;
	}
	
	/**
	 * 不分页查询直播间数据源记录-elasticsearch
	 */
	@Override
	public List<RoomDataSource> findRoomDataSourceList(DataSourceListSearchModel searchBean)
			throws Exception {	
		if(StringUtils.isNotBlank(searchBean.getPlatformType())){
			if(ClientEnum.mobile.getLabelKey().equals(searchBean.getPlatformType())){
				searchBean.setPlatformType("1");
			}else{
				searchBean.setPlatformType("0");
			}
		}
		
		RpcResult rpcResult = RpcUtils.post(Constants.findRoomDataSourceList, BeanToMapUtil.toMap(searchBean), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		return JacksonUtil.readValue(rows, new TypeReference<List<RoomDataSource>>(){});			
	}
	

}
