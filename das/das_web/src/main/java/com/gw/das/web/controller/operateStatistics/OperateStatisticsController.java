package com.gw.das.web.controller.operateStatistics;

import java.io.File;
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

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.PlatformtypeEnum;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.enums.srcTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.dao.operateStatistics.bean.ChannelActiveEffectVO;
import com.gw.das.dao.operateStatistics.bean.CustomerQualityVO;
import com.gw.das.dao.operateStatistics.bean.OperateStatisticsModel;
import com.gw.das.service.market.ChannelService;
import com.gw.das.service.operateStatistics.OperateStatisticsService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/OperateStatisticsController")
public class OperateStatisticsController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(OperateStatisticsController.class);

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private OperateStatisticsService operateStatisticsService;
	
	/**
	 * 跳转渠道新增、活跃报表（client）报表管理页面
	 */
	@RequestMapping(value = "/channelActivePage", method = { RequestMethod.GET })
	public String channelActivePage(HttpServletRequest request) {
		try {
			request.setAttribute("deviceTypeEnum", DeviceTypeEnum.getList4());	
						
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());			
			request.setAttribute("platformEnum", PlatformtypeEnum.getList());
			
			ChannelEntity channelEntity = new ChannelEntity();
			List<String> channelNameList = channelService.findChannelList(channelEntity); //渠道名称
			request.setAttribute("channelNameList", channelNameList);
			
			List<String> channelGroupList = channelService.findChannelGroupList(channelEntity); //渠道分组
			request.setAttribute("channelGroupList", channelGroupList);
			
			List<String> channelLevelList = channelService.findChannelLevelList(channelEntity); //渠道分级
			request.setAttribute("channelLevelList", channelLevelList);
			
			
			return "/operateStatistics/channelActive/channelActive";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 分页查询渠道新增、活跃报表（client）报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findChannelActivePageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<OperateStatisticsModel> findChannelActivePageList(HttpServletRequest request,
			@ModelAttribute OperateStatisticsModel searchBean) {
		try {
			return operateStatisticsService.findChannelActivePageList(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<OperateStatisticsModel>();
		}
	}
		
	/**
	 * 不分页查询渠道新增、活跃报表（client）报表记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findChannelActiveList", method = { RequestMethod.POST })
	@ResponseBody
	public List<ChannelActiveEffectVO> findChannelActiveList(HttpServletRequest request,
			@ModelAttribute OperateStatisticsModel searchBean) throws Exception {
		return operateStatisticsService.findChannelActiveList(searchBean);
	}
	
	/**
	 * 渠道新增、活跃报表（client）报表记录-导出
	 */
	@RequestMapping("/exportExcelChannelActive")
	public void exportExcelChannelActive(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute OperateStatisticsModel searchBean) {
		try {
			String tradeSituationReportPath = ExportTemplateEnum.channelActiveReport.getPath();
			String tradeSituationReportValue = ExportTemplateEnum.channelActiveReport.getValue();
			
			if(ReportTypeEnum.weeks.getLabelKey().equals(searchBean.getReportType())){
				 tradeSituationReportPath = ExportTemplateEnum.channelActiveWeekReport.getPath();
				 tradeSituationReportValue = ExportTemplateEnum.channelActiveWeekReport.getValue();
			}else{
				 tradeSituationReportPath = ExportTemplateEnum.channelActiveReport.getPath();
				 tradeSituationReportValue = ExportTemplateEnum.channelActiveReport.getValue();
			}

			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(tradeSituationReportPath)));
			// 2、需要导出的数据
			List<ChannelActiveEffectVO> recordList = operateStatisticsService.findChannelActiveList(searchBean);
			Integer total = recordList.size();
			if(ReportTypeEnum.weeks.getLabelKey().equals(searchBean.getReportType())){
				for (ChannelActiveEffectVO channelActiveEffectVO : recordList) {
					String execdate = channelActiveEffectVO.getDateTime();
					int weeks = DateUtil.getWeekNumber(DateUtil.stringToDate(execdate));
					channelActiveEffectVO.setWeeks(String.valueOf(weeks));
				}
			}
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<ChannelActiveEffectVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, ChannelActiveEffectVO param) {					
					if("deviceType".equals(fieldName)){
						if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.PC.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.PC.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.ANDROID.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.ANDROID.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.IOS.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.IOS.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.PCUI.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.PCUI.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.APP.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.APP.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.MOBILE.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.MOBILE.getValue();
						}
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(tradeSituationReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转渠道新增、活跃、效果报表（web）报表管理页面
	 */
	@RequestMapping(value = "/channelEffectPage", method = { RequestMethod.GET })
	public String channelEffectPage(HttpServletRequest request) {
		try {
			request.setAttribute("deviceTypeEnum", DeviceTypeEnum.getList5());	
			
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());			
			request.setAttribute("platformEnum", PlatformtypeEnum.getList());
			
			ChannelEntity channelEntity = new ChannelEntity();
			List<String> channelNameList = channelService.findChannelList(channelEntity); //渠道名称
			request.setAttribute("channelNameList", channelNameList);
			
			List<String> channelGroupList = channelService.findChannelGroupList(channelEntity); //渠道分组
			request.setAttribute("channelGroupList", channelGroupList);
			
			List<String> channelLevelList = channelService.findChannelLevelList(channelEntity); //渠道分级
			request.setAttribute("channelLevelList", channelLevelList);
			
			
			return "/operateStatistics/channelEffect/channelEffect";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 分页查询渠道新增、活跃、效果报表（web）报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findChannelEffectPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<OperateStatisticsModel> findChannelEffectPageList(HttpServletRequest request,
			@ModelAttribute OperateStatisticsModel searchBean) {
		try {
			return operateStatisticsService.findChannelEffectPageList(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<OperateStatisticsModel>();
		}
	}
		
	/**
	 * 不分页查询渠道新增、活跃、效果报表（web）报表记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findChannelEffectList", method = { RequestMethod.POST })
	@ResponseBody
	public List<ChannelActiveEffectVO> findChannelEffectList(HttpServletRequest request,
			@ModelAttribute OperateStatisticsModel searchBean) throws Exception {
		return operateStatisticsService.findChannelEffectList(searchBean);
	}
	
	/**
	 * 渠道新增、活跃、效果报表（web）报表记录-导出
	 */
	@RequestMapping("/exportExcelChannelEffect")
	public void exportExcelChannelEffect(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute OperateStatisticsModel searchBean) {
		try {
			String tradeSituationReportPath = ExportTemplateEnum.channelEffectReport.getPath();
			String tradeSituationReportValue = ExportTemplateEnum.channelEffectReport.getValue();
			if(ReportTypeEnum.weeks.getLabelKey().equals(searchBean.getReportType())){
				 tradeSituationReportPath = ExportTemplateEnum.channelEffectWeekReport.getPath();
				 tradeSituationReportValue = ExportTemplateEnum.channelEffectWeekReport.getValue();
			}else{
				 tradeSituationReportPath = ExportTemplateEnum.channelEffectReport.getPath();
				 tradeSituationReportValue = ExportTemplateEnum.channelEffectReport.getValue();
			}
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(tradeSituationReportPath)));
			// 2、需要导出的数据
			List<ChannelActiveEffectVO> recordList = operateStatisticsService.findChannelEffectList(searchBean);
			Integer total = recordList.size();
			if(ReportTypeEnum.weeks.getLabelKey().equals(searchBean.getReportType())){
				for (ChannelActiveEffectVO channelActiveEffectVO : recordList) {
					String execdate = channelActiveEffectVO.getDateTime();
					int weeks = DateUtil.getWeekNumber(DateUtil.stringToDate(execdate));
					channelActiveEffectVO.setWeeks(String.valueOf(weeks));
				}
			}
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<ChannelActiveEffectVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, ChannelActiveEffectVO param) {	
					if("deviceType".equals(fieldName)){
						if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.PC.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.PC.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.ANDROID.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.ANDROID.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.IOS.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.IOS.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.PCUI.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.PCUI.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.APP.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.APP.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.MOBILE.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.MOBILE.getValue();
						}
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(tradeSituationReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转获客质量（web、client）报表管理页面
	 */
	@RequestMapping(value = "/customerQualityWebPage", method = { RequestMethod.GET })
	public String customerQualityWebPage(HttpServletRequest request) {
		try {
			request.setAttribute("deviceTypeEnum", DeviceTypeEnum.getList5());	
			
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());			
			request.setAttribute("platformEnum", PlatformtypeEnum.getList());
			
			ChannelEntity channelEntity = new ChannelEntity();
			List<String> channelNameList = channelService.findChannelList(channelEntity); //渠道名称
			request.setAttribute("channelNameList", channelNameList);
			
			List<String> channelGroupList = channelService.findChannelGroupList(channelEntity); //渠道分组
			request.setAttribute("channelGroupList", channelGroupList);
			
			List<String> channelLevelList = channelService.findChannelLevelList(channelEntity); //渠道分级
			request.setAttribute("channelLevelList", channelLevelList);
			
			
			return "/operateStatistics/customerQuality/customerQualityWeb";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转获客质量（web、client）报表管理页面
	 */
	@RequestMapping(value = "/customerQualityClientPage", method = { RequestMethod.GET })
	public String customerQualityClientPage(HttpServletRequest request) {
		try {
			request.setAttribute("deviceTypeEnum", DeviceTypeEnum.getList4());	
			
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());			
			request.setAttribute("platformEnum", PlatformtypeEnum.getList());
			
			ChannelEntity channelEntity = new ChannelEntity();
			List<String> channelNameList = channelService.findChannelList(channelEntity); //渠道名称
			request.setAttribute("channelNameList", channelNameList);
			
			List<String> channelGroupList = channelService.findChannelGroupList(channelEntity); //渠道分组
			request.setAttribute("channelGroupList", channelGroupList);
			
			List<String> channelLevelList = channelService.findChannelLevelList(channelEntity); //渠道分级
			request.setAttribute("channelLevelList", channelLevelList);
			
			
			return "/operateStatistics/customerQuality/customerQualityClient";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 分页查询获客质量（web、client）报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findCustomerQualityPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<OperateStatisticsModel> findCustomerQualityPageList(HttpServletRequest request,
			@ModelAttribute OperateStatisticsModel searchBean) {
		try {
			return operateStatisticsService.findCustomerQualityPageList(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<OperateStatisticsModel>();
		}
	}
		
	/**
	 * 不分页查询获客质量（web、client）报表记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findCustomerQualityList", method = { RequestMethod.POST })
	@ResponseBody
	public List<CustomerQualityVO> findCustomerQualityList(HttpServletRequest request,
			@ModelAttribute OperateStatisticsModel searchBean) throws Exception {
		return operateStatisticsService.findCustomerQualityList(searchBean);
	}
	
	/**
	 * 获客质量（web、client）报表记录-导出
	 */
	@RequestMapping("/exportExcelCustomerQuality")
	public void exportExcelCustomerQuality(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute OperateStatisticsModel searchBean) {
		try {
			String tradeSituationReportPath = ExportTemplateEnum.customerQualityWebReport.getPath();
			String tradeSituationReportValue = ExportTemplateEnum.customerQualityWebReport.getValue();
			if(srcTypeEnum.WEB.getValue().equals(searchBean.getSrcType())){
				 if(ReportTypeEnum.weeks.getLabelKey().equals(searchBean.getReportType())){
					 tradeSituationReportPath = ExportTemplateEnum.customerQualityWebWeekReport.getPath();
					 tradeSituationReportValue = ExportTemplateEnum.customerQualityWebWeekReport.getValue();
				 }else{
					 tradeSituationReportPath = ExportTemplateEnum.customerQualityWebReport.getPath();
					 tradeSituationReportValue = ExportTemplateEnum.customerQualityWebReport.getValue();
				 }
			}
			
            if(srcTypeEnum.CLIENT.getValue().equals(searchBean.getSrcType())){
            	if(ReportTypeEnum.weeks.getLabelKey().equals(searchBean.getReportType())){
            		tradeSituationReportPath = ExportTemplateEnum.customerQualityClientWeekReport.getPath();
       			    tradeSituationReportValue = ExportTemplateEnum.customerQualityClientWeekReport.getValue();
            	}else{
            		tradeSituationReportPath = ExportTemplateEnum.customerQualityClientReport.getPath();
       			    tradeSituationReportValue = ExportTemplateEnum.customerQualityClientReport.getValue();
            	}
            	 
			}						
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(tradeSituationReportPath)));
			// 2、需要导出的数据
			List<CustomerQualityVO> recordList = operateStatisticsService.findCustomerQualityList(searchBean);
			Integer total = recordList.size();
			
			if(ReportTypeEnum.weeks.getLabelKey().equals(searchBean.getReportType())){
				for (CustomerQualityVO customerQualityVO : recordList) {
					String execdate = customerQualityVO.getDateTime();
					int weeks = DateUtil.getWeekNumber(DateUtil.stringToDate(execdate));
					customerQualityVO.setWeeks(String.valueOf(weeks));
				}
			}
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<CustomerQualityVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, CustomerQualityVO param) {	
					if("deviceType".equals(fieldName)){
						if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.PC.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.PC.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.ANDROID.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.ANDROID.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.IOS.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.IOS.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.PCUI.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.PCUI.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.APP.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.APP.getValue();
						}else if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && DeviceTypeEnum.MOBILE.getLabelKey().equals(String.valueOf(fieldValue))){
							return DeviceTypeEnum.MOBILE.getValue();
						}
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(tradeSituationReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}


}
