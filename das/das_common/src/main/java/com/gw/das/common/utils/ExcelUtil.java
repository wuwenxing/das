package com.gw.das.common.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
					+ java.net.URLEncoder.encode(codedFileName, "UTF-8") + DateUtil.toYyyymmddHhmmss() + ".xlsx");
		} else {
			String newtitle = new String(codedFileName.getBytes("UTF-8"), "ISO8859-1");
			response.setHeader("content-disposition",
					"attachment;filename=" + newtitle + DateUtil.toYyyymmddHhmmss() + ".xlsx");
		}
	}

	/**
	 * 功能：读取 Excel 2007 文件内容(以InputStream方式)
	 * 
	 * @param inputstream
	 *            文件流
	 * @param col
	 *            指定读取的列数
	 * @throws Exception
	 */
	public static List<List<Object>> read2007ExcelByInputStream(InputStream inputstream, int col) throws Exception {

		List<List<Object>> list = new ArrayList<List<Object>>(); // 结果集
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputstream);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0); // 遍历该表格中所有的工作表，i表示工作表的数量
															// getNumberOfSheets表示工作表的总数
		for (int j = 0; j < xssfSheet.getPhysicalNumberOfRows(); j++) { // 遍历该行所有的行,j表示行数
																		// getPhysicalNumberOfRows行的总数
			XSSFRow xssfRow = xssfSheet.getRow(j);
			if (xssfRow != null) {
				List<Object> arrayString = new ArrayList<Object>(); // 单行数据
				for (int i = 0; i < col; i++) {
					XSSFCell cell = xssfRow.getCell(i);
					if (cell == null) {
						arrayString.add("");
					}else{
						int cellType = cell.getCellType();
						String temp = "";
						switch (cellType) {
						case XSSFCell.CELL_TYPE_STRING:
							temp = cell.getStringCellValue().trim();
							temp = StringUtils.isBlank(temp)? "" : temp;;
							arrayString.add(temp);
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							arrayString.add(String.valueOf(cell.getBooleanCellValue()));
							break;
						case XSSFCell.CELL_TYPE_FORMULA:
							arrayString.add(String.valueOf(cell.getCellFormula().trim()));
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								arrayString.add(DateUtil.formatDate(cell.getDateCellValue()));
							} else {
								arrayString.add(String.valueOf(cell.getNumericCellValue()));
							}
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							arrayString.add("");
							break;
						case XSSFCell.CELL_TYPE_ERROR:
							arrayString.add("");
							break;
						default:
							arrayString.add(cell.toString().trim());
							break;
						}
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}

}
