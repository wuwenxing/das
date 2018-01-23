package com.gw.das.web.interceptors;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.SessionKeyEnum;
import com.gw.das.common.utils.SystemPathUtil;
import com.gw.das.dao.system.entity.SystemMenuEntity;
import com.gw.das.dao.system.entity.SystemUserEntity;
import com.gw.das.service.cache.MenuCache;
import com.gw.das.web.controller.system.BaseController;
import com.gw.das.web.sessionbean.Client;

/**
 * 权限拦截器
 * @author wayne
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	// 排除不拦截的URL
	public static Map<String, String> excludeUrls = new HashMap<String, String>();
	
	static{
		excludeUrls.putAll(BaseController.urls);
		excludeUrls.putAll(BaseController.interfaceUrls);
		excludeUrls.put("LoginController/home", "");
		excludeUrls.put("LoginController/index", "");
		excludeUrls.put("LoginController/system", "");
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestPath = SystemPathUtil.getRequestPath(request);
		if(StringUtils.isBlank(requestPath)){
			response.sendRedirect(request.getContextPath() + "/LoginController/login");
			return false;
		}
		if (StringUtils.isNotBlank(requestPath) && null != excludeUrls.get(requestPath)) {
			return true;
		} else {
			Client client = (Client)request.getSession().getAttribute(SessionKeyEnum.client.getLabelKey());
			if (null != client && null != client.getUser()) {
				return actionAuth(request, response, client.getUser(), requestPath);
			}else{
				jumpLoginPage(request, response);
				return false;
			}
		}
	}

	/**
	 * 功能：跳转到登录页
	 * 
	 * @param request
	 * @param response
	 */
	private void jumpLoginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestType = request.getHeader("X-Requested-With");
		if (!"XMLHttpRequest".equalsIgnoreCase(requestType)) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			response.sendRedirect(request.getContextPath() + "/page/common/sessionTimeOut.jsp");
		}else{
			// ajax请求
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
			response.setHeader(ErrorCodeEnum.sessionTimeOut.getLabelKey(), ErrorCodeEnum.sessionTimeOut.getLabelKey());
			PrintWriter printWriter = response.getWriter();
			printWriter.print(ErrorCodeEnum.sessionTimeOut.getLabelKey());
			printWriter.flush();
			printWriter.close();
		}
	}

	/**
	 * 动作拦截
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	private boolean actionAuth(HttpServletRequest request, HttpServletResponse response, SystemUserEntity user, String requestPath)
			throws Exception {
		logger.info("userNo:" + user.getUserName() + "|comanyId:" + user.getCompanyId() + "|requestPath:" + requestPath);
		// 验证菜单权限,根据requestPath判断用户是否有权限
		boolean hasRecord = true;
		if(StringUtils.isNotBlank(requestPath)){
			String superAdminFlag = request.getSession().getAttribute(SessionKeyEnum.superAdminFlag.getLabelKey()) + "";
			if(StringUtils.isNotBlank(superAdminFlag) && "Y".equals(superAdminFlag)){
				hasRecord = true; // 有权限
			}else{
				// 如在需要权限控制的url中，则判断是否有权限
				String companyId = request.getSession().getAttribute(SessionKeyEnum.companyId.getLabelKey()) + "";
				Map<String, String> menuUrlMap = MenuCache.getMenuUrlList(companyId);
				if(null != menuUrlMap.get(requestPath)){
					@SuppressWarnings("unchecked")
					Map<String, SystemMenuEntity> map = (Map<String, SystemMenuEntity>)request.getSession().getAttribute(SessionKeyEnum.menuMap.getLabelKey());
					if(null != map){
						SystemMenuEntity menuEntity = map.get(requestPath);
						if(null == menuEntity){
							hasRecord = false; // 无权限
						}
					}else{
						hasRecord = false; // 无权限
					}
				}
			}
		}
		if (!hasRecord) {
			String requestType = request.getHeader("X-Requested-With");
			if (!"XMLHttpRequest".equalsIgnoreCase(requestType)) {
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
				response.sendRedirect(request.getContextPath() + "/page/common/noAuth.jsp");
			} else {
				// ajax请求
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json; charset=utf-8");
				response.setHeader(ErrorCodeEnum.noPermission.getLabelKey(), ErrorCodeEnum.noPermission.getLabelKey());
				PrintWriter printWriter = response.getWriter();
				printWriter.print(ErrorCodeEnum.noPermission.getLabelKey());
				printWriter.flush();
				printWriter.close();
			}
			return false;
		}
		return true;
	}

}
