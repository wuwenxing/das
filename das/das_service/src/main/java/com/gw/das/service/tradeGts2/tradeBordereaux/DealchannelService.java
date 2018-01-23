package com.gw.das.service.tradeGts2.tradeBordereaux;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.bean.DealchannelVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;

/**
 * GTS2下单途径比例报表接口
 * 
 * @author darren
 *
 */
public interface DealchannelService {

	/**
	 * 分页查询GTS2下单途径比例报表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findDealchannelPage(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询GTS2下单途径比例报表记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DealchannelVO> findDealchannelList(TradeSearchModel searchModel) throws Exception;
	
}
