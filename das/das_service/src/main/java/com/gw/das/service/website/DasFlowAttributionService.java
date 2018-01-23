package com.gw.das.service.website;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.website.bean.DasFlowAttribution;
import com.gw.das.dao.website.bean.DasFlowAttributionSearchBean;

public interface DasFlowAttributionService {

	/**
	 * 获取来源媒介效果列表
	 */
	public List<DasFlowAttribution> findList(DasFlowAttributionSearchBean attribution) throws Exception;

	/**
	 * 来源媒介效果-分页查询
	 */
	public PageGrid<DasFlowAttributionSearchBean> findPageList(PageGrid<DasFlowAttributionSearchBean> pageGrid) throws Exception;

}
