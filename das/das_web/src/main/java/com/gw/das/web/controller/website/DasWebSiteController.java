package com.gw.das.web.controller.website;

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
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.dao.website.bean.DasBehaviorEventChannelEffect;
import com.gw.das.dao.website.bean.DasBehaviorEventChannelEffectSearchModel;
import com.gw.das.dao.website.bean.DasWebLandingpageDetail;
import com.gw.das.dao.website.bean.DasWebLandingpageDetailSearchModel;
import com.gw.das.service.website.DasWebSiteService;
import com.gw.das.web.controller.system.BaseController;

/**
 * Landingpage
 * @author wayne
 */
@Controller
@RequestMapping("/DasWebSiteController")
public class DasWebSiteController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasWebSiteController.class);

	@Autowired
	private DasWebSiteService dasWebSiteService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/dasWebLandingpageDetailPage", method = { RequestMethod.GET })
	public String dasWebLandingpageDetailPage(HttpServletRequest request) {
		try {
			request.setAttribute("deviceTypeEnum", DeviceTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/website/dasWebLandingpageDetail";
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
	@RequestMapping(value = "/dasWebLandingpageDetailPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasWebLandingpageDetailSearchModel> dasWebLandingpageDetailPageList(HttpServletRequest request,
			@ModelAttribute DasWebLandingpageDetailSearchModel searchModel) {
		try {
			PageGrid<DasWebLandingpageDetailSearchModel> pageGrid = dasWebSiteService.dasWebLandingpageDetailPageList(super.createPageGrid(request, searchModel));
			for(Object obj: pageGrid.getRows()){
				DasWebLandingpageDetail record = (DasWebLandingpageDetail)obj;
				record.setDeviceType(DeviceTypeEnum.format(record.getDeviceType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasWebLandingpageDetailSearchModel>();
		}
	}
	
	/**
	 * Landingpage-导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasWebLandingpageDetailSearchModel searchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.dasWebLandingpageDetail.getPath())));
			// 2、需要导出的数据
			List<DasWebLandingpageDetail> recordList = dasWebSiteService.dasWebLandingpageDetailList(searchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasWebLandingpageDetail>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasWebLandingpageDetail param) {
					if("deviceType".equals(fieldName)){
						if(null != fieldValue){
							return DeviceTypeEnum.format(fieldValue.toString());
						}else{
							return "";
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.dasWebLandingpageDetail.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/dasBehaviorEventChannelEffectPage", method = { RequestMethod.GET })
	public String dasBehaviorEventChannelEffectPage(HttpServletRequest request) {
		try {
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());
			request.setAttribute("deviceTypeEnum", DeviceTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/website/dasBehaviorEventChannelEffect";
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
	@RequestMapping(value = "/dasBehaviorEventChannelEffectPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasBehaviorEventChannelEffectSearchModel> dasBehaviorEventChannelEffectPageList(HttpServletRequest request,
			@ModelAttribute DasBehaviorEventChannelEffectSearchModel searchModel) {
		try {
			PageGrid<DasBehaviorEventChannelEffectSearchModel> pageGrid = dasWebSiteService.dasBehaviorEventChannelEffectPageList(super.createPageGrid(request, searchModel));
			for(Object obj: pageGrid.getRows()){
				DasBehaviorEventChannelEffect record = (DasBehaviorEventChannelEffect)obj;
				record.setDeviceType(DeviceTypeEnum.format(record.getDeviceType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasBehaviorEventChannelEffectSearchModel>();
		}
	}
	
	/**
	 * 导出
	 */
	@RequestMapping("/eventChannelEffectExportExcel")
	public void eventChannelEffectExportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasBehaviorEventChannelEffectSearchModel searchModel) {
		try {
			
			String path = ExportTemplateEnum.dasBehaviorEventChannelEffect.getPath();
			String value = ExportTemplateEnum.dasBehaviorEventChannelEffect.getValue();
			if(ReportTypeEnum.weeks.getLabelKey().equals(searchModel.getReportType())){
				path = ExportTemplateEnum.dasBehaviorEventChannelEffectWeek.getPath();
				value = ExportTemplateEnum.dasBehaviorEventChannelEffectWeek.getValue();
			}
			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(new File(request.getServletContext().getRealPath(path)));
			// 2、需要导出的数据
			List<DasBehaviorEventChannelEffect> recordList = dasWebSiteService.dasBehaviorEventChannelEffectList(searchModel);
			
			if(ReportTypeEnum.weeks.getLabelKey().equals(searchModel.getReportType())){
				for (DasBehaviorEventChannelEffect dasBehaviorEventChannelEffect : recordList) {
					String execdate = dasBehaviorEventChannelEffect.getDateTime();
					int weeks = DateUtil.getWeekNumber(DateUtil.stringToDate(execdate));
					dasBehaviorEventChannelEffect.setWeeks(String.valueOf(weeks));
				}
			}
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasBehaviorEventChannelEffect>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasBehaviorEventChannelEffect param) {
					if("deviceType".equals(fieldName)){
						if(null != fieldValue){
							return DeviceTypeEnum.format(fieldValue.toString());
						}else{
							return "";
						}
					}else if("demoRate".equals(fieldName) 
							|| "realRate".equals(fieldName)
							|| "activeRate".equals(fieldName)){
						if(null != fieldValue){
							return NumberUtil.m2(Double.parseDouble(fieldValue.toString())*100) + "%";
						}else{
							return "0%";
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
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
}
