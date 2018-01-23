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
import com.gw.das.common.enums.AccountTypeEnum;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.UserGroupDetailEntity;
import com.gw.das.service.market.UserGroupDetailService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/UserGroupDetailController")
public class UserGroupDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserGroupDetailController.class);

	@Autowired
	private UserGroupDetailService userGroupDetailService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("groupId", request.getParameter("groupId"));
			return "/market/userGroup/userGroupDetail";
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
	public PageGrid<UserGroupDetailEntity> pageList(HttpServletRequest request,
			@ModelAttribute UserGroupDetailEntity userGroupDetailEntity) {
		try {
			PageGrid<UserGroupDetailEntity> pageGrid = userGroupDetailService.findPageList(super.createPageGrid(request, userGroupDetailEntity));
			for(Object obj: pageGrid.getRows()){
				UserGroupDetailEntity record = (UserGroupDetailEntity)obj;
				if(AccountTypeEnum.phone.getLabelKey().equals(record.getAccountType())){
					record.setAccount(StringUtil.formatPhone(record.getAccount()));
				}else if(AccountTypeEnum.email.getLabelKey().equals(record.getAccountType())){
					record.setAccount(StringUtil.formatEmail(record.getAccount()));
				}
				record.setAccountType(AccountTypeEnum.format(record.getAccountType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<UserGroupDetailEntity>();
		}
	}
	
}
