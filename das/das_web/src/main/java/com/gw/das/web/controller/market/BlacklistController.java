package com.gw.das.web.controller.market;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.AccountTypeEnum;
import com.gw.das.common.enums.BlacklistTypeEnum;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.BlacklistEntity;
import com.gw.das.service.market.BlacklistService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/BlacklistController")
public class BlacklistController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BlacklistController.class);

	@Autowired
	private BlacklistService blacklistService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("blacklistTypeEnum", BlacklistTypeEnum.getList());
			request.setAttribute("accountTypeEnum", AccountTypeEnum.getList());
			return "/market/blacklist/blacklist";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<BlacklistEntity> pageList(HttpServletRequest request,
			@ModelAttribute BlacklistEntity blacklistEntity) {
		try {
			PageGrid<BlacklistEntity> pageGrid = blacklistService.findPageList(super.createPageGrid(request, blacklistEntity));
			for(Object obj: pageGrid.getRows()){
				BlacklistEntity record = (BlacklistEntity)obj;

				if(AccountTypeEnum.phone.getLabelKey().equals(record.getAccountType())){
					record.setAccount(StringUtil.formatPhone(record.getAccount()));
				}else if(AccountTypeEnum.email.getLabelKey().equals(record.getAccountType())){
					record.setAccount(StringUtil.formatEmail(record.getAccount()));
				}
				record.setAccountType(AccountTypeEnum.format(record.getAccountType()));
				record.setBlacklistType(BlacklistTypeEnum.format(record.getBlacklistType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<BlacklistEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public BlacklistEntity findById(Long blacklistId) {
		try {
			return blacklistService.findById(blacklistId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new BlacklistEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute BlacklistEntity blacklistEntity) {
		try {
			// 1、校验编号不能重复
			if (blacklistService.checkAccount(blacklistEntity.getAccount(), blacklistEntity.getBlacklistId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.blacklistAccountExists));
				return resultCode;
			}

			// 2、新增操作
			blacklistService.saveOrUpdate(blacklistEntity);
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
	public ResultCode deleteById(String blacklistIdArray) {
		try {
			blacklistService.deleteByIdArray(blacklistIdArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute BlacklistEntity blacklistEntity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.blacklistInfo.getPath())));
			// 2、需要导出的数据
			List<BlacklistEntity> recordList = blacklistService.findList(blacklistEntity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<BlacklistEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, BlacklistEntity param) {
					if ("accountType".equals(fieldName)) {

					} else if ("blacklistType".equals(fieldName)) {

					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.blacklistInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

}
