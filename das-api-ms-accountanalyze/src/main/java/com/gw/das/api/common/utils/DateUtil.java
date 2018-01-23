package com.gw.das.api.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	
	public static String format1 = "yyyy-MM-dd";
	public static String format2 = "yyyy-MM-dd HH:mm:ss";

	public static String getCurrentDate() {
		return formatDateToString(new Date(), "yyyy-MM-dd");
	}

	public static String getCurrentTime() {
		return formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDateToString(Date date, String format) {
		if(date==null){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static String formatDate(Date date) {
		return formatDateToString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatDateToString(Date date) {
		return formatDateToString(date, "yyyy-MM-dd");
	}

	public static String formatDateToString(long millis, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(millis);
		return dateFormat.format(c.getTime());
	}
	
	public static Date stringToDate(String date, String format) {
		if(date==null){
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date day = null;
		try {
			day = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

	public static Date stringToDate(String date) {
		return stringToDate(date, "yyyy-MM-dd");
	}

	public static Date longtimeToDate(Long longtime, String format) {
		if(longtime==null){
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date day = null;
		try {
			String d = dateFormat.format(longtime);
			day = dateFormat.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	
	
	/**
	 * 
	 * 方法用途: 对日期进行小时操作<br>
	 * 实现步骤: <br>
	 * @param date
	 * @param hh
	 * @return
	 */
	public static Date operationalAddHour(Date date, int hh) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.add(Calendar.HOUR_OF_DAY, hh);
		return c1.getTime();
	}

	/**
	 * 
	 * 方法用途: 对日期进行分钟操作<br>
	 * 实现步骤: <br>
	 * @param date
	 * @param mm
	 * @return
	 */
	public static Date operationalAddMinute(Date date, int mm) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.add(Calendar.MINUTE, mm);
		return c1.getTime();
	}

	/**
	 * 获取当天的前?天开始时间,精确到秒
	 * @param day 需要设置前?天的天数
	 * @return
	 */
	public static Date getCurrentDateStartTimeByDay(int day) {
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        //System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
		return cal.getTime();
	}
	
	/**
	 * 获取当天的前一天开始时间,精确到秒
	 */
	public static Date getCurrentDateBeforeStartTime() {
		Calendar cal = Calendar.getInstance();//获取当前日期 
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        //System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
     	return cal.getTime();
	}
	
	/**
	 * 获取当天的前一天结束时间,精确到秒
	 */
	public static Date getCurrentDateBeforeEndTime() {
		Calendar cal = Calendar.getInstance();//获取当前日期 
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        //System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
     	return cal.getTime();
	}
	
	/**
	 * 获取当天的开始时间,精确到秒
	 */
	public static Date getCurrentDateStartTime() {
		Calendar cal = Calendar.getInstance();//获取当前日期 
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        //System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
     	return cal.getTime();
	}
	
	/**
	 * 获取当天的结束时间,精确到秒
	 */
	public static Date getCurrentDateEndTime() {
		Calendar cal = Calendar.getInstance();//获取当前日期 
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        //System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
     	return cal.getTime();
	}
	
	/**
	 * 获取当月的开始时间,精确到秒
	 */
	public static Date getCurrentMonthStartTime() {
		Calendar cal = Calendar.getInstance();//获取当前日期 
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置当月的第1天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        //System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
		return cal.getTime();
	}
	
	/**
	 * 获取当月的结束时间,精确到秒
	 */
	public static Date getCurrentMonthEndTime() {
		Calendar cal = Calendar.getInstance();//获取当前日期 
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));//设置当月的最后一天
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        //System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
		return cal.getTime();
	}
	
	/**
	 * 获取当年的开始时间,精确到秒
	 */
	public static Date getCurrentYearStartTime() {
		Calendar cal = Calendar.getInstance();//获取当前日期 
		cal.add(Calendar.MONTH, - cal.get(Calendar.MONTH));//设置为1月
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置当月的第1天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        //System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
		return cal.getTime();
	}
	
	/**
	 * 获取当年的结束时间,精确到秒
	 */
	public static Date getCurrentYearEndTime() {
		Calendar cal = Calendar.getInstance();//获取当前日期 
		cal.add(Calendar.MONTH, - cal.get(Calendar.MONTH) + 11);//设置为12月
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));//设置当月的最后一天
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        //System.out.println(formatDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
		return cal.getTime();
	}
	
	/**
	 * 根据日期获取当天开始时间,精确到秒
	 */
	public static Date getStartTimeByDate(String date) {
		if(StringUtils.isBlank(date)){
			return null;
		}
		date += " 00:00:00";
		return stringToDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据日期获取当天结束时间,精确到秒
	 */
	public static Date getEndTimeByDate(String date) {
		if(StringUtils.isBlank(date)){
			return null;
		}
		date += " 23:59:59";
		return stringToDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 功能：获取yyyyMMddHHmmss格式字符串
	 */
	public final static String toYyyymmddHhmmss(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	public final static String toYyyymmddHhmmss(Date aDate) {
		if (aDate == null) {
			return "";
		}

		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		int nYear = cal.get(Calendar.YEAR);
		int nMonth = cal.get(Calendar.MONTH);
		nMonth++;
		int nDay = cal.get(Calendar.DAY_OF_MONTH);
		int nHour = cal.get(Calendar.HOUR_OF_DAY);
		int nMInute = cal.get(Calendar.MINUTE);
		int nSeconf = cal.get(Calendar.SECOND);

		StringBuilder sb = new StringBuilder();
		sb.append(nYear);
		sb.append('-');
		if (nMonth < 10) {
			sb.append('0');
		}
		sb.append(nMonth);
		sb.append('-');
		if (nDay < 10) {
			sb.append('0');
		}
		sb.append(nDay);

		sb.append(' ');

		if (nHour < 10) {
			sb.append('0');
		}
		sb.append(nHour);
		sb.append(':');
		if (nMInute < 10) {
			sb.append('0');
		}
		sb.append(nMInute);
		sb.append(':');
		if (nSeconf < 10) {
			sb.append('0');
		}
		sb.append(nSeconf);

		return sb.toString();
	}

	public final static String toYyyymmdd(Date aDate) {
		if (aDate == null) {
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(aDate);
	}
	
	/**
	 * 获取前一天
	 * 
	 * @param date 日期
	 * @return 前一天
	 */
	public static Date getBeforeDay(Date date, int n) {
		Calendar objCalendar = Calendar.getInstance();
		objCalendar.setTime(date);
		int iDay = objCalendar.get(Calendar.DATE);
		objCalendar.set(Calendar.DATE, iDay - n);
		return objCalendar.getTime();
	}

	/**
	 * 获取后一天
	 * 
	 * @param date 日期
	 * @return 后一天
	 */
	public static Date getAfterDay(Date date) {
		Calendar objCalendar = Calendar.getInstance();
		objCalendar.setTime(date);
		int iDay = objCalendar.get(Calendar.DATE);
		objCalendar.set(Calendar.DATE, iDay + 1);
		return objCalendar.getTime();
	}
	
	public static Date addHours(Date date, int hours) {
		if (date == null) {
			return null;
		}
		Calendar calenda = Calendar.getInstance();
		calenda.setTime(date);
		calenda.add(Calendar.HOUR_OF_DAY, hours);
		return calenda.getTime();
	}

	public static Date addMinutes(Date date, int minutes) {
		if (date == null) {
			return null;
		}
		Calendar calenda = Calendar.getInstance();
		calenda.setTime(date);
		calenda.add(Calendar.MINUTE, minutes);
		return calenda.getTime();
	}

	public static Date addSeconds(Date date, int seconds) {
		if (date == null) {
			return null;
		}
		Calendar calenda = Calendar.getInstance();
		calenda.setTime(date);
		calenda.add(Calendar.SECOND, seconds);
		return calenda.getTime();
	}

	public static Date addDays(Date date, int day) {
		if (date == null) {
			return null;
		}
		Calendar calenda = Calendar.getInstance();
		calenda.setTime(date);
		calenda.add(Calendar.DAY_OF_YEAR, day);
		return calenda.getTime();
	}

	public static Date addMonths(Date date, int month) {
		if (date == null) {
			return null;
		}
		Calendar calenda = Calendar.getInstance();
		calenda.setTime(date);
		calenda.add(Calendar.MONTH, month);
		return calenda.getTime();
	}
	
	/**
	 * 方法用途: 计算两个日期之间的天数<br>
	 * 实现步骤: <br>
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int daysBetween(Date startTime, Date endTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		startTime = sdf.parse(sdf.format(startTime));
		endTime = sdf.parse(sdf.format(endTime));
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endTime);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	public static synchronized Integer getHours(java.util.Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 根据日期获得星期
	 *   
	 * @param date  
	 * @param type 0:返回{"0","1","2","3","4","5","6"},1：返回星期{"星期日","星期一","星期二","星期三","星期四","星期五","星期六"}
	 * @return
	 */
	public static String getWeekOfDate(Date date,int type) { 
	  String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" }; 
	  String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" }; 
	  Calendar calendar = Calendar.getInstance(); 
	  calendar.setTime(date); 
	  int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
	  if(type == 0){
		  return weekDaysCode[intWeek]; 
	  }else{
		  return weekDaysName[intWeek]; 
	  }
	} 
	
	/**
	 * 判断两个日期是否是同一周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(String date1, String date2) 
	{
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  Date d1 = null;
	  Date d2 = null;
	  try 
	  {
	   d1 = format.parse(date1);
	   d2 = format.parse(date2);
	  }
	  catch (Exception e) 
	  {
	   e.printStackTrace();
	  }
	  Calendar cal1 = Calendar.getInstance();
	  Calendar cal2 = Calendar.getInstance();
	  cal1.setFirstDayOfWeek(Calendar.MONDAY);//西方周日为一周的第一天，咱得将周一设为一周第一天
	  cal2.setFirstDayOfWeek(Calendar.MONDAY);
	  cal1.setTime(d1);
	  cal2.setTime(d2);
	  int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
	  if (subYear == 0)// subYear==0,说明是同一年
	  {
	   if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
	    return true;
	  }
	  else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) //subYear==1,说明cal比cal2大一年;java的一月用"0"标识，那么12月用"11"
	  {
	   if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
	    return true;
	  }
	  else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11)//subYear==-1,说明cal比cal2小一年
	  {
	   if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
	    return true;
	  }
	  return false;
	}
	
	public static void main(String[] args) {
		DateUtil.getCurrentDateStartTimeByDay(30);
		DateUtil.getCurrentDateBeforeStartTime();
		DateUtil.getCurrentDateBeforeEndTime();
		DateUtil.getCurrentDateStartTime();
		DateUtil.getCurrentDateEndTime();
		DateUtil.getCurrentMonthStartTime();
		DateUtil.getCurrentMonthEndTime();
		DateUtil.getCurrentYearStartTime();
		DateUtil.getCurrentYearEndTime();

		String str1 = "2016-06-16 11:30:30";
		String str2 = "2016-06-16 11:40:30";
		Date date1 = DateUtil.stringToDate(str1, "yyyy-MM-dd HH:mm:ss");
		Date date2 = DateUtil.stringToDate(str2, "yyyy-MM-dd HH:mm:ss");
		if(date1.getTime() < date2.getTime()){
			System.out.println("date2时间更大");
		}
		
	}
	
}
