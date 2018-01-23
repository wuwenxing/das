package com.gw.das.service.operateStatistics;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.operateStatistics.bean.ChannelActiveEffectVO;
import com.gw.das.dao.operateStatistics.bean.CustomerQualityVO;
import com.gw.das.dao.operateStatistics.bean.OperateStatisticsModel;

/**
 * 运营统计
 * 
 * @author darren
 *
 */
public interface OperateStatisticsService {
	
	/**
	 * 分页查询渠道新增、活跃报表（client）记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<OperateStatisticsModel> findChannelActivePageList(PageGrid<OperateStatisticsModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询渠道新增、活跃报表（client）记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<ChannelActiveEffectVO> findChannelActiveList(OperateStatisticsModel searchModel) throws Exception;
	
	/**
	 * 分页查询渠道新增、活跃、效果报表（web）记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<OperateStatisticsModel> findChannelEffectPageList(PageGrid<OperateStatisticsModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询渠道新增、活跃、效果报表（web）记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<ChannelActiveEffectVO> findChannelEffectList(OperateStatisticsModel searchModel) throws Exception;
	
	/**
	 * 分页查询获客质量（web、client）记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid<OperateStatisticsModel> findCustomerQualityPageList(PageGrid<OperateStatisticsModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询获客质量（web、client）记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<CustomerQualityVO> findCustomerQualityList(OperateStatisticsModel searchModel) throws Exception;
	
}
