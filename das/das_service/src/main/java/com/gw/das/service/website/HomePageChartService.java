package com.gw.das.service.website;

import java.util.List;
import java.util.Map;

import com.gw.das.dao.website.bean.HomePageChartModel;
import com.gw.das.dao.website.bean.HomePageConditonModel;
import com.gw.das.dao.website.bean.HomePageDataModel;

/**
 * 首页图形报表Service
 */
public interface HomePageChartService {

	// 根据统计条件统计
	public Map<String, List<HomePageDataModel>> hourChart(HomePageConditonModel condition) throws Exception;

	// 补点（小时数据）
	public void addHourChartPoint(HomePageChartModel chartModel) throws Exception;
}
