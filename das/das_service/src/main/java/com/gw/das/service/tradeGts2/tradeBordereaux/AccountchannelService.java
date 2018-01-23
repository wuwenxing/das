package com.gw.das.service.tradeGts2.tradeBordereaux;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.bean.AccountchannelVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;

/**
 * 新开户_激活途径比例接口
 * 
 * @author darren
 *
 */
public interface AccountchannelService {

	/**
	 * 分页查询新开户_激活途径比例报表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findAccountchannelPage(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询新开户_激活途径比例报表记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<AccountchannelVO> findAccountchannelList(TradeSearchModel searchModel) throws Exception;
	
}
