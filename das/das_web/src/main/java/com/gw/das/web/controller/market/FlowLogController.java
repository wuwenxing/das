package com.gw.das.web.controller.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.FlowChannelEnum;
import com.gw.das.common.enums.FlowPackageEnum;
import com.gw.das.common.enums.RedisKeyEnum;
import com.gw.das.common.enums.ResCodeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.response.UtmFlowRequest;
import com.gw.das.common.response.UtmResult;
import com.gw.das.common.sms.SmsUtil;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.HttpHelper;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SignUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.FlowConfigEntity;
import com.gw.das.dao.market.entity.FlowLogEntity;
import com.gw.das.service.base.FlowService;
import com.gw.das.service.market.FlowConfigService;
import com.gw.das.service.market.FlowLogService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/FlowLogController")
public class FlowLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FlowLogController.class);

	@Autowired
	private FlowConfigService flowConfigService;
	@Autowired
	private FlowLogService flowLogService;
	@Autowired
	private FlowService flowService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("flowPackageEnum", FlowPackageEnum.getList());
			request.setAttribute("statusCode", ResCodeEnum.getList("flow"));
			return "/market/flowLog/flowLog";
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
	public PageGrid<FlowLogEntity> pageList(HttpServletRequest request, @ModelAttribute FlowLogEntity flowLogEntity) {
		try {
			PageGrid<FlowLogEntity> pageGrid = flowLogService
					.findPageList(super.createPageGrid(request, flowLogEntity));
			for (Object obj : pageGrid.getRows()) {
				FlowLogEntity record = (FlowLogEntity) obj;
				record.setStatusCode(ResCodeEnum.format(record.getStatusCode()));
				// 参数中手机号使用“*”号替换
				String param = record.getParam();
				if (StringUtils.isNotBlank(param)) {
					UtmFlowRequest utmFlowRequest = JacksonUtil.readValue(param, UtmFlowRequest.class);
					if(null != utmFlowRequest){
						String phones = utmFlowRequest.getPhones();
						String tempPhones = "";
						if (StringUtils.isNotBlank(phones)) {
							String[] phonesAry = phones.split(",");
							for (int i = 0; i < phonesAry.length; i++) {
								String phone = phonesAry[i];
								if (i + 1 == phonesAry.length) {
									tempPhones += StringUtil.formatPhone(phone);
								} else {
									tempPhones += StringUtil.formatPhone(phone) + ",";
								}
							}
						}
						utmFlowRequest.setPhones(tempPhones);
						record.setParam(JacksonUtil.toJSon(utmFlowRequest));
					}
				}
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<FlowLogEntity>();
		}
	}

	/**
	 * 手工充值流量
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request) {
		try {
			// 参数定义
			ResultCode resultCode = null;
			Long companyId = UserContext.get().getCompanyId();
			String phones = request.getParameter("phones");
			String flowSize = request.getParameter("flowSize");
			String postStr = "{\"timestamp\":\"\",\"accountSid\":\"\",\"sign\":\"\",\"phones\":\""+phones+"\", \"flowSize\":\""+flowSize+"\"}";
			
			// 3、手机号码空值校验-校验及验证
			if (StringUtils.isBlank(phones)) {
				logger.error("手机号为空值");
				resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ResCodeEnum.PhoneIsNull.getLabelKey(), ResCodeEnum.PhoneIsNull.getValue()));
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.PhoneIsNull);
				return resultCode;
			}

			// 4、手机号码格式校验及统计-校验及验证
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
			if (legalMobiles.size() <= 0) {
				logger.error("不存在合法的手机号");
				resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ResCodeEnum.NoValidPhone.getLabelKey(), ResCodeEnum.NoValidPhone.getValue()));
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.NoValidPhone);
				return resultCode;
			}

			// 5、验证是否存在对应的类型
			if (null == FlowPackageEnum.check(flowSize)) {
				resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ResCodeEnum.flowSizeError.getLabelKey(), ResCodeEnum.flowSizeError.getValue()));
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.flowSizeError);
				return resultCode;
			}

			// 6、验证是否设置流量充值通道
			FlowConfigEntity flowConfig = flowConfigService.findByCompanyId();
			if (null == flowConfig) {
				logger.error("utm未设置流量充值通道");
				resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ResCodeEnum.flowChannelIsNull.getLabelKey(), ResCodeEnum.flowChannelIsNull.getValue()));
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.flowChannelIsNull);
				return resultCode;
			}
			
			// 7、成功-保存提交请求日志
			FlowLogEntity reqLog = new FlowLogEntity();
			reqLog.setParam(postStr);
			reqLog.setPhones(phones);
			reqLog.setFlowSize(flowSize);
			reqLog.setStatusCode(ResCodeEnum.Success.getLabelKey());
			reqLog.setInputNum(Long.parseLong(phoneAry.length + ""));
			reqLog.setLegalNum(Long.parseLong(legalMobiles.size() + ""));
			reqLog.setIllegalNum(Long.parseLong(illegalMobiles.size() + ""));
			reqLog.setLegalPhones(StringUtil.list2String(legalMobiles));
			reqLog.setIllegalPhones(StringUtil.list2String(illegalMobiles));
			flowLogService.saveOrUpdate(reqLog);

			// 8、调用异步线程进行流量充值
			flowService.asynThreading(reqLog, flowConfig.getFlowChannel());

			// 9、执行完毕，返回成功消息
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 调用流量充值接口 根据开发者账号区分不同业务平台
	 */
	@RequestMapping(value = "/send", method = { RequestMethod.POST })
	@ResponseBody
	public UtmResult send(HttpServletRequest request) {
		return sendHandler(request);
	}

	/**
	 * 流量充值统一处理
	 */
	private UtmResult sendHandler(HttpServletRequest request) {
		UtmResult utmResult = null;
		String postStr = "";
		// 参数定义
		Long companyId = null;
		String accountSid = "";
		String timestamp = "";
		String sign = "";
		String phones = "";
		String flowSize = "";
		try {
			// 1.获取post过来的数据
			accountSid = request.getParameter("accountSid");
			timestamp = request.getParameter("timestamp");
			sign = request.getParameter("sign");
			phones = request.getParameter("phones");
			flowSize = request.getParameter("flowSize");

			// accountSid = "fa573c78eaa8402cb6c84dabfcce7158";
			// timestamp = "20161008140800";
			// sign = "5ed770bc2418fa0081fdb4538deec4a3";
			// phones = "13760291376,13760291376,eeeeee,54545454";
			// flowSize = "100";

			UtmFlowRequest utmFlowRequest = new UtmFlowRequest();
			utmFlowRequest.setTimestamp(timestamp);
			utmFlowRequest.setAccountSid(accountSid);
			utmFlowRequest.setSign(sign);
			utmFlowRequest.setPhones(phones);
			utmFlowRequest.setFlowSize(flowSize);
			postStr = JacksonUtil.toJSon(utmFlowRequest);

			// 1、开发者账号校验-验证
			if (StringUtils.isBlank(accountSid)) {
				logger.error("开发者账号为空");
				utmResult = new UtmResult(ResCodeEnum.AccountSidIsNull);
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.AccountSidIsNull);
				return utmResult;
			} else {
				if (SignUtil.FX_SID.equals(accountSid)) {
					companyId = 1L;
				} else if (SignUtil.PM_SID.equals(accountSid)) {
					companyId = 2L;
				} else if (SignUtil.HX_SID.equals(accountSid)) {
					companyId = 3L;
				} else if (SignUtil.CF_SID.equals(accountSid)) {
					companyId = 4L;
				} else {
					logger.error("开发者账号错误");
					utmResult = new UtmResult(ResCodeEnum.AccountSidError);
					this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.AccountSidError);
					return utmResult;
				}
			}

			// 2、签名校验-验证
			if (StringUtils.isBlank(timestamp)) {
				logger.error("请求时间戳为空");
				utmResult = new UtmResult(ResCodeEnum.TimestampIsNull);
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.TimestampIsNull);
				return utmResult;
			}
			// 超过有效期校验,超过5分钟失效
//			int minutes = -5;
//			Date requestTime = DateUtil.stringToDate(timestamp, "yyyyMMddHHmmss");
//			Date nowDate = DateUtil.addMinutes(new Date(), minutes);
//			if (nowDate.after(requestTime)) {
//				logger.error("超过有效期");
//				utmResult = new UtmResult(ResCodeEnum.MoreThanValid);
//				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.MoreThanValid);
//				return utmResult;
//			}
			if (StringUtils.isBlank(sign) || !SignUtil.validate(timestamp, sign, accountSid)) {
				logger.error("签名校验错误");
				utmResult = new UtmResult(ResCodeEnum.SignError);
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.SignError);
				return utmResult;
			}

			// 3、手机号码空值校验-校验及验证
			if (StringUtils.isBlank(phones)) {
				logger.error("手机号为空值");
				utmResult = new UtmResult(ResCodeEnum.PhoneIsNull);
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.PhoneIsNull);
				return utmResult;
			}

			// 4、手机号码格式校验及统计-校验及验证
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
			if (legalMobiles.size() <= 0) {
				logger.error("不存在合法的手机号");
				utmResult = new UtmResult(ResCodeEnum.NoValidPhone);
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.NoValidPhone);
				return utmResult;
			}

			// 5、验证是否存在对应的类型
			if (null == FlowPackageEnum.check(flowSize)) {
				logger.error("充值的流量大小有误，不存在");
				utmResult = new UtmResult(ResCodeEnum.flowSizeError);
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.flowSizeError);
				return utmResult;
			}

			// 6、验证是否设置流量充值通道
			FlowConfigEntity flowConfig = flowConfigService.findByCompanyId();
			if (null == flowConfig) {
				logger.error("utm未设置流量充值通道");
				utmResult = new UtmResult(ResCodeEnum.flowChannelIsNull);
				this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.flowChannelIsNull);
				return utmResult;
			}

			// 7、成功-保存提交请求日志
			FlowLogEntity reqLog = new FlowLogEntity();
			reqLog.setParam(postStr);
			reqLog.setPhones(phones);
			reqLog.setFlowSize(flowSize);
			reqLog.setStatusCode(ResCodeEnum.Success.getLabelKey());
			reqLog.setInputNum(Long.parseLong(phoneAry.length + ""));
			reqLog.setLegalNum(Long.parseLong(legalMobiles.size() + ""));
			reqLog.setIllegalNum(Long.parseLong(illegalMobiles.size() + ""));
			reqLog.setLegalPhones(StringUtil.list2String(legalMobiles));
			reqLog.setIllegalPhones(StringUtil.list2String(illegalMobiles));
			flowLogService.saveOrUpdate(reqLog);

			// 8、调用异步线程进行流量充值
			flowService.asynThreading(reqLog, flowConfig.getFlowChannel());

			// 9、执行完毕，返回成功消息
			utmResult = new UtmResult(ResCodeEnum.Success);
			
			// 目前只有容联通道，充值联通手机号码充值30M时，真正充值30M，其他通道都是20M
			if(!FlowChannelEnum.rl.getLabelKey().equals(flowConfig.getFlowChannel())){
				if(FlowPackageEnum._30M.getLabelKey().equals(flowSize)){
					utmResult.setResult(FlowPackageEnum._20M.getLabelKey());
				}else{
					utmResult.setResult(flowSize);
				}
			}else{
				utmResult.setResult(flowSize);
			}
			return utmResult;
		} catch (Exception e) {
			logger.error("流量充值[" + postStr + "]出现异常" + e.getMessage(), e);
			utmResult = new UtmResult(ResCodeEnum.Exception);
			this.saveReqLog(postStr, phones, flowSize, companyId, ResCodeEnum.Exception);
			return utmResult;
		}
	}

	/**
	 * 验证失败时，保存请求日志处理
	 * 
	 * @param postStr
	 * @param phones
	 * @param companyId
	 * @param errorCodeEnum
	 */
	private void saveReqLog(String postStr, String phones, String flowSize, Long companyId, ResCodeEnum resCodeEnum) {
		try {
			FlowLogEntity reqLog = new FlowLogEntity();
			reqLog.setParam(postStr);
			reqLog.setPhones(phones);
			reqLog.setFlowSize(flowSize);
			reqLog.setStatusCode(resCodeEnum.getLabelKey());
			flowLogService.saveOrUpdate(reqLog);
		} catch (Exception e) {
			logger.error("保存提交请求日志error" + e.getMessage(), e);
		}
	}

	/**
	 * 同步流量充值状态-ym流量充值回执推送
	 */
	@RequestMapping("/syncFlowStatusByYm")
	@ResponseBody
	public String syncFlowStatusByYm(HttpServletRequest request) {
		String data = "";
		try {
			// 1.获取传输过来的数据
			data = request.getParameter("data");
			// data ="{\"batchNo\":\"11\",\"successCount\":1,\"failCount\":2,\"errorlist\":[{\"mobile\":\"13760291376\",\"code\":\"N0002\",\"message\":\"运营商异常\"},{\"mobile\":\"15563736372\",\"code\":\"N0003\",\"message\":\"运营商异常\"},{\"mobile\":\"15563736373\",\"code\":\"N0002\",\"message\":\"运营商异常\"}]}";
			logger.info("ymFlow-data=" + data);
			// 2、放入队列，等待系统线程，定时读取队列并入库
			if (StringUtils.isNotBlank(data)) {
				ListOperations<String, Object> list = redisTemplate.opsForList();
				list.leftPush(RedisKeyEnum.ymFlowStatusData.getLabelKey(), data);
			} else {
				logger.error("ymFlow-数据为空!");
			}
			// 3、执行完毕，返回
			return "{\"SUCCESS\":\"ok\"}";
		} catch (Exception e) {
			logger.error("ymFlow-同步流量充值状态信息[" + data + "]出现异常" + e.getMessage(), e);
			return "{\"SUCCESS\":\"ERROR\"}";
		}
	}

	/**
	 * 同步流量充值状态-lm流量充值回执推送
	 */
	@RequestMapping("/syncFlowStatusByLm")
	@ResponseBody
	public Map<String, String> syncFlowStatusByLm(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<String, String>();
		String data = "";
		try {
			// 1.获取post过来的数据
			data = HttpHelper.getRequestPostStr(request);
			// data = "[{\"mobile\":\"18576615293\",\"msgid\":\"1703011537534086\",\"time\":\"2017-03-02 15:33:10\",\"status\":\"00000\"},{\"mobile\":\"15510331875\",\"msgid\":\"1501081533080113\",\"time\":\"2015-01-08 15:33:09\",\"status\":\"00000\"}]";
			logger.info("lmFlow-data=" + data);
			// 2、放入队列，等待系统线程，定时读取队列并入库
			if (StringUtils.isNotBlank(data)) {
				ListOperations<String, Object> list = redisTemplate.opsForList();
				list.leftPush(RedisKeyEnum.lmFlowStatusData.getLabelKey(), data);
			} else {
				logger.error("lmFlow-数据为空!");
			}
			// 3、执行完毕，返回
			retMap.put("respCode", "SUCCESS");
		} catch (Exception e) {
			logger.error("lmFlow-同步流量充值状态信息[" + data + "]出现异常" + e.getMessage(), e);
			retMap.put("respCode", "error:" + e.getMessage());
		}
		return retMap;
	}

	/**
	 * 同步流量充值状态-rl流量充值回执推送
	 */
	@RequestMapping("/syncFlowStatusByRl")
	@ResponseBody
	public String syncFlowStatusByRl(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String data = "";
		try {
			// 1.获取post过来的数据
			data = HttpHelper.getRequestPostStr(request);
			/** data为xml格式：
				<?xml version="1.0" encoding="UTF-8"?>
				<Request>
					<appId>11112222333344445555666677778000</appId>
					<rechargeId>09c62d8f028b11e5a1610050568e55bd</rechargeId>
					<phoneNum>13800138000</phoneNum>
					<status>3</status>
					<msg>充值成功</msg>
					<customId>695136f5028d11e5a1610050568e55bd</customId>
				</Request>
			*/
			logger.info("rlFlow-data=" + data);
			// 2、放入队列，等待系统线程，定时读取队列并入库
			if (StringUtils.isNotBlank(data)) {
				ListOperations<String, Object> list = redisTemplate.opsForList();
				list.leftPush(RedisKeyEnum.rlFlowStatusData.getLabelKey(), data);
			} else {
				logger.error("rlFlow-数据为空!");
			}
			// 3、执行完毕，返回
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<Response>");
			sb.append("<Result>SUCC</Result>");
			sb.append("</Response>");
		} catch (Exception e) {
			logger.error("rlFlow-同步流量充值状态信息[" + data + "]出现异常" + e.getMessage(), e);
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<Response>");
			sb.append("<Result>同步流量充值状态出现异常:"+e.getMessage()+"</Result>");
			sb.append("</Response>");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Date requestTime = DateUtil.stringToDate("20170421154500", "yyyyMMddHHmmss");
		Date nowDate = DateUtil.addMinutes(new Date(), -5);
		if (nowDate.after(requestTime)) {
			logger.info("超过有效期");
		}else{
			logger.info("没有超过有效期");
		}
	}
	
}
