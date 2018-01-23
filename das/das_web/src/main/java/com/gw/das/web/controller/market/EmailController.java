package com.gw.das.web.controller.market;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

import com.gw.das.common.context.Constants;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.email.EmailUtil;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.enums.SendTypeEnum;
import com.gw.das.common.enums.SourceTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.DasUserScreenEntity;
import com.gw.das.dao.market.entity.EmailEntity;
import com.gw.das.dao.market.entity.TimingEntity;
import com.gw.das.dao.market.entity.UserGroupEntity;
import com.gw.das.service.market.EmailService;
import com.gw.das.service.market.TimingService;
import com.gw.das.service.market.UserGroupService;
import com.gw.das.service.website.DasUserScreenService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/EmailController")
public class EmailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@Autowired
	private EmailService emailService;
	@Autowired
	private TimingService timingService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private DasUserScreenService dasUserScreenService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("sendTypeEnum", SendTypeEnum.getList());
			request.setAttribute("sourceTypeEnum", SourceTypeEnum.getList());

			List<UserGroupEntity> userGroupList = userGroupService.findList(null);
			List<DasUserScreenEntity> dasUserScreenList = dasUserScreenService.findList(null);
			request.setAttribute("userGroupList", userGroupList);
			request.setAttribute("dasUserScreenList", dasUserScreenList);
			return "/market/email/email";
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
	public PageGrid<EmailEntity> pageList(HttpServletRequest request,
			@ModelAttribute EmailEntity emailEntity) {
		try {
			PageGrid<EmailEntity> pageGrid = emailService.findPageList(super.createPageGrid(request, emailEntity));
			for(Object obj: pageGrid.getRows()){
				EmailEntity record = (EmailEntity)obj;
				record.setSendType(SendTypeEnum.format(record.getSendType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<EmailEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public EmailEntity findById(Long emailId) {
		try {
			EmailEntity emailEntity = emailService.findById(emailId);
			if(null != emailEntity){
				if(SendTypeEnum.setTime.getLabelKey().equals(emailEntity.getSendType())){
					// 如果是定时发送邮件，则还需查询定时的时间数据
					TimingEntity timingEntity = new TimingEntity();
					timingEntity.setEmailId(emailEntity.getEmailId());
					List<TimingEntity> timingList = timingService.findList(timingEntity);
					emailEntity.setTimingList(timingList);
				}else if(SendTypeEnum.sendNow.getLabelKey().equals(emailEntity.getSendType())){
					// 如果是即时发送邮件，邮箱需屏蔽，否则暂时不屏蔽，因为可以修改
					// 手动和上传的邮箱，邮箱需屏蔽
					List<String> formatPhoneList = new ArrayList<String>();
					if(null != emailEntity && StringUtils.isNotBlank(emailEntity.getEmails())){
						String[] emailsAry = emailEntity.getEmails().split(",");
						for(int i=0; i<emailsAry.length; i++){
							String email = emailsAry[i];
							formatPhoneList.add(StringUtil.formatPhone(email));
						}
					}
					emailEntity.setEmails(StringUtil.list2String(formatPhoneList));
				}
			}
			
			return emailEntity;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new EmailEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request, @ModelAttribute EmailEntity emailEntity) {
		try {
			// 手动输入或上传邮箱
			String emails = request.getParameter("emails");
			// 用户筛选邮箱
			String[] userScreenIds = request.getParameterValues("userScreenId");
			// 用户分组筛选邮箱
			String[] userGroupIds = request.getParameterValues("userGroupId");
			
			if(StringUtils.isNotBlank(emails)){
				logger.info("手动输入或上传邮箱");
				String[] emailAry = emails.split(",");
				if(emailAry.length > Constants.uploadSize){
					logger.error("一次上传最多上传50000个邮箱");
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.emailUploadSizeError));
					return resultCode;
				}
				// 待发送的邮箱
				List<String> emailsList = new ArrayList<String>();
				// 合法邮箱
				List<String> legalEmailsList = new ArrayList<String>();
				// 非法邮箱
				List<String> illegalEmailsList = new ArrayList<String>();
				for(String email: emailAry){
					emailsList.add(email);
					if(StringUtils.isNotBlank(email)){
						email = email.trim();
						if(EmailUtil.checkEmail(email)){
							if(!legalEmailsList.contains(email)){
								legalEmailsList.add(email);
							}else{
								logger.info("重复邮箱：" + email);
								illegalEmailsList.add(email);
							}
						}else{
							logger.info("邮箱非法：" + email);
							illegalEmailsList.add(email);
						}
					}else{
						logger.info("邮箱空值");
					}
				}
				if(legalEmailsList.size() <= 0){
					logger.error("不存在合法的邮箱");
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.emailPhoneError));
					return resultCode;
				}
				emailEntity.setSourceType(SourceTypeEnum.input.getLabelKey());
				emailEntity.setInputNum(Long.parseLong(emailsList.size() + ""));
				emailEntity.setLegalNum(Long.parseLong(legalEmailsList.size() + ""));
				emailEntity.setIllegalNum(Long.parseLong(illegalEmailsList.size() + ""));
				emailEntity.setEmails(StringUtil.list2String(emailsList));
				emailEntity.setLegalEmails(StringUtil.list2String(legalEmailsList));
				emailEntity.setIllegalEmails(StringUtil.list2String(illegalEmailsList));
			}else if(null != userScreenIds && userScreenIds.length > 0){
				logger.info("用户筛选邮箱");
				emailEntity.setSourceType(SourceTypeEnum.userScreen.getLabelKey());
				emailEntity.setScreenIds(StringUtil.array2String(userScreenIds));
			}else if(null != userGroupIds && userGroupIds.length > 0){
				logger.info("用户分组筛选邮箱");
				emailEntity.setSourceType(SourceTypeEnum.userGroup.getLabelKey());
				emailEntity.setGroupIds(StringUtil.array2String(userGroupIds));
			}else{
				logger.error("邮箱筛选条件设置错误");
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.emailSourceTypeError));
				return resultCode;
			}

			// 发送类型
			String sendType = emailEntity.getSendType();
			if(StringUtils.isNotBlank(sendType)){
				if(SendTypeEnum.sendNow.getLabelKey().equals(sendType)){
					// 即时发送邮件，只允许新增，不可修改
					if(null != emailEntity.getEmailId() && emailEntity.getEmailId() > 0){
						logger.error("即时发送邮件的记录，不可修改");
						ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
						resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.emailSendError_1));
						return resultCode;
					}else{
						emailService.saveOrUpdate(emailEntity);
					}
				}else if(SendTypeEnum.setTime.getLabelKey().equals(sendType)){
					// 获取定时时间
					String[] startDateList = request.getParameterValues("startDateList");
					String[] endDateList = request.getParameterValues("endDateList");
					String[] hourList = request.getParameterValues("hourList");
					String[] minuteList = request.getParameterValues("minuteList");
					emailService.saveOrUpdate(startDateList, endDateList, hourList, minuteList, emailEntity);
				}else{
					logger.error("邮件发送类型设置错误");
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.emailSendTypeError));
					return resultCode;
				}
			}else{
				logger.error("邮件发送类型设置错误");
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.emailSendTypeError));
				return resultCode;
			}
			
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
	public ResultCode deleteById(String emailIdArray) {
		try {
			emailService.deleteByIdArray(emailIdArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 邮件预览
	 */
	@RequestMapping("/preview/{emailId}")
	public String preview(HttpServletRequest request, @PathVariable Long emailId) {
		try {
			EmailEntity model = emailService.findById(emailId);
			request.setAttribute("title", model.getTitle());
			request.setAttribute("content", model.getContent());
		} catch (Exception e) {
			logger.error("预览邮件内容异常：" + e.getMessage(), e);
		}
		return "market/emailTemplate/preview";
	}

	/**
	 * 立即重发
	 */
	@RequestMapping(value = "/reSendEmail", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode reSendEmail(Long emailId) {
		try {
			emailService.reSendEmail(emailId);
			return new ResultCode(ResultCodeEnum.success);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 立即对失败重发
	 */
	@RequestMapping(value = "/reSendEmailByFailPhone", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode reSendEmailByFailPhone(Long emailId) {
		try {
			emailService.reSendEmailByFailPhone(emailId);
			return new ResultCode(ResultCodeEnum.success);
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
			@ModelAttribute EmailEntity emailEntity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.emailListInfo.getPath())));
			// 2、需要导出的数据
			List<EmailEntity> recordList = emailService.findList(emailEntity);
			
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<EmailEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, EmailEntity param) {
					if("sendType".equals(fieldName)){
						return SendTypeEnum.format(fieldValue+"");
					}else if("timeSwitch".equals(fieldName)){
						if("Y".equals(fieldValue+"")){
							return "开启";
						}else{
							return "关闭";
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.emailListInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}
	
}
