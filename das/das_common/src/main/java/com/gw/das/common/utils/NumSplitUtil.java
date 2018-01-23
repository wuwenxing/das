package com.gw.das.common.utils;

import java.util.HashMap;
import java.util.Map;

public class NumSplitUtil {

	private static final int[] BASE_NUMBER = { 200, 100, 50, 20 };// 充值卡面值,流量单位M
	private static int[] COUNT_NUMBER = {};// 充值卡面值个数
	private static int[] RESULT = { 0, 0, 0, 0 };// 拆分充值卡面值个数

	/**
	 * 描述：splitPerpainCard 拆分
	 * 
	 * @param num
	 * @return
	 * @CreateOn 2010-10-20 下午01:55:15
	 * @author chun_chang
	 */
	public static int[] splitPerpainCard(Map<String, Integer> map, int num) {
		RESULT = new int[] { 0, 0, 0, 0 };
		COUNT_NUMBER = new int[] { map.get("100"), map.get("50"), map.get("30"), map.get("20") };
		int totleMoney = BASE_NUMBER[0] * COUNT_NUMBER[0] + BASE_NUMBER[1] * COUNT_NUMBER[1]
				+ BASE_NUMBER[2] * COUNT_NUMBER[2] + BASE_NUMBER[3] * COUNT_NUMBER[3];
		if (num % 10 != 0) {
			return null;
		}
		if (totleMoney > num && num >= 20) {
			splitMoney(num, num, 0);
		} else if (totleMoney == num) {
			RESULT[0] = COUNT_NUMBER[0];
			RESULT[1] = COUNT_NUMBER[1];
			RESULT[2] = COUNT_NUMBER[2];
			RESULT[3] = COUNT_NUMBER[3];
		} else {
			return null;
		}
		return RESULT;
	}

	/**
	 * 拆分
	 * 
	 * @param src
	 *            总目标数额
	 * @param nowSrc
	 *            当前目标数额
	 * @param level
	 *            当前搜索深度
	 * @return
	 */
	public static boolean splitMoney(int src, int nowSrc, int level) {
		// 计算当前充值卡使用总额
		int sum = 0;
		for (int i = 0; i < BASE_NUMBER.length; i++) {
			sum += BASE_NUMBER[i] * RESULT[i];
		}
		// 如果当前充值卡使用总额等于目标总额,是拆分完成
		if (src == sum) {
			return true;
		}
		// 如果当前搜索深度大于最大深度,退出
		if (level >= BASE_NUMBER.length) {
			return false;
		}

		// 计算本层使用充值卡最大数量
		RESULT[level] = nowSrc / BASE_NUMBER[level];
		if (RESULT[level] > COUNT_NUMBER[level]) {
			// 使用数量大于库存,则最大使用量等于库存
			RESULT[level] = COUNT_NUMBER[level];
		}
		int k = RESULT[level];
		for (; k >= 0; k--) {
			RESULT[level] = k;
			// 本次搜索后余额
			int nosplit = nowSrc - BASE_NUMBER[level] * RESULT[level];
			if (nosplit >= BASE_NUMBER[BASE_NUMBER.length - 1]) {
				// 本次搜索后余额大于最低面值,进入下一层搜索
				if (splitMoney(src, nosplit, level + 1)) {
					return true;
				}
			} else if (nosplit == 0) {
				// // 本次搜索后无余额,停止搜索
				return true;
			} else {
				// 本次搜索后余额小于最低面值,回溯
			}
		}

		return false;
	}

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("100", 2);
		map.put("50", 5);
		map.put("30", 5);
		map.put("20", 5);

		for (int j = 1; j < 100; j++) {
			int[] rssult = splitPerpainCard(map, j * 10);
			if (null == rssult) {
				System.out.println("无法拆分！");
			} else {
				System.out.println(j * 10 + ":" + rssult[0] + "_" + rssult[1] + "_" + rssult[2] + "_" + rssult[3]);
			}
		}
	}
}
