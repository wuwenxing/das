package com.gw.das.web.controller.market;

import java.io.File;
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
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ErrorCodeEnum;
import com.gw.das.common.enums.ExportTemplateEnum;
import com.gw.das.common.enums.RedisKeyEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.enums.SendTypeEnum;
import com.gw.das.common.enums.SmsSignEnum;
import com.gw.das.common.enums.SourceTypeEnum;
import com.gw.das.common.excel.POIFormatConfig;
import com.gw.das.common.excel.POIXSSFExcelBuilder;
import com.gw.das.common.response.ErrorCode;
import com.gw.das.common.response.ResultCode;
import com.gw.das.common.sms.SmsUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.HttpHelper;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.DasUserScreenEntity;
import com.gw.das.dao.market.entity.SmsEntity;
import com.gw.das.dao.market.entity.TimingEntity;
import com.gw.das.dao.market.entity.UserGroupEntity;
import com.gw.das.service.market.SmsService;
import com.gw.das.service.market.TimingService;
import com.gw.das.service.market.UserGroupService;
import com.gw.das.service.website.DasUserScreenService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/SmsController")
public class SmsController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

	@Autowired
	private SmsService smsService;
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
			Long companyId = UserContext.get().getCompanyId();
			request.setAttribute("smsSignList", SmsSignEnum.getList(companyId));
			request.setAttribute("sendTypeEnum", SendTypeEnum.getList());
			request.setAttribute("sourceTypeEnum", SourceTypeEnum.getList());

			List<UserGroupEntity> userGroupList = userGroupService.findList(null);
			List<DasUserScreenEntity> dasUserScreenList = dasUserScreenService.findList(null);
			request.setAttribute("userGroupList", userGroupList);
			request.setAttribute("dasUserScreenList", dasUserScreenList);
			return "/market/sms/sms";
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
	public PageGrid<SmsEntity> pageList(HttpServletRequest request, @ModelAttribute SmsEntity smsEntity) {
		try {
			PageGrid<SmsEntity> pageGrid = smsService.findPageList(super.createPageGrid(request, smsEntity));
			for (Object obj : pageGrid.getRows()) {
				SmsEntity record = (SmsEntity) obj;
				record.setSendType(SendTypeEnum.format(record.getSendType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SmsEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public SmsEntity findById(Long smsId) {
		try {
			SmsEntity smsEntity = smsService.findById(smsId);
			if (null != smsEntity) {
				if (SendTypeEnum.setTime.getLabelKey().equals(smsEntity.getSendType())) {
					// 如果是定时发送短信，则还需查询定时的时间数据
					TimingEntity timingEntity = new TimingEntity();
					timingEntity.setSmsId(smsEntity.getSmsId());
					List<TimingEntity> timingList = timingService.findList(timingEntity);
					smsEntity.setTimingList(timingList);
				} else if (SendTypeEnum.sendNow.getLabelKey().equals(smsEntity.getSendType())) {
					// 如果是即时发送短信，短信手机号需屏蔽，否则暂时不屏蔽，因为可以修改
					// 手动和上传的手机号，短信手机号需屏蔽
					List<String> formatPhoneList = new ArrayList<String>();
					if (null != smsEntity && StringUtils.isNotBlank(smsEntity.getPhones())) {
						String[] phonesAry = smsEntity.getPhones().split(",");
						for (int i = 0; i < phonesAry.length; i++) {
							String phone = phonesAry[i];
							formatPhoneList.add(StringUtil.formatPhone(phone));
						}
					}
					smsEntity.setPhones(StringUtil.list2String(formatPhoneList));
				}
			}
			return smsEntity;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SmsEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request, @ModelAttribute SmsEntity smsEntity) {
		try {
			// 对编辑框中的短信内容处理，去除签名，去除退订回T
			String content = smsEntity.getContent();
			if (StringUtils.isNotBlank(content)) {
				if (content.startsWith("【") || content.indexOf("退订") != -1) {
					logger.error("短信内容中不需要加签名及退订回T，请检查");
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.smsContentError));
					return resultCode;
				}
			}

			// 手动输入或上传手机号
			String phones = request.getParameter("phones");
			// 用户筛选手机号
			String[] userScreenIds = request.getParameterValues("userScreenId");
			// 用户分组筛选手机号
			String[] userGroupIds = request.getParameterValues("userGroupId");

			if (StringUtils.isNotBlank(phones)) {
				logger.info("手动输入或上传手机号");
				String[] phoneAry = phones.split(",");
				if (phoneAry.length > Constants.uploadSize) {
					logger.error("一次上传最多上传50000个手机号码");
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.smsUploadSizeError));
					return resultCode;
				}
				// 待发送的手机号
				List<String> phonesList = new ArrayList<String>();
				// 合法手机号码
				List<String> legalPhonesList = new ArrayList<String>();
				// 非法手机号
				List<String> illegalPhonesList = new ArrayList<String>();
				for (String phone : phoneAry) {
					phonesList.add(phone);
					if (StringUtils.isNotBlank(phone)) {
						phone = phone.trim();
						if (SmsUtil.checkMobileNum(phone)) {
							if (!legalPhonesList.contains(phone)) {
								legalPhonesList.add(phone);
							} else {
								logger.info("重复手机号：" + phone);
								illegalPhonesList.add(phone);
							}
						} else {
							logger.info("手机号非法：" + phone);
							illegalPhonesList.add(phone);
						}
					} else {
						logger.info("手机号空值");
					}
				}
				if (legalPhonesList.size() <= 0) {
					logger.error("不存在合法的手机号");
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.smsPhoneError));
					return resultCode;
				}
				smsEntity.setSourceType(SourceTypeEnum.input.getLabelKey());
				smsEntity.setInputNum(Long.parseLong(phonesList.size() + ""));
				smsEntity.setLegalNum(Long.parseLong(legalPhonesList.size() + ""));
				smsEntity.setIllegalNum(Long.parseLong(illegalPhonesList.size() + ""));
				smsEntity.setPhones(StringUtil.list2String(phonesList));
				smsEntity.setLegalPhones(StringUtil.list2String(legalPhonesList));
				smsEntity.setIllegalPhones(StringUtil.list2String(illegalPhonesList));
			} else if (null != userScreenIds && userScreenIds.length > 0) {
				logger.info("用户筛选手机号");
				smsEntity.setSourceType(SourceTypeEnum.userScreen.getLabelKey());
				smsEntity.setScreenIds(StringUtil.array2String(userScreenIds));
			} else if (null != userGroupIds && userGroupIds.length > 0) {
				logger.info("用户分组筛选手机号");
				smsEntity.setSourceType(SourceTypeEnum.userGroup.getLabelKey());
				smsEntity.setGroupIds(StringUtil.array2String(userGroupIds));
			} else {
				logger.error("手机号筛选条件设置错误");
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.smsSourceTypeError));
				return resultCode;
			}

			// 发送类型
			String sendType = smsEntity.getSendType();
			if (StringUtils.isNotBlank(sendType)) {
				if (SendTypeEnum.sendNow.getLabelKey().equals(sendType)) {
					// 即时发送短信，只允许新增，不可修改
					if (null != smsEntity.getSmsId() && smsEntity.getSmsId() > 0) {
						logger.error("即时发送短信的记录，不可修改");
						ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
						resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.smsSendError_1));
						return resultCode;
					} else {
						smsService.saveOrUpdate(smsEntity);
					}
				} else if (SendTypeEnum.setTime.getLabelKey().equals(sendType)) {
					// 获取定时时间
					String[] startDateList = request.getParameterValues("startDateList");
					String[] endDateList = request.getParameterValues("endDateList");
					String[] hourList = request.getParameterValues("hourList");
					String[] minuteList = request.getParameterValues("minuteList");
					smsService.saveOrUpdate(startDateList, endDateList, hourList, minuteList, smsEntity);
				} else {
					logger.error("短信发送类型设置错误");
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.smsSendTypeError));
					return resultCode;
				}
			} else {
				logger.error("短信发送类型设置错误");
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.smsSendTypeError));
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
	public ResultCode deleteById(String smsIdArray) {
		try {
			smsService.deleteByIdArray(smsIdArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 立即重发
	 */
	@RequestMapping(value = "/reSendSms", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode reSendSms(Long smsId) {
		try {
			smsService.reSendSms(smsId);
			return new ResultCode(ResultCodeEnum.success);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 立即对失败重发
	 */
	@RequestMapping(value = "/reSendSmsByFailPhone", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode reSendSmsByFailPhone(Long smsId) {
		try {
			smsService.reSendSmsByFailPhone(smsId);
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
			@ModelAttribute SmsEntity smsEntity) {
		try {
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					new File(request.getServletContext().getRealPath(ExportTemplateEnum.smsListInfo.getPath())));
			// 2、需要导出的数据
			List<SmsEntity> recordList = smsService.findList(smsEntity);

			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<SmsEntity>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, SmsEntity param) {
					if ("sendType".equals(fieldName)) {
						return SendTypeEnum.format(fieldValue + "");
					} else if ("timeSwitch".equals(fieldName)) {
						if ("Y".equals(fieldValue + "")) {
							return "开启";
						} else {
							return "关闭";
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(ExportTemplateEnum.smsListInfo.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 同步状态-发财鱼使用 短信回执推送
	 */
	@RequestMapping("/syncSmsStatusByFcy")
	@ResponseBody
	public Map<String, String> syncSmsStatusByFcy(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<String, String>();
		String data = "";
		try {
			// 1.获取post过来的数据
			data = HttpHelper.getRequestPostStr(request);
			logger.info("fcy data=" + data);
			// 2、放入队列，等待系统线程，定时读取队列并入库
			if (StringUtils.isNotBlank(data)) {
				// 发财鱼可不同步
				// ListOperations<String, Object> list = redisTemplate.opsForList();
				// list.leftPush(RedisKeyEnum.fcySmsStatusData.getLabelKey(), data);
			} else {
				logger.error("fcy数据为空!");
			}
			// 3、执行完毕，返回
			retMap.put("respCode", "ok");
		} catch (Exception e) {
			logger.error("fcy-同步状态信息[" + data + "]出现异常" + e.getMessage(), e);
			retMap.put("respCode", "error:" + e.getMessage());
		}
		return retMap;
	}

	/**
	 * 同步状态-秒嘀科技使用 短信回执推送
	 */
	@RequestMapping("/syncSmsStatusByMdkj")
	@ResponseBody
	public Map<String, String> syncSmsStatusByMdkj(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<String, String>();
		String data = "";
		try {
			// 1.获取post过来的数据
			data = HttpHelper.getRequestPostStr(request);
			logger.info("mdkj data=" + data);
//			data = "{\"smsResult\":[" + "{\"smsId\":\"cca80f0ae70c4104a04ed598aae45290\",\"phone\":\"13760291376\","
//					+ "\"status\":\"0\",\"respMessage\":\"\",\"receiveTime\":\"2016-06-29 19:23:26\",\"chargingNum\":\"2\"},"
//					+ "{\"smsId\":\"cca80f0ae70c4104a04ed598aae45290\",\"phone\":\"18576615293\","
//					+ "\"status\":\"0\",\"respMessage\":\"\",\"receiveTime\":\"2016-06-29 19:23:26\",\"chargingNum\":\"2\"},"
//					+ "{\"smsId\":\"19db1a643ea3475c96adcd736d97c2f9\",\"phone\":\"13760291376\","
//					+ "\"status\":\"0\",\"respMessage\":\"\",\"receiveTime\":\"2016-06-29 19:23:26\",\"chargingNum\":\"2\"},"
//					+ "{\"smsId\":\"19db1a643ea3475c96adcd736d97c2f9\",\"phone\":\"15237112625\","
//					+ "\"status\":\"0\",\"respMessage\":\"\",\"receiveTime\":\"2016-06-29 19:23:26\",\"chargingNum\":\"2\"}"
//					+ "]}";
			// 2、放入队列，等待系统线程，定时读取队列并入库
			if (StringUtils.isNotBlank(data)) {
				ListOperations<String, Object> list = redisTemplate.opsForList();
				list.leftPush(RedisKeyEnum.mdkjSmsStatusData.getLabelKey(), data);
			} else {
				logger.error("mdkj数据为空!");
			}
			// 3、执行完毕，返回
			retMap.put("respCode", "ok");
		} catch (Exception e) {
			logger.error("mdkj-同步状态信息[" + data + "]出现异常" + e.getMessage(), e);
			retMap.put("respCode", "error:" + e.getMessage());
		}
		return retMap;
	}

	/**
	 * 同步状态-至臻互联使用 短信回执推送
	 */
	@RequestMapping("/syncSmsStatusByZzhl")
	@ResponseBody
	public Map<String, String> syncSmsStatusByZzhl(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<String, String>();
		String data = "";
		try {
			// 1.获取传输过来的数据
			// 采用自定义消息格式。体积小，信息量大；
			// 状态报告和上行消息不会在同一个包中出现，每个包中最多不会超过250条消息；
			// 每条消息之间分隔符为英文分号“;” 每条消息内分隔符为英文逗号“,”；
			// Post传出参数名dxstr。
			data = request.getParameter("dxstr");
			logger.info("zzhl data=" + data);
			// 参数顺序 说明 备注
			// 第一个 消息类型 0为上行消息2为状态报告
			// 第二个 手机号 手机号码
			// 第三个 上行消息时为扩展号码,状态报告时为消息状态
			// 上行消息时内容为Subid（返回帐号固定扩展+用户自扩展）状态报告内容0为成功其他失败
			// 第四个 上行消息时为内容,状态报告时为消息的包ID
			// 上行内容经过GBK编码，需客户解码例如：URLDecoder.decode(Content, "GBK")
			// 第五个 接收时间 时间格式：yyyyMMddHHmmss14位串小时采用24小时制
//			data = "2,13760291376,0,F165245140224152846,20140428172543;"
//					+ "2,18576615293,0,F165246140224152846,20140428172543;";
			// 2、放入队列，等待系统线程，定时读取队列并入库
			if (StringUtils.isNotBlank(data)) {
				ListOperations<String, Object> list = redisTemplate.opsForList();
				list.leftPush(RedisKeyEnum.zzhlSmsStatusData.getLabelKey(), data);
			} else {
				logger.error("zzhl数据为空!");
			}
			// 3、执行完毕，返回
			retMap.put("respCode", "ok");
		} catch (Exception e) {
			logger.error("zzhl-同步状态信息[" + data + "]出现异常" + e.getMessage(), e);
			retMap.put("respCode", "error:" + e.getMessage());
		}
		return retMap;
	}

}
