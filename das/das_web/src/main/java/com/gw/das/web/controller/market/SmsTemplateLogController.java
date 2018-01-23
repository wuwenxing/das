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
import com.gw.das.common.response.UtmSmsRequest;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.SmsTemplateLogEntity;
import com.gw.das.service.market.SmsTemplateLogService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/SmsTemplateLogController")
public class SmsTemplateLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SmsTemplateLogController.class);

	@Autowired
	private SmsTemplateLogService smsTemplateLogService;
	
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
    		
			request.setAttribute("statusCode", ResCodeEnum.getList("smsTemplate"));
			request.setAttribute("templateId", request.getParameter("templateId"));
			return "/market/smsTemplate/smsTemplateLog";
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
	public PageGrid<SmsTemplateLogEntity> pageList(HttpServletRequest request,
			@ModelAttribute SmsTemplateLogEntity smsTemplateLogEntity, String type) {
		try {
			if(StringUtils.isNotBlank(type) && "unknown".equals(type)){
				// 查询未知错误
				smsTemplateLogEntity.setCompanyId(null);
			}else{
				smsTemplateLogEntity.setCompanyId(UserContext.get().getCompanyId());
			}
			PageGrid<SmsTemplateLogEntity> pageGrid = smsTemplateLogService.findPageList(super.createPageGrid(request, smsTemplateLogEntity));
			for(Object obj: pageGrid.getRows()){
				SmsTemplateLogEntity record = (SmsTemplateLogEntity)obj;
				record.setStatusCode(ResCodeEnum.format(record.getStatusCode()));
				// 参数中手机号使用“*”号替换
				String param = record.getParam();
				if(StringUtils.isNotBlank(param)){
					UtmSmsRequest utmSmsRequest = JacksonUtil.readValue(param, UtmSmsRequest.class);
					String phones = utmSmsRequest.getPhones();
					String tempPhones = "";
					if(StringUtils.isNotBlank(phones)){
						String[] phonesAry = phones.split(",");
						for(int i=0; i<phonesAry.length; i++){
							String phone = phonesAry[i];
							if(i + 1 == phonesAry.length){
								tempPhones += StringUtil.formatPhone(phone);
							}else{
								tempPhones += StringUtil.formatPhone(phone) + ",";
							}
						}
					}
					utmSmsRequest.setPhones(tempPhones);
					record.setParam(JacksonUtil.toJSon(utmSmsRequest));
				}
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SmsTemplateLogEntity>();
		}
	}
	
}
