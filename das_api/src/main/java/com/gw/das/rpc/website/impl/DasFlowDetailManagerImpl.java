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
import com.gw.das.business.dao.website.DasFlowDetailDao;
import com.gw.das.business.dao.website.DasFlowDetailESDao;
import com.gw.das.business.dao.website.entity.DasFlowAttribution;
import com.gw.das.business.dao.website.entity.DasFlowDetail;
import com.gw.das.business.dao.website.entity.DasFlowDetailES;
import com.gw.das.business.dao.website.entity.DasFlowDetailSearchBean;
import com.gw.das.business.dao.website.entity.DasFlowDetailUrl;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.website.DasFlowDetailManager;

public class DasFlowDetailManagerImpl extends ManagerImpl implements DasFlowDetailManager {

	private static final Logger logger = LoggerFactory.getLogger(DasFlowDetailManagerImpl.class);

	@Override
	public Map<String, String> DasFlowDetailListNew(String jsonStr) throws Exception{
		try {
			DasFlowDetailSearchBean model = JacksonUtil.readValue(jsonStr, DasFlowDetailSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			
			DasFlowDetailDao dasFlowDetailDao = getService(DasFlowDetailDao.class);
			List<DasFlowDetail> list = dasFlowDetailDao.queryForList(model);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[详细汇总列表-查询]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[详细汇总列表-查询]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> DasFlowDetailListPageNew(String jsonStr) throws Exception{
		try {
			DasFlowDetailSearchBean model = JacksonUtil.readValue(jsonStr, DasFlowDetailSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			
			DasFlowDetailDao dasFlowDetailDao = getService(DasFlowDetailDao.class);
			PageGrid<DasFlowDetailSearchBean> pg = new PageGrid<DasFlowDetailSearchBean>();
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg = dasFlowDetailDao.queryForListPage(pg);
			List<DasFlowDetail> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[详细汇总列表-分页查询]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[详细汇总列表-分页查询]接口异常:" + e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> DasFlowDetailUrlListPage(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			String flowDetailId = "";
			if(null != parameters.get("flowDetailId")){
				flowDetailId = parameters.get("flowDetailId") + "";
			}
			String flowDetailUrl = "";
			if(null != parameters.get("flowDetailUrl")){
				flowDetailUrl = parameters.get("flowDetailUrl") + "";
			}
			String utmcsr = "";
			if(null != parameters.get("utmcsr")){
				utmcsr = parameters.get("utmcsr") + "";
			}
			String utmcmd = "";
			if(null != parameters.get("utmcmd")){
				utmcmd = parameters.get("utmcmd") + "";
			}
			String startTime = "";
			if(null != parameters.get("startTime")){
				startTime = parameters.get("startTime") + "";
			}
			String endTime = "";
			if(null != parameters.get("endTime")){
				endTime = parameters.get("endTime") + "";
			}
			String businessPlatform = "";
			if(null != parameters.get("businessPlatform")){
				businessPlatform = parameters.get("businessPlatform") + "";
			}
			if(StringUtils.isBlank(businessPlatform)){
				businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
			}
			String platformType = "";
			if(null != parameters.get("platformType")){
				platformType = parameters.get("platformType") + "";
			}
			
			int pageNumber = Integer.parseInt(parameters.get("pageNumber")+"");
			int pageSize = Integer.parseInt(parameters.get("pageSize")+"");
			String sort = parameters.get("sortName") + "";
			String order = parameters.get("sortDirection") + "";
			
			DasFlowDetailDao dasFlowDetailDao = getService(DasFlowDetailDao.class);
			
			DasFlowDetailUrl model = new DasFlowDetailUrl();
			model.setBusinessPlatform(businessPlatform);
			model.setUtmcsr(utmcsr);
			model.setUtmcmd(utmcmd);
			model.setFlowDetailUrl(flowDetailUrl);
			model.setFlowDetailId(flowDetailId);
			model.setPlatformType(platformType);
			if (StringUtils.isNotBlank(startTime)) {
				model.setVisitTimeStart(startTime);
			}
			if (StringUtils.isNotBlank(endTime)) {
				model.setVisitTimeEnd(endTime);
			}
			
			PageGrid<DasFlowDetailUrl> pg = new PageGrid<DasFlowDetailUrl>();
			pg.setSearchModel(model);
			pg.setPageNumber(pageNumber);
			pg.setPageSize(pageSize);
			pg.setSort(sort);
			pg.setOrder(order);
			pg = dasFlowDetailDao.queryForUrlListPage(pg);
			List<DasFlowAttribution> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
			
		}catch (Exception e) {
			logger.error("DasFlowDetailUrlListPage方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowDetailUrlListPage方法出现异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> DasFlowDetailUrlList(String jsonStr) throws Exception {
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			String flowDetailId = "";
			if(null != parameters.get("flowDetailId")){
				flowDetailId = parameters.get("flowDetailId") + "";
			}
			String flowDetailUrl = "";
			if(null != parameters.get("flowDetailUrl")){
				flowDetailUrl = parameters.get("flowDetailUrl") + "";
			}
			String utmcsr = "";
			if(null != parameters.get("utmcsr")){
				utmcsr = parameters.get("utmcsr") + "";
			}
			String utmcmd = "";
			if(null != parameters.get("utmcmd")){
				utmcmd = parameters.get("utmcmd") + "";
			}
			String startTime = "";
			if(null != parameters.get("startTime")){
				startTime = parameters.get("startTime") + "";
			}
			String endTime = "";
			if(null != parameters.get("endTime")){
				endTime = parameters.get("endTime") + "";
			}
			String businessPlatform = "";
			if(null != parameters.get("businessPlatform")){
				businessPlatform = parameters.get("businessPlatform") + "";
			}
			if(StringUtils.isBlank(businessPlatform)){
				businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
			}
			String platformType = "";
			if(null != parameters.get("platformType")){
				platformType = parameters.get("platformType") + "";
			}
			
			
			DasFlowDetailDao dasFlowDetailDao = getService(DasFlowDetailDao.class);
			
			DasFlowDetailUrl model = new DasFlowDetailUrl();
			model.setBusinessPlatform(businessPlatform);
			model.setUtmcsr(utmcsr);
			model.setUtmcmd(utmcmd);
			model.setFlowDetailUrl(flowDetailUrl);
			model.setFlowDetailId(flowDetailId);
			model.setPlatformType(platformType);
			if (StringUtils.isNotBlank(startTime)) {
				model.setVisitTimeStart(startTime);
			}
			if (StringUtils.isNotBlank(endTime)) {
				model.setVisitTimeEnd(endTime);
			}
			List<DasFlowDetailUrl> list = dasFlowDetailDao.queryForUrlList(model);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		}catch (Exception e) {
			logger.error("DasFlowDetailUrlList方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowDetailUrlList方法出现异常:" + e.getMessage());
		}
	}

	/**
	 * 分页查询用户行为列表报表记录 - elasticsearch
	 */
	@Override
	public Map<String, String> DasFlowDetailListESPageList(String jsonStr) throws Exception {
		try {
			DasFlowDetailSearchBean model = JacksonUtil.readValue(jsonStr, DasFlowDetailSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			
			DasFlowDetailESDao dasFlowDetailESDao = getService(DasFlowDetailESDao.class);
			PageGrid<DasFlowDetailSearchBean> pg = new PageGrid<DasFlowDetailSearchBean>();
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg = dasFlowDetailESDao.findDasFlowDetailPageList(model);
			List<DasFlowDetailES> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[用户行为列表-分页查询]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[用户行为列表-分页查询]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 不分页查询用户行为列表报表记录 - elasticsearch
	 */
	@Override
	public Map<String, String> DasFlowDetailESList(String jsonStr) throws Exception{
		try {
			DasFlowDetailSearchBean model = JacksonUtil.readValue(jsonStr, DasFlowDetailSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			
			DasFlowDetailESDao dasFlowDetailESDao = getService(DasFlowDetailESDao.class);
			List<DasFlowDetailES> list = dasFlowDetailESDao.findDasFlowDetailList(model);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[详细汇总列表-查询]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[详细汇总列表-查询]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 用户详细-页面浏览详细列表分页
	 * 
	 */
	@Override
	public Map<String, String> DasFlowDetailUrlESPageList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			String flowDetailId = "";
			if(null != parameters.get("flowDetailId")){
				flowDetailId = parameters.get("flowDetailId") + "";
			}
			String flowDetailUrl = "";
			if(null != parameters.get("flowDetailUrl")){
				flowDetailUrl = parameters.get("flowDetailUrl") + "";
			}
			String utmcsr = "";
			if(null != parameters.get("utmcsr")){
				utmcsr = parameters.get("utmcsr") + "";
			}
			String utmcmd = "";
			if(null != parameters.get("utmcmd")){
				utmcmd = parameters.get("utmcmd") + "";
			}
			String startTime = "";
			if(null != parameters.get("startTime")){
				startTime = parameters.get("startTime") + "";
			}
			String endTime = "";
			if(null != parameters.get("endTime")){
				endTime = parameters.get("endTime") + "";
			}
			String businessPlatform = "";
			if(null != parameters.get("businessPlatform")){
				businessPlatform = parameters.get("businessPlatform") + "";
			}
			if(StringUtils.isBlank(businessPlatform)){
				businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
			}
			String platformType = "";
			if(null != parameters.get("platformType")){
				platformType = parameters.get("platformType") + "";
			}
			
			int pageNumber = Integer.parseInt(parameters.get("pageNumber")+"");
			int pageSize = Integer.parseInt(parameters.get("pageSize")+"");
			String sort = parameters.get("sortName") + "";
			String order = parameters.get("sortDirection") + "";
			
			DasFlowDetailESDao dasFlowDetailESDao = getService(DasFlowDetailESDao.class);
			
			DasFlowDetailUrl model = new DasFlowDetailUrl();
			model.setBusinessPlatform(businessPlatform);
			model.setUtmcsr(utmcsr);
			model.setUtmcmd(utmcmd);
			model.setFlowDetailUrl(flowDetailUrl);
			model.setFlowDetailId(flowDetailId);
			model.setPlatformType(platformType);
			model.setPageNumber(pageNumber);
			model.setPageSize(pageSize);
			if (StringUtils.isNotBlank(startTime)) {
				model.setVisitTimeStart(startTime);
			}
			if (StringUtils.isNotBlank(endTime)) {
				model.setVisitTimeEnd(endTime);
			}
			model.setSort(sort);
			model.setOrder(order);
			PageGrid<DasFlowDetailUrl> pg = new PageGrid<DasFlowDetailUrl>();
			pg.setSearchModel(model);
			pg.setPageNumber(pageNumber);
			pg.setPageSize(pageSize);
			pg.setSort(sort);
			pg.setOrder(order);
			pg = dasFlowDetailESDao.findDasflowUrlDetailPageList(model);
			List<DasFlowAttribution> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
			
		}catch (Exception e) {
			logger.error("DasFlowDetailUrlListPage方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowDetailUrlListPage方法出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 用户详细-页面浏览详细列表不分页
	 */
	@Override
	public Map<String, String> DasFlowDetailUrlESList(String jsonStr) throws Exception {
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			String flowDetailId = "";
			if(null != parameters.get("flowDetailId")){
				flowDetailId = parameters.get("flowDetailId") + "";
			}
			String flowDetailUrl = "";
			if(null != parameters.get("flowDetailUrl")){
				flowDetailUrl = parameters.get("flowDetailUrl") + "";
			}
			String utmcsr = "";
			if(null != parameters.get("utmcsr")){
				utmcsr = parameters.get("utmcsr") + "";
			}
			String utmcmd = "";
			if(null != parameters.get("utmcmd")){
				utmcmd = parameters.get("utmcmd") + "";
			}
			String startTime = "";
			if(null != parameters.get("startTime")){
				startTime = parameters.get("startTime") + "";
			}
			String endTime = "";
			if(null != parameters.get("endTime")){
				endTime = parameters.get("endTime") + "";
			}
			String businessPlatform = "";
			if(null != parameters.get("businessPlatform")){
				businessPlatform = parameters.get("businessPlatform") + "";
			}
			if(StringUtils.isBlank(businessPlatform)){
				businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
			}
			String platformType = "";
			if(null != parameters.get("platformType")){
				platformType = parameters.get("platformType") + "";
			}
			String sort = parameters.get("sortName") + "";
			String order = parameters.get("sortDirection") + "";
			
			DasFlowDetailUrl model = new DasFlowDetailUrl();
			model.setBusinessPlatform(businessPlatform);
			model.setUtmcsr(utmcsr);
			model.setUtmcmd(utmcmd);
			model.setFlowDetailUrl(flowDetailUrl);
			model.setFlowDetailId(flowDetailId);
			model.setPlatformType(platformType);
			if (StringUtils.isNotBlank(startTime)) {
				model.setVisitTimeStart(startTime);
			}
			if (StringUtils.isNotBlank(endTime)) {
				model.setVisitTimeEnd(endTime);
			}
			model.setSort(sort);
			model.setOrder(order);
			DasFlowDetailESDao dasFlowDetailESDao = getService(DasFlowDetailESDao.class);
			List<DasFlowDetailUrl> list = dasFlowDetailESDao.findDasflowUrlDetailESList(model);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		}catch (Exception e) {
			logger.error("DasFlowDetailUrlESList方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowDetailUrlESList方法出现异常:" + e.getMessage());
		}
	}

}
