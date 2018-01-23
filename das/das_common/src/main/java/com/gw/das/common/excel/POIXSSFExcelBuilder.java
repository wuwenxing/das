package com.gw.das.common.excel;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.utils.DateUtil;

/**
 * @author wayne 2016年3月17日
 * 模板大标题、小标题、数据列表、统计 都已固定样式
 * 
 * 支持$V{NO}是否生成序号，固定写法，并且生成在每行的第一列；
 * 支持$T{xxx}参数，xxx代表List<Bean>集合中Bean对象的类型；
 * 支持$F{xxx}参数，xxx代表List<Bean>集合中Bean对象的属性；
 * 支持$F{xxx.zzz}参数写法，其中xxx代表List<Bean>集合中Bean对象的子对象,zzz代码子对象的属性；
 * 支持$P{xxx}参数，xxx代表POIXSSFExcelBuilder对象属性pMap的Key值；
 * 
 * 注意：
 * 1、$V{NO}固定写法只能有一个参数；$T{xxx}、$F{xxx}与$P{xxx}可以有多个参数；
 * 2、$V{NO}与$F{xxx}必须在同一行，且只能有一行；$V{NO}如果有只能在第一列；
 * 3、$P{xxx}只能有一行；
 * 4、对于大数值类型的列，不显示科学计算法E+12等，需设置模板单元格格式：选择F类型参数单元格-右键-设置单元格格式-自定义-类型=0.00；并设置setDataFormatFlag为true
 * 5、避免耗系统资源，设置了最大导出30万条；
 */
public class POIXSSFExcelBuilder {

	private static final Logger logger = LoggerFactory.getLogger(POIXSSFExcelBuilder.class);
	
	/**
	 * 待导出数据列表转换后
	 */
	private List<HashMap<String, Object>> transBeanList = new ArrayList<HashMap<String, Object>>();

	/**
	 * P类型参数对象-外部传入
	 */
	private HashMap<String, Object> pMap = new HashMap<String, Object>();

	/**
	 * 序号数据, 默认1
	 */
	private int no = 1;
	
	/**
	 * 是否需要设置序号数据, 默认false
	 */
	private boolean noFlag = false;
	
	/**
	 * Excel中F类型集合-对应的下标
	 */
	private int fRowNum;
	
	/**
	 * Excel中P类型集合-对应的下标
	 */
	private int pRowNum;
	
	/**
	 * Excel中T类型集合
	 */
	private List<String> tKeyList = new ArrayList<String>();
	
	/**
	 * 创建SXSSFWorkbook.createRow时的下标
	 */
	private int createRowNum = 0;
	
	/**
	 * 定义excel工作簿-只写入
	 */
	private SXSSFWorkbook wb = null;

	/**
	 * 定义excel工作簿-为模板数据
	 */
	private XSSFWorkbook xswb = null;
    
	/**
	 * 默认日期格式
	 */
	private String _defaultDateFormat="yyyy-MM-dd",
			_defaultTimeFormat="HH:mm:ss",
			_defaultDateTimeFormat=_defaultDateFormat+" "+_defaultTimeFormat;
	
	/**
	 * 创建公共cellStyle，因为每个单元格都通过wb.createCellStyle()创建的话，导出性能会快速下降
	 */
	/**
	 * 大标题 cellstyle
	 */
	private CellStyle cellstyleT1 = null;

	/**
	 * 小标题 cellstyle
	 */
	private CellStyle cellstyleT2 = null;

	/**
	 * P类型 cellstyle
	 */
	private CellStyle cellstyleP = null;

	/**
	 * F类型  cellstyle
	 */
	private CellStyle cellstyleF = null;

	/**
	 * P类型 cellstyle,对应每列的cellStyle
	 */
	private Map<String, CellStyle> cellstylePMap = null;

	/**
	 * F类型 cellstyle,对应每列的cellStyle
	 */
	private Map<String, CellStyle> cellstyleFMap = null;
	
	/**
	 * 设置单元格格式-自定义-开关
	 * 默认不使用，需要时开启，因为写入大量数据时createCellStyle很占性能
	 */
	private boolean setDataFormatFlag = false;
	
	/**
	 * 构造方法1
	 */
	public POIXSSFExcelBuilder(File excelFile) throws IOException {
		this(new FileInputStream(excelFile));
	}
	
	/**
	 * 构造方法2
	 */
	public POIXSSFExcelBuilder(InputStream inputStream) throws IOException {
		String errorMsg = "模板出錯，請確認EXCEL文件能被正確打開";
		try {
			xswb = new XSSFWorkbook(inputStream);
			Sheet sheet = xswb.getSheetAt(0);
			
			int i=0,j=0,size=0,vRowNum=0;
			for (Row row : sheet) {
				int k = 0;
				for (Cell cell : row) {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().length() > 0){
						Pattern p=Pattern.compile("(.*)(\\$)([P,F,T,C,L,V,A,I])(\\{)(.+?)(\\})(.*)",Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
						Matcher matcher=p.matcher(cell.getStringCellValue());
						if(matcher.find()){
							if(matcher.group(3).equalsIgnoreCase("V")){
								// 设置生成序号列-则每行第一列数据为序号
								noFlag = true;
								// 设置下标
								vRowNum = i;
								// 设置所在列数
								j = k;
								// 设置大小
								size++;
							}else if(matcher.group(3).equalsIgnoreCase("F")){
								// 设置下标
								fRowNum = i;
							}else if(matcher.group(3).equalsIgnoreCase("P")){
								// 设置下标
								pRowNum = i;
							}
						}
					}
					k++;
				}
				i++;
			}
			
			// 校验模板
			if(noFlag && size > 1){
				errorMsg = "模板V类型参数设置错误，V类型参数只能有一个";
				throw new IOException(errorMsg);
			}
			if(noFlag && j != 0){
				errorMsg = "模板V类型参数设置错误，V类型参数只能在第一列";
				throw new IOException(errorMsg);
			}
			if(noFlag && vRowNum != fRowNum){
				errorMsg = "模板V类型参数设置错误，V类型参数只能与F类型参数在同一行";
				throw new IOException(errorMsg);
			}
		} catch (Exception e) {
			throw new IOException(errorMsg);
		}
	}

	/**
	 * 设置导出list
	 * @param list
	 * @param config
	 */
	public <T> void put(List<T> list, POIFormatConfig<T> config) throws Exception{
		setList(list, config);
	}
	
	/**
	 * 设置导出list
	 * @param list
	 * @param config
	 */
	public <T> void setList(List<T> list, POIFormatConfig<T> config) throws Exception{
		if(list.size() > 300000){
			throw new Exception("导出30万以上的数据直接抛出异常!");
		}
		
		for (T bean : list) {
			transBean(bean, config);
		}
	}
	
	/**
	 * 把bean对象转成List<HashMap<fieldName,fieldVal>>
	 * @param bean
	 * @param row
	 * @param config
	 */
	private <T> void transBean(T bean, POIFormatConfig<T> config) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		String fieldName = null;
		Object fieldVal = null;
		try{
			
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				fieldName = descriptor.getName();
				if ("class".equals(fieldName) || fieldName.isEmpty()) {
					continue;
				}
				fieldVal = descriptor.getReadMethod().invoke(bean);
				if (fieldVal == null) {
					fieldVal = "";
				}
				
				if (config != null) {// 根据自定义格式，格式对应值
					Object valueObj = config.fromatValue(fieldName, fieldVal, bean);
					if (valueObj == null) {
						map.put(fieldName, "");
					} else {
						map.put(fieldName, valueObj);
					}
				} else {
					map.put(fieldName, fieldVal);
				}
			}
			transBeanList.add(map);
		}catch(Exception e){
			logger.error("transBean error:[fieldName:"+fieldName+",fieldVal:"+fieldVal+"]" + e.getMessage());
		}
	}
	
	/**
	 * 获取子对象属性的值
	 */
	private <T> HashMap<String, Object> getSubBeanProperty(T bean) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		String fieldName = null;
		Object fieldVal = null;
		try{
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				fieldName = descriptor.getName();
				if ("class".equals(fieldName) || fieldName.isEmpty()) {
					continue;
				}
				fieldVal = descriptor.getReadMethod().invoke(bean);
				if (fieldVal == null) {
					fieldVal = "";
				}
				map.put(fieldName, fieldVal);
			}
		}catch(Exception e){
			logger.error("getSubBeanProperty error:[fieldName:"+fieldName+",fieldVal:"+fieldVal+"]" + e.getMessage());
		}
		return map;
	}
	
	/**
	 * 设置P类型参数Map
	 */
	public void put(String key, Object value) throws Exception{
		pMap.put(key, value);
	}
	
	/**
	 * 創建SXSSFWorkbook并寫入數據
	 * @param sheetIndex
	 */
	public void parse() throws Exception{
		createSXSSFWb();
	}
	
	/**
	 * 創建SXSSFWorkbook并寫入數據
	 * @param sheetIndex
	 */
	public void createSXSSFWb() throws Exception{
		Sheet xssheet = xswb.getSheetAt(0);
		// create SXSSFWorkbook
		if(transBeanList.size() > 50000){
			wb = new SXSSFWorkbook(1000);
		}else{
			// keep 100 rows in memory, exceeding rows will be flushed to disk
			wb = new SXSSFWorkbook(100);
		}
		// create sheet
		Sheet sheet = wb.createSheet(xssheet.getSheetName());// sheet名称
		// setColumnWidth
		setColumnWidth(xssheet, sheet);
		// createCellStyle
		createCellStyle(xssheet);
		// copyRow
		int i=0;
		for (Row xsrow : xssheet) {
			if(i < (fRowNum - 2)){
				// F类型以上大标题的row, 进行复制
				copyRowT1(sheet, xssheet, xsrow);
			}else if(i == (fRowNum - 2)){
				// F类型以上小标题的row, 进行复制
				copyRowT2(sheet, xssheet, xsrow);
			}else if(i == (fRowNum - 1)){
				// F类型以上T类型的row, 获取设置的对象类型
				setObjTypeList(sheet, xsrow);
			}else if(i == fRowNum){
				// F类型的row, 循环list数据创建多行Row
				copyRowF(sheet, xsrow);
			}else if(i > fRowNum && i < pRowNum){
				// F类型与P类型之间的row, 直接进行复制
				copyRowT(sheet, xssheet, xsrow, 0);
			}else if(i == pRowNum){
				// P类型的row, 根据pMap数据进行填充创建一行
				copyRowP(sheet, xssheet, xsrow);
			}
			i++;
		}
	}
	
	/**
	 * create cellStyle
	 * @param cellStyle
	 */
	private void createCellStyle(Sheet xssheet){
		// 设置大标题类型
		cellstyleT1 = wb.createCellStyle();
	    Font fontT1 = wb.createFont();
	    fontT1.setFontHeightInPoints((short) 15);//设置字体大小
	    fontT1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
	    cellstyleT1.setAlignment(XSSFCellStyle.ALIGN_CENTER);//居中
	    cellstyleT1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
	    cellstyleT1.setFont(fontT1);
	    cellstyleT1.setBorderBottom(CellStyle.BORDER_THIN); //下边框
	    cellstyleT1.setBorderLeft(CellStyle.BORDER_THIN);//左边框
	    cellstyleT1.setBorderTop(CellStyle.BORDER_THIN);//上边框
	    cellstyleT1.setBorderRight(CellStyle.BORDER_THIN);//右边框
	    
	    // 设置小标题类型
		cellstyleT2 = wb.createCellStyle();
        Font fontT2 = wb.createFont();
        fontT2.setFontHeightInPoints((short) 13);//设置字体大小
        fontT2.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
        cellstyleT2.setAlignment(XSSFCellStyle.ALIGN_LEFT);//左对齐
        cellstyleT2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
		cellstyleT2.setFont(fontT2);
		cellstyleT2.setBorderBottom(CellStyle.BORDER_THIN); //下边框
		cellstyleT2.setBorderLeft(CellStyle.BORDER_THIN);//左边框
		cellstyleT2.setBorderTop(CellStyle.BORDER_THIN);//上边框
		cellstyleT2.setBorderRight(CellStyle.BORDER_THIN);//右边框
	    
        if(!setDataFormatFlag){
    	    // 设置F类型
    		cellstyleF = wb.createCellStyle();
            Font fontF = wb.createFont();
            fontF.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//正常显示
            cellstyleF.setAlignment(XSSFCellStyle.ALIGN_LEFT);//左对齐
            cellstyleF.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
            cellstyleF.setFont(fontF);
            cellstyleF.setBorderBottom(CellStyle.BORDER_THIN); //下边框
            cellstyleF.setBorderLeft(CellStyle.BORDER_THIN);//左边框
            cellstyleF.setBorderTop(CellStyle.BORDER_THIN);//上边框
            cellstyleF.setBorderRight(CellStyle.BORDER_THIN);//右边框
    	    
    	    // 设置P类型
    		cellstyleP = wb.createCellStyle();
            Font fontP = wb.createFont();
            fontP.setFontHeightInPoints((short) 13);//设置字体大小
            fontP.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
            cellstyleP.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//右对齐
            cellstyleP.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
            cellstyleP.setFont(fontP);
        }else{
        	// 设置F类型
    		cellstyleFMap = new HashMap<String, CellStyle>();
    		Row row = xssheet.getRow(fRowNum);
    		if(null != row){
    			int i=0;
    			for(Cell cell : row){
    				CellStyle cellStyle = wb.createCellStyle();
    		        Font font = wb.createFont();
    		        font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//正常显示
    		        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);//左对齐
    		        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
    		        cellStyle.setFont(font);
    		        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
    		        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
    		        cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
    		        cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
    		        
    		        short fmt = cell.getCellStyle().getDataFormat();
    				if(fmt != 0){
    					cellStyle.setDataFormat(fmt);
    				}
    		        
    				cellstyleFMap.put(i+"", cellStyle);
    		        i++;
    			}
    		}

    		// 设置P类型
    		cellstylePMap = new HashMap<String, CellStyle>();
    		Row rowP = xssheet.getRow(pRowNum);
    		if(null != rowP){
    			int i=0;
    			for(Cell cell : rowP){
    				CellStyle cellStyle = wb.createCellStyle();
    		        Font font = wb.createFont();
    		        font.setFontHeightInPoints((short) 13);//设置字体大小
    		        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
    		        cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//右对齐
    		        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
    		        cellStyle.setFont(font);
    		        
    		        short fmt = cell.getCellStyle().getDataFormat();
    				if(fmt != 0){
    					cellStyle.setDataFormat(fmt);
    				}
    		        
    				cellstylePMap.put(i+"", cellStyle);
    		        i++;
    			}
    		}
        }
		
	}
	
	/**
	 * create cellStyle
	 * @param cellStyle
	 */
	private CellStyle getCellStyle(int type, String key){
		if(type == 1){
			return cellstyleT1;
		}else if(type == 2){
			return cellstyleT2;
		}else if(type == 3){
			if(!setDataFormatFlag){
				return cellstyleF;
			}else{
				return cellstyleFMap.get(key);
			}
		}else if(type == 4){
			if(!setDataFormatFlag){
				return cellstyleP;
			}else{
				return cellstylePMap.get(key);
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 设置每列的列宽
	 * @param xssheet
	 * @param sheet
	 */
	private void setColumnWidth(Sheet xssheet, Sheet sheet){
		Row row = xssheet.getRow(fRowNum);
		for(int i=0; i<row.getLastCellNum(); i++){
			sheet.setColumnWidth(i, xssheet.getColumnWidth(i));// 設置列寬
		}
	}

	/**
	 * createRow
	 * @param sheet
	 */
	private Row createRow(Sheet sheet){
		Row targetRow = sheet.createRow(createRowNum);
		createRowNum ++;
		return targetRow;
	}
	
	/**
	 * copyRow 大标题
	 */
	private void copyRowT1(Sheet sheet, Sheet xssheet, Row sourceRow){
		int type = 1;
		copyRowT(sheet, xssheet, sourceRow, type);
	}
	
	/**
	 * copyRow 小标题
	 */
	private void copyRowT2(Sheet sheet, Sheet xssheet, Row sourceRow){
		int type = 2;
		copyRowT(sheet, xssheet, sourceRow, type);
	}

	/**
	 * 获取设置的类型
	 */
	private List<String> setObjTypeList(Sheet sheet, Row sourceRow){
		for (Cell cell : sourceRow) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().length() > 0){
				Pattern p=Pattern.compile("(.*)(\\$)([P,F,C,T,L,V,A,I])(\\{)(.+?)(\\})(.*)",Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
				Matcher matcher=p.matcher(cell.getStringCellValue());
				if(matcher.find()){
					if(matcher.group(3).equalsIgnoreCase("T")){
						String tKey = matcher.group(5);
						tKeyList.add(tKey);//该列有设置类型，则添加对应类型
					}else{
						tKeyList.add("");//该列没有设置类型，则添加空值
					}
				}
			}
		}
		return tKeyList;
	}
	
	/**
	 * copyRow F类型
	 */
	private void copyRowF(Sheet sheet, Row sourceRow) throws Exception{
		int type = 3;
		// 记录fkey到List,如果cell不是f类型参数则为"";
		List<String> fKeyList = new ArrayList<String>();
		
		for (Cell cell : sourceRow) {
			boolean flag = false;
			if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().length() > 0){
				Pattern p=Pattern.compile("(.*)(\\$)([P,F,C,T,L,V,A,I])(\\{)(.+?)(\\})(.*)",Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
				Matcher matcher=p.matcher(cell.getStringCellValue());
				if(matcher.find()){
					if(matcher.group(3).equalsIgnoreCase("F")){
						String fKey = matcher.group(5);
						fKeyList.add(fKey);
						flag = true;
					}
				}
			}
			
			if(!flag){
				fKeyList.add("");
			}
		}
		
		// F类型的row, 循环list数据创建多行Row
		for(int k=0; k<transBeanList.size(); k++){
			Row targetRow = createRow(sheet);
			HashMap<String, Object> map = transBeanList.get(k);
			
			// 循环fKeyList生成row cell
			for (int i=0; i<fKeyList.size(); i++) {
				Cell cell = targetRow.createCell(i);
				Cell xscell = sourceRow.getCell(i);
				
				if(i==0 && noFlag){
					// 生成序号
					setCellValue(xscell, cell, no, type, i);
					no++;
				}else{
					String key = fKeyList.get(i);
					if(StringUtils.isNotBlank(key)){
						// 判断是否是二级属性
						Object obj = null;
						int index = key.indexOf('.');
						if (index >= 0) {
							String fix = key.substring(0, index);
							String pix = key.substring(index+1);
							Object objTemp = map.get(fix);
							// 循环objTemp的属性，匹配pix并获取对应的值
							HashMap<String, Object> subMap = getSubBeanProperty(objTemp);
							obj = subMap.get(pix);
						}else{
							obj = map.get(key);
						}
						// 根据模板设置的类型转换类型
						String objType = tKeyList.get(i);
						setCellValue(xscell, cell, converObjectType(obj, objType), type, i);
					}else{
						copyCell(xscell, cell, type, i);
					}
				}
				
			}
			
		}
	}
	
	/**
	 * copyRow P类型
	 * 根据pMap数据进行填充创建一行
	 */
	private void copyRowP(Sheet sheet, Sheet xssheet, Row sourceRow) throws Exception {
		int type = 4;
		// 替换此行的cellValue
		for (Cell cell : sourceRow) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().length() > 0) {
				String cellValue = new RegexReplace(cell.getStringCellValue(), "(.*)(\\$P\\{)(.+?)(\\})(.*)",
						Pattern.DOTALL | Pattern.CASE_INSENSITIVE) {
					@Override
					public String getReplacement(Matcher matcher) throws Exception {
						String key = matcher.group(3);
						if(StringUtils.isNotBlank(key)){
							Object obj = pMap.get(key);
							if(null != obj){
								return matcher.group(1) + obj + matcher.group(5);
							}
						}
						return matcher.group(1) + " " + matcher.group(5);
					}
				}.replaceAll();
				cell.setCellValue(cellValue);
			}
		}
		copyRowT(sheet, xssheet, sourceRow, type);
	}
	
	/**
	 * copyRow
	 */
	private void copyRowT(Sheet sheet, Sheet xssheet, Row sourceRow, int type){
		Row targetRow = createRow(sheet);
		
		Set<CellRangeAddress> mergedRegions = new HashSet<CellRangeAddress>();
		if (targetRow != null) {
			if (sourceRow.getHeight() >= 0) {
				targetRow.setHeight(sourceRow.getHeight());
			}
			int startCellNum = sourceRow.getFirstCellNum();
			int endCellNum = sourceRow.getLastCellNum();
			
			if(startCellNum<0 || endCellNum<0){
				return;
			}
			
			for (int j = startCellNum; j <= endCellNum; j++) {
				Cell oldCell = sourceRow.getCell(j);
				Cell newCell = targetRow.getCell(j);
				if (oldCell != null) {
					if (newCell == null) {
						newCell = targetRow.createCell(j);
					}
					CellRangeAddress mergedRegion = getMergedRegion(xssheet, sourceRow.getRowNum(),
							oldCell.getColumnIndex());
					if (mergedRegion != null) {
						CellRangeAddress newMergedRegion = new CellRangeAddress(targetRow.getRowNum(),
								targetRow.getRowNum() + mergedRegion.getLastRow() - mergedRegion.getFirstRow(),
								mergedRegion.getFirstColumn(), mergedRegion.getLastColumn());
						if (isNewMergedRegion(newMergedRegion, mergedRegions)) {
							mergedRegions.add(newMergedRegion);
							sheet.addMergedRegion(newMergedRegion);
						}
					}
					copyCell(oldCell, newCell, type, j);
				}
			}
		}
	}
	
	/**
	 * copyCell
	 * type: 类型
	 * cellColNum: cell所在列数
	 */
	private void copyCell(Cell sourceCell, Cell targetCell, int type, int cellColNum){
		CellStyle cellStyle = getCellStyle(type, cellColNum+"");
		if(null != cellStyle){
			targetCell.setCellStyle(cellStyle);
		}
		
		switch(sourceCell.getCellType()){
			case Cell.CELL_TYPE_STRING:
				targetCell.setCellValue(sourceCell.getRichStringCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				targetCell.setCellValue(sourceCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				targetCell.setCellType(Cell.CELL_TYPE_BLANK);
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				targetCell.setCellValue(sourceCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				int oldRowNum = sourceCell.getRowIndex()+1;
				int newRowNum = targetCell.getRowIndex()+1;
				String oldFormula = sourceCell.getCellFormula();
				if(oldFormula.indexOf("SUM")!=-1){
					oldFormula = oldFormula.replace(oldRowNum+"",newRowNum+"");
				}
				targetCell.setCellFormula(oldFormula);
				break;
			default:
				break;
		}
	}

	/**
	 * setCellValue
	 * type: 类型
	 * cellColNum: cell所在列数
	 */
	private void setCellValue(Cell sourceCell, Cell cell, Object value, int type, int cellColNum){
		if(type != 3){
			CellStyle cellStyle = getCellStyle(type, cellColNum+"");
			if(null != cellStyle){
				cell.setCellStyle(cellStyle);
			}
		}
		
		if(value==null)
			cell.setCellValue("");
		else if(value instanceof Integer){
			cell.setCellValue((Integer)value);
		}else if(value instanceof Long){
			cell.setCellValue((Long)value);
		}else if(value instanceof Boolean){
			cell.setCellValue((Boolean)value);
		}else if(value instanceof Double){ 
			cell.setCellValue((Double)value);
		}else if(value instanceof Date){
			cell.setCellValue(DateUtil.formatDateToString((Date)value, _defaultDateTimeFormat));
		}else if(value instanceof String){
			String strValue=((String)value).trim();
			if(strValue!="" && strValue.matches("=[IF|SUM|COUNT|COUNTIF]\\(*.*(\\)$)")) {
				 cell.setCellFormula(cell.getCellType()==Cell.CELL_TYPE_FORMULA?(cell.getCellFormula()):(strValue.substring(1,strValue.length())));
			}else {
			  cell.setCellValue((String)value);
			}
		}else if(value instanceof BigDecimal){
			cell.setCellValue(((BigDecimal)value).doubleValue());
		}else{
			throw new RuntimeException("unknown data type:value["+value+"],class["+value.getClass().getName()+"]");
		}
	}
	
	/**
	 * 根据模板设置的类型转换类型
	 */
	private Object converObjectType(Object obj, String objType){
		if(StringUtils.isNotBlank(objType)){
			if(ObjectTypeEnum.int_.getLabelKey().equals(objType)){
				if(null != obj && StringUtils.isNotBlank(obj + "")){
					return Integer.parseInt(obj + "");
				}else{
					return 0;
				}
			}else if(ObjectTypeEnum.double_.getLabelKey().equals(objType)){
				if(null != obj && StringUtils.isNotBlank(obj + "")){
					return Double.parseDouble(obj + "");
				}else{
					return 0.0D;
				}
			}else if(ObjectTypeEnum.String_.getLabelKey().equals(objType)){
				return obj + "";
			}else if(ObjectTypeEnum.Date_.getLabelKey().equals(objType)){
				if(null != obj && StringUtils.isNotBlank(obj + "")){
					return DateUtil.formatDateToString((Date)obj, _defaultDateTimeFormat);
				}else{
					return "";
				}
			}
		}
		return obj;
	}
	
	/**
	 * 把文件转成流写入工作簿
	 * @param saveTo
	 * @throws IOException
	 */
	public void write(File saveTo) throws IOException{
		this.CreateFolder(saveTo.getParent(),false);
		FileOutputStream fos=new FileOutputStream(saveTo);
		wb.write(fos);
		fos.flush();
		fos.close();
	}

	public boolean CreateFolder(String strPath, boolean isAncestorExist) {
		try {
			strPath = getDirPath(strPath);
			File file = new File(strPath);
			boolean success = false;
			if (file.exists()) {
				success = true;
				return true;
			}
			if (isAncestorExist) {
				// create a directory;all ancestor directories must exist;
				success = file.mkdir();
				if (!success) {
					success = true;
				}
			} else {
				// create a directory;all non-ancestor directories are
				success = file.mkdirs();
			}
			return success;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static String getDirPath(String value) {
		if (value.endsWith("\\") || value.endsWith("/")) {
			return value;
		}
		if (0 == value.indexOf("/")) {
			return value + "/";
		}
		return (value + "\\");
	}
	
	/**
	 * 把流写入工作簿
	 * @param writer
	 * @throws IOException
	 */
	public void write(OutputStream writer) throws IOException{
		wb.write(writer);
	}
	
	private CellRangeAddress getMergedRegion(Sheet sheet, int rowNum, int cellNum) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress merged = getMergedRegion(sheet, i);
			if (isRangeContainsCell(merged, rowNum, cellNum)) {
				return merged;
			}
		}
		return null;
	}

	private CellRangeAddress getMergedRegion(Sheet sheet, int i) {
		CellRangeAddress region = sheet.getMergedRegion(i);
		return region;
	}

	private boolean isRangeContainsCell(CellRangeAddress range, int row, int col) {
		if ((range.getFirstRow() <= row) && (range.getLastRow() >= row) && (range.getFirstColumn() <= col)
				&& (range.getLastColumn() >= col)) {
			return true;
		}
		return false;
	}
	
	private boolean isNewMergedRegion(CellRangeAddress region, Collection<?> mergedRegions) {
		for (Iterator<?> iterator = mergedRegions.iterator(); iterator.hasNext();) {
			CellRangeAddress cellRangeAddress = (CellRangeAddress) iterator.next();
			if (areRegionsEqual(cellRangeAddress, region)) {
				return false;
			}
		}
		return true;
	}

	private boolean areRegionsEqual(CellRangeAddress region1, CellRangeAddress region2) {
		if ((region1 == null && region2 != null) || (region1 != null && region2 == null)) {
			return false;
		}
		if (region1 == null && region2 == null) {
			return true;
		}
		return (region1.getFirstColumn() == region2.getFirstColumn()
				&& region1.getLastColumn() == region2.getLastColumn() && region1.getFirstRow() == region2.getFirstRow()
				&& region2.getLastRow() == region2.getLastRow());
	}
	
	public void setSetDataFormatFlag(boolean setDataFormatFlag) {
		this.setDataFormatFlag = setDataFormatFlag;
	}

	/**
	 * 測試
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		// jvm 设置 -Xms256m -Xmx1024m
		// 10万数据导出时间约等于24374毫秒
		// 20万数据导出时间约等于47732毫秒
		// 25万数据导出时间约等于66508毫秒
		// 30万数据导出时间约等于159160毫秒-此时导出的时间过久
	}
}
