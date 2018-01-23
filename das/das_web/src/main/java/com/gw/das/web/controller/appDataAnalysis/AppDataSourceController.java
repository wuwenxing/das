package com.gw.das.web.controller.appDataAnalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.PlatformtypeEnum;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.enums.UserTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisSearchModel;
import com.gw.das.dao.appDataAnalysis.bean.DasAppOdsVO;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.service.appDataAnalysis.AppDataSourceService;
import com.gw.das.service.market.ChannelService;
import com.gw.das.web.controller.system.BaseController;

/**
 * APP事件统计列表
 * 
 * @author darren
 *
 */
@Controller
@RequestMapping("/appDataSourceController")
public class AppDataSourceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AppDataSourceController.class);

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private AppDataSourceService appDataSourceService;
	
	/**
	 * 跳转APP事件统计管理页面
	 */
	@RequestMapping(value = "/appDataSourcePage", method = { RequestMethod.GET })
	public String appDataSourcePage(HttpServletRequest request) {
		try {
			/*String startTime = DateUtil.formatDateToString(DateUtil.addDays(new Date(),-1), "yyyy-MM-dd HH");
			String endTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH");
    		request.setAttribute("startTime", startTime);
    		request.setAttribute("endTime", endTime);*/
    		
    		
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList());
			request.setAttribute("devicetypeEnum", DeviceTypeEnum.getList());
			request.setAttribute("userTypeEnum", UserTypeEnum.getList());
			
			List<PlatformtypeEnum> platformTypeEnum = new ArrayList<PlatformtypeEnum>();
			if(CompanyEnum.fx.getLabelKey().equals(String.valueOf(UserContext.get().getCompanyId()))){
				platformTypeEnum.add(PlatformtypeEnum.GTS2);
				platformTypeEnum.add(PlatformtypeEnum.MT4);
			}
			if(CompanyEnum.pm.getLabelKey().equals(String.valueOf(UserContext.get().getCompanyId()))){
				platformTypeEnum.add(PlatformtypeEnum.GTS);
				platformTypeEnum.add(PlatformtypeEnum.GTS2);
				platformTypeEnum.add(PlatformtypeEnum.MT4);
				platformTypeEnum.add(PlatformtypeEnum.MT5);
			}
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(UserContext.get().getCompanyId()))){
				platformTypeEnum.add(PlatformtypeEnum.GTS2);
				platformTypeEnum.add(PlatformtypeEnum.MT4);
			}
			if(CompanyEnum.cf.getLabelKey().equals(String.valueOf(UserContext.get().getCompanyId()))){
				platformTypeEnum.add(PlatformtypeEnum.GTS2);
			}
			request.setAttribute("platformTypeEnum", platformTypeEnum);
			
	    	String channel = request.getParameter("channel");
	    	request.setAttribute("channel", channel);
			ChannelEntity channelEntity = new ChannelEntity();
			channelEntity.setChannelType(ChannelTypeEnum.app.getLabelKey());
			List<String> channelList = channelService.findChannelUtmcsrList(channelEntity);
			request.setAttribute("channelList", channelList);
			return "/appDataAnalysis/appDataSource";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * APP事件统计分页查询 -elasticsearch
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appDataSourcePageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<AppDataAnalysisSearchModel> appDataSourcePageList(HttpServletRequest request, @ModelAttribute AppDataAnalysisSearchModel searchBean) {
		try {
			PageGrid<AppDataAnalysisSearchModel> pageGrid = appDataSourceService.findAppDataSourcePageList(super.createPageGrid(request, searchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<AppDataAnalysisSearchModel>();
		}
	}
	
	/**
	 * APP事件统计报表导出 -elasticsearch
	 */
	@RequestMapping("/exportExcelAppDataSource")
	public void exportExcelAppDataSource(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute AppDataAnalysisSearchModel searchBean) {
		try {
			
			long start = System.currentTimeMillis();
			
			String path = "";
			String value = "";
			if(StringUtils.isNotBlank(searchBean.getReportType())){
				path = ExportTemplateEnum.appEventStatisticsDateReport.getPath();
				value = ExportTemplateEnum.appEventStatisticsDateReport.getValue();
			}else{
				path = ExportTemplateEnum.appEventStatisticsReport.getPath();
				value = ExportTemplateEnum.appEventStatisticsReport.getValue();
			}
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					
					new File(request.getServletContext().getRealPath(path)));
			// 2、需要导出的数据
			List<DasAppOdsVO> recordList = appDataSourceService.findAppDataSourceList(searchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasAppOdsVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasAppOdsVO param) {
					if("userType".equals(fieldName)){
						if(UserTypeEnum.tourist.getLabelKey().equals(String.valueOf(fieldValue))){
							return UserTypeEnum.tourist.getValue();
						}else if(UserTypeEnum.dome.getLabelKey().equals(String.valueOf(fieldValue))){
							return UserTypeEnum.dome.getValue();
						}else if(UserTypeEnum.real.getLabelKey().equals(String.valueOf(fieldValue))){
							return UserTypeEnum.real.getValue();
						}
					}
					
					if("deviceType".equals(fieldName)){
						if("1".equals(String.valueOf(fieldValue))){
							return "ANDROID";
						}
						else if("2".equals(String.valueOf(fieldValue))){
							return "IOS";
						}
						else if("3".equals(String.valueOf(fieldValue))){
							return "PCUI";
						}
						else if("4".equals(String.valueOf(fieldValue))){
							return "WEBUI";
						}
					}
					
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(value, request, response);
			builder.write(response.getOutputStream());		
			
			long end = System.currentTimeMillis();
			logger.info("导出APP事件统计Excel报表消耗时间:"+(end-start)/1000+"秒");
			
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	
	
}
