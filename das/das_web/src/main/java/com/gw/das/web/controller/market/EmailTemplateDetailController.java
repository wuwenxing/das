package com.gw.das.web.controller.market;

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
import com.gw.das.common.enums.CommitStatusEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.EmailTemplateDetailContentEntity;
import com.gw.das.dao.market.entity.EmailTemplateDetailEntity;
import com.gw.das.service.market.EmailTemplateDetailContentService;
import com.gw.das.service.market.EmailTemplateDetailService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/EmailTemplateDetailController")
public class EmailTemplateDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(EmailTemplateDetailController.class);

	@Autowired
	private EmailTemplateDetailService emailTemplateDetailService;
	@Autowired
	private EmailTemplateDetailContentService emailTemplateDetailContentService;
	
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
			request.setAttribute("sendStatus", SendStatusEnum.getEmailStatusList());
			request.setAttribute("templateId", request.getParameter("templateId"));
			return "/market/emailTemplate/emailTemplateDetail";
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
	public PageGrid<EmailTemplateDetailEntity> pageList(HttpServletRequest request,
			@ModelAttribute EmailTemplateDetailEntity emailTemplateDetailEntity) {
		try {
			PageGrid<EmailTemplateDetailEntity> pageGrid = emailTemplateDetailService.findPageList(super.createPageGrid(request, emailTemplateDetailEntity));
			for(Object obj: pageGrid.getRows()){
				EmailTemplateDetailEntity record = (EmailTemplateDetailEntity)obj;
				record.setCommitStatus(CommitStatusEnum.format(record.getCommitStatus()));
				record.setSendStatus(SendStatusEnum.format(record.getSendStatus()));
				record.setRecEmail(StringUtil.formatEmail(record.getRecEmail()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<EmailTemplateDetailEntity>();
		}
	}

	/**
	 * 邮件详细预览
	 */
	@RequestMapping("/preview/{detailId}")
	public String preview(final HttpServletRequest request, @PathVariable final Long detailId) {
		try {
			EmailTemplateDetailEntity model = emailTemplateDetailService.findById(detailId);
			EmailTemplateDetailContentEntity detailModel = emailTemplateDetailContentService.findById(model.getContentId());
			request.setAttribute("title", model.getTitle());
			request.setAttribute("content", detailModel.getContent());
		} catch (Exception e) {
			logger.error("查看邮件内容异常：" + e.getMessage(), e);
		}
		return "market/emailTemplate/preview";
	}
	
}
