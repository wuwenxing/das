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
import com.gw.das.common.enums.FlowChannelEnum;
import com.gw.das.common.enums.FlowStatusEnum;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.FlowLogDetailEntity;
import com.gw.das.service.market.FlowLogDetailService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/FlowLogDetailController")
public class FlowLogDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FlowLogDetailController.class);

	@Autowired
	private FlowLogDetailService flowLogDetailService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("commitStatus", CommitStatusEnum.getList());
			request.setAttribute("sendStatus", FlowStatusEnum.getList());
			request.setAttribute("flowLogId", request.getParameter("flowLogId"));
			return "/market/flowLog/flowLogDetail";
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
	public PageGrid<FlowLogDetailEntity> pageList(HttpServletRequest request,
			@ModelAttribute FlowLogDetailEntity flowLogDetailEntity) {
		try {
			PageGrid<FlowLogDetailEntity> pageGrid = flowLogDetailService.findPageList(super.createPageGrid(request, flowLogDetailEntity));
			for(Object obj: pageGrid.getRows()){
				FlowLogDetailEntity record = (FlowLogDetailEntity)obj;
				record.setCommitStatus(CommitStatusEnum.format(record.getCommitStatus()));
				record.setSendStatus(FlowStatusEnum.format(record.getSendStatus()));
				record.setPhone(StringUtil.formatPhone(record.getPhone()));
				record.setInterfaceType(FlowChannelEnum.format(record.getInterfaceType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<FlowLogDetailEntity>();
		}
	}
	
}
