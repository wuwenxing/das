package com.gw.das.service.tradeGts2.tradeBordereaux;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.bean.DealcategoryVO;
import com.gw.das.dao.trade.bean.DealprofitdetailMT4AndGts2VO;
import com.gw.das.dao.trade.bean.DealprofitdetailUseraAndDealamountVO;
import com.gw.das.dao.trade.bean.DealprofitdetailVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.dao.trade.bean.TradeSituationVO;

/**
 * 交易记录总结报表(包括了交易手数图表和毛利图表)接口
 * 
 * @author darren
 *
 */
public interface DealprofitdetailService {
	
	/**
	 * 分页查询交易人数/次数报表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findDealprofitdetailUseraAndDealamountPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询交易人数/次数报表记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DealprofitdetailUseraAndDealamountVO> findDealprofitdetailUseraAndDealamountList(TradeSearchModel searchModel) throws Exception;

	/**
	 * 分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findDealprofitdetailPage(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DealprofitdetailVO> findDealprofitdetailList(TradeSearchModel searchModel) throws Exception;
	
	/**
	 * 分页查询交易类别数据记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findDealcategoryPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询交易类别数据记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DealcategoryVO> findDealcategoryList(TradeSearchModel searchModel) throws Exception;
	
	/**
	 * 分页查询交易类别倫敦/人民幣数据记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findDealcategoryAxuAxgcnhPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询交易类别倫敦/人民幣数据记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DealcategoryVO> findDealcategoryAxuAxgcnhList(TradeSearchModel searchModel) throws Exception;
	
	/**
	 * 分页查询MT4/GTS2数据报表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findDealprofitdetailMT4AndGts2PageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询MT4/GTS2数据报表记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DealprofitdetailMT4AndGts2VO> findDealprofitdetailMT4AndGts2List(TradeSearchModel searchModel) throws Exception;
	
	/**
	 * 分页查询人均交易手数记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findAverageTransactionVolumePageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询人均交易手数记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DealprofitdetailVO> findAverageTransactionVolumeList(TradeSearchModel searchModel) throws Exception;
	
	/**
	 * 分页查询交易情况记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<TradeSearchModel> findTradeSituationPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询交易情况记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<TradeSituationVO> findTradeSituationList(TradeSearchModel searchModel) throws Exception;
	
}
