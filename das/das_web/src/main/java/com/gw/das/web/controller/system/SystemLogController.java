package com.gw.das.web.controller.system;

import javax.servlet.http.HttpServletRequest;

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
import com.gw.das.common.enums.SystemLogEnum;
import com.gw.das.dao.system.entity.SystemLogEntity;
import com.gw.das.service.system.SystemLogService;

@Controller
@RequestMapping("/SystemLogController")
public class SystemLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemLogController.class);

	@Autowired
	private SystemLogService systemLogService;

	/**
	 * 日志-跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("companyEnum", CompanyEnum.getList());
			request.setAttribute("systemLogEnum", SystemLogEnum.getList());
			return "/system/systemLog/systemLog";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 日志-分页查询
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<SystemLogEntity> pageList(HttpServletRequest request,
			@ModelAttribute SystemLogEntity systemLogEntity) {
		try {
			PageGrid<SystemLogEntity> pageGrid = systemLogService
					.findPageList(super.createPageGrid(request, systemLogEntity));
			for(Object obj: pageGrid.getRows()){
				SystemLogEntity record = (SystemLogEntity)obj;
				record.setLogType(SystemLogEnum.format(record.getLogType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SystemLogEntity>();
		}
	}

}
