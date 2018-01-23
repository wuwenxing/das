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
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.PlatformtypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.Decrypt;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.trade.bean.DimAccountBlackList;
import com.gw.das.dao.trade.bean.DimAccountBlackListSearchBean;
import com.gw.das.service.cache.IdCardCache;
import com.gw.das.service.trade.TradeService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/DimAccountBlackListController")
public class DimAccountBlackListController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DimAccountBlackListController.class);

	@Autowired
	private TradeService tradeService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("platformtypeEnum", PlatformtypeEnum.getAllList());
			request.setAttribute("companyEnum", CompanyEnum.getList());
			return "/trade/dimAccountBlackList/dimAccountBlackList";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 账户黑名单列表-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDimAccountBlackListPage", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DimAccountBlackListSearchBean> findDimAccountBlackListPage(HttpServletRequest request,
			@ModelAttribute DimAccountBlackListSearchBean tradeSearchModel) {
		try {
			PageGrid<DimAccountBlackListSearchBean> pageGrid = tradeService.findDimAccountBlackListPage(super.createPageGrid(request, tradeSearchModel));
			for(Object obj: pageGrid.getRows()){
				DimAccountBlackList record = (DimAccountBlackList)obj;
				if(StringUtils.isNotBlank(record.getIdCardEncrypt())){
					String value = IdCardCache.get(record.getIdCardEncrypt());
					if(StringUtils.isNotBlank(value)){
						record.setIdCardEncrypt(value);
					}else{
						value = Decrypt.decrypt(record.getIdCardEncrypt());
						IdCardCache.put(record.getIdCardEncrypt(), value);
					}
					record.setIdCardEncrypt(StringUtil.formatIdCard(value));
				}
				record.setMobile(StringUtil.formatPhone(record.getMobile()));
			}
			// 将缓存的值放入redis
			IdCardCache.refresh();
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DimAccountBlackListSearchBean>();
		}
	}
	
	/**
	 * 账户黑名单列表-导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute DimAccountBlackListSearchBean tradeSearchModel) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.dimAccountBlackList.getPath())));
			// 2、需要导出的数据
			List<DimAccountBlackList> recordList = tradeService.findDimAccountBlackList(tradeSearchModel);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DimAccountBlackList>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DimAccountBlackList param) {
					if("idCardEncrypt".equals(fieldName)){
						if(null != fieldValue && StringUtils.isNotBlank(fieldValue+"")){
							String value = IdCardCache.get(fieldValue+"");
							if(StringUtils.isBlank(value)){
								value = Decrypt.decrypt(fieldValue+"");
								IdCardCache.put(fieldValue+"", value);
							}
							return value;
						}
					}
					return fieldValue;
				}
			});
			// 将缓存的值放入redis
			IdCardCache.refresh();
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.dimAccountBlackList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
}
