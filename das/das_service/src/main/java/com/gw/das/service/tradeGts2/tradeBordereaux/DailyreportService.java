package com.gw.das.service.tradeGts2.tradeBordereaux;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.bean.DailyreportVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;

/**
 * 结余图表报表接口
 * 
 * @author darren
 *
 */
public interface DailyreportService {

	/**
	 * 分页查询结余图表报表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findDailyreportPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询结余图表报表记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DailyreportVO> findDailyreportList(TradeSearchModel searchModel) throws Exception;
	
}
