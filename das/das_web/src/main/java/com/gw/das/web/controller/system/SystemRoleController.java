package com.gw.das.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.easyui.TreeBean;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.dao.system.entity.SystemRoleEntity;
import com.gw.das.dao.system.entity.SystemUserEntity;
import com.gw.das.service.system.SystemMenuRoleService;
import com.gw.das.service.system.SystemMenuService;
import com.gw.das.service.system.SystemRoleService;
import com.gw.das.service.system.SystemUserService;

@Controller
@RequestMapping("/SystemRoleController")
public class SystemRoleController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(SystemRoleController.class);

	@Autowired
	private SystemRoleService systemRoleService;
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private SystemMenuService systemMenuService;
	@Autowired
	private SystemMenuRoleService systemMenuRoleService;
	
	/**
	 * 跳转管理页面
	 * @return
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try{
			String companyIds = systemRoleService.findCompanyIdsByRoleId(this.getLoginUser(request).getRoleId());
			request.setAttribute("companyEnum", CompanyEnum.getList(companyIds));
			return "/system/role/role";
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
	public PageGrid<SystemRoleEntity> pageList(HttpServletRequest request, @ModelAttribute SystemRoleEntity roleEntity) {
		try{
			String companyIds = systemRoleService.findCompanyIdsByRoleId(this.getLoginUser(request).getRoleId());
			return systemRoleService.findPageList(super.createPageGrid(request, roleEntity), companyIds);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SystemRoleEntity>();
		}
	}
	
	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public SystemRoleEntity findById(Long roleId) {
		try{
			return systemRoleService.findById(roleId);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SystemRoleEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request, @ModelAttribute SystemRoleEntity roleEntity) {
		try{
			// 1、校验编号不能重复
			if (systemRoleService.checkRoleCode(roleEntity.getRoleCode(), roleEntity.getRoleId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.codeExists));
				return resultCode;
			}
			// 分配业务权限时校验
			String companyIds = systemRoleService.findCompanyIdsByRoleId(this.getLoginUser(request).getRoleId());
			String newCompanyIds = roleEntity.getCompanyIds();
			if(StringUtils.isNotBlank(newCompanyIds)){
				for(String companyId: newCompanyIds.split(",")){
					if(companyIds.indexOf(companyId) == -1){
						ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
						resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.roleCompanyIdsError));
						return resultCode;
					}
				}
			}
			
			if (systemRoleService.checkRoleCode(roleEntity.getRoleCode(), roleEntity.getRoleId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.codeExists));
				return resultCode;
			}
			
			// 2、新增操作
			systemRoleService.saveOrUpdate(roleEntity);
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
	public ResultCode deleteById(String roleIdArray) {
		try{
			systemRoleService.deleteByIdArray(roleIdArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分配用户-根据roleId查询用户列表
	 */
	@RequestMapping(value = "/assignUser", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<SystemUserEntity> assignUser(HttpServletRequest request, @RequestParam Long roleId,
			@ModelAttribute SystemUserEntity userEntity) {
		try{
			userEntity.setRoleId(roleId);// 查询指定roleId
			userEntity.setRoleIsNull(true);// 查询包含为空的roleId
			String companyIds = systemRoleService.findCompanyIdsByRoleId(this.getLoginUser(request).getRoleId());
			PageGrid<SystemUserEntity> pageGrid = systemUserService.findPageList(super.createPageGrid(request, userEntity), companyIds);
			for(Object obj: pageGrid.getRows()){
				SystemUserEntity record = (SystemUserEntity)obj;
				if(null != record.getRoleId() && roleId == record.getRoleId()){
					record.setChecked(true);
				}else{
					record.setChecked(false);
				}
			}
			return pageGrid;
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SystemUserEntity>();
		}
	}

	/**
	 * 分配用户保存
	 */
	@RequestMapping(value = "/assignUserSave", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode assignUserSave(@RequestParam Long roleId,
			@RequestParam String selectIds, @RequestParam String unSelectIds) {
		try{
			systemUserService.updateRoleIdByUserIdArray(roleId, selectIds, unSelectIds);
			return new ResultCode(ResultCodeEnum.success);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分配菜单-提取树形菜单,并通过角色id设置checkbox是否勾选
	 */
	@RequestMapping(value = "/loadMenuTreeShowCheckbox/{roleId}", method = { RequestMethod.POST })
	@ResponseBody
	public List<TreeBean> loadMenuTreeShowCheckbox(@PathVariable Long roleId) {
		try{
			return systemMenuService.getMenuTreeJson(roleId, true);
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
	public ResultCode assignMenuSave(@RequestParam Long roleId
			, @RequestParam String menuCodes, @RequestParam String menuTypes) {
		try{
			systemMenuRoleService.updateRoleIdMenuIds(roleId, menuCodes);
			return new ResultCode(ResultCodeEnum.success);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
}
