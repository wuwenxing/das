package com.gw.das.service.website;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.website.bean.DasFlowDetail;
import com.gw.das.dao.website.bean.DasFlowDetailSearchBean;
import com.gw.das.dao.website.bean.DasFlowDetailUrl;

public interface DasUserBehaviorService {

	/**
	 * 用户行为列表分页查询
	 */
	public PageGrid<DasFlowDetailSearchBean> findPageList(PageGrid<DasFlowDetailSearchBean> pageGrid) throws Exception;
	
	/**
	 * 用户行为列表不分页查询
	 */
	public List<DasFlowDetail> findList(DasFlowDetailSearchBean detail) throws Exception;
	
	/**
	 * 用户行为列表url分页查询
	 */
	public PageGrid<DasFlowDetailSearchBean> findDetailUrlPageList(PageGrid<DasFlowDetailSearchBean> pageGrid) throws Exception;
	
	/**
	 * 用户行为列表url不分页查询
	 */
	public List<DasFlowDetailUrl> findDetailUrlList(DasFlowDetailSearchBean detail) throws Exception;
	
	
}
