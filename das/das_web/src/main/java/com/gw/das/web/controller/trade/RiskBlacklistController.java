package com.gw.das.web.controller.trade;

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
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.DimBlackListTypeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.PlatformtypeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.enums.RiskTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.trade.bean.DimBlackList;
import com.gw.das.dao.trade.bean.DimBlackListSearchBean;
import com.gw.das.dao.trade.entity.RiskBlacklistEntity;
import com.gw.das.service.trade.RiskBlacklistService;
import com.gw.das.service.trade.TradeService;
import com.gw.das.web.controller.system.BaseController;

/**
 * 风控列表
 * @author wayne
 */
@Controller
@RequestMapping("/RiskBlacklistController")
public class RiskBlacklistController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(RiskBlacklistController.class);

	@Autowired
	private RiskBlacklistService riskBlacklistService;
	@Autowired
	private TradeService tradeService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("deviceTypeEnum", DeviceTypeEnum.getList());
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getAllList());
			request.setAttribute("companyEnum", CompanyEnum.getList());
			request.setAttribute("dimBlackListTypeEnum", DimBlackListTypeEnum.getList());
			request.setAttribute("riskTypeEnum", RiskTypeEnum.getList());
			return "/trade/riskBlacklist/riskBlacklist";
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
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<RiskBlacklistEntity> pageList(HttpServletRequest request,
			@ModelAttribute RiskBlacklistEntity riskBlacklistEntity) {
		try {
			PageGrid<RiskBlacklistEntity> pageGrid = riskBlacklistService.findPageList(super.createPageGrid(request, riskBlacklistEntity));
			for(Object obj: pageGrid.getRows()){
				RiskBlacklistEntity record = (RiskBlacklistEntity)obj;
				record.setPlatform(PlatformtypeEnum.format(record.getPlatform()));
				record.setDeviceType(DeviceTypeEnum.format(record.getDeviceType()));
				record.setIdCard(StringUtil.formatIdCard(record.getIdCard()));
				record.setMobile(StringUtil.formatPhone(record.getMobile()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<RiskBlacklistEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public RiskBlacklistEntity findById(Long riskBlacklistId) {
		try {
			return riskBlacklistService.findById(riskBlacklistId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new RiskBlacklistEntity();
		}
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute RiskBlacklistEntity riskBlacklistEntity) {
		try {
			// 2、编辑操作
			riskBlacklistService.update(riskBlacklistEntity);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 风控列表-导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute RiskBlacklistEntity riskBlacklistEntity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.riskBlacklist.getPath())));
			// 2、需要导出的数据
			List<RiskBlacklistEntity> recordList = riskBlacklistService.findList(riskBlacklistEntity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<RiskBlacklistEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, RiskBlacklistEntity param) {
					if("companyId".equals(fieldName)){
						if(null != fieldValue){
							return CompanyEnum.format(fieldValue.toString());
						}else{
							return "";
						}
					}
					if("platform".equals(fieldName)){
						if(null != fieldValue){
							return PlatformtypeEnum.format(fieldValue.toString());
						}else{
							return "";
						}
					}
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
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.riskBlacklist.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 风控要素-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDimBlackList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DimBlackListSearchBean> findDimBlackList(HttpServletRequest request,
			@ModelAttribute DimBlackListSearchBean tradeSearchModel) {
		try {
			PageGrid<DimBlackListSearchBean> pageGrid = tradeService.findDimBlackListPage(super.createPageGrid(request, tradeSearchModel));
			for(Object obj: pageGrid.getRows()){
				DimBlackList record = (DimBlackList)obj;
				if(StringUtils.isNotBlank(record.getType())){
					if(DimBlackListTypeEnum.email.getLabelKey().equals(record.getType())){
						record.setValue(StringUtil.formatEmail(record.getValue()));
					}else if(DimBlackListTypeEnum.mobile.getLabelKey().equals(record.getType())){
						record.setValue(StringUtil.formatPhone(record.getValue()));
					}else if(DimBlackListTypeEnum.idCard.getLabelKey().equals(record.getType())){
						record.setValue(StringUtil.formatIdCard(record.getValue()));
					}
					record.setType(DimBlackListTypeEnum.format(record.getType()));
				}
				if(StringUtils.isNotBlank(record.getRiskType())){
					record.setRiskType(RiskTypeEnum.format(record.getRiskType()));
				}
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DimBlackListSearchBean>();
		}
	}
	
	/**
	 * 风控要素-导出
	 */
	@RequestMapping("/dimBlackListExportExcel")
	public void dimBlackListExportExcel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute DimBlackListSearchBean tradeSearchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.dimBlackList.getPath())));
			// 2、需要导出的数据
			List<DimBlackList> recordList = tradeService.findDimBlackList(tradeSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DimBlackList>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DimBlackList param) {
					if("type".equals(fieldName)){
						if(null != fieldValue && StringUtils.isNotBlank(fieldValue+"")){
							return DimBlackListTypeEnum.format(fieldValue+"");
						}
					}
					if("riskType".equals(fieldName)){
						if(null != fieldValue && StringUtils.isNotBlank(fieldValue+"")){
							return RiskTypeEnum.format(fieldValue+"");
						}
					}
					if("source".equals(fieldName)){
						if(null != fieldValue && StringUtils.isNotBlank(fieldValue+"")){
							if("1".equals(fieldValue+"")){
								return "自动";
							}else if("2".equals(fieldValue+"")){
								return "手工";
							}else if("3".equals(fieldValue+"")){
								return "自动&手工";
							}
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.dimBlackList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
}
