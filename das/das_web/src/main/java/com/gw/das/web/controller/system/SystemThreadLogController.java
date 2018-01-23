package com.gw.das.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.SystemThreadEnum;
import com.gw.das.dao.system.entity.SystemThreadLogEntity;
import com.gw.das.service.system.SystemThreadLogService;
import com.gw.das.web.task.SmsAndEmailSendTask;
import com.gw.das.web.task.SyncSmsStatusTask;

@Controller
@RequestMapping("/SystemThreadLogController")
public class SystemThreadLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemThreadLogController.class);

	@Autowired
	private SystemThreadLogService systemThreadLogService;
	@Autowired
	private SmsAndEmailSendTask smsAndEmailSendTask;
	@Autowired
	private SyncSmsStatusTask syncSmsStatusTask;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("systemThreadEnum", SystemThreadEnum.getList());
			return "/system/threadLog/threadLog";
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
	public PageGrid<SystemThreadLogEntity> pageList(HttpServletRequest request,
			@ModelAttribute SystemThreadLogEntity emailTemplateLogEntity) {
		try {
			PageGrid<SystemThreadLogEntity> pageGrid = systemThreadLogService.findPageList(super.createPageGrid(request, emailTemplateLogEntity));
			for (Object obj : pageGrid.getRows()) {
				SystemThreadLogEntity record = (SystemThreadLogEntity) obj;
				record.setCode(SystemThreadEnum.format(record.getCode()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SystemThreadLogEntity>();
		}
	}

	/**
	 * 手动调用线程
	 */
	@RequestMapping("/call/{className}")
	@ResponseBody
	public String callPmAllDepositData(@PathVariable String className) {
		String msg = "call success!";
		try{
			if(SystemThreadEnum.syncSmsStatusTask.getLabelKey().equals(className)){
				syncSmsStatusTask.initDate();
			}else if(SystemThreadEnum.smsAndEmailSendTask.getLabelKey().equals(className)){
				smsAndEmailSendTask.initDate();
			}
		}catch(Exception e){
			msg = "call error:" + e.getMessage();
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
        return msg;
	}

	
}
