package com.gw.das.web.controller.website;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import com.gw.das.common.enums.BehaviorTypeEnum;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.website.bean.DasFlowDetail;
import com.gw.das.dao.website.bean.DasFlowDetailSearchBean;
import com.gw.das.service.website.DasUserBehaviorService;
import com.gw.das.web.controller.system.BaseController;

/**
 * 官网行为详细
 * @author wayne
 */
@Controller
@RequestMapping("/DasUserBehaviorController")
public class DasUserBehaviorController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasUserBehaviorController.class);

	@Autowired
	private DasUserBehaviorService dasUserBehaviorService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			String startTime = DateUtil.formatDateToString(DateUtil.addHours(new Date(), -4), "yyyy-MM-dd HH");
			String endTime = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
			startTime += ":00:00";
			endTime += " 23:59:59";
    		request.setAttribute("startTime", startTime);
    		request.setAttribute("endTime", endTime);
			request.setAttribute("behaviorTypeEnum", BehaviorTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/behavior/behavior";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/pageByFlowStatistics", method = { RequestMethod.GET })
	public String pageByFlowStatistics(HttpServletRequest request) {
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
	    	request.setAttribute("platformType", request.getParameter("platformType"));
			request.setAttribute("behaviorTypeEnum", BehaviorTypeEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			return "/behavior/behavior";
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
	public PageGrid<DasFlowDetailSearchBean> pageList(HttpServletRequest request,
			@ModelAttribute DasFlowDetailSearchBean dasUserInfoSeachBean) {
		try {
			PageGrid<DasFlowDetailSearchBean> pageGrid = dasUserBehaviorService.findPageList(super.createPageGrid(request, dasUserInfoSeachBean));
			for(Object obj: pageGrid.getRows()){
				DasFlowDetail record = (DasFlowDetail)obj;
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
			return new PageGrid<DasFlowDetailSearchBean>();
		}
	}

	/**
	 * 不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findList", method = { RequestMethod.POST })
	@ResponseBody
	public List<DasFlowDetail> findList(HttpServletRequest request,
			@ModelAttribute DasFlowDetailSearchBean dasUserInfoSeachBean) {
		try {
			List<DasFlowDetail> list = dasUserBehaviorService.findList(dasUserInfoSeachBean);
			for(DasFlowDetail obj: list){
				DasFlowDetail record = (DasFlowDetail)obj;
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
			return new ArrayList<DasFlowDetail>();
		}
	}
	
	/**
	 * 官网行为详细-导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DasFlowDetailSearchBean dasFlowDetailSearchBean) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.detailSummaryInfo.getPath())));
			// 2、需要导出的数据
			List<DasFlowDetail> recordList = dasUserBehaviorService.findList(dasFlowDetailSearchBean);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<DasFlowDetail>() {
				String behaviorDetail = "";
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, DasFlowDetail param) {
					if("behaviorType".equals(fieldName)){
						/*if(null != fieldValue){
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
						}*/
						
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
					if("platformType".equals(fieldName)){
						if(null == fieldValue){
							return "";
						}else{
							if("0".equals(String.valueOf(fieldValue))){
								return "PC端";
							}else if("1".equals(String.valueOf(fieldValue))){
								return "移动端";
							}else{
								return "";
							}
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.detailSummaryInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

}
