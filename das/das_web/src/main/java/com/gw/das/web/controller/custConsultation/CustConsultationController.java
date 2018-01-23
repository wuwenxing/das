package com.gw.das.web.controller.custConsultation;

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

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.custConsultation.entity.CustConsultationEntity;
import com.gw.das.service.custConsultation.CustConsultationService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/CustConsultation")
public class CustConsultationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustConsultationController.class);

	@Autowired
	private CustConsultationService custConsultationService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("reportTypeEnum", ReportTypeEnum.getList3());
			return "/custConsultation/custConsultation";
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
	public PageGrid<CustConsultationEntity> pageList(HttpServletRequest request,
			@ModelAttribute CustConsultationEntity entity) {
		try {
			PageGrid<CustConsultationEntity> pageGrid = custConsultationService.findPageList(super.createPageGrid(request, entity));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<CustConsultationEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public CustConsultationEntity findById(Long consulttationId) {
		try {
			return custConsultationService.findById(consulttationId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new CustConsultationEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute CustConsultationEntity entity) {
		try {
			if(null != entity.getConsulttationId()){
				CustConsultationEntity custConsultation = custConsultationService.findById(entity.getConsulttationId());
				if(null == custConsultation){
					CustConsultationEntity custConsultationEntity = custConsultationService.findCustConsultation(entity);
					if(null != custConsultationEntity ){
						ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
						String value = CompanyEnum.format(UserContext.get().getCompanyId()+"");
						resultCode.addErrorInfo(new ErrorCode("custConsultationExists",value + "的新客有效咨询数据已经存在了"));
						return resultCode;
					}
				}
			}else{
				CustConsultationEntity custConsultationEntity = custConsultationService.findCustConsultation(entity);
				if(null != custConsultationEntity ){
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					String value = CompanyEnum.format(UserContext.get().getCompanyId()+"");
					resultCode.addErrorInfo(new ErrorCode("custConsultationExists",value + "的新客有效咨询数据已经存在了"));
					return resultCode;
				}
			}
			
			// 2、新增操作
			custConsultationService.saveOrUpdate(entity);
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
	public ResultCode deleteById(String consulttationIdArray) {
		try {
			custConsultationService.deleteByIdArray(consulttationIdArray);
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
			@ModelAttribute CustConsultationEntity entity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.custConsultation.getPath())));
			// 2、需要导出的数据
			List<CustConsultationEntity> recordList = custConsultationService.findList(entity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<CustConsultationEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, CustConsultationEntity param) {
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.custConsultation.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

}
