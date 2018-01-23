package com.gw.das.common.utils;

import org.apache.commons.lang3.StringUtils;

public class SqlUtil {

	/**
	 * 获取排序SQL
	 */
	public static String getOrderField(String sortStr, String orderStr) throws Exception {
		String orderSql = "";
		if (StringUtils.isNotBlank(sortStr)) {
			orderSql += " order by ";
			if (StringUtils.isNotBlank(orderStr)) {
				String[] sortAry = sortStr.split(",");
				String[] orderAry = orderStr.split(",");
				for (int i = 0; i < sortAry.length; i++) {
					String sort = sortAry[i];
					String order = "";
					if (i < orderAry.length) {
						order = orderAry[i];
					} else {
						order = "ASC";
					}
					if ((i + 1) == sortAry.length) {
						orderSql += " " + sort + " " + order;
					} else {
						orderSql += " " + sort + " " + order + ",";
					}
				}
			} else {
				String[] sortAry = sortStr.split(",");
				for (int i = 0; i < sortAry.length; i++) {
					String sort = sortAry[i];
					if ((i + 1) == sortAry.length) {
						orderSql += " " + sort;
					} else {
						orderSql += " " + sort + ",";
					}
				}
			}
		}
		return orderSql;
	}
	
}
