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
import com.gw.das.common.enums.BehaviorTypeEnum;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.website.bean.DasFlowAttribution;
import com.gw.das.dao.website.bean.DasFlowAttributionSearchBean;
import com.gw.das.service.website.DasFlowAttributionService;
import com.gw.das.web.controller.system.BaseController;

/**
 * 来源媒介效果
 * @author wayne
 */
@Controller
@RequestMapping("/DasFlowAttributionController")
public class DasFlowAttributionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasFlowAttributionController.class);

	@Autowired
	private DasFlowAttributionService dasFlowAttributionService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("behaviorTypeEnum", BehaviorTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/dasFlowAttribution/dasFlowAttribution";
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
	public PageGrid<DasFlowAttributionSearchBean> pageList(HttpServletRequest request,
			@ModelAttribute DasFlowAttributionSearchBean dasFlowAttributionSearchBean) {
		try {
			PageGrid<DasFlowAttributionSearchBean> pageGrid = dasFlowAttributionService.findPageList(super.createPageGrid(request, dasFlowAttributionSearchBean));
//			for(Object obj: pageGrid.getRows()){
//				DasFlowAttribution record = (DasFlowAttribution)obj;
//			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasFlowAttributionSearchBean>();
		}
	}
	
	/**
	 * 来源媒介效果-导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasFlowAttributionSearchBean dasFlowAttributionSearchBean) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.attributionCount.getPath())));
			// 2、需要导出的数据
			List<DasFlowAttribution> recordList = dasFlowAttributionService.findList(dasFlowAttributionSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasFlowAttribution>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasFlowAttribution param) {
					if("platformType".equals(fieldName)){
						if(null == fieldValue){
							return "(全部)";
						}else{
							if("0".equals(String.valueOf(fieldValue))){
								return "PC端";
							}else if("1".equals(String.valueOf(fieldValue))){
								return "移动端";
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
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.attributionCount.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

}
