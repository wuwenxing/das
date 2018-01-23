package com.gw.das.business.analysis.service;

import java.util.List;

import com.gw.das.business.dao.website.entity.DasFlowAttribution;

/**
 * 归因统计接口
 * @author kirin.guan
 *
 */
public interface IDataAttributionUserTrackService {
	
	 /**
	  * 获取归因数据
	  * @param DasFlowAttribution
	  * @return
	  */
	 public DasFlowAttribution getAttributionByData(DasFlowAttribution DasFlowAttribution) throws Exception;
	 
	 /**
	 * 批量保存
	 * @param list
	 */
	public void insertList(List<DasFlowAttribution> list) throws Exception;
	
	/**
	 * 批量修改
	 * @param list
	 */
	public void batchUpdateList(List<DasFlowAttribution> list) throws Exception;
}
