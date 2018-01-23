package com.gw.das.service.website.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.ReflectionUtil;
import com.gw.das.dao.website.bean.DasStatisticsDailyChannel;
import com.gw.das.dao.website.bean.DasStatisticsReport;
import com.gw.das.dao.website.bean.DasStatisticsReportSearchBean;
import com.gw.das.service.website.DasStatisticsReportService;

/**
 * 官网统计
 */
@Service
public class DasStatisticsReportServiceImpl implements DasStatisticsReportService {

	/**
	 * 官网统计  -- （日/月）分页查询
	 */
	public PageGrid<DasStatisticsReportSearchBean> findStatisticsReportPage(PageGrid<DasStatisticsReportSearchBean> pageGrid)
			throws Exception {
		// 设置查询条件
		DasStatisticsReportSearchBean detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
			
			if(StringUtils.isBlank(detail.getSort())){
				detail.setSort("dt_day");
				if("months".equals(detail.getReportType())){
					detail.setSort("dt_month");
				}
				detail.setOrder("desc");
			}else{				
				String sort = ReflectionUtil.getColumnName(detail.getSort(),DasStatisticsReport.class);
				if(StringUtils.isNotBlank(sort)){
					detail.setSort(sort);
					detail.setOrder(pageGrid.getOrder());
				}			
			}	
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.DasStatisticsReportPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasStatisticsReport> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasStatisticsReport>>() {
				});

		PageGrid<DasStatisticsReportSearchBean> page = new PageGrid<DasStatisticsReportSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 官网统计  -- （日/月）-不分页查询
	 */
	public List<DasStatisticsReport> findStatisticsReportList(DasStatisticsReportSearchBean searchModel) throws Exception{
		// 访问客户端
		if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("0");
		} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("1");
		}
		
		if(StringUtils.isBlank(searchModel.getSort())){
			searchModel.setSort("dt_day");
			if("months".equals(searchModel.getReportType())){
				searchModel.setSort("dt_month");
			}
			searchModel.setOrder("desc");
		}else{			
			String sort = ReflectionUtil.getColumnName(searchModel.getSort(),DasStatisticsReport.class);
			if(StringUtils.isNotBlank(sort)){
				searchModel.setSort(sort);				
			}
		}
		RpcResult rpcResult = RpcUtils.post(Constants.DasStatisticsReportList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasStatisticsReport> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasStatisticsReport>>() {
		});
		return rowsList;		
	}
	
	/**
	 * 官网统计  -- （日/月）分页查询
	 */
	public PageGrid<DasStatisticsReportSearchBean> findDailyChannelPage(PageGrid<DasStatisticsReportSearchBean> pageGrid)
			throws Exception {
		// 设置查询条件
		DasStatisticsReportSearchBean detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
			if(StringUtils.isBlank(detail.getSort())){
				detail.setSort("dt_day");
				if("months".equals(detail.getReportType())){
					detail.setSort("dt_month");
				}
				detail.setOrder("desc");
			}else{
				String sort = ReflectionUtil.getColumnName(detail.getSort(),DasStatisticsReport.class);
				if(StringUtils.isNotBlank(sort)){
					detail.setSort(sort);
					detail.setOrder(pageGrid.getOrder());
				}
			}			
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.DasStatisticsDailyChannelPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasStatisticsDailyChannel> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasStatisticsDailyChannel>>() {
				});

		PageGrid<DasStatisticsReportSearchBean> page = new PageGrid<DasStatisticsReportSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 官网统计  -- （日/月）-不分页查询
	 */
	public List<DasStatisticsDailyChannel> findDailyChannelList(DasStatisticsReportSearchBean searchModel) throws Exception{
		// 访问客户端
		if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("0");
		} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("1");
		}
		if(StringUtils.isBlank(searchModel.getSort())){
			searchModel.setSort("dt_day");
			if("months".equals(searchModel.getReportType())){
				searchModel.setSort("dt_month");
			}
			searchModel.setOrder("desc");
		}else{
			String sort = ReflectionUtil.getColumnName(searchModel.getSort(),DasStatisticsReport.class);
			if(StringUtils.isNotBlank(sort)){
				searchModel.setSort(sort);				
			}
		}
		
		RpcResult rpcResult = RpcUtils.post(Constants.DasStatisticsDailyChannelList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasStatisticsDailyChannel> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasStatisticsDailyChannel>>() {
		});
		return rowsList;		
	}
	
}
