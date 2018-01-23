package com.gw.das.rpc.website.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.website.DasFlowStatisticsDao;
import com.gw.das.business.dao.website.entity.DasFlowAttribution;
import com.gw.das.business.dao.website.entity.DasFlowStatistics;
import com.gw.das.business.dao.website.entity.DasFlowStatisticsAverage;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.website.DasFlowStatisticsManager;

public class DasFlowStatisticsManagerImpl extends ManagerImpl implements DasFlowStatisticsManager {

	private static final Logger logger = LoggerFactory.getLogger(DasFlowStatisticsManagerImpl.class);

	@Override
	public Map<String, String> DasFlowStatisticsTimeList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);
			List<DasFlowStatistics> list = dasFlowStatisticsDao.timeQueryForList(getStatisticsModel(parameters));
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsTimeList方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsTimeList方法出现异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> DasFlowStatisticsTimePage(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);
			PageGrid<DasFlowStatistics> pg = getPageGrid(parameters);
			pg = dasFlowStatisticsDao.timeQueryForPage(pg);
			List<DasFlowAttribution> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsTimeListPage方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsTimeListPage方法出现异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> DasFlowStatisticsMediaList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);
			List<DasFlowStatistics> list = dasFlowStatisticsDao.mediaQueryForList(getStatisticsModel(parameters));
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsMediaList方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsMediaList方法出现异常:" + e.getMessage());
		}
	}

	private PageGrid<DasFlowStatistics> getPageGrid(Map<String, String> parameters) {
		int pageNumber = Integer.parseInt(parameters.get("pageNumber") + "");
		int pageSize = Integer.parseInt(parameters.get("pageSize") + "");
		String sort = (String) parameters.get("sortName");
		String order = (String) parameters.get("sortDirection");
		PageGrid<DasFlowStatistics> pg = new PageGrid<DasFlowStatistics>();
		pg.setSearchModel(getStatisticsModel(parameters));
		pg.setPageNumber(pageNumber);
		pg.setPageSize(pageSize);
		pg.setSort(sort);
		pg.setOrder(order);
		return pg;
	}

	private DasFlowStatisticsAverage getStatisticsAverageModel(Map<String, String> parameters) {
		DasFlowStatisticsAverage model = new DasFlowStatisticsAverage();

		String utmcmd = "";
		if (null != parameters.get("utmcmd")) {
			utmcmd = parameters.get("utmcmd") + "";
		}

		String dataTime = "";
		if (null != parameters.get("dataTime")) {
			dataTime = parameters.get("dataTime") + "";
		}
		String behaviorType = "";
		if (null != parameters.get("behaviorType")) {
			behaviorType = parameters.get("behaviorType") + "";
		}
		String businessPlatform = "";
		if (null != parameters.get("businessPlatform")) {
			businessPlatform = parameters.get("businessPlatform") + "";
		}
		if (StringUtils.isBlank(businessPlatform)) {
			businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
		}
		if (StringUtils.isNoneBlank(parameters.get("platformType"))) {
			model.setPlatformType(parameters.get("platformType"));
		}

		model.setBusinessPlatform(businessPlatform);
		model.setUtmcmd(utmcmd);
		model.setDataTime(dataTime);
		model.setBehaviorType(behaviorType);
		return model;
	}

	private DasFlowStatistics getStatisticsModel(Map<String, String> parameters) {
		DasFlowStatistics model = new DasFlowStatistics();
		String utmcsr = "";
		if (null != parameters.get("utmcsr")) {
			utmcsr = parameters.get("utmcsr") + "";
		}
		String utmcmd = "";
		if (null != parameters.get("utmcmd")) {
			utmcmd = parameters.get("utmcmd") + "";
		}
		String utmccn = "";
		if (null != parameters.get("utmccn")) {
			utmccn = parameters.get("utmccn") + "";
		}
		String utmcct = "";
		if (null != parameters.get("utmcct")) {
			utmcct = parameters.get("utmcct") + "";
		}
		String utmctr = "";
		if (null != parameters.get("utmctr")) {
			utmctr = parameters.get("utmctr") + "";
		}
		
		String startTime = "";
		if (null != parameters.get("startTime")) {
			startTime = parameters.get("startTime") + "";
		}
		String endTime = "";
		if (null != parameters.get("endTime")) {
			endTime = parameters.get("endTime") + "";
		}

		String businessPlatform = "";
		if (null != parameters.get("businessPlatform")) {
			businessPlatform = parameters.get("businessPlatform") + "";
		}
		if (StringUtils.isBlank(businessPlatform)) {
			businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
		}

		String searchType = "";
		if (null != parameters.get("searchType")) {
			searchType = parameters.get("searchType") + "";
		}

		if (parameters.get("utmcmdChecked") != null && "true".equals(parameters.get("utmcmdChecked"))) {
			boolean utmcmdChecked = true;
			model.setUtmcmdChecked(utmcmdChecked);
		}
		if (parameters.get("utmcsrChecked") != null && "true".equals(parameters.get("utmcsrChecked"))) {
			boolean utmcsrChecked = true;
			model.setUtmcsrChecked(utmcsrChecked);
		}
		if (parameters.get("utmccnChecked") != null && "true".equals(parameters.get("utmccnChecked"))) {
			boolean utmccnChecked = true;
			model.setUtmccnChecked(utmccnChecked);
		}
		if (parameters.get("utmcctChecked") != null && "true".equals(parameters.get("utmcctChecked"))) {
			boolean utmcctChecked = true;
			model.setUtmcctChecked(utmcctChecked);
		}
		if (parameters.get("utmctrChecked") != null && "true".equals(parameters.get("utmctrChecked"))) {
			boolean utmctrChecked = true;
			model.setUtmctrChecked(utmctrChecked);
		}
		model.setBusinessPlatform(businessPlatform);
		model.setUtmcsr(utmcsr);
		model.setUtmcmd(utmcmd);
		model.setUtmccn(utmccn);
		model.setUtmcct(utmcct);
		model.setUtmctr(utmctr);
		model.setSearchType(searchType);

		if (StringUtils.isNotBlank(startTime)) {
			model.setDataTimeStart(startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			model.setDataTimeEnd(endTime);
		}
		
		// 排序字段
		String sortName = "";
		if (null != parameters.get("sortName")) {
			sortName = parameters.get("sortName") + "";
		}
		String sortDirection = "";
		if (null != parameters.get("sortDirection")) {
			sortDirection = parameters.get("sortDirection") + "";
		}
		
		if (StringUtils.isNotBlank(parameters.get("platformType"))) {
			model.setPlatformType(parameters.get("platformType"));;
		}
		model.setSortName(sortName);
		model.setSortDirection(sortDirection);
		
		if (StringUtils.isNotBlank(parameters.get("utmcmdList"))) {
			String utmcmds = parameters.get("utmcmdList");
			model.setUtmcmdList(utmcmds.split(","));
		}
		if (StringUtils.isNotBlank(parameters.get("utmcsrList"))) {
			String utmcsrs = parameters.get("utmcsrList");
			model.setUtmcsrList(utmcsrs.split(","));
		}
		
		if (StringUtils.isNotBlank(parameters.get("startTimeCompare"))) {
			model.setStartTimeCompare(parameters.get("startTimeCompare"));
		}
		if (StringUtils.isNotBlank(parameters.get("endTimeCompare"))) {
			model.setEndTimeCompare(parameters.get("endTimeCompare"));
		}
		return model;
	}

	@Override
	public Map<String, String> DasFlowStatisticsMediaPage(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);
			PageGrid<DasFlowStatistics> pg = getPageGrid(parameters);
			pg = dasFlowStatisticsDao.mediaQueryForPage(pg);
			List<DasFlowStatistics> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));			
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsMediaListPage方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsMediaListPage方法出现异常:" + e.getMessage());
		}
	}		
	
	@Override
	public Map<String, String> DasFlowStatisticsMediaTreePage(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);
		
			PageGrid<DasFlowStatistics> pg = getPageGrid(parameters);
			
			if(StringUtils.isNotBlank(pg.getSearchModel().getStartTimeCompare()) && StringUtils.isNotBlank(pg.getSearchModel().getEndTimeCompare())){
				pg.setSort("utmcmd,utmcsr,"+pg.getSort());
				pg.setOrder("desc,desc,"+pg.getOrder());
			}
			
			pg = dasFlowStatisticsDao.mediaQueryForPage(pg);
			Map<String, String> resultMap = new HashMap<String, String>();
			List<DasFlowStatistics> list = pg.getRows();
			resultMap.put("total", pg.getTotal()+"");
			/*StringBuffer utmcmdList = new StringBuffer();
			StringBuffer utmcsrList = new StringBuffer();
			for (int i = 0; i < list.size(); i++)
			{
				DasFlowStatistics dasBean = list.get(i);
				if(i+1 == list.size()){
					utmcmdList.append(dasBean.getUtmcmd());
					utmcsrList.append(dasBean.getUtmcsr());
				}else{
					utmcmdList.append(dasBean.getUtmcmd()).append(",");
					utmcsrList.append(dasBean.getUtmcsr()).append(",");
				}
			}				
			pg.getSearchModel().setUtmcmdList(utmcmdList.toString().split(","));
			pg.getSearchModel().setUtmcsrList(utmcsrList.toString().split(","));*/
			
			List<DasFlowStatistics> contrastFrontList = list;//dasFlowStatisticsDao.mediaQueryForTreeList(pg.getSearchModel());//环比前的数据
		
			pg.getSearchModel().setDataTimeStart(pg.getSearchModel().getStartTimeCompare());
			pg.getSearchModel().setDataTimeEnd(pg.getSearchModel().getEndTimeCompare());
			List<DasFlowStatistics> contrastBacktList = dasFlowStatisticsDao.mediaQueryForPage(pg).getRows();//环比后的数据
							
			
			resultMap.put("rows", JacksonUtil.toJSon(list));	
			resultMap.put("contrastFront", JacksonUtil.toJSon(contrastFrontList));
			resultMap.put("contrastBack", JacksonUtil.toJSon(contrastBacktList));
			return resultMap;				
		} catch (Exception e) {
			logger.error("DasFlowStatisticsMediaTreePage方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsMediaTreePage方法出现异常:" + e.getMessage());
		}
	}
	
	@Override
	public Map<String, String> DasFlowStatisticsBehaviorList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			Map<String, String> resultMap = new HashMap<String, String>();
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);
			List<DasFlowStatistics> list = dasFlowStatisticsDao.behaviorQueryForList(getStatisticsModel(parameters));
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsBehaviorList方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsBehaviorList方法出现异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> DasFlowStatisticsBehaviorPage(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);

			PageGrid<DasFlowStatistics> pg = getPageGrid(parameters);
			pg = dasFlowStatisticsDao.behaviorQueryForPage(pg);
			List<DasFlowAttribution> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsBehaviorListPage方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsBehaviorListPage方法出现异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> DasFlowStatisticsAverageList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);
			List<DasFlowStatisticsAverage> list = dasFlowStatisticsDao
					.statisticsAverageQueryForList(getStatisticsAverageModel(parameters));
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsAverageList方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsAverageList方法出现异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> DasFlowStatisticsAveragePage(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);

			int pageNumber = Integer.parseInt(parameters.get("pageNumber") + "");
			int pageSize = Integer.parseInt(parameters.get("pageSize") + "");
			String sortName = "";
			if (null != parameters.get("sortName")) {
				sortName = parameters.get("sortName") + "";
			}
			String sortDirection = "";
			if (null != parameters.get("sortDirection")) {
				sortDirection = parameters.get("sortDirection") + "";
			}
			PageGrid<DasFlowStatisticsAverage> pg = new PageGrid<DasFlowStatisticsAverage>();

			pg.setSearchModel(getStatisticsAverageModel(parameters));
			pg.setPageNumber(pageNumber);
			pg.setPageSize(pageSize);
			pg.setSort(sortName);
			pg.setOrder(sortDirection);
			pg = dasFlowStatisticsDao.statisticsAverageQueryForPage(pg);
			List<DasFlowAttribution> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsBehaviorList方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsBehaviorList方法出现异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> DasFlowStatisticsHoursSumData(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasFlowStatisticsDao dasFlowStatisticsDao = getService(DasFlowStatisticsDao.class);
			List<DasFlowStatistics> list = dasFlowStatisticsDao.statisticsHoursSumData(getStatisticsModel(parameters));

			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsHoursSumData方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsHoursSumData方法出现异常:" + e.getMessage());
		}
	}
}
