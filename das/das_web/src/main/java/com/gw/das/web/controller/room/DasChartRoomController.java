package com.gw.das.web.controller.room;

import java.io.File;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.BehaviorTypeEnum;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.enums.RoomSpeakTypeEnum;
import com.gw.das.common.enums.RoomUserTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.dao.room.bean.DasChartFlowDetail;
import com.gw.das.dao.room.bean.DasChartFlowDetailSearchBean;
import com.gw.das.dao.room.bean.DasChartFlowDetailUrl;
import com.gw.das.dao.room.bean.DasChartFlowDetailUrlSearchBean;
import com.gw.das.dao.room.bean.DasChartFlowStatistics;
import com.gw.das.dao.room.bean.DasChartFlowStatisticsAverage;
import com.gw.das.dao.room.bean.DasChartFlowStatisticsSearchBean;
import com.gw.das.dao.room.bean.DasChartRoomDetail;
import com.gw.das.dao.room.bean.DasChartRoomDetailSum;
import com.gw.das.dao.room.bean.DasChartRoomOnlineHours;
import com.gw.das.dao.room.bean.DasChartRoomRegTouristUserStatistics;
import com.gw.das.dao.room.bean.DasChartRoomSearchModel;
import com.gw.das.dao.room.bean.DasChartRoomSpeakStatistics;
import com.gw.das.dao.room.bean.DasChartRoomStatistics;
import com.gw.das.dao.room.bean.DasChartRoomUserTypeStatistics;
import com.gw.das.dao.room.bean.DasRoomLoginStatistics;
import com.gw.das.dao.room.bean.DasRoomLoginStatisticsSearchBean;
import com.gw.das.service.market.ChannelService;
import com.gw.das.service.room.DasChartRoomService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/DasChartRoomController")
public class DasChartRoomController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasChartRoomController.class);

	@Autowired
	private DasChartRoomService dasChartRoomService;
	
	@Autowired
	private ChannelService channelService;

	/**
	 * 恒信直播间登陆统计-跳转管理页面
	 */
	@RequestMapping(value = "/loginStatisticsPage", method = { RequestMethod.GET })
	public String loginStatisticsPage(HttpServletRequest request) {
		try {
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			return "/room/loginStatistics/loginStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 恒信直播间登陆统计-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findLoginStatisticsPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasRoomLoginStatisticsSearchBean> findLoginStatisticsPage(HttpServletRequest request,
			@ModelAttribute DasRoomLoginStatisticsSearchBean searchBean) {
		try {
			PageGrid<DasRoomLoginStatisticsSearchBean> pageGrid = dasChartRoomService.findLoginStatisticsPage(super.createPageGrid(request, searchBean));
			for(Object obj: pageGrid.getRows()){
				DasRoomLoginStatistics record = (DasRoomLoginStatistics)obj;
				if(StringUtils.isBlank(record.getUserType())){
					record.setUserType("1");
				}
				record.setUserType(RoomUserTypeEnum.format(record.getUserType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasRoomLoginStatisticsSearchBean>();
		}
	}
	
	/**
	 * 恒信直播间登陆统计-导出
	 */
	@RequestMapping("/loginStatisticsExportExcel")
	public void loginStatisticsExportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasRoomLoginStatisticsSearchBean searchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.loginStatistics.getPath())));
			// 2、需要导出的数据
			List<DasRoomLoginStatistics> recordList = dasChartRoomService.findLoginStatisticsList(searchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasRoomLoginStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasRoomLoginStatistics param) {
					DasRoomLoginStatistics record = (DasRoomLoginStatistics)param;
					if(StringUtils.isBlank(record.getUserType())){
						record.setUserType("1");
					}
					record.setUserType(RoomUserTypeEnum.format(record.getUserType()));
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.loginStatistics.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 访客列表-跳转管理页面
	 */
	@RequestMapping(value = "/visitorPage", method = { RequestMethod.GET })
	public String visitorPage(HttpServletRequest request) {
		try {
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/room/dasChartRoom/dasChartRoomVisitor";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 访客列表-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findVisitorPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findVisitorPage(HttpServletRequest request,
			@ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findVisitorPage(super.createPageGrid(request, searchBean));
			for(Object obj: pageGrid.getRows()){
				DasChartRoomDetailSum record = (DasChartRoomDetailSum)obj;
				if(StringUtils.isBlank(record.getUserType())){
					record.setUserType("1");
				}
				record.setUserType(RoomUserTypeEnum.format(record.getUserType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}

	/**
	 * 访客列表-导出
	 */
	@RequestMapping("/visitorExportExcel")
	public void visitorExportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartRoomSearchModel dasChartRoomSearchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.visitorList.getPath())));
			// 2、需要导出的数据
			List<DasChartRoomDetailSum> recordList = dasChartRoomService.findVisitorList(dasChartRoomSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartRoomDetailSum>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartRoomDetailSum param) {
					if("userType".equals(fieldName)){
						if(null == fieldValue || StringUtils.isBlank(fieldValue+"")){
							// 当用户级别为空，默认为游客
							return RoomUserTypeEnum.tourist.getValue();
						}else{
							return RoomUserTypeEnum.format(param.getUserType());
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.visitorList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 访客明细列表-跳转管理页面
	 */
	@RequestMapping(value = "/visitorDetailPage", method = { RequestMethod.GET })
	public String visitorDetailPage(HttpServletRequest request) {
		try {
    		String userTel = request.getParameter("userTel");
    		if(StringUtils.isNotBlank(userTel)){
    			request.setAttribute("userTel", request.getParameter("userTel"));
    		}else{
    			request.setAttribute("touristId", request.getParameter("touristId"));
    		}
    		// 对rowKey参数解码
    		String rowKey = request.getParameter("rowKey");
    		rowKey = java.net.URLDecoder.decode(rowKey, "utf-8");
    		request.setAttribute("rowKey", rowKey);
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/room/dasChartRoom/dasChartRoomVisitorDetail";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 访客明细列表-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findVisitorDetailPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findVisitorDetailPage(HttpServletRequest request,
			@ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findVisitorDetailPage(super.createPageGrid(request, searchBean));
			for(Object obj: pageGrid.getRows()){
				DasChartRoomDetail record = (DasChartRoomDetail)obj;
				if(StringUtils.isBlank(record.getUserType())){
					record.setUserType("1");
				}
				record.setUserType(RoomUserTypeEnum.format(record.getUserType()));
				record.setUserTel(StringUtil.formatPhone(record.getUserTel()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}

	/**
	 * 访客明细列表-导出
	 */
	@RequestMapping("/visitorDetailExportExcel")
	public void visitorDetailExportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartRoomSearchModel dasChartRoomSearchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.visitorDetailList.getPath())));
			// 2、需要导出的数据
			List<DasChartRoomDetail> recordList = dasChartRoomService.findVisitorDetailList(dasChartRoomSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartRoomDetail>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartRoomDetail param) {
					if("userType".equals(fieldName)){
						if(null == fieldValue || StringUtils.isBlank(fieldValue+"")){
							// 当用户级别为空，默认为游客
							return RoomUserTypeEnum.tourist.getValue();
						}else{
							return RoomUserTypeEnum.format(param.getUserType());
						}
					}if("platformType".equals(fieldName)){
						if(null != fieldValue){
							if("0".equals(fieldValue+"")){
								return ClientEnum.pc.getValue();
							}else if("1".equals(fieldValue+"")){
								return ClientEnum.mobile.getValue();
							}
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.visitorDetailList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 行为统计
	 * 直播间-（时段/日/周/月）统计-跳转管理页面
	 */
	@RequestMapping(value = "/statisticsPage", method = { RequestMethod.GET })
	public String statisticsPage(HttpServletRequest request) {
		try {
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList());
			return "/room/dasChartRoomStatistics/dasChartRoomStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 行为统计
	 * 直播间-（时段/日/周/月）统计-分页查询
	 */
	@RequestMapping(value = "/findStatisticsPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findStatisticsPage(HttpServletRequest request,
			@ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findStatisticsPage(super.createPageGrid(request, searchBean));
			// 小计与总计
			DasChartRoomStatistics footer = new DasChartRoomStatistics();
			for(Object obj: pageGrid.getRows()){
				DasChartRoomStatistics record = (DasChartRoomStatistics)obj;
				// 小计与总计
				footer.setPublicSpeakCount(footer.getPublicSpeakCount() + record.getPublicSpeakCount());
				footer.setPublicSpeakNumber(footer.getPublicSpeakNumber() + record.getPublicSpeakNumber());
				footer.setPrivateSpeakCount(footer.getPrivateSpeakCount() + record.getPrivateSpeakCount());
				footer.setPrivateSpeakNumber(footer.getPrivateSpeakNumber() + record.getPrivateSpeakNumber());
				footer.setVisitCount(footer.getVisitCount() + record.getVisitCount());
				footer.setVisitNumber(footer.getVisitNumber() + record.getVisitNumber());
				footer.setLoginCount(footer.getLoginCount() + record.getLoginCount());
				footer.setLoginNumber(footer.getLoginNumber() + record.getLoginNumber());
			}
			List<DasChartRoomStatistics> list = new ArrayList<DasChartRoomStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}
	

    /**
	 * 行为统计列表-导出
	 */
	@RequestMapping("/exportStatistics")
	public void exportStatistics(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartRoomSearchModel dasChartRoomSearchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.statistics.getPath())));
			// 2、需要导出的数据
			List<DasChartRoomStatistics> recordList = dasChartRoomService.findStatisticsList(dasChartRoomSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartRoomStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartRoomStatistics param) {						
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.statistics.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 发言天数列表-跳转管理页面
	 */
	@RequestMapping(value = "/statisticsSpeakdaysPage", method = { RequestMethod.GET })
	public String statisticsSpeakdaysPage(HttpServletRequest request) {
		try {
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("roomSpeakTypeEnum", RoomSpeakTypeEnum.getList());
			return "/room/dasChartRoomStatisticsSpeakdays/dasChartRoomStatisticsSpeakdays";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 发言天数列表-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findStatisticsSpeakdaysPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findStatisticsSpeakdaysPage(HttpServletRequest request,
			@ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findStatisticsSpeakPage(super.createPageGrid(request, searchBean), "1");
			// 小计与总计
			DasChartRoomSpeakStatistics footer = new DasChartRoomSpeakStatistics();
			for(Object obj: pageGrid.getRows()){
				DasChartRoomSpeakStatistics record = (DasChartRoomSpeakStatistics)obj;
				// 小计与总计
				footer.setU0(footer.getU0() + record.getU0());
				footer.setU1(footer.getU1() + record.getU1());
				footer.setU2(footer.getU2() + record.getU2());
				footer.setU3(footer.getU3() + record.getU3());
				footer.setU4(footer.getU4() + record.getU4());
				footer.setU5(footer.getU5() + record.getU5());
				footer.setU6(footer.getU6() + record.getU6());
			}
			List<DasChartRoomSpeakStatistics> list = new ArrayList<DasChartRoomSpeakStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}
	

	/**
	 * 发言天数/次数列表-导出
	 */
	@RequestMapping("/exportExcelStatisticsSpeak")
	public void exportExcelStatisticsSpeak(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartRoomSearchModel dasChartRoomSearchModel) {
		try {
			String TemplatePath = "";
			String codedFileName = "";
			if ("1".equals(dasChartRoomSearchModel.getSpeakTypeStatistics())) {
				 TemplatePath = ExportTemplateEnum.statisticsSpeakdays.getPath();
				 codedFileName = ExportTemplateEnum.statisticsSpeakdays.getValue();
			}
			if ("2".equals(dasChartRoomSearchModel.getSpeakTypeStatistics())) {
				 TemplatePath = ExportTemplateEnum.statisticsSpeakCounts.getPath();
				 codedFileName = ExportTemplateEnum.statisticsSpeakCounts.getValue();
			}
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(TemplatePath)));
			// 2、需要导出的数据
			List<DasChartRoomSpeakStatistics> recordList = dasChartRoomService.findStatisticsSpeakList(dasChartRoomSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartRoomSpeakStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartRoomSpeakStatistics param) {
					if("dateTime".equals(fieldName)){
						return DateUtil.formatDateToString((Date)fieldValue);
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(codedFileName, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 发言次数列表-跳转管理页面
	 */
	@RequestMapping(value = "/statisticsSpeakcountsPage", method = { RequestMethod.GET })
	public String statisticsSpeakcountsPage(HttpServletRequest request) {
		try {
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("roomSpeakTypeEnum", RoomSpeakTypeEnum.getList());
			return "/room/dasChartRoomStatisticsSpeakcounts/dasChartRoomStatisticsSpeakcounts";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 发言次数列表-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findStatisticsSpeakcountsPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findStatisticsSpeakcountsPage(HttpServletRequest request,
			@ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findStatisticsSpeakPage(super.createPageGrid(request, searchBean), "2");
			// 小计与总计
			DasChartRoomSpeakStatistics footer = new DasChartRoomSpeakStatistics();
			for(Object obj: pageGrid.getRows()){
				DasChartRoomSpeakStatistics record = (DasChartRoomSpeakStatistics)obj;
				// 小计与总计
				footer.setU0(footer.getU0() + record.getU0());
				footer.setU1(footer.getU1() + record.getU1());
				footer.setU2(footer.getU2() + record.getU2());
				footer.setU3(footer.getU3() + record.getU3());
				footer.setU4(footer.getU4() + record.getU4());
				footer.setU5(footer.getU5() + record.getU5());
				footer.setU6(footer.getU6() + record.getU6());
			}
			List<DasChartRoomSpeakStatistics> list = new ArrayList<DasChartRoomSpeakStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}
	
	/**
	 * 登录天数统计-跳转管理页面
	 */
	@RequestMapping(value = "/statisticsLogindaysPage", method = { RequestMethod.GET })
	public String statisticsLogindaysPage(HttpServletRequest request) {
		try {
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("roomSpeakTypeEnum", RoomSpeakTypeEnum.getList());
			return "/room/dasChartRoomStatisticsLogindays/dasChartRoomStatisticsLogindays";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 登录天数统计-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findStatisticsLogindaysPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findStatisticsLogindaysPage(HttpServletRequest request,
			@ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findStatisticsLogindaysPage(super.createPageGrid(request, searchBean));
			// 小计与总计
			DasChartRoomSpeakStatistics footer = new DasChartRoomSpeakStatistics();
			for(Object obj: pageGrid.getRows()){
				DasChartRoomSpeakStatistics record = (DasChartRoomSpeakStatistics)obj;
				// 小计与总计
				footer.setU0(footer.getU0() + record.getU0());
				footer.setU1(footer.getU1() + record.getU1());
				footer.setU2(footer.getU2() + record.getU2());
				footer.setU3(footer.getU3() + record.getU3());
				footer.setU4(footer.getU4() + record.getU4());
				footer.setU5(footer.getU5() + record.getU5());
				footer.setU6(footer.getU6() + record.getU6());
			}
			List<DasChartRoomSpeakStatistics> list = new ArrayList<DasChartRoomSpeakStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}
	
	/**
	 * 登录天数统计-导出
	 */
	@RequestMapping("/exportExcelStatisticsLogindays")
	public void exportExcelStatisticsLogindays(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartRoomSearchModel dasChartRoomSearchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.statisticsLogindays.getPath())));
			// 2、需要导出的数据
			List<DasChartRoomSpeakStatistics> recordList = dasChartRoomService.findStatisticsLogindaysList(dasChartRoomSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartRoomSpeakStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartRoomSpeakStatistics param) {
					if("dateTime".equals(fieldName)){
						return DateUtil.formatDateToString((Date)fieldValue);
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.statisticsLogindays.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 在线时长列表-跳转管理页面
	 */
	@RequestMapping(value = "/statisticsOnlineHoursPage", method = { RequestMethod.GET })
	public String statisticsOnlineHoursPage(HttpServletRequest request) {
		try {
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/room/dasChartRoomStatisticsOnlineHours/dasChartRoomStatisticsOnlineHours";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 在线时长列表-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findStatisticsOnlineHoursPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findStatisticsOnlineHoursPage(HttpServletRequest request,
			@ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findStatisticsOnlineHoursPage(super.createPageGrid(request, searchBean));
			// 小计与总计
			DasChartRoomOnlineHours footer = new DasChartRoomOnlineHours();
			for(Object obj: pageGrid.getRows()){
				DasChartRoomOnlineHours record = (DasChartRoomOnlineHours)obj;
				// 小计与总计
				footer.setSeconds1(footer.getSeconds1() + record.getSeconds1());
				footer.setSeconds2(footer.getSeconds2() + record.getSeconds2());
				footer.setSeconds3(footer.getSeconds3() + record.getSeconds3());
				footer.setSeconds4(footer.getSeconds4() + record.getSeconds4());
				footer.setSeconds5(footer.getSeconds5() + record.getSeconds5());
				footer.setSeconds6(footer.getSeconds6() + record.getSeconds6());
			}
			List<DasChartRoomOnlineHours> list = new ArrayList<DasChartRoomOnlineHours>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}
	
	/**
	 * 在线时长列表-导出
	 */
	@RequestMapping("/exportStatisticsOnlineHours")
	public void exportStatisticsOnlineHours(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartRoomSearchModel dasChartRoomSearchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.statisticsOnlineHours.getPath())));
			// 2、需要导出的数据
			List<DasChartRoomOnlineHours> recordList = dasChartRoomService.findStatisticsOnlineHoursKList(dasChartRoomSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartRoomOnlineHours>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartRoomOnlineHours param) {
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.statisticsOnlineHours.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 登录\访问\发言 数据列表
	 * 根据用户类型进行登录\访问\发言-（日/周/月）统计-跳转管理页面
	 * 对应type参数为1\2\3
	 */
	@RequestMapping(value = "/statisticsByUserTypePage/{type}", method = { RequestMethod.GET })
	public String statisticsByUserTypePage(HttpServletRequest request, @PathVariable String type) {
		try {
			request.setAttribute("roomSpeakTypeEnum", RoomSpeakTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());
			if("1".equals(type)){
				return "/room/statisticsLogin/statisticsLogin";
			}else if("2".equals(type)){
				return "/room/statisticsVisitor/statisticsVisitor";
			}else if("3".equals(type)){
				return "/room/statisticsSpeak/statisticsSpeak";
			}
			return "";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 登录\访问\发言 数据列表
	 * 根据用户类型进行登录\访问\发言-（日/周/月）统计-分页查询 对应type参数为1\2\3
	 */
	@RequestMapping(value = "/findStatisticsByUserTypePage/{type}", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findStatisticsByUserTypePage(HttpServletRequest request, 
			@PathVariable String type, @ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			searchBean.setUserTypeStatistics(type);
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findStatisticsByUserTypePage(super.createPageGrid(request, searchBean));
			// 小计与总计
			DasChartRoomUserTypeStatistics footer = new DasChartRoomUserTypeStatistics();
			for(Object obj: pageGrid.getRows()){
				DasChartRoomUserTypeStatistics record = (DasChartRoomUserTypeStatistics)obj;
				// 小计与总计
				footer.setVipCounts(footer.getVipCounts() + record.getVipCounts());
				footer.setVipNumbers(footer.getVipNumbers() + record.getVipNumbers());
				footer.setRealACounts(footer.getRealACounts() + record.getRealACounts());
				footer.setRealANumbers(footer.getRealANumbers() + record.getRealANumbers());
				footer.setRealNCounts(footer.getRealNCounts() + record.getRealNCounts());
				footer.setRealNNumbers(footer.getRealNNumbers() + record.getRealNNumbers());
				footer.setDemoCounts(footer.getDemoCounts() + record.getDemoCounts());
				footer.setDemoNumbers(footer.getDemoNumbers() + record.getDemoNumbers());
				footer.setRegistCounts(footer.getRegistCounts() + record.getRegistCounts());
				footer.setRegistNumbers(footer.getRegistNumbers() + record.getRegistNumbers());
				footer.setTouristCounts(footer.getTouristCounts() + record.getTouristCounts());
				footer.setTouristNumbers(footer.getTouristNumbers() + record.getTouristNumbers());
				footer.setAnalystCounts(footer.getAnalystCounts() + record.getAnalystCounts());
				footer.setAnalystNumbers(footer.getAnalystNumbers() + record.getAnalystNumbers());
				footer.setAdminCounts(footer.getAdminCounts() + record.getAdminCounts());
				footer.setAdminNumbers(footer.getAdminNumbers() + record.getAdminNumbers());
				footer.setcServiceCounts(footer.getcServiceCounts() + record.getcServiceCounts());
				footer.setcServiceNumbers(footer.getcServiceNumbers() + record.getcServiceNumbers());
			}
			List<DasChartRoomUserTypeStatistics> list = new ArrayList<DasChartRoomUserTypeStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}
	
	/**
	 * 登录\访问\发言 数据列表
	 * 根据用户类型进行登录\访问\发言-（日/周/月）统计-分页查询 对应type参数为1\2\3  导出
	 */
	@RequestMapping("/exportExcelStatisticsByUserType")
	public void exportExcelStatisticsByUserType(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartRoomSearchModel dasChartRoomSearchModel) {
		try {
			String TemplatePath = "";
			String codedFileName = "";
			if ("1".equals(dasChartRoomSearchModel.getUserTypeStatistics())) {
				 TemplatePath = ExportTemplateEnum.statisticsLogin.getPath();
				 codedFileName = ExportTemplateEnum.statisticsLogin.getValue();
			}
			if ("2".equals(dasChartRoomSearchModel.getUserTypeStatistics())) {
				 TemplatePath = ExportTemplateEnum.statisticsVisitor.getPath();
				 codedFileName = ExportTemplateEnum.statisticsVisitor.getValue();
			}
			if ("3".equals(dasChartRoomSearchModel.getUserTypeStatistics())) {
				 TemplatePath = ExportTemplateEnum.statisticsSpeak.getPath();
				 codedFileName = ExportTemplateEnum.statisticsSpeak.getValue();
			}
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(TemplatePath)));
			// 2、需要导出的数据
			List<DasChartRoomUserTypeStatistics> recordList = dasChartRoomService.findStatisticsByUserTypeList(dasChartRoomSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartRoomUserTypeStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartRoomUserTypeStatistics param) {
					if("dateTime".equals(fieldName)){
						return DateUtil.formatDateToString((Date)fieldValue);
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(codedFileName, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 注册用户列表-跳转管理页面
	 */
	@RequestMapping(value = "/statisticsRegTouristUserPage", method = { RequestMethod.GET })
	public String statisticsRegTouristUserPage(HttpServletRequest request) {
		try {
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());
			return "/room/dasChartRoomStatisticsRegTouristUser/dasChartRoomStatisticsRegTouristUser";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 注册用户列表-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findStatisticsRegTouristUserPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findStatisticsRegTouristUserPage(HttpServletRequest request,
			@ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findStatisticsRegTouristUserPage(super.createPageGrid(request, searchBean));
			// 小计与总计
			DasChartRoomRegTouristUserStatistics footer = new DasChartRoomRegTouristUserStatistics();
			for(Object obj: pageGrid.getRows()){
				DasChartRoomRegTouristUserStatistics record = (DasChartRoomRegTouristUserStatistics)obj;
				// 小计与总计
				footer.setVipNumbers(footer.getVipNumbers() + record.getVipNumbers());
				footer.setRealANumbers(footer.getRealANumbers() + record.getRealANumbers());
				footer.setRealNNumbers(footer.getRealNNumbers() + record.getRealNNumbers());
				footer.setDemoNumbers(footer.getDemoNumbers() + record.getDemoNumbers());
				footer.setRegistNumbers(footer.getRegistNumbers() + record.getRegistNumbers());
				footer.setTouristNumbers(footer.getTouristNumbers() + record.getTouristNumbers());
			}
			List<DasChartRoomRegTouristUserStatistics> list = new ArrayList<DasChartRoomRegTouristUserStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}
	
	/**
	 * 注册用户列表-导出
	 */
	@RequestMapping("/exportStatisticsRegTouristUser")
	public void exportStatisticsRegTouristUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartRoomSearchModel dasChartRoomSearchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.statisticsRegTouristUser.getPath())));
			// 2、需要导出的数据
			List<DasChartRoomRegTouristUserStatistics> recordList = dasChartRoomService.findStatisticsRegTouristUserList(dasChartRoomSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartRoomRegTouristUserStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartRoomRegTouristUserStatistics param) {	
					if("dateTime".equals(fieldName)){
						return DateUtil.formatDateToString((Date)fieldValue);
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.statisticsRegTouristUser.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 访问次数列表-跳转管理页面
	 */
	@RequestMapping(value = "/statisticsVisitcountsPage", method = { RequestMethod.GET })
	public String statisticsVisitcountsPage(HttpServletRequest request) {
		try {
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());
			return "/room/dasChartRoomStatisticsVisitcounts/dasChartRoomStatisticsVisitcounts";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 访问次数列表-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findStatisticsVisitcountsPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartRoomSearchModel> findStatisticsVisitcountsPage(HttpServletRequest request,
			@ModelAttribute DasChartRoomSearchModel searchBean) {
		try {
			PageGrid<DasChartRoomSearchModel> pageGrid = dasChartRoomService.findStatisticsVisitcountsPage(super.createPageGrid(request, searchBean));
			// 小计与总计
			DasChartRoomSpeakStatistics footer = new DasChartRoomSpeakStatistics();
			for(Object obj: pageGrid.getRows()){
				DasChartRoomSpeakStatistics record = (DasChartRoomSpeakStatistics)obj;
				// 小计与总计
				footer.setU0(footer.getU0() + record.getU0());
				footer.setU1(footer.getU1() + record.getU1());
				footer.setU2(footer.getU2() + record.getU2());
				footer.setU3(footer.getU3() + record.getU3());
				footer.setU4(footer.getU4() + record.getU4());
				footer.setU5(footer.getU5() + record.getU5());
				footer.setU6(footer.getU6() + record.getU6());
			}
			List<DasChartRoomSpeakStatistics> list = new ArrayList<DasChartRoomSpeakStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartRoomSearchModel>();
		}
	}
	
	/**
	 * 访问次数列表-导出
	 */
	@RequestMapping("/exportStatisticsVisitcounts")
	public void exportStatisticsVisitcounts(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartRoomSearchModel dasChartRoomSearchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.statisticsVisitcounts.getPath())));
			// 2、需要导出的数据
			List<DasChartRoomSpeakStatistics> recordList = dasChartRoomService.findStatisticsVisitcountsList(dasChartRoomSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartRoomSpeakStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartRoomSpeakStatistics param) {	
					if("dateTime".equals(fieldName)){
						return DateUtil.formatDateToString((Date)fieldValue);
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.statisticsVisitcounts.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 直播间开户统计-跳转管理页面
	 */
	@RequestMapping(value = "/behaviorCountpage", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("devicetypeEnum", DeviceTypeEnum.getList());
			return "/room/dasChartRoomStatisticsBehaviorCount/dasChartRoomStatisticsBehaviorCount";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 直播间开户统计-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/behaviorCountPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartFlowStatisticsSearchBean> pageList(HttpServletRequest request,
			@ModelAttribute DasChartFlowStatisticsSearchBean dasChartFlowStatisticsSearchBean) {
		try {
			dasChartFlowStatisticsSearchBean.setSearchType("behavior");
			PageGrid<DasChartFlowStatisticsSearchBean> pageGrid = dasChartRoomService.findBehaviorCountPageList(super.createPageGrid(request, dasChartFlowStatisticsSearchBean));
			// 小计与总计
			DasChartFlowStatistics footer = new DasChartFlowStatistics();
			for(Object obj: pageGrid.getRows()){
				DasChartFlowStatistics record = (DasChartFlowStatistics)obj;
				// 小计与总计
				footer.setVisitCount(footer.getVisitCount() + record.getVisitCount());
				footer.setAdvisoryCountQQ(footer.getAdvisoryCountQQ() + record.getAdvisoryCountQQ());
				footer.setAdvisoryCountLIVE800(footer.getAdvisoryCountLIVE800() + record.getAdvisoryCountLIVE800());
				footer.setDemoCount(footer.getDemoCount() + record.getDemoCount());
				footer.setRealCount(footer.getRealCount() + record.getRealCount());
				footer.setDepositCount(footer.getDepositCount() + record.getDepositCount());
				footer.setDevicecount(footer.getDevicecount() + record.getDevicecount());
			}
			List<DasChartFlowStatistics> list = new ArrayList<DasChartFlowStatistics>();
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartFlowStatisticsSearchBean>();
		}
	}

	/**
	 * 直播间开户统计-不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/behaviorCountfindList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasChartFlowStatistics> findList(HttpServletRequest request,
			@ModelAttribute DasChartFlowStatisticsSearchBean dasChartFlowStatisticsSearchBean) {
		try {
			// 排序
			String sort = request.getParameter("sort");
			String order = request.getParameter("order");
			dasChartFlowStatisticsSearchBean.setSortName(sort);
			dasChartFlowStatisticsSearchBean.setSortDirection(order);
			dasChartFlowStatisticsSearchBean.setSearchType("behavior");
			List<DasChartFlowStatistics> recordList = dasChartRoomService.findBehaviorCountList(dasChartFlowStatisticsSearchBean);
			return recordList;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasChartFlowStatistics>();
		}
	}
	
	/**
	 * 直播间开户统计-导出
	 */
	@RequestMapping("/exportExcelBehaviorCount")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartFlowStatisticsSearchBean dasChartFlowStatisticsSearchBean) {
		try {
			dasChartFlowStatisticsSearchBean.setSearchType("behavior");
			dasChartFlowStatisticsSearchBean.setSortName(request.getParameter("sort"));
			dasChartFlowStatisticsSearchBean.setSortDirection(request.getParameter("order"));
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.roomBehaviorCount.getPath())));
			// 2、需要导出的数据
			List<DasChartFlowStatistics> recordList = dasChartRoomService.findBehaviorCountList(dasChartFlowStatisticsSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartFlowStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartFlowStatistics param) {
					if("devicetype".equals(fieldName)){
						if(null == fieldValue){
							return "(全部)";
						}else{
							if("0".equals(String.valueOf(fieldValue))){
								return "PC";
							}else if("1".equals(String.valueOf(fieldValue))){
								return "Android";
							}else if("2".equals(String.valueOf(fieldValue))){
								return "IOS";
							}else{
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
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.roomBehaviorCount.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 直播间来源媒介统计-跳转管理页面
	 */
	@RequestMapping(value = "/roomSourceCountPage", method = { RequestMethod.GET })
	public String pageMedia(HttpServletRequest request) {
		try {
			String dataTime = request.getParameter("dataTime");
	    	if(StringUtils.isNotBlank(dataTime)){
	    		request.setAttribute("startTime", dataTime);
	    		request.setAttribute("endTime", dataTime);
	    	}else{
				String startTime = DateUtil.formatDateToString(DateUtil.addMonths(new Date(),-1), "yyyy-MM-dd");
				String endTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
	    		request.setAttribute("startTime", startTime);
	    		request.setAttribute("endTime", endTime);
	    	}
	    	
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
	    	String devicetype = request.getParameter("devicetype");
	    	if(StringUtils.isNotBlank(devicetype)){
	    		if(DeviceTypeEnum.PC.getLabelKey().equals(devicetype)){
	    			request.setAttribute("devicetype",DeviceTypeEnum.PC.getLabelKey());
	    		}
                if(DeviceTypeEnum.ANDROID.getLabelKey().equals(devicetype)){
                	request.setAttribute("devicetype",DeviceTypeEnum.ANDROID.getLabelKey());
	    		}
                if(DeviceTypeEnum.IOS.getLabelKey().equals(devicetype)){
                	request.setAttribute("devicetype",DeviceTypeEnum.IOS.getLabelKey());
	    		}
	    	}
	    	request.setAttribute("devicetypeEnumEnum", DeviceTypeEnum.getList());
	    	String channel = request.getParameter("channel");
	    	request.setAttribute("channel", channel);
			List<String> channelList = channelService.findListGroupName(ChannelTypeEnum.room);
			request.setAttribute("channelList", channelList);
			return "/room/dasChartRoomStatisticsSourceCount/dasChartRoomStatisticsSourceCount";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 直播间来源媒介统计-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageListRoomSourceCount", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartFlowStatisticsSearchBean> pageListMedia(HttpServletRequest request,
			@ModelAttribute DasChartFlowStatisticsSearchBean dasChartFlowStatisticsSearchBean) {
		try {
			if(StringUtils.isNotBlank(dasChartFlowStatisticsSearchBean.getChannelIds())){
				List<ChannelEntity> channelEntityList = channelService.findListByName(ChannelTypeEnum.webSite, dasChartFlowStatisticsSearchBean.getChannelIds());
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
				if (StringUtils.isNotBlank(dasChartFlowStatisticsSearchBean.getUtmcsr())) {
					if(utmcsrList.length() > 0){					
						utmcsrList.append(",").append(dasChartFlowStatisticsSearchBean.getUtmcsr());
					}else{
						utmcsrList.append(dasChartFlowStatisticsSearchBean.getUtmcsr());
					}
				}
				if (StringUtils.isNotBlank(dasChartFlowStatisticsSearchBean.getUtmcmd())) {
					if(utmcmdList.length() > 0){					
						utmcmdList.append(",").append(dasChartFlowStatisticsSearchBean.getUtmcmd());
					}else{
						utmcmdList.append(dasChartFlowStatisticsSearchBean.getUtmcmd());
					}
				}
				dasChartFlowStatisticsSearchBean.setUtmcmdList(utmcmdList.toString());
				dasChartFlowStatisticsSearchBean.setUtmcsrList(utmcsrList.toString());
			}
			
			dasChartFlowStatisticsSearchBean.setSearchType("media");
			PageGrid<DasChartFlowStatisticsSearchBean> pageGrid;
			if(StringUtils.isNotBlank(dasChartFlowStatisticsSearchBean.getStartTimeCompare()) && StringUtils.isNotBlank(dasChartFlowStatisticsSearchBean.getEndTimeCompare())
					&& (!dasChartFlowStatisticsSearchBean.getStartTime().equals(dasChartFlowStatisticsSearchBean.getStartTimeCompare()) || !dasChartFlowStatisticsSearchBean.getEndTime().equals(dasChartFlowStatisticsSearchBean.getEndTimeCompare()))){
				pageGrid = dasChartRoomService.findRoomSourceCountTreePageList(super.createPageGrid(request, dasChartFlowStatisticsSearchBean));
			}else{
				pageGrid = dasChartRoomService.findRoomSourceCountPageList(super.createPageGrid(request, dasChartFlowStatisticsSearchBean));
				// 小计与总计
				DasChartFlowStatistics footer = new DasChartFlowStatistics();
				for(Object obj: pageGrid.getRows()){
					DasChartFlowStatistics record = (DasChartFlowStatistics)obj;
					// 小计与总计
					footer.setVisitCount(footer.getVisitCount() + record.getVisitCount());
					footer.setAdvisoryCountQQ(footer.getAdvisoryCountQQ() + record.getAdvisoryCountQQ());
					footer.setAdvisoryCountLIVE800(footer.getAdvisoryCountLIVE800() + record.getAdvisoryCountLIVE800());
					footer.setDemoCount(footer.getDemoCount() + record.getDemoCount());
					footer.setRealCount(footer.getRealCount() + record.getRealCount());
					footer.setDepositCount(footer.getDepositCount() + record.getDepositCount());
					footer.setDevicecount(footer.getDevicecount() + record.getDevicecount());
				}
				List<DasChartFlowStatistics> list = new ArrayList<DasChartFlowStatistics>();
				list.add(footer);
				pageGrid.setFooter(list);
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartFlowStatisticsSearchBean>();
		}
	}
	
	/**
	 * 直播间来源媒介统计-导出
	 */
	@RequestMapping("/exportExcelRoomSourceCount")
	public void exportExcelMedia(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartFlowStatisticsSearchBean dasChartFlowStatisticsSearchBean) {
		try {
			dasChartFlowStatisticsSearchBean.setSearchType("media");
			dasChartFlowStatisticsSearchBean.setSortName(request.getParameter("sort"));
			dasChartFlowStatisticsSearchBean.setSortDirection(request.getParameter("order"));
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.roomSourceMediumCount.getPath())));
			// 2、需要导出的数据
			List<DasChartFlowStatistics> recordList = dasChartRoomService.findRoomSourceCountList(dasChartFlowStatisticsSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartFlowStatistics>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartFlowStatistics param) {
					if("devicetype".equals(fieldName)){
						if(null == fieldValue){
							return "(全部)";
						}else{
							if(DeviceTypeEnum.PC.getLabelKey().equals(String.valueOf(fieldValue))){
								return "PC";
							}else if(DeviceTypeEnum.ANDROID.getLabelKey().equals(String.valueOf(fieldValue))){
								return "Android";
							}else if(DeviceTypeEnum.IOS.getLabelKey().equals(String.valueOf(fieldValue))){
								return "IOS";
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
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.roomSourceMediumCount.getValue(), request, response);
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
			request.setAttribute("devicetype", request.getParameter("devicetype"));
			request.setAttribute("behaviorType", request.getParameter("behaviorType"));
			return "/room/dasChartRoomStatisticsBehaviorCount/dasChartStatisticsAverage";
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
	public PageGrid<DasChartFlowStatisticsSearchBean> pageListAverage(HttpServletRequest request,
			@ModelAttribute DasChartFlowStatisticsSearchBean dasChartFlowStatisticsSearchBean) {
		try {
			PageGrid<DasChartFlowStatisticsSearchBean> pageGrid = dasChartRoomService.findAveragePageList(super.createPageGrid(request, dasChartFlowStatisticsSearchBean));
			// 小计与总计
			DasChartFlowStatisticsAverage footer = new DasChartFlowStatisticsAverage();
			for(Object obj: pageGrid.getRows()){
				DasChartFlowStatisticsAverage record = (DasChartFlowStatisticsAverage)obj;
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
				
				if(null != footer.getDeviceAvgCount()){
					if(null != record.getDeviceAvgCount()){
						footer.setDeviceAvgCount(footer.getDeviceAvgCount() + record.getDeviceAvgCount());
					}
				}else{
					footer.setDeviceAvgCount(record.getDeviceAvgCount());
				}
				
			}
			List<DasChartFlowStatisticsAverage> list = new ArrayList<DasChartFlowStatisticsAverage>();
			// 对footer平均值四舍五入处理
			if(null != footer.getLastMonthAvgCount()){
				footer.setLastMonthAvgCount(NumberUtil.m2(footer.getLastMonthAvgCount()));
			}
			list.add(footer);
			pageGrid.setFooter(list);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartFlowStatisticsSearchBean>();
		}
	}
	
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcelAverage")
	public void exportExcelAverage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartFlowStatisticsSearchBean dasChartFlowStatisticsSearchBean) {
		try {
			dasChartFlowStatisticsSearchBean.setSortName(request.getParameter("sort"));
			dasChartFlowStatisticsSearchBean.setSortDirection(request.getParameter("order"));
			String excelPath = ExportTemplateEnum.roomBehaviorOpenAccountCount.getPath();
			String excelValue = ExportTemplateEnum.roomBehaviorOpenAccountCount.getValue();
			
			if("3".equals(dasChartFlowStatisticsSearchBean.getBehaviorType())){
				excelPath = ExportTemplateEnum.roomBehaviorOpenAccountCountDemo.getPath();
				excelValue = ExportTemplateEnum.roomBehaviorOpenAccountCountDemo.getValue();
			}
			if("4".equals(dasChartFlowStatisticsSearchBean.getBehaviorType())){
				excelPath = ExportTemplateEnum.roomBehaviorOpenAccountCountReal.getPath();
				excelValue = ExportTemplateEnum.roomBehaviorOpenAccountCountReal.getValue();
			}
			if("5".equals(dasChartFlowStatisticsSearchBean.getBehaviorType())){
				excelPath = ExportTemplateEnum.roomBehaviorOpenAccountCountDeposit.getPath();
				excelValue = ExportTemplateEnum.roomBehaviorOpenAccountCountDeposit.getValue();
			}
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(excelPath)));
			// 2、需要导出的数据
			List<DasChartFlowStatisticsAverage> recordList = dasChartRoomService.findAverageList(dasChartFlowStatisticsSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartFlowStatisticsAverage>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartFlowStatisticsAverage param) {
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
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/pageRoomBehavior", method = { RequestMethod.GET })
	public String pageByRoomStatistics(HttpServletRequest request) {
		try {
			
			String dataTime = request.getParameter("dataTime");
	    	if(StringUtils.isNotBlank(dataTime)){
	    		String startTime = dataTime + " 00:00:00";
	    		request.setAttribute("startTime", startTime);
	    	}
	    	if(StringUtils.isNotBlank(dataTime)){
	    		String endTime = dataTime + " 23:59:59";
	    		request.setAttribute("endTime", endTime);
	    	}
	    	
	    	String behaviorType = request.getParameter("behaviorType");
	    	if(StringUtils.isNotBlank(dataTime)){
	    		if("3".equals(behaviorType)){
	    			behaviorType = BehaviorTypeEnum.demo.getLabelKey();
	    		}else if("4".equals(behaviorType)){
	    			behaviorType = BehaviorTypeEnum.real.getLabelKey();
	    		}else if("5".equals(behaviorType)){
	    			behaviorType = BehaviorTypeEnum.depesit.getLabelKey();
	    		}
				request.setAttribute("behaviorType", behaviorType);
	    	}

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
	    	request.setAttribute("devicetype", request.getParameter("devicetype"));
			request.setAttribute("behaviorTypeEnum", BehaviorTypeEnum.getList("dasUserInfo"));
			request.setAttribute("devicetypeEnumEnum", DeviceTypeEnum.getList());
			return "/room/behavior/behavior";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/behaviorpage", method = { RequestMethod.GET })
	public String behaviorpage(HttpServletRequest request) {
		try {
			String startTime = DateUtil.formatDateToString(DateUtil.addHours(new Date(), -4), "yyyy-MM-dd HH");
			String endTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
			startTime += ":00:00";
			endTime += " 23:59:59";
    		request.setAttribute("startTime", startTime);
    		request.setAttribute("endTime", endTime);
			request.setAttribute("behaviorTypeEnum", BehaviorTypeEnum.getList());
			request.setAttribute("devicetypeEnumEnum", DeviceTypeEnum.getList());
			return "/room/behavior/behavior";
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
	@RequestMapping(value = "/findBehaviorPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartFlowDetailSearchBean> findBehaviorPageList(HttpServletRequest request,
			@ModelAttribute DasChartFlowDetailSearchBean dasUserInfoSeachBean) {
		try {
			PageGrid<DasChartFlowDetailSearchBean> pageGrid = dasChartRoomService.findBehaviorPageList(super.createPageGrid(request, dasUserInfoSeachBean));
			for(Object obj: pageGrid.getRows()){
				DasChartFlowDetail record = (DasChartFlowDetail)obj;
				if("1".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.visit.getValue());
				}else if("2".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.live.getValue());
				}else if("3".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.demo.getValue());
				}else if("4".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.real.getValue());
				}else if("5".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.depesit.getValue());
				}
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartFlowDetailSearchBean>();
		}
	}
	
	/**
	 * 不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findBehaviorList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasChartFlowDetail> findBehaviorList(HttpServletRequest request,
			@ModelAttribute DasChartFlowDetailSearchBean dasUserInfoSeachBean) {
		try {
			List<DasChartFlowDetail> list = dasChartRoomService.findBehaviorList(dasUserInfoSeachBean);
			for(DasChartFlowDetail obj: list){
				DasChartFlowDetail record = (DasChartFlowDetail)obj;
				if("1".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.visit.getValue());
				}else if("2".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.live.getValue());
				}else if("3".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.demo.getValue());
				}else if("4".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.real.getValue());
				}else if("5".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.depesit.getValue());
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasChartFlowDetail>();
		}
	}
	
	/**
	 * 官网行为详细-导出
	 */
	@RequestMapping("/exportExcelBehavior")
	public void exportExcelBehavior(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartFlowDetailSearchBean dasChartFlowDetailSearchBean) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.roomDetailSummaryInfo.getPath())));
			// 2、需要导出的数据
			List<DasChartFlowDetail> recordList = dasChartRoomService.findBehaviorList(dasChartFlowDetailSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartFlowDetail>() {
				String behaviorDetail = "";
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartFlowDetail param) {
					if("behaviorType".equals(fieldName)){						
						if(null != fieldValue){
							if("3".equals(fieldValue+"") || "4".equals(fieldValue+"") || "5".equals(fieldValue+"")){
		 						return behaviorDetail;
		 					}else{
		 						if("1".equals(fieldValue+"")){
									return BehaviorTypeEnum.visit.getValue();
								}else if("2".equals(fieldValue+"")){
									return BehaviorTypeEnum.live.getValue();
								}else if("3".equals(fieldValue+"")){
									return BehaviorTypeEnum.demo.getValue() + "(" + param.getBehaviorDetail() +")";
								}else if("4".equals(fieldValue+"")){
									return BehaviorTypeEnum.real.getValue() + "(" + param.getBehaviorDetail() +")";
								}else if("5".equals(fieldValue+"")){
									return BehaviorTypeEnum.depesit.getValue() + "(" + param.getBehaviorDetail() +")";
								}
		 					}
						} 					
					}
					if("behaviorDetail".equals(fieldName)){
						behaviorDetail = String.valueOf(fieldValue);
					}
					if("devicetype".equals(fieldName)){
						if(null == fieldValue){
							return "";
						}else{
							if(DeviceTypeEnum.PC.getLabelKey().equals(String.valueOf(fieldValue))){
								return "PC";
							}else if(DeviceTypeEnum.ANDROID.getLabelKey().equals(String.valueOf(fieldValue))){
								return "Android";
							}else if(DeviceTypeEnum.IOS.getLabelKey().equals(String.valueOf(fieldValue))){
								return "IOS";
							}else{
								return "全部";
							}
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.roomDetailSummaryInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/flowDetailUrlPage", method = { RequestMethod.GET })
	public String dasFlowDetailUrlPage(HttpServletRequest request) {
		try {
			request.setAttribute("formatTime", request.getParameter("formatTime"));
			request.setAttribute("flowDetailId", request.getParameter("flowDetailId"));
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("devicetype", request.getParameter("devicetype"));
			request.setAttribute("utmcmd", request.getParameter("utmcmd"));
			request.setAttribute("utmcsr", request.getParameter("utmcsr"));
			request.setAttribute("startTime", request.getParameter("startTime"));
			request.setAttribute("endTime", request.getParameter("endTime"));
			return "/room/behavior/dasFlowDetailUrl";
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
	@RequestMapping(value = "/flowDetailUrlPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasChartFlowDetailUrlSearchBean> flowDetailUrlPageList(HttpServletRequest request,
			@ModelAttribute DasChartFlowDetailUrlSearchBean dasChartFlowDetailUrlSearchBean) {
		try {
			PageGrid<DasChartFlowDetailUrlSearchBean> pageGrid = dasChartRoomService.findFlowDetailUrlPageList(super.createPageGrid(request, dasChartFlowDetailUrlSearchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasChartFlowDetailUrlSearchBean>();
		}
	}
	
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcelFlowDetailUrl")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasChartFlowDetailUrlSearchBean dasFlowAttributionSearchBean) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.visitDetailInfo.getPath())));
			// 2、需要导出的数据
			List<DasChartFlowDetailUrl> recordList = dasChartRoomService.findFlowDetailUrlList(dasFlowAttributionSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasChartFlowDetailUrl>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasChartFlowDetailUrl param) {
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.visitDetailInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	
}
