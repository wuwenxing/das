package com.gw.das.service.appDataAnalysis.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisSearchModel;
import com.gw.das.dao.appDataAnalysis.bean.DasAppOdsVO;
import com.gw.das.service.appDataAnalysis.DasAppOdsService;

/**
 * app数据源报表接口
 * 
 * @author darren
 *
 */
@Service
public class DasAppOdsServiceImpl implements DasAppOdsService{

	/**
	 * app数据源报表记录-elasticsearch
	 */
	@Override
	public PageGrid<AppDataAnalysisSearchModel> findDasAppOdsPageList(PageGrid<AppDataAnalysisSearchModel> pageGrid)
			throws Exception {
		AppDataAnalysisSearchModel detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		
		RpcResult rpcResult = RpcUtils.post(Constants.findDasAppOdsPageList, BeanToMapUtil.toMap(detail), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");
		List<DasAppOdsVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasAppOdsVO>>(){});
		
		
		PageGrid<AppDataAnalysisSearchModel> page = new PageGrid<AppDataAnalysisSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 
		return page;
	}
	
	/**
	 * 不分页查询app数据源报表记录-elasticsearch
	 */
	@Override
	public List<DasAppOdsVO> findDasAppOdsList(AppDataAnalysisSearchModel searchBean)
			throws Exception {	
		RpcResult rpcResult = RpcUtils.post(Constants.findDasAppOdsList, BeanToMapUtil.toMap(searchBean), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		List<DasAppOdsVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasAppOdsVO>>(){});					
		return rowsList;
	}
    
	
}
