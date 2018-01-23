package com.gw.das.web.controller.market;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.context.Constants;
import com.gw.das.common.context.PersistContext;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.email.EmailUtil;
import com.gw.das.common.enums.AccountAnalyzeStatusEnum;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.utils.FileUtil;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gw.das.common.utils.SystemPathUtil;
import com.gw.das.common.utils.UUIDUtil;
import com.gw.das.dao.market.entity.AccountAnalyzeEntity;
import com.gw.das.dao.market.entity.EmailTemplateEntity;
import com.gw.das.service.base.EmailSendService;
import com.gw.das.service.market.AccountAnalyzeService;
import com.gw.das.service.market.EmailTemplateService;
import com.gw.das.service.market.PersistService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/AccountAnalyzeController")
public class AccountAnalyzeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AccountAnalyzeController.class);

	/**
	 * 固定邮件模板编号
	 */
	private static final String templateCode = "accountAnalyze";

	@Autowired
	private AccountAnalyzeService accountAnalyzeService;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	private EmailSendService emailSendService;
	@Autowired
	private PersistService persistService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("accountAnalyzeDomainName",
					SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeDomainName));
			request.setAttribute("sendStatusEnum", SendStatusEnum.getEmailStatusList());
			request.setAttribute("accountAnalyzeStatusEnum", AccountAnalyzeStatusEnum.getList());
			return "/market/accountAnalyze/accountAnalyze";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<AccountAnalyzeEntity> pageList(HttpServletRequest request,
			@ModelAttribute AccountAnalyzeEntity accountAnalyzeEntity) {
		try {
			PageGrid<AccountAnalyzeEntity> pageGrid = accountAnalyzeService
					.findPageList(super.createPageGrid(request, accountAnalyzeEntity));
			for (Object obj : pageGrid.getRows()) {
				AccountAnalyzeEntity record = (AccountAnalyzeEntity) obj;
				record.setGenerateStatus(AccountAnalyzeStatusEnum.format(record.getGenerateStatus()));
				record.setSendStatus(SendStatusEnum.format(record.getSendStatus()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<AccountAnalyzeEntity>();
		}
	}

	/**
	 * 根据id删除
	 */
	@RequestMapping(value = "/deleteById", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode deleteById(String batchIdArray) {
		try {
			accountAnalyzeService.deleteByIdArray(batchIdArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 回调解锁任务
	 * @param request
	 * @param key
	 * @return
	 */
    @RequestMapping(value = "/persistCallback", method = { RequestMethod.POST })
	@ResponseBody
    public ResultCode persistCallback(HttpServletRequest request, String persistId, String flag) {
        persistService.persistCallback(persistId, flag);
    	return new ResultCode(ResultCodeEnum.success);
    }

	/**
	 * 生成报告
	 */
	@RequestMapping(value = "/generateReport", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode generateReport(HttpServletRequest request, String batchIds) {
		try {
			// 校验模板
			EmailTemplateEntity template = emailTemplateService.findByCode(templateCode);
			if (null == template) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.emailTemplateNotExists));
				return resultCode;
			}
			threadPoolTaskExecutor.execute(new generateReportThread(accountAnalyzeService, template, batchIds,
					UserContext.get().getLoginName(), UserContext.get().getLoginIp()) {
			});
			return new ResultCode(ResultCodeEnum.success);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	private class generateReportThread implements Runnable {
		private AccountAnalyzeService accountAnalyzeService;
		private EmailTemplateEntity template;
		private String batchIds;
		private String loginName;
		private String loginIp;

		private generateReportThread(AccountAnalyzeService accountAnalyzeService, EmailTemplateEntity template,
				String batchIds, String loginName, String loginIp) {
			super();
			this.accountAnalyzeService = accountAnalyzeService;
			this.template = template;
			this.batchIds = batchIds;
			this.loginName = loginName;
			this.loginIp = loginIp;
		}

		@Override
		public void run() {
			// 依次生成报告
			try {
				// 获取顶层盘符
				String topDirectoryPath = SystemPathUtil.getTopDirectoryPath();
				String[] batchIdArray = batchIds.split(",");
				for (int i = 0; i < batchIdArray.length; i++) {
					String batchId = batchIdArray[i];
					if (StringUtils.isNotBlank(batchId)) {
						try{
							AccountAnalyzeEntity entity = accountAnalyzeService.findById(Long.parseLong(batchId));
							if (null != entity
									&& !AccountAnalyzeStatusEnum.Y.getLabelKey().equals(entity.getGenerateStatus())) {
								// 查询接口数据
								// 获取 账户概况信息
								Map accountOverviewMap = accountAnalyzeService.accountOverview(entity);
								// 收益分析
								Map accountAnalyzeMap = accountAnalyzeService.profitAnalysis(entity);
								if(null != accountOverviewMap && null != accountAnalyzeMap){
									accountOverviewMap.put("tradeprofitratio", accountAnalyzeMap.get("tradeprofitratio"));
									accountOverviewMap.put("avgprofitlossratio", accountAnalyzeMap.get("avgprofitlossratio"));
								}
								
								// 获取3笔最大亏损单
								Object accountTopLossList = accountAnalyzeService.topLoss(entity);
								// 获取3笔最大盈利单
								Object accountTopProfitLossList = accountAnalyzeService.topProfit(entity);
								
								// 生成文件名及路径
								String uuid = "";
								String tempPath = entity.getPath();
								if(StringUtils.isNotBlank(tempPath)){
									// 如果不为空时，说明之前生成过报告，则不重新生成文件名
									uuid = tempPath.substring(tempPath.lastIndexOf("/") + 1, tempPath.length() - 5);
								}else{
									uuid = UUIDUtil.getUUID();
								}
								String batchNo = entity.getBatchNo();
								String fileName = uuid + ".html";
								String path = Constants.accountAnalyzePath + "/" + batchNo + "/";
								String linkPath = Constants.accountAnalyzeLinkPath + "/" + entity.getBatchNo() + "/";
								
								// 调用PhantomJS在服务端生成图片
								PersistContext persistContext = persistService.persist(batchNo, uuid, entity);
								if("Y".equals(persistContext.getFlag())){
									// 操作成功再继续下一步
									// 使用framemark替换邮件内容
									Map<String, Object> args = new HashMap<String, Object>();
									args.put("fileName", uuid);
									args.put("batchNo", entity.getBatchNo());
									args.put("accountNo", entity.getAccountNo());
									args.put("platform", entity.getPlatform());
									args.put("startDate", entity.getStartDate());
									args.put("endDate", entity.getEndDate());
									args.put("accountOverviewMap", accountOverviewMap);
									args.put("accountTopLossList", accountTopLossList);
									args.put("accountTopProfitLossList", accountTopProfitLossList);
									String content = EmailUtil.formatContent(template.getContent(), args);
									// 保存文件
									FileUtil.saveFile(topDirectoryPath + path, fileName, content);
									
									// 更新生成状态、路径、访问路径等
									entity.setPath(topDirectoryPath + path + fileName);
									entity.setUrl(linkPath + fileName);
									accountAnalyzeService.updateGenerateStatus(entity, loginName, loginIp);
								}else{
									logger.info("批次号{}, 文件名{}生成报告失败!", batchId, uuid);
								}
							}
						}catch(Exception e){
							logger.error("批次号{}生成报告异常!", batchId, e);
						}
					}
				}
			} catch (Exception e) {
				logger.error("系统出现异常:" + e.getMessage(), e);
			}
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public AccountAnalyzeEntity findById(Long batchId) {
		try {
			StringBuffer sb = new StringBuffer();
			AccountAnalyzeEntity accountAnalyzeEntity = accountAnalyzeService.findById(batchId);
			if (null != accountAnalyzeEntity
					&& AccountAnalyzeStatusEnum.Y.getLabelKey().equals(accountAnalyzeEntity.getGenerateStatus())
					&& !SendStatusEnum.sendSuccess.getLabelKey().equals(accountAnalyzeEntity.getSendStatus())) {
				String path = accountAnalyzeEntity.getPath();
				if (StringUtils.isNotBlank(path)) {
					File file = new File(path);
					// 构造一个BufferedReader类来读取文件
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(file));
					String s = null;
					while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
						sb.append(s);
					}
				}
			}
			accountAnalyzeEntity.setContent(sb.toString());
			return accountAnalyzeEntity;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new AccountAnalyzeEntity();
		}
	}

	/**
	 * 保存报告
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request, Long batchId, String content) {
		try {
			AccountAnalyzeEntity accountAnalyzeEntity = accountAnalyzeService.findById(batchId);
			accountAnalyzeService.saveOrUpdate(accountAnalyzeEntity);
			FileUtil.saveFile(accountAnalyzeEntity.getPath(), content);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 发送邮件
	 */
	@RequestMapping(value = "/sendEmail", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode sendEmail(HttpServletRequest request, String batchIds) {
		try {
			// 校验模板
			EmailTemplateEntity template = emailTemplateService.findByCode(templateCode);
			if (null == template) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.emailTemplateNotExists));
				return resultCode;
			}
			emailSendService.asynThreading(batchIds, template);
			return new ResultCode(ResultCodeEnum.success);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		String tempPath = "/web/sdfsf/sdfsdf/asssdfsdfdsf.html";
		String uuid = tempPath.substring(tempPath.lastIndexOf("/") + 1, tempPath.length() - 5);
		logger.info(uuid);
	}
	
}
