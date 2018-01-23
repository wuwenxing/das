package com.gw.das.web.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.gw.das.common.enums.SessionKeyEnum;
import com.gw.das.dao.system.entity.SystemMenuEntity;

/**
 * 摘要：权限验证标签
 */
public class AuthTag extends TagSupport {

	private static final long serialVersionUID = -9185465985775331288L;

	private String url;

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			String authFlag = "true";// 默认有权限
			JspWriter out = this.pageContext.getOut();
			HttpSession session = pageContext.getSession();
			
			// 先验证是否超级管理员
			String isSuperAdmin = session.getAttribute(SessionKeyEnum.superAdminFlag.getLabelKey()) + "";
			if ("Y".equals(isSuperAdmin)) {
				authFlag = "true";
			} else {
				// 验证该地址是否有设置权限
				@SuppressWarnings("unchecked")
				Map<String, SystemMenuEntity> map = (Map<String, SystemMenuEntity>)session.getAttribute(SessionKeyEnum.menuMap.getLabelKey());
				if(null == map || null == map.get(url)){
					authFlag = "false";
				}
			}
			
			out.print(authFlag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
