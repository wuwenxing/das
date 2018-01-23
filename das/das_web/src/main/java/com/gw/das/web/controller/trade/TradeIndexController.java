package com.gw.das.web.controller.trade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.dao.trade.entity.TradeIndexEntity;
import com.gw.das.service.trade.TradeIndexService;
import com.gw.das.web.controller.system.BaseController;

/**
 * 交易指标手工录入
 * @author wayne
 */
@Controller
@RequestMapping("/TradeIndexController")
public class TradeIndexController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TradeIndexController.class);

	@Autowired
	private TradeIndexService tradeIndexService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			return "/trade/tradeIndex/tradeIndex";
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
	public PageGrid<TradeIndexEntity> pageList(HttpServletRequest request, @ModelAttribute TradeIndexEntity entity) {
		try {
			PageGrid<TradeIndexEntity> pageGrid = tradeIndexService.findPageList(super.createPageGrid(request, entity));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<TradeIndexEntity>();
		}
	}
	
	/**
	 * 不分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	@ResponseBody
	public List<TradeIndexEntity> list(HttpServletRequest request, @ModelAttribute TradeIndexEntity entity) {
		try {
			List<TradeIndexEntity> list = tradeIndexService.findList(entity);
			return list;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<TradeIndexEntity>();
		}
	}
	
	/**
	 * 不分页查询-补全没有数据的日期
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dateIntervalList", method = { RequestMethod.POST })
	@ResponseBody
	public List<TradeIndexEntity> dateIntervalList(HttpServletRequest request, @ModelAttribute TradeIndexEntity entity) {
		try {
			// 获取时间区间集合
			List<String> dateList = DateUtil.getDateIntervalList(entity.getStartDate(), entity.getEndDate());
			
			// list转map,日期当key
			List<TradeIndexEntity> indexList = tradeIndexService.findList(entity);
			Map<String, TradeIndexEntity> indexMap = new HashMap<String, TradeIndexEntity>();
			for(TradeIndexEntity tempEntity:indexList){
				indexMap.put(tempEntity.getDateTime(), tempEntity);
			}
			// 补全时间区间数据
			List<TradeIndexEntity> returnList = new ArrayList<TradeIndexEntity>();
			for(String tempDate: dateList){
				if(null != indexMap.get(tempDate)){
					returnList.add(indexMap.get(tempDate));
				}else{
					TradeIndexEntity indexEntity = new TradeIndexEntity();
					indexEntity.setDateTime(tempDate);
					returnList.add(indexEntity);
				}
			}
			return returnList;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<TradeIndexEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public TradeIndexEntity findById(Long tradeIndexId) {
		try {
			return tradeIndexService.findById(tradeIndexId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new TradeIndexEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute TradeIndexEntity entity) {
		try {
			// 1、校验日期不能重复
			if (tradeIndexService.checkDateTime(entity.getDateTime(), entity.getTradeIndexId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.dateTimeIsExists));
				return resultCode;
			}

			// 2、新增操作
			tradeIndexService.saveOrUpdate(entity);
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
	public ResultCode deleteById(String tradeIndexIdArray) {
		try {
			tradeIndexService.deleteByIdArray(tradeIndexIdArray);
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
			@ModelAttribute TradeIndexEntity entity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.tradeIndexList.getPath())));
			// 2、需要导出的数据
			List<TradeIndexEntity> recordList = tradeIndexService.findList(entity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<TradeIndexEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, TradeIndexEntity param) {
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.tradeIndexList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

}
