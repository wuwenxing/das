package com.gw.das.web.controller.tradeGts2;

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
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ReasonEnum;
import com.gw.das.common.enums.TradeDirectionEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.trade.bean.AccountbalanceVO;
import com.gw.das.dao.trade.bean.CustomerdailyVO;
import com.gw.das.dao.trade.bean.DealVO;
import com.gw.das.dao.trade.bean.PositionVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.service.tradeGts2.tradeAccountDiagnosis.AccountbalanceService;
import com.gw.das.service.tradeGts2.tradeAccountDiagnosis.CustomerdailyService;
import com.gw.das.service.tradeGts2.tradeAccountDiagnosis.TradeDetailGts2Service;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/tradeAccountDiagnosisController")
public class TradeAccountDiagnosisController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TradeAccountDiagnosisController.class);

	@Autowired
	private AccountbalanceService accountbalanceService;
	
	@Autowired
	private CustomerdailyService customerdailyService;
	
	@Autowired
	private TradeDetailGts2Service tradeDetailGts2Service;

	/**
	 * 跳转账户余额变动表管理页面
	 */
	@RequestMapping(value = "/accountbalancePage", method = { RequestMethod.GET })
	public String accountbalancePage(HttpServletRequest request) {
		try {
			return "/trade/gts2/accountbalance/accountbalance";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 账户余额变动表不分页查询 -elasticsearch
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findAccountbalancePageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findAccountbalancePageList(HttpServletRequest request, @ModelAttribute TradeSearchModel searchBean) {
		try {
			PageGrid<TradeSearchModel> pageGrid = accountbalanceService.findAccountbalanceWithPageList(super.createPageGrid(request, searchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 账户余额变动表-导出 -elasticsearch
	 */
	@RequestMapping("/exportExcelAccountbalance")
	public void exportExcelAccountbalance(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.accountbalanceList.getPath())));
			// 2、需要导出的数据
			List<AccountbalanceVO> recordList = accountbalanceService.findAccountbalanceWithList(searchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<AccountbalanceVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, AccountbalanceVO param) {
					if("reason".equals(fieldName)){
						if(ReasonEnum.X01.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X01.getValue();
						}else if(ReasonEnum.X02.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X02.getValue();
						}else if(ReasonEnum.X04.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X04.getValue();
						}else if(ReasonEnum.X08.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X08.getValue();
						}else if(ReasonEnum.X16.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X16.getValue();
						}else if(ReasonEnum.X32.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X32.getValue();
						}else if(ReasonEnum.X64.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X64.getValue();
						}else if(ReasonEnum.X81.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X81.getValue();
						}else if(ReasonEnum.X128.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X128.getValue();
						}else if(ReasonEnum.X129.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X129.getValue();
						}else if(ReasonEnum.X130.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X130.getValue();
						}else if(ReasonEnum.X131.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X131.getValue();
						}else if(ReasonEnum.X132.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X132.getValue();
						}else if(ReasonEnum.X134.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X134.getValue();
						}else if(ReasonEnum.X135.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X135.getValue();
						}else if(ReasonEnum.X160.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X160.getValue();
						}else if(ReasonEnum.X161.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X161.getValue();
						}else if(ReasonEnum.X162.getLabelKey().equals(fieldValue)){
							return ReasonEnum.X162.getValue();
						}else if("0".equals(fieldValue)){
							return null;
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.accountbalanceList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转账户结算日报表管理页面
	 */
	@RequestMapping(value = "/customerdailyPage", method = { RequestMethod.GET })
	public String customerdailyPage(HttpServletRequest request) {
		try {
			return "/trade/gts2/customerdaily/customerdaily";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 账户结算日报表分页查询-elasticsearch
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findCustomerdailyPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findCustomerdailyPageList(HttpServletRequest request, @ModelAttribute TradeSearchModel searchBean) {
		try {
			PageGrid<TradeSearchModel> pageGrid = customerdailyService.findCustomerdailyWithPageList(super.createPageGrid(request, searchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 账户结算日报表-导出-elasticsearch
	 */
	@RequestMapping("/exportExcelCustomerdaily")
	public void exportExcelCustomerdaily(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.customerdailyList.getPath())));
			// 2、需要导出的数据
			List<CustomerdailyVO> recordList = customerdailyService.findCustomerdailyWithList(searchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<CustomerdailyVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, CustomerdailyVO param) {
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.customerdailyList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 跳转平仓交易报表管理页面
	 */
	@RequestMapping(value = "/tradeDetailClosePage", method = { RequestMethod.GET })
	public String tradeDetailClosePage(HttpServletRequest request) {
		try {
			return "/trade/gts2/tradeDetail/tradeDetailClose";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 平仓交易报表分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findTradeDetailClosePageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findTradeDetailClosePageList(HttpServletRequest request, @ModelAttribute TradeSearchModel searchBean) {
		try {
			PageGrid<TradeSearchModel> pageGrid = tradeDetailGts2Service.findTradeDetailCloseWithPageList(super.createPageGrid(request, searchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}

	/**
	 *平仓交易报表-导出
	 */
	@RequestMapping("/exportExcelTradeDetailClose")
	public void exportExcelTradeDetailClose(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.tradeDetailCloseList.getPath())));
			// 2、需要导出的数据
			List<DealVO> recordList = tradeDetailGts2Service.findTradeDetailCloseWithList(searchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DealVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DealVO param) {
					if("direction".equals(fieldName) && TradeDirectionEnum.buy.getValue().equals(String.valueOf(fieldValue))){
						return TradeDirectionEnum.buy.getLabelKey();
					}else if("direction".equals(fieldName) && TradeDirectionEnum.sell.getValue().equals(String.valueOf(fieldValue))){
						return TradeDirectionEnum.sell.getLabelKey();
					}else{
						return fieldValue;
					}
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.tradeDetailCloseList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}	

	/**
	 * 跳转开仓交易报表管理页面
	 */
	@RequestMapping(value = "/tradeDetailOpenPage", method = { RequestMethod.GET })
	public String tradeDetailOpenPage(HttpServletRequest request) {
		try {
			return "/trade/gts2/tradeDetail/tradeDetailOpen";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 开仓交易报表分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findTradeDetailOpenPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<TradeSearchModel> findTradeDetailOpenPageList(HttpServletRequest request, @ModelAttribute TradeSearchModel searchBean) {
		try {
			 PageGrid<TradeSearchModel> pageGrid = tradeDetailGts2Service.findTradeDetailOpenWithPageList(super.createPageGrid(request, searchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeSearchModel>();
		}
	}
	
	/**
	 * 开仓交易报表-导出
	 */
	@RequestMapping("/exportExcelTradeDetailOpen")
	public void exportExcelTradeDetailOpen(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TradeSearchModel searchBean) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.tradeDetailOpenList.getPath())));
			// 2、需要导出的数据
			List<PositionVO> recordList = tradeDetailGts2Service.findTradeDetailOpenWithList(searchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<PositionVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, PositionVO param) {
					if("direction".equals(fieldName) && TradeDirectionEnum.buy.getValue().equals(String.valueOf(fieldValue))){
						return TradeDirectionEnum.buy.getLabelKey();
					}else if("direction".equals(fieldName) && TradeDirectionEnum.sell.getValue().equals(String.valueOf(fieldValue))){
						return TradeDirectionEnum.sell.getLabelKey();
					}else{
						return fieldValue;
					}
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.tradeDetailOpenList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
		
}
