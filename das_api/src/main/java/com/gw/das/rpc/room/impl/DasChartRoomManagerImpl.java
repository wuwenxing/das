package com.gw.das.rpc.room.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.room.DasChartBehaviorSourceCountDao;
import com.gw.das.business.dao.room.DasChartRoomOnlineHoursDao;
import com.gw.das.business.dao.room.DasChartRoomRegTouristUserStatisticsDao;
import com.gw.das.business.dao.room.DasChartRoomSpeakStatisticsDao;
import com.gw.das.business.dao.room.DasChartRoomStatisticsDao;
import com.gw.das.business.dao.room.DasChartRoomStatisticsLogindaysDao;
import com.gw.das.business.dao.room.DasChartRoomStatisticsVisitcountsDao;
import com.gw.das.business.dao.room.DasChartRoomUserTypeStatisticsDao;
import com.gw.das.business.dao.room.DasChartVisitorDetailRoomDao;
import com.gw.das.business.dao.room.DasChartVisitorRoomDao;
import com.gw.das.business.dao.room.DasRoomDetailESDao;
import com.gw.das.business.dao.room.DasRoomLoginStatisticsDao;
import com.gw.das.business.dao.room.entity.DasChartFlowAttribution;
import com.gw.das.business.dao.room.entity.DasChartFlowDetailES;
import com.gw.das.business.dao.room.entity.DasChartFlowDetailSearchBean;
import com.gw.das.business.dao.room.entity.DasChartFlowDetailUrl;
import com.gw.das.business.dao.room.entity.DasChartFlowStatistics;
import com.gw.das.business.dao.room.entity.DasChartFlowStatisticsAverage;
import com.gw.das.business.dao.room.entity.DasChartRoomDetail;
import com.gw.das.business.dao.room.entity.DasChartRoomDetailSum;
import com.gw.das.business.dao.room.entity.DasChartRoomOnlineHours;
import com.gw.das.business.dao.room.entity.DasChartRoomRegTouristUserStatistics;
import com.gw.das.business.dao.room.entity.DasChartRoomSearchModel;
import com.gw.das.business.dao.room.entity.DasChartRoomSpeakStatistics;
import com.gw.das.business.dao.room.entity.DasChartRoomStatistics;
import com.gw.das.business.dao.room.entity.DasChartRoomUserTypeStatistics;
import com.gw.das.business.dao.room.entity.DasRoomLoginStatistics;
import com.gw.das.business.dao.room.entity.DasRoomLoginStatisticsSearchBean;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.room.DasChartRoomManager;

public class DasChartRoomManagerImpl extends ManagerImpl implements DasChartRoomManager {

	private static final Logger logger = LoggerFactory.getLogger(DasChartRoomManagerImpl.class);


	@Override
	public Map<String, String> roomLoginStatisticsPage(String jsonStr) throws Exception{
		try {
			PageGrid<DasRoomLoginStatisticsSearchBean> pg = new PageGrid<DasRoomLoginStatisticsSearchBean>();
			DasRoomLoginStatisticsSearchBean model = JacksonUtil.readValue(jsonStr, DasRoomLoginStatisticsSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(OrmUtil.reflectColumn(DasRoomLoginStatistics.class, model.getSort()));
			pg.setOrder(model.getOrder());
			DasRoomLoginStatisticsDao dao = getService(DasRoomLoginStatisticsDao.class);
			pg = dao.queryForPage(pg, new DasRoomLoginStatistics());
			List<DasRoomLoginStatistics> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal() + "");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[直播间报表_登陆数据明细]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[直播间报表_登陆数据明细]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> roomLoginStatisticsList(String jsonStr) throws Exception{
		try {
			DasRoomLoginStatisticsSearchBean model = JacksonUtil.readValue(jsonStr, DasRoomLoginStatisticsSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setSort(OrmUtil.reflectColumn(DasRoomLoginStatistics.class, model.getSort()));
			DasRoomLoginStatisticsDao dao = getService(DasRoomLoginStatisticsDao.class);
			List<DasRoomLoginStatistics> list = dao.queryForList(model, new DasRoomLoginStatistics());
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[直播间报表_登陆数据明细]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[直播间报表_登陆数据明细]接口异常:" + e.getMessage());
		}
	}
	
	@Override
	public Map<String, String> visitorPage(String jsonStr) throws Exception{
		try {
			PageGrid<DasChartRoomSearchModel> pg = new PageGrid<DasChartRoomSearchModel>();
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort("startTime");
			pg.setOrder("desc");
			DasChartVisitorRoomDao dasChartRoomDao = getService(DasChartVisitorRoomDao.class);
			pg = dasChartRoomDao.queryForPage(pg, new DasChartRoomDetailSum());
			List<DasChartRoomDetailSum> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal() + "");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[访客列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[访客列表]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> visitorList(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setSort("startTime");
			model.setOrder("desc");
			DasChartVisitorRoomDao dasChartRoomDao = getService(DasChartVisitorRoomDao.class);
			List<DasChartRoomDetailSum> list = dasChartRoomDao.queryForList(model, new DasChartRoomDetailSum());
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[访客列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[访客列表]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> visitorDetailPage(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			PageGrid<DasChartRoomSearchModel> pg = new PageGrid<DasChartRoomSearchModel>();
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort("startTime");
			pg.setOrder("desc");
			DasChartVisitorDetailRoomDao dasChartVisitorDetailRoomDao = getService(DasChartVisitorDetailRoomDao.class);
			pg = dasChartVisitorDetailRoomDao.queryForPage(pg, new DasChartRoomDetail());
			List<DasChartRoomDetail> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[访客明细列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[访客明细列表]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> visitorDetailList(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DasChartVisitorDetailRoomDao dasChartVisitorDetailRoomDao = getService(DasChartVisitorDetailRoomDao.class);
			List<DasChartRoomDetail> list = dasChartVisitorDetailRoomDao.queryForList(model, new DasChartRoomDetail());

			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[访客明细列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[访客明细列表]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsPage(String jsonStr) throws Exception{
		try {
			PageGrid<DasChartRoomSearchModel> pg = new PageGrid<DasChartRoomSearchModel>();
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());

			if (null != model) {
				if (ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())) {
					// 如果是日期自动排序,则小时数字段也排序
					if ("dateTime".equals(model.getSort())) {
						model.setSort("dateTime,hours");
						String tempOrder = model.getOrder();
						model.setOrder(tempOrder + "," + tempOrder);
					}
				} else if (ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())) {
					// 如果是日期自动排序,则周数字段也排序
					if (null != model && "dateTime".equals(model.getSort())) {
						model.setSort("dateTime,weeks");
						String tempOrder = model.getOrder();
						model.setOrder(tempOrder + "," + tempOrder);
					}
				}
			}

			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DasChartRoomStatisticsDao dasChartRoomStatisticsDao = getService(DasChartRoomStatisticsDao.class);
			pg = dasChartRoomStatisticsDao.queryForPage(pg, new DasChartRoomStatistics());
			List<DasChartRoomStatistics> list = pg.getRows();
			
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
	public Map<String, String> statisticsList(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");

			if (null != model) {
				if (ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())) {
					// 如果是日期自动排序,则小时数字段也排序
					if ("dateTime".equals(model.getSort())) {
						model.setSort("dateTime,hours");
						String tempOrder = model.getOrder();
						model.setOrder(tempOrder + "," + tempOrder);
					}
				} else if (ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())) {
					// 如果是日期自动排序,则周数字段也排序
					if (null != model && "dateTime".equals(model.getSort())) {
						model.setSort("dateTime,weeks");
						String tempOrder = model.getOrder();
						model.setOrder(tempOrder + "," + tempOrder);
					}
				}
			}

			DasChartRoomStatisticsDao dasChartRoomStatisticsDao = getService(DasChartRoomStatisticsDao.class);
			List<DasChartRoomStatistics> list = dasChartRoomStatisticsDao.queryForList(model,
					new DasChartRoomStatistics());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[（时/日/周/月）统计]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[（时/日/周/月）统计]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsSpeakdaysPage(String jsonStr) throws Exception{
		return this.statisticsCountsOrdaysPage(jsonStr);
	}

	@Override
	public Map<String, String> statisticsSpeakdaysList(String jsonStr) throws Exception{
		return this.statisticsCountsOrdaysList(jsonStr);
	}

	@Override
	public Map<String, String> statisticsSpeakcountsPage(String jsonStr) throws Exception{
		return this.statisticsCountsOrdaysPage(jsonStr);
	}

	@Override
	public Map<String, String> statisticsSpeakcountsList(String jsonStr) throws Exception{
		return this.statisticsCountsOrdaysList(jsonStr);
	}

	@Override
	public Map<String, String> statisticsLogindaysPage(String jsonStr) throws Exception{
		try {
			PageGrid<DasChartRoomSearchModel> pg = new PageGrid<DasChartRoomSearchModel>();
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DasChartRoomStatisticsLogindaysDao dasChartRoomStatisticsLogindaysDao = getService(
					DasChartRoomStatisticsLogindaysDao.class);
			pg = dasChartRoomStatisticsLogindaysDao.queryForPage(pg, new DasChartRoomSpeakStatistics());
			List<DasChartRoomSpeakStatistics> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[登录天数分析]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[登录天数分析]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsLogindaysList(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DasChartRoomStatisticsLogindaysDao dasChartRoomStatisticsLogindaysDao = getService(
					DasChartRoomStatisticsLogindaysDao.class);
			List<DasChartRoomSpeakStatistics> list = dasChartRoomStatisticsLogindaysDao.queryForList(model,
					new DasChartRoomSpeakStatistics());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[登录天数分析]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[登录天数分析]接口异常:" + e.getMessage());
		}
	}

	private Map<String, String> statisticsCountsOrdaysPage(String jsonStr) throws Exception{
		String type = "";
		try {
			PageGrid<DasChartRoomSearchModel> pg = new PageGrid<DasChartRoomSearchModel>();
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			type = model.getSpeakTypeStatistics();
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DasChartRoomSpeakStatisticsDao dasChartRoomSpeakStatisticsDao = getService(
					DasChartRoomSpeakStatisticsDao.class);
			DasChartRoomSpeakStatistics dasChartRoomSpeakStatistics = new DasChartRoomSpeakStatistics();
			pg = dasChartRoomSpeakStatisticsDao.queryForPage(pg, dasChartRoomSpeakStatistics);
			List<DasChartRoomSpeakStatistics> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[（0、1-5、5-10、10-20、20-30、30以上）次数与天数分析]type =" + type + ",接口异常:" + e.getMessage(), e);
			throw new Exception("调用[（0、1-5、5-10、10-20、20-30、30以上）次数与天数分析]type =" + type + ",接口异常:" + e.getMessage());
		}
	}

	private Map<String, String> statisticsCountsOrdaysList(String jsonStr) throws Exception{
		String type = "";
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			type = model.getSpeakTypeStatistics();
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DasChartRoomSpeakStatisticsDao dasChartRoomSpeakStatisticsDao = getService(
					DasChartRoomSpeakStatisticsDao.class);
			DasChartRoomSpeakStatistics dasChartRoomSpeakStatistics = new DasChartRoomSpeakStatistics();
			List<DasChartRoomSpeakStatistics> list = dasChartRoomSpeakStatisticsDao.queryForList(model,
					dasChartRoomSpeakStatistics);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[（0、1-5、5-10、10-20、20-30、30以上）次数与天数分析]type =" + type + ",接口异常:" + e.getMessage(), e);
			throw new Exception("调用[（0、1-5、5-10、10-20、20-30、30以上）次数与天数分析]type =" + type + ",接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsOnlineHoursPage(String jsonStr) throws Exception{
		try {
			PageGrid<DasChartRoomSearchModel> pg = new PageGrid<DasChartRoomSearchModel>();
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DasChartRoomOnlineHoursDao dasChartRoomOnlineHoursDao = getService(DasChartRoomOnlineHoursDao.class);
			DasChartRoomOnlineHours dasChartRoomOnlineHours = new DasChartRoomOnlineHours();
			pg = dasChartRoomOnlineHoursDao.queryForPage(pg, dasChartRoomOnlineHours);
			List<DasChartRoomOnlineHours> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[用户在线时长分页]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[用户在线时长分页]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsOnlineHoursList(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DasChartRoomOnlineHoursDao dasChartRoomOnlineHoursDao = getService(DasChartRoomOnlineHoursDao.class);
			List<DasChartRoomOnlineHours> list = dasChartRoomOnlineHoursDao.queryForList(model,
					new DasChartRoomOnlineHours());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[用户在线时长不分页]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[用户在线时长不分页]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsUserTypePage(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			PageGrid<DasChartRoomSearchModel> pg = new PageGrid<DasChartRoomSearchModel>();
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DasChartRoomUserTypeStatisticsDao dasChartRoomUserTypeStatisticsDao = getService(
					DasChartRoomUserTypeStatisticsDao.class);
			pg = dasChartRoomUserTypeStatisticsDao.queryForPage(pg, new DasChartRoomUserTypeStatistics());
			List<DasChartRoomUserTypeStatistics> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[登录次/人数、访问次/人数、发言次/人数 分析分页]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[登录次/人数、访问次/人数、发言次/人数 分析分页]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsUserTypeList(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DasChartRoomUserTypeStatisticsDao dasChartRoomUserTypeStatisticsDao = getService(
					DasChartRoomUserTypeStatisticsDao.class);
			List<DasChartRoomUserTypeStatistics> list = dasChartRoomUserTypeStatisticsDao.queryForList(model,
					new DasChartRoomUserTypeStatistics());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[登录次/人数、访问次/人数、发言次/人数 分析不分页]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[登录次/人数、访问次/人数、发言次/人数 分析不分页]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsRegTouristUserPage(String jsonStr) throws Exception{
		try {
			PageGrid<DasChartRoomSearchModel> pg = new PageGrid<DasChartRoomSearchModel>();
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DasChartRoomRegTouristUserStatisticsDao dasChartRoomRegTouristUserStatisticsDao = getService(
					DasChartRoomRegTouristUserStatisticsDao.class);
			pg = dasChartRoomRegTouristUserStatisticsDao.queryForPage(pg, new DasChartRoomRegTouristUserStatistics());
			List<DasChartRoomRegTouristUserStatistics> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[注册用户统计周期(日、周、月)列表分析分页]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[注册用户统计周期(日、周、月)列表分析分页]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsRegTouristUserList(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DasChartRoomRegTouristUserStatisticsDao dasChartRoomRegTouristUserStatisticsDao = getService(
					DasChartRoomRegTouristUserStatisticsDao.class);
			List<DasChartRoomRegTouristUserStatistics> list = dasChartRoomRegTouristUserStatisticsDao
					.queryForList(model, new DasChartRoomRegTouristUserStatistics());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[注册用户统计周期(日、周、月)列表分析不分页]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[注册用户统计周期(日、周、月)列表分析不分页]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsVisitcountsPage(String jsonStr) throws Exception{
		try {
			PageGrid<DasChartRoomSearchModel> pg = new PageGrid<DasChartRoomSearchModel>();
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DasChartRoomStatisticsVisitcountsDao dasChartRoomStatisticsVisitcountsDao = getService(
					DasChartRoomStatisticsVisitcountsDao.class);
			pg = dasChartRoomStatisticsVisitcountsDao.queryForPage(pg, new DasChartRoomSpeakStatistics());
			List<DasChartRoomRegTouristUserStatistics> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[访问记录统计周期(日、周、月)列表分析]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[访问记录统计周期(日、周、月)列表分析]接口异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> statisticsVisitcountsList(String jsonStr) throws Exception{
		try {
			DasChartRoomSearchModel model = JacksonUtil.readValue(jsonStr, DasChartRoomSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DasChartRoomStatisticsVisitcountsDao dasChartRoomStatisticsVisitcountsDao = getService(
					DasChartRoomStatisticsVisitcountsDao.class);
			List<DasChartRoomSpeakStatistics> list = dasChartRoomStatisticsVisitcountsDao.queryForList(model,
					new DasChartRoomSpeakStatistics());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[访问记录统计周期(日、周、月)列表分析]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[访问记录统计周期(日、周、月)列表分析]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 直播间开户统计列表-不分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindBehaviorCountList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			Map<String, String> resultMap = new HashMap<String, String>();
			DasChartBehaviorSourceCountDao dasChartBehaviorSourceCountDao = getService(DasChartBehaviorSourceCountDao.class);
			List<DasChartFlowStatistics> list = dasChartBehaviorSourceCountDao.dasFindBehaviorCountList(getStatisticsModel(parameters));
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("DasFlowStatisticsBehaviorList方法出现异常:" + e.getMessage(), e);
			throw new Exception("DasFlowStatisticsBehaviorList方法出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 直播间开户统计列表-分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindBehaviorCountPageList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasChartBehaviorSourceCountDao dasChartBehaviorSourceCountDao = getService(DasChartBehaviorSourceCountDao.class);

			PageGrid<DasChartFlowStatistics> pg = getPageGrid(parameters);
			pg = dasChartBehaviorSourceCountDao.dasFindBehaviorCountPageList(pg);
			List<DasChartFlowAttribution> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("dasFindBehaviorCountPageList方法出现异常:" + e.getMessage(), e);
			throw new Exception("dasFindBehaviorCountPageList方法出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 直播间来源媒介统计-不分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindRoomSourceCountList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasChartBehaviorSourceCountDao dasChartBehaviorSourceCountDao = getService(DasChartBehaviorSourceCountDao.class);
			List<DasChartFlowStatistics> list = dasChartBehaviorSourceCountDao.dasFindRoomSourceCountList(getStatisticsModel(parameters));
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("dasFindRoomSourceCountList方法出现异常:" + e.getMessage(), e);
			throw new Exception("dasFindRoomSourceCountList方法出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 直播间来源媒介统计-分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindRoomSourceCountPageList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasChartBehaviorSourceCountDao dasChartBehaviorSourceCountDao = getService(DasChartBehaviorSourceCountDao.class);
			PageGrid<DasChartFlowStatistics> pg = getPageGrid(parameters);
			pg = dasChartBehaviorSourceCountDao.dasFindRoomSourceCountPageList(pg);
			List<DasChartFlowStatistics> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));			
			return resultMap;
		} catch (Exception e) {
			logger.error("dasFindRoomSourceCountPageList方法出现异常:" + e.getMessage(), e);
			throw new Exception("dasFindRoomSourceCountPageList方法出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 直播间来源媒介统计环比-分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindRoomSourceCountTreePageList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasChartBehaviorSourceCountDao dasChartBehaviorSourceCountDao = getService(DasChartBehaviorSourceCountDao.class);

			PageGrid<DasChartFlowStatistics> pg = getPageGrid(parameters);
			pg = dasChartBehaviorSourceCountDao.dasFindRoomSourceCountPage(pg);
			List<DasChartFlowStatistics> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			StringBuffer utmcmdList = new StringBuffer();
			StringBuffer utmcsrList = new StringBuffer();
			for (int i = 0; i < list.size(); i++)
			{
				DasChartFlowStatistics dasBean = list.get(i);
				if(i+1 == list.size()){
					utmcmdList.append(dasBean.getUtmcmd());
					utmcsrList.append(dasBean.getUtmcsr());
				}else{
					utmcmdList.append(dasBean.getUtmcmd()).append(",");
					utmcsrList.append(dasBean.getUtmcsr()).append(",");
				}
			}				
			pg.getSearchModel().setUtmcmdList(utmcmdList.toString().split(","));
			pg.getSearchModel().setUtmcsrList(utmcsrList.toString().split(","));
			
			List<DasChartFlowStatistics> contrastFrontList = dasChartBehaviorSourceCountDao.dasFindRoomSourceCountTreePageList(pg.getSearchModel());//环比前的数据
		
			pg.getSearchModel().setDataTimeStart(pg.getSearchModel().getStartTimeCompare());
			pg.getSearchModel().setDataTimeEnd(pg.getSearchModel().getEndTimeCompare());
			List<DasChartFlowStatistics> contrastBacktList = dasChartBehaviorSourceCountDao.dasFindRoomSourceCountTreePageList(pg.getSearchModel());//环比后的数据
							
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));	
			resultMap.put("contrastFront", JacksonUtil.toJSon(contrastFrontList));
			resultMap.put("contrastBack", JacksonUtil.toJSon(contrastBacktList));
			return resultMap;				
		} catch (Exception e) {
			logger.error("dasFindRoomSourceCountTreePageList方法出现异常:" + e.getMessage(), e);
			throw new Exception("dasFindRoomSourceCountTreePageList方法出现异常:" + e.getMessage());
		}
	}
	
	private DasChartFlowStatistics getStatisticsModel(Map<String, String> parameters) {
		DasChartFlowStatistics model = new DasChartFlowStatistics();
		String utmcsr = "";
		if (null != parameters.get("utmcsr")) {
			utmcsr = parameters.get("utmcsr") + "";
		}
		String utmcmd = "";
		if (null != parameters.get("utmcmd")) {
			utmcmd = parameters.get("utmcmd") + "";
		}
		String utmccn = "";
		if (null != parameters.get("utmccn")) {
			utmccn = parameters.get("utmccn") + "";
		}
		String utmcct = "";
		if (null != parameters.get("utmcct")) {
			utmcct = parameters.get("utmcct") + "";
		}
		String utmctr = "";
		if (null != parameters.get("utmctr")) {
			utmctr = parameters.get("utmctr") + "";
		}
		
		String startTime = "";
		if (null != parameters.get("startTime")) {
			startTime = parameters.get("startTime") + "";
		}
		String endTime = "";
		if (null != parameters.get("endTime")) {
			endTime = parameters.get("endTime") + "";
		}

		String businessPlatform = "";
		if (null != parameters.get("businessPlatform")) {
			businessPlatform = parameters.get("businessPlatform") + "";
		}
		if (StringUtils.isBlank(businessPlatform)) {
			businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
		}

		String searchType = "";
		if (null != parameters.get("searchType")) {
			searchType = parameters.get("searchType") + "";
		}

		if (parameters.get("utmcmdChecked") != null && "true".equals(parameters.get("utmcmdChecked"))) {
			boolean utmcmdChecked = true;
			model.setUtmcmdChecked(utmcmdChecked);
		}
		if (parameters.get("utmcsrChecked") != null && "true".equals(parameters.get("utmcsrChecked"))) {
			boolean utmcsrChecked = true;
			model.setUtmcsrChecked(utmcsrChecked);
		}
		if (parameters.get("utmccnChecked") != null && "true".equals(parameters.get("utmccnChecked"))) {
			boolean utmccnChecked = true;
			model.setUtmccnChecked(utmccnChecked);
		}
		if (parameters.get("utmcctChecked") != null && "true".equals(parameters.get("utmcctChecked"))) {
			boolean utmcctChecked = true;
			model.setUtmcctChecked(utmcctChecked);
		}
		if (parameters.get("utmctrChecked") != null && "true".equals(parameters.get("utmctrChecked"))) {
			boolean utmctrChecked = true;
			model.setUtmctrChecked(utmctrChecked);
		}
		model.setBusinessPlatform(businessPlatform);
		model.setUtmcsr(utmcsr);
		model.setUtmcmd(utmcmd);
		model.setUtmccn(utmccn);
		model.setUtmcct(utmcct);
		model.setUtmctr(utmctr);
		model.setSearchType(searchType);

		if (StringUtils.isNotBlank(startTime)) {
			model.setDataTimeStart(startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			model.setDataTimeEnd(endTime);
		}
		
		// 排序字段
		String sortName = "";
		if (null != parameters.get("sortName")) {
			sortName = parameters.get("sortName") + "";
		}
		String sortDirection = "";
		if (null != parameters.get("sortDirection")) {
			sortDirection = parameters.get("sortDirection") + "";
		}
		
		if (StringUtils.isNotBlank(parameters.get("devicetype"))) {
			model.setDevicetype(parameters.get("devicetype"));;
		}
		model.setSortName(sortName);
		model.setSortDirection(sortDirection);
		
		if (StringUtils.isNotBlank(parameters.get("utmcmdList"))) {
			String utmcmds = parameters.get("utmcmdList");
			model.setUtmcmdList(utmcmds.split(","));
		}
		if (StringUtils.isNotBlank(parameters.get("utmcsrList"))) {
			String utmcsrs = parameters.get("utmcsrList");
			model.setUtmcsrList(utmcsrs.split(","));
		}
		
		if (StringUtils.isNotBlank(parameters.get("startTimeCompare"))) {
			model.setStartTimeCompare(parameters.get("startTimeCompare"));
		}
		if (StringUtils.isNotBlank(parameters.get("endTimeCompare"))) {
			model.setEndTimeCompare(parameters.get("endTimeCompare"));
		}
		return model;
	}
	
	private PageGrid<DasChartFlowStatistics> getPageGrid(Map<String, String> parameters) {
		int pageNumber = Integer.parseInt(parameters.get("pageNumber") + "");
		int pageSize = Integer.parseInt(parameters.get("pageSize") + "");
		String sort = (String) parameters.get("sortName");
		String order = (String) parameters.get("sortDirection");
		PageGrid<DasChartFlowStatistics> pg = new PageGrid<DasChartFlowStatistics>();
		pg.setSearchModel(getStatisticsModel(parameters));
		pg.setPageNumber(pageNumber);
		pg.setPageSize(pageSize);
		pg.setSort(sort);
		pg.setOrder(order);
		return pg;
	}
	
	@Override
	public Map<String, String> dasRoomStatisticsAverageList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasChartBehaviorSourceCountDao dasChartBehaviorSourceCountDao = getService(DasChartBehaviorSourceCountDao.class);
			List<DasChartFlowStatisticsAverage> list = dasChartBehaviorSourceCountDao
					.statisticsAverageQueryForList(getStatisticsAverageModel(parameters));
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			resultMap.put("total", list.size()+"");
			return resultMap;
		} catch (Exception e) {
			logger.error("dasRoomStatisticsAverageList方法出现异常:" + e.getMessage(), e);
			throw new Exception("dasRoomStatisticsAverageList方法出现异常:" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> dasRoomStatisticsAveragePage(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			DasChartBehaviorSourceCountDao dasChartBehaviorSourceCountDao = getService(DasChartBehaviorSourceCountDao.class);

			int pageNumber = Integer.parseInt(parameters.get("pageNumber") + "");
			int pageSize = Integer.parseInt(parameters.get("pageSize") + "");
			String sortName = "";
			if (null != parameters.get("sortName")) {
				sortName = parameters.get("sortName") + "";
			}
			String sortDirection = "";
			if (null != parameters.get("sortDirection")) {
				sortDirection = parameters.get("sortDirection") + "";
			}
			PageGrid<DasChartFlowStatisticsAverage> pg = new PageGrid<DasChartFlowStatisticsAverage>();

			pg.setSearchModel(getStatisticsAverageModel(parameters));
			pg.setPageNumber(pageNumber);
			pg.setPageSize(pageSize);
			pg.setSort(sortName);
			pg.setOrder(sortDirection);
			pg = dasChartBehaviorSourceCountDao.statisticsAverageQueryForPage(pg);
			List<DasChartFlowAttribution> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("dasRoomStatisticsAveragePage方法出现异常:" + e.getMessage(), e);
			throw new Exception("dasRoomStatisticsAveragePage方法出现异常:" + e.getMessage());
		}
	}
	
	private DasChartFlowStatisticsAverage getStatisticsAverageModel(Map<String, String> parameters) {
		DasChartFlowStatisticsAverage model = new DasChartFlowStatisticsAverage();

		String utmcmd = "";
		if (null != parameters.get("utmcmd")) {
			utmcmd = parameters.get("utmcmd") + "";
		}

		String dataTime = "";
		if (null != parameters.get("dataTime")) {
			dataTime = parameters.get("dataTime") + "";
		}
		String behaviorType = "";
		if (null != parameters.get("behaviorType")) {
			behaviorType = parameters.get("behaviorType") + "";
		}
		String businessPlatform = "";
		if (null != parameters.get("businessPlatform")) {
			businessPlatform = parameters.get("businessPlatform") + "";
		}
		if (StringUtils.isBlank(businessPlatform)) {
			businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
		}
		if (StringUtils.isNoneBlank(parameters.get("devicetype"))) {
			model.setDevicetype(parameters.get("devicetype"));
		}

		model.setBusinessPlatform(businessPlatform);
		model.setUtmcmd(utmcmd);
		model.setDataTime(dataTime);
		model.setBehaviorType(behaviorType);
		return model;
	}
	
	/**
	 * 不分页查询用户行为列表报表记录 - elasticsearch
	 */
	@Override
	public Map<String, String> dasFindBehaviorESList(String jsonStr) throws Exception{
		try {
			DasChartFlowDetailSearchBean model = JacksonUtil.readValue(jsonStr, DasChartFlowDetailSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			
			DasRoomDetailESDao dasRoomDetailESDao = getService(DasRoomDetailESDao.class);
			List<DasChartFlowDetailES> list = dasRoomDetailESDao.findDasFlowDetailList(model);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[详细汇总列表-查询]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[详细汇总列表-查询]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分页查询用户行为列表报表记录 - elasticsearch
	 */
	@Override
	public Map<String, String> dasFindBehaviorESPageList(String jsonStr) throws Exception {
		try {
			DasChartFlowDetailSearchBean model = JacksonUtil.readValue(jsonStr, DasChartFlowDetailSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			
			DasRoomDetailESDao dasRoomDetailESDao = getService(DasRoomDetailESDao.class);
			PageGrid<DasChartFlowDetailSearchBean> pg = new PageGrid<DasChartFlowDetailSearchBean>();
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg = dasRoomDetailESDao.findDasFlowDetailPageList(model);
			List<DasChartFlowDetailES> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[用户行为列表-分页查询]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[用户行为列表-分页查询]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 用户详细-页面浏览详细列表分页
	 * 
	 */
	@Override
	public Map<String, String> dasFindFlowDetailUrlPageList(String jsonStr) throws Exception{
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			String flowDetailId = "";
			if(null != parameters.get("flowDetailId")){
				flowDetailId = parameters.get("flowDetailId") + "";
			}
			String flowDetailUrl = "";
			if(null != parameters.get("flowDetailUrl")){
				flowDetailUrl = parameters.get("flowDetailUrl") + "";
			}
			String utmcsr = "";
			if(null != parameters.get("utmcsr")){
				utmcsr = parameters.get("utmcsr") + "";
			}
			String utmcmd = "";
			if(null != parameters.get("utmcmd")){
				utmcmd = parameters.get("utmcmd") + "";
			}
			String startTime = "";
			if(null != parameters.get("startTime")){
				startTime = parameters.get("startTime") + "";
			}
			String endTime = "";
			if(null != parameters.get("endTime")){
				endTime = parameters.get("endTime") + "";
			}
			String businessPlatform = "";
			if(null != parameters.get("businessPlatform")){
				businessPlatform = parameters.get("businessPlatform") + "";
			}
			if(StringUtils.isBlank(businessPlatform)){
				businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
			}
			String devicetype = "";
			if(null != parameters.get("devicetype")){
				devicetype = parameters.get("devicetype") + "";
			}
			
			int pageNumber = Integer.parseInt(parameters.get("pageNumber")+"");
			int pageSize = Integer.parseInt(parameters.get("pageSize")+"");
			String sort = parameters.get("sortName") + "";
			String order = parameters.get("sortDirection") + "";
			
			DasRoomDetailESDao dasRoomDetailESDao = getService(DasRoomDetailESDao.class);
			
			DasChartFlowDetailUrl model = new DasChartFlowDetailUrl();
			model.setBusinessPlatform(businessPlatform);
			model.setUtmcsr(utmcsr);
			model.setUtmcmd(utmcmd);
			model.setFlowDetailUrl(flowDetailUrl);
			model.setFlowDetailId(flowDetailId);
			model.setDevicetype(devicetype);
			model.setPageNumber(pageNumber);
			model.setPageSize(pageSize);
			if (StringUtils.isNotBlank(startTime)) {
				model.setVisitTimeStart(startTime);
			}
			if (StringUtils.isNotBlank(endTime)) {
				model.setVisitTimeEnd(endTime);
			}
			model.setSort(sort);
			model.setOrder(order);
			PageGrid<DasChartFlowDetailUrl> pg = new PageGrid<DasChartFlowDetailUrl>();
			pg.setSearchModel(model);
			pg.setPageNumber(pageNumber);
			pg.setPageSize(pageSize);
			pg.setSort(sort);
			pg.setOrder(order);
			pg = dasRoomDetailESDao.findDasflowUrlDetailPageList(model);
			List<DasChartFlowAttribution> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
			
		}catch (Exception e) {
			logger.error("dasFindFlowDetailUrlPageList方法出现异常:" + e.getMessage(), e);
			throw new Exception("dasFindFlowDetailUrlPageList方法出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 用户详细-页面浏览详细列表不分页
	 */
	@Override
	public Map<String, String> dasFindFlowDetailUrlList(String jsonStr) throws Exception {
		try {
			Map<String, String> parameters = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {
			});
			String flowDetailId = "";
			if(null != parameters.get("flowDetailId")){
				flowDetailId = parameters.get("flowDetailId") + "";
			}
			String flowDetailUrl = "";
			if(null != parameters.get("flowDetailUrl")){
				flowDetailUrl = parameters.get("flowDetailUrl") + "";
			}
			String utmcsr = "";
			if(null != parameters.get("utmcsr")){
				utmcsr = parameters.get("utmcsr") + "";
			}
			String utmcmd = "";
			if(null != parameters.get("utmcmd")){
				utmcmd = parameters.get("utmcmd") + "";
			}
			String startTime = "";
			if(null != parameters.get("startTime")){
				startTime = parameters.get("startTime") + "";
			}
			String endTime = "";
			if(null != parameters.get("endTime")){
				endTime = parameters.get("endTime") + "";
			}
			String businessPlatform = "";
			if(null != parameters.get("businessPlatform")){
				businessPlatform = parameters.get("businessPlatform") + "";
			}
			if(StringUtils.isBlank(businessPlatform)){
				businessPlatform = ClientUserContext.get().getBusinessPlatform() + "";
			}
			String devicetype = "";
			if(null != parameters.get("devicetype")){
				devicetype = parameters.get("devicetype") + "";
			}
			String sort = parameters.get("sortName") + "";
			String order = parameters.get("sortDirection") + "";
			
			DasChartFlowDetailUrl model = new DasChartFlowDetailUrl();
			model.setBusinessPlatform(businessPlatform);
			model.setUtmcsr(utmcsr);
			model.setUtmcmd(utmcmd);
			model.setFlowDetailUrl(flowDetailUrl);
			model.setFlowDetailId(flowDetailId);
			model.setDevicetype(devicetype);
			if (StringUtils.isNotBlank(startTime)) {
				model.setVisitTimeStart(startTime);
			}
			if (StringUtils.isNotBlank(endTime)) {
				model.setVisitTimeEnd(endTime);
			}
			model.setSort(sort);
			model.setOrder(order);
			DasRoomDetailESDao dasRoomDetailESDao = getService(DasRoomDetailESDao.class);
			List<DasChartFlowDetailUrl> list = dasRoomDetailESDao.findDasflowUrlDetailESList(model);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		}catch (Exception e) {
			logger.error("dasFindFlowDetailUrlList方法出现异常:" + e.getMessage(), e);
			throw new Exception("dasFindFlowDetailUrlList方法出现异常:" + e.getMessage());
		}
	}

}
