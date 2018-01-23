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
import com.gw.das.common.enums.BusTagContentEnum;
import com.gw.das.common.enums.BusTagTypeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.market.entity.BusinessTagEntity;
import com.gw.das.service.market.BusinessTagService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/businessTagController")
public class BusinessTagController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessTagController.class);

	@Autowired
	private BusinessTagService tagService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("busTagContentEnum", BusTagContentEnum.getList());
			request.setAttribute("busTagTypeEnum", BusTagTypeEnum.getList());
			return "/market/businessTag/businessTag";
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
	public PageGrid<BusinessTagEntity> pageList(HttpServletRequest request,
			@ModelAttribute BusinessTagEntity BusinessTagEntity) {
		try {
			PageGrid<BusinessTagEntity> pageGrid = tagService.findPageList(super.createPageGrid(request, BusinessTagEntity));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<BusinessTagEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public BusinessTagEntity findById(Long tagId) {
		try {
			return tagService.findById(tagId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new BusinessTagEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute BusinessTagEntity BusinessTagEntity) {
		try {
			// 2、新增操作
			tagService.saveOrUpdate(BusinessTagEntity);
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
	public ResultCode deleteById(String tagIdArray) {
		try {
			tagService.deleteByIdArray(tagIdArray);
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
			@ModelAttribute BusinessTagEntity BusinessTagEntity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.businessTagListInfo.getPath())));
			// 2、需要导出的数据
			List<BusinessTagEntity> recordList = tagService.findList(BusinessTagEntity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<BusinessTagEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, BusinessTagEntity param) {
					if("tagContent".equals(fieldName)){
						if(BusTagContentEnum.TAGCONTENT1.getLabelKey().equals(String.valueOf(fieldValue))){
							return BusTagContentEnum.TAGCONTENT1.getValue();
						}else if(BusTagContentEnum.TAGCONTENT2.getLabelKey().equals(String.valueOf(fieldValue))){
							return BusTagContentEnum.TAGCONTENT2.getValue();
						}else if(BusTagContentEnum.TAGCONTENT3.getLabelKey().equals(String.valueOf(fieldValue))){
							return BusTagContentEnum.TAGCONTENT3.getValue();
						}else if(BusTagContentEnum.TAGCONTENT4.getLabelKey().equals(String.valueOf(fieldValue))){
							return BusTagContentEnum.TAGCONTENT4.getValue();
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.businessTagListInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

}
