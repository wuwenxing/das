package com.gw.das.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.gw.das.common.easyui.DictTreeBean;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.dao.system.entity.SystemDictEntity;
import com.gw.das.service.cache.DictCache;
import com.gw.das.service.system.SystemDictService;

@Controller
@RequestMapping("/SystemDictController")
public class SystemDictController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemDictController.class);

	@Autowired
	private SystemDictService systemDictService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			return "/system/dict/dict";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 数据字典管理-加载菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/loadTreeGrid", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<SystemDictEntity> loadTreeGrid(HttpServletRequest request, @ModelAttribute SystemDictEntity dictEntity) {
		try {
			return systemDictService.getDictTreeJson(this.createPageGrid(request, dictEntity));
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SystemDictEntity>();
		}
	}

	/**
	 * 数据字典管理-加载子菜单
	 */
	@RequestMapping(value = "/loadChildTreeGrid/{dictCode}", method = RequestMethod.POST)
	@ResponseBody
	public List<DictTreeBean> loadChildTreeGrid(@PathVariable String dictCode){
		try {
			return systemDictService.getSubDictTreeJson(dictCode);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DictTreeBean>();
		}
	}
	
	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public SystemDictEntity findById(Long dictId) {
		try {
			return systemDictService.findById(dictId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SystemDictEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute SystemDictEntity dictEntity) {
		try {
			// 1、校验编号不能重复
			if (systemDictService.checkDictCode(dictEntity.getDictCode(), dictEntity.getDictId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.codeExists));
				return resultCode;
			}
			// 2、新增操作
			systemDictService.saveOrUpdate(dictEntity);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 根据id删除
	 */
	@RequestMapping(value = "/deleteById", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode deleteById(@RequestParam Long dictId) {
		try {
			// 删除前校验
			SystemDictEntity entity = systemDictService.findById(dictId);
			if(null != entity){
				List<SystemDictEntity> list = systemDictService.findListByParentDictCode(entity.getDictCode(), false);
				if(null != list && list.size() > 0){
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.childNodeExists));
					return resultCode;
				}
			}
			
			systemDictService.deleteById(dictId);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 根据parentDictCode，
	 * 返回<option value=\"" + dict.getDictCode() + "\" selected=\"selected\"></option>这种html文本
	 */
	@RequestMapping(value = "/getSubDictList/{parentDictCode}/{showPleaseFlag}", method = { RequestMethod.POST })
	@ResponseBody
	public List<SystemDictEntity> getSubDictList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String parentDictCode, @PathVariable String showPleaseFlag) {
		try {
			List<SystemDictEntity> list = DictCache.getSubDictList(parentDictCode);
			if("Y".equals(showPleaseFlag)){
				List<SystemDictEntity> dataList = new ArrayList<SystemDictEntity>();
				SystemDictEntity dictEntity = new SystemDictEntity();
				dictEntity.setDictName("---请选择---");
				dictEntity.setDictCode("");
				dataList.add(dictEntity);
				dataList.addAll(list);
				return dataList;
			}else{
				return list;
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<SystemDictEntity>();
		}
	}
	
}
