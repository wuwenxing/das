package com.gw.das.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 格式邮件地址，末尾4位使用*号代替
 */
public class FormatEmailTag extends TagSupport {

	private static final Logger logger = LoggerFactory.getLogger(FormatEmailTag.class);
	
	private static final long serialVersionUID = -4046584473485629003L;

	
	private String value;

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			if(StringUtils.isNotBlank(value) && value.indexOf("@") != -1){
				String emailPrefix = value.split("@")[0];
				String emailSuffix = value.split("@")[1];
				if(StringUtils.isNotBlank(emailPrefix) && emailPrefix.length() > 4){
					emailPrefix = emailPrefix.substring(0, emailPrefix.length()-4) + "****";
				}else{
					emailPrefix = "****";
				}
				value = emailPrefix + "@" + emailSuffix;
			}
			out.print(value);
		} catch (IOException e) {
			logger.error("格式邮件地址异常:" + value, e);
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
