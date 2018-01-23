package com.gw.das.service.website.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.website.bean.DasFlowStatistics;
import com.gw.das.dao.website.bean.DasFlowStatisticsAverage;
import com.gw.das.dao.website.bean.DasFlowStatisticsTreeMedia;
import com.gw.das.dao.website.bean.DasFlowStatisticsSearchBean;
import com.gw.das.service.website.DasFlowStatisticsService;

/**
 * 流量统计列表
 */
@Service
public class DasFlowStatisticsServiceImpl implements DasFlowStatisticsService {

	/**
	 * 流量统计列表
	 */
	public List<DasFlowStatistics> findList(DasFlowStatisticsSearchBean bean) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsBehaviorList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatistics>>() {});
		return rowsList;
	}
	
	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findPageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception {
		DasFlowStatisticsSearchBean bean = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");
		parameters.put("sortName", pageGrid.getSort());
		parameters.put("sortDirection", pageGrid.getOrder());

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsBehaviorPage, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();

		List<DasFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatistics>>() {});
		PageGrid<DasFlowStatisticsSearchBean> page = new PageGrid<DasFlowStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 流量统计列表
	 */
	public List<DasFlowStatistics> findMediaList(DasFlowStatisticsSearchBean bean) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsMediaList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatistics>>() {});
		return rowsList;
	}
		
	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findMediaPageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception {
		DasFlowStatisticsSearchBean bean = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");
		parameters.put("sortName", pageGrid.getSort());
		parameters.put("sortDirection", pageGrid.getOrder());

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsMediaPage, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();		

		List<DasFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatistics>>() {});
		PageGrid<DasFlowStatisticsSearchBean> page = new PageGrid<DasFlowStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findMediaTreePageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception {
		DasFlowStatisticsSearchBean bean = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");
		parameters.put("sortName", pageGrid.getSort());
		parameters.put("sortDirection", pageGrid.getOrder());

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsMediaTreePage, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();
		List<DasFlowStatisticsTreeMedia> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatisticsTreeMedia>>() {});
		
		String contrastFront = resultMap.get("contrastFront");
		String contrastBack = resultMap.get("contrastBack");
		List<DasFlowStatisticsTreeMedia> contrastFrontList = null;
		List<DasFlowStatisticsTreeMedia> contrastBackList = null;
		if(StringUtils.isNotBlank(contrastFront)){
			 contrastFrontList = JacksonUtil.readValue(contrastFront, new TypeReference<List<DasFlowStatisticsTreeMedia>>() {});
		}
		if(StringUtils.isNotBlank(contrastFront)){
			contrastBackList = JacksonUtil.readValue(contrastBack, new TypeReference<List<DasFlowStatisticsTreeMedia>>() {});
		}		
		
		NumberFormat df = NumberFormat.getNumberInstance();
        df.setMaximumFractionDigits(6);
		List<DasFlowStatisticsTreeMedia> resultList = new ArrayList<DasFlowStatisticsTreeMedia>();
		int k = 0;
		for (int i = 0; i < rowsList.size(); i++)
		{
			k++;
			DasFlowStatisticsTreeMedia das = rowsList.get(i);
			DasFlowStatisticsTreeMedia dasBean = new DasFlowStatisticsTreeMedia();
			dasBean.setId(k);
			k++;
			dasBean.setUtmcsrAndUtmcmd(das.getUtmcsr()+"/"+das.getUtmcmd());
			dasBean.setState("open");
			dasBean.setRowKey(i+"");
			dasBean.setPlatformType("-");			
			List<DasFlowStatisticsTreeMedia> childrenList = new ArrayList<DasFlowStatisticsTreeMedia>();
			////////////////////
			double visitCount = 0,visitCountCompare = 0;
			double advisoryCountQQ = 0,advisoryCountQQCompare = 0;
			double advisoryCountLIVE800 = 0,advisoryCountLIVE800Compare = 0;
			double demoCount = 0,demoCountCompare = 0;
			double realCount = 0,realCountCompare = 0;
			double depositCount = 0,depositCountCompare = 0;
			DasFlowStatisticsTreeMedia dasFlowStatisticsTotal = new DasFlowStatisticsTreeMedia();
			DasFlowStatisticsTreeMedia dasFlowStatisticsBefore = null;
			DasFlowStatisticsTreeMedia dasFlowStatisticsAfter = null;
			if((null != contrastFrontList && contrastFrontList.size() >0)){	
				boolean contrastFrontFlag = false;
				for (DasFlowStatisticsTreeMedia dasFlowStatistics : contrastFrontList)
				{
					if(das.getUtmcmd().equals(dasFlowStatistics.getUtmcmd()) && das.getUtmcsr().equals(dasFlowStatistics.getUtmcsr())){
						contrastFrontFlag = true;
						visitCount += Integer.valueOf(dasFlowStatistics.getVisitCount());
						advisoryCountQQ += Integer.valueOf(dasFlowStatistics.getAdvisoryCountQQ());
						advisoryCountLIVE800 += Integer.valueOf(dasFlowStatistics.getAdvisoryCountLIVE800());
						demoCount += Integer.valueOf(dasFlowStatistics.getDemoCount());
						realCount += Integer.valueOf(dasFlowStatistics.getRealCount());
						depositCount += Integer.valueOf(dasFlowStatistics.getDepositCount());
					}					
				}
				if(contrastFrontFlag){
					dasFlowStatisticsBefore = new DasFlowStatisticsTreeMedia();
					dasFlowStatisticsBefore.setId(Integer.valueOf(k));
					k++;
					//dasFlowStatisticsBefore.set_parentId(k);
					dasFlowStatisticsBefore.setUtmcsrAndUtmcmd(bean.getStartTime()+"---"+bean.getEndTime());
					dasFlowStatisticsBefore.setVisitCount(String.valueOf(visitCount));
					dasFlowStatisticsBefore.setAdvisoryCountQQ(String.valueOf(advisoryCountQQ));
					dasFlowStatisticsBefore.setAdvisoryCountLIVE800(String.valueOf(advisoryCountLIVE800));
					dasFlowStatisticsBefore.setDemoCount(String.valueOf(demoCount));
					dasFlowStatisticsBefore.setRealCount(String.valueOf(realCount));
					dasFlowStatisticsBefore.setDepositCount(String.valueOf(depositCount));
					childrenList.add(dasFlowStatisticsBefore);
				}
			}
			if((null != contrastBackList && contrastBackList.size() >0)){	
				boolean contrastBackFlag = false;
				for (DasFlowStatisticsTreeMedia dasFlowStatistics : contrastBackList)
				{
					if(das.getUtmcmd().equals(dasFlowStatistics.getUtmcmd()) && das.getUtmcsr().equals(dasFlowStatistics.getUtmcsr())){
						contrastBackFlag = true;
						visitCountCompare += Integer.valueOf(dasFlowStatistics.getVisitCount());
						advisoryCountQQCompare += Integer.valueOf(dasFlowStatistics.getAdvisoryCountQQ());
						advisoryCountLIVE800Compare += Integer.valueOf(dasFlowStatistics.getAdvisoryCountLIVE800());
						demoCountCompare += Integer.valueOf(dasFlowStatistics.getDemoCount());
						realCountCompare += Integer.valueOf(dasFlowStatistics.getRealCount());
						depositCountCompare += Integer.valueOf(dasFlowStatistics.getDepositCount());
					}
				}
				if(contrastBackFlag){
					dasFlowStatisticsAfter = new DasFlowStatisticsTreeMedia();
					dasFlowStatisticsAfter.setId(Integer.valueOf(k));
					k++;
					//dasFlowStatisticsAfter.set_parentId(k);
					dasFlowStatisticsAfter.setUtmcsrAndUtmcmd(bean.getStartTimeCompare()+"---"+bean.getEndTimeCompare());
					dasFlowStatisticsAfter.setVisitCount(String.valueOf(visitCountCompare));
					dasFlowStatisticsAfter.setAdvisoryCountQQ(String.valueOf(advisoryCountQQCompare));
					dasFlowStatisticsAfter.setAdvisoryCountLIVE800(String.valueOf(advisoryCountLIVE800Compare));
					dasFlowStatisticsAfter.setDemoCount(String.valueOf(demoCountCompare));
					dasFlowStatisticsAfter.setRealCount(String.valueOf(realCountCompare));
					dasFlowStatisticsAfter.setDepositCount(String.valueOf(depositCountCompare));
					childrenList.add(dasFlowStatisticsAfter);
				}
			}
			
			if(null != dasFlowStatisticsBefore && null != dasFlowStatisticsAfter){
				dasFlowStatisticsTotal.setUtmcsrAndUtmcmd("变动百分比");
				dasFlowStatisticsTotal.setId(Integer.valueOf(k));
				k++;
				//dasFlowStatisticsTotal.set_parentId(k);
				dasFlowStatisticsTotal.setVisitCount(visitCountCompare == 0?"-":df.format((visitCount - visitCountCompare)/visitCountCompare * 100) + "%");
				dasFlowStatisticsTotal.setAdvisoryCountQQ(advisoryCountQQCompare == 0?"-":df.format((advisoryCountQQ - advisoryCountQQCompare)/advisoryCountQQCompare  * 100)+"%" );
				dasFlowStatisticsTotal.setAdvisoryCountLIVE800(advisoryCountLIVE800Compare == 0?"-":df.format((advisoryCountLIVE800 - advisoryCountLIVE800Compare)/advisoryCountLIVE800Compare  * 100) + "%" );
				dasFlowStatisticsTotal.setDemoCount(demoCountCompare == 0?"-":df.format((demoCount - demoCountCompare)/demoCountCompare  * 100) +"%" );
				dasFlowStatisticsTotal.setRealCount(realCountCompare == 0 ?"-":df.format((realCount - realCountCompare)/realCountCompare  * 100) +"%" );
				dasFlowStatisticsTotal.setDepositCount(depositCountCompare == 0?"-":df.format((depositCount - depositCountCompare)/depositCountCompare  * 100) +"%" );
			}else{
				dasFlowStatisticsTotal.setId(Integer.valueOf(k));
				k++;
				//dasFlowStatisticsTotal.set_parentId(k);
				dasFlowStatisticsTotal.setUtmcsrAndUtmcmd("变动百分比");
				dasFlowStatisticsTotal.setVisitCount("-");
				dasFlowStatisticsTotal.setAdvisoryCountQQ("-");
				dasFlowStatisticsTotal.setAdvisoryCountLIVE800("-");
				dasFlowStatisticsTotal.setDemoCount("-");
				dasFlowStatisticsTotal.setRealCount("-");
				dasFlowStatisticsTotal.setDepositCount("-");
			}
			childrenList.add(dasFlowStatisticsTotal);
			///////////////////
			dasBean.setChildren(childrenList);	
			resultList.add(dasBean);
		}
						
		PageGrid<DasFlowStatisticsSearchBean> page = new PageGrid<DasFlowStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(resultList);
		return page;
	}
	
	/**
	 * 流量统计列表
	 */
	public List<DasFlowStatistics> findTimeList(DasFlowStatisticsSearchBean bean) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsTimeList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatistics>>() {});
		return rowsList;
	}
	
	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findTimePageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception {
		DasFlowStatisticsSearchBean bean = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");
		parameters.put("sortName", pageGrid.getSort());
		parameters.put("sortDirection", pageGrid.getOrder());

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsTimePage, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();

		List<DasFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatistics>>() {});
		PageGrid<DasFlowStatisticsSearchBean> page = new PageGrid<DasFlowStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 流量统计列表
	 */
	public List<DasFlowStatisticsAverage> findAverageList(DasFlowStatisticsSearchBean bean) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsAverageList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasFlowStatisticsAverage> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatisticsAverage>>() {});
		return rowsList;
	}
	
	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findAveragePageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception {
		DasFlowStatisticsSearchBean bean = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");
		parameters.put("sortName", pageGrid.getSort());
		parameters.put("sortDirection", pageGrid.getOrder());

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsAveragePage, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();

		List<DasFlowStatisticsAverage> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatisticsAverage>>() {});
		PageGrid<DasFlowStatisticsSearchBean> page = new PageGrid<DasFlowStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	private void getArgs(DasFlowStatisticsSearchBean bean, Map<String, String> parameters){
		if (bean != null) {
			// 设置查询条件
			if (StringUtils.isNotBlank(bean.getUtmcsr())) {
				parameters.put("utmcsr", bean.getUtmcsr());
			}
			if (StringUtils.isNotBlank(bean.getUtmcmd())) {
				parameters.put("utmcmd", bean.getUtmcmd());
			}
			if (StringUtils.isNotBlank(bean.getUtmccn())) {
				parameters.put("utmccn", bean.getUtmccn());
			}
			if (StringUtils.isNotBlank(bean.getUtmcct())) {
				parameters.put("utmcct", bean.getUtmcct());
			}
			if (StringUtils.isNotBlank(bean.getUtmctr())) {
				parameters.put("utmctr", bean.getUtmctr());
			}
			if (StringUtils.isNotBlank(bean.getStartTime())) {
				parameters.put("startTime", bean.getStartTime());
			}
			if (StringUtils.isNotBlank(bean.getEndTime())) {
				parameters.put("endTime", bean.getEndTime());
			}
			if (StringUtils.isNotBlank(bean.getPlatformType())) {
				// 访问客户端
				if (ClientEnum.pc.getLabelKey().equals(bean.getPlatformType())) {
					parameters.put("platformType", "0");
				} else if (ClientEnum.mobile.getLabelKey().equals(bean.getPlatformType())) {
					parameters.put("platformType", "1");
				}
			}
			parameters.put("dataTime", bean.getDataTime());
			parameters.put("behaviorType", bean.getBehaviorType());
			parameters.put("searchType", bean.getSearchType());
			parameters.put("sortName", bean.getSortName());
			parameters.put("sortDirection", bean.getSortDirection());
			parameters.put("utmcsrChecked", bean.isUtmcsrChecked() + "");
			parameters.put("utmcmdChecked", bean.isUtmcmdChecked() + "");
			parameters.put("utmccnChecked", bean.isUtmccnChecked() + "");
			parameters.put("utmcctChecked", bean.isUtmcctChecked() + "");
			parameters.put("utmctrChecked", bean.isUtmctrChecked() + "");
			if(StringUtils.isNotBlank(bean.getUtmcmdList())){
				parameters.put("utmcmdList", bean.getUtmcmdList());
			}
			if(StringUtils.isNotBlank(bean.getUtmcsrList())){
				parameters.put("utmcsrList", bean.getUtmcsrList());
			}
			if(StringUtils.isNotBlank(bean.getStartTimeCompare())){
				parameters.put("startTimeCompare", bean.getStartTimeCompare());
			}
			if(StringUtils.isNotBlank(bean.getEndTimeCompare())){
				parameters.put("endTimeCompare", bean.getEndTimeCompare());
			}
			
		}
	}
	
}
