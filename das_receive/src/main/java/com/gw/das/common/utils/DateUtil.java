package com.gw.das.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
	// final static Logger logger = Logger.getLogger(DateUtil.class);

	public static synchronized java.util.Date getNextMonth(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, 1);
		return gc.getTime();
	}

	public static synchronized String getRboDateFormat(java.util.Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		{
			String str = "";
			if (date != null) {
				str = sdf.format(date);
				str = str.replace(" ", "T");
				str = str + ".000-04:00";
			}
			return str;
		}
	}

	public static synchronized String getDateDayFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd";
		return getDateFormat(date, pattern);
	}

	public static Date getRboDateFromStr(String str) {
		Date result = null;
		if (StringUtil.isNullOrEmpty(str)) {
			return null;
		}
		try {
			str = str.substring(0, str.indexOf("."));
			str = str.replace("T", " ");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = format.parse(str);
		} catch (ParseException e) {
			// logger.error("DateUtil.getDateFromStr parse error:", e);
		} catch (Exception e) {
			// logger.error("DateUtil.getDateFromStr parse error:", e);
		}
		return result;
	}
	
	public static Date getDateFromStr(String str, String m) {
		Date result = null;
		if (StringUtil.isNullOrEmpty(str)) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(m);
			result = format.parse(str);
		} catch (Exception e) {
		}
		return result;
	}

	public static Date getDateFromStr(String str) {
		Date result = null;
		if (StringUtil.isNullOrEmpty(str)) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = format.parse(str);
		} catch (Exception e) {
		}
		return result;
	}

	public static Date getDateFromStrByyyyMMdd(String str) {
		Date result = null;
		if (StringUtil.isNullOrEmpty(str)) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			result = format.parse(str);
		} catch (ParseException e) {
			// logger.error("DateUtil.getDateFromStr parse error:", e);
		} catch (Exception e) {
			// logger.error("DateUtil.getDateFromStr parse error:", e);
		}
		return result;
	}

	/**
	 * Retrieves the current timestamp.
	 * 
	 * @return Timestamp - the current timestamp
	 */
	public static Timestamp getCurrentTimestamp() {

		Calendar currDate = Calendar.getInstance();

		return new Timestamp(currDate.getTimeInMillis());
	}

	/**
	 * Method that retrieves first day of month
	 * 
	 * @param month
	 *            - 0 based month
	 * @param year
	 *            - year
	 * @return a java.util.Date of this format 'yyyy-MM-dd'
	 * @throws ParseException
	 */
	public static Date getFirstDayOfMonth(Integer month, Integer year) {

		Calendar calendar = Calendar.getInstance();

		month = month == null || month < 0 || month > 11 ? calendar.get(Calendar.MONTH) : month;
		year = year == null || year < 0 ? calendar.get(Calendar.YEAR) : year;

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Timestamp now() {
		Calendar currDate = Calendar.getInstance();
		return new Timestamp(currDate.getTime().getTime());
	}

	public static String getNowString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	public static String getTodayStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	public static String getTodayString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(new Date());
	}

	@SuppressWarnings("deprecation")
	public static Date getRetreiveStartDate() {
		Date date = new Date(109, 8, 25, 0, 0, 0);
		return date;
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

	public static Date getOneWeekAgo() {
		Calendar calenda = Calendar.getInstance();
		calenda.add(Calendar.DAY_OF_YEAR, -7);
		return calenda.getTime();
	}

	public static synchronized java.util.Date getNextnMonth(java.util.Date date, int n) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, n);
		return gc.getTime();
	}

	public static Date getOneMonthLater() {
		Calendar calenda = Calendar.getInstance();
		calenda.add(Calendar.MONTH, 1);
		return calenda.getTime();
	}

	/**
	 * 
	 * Handles difference in Time zone between products and OAS.
	 * 
	 */
	// public static Date convertTime(Date date, String product) {
	// if(Constants.PRODUCT_DYJ.equals(product) || Constants.PRODUCT_DYGW.equals(product)) {
	// return DateUtil.addHours(date, 12);
	// } else if (Constants.PRODUCT_YZ.equals(product) || Constants.PRODUCT_ZZ.equals(product)) {
	// return DateUtil.addHours(date, 8);
	// } else {
	// return date;
	// }
	// }

	// public static void main(String[] args) {
	// PasswordEncoder a = new PasswordEncoder();
	// System.out.println(a.encode("123123"));
	// String str = "2011-02-22T00:00:00.000+08:00";
	// Date now = new java.util.Date();
	// System.out.println("reuslt:" + getRboDateFormat(now));
	// System.out.println("reuslt:" + getRboDateFromStr(str));
	//
	// System.out.println("reuslt:" + toYyyymmddHhmmss(new Date()));
	//
	// System.out.println(get15sDateFormat(new Date()));
	//
	// Integer in = 1;
	// System.out.println(in.compareTo(1) == 0);
	// }

	public static Date getFirstDayOfThisMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getFirstDayOfNextMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(GregorianCalendar.MONTH, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getDateLast(int i) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.DATE, -i);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getLastTradeDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.add(Calendar.DATE, -1);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, -2);
		}
		if (dayOfWeek == Calendar.SATURDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		return DateUtils.truncate(calendar.getTime(), Calendar.DATE);
	}

	public static Date getLastTradeDay() {
		return getLastTradeDay(null);
	}

	public static Date getLast24Hour(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static Date getYesterday() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getTomorrow() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date truncate(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getLastSecondOfDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 23, 59, 59);
		return calendar.getTime();
	}

	public static Date getLastMilliSecondOfDate(Date date) {
		return DateUtils.addMilliseconds(DateUtils.addDays(DateUtils.truncate(date, Calendar.DATE), 1), -1);
	}

	public static Date getLastSecondOfToday() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 23, 59, 59);
		return calendar.getTime();
	}

	public static Date getFirstSecondOfToday() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getFirstSecondOfDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getBeginSummerDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 21, 47, 0);
		return calendar.getTime();
	}

	public static Date getEndSummerDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 21, 46, 59);
		return calendar.getTime();
	}

	public static Date getBeginWinterDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 22, 47, 0);
		return calendar.getTime();
	}

	public static Date getEndWinterDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 22, 46, 59);
		return calendar.getTime();
	}

	public static Date getMidDayOfThisMonthExptSunday() {
		Date today = new Date();
		String testTime = "2009-12-14 15:20:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date result = null;
		try {
			result = sdf.parse(testTime);
		} catch (ParseException e) {
			// logger.error("", e);
		}

		if (today.before(result)) {
			System.out.println("today before midday, begin with 1st of month.");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH), 15, 0, 0, 0);
		int days = calendar.get(Calendar.DAY_OF_WEEK);
		if (days == 1) {
			calendar.add(Calendar.DATE, 1);
		}
		Date midMonth = calendar.getTime();
		Date clearingBeginDate = getFirstDayOfThisMonth();
		if (today.after(midMonth)) {
			clearingBeginDate = midMonth;
		}

		System.out.println("real result: " + clearingBeginDate);
		return result;
	}

	public static Date getMaxDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(2999, 1, 1, 0, 0, 0);
		return calendar.getTime();
	}

	public final static Date getYyyyMmDd(String str) {
		if (StringUtil.isNullOrEmpty(str)) {
			return null;
		}
		try {
			// String[] strs = str.split(" ");
			String[] strDays = str.split("-");
			int nYear = Integer.parseInt(strDays[0]);
			int nMonth = Integer.parseInt(strDays[1]);
			int nDay = Integer.parseInt(strDays[2]);

			Calendar cal = new GregorianCalendar(nYear, nMonth - 1, nDay, 0, 0, 0);
			return cal.getTime();
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		} catch (NumberFormatException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public final static Date getYyyyMmDdHhMmss(String str) {
		if (StringUtil.isNullOrEmpty(str)) {
			return null;
		}
		try {
			String[] strs = str.split(" ");
			String[] strDays = strs[0].split("-");
			int nYear = Integer.parseInt(strDays[0]);
			int nMonth = Integer.parseInt(strDays[1]);
			int nDay = Integer.parseInt(strDays[2]);

			// String[] strTimes = strs[1].split(":");
			// int nHour = Integer.parseInt(strTimes[0]);
			// int nMinute = Integer.parseInt(strTimes[1]);
			// int nSecond = Integer.parseInt(strTimes[2]);
			Calendar cal = new GregorianCalendar(nYear, nMonth - 1, nDay);
			return cal.getTime();
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		} catch (NumberFormatException e) {
			return null;
		} catch (Exception e) {
			return null;

		}
	}

	/**
	 * 获取指定年月的最大天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getMaxDay(int year, int month) {
		int day = 1;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		int maxDay = cal.getActualMaximum(Calendar.DATE);
		return maxDay;
	}

	public static synchronized String getDateW3CFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	public static synchronized String getDateYYYYMMDD(java.util.Date date) {
		String pattern = "yyyyMMdd";
		return getDateFormat(date, pattern);
	}

	public static synchronized String getDateYYYY() {
		String pattern = "yyyy";
		return getDateFormat(now(), pattern);
	}

	public static synchronized String getDateFormat(java.util.Calendar cal, String pattern) {
		return getDateFormat(cal.getTime(), pattern);
	}

	public static synchronized String getDateFormat(java.util.Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		{
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	public final static String toYyMmdd(Date aDate) {
		if (aDate == null) {
			return "";
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		StringBuilder sb = new StringBuilder();
		int nYear = cal.get(Calendar.YEAR);
		nYear = nYear % 100;
		int nMonth = cal.get(Calendar.MONTH);
		nMonth++;
		int nDay = cal.get(Calendar.DAY_OF_MONTH);
		if (nYear < 10) {
			sb.append('0');
		}
		sb.append(nYear);
		if (nMonth < 10) {
			sb.append('0');
		}
		sb.append(nMonth);
		if (nDay < 10) {
			sb.append('0');
		}
		sb.append(nDay);
		return sb.toString();
	}

	public final static String toYyyyMmdd(Date aDate) {
		if (aDate == null) {
			return "";
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		StringBuilder sb = new StringBuilder();
		int nYear = cal.get(Calendar.YEAR);
		int nMonth = cal.get(Calendar.MONTH);
		nMonth++;
		int nDay = cal.get(Calendar.DAY_OF_MONTH);

		sb.append(nYear);
		if (nMonth < 10) {
			sb.append('0');
		}
		sb.append(nMonth);
		if (nDay < 10) {
			sb.append('0');
		}
		sb.append(nDay);
		return sb.toString();
	}

	public final static String toHyphenatedYyyyMmdd(Date aDate) {
		if (aDate == null) {
			return "";
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		int nYear = cal.get(Calendar.YEAR);
		int nMonth = cal.get(Calendar.MONTH);
		nMonth++;
		int nDay = cal.get(Calendar.DAY_OF_MONTH);

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
		return sb.toString();
	}

	public final static String toHyphenatedMmdd(Date aDate) {
		if (aDate == null) {
			return "";
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		int nMonth = cal.get(Calendar.MONTH);
		nMonth++;
		int nDay = cal.get(Calendar.DAY_OF_MONTH);

		StringBuilder sb = new StringBuilder();
		if (nMonth < 10) {
			sb.append('0');
		}
		sb.append(nMonth);
		sb.append('-');
		if (nDay < 10) {
			sb.append('0');
		}
		sb.append(nDay);
		return sb.toString();
	}

	public final static String toYyyyMmddHHmm(Date aDate) {
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
		int nMinute = cal.get(Calendar.MINUTE);

		StringBuilder sb = new StringBuilder();
		sb.append(nYear);
		sb.append("-");

		if (nMonth < 10) {
			sb.append('0');
		}
		sb.append(nMonth);
		sb.append("-");

		if (nDay < 10) {
			sb.append('0');
		}
		sb.append(nDay);
		sb.append(" ");

		if (nHour < 10) {
			sb.append('0');
		}
		sb.append(nHour);
		sb.append(":");

		if (nMinute < 10) {
			sb.append('0');
		}
		sb.append(nMinute);
		return sb.toString();
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

	public static String getIntervalTimeTillNow(Date fromDate) {
		long interval = System.currentTimeMillis() - fromDate.getTime();
		long second = interval / 1000;
		long minute = 0;
		long hour = 0;
		String timeStr = second + "s";
		if (second >= 60) {
			minute = second / 60;
			second = second % 60;
			timeStr = minute + "m " + second + "s";
		}
		if (minute >= 60) {
			hour = minute / 60;
			minute = minute % 60;
			timeStr = hour + "h " + minute + "m " + second + "s";
		}
		return timeStr;
	}

	public static String getIntervalTimeTillNow(long intervalFrom) {
		long interval = System.currentTimeMillis() - intervalFrom;
		long second = interval / 1000;
		long minute = 0;
		long hour = 0;
		String timeStr = second + "s";
		if (second >= 60) {
			minute = second / 60;
			second = second % 60;
			timeStr = minute + "m " + second + "s";
		}
		if (minute >= 60) {
			hour = minute / 60;
			minute = minute % 60;
			timeStr = hour + "h " + minute + "m " + second + "s";
		}
		return timeStr;
	}

	public static int getDateField(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}

	public static boolean isTodayPassed16() {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.DATE) >= 16) {
			return true;
		}
		return false;
	}

	public static boolean isWeekEnd(Calendar nowCalendar) {
		if (nowCalendar == null) {
			return true;
		}

		int dayOfWeek = nowCalendar.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY;
	}

	public static Date getOneMonthAgo() {
		Calendar calenda = Calendar.getInstance();
		calenda.add(Calendar.MONTH, -1);
		return calenda.getTime();
	}

	public static Integer calDateDiffWithNow(Date date) {
		Date now = new Date();

		long milli_d1 = date.getTime();
		long milli_d2 = now.getTime();
		long diff = milli_d1 - milli_d2;
		long one_day = 1000 * 60 * 60 * 24;
		double days = diff / one_day;
		Double d = Math.ceil(days);
		Integer numDays = d.intValue();
		System.out.println("numdays: " + numDays);
		return numDays;
	}

	public static Boolean equalDate(Date date1, Date date2) {
		int year1 = getDateField(date1, Calendar.YEAR);
		int month1 = getDateField(date1, Calendar.MONTH);
		int day1 = getDateField(date1, Calendar.DAY_OF_MONTH);

		int year2 = getDateField(date2, Calendar.YEAR);
		int month2 = getDateField(date2, Calendar.MONTH);
		int day2 = getDateField(date2, Calendar.DAY_OF_MONTH);

		if (year1 == year2 && month1 == month2 && day1 == day2) {
			return true;
		}

		return false;
	}

	public static int calculateAgeFromBirthday(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				throw new IllegalArgumentException("Can't be born in the future");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}
		return age;
	}

	public static synchronized String getBirthDateFormat(java.util.Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		{
			String str = "";
			if (date != null) {
				str = sdf.format(date);
			}
			return str;
		}
	}

	public static synchronized String get15sDateFormat(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return date != null ? sdf.format(date) : "";
	}

	public static synchronized Integer getHours(java.util.Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);

		// SimpleDateFormat sdf = new SimpleDateFormat("HH");
		// String hh = sdf.format(date);
		// if(hh.length() != 2){
		// hh = "0" + hh;
		// }
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static synchronized List<String> getMonthBetween(java.util.Calendar start, java.util.Calendar end,
			boolean bOnlyConsiderYearMonth) {
		List<String> lt = new ArrayList<String>();

		try {

			if (start == null || end == null) {
				return new ArrayList<String>();
			}

			while (start.before(end)) {
				String year = String.valueOf(start.get(Calendar.YEAR));
				String month = String.valueOf(start.get(Calendar.MONTH) + 1);
				month = month.length() > 1 ? month : "0" + month;

				String monthBetween = year + month;
				if (!lt.contains(monthBetween)) {
					lt.add(monthBetween);
				}

				start.add(Calendar.MONTH, 1);
			}

			if (bOnlyConsiderYearMonth && start.get(Calendar.YEAR) == end.get(Calendar.YEAR)
					&& start.get(Calendar.MONTH) == end.get(Calendar.MONTH)) {
				String year = String.valueOf(end.get(Calendar.YEAR));
				String month = String.valueOf(end.get(Calendar.MONTH) + 1);
				month = month.length() > 1 ? month : "0" + month;

				String monthBetween = year + month;
				if (!lt.contains(monthBetween)) {
					lt.add(monthBetween);
				}
			}

		} catch (Exception e) {
			return new ArrayList<String>();
		}
		return lt;
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.roll(Calendar.DAY_OF_MONTH, -1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 23, 59, 59);
		return calendar.getTime();
	}

	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getLastWeekMonday(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), hour, minute, second);
		return calendar.getTime();
	}

	public static Date getLastWeekSunday(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.add(Calendar.DATE, -7);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), hour, minute, second);
		return calendar.getTime();
	}

	public static Date getCurrentWeekMonday(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), hour, minute, second);
		return calendar.getTime();
	}

	public static Date getCurrentWeekSunday(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), hour, minute, second);
		return calendar.getTime();
	}

	public static Date getLastMonthFirstDay(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), hour, minute, second);
		return calendar.getTime();
	}

	public static Date getLastMonthEndDay(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), hour, minute, second);
		return calendar.getTime();
	}

	public static Date getCurrentMonthFirstDay(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), hour, minute, second);
		return calendar.getTime();
	}

	public static Date getCurrentMonthEndDay(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), hour, minute, second);
		return calendar.getTime();
	}

	public static Date getBeginDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		return calendar.getTime();
	}

	public static Date getAgentBeginSummerDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 22, 0, 0);
		return calendar.getTime();
	}

	public static Date getAgentEndSummerDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 22, 0, 0);
		return calendar.getTime();
	}

	public static Date getAgentBeginWinterDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 23, 0, 0);
		return calendar.getTime();
	}

	public static Date getAgentEndWinterDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH),
				calendar.get(GregorianCalendar.DATE), 23, 0, 0);
		return calendar.getTime();
	}

	// 时长
	public static String timeLength(Date start, Date end) {
		long min = 0;
		long sec = 0;
		String min_str = "";
		String sec_str = "";
		long time1 = start.getTime();
		long time2 = end.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
			min = (diff / (60 * 1000));
			sec = (diff / 1000 - min * 60);

			min_str = String.valueOf(min);
			sec_str = String.valueOf(sec);

			min_str = min_str.length() > 1 ? min_str : "0" + min_str;
			sec_str = sec_str.length() > 1 ? sec_str : "0" + sec_str;

			return min_str + "\"" + sec_str;
		} else {
			return null;
		}

	}

	// 累加时长
	public static String timeLengthAppend(Date start, Date end, String firstTimeLength) {
		long min = 0;
		long sec = 0;
		String min_str = "";
		String sec_str = "";
		String[] str2 = firstTimeLength.split("\"");
		long s2_1 = Integer.parseInt(str2[0]) * (60 * 1000); // 分 long
		long s2_2 = Integer.parseInt(str2[1]) * 1000; // 秒 long
		long nextLongTime = s2_1 + s2_2;

		long time1 = start.getTime();
		long time2 = end.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
			diff = diff + nextLongTime;

			min = (diff / (60 * 1000));
			sec = (diff / 1000 - min * 60);

			min_str = String.valueOf(min);
			sec_str = String.valueOf(sec);

			min_str = min_str.length() > 1 ? min_str : "0" + min_str;
			sec_str = sec_str.length() > 1 ? sec_str : "0" + sec_str;

			return min_str + "\"" + sec_str;
		} else {
			return null;
		}

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

	/**
	 * 日期格式化
	 * 
	 * @param date 日期
	 * @return 
	 */
	public static String getYyyyMmDd(java.util.Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		{
			String str = "";
			if (date != null) {
				str = sdf.format(date);
			}
			return str;
		}
	}

	/**
	* 日期格式化
	* 
	* @param date 日期
	* @return 
	*/
	public static String getYyyyMmDdZero(java.util.Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		{
			String str = "";
			if (date != null) {
				str = sdf.format(date) + " 00:00:00";
			}
			return str;
		}
	}

	/**
	* 日期时间格式化
	* 
	* @param date 日期
	* @return 
	*/
	public static String getYyyyMmDdHhMm(java.util.Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		{
			String str = "";
			if (date != null) {
				str = sdf.format(date);
			}
			return str;
		}
	}

	/**
	* 日期格式化
	* 
	* @param date 日期
	* @return 
	*/
	public static String getYyyyMmDdOther(java.util.Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		{
			String str = "";
			if (date != null) {
				str = sdf.format(date);
			}
			return str;
		}
	}

	public static Long dateDiff(Date startTime, Date endTime) {
		// 按照传入的格式生成一个simpledateformate对象
		// SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数

		// 获得两个时间的毫秒时间差异
		long diff = endTime.getTime() - startTime.getTime();

		long min = diff % nd % nh / nm;// 计算差多少分钟

		// 输出结果
		System.out.println("时间相差：" + min + "分钟");
		return min;

	}

	public static String getDatePoor(Date endDate, Date nowDate) {

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	/**
	 * 
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
	
	/**
	 * 获取时间，格式为yyyy-MM-dd HH:00:00
	 * @param date
	 * @return
	 */
	public static String getDateYyyyMMddHH(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		String dateStr = sdf.format(date) + ":00:00";

		return dateStr;
	}
	
	public static Date stringToDateSecond(String date) {
		return stringToDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date stringToDate(String date, String format) {
		if(date==null){
			return null;
		}
		DateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat(format);
		Date day = null;
		try {
			day = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	
	public static String formatDate(Date date) {
		return formatDateToString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatDateToString(Date date) {
		return formatDateToString(date, "yyyy-MM-dd");
	}
	
	public static String formatDateToYYYYMMString(Date date) {
		return formatDateToString(date, "yyyy-MM");
	}
	
	public static String formatDateToYYYYString(Date date) {
		return formatDateToString(date, "yyyy");
	}
	
	public static String formatDateToString(Date date, String format) {
		if(date==null){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static String GMT0ToGMT8(String dateStr){
		if(StringUtils.isNotBlank(dateStr)){
			Date date = DateUtil.stringToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
		    Date d = DateUtil.addHours(date, 8);
		    return DateUtil.formatDateToString(d,"yyyy-MM-dd HH:mm:ss");
		}else{
			return "";
		}    
	}


}
