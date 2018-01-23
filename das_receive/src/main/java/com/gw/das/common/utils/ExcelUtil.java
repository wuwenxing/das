package com.gw.das.common.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Excel工具类
 */
public final class ExcelUtil {

	/**
	 * 功能：包装excel导出response，使其支持excel输出
	 * 
	 * @param codedFileName
	 *            文件名
	 * @param request
	 *            request请求对象
	 * @param response
	 *            response请求对象
	 */
	public static void wrapExcelExportResponse(String codedFileName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.ms-excel");
		String browse = BrowserUtil.checkBrowse(request);
		if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) { // 根据浏览器进行转码，使其支持中文文件名
			response.setHeader("content-disposition", "attachment;filename="
					+ java.net.URLEncoder.encode(codedFileName, "UTF-8") + DateUtil.toYyyymmddHhmmss(new Date()) + ".xlsx");
		} else {
			String newtitle = new String(codedFileName.getBytes("UTF-8"), "ISO8859-1");
			response.setHeader("content-disposition",
					"attachment;filename=" + newtitle + DateUtil.toYyyymmddHhmmss(new Date()) + ".xlsx");
		}
	}

}
