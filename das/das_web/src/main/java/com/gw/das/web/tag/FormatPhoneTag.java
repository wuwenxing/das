package com.gw.das.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.utils.StringUtil;

/**
 * 格式手机号码，中间使用*号代替
 */
public class FormatPhoneTag extends TagSupport {

	private static final Logger logger = LoggerFactory.getLogger(FormatPhoneTag.class);
	
	private static final long serialVersionUID = -4046584473485629003L;

	
	private String value;

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(StringUtil.formatPhone(value));
		} catch (IOException e) {
			logger.error("格式手机号码异常:" + value, e);
		}
		return EVAL_PAGE;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
