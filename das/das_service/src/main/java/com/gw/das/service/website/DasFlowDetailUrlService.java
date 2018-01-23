package com.gw.das.service.website;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.website.bean.DasFlowDetailUrl;
import com.gw.das.dao.website.bean.DasFlowDetailUrlSearchBean;

public interface DasFlowDetailUrlService {

	/**
	 * 用户详细-页面浏览详细列表
	 */
	public List<DasFlowDetailUrl> findList(DasFlowDetailUrlSearchBean attribution) throws Exception;

	/**
	 * 用户详细-页面浏览详细列表分页
	 */
	public PageGrid<DasFlowDetailUrlSearchBean> findPageList(PageGrid<DasFlowDetailUrlSearchBean> pageGrid) throws Exception;

}
