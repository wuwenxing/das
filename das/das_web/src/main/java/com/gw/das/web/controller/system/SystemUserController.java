package com.gw.das.web.controller.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.context.Constants;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.easyui.TreeBean;
import com.gw.das.common.easyui.TreeBeanUtil;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.EnableFlagEnum;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.system.entity.SystemMenuEntity;
import com.gw.das.dao.system.entity.SystemUserEntity;
import com.gw.das.service.cache.MenuCache;
import com.gw.das.service.system.SystemMenuService;
import com.gw.das.service.system.SystemMenuUserService;
import com.gw.das.service.system.SystemUserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/SystemUserController")
public class SystemUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemUserController.class);

	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private SystemMenuService systemMenuService;
	@Autowired
	private SystemMenuUserService systemMenuUserService;
	
	/**
	 * 预留5列做为菜单
	 */
	private static final int cellColumn = 5;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try{
			String companyIds = "";
			if(super.isSuperAdmin(request)){
				companyIds = CompanyEnum.getAllCompanyIds();
			}else{
				companyIds = this.getLoginUser(request).getCompanyIds();
			}
			request.setAttribute("companyEnum", CompanyEnum.getList(companyIds));
			request.setAttribute("enableFlagEnum", EnableFlagEnum.getList());
			return "/system/user/user";
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询
	 * @return
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<SystemUserEntity> pageList(HttpServletRequest request, @ModelAttribute SystemUserEntity userEntity) {
		try{
			String companyIds = this.getLoginUser(request).getCompanyIds();
			return systemUserService.findPageList(super.createPageGrid(request, userEntity), companyIds);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SystemUserEntity>();
		}
	}
	
	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public SystemUserEntity findById(Long userId) {
		try{
			return systemUserService.findById(userId);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SystemUserEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request, @ModelAttribute SystemUserEntity userEntity) {
		try{
			if(null != userEntity.getUserId() && userEntity.getUserId() == 1){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.initDataTip));
				return resultCode;
			}
			
			// 1、校验编号不能重复
			if (systemUserService.checkUserNo(userEntity.getUserNo(), userEntity.getUserId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.userCodeExists));
				return resultCode;
			}
			
			// 2、业务权限时校验
			String newCompanyIds = userEntity.getCompanyIds();
			if(StringUtils.isNotBlank(newCompanyIds)){
				String companyIds = "";
				if(super.isSuperAdmin(request)){
					companyIds = CompanyEnum.getAllCompanyIds();
				}else{
					companyIds = this.getLoginUser(request).getCompanyIds();
				}
				for(String companyId: newCompanyIds.split(",")){
					if(companyIds.indexOf(companyId) == -1){
						ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
						resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.companyIdsError));
						return resultCode;
					}
				}
			}
			
			// 3、新增操作
			systemUserService.saveOrUpdate(userEntity);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 根据id删除
	 */
	@RequestMapping(value = "/deleteById", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode deleteById(String userIdArray) {
		try{
			String[] userIds = userIdArray.split(",");
			for(String userId: userIds){
				if("1".equals(userId)){
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.initDataTip));
					return resultCode;
				}
			}
			systemUserService.deleteByIdArray(userIdArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 重置密码
	 */
	@RequestMapping(value = "/resetPassword", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode resetPassword(HttpServletRequest request, Long userId) {
		try{
			Long loginUserId = this.getLoginUser(request).getUserId();
			if(userId == 1 && loginUserId != 1){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.superAdminResetPassword));
				return resultCode;
			}
			systemUserService.resetPassword(userId);
			return new ResultCode(ResultCodeEnum.success);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 分配菜单-提取树形菜单,并通过userId设置checkbox是否勾选
	 */
	@RequestMapping(value = "/loadMenuTreeShowCheckbox/{userId}", method = { RequestMethod.POST })
	@ResponseBody
	public List<TreeBean> loadMenuTreeShowCheckbox(HttpServletRequest request, @PathVariable Long userId) {
		try{
			boolean hasTopTag = true;// 是否包含顶部页签
			boolean hasMenu = true;//是否包含菜单
			boolean hasFun = true;// 是否包含功能
			boolean showFlag = true;// 若没有权限，是否显示在树形结构里
			String topTagMenuCode = null;// 查询指定顶部页签节点下的菜单,为空不过滤
			Long loginUserId = this.getLoginUser(request).getUserId();
			return systemMenuService.getMenuTreeJsonByUserId(hasTopTag, hasMenu, hasFun, showFlag, userId, loginUserId, topTagMenuCode);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<TreeBean>();
		}
	}
	
	/**
	 * 分配菜单保存
	 */
	@RequestMapping(value = "/assignMenuSave", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode assignMenuSave(@RequestParam Long userId
			, @RequestParam String menuCodes, @RequestParam String menuTypes) {
		try{
			systemMenuUserService.updateUserIdMenuIds(userId, menuCodes);
			MenuCache.refreshByUser(userId+"");
			return new ResultCode(ResultCodeEnum.success);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 导出系统用户列表
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute SystemUserEntity userEntity) {
		try{
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(new File(request.getServletContext().getRealPath(ExportTemplateEnum.systemUserInfo.getPath())));
			// 2、需要导出的数据
			String companyIds = "";
			if(super.isSuperAdmin(request)){
				companyIds = CompanyEnum.getAllCompanyIds();
			}else{
				companyIds = this.getLoginUser(request).getCompanyIds();
			}
			List<SystemUserEntity> recordList = systemUserService.findList(userEntity, companyIds);
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<SystemUserEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, SystemUserEntity param) {
					if("enableFlag".equals(fieldName) && null != fieldValue){
						if("Y".equals(fieldValue+"")){
							return "启用";
						}else{
							return "禁用";
						}
					}
					if("companyIds".equals(fieldName) && null != fieldValue){
						if(StringUtils.isNotBlank(fieldValue+"")){
							String companyName = "";
							String[] companyIdAry = (fieldValue+"").split(",");
							for(int i=0; i<companyIdAry.length; i++){
								String companyId = companyIdAry[i];
								companyName += CompanyEnum.format(companyId) + ",";
							}
							return companyName.substring(0, companyName.length()-1);
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			//对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.systemUserInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 导出系统用户权限列表
	 */
	@RequestMapping("/exportExcelAuth")
	public void exportExcelAuth(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute SystemUserEntity userEntity) {
		try{
			// 用户列表
			String companyIds = "";
			if(super.isSuperAdmin(request)){
				companyIds = CompanyEnum.getAllCompanyIds();
			}else{
				companyIds = this.getLoginUser(request).getCompanyIds();
			}
			List<SystemUserEntity> userList = systemUserService.findList(userEntity, companyIds);
			Map<String, String> map = systemUserService.findUserMenuMap();
			
			// 拥有的菜单集合，并转tree结构
			List<TreeBean> menuBeanList = new ArrayList<TreeBean>();
			List<SystemMenuEntity> menuList = MenuCache.getMenuObjList();
			for (SystemMenuEntity row : menuList) {
				TreeBean menuBean = new TreeBean();
				menuBean.setText(row.getMenuName());
				menuBean.setId(row.getMenuCode());
				menuBean.setParentId(row.getParentMenuCode());
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", row.getMenuId());
				menuBean.setAttributes(jsonObj);
				menuBeanList.add(menuBean);
			}
			List<TreeBean> treeBeanList = TreeBeanUtil.formatTreeBean(menuBeanList, false);
			
			// 定义excel工作簿-只写入
			SXSSFWorkbook wb = new SXSSFWorkbook(100);
			// create sheet
			Sheet sheet = wb.createSheet("系统用户权限列表");// sheet名称
			sheet.setColumnWidth(0, 15*256);// 设置列宽
			sheet.setColumnWidth(1, 15*256);// 设置列宽
			sheet.setColumnWidth(2, 15*256);// 设置列宽
			sheet.setColumnWidth(3, 35*256);// 设置列宽
			// 合并单元格
			CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 3);
			CellRangeAddress craUser = new CellRangeAddress(0, 0, 5, 5 + userList.size());
			sheet.addMergedRegion(cra);
			sheet.addMergedRegion(craUser);
			// 创建SXSSFWorkbook.createRow时的下标
			int createRowNum = 0;
			// CreateRowNum title
			Row platformRow = sheet.createRow(createRowNum);
			createRowNum ++;
			String companyName = "(" + CompanyEnum.format(this.getCompanyId(request)+"") + ")";
			String userListName = "用户No:";
			this.createCellT(wb, platformRow, 0).setCellValue(companyName + "系统用户权限列表");
			this.createCellT(wb, platformRow, 5).setCellValue(userListName);
			// CreateRowNum title
			Row titleRow = sheet.createRow(createRowNum);
			createRowNum ++;
			this.createCellT(wb, titleRow, 0).setCellValue("一级菜单");
			this.createCellT(wb, titleRow, 1).setCellValue("二级菜单");
			this.createCellT(wb, titleRow, 2).setCellValue("三级菜单");
			this.createCellT(wb, titleRow, 3).setCellValue("四级菜单");
			
			// create Row
			boolean writeUserNoFlag = false;
			for (int i=0; i<treeBeanList.size(); i++) {
				TreeBean treeBean = treeBeanList.get(i);
				// CreateRowNum
				Row targetRow = sheet.createRow(createRowNum);
				createRowNum ++;
				// CreateCell
				int cellColNum = 0;
				Cell cell = targetRow.createCell(cellColNum);
				// CellValue
				cell.setCellValue(treeBean.getText());
				// 写入用户No
				if(!writeUserNoFlag){
					writeUserNoFlag = true;
					this.writeUserNo(userList, treeBean, wb, titleRow);
				}
				// 写入是否有权限
				this.writeUser(userList, map, treeBean, targetRow);
		        
		        List<TreeBean> childrenList = treeBean.getChildren();
		        createRowNum = this.children(userList, map, childrenList, wb, sheet, createRowNum, cellColNum + 1);
		        
			}
			
			//对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(companyName + ExportTemplateEnum.systemUserInfoAuth.getValue(), request, response);
			wb.write(response.getOutputStream());
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 递归查子节点方法,并写入Excel
	 * @param treeList
	 */
	private int children(List<SystemUserEntity> userList, Map<String, String> map, List<TreeBean> childrenList, SXSSFWorkbook wb, Sheet sheet, int createRowNum, int cellColNum){
		if(null != childrenList && childrenList.size() > 0){
			for(int i=0; i<childrenList.size(); i++){
				TreeBean children = childrenList.get(i);
				// CreateRowNum children
				Row childrenRow = sheet.createRow(createRowNum);
				createRowNum ++;
				// CreateCell children
				Cell cellChildren = childrenRow.createCell(cellColNum);
				// CellValue children
				cellChildren.setCellValue(children.getText());
				// 写入是否有权限
				this.writeUser(userList, map, children, childrenRow);
				
		        List<TreeBean> subChildrenList = children.getChildren();
		        if(null != subChildrenList && subChildrenList.size() > 0){
		        	createRowNum = this.children(userList, map, subChildrenList, wb, sheet, createRowNum, cellColNum + 1);
		        }
			}
		}
		return createRowNum;
	}
	
	/**
	 * 写入用户No
	 * @param treeList
	 */
	private void writeUserNo(List<SystemUserEntity> userList, TreeBean treeBean, SXSSFWorkbook wb, Row row){
		if(null != userList && userList.size() > 0){
			for(int i=0; i<userList.size(); i++){
				SystemUserEntity userEntity = userList.get(i);
				// CreateCell 写入用户No
				Cell userNoCell = this.createCellT(wb, row, i + cellColumn);
				userNoCell.setCellValue(userEntity.getUserNo());
			}
		}
	}
	
	/**
	 * 写入是否有权限
	 * @param treeList
	 */
	private void writeUser(List<SystemUserEntity> userList, Map<String, String> map, TreeBean treeBean, Row row){
		if(null != userList && userList.size() > 0){
			for(int i=0; i<userList.size(); i++){
				SystemUserEntity userEntity = userList.get(i);
				// CreateCell 写入是否有权限
				Cell cell = row.createCell(i + cellColumn);
				if(Constants.superAdmin.equals(userEntity.getUserNo())){
					cell.setCellValue("Y");
				}else{
					String key = userEntity.getUserId() + "," + treeBean.getAttributes().get("id");
					if(null != map.get(key)){
						cell.setCellValue("Y");
					}
				}
			}
		}
	}
	
	/**
	 * 创建T类型cell
	 */
	private Cell createCellT(SXSSFWorkbook wb, Row row, int cellNum){
		Cell cell = row.createCell(cellNum);
		CellStyle cellStyle = wb.createCellStyle();
	    Font font = wb.createFont();
	    font.setFontHeightInPoints((short) 13);//设置字体大小
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
	    cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);//左对齐
	    cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
	    cellStyle.setFont(font);
	    cell.setCellStyle(cellStyle);
	    return cell;
	}
	
}
