package com.gw.das.web.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.utils.DateUtil;

/**
 * 日期自定义标签
 */
public class DateTag extends TagSupport {

	private static final Logger logger = LoggerFactory.getLogger(DateTag.class);
	
	private static final long serialVersionUID = -4046584473485629003L;

	private String type = "1";// 获取日期的类型，默认为：1
	private String format = "yyyy-MM-dd HH:mm:ss"; // 格式化日期，默认为："yyyy-MM-dd HH:mm:ss"

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			if("yesterdayStart".equals(type)){
				// 昨天开始时间
				String yesterdayStart = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeStartTime(), format);
				out.print(yesterdayStart);
			}else if("yesterdayEnd".equals(type)){
				// 昨天结束时间
				String yesterdayEnd = DateUtil.formatDateToString(DateUtil.getCurrentDateBeforeEndTime(), format);
				out.print(yesterdayEnd);
			}else if("dayStart".equals(type)){
				// 当天开始时间
				String dayStart = DateUtil.formatDateToString(DateUtil.getCurrentDateStartTime(), format);
				out.print(dayStart);
			}else if("dayEnd".equals(type)){
				// 当天结束时间
				String dayEnd = DateUtil.formatDateToString(DateUtil.getCurrentDateEndTime(), format);
				out.print(dayEnd);
			}else if("monthStart".equals(type)){
				// 当月第一天开始时间
				String monthStart = DateUtil.formatDateToString(DateUtil.getCurrentMonthStartTime(), format);
				out.print(monthStart);
			}else if("monthEnd".equals(type)){
				// 当月最后一天结束时间
				String monthEnd = DateUtil.formatDateToString(DateUtil.getCurrentMonthEndTime(), format);
				out.print(monthEnd);
			}else if("yearStart".equals(type)){
				// 当年第一天开始时间
				String yearStart = DateUtil.formatDateToString(DateUtil.getCurrentYearStartTime(), format);
				out.print(yearStart);
			}else if("yearEnd".equals(type)){
				// 当年最后一天结束时间
				String yearEnd = DateUtil.formatDateToString(DateUtil.getCurrentYearEndTime(), format);
				out.print(yearEnd);
			}else if("lessOneMonths".equals(type)){
				// 前1个月的当天时间
				String addMonths = DateUtil.formatDateToString(DateUtil.addMonths(new Date(),-1), format);
				out.print(addMonths);
			}else if("lessThreeMonths".equals(type)){
				// 前3个月的当天时间
				String addMonths = DateUtil.formatDateToString(DateUtil.addMonths(new Date(),-3), format);
				out.print(addMonths);
			}else if("addSixMonths".equals(type)){
				//最近6个月
				String addMonths = DateUtil.formatDateToString(DateUtil.addMonths(new Date(),-6), format);
				out.print(addMonths);
			}else if("halfaMonth".equals(type)){
				//近半个月
				String addMonths = DateUtil.formatDateToString(DateUtil.addDays(new Date(),-14), format);
				out.print(addMonths);
			}else if("halfaWeek".equals(type)){
				//近一周
				String addWeek = DateUtil.formatDateToString(DateUtil.addDays(new Date(),-7), format);
				out.print(addWeek);
			}else if("beforeYesterdayStart".equals(type)){
				// 前天开始时间
				String beforeYesterdayStart = DateUtil.formatDateToString(DateUtil.addDays(new Date(),-2), format);
				out.print(beforeYesterdayStart);
			}else if("lessOneYears".equals(type)){
				//近1年
				String addMonths = DateUtil.formatDateToString(DateUtil.addMonths(new Date(),-12), format);
				out.print(addMonths);
			}
		} catch (IOException e) {
			logger.error("日期自定义标签异常:type = " + type + ", format = " + format, e);
		}
		return EVAL_PAGE;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
