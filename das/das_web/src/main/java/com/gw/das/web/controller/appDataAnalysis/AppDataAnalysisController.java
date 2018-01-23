package com.gw.das.web.controller.appDataAnalysis;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisDetailsVO;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisSearchModel;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisVO;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.service.appDataAnalysis.AppDataAnalysisService;
import com.gw.das.service.market.ChannelService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/appDataAnalysisController")
public class AppDataAnalysisController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AppDataAnalysisController.class);

	@Autowired
	private AppDataAnalysisService appDataAnalysisService;
	

	@Autowired
	private ChannelService channelService;
	
	/**
	 * 跳转APP数据分析报表管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());
			request.setAttribute("devicetypeEnum", DeviceTypeEnum.getList2());
			ChannelEntity channelEntity = new ChannelEntity();
			channelEntity.setChannelType(ChannelTypeEnum.app.getLabelKey());
			//List<String> channelList = channelService.findChannelList(channelEntity);
			List<String> channelList = channelService.findChannelUtmcsrList(channelEntity);
			request.setAttribute("channelList", channelList);
			return "/appDataAnalysis/appDataAnalysis";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 查询APP数据分析报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<AppDataAnalysisSearchModel> pageList(HttpServletRequest request,
			@ModelAttribute AppDataAnalysisSearchModel searchBean) {
		try {
			PageGrid<AppDataAnalysisSearchModel> pageGrid = appDataAnalysisService.findAppDataAnalysisPageList(super.createPageGrid(request, searchBean));
			return pageGrid;						
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<AppDataAnalysisSearchModel>();
		}
	}
	
	/**
	 * APP数据分析报表记录-导出
	 */
	@RequestMapping("/exportExcelAnalysis")
	public void exportExcelAnalysis(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute AppDataAnalysisSearchModel searchBean) {
		try {
			String statisticsDailyChannelPath = null;
			String statisticsDailyChannelValue = null;
			if(ReportTypeEnum.weeks.getLabelKey().equals(searchBean.getReportType())){
				 if(searchBean.isChannelChecked() || searchBean.isDevicetypeChecked()){
					 statisticsDailyChannelPath = ExportTemplateEnum.appDataAnalysisWeekAll.getPath();
					 statisticsDailyChannelValue = ExportTemplateEnum.appDataAnalysisWeekAll.getValue();
				 }else{
					 statisticsDailyChannelPath = ExportTemplateEnum.appDataAnalysisWeek.getPath();
					 statisticsDailyChannelValue = ExportTemplateEnum.appDataAnalysisWeek.getValue(); 
				 }				 
			}else{
				if(searchBean.isChannelChecked() || searchBean.isDevicetypeChecked()){
					statisticsDailyChannelPath = ExportTemplateEnum.appDataAnalysisAll.getPath();
					statisticsDailyChannelValue = ExportTemplateEnum.appDataAnalysisAll.getValue();
				}else{
					statisticsDailyChannelPath = ExportTemplateEnum.appDataAnalysis.getPath();
					statisticsDailyChannelValue = ExportTemplateEnum.appDataAnalysis.getValue();
				}
			}
								
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(statisticsDailyChannelPath)));
			// 2、需要导出的数据
			List<AppDataAnalysisVO> recordList = appDataAnalysisService.findAppDataAnalysisList(searchBean);
			int total = null !=recordList && recordList.size() >1?recordList.size() -1 : 0;
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<AppDataAnalysisVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, AppDataAnalysisVO param) {	
					if("devicetype".equals(fieldName) && "1".equals(String.valueOf(fieldValue))){
						return "Android";
					}
				    if("devicetype".equals(fieldName) && "2".equals(String.valueOf(fieldValue))){
						return "IOS";
					}
				    
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(statisticsDailyChannelValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转APP数据分析报表详情页面
	 */
	@RequestMapping(value = "/appDataAnalysisDetails", method = { RequestMethod.GET })
	public String appDataAnalysisDetails(HttpServletRequest request) {
		try {
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());
			request.setAttribute("devicetypeEnum", DeviceTypeEnum.getList());
			List<String> channelList = channelService.findListGroupName();
			request.setAttribute("channelList", channelList);
			
			request.setAttribute("reportType", request.getParameter("reportType"));
			request.setAttribute("dtMonth", request.getParameter("dtMonth"));
			request.setAttribute("channel", request.getParameter("channel"));
			request.setAttribute("mobiletype", request.getParameter("mobiletype"));
			request.setAttribute("field", request.getParameter("field"));
			request.setAttribute("paramValue", request.getParameter("paramValue"));
			
			request.setAttribute("channelChecked", request.getParameter("channelChecked"));
			request.setAttribute("devicetypeChecked", request.getParameter("devicetypeChecked"));
			
			return "/appDataAnalysis/appDataAnalysisDetails";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 查询APP数据分析报表详情
	 */
	@RequestMapping(value = "/appDataAnalysisDetailsPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<AppDataAnalysisSearchModel> appDataAnalysisDetailsPage(HttpServletRequest request,
			@ModelAttribute AppDataAnalysisSearchModel searchBean) {
		try {
			PageGrid<AppDataAnalysisSearchModel> pageGrid = appDataAnalysisService.findAppDataAnalysisDetailsPageList(super.createPageGrid(request, searchBean));
			return pageGrid;	
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<AppDataAnalysisSearchModel>();
		}
	}
	
	/**
	 * APP数据分析报表详情-导出
	 */
	@RequestMapping("/exportExcelAnalysisDetails")
	public void exportExcelAnalysisDetails(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute AppDataAnalysisSearchModel searchBean) {
		try {
			String statisticsDailyChannelPath = ExportTemplateEnum.appDataAnalysisDetails.getPath();
			String statisticsDailyChannelValue = ExportTemplateEnum.appDataAnalysisDetails.getValue();					
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(statisticsDailyChannelPath)));
			// 2、需要导出的数据
			List<AppDataAnalysisDetailsVO> recordList = appDataAnalysisService.findAppDataAnalysisDetailsList(searchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<AppDataAnalysisDetailsVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, AppDataAnalysisDetailsVO param) {						
					if("devicetype".equals(fieldName) && "1".equals(String.valueOf(fieldValue))){
						return "Android";
					}
				    if("devicetype".equals(fieldName) && "2".equals(String.valueOf(fieldValue))){
						return "IOS";
					}
				    
					return fieldValue;	
				}
			});

			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(statisticsDailyChannelValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转开户来源分析页面
	 */
	@RequestMapping(value = "/openSourceAnalysisPage", method = { RequestMethod.GET })
	public String openSourceAnalysisPage(HttpServletRequest request) {
		try {
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());
			request.setAttribute("devicetypeEnum", DeviceTypeEnum.getList());
			List<String> channelList = channelService.findListGroupName();
			request.setAttribute("channelList", channelList);
			return "/appDataAnalysis/openSourceAnalysis";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 查询开户来源分析报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openSourceAnalysisPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<AppDataAnalysisSearchModel> openSourceAnalysisPageList(HttpServletRequest request,
			@ModelAttribute AppDataAnalysisSearchModel searchBean) {
		try {
			PageGrid<AppDataAnalysisSearchModel> pageGrid = appDataAnalysisService.findOpenSourceAnalysisPageList(super.createPageGrid(request, searchBean));
			return pageGrid;						
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<AppDataAnalysisSearchModel>();
		}
	}
	


}
