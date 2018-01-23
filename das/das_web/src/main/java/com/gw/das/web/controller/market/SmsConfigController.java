package com.gw.das.web.controller.market;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.enums.SmsChannelEnum;
import com.gw.das.common.enums.SmsSignEnum;
import com.gw.das.common.response.ResultCode;
import com.gw.das.dao.market.entity.SmsConfigEntity;
import com.gw.das.service.market.SmsConfigService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/SmsConfigController")
public class SmsConfigController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SmsConfigController.class);

	@Autowired
	private SmsConfigService smsConfigService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request, ModelMap map) {
		try {
			Long companyId = UserContext.get().getCompanyId();
			request.setAttribute("smsSignList", SmsSignEnum.getList(companyId));
			// 查询每个签名对应的短信通道,放入map
			for(SmsSignEnum signEnum: SmsSignEnum.getList(companyId)){
				SmsConfigEntity smsConfigEntity = smsConfigService.findBySign(signEnum.getLabelKey());
				map.put(signEnum.getLabelKey(), smsConfigEntity);
			}
			request.setAttribute("smsChannelList", SmsChannelEnum.getList(companyId));
			request.setAttribute("smsSignList", SmsSignEnum.getList(companyId));
			
			return "/market/smsConfig/smsConfig";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request) {
		try {
			String[] signs = request.getParameterValues("sign");
			String[] smsChannels = request.getParameterValues("smsChannel");
			smsConfigService.saveOrUpdate(signs, smsChannels);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

}
