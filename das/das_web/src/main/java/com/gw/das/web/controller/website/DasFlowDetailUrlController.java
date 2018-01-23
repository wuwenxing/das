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
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.website.bean.DasFlowDetailUrl;
import com.gw.das.dao.website.bean.DasFlowDetailUrlSearchBean;
import com.gw.das.service.website.DasFlowDetailUrlService;
import com.gw.das.web.controller.system.BaseController;

/**
 * 官网行为详细-访问url明细
 * @author wayne
 */
@Controller
@RequestMapping("/DasFlowDetailUrlController")
public class DasFlowDetailUrlController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasFlowDetailUrlController.class);

	@Autowired
	private DasFlowDetailUrlService dasFlowDetailUrlService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("formatTime", request.getParameter("formatTime"));
			request.setAttribute("flowDetailId", request.getParameter("flowDetailId"));
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("platformType", request.getParameter("platformType"));
			request.setAttribute("utmcmd", request.getParameter("utmcmd"));
			request.setAttribute("utmcsr", request.getParameter("utmcsr"));
			request.setAttribute("startTime", request.getParameter("startTime"));
			request.setAttribute("endTime", request.getParameter("endTime"));
			return "/behavior/dasFlowDetailUrl";
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
	public PageGrid<DasFlowDetailUrlSearchBean> pageList(HttpServletRequest request,
			@ModelAttribute DasFlowDetailUrlSearchBean dasFlowDetailUrlSearchBean) {
		try {
			PageGrid<DasFlowDetailUrlSearchBean> pageGrid = dasFlowDetailUrlService.findPageList(super.createPageGrid(request, dasFlowDetailUrlSearchBean));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasFlowDetailUrlSearchBean>();
		}
	}
	
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasFlowDetailUrlSearchBean dasFlowAttributionSearchBean) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.visitDetailInfo.getPath())));
			// 2、需要导出的数据
			List<DasFlowDetailUrl> recordList = dasFlowDetailUrlService.findList(dasFlowAttributionSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasFlowDetailUrl>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasFlowDetailUrl param) {
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
