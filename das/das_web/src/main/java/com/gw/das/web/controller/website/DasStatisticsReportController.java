package com.gw.das.web.controller.website;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.enums.RoomUserTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.custConsultation.entity.CustConsultationEntity;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.dao.website.bean.DasStatisticsDailyChannel;
import com.gw.das.dao.website.bean.DasStatisticsReport;
import com.gw.das.dao.website.bean.DasStatisticsReportSearchBean;
import com.gw.das.service.custConsultation.CustConsultationService;
import com.gw.das.service.market.ChannelService;
import com.gw.das.service.website.DasStatisticsReportService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/DasStatisticsReport")
public class DasStatisticsReportController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasStatisticsReportController.class);

	@Autowired
	private DasStatisticsReportService dasStatisticsReportService;
	
	@Autowired
	private CustConsultationService custConsultationService;
	
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 官网统计-（日/月）统计-跳转管理页面
	 */
	@RequestMapping(value = "/statisticsPage", method = { RequestMethod.GET })
	public String statisticsPage(HttpServletRequest request) {
		try {
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList3());
			
			List<String> channelList = channelService.findListGroupName(ChannelTypeEnum.webSite);
			request.setAttribute("channelList", channelList);
			
			return "/dasStatisticsReport/dasStatisticsReport";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 根据是否付费查询
	 */
	@RequestMapping(value = "/findChannelByisPay", method = { RequestMethod.POST })
	@ResponseBody
	public List<String> findChannelByisPay(HttpServletRequest request) {
		try {
			ChannelEntity channelEntity = new ChannelEntity();
			String channeltype = request.getParameter("channeltype");
			if(StringUtils.isNotBlank(channeltype) && !"-1".equals(channeltype)){						
				channelEntity.setIsPay(Long.valueOf(channeltype));
			}
			return channelService.findChannelList(channelEntity);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<String>();
		}
	}

	/**
	 * 官网统计  -- （日/月）分页查询
	 */
	@RequestMapping(value = "/findStatisticsPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasStatisticsReportSearchBean> findStatisticsPage(HttpServletRequest request,
			@ModelAttribute DasStatisticsReportSearchBean searchBean) {
		try {
			PageGrid<DasStatisticsReportSearchBean> pageGrid = dasStatisticsReportService.findStatisticsReportPage(super.createPageGrid(request, searchBean));
			
			List<CustConsultationEntity> custConsultationEntityList = custConsultationService.findByBusinessplatform(searchBean.getReportType());
			// 合计
			DasStatisticsReport footer = new DasStatisticsReport();
			for(Object obj: pageGrid.getRows()){
				String data = "";
				if("days".equals(searchBean.getReportType())){
				    data = ((DasStatisticsReport)obj).getDateDay();
				}else{
					data = ((DasStatisticsReport)obj).getDateMonth();
				}				
				if(null != custConsultationEntityList && custConsultationEntityList.size() >0){
					for (CustConsultationEntity custConsultationEntity : custConsultationEntityList)
					{
						if(custConsultationEntity.getConsulttationTime().equals(data)){
							((DasStatisticsReport)obj).setNewCustConsultation(custConsultationEntity.getNewConsulttationNum());
							((DasStatisticsReport)obj).setOldCustConsultation(custConsultationEntity.getOldConsulttationNum());
						}
					}
				}
				
	            if("days".equals(searchBean.getReportType())){//只统计日的，月的不统计
	            	DasStatisticsReport record = (DasStatisticsReport)obj;
					
					// 合计
					footer.setAllVisit(footer.getAllVisit() + record.getAllVisit());
					footer.setAllAdvisory(footer.getAllAdvisory() + record.getAllAdvisory());
					footer.setNewCustConsultation(footer.getNewCustConsultation() + record.getNewCustConsultation());
					footer.setOldCustConsultation(footer.getOldCustConsultation() + record.getOldCustConsultation());
					footer.setAllReal(footer.getAllReal() + record.getAllReal());
					footer.setAllDemo(footer.getAllDemo() + record.getAllDemo());
					footer.setAllDeposit(footer.getAllDeposit() + record.getAllDeposit());
					
					footer.setDevpcVisit(footer.getDevpcVisit() + record.getDevpcVisit());
					footer.setDevpcAdvisory(footer.getDevpcAdvisory() + record.getDevpcAdvisory());
					footer.setDevpcReal(footer.getDevpcReal() + record.getDevpcReal());
					footer.setDevpcDemo(footer.getDevpcDemo() + record.getDevpcDemo());
					footer.setDevpcDeposit(footer.getDevpcDeposit() + record.getDevpcDeposit());
					
					footer.setDevAndroidVisit(footer.getDevAndroidVisit() + record.getDevAndroidVisit());
					footer.setDevAndroidAdvisory(footer.getDevAndroidAdvisory() + record.getDevAndroidAdvisory());
					footer.setDevAndroidReal(footer.getDevAndroidReal() + record.getDevAndroidReal());
					footer.setDevAndroidDemo(footer.getDevAndroidDemo() + record.getDevAndroidDemo());
					footer.setDevAndroidDeposit(footer.getDevAndroidDeposit() + record.getDevAndroidDeposit());
					
					footer.setDevIosVisit(footer.getDevIosVisit() + record.getDevIosVisit());
					footer.setDevIosAdvisory(footer.getDevIosAdvisory() + record.getDevIosAdvisory());
					footer.setDevIosReal(footer.getDevIosReal() + record.getDevIosReal());
					footer.setDevIosDemo(footer.getDevIosDemo() + record.getDevIosDemo());
					footer.setDevIosDeposit(footer.getDevIosDeposit() + record.getDevIosDeposit());
					
					footer.setChannelPayVisit(footer.getChannelPayVisit() + record.getChannelPayVisit());
					footer.setChannelPayAdvisory(footer.getChannelPayAdvisory() + record.getChannelPayAdvisory());
					footer.setChannelPayReal(footer.getChannelPayReal() + record.getChannelPayReal());
					footer.setChannelPayDemo(footer.getChannelPayDemo() + record.getChannelPayDemo());
					footer.setChannelPayDeposit(footer.getChannelPayDeposit() + record.getChannelPayDeposit());
					
					footer.setChannelFreeVisit(footer.getChannelFreeVisit() + record.getChannelFreeVisit());
					footer.setChannelFreeAdvisory(footer.getChannelFreeAdvisory() + record.getChannelFreeAdvisory());
					footer.setChannelFreeReal(footer.getChannelFreeReal() + record.getChannelFreeReal());
					footer.setChannelFreeDemo(footer.getChannelFreeDemo() + record.getChannelFreeDemo());
					footer.setChannelFreeDeposit(footer.getChannelFreeDeposit() + record.getChannelFreeDeposit());
					
					footer.setChannelOtherVisit(footer.getChannelOtherVisit() + record.getChannelOtherVisit());
					footer.setChannelOtherAdvisory(footer.getChannelOtherAdvisory() + record.getChannelOtherAdvisory());
					footer.setChannelOtherReal(footer.getChannelOtherReal() + record.getChannelOtherReal());
					footer.setChannelOtherDemo(footer.getChannelOtherDemo() + record.getChannelOtherDemo());
					footer.setChannelOtherDeposit(footer.getChannelOtherDeposit() + record.getChannelOtherDeposit());
	            }
			}
			if("days".equals(searchBean.getReportType())){//只统计日的，月的不统计
				List<DasStatisticsReport> list = new ArrayList<DasStatisticsReport>();
				list.add(footer);
				pageGrid.setFooter(list); 
			}

			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasStatisticsReportSearchBean>();
		}
	}
	
	/**
	 * 行为统计列表-导出
	 */
	@RequestMapping("/exportStatistics")
	public void exportStatistics(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasStatisticsReportSearchBean searchBean) {
		try {
			String statisticsMonthReportPath = ExportTemplateEnum.statisticsDaysReport.getPath();
			String statisticsMonthReportValue = ExportTemplateEnum.statisticsDaysReport.getValue();
			if("months".equals(searchBean.getReportType())){
				 statisticsMonthReportPath = ExportTemplateEnum.statisticsMonthReport.getPath();
				 statisticsMonthReportValue = ExportTemplateEnum.statisticsMonthReport.getValue();
			}
			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(statisticsMonthReportPath)));
			// 2、需要导出的数据
			List<DasStatisticsReport> recordList = dasStatisticsReportService.findStatisticsReportList(searchBean);
			
			final List<CustConsultationEntity> custConsultationEntityList = custConsultationService.findByBusinessplatform(searchBean.getReportType());
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasStatisticsReport>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasStatisticsReport param) {						
					for(CustConsultationEntity custConsultationEntity:custConsultationEntityList){
						if("newCustConsultation".equals(fieldName) && custConsultationEntity.getConsulttationTime().equals(fieldValue)){
							return custConsultationEntity.getNewConsulttationNum();
						}
						if("oldCustConsultation".equals(fieldName) && custConsultationEntity.getConsulttationTime().equals(fieldValue)){
							return custConsultationEntity.getOldConsulttationNum();
						}
					}
					return fieldValue;
				}
			});

			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(statisticsMonthReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 日报渠道统计  -- （日/月）分页查询
	 */
	@RequestMapping(value = "/findDailyChannelPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasStatisticsReportSearchBean> findDailyChannelPage(HttpServletRequest request,
			@ModelAttribute DasStatisticsReportSearchBean searchBean) {
		try {
			PageGrid<DasStatisticsReportSearchBean> pageGrid = dasStatisticsReportService.findDailyChannelPage(super.createPageGrid(request, searchBean));
			if(null != pageGrid.getRows() && pageGrid.getRows().size() >0){
				DasStatisticsDailyChannel footer = new DasStatisticsDailyChannel();
				//footer.setDateDay("小计:");
				footer.setChannel("-");
				footer.setOnwvisit("-");
				footer.setOnwadvisory("-");
				footer.setOnwreal("-");
				footer.setOnwdemo("-");
				footer.setOnwdeposit("-");
				
				footer.setOnmvisit("-");
				footer.setOnmadvisory("-");
				footer.setOnmreal("-");
				footer.setOnmdemo("-");
				footer.setOnmdeposit("-");
				NumberFormat df = NumberFormat.getNumberInstance();
	            df.setMaximumFractionDigits(6);
				for(Object obj: pageGrid.getRows()){
					DasStatisticsDailyChannel record = (DasStatisticsDailyChannel)obj;
					
					footer.setVisit(footer.getVisit() + record.getVisit());
					footer.setAdvisory(footer.getAdvisory() + record.getAdvisory());
					footer.setReal(footer.getReal() + record.getReal());
					footer.setDemo(footer.getDemo() + record.getDemo());
					footer.setDeposit(footer.getDeposit() + record.getDeposit());	
					
					record.setOnwvisit(df.format(Double.valueOf(record.getOnwvisit()) * 100)+"%");
					record.setOnwadvisory(df.format(Double.valueOf(record.getOnwadvisory()) * 100)+"%");
					record.setOnwreal(df.format(Double.valueOf(record.getOnwreal()) * 100)+"%");
					record.setOnwdemo(df.format(Double.valueOf(record.getOnwdemo()) * 100)+"%");
					record.setOnwdeposit(df.format(Double.valueOf(record.getOnwdeposit()) * 100)+"%");

					record.setOnmvisit(df.format(Double.valueOf(record.getOnmvisit()) * 100)+"%");
					record.setOnmadvisory(df.format(Double.valueOf(record.getOnmadvisory()) * 100)+"%");
					record.setOnmreal(df.format(Double.valueOf(record.getOnmreal()) * 100)+"%");
					record.setOnmdemo(df.format(Double.valueOf(record.getOnmdemo()) * 100)+"%");
					record.setOnmdeposit(df.format(Double.valueOf(record.getOnmdeposit()) * 100)+"%");			
				}
				List<DasStatisticsDailyChannel> list = new ArrayList<DasStatisticsDailyChannel>();
				list.add(footer);
				pageGrid.setFooter(list);
			}
			
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasStatisticsReportSearchBean>();
		}
	}
	
	/**
	 * 日报渠道统计列表-导出
	 */
	@RequestMapping("/exportDailyChannel")
	public void exportDailyChannel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasStatisticsReportSearchBean searchBean) {
		try {
			String statisticsDailyChannelPath = ExportTemplateEnum.statisticsDailyChannel.getPath();
			String statisticsDailyChannelValue = ExportTemplateEnum.statisticsDailyChannel.getValue();					
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(statisticsDailyChannelPath)));
			// 2、需要导出的数据
			List<DasStatisticsDailyChannel> recordList = dasStatisticsReportService.findDailyChannelList(searchBean);
			NumberFormat df = NumberFormat.getNumberInstance();
            df.setMaximumFractionDigits(6);
			for (DasStatisticsDailyChannel record : recordList) {
				record.setOnwvisit(df.format(Double.valueOf(record.getOnwvisit()) * 100)+"%");
				record.setOnwadvisory(df.format(Double.valueOf(record.getOnwadvisory()) * 100)+"%");
				record.setOnwreal(df.format(Double.valueOf(record.getOnwreal()) * 100)+"%");
				record.setOnwdemo(df.format(Double.valueOf(record.getOnwdemo()) * 100)+"%");
				record.setOnwdeposit(df.format(Double.valueOf(record.getOnwdeposit()) * 100)+"%");

				record.setOnmvisit(df.format(Double.valueOf(record.getOnmvisit()) * 100)+"%");
				record.setOnmadvisory(df.format(Double.valueOf(record.getOnmadvisory()) * 100)+"%");
				record.setOnmreal(df.format(Double.valueOf(record.getOnmreal()) * 100)+"%");
				record.setOnmdemo(df.format(Double.valueOf(record.getOnmdemo()) * 100)+"%");
				record.setOnmdeposit(df.format(Double.valueOf(record.getOnmdeposit()) * 100)+"%");	
				if(StringUtils.isBlank(searchBean.getPlatformType())){
					record.setPlatformtype("全部");
				}
			}
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasStatisticsDailyChannel>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasStatisticsDailyChannel param) {						
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
	 * 官网统计-（日/月）统计-跳转管理页面
	 */
	@RequestMapping(value = "/statisticsHxPage", method = { RequestMethod.GET })
	public String statisticsHxPage(HttpServletRequest request) {
		try {
    		request.setAttribute("roomUserTypeEnum", RoomUserTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList3());
			
			List<String> channelList = channelService.findListGroupName(ChannelTypeEnum.webSite);
			request.setAttribute("channelList", channelList);
			
			return "/dasStatisticsReport/dasStatisticsHxReport";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	

}
