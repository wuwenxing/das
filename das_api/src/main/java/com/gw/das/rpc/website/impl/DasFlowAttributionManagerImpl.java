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
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.website.DasChartWebSiteSearchModel;
import com.gw.das.business.dao.website.DasFlowAttributionDao;
import com.gw.das.business.dao.website.entity.DasFlowAttribution;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.website.DasFlowAttributionManager;

public class DasFlowAttributionManagerImpl extends ManagerImpl implements DasFlowAttributionManager {

	private static final Logger logger = LoggerFactory.getLogger(DasFlowAttributionManagerImpl.class);

	@Override
	public Map<String, String> DasFlowAttributionList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});

			String utmcsr = parameters.get("utmcsr");
			String utmcmd = parameters.get("utmcmd");
			String startTime = parameters.get("startTime");
			String endTime = parameters.get("endTime");
			String businessPlatform = parameters.get("businessPlatform");
			if (StringUtils.isBlank(businessPlatform)) {
				businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
			}

			DasFlowAttributionDao dasFlowAttributionDao = getService(DasFlowAttributionDao.class);

			DasChartWebSiteSearchModel model = new DasChartWebSiteSearchModel();
			model.setBusinessPlatform(businessPlatform);
			model.setUtmcsr(utmcsr);
			model.setUtmcmd(utmcmd);
			if (StringUtils.isNotBlank(startTime)) {
				model.setDataTimeStart(DateUtil.getDateFromStrByyyyMMdd(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				model.setDataTimeEnd(DateUtil.getDateFromStrByyyyMMdd(endTime));
			}
			if (StringUtils.isNotBlank(parameters.get("platformType"))) {
				model.setPlatformType(parameters.get("platformType"));
			}
			model.setSort("dataTime");
			model.setOrder("desc");
			List<DasFlowAttribution> list = dasFlowAttributionDao.queryForList(model, new DasFlowAttribution());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasAttributionList方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasAttributionList方法出现异常:" + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> DasFlowAttributionListPage(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			String utmcsr = parameters.get("utmcsr");
			String utmcmd = parameters.get("utmcmd");
			String startTime = parameters.get("startTime");
			String endTime = parameters.get("endTime");
			String businessPlatform = parameters.get("businessPlatform");
			if (StringUtils.isBlank(businessPlatform)) {
				businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
			}

			int pageNumber = Integer.parseInt(parameters.get("pageNumber"));
			int pageSize = Integer.parseInt(parameters.get("pageSize"));

			DasFlowAttributionDao dasFlowAttributionDao = getService(DasFlowAttributionDao.class);

			DasChartWebSiteSearchModel model = new DasChartWebSiteSearchModel();
			model.setBusinessPlatform(businessPlatform);
			model.setUtmcsr(utmcsr);
			model.setUtmcmd(utmcmd);
			if (StringUtils.isNotBlank(startTime)) {
				model.setDataTimeStart(DateUtil.getDateFromStrByyyyMMdd(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				model.setDataTimeEnd(DateUtil.getDateFromStrByyyyMMdd(endTime));
			}
			if (StringUtils.isNotBlank(parameters.get("platformType"))) {
				model.setPlatformType(parameters.get("platformType"));
			}

			PageGrid<DasChartWebSiteSearchModel> pg = new PageGrid<DasChartWebSiteSearchModel>();
			pg.setSearchModel(model);
			pg.setPageNumber(pageNumber);
			pg.setPageSize(pageSize);
			pg.setSort("dataTime");
			pg.setOrder("desc");
			pg = dasFlowAttributionDao.queryForPage(pg, new DasFlowAttribution());
			List<DasFlowAttribution> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasAttributionListPage方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasAttributionListPage方法出现异常:" + e.getMessage());
		}
	}
}
