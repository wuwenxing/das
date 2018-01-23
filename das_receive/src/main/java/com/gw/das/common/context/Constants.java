package com.gw.das.common.context;

import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * 外汇类型
	 */
	public static final Integer FX_TYPE = 1;

	/**
	 * 贵金属类型
	 */
	public static final Integer PM_TYPE = 2;

	/**
	 * 恒信类型
	 */
	public static final Integer HX_TYPE = 3;
	
	// 因网站不好修改请求地址，所以加入连接请求地址关系映射表，到新netty的接口地址
	public static Map<String, String> linkUrls = new HashMap<String, String>();

	static {
		linkUrls.put("put/insertOA", "WebSiteManager/setData");
		linkUrls.put("put/insert", "WebSiteManager/setDataCallback");
		linkUrls.put("put/insertRoom", "WebSiteManager/setChartRoomData");
		linkUrls.put("put/insertRoomClose", "WebSiteManager/setChartRoomData");
	}

}
