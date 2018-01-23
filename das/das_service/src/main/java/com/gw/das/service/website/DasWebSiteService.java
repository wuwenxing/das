package com.gw.das.service.website;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.website.bean.DasBehaviorEventChannelEffect;
import com.gw.das.dao.website.bean.DasBehaviorEventChannelEffectSearchModel;
import com.gw.das.dao.website.bean.DasWebLandingpageDetail;
import com.gw.das.dao.website.bean.DasWebLandingpageDetailSearchModel;

public interface DasWebSiteService {

	/**
	 * Landingpage
	 */
	public List<DasWebLandingpageDetail> dasWebLandingpageDetailList(DasWebLandingpageDetailSearchModel searchModel) throws Exception;

	/**
	 * Landingpage-分页查询
	 */
	public PageGrid<DasWebLandingpageDetailSearchModel> dasWebLandingpageDetailPageList(PageGrid<DasWebLandingpageDetailSearchModel> pageGrid) throws Exception;
	
	/**
	 * 获取das_behavior_event_channel_effect_d列表
	 */
	public List<DasBehaviorEventChannelEffect> dasBehaviorEventChannelEffectList(DasBehaviorEventChannelEffectSearchModel searchModel) throws Exception;
	
	/**
	 * das_behavior_event_channel_effect_d-分页查询
	 */
	public PageGrid<DasBehaviorEventChannelEffectSearchModel> dasBehaviorEventChannelEffectPageList(PageGrid<DasBehaviorEventChannelEffectSearchModel> pageGrid) throws Exception;
	
}
