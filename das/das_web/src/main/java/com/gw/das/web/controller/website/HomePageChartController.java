package com.gw.das.web.controller.website;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.dao.website.bean.DasFlowStatistics;
import com.gw.das.dao.website.bean.DasFlowStatisticsSearchBean;
import com.gw.das.dao.website.bean.HomePageChartModel;
import com.gw.das.dao.website.bean.HomePageConditonModel;
import com.gw.das.service.website.DasFlowStatisticsService;
import com.gw.das.service.website.HomePageChartService;
import com.gw.das.web.controller.system.BaseController;

/**
 * 官网报表
 * 
 * @author wayne
 */
@Controller
@RequestMapping("/HomePageChartController")
public class HomePageChartController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(HomePageChartController.class);

	@Autowired
	private HomePageChartService homePageChartService;
	@Autowired
	private DasFlowStatisticsService dasFlowStatisticsService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			String startTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
			String endTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
			request.setAttribute("startTime", startTime);
			request.setAttribute("endTime", endTime);
			return "/homePageChart/homePageChart";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 统计分析-官网报表
	 */
	@RequestMapping("/hourChart")
	@ResponseBody
	public HomePageChartModel hourChart(HttpServletRequest request, HomePageConditonModel condition) {
		try {
			HomePageChartModel charModel = new HomePageChartModel();
			charModel.setCondition(condition);
			charModel.setDataMap(homePageChartService.hourChart(condition));
			homePageChartService.addHourChartPoint(charModel);
			return charModel;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new HomePageChartModel();
		}
	}

	/**
	 * 首页
	 * 最近7天-最近1天行为统计
	 * 当日pc及移动端行为统计
	 */
	@RequestMapping("/homeChart")
	@ResponseBody
	public List<DasFlowStatistics> homeChart(HttpServletRequest request,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			List<DasFlowStatistics> recordList = new ArrayList<DasFlowStatistics>();
			List<DasFlowStatistics> less7RecordList = new ArrayList<DasFlowStatistics>();
			DasFlowStatistics pcObj = new DasFlowStatistics();
			DasFlowStatistics mobileObj = new DasFlowStatistics();
			
			// 最近7天
			String less7Time = DateUtil.formatDateToString(DateUtil.getBeforeDay(new Date(), 7), "yyyy-MM-dd");
			// 最近1天
			String less1Time = DateUtil.formatDateToString(DateUtil.getBeforeDay(new Date(), 1), "yyyy-MM-dd");
			// 今天
			String nowTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
			
			dasFlowStatisticsSearchBean.setStartTime(less7Time);
			dasFlowStatisticsSearchBean.setEndTime(less1Time);
			dasFlowStatisticsSearchBean.setSortName("dataTime");
			dasFlowStatisticsSearchBean.setSortDirection("asc");
			dasFlowStatisticsSearchBean.setSearchType("behavior");
			less7RecordList = dasFlowStatisticsService.findList(dasFlowStatisticsSearchBean);
			
			dasFlowStatisticsSearchBean.setStartTime(nowTime);
			dasFlowStatisticsSearchBean.setEndTime(nowTime);
			dasFlowStatisticsSearchBean.setPlatformType(ClientEnum.pc.getLabelKey());// pc端
			List<DasFlowStatistics> pcList = dasFlowStatisticsService.findList(dasFlowStatisticsSearchBean);
			if(null != pcList && pcList.size()>0){
				pcObj = pcList.get(0);
			}
			dasFlowStatisticsSearchBean.setPlatformType(ClientEnum.mobile.getLabelKey());// 移动端
			List<DasFlowStatistics> mobileList = dasFlowStatisticsService.findList(dasFlowStatisticsSearchBean);
			if(null != mobileList && mobileList.size()>0){
				mobileObj = mobileList.get(0);
			}
			
			recordList.addAll(less7RecordList); // 最近7天-最近1天
			recordList.add(pcObj); // 当天pc端
			recordList.add(mobileObj); // 当天移动端
			return recordList;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasFlowStatistics>();
		}
	}
	
	/**
	 * 首页-当日pc及移动端行为统计
	 */
	@RequestMapping("/findDayBehavior")
	@ResponseBody
	public List<DasFlowStatistics> findDayBehavior(HttpServletRequest request,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			List<DasFlowStatistics> recordList = new ArrayList<DasFlowStatistics>();
			DasFlowStatistics pcObj = new DasFlowStatistics();
			DasFlowStatistics mobileObj = new DasFlowStatistics();
			
			// 今天
			String nowTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");

			dasFlowStatisticsSearchBean.setStartTime(nowTime);
			dasFlowStatisticsSearchBean.setEndTime(nowTime);
			dasFlowStatisticsSearchBean.setSortName("dataTime");
			dasFlowStatisticsSearchBean.setSortDirection("asc");
			dasFlowStatisticsSearchBean.setSearchType("behavior");
			dasFlowStatisticsSearchBean.setPlatformType(ClientEnum.pc.getLabelKey());// pc端
			List<DasFlowStatistics> pcList = dasFlowStatisticsService.findList(dasFlowStatisticsSearchBean);
			if(null != pcList && pcList.size()>0){
				pcObj = pcList.get(0);
			}
			dasFlowStatisticsSearchBean.setPlatformType(ClientEnum.mobile.getLabelKey());// 移动端
			List<DasFlowStatistics> mobileList = dasFlowStatisticsService.findList(dasFlowStatisticsSearchBean);
			if(null != mobileList && mobileList.size()>0){
				mobileObj = mobileList.get(0);
			}
			
			recordList.add(pcObj); // 当天pc端
			recordList.add(mobileObj); // 当天移动端
			return recordList;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasFlowStatistics>();
		}
	}
	
	
}
