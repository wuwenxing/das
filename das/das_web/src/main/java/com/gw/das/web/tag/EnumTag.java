package com.gw.das.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.gw.das.common.enums.EnumIntf;

/**
 * 摘要：枚举类型翻译
 */
public class EnumTag extends TagSupport {

	private static final long serialVersionUID = -9185465985775331288L;

	private String value;
	private String defaultVal; // 默认值,如果没有给值，则默认值为""
	private List<EnumIntf> dataList; // 翻译对应列表

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(defaultVal)) {
			defaultVal = ""; // 默认值为""
		}

		EnumIntf boEnum = null;
		if (dataList != null && dataList.size() > 0) {
			for (EnumIntf enumIntf : dataList) {
				if (enumIntf.getLabelKey().equals(value)) {
					boEnum = enumIntf;
					break;
				}
			}
		}

		if (boEnum != null) {
			sb.append(boEnum.getValue());
		} else {
			sb.append(defaultVal);
		}
		return sb;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public List<EnumIntf> getDataList() {
		return dataList;
	}

	public void setDataList(List<EnumIntf> dataList) {
		this.dataList = dataList;
	}

}
