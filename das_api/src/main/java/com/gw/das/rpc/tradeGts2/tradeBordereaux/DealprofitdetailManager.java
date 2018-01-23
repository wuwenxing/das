package com.gw.das.rpc.tradeGts2.tradeBordereaux;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 交易记录总结报表(包括了交易手数图表和毛利图表)/交易类别数据接口
 * 
 * @author darren
 *
 */
public interface DealprofitdetailManager  extends Manager{

	/**
	 * 分页查询交易人数/次数报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealprofitdetailUseraAndDealamountPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询交易人数/次数报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealprofitdetailUseraAndDealamountList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询MT4/GTS2数据报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealprofitdetailMT4AndGts2PageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询MT4/GTS2数据报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealprofitdetailMT4AndGts2List(String jsonStr) throws Exception;
	
	/**
	 * 分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealprofitdetailPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealprofitdetailList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询交易类别数据记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealcategoryPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询交易类别数据记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealcategoryList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询交易类别倫敦/人民幣数据记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealcategoryAxuAxgcnhPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询交易类别倫敦/人民幣数据记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealcategoryAxuAxgcnhList(String jsonStr) throws Exception;
	
	
	/**
	 * 分页查询人均交易手数记录
	 * 
	 * @return
	 */
	public Map<String, String> findAverageTransactionVolumePageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询人均交易手数记录
	 * 
	 * @return
	 */
	public Map<String, String> findAverageTransactionVolumeList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询交易情况记录
	 * 
	 * @return
	 */
	public Map<String, String> findTradeSituationPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询交易情况记录
	 * 
	 * @return
	 */
	public Map<String, String> findTradeSituationList(String jsonStr) throws Exception;
	
}
