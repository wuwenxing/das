package com.gw.das.api.conf;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.gw.das.api.common.enums.CompanyEnum;
import com.gw.das.api.common.response.ApiFailEnum;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.common.token.TokenUtil;
import com.gw.das.api.common.token.VerifyTokenResult;
import com.gw.das.api.common.utils.JacksonUtil;

/**
 * 访问安全验证,验证token是否合法(除Druid监控地址不验证) 使用注解标注过滤器
 * 
 * @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器 属性filterName声明过滤器的名称,可选
 *                                                属性urlPatterns指定要过滤的URL模式,
 *                                                也可使用属性value来声明.(
 *                                                指定要过滤的URL模式是必选属性)
 * @author: James.pu
 * @Email : James.pu@gwtsz.net
 * @Create Date: 2017年6月6日
 */
@WebFilter(filterName = "securityFilter", urlPatterns = "/*")
public class SecurityFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.debug("==>>securityFilter init ...");
	}

	@Override
	public void destroy() {
		logger.debug("==>>securityFilter destroy ...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		String requestURI = httpRequest.getRequestURI();
		String companyId = httpRequest.getParameter("companyId");
		String token = httpRequest.getParameter("token");
//		companyId = CompanyEnum.pm.getLabelKey();
//		token = TokenUtil.getTokenStr(CompanyEnum.pm.getAppId(), CompanyEnum.pm.getAppScret());
		logger.debug("==>>doFilter[requestURI={},companyId={},token={}]", requestURI, companyId, token);
		if (!StringUtils.isEmpty(requestURI)) {
			if (requestURI.contains("druid")) {
				// druid数据库监控页面不进行token验证
				logger.debug("==>>doFilter druid success");
				chain.doFilter(request, httpResponse);
			} else {
				
				if("/riskControl/getAccountBlacklist".equals(requestURI) || "/riskControl/channelWarning".equals(requestURI)){
					logger.debug("==>>doFilter success");
					chain.doFilter(request, response);
					return ;
				}
				
				if (StringUtils.isEmpty(companyId)) {
					logger.warn("==>>doFilter [companyId is blank!]");
					ApiResult result = new ApiResult(ApiFailEnum.signCheckError1);
					responseContent(httpResponse, result);
					return;
				}
				if (StringUtils.isEmpty(token) || token.trim().length() <= 0) {
					logger.warn("==>>doFilter[token is blank!]");
					ApiResult result = new ApiResult(ApiFailEnum.signCheckError3);
					responseContent(httpResponse, result);
					return;
				}
				// 从接口验证Token值
				VerifyTokenResult verifyTokenResult = TokenUtil.checkToken(requestURI, token);
				if (null != verifyTokenResult && "0".equals(verifyTokenResult.getCode())) {
					// 根据token验证对应的companyId是否一致
					Map map = JacksonUtil.toHashMap(verifyTokenResult.getData());
					String systemCompanyName = String.valueOf(map.get("flag"));
					String systemCompanyId = "";
					try{
						systemCompanyId = CompanyEnum.valueOf(systemCompanyName).getLabelKey();
					}catch(Exception e){
						systemCompanyId = "";
					}
					if (!StringUtils.isEmpty(systemCompanyId) && systemCompanyId.equals(companyId)) {
						logger.debug("==>>doFilter success");
						chain.doFilter(request, response);
					} else {
						logger.warn("==>>doFilter[companyId({}) is invalid!]", companyId);
						ApiResult result = new ApiResult(ApiFailEnum.signCheckError2);
						responseContent(response, result);
					}
				} else {
					logger.warn("==>>doFilter[token({}) is invalid!]", token);
					ApiResult result = new ApiResult(ApiFailEnum.signCheckError4);
					responseContent(httpResponse, result);
					return;
				}
			}
		} else {
			chain.doFilter(request, httpResponse);
		}
	}

	/**
	 * 封装响应内容
	 * 
	 * @param response
	 * @param content
	 */
	private static void responseContent(ServletResponse response, ApiResult result) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(JacksonUtil.toJSon(result));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
