package com.gw.das.web.controller.trade;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.context.Constants;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.PlatformtypeEnum;
import com.gw.das.common.enums.TimeRangeEnum;
import com.gw.das.common.enums.VolumeRangeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.dao.trade.bean.DasTradeDealprofitdetailStatisticsYears;
import com.gw.das.dao.trade.bean.DasTradeGts2Mt4Top10PositionStatisticsYears;
import com.gw.das.dao.trade.bean.DasTradeGts2Mt4Top10TargetDateYears;
import com.gw.das.dao.trade.bean.DasTradeGts2Mt4Top10VolumeStatisticsYears;
import com.gw.das.dao.trade.bean.DasTradeGts2cashandaccountdetailStatisticsYears;
import com.gw.das.dao.trade.bean.OpenAccountAndDepositSummary;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.service.trade.TradeService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/TradeController")
public class TradeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	private TradeService tradeService;

	/**
	 * 跳转交易报表-定制页面
	 */
	@RequestMapping(value = "/portal", method = { RequestMethod.GET })
	public String portal(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/tradePortal/tradePortal";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			return "/trade/tradeDetail/tradeDetail";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 十大赢家、十大亏损-不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/top10WinnerLoserYearsList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasTradeGts2Mt4Top10TargetDateYears> top10WinnerLoserYearsList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			
			List<DasTradeGts2Mt4Top10TargetDateYears> list = tradeService.top10WinnerLoserYearsList(tradeSearchModel);
			return list;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasTradeGts2Mt4Top10TargetDateYears>();
		}
	}
	
	/**
	 * 十大交易量客戶-不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/top10TraderYearsList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasTradeGts2Mt4Top10TargetDateYears> top10TraderYearsList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			List<DasTradeGts2Mt4Top10TargetDateYears> list = tradeService.top10TraderYearsList(tradeSearchModel);
			return list;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasTradeGts2Mt4Top10TargetDateYears>();
		}
	}
	
	/**
	 * 十大存款、十大取款-不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/top10CashinCashoutYearsList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasTradeGts2Mt4Top10TargetDateYears> top10CashinCashoutYearsList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			List<DasTradeGts2Mt4Top10TargetDateYears> list = tradeService.top10CashinCashoutYearsList(tradeSearchModel);
			return list;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasTradeGts2Mt4Top10TargetDateYears>();
		}
	}
	
	/**
	 * 十大交易统计-手数-不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/top10VolumeStatisticsYearsList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasTradeGts2Mt4Top10VolumeStatisticsYears> top10VolumeStatisticsYearsList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			List<DasTradeGts2Mt4Top10VolumeStatisticsYears> list = tradeService.top10VolumeStatisticsYearsList(tradeSearchModel);
			// 小计与总计
			List<DasTradeGts2Mt4Top10VolumeStatisticsYears> footerList = new ArrayList<DasTradeGts2Mt4Top10VolumeStatisticsYears>();
			DasTradeGts2Mt4Top10VolumeStatisticsYears footer = new DasTradeGts2Mt4Top10VolumeStatisticsYears();
			if(null != list && list.size()>0){
				for(Object obj: list){
					DasTradeGts2Mt4Top10VolumeStatisticsYears record = (DasTradeGts2Mt4Top10VolumeStatisticsYears)obj;
					record.setVolumeRange(VolumeRangeEnum.format(record.getVolumeRange()));
					footer.setClosedvolume(footer.getClosedvolume() + record.getClosedvolume());
					footer.setCleanProfit(footer.getCleanProfit() + record.getCleanProfit());
					if(record.getPercent()>0D){
						footer.setPercent(100D);//有值时，肯定是100%
					}
				}
				footer.setClosedvolume(NumberUtil.m2(footer.getClosedvolume()));//保留两位有效数字
				footer.setCleanProfit(NumberUtil.m2(footer.getCleanProfit()));//保留两位有效数字
			}
			footerList.add(footer);
			PageGrid<DasTradeGts2Mt4Top10VolumeStatisticsYears> pageGrid = new PageGrid<DasTradeGts2Mt4Top10VolumeStatisticsYears>();
			pageGrid.setOrder(tradeSearchModel.getOrder());
			pageGrid.setSort(tradeSearchModel.getSort());
			pageGrid.setRows(list);
			pageGrid.setFooter(footerList);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasTradeGts2Mt4Top10VolumeStatisticsYears>();
		}
	}
	
	/**
	 * 十大交易统计-持仓时间-不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/top10PositionStatisticsYearsList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasTradeGts2Mt4Top10PositionStatisticsYears> top10PositionStatisticsYearsList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			List<DasTradeGts2Mt4Top10PositionStatisticsYears> list = tradeService.top10PositionStatisticsYearsList(tradeSearchModel);
			// 小计与总计
			List<DasTradeGts2Mt4Top10PositionStatisticsYears> footerList = new ArrayList<DasTradeGts2Mt4Top10PositionStatisticsYears>();
			DasTradeGts2Mt4Top10PositionStatisticsYears footer = new DasTradeGts2Mt4Top10PositionStatisticsYears();
			if(null != list && list.size()>0){
				for(Object obj: list){
					DasTradeGts2Mt4Top10PositionStatisticsYears record = (DasTradeGts2Mt4Top10PositionStatisticsYears)obj;
					record.setTimeRange(TimeRangeEnum.format(record.getTimeRange()));
					footer.setDealamount(footer.getDealamount() + record.getDealamount());
					footer.setCleanProfit(footer.getCleanProfit() + record.getCleanProfit());
					if(record.getPercent()>0D){
						footer.setPercent(100D);//有值时，肯定是100%
					}
				}
				footer.setCleanProfit(NumberUtil.m2(footer.getCleanProfit()));//保留两位有效数字
			}
			footerList.add(footer);
			PageGrid<DasTradeGts2Mt4Top10PositionStatisticsYears> pageGrid = new PageGrid<DasTradeGts2Mt4Top10PositionStatisticsYears>();
			pageGrid.setOrder(tradeSearchModel.getOrder());
			pageGrid.setSort(tradeSearchModel.getSort());
			pageGrid.setRows(list);
			pageGrid.setFooter(footerList);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasTradeGts2Mt4Top10PositionStatisticsYears>();
		}
	}
	
	/**
	 * 交易状况-当日与每月查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDaysOrMonthsSumByProfitdetail", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasTradeDealprofitdetailStatisticsYears> findDaysOrMonthsSumByProfitdetail(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			List<DasTradeDealprofitdetailStatisticsYears> list = tradeService.findDaysOrMonthsSumByProfitdetail(tradeSearchModel);
			return list;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasTradeDealprofitdetailStatisticsYears>();
		}
	}
	
	/**
	 * 跳转净入金图表报表管理页面
	 */
	@RequestMapping(value = "/cashandaccountdetailStatisticsYearsPage", method = { RequestMethod.GET })
	public String cashandaccountdetailStatisticsYearsPage(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList(this.getCompanyId(request)));
			return "/trade/gts2/companyequitymdeposit/companyequitymdepositStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 存取款及账户-净入金-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cashandaccountdetailStatisticsYearsPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> cashandaccountdetailStatisticsYearsPage(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			PageGrid<TradeSearchModel> pageGrid = tradeService.cashandaccountdetailStatisticsYearsPage(super.createPageGrid(request, tradeSearchModel));
			DasTradeGts2cashandaccountdetailStatisticsYears total = new DasTradeGts2cashandaccountdetailStatisticsYears();
			total.setPlatformtype(tradeSearchModel.getPlatformType());
			Double equitymdeposit = 0.0;
			for (Object obj : pageGrid.getRows()) {
				DasTradeGts2cashandaccountdetailStatisticsYears model = (DasTradeGts2cashandaccountdetailStatisticsYears)obj;
				equitymdeposit += Double.valueOf(model.getEquitymdeposit());
				total.setEquitymdeposit(equitymdeposit);				
			}
			List<DasTradeGts2cashandaccountdetailStatisticsYears> listFooter = new ArrayList<DasTradeGts2cashandaccountdetailStatisticsYears>();
			listFooter.add(total);
			pageGrid.setFooter(listFooter);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 存取款及账户-净入金-不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cashandaccountdetailStatisticsYearsList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasTradeGts2cashandaccountdetailStatisticsYears> cashandaccountdetailStatisticsYearsList(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			List<DasTradeGts2cashandaccountdetailStatisticsYears> list = tradeService.cashandaccountdetailStatisticsYearsList(tradeSearchModel);
			return list;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasTradeGts2cashandaccountdetailStatisticsYears>();
		}
	}

	/**
	 *  净入金图表报表记录-导出
	 */
	@RequestMapping("/exportExcelCompanyequitymdeposit")
	public void exportExcelCompanyequitymdeposit(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			String companyequitymdepositReportPath = ExportTemplateEnum.companyequitymdepositReport.getPath();
			String companyequitymdepositReportValue = ExportTemplateEnum.companyequitymdepositReport.getValue();
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(companyequitymdepositReportPath)));
			// 2、需要导出的数据
			List<DasTradeGts2cashandaccountdetailStatisticsYears> recordList = tradeService.cashandaccountdetailStatisticsYearsList(searchBean);			
			int total = recordList.size();		
			
			if(null != recordList && recordList.size() >0){
				DasTradeGts2cashandaccountdetailStatisticsYears model = new DasTradeGts2cashandaccountdetailStatisticsYears();
				Double equitymdeposit = 0.0;
				model.setPlatformtype(searchBean.getPlatformType());
				for (DasTradeGts2cashandaccountdetailStatisticsYears companyequitymdepositVO : recordList) {
					equitymdeposit += Double.valueOf(companyequitymdepositVO.getEquitymdeposit());
					model.setEquitymdeposit(equitymdeposit);
				}
				model.setExectime("小计：");
				recordList.add(model);
			}
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasTradeGts2cashandaccountdetailStatisticsYears>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasTradeGts2cashandaccountdetailStatisticsYears param) {	
					if("platformtype".equals(fieldName) && StringUtils.isBlank(String.valueOf(fieldValue))){
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
	 * 存取款及账户-当日与每月查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDaysOrMonthsSumByCashandaccountdetail", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasTradeGts2cashandaccountdetailStatisticsYears> findDaysOrMonthsSumByCashandaccountdetail(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			List<DasTradeGts2cashandaccountdetailStatisticsYears> list = tradeService.findDaysOrMonthsSumByCashandaccountdetail(tradeSearchModel);
			return list;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DasTradeGts2cashandaccountdetailStatisticsYears>();
		}
	}
	
	/**
	 * 开户及存款总结
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openAccountAndDepositSummary", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<OpenAccountAndDepositSummary> openAccountAndDepositSummary(HttpServletRequest request,
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {		
			
			// 判断endTime是否周五，如果是周五，将日期增加两天；
			boolean flag = false;
			Date endTimeDate = DateUtil.stringToDate(tradeSearchModel.getEndTime());
			String endWeekDays = DateUtil.getWeekOfDate(endTimeDate);
			if(Constants.weekDays[5].equals(endWeekDays)){//星期五-加2天
				String endTime = DateUtil.formatDateToString(DateUtil.addDays(endTimeDate, 2), "yyyy-MM-dd");
				tradeSearchModel.setEndTime(endTime);
				flag = true;
			}
			
			List<OpenAccountAndDepositSummary> list = tradeService.openAccountAndDepositSummaryList(tradeSearchModel);
			
			if(flag){
				int size = list.size();
				// 移除后面添加的日期
				list.remove(size -1);
				list.remove(size -2);
			}
			
			// 小计与总计
			List<OpenAccountAndDepositSummary> footerList = new ArrayList<OpenAccountAndDepositSummary>();
			OpenAccountAndDepositSummary footerSum = new OpenAccountAndDepositSummary();
			OpenAccountAndDepositSummary footerAvg = new OpenAccountAndDepositSummary();
			if(null != list && list.size()>0){
				for(Object obj: list){
					OpenAccountAndDepositSummary record = (OpenAccountAndDepositSummary)obj;
					footerSum.setSumactdayall(footerSum.getSumactdayall() + record.getSumactdayall());
					footerSum.setSumactday(footerSum.getSumactday() + record.getSumactday());
					footerSum.setSumactall(footerSum.getSumactall() + record.getSumactall());
					footerSum.setSumopendayreal(footerSum.getSumopendayreal() + record.getSumopendayreal());
					footerSum.setSumopenallreal(footerSum.getSumopenallreal() + record.getSumopenallreal());
					footerSum.setSumopenalldemo(footerSum.getSumopenalldemo() + record.getSumopenalldemo());
					footerSum.setSumnewaccountday(footerSum.getSumnewaccountday() + record.getSumnewaccountday());
					footerSum.setSumnewaccountmonth(footerSum.getSumnewaccountmonth() + record.getSumnewaccountmonth());
					footerSum.setSummdeposit(footerSum.getSummdeposit() + record.getSummdeposit());
					footerSum.setSumwithdraw(footerSum.getSumwithdraw() + record.getSumwithdraw());
					footerSum.setEquitymdeposit(footerSum.getEquitymdeposit() + record.getEquitymdeposit());
					footerSum.setOnlinepayment(footerSum.getOnlinepayment() + record.getOnlinepayment());
					footerSum.setReimbursement(footerSum.getReimbursement() + record.getReimbursement());
					footerSum.setBalance(footerSum.getBalance() + record.getBalance());
					footerSum.setAdvisoryCustomerCount(footerSum.getAdvisoryCustomerCount() + record.getAdvisoryCustomerCount());
					footerSum.setAdvisoryCount(footerSum.getAdvisoryCount() + record.getAdvisoryCount());
					footerSum.setCustomerPhoneCount(footerSum.getCustomerPhoneCount() + record.getCustomerPhoneCount());
				}
				//保留两位有效数字
				footerSum.setDatetime("总数：");
				footerSum.setSumnewaccountday(NumberUtil.m2(footerSum.getSumnewaccountday()));
				footerSum.setSumnewaccountmonth(NumberUtil.m2(footerSum.getSumnewaccountmonth()));
				footerSum.setSummdeposit(NumberUtil.m2(footerSum.getSummdeposit()));
				footerSum.setSumwithdraw(NumberUtil.m2(footerSum.getSumwithdraw()));
				footerSum.setEquitymdeposit(NumberUtil.m2(footerSum.getEquitymdeposit()));
				footerSum.setOnlinepayment(NumberUtil.m2(footerSum.getOnlinepayment()));
				footerSum.setReimbursement(NumberUtil.m2(footerSum.getReimbursement()));
				footerSum.setBalance(NumberUtil.m2(footerSum.getBalance()));
				// avg
				footerAvg.setDatetime("平均：");
				footerAvg.setSumactday(footerSum.getSumactday()/list.size());
				footerAvg.setSumactall(footerSum.getSumactall()/list.size());
				footerAvg.setSumopendayreal(footerSum.getSumopendayreal()/list.size());
				footerAvg.setSumopenallreal(footerSum.getSumopenallreal()/list.size());
				footerAvg.setSumopenalldemo(footerSum.getSumopenalldemo()/list.size());
				footerAvg.setSumnewaccountday(NumberUtil.m2(footerSum.getSumnewaccountday()/list.size()));
				footerAvg.setSumnewaccountmonth(NumberUtil.m2(footerSum.getSumnewaccountmonth()/list.size()));
				footerAvg.setSummdeposit(NumberUtil.m2(footerSum.getSummdeposit()/list.size()));
				footerAvg.setSumwithdraw(NumberUtil.m2(footerSum.getSumwithdraw()/list.size()));
				footerAvg.setEquitymdeposit(NumberUtil.m2(footerSum.getEquitymdeposit()/list.size()));
				footerAvg.setOnlinepayment(NumberUtil.m2(footerSum.getOnlinepayment()/list.size()));
				footerAvg.setReimbursement(NumberUtil.m2(footerSum.getReimbursement()/list.size()));
				footerAvg.setBalance(NumberUtil.m2(footerSum.getBalance()/list.size()));
				footerAvg.setAdvisoryCustomerCount(footerSum.getAdvisoryCustomerCount()/list.size());
				footerAvg.setAdvisoryCount(footerSum.getAdvisoryCount()/list.size());
				footerAvg.setCustomerPhoneCount(footerSum.getCustomerPhoneCount()/list.size());
			}
			footerList.add(footerSum);
			footerList.add(footerAvg);
			PageGrid<OpenAccountAndDepositSummary> pageGrid = new PageGrid<OpenAccountAndDepositSummary>();
			pageGrid.setOrder(tradeSearchModel.getOrder());
			pageGrid.setSort(tradeSearchModel.getSort());
			pageGrid.setRows(list);
			pageGrid.setFooter(footerList);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<OpenAccountAndDepositSummary>();
		}
	}
	
	/**
	 * 交易记录\存取款及账户\导出
	 * 
	 * @return
	 */
	@RequestMapping("/exportBusinessExcel")
	public void exportBusinessExcel(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 1、需要导出的数据
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			
			// 交易记录-交易状况及市场状况
			tradeSearchModel.setPlatformType("");
			List<DasTradeDealprofitdetailStatisticsYears> list_1 = tradeService.findDaysOrMonthsSumByProfitdetail(tradeSearchModel);
			// 交易记录-MT4
			tradeSearchModel.setPlatformType(PlatformtypeEnum.MT4.getLabelKey());
			List<DasTradeDealprofitdetailStatisticsYears> list_2 = tradeService.findDaysOrMonthsSumByProfitdetail(tradeSearchModel);
			// 交易记录-GTS2
			tradeSearchModel.setPlatformType(PlatformtypeEnum.GTS2.getLabelKey());
			List<DasTradeDealprofitdetailStatisticsYears> list_3 = tradeService.findDaysOrMonthsSumByProfitdetail(tradeSearchModel);
			// 存取款及账户-客户状况
			tradeSearchModel.setPlatformType("");
			List<DasTradeGts2cashandaccountdetailStatisticsYears> list_4 = tradeService.findDaysOrMonthsSumByCashandaccountdetail(tradeSearchModel);
			// 存取款及账户-MT4
			tradeSearchModel.setPlatformType(PlatformtypeEnum.MT4.getLabelKey());
			List<DasTradeGts2cashandaccountdetailStatisticsYears> list_5 = tradeService.findDaysOrMonthsSumByCashandaccountdetail(tradeSearchModel);
			// 存取款及账户-GTS2
			tradeSearchModel.setPlatformType(PlatformtypeEnum.GTS2.getLabelKey());
			List<DasTradeGts2cashandaccountdetailStatisticsYears> list_6 = tradeService.findDaysOrMonthsSumByCashandaccountdetail(tradeSearchModel);
			
			// 2、格式化数据与导出
			// 定义excel工作簿-只写入
			SXSSFWorkbook wb = new SXSSFWorkbook(100);
			// create sheet
			Sheet sheet = wb.createSheet("交易记录&存取款及账户");// sheet名称
			sheet.setColumnWidth(0, 25*256);// 设置列宽
			sheet.setColumnWidth(1, 15*256);// 设置列宽
			sheet.setColumnWidth(2, 32*256);// 设置列宽
			sheet.setColumnWidth(3, 15*256);// 设置列宽
			sheet.setColumnWidth(6, 25*256);// 设置列宽
			sheet.setColumnWidth(7, 15*256);// 设置列宽
			sheet.setColumnWidth(8, 32*256);// 设置列宽
			sheet.setColumnWidth(9, 15*256);// 设置列宽
			
			// 创建SXSSFWorkbook.createRow时的下标
			int createRowNum = 0;

			// 合并单元格
			CellRangeAddress cra_T1 = new CellRangeAddress(createRowNum, createRowNum, 0, 9);
			sheet.addMergedRegion(cra_T1);
			// 合并单元格title
			Row cellTitleRow_0 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_0, 0).setCellValue(">>交易记录");
			// 合并单元格
			CellRangeAddress cra_1 = new CellRangeAddress(createRowNum, createRowNum, 0, 3);
			CellRangeAddress cra_2 = new CellRangeAddress(createRowNum, createRowNum, 6, 9);
			sheet.addMergedRegion(cra_1);
			sheet.addMergedRegion(cra_2);
			// 合并单元格title
			Row cellTitleRow_1 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_1, 0).setCellValue("交易状况");
			this.createCellT(wb, cellTitleRow_1, 6).setCellValue("MT4");
			// 表格title
			Row titleRow_1 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_2 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_3 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_4 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_5 = sheet.createRow(createRowNum);
			createRowNum ++;
			// 20170928新增需求
			Row titleRow_6_0 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_6 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_7 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_8 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_9 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_10 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_11 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_12 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_13 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_14 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_15 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_16 = sheet.createRow(createRowNum);
			createRowNum ++;
			// 放入数据
			DasTradeDealprofitdetailStatisticsYears days = list_1.get(0);
			DasTradeDealprofitdetailStatisticsYears months = list_1.get(1);
			DasTradeDealprofitdetailStatisticsYears days_mt4 = list_2.get(0);
			DasTradeDealprofitdetailStatisticsYears months_mt4 = list_2.get(1);
			this.createCell(wb, titleRow_1, 0).setCellValue("当日毛利");
			this.createCell(wb, titleRow_1, 1).setCellValue(NumberUtil.m2(days.getGrossprofit() 
					- days.getHandOperHedgeProfitAndLossGts2() - days.getHandOperHedgeProfitAndLossMt4()));
			this.createCell(wb, titleRow_1, 2).setCellValue("当月毛利(扣除推广赠金及赠金清零)");
			this.createCell(wb, titleRow_1, 3).setCellValue(NumberUtil.m2(months.getGrossprofit()
					- months.getHandOperHedgeProfitAndLossGts2() - months.getHandOperHedgeProfitAndLossMt4() 
					- months.getBonusOtherGts2() - months.getBonusOtherMt4()));
			this.createCell(wb, titleRow_1, 6).setCellValue("当日盈亏");
			this.createCell(wb, titleRow_1, 7).setCellValue(days_mt4.getCompanyprofit());
			this.createCell(wb, titleRow_1, 8).setCellValue("当月盈亏");
			this.createCell(wb, titleRow_1, 9).setCellValue(months_mt4.getCompanyprofit());

			this.createCell(wb, titleRow_2, 0).setCellValue("当日实现盈亏");
			this.createCell(wb, titleRow_2, 1).setCellValue(days.getCompanyprofit());
			this.createCell(wb, titleRow_2, 2).setCellValue("当月实现盈亏");
			this.createCell(wb, titleRow_2, 3).setCellValue(months.getCompanyprofit());
			this.createCell(wb, titleRow_2, 6).setCellValue("当日净盈亏");
			this.createCell(wb, titleRow_2, 7).setCellValue(days_mt4.getNetCompanyprofit());
			this.createCell(wb, titleRow_2, 8).setCellValue("当月净盈亏");
			this.createCell(wb, titleRow_2, 9).setCellValue(months_mt4.getNetCompanyprofit());
			
			this.createCell(wb, titleRow_3, 0).setCellValue("当日净盈亏");
			this.createCell(wb, titleRow_3, 1).setCellValue(days.getNetCompanyprofit());
			this.createCell(wb, titleRow_3, 2).setCellValue("当月净盈亏");
			this.createCell(wb, titleRow_3, 3).setCellValue(months.getNetCompanyprofit());
			this.createCell(wb, titleRow_3, 6).setCellValue("当日浮动盈亏");
			this.createCell(wb, titleRow_3, 7).setCellValue(days_mt4.getFloatingprofit());

			this.createCell(wb, titleRow_4, 0).setCellValue("当日实现利息");
			this.createCell(wb, titleRow_4, 1).setCellValue(days.getSwap());
			this.createCell(wb, titleRow_4, 2).setCellValue("当月实现利息");
			this.createCell(wb, titleRow_4, 3).setCellValue(months.getSwap());
			this.createCell(wb, titleRow_4, 6).setCellValue("当日利息收入");
			this.createCell(wb, titleRow_4, 7).setCellValue(days_mt4.getSwap());
			this.createCell(wb, titleRow_4, 8).setCellValue("当月利息收入");
			this.createCell(wb, titleRow_4, 9).setCellValue(months_mt4.getSwap());

			this.createCell(wb, titleRow_5, 0).setCellValue("当日系统清零");
			this.createCell(wb, titleRow_5, 1).setCellValue(days.getSysclearzero());
			this.createCell(wb, titleRow_5, 2).setCellValue("当月系统清零");
			this.createCell(wb, titleRow_5, 3).setCellValue(months.getSysclearzero());
			this.createCell(wb, titleRow_5, 6).setCellValue("当日系统清零");
			this.createCell(wb, titleRow_5, 7).setCellValue(days_mt4.getSysclearzero());
			this.createCell(wb, titleRow_5, 8).setCellValue("当月系统清零");
			this.createCell(wb, titleRow_5, 9).setCellValue(months_mt4.getSysclearzero());

			// 20170928新增需求
			this.createCell(wb, titleRow_6_0, 0).setCellValue("当日手动对冲盈亏");
			this.createCell(wb, titleRow_6_0, 1).setCellValue(NumberUtil.m2(days.getHandOperHedgeProfitAndLossGts2() + days.getHandOperHedgeProfitAndLossMt4()));
			this.createCell(wb, titleRow_6_0, 2).setCellValue("当月手动对冲盈亏");
			this.createCell(wb, titleRow_6_0, 3).setCellValue(NumberUtil.m2(months.getHandOperHedgeProfitAndLossGts2() + months.getHandOperHedgeProfitAndLossMt4()));
			this.createCell(wb, titleRow_6_0, 6).setCellValue("当日手动对冲盈亏");
			this.createCell(wb, titleRow_6_0, 7).setCellValue(days_mt4.getHandOperHedgeProfitAndLossMt4());
			this.createCell(wb, titleRow_6_0, 8).setCellValue("当月手动对冲盈亏");
			this.createCell(wb, titleRow_6_0, 9).setCellValue(months_mt4.getHandOperHedgeProfitAndLossMt4());
			
			this.createCell(wb, titleRow_6, 0).setCellValue("当日总交易手数");
			this.createCell(wb, titleRow_6, 1).setCellValue(days.getVolume());
			this.createCell(wb, titleRow_6, 2).setCellValue("当月总交易手数");
			this.createCell(wb, titleRow_6, 3).setCellValue(months.getVolume());
			this.createCell(wb, titleRow_6, 6).setCellValue("当日回赠");
			this.createCell(wb, titleRow_6, 7).setCellValue(days_mt4.getCommission());
			this.createCell(wb, titleRow_6, 8).setCellValue("当月回赠");
			this.createCell(wb, titleRow_6, 9).setCellValue(months_mt4.getCommission());

			this.createCell(wb, titleRow_7, 0).setCellValue("浮动盈亏");
			this.createCell(wb, titleRow_7, 1).setCellValue(days.getFloatingprofit());
			this.createCell(wb, titleRow_7, 6).setCellValue("当日毛利");
			this.createCell(wb, titleRow_7, 7).setCellValue(NumberUtil.m2(days_mt4.getGrossprofit() 
					- days_mt4.getHandOperHedgeProfitAndLossMt4()));
			this.createCell(wb, titleRow_7, 8).setCellValue("当月毛利(扣除推广赠金及赠金清零)");
			this.createCell(wb, titleRow_7, 9).setCellValue(NumberUtil.m2(months_mt4.getGrossprofit() 
					- months_mt4.getHandOperHedgeProfitAndLossMt4() - months_mt4.getBonusOtherMt4()));

			this.createCell(wb, titleRow_8, 0).setCellValue("当日交易盈亏(金)");
			this.createCell(wb, titleRow_8, 1).setCellValue(days.getGoldprofit());
			this.createCell(wb, titleRow_8, 2).setCellValue("当月交易盈亏(金)");
			this.createCell(wb, titleRow_8, 3).setCellValue(months.getGoldprofit());
			this.createCell(wb, titleRow_8, 6).setCellValue("当日交易盈亏(金)");
			this.createCell(wb, titleRow_8, 7).setCellValue(days_mt4.getGoldprofit());
			this.createCell(wb, titleRow_8, 8).setCellValue("当月交易盈亏(金)");
			this.createCell(wb, titleRow_8, 9).setCellValue(months_mt4.getGoldprofit());

			this.createCell(wb, titleRow_9, 0).setCellValue("当日交易盈亏(银)");
			this.createCell(wb, titleRow_9, 1).setCellValue(days.getSilverprofit());
			this.createCell(wb, titleRow_9, 2).setCellValue("当月交易盈亏(银)");
			this.createCell(wb, titleRow_9, 3).setCellValue(months.getSilverprofit());
			this.createCell(wb, titleRow_9, 6).setCellValue("当日交易盈亏(银)");
			this.createCell(wb, titleRow_9, 7).setCellValue(days_mt4.getSilverprofit());
			this.createCell(wb, titleRow_9, 8).setCellValue("当月交易盈亏(银)");
			this.createCell(wb, titleRow_9, 9).setCellValue(months_mt4.getSilverprofit());

			this.createCell(wb, titleRow_10, 0).setCellValue("当日交易盈亏(人民币金)");
			this.createCell(wb, titleRow_10, 1).setCellValue(days.getXaucnhprofit());
			this.createCell(wb, titleRow_10, 2).setCellValue("当月交易盈亏(人民币金)");
			this.createCell(wb, titleRow_10, 3).setCellValue(months.getXaucnhprofit());
			this.createCell(wb, titleRow_10, 6).setCellValue("当日交易盈亏(人民币金)");
			this.createCell(wb, titleRow_10, 7).setCellValue(days_mt4.getXaucnhprofit());
			this.createCell(wb, titleRow_10, 8).setCellValue("当月交易盈亏(人民币金)");
			this.createCell(wb, titleRow_10, 9).setCellValue(months_mt4.getXaucnhprofit());

			this.createCell(wb, titleRow_11, 0).setCellValue("当日交易盈亏(人民币银)");
			this.createCell(wb, titleRow_11, 1).setCellValue(days.getXagcnhprofit());
			this.createCell(wb, titleRow_11, 2).setCellValue("当月交易盈亏(人民币银)");
			this.createCell(wb, titleRow_11, 3).setCellValue(months.getXagcnhprofit());
			this.createCell(wb, titleRow_11, 6).setCellValue("当日交易盈亏(人民币银)");
			this.createCell(wb, titleRow_11, 7).setCellValue(days_mt4.getXagcnhprofit());
			this.createCell(wb, titleRow_11, 8).setCellValue("当月交易盈亏(人民币银)");
			this.createCell(wb, titleRow_11, 9).setCellValue(months_mt4.getXagcnhprofit());
			
			this.createCell(wb, titleRow_12, 0).setCellValue("当日交易手数(金)");
			this.createCell(wb, titleRow_12, 1).setCellValue(days.getGoldvolume());
			this.createCell(wb, titleRow_12, 2).setCellValue("当月交易手数(金)");
			this.createCell(wb, titleRow_12, 3).setCellValue(months.getGoldvolume());
			this.createCell(wb, titleRow_12, 6).setCellValue("当日交易手数(金)");
			this.createCell(wb, titleRow_12, 7).setCellValue(days_mt4.getGoldvolume());
			this.createCell(wb, titleRow_12, 8).setCellValue("当月交易手数(金)");
			this.createCell(wb, titleRow_12, 9).setCellValue(months_mt4.getGoldvolume());
			
			this.createCell(wb, titleRow_13, 0).setCellValue("当日交易手数(银)");
			this.createCell(wb, titleRow_13, 1).setCellValue(days.getSilvervolume());
			this.createCell(wb, titleRow_13, 2).setCellValue("当月交易手数(银)");
			this.createCell(wb, titleRow_13, 3).setCellValue(months.getSilvervolume());
			this.createCell(wb, titleRow_13, 6).setCellValue("当日交易手数(银)");
			this.createCell(wb, titleRow_13, 7).setCellValue(days_mt4.getSilvervolume());
			this.createCell(wb, titleRow_13, 8).setCellValue("当月交易手数(银)");
			this.createCell(wb, titleRow_13, 9).setCellValue(months_mt4.getSilvervolume());
			
			this.createCell(wb, titleRow_14, 0).setCellValue("当日交易手数(人民币金)");
			this.createCell(wb, titleRow_14, 1).setCellValue(days.getXaucnhvolume());
			this.createCell(wb, titleRow_14, 2).setCellValue("当月交易手数(人民币金)");
			this.createCell(wb, titleRow_14, 3).setCellValue(months.getXaucnhvolume());
			this.createCell(wb, titleRow_14, 6).setCellValue("当日交易手数(人民币金)");
			this.createCell(wb, titleRow_14, 7).setCellValue(days_mt4.getXaucnhvolume());
			this.createCell(wb, titleRow_14, 8).setCellValue("当月交易手数(人民币金)");
			this.createCell(wb, titleRow_14, 9).setCellValue(months_mt4.getXaucnhvolume());

			this.createCell(wb, titleRow_15, 0).setCellValue("当日交易手数(人民币银)");
			this.createCell(wb, titleRow_15, 1).setCellValue(days.getXagcnhvolume());
			this.createCell(wb, titleRow_15, 2).setCellValue("当月交易手数(人民币银)");
			this.createCell(wb, titleRow_15, 3).setCellValue(months.getXagcnhvolume());
			this.createCell(wb, titleRow_15, 6).setCellValue("当日交易手数(人民币银)");
			this.createCell(wb, titleRow_15, 7).setCellValue(days_mt4.getXagcnhvolume());
			this.createCell(wb, titleRow_15, 8).setCellValue("当月交易手数(人民币银)");
			this.createCell(wb, titleRow_15, 9).setCellValue(months_mt4.getXagcnhvolume());
			
			this.createCell(wb, titleRow_16, 6).setCellValue("当日总交易手数");
			this.createCell(wb, titleRow_16, 7).setCellValue(days_mt4.getVolume());
			this.createCell(wb, titleRow_16, 8).setCellValue("当月总交易手数");
			this.createCell(wb, titleRow_16, 9).setCellValue(months_mt4.getVolume());

			// 合并单元格
			CellRangeAddress cra_3 = new CellRangeAddress(createRowNum, createRowNum, 0, 3);
			CellRangeAddress cra_4 = new CellRangeAddress(createRowNum, createRowNum, 6, 9);
			sheet.addMergedRegion(cra_3);
			sheet.addMergedRegion(cra_4);
			// 合并单元格title
			Row cellTitleRow_2 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_2, 0).setCellValue("市场状况");
			this.createCellT(wb, cellTitleRow_2, 6).setCellValue("GTS2");
			// 表格title
			
			// 20170928新增需求
			Row titleRow_17_0 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_17 = sheet.createRow(createRowNum);
			createRowNum ++;
			// 20170928新增需求
			Row titleRow_18_0 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_18 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_19 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_20 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_21 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_22 = sheet.createRow(createRowNum);
			createRowNum ++;
			// 20170928新增需求
			Row titleRow_22_0 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_23 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_24 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_25 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_26 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_27 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_28 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_29 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_30 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_31 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_32 = sheet.createRow(createRowNum);
			createRowNum ++;
			// 放入数据
			DasTradeDealprofitdetailStatisticsYears days_gts2 = list_3.get(0);
			DasTradeDealprofitdetailStatisticsYears months_gts2 = list_3.get(1);

			// 20170928新增需求
			this.createCell(wb, titleRow_17_0, 0).setCellValue("当日活动推广赠金(其它)");
			this.createCell(wb, titleRow_17_0, 1).setCellValue(NumberUtil.m2(days.getBonusOtherGts2() + days.getBonusOtherMt4()));
			this.createCell(wb, titleRow_17_0, 2).setCellValue("当月活动推广赠金(其它)");
			this.createCell(wb, titleRow_17_0, 3).setCellValue(NumberUtil.m2(months.getBonusOtherGts2() + months.getBonusOtherMt4()));
			
			this.createCell(wb, titleRow_17, 0).setCellValue("当日活动推广赠金");
			this.createCell(wb, titleRow_17, 1).setCellValue(days.getBonus());
			this.createCell(wb, titleRow_17, 2).setCellValue("当月活动推广赠金");
			this.createCell(wb, titleRow_17, 3).setCellValue(months.getBonus());
			this.createCell(wb, titleRow_17, 6).setCellValue("当日盈亏");
			this.createCell(wb, titleRow_17, 7).setCellValue(days_gts2.getCompanyprofit());
			this.createCell(wb, titleRow_17, 8).setCellValue("当月盈亏");
			this.createCell(wb, titleRow_17, 9).setCellValue(months_gts2.getCompanyprofit());

			// 20170928新增需求
			this.createCell(wb, titleRow_18_0, 0).setCellValue("当日累计活动推广赠金");
			this.createCell(wb, titleRow_18_0, 1).setCellValue(NumberUtil.m2(days.getBonus() + days.getBonusOtherGts2() + days.getBonusOtherMt4()));
			this.createCell(wb, titleRow_18_0, 2).setCellValue("当月累计活动推广赠金");
			this.createCell(wb, titleRow_18_0, 3).setCellValue(NumberUtil.m2(months.getBonus() + months.getBonusOtherGts2() + months.getBonusOtherMt4()));
			
			this.createCell(wb, titleRow_18, 0).setCellValue("当日赠金活动清零");
			this.createCell(wb, titleRow_18, 1).setCellValue(days.getBonusclear());
			this.createCell(wb, titleRow_18, 2).setCellValue("当月赠金活动清零");
			this.createCell(wb, titleRow_18, 3).setCellValue(months.getBonusclear());
			this.createCell(wb, titleRow_18, 6).setCellValue("当日净盈亏");
			this.createCell(wb, titleRow_18, 7).setCellValue(days_gts2.getNetCompanyprofit());
			this.createCell(wb, titleRow_18, 8).setCellValue("当月净盈亏");
			this.createCell(wb, titleRow_18, 9).setCellValue(months_gts2.getNetCompanyprofit());
			
			this.createCell(wb, titleRow_19, 0).setCellValue("当日调整");
			this.createCell(wb, titleRow_19, 1).setCellValue(days.getAdjustfixedamount());
			this.createCell(wb, titleRow_19, 2).setCellValue("当月调整");
			this.createCell(wb, titleRow_19, 3).setCellValue(months.getAdjustfixedamount());
			this.createCell(wb, titleRow_19, 6).setCellValue("当日浮动盈亏");
			this.createCell(wb, titleRow_19, 7).setCellValue(days_gts2.getFloatingprofit());

			this.createCell(wb, titleRow_20, 0).setCellValue("当日回赠");
			this.createCell(wb, titleRow_20, 1).setCellValue(days.getCommission());
			this.createCell(wb, titleRow_20, 2).setCellValue("当月回赠");
			this.createCell(wb, titleRow_20, 3).setCellValue(months.getCommission());
			this.createCell(wb, titleRow_20, 6).setCellValue("当日利息收入");
			this.createCell(wb, titleRow_20, 7).setCellValue(days_gts2.getSwap());
			this.createCell(wb, titleRow_20, 8).setCellValue("当月利息收入");
			this.createCell(wb, titleRow_20, 9).setCellValue(months_gts2.getSwap());

			// 20170928新增需求
			this.createCell(wb, titleRow_21, 0).setCellValue("当日网站总访问量");
			this.createCell(wb, titleRow_21, 1).setCellValue(days.getSiteVisitsCount());
			this.createCell(wb, titleRow_21, 2).setCellValue("当月网站总访问量");
			this.createCell(wb, titleRow_21, 3).setCellValue(months.getSiteVisitsCount());
			this.createCell(wb, titleRow_21, 6).setCellValue("当日系统清零");
			this.createCell(wb, titleRow_21, 7).setCellValue(days_gts2.getSysclearzero());
			this.createCell(wb, titleRow_21, 8).setCellValue("当月系统清零");
			this.createCell(wb, titleRow_21, 9).setCellValue(months_gts2.getSysclearzero());
			
			this.createCell(wb, titleRow_22, 0).setCellValue("当日渠道推广费用");
			this.createCell(wb, titleRow_22, 1).setCellValue(days.getChannelPromotionCosts());
			this.createCell(wb, titleRow_22, 2).setCellValue("当月渠道推广费用");
			this.createCell(wb, titleRow_22, 3).setCellValue(months.getChannelPromotionCosts());
			this.createCell(wb, titleRow_22, 6).setCellValue("当日手动对冲盈亏");
			this.createCell(wb, titleRow_22, 7).setCellValue(days_gts2.getHandOperHedgeProfitAndLossGts2());
			this.createCell(wb, titleRow_22, 8).setCellValue("当月手动对冲盈亏");
			this.createCell(wb, titleRow_22, 9).setCellValue(months_gts2.getHandOperHedgeProfitAndLossGts2());
			
			this.createCell(wb, titleRow_22_0, 6).setCellValue("当日回赠");
			this.createCell(wb, titleRow_22_0, 7).setCellValue(days_gts2.getCommission());
			this.createCell(wb, titleRow_22_0, 8).setCellValue("当月回赠");
			this.createCell(wb, titleRow_22_0, 9).setCellValue(months_gts2.getCommission());

			this.createCell(wb, titleRow_23, 6).setCellValue("当日毛利");
			this.createCell(wb, titleRow_23, 7).setCellValue(NumberUtil.m2(days_gts2.getGrossprofit() 
					- days_gts2.getHandOperHedgeProfitAndLossGts2()));
			this.createCell(wb, titleRow_23, 8).setCellValue("当月毛利(扣除推广赠金及赠金清零)");
			this.createCell(wb, titleRow_23, 9).setCellValue(NumberUtil.m2(months_gts2.getGrossprofit() 
					- months_gts2.getHandOperHedgeProfitAndLossGts2() - months_gts2.getBonusOtherGts2()));

			this.createCell(wb, titleRow_24, 6).setCellValue("当日交易盈亏(金)");
			this.createCell(wb, titleRow_24, 7).setCellValue(days_gts2.getGoldprofit());
			this.createCell(wb, titleRow_24, 8).setCellValue("当月交易盈亏(金)");
			this.createCell(wb, titleRow_24, 9).setCellValue(months_gts2.getGoldprofit());

			this.createCell(wb, titleRow_25, 6).setCellValue("当日交易盈亏(银)");
			this.createCell(wb, titleRow_25, 7).setCellValue(days_gts2.getSilverprofit());
			this.createCell(wb, titleRow_25, 8).setCellValue("当月交易盈亏(银)");
			this.createCell(wb, titleRow_25, 9).setCellValue(months_gts2.getSilverprofit());

			this.createCell(wb, titleRow_26, 6).setCellValue("当日交易盈亏(人民币金)");
			this.createCell(wb, titleRow_26, 7).setCellValue(days_gts2.getXaucnhprofit());
			this.createCell(wb, titleRow_26, 8).setCellValue("当月交易盈亏(人民币金)");
			this.createCell(wb, titleRow_26, 9).setCellValue(months_gts2.getXaucnhprofit());

			this.createCell(wb, titleRow_27, 6).setCellValue("当日交易盈亏(人民币银)");
			this.createCell(wb, titleRow_27, 7).setCellValue(days_gts2.getXagcnhprofit());
			this.createCell(wb, titleRow_27, 8).setCellValue("当月交易盈亏(人民币银)");
			this.createCell(wb, titleRow_27, 9).setCellValue(months_gts2.getXagcnhprofit());
			
			this.createCell(wb, titleRow_28, 6).setCellValue("当日交易手数(金)");
			this.createCell(wb, titleRow_28, 7).setCellValue(days_gts2.getGoldvolume());
			this.createCell(wb, titleRow_28, 8).setCellValue("当月交易手数(金)");
			this.createCell(wb, titleRow_28, 9).setCellValue(months_gts2.getGoldvolume());
			
			this.createCell(wb, titleRow_29, 6).setCellValue("当日交易手数(银)");
			this.createCell(wb, titleRow_29, 7).setCellValue(days_gts2.getSilvervolume());
			this.createCell(wb, titleRow_29, 8).setCellValue("当月交易手数(银)");
			this.createCell(wb, titleRow_29, 9).setCellValue(months_gts2.getSilvervolume());
			
			this.createCell(wb, titleRow_30, 6).setCellValue("当日交易手数(人民币金)");
			this.createCell(wb, titleRow_30, 7).setCellValue(days_gts2.getXaucnhvolume());
			this.createCell(wb, titleRow_30, 8).setCellValue("当月交易手数(人民币金)");
			this.createCell(wb, titleRow_30, 9).setCellValue(months_gts2.getXaucnhvolume());

			this.createCell(wb, titleRow_31, 6).setCellValue("当日交易手数(人民币银)");
			this.createCell(wb, titleRow_31, 7).setCellValue(days_gts2.getXagcnhvolume());
			this.createCell(wb, titleRow_31, 8).setCellValue("当月交易手数(人民币银)");
			this.createCell(wb, titleRow_31, 9).setCellValue(months_gts2.getXagcnhvolume());
			
			this.createCell(wb, titleRow_32, 6).setCellValue("当日总交易手数");
			this.createCell(wb, titleRow_32, 7).setCellValue(days_gts2.getVolume());
			this.createCell(wb, titleRow_32, 8).setCellValue("当月总交易手数");
			this.createCell(wb, titleRow_32, 9).setCellValue(months_gts2.getVolume());
			
			// 合并单元格
			CellRangeAddress cra_T2 = new CellRangeAddress(createRowNum, createRowNum, 0, 9);
			sheet.addMergedRegion(cra_T2);
			// 合并单元格title
			Row cellTitleRow_3 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_3, 0).setCellValue(">>存取款及账户");
			// 合并单元格
			CellRangeAddress cra_5 = new CellRangeAddress(createRowNum, createRowNum, 0, 3);
			CellRangeAddress cra_6 = new CellRangeAddress(createRowNum, createRowNum, 6, 9);
			sheet.addMergedRegion(cra_5);
			sheet.addMergedRegion(cra_6);
			// 合并单元格title
			Row cellTitleRow_4 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_4, 0).setCellValue("客户状况");
			this.createCellT(wb, cellTitleRow_4, 6).setCellValue("MT4");
			// 表格title
			Row titleRow_33 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_34 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_35 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_36 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_37 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_38 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_39 = sheet.createRow(createRowNum);
			createRowNum ++;
			// 20170928新增需求
			Row titleRow_40_0 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_40 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_40_1 = sheet.createRow(createRowNum);
			createRowNum ++;
			// 放入数据
			DasTradeGts2cashandaccountdetailStatisticsYears days_1 = list_4.get(0);
			DasTradeGts2cashandaccountdetailStatisticsYears months_1 = list_4.get(1);
			DasTradeGts2cashandaccountdetailStatisticsYears days_1_mt4 = list_5.get(0);
			DasTradeGts2cashandaccountdetailStatisticsYears months_1_mt4 = list_5.get(1);
			this.createCell(wb, titleRow_33, 0).setCellValue("客户账户结馀");
			this.createCell(wb, titleRow_33, 1).setCellValue(days_1.getPreviousbalance());
			this.createCell(wb, titleRow_33, 2).setCellValue("");
			this.createCell(wb, titleRow_33, 3).setCellValue("");
			this.createCell(wb, titleRow_33, 6).setCellValue("当日激活账户");
			this.createCell(wb, titleRow_33, 7).setCellValue(days_1_mt4.getSumactday());
			this.createCell(wb, titleRow_33, 8).setCellValue("当月激活账户");
			this.createCell(wb, titleRow_33, 9).setCellValue(months_1_mt4.getSumactday());

			this.createCell(wb, titleRow_34, 0).setCellValue("当日净存款");
			this.createCell(wb, titleRow_34, 1).setCellValue(days_1.getEquitymdeposit());
			this.createCell(wb, titleRow_34, 2).setCellValue("当月净存款");
			this.createCell(wb, titleRow_34, 3).setCellValue(months_1.getEquitymdeposit());
			this.createCell(wb, titleRow_34, 6).setCellValue("累计激活账户");
			this.createCell(wb, titleRow_34, 7).setCellValue(days_1_mt4.getSumactall());
			this.createCell(wb, titleRow_34, 8).setCellValue("当月累计总开户");
			this.createCell(wb, titleRow_34, 9).setCellValue(months_1_mt4.getSumopenall());

			this.createCell(wb, titleRow_35, 0).setCellValue("当日总存款");
			this.createCell(wb, titleRow_35, 1).setCellValue(days_1.getSummdeposit());
			this.createCell(wb, titleRow_35, 2).setCellValue("当月总存款");
			this.createCell(wb, titleRow_35, 3).setCellValue(months_1.getSummdeposit());
			this.createCell(wb, titleRow_35, 6).setCellValue("当日激活账户存款");
			this.createCell(wb, titleRow_35, 7).setCellValue(days_1_mt4.getSumnewaccount());
			this.createCell(wb, titleRow_35, 8).setCellValue("当月激活账户存款");
			this.createCell(wb, titleRow_35, 9).setCellValue(months_1_mt4.getSumnewaccount());

			this.createCell(wb, titleRow_36, 0).setCellValue("当日总取款");
			this.createCell(wb, titleRow_36, 1).setCellValue(days_1.getSumwithdraw());
			this.createCell(wb, titleRow_36, 2).setCellValue("当月总取款");
			this.createCell(wb, titleRow_36, 3).setCellValue(months_1.getSumwithdraw());
			this.createCell(wb, titleRow_36, 6).setCellValue("当日净存款");
			this.createCell(wb, titleRow_36, 7).setCellValue(days_1_mt4.getEquitymdeposit());
			this.createCell(wb, titleRow_36, 8).setCellValue("当月净存款");
			this.createCell(wb, titleRow_36, 9).setCellValue(months_1_mt4.getEquitymdeposit());

			this.createCell(wb, titleRow_37, 0).setCellValue("当日激活账户存款");
			this.createCell(wb, titleRow_37, 1).setCellValue(days_1.getSumnewaccount());
			this.createCell(wb, titleRow_37, 2).setCellValue("当月激活账户存款");
			this.createCell(wb, titleRow_37, 3).setCellValue(months_1.getSumnewaccount());
			this.createCell(wb, titleRow_37, 6).setCellValue("当日总存款");
			this.createCell(wb, titleRow_37, 7).setCellValue(days_1_mt4.getSummdeposit());
			this.createCell(wb, titleRow_37, 8).setCellValue("当月总存款");
			this.createCell(wb, titleRow_37, 9).setCellValue(months_1_mt4.getSummdeposit());

			this.createCell(wb, titleRow_38, 0).setCellValue("当日激活账户");
			this.createCell(wb, titleRow_38, 1).setCellValue(days_1.getSumactday());
			this.createCell(wb, titleRow_38, 2).setCellValue("当月激活账户");
			this.createCell(wb, titleRow_38, 3).setCellValue(months_1.getSumactday());
			this.createCell(wb, titleRow_38, 6).setCellValue("当日总取款");
			this.createCell(wb, titleRow_38, 7).setCellValue(days_1_mt4.getSumwithdraw());
			this.createCell(wb, titleRow_38, 8).setCellValue("当月总取款");
			this.createCell(wb, titleRow_38, 9).setCellValue(months_1_mt4.getSumwithdraw());
			
			this.createCell(wb, titleRow_39, 0).setCellValue("累计激活账户");
			this.createCell(wb, titleRow_39, 1).setCellValue(days_1.getSumactall());
			this.createCell(wb, titleRow_39, 6).setCellValue("当日结馀");
			this.createCell(wb, titleRow_39, 7).setCellValue(days_1_mt4.getPreviousbalance());
			
			// 20170928新增需求
			this.createCell(wb, titleRow_40_0, 0).setCellValue("当日客户对话量");
			this.createCell(wb, titleRow_40_0, 1).setCellValue(days_1.getAdvisoryCustomerCount());
			this.createCell(wb, titleRow_40_0, 2).setCellValue("当月客户对话量");
			this.createCell(wb, titleRow_40_0, 3).setCellValue(months_1.getAdvisoryCustomerCount());
			this.createCell(wb, titleRow_40_0, 6).setCellValue("当日活动推广赠金(其它)");
			this.createCell(wb, titleRow_40_0, 7).setCellValue(days_1_mt4.getBonusOtherMt4());
			this.createCell(wb, titleRow_40_0, 8).setCellValue("当月活动推广赠金(其它)");
			this.createCell(wb, titleRow_40_0, 9).setCellValue(months_1_mt4.getBonusOtherMt4());

			this.createCell(wb, titleRow_40, 0).setCellValue("当日对话总数");
			this.createCell(wb, titleRow_40, 1).setCellValue(days_1.getAdvisoryCount());
			this.createCell(wb, titleRow_40, 2).setCellValue("当月对话总数");
			this.createCell(wb, titleRow_40, 3).setCellValue(months_1.getAdvisoryCount());
			this.createCell(wb, titleRow_40, 6).setCellValue("当日活动推广赠金");
			this.createCell(wb, titleRow_40, 7).setCellValue(days_1_mt4.getBonus());
			this.createCell(wb, titleRow_40, 8).setCellValue("当月活动推广赠金");
			this.createCell(wb, titleRow_40, 9).setCellValue(months_1_mt4.getBonus());
			
			// 20170928新增需求
			this.createCell(wb, titleRow_40_1, 6).setCellValue("当日累计活动推广赠金");
			this.createCell(wb, titleRow_40_1, 7).setCellValue(NumberUtil.m2(days_1_mt4.getBonus() + days_1_mt4.getBonusOtherMt4()));
			this.createCell(wb, titleRow_40_1, 8).setCellValue("当月累计活动推广赠金");
			this.createCell(wb, titleRow_40_1, 9).setCellValue(NumberUtil.m2(months_1_mt4.getBonus() + months_1_mt4.getBonusOtherMt4()));
			
			// 合并单元格
			CellRangeAddress cra_7 = new CellRangeAddress(createRowNum, createRowNum, 6, 9);
			sheet.addMergedRegion(cra_7);
			// 合并单元格title
			Row cellTitleRow_5 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_5, 6).setCellValue("GTS2");
			// 表格title
			Row titleRow_41 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_42 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_43 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_44 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_45 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_46 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_47 = sheet.createRow(createRowNum);
			createRowNum ++;
			// 20170928新增需求
			Row titleRow_48_0 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_48 = sheet.createRow(createRowNum);
			createRowNum ++;
			Row titleRow_48_1 = sheet.createRow(createRowNum);
			createRowNum ++;
			
			// 放入数据
			DasTradeGts2cashandaccountdetailStatisticsYears days_1_gts2 = list_6.get(0);
			DasTradeGts2cashandaccountdetailStatisticsYears months_1_gts2 = list_6.get(1);
			this.createCell(wb, titleRow_41, 6).setCellValue("当日激活账户");
			this.createCell(wb, titleRow_41, 7).setCellValue(days_1_gts2.getSumactday());
			this.createCell(wb, titleRow_41, 8).setCellValue("当月激活账户");
			this.createCell(wb, titleRow_41, 9).setCellValue(months_1_gts2.getSumactday());

			this.createCell(wb, titleRow_42, 6).setCellValue("累计激活账户");
			this.createCell(wb, titleRow_42, 7).setCellValue(days_1_gts2.getSumactall());
			this.createCell(wb, titleRow_42, 8).setCellValue("当月累计总开户");
			this.createCell(wb, titleRow_42, 9).setCellValue(months_1_gts2.getSumopenall());

			this.createCell(wb, titleRow_43, 6).setCellValue("当日激活账户存款");
			this.createCell(wb, titleRow_43, 7).setCellValue(days_1_gts2.getSumnewaccount());
			this.createCell(wb, titleRow_43, 8).setCellValue("当月激活账户存款");
			this.createCell(wb, titleRow_43, 9).setCellValue(months_1_gts2.getSumnewaccount());

			this.createCell(wb, titleRow_44, 6).setCellValue("当日净存款");
			this.createCell(wb, titleRow_44, 7).setCellValue(days_1_gts2.getEquitymdeposit());
			this.createCell(wb, titleRow_44, 8).setCellValue("当月净存款");
			this.createCell(wb, titleRow_44, 9).setCellValue(months_1_gts2.getEquitymdeposit());

			this.createCell(wb, titleRow_45, 6).setCellValue("当日总存款");
			this.createCell(wb, titleRow_45, 7).setCellValue(days_1_gts2.getSummdeposit());
			this.createCell(wb, titleRow_45, 8).setCellValue("当月总存款");
			this.createCell(wb, titleRow_45, 9).setCellValue(months_1_gts2.getSummdeposit());

			this.createCell(wb, titleRow_46, 6).setCellValue("当日总取款");
			this.createCell(wb, titleRow_46, 7).setCellValue(days_1_gts2.getSumwithdraw());
			this.createCell(wb, titleRow_46, 8).setCellValue("当月总取款");
			this.createCell(wb, titleRow_46, 9).setCellValue(months_1_gts2.getSumwithdraw());
			
			this.createCell(wb, titleRow_47, 6).setCellValue("当日结馀");
			this.createCell(wb, titleRow_47, 7).setCellValue(days_1_gts2.getPreviousbalance());

			// 20170928新增需求
			this.createCell(wb, titleRow_48_0, 6).setCellValue("当日活动推广赠金(其它)");
			this.createCell(wb, titleRow_48_0, 7).setCellValue(days_1_gts2.getBonusOtherGts2());
			this.createCell(wb, titleRow_48_0, 8).setCellValue("当月活动推广赠金(其它)");
			this.createCell(wb, titleRow_48_0, 9).setCellValue(months_1_gts2.getBonusOtherGts2());
			this.createCell(wb, titleRow_48, 6).setCellValue("当日活动推广赠金");
			this.createCell(wb, titleRow_48, 7).setCellValue(days_1_gts2.getBonus());
			this.createCell(wb, titleRow_48, 8).setCellValue("当月活动推广赠金");
			this.createCell(wb, titleRow_48, 9).setCellValue(months_1_gts2.getBonus());
			// 20170928新增需求
			this.createCell(wb, titleRow_48_1, 6).setCellValue("当日累计活动推广赠金");
			this.createCell(wb, titleRow_48_1, 7).setCellValue(NumberUtil.m2(days_1_gts2.getBonus() + days_1_gts2.getBonusOtherGts2()));
			this.createCell(wb, titleRow_48_1, 8).setCellValue("当月累计活动推广赠金");
			this.createCell(wb, titleRow_48_1, 9).setCellValue(NumberUtil.m2(months_1_gts2.getBonus() + months_1_gts2.getBonusOtherGts2()));
			
			//对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse("交易记录&存取款及账户", request, response);
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	
	/**
	 * 十大排名导出
	 * 
	 * @return
	 */
	@RequestMapping("/exportRankingExcel")
	public void exportRankingExcel(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute TradeSearchModel tradeSearchModel) {
		try {
			// 1、需要导出的数据
			// 默认查询昨天
			if(StringUtils.isBlank(tradeSearchModel.getDateTime())){
				String dateTime = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), "yyyy-MM-dd");
				tradeSearchModel.setDateTime(dateTime);
			}
			
			// 十大赢家
			tradeSearchModel.setType("0");
			tradeSearchModel.setSort("cleanProfit");
			tradeSearchModel.setOrder("desc");
			List<DasTradeGts2Mt4Top10TargetDateYears> list_1 = tradeService.top10WinnerLoserYearsList(tradeSearchModel);
			// 十大亏损
			tradeSearchModel.setType("1");
			tradeSearchModel.setSort("cleanProfit");
			tradeSearchModel.setOrder("asc");
			List<DasTradeGts2Mt4Top10TargetDateYears> list_2 = tradeService.top10WinnerLoserYearsList(tradeSearchModel);
			// 十大存款
			tradeSearchModel.setType("0");
			tradeSearchModel.setSort("cashin");
			tradeSearchModel.setOrder("desc");
			List<DasTradeGts2Mt4Top10TargetDateYears> list_3 = tradeService.top10CashinCashoutYearsList(tradeSearchModel);
			// 十大取款
			tradeSearchModel.setType("1");
			tradeSearchModel.setSort("cashout");
			tradeSearchModel.setOrder("desc");
			List<DasTradeGts2Mt4Top10TargetDateYears> list_4 = tradeService.top10CashinCashoutYearsList(tradeSearchModel);
			// 十大交易量客户
			tradeSearchModel.setType("");
			tradeSearchModel.setSort("closedvolume");
			tradeSearchModel.setOrder("desc");
			List<DasTradeGts2Mt4Top10TargetDateYears> list_5 = tradeService.top10TraderYearsList(tradeSearchModel);
			// 交易统计-手数
			tradeSearchModel.setSort("volumeRange");
			tradeSearchModel.setOrder("asc");
			List<DasTradeGts2Mt4Top10VolumeStatisticsYears> list_6 = tradeService.top10VolumeStatisticsYearsList(tradeSearchModel);
			// 交易统计-持仓时间
			tradeSearchModel.setSort("timeRange");
			tradeSearchModel.setOrder("asc");
			List<DasTradeGts2Mt4Top10PositionStatisticsYears> list_7 = tradeService.top10PositionStatisticsYearsList(tradeSearchModel);
			
			// 2、格式化数据与导出
			// 定义excel工作簿-只写入
			SXSSFWorkbook wb = new SXSSFWorkbook(100);
			// create sheet
			Sheet sheet = wb.createSheet("MT4&GTS2十大排名及交易统计");// sheet名称
			sheet.setColumnWidth(0, 15*256);// 设置列宽
			sheet.setColumnWidth(1, 15*256);// 设置列宽
			sheet.setColumnWidth(2, 15*256);// 设置列宽
			sheet.setColumnWidth(3, 30*256);// 设置列宽
			sheet.setColumnWidth(4, 30*256);// 设置列宽
			
			// 创建SXSSFWorkbook.createRow时的下标
			int createRowNum = 0;

			// 空两行隔开
			createRowNum ++;
			createRowNum ++;
			// 合并单元格
			CellRangeAddress cra_1 = new CellRangeAddress(createRowNum, createRowNum, 0, 4);
			sheet.addMergedRegion(cra_1);
			// 合并单元格title
			Row cellTitleRow_1 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_1, 0).setCellValue("十大赢家");
			// 表格title
			Row titleRow_1 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, titleRow_1, 0).setCellValue("日期");
			this.createCellT(wb, titleRow_1, 1).setCellValue("账号");
			this.createCellT(wb, titleRow_1, 2).setCellValue("姓名");
			this.createCellT(wb, titleRow_1, 3).setCellValue("当天净盈亏(盈亏+回佣+利息)");
			this.createCellT(wb, titleRow_1, 4).setCellValue("开户总净盈亏");
			// 放入表格数据
			if(null != list_1 && list_1.size() > 0){
				for(int i=0; i<list_1.size(); i++){
					DasTradeGts2Mt4Top10TargetDateYears model = list_1.get(i);
					Row dataRow = sheet.createRow(createRowNum);
					createRowNum ++;
					for(int cellNum=0; cellNum<5; cellNum++){
						// CreateCell
						Cell cell = dataRow.createCell(cellNum);
						String value = "";
						if(cellNum == 0){
							value = model.getDatetime();
						}else if(cellNum == 1){
							value = model.getAccountNo();
						}else if(cellNum == 2){
							value = model.getChineseName();
						}else if(cellNum == 3){
							value = model.getCleanProfit() + "";
						}else if(cellNum == 4){
							value = model.getAccountCleanProfit() + "";
						}
						cell.setCellValue(value);
					}
				}
			}

			// 空两行隔开
			createRowNum ++;
			createRowNum ++;
			// 合并单元格
			CellRangeAddress cra_2 = new CellRangeAddress(createRowNum, createRowNum, 0, 4);
			sheet.addMergedRegion(cra_2);
			// 合并单元格title
			Row cellTitleRow_2 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_2, 0).setCellValue("十大亏损");
			// 表格title
			Row titleRow_2 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, titleRow_2, 0).setCellValue("日期");
			this.createCellT(wb, titleRow_2, 1).setCellValue("账号");
			this.createCellT(wb, titleRow_2, 2).setCellValue("姓名");
			this.createCellT(wb, titleRow_2, 3).setCellValue("当天净盈亏(盈亏+回佣+利息)");
			this.createCellT(wb, titleRow_2, 4).setCellValue("开户总净盈亏");
			// 放入表格数据
			if(null != list_2 && list_2.size() > 0){
				for(int i=0; i<list_2.size(); i++){
					DasTradeGts2Mt4Top10TargetDateYears model = list_2.get(i);
					Row dataRow = sheet.createRow(createRowNum);
					createRowNum ++;
					for(int cellNum=0; cellNum<5; cellNum++){
						// CreateCell
						Cell cell = dataRow.createCell(cellNum);
						String value = "";
						if(cellNum == 0){
							value = model.getDatetime();
						}else if(cellNum == 1){
							value = model.getAccountNo();
						}else if(cellNum == 2){
							value = model.getChineseName();
						}else if(cellNum == 3){
							value = model.getCleanProfit() + "";
						}else if(cellNum == 4){
							value = model.getAccountCleanProfit() + "";
						}
						cell.setCellValue(value);
					}
				}
			}
			
			// 空两行隔开
			createRowNum ++;
			createRowNum ++;
			// 合并单元格
			CellRangeAddress cra_3 = new CellRangeAddress(createRowNum, createRowNum, 0, 4);
			sheet.addMergedRegion(cra_3);
			// 合并单元格title
			Row cellTitleRow_3 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_3, 0).setCellValue("十大存款");
			// 表格title
			Row titleRow_3 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, titleRow_3, 0).setCellValue("日期");
			this.createCellT(wb, titleRow_3, 1).setCellValue("账号");
			this.createCellT(wb, titleRow_3, 2).setCellValue("姓名");
			this.createCellT(wb, titleRow_3, 3).setCellValue("当天存款");
			this.createCellT(wb, titleRow_3, 4).setCellValue("开户总存款");
			// 放入表格数据
			if(null != list_5 && list_3.size() > 0){
				for(int i=0; i<list_3.size(); i++){
					DasTradeGts2Mt4Top10TargetDateYears model = list_3.get(i);
					Row dataRow = sheet.createRow(createRowNum);
					createRowNum ++;
					for(int cellNum=0; cellNum<5; cellNum++){
						// CreateCell
						Cell cell = dataRow.createCell(cellNum);
						String value = "";
						if(cellNum == 0){
							value = model.getDatetime();
						}else if(cellNum == 1){
							value = model.getAccountNo();
						}else if(cellNum == 2){
							value = model.getChineseName();
						}else if(cellNum == 3){
							value = model.getCashin() + "";
						}else if(cellNum == 4){
							value = model.getAccountCashin() + "";
						}
						cell.setCellValue(value);
					}
				}
			}
			
			// 空两行隔开
			createRowNum ++;
			createRowNum ++;
			// 合并单元格
			CellRangeAddress cra_4 = new CellRangeAddress(createRowNum, createRowNum, 0, 4);
			sheet.addMergedRegion(cra_4);
			// 合并单元格title
			Row cellTitleRow_4 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_4, 0).setCellValue("十大取款");
			// 表格title
			Row titleRow_4 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, titleRow_4, 0).setCellValue("日期");
			this.createCellT(wb, titleRow_4, 1).setCellValue("账号");
			this.createCellT(wb, titleRow_4, 2).setCellValue("姓名");
			this.createCellT(wb, titleRow_4, 3).setCellValue("当天取款");
			this.createCellT(wb, titleRow_4, 4).setCellValue("开户总取款");
			// 放入表格数据
			if(null != list_4 && list_4.size() > 0){
				for(int i=0; i<list_4.size(); i++){
					DasTradeGts2Mt4Top10TargetDateYears model = list_4.get(i);
					Row dataRow = sheet.createRow(createRowNum);
					createRowNum ++;
					for(int cellNum=0; cellNum<5; cellNum++){
						// CreateCell
						Cell cell = dataRow.createCell(cellNum);
						String value = "";
						if(cellNum == 0){
							value = model.getDatetime();
						}else if(cellNum == 1){
							value = model.getAccountNo();
						}else if(cellNum == 2){
							value = model.getChineseName();
						}else if(cellNum == 3){
							value = model.getCashout() + "";
						}else if(cellNum == 4){
							value = model.getAccountCashout() + "";
						}
						cell.setCellValue(value);
					}
				}
			}
			
			// 空两行隔开
			createRowNum ++;
			createRowNum ++;
			// 合并单元格
			CellRangeAddress cra_5 = new CellRangeAddress(createRowNum, createRowNum, 0, 4);
			sheet.addMergedRegion(cra_5);
			// 合并单元格title
			Row cellTitleRow_5 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_5, 0).setCellValue("十大交易量客户");
			// 表格title
			Row titleRow_5 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, titleRow_5, 0).setCellValue("日期");
			this.createCellT(wb, titleRow_5, 1).setCellValue("账号");
			this.createCellT(wb, titleRow_5, 2).setCellValue("姓名");
			this.createCellT(wb, titleRow_5, 3).setCellValue("平仓手数");
			this.createCellT(wb, titleRow_5, 4).setCellValue("开户总净盈亏");
			// 放入表格数据
			if(null != list_5 && list_5.size() > 0){
				for(int i=0; i<list_5.size(); i++){
					DasTradeGts2Mt4Top10TargetDateYears model = list_5.get(i);
					Row dataRow = sheet.createRow(createRowNum);
					createRowNum ++;
					for(int cellNum=0; cellNum<5; cellNum++){
						// CreateCell
						Cell cell = dataRow.createCell(cellNum);
						String value = "";
						if(cellNum == 0){
							value = model.getDatetime();
						}else if(cellNum == 1){
							value = model.getAccountNo();
						}else if(cellNum == 2){
							value = model.getChineseName();
						}else if(cellNum == 3){
							value = model.getClosedvolume() + "";
						}else if(cellNum == 4){
							value = model.getAccountCleanProfit() + "";
						}
						cell.setCellValue(value);
					}
				}
			}
			
			// 空两行隔开
			createRowNum ++;
			createRowNum ++;
			// 合并单元格
			CellRangeAddress cra_6 = new CellRangeAddress(createRowNum, createRowNum, 0, 4);
			sheet.addMergedRegion(cra_6);
			// 合并单元格title
			Row cellTitleRow_6 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_6, 0).setCellValue("交易统计-手数");
			// 表格title
			Row titleRow_6 = sheet.createRow(createRowNum);
			createRowNum ++;
			// 第5列隔开
			this.createCellT(wb, titleRow_6, 0).setCellValue("日期");
			this.createCellT(wb, titleRow_6, 1).setCellValue("手数范围");
			this.createCellT(wb, titleRow_6, 2).setCellValue("总手数");
			this.createCellT(wb, titleRow_6, 3).setCellValue("百分比");
			this.createCellT(wb, titleRow_6, 4).setCellValue("当天净盈亏(盈亏+回佣+利息)");
			
			// 1、放入表格数据
			// 2、累计总手数、百分比、当天净盈亏(盈亏+回佣+利息)
			double tempVolume = 0D;
			String tempRate = "0%";
			double tempJyk = 0D;
			if(null != list_6 && list_6.size() > 0){
				for(int i=0; i<list_6.size(); i++){
					DasTradeGts2Mt4Top10VolumeStatisticsYears model = list_6.get(i);
					Row dataRow = sheet.createRow(createRowNum);
					createRowNum ++;
					for(int cellNum=0; cellNum<5; cellNum++){
						// CreateCell
						Cell cell = dataRow.createCell(cellNum);
						String value = "";
						if(cellNum == 0){
							value = model.getDatetime();
						}else if(cellNum == 1){
							value = VolumeRangeEnum.format(model.getVolumeRange());
						}else if(cellNum == 2){
							value = model.getClosedvolume() + "";
							tempVolume += model.getClosedvolume();// 累计总手数
						}else if(cellNum == 3){
							value = model.getPercent() + "%";
							if(model.getPercent()>0D){
								tempRate = "100%" ;//有值时，肯定是100%
							}
						}else if(cellNum == 4){
							value = model.getCleanProfit() + "";
							tempJyk += model.getCleanProfit();// 累计当天净盈亏(盈亏+回佣+利息)
						}
						cell.setCellValue(value);
					}
				}
			}
			// 3、放入累计数据
			Row titleRow_6_0 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, titleRow_6_0, 0).setCellValue("总计：");
			this.createCell(wb, titleRow_6_0, 1).setCellValue("");
			this.createCell(wb, titleRow_6_0, 2).setCellValue(NumberUtil.m2(tempVolume));
			this.createCell(wb, titleRow_6_0, 3).setCellValue(tempRate);
			this.createCell(wb, titleRow_6_0, 4).setCellValue(NumberUtil.m2(tempJyk));
			
			// 空两行隔开
			createRowNum ++;
			createRowNum ++;
			// 合并单元格
			CellRangeAddress cra_7 = new CellRangeAddress(createRowNum, createRowNum, 0, 4);
			sheet.addMergedRegion(cra_7);
			// 合并单元格title
			Row cellTitleRow_7 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, cellTitleRow_7, 0).setCellValue("交易统计-持仓时间");
			// 表格title
			Row titleRow_7 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, titleRow_7, 0).setCellValue("日期");
			this.createCellT(wb, titleRow_7, 1).setCellValue("时间");
			this.createCellT(wb, titleRow_7, 2).setCellValue("次数");
			this.createCellT(wb, titleRow_7, 3).setCellValue("百分比");
			this.createCellT(wb, titleRow_7, 4).setCellValue("当天净盈亏(盈亏+回佣+利息)");
			// 放入表格数据
			// 2、累计总次数、百分比、当天净盈亏(盈亏+回佣+利息)
			double tempNum = 0D;
			String tempRate_1 = "0%";
			double tempJyk_1 = 0D;
			if(null != list_7 && list_7.size() > 0){
				for(int i=0; i<list_7.size(); i++){
					DasTradeGts2Mt4Top10PositionStatisticsYears model = list_7.get(i);
					Row dataRow = sheet.createRow(createRowNum);
					createRowNum ++;
					for(int cellNum=0; cellNum<5; cellNum++){
						// CreateCell
						Cell cell = dataRow.createCell(cellNum);
						String value = "";
						if(cellNum == 0){
							value = model.getDatetime();
						}else if(cellNum == 1){
							value = TimeRangeEnum.format(model.getTimeRange());
						}else if(cellNum == 2){
							value = model.getDealamount() + "";
							tempNum += model.getDealamount();// 累计总次数
						}else if(cellNum == 3){
							value = model.getPercent() + "%";
							if(model.getPercent()>0D){
								tempRate_1 = "100%" ;//有值时，肯定是100%
							}
						}else if(cellNum == 4){
							value = model.getCleanProfit() + "";
							tempJyk_1 += model.getCleanProfit();// 累计当天净盈亏(盈亏+回佣+利息)
						}
						cell.setCellValue(value);
					}
				}
			}
			// 3、放入累计数据
			Row titleRow_7_0 = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, titleRow_7_0, 0).setCellValue("总计：");
			this.createCell(wb, titleRow_7_0, 1).setCellValue("");
			this.createCell(wb, titleRow_7_0, 2).setCellValue(NumberUtil.m2(tempNum));
			this.createCell(wb, titleRow_7_0, 3).setCellValue(tempRate_1);
			this.createCell(wb, titleRow_7_0, 4).setCellValue(NumberUtil.m2(tempJyk_1));
			
			//对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse("MT4&GTS2十大排名及交易统计", request, response);
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 创建T类型cell
	 */
	private Cell createCellT(SXSSFWorkbook wb, Row row, int cellNum){
		Cell cell = row.createCell(cellNum);
		CellStyle cellStyle = wb.createCellStyle();
	    Font font = wb.createFont();
	    font.setFontHeightInPoints((short) 13);//设置字体大小
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
	    cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);//左对齐
	    cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
	    cellStyle.setFont(font);
	    cell.setCellStyle(cellStyle);
	    return cell;
	}

	/**
	 * 创建类型cell
	 */
	private Cell createCell(SXSSFWorkbook wb, Row row, int cellNum){
		Cell cell = row.createCell(cellNum);
		CellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);//左对齐
	    cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
	    cell.setCellStyle(cellStyle);
	    return cell;
	}
}
