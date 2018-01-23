package com.gw.das.rpc.website;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 详细数据接口管理类
 * 
 * @author wayne
 */
public interface DasWebSiteManager extends Manager {

	/**
	 * 分页查询das_web_landingpage_detail_d表
	 */
	public Map<String, String> dasWebLandingpageDetailPage(String jsonStr) throws Exception;

	/**
	 * 不分页查询das_web_landingpage_detail_d表
	 */
	public Map<String, String> dasWebLandingpageDetailList(String jsonStr) throws Exception;

	/**
	 * 分页查询das_behavior_event_channel_effect_d表
	 */
	public Map<String, String> dasBehaviorEventChannelEffectPage(String jsonStr) throws Exception;

	/**
	 * 不分页查询das_behavior_event_channel_effect_d表
	 */
	public Map<String, String> dasBehaviorEventChannelEffectList(String jsonStr) throws Exception;
	
}
