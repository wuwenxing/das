package com.gw.das.service.website.impl;

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
import com.gw.das.dao.website.bean.DasBehaviorEventChannelEffect;
import com.gw.das.dao.website.bean.DasBehaviorEventChannelEffectSearchModel;
import com.gw.das.dao.website.bean.DasWebLandingpageDetail;
import com.gw.das.dao.website.bean.DasWebLandingpageDetailSearchModel;
import com.gw.das.service.website.DasWebSiteService;

/**
 * Landingpage业务处理类
 */
@Service
public class DasWebSiteServiceImpl implements DasWebSiteService {

	/**
	 * 获取Landingpage列表
	 */
	public List<DasWebLandingpageDetail> dasWebLandingpageDetailList(DasWebLandingpageDetailSearchModel searchModel) throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.dasWebLandingpageDetailList, BeanToMapUtil.toMap(searchModel), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasWebLandingpageDetail> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasWebLandingpageDetail>>() {});
		return rowsList;
	}
	
	/**
	 * Landingpage-分页查询
	 */
	public PageGrid<DasWebLandingpageDetailSearchModel> dasWebLandingpageDetailPageList(PageGrid<DasWebLandingpageDetailSearchModel> pageGrid) throws Exception {
		DasWebLandingpageDetailSearchModel searchModel = pageGrid.getSearchModel();
		searchModel.setPageNumber(pageGrid.getPageNumber());
		searchModel.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasWebLandingpageDetailPage, BeanToMapUtil.toMap(searchModel), UserContext.get().getCompanyId());
		String result = rpcResult.getResult();

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasWebLandingpageDetail> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasWebLandingpageDetail>>() {});
		PageGrid<DasWebLandingpageDetailSearchModel> page = new PageGrid<DasWebLandingpageDetailSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}

	/**
	 * 获取das_behavior_event_channel_effect_d列表
	 */
	public List<DasBehaviorEventChannelEffect> dasBehaviorEventChannelEffectList(DasBehaviorEventChannelEffectSearchModel searchModel) throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.dasBehaviorEventChannelEffectList, BeanToMapUtil.toMap(searchModel), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasBehaviorEventChannelEffect> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasBehaviorEventChannelEffect>>() {});
		return rowsList;
	}
	
	/**
	 * das_behavior_event_channel_effect_d-分页查询
	 */
	public PageGrid<DasBehaviorEventChannelEffectSearchModel> dasBehaviorEventChannelEffectPageList(PageGrid<DasBehaviorEventChannelEffectSearchModel> pageGrid) throws Exception {
		DasBehaviorEventChannelEffectSearchModel searchModel = pageGrid.getSearchModel();
		searchModel.setPageNumber(pageGrid.getPageNumber());
		searchModel.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasBehaviorEventChannelEffectPage, BeanToMapUtil.toMap(searchModel), UserContext.get().getCompanyId());
		String result = rpcResult.getResult();

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasBehaviorEventChannelEffect> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasBehaviorEventChannelEffect>>() {});
		PageGrid<DasBehaviorEventChannelEffectSearchModel> page = new PageGrid<DasBehaviorEventChannelEffectSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
}
