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
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.EmailDetailEntity;
import com.gw.das.service.market.EmailDetailService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/EmailDetailController")
public class EmailDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(EmailDetailController.class);

	@Autowired
	private EmailDetailService emailDetailService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("commitStatus", CommitStatusEnum.getList());
			request.setAttribute("sendStatus", SendStatusEnum.getEmailStatusList());
			request.setAttribute("emailId", request.getParameter("emailId"));
			return "/market/email/emailDetail";
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
	public PageGrid<EmailDetailEntity> pageList(HttpServletRequest request,
			@ModelAttribute EmailDetailEntity emailDetailEntity) {
		try {
			PageGrid<EmailDetailEntity> pageGrid = emailDetailService.findPageList(super.createPageGrid(request, emailDetailEntity));
			for(Object obj: pageGrid.getRows()){
				EmailDetailEntity record = (EmailDetailEntity)obj;
				record.setCommitStatus(CommitStatusEnum.format(record.getCommitStatus()));
				record.setSendStatus(SendStatusEnum.format(record.getSendStatus()));
				record.setRecEmail(StringUtil.formatEmail(record.getRecEmail()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<EmailDetailEntity>();
		}
	}
	
}
