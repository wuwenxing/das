package com.gw.das.service.website.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Ordering;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.website.bean.DasFlowStatistics;
import com.gw.das.dao.website.bean.HomePageChartModel;
import com.gw.das.dao.website.bean.HomePageConditonModel;
import com.gw.das.dao.website.bean.HomePageDataModel;
import com.gw.das.service.website.HomePageChartService;

@Service
public class HomePageChartServiceImpl implements HomePageChartService {

	private static final Logger logger = LoggerFactory.getLogger(HomePageChartServiceImpl.class);

	private List<DasFlowStatistics> getRpcData(HomePageConditonModel condition) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		// 设置查询条件
		if (StringUtils.isNotBlank(condition.getUtmcsr())) {
			parameters.put("utmcsr", condition.getUtmcsr());
		}
		if (StringUtils.isNotBlank(condition.getUtmcmd())) {
			parameters.put("utmcmd", condition.getUtmcmd());
		}
		parameters.put("startTime", condition.getStartTime());
		parameters.put("endTime", condition.getEndTime());

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowStatisticsHoursSumData, parameters,
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowStatistics>>() {
		});
		return rowsList;
	}

	private List<HomePageDataModel> getAvgHoursChartData(HomePageConditonModel condition) throws Exception {
		List<DasFlowStatistics> retList = getRpcData(condition);

		List<HomePageDataModel> list = new ArrayList<HomePageDataModel>();

		// 处理小数位(求平均值并保留两位小数，4舍5入)
		int dayDiff = DateUtil.daysBetween(DateUtil.stringToDate(condition.getStartTime(), "yyyy-MM-dd"),
				DateUtil.stringToDate(condition.getEndTime(), "yyyy-MM-dd")) + 1;
		if (retList != null && retList.size() > 0) {
			for (int i = 0; i < retList.size(); i++) {
				DasFlowStatistics statistic = retList.get(i);
				HomePageDataModel tempModel = new HomePageDataModel();
				tempModel.setHours(statistic.getHours());
				tempModel.setAdvisoryCount(
						new BigDecimal(statistic.getAdvisoryCountQQ() + statistic.getAdvisoryCountLIVE800())
								.divide(new BigDecimal(dayDiff), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
				tempModel.setDemoCount(new BigDecimal(statistic.getDemoCount())
						.divide(new BigDecimal(dayDiff), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
				tempModel.setDepositCount(new BigDecimal(statistic.getDepositCount())
						.divide(new BigDecimal(dayDiff), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
				tempModel.setRealCount(new BigDecimal(statistic.getRealCount())
						.divide(new BigDecimal(dayDiff), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
				tempModel.setVisitCount(new BigDecimal(statistic.getVisitCount())
						.divide(new BigDecimal(dayDiff), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
				list.add(tempModel);
			}
		}

		return list;
	}

	private List<HomePageDataModel> getHoursChartData(HomePageConditonModel condition) throws Exception {
		List<DasFlowStatistics> retList = getRpcData(condition);
		List<HomePageDataModel> list = new ArrayList<HomePageDataModel>();

		if (retList != null && retList.size() > 0) {
			for (int i = 0; i < retList.size(); i++) {
				DasFlowStatistics statistic = retList.get(i);
				HomePageDataModel tempModel = new HomePageDataModel();
				tempModel.setHours(statistic.getHours());
				tempModel.setAdvisoryCount(
						new BigDecimal(statistic.getAdvisoryCountQQ() + statistic.getAdvisoryCountLIVE800())
								.doubleValue());
				tempModel.setDemoCount(new BigDecimal(statistic.getDemoCount()).doubleValue());
				tempModel.setDepositCount(new BigDecimal(statistic.getDepositCount()).doubleValue());
				tempModel.setVisitCount(new BigDecimal(statistic.getVisitCount()).doubleValue());
				tempModel.setRealCount(new BigDecimal(statistic.getRealCount()).doubleValue());
				list.add(tempModel);
			}
		}

		return list;
	}

	@Override
	public Map<String, List<HomePageDataModel>> hourChart(HomePageConditonModel condition) throws Exception {
		Map<String, List<HomePageDataModel>> retMap = new HashMap<String, List<HomePageDataModel>>();
		try {
			String initalStartTime = condition.getStartTime();// 页面传过来的初始查询条件
			String initalEndTime = condition.getEndTime();

			Map<String, Object> parameters = new HashMap<String, Object>();

			// 设置查询条件
			if (StringUtils.isNotBlank(condition.getUtmcsr())) {
				parameters.put("utmcsr", condition.getUtmcsr());
			}
			if (StringUtils.isNotBlank(condition.getUtmcmd())) {
				parameters.put("utmcmd", condition.getUtmcmd());
			}
			if (condition.getBusinessPlatform() != null) {
				parameters.put("businessPlatform", condition.getBusinessPlatform() + "");
			}
			parameters.put("startTime", condition.getStartTime());
			parameters.put("endTime", condition.getEndTime());

			// 1.如果起始时间和结束时间相同，返回最近7天的平均数据，否则返回起始时间到结束时间的平均数据（注意：最近7天是不包括当天的）
			if (condition.getStartTime().equals(condition.getEndTime())) {
				condition.setStartTime(DateUtil.toYyyymmdd(DateUtil.getBeforeDay(new Date(), 7)));// 七天前
				condition.setEndTime(DateUtil.toYyyymmdd(DateUtil.getBeforeDay(new Date(), 1)));// 昨天

			}
			retMap.put("avg", getAvgHoursChartData(condition));

			// 2.今天的小时图表数据
			String today = DateUtil.toYyyymmdd(new Date());
			condition.setStartTime(today);
			condition.setEndTime(today);
			retMap.put("today", getHoursChartData(condition));

			// 3.昨天的小时图表数据
			String yesterday = DateUtil.toYyyymmdd(DateUtil.getBeforeDay(new Date(), 1));
			condition.setStartTime(yesterday);
			condition.setEndTime(yesterday);
			retMap.put("yesterday", getHoursChartData(condition));

			condition.setStartTime(initalStartTime);// 页面传过来的初始查询条件
			condition.setEndTime(initalEndTime);
		} catch (Exception e) {
			logger.error("hourChart方法发生异常，异常信息：" + e.getMessage(), e);
			throw e;
		}
		return retMap;
	}

	@Override
	public void addHourChartPoint(HomePageChartModel chartModel) throws Exception {
		try {
			if (chartModel != null && chartModel.getDataMap() != null) {

				// 1.给今天的小时图补点(从0点开始，一直到当前时间里面的小时数，中间缺失的补上，其它的不补)
				List<HomePageDataModel> todayDatas = chartModel.getDataMap().get("today");
				int curHour = DateUtil.getHours(new Date());// 当前小时
				List<HomePageDataModel> todayDatasRet = new ArrayList<HomePageDataModel>();// 不能在原来的集合基础上面修改，这里只好新起一个对象
				for (int i = 0; i <= curHour; i++) {
					boolean isContain = false;
					for (int j = 0; j < todayDatas.size(); j++) {
						HomePageDataModel tempTodayData = todayDatas.get(j);
						if (i == Integer.parseInt(tempTodayData.getHours() == null ? "0" : tempTodayData.getHours())) {
							isContain = true;
							if (Integer.parseInt(tempTodayData.getHours()) < 10) {// 原始数据里面的小时小于10的也要补0
								tempTodayData.setHours(tempTodayData.getHours());
							}
							break;
						}
					}
					if (!isContain) {// 全部遍历完之后，没有找到匹配的，就补点
						HomePageDataModel tempPoint = new HomePageDataModel();
						tempPoint.setHours(i < 10 ? ("0" + i) : ("" + i));
						todayDatasRet.add(tempPoint);
					}
				}
				todayDatasRet.addAll(todayDatas);// 再加上之前已经有的数据
				todayDatasRet = Ordering.usingToString().sortedCopy(todayDatasRet);

				// 2.给昨天的小时图补点
				List<HomePageDataModel> yesterdayDatas = chartModel.getDataMap().get("yesterday");
				List<HomePageDataModel> yesterdayDatasRet = new ArrayList<HomePageDataModel>();// 不能在原来的集合基础上面修改，这里只好新起一个对象
				for (int i = 0; i <= 23; i++) {
					boolean isContain = false;
					for (int j = 0; j < yesterdayDatas.size(); j++) {
						HomePageDataModel tempYesterdayData = yesterdayDatas.get(j);
						if (i == Integer
								.parseInt(tempYesterdayData.getHours() == null ? "0" : tempYesterdayData.getHours())) {
							isContain = true;
							if (Integer.parseInt(tempYesterdayData.getHours()) < 10) {// 原始数据里面的小时小于10的也要补0
								tempYesterdayData.setHours(tempYesterdayData.getHours());
							}
							break;
						}
					}
					if (!isContain) {// 全部遍历完之后，没有找到匹配的，就补点
						HomePageDataModel tempPoint = new HomePageDataModel();
						tempPoint.setHours(i < 10 ? ("0" + i) : ("" + i));
						yesterdayDatasRet.add(tempPoint);
					}
				}
				yesterdayDatasRet.addAll(yesterdayDatas);

				// 3.给平均数据的小时图补点
				List<HomePageDataModel> avgDatas = chartModel.getDataMap().get("avg");
				List<HomePageDataModel> avgDatasRet = new ArrayList<HomePageDataModel>();
				for (int i = 0; i <= 23; i++) {
					boolean isContain = false;
					for (int j = 0; j < avgDatas.size(); j++) {
						HomePageDataModel tempAvgData = avgDatas.get(j);
						if (i == Integer.parseInt(tempAvgData.getHours() == null ? "0" : tempAvgData.getHours())) {
							isContain = true;
							if (Integer.parseInt(tempAvgData.getHours()) < 10) {// 原始数据里面的小时小于10的也要补0
								tempAvgData.setHours(tempAvgData.getHours());
							}
							break;
						}
					}
					if (!isContain) {// 全部遍历完之后，没有找到匹配的，就补点
						HomePageDataModel tempPoint = new HomePageDataModel();
						tempPoint.setHours(i < 10 ? ("0" + i) : ("" + i));
						avgDatasRet.add(tempPoint);
					}
				}
				avgDatasRet.addAll(avgDatas);

				// 4.重新设置返回值
				chartModel.getDataMap().put("today", Ordering.usingToString().sortedCopy(todayDatasRet));// 排序
				chartModel.getDataMap().put("yesterday", Ordering.usingToString().sortedCopy(yesterdayDatasRet));// 排序
				chartModel.getDataMap().put("avg", Ordering.usingToString().sortedCopy(avgDatasRet));
			}
		} catch (Exception e) {
			logger.error("addHourChartPoint方法发生异常，异常信息：" + e.getMessage(), e);
			throw e;
		}
	}

}
