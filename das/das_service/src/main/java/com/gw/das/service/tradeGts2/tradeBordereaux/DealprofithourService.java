package com.gw.das.service.tradeGts2.tradeBordereaux;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.bean.DealprofithourVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;

/**
 * 公司盈亏报表接口
 * 
 * @author darren
 *
 */
public interface DealprofithourService {

	/**
	 * 分页查询公司盈亏报表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findDealprofithourPage(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询公司盈亏报表记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DealprofithourVO> findDealprofithourList(TradeSearchModel searchModel) throws Exception;
	
}
