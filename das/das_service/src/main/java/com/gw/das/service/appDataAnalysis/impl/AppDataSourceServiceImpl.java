package com.gw.das.service.appDataAnalysis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisSearchModel;
import com.gw.das.dao.appDataAnalysis.bean.DasAppOdsVO;
import com.gw.das.service.appDataAnalysis.AppDataSourceService;

/**
 * app数据源报表接口
 * 
 * @author darren
 *
 */
@Service
public class AppDataSourceServiceImpl implements AppDataSourceService{

	/**
	 * app数据源报表记录-elasticsearch
	 */
	@Override
	public PageGrid<AppDataAnalysisSearchModel> findAppDataSourcePageList(PageGrid<AppDataAnalysisSearchModel> pageGrid)
			throws Exception {
		AppDataAnalysisSearchModel detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		
		RpcResult rpcResult = RpcUtils.post(Constants.findAppDataSourcePageList, BeanToMapUtil.toMap(detail), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");
		List<DasAppOdsVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasAppOdsVO>>(){});
		if(ReportTypeEnum.hours.getLabelKey().equals(detail.getReportType())){
			for (DasAppOdsVO dasAppOdsVO : rowsList) {
				dasAppOdsVO.setDatetime(dasAppOdsVO.getDatetime()+" "+dasAppOdsVO.getHour());
			}
		}
		if(ReportTypeEnum.weeks.getLabelKey().equals(detail.getReportType())){
			for (DasAppOdsVO dasAppOdsVO : rowsList) {
				dasAppOdsVO.setWeeks(dasAppOdsVO.getWeeks().split("-")[1]);
			}
		}
		
		DasAppOdsVO foot = new DasAppOdsVO();
		for (DasAppOdsVO dasAppOdsVO : rowsList) {
			foot.setDeviceidCount(foot.getDeviceidCount()+dasAppOdsVO.getDeviceidCount());
			foot.setDeviceidNum(foot.getDeviceidNum()+dasAppOdsVO.getDeviceidNum());
		}
		List<DasAppOdsVO> footer = new ArrayList<DasAppOdsVO>();
		footer.add(foot);
		
		PageGrid<AppDataAnalysisSearchModel> page = new PageGrid<AppDataAnalysisSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 
		page.setFooter(footer);
		return page;
	}
	
	/**
	 * 不分页查询app数据源报表记录-elasticsearch
	 */
	@Override
	public List<DasAppOdsVO> findAppDataSourceList(AppDataAnalysisSearchModel searchBean)
			throws Exception {	
		RpcResult rpcResult = RpcUtils.post(Constants.findAppDataSourceList, BeanToMapUtil.toMap(searchBean), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		List<DasAppOdsVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasAppOdsVO>>(){});		
		if(ReportTypeEnum.hours.getLabelKey().equals(searchBean.getReportType())){
			for (DasAppOdsVO dasAppOdsVO : rowsList) {
				dasAppOdsVO.setDatetime(dasAppOdsVO.getDatetime()+" "+dasAppOdsVO.getHour());
			}
		}
		if(ReportTypeEnum.weeks.getLabelKey().equals(searchBean.getReportType())){
			for (DasAppOdsVO dasAppOdsVO : rowsList) {
				dasAppOdsVO.setWeeks(dasAppOdsVO.getWeeks().split("-")[1]);
			}
		}
		return rowsList;
	}
    
	
}
