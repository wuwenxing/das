package com.gw.das.service.tradeGts2.tradeAccountDiagnosis;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.bean.DealVO;
import com.gw.das.dao.trade.bean.PositionVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;

/**
 * 交易记录接口
 * 
 * @author darren
 *
 */
public interface TradeDetailGts2Service {
	
	/**
	 * 分页查询所有平仓交易记录-宽表查询
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findTradeDetailCloseWithPageList(PageGrid<TradeSearchModel> pageGrid)throws Exception;
	
	/**
	 * 不分页查询所有平仓交易记录-宽表查询
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public List<DealVO> findTradeDetailCloseWithList(TradeSearchModel searchBean)throws Exception;
	
	/**
	 * 分页查询所有开仓交易记录-宽表查询
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findTradeDetailOpenWithPageList(PageGrid<TradeSearchModel> pageGrid)throws Exception;
	
	/**
	 * 不分页查询所有开仓交易记录-宽表查询
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public List<PositionVO> findTradeDetailOpenWithList(TradeSearchModel searchBean)throws Exception;
}
