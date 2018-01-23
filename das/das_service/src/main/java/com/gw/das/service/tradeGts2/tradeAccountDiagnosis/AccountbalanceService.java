package com.gw.das.service.tradeGts2.tradeAccountDiagnosis;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.bean.AccountbalanceVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;

/**
 * 查询所有账户余额变动记录接口
 * 
 * @author darren
 *
 */
public interface AccountbalanceService {

	/**
	 * 分页查询所有账户余额变动记录-elasticsearch
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findAccountbalancePageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 分页查询所有账户余额变动记录-elasticsearch
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public List<AccountbalanceVO> findAccountbalanceList(TradeSearchModel searchBean)
			throws Exception;
	
	/**
	 * 分页查询所有账户余额变动记录-宽表查询
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findAccountbalanceWithPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询所有账户余额变动记录-宽表查询
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public List<AccountbalanceVO> findAccountbalanceWithList(TradeSearchModel searchBean)
			throws Exception;
	
	
}
