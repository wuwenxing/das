package com.gw.das.web.controller.appDataAnalysis;

import java.io.File;
import java.util.ArrayList;
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

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.DataAccountTypeEnum;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.OperationTypeEnum;
import com.gw.das.common.enums.PlatformtypeEnum;
import com.gw.das.common.enums.UserTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisSearchModel;
import com.gw.das.dao.appDataAnalysis.bean.DasAppOdsVO;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.service.appDataAnalysis.DasAppOdsService;
import com.gw.das.service.market.ChannelService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/dasAppOdsController")
public class DasAppOdsController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasAppOdsController.class);

	@Autowired
	private DasAppOdsService DasAppOdsService;
	
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 跳转交易客户端数据源报表管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {	
			request.setAttribute("operationTypeEnum", OperationTypeEnum.getList());
			
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
			request.setAttribute("platformtypeEnum", platformTypeEnum);
			
			//request.setAttribute("platformtypeEnum", PlatformtypeEnum.getList());
			request.setAttribute("userTypeEnum", UserTypeEnum.getList());
			request.setAttribute("dataAccountTypeEnum", DataAccountTypeEnum.getList());
			request.setAttribute("devicetypeEnumEnum", DeviceTypeEnum.getList());
			
	    	String channel = request.getParameter("channel");
	    	request.setAttribute("channel", channel);
			ChannelEntity channelEntity = new ChannelEntity();
			channelEntity.setChannelType(ChannelTypeEnum.app.getLabelKey());
			List<String> channelList = channelService.findChannelUtmcsrList(channelEntity);
			request.setAttribute("channelList", channelList);
			
			return "/appDataAnalysis/appOdsStatistics";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 交易客户端数据源报表分页查询 -elasticsearch
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDasAppOdsPageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<AppDataAnalysisSearchModel> findDasAppOdsPageList(HttpServletRequest request, @ModelAttribute AppDataAnalysisSearchModel searchBean) {
		try {
			PageGrid<AppDataAnalysisSearchModel> pageGrid = DasAppOdsService.findDasAppOdsPageList(super.createPageGrid(request, searchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<AppDataAnalysisSearchModel>();
		}
	}
	
	/**
	 * 交易客户端数据源报表-导出 -elasticsearch
	 */
	@RequestMapping("/exportExcelDasAppOds")
	public void exportExcelDasAppOds(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute AppDataAnalysisSearchModel searchBean) {
		try {
			
			long start = System.currentTimeMillis();
			
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.dasAppOdsReport.getPath())));
			// 2、需要导出的数据
			List<DasAppOdsVO> recordList = DasAppOdsService.findDasAppOdsList(searchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasAppOdsVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasAppOdsVO param) {
					if("accountType".equals(fieldName)){
						if(DataAccountTypeEnum.mini.getLabelKey().equals(String.valueOf(fieldValue))){
 					    	return DataAccountTypeEnum.mini.getValue();
 					    }
 					   if(DataAccountTypeEnum.standard.getLabelKey().equals(String.valueOf(fieldValue))){
 					    	return DataAccountTypeEnum.standard.getValue();
 					    }
 					   if(DataAccountTypeEnum.vip.getLabelKey().equals(String.valueOf(fieldValue))){
 					    	return DataAccountTypeEnum.vip.getValue();
 					   }
					}
					if("userType".equals(fieldName)){
						if(UserTypeEnum.tourist.getLabelKey().equals(String.valueOf(fieldValue))){
							return UserTypeEnum.tourist.getValue();
 					    }
 					   if(UserTypeEnum.dome.getLabelKey().equals(String.valueOf(fieldValue))){
 						  return UserTypeEnum.dome.getValue();
 					    }
 					   if(UserTypeEnum.real.getLabelKey().equals(String.valueOf(fieldValue))){
 						  return UserTypeEnum.real.getValue();
 					   }
					}
					
					if("operationType".equals(fieldName)){
						if(OperationTypeEnum.start.getLabelKey().equals(String.valueOf(fieldValue))){
							return OperationTypeEnum.start.getValue();
 					    }
						if(OperationTypeEnum.login.getLabelKey().equals(String.valueOf(fieldValue))){
							return OperationTypeEnum.login.getValue();
 					    }
						if(OperationTypeEnum.transaction.getLabelKey().equals(String.valueOf(fieldValue))){
							return OperationTypeEnum.transaction.getValue();
 					    }
						if(OperationTypeEnum.Cancel.getLabelKey().equals(String.valueOf(fieldValue))){
							return OperationTypeEnum.Cancel.getValue();
 					    }
						if(OperationTypeEnum.out.getLabelKey().equals(String.valueOf(fieldValue))){
							return OperationTypeEnum.out.getValue();
 					    }
					}
					
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.dasAppOdsReport.getValue(), request, response);
			builder.write(response.getOutputStream());
			
			long end = System.currentTimeMillis();
			logger.info("导出交易客户端数据源统计Excel报表消耗时间:"+(end-start)/1000+"秒");
			
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	

	
}
