package com.gw.das.web.controller.market;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ResCodeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.enums.SmsSignEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.response.UtmResponse;
import com.gw.das.common.response.UtmResult;
import com.gw.das.common.response.UtmSmsRequest;
import com.gw.das.common.sms.SmsUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SignUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.SmsTemplateEntity;
import com.gw.das.dao.market.entity.SmsTemplateLogEntity;
import com.gw.das.service.base.SmsSendService;
import com.gw.das.service.market.SmsConfigService;
import com.gw.das.service.market.SmsTemplateDetailService;
import com.gw.das.service.market.SmsTemplateLogService;
import com.gw.das.service.market.SmsTemplateService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping({"/SmsTemplateController", "/smsTemplate"})
public class SmsTemplateController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SmsTemplateController.class);
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private SmsConfigService smsConfigService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private SmsTemplateLogService smsTemplateLogService;
	@Autowired
	private SmsTemplateDetailService smsTemplateDetailService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			Long companyId = UserContext.get().getCompanyId();
			request.setAttribute("smsSignList", SmsSignEnum.getList(companyId));
			return "/market/smsTemplate/smsTemplate";
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
	public PageGrid<SmsTemplateEntity> pageList(HttpServletRequest request,
			@ModelAttribute SmsTemplateEntity smsTemplateEntity) {
		try {
			PageGrid<SmsTemplateEntity> pageGrid = smsTemplateService.findPageList(super.createPageGrid(request, smsTemplateEntity));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SmsTemplateEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public SmsTemplateEntity findById(Long templateId) {
		try {
			return smsTemplateService.findById(templateId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SmsTemplateEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute SmsTemplateEntity smsTemplateEntity) {
		try {
			// 1、校验编号不能重复
			if (smsTemplateService.checkSmsTemplate(smsTemplateEntity.getCode(), smsTemplateEntity.getTemplateId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.templateCodeExists));
				return resultCode;
			}

			// 2、新增操作
			smsTemplateService.saveOrUpdate(smsTemplateEntity);
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
	public ResultCode deleteById(String templateIdArray) {
		try {
			smsTemplateService.deleteByIdArray(templateIdArray);
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
			@ModelAttribute SmsTemplateEntity smsTemplateEntity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.smsTemplateListInfo.getPath())));
			// 2、需要导出的数据
			List<SmsTemplateEntity> recordList = smsTemplateService.findList(smsTemplateEntity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<SmsTemplateEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, SmsTemplateEntity param) {
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.smsTemplateListInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 调用模板发送短信
	 * 根据开发者账号区分不同业务平台
	 */
	@RequestMapping(value = "/send", method = { RequestMethod.POST })
	@ResponseBody
	public UtmResponse send(HttpServletRequest request) {
		return sendHandler(request);
	}

	/**
	 * 根据模板编号获取模板内容
	 */
	@RequestMapping(value = "/getContent", method = { RequestMethod.POST })
	@ResponseBody
	public UtmResult getContent(HttpServletRequest request) {
		UtmResult utmResult = null;
		// 参数定义
		String accountSid = "";
		String timestamp = "";
		String sign = "";
		String templateCode = "";
		try {
			// 1.获取post过来的数据
			accountSid = request.getParameter("accountSid");
			timestamp = request.getParameter("timestamp");
			sign = request.getParameter("sign");
			templateCode = request.getParameter("templateCode");
			
			// 1、开发者账号校验-验证
			if(StringUtils.isBlank(accountSid)){
				logger.error("开发者账号为空");
				utmResult = new UtmResult(ResCodeEnum.AccountSidIsNull);
				return utmResult;
			}else{
				if(SignUtil.FX_SID.equals(accountSid)){
				}else if(SignUtil.PM_SID.equals(accountSid)){
				}else if(SignUtil.HX_SID.equals(accountSid)){
				}else if(SignUtil.CF_SID.equals(accountSid)){
				}else{
					logger.error("开发者账号错误");
					utmResult = new UtmResult(ResCodeEnum.AccountSidError);
					return utmResult;
				}
			}
			
			// 2、查询短信模板-验证
			if(StringUtils.isBlank(templateCode)){
				logger.error("模板编号为空");
				utmResult = new UtmResult(ResCodeEnum.TemplateCodeIsNull);
				return utmResult;
			}
			SmsTemplateEntity smsTemplate = smsTemplateService.findByCode(templateCode);
			if (null == smsTemplate) {
				logger.error("模板编号不存在[templateCode=" + templateCode + "]");
				utmResult = new UtmResult(ResCodeEnum.TemplateCodeNotExist);
				return utmResult;
			}else{
				if(!"Y".equals(smsTemplate.getEnableFlag())){
					logger.error("模板被禁用[templateCode=" + templateCode + "]");
					utmResult = new UtmResult(ResCodeEnum.TemplateIsDisabled);
					return utmResult;
				}
			}
			
			// 3、签名校验-验证
			if(StringUtils.isBlank(timestamp)){
				logger.error("请求时间戳为空");
				utmResult = new UtmResult(ResCodeEnum.TimestampIsNull);
				return utmResult;
			}
			if(StringUtils.isBlank(sign)||!SignUtil.validate(timestamp, sign, accountSid)){
				logger.error("签名校验错误");
				utmResult = new UtmResult(ResCodeEnum.SignError);
				return utmResult;
			}

			// 4、返回查询结果及成功消息
			utmResult = new UtmResult(ResCodeEnum.Success);
			utmResult.setResult(smsTemplate.getContent());
			return utmResult;
		} catch (Exception e) {
			logger.error("获取模板[" + templateCode + "]内容出现异常" + e.getMessage(), e);
			utmResult = new UtmResult(ResCodeEnum.Exception);
			return utmResult;
		}
	}
	
	/**
	 * 据发送人账户获取某段时间内的短信发送记录
	 */
	@RequestMapping(value = "/getSendNum", method = { RequestMethod.POST })
	@ResponseBody
	public UtmResult getSendNum(HttpServletRequest request) {
		UtmResult utmResult = null;
		// 参数定义
		String accountSid = "";
		String timestamp = "";
		String sign = "";
		String templateCode = "";
		String startTime = "";
		String endTime = "";
		String sendNo = "";
		try {
			// 1.获取post过来的数据
			accountSid = request.getParameter("accountSid");
			timestamp = request.getParameter("timestamp");
			sign = request.getParameter("sign");
			templateCode = request.getParameter("templateCode");// 多个模板编号，逗号分隔
			startTime = request.getParameter("startTime");
			endTime = request.getParameter("endTime");
			sendNo = request.getParameter("sendNo");
			
			// 1、开发者账号校验-验证
			if(StringUtils.isBlank(accountSid)){
				logger.error("开发者账号为空");
				utmResult = new UtmResult(ResCodeEnum.AccountSidIsNull);
				return utmResult;
			}else{
				if(SignUtil.FX_SID.equals(accountSid)){
				}else if(SignUtil.PM_SID.equals(accountSid)){
				}else if(SignUtil.HX_SID.equals(accountSid)){
				}else if(SignUtil.CF_SID.equals(accountSid)){
				}else{
					logger.error("开发者账号错误");
					utmResult = new UtmResult(ResCodeEnum.AccountSidError);
					return utmResult;
				}
			}
			
			// 2、查询短信模板-验证
			if(StringUtils.isBlank(templateCode)){
				logger.error("模板编号为空");
				utmResult = new UtmResult(ResCodeEnum.TemplateCodeIsNull);
				return utmResult;
			}
			
			String templateIds = "";
			if(templateCode.indexOf(",") == -1){
				SmsTemplateEntity smsTemplate = smsTemplateService.findByCode(templateCode);
				if (null == smsTemplate) {
					logger.error("模板编号不存在[templateCode=" + templateCode + "]");
					utmResult = new UtmResult(ResCodeEnum.TemplateCodeNotExist);
					return utmResult;
				}else{
					if(!"Y".equals(smsTemplate.getEnableFlag())){
						logger.error("模板被禁用[templateCode=" + templateCode + "]");
						utmResult = new UtmResult(ResCodeEnum.TemplateIsDisabled);
						return utmResult;
					}
				}
				templateIds = smsTemplate.getTemplateId() + "";
			}else{
				String[] codes = templateCode.split(",");
				for(int i=0; i<codes.length; i++){
					String tempCode = codes[i];
					if(StringUtils.isNoneBlank(tempCode)){
						SmsTemplateEntity smsTemplate = smsTemplateService.findByCode(tempCode);
						if (null == smsTemplate) {
							logger.error("模板编号不存在[templateCode=" + templateCode + "]");
							utmResult = new UtmResult(ResCodeEnum.TemplateCodeNotExist);
							return utmResult;
						}else{
							if(!"Y".equals(smsTemplate.getEnableFlag())){
								logger.error("模板被禁用[templateCode=" + templateCode + "]");
								utmResult = new UtmResult(ResCodeEnum.TemplateIsDisabled);
								return utmResult;
							}
						}
						
						if((i+1) == codes.length){
							templateIds += smsTemplate.getTemplateId();
						}else{
							templateIds += smsTemplate.getTemplateId() + ",";
						}
						
					}
				}
			}
			
			// 3、判断是否传入发送人账户
			if(StringUtils.isNotBlank(sendNo)){
				if(sendNo.length() > 50){
					logger.error("发送人账户长度大于50字符");
					utmResult = new UtmResult("tooLong", "发送人账户长度不能大于50字符");
					return utmResult;
				}
			}else{
				logger.error("发送人账户参数为空");
				utmResult = new UtmResult("tooLong", "发送人账户参数为空");
				return utmResult;
			}
			
			// 4、签名校验-验证
			if(StringUtils.isBlank(timestamp)){
				logger.error("请求时间戳为空");
				utmResult = new UtmResult(ResCodeEnum.TimestampIsNull);
				return utmResult;
			}
			if(StringUtils.isBlank(sign)||!SignUtil.validate(timestamp, sign, accountSid)){
				logger.error("签名校验错误");
				utmResult = new UtmResult(ResCodeEnum.SignError);
				return utmResult;
			}

			// 5、对日期格式校验
			if(StringUtils.isNotBlank(startTime)){
				try {
					dateFormat.parse(startTime);
				} catch (Exception e) {
					// 日期格式错误
					logger.error("日期格式错误");
					utmResult = new UtmResult(ResCodeEnum.dateFormatError);
					return utmResult;
				}
			}
			if(StringUtils.isNotBlank(endTime)){
				try {
					dateFormat.parse(endTime);
				} catch (Exception e) {
					// 日期格式错误
					logger.error("日期格式错误");
					utmResult = new UtmResult(ResCodeEnum.dateFormatError);
					return utmResult;
				}
			}
			
			// 6、返回查询结果及成功消息
			List<Map<String, String>> list = smsTemplateDetailService.findList(templateIds, sendNo, startTime, endTime);
			utmResult = new UtmResult(ResCodeEnum.Success);
			utmResult.setResult(JacksonUtil.toJSon(list));
			return utmResult;
		} catch (Exception e) {
			logger.error("获取模板[" + templateCode + "]的发送记录[" + sendNo + "]出现异常" + e.getMessage(), e);
			utmResult = new UtmResult(ResCodeEnum.Exception);
			return utmResult;
		}
	}

	/**
	 * 发送统一处理
	 */
	private UtmResponse sendHandler(HttpServletRequest request) {
		UtmResponse utmResponse = null;
		String postStr = "";
		Long templateId = null;
		// 参数定义
		Long companyId = null;
		String accountSid = "";
		String timestamp = "";
		String sign = "";
		String templateCode = "";
		String templateParam = "";
		String phones = "";
		String sendNo = "";
		try {
			// 1.获取post过来的数据
			accountSid = request.getParameter("accountSid");
			timestamp = request.getParameter("timestamp");
			sign = request.getParameter("sign");
			templateCode = request.getParameter("templateCode");
			templateParam = request.getParameter("templateParam");
			phones = request.getParameter("phones");
			sendNo = request.getParameter("sendNo");
			
//			accountSid = "fa573c78eaa8402cb6c84dabfcce7158";
//			timestamp = "20161008140800";
//			sign = "5ed770bc2418fa0081fdb4538deec4a3";
//			templateCode = "reward_001";
//			templateParam = "{\"name\":\"小雪\"}";
//			phones = "13760291376,13760291376,eeeeee,54545454";
			
			UtmSmsRequest utmSmsRequest = new UtmSmsRequest();
			utmSmsRequest.setTimestamp(timestamp);
			utmSmsRequest.setAccountSid(accountSid);
			utmSmsRequest.setSign(sign);
			utmSmsRequest.setTemplateCode(templateCode);
			utmSmsRequest.setTemplateParam(templateParam);
			utmSmsRequest.setPhones(phones);
			postStr = JacksonUtil.toJSon(utmSmsRequest);

			// 1、开发者账号校验-验证
			if(StringUtils.isBlank(accountSid)){
				logger.error("开发者账号为空");
				utmResponse = new UtmResponse(ResCodeEnum.AccountSidIsNull);
				this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.AccountSidIsNull);
				return utmResponse;
			}else{
				if(SignUtil.FX_SID.equals(accountSid)){
					companyId = 1L;
				}else if(SignUtil.PM_SID.equals(accountSid)){
					companyId = 2L;
				}else if(SignUtil.HX_SID.equals(accountSid)){
					companyId = 3L;
				}else if(SignUtil.CF_SID.equals(accountSid)){
					companyId = 4L;
				}else{
					logger.error("开发者账号错误");
					utmResponse = new UtmResponse(ResCodeEnum.AccountSidError);
					this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.AccountSidError);
					return utmResponse;
				}
			}
			
			// 2、查询短信模板-验证
			if(StringUtils.isBlank(templateCode)){
				logger.error("模板编号为空");
				utmResponse = new UtmResponse(ResCodeEnum.TemplateCodeIsNull);
				this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.TemplateCodeIsNull);
				return utmResponse;
			}
			SmsTemplateEntity smsTemplate = smsTemplateService.findByCode(templateCode);
			if (null == smsTemplate) {
				logger.error("模板编号不存在[templateCode=" + templateCode + "]");
				utmResponse = new UtmResponse(ResCodeEnum.TemplateCodeNotExist);
				this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.TemplateCodeNotExist);
				return utmResponse;
			}else{
				templateId = smsTemplate.getTemplateId();
				if(!"Y".equals(smsTemplate.getEnableFlag())){
					logger.error("模板被禁用[templateCode=" + templateCode + "]");
					utmResponse = new UtmResponse(ResCodeEnum.TemplateIsDisabled);
					this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.TemplateIsDisabled);
					return utmResponse;
				}
			}
			
			// 3、签名校验-验证
			if(StringUtils.isBlank(timestamp)){
				logger.error("请求时间戳为空");
				utmResponse = new UtmResponse(ResCodeEnum.TimestampIsNull);
				this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.TimestampIsNull);
				return utmResponse;
			}
			if(StringUtils.isBlank(sign)||!SignUtil.validate(timestamp, sign, accountSid)){
				logger.error("签名校验错误");
				utmResponse = new UtmResponse(ResCodeEnum.SignError);
				this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.SignError);
				return utmResponse;
			}
			
			// 4、模板参数是否匹配模板-校验及验证
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(templateParam)){
				paramMap = JacksonUtil.readValue(templateParam, new TypeReference<Map<String, Object>>(){});
			}
			String content = "";
			try{
				content = SmsUtil.formatContent(smsTemplate.getContent(), paramMap);
			}catch(Exception e){
				logger.error("模板参数不匹配[templateCode=" + templateCode + "]" + e.getMessage(), e);
				utmResponse = new UtmResponse(ResCodeEnum.TemplateParamNotMatch);
				this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.TemplateParamNotMatch);
				return utmResponse;
			}
			
			// 5、手机号码空值校验-校验及验证
			if(StringUtils.isBlank(phones)){
				logger.error("手机号为空值");
				utmResponse = new UtmResponse(ResCodeEnum.PhoneIsNull);
				this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.PhoneIsNull);
				return utmResponse;
			}
			
			// 6、手机号码格式校验及统计-校验及验证
			List<String> legalMobiles = new ArrayList<String>();
			List<String> illegalMobiles = new ArrayList<String>();
			String[] phoneAry = phones.split(",");
			for (int i = 0; i < phoneAry.length; i++) {
				String tempPhone = phoneAry[i];
				if (!SmsUtil.checkMobileNum(tempPhone)) {
					logger.error("第" + (i + 1) + "个的手机号：" + tempPhone + "格式错误;");
					illegalMobiles.add(tempPhone);
					continue;
				}
				if (legalMobiles.contains(tempPhone)) {
					logger.error("第" + (i + 1) + "个的手机号重复;");
					illegalMobiles.add(tempPhone);
					continue;
				}
				legalMobiles.add(tempPhone);
			}
			if(legalMobiles.size() <= 0){
				logger.error("不存在合法的手机号");
				utmResponse = new UtmResponse(ResCodeEnum.NoValidPhone);
				this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.NoValidPhone);
				return utmResponse;
			}
			
			// 7、成功-保存提交请求日志
			SmsTemplateLogEntity reqLog = new SmsTemplateLogEntity();
			// 判断是否传入发送人账户
			if(StringUtils.isNotBlank(sendNo)){
				if(sendNo.length() <= 50){
					reqLog.setSendNo(sendNo);
				}else{
					logger.error("发送人账户长度大于50字符");
				}
			}
			reqLog.setParam(postStr);
			reqLog.setTemplateId(templateId);
			reqLog.setPhones(phones);
			reqLog.setStatusCode(ResCodeEnum.Success.getLabelKey());
			reqLog.setInputNum(Long.parseLong(phoneAry.length + ""));
			reqLog.setLegalNum(Long.parseLong(legalMobiles.size() + ""));
			reqLog.setIllegalNum(Long.parseLong(illegalMobiles.size() + ""));
			reqLog.setLegalPhones(StringUtil.list2String(legalMobiles));
			reqLog.setIllegalPhones(StringUtil.list2String(illegalMobiles));
			smsTemplateLogService.saveOrUpdate(reqLog);

			// 8、调用发送短信线程
			String smsChannel = smsConfigService.getSmsChannel(smsTemplate.getSmsSign());
			smsTemplate.setContent(content);// 注意：使用替换后的短信内容
			smsSendService.asynThreading(smsTemplate, reqLog, smsChannel);
			
			// 9、执行完毕，返回成功消息
			utmResponse = new UtmResponse(ResCodeEnum.Success);
			return utmResponse;
		} catch (Exception e) {
			logger.error("调用模板发送短信[" + postStr + "]出现异常" + e.getMessage(), e);
			utmResponse = new UtmResponse(ResCodeEnum.Exception);
			this.saveReqLog(postStr, phones, templateId, companyId, ResCodeEnum.Exception);
			return utmResponse;
		}
	}
	
	/**
	 * 验证失败时，保存请求日志处理
	 * @param postStr
	 * @param phones
	 * @param companyId
	 * @param errorCodeEnum
	 */
	private void saveReqLog(String postStr, String phones, Long templateId, Long companyId, ResCodeEnum resCodeEnum){
		try{
			SmsTemplateLogEntity reqLog = new SmsTemplateLogEntity();
			reqLog.setParam(postStr);
			reqLog.setTemplateId(templateId);
			reqLog.setPhones(phones);
			reqLog.setStatusCode(resCodeEnum.getLabelKey());
			smsTemplateLogService.saveOrUpdate(reqLog);
		}catch(Exception e){
			logger.error("保存提交请求日志error" + e.getMessage(), e);
		}
	}
	public static void main(String[] args) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String, String>();
		map.put("phone", "13760291376");
		map.put("sendNum", "1");
		list.add(map);
		String result = JacksonUtil.toJSon(list);
		logger.info("result=" + result);
		UtmResult utmResult = new UtmResult(ResCodeEnum.Success);
		utmResult.setResult(result);
		logger.info("返回对象json=" + JacksonUtil.toJSon(utmResult));
	}
}
