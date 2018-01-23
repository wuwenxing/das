package com.gw.das.web.controller.dataSourceList;

import java.io.File;
import java.util.Date;
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
import com.gw.das.common.enums.BehaviorTypeDataEnum;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.OperationDataTypeEnum;
import com.gw.das.common.enums.UserTypeDataEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.dataSourceList.bean.DataSourceListSearchModel;
import com.gw.das.dao.dataSourceList.bean.RoomDataSource;
import com.gw.das.dao.dataSourceList.bean.WebsiteBehaviorDataSource;
import com.gw.das.service.dataSourceList.DataSourceListService;
import com.gw.das.web.controller.system.BaseController;

/**
 * 数据源列表
 * 
 * @author darren
 *
 */
@Controller
@RequestMapping("/DataSourceListController")
public class DataSourceListController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceListController.class);

	@Autowired
	private DataSourceListService dataSourceListService;
	
	/**
	 * 跳转官网行为数据源管理页面
	 */
	@RequestMapping(value = "/websiteBehaviorDataSourcePage", method = { RequestMethod.GET })
	public String websiteBehaviorDataSourcePage(HttpServletRequest request) {
		try {
			String startTime = DateUtil.formatDateToString(DateUtil.getCurrentMonthStartTime(), "yyyy-MM-dd HH:mm:ss");
			String endTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
			endTime += " 23:59:59";
    		request.setAttribute("startTime", startTime);
    		request.setAttribute("endTime", endTime);
			request.setAttribute("behaviorTypeEnum", BehaviorTypeDataEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/dataSourceList/websiteBehaviorDataSource";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 官网行为数据源分页查询 -elasticsearch
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findWebsiteBehaviorDataSourcePageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DataSourceListSearchModel> findWebsiteBehaviorDataSourcePageList(HttpServletRequest request, @ModelAttribute DataSourceListSearchModel searchBean) {
		try {
			PageGrid<DataSourceListSearchModel> pageGrid = dataSourceListService.findWebsiteBehaviorDataSourcePageList(super.createPageGrid(request, searchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DataSourceListSearchModel>();
		}
	}
	
	/**
	 * 官网行为数据源报表导出 -elasticsearch
	 */
	@RequestMapping("/exportExcelWebsiteBehaviorDataSource")
	public void exportExcelWebsiteBehaviorDataSource(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DataSourceListSearchModel searchBean) {
		try {
			
			long start = System.currentTimeMillis();
			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.websiteBehaviorDataSource.getPath())));
			// 2、需要导出的数据
			List<WebsiteBehaviorDataSource> recordList = dataSourceListService.findWebsiteBehaviorDataSourceList(searchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<WebsiteBehaviorDataSource>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, WebsiteBehaviorDataSource param) {
					if("businessPlatform".equals(fieldName)){
						if(StringUtils.isBlank(String.valueOf(fieldValue))){
							return "";
						}else{
							if(1 == Integer.valueOf(String.valueOf(fieldValue))){
								return "外汇";
							}else if(2 == Integer.valueOf(String.valueOf(fieldValue))){
								return "贵金属";
							}else if(3 == Integer.valueOf(String.valueOf(fieldValue))){
								return "恒信";
							}else if(4 == Integer.valueOf(String.valueOf(fieldValue))){
								return "创富";
							}else if(5 == Integer.valueOf(String.valueOf(fieldValue))){
								return "烧麦";
							}
						}
					}
					
					if("platformType".equals(fieldName)){
						if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && 1 == Integer.valueOf(String.valueOf(fieldValue))){
							return "移动端";
						}else{
							return "PC";
						}
						
					}
					
					if("behaviorType".equals(fieldName)){
						if(StringUtils.isBlank(String.valueOf(fieldValue))){
							return "";
						}else{
							if(1 == Integer.valueOf(String.valueOf(fieldValue))){
								return "访问";
							}else if(2 == Integer.valueOf(String.valueOf(fieldValue))){
								return "咨询";
							}else if(3 == Integer.valueOf(String.valueOf(fieldValue))){
								return "模拟";
							}else if(4 == Integer.valueOf(String.valueOf(fieldValue))){
								return "真实";
							}else if(6 == Integer.valueOf(String.valueOf(fieldValue))){
								return "事件";
							}
						}
					}
					
					if("advisoryType".equals(fieldName)){
						if(StringUtils.isBlank(String.valueOf(fieldValue))){
							return "";
						}else{
							if(1 == Integer.valueOf(String.valueOf(fieldValue))){
								return "qq";
							}else if(2 == Integer.valueOf(String.valueOf(fieldValue))){
								return "live800";
							}
						}
					}
					
					if("visitTime".equals(fieldName)){
						if(StringUtils.isBlank(String.valueOf(fieldValue))){
							return "";
						}else{
							return DateUtil.DateString2formatString(String.valueOf(fieldValue));
						}
					}
					
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.websiteBehaviorDataSource.getValue(), request, response);
			builder.write(response.getOutputStream());
			
			long end = System.currentTimeMillis();
			logger.info("导出官网行为数据源统计Excel报表消耗时间:"+(end-start)/1000+"秒");
			
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转直播间数据源管理页面
	 */
	@RequestMapping(value = "/roomDataSourcePage", method = { RequestMethod.GET })
	public String roomDataSourcePage(HttpServletRequest request) {
		try {
			String startTime = DateUtil.formatDateToString(DateUtil.getCurrentMonthStartTime(), "yyyy-MM-dd HH:mm:ss");
			String endTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
			endTime += " 23:59:59";
    		request.setAttribute("startTime", startTime);
    		request.setAttribute("endTime", endTime);
			request.setAttribute("userTypeDataEnum", UserTypeDataEnum.getList());
			request.setAttribute("operationDataTypeEnum", OperationDataTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/dataSourceList/roomDataSource";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 直播间数据源管理分页查询 -elasticsearch
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findRoomDataSourcePageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DataSourceListSearchModel> findRoomDataSourcePageList(HttpServletRequest request, @ModelAttribute DataSourceListSearchModel searchBean) {
		try {
			PageGrid<DataSourceListSearchModel> pageGrid = dataSourceListService.findRoomDataSourcePageList(super.createPageGrid(request, searchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DataSourceListSearchModel>();
		}
	}
	
	/**
	 * 直播间数据源管理报表导出 -elasticsearch
	 */
	@RequestMapping("/exportExcelRoomDataSource")
	public void exportExcelRoomDataSource(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DataSourceListSearchModel searchBean) {
		try {
			
			long start = System.currentTimeMillis();
			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.roomDataSource.getPath())));
			// 2、需要导出的数据
			List<RoomDataSource> recordList = dataSourceListService.findRoomDataSourceList(searchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<RoomDataSource>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, RoomDataSource param) {
					if("businessPlatform".equals(fieldName)){
						if(StringUtils.isBlank(String.valueOf(fieldValue))){
							return "";
						}else{
							if(1 == Integer.valueOf(String.valueOf(fieldValue))){
								return "外汇";
							}else if(2 == Integer.valueOf(String.valueOf(fieldValue))){
								return "贵金属";
							}else if(3 == Integer.valueOf(String.valueOf(fieldValue))){
								return "恒信";
							}else if(4 == Integer.valueOf(String.valueOf(fieldValue))){
								return "创富";
							}else if(5 == Integer.valueOf(String.valueOf(fieldValue))){
								return "烧麦";
							}
						}
					}
					
					if("platformType".equals(fieldName)){
						if(StringUtils.isNotBlank(String.valueOf(fieldValue)) && 1 == Integer.valueOf(String.valueOf(fieldValue))){
							return "移动端";
						}else{
							return "PC";
						}
					}
					
					if("userType".equals(fieldName)){
						if(StringUtils.isBlank(String.valueOf(fieldValue))){
							return "";
						}else{
							if(1 == Integer.valueOf(String.valueOf(fieldValue))){
								return "游客";
							}else if(2 == Integer.valueOf(String.valueOf(fieldValue))){
								return "注册";
							}else if(3 == Integer.valueOf(String.valueOf(fieldValue))){
								return "模拟用户";
							}else if(4 == Integer.valueOf(String.valueOf(fieldValue))){
								return "真实A用户";
							}else if(5 == Integer.valueOf(String.valueOf(fieldValue))){
								return "真实N用户";
							}else if(6 == Integer.valueOf(String.valueOf(fieldValue))){
								return "VIP用户";
							}else if(7 == Integer.valueOf(String.valueOf(fieldValue))){
								return "分析师";
							}else if(8 == Integer.valueOf(String.valueOf(fieldValue))){
								return "管理员";
							}else if(9 == Integer.valueOf(String.valueOf(fieldValue))){
								return "客服";
							}
						}
					}
					
					if("operationType".equals(fieldName)){
						if(StringUtils.isBlank(String.valueOf(fieldValue))){
							return "";
						}else{
							if(1 == Integer.valueOf(String.valueOf(fieldValue))){
								return "上线";
							}else if(2 == Integer.valueOf(String.valueOf(fieldValue))){
								return "公聊";
							}else if(3 == Integer.valueOf(String.valueOf(fieldValue))){
								return "注册";
							}else if(4 == Integer.valueOf(String.valueOf(fieldValue))){
								return "登录";
							}else if(5 == Integer.valueOf(String.valueOf(fieldValue))){
								return "退出";
							}else if(6 == Integer.valueOf(String.valueOf(fieldValue))){
								return "下线";
							}else if(7 == Integer.valueOf(String.valueOf(fieldValue))){
								return "视频";
							}else if(8 == Integer.valueOf(String.valueOf(fieldValue))){
								return "私聊";
							}
						}
					}
					
					if("operationTime".equals(fieldName)){
						if(StringUtils.isBlank(String.valueOf(fieldValue))){
							return "";
						}else{
							return DateUtil.DateString2formatString(String.valueOf(fieldValue));
						}
					}
					
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.roomDataSource.getValue(), request, response);
			builder.write(response.getOutputStream());
			
			long end = System.currentTimeMillis();
			logger.info("导出直播间数据源统计Excel报表消耗时间:"+(end-start)/1000+"秒");
			
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
}
