package com.gw.das.dao.website.bean;

import java.util.List;
import java.util.Map;

/**
 * 首页图表实体类
 */
public class HomePageChartModel {

	private HomePageConditonModel condition;// 条件
	private Map<String, List<HomePageDataModel>> dataMap;// 数据集合

	public HomePageConditonModel getCondition() {
		return condition;
	}

	public void setCondition(HomePageConditonModel condition) {
		this.condition = condition;
	}

	public Map<String, List<HomePageDataModel>> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, List<HomePageDataModel>> dataMap) {
		this.dataMap = dataMap;
	}

}
