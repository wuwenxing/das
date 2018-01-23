package com.gw.das.web.controller.website;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.dao.website.bean.DasFlowStatistics;
import com.gw.das.dao.website.bean.DasFlowStatisticsAverage;
import com.gw.das.dao.website.bean.DasFlowStatisticsSearchBean;
import com.gw.das.service.market.ChannelService;
import com.gw.das.service.website.DasFlowStatisticsService;
import com.gw.das.web.controller.system.BaseController;

/**
 * 官网行为统计/来源媒介统计/官网时段统计
 * @author wayne
 */
@Controller
@RequestMapping("/DasFlowStatisticsController")
public class DasFlowStatisticsController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasFlowStatisticsController.class);

	@Autowired
	private DasFlowStatisticsService dasFlowStatisticsService;
	
	@Autowired
	private ChannelService channelService;

	/**
	 * 官网行为统计-跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/report/dasFlowStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 官网行为统计-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasFlowStatisticsSearchBean> pageList(HttpServletRequest request,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			dasFlowStatisticsSearchBean.setSearchType("behavior");
			PageGrid<DasFlowStatisticsSearchBean> pageGrid = dasFlowStatisticsService.findPageList(super.createPageGrid(request, dasFlowStatisticsSearchBean));
			// 小计与总计
			DasFlowStatistics footer = new DasFlowStatistics();
			for(Object obj: pageGrid.getRows()){
				DasFlowStatistics record = (DasFlowStatistics)obj;
				// 小计与总计
				footer.setVisitCount(footer.getVisitCount() + record.getVisitCount());
				footer.setAdvisoryCountQQ(footer.getAdvisoryCountQQ() + record.getAdvisoryCountQQ());
				footer.setAdvisoryCountLIVE800(footer.getAdvisoryCountLIVE800() + record.getAdvisoryCountLIVE800());
				footer.setDemoCount(footer.getDemoCount() + record.getDemoCount());
				footer.setRealCount(footer.getRealCount() + record.getRealCount());
				footer.setDepositCount(footer.getDepositCount() + record.getDepositCount());
			}
			List<DasFlowStatistics> list = new ArrayList<DasFlowStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasFlowStatisticsSearchBean>();
		}
	}

	/**
	 * 官网行为统计-不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasFlowStatistics> findList(HttpServletRequest request,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			// 排序
			String sort = request.getParameter("sort");
			String order = request.getParameter("order");
			dasFlowStatisticsSearchBean.setSortName(sort);
			dasFlowStatisticsSearchBean.setSortDirection(order);
			dasFlowStatisticsSearchBean.setSearchType("behavior");
			List<DasFlowStatistics> recordList = dasFlowStatisticsService.findList(dasFlowStatisticsSearchBean);
			return recordList;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasFlowStatistics>();
		}
	}
	
	/**
	 * 官网行为统计-导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			dasFlowStatisticsSearchBean.setSearchType("behavior");
			dasFlowStatisticsSearchBean.setSortName(request.getParameter("sort"));
			dasFlowStatisticsSearchBean.setSortDirection(request.getParameter("order"));
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.behaviorCount.getPath())));
			// 2、需要导出的数据
			List<DasFlowStatistics> recordList = dasFlowStatisticsService.findList(dasFlowStatisticsSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasFlowStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasFlowStatistics param) {
					if("platformType".equals(fieldName)){
						if(null == fieldValue){
							return "(全部)";
						}else{
							if("0".equals(String.valueOf(fieldValue))){
								return "PC端";
							}else if("1".equals(String.valueOf(fieldValue))){
								return "移动端";
							}else{
								// 统计时，为空代表所有客户端
								return "(全部)";
							}
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.behaviorCount.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 来源媒介统计-跳转管理页面
	 */
	@RequestMapping(value = "/pageMedia", method = { RequestMethod.GET })
	public String pageMedia(HttpServletRequest request) {
		try {
			String dataTime = request.getParameter("dataTime");
	    	if(StringUtils.isNotBlank(dataTime)){
	    		request.setAttribute("startTime", dataTime);
	    		request.setAttribute("endTime", dataTime);
	    	}else{
				String startTime = DateUtil.formatDateToString(DateUtil.getBeforeDay(new Date(), 1), "yyyy-MM-dd");
				String endTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
	    		request.setAttribute("startTime", startTime);
	    		request.setAttribute("endTime", endTime);
	    	}
	    	
/*	    	String startTimeCompare = DateUtil.formatDateToString(DateUtil.getBeforeDay(new Date(), 1), "yyyy-MM-dd");
			String endTimeCompare = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
    		request.setAttribute("startTimeCompare", startTimeCompare);
    		request.setAttribute("endTimeCompare", endTimeCompare);*/
	    	
			String utmcsr = request.getParameter("utmcsr");
			String utmcmd = request.getParameter("utmcmd");
			String utmccn = request.getParameter("utmccn");
			String utmcct = request.getParameter("utmcct");
			String utmctr = request.getParameter("utmctr");
	    	if(StringUtils.isNotBlank(utmcsr)){
	    		request.setAttribute("utmcsr", utmcsr);
	    	}
	    	if(StringUtils.isNotBlank(utmcmd)){
	    		request.setAttribute("utmcmd", utmcmd);
	    	}
	    	if(StringUtils.isNotBlank(utmccn)){
	    		request.setAttribute("utmccn", utmccn);
	    	}
	    	if(StringUtils.isNotBlank(utmcct)){
	    		request.setAttribute("utmcct", utmcct);
	    	}
	    	if(StringUtils.isNotBlank(utmctr)){
	    		request.setAttribute("utmctr", utmctr);
	    	}
	    	String platformType = request.getParameter("platformType");
	    	if(StringUtils.isNotBlank(platformType)){
	    		if("0".equals(platformType)){
	    			request.setAttribute("platformType",ClientEnum.pc.getLabelKey());
	    		}
                if("1".equals(platformType)){
                	request.setAttribute("platformType",ClientEnum.mobile.getLabelKey());
	    		}
	    	}
	    	request.setAttribute("clientEnum", ClientEnum.getList());
	    	String channel = request.getParameter("channel");
	    	request.setAttribute("channel", channel);
			List<String> channelList = channelService.findListGroupName(ChannelTypeEnum.webSite);
			request.setAttribute("channelList", channelList);
			return "/report/dasFlowStatisticsMedia";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 来源媒介统计-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageListMedia", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasFlowStatisticsSearchBean> pageListMedia(HttpServletRequest request,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			if(StringUtils.isNotBlank(dasFlowStatisticsSearchBean.getChannelIds())){
				List<ChannelEntity> channelEntityList = channelService.findListByName(ChannelTypeEnum.webSite, dasFlowStatisticsSearchBean.getChannelIds());
				StringBuffer utmcmdList = new StringBuffer();
				StringBuffer utmcsrList = new StringBuffer();
				for (int i = 0; i < channelEntityList.size(); i++) {
					ChannelEntity channel = channelEntityList.get(i);
					if(i+1 == channelEntityList.size()){
						utmcmdList.append(channel.getUtmcmd());
						utmcsrList.append(channel.getUtmcsr());
					}else{
						utmcmdList.append(channel.getUtmcmd()).append(",");
						utmcsrList.append(channel.getUtmcsr()).append(",");
					}
				}
				if (StringUtils.isNotBlank(dasFlowStatisticsSearchBean.getUtmcsr())) {
					if(utmcsrList.length() > 0){					
						utmcsrList.append(",").append(dasFlowStatisticsSearchBean.getUtmcsr());
					}else{
						utmcsrList.append(dasFlowStatisticsSearchBean.getUtmcsr());
					}
				}
				if (StringUtils.isNotBlank(dasFlowStatisticsSearchBean.getUtmcmd())) {
					if(utmcmdList.length() > 0){					
						utmcmdList.append(",").append(dasFlowStatisticsSearchBean.getUtmcmd());
					}else{
						utmcmdList.append(dasFlowStatisticsSearchBean.getUtmcmd());
					}
				}
				dasFlowStatisticsSearchBean.setUtmcmdList(utmcmdList.toString());
				dasFlowStatisticsSearchBean.setUtmcsrList(utmcsrList.toString());
			}
			
			dasFlowStatisticsSearchBean.setSearchType("media");
			PageGrid<DasFlowStatisticsSearchBean> pageGrid;
			if(StringUtils.isNotBlank(dasFlowStatisticsSearchBean.getStartTimeCompare()) && StringUtils.isNotBlank(dasFlowStatisticsSearchBean.getEndTimeCompare())
					&& (!dasFlowStatisticsSearchBean.getStartTime().equals(dasFlowStatisticsSearchBean.getStartTimeCompare()) || !dasFlowStatisticsSearchBean.getEndTime().equals(dasFlowStatisticsSearchBean.getEndTimeCompare()))){
				pageGrid = dasFlowStatisticsService.findMediaTreePageList(super.createPageGrid(request, dasFlowStatisticsSearchBean));
			}else{
				pageGrid = dasFlowStatisticsService.findMediaPageList(super.createPageGrid(request, dasFlowStatisticsSearchBean));
				// 小计与总计
				DasFlowStatistics footer = new DasFlowStatistics();
				for(Object obj: pageGrid.getRows()){
					DasFlowStatistics record = (DasFlowStatistics)obj;
					// 小计与总计
					footer.setVisitCount(footer.getVisitCount() + record.getVisitCount());
					footer.setAdvisoryCountQQ(footer.getAdvisoryCountQQ() + record.getAdvisoryCountQQ());
					footer.setAdvisoryCountLIVE800(footer.getAdvisoryCountLIVE800() + record.getAdvisoryCountLIVE800());
					footer.setDemoCount(footer.getDemoCount() + record.getDemoCount());
					footer.setRealCount(footer.getRealCount() + record.getRealCount());
					footer.setDepositCount(footer.getDepositCount() + record.getDepositCount());
				}
				List<DasFlowStatistics> list = new ArrayList<DasFlowStatistics>();
				list.add(footer);
				pageGrid.setFooter(list);
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasFlowStatisticsSearchBean>();
		}
	}
	
	/**
	 * 来源媒介统计-导出
	 */
	@RequestMapping("/exportExcelMedia")
	public void exportExcelMedia(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			dasFlowStatisticsSearchBean.setSearchType("media");
			dasFlowStatisticsSearchBean.setSortName(request.getParameter("sort"));
			dasFlowStatisticsSearchBean.setSortDirection(request.getParameter("order"));
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.sourceMediumCount.getPath())));
			// 2、需要导出的数据
			List<DasFlowStatistics> recordList = dasFlowStatisticsService.findMediaList(dasFlowStatisticsSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasFlowStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasFlowStatistics param) {
					if("platformType".equals(fieldName)){
						if(null == fieldValue){
							return "(全部)";
						}else{
							if("0".equals(String.valueOf(fieldValue))){
								return "PC端";
							}else if("1".equals(String.valueOf(fieldValue))){
								return "移动端";
							}else{
								// 统计时，为空代表所有客户端
								return "(全部)";
							}
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.sourceMediumCount.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 官网时段统计-跳转管理页面
	 */
	@RequestMapping(value = "/pageTime", method = { RequestMethod.GET })
	public String pageTime(HttpServletRequest request) {
		try {
			String startTime = DateUtil.formatDateToString(DateUtil.addDays(new Date(),-1), "yyyy-MM-dd HH");
			String endTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH");
    		request.setAttribute("startTime", startTime);
    		request.setAttribute("endTime", endTime);
    		request.setAttribute("clientEnum", ClientEnum.getList());
			return "/report/dasFlowStatisticsTime";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 官网时段统计-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageListTime", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasFlowStatisticsSearchBean> pageListTime(HttpServletRequest request,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			dasFlowStatisticsSearchBean.setSearchType("time");
			PageGrid<DasFlowStatisticsSearchBean> pageGrid = dasFlowStatisticsService.findTimePageList(super.createPageGrid(request, dasFlowStatisticsSearchBean));
			// 小计与总计
			DasFlowStatistics footer = new DasFlowStatistics();
			for(Object obj: pageGrid.getRows()){
				DasFlowStatistics record = (DasFlowStatistics)obj;
				// 小计与总计
				footer.setVisitCount(footer.getVisitCount() + record.getVisitCount());
				footer.setAdvisoryCountQQ(footer.getAdvisoryCountQQ() + record.getAdvisoryCountQQ());
				footer.setAdvisoryCountLIVE800(footer.getAdvisoryCountLIVE800() + record.getAdvisoryCountLIVE800());
				footer.setDemoCount(footer.getDemoCount() + record.getDemoCount());
				footer.setRealCount(footer.getRealCount() + record.getRealCount());
				footer.setDepositCount(footer.getDepositCount() + record.getDepositCount());
			}
			List<DasFlowStatistics> list = new ArrayList<DasFlowStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasFlowStatisticsSearchBean>();
		}
	}
	
	/**
	 * 官网时段统计-导出
	 */
	@RequestMapping("/exportExcelTime")
	public void exportExcelTime(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			dasFlowStatisticsSearchBean.setSearchType("time");
			dasFlowStatisticsSearchBean.setSortName(request.getParameter("sort"));
			dasFlowStatisticsSearchBean.setSortDirection(request.getParameter("order"));
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.timeCount.getPath())));
			// 2、需要导出的数据
			List<DasFlowStatistics> recordList = dasFlowStatisticsService.findTimeList(dasFlowStatisticsSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasFlowStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasFlowStatistics param) {
					if("platformType".equals(fieldName)){
						if(null == fieldValue){
							return "(全部)";
						}else{
							if("0".equals(String.valueOf(fieldValue))){
								return "PC端";
							}else if("1".equals(String.valueOf(fieldValue))){
								return "移动端";
							}else{
								// 统计时，为空代表所有客户端
								return "(全部)";
							}
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.timeCount.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/pageAverage", method = { RequestMethod.GET })
	public String pageAverage(HttpServletRequest request) {
		try {
			request.setAttribute("dataTime", request.getParameter("dataTime"));
			request.setAttribute("platformType", request.getParameter("platformType"));
			request.setAttribute("behaviorType", request.getParameter("behaviorType"));
			return "/report/dasFlowStatisticsAverage";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageListAverage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasFlowStatisticsSearchBean> pageListAverage(HttpServletRequest request,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			PageGrid<DasFlowStatisticsSearchBean> pageGrid = dasFlowStatisticsService.findAveragePageList(super.createPageGrid(request, dasFlowStatisticsSearchBean));
			// 小计与总计
			DasFlowStatisticsAverage footer = new DasFlowStatisticsAverage();
			for(Object obj: pageGrid.getRows()){
				DasFlowStatisticsAverage record = (DasFlowStatisticsAverage)obj;
				// 小计与总计
				if(null != footer.getAccountCount()){
					if(null != record.getAccountCount()){
						footer.setAccountCount(footer.getAccountCount() + record.getAccountCount());
					}
				}else{
					footer.setAccountCount(record.getAccountCount());
				}
				if(null != footer.getLastMonthAvgCount()){
					if(null != record.getLastMonthAvgCount()){
						footer.setLastMonthAvgCount(footer.getLastMonthAvgCount() + record.getLastMonthAvgCount());
					}
				}else{
					footer.setLastMonthAvgCount(record.getLastMonthAvgCount());
				}
			}
			List<DasFlowStatisticsAverage> list = new ArrayList<DasFlowStatisticsAverage>();
			// 对footer平均值四舍五入处理
			if(null != footer.getLastMonthAvgCount()){
				footer.setLastMonthAvgCount(NumberUtil.m2(footer.getLastMonthAvgCount()));
			}
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasFlowStatisticsSearchBean>();
		}
	}
	
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcelAverage")
	public void exportExcelAverage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasFlowStatisticsSearchBean dasFlowStatisticsSearchBean) {
		try {
			dasFlowStatisticsSearchBean.setSortName(request.getParameter("sort"));
			dasFlowStatisticsSearchBean.setSortDirection(request.getParameter("order"));
			String excelPath = ExportTemplateEnum.behaviorOpenAccountCount.getPath();
			String excelValue = ExportTemplateEnum.behaviorOpenAccountCount.getValue();
			
			if("3".equals(dasFlowStatisticsSearchBean.getBehaviorType())){
				excelPath = ExportTemplateEnum.behaviorOpenAccountCountDemo.getPath();
				excelValue = ExportTemplateEnum.behaviorOpenAccountCountDemo.getValue();
			}
			if("4".equals(dasFlowStatisticsSearchBean.getBehaviorType())){
				excelPath = ExportTemplateEnum.behaviorOpenAccountCountReal.getPath();
				excelValue = ExportTemplateEnum.behaviorOpenAccountCountReal.getValue();
			}
			if("5".equals(dasFlowStatisticsSearchBean.getBehaviorType())){
				excelPath = ExportTemplateEnum.behaviorOpenAccountCountDeposit.getPath();
				excelValue = ExportTemplateEnum.behaviorOpenAccountCountDeposit.getValue();
			}
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(excelPath)));
			// 2、需要导出的数据
			List<DasFlowStatisticsAverage> recordList = dasFlowStatisticsService.findAverageList(dasFlowStatisticsSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasFlowStatisticsAverage>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasFlowStatisticsAverage param) {
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(excelValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
}
