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
import com.gw.das.common.response.UtmCustomerGroupRequest;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.UserGroupLogEntity;
import com.gw.das.service.market.UserGroupLogService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/UserGroupLogController")
public class UserGroupLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserGroupLogController.class);

	@Autowired
	private UserGroupLogService userGroupLogService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("statusCode", ResCodeEnum.getList("userGroup"));
			request.setAttribute("groupId", request.getParameter("groupId"));
			request.setAttribute("type", request.getParameter("type"));
			return "/market/userGroup/userGroupLog";
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
	public PageGrid<UserGroupLogEntity> pageList(HttpServletRequest request,
			@ModelAttribute UserGroupLogEntity userGroupLogEntity, String type) {
		try {
			if(StringUtils.isNotBlank(type) && "unknown".equals(type)){
				// 查询未知错误
				userGroupLogEntity.setCompanyId(null);
			}else{
				userGroupLogEntity.setCompanyId(UserContext.get().getCompanyId());
			}
			PageGrid<UserGroupLogEntity> pageGrid = userGroupLogService.findPageList(super.createPageGrid(request, userGroupLogEntity));
			for(Object obj: pageGrid.getRows()){
				UserGroupLogEntity record = (UserGroupLogEntity)obj;
				record.setStatusCode(ResCodeEnum.format(record.getStatusCode()));
				// 参数中手机号或邮箱使用“*”号替换
				String param = record.getParam();
				if(StringUtils.isNotBlank(param)){
					UtmCustomerGroupRequest customerGroupRequest = JacksonUtil.readValue(param, UtmCustomerGroupRequest.class);
					String phones = customerGroupRequest.getPhones();
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
					customerGroupRequest.setPhones(tempPhones);
					
					String emails = customerGroupRequest.getEmails();
					String tempEmails = "";
					if(StringUtils.isNotBlank(emails)){
						String[] emailsAry = emails.split(",");
						for(int i=0; i<emailsAry.length; i++){
							String email = emailsAry[i];
							if(i + 1 == emailsAry.length){
								tempEmails += StringUtil.formatEmail(email);
							}else{
								tempEmails += StringUtil.formatEmail(email) + ",";
							}
						}
					}
					customerGroupRequest.setEmails(tempEmails);
					
					record.setParam(JacksonUtil.toJSon(customerGroupRequest));
				}
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<UserGroupLogEntity>();
		}
	}
	
}
