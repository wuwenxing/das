package com.gw.das.web.controller.market;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.email.EmailUtil;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ResCodeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.response.UtmEmailRequest;
import com.gw.das.common.response.UtmResponse;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SignUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.EmailTemplateEntity;
import com.gw.das.dao.market.entity.EmailTemplateLogEntity;
import com.gw.das.service.base.EmailSendService;
import com.gw.das.service.market.EmailTemplateLogService;
import com.gw.das.service.market.EmailTemplateService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping({"/EmailTemplateController", "/emailTemplate"})
public class EmailTemplateController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(EmailTemplateController.class);

	@Autowired
	private EmailSendService emailSendService;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private EmailTemplateLogService emailTemplateLogService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			return "/market/emailTemplate/emailTemplate";
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
	public PageGrid<EmailTemplateEntity> pageList(HttpServletRequest request,
			@ModelAttribute EmailTemplateEntity emailTemplateEntity) {
		try {
			PageGrid<EmailTemplateEntity> pageGrid = emailTemplateService.findPageList(super.createPageGrid(request, emailTemplateEntity));
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<EmailTemplateEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public EmailTemplateEntity findById(Long templateId) {
		try {
			return emailTemplateService.findById(templateId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new EmailTemplateEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute EmailTemplateEntity emailTemplateEntity) {
		try {
			// 1、校验编号不能重复
			if (emailTemplateService.checkEmailTemplate(emailTemplateEntity.getCode(), emailTemplateEntity.getTemplateId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.templateCodeExists));
				return resultCode;
			}

			// 2、新增操作
			emailTemplateService.saveOrUpdate(emailTemplateEntity);
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
			emailTemplateService.deleteByIdArray(templateIdArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 邮件模板预览
	 */
	@RequestMapping("/preview/{templateId}")
	public String preview(HttpServletRequest request, @PathVariable Long templateId) {
		try {
			EmailTemplateEntity model = emailTemplateService.findById(templateId);
			request.setAttribute("title", model.getTitle());
			request.setAttribute("content", model.getContent());
		} catch (Exception e) {
			logger.error("预览邮件内容异常：" + e.getMessage(), e);
		}
		return "market/emailTemplate/preview";
	}
	
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute EmailTemplateEntity emailTemplateEntity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.emailTemplateListInfo.getPath())));
			// 2、需要导出的数据
			List<EmailTemplateEntity> recordList = emailTemplateService.findList(emailTemplateEntity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<EmailTemplateEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, EmailTemplateEntity param) {
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.emailTemplateListInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}


	/**
	 * 调用模板发送邮件 根据开发者账号区分不同业务平台
	 */
	@RequestMapping(value = "/send", method = { RequestMethod.POST })
	@ResponseBody
	public UtmResponse send(HttpServletRequest request) {
		return sendHandler(request);
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
		String emails = "";
		try {
			// 1.获取post过来的数据
			accountSid = request.getParameter("accountSid");
			timestamp = request.getParameter("timestamp");
			sign = request.getParameter("sign");
			templateCode = request.getParameter("templateCode");
			templateParam = request.getParameter("templateParam");
			emails = request.getParameter("emails");

			// accountSid = "fa573c78eaa8402cb6c84dabfcce7158";
			// timestamp = "20161008140800";
			// sign = "e9ccb14450d1e7d03014defbf6d401e8";
			// templateCode = "reward_001";
			// templateParam = "{\"name\":\"小雪\"}";
			// emails = "wuwenxing-nc@qq.com,eeeeee,54545454";

			UtmEmailRequest utmEmailRequest = new UtmEmailRequest();
			utmEmailRequest.setTimestamp(timestamp);
			utmEmailRequest.setAccountSid(accountSid);
			utmEmailRequest.setSign(sign);
			utmEmailRequest.setTemplateCode(templateCode);
			utmEmailRequest.setTemplateParam(templateParam);
			utmEmailRequest.setEmails(emails);
			postStr = JacksonUtil.toJSon(utmEmailRequest);

			// 1、开发者账号校验-验证
			if (StringUtils.isBlank(accountSid)) {
				logger.error("开发者账号为空");
				utmResponse = new UtmResponse(ResCodeEnum.AccountSidIsNull);
				this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.AccountSidIsNull);
				return utmResponse;
			} else {
				if (SignUtil.FX_SID.equals(accountSid)) {
					companyId = 1L;
				} else if (SignUtil.PM_SID.equals(accountSid)) {
					companyId = 2L;
				} else if (SignUtil.HX_SID.equals(accountSid)){
					companyId = 3L;
				} else if (SignUtil.CF_SID.equals(accountSid)){
					companyId = 4L;
				} else {
					logger.error("开发者账号错误");
					utmResponse = new UtmResponse(ResCodeEnum.AccountSidError);
					this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.AccountSidError);
					return utmResponse;
				}
			}

			// 2、查询邮件模板-验证
			if (StringUtils.isBlank(templateCode)) {
				logger.error("模板编号为空");
				utmResponse = new UtmResponse(ResCodeEnum.EmailTemplateCodeIsNull);
				this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.EmailTemplateCodeIsNull);
				return utmResponse;
			}
			EmailTemplateEntity emailTemplate = emailTemplateService.findByCode(templateCode);
			if (null == emailTemplate) {
				logger.error("模板编号不存在[templateCode=" + templateCode + "]");
				utmResponse = new UtmResponse(ResCodeEnum.EmailTemplateCodeNotExist);
				this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.EmailTemplateCodeNotExist);
				return utmResponse;
			} else {
				templateId = emailTemplate.getTemplateId();
				if (!"Y".equals(emailTemplate.getEnableFlag())) {
					logger.error("模板被禁用[templateCode=" + templateCode + "]");
					utmResponse = new UtmResponse(ResCodeEnum.EmailTemplateIsDisabled);
					this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.EmailTemplateIsDisabled);
					return utmResponse;
				}
			}

			// 3、签名校验-验证
			if (StringUtils.isBlank(timestamp)) {
				logger.error("请求时间戳为空");
				utmResponse = new UtmResponse(ResCodeEnum.TimestampIsNull);
				this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.TimestampIsNull);
				return utmResponse;
			}
			if (StringUtils.isBlank(sign) || !SignUtil.validate(timestamp, sign, accountSid)) {
				logger.error("签名校验错误");
				utmResponse = new UtmResponse(ResCodeEnum.SignError);
				this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.SignError);
				return utmResponse;
			}

			// 4、模板参数是否匹配模板-校验及验证
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(templateParam)) {
				paramMap = JacksonUtil.readValue(templateParam, new TypeReference<Map<String, Object>>() {
				});
			}
			String content = "";
			String title = "";
			try {
				title = EmailUtil.formatContent(emailTemplate.getTitle(), paramMap);
				content = EmailUtil.formatContent(emailTemplate.getContent(), paramMap);
			} catch (Exception e) {
				logger.error("模板参数不匹配[templateCode=" + templateCode + "]" + e.getMessage(), e);
				utmResponse = new UtmResponse(ResCodeEnum.EmailTemplateParamNotMatch);
				this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.EmailTemplateParamNotMatch);
				return utmResponse;
			}

			// 5、邮箱空值校验-校验及验证
			if (StringUtils.isBlank(emails)) {
				logger.error("邮箱为空值");
				utmResponse = new UtmResponse(ResCodeEnum.EmailIsNull);
				this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.EmailIsNull);
				return utmResponse;
			}

			// 6、邮箱格式校验及统计-校验及验证
			List<String> legalEmails = new ArrayList<String>();
			List<String> illegalEmails = new ArrayList<String>();
			String[] emailAry = emails.split(",");
			for (int i = 0; i < emailAry.length; i++) {
				String tempEmail = emailAry[i];
				if (!EmailUtil.checkEmail(tempEmail)) {
					logger.error("第" + (i + 1) + "个的邮箱：" + tempEmail + "格式错误;");
					illegalEmails.add(tempEmail);
					continue;
				}
				if (legalEmails.contains(tempEmail)) {
					logger.error("第" + (i + 1) + "个的邮箱重复;");
					illegalEmails.add(tempEmail);
					continue;
				}
				legalEmails.add(tempEmail);
			}
			if (legalEmails.size() <= 0) {
				logger.error("不存在合法的邮箱");
				utmResponse = new UtmResponse(ResCodeEnum.NoValidEmail);
				this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.NoValidEmail);
				return utmResponse;
			}

			// 7、成功-保存提交请求日志
			EmailTemplateLogEntity reqLog = new EmailTemplateLogEntity();
			reqLog.setParam(postStr);
			reqLog.setTemplateId(templateId);
			reqLog.setEmails(emails);
			reqLog.setStatusCode(ResCodeEnum.Success.getLabelKey());
			reqLog.setInputNum(Long.parseLong(emailAry.length+""));
			reqLog.setLegalNum(Long.parseLong(legalEmails.size()+""));
			reqLog.setIllegalNum(Long.parseLong(illegalEmails.size()+""));
			reqLog.setLegalEmails(StringUtil.list2String(legalEmails));
			reqLog.setIllegalEmails(StringUtil.list2String(illegalEmails));
			reqLog.setCreateDate(new Date());
			reqLog.setCompanyId(companyId);
			emailTemplateLogService.saveOrUpdate(reqLog);

			// 8、调用发送邮件线程
			// 注意：使用替换后的邮件内容
			emailSendService.asynThreading(title, content, emailTemplate, reqLog);

			// 9、执行完毕，返回成功消息
			utmResponse = new UtmResponse(ResCodeEnum.Success);
			return utmResponse;
		} catch (Exception e) {
			logger.error("调用模板发送邮件[" + postStr + "]出现异常" + e.getMessage(), e);
			utmResponse = new UtmResponse(ResCodeEnum.Exception);
			this.saveReqLog(postStr, emails, templateId, companyId, ResCodeEnum.Exception);
			return utmResponse;
		}
	}

	/**
	 * 验证失败时，保存请求日志处理
	 * 
	 * @param postStr
	 * @param emails
	 * @param companyId
	 * @param errorCodeEnum
	 */
	private void saveReqLog(String postStr, String emails, Long templateId, Long companyId,
			ResCodeEnum resCodeEnum) {
		try {
			EmailTemplateLogEntity reqLog = new EmailTemplateLogEntity();
			reqLog.setParam(postStr);
			reqLog.setTemplateId(templateId);
			reqLog.setEmails(emails);
			reqLog.setStatusCode(resCodeEnum.getLabelKey());
			reqLog.setCreateDate(new Date());
			reqLog.setCompanyId(companyId);
			emailTemplateLogService.saveOrUpdate(reqLog);
		} catch (Exception e) {
			logger.error("保存提交请求日志error" + e.getMessage(), e);
		}
	}
	
}
