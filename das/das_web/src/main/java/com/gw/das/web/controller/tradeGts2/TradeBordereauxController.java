package com.gw.das.web.controller.tradeGts2;

import java.io.File;
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

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.PlatformtypeEnum;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.dao.trade.bean.AccountchannelVO;
import com.gw.das.dao.trade.bean.DailyreportVO;
import com.gw.das.dao.trade.bean.DealcategoryVO;
import com.gw.das.dao.trade.bean.DealchannelVO;
import com.gw.das.dao.trade.bean.DealprofitdetailMT4AndGts2VO;
import com.gw.das.dao.trade.bean.DealprofitdetailUseraAndDealamountVO;
import com.gw.das.dao.trade.bean.DealprofitdetailVO;
import com.gw.das.dao.trade.bean.DealprofithourVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.dao.trade.bean.TradeSituationVO;
import com.gw.das.service.market.ChannelService;
import com.gw.das.service.tradeGts2.tradeBordereaux.AccountchannelService;
import com.gw.das.service.tradeGts2.tradeBordereaux.DailyreportService;
import com.gw.das.service.tradeGts2.tradeBordereaux.DealchannelService;
import com.gw.das.service.tradeGts2.tradeBordereaux.DealprofitdetailService;
import com.gw.das.service.tradeGts2.tradeBordereaux.DealprofithourService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/tradeBordereauxController")
public class TradeBordereauxController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(TradeBordereauxController.class);

	@Autowired
	private DailyreportService dailyreportService;
	
	@Autowired
	private DealchannelService dealchannelService;
	
	@Autowired
	private DealprofithourService dealprofithourService;
	
	@Autowired
	private DealprofitdetailService dealprofitdetailService;
	
	@Autowired
	private AccountchannelService accountchannelService;
	
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 跳转双周报表管理页面
	 */
	@RequestMapping(value = "/biweeklyReport", method = { RequestMethod.GET })
	public String biweeklyReport(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/tradebiweeklyReport/tradebiweeklyReport";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转交易人次图表管理页面
	 */
	@RequestMapping(value = "/dealprofitdetailUseramountPage", method = { RequestMethod.GET })
	public String dealprofitdetailUseramountPage(HttpServletRequest request) {
		try {
			return "/trade/gts2/dealprofitdetailUseramount/dealprofitdetailUseramountStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转交易次数图表管理页面
	 */
	@RequestMapping(value = "/dealprofitdetailDealamountPage", method = { RequestMethod.GET })
	public String dealprofitdetailDealamountPage(HttpServletRequest request) {
		try {
			return "/trade/gts2/dealprofitdetailDealamount/dealprofitdetailDealamountStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询交易人数/次数报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealprofitdetailUseraAndDealamountPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findDealprofitdetailUseraAndDealamountPageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealprofitdetailService.findDealprofitdetailUseraAndDealamountPageList(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 不分页查询交易人数/次数报表记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findDealprofitdetailUseraAndDealamountList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DealprofitdetailUseraAndDealamountVO> findDealprofitdetailUseraAndDealamountList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) throws Exception {
		return  dealprofitdetailService.findDealprofitdetailUseraAndDealamountList(searchBean);
	}
	
	/**
	 * 交易人数/次数报表(包括了交易手数图表和毛利图表)记录-导出
	 */
	@RequestMapping("/exportExcelDealprofitdetailUseraAndDealamount")
	public void exportExcelDealprofitdetailUseraAndDealamount(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String reportType = searchBean.getReportType();
			String dealprofithourReportPath = null;
			String dealprofithourReportValue = null;
			
            if("1".equals(reportType)){
				 dealprofithourReportPath = ExportTemplateEnum.dealprofitdetailUseramountReport.getPath();
				 dealprofithourReportValue = ExportTemplateEnum.dealprofitdetailUseramountReport.getValue();
			}else if("2".equals(reportType)){
				 dealprofithourReportPath = ExportTemplateEnum.dealprofitdetailDealamountReport.getPath();
				 dealprofithourReportValue = ExportTemplateEnum.dealprofitdetailDealamountReport.getValue();
			}
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(dealprofithourReportPath)));
			// 2、需要导出的数据
			List<DealprofitdetailUseraAndDealamountVO> recordList = dealprofitdetailService.findDealprofitdetailUseraAndDealamountList(searchBean);
			Integer total = recordList.size()-2;
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DealprofitdetailUseraAndDealamountVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DealprofitdetailUseraAndDealamountVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(dealprofithourReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转交易伦敦金平仓手数图表管理页面
	 */
	@RequestMapping(value = "/dealcateGoldPage", method = { RequestMethod.GET })
	public String dealcateGoldPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dealcateGold/dealcateGoldStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转交易伦敦银平仓手数图表管理页面
	 */
	@RequestMapping(value = "/dealcateXaucnhPage", method = { RequestMethod.GET })
	public String dealcateXaucnhPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dealcateXaucnh/dealcateXaucnhStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转交易人民币金平仓手数图表管理页面
	 */
	@RequestMapping(value = "/dealcateSilverPage", method = { RequestMethod.GET })
	public String dealcateSilverPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dealcateSilver/dealcateSilverStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转交易人民币銀平仓手数图表管理页面
	 */
	@RequestMapping(value = "/dealcateXagcnhPage", method = { RequestMethod.GET })
	public String dealcateXagcnhPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dealcateXagcnh/dealcateXagcnhStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 分页查询交易类别倫敦/人民幣数据记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealcategoryAxuAxgcnhPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findDealcategoryAxuAxgcnhPageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealprofitdetailService.findDealcategoryAxuAxgcnhPageList(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 不分页查询交易类别倫敦/人民幣数据记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealcategoryAxuAxgcnhList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DealcategoryVO> findDealcategoryAxuAxgcnhList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return  dealprofitdetailService.findDealcategoryAxuAxgcnhList(searchBean);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DealcategoryVO>();
		}
	}
	
	/**
	 *  交易类别数据倫敦/人民幣记录-导出
	 */
	@RequestMapping("/exportExcelDealcategoryAxuAxgcnh")
	public void exportExcelDealcategoryAxuAxgcnh(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String reportType = searchBean.getReportType();
			String companyequitymdepositReportPath = null;
			String companyequitymdepositReportValue = null;

			if("1".equals(reportType)){
				companyequitymdepositReportPath = ExportTemplateEnum.dealcateGoldReport.getPath();
				companyequitymdepositReportValue = ExportTemplateEnum.dealcateGoldReport.getValue();
			}
			if("2".equals(reportType)){
				companyequitymdepositReportPath = ExportTemplateEnum.dealcateSilverReport.getPath();
				companyequitymdepositReportValue = ExportTemplateEnum.dealcateSilverReport.getValue();
			}
			if("3".equals(reportType)){
				companyequitymdepositReportPath = ExportTemplateEnum.dealcateXagcnhReport.getPath();
				companyequitymdepositReportValue = ExportTemplateEnum.dealcateXagcnhReport.getValue();
			}
			if("4".equals(reportType)){
				companyequitymdepositReportPath = ExportTemplateEnum.dealcateXaucnhReport.getPath();
				companyequitymdepositReportValue = ExportTemplateEnum.dealcateXaucnhReport.getValue();
			}
			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(companyequitymdepositReportPath)));
			// 2、需要导出的数据
			List<DealcategoryVO> recordList =  dealprofitdetailService.findDealcategoryAxuAxgcnhList(searchBean);			
			Integer total = recordList.size()-1;						
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DealcategoryVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DealcategoryVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(companyequitymdepositReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转浮盈报表管理页面
	 */
	@RequestMapping(value = "/dailyreportFloatingprofitPage", method = { RequestMethod.GET })
	public String dailyreportFloatingprofitPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dailyreportFloatingprofit/dailyreportFloatingprofitStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转结余报表管理页面
	 */
	@RequestMapping(value = "/dailyreportPreviousbalancePage", method = { RequestMethod.GET })
	public String dailyreportPreviousbalancePage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dailyreportPreviousbalance/dailyreportPreviousbalanceStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转占用保证金比例报表管理页面
	 */
	@RequestMapping(value = "/dailyreportMarginRatioPage", method = { RequestMethod.GET })
	public String dailyreportMarginRatioPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dailyreportMarginRatio/dailyreportMarginRatioStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}	
	
	/**
	 * 跳转浮盈比例报表管理页面
	 */
	@RequestMapping(value = "/dailyreportFloatingprofitRatioPage", method = { RequestMethod.GET })
	public String dailyreportFloatingprofitRatioPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dailyreportFloatingprofitRatio/dailyreportFloatingprofitRatioStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 分页查询浮盈/结余/占用保证金比例/浮盈比例图表报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDailyreportPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findDailyreportPageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dailyreportService.findDailyreportPageList(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 不分页查询浮盈/结余/占用保证金比例/浮盈比例图表报表记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findDailyreportList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DailyreportVO> findDailyreportList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) throws Exception {
		return dailyreportService.findDailyreportList(searchBean);
	}
	
	/**
	 * 浮盈/结余/占用保证金比例/浮盈比例图表报表记录-导出
	 */
	@RequestMapping("/exportExcelDailyreport")
	public void exportExcelDailyreport(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String reportType = searchBean.getReportType();
			String dailyreportlReportPath = null;
			String dailyreportlReportValue = null;
			if("1".equals(reportType)){
				 dailyreportlReportPath = ExportTemplateEnum.dailyreportlReport.getPath();
				 dailyreportlReportValue = ExportTemplateEnum.dailyreportlReport.getValue();
			}
			
			if("2".equals(reportType)){
				 dailyreportlReportPath = ExportTemplateEnum.dailyreportFloatingprofitReport.getPath();
				 dailyreportlReportValue = ExportTemplateEnum.dailyreportFloatingprofitReport.getValue();
			}
			
			if("3".equals(reportType)){
				 dailyreportlReportPath = ExportTemplateEnum.dailyreportPreviousbalanceReport.getPath();
				 dailyreportlReportValue = ExportTemplateEnum.dailyreportPreviousbalanceReport.getValue();
			}
			
			if("4".equals(reportType)){
				 dailyreportlReportPath = ExportTemplateEnum.dailyreportFloatingprofitRatioReport.getPath();
				 dailyreportlReportValue = ExportTemplateEnum.dailyreportFloatingprofitRatioReport.getValue();
			}
			
			if("5".equals(reportType)){
				 dailyreportlReportPath = ExportTemplateEnum.dailyreportMarginRatioReport.getPath();
				 dailyreportlReportValue = ExportTemplateEnum.dailyreportMarginRatioReport.getValue();
			}
			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(new File(request.getServletContext().getRealPath(dailyreportlReportPath)));
			// 2、需要导出的数据
			List<DailyreportVO> recordList = dailyreportService.findDailyreportList(searchBean);
			Integer total = 0;
			if(!"4".equals(reportType) && !"5".equals(reportType)){
				 total = recordList.size()-1;
			}else{
				total = recordList.size();
			}
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DailyreportVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DailyreportVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(dailyreportlReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 分页查询人均交易手数记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findAverageTransactionVolumePageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findAverageTransactionVolumePageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealprofitdetailService.findAverageTransactionVolumePageList(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 跳转人均交易手数管理页面
	 */
	@RequestMapping(value = "/averageTransactionVolumeStatisticsPage", method = { RequestMethod.GET })
	public String averageTransactionVolumeStatisticsPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/averageTransactionVolume/averageTransactionVolumeStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 不分页查询人均交易手数记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findAverageTransactionVolumeList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DealprofitdetailVO> findAverageTransactionVolumeList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return  dealprofitdetailService.findAverageTransactionVolumeList(searchBean);			
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DealprofitdetailVO>();
		}
	}
	
	/**
	 *  人均交易手数记录-导出
	 */
	@RequestMapping("/exportExcelAverageTransactionVolume")
	public void exportExcelAverageTransactionVolume(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String companyequitymdepositReportPath = ExportTemplateEnum.averageTransactionVolume.getPath();
			String companyequitymdepositReportValue = ExportTemplateEnum.averageTransactionVolume.getValue();			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(new File(request.getServletContext().getRealPath(companyequitymdepositReportPath)));
			// 2、需要导出的数据
			List<DealprofitdetailVO> recordList = dealprofitdetailService.findAverageTransactionVolumeList(searchBean);
			Integer total = recordList.size()-2;
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DealprofitdetailVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DealprofitdetailVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(companyequitymdepositReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转MT4/GTS2数据报表管理页面
	 */
	@RequestMapping(value = "/findProfitdetailMT4AndGts2Page", method = { RequestMethod.GET })
	public String findProfitdetailMT4AndGts2Page(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/profitdetailMT4AndGts2/profitdetailMT4AndGts2Statistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 分页查询MT4/GTS2数据报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealprofitdetailMT4AndGts2PageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findDealprofitdetailMT4AndGts2PageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealprofitdetailService.findDealprofitdetailMT4AndGts2PageList(super.createPageGrid(request, searchBean));		
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 不分页查询MT4/GTS2数据报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealprofitdetailMT4AndGts2List", method = { RequestMethod.POST })
	@ResponseBody
	public List<DealprofitdetailMT4AndGts2VO> findDealprofitdetailMT4AndGts2List(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealprofitdetailService.findDealprofitdetailMT4AndGts2List(searchBean);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DealprofitdetailMT4AndGts2VO>();
		}
	}
	
	/**
	 *  MT4/GTS2数据报表记录-导出
	 */
	@RequestMapping("/exportExcelDealprofitdetailMT4AndGts2")
	public void exportExcelDealprofitdetailMT4AndGts2(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String companyequitymdepositReportPath = ExportTemplateEnum.dealprofitdetailMT4AndGts2.getPath();
			String companyequitymdepositReportValue = ExportTemplateEnum.dealprofitdetailMT4AndGts2.getValue();
			if(PlatformtypeEnum.GTS2.getValue().equals(searchBean.getPlatformType())){
				 companyequitymdepositReportPath = ExportTemplateEnum.dealprofitdetailGts2.getPath();
				 companyequitymdepositReportValue = ExportTemplateEnum.dealprofitdetailGts2.getValue();
			}else if(PlatformtypeEnum.MT4.getValue().equals(searchBean.getPlatformType())){
				 companyequitymdepositReportPath = ExportTemplateEnum.dealprofitdetailMT4.getPath();
				 companyequitymdepositReportValue = ExportTemplateEnum.dealprofitdetailMT4.getValue();
			}else{
				 companyequitymdepositReportPath = ExportTemplateEnum.dealprofitdetailMT4AndGts2.getPath();
				 companyequitymdepositReportValue = ExportTemplateEnum.dealprofitdetailMT4AndGts2.getValue();
			}
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(companyequitymdepositReportPath)));
			// 2、需要导出的数据
			searchBean.setSort("exectime");
			searchBean.setOrder("desc");
			List<DealprofitdetailMT4AndGts2VO> recordList =  dealprofitdetailService.findDealprofitdetailMT4AndGts2List(searchBean);	
			Integer total = recordList.size()-1;
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DealprofitdetailMT4AndGts2VO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DealprofitdetailMT4AndGts2VO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(companyequitymdepositReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转交易类别数据报表管理页面
	 */
	@RequestMapping(value = "/dealcategoryPage", method = { RequestMethod.GET })
	public String dealcategoryPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dealcategory/dealcategoryStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 分页查询交易类别数据记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealcategoryPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findDealcategoryPageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealprofitdetailService.findDealcategoryPageList(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 不分页查询交易类别数据记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealcategoryList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DealcategoryVO> findDealcategoryList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return  dealprofitdetailService.findDealcategoryList(searchBean);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DealcategoryVO>();
		}
	}
	
	/**
	 *  交易类别数据记录-导出
	 */
	@RequestMapping("/exportExcelDealcategory")
	public void exportExcelDealcategory(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String type = request.getParameter("type");
			String companyequitymdepositReportPath = null;
			String companyequitymdepositReportValue = null;
			if("0".equals(type)){
				if(PlatformtypeEnum.GTS2.getValue().equals(searchBean.getPlatformType())){
					 companyequitymdepositReportPath = ExportTemplateEnum.dealcategoryGTS2Report.getPath();
					 companyequitymdepositReportValue = ExportTemplateEnum.dealcategoryGTS2Report.getValue();
				}else if(PlatformtypeEnum.MT4.getValue().equals(searchBean.getPlatformType())){
					 companyequitymdepositReportPath = ExportTemplateEnum.dealcategoryMT4Report.getPath();
					 companyequitymdepositReportValue = ExportTemplateEnum.dealcategoryMT4Report.getValue();
				}else{
					 companyequitymdepositReportPath = ExportTemplateEnum.dealcategoryReport.getPath();
					 companyequitymdepositReportValue = ExportTemplateEnum.dealcategoryReport.getValue();
				}
			}			
			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(companyequitymdepositReportPath)));
			// 2、需要导出的数据
			List<DealcategoryVO> recordList =  dealprofitdetailService.findDealcategoryList(searchBean);			
			Integer total = recordList.size()-1;		
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DealcategoryVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DealcategoryVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(companyequitymdepositReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	
	//营运业务报表
	
	/**
	 * 跳转公司盈亏报表管理页面
	 */
	@RequestMapping(value = "/dealprofithourPage", method = { RequestMethod.GET })
	public String dealprofithourPage(HttpServletRequest request) {
		try {
			String platformType = request.getParameter("platformType");
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			request.setAttribute("platformType", platformType);
			return "/trade/gts2/dealprofithour/dealprofithourStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询公司盈亏报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealprofithourPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findDealprofithourPageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealprofithourService.findDealprofithourPage(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 不分页查询公司盈亏报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealprofithourList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DealprofithourVO> findDealprofithourList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			List<DealprofithourVO> recordList = dealprofithourService.findDealprofithourList(searchBean);
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());
			for (DealprofithourVO dealprofithourVO : recordList) {				
				dealprofithourVO.setCompanyprofit(NumberUtil.NumberFormat(dealprofithourVO.getCompanyprofit(),businessplatform));
			}
			return recordList;						
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DealprofithourVO>();
		}
	}
	
	/**
	 * 公司盈亏报表记录-导出
	 */
	@RequestMapping("/exportExcelDealprofithour")
	public void exportExcelDealprofithour(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String dealprofithourReportPath = ExportTemplateEnum.dealprofithourReport.getPath();
			String dealprofithourReportValue = ExportTemplateEnum.dealprofithourReport.getValue();
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(dealprofithourReportPath)));
			// 2、需要导出的数据
			List<DealprofithourVO> recordList = dealprofithourService.findDealprofithourList(searchBean);
			Integer total = recordList.size()-1;		
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DealprofithourVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DealprofithourVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(dealprofithourReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转GTS2下单途径比例报表管理页面
	 */
	@RequestMapping(value = "/dealchannelPage", method = { RequestMethod.GET })
	public String dealchannelPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dealchannel/dealchannelStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询GTS2下单途径比例报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealchannelPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findDealchannelPageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealchannelService.findDealchannelPage(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 不分页查询GTS2下单途径比例报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealchannelList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DealchannelVO> findDealchannelList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealchannelService.findDealchannelList(searchBean);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DealchannelVO>();
		}
	}
	
	/**
	 * GTS2下单途径比例报表记录-导出
	 */
	@RequestMapping("/exportExcelDealchannel")
	public void exportExcelDealchannel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String dealchannelReportPath = ExportTemplateEnum.dealchannelReport.getPath();
			String dealchannelReportValue = ExportTemplateEnum.dealchannelReport.getValue();
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(dealchannelReportPath)));
			// 2、需要导出的数据
			List<DealchannelVO> recordList = dealchannelService.findDealchannelList(searchBean);
			Integer total = recordList.size()-2;			
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DealchannelVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DealchannelVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(dealchannelReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转新开户_激活途径比例报表管理页面
	 */
	@RequestMapping(value = "/accountchannelPage", method = { RequestMethod.GET })
	public String accountchannelPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			String accountchannelType = request.getParameter("accountchannelType");
			request.setAttribute("accountchannelType", accountchannelType);
			return "/trade/gts2/accountchannel/accountchannelStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询新开户_激活途径比例报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findAccountchannelPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findAccountchannelPageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return accountchannelService.findAccountchannelPage(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 不分页查询新开户_激活途径比例报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findAccountchannelList", method = { RequestMethod.POST })
	@ResponseBody
	public List<AccountchannelVO> findAccountchannelList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return accountchannelService.findAccountchannelList(searchBean);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<AccountchannelVO>();
		}
	}
	
	/**
	 * 新开户_激活途径比例报表记录-导出
	 */
	@RequestMapping("/exportExcelAccountchannel")
	public void exportExcelAccountchannel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String type = request.getParameter("type");
			String dealchannelReportPath = null;
			String dealchannelReportValue = null;
			if("open".equals(type)){
				 dealchannelReportPath = ExportTemplateEnum.accountchannelOpenReport.getPath();
				 dealchannelReportValue = ExportTemplateEnum.accountchannelOpenReport.getValue();
			}
            if("active".equals(type)){
            	 dealchannelReportPath = ExportTemplateEnum.accountchannelActiveReport.getPath();
    			 dealchannelReportValue = ExportTemplateEnum.accountchannelActiveReport.getValue();
			}
			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(dealchannelReportPath)));
			// 2、需要导出的数据
			List<AccountchannelVO> recordList = accountchannelService.findAccountchannelList(searchBean);
			Integer total = recordList.size()-2;
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<AccountchannelVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, AccountchannelVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(dealchannelReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 毛利/交易手数图表记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findBusinessDealprofitdetailList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DealprofitdetailVO> findBusinessDealprofitdetailList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) throws Exception {
		return dealprofitdetailService.findDealprofitdetailList(searchBean);
	}
	
	/**
	 * 跳转毛利图表管理页面
	 */
	@RequestMapping(value = "/dealprofitdetailGrossprofitPage", method = { RequestMethod.GET })
	public String dealprofitdetailGrossprofitPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dealprofitdetailGrossprofit/dealprofitdetailGrossprofitStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转交易手数图表管理页面
	 */
	@RequestMapping(value = "/dealprofitdetailVolumePage", method = { RequestMethod.GET })
	public String dealprofitdetailVolumePage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dealprofitdetailVolume/dealprofitdetailVolumeStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 结余图表记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findBusinessDailyreportList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DailyreportVO> findBusinessDailyreportList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) throws Exception {
		return dailyreportService.findDailyreportList(searchBean);
	}
	
	/**
	 * 跳转结余图表报表管理页面
	 */
	@RequestMapping(value = "/dailyreportPage", method = { RequestMethod.GET })
	public String dailyreportPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dailyreport/dailyreportStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转交易记录总结报表管理页面
	 */
	@RequestMapping(value = "/dealprofitdetailPage", method = { RequestMethod.GET })
	public String dealprofitdetailPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/dealprofitdetail/dealprofitdetailStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDealprofitdetailPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findDealprofitdetailPageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealprofitdetailService.findDealprofitdetailPage(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
		
	/**
	 * 不分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findDealprofitdetailList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DealprofitdetailVO> findDealprofitdetailList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) throws Exception {
		return dealprofitdetailService.findDealprofitdetailList(searchBean);
	}
	
	/**
	 * 交易记录总结报表(包括了交易手数图表和毛利图表)记录-导出
	 */
	@RequestMapping("/exportExcelDealprofitdetail")
	public void exportExcelDealprofitdetail(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String type = request.getParameter("type");
			String dealprofithourReportPath = null;
			String dealprofithourReportValue = null;
			
			if("0".equals(type)){
				 dealprofithourReportPath = ExportTemplateEnum.dealprofitdetailReport.getPath();
				 dealprofithourReportValue = ExportTemplateEnum.dealprofitdetailReport.getValue();
			}else if("1".equals(type)){
				 dealprofithourReportPath = ExportTemplateEnum.dealprofitdetailGrossprofitReport.getPath();
				 dealprofithourReportValue = ExportTemplateEnum.dealprofitdetailGrossprofitReport.getValue();
			}else if("2".equals(type)){
				 dealprofithourReportPath = ExportTemplateEnum.dealprofitdetailVolumeReport.getPath();
				 dealprofithourReportValue = ExportTemplateEnum.dealprofitdetailVolumeReport.getValue();
			}else if("3".equals(type)){
				 dealprofithourReportPath = ExportTemplateEnum.dealprofitdetailUseramountReport.getPath();
				 dealprofithourReportValue = ExportTemplateEnum.dealprofitdetailUseramountReport.getValue();
			}else if("4".equals(type)){
				 dealprofithourReportPath = ExportTemplateEnum.dealprofitdetailDealamountReport.getPath();
				 dealprofithourReportValue = ExportTemplateEnum.dealprofitdetailDealamountReport.getValue();
			}
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(dealprofithourReportPath)));
			// 2、需要导出的数据
			List<DealprofitdetailVO> recordList = dealprofitdetailService.findDealprofitdetailList(searchBean);
			Integer total = recordList.size();
			if(null != recordList && recordList.size() >0){
				
				Double grossprofit = 0.0;
				Double companyprofit = 0.0;
				Double swap = 0.0;
				Double sysclearzero = 0.0;
				Double bonus = 0.0;
				Double commission = 0.0;
				Double adjustfixedamount = 0.0;
				Double floatingprofit = 0.0;
				Double goldprofit = 0.0;
				Double silverprofit = 0.0;
				Double xaucnhprofit = 0.0;
				Double xagcnhprofit = 0.0;
				Double goldvolume = 0.0;
				Double silvervolume = 0.0;
				Double xaucnhvolume = 0.0;
				Double xagcnhvolume = 0.0;
				Double volume = 0.0;
				Double closedvolume = 0.0;
				Double useramount = 0.0;
				Double dealamount = 0.0;
				
				Double bonusclear = 0.0;
				Double margin = 0.0;
				Double cgse = 0.0;
				
				Double handOperHedgeProfitAndLoss = 0.0;
				
				String businessplatform = String.valueOf(UserContext.get().getCompanyId());
				for (DealprofitdetailVO dealprofitdetailVO : recordList) {
					grossprofit += NumberUtil.StringToDouble(dealprofitdetailVO.getGrossprofit());
					companyprofit += NumberUtil.StringToDouble(dealprofitdetailVO.getCompanyprofit());
					swap += NumberUtil.StringToDouble(dealprofitdetailVO.getSwap());
					sysclearzero += NumberUtil.StringToDouble(dealprofitdetailVO.getSysclearzero());
					bonus += NumberUtil.StringToDouble(dealprofitdetailVO.getBonus());
					commission += NumberUtil.StringToDouble(dealprofitdetailVO.getCommission());
					adjustfixedamount += NumberUtil.StringToDouble(dealprofitdetailVO.getAdjustfixedamount());
					floatingprofit += NumberUtil.StringToDouble(dealprofitdetailVO.getFloatingprofit());
					goldprofit += NumberUtil.StringToDouble(dealprofitdetailVO.getGoldprofit());
					silverprofit += NumberUtil.StringToDouble(dealprofitdetailVO.getSilverprofit());
					xaucnhprofit += NumberUtil.StringToDouble(dealprofitdetailVO.getXaucnhprofit());
					xagcnhprofit += NumberUtil.StringToDouble(dealprofitdetailVO.getXagcnhprofit());
					goldvolume += NumberUtil.StringToDouble(dealprofitdetailVO.getGoldvolume());
					silvervolume += NumberUtil.StringToDouble(dealprofitdetailVO.getSilvervolume());
					xaucnhvolume += NumberUtil.StringToDouble(dealprofitdetailVO.getXaucnhvolume());
					xagcnhvolume += NumberUtil.StringToDouble(dealprofitdetailVO.getXagcnhvolume());
					volume += NumberUtil.StringToDouble(dealprofitdetailVO.getVolume());
					closedvolume += NumberUtil.StringToDouble(dealprofitdetailVO.getClosedvolume());
					useramount += NumberUtil.StringToDouble(dealprofitdetailVO.getUseramount());
					dealamount += NumberUtil.StringToDouble(dealprofitdetailVO.getDealamount());
					
					bonusclear += NumberUtil.StringToDouble(dealprofitdetailVO.getBonusclear());
					margin += NumberUtil.StringToDouble(dealprofitdetailVO.getMargin());
					cgse += NumberUtil.StringToDouble(dealprofitdetailVO.getCgse());	
					handOperHedgeProfitAndLoss+= NumberUtil.StringToDouble(dealprofitdetailVO.getHandOperHedgeProfitAndLoss());
				}
				DealprofitdetailVO dealprofitdetailVOTotal = new DealprofitdetailVO();
				dealprofitdetailVOTotal.setCurrency("-");
				dealprofitdetailVOTotal.setSource("-");
				dealprofitdetailVOTotal.setPlatform(searchBean.getPlatformType());
				dealprofitdetailVOTotal.setGrossprofit(NumberUtil.NumberFormat(String.valueOf(grossprofit),businessplatform));
				dealprofitdetailVOTotal.setCompanyprofit(NumberUtil.NumberFormat(String.valueOf(companyprofit),businessplatform));
				dealprofitdetailVOTotal.setSwap(NumberUtil.NumberFormat(String.valueOf(swap),businessplatform));
				dealprofitdetailVOTotal.setSysclearzero(NumberUtil.NumberFormat(String.valueOf(sysclearzero),businessplatform));
				dealprofitdetailVOTotal.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus),businessplatform));
				dealprofitdetailVOTotal.setCommission(NumberUtil.NumberFormat(String.valueOf(commission),businessplatform));		
				dealprofitdetailVOTotal.setAdjustfixedamount(NumberUtil.NumberFormat(String.valueOf(adjustfixedamount),businessplatform));
				dealprofitdetailVOTotal.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(floatingprofit),businessplatform));
				dealprofitdetailVOTotal.setGoldprofit(NumberUtil.NumberFormat(String.valueOf(goldprofit),businessplatform));
				dealprofitdetailVOTotal.setSilverprofit(NumberUtil.NumberFormat(String.valueOf(silverprofit),businessplatform));
				dealprofitdetailVOTotal.setXaucnhprofit(NumberUtil.NumberFormat(String.valueOf(xaucnhprofit),businessplatform));
				dealprofitdetailVOTotal.setXagcnhprofit(NumberUtil.NumberFormat(String.valueOf(xagcnhprofit),businessplatform));
				dealprofitdetailVOTotal.setGoldvolume(NumberUtil.NumberFormat(String.valueOf(goldvolume),businessplatform));
				dealprofitdetailVOTotal.setSilvervolume(NumberUtil.NumberFormat(String.valueOf(silvervolume),businessplatform));
				dealprofitdetailVOTotal.setXaucnhvolume(NumberUtil.NumberFormat(String.valueOf(xaucnhvolume),businessplatform));
				dealprofitdetailVOTotal.setXagcnhvolume(NumberUtil.NumberFormat(String.valueOf(xagcnhvolume),businessplatform));
				dealprofitdetailVOTotal.setVolume(NumberUtil.NumberFormat(String.valueOf(volume),businessplatform));
				dealprofitdetailVOTotal.setClosedvolume(NumberUtil.NumberFormat(String.valueOf(closedvolume),businessplatform));
				dealprofitdetailVOTotal.setUseramount(NumberUtil.NumberFormat(String.valueOf(useramount),businessplatform));
				dealprofitdetailVOTotal.setDealamount(NumberUtil.NumberFormat(String.valueOf(dealamount),businessplatform));
				
				dealprofitdetailVOTotal.setBonusclear(NumberUtil.NumberFormat(String.valueOf(bonusclear),businessplatform));
				dealprofitdetailVOTotal.setMargin(NumberUtil.NumberFormat(String.valueOf(margin),businessplatform));
				dealprofitdetailVOTotal.setCgse(NumberUtil.NumberFormat(String.valueOf(cgse),businessplatform));
				dealprofitdetailVOTotal.setHandOperHedgeProfitAndLoss(NumberUtil.NumberFormat(String.valueOf(handOperHedgeProfitAndLoss),businessplatform));
				
				
				DealprofitdetailVO dealprofitdetailVOMavg = new DealprofitdetailVO();
				dealprofitdetailVOMavg.setGrossprofit(NumberUtil.NumberFormat(String.valueOf((grossprofit)/total),businessplatform));
				dealprofitdetailVOMavg.setCompanyprofit(NumberUtil.NumberFormat(String.valueOf(companyprofit/total),businessplatform));
				dealprofitdetailVOMavg.setSwap(NumberUtil.NumberFormat(String.valueOf(swap/total),businessplatform));
				dealprofitdetailVOMavg.setSysclearzero(NumberUtil.NumberFormat(String.valueOf(sysclearzero/total),businessplatform));
				dealprofitdetailVOMavg.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus/total),businessplatform));
				dealprofitdetailVOMavg.setCommission(NumberUtil.NumberFormat(String.valueOf(commission/total),businessplatform));		
				dealprofitdetailVOMavg.setAdjustfixedamount(NumberUtil.NumberFormat(String.valueOf(adjustfixedamount/total),businessplatform));
				dealprofitdetailVOMavg.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(floatingprofit/total),businessplatform));
				dealprofitdetailVOMavg.setGoldprofit(NumberUtil.NumberFormat(String.valueOf(goldprofit/total),businessplatform));
				dealprofitdetailVOMavg.setSilverprofit(NumberUtil.NumberFormat(String.valueOf(silverprofit/total),businessplatform));
				dealprofitdetailVOMavg.setXaucnhprofit(NumberUtil.NumberFormat(String.valueOf(xaucnhprofit/total),businessplatform));
				dealprofitdetailVOMavg.setXagcnhprofit(NumberUtil.NumberFormat(String.valueOf(xagcnhprofit/total),businessplatform));
				dealprofitdetailVOMavg.setGoldvolume(NumberUtil.NumberFormat(String.valueOf(goldvolume/total),businessplatform));
				dealprofitdetailVOMavg.setSilvervolume(NumberUtil.NumberFormat(String.valueOf(silvervolume/total),businessplatform));
				dealprofitdetailVOMavg.setXaucnhvolume(NumberUtil.NumberFormat(String.valueOf(xaucnhvolume/total),businessplatform));
				dealprofitdetailVOMavg.setXagcnhvolume(NumberUtil.NumberFormat(String.valueOf(xagcnhvolume/total),businessplatform));
				dealprofitdetailVOMavg.setVolume(NumberUtil.NumberFormat(String.valueOf(volume/total),businessplatform));
				dealprofitdetailVOMavg.setClosedvolume(NumberUtil.NumberFormat(String.valueOf(closedvolume/total),businessplatform));
				dealprofitdetailVOMavg.setUseramount(NumberUtil.NumberFormat(String.valueOf(useramount),businessplatform));
				dealprofitdetailVOMavg.setDealamount(NumberUtil.NumberFormat(String.valueOf(dealamount),businessplatform));
				
				dealprofitdetailVOMavg.setBonusclear(NumberUtil.NumberFormat(String.valueOf(bonusclear/total),businessplatform));
				dealprofitdetailVOMavg.setMargin(NumberUtil.NumberFormat(String.valueOf(margin/total),businessplatform));
				dealprofitdetailVOMavg.setCgse(NumberUtil.NumberFormat(String.valueOf(cgse/total),businessplatform));
				dealprofitdetailVOMavg.setHandOperHedgeProfitAndLoss(NumberUtil.NumberFormat(String.valueOf(handOperHedgeProfitAndLoss/Double.valueOf(total)),businessplatform));
				dealprofitdetailVOMavg.setCurrency("-");
				dealprofitdetailVOMavg.setSource("-");
				dealprofitdetailVOMavg.setPlatform(searchBean.getPlatformType());
				
				dealprofitdetailVOTotal.setExectime("总数");
				dealprofitdetailVOMavg.setExectime("平均");
				recordList.add(dealprofitdetailVOTotal);
				recordList.add(dealprofitdetailVOMavg);
			}
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DealprofitdetailVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DealprofitdetailVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
					}
					return fieldValue;					
				}
			});

			builder.put("totalRecordSize", total);
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(dealprofithourReportValue, request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
		
	/**
	 * 跳转交易情况报表管理页面
	 */
	@RequestMapping(value = "/tradeSituationPage", method = { RequestMethod.GET })
	public String tradeSituationPage(HttpServletRequest request) {
		try {
			
			request.setAttribute("deviceTypeEnum", DeviceTypeEnum.getList3());			
			
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList2());			
			request.setAttribute("platformEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			
			ChannelEntity channelEntity = new ChannelEntity();
			channelEntity.setChannelType(ChannelTypeEnum.app.getLabelKey());
			List<String> channelNameList = channelService.findChannelList(channelEntity); //渠道名称
			request.setAttribute("channelNameList", channelNameList);
			
			List<String> channelGroupList = channelService.findChannelGroupList(channelEntity); //渠道分组
			request.setAttribute("channelGroupList", channelGroupList);
			
			List<String> channelLevelList = channelService.findChannelLevelList(channelEntity); //渠道分级
			request.setAttribute("channelLevelList", channelLevelList);
			
			
			return "/trade/gts2/tradeSituation/tradeSituation";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 分页查询交易情况报表记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findTradeSituationPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findTradeSituationPageList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			return dealprofitdetailService.findTradeSituationPageList(super.createPageGrid(request, searchBean));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
		
	/**
	 * 不分页查询交易情况报表记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findTradeSituationList", method = { RequestMethod.POST })
	@ResponseBody
	public List<TradeSituationVO> findTradeSituationList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel searchBean) throws Exception {
		return dealprofitdetailService.findTradeSituationList(searchBean);
	}
	
	/**
	 * 交易情况报表记录-导出
	 */
	@RequestMapping("/exportExcelTradeSituation")
	public void exportExcelTradeSituation(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String tradeSituationReportPath = ExportTemplateEnum.tradeSituationReport.getPath();
			String tradeSituationReportValue = ExportTemplateEnum.tradeSituationReport.getValue();

			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(tradeSituationReportPath)));
			// 2、需要导出的数据
			List<TradeSituationVO> recordList = dealprofitdetailService.findTradeSituationList(searchBean);
			Integer total = recordList.size();
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<TradeSituationVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, TradeSituationVO param) {	
					if("platform".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
						return "全部";
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
