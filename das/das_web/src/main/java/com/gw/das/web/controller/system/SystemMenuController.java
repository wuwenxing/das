package com.gw.das.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.easyui.TreeBean;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.dao.system.entity.SystemMenuEntity;
import com.gw.das.service.system.SystemMenuService;

@Controller
@RequestMapping("/SystemMenuController")
public class SystemMenuController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemMenuController.class);

	@Autowired
	private SystemMenuService systemMenuService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page() {
		return "/system/menu/menu";
	}

	/**
	 * tree
	 * @return
	 */
	@RequestMapping(value = "/loadMenuTree", method = { RequestMethod.POST })
	@ResponseBody
	public List<TreeBean> loadMenuTree(HttpServletRequest request) {
		try{
			boolean hasTopTag = true;// 是否包含顶部页签
			boolean hasMenu = true;//是否包含菜单
			boolean hasFun = true;// 是否包含功能
			String topTagMenuCode = null;// 查询指定顶部页签节点下的菜单,为空不过滤
			return systemMenuService.getMenuTreeJson(hasTopTag, hasMenu, hasFun, topTagMenuCode);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<TreeBean>();
		}
	}
	
	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public SystemMenuEntity findById(Long menuId) {
		try{
			return systemMenuService.findById(menuId);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SystemMenuEntity();
		}
	}
	
	/**
	 * 根据code查询
	 */
	@RequestMapping(value = "/findByCode", method = { RequestMethod.POST })
	@ResponseBody
	public SystemMenuEntity findByCode(String menuCode) {
		try{
			return systemMenuService.findByMenuCode(menuCode);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SystemMenuEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute SystemMenuEntity menuEntity) {
		try{
			// 1、校验编号不能重复
			if (systemMenuService.checkMenuCode(menuEntity.getMenuCode(), menuEntity.getMenuId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.codeExists));
				return resultCode;
			}
			// 2、新增操作
			systemMenuService.saveOrUpdate(menuEntity);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 根据menuCode删除
	 */
	@RequestMapping(value = "/deleteByMenuCode", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode deleteByMenuCode(@RequestParam String menuCode) {
		try{
			// 删除前校验
			List<SystemMenuEntity> menuList = systemMenuService.findByParentMenuCode(menuCode);
			if(null != menuList && menuList.size() > 0){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.childNodeExists));
				return resultCode;
			}
			
			systemMenuService.deleteByMenuCode(menuCode);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
}
