package com.gw.das.service.website;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.website.bean.DasUserInfoSearchBean;

public interface DasUserInfoService {
	
	/**
	 * 用户列表查询
	 */
	public PageGrid<DasUserInfoSearchBean> findPageList(PageGrid<DasUserInfoSearchBean> pageGrid) throws Exception;
	
}
