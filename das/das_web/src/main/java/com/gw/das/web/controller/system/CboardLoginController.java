package com.gw.das.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.SystemConfigUtil;

@Controller
@RequestMapping("/CboardLoginController")
public class CboardLoginController extends BaseController {

	/**
	 * 跳转登陆页面
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String login(HttpServletRequest request) {
		request.setAttribute("cboardLinkUrl", SystemConfigUtil.getProperty(SystemConfigEnum.cboardLinkUrl));
		return "login/cboardLogin";
	}

}
