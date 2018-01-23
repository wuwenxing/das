package com.gw.das.web.controller.market;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.service.market.ChannelService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/ChannelController")
public class ChannelController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

	@Autowired
	private ChannelService channelService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("channelTypeEnum", ChannelTypeEnum.getList());
			return "/market/channel/channel";
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
	public PageGrid<ChannelEntity> pageList(HttpServletRequest request,
			@ModelAttribute ChannelEntity channelEntity) {
		try {
			PageGrid<ChannelEntity> pageGrid = channelService.findPageList(super.createPageGrid(request, channelEntity));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<ChannelEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public ChannelEntity findById(Long channelId) {
		try {
			return channelService.findById(channelId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ChannelEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute ChannelEntity channelEntity) {
		try {
			// 1、校验编号不能重复
			ChannelTypeEnum channelType = ChannelTypeEnum.format(channelEntity.getChannelType());
			if(null == channelType){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.channelTypeError));
				return resultCode;
			}
			// 1、来源并媒介不能重复
			if (channelService.checkChannel(channelType, channelEntity.getUtmcsr(), channelEntity.getUtmcmd(), channelEntity.getChannelId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.channelExists));
				return resultCode;
			}
			
			// 2、新增操作
			// 不填写则默认为：Direct
			if(StringUtils.isBlank(channelEntity.getChannelGroup())){
				channelEntity.setChannelGroup("Direct");
			}
			if(StringUtils.isBlank(channelEntity.getChannelLevel())){
				channelEntity.setChannelLevel("Direct");
			}
			channelService.saveOrUpdate(channelEntity);
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
	public ResultCode deleteById(String channelIdArray) {
		try {
			channelService.deleteByIdArray(channelIdArray);
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
			@ModelAttribute ChannelEntity channelEntity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.channelListInfo.getPath())));
			// 2、需要导出的数据
			List<ChannelEntity> recordList = channelService.findList(channelEntity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<ChannelEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, ChannelEntity param) {
					if("isPay".equals(fieldName)){
						if(null != fieldValue){
							if("1".equals(fieldValue+"")){
								return "免费";
							}else if("2".equals(fieldValue+"")){
								return "付费";
							}else if("3".equals(fieldValue+"")){
								return "其他";
							}
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.channelListInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

}
