package com.gw.das.rpc.website.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.website.DasBehaviorEventChannelEffectDao;
import com.gw.das.business.dao.website.DasWebLandingpageDetailDao;
import com.gw.das.business.dao.website.entity.DasBehaviorEventChannelEffect;
import com.gw.das.business.dao.website.entity.DasBehaviorEventChannelEffectSearchModel;
import com.gw.das.business.dao.website.entity.DasWebLandingpageDetail;
import com.gw.das.business.dao.website.entity.DasWebLandingpageDetailSearchModel;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.website.DasWebSiteManager;

public class DasWebSiteManagerImpl extends ManagerImpl implements DasWebSiteManager {

	private static final Logger logger = LoggerFactory.getLogger(DasWebSiteManagerImpl.class);

	/**
	 * 分页查询das_web_landingpage_detail_d表
	 */
	public Map<String, String> dasWebLandingpageDetailPage(String jsonStr) throws Exception {
		try {
			DasWebLandingpageDetailSearchModel model = JacksonUtil.readValue(jsonStr,
					DasWebLandingpageDetailSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			PageGrid<DasWebLandingpageDetailSearchModel> pg = new PageGrid<DasWebLandingpageDetailSearchModel>();
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(OrmUtil.reflectColumn(DasWebLandingpageDetail.class, model.getSort()));
			pg.setOrder(model.getOrder());

			DasWebLandingpageDetailDao dao = getService(DasWebLandingpageDetailDao.class);
			pg = dao.queryForPage(pg, new DasWebLandingpageDetail());
			List<DasWebLandingpageDetail> list = pg.getRows();

			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal() + "");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("接口异常:" + e.getMessage(), e);
			throw new Exception("接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 不分页查询das_web_landingpage_detail_d表
	 */
	public Map<String, String> dasWebLandingpageDetailList(String jsonStr) throws Exception {
		try {
			DasWebLandingpageDetailSearchModel model = JacksonUtil.readValue(jsonStr,
					DasWebLandingpageDetailSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setSort(OrmUtil.reflectColumn(DasWebLandingpageDetail.class, model.getSort()));
			DasWebLandingpageDetailDao dao = getService(DasWebLandingpageDetailDao.class);
			List<DasWebLandingpageDetail> list = dao.queryForList(model, new DasWebLandingpageDetail());
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("接口异常:" + e.getMessage(), e);
			throw new Exception("接口异常:" + e.getMessage());
		}
	}

	/**
	 * 分页查询das_behavior_event_channel_effect_d表
	 */
	public Map<String, String> dasBehaviorEventChannelEffectPage(String jsonStr) throws Exception {
		try {
			DasBehaviorEventChannelEffectSearchModel model = JacksonUtil.readValue(jsonStr,
					DasBehaviorEventChannelEffectSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			PageGrid<DasBehaviorEventChannelEffectSearchModel> pg = new PageGrid<DasBehaviorEventChannelEffectSearchModel>();
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(OrmUtil.reflectColumn(DasBehaviorEventChannelEffect.class, model.getSort()));
			pg.setOrder(model.getOrder());

			DasBehaviorEventChannelEffectDao dao = getService(DasBehaviorEventChannelEffectDao.class);
			pg = dao.queryForPage(pg, new DasBehaviorEventChannelEffect());
			List<DasBehaviorEventChannelEffect> list = pg.getRows();

			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal() + "");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("接口异常:" + e.getMessage(), e);
			throw new Exception("接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 不分页查询das_behavior_event_channel_effect_d表
	 */
	public Map<String, String> dasBehaviorEventChannelEffectList(String jsonStr) throws Exception {
		try {
			DasBehaviorEventChannelEffectSearchModel model = JacksonUtil.readValue(jsonStr,
					DasBehaviorEventChannelEffectSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setSort(OrmUtil.reflectColumn(DasBehaviorEventChannelEffect.class, model.getSort()));
			DasBehaviorEventChannelEffectDao dao = getService(DasBehaviorEventChannelEffectDao.class);
			List<DasBehaviorEventChannelEffect> list = dao.queryForList(model, new DasBehaviorEventChannelEffect());
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("接口异常:" + e.getMessage(), e);
			throw new Exception("接口异常:" + e.getMessage());
		}
	}
	
}
