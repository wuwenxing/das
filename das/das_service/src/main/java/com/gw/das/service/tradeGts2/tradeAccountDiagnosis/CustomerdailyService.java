package com.gw.das.service.tradeGts2.tradeAccountDiagnosis;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.bean.CustomerdailyVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;

/**
 * 查询账户结算日报表记录接口
 * 
 * @author darren
 *
 */
public interface CustomerdailyService {
	
	
	/**
	 * 分页查询账户结算日报表记录-elasticsearch
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findCustomerdailyPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询账户结算日报表记录-elasticsearch
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public List<CustomerdailyVO> findCustomerdailyList(TradeSearchModel searchBean)
			throws Exception;
	
	/**
	 * 分页查询账户结算日报表记录-宽表查询
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findCustomerdailyWithPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询账户结算日报表记录-宽表查询
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public List<CustomerdailyVO> findCustomerdailyWithList(TradeSearchModel searchBean)
			throws Exception;

	
}
