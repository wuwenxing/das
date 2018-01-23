package com.gw.das.web.controller.market;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.gw.das.common.enums.ResCodeEnum;
import com.gw.das.common.response.UtmEmailRequest;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.EmailTemplateLogEntity;
import com.gw.das.service.market.EmailTemplateLogService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/EmailTemplateLogController")
public class EmailTemplateLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(EmailTemplateLogController.class);

	@Autowired
	private EmailTemplateLogService emailTemplateLogService;
	
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
    		
			request.setAttribute("statusCode", ResCodeEnum.getList("emailTemplate"));
			request.setAttribute("templateId", request.getParameter("templateId"));
			return "/market/emailTemplate/emailTemplateLog";
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
	public PageGrid<EmailTemplateLogEntity> pageList(HttpServletRequest request,
			@ModelAttribute EmailTemplateLogEntity emailTemplateLogEntity, String type) {
		try {
			if(StringUtils.isNotBlank(type) && "unknown".equals(type)){
				// 查询未知错误
				emailTemplateLogEntity.setCompanyId(null);
			}else{
				emailTemplateLogEntity.setCompanyId(UserContext.get().getCompanyId());
			}
			PageGrid<EmailTemplateLogEntity> pageGrid = emailTemplateLogService.findPageList(super.createPageGrid(request, emailTemplateLogEntity));
			for(Object obj: pageGrid.getRows()){
				EmailTemplateLogEntity record = (EmailTemplateLogEntity)obj;
				record.setStatusCode(ResCodeEnum.format(record.getStatusCode()));
				// 参数中邮件使用“*”号替换
				String param = record.getParam();
				if (StringUtils.isNotBlank(param)) {
					UtmEmailRequest utmEmailRequest = JacksonUtil.readValue(param, UtmEmailRequest.class);
					String emails = utmEmailRequest.getEmails();
					String tempEmails = "";
					if (StringUtils.isNotBlank(emails)) {
						String[] emailsAry = emails.split(",");
						for (int i = 0; i < emailsAry.length; i++) {
							String email = emailsAry[i];
							if (i + 1 == emailsAry.length) {
								tempEmails += StringUtil.formatEmail(email);
							} else {
								tempEmails += StringUtil.formatEmail(email) + ",";
							}
						}
					}
					utmEmailRequest.setEmails(tempEmails);
					record.setParam(JacksonUtil.toJSon(utmEmailRequest));
				}
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<EmailTemplateLogEntity>();
		}
	}
	
}
