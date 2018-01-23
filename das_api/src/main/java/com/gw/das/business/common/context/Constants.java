package com.gw.das.business.common.context;

/**
 * 公共常量类
 * 
 * @author kirin.guan
 *
 */
public class Constants {

	/**
	 * 自定义密匙
	 */
	public static final String PASSWORD = "PWD_DAS_API";

	public final static double THREE = 0.3;
	public final static double FOUR = 0.4;
	public final static double FIVE = 0.5;
	public final static double TEN = 1;

	// 只有模拟开户
	public final static double DEMO_FIRST = 0.3;
	public final static double DEMO_CENTER = 0.3;
	public final static double DEMO_LAST = 0.4;

	public final static double NO_CENTER_DEMO_FIRST = 0.5;
	public final static double NO_CENTER_DEMO_LAST = 0.5;

	// 只有真实开户
	public final static double REAL_FIRST = 0.3;
	public final static double REAL_CENTER = 0.3;
	public final static double REAL_LAST = 0.4;

	public final static double NO_CENTER_REAL_FIRST = 0.5;
	public final static double NO_CENTER_REAL_LAST = 0.5;

	// 既有模拟开户，又有真实开户
	public final static double DEMO_REAL_FIRST = 0.4;
	public final static double DEMO_REAL_CENTER = 0.3;
	public final static double DEMO_REAL_LAST = 0.3;

	public final static double NO_CENTER_DEMO_REAL_FIRST = 0.5;
	public final static double NO_CENTER_DEMO_REAL_LAST = 0.5;

	// 天数，30天之内
	public final static int DAY_NUMBER = 30;
	
	public final static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	
}
