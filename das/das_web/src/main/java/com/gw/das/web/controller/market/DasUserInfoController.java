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
import com.gw.das.common.enums.BehaviorTypeEnum;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.website.bean.DasUserInfo;
import com.gw.das.dao.website.bean.DasUserInfoSearchBean;
import com.gw.das.service.website.DasUserInfoService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/DasUserInfoController")
public class DasUserInfoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasUserInfoController.class);

	@Autowired
	private DasUserInfoService dasUserInfoService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("behaviorTypeEnum", BehaviorTypeEnum.getList("dasUserInfo"));
			return "/market/dasUserInfo/dasUserInfo";
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
	public PageGrid<DasUserInfoSearchBean> pageList(HttpServletRequest request,
			@ModelAttribute DasUserInfoSearchBean dasUserInfoSeachBean) {
		try {
			PageGrid<DasUserInfoSearchBean> pageGrid = dasUserInfoService.findPageList(super.createPageGrid(request, dasUserInfoSeachBean));
			for(Object obj: pageGrid.getRows()){
				DasUserInfo record = (DasUserInfo)obj;
				if("3".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.demo.getValue());
				}else if("4".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.real.getValue());
				}else if("5".equals(record.getBehaviorType())){
					record.setBehaviorType(BehaviorTypeEnum.depesit.getValue());
				}
				record.setTel(StringUtil.formatPhone(record.getTel()));
				record.setEmail(StringUtil.formatEmail(record.getEmail()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasUserInfoSearchBean>();
		}
	}

}
