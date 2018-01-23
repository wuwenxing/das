package com.gw.das.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.gw.das.common.enums.EnumIntf;

/**
 * 摘要：枚举类型select选择标签
 */
public class EnumListTag extends TagSupport {

	private static final long serialVersionUID = -4934685405732696502L;

	private String defaultVal; // 默认值
	private List<EnumIntf> dataList; // select数据列表
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
		if (dataList != null && dataList.size() > 0) {
			for (EnumIntf enumIntf : dataList) {
				if (enumIntf.getLabelKey().equals(this.defaultVal)) {
					sb.append(" <option value=\"" + enumIntf.getLabelKey() + "\" selected=\"selected\">");
				} else {
					sb.append(" <option value=\"" + enumIntf.getLabelKey() + "\">");
				}
				sb.append(enumIntf.getValue());
				sb.append(" </option>");
			}
		}
		return sb;
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
