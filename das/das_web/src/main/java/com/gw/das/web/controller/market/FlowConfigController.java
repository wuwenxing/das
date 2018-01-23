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

import com.gw.das.common.enums.FlowChannelEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.response.ResultCode;
import com.gw.das.dao.market.entity.FlowConfigEntity;
import com.gw.das.service.market.FlowConfigService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/FlowConfigController")
public class FlowConfigController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FlowConfigController.class);

	@Autowired
	private FlowConfigService flowConfigService;
	
	/**
	 * 流量通道设置-跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request, ModelMap map) {
		try {
			// 根据业务平台获取设置的流量通道
			FlowConfigEntity flow = flowConfigService.findByCompanyId();
			if(null != flow){
				request.setAttribute("flowChannel", flow);
			}
			request.setAttribute("flowChannelList", FlowChannelEnum.getList());
			return "/market/flowConfig/flowConfig";
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
	public ResultCode save(HttpServletRequest request, String flowChannel) {
		try {
			FlowConfigEntity flow = flowConfigService.findByCompanyId();
			if(null == flow){
				flow = new FlowConfigEntity();
			}
			flow.setFlowChannel(flowChannel);
			flowConfigService.saveOrUpdate(flow);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
}
