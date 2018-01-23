package com.gw.das.web.controller.market;

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
import com.gw.das.common.enums.CommitStatusEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.enums.SmsChannelEnum;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.SmsTemplateDetailEntity;
import com.gw.das.service.market.SmsTemplateDetailService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/SmsTemplateDetailController")
public class SmsTemplateDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SmsTemplateDetailController.class);

	@Autowired
	private SmsTemplateDetailService smsTemplateDetailService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			String startDate = DateUtil.formatDateToString(DateUtil.getCurrentMonthStartTime(), "yyyy-MM-dd HH:mm:ss");
			String endDate = DateUtil.formatDateToString(DateUtil.getCurrentMonthEndTime(), "yyyy-MM-dd HH:mm:ss");
    		request.setAttribute("startDate", startDate);
    		request.setAttribute("endDate", endDate);
    		
			request.setAttribute("commitStatus", CommitStatusEnum.getList());
			request.setAttribute("sendStatus", SendStatusEnum.getList());
			request.setAttribute("templateId", request.getParameter("templateId"));
			return "/market/smsTemplate/smsTemplateDetail";
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
	public PageGrid<SmsTemplateDetailEntity> pageList(HttpServletRequest request,
			@ModelAttribute SmsTemplateDetailEntity smsTemplateDetailEntity) {
		try {
			PageGrid<SmsTemplateDetailEntity> pageGrid = smsTemplateDetailService.findPageList(super.createPageGrid(request, smsTemplateDetailEntity));
			for(Object obj: pageGrid.getRows()){
				SmsTemplateDetailEntity record = (SmsTemplateDetailEntity)obj;
				record.setCommitStatus(CommitStatusEnum.format(record.getCommitStatus()));
				record.setSendStatus(SendStatusEnum.format(record.getSendStatus()));
				record.setPhone(StringUtil.formatPhone(record.getPhone()));
				record.setInterfaceType(SmsChannelEnum.format(record.getInterfaceType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SmsTemplateDetailEntity>();
		}
	}
	
}
