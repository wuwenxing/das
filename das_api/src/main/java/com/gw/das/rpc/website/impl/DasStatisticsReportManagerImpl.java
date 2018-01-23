package com.gw.das.rpc.website.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.website.DasStatisticsReportDao;
import com.gw.das.business.dao.website.entity.DasStatisticsDailyChannel;
import com.gw.das.business.dao.website.entity.DasStatisticsReport;
import com.gw.das.business.dao.website.entity.DasStatisticsReportSearchModel;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.website.DasStatisticsReportManager;

public class DasStatisticsReportManagerImpl extends ManagerImpl implements DasStatisticsReportManager {

	private static final Logger logger = LoggerFactory.getLogger(DasStatisticsReportManagerImpl.class);

	@Override
	public Map<String, String> statisticsReportPage(String jsonStr) throws Exception{
		try {
			PageGrid<DasStatisticsReportSearchModel> pg = new PageGrid<DasStatisticsReportSearchModel>();
			DasStatisticsReportSearchModel model = JacksonUtil.readValue(jsonStr, DasStatisticsReportSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setTableType("1");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DasStatisticsReportDao dasStatisticsReportDao = getService(DasStatisticsReportDao.class);
			pg = dasStatisticsReportDao.queryForPage(pg, new DasStatisticsReport());
			List<DasStatisticsReport> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[（时/日/周/月）统计]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[（时/日/周/月）统计]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsReportList(String jsonStr) throws Exception{
		try {
			DasStatisticsReportSearchModel model = JacksonUtil.readValue(jsonStr, DasStatisticsReportSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setTableType("1");
			DasStatisticsReportDao dasStatisticsReportDao = getService(DasStatisticsReportDao.class);
			List<DasStatisticsReport> list = dasStatisticsReportDao.queryForList(model,
					new DasStatisticsReport());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[（时/日/周/月）统计]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[（时/日/周/月）统计]接口异常:" + e.getMessage());
		}
	}
	
	@Override
	public Map<String, String> dailyChannelPage(String jsonStr) throws Exception{
		try {
			PageGrid<DasStatisticsReportSearchModel> pg = new PageGrid<DasStatisticsReportSearchModel>();
			DasStatisticsReportSearchModel model = JacksonUtil.readValue(jsonStr, DasStatisticsReportSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setTableType("2");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DasStatisticsReportDao dasStatisticsReportDao = getService(DasStatisticsReportDao.class);
			pg = dasStatisticsReportDao.queryForPage(pg, new DasStatisticsDailyChannel());
			List<DasStatisticsDailyChannel> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用日报渠道统计接口异常:" + e.getMessage(), e);
			throw new Exception("调用日报渠道统计接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> dailyChannelList(String jsonStr) throws Exception{
		try {
			DasStatisticsReportSearchModel model = JacksonUtil.readValue(jsonStr, DasStatisticsReportSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setTableType("2");
			DasStatisticsReportDao dasStatisticsReportDao = getService(DasStatisticsReportDao.class);
			List<DasStatisticsDailyChannel> list = dasStatisticsReportDao.queryForList(model,
					new DasStatisticsDailyChannel());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用日报渠道统计接口异常:" + e.getMessage(), e);
			throw new Exception("调用日报渠道统计接口异常:" + e.getMessage());
		}
	}
	
}
