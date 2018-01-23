package com.gw.das.service.website.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.AccountTypeEnum;
import com.gw.das.common.enums.AdvisoryEnum;
import com.gw.das.common.enums.BehaviorTypeEnum;
import com.gw.das.common.enums.BlacklistTypeEnum;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.enums.GenderEnum;
import com.gw.das.common.enums.PlanTimeTypeEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.enums.SilenceEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.BlacklistDao;
import com.gw.das.dao.market.ChannelDao;
import com.gw.das.dao.market.DasUserScreenDao;
import com.gw.das.dao.market.entity.BlacklistEntity;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.dao.market.entity.DasUserScreenEntity;
import com.gw.das.dao.market.entity.TagEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.website.DasUserScreenService;

@Service
public class DasUserScreenServiceImpl extends BaseService implements DasUserScreenService {

	private static final Logger logger = LoggerFactory.getLogger(DasUserScreenServiceImpl.class);
	
	@Autowired
	private DasUserScreenDao dasUserScreenDao;
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private BlacklistDao blacklistDao;

	@Override
	public DasUserScreenEntity findById(Long id) throws Exception {
		return (DasUserScreenEntity) dasUserScreenDao.findById(id, DasUserScreenEntity.class);
	}

	@Override
	public void saveOrUpdate(DasUserScreenEntity entity) throws Exception {
		if (null == entity.getScreenId()) {
			dasUserScreenDao.save(entity);
		} else {
			DasUserScreenEntity oldEntity = findById(entity.getScreenId());
			
			oldEntity.setBehaviorType(entity.getBehaviorType());
			oldEntity.setPlanTimeType(entity.getPlanTimeType());
			oldEntity.setStartTime(entity.getStartTime());
			oldEntity.setEndTime(entity.getEndTime());
			oldEntity.setSilence(entity.getSilence());
			oldEntity.setStartSilenceDays(entity.getStartSilenceDays());
			oldEntity.setSilenceDays(entity.getSilenceDays());
			
			oldEntity.setGender(entity.getGender());
			oldEntity.setChannelIds(entity.getChannelIds());
			oldEntity.setTagIds(entity.getTagIds());
			oldEntity.setAreas(entity.getAreas());
			oldEntity.setClient(entity.getClient());
			oldEntity.setBrowsers(entity.getBrowsers());

			oldEntity.setVisitClient(entity.getVisitClient());
			oldEntity.setVisitCountGt(entity.getVisitCountGt());
			oldEntity.setVisitCountLt(entity.getVisitCountLt());
			oldEntity.setAdvisory(entity.getAdvisory());
			oldEntity.setAdvisoryCountGt(entity.getAdvisoryCountGt());
			oldEntity.setAdvisoryCountLt(entity.getAdvisoryCountLt());
			oldEntity.setSmsSendStatus(entity.getSmsSendStatus());
			oldEntity.setSmsSendCountGt(entity.getSmsSendCountGt());
			oldEntity.setSmsSendCountLt(entity.getSmsSendCountLt());

			oldEntity.setIsDemo(entity.getIsDemo());
			oldEntity.setIsReal(entity.getIsReal());
			oldEntity.setIsDepesit(entity.getIsDepesit());
			oldEntity.setIsBacklist(entity.getIsBacklist());
			oldEntity.setIsFreeAngry(entity.getIsFreeAngry());
			
			oldEntity.setResultSmsCount(entity.getResultSmsCount());
			oldEntity.setResultEmailCount(entity.getResultEmailCount());
			
			dasUserScreenDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		dasUserScreenDao.deleteAllByIdArray(idArray.split(","), DasUserScreenEntity.class);
	}

	@Override
	public List<DasUserScreenEntity> findList(DasUserScreenEntity dasUserScreenEntity) throws Exception {
		return dasUserScreenDao.findList(dasUserScreenEntity);
	}

	@Override
	public PageGrid<DasUserScreenEntity> findPageList(PageGrid<DasUserScreenEntity> pageGrid) throws Exception {
		return dasUserScreenDao.findPageList(pageGrid);
	}
	
	/**
	 * 根据条件筛选统计[手机号或者邮箱]集合-调用netty接口
	 * @param screenIdAry 筛选条件ID集合
	 * @param accountTypeEnum 统计类型
	 */
	public void getTelOrEmailList(String[] screenIdAry, AccountTypeEnum accountTypeEnum, Map<String, String> map) throws Exception{
		List<String> screenIdList = new ArrayList<String>();
		for(int i = 0; i < screenIdAry.length; i++){
			String screenId = screenIdAry[i];
			if(StringUtils.isNotBlank(screenId)){
				screenIdList.add(screenIdAry[i]);
			}
		}
		this.getTelOrEmailList(screenIdList, accountTypeEnum, map);
	}
	
	/**
	 * 根据条件筛选统计[手机号或者邮箱]集合-调用netty接口
	 * @param screenIdList 筛选条件ID集合
	 * @param accountTypeEnum 统计类型
	 */
	public void getTelOrEmailList(List<String> screenIdList, AccountTypeEnum accountTypeEnum, Map<String, String> map) throws Exception{
		if(AccountTypeEnum.phone.getLabelKey().equals(accountTypeEnum.getLabelKey())){
			this.getTelAndEmailList(screenIdList, map, new HashMap<String, String>());
		}else if(AccountTypeEnum.email.getLabelKey().equals(accountTypeEnum.getLabelKey())){
			this.getTelAndEmailList(screenIdList, new HashMap<String, String>(), map);
		}
	}
	
	/**
	 * 根据条件筛选统计[手机号与邮箱]集合-调用netty接口
	 * @param screenIdList 筛选条件ID集合
	 */
	public void getTelAndEmailList(String[] screenIdAry, Map<String, String> telMap, Map<String, String> emailMap) throws Exception {
		List<String> screenIdList = new ArrayList<String>();
		for(int i = 0; i < screenIdAry.length; i++){
			String screenId = screenIdAry[i];
			if(StringUtils.isNotBlank(screenId)){
				screenIdList.add(screenIdAry[i]);
			}
		}
		this.getTelAndEmailList(screenIdList, telMap, emailMap);
	}
	
	/**
	 * 根据条件筛选统计[手机号与邮箱]集合-调用netty接口
	 * @param screenIdList 筛选条件ID集合
	 */
	public void getTelAndEmailList(List<String> screenIdList, Map<String, String> telMap, Map<String, String> emailMap) throws Exception {
		for(int i = 0; i < screenIdList.size(); i++){
			String screenId = screenIdList.get(i);
			//获取筛选列表中的条件 
			DasUserScreenEntity model = (DasUserScreenEntity)dasUserScreenDao.findById(Long.parseLong(screenId), DasUserScreenEntity.class);
			this.getTelAndEmailList(model, telMap, emailMap);
		}
		logger.info("-->>符合条件的所有手机号个数：" + telMap.size());
		logger.info("-->>符合条件的所有邮箱个数：" + emailMap.size());
	}
	
	/**
	 * 根据条件筛选统计[手机号与邮箱]集合-调用netty接口
	 */
	public void getTelAndEmailList(DasUserScreenEntity model, Map<String, String> telMap, Map<String, String> emailMap) throws Exception {
		//获取手机号码及邮箱
		if(model != null){
			Map<String, List<String>> map = this.getDataByUserScreen(model);
			//获取手机号码
			List<String> telsList = map.get("tels");
			if(null != telsList && telsList.size() >= 0){
				logger.info("本次用户筛选手机号总记录数：" + telsList.size());
				for(String tel: telsList){
					// 手机号
					if(StringUtils.isNotBlank(tel)){
						if (tel.length() == 15) {// 86--开头
							tel = tel.substring(4);
						} else if (tel.length() == 14) {// 86-开头
							tel = tel.substring(3);
						}
						telMap.put(tel, tel);
					}else{
						logger.info("-->>手机号码为空字符串");
					}
				}
				logger.info("本次去重后手机号总记录数：" + telMap.size());
			}else{
				logger.info("本次用户筛选手机号总记录数为0");
			}
			//获取邮箱
			List<String> emailsList = map.get("emails");
			if(null != emailsList && emailsList.size() >= 0){
				logger.info("本次用户筛选邮箱总记录数：" + emailsList.size());
				for(String email: emailsList){
					// 邮箱
					if(StringUtils.isNotBlank(email)){
						emailMap.put(email, email);
					}else{
						logger.info("-->>邮箱为空字符串");
					}
				}
				logger.info("本次去重后邮箱总记录数：" + emailMap.size());
			}else{
				logger.info("本次用户筛选邮箱总记录数为0");
			}
		}
	}
	
	/**
	 * 根据用户筛选条件查询-调用netty接口
	 */
	private Map<String, List<String>> getDataByUserScreen(DasUserScreenEntity userScreen) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		if (null != userScreen) {
			//设置查询条件
			String behaviorType = userScreen.getBehaviorType();
			if(StringUtils.isNotBlank(behaviorType)){
				if(BehaviorTypeEnum.demo.getLabelKey().equals(behaviorType)){
					parameters.put("behaviorType", "3");
				}else if(BehaviorTypeEnum.real.getLabelKey().equals(behaviorType)){
					parameters.put("behaviorType", "4");
				}else if(BehaviorTypeEnum.depesit.getLabelKey().equals(behaviorType)){
					parameters.put("behaviorType", "5");
				}
			}
			// 性别
			if(StringUtils.isNotBlank(userScreen.getGender())){
				if(GenderEnum.male.getLabelKey().equals(userScreen.getGender())){
					parameters.put("gender", "0");
				}else if(GenderEnum.female.getLabelKey().equals(userScreen.getGender())){
					parameters.put("gender", "1");
				}
			}
			// 开户客户端类型
			if(StringUtils.isNotBlank(userScreen.getClient())){
				if(ClientEnum.pc.getLabelKey().equals(userScreen.getClient())){
					parameters.put("platformType", "0");
				}else if(ClientEnum.mobile.getLabelKey().equals(userScreen.getClient())){
					parameters.put("platformType", "1");
				}
			}
			// 开户使用的浏览器
			if(StringUtils.isNotBlank(userScreen.getBrowsers())){
				parameters.put("browser", userScreen.getBrowsers());
			}
			// 地域
			if(StringUtils.isNotBlank(userScreen.getAreas())){
				parameters.put("area", userScreen.getAreas());
			}
			// 计划时间类型与时间条件-（计算得出过滤时间）
			Date startTime = null;
			Date endTime = null;
			if(StringUtils.isNotBlank(userScreen.getPlanTimeType())){
				if(PlanTimeTypeEnum.dayBefore.getLabelKey().equals(userScreen.getPlanTimeType())){
					startTime = DateUtil.getCurrentDateBeforeStartTime();
					endTime = DateUtil.getCurrentDateBeforeEndTime();
				}else if(PlanTimeTypeEnum.day.getLabelKey().equals(userScreen.getPlanTimeType())){
					startTime = DateUtil.getCurrentDateStartTime();
					endTime = DateUtil.getCurrentDateEndTime();
				}else if(PlanTimeTypeEnum.month.getLabelKey().equals(userScreen.getPlanTimeType())){
					startTime = DateUtil.getCurrentMonthStartTime();
					endTime = DateUtil.getCurrentMonthEndTime();
				}else if(PlanTimeTypeEnum.year.getLabelKey().equals(userScreen.getPlanTimeType())){
					startTime = DateUtil.getCurrentYearStartTime();
					endTime = DateUtil.getCurrentYearEndTime();
				}
			}else{
				startTime = DateUtil.getStartTimeByDate(userScreen.getStartTime());
				endTime = DateUtil.getEndTimeByDate(userScreen.getEndTime());
			}
			// 沉默无行为条件-（计算得出过滤时间）
			if(StringUtils.isNotBlank(userScreen.getSilenceDays())
					|| StringUtils.isNotBlank(userScreen.getStartSilenceDays())){ // 设置的天数不为空
				boolean temp = false; // 标示是否选择沉默无行为条件
				if(BehaviorTypeEnum.demo.getLabelKey().equals(behaviorType)){// 针对D类：模拟账户
					if(SilenceEnum.notRealAccount.getLabelKey().equals(userScreen.getSilence())){
						// 在？天内没开真实账户
						// 当没有勾选排重时，需要设置条件：未开立真实账户-即设置排除真实账户
						if(!"Y".equals(userScreen.getIsReal())){
							parameters.put("isReal", "Y");
						}
						temp = true;
					}else if(SilenceEnum.noOperation.getLabelKey().equals(userScreen.getSilence())){
						// 在？天内无任何操作（模拟交易、开真实账户）
						// 当没有勾选排重时，需要设置条件：未开立真实账户-即设置排除真实账户
						if(!"Y".equals(userScreen.getIsReal())){
							parameters.put("isReal", "Y");
						}
						// 无模拟交易
						temp = true;
					}
				}else if(BehaviorTypeEnum.real.getLabelKey().equals(behaviorType)){// 针对N类：真实账户
					if(SilenceEnum.noDeposit.getLabelKey().equals(userScreen.getSilence())){
						// 在?天内未激活（即未入金）
						// 当没有勾选排重时，需要设置条件：未入金-即设置排除入金账户
						if(!"Y".equals(userScreen.getIsDepesit())){
							parameters.put("isDepesit", "Y");
						}
						temp = true;
					}
				}else if(BehaviorTypeEnum.depesit.getLabelKey().equals(behaviorType)){// 针对A类：首次入金
					if(SilenceEnum.untraded_1.getLabelKey().equals(userScreen.getSilence())){
						// ?天内没有交易（且历史没有任何交易）
						temp = true;
					}else if(SilenceEnum.untraded_2.getLabelKey().equals(userScreen.getSilence())){
						// ?天内没有交易（且历史有交易）
						temp = true;
					}else if(SilenceEnum.untraded_3.getLabelKey().equals(userScreen.getSilence())){
						// ?天内没有交易（且历史有交易，整体赔钱的）
						temp = true;
					}else if(SilenceEnum.untraded_4.getLabelKey().equals(userScreen.getSilence())){
						// ?天内没有交易（且历史有交易，整体盈利的）
						temp = true;
					}
				}
				if(temp){
					if(StringUtils.isNotBlank(userScreen.getStartSilenceDays())){
						// 根据当天时间，计算？天内的第一天时间
						Date startDateTemp = DateUtil.getCurrentDateStartTimeByDay(Integer.parseInt(userScreen.getStartSilenceDays()));
						// 与endTime时间进行比较，取小值
						if(startDateTemp.getTime() < endTime.getTime()){
							endTime = startDateTemp;
						}
					}

					if(StringUtils.isNotBlank(userScreen.getSilenceDays())){
						// 根据当天时间，计算？天内的第一天时间
						Date dateTemp = DateUtil.getCurrentDateStartTimeByDay(Integer.parseInt(userScreen.getSilenceDays()));
						// 与startTime时间进行比较，取大值
						if(startTime.getTime() < dateTemp.getTime()){
							startTime = dateTemp;
						}
					}
				}
			}
			// 设置已计算的时间条件
			parameters.put("startTime", DateUtil.formatDateToString(startTime, "yyyy-MM-dd HH:mm:ss"));
			parameters.put("endTime", DateUtil.formatDateToString(endTime, "yyyy-MM-dd HH:mm:ss"));
			// 渠道条件
			String channelIds = userScreen.getChannelIds();
			if(StringUtils.isNotBlank(channelIds)){
				List<Map<String, String>> channelMapList = new ArrayList<Map<String, String>>();
				String[] channelAry = channelIds.split(",");
				for(int i = 0; i < channelAry.length; i++){
					String channelId = channelAry[i];
					if(StringUtils.isNotBlank(channelId)){
						//（备注：20170216，改为存渠道名称，根据名称查询对应的渠道id）
						List<ChannelEntity> list = channelDao.findListByName(ChannelTypeEnum.webSite, channelId);
						for(int j=0; j<list.size(); j++){
							ChannelEntity channel = list.get(j);
							Map<String, String> channelMap = new HashMap<String, String>();
							channelMap.put("utmcsr", channel.getUtmcsr());
							channelMap.put("utmcmd", channel.getUtmcmd());
							channelMapList.add(channelMap);
						}
					}
				}
				parameters.put("channel", JacksonUtil.toJSon(channelMapList));
			}
			// 排重条件
			if("Y".equals(userScreen.getIsDemo())){
				parameters.put("isDemo", userScreen.getIsDemo());
			}
			if("Y".equals(userScreen.getIsReal())){
				parameters.put("isReal", userScreen.getIsReal());
			}
			if("Y".equals(userScreen.getIsDepesit())){
				parameters.put("isDepesit", userScreen.getIsDepesit());
			}
			// 排重条件-黑名单及免恼
			if("Y".equals(userScreen.getIsBacklist()) || "Y".equals(userScreen.getIsFreeAngry())){
				BlacklistEntity blacklist = new BlacklistEntity();
				if("Y".equals(userScreen.getIsBacklist()) && !"Y".equals(userScreen.getIsFreeAngry())){
					// 只查黑名单类型
					blacklist.setBlacklistType(BlacklistTypeEnum.blacklist.getLabelKey());
				}else if(!"Y".equals(userScreen.getIsBacklist()) && "Y".equals(userScreen.getIsFreeAngry())){
					// 只查免恼类型
					blacklist.setBlacklistType(BlacklistTypeEnum.freeAngry.getLabelKey());
				}else{
					// 不设置类型，则查全部类型
					blacklist.setBlacklistType(null);
				}
				// 只查手机号类型
				blacklist.setAccountType(AccountTypeEnum.phone.getLabelKey());
				// 业务平台
				blacklist.setCompanyId(userScreen.getCompanyId());
				List<BlacklistEntity> blacklistList = blacklistDao.findList(blacklist);
				if(null != blacklistList && blacklistList.size() >= 0){
					logger.info("-->>黑名单需过滤的总记录：" + blacklistList.size());
					List<String> phoneList = new ArrayList<String>();
					for (BlacklistEntity bl : blacklistList) {
						phoneList.add(bl.getAccount());
					}
					parameters.put("disPhones", StringUtil.list2String(phoneList));
				}else{
					logger.info("-->>黑名单需过滤的总记录为0");
				}
			}
			// 标签条件
			String tagIds = userScreen.getTagIds();
			if(StringUtils.isNotBlank(tagIds)){
				List<String> list = new ArrayList<String>();
				String[] tagAry = tagIds.split(",");
				for(int i = 0; i < tagAry.length; i++){
					String tagId = tagAry[i];
					if(StringUtils.isNotBlank(tagId)){
						TagEntity tag = (TagEntity)channelDao.findById(Long.parseLong(tagId+""), TagEntity.class);
						list.add(tag.getTagUrl());
					}
				}
				parameters.put("url", StringUtil.list2String(list));
			}
			// 访问客户端类型、访问次数条件
			if(ClientEnum.pc.getLabelKey().equals(userScreen.getVisitClient())){
				parameters.put("visitClient", "0");
			}else if(ClientEnum.mobile.getLabelKey().equals(userScreen.getVisitClient())){
				parameters.put("visitClient", "1");
			}
			if(null != userScreen.getVisitCountGt()){
				parameters.put("visitCountGt", userScreen.getVisitCountGt()+"");
			}else{
				parameters.put("visitCountGt", "");
			}
			if(null != userScreen.getVisitCountLt()){
				parameters.put("visitCountLt", userScreen.getVisitCountLt()+"");
			}else{
				parameters.put("visitCountLt", "");
			}
			// 咨询类型、咨询次数条件
			if(AdvisoryEnum.QQ.getLabelKey().equals(userScreen.getAdvisory())){
				parameters.put("advisory", "1");
			}else if(AdvisoryEnum.Live800.getLabelKey().equals(userScreen.getAdvisory())){
				parameters.put("advisory", "2");
			}
			if(null != userScreen.getAdvisoryCountGt()){
				parameters.put("advisoryCountGt", userScreen.getAdvisoryCountGt()+"");
			}else{
				parameters.put("advisoryCountGt", "");
			}
			if(null != userScreen.getAdvisoryCountLt()){
				parameters.put("advisoryCountLt", userScreen.getAdvisoryCountLt()+"");
			}else{
				parameters.put("advisoryCountLt", "");
			}
			// 下载客户端类型、下载次数条件
			if(ClientEnum.pc.getLabelKey().equals(userScreen.getDownloadClient())){
				parameters.put("downloadClient", "0");
			}else if(ClientEnum.mobile.getLabelKey().equals(userScreen.getDownloadClient())){
				parameters.put("downloadClient", "1");
			}
			if(null != userScreen.getDownloadCountGt()){
				parameters.put("downloadCountGt", userScreen.getDownloadCountGt()+"");
			}else{
				parameters.put("downloadCountGt", "");
			}
			if(null != userScreen.getDownloadCountLt()){
				parameters.put("downloadCountLt", userScreen.getDownloadCountLt()+"");
			}else{
				parameters.put("downloadCountLt", "");
			}
			// 短信发送行为、发送次数条件
			if(SendStatusEnum.sendFail.getLabelKey().equals(userScreen.getSmsSendStatus())){
				parameters.put("smsSendStatus", "2");
			}else if(SendStatusEnum.sendSuccess.getLabelKey().equals(userScreen.getSmsSendStatus())){
				parameters.put("smsSendStatus", "0");
			}else if(SendStatusEnum.sendRecSuccess.getLabelKey().equals(userScreen.getSmsSendStatus())){
				parameters.put("smsSendStatus", "1");
			}
			if(null != userScreen.getSmsSendCountGt()){
				parameters.put("smsSendCountGt", userScreen.getSmsSendCountGt()+"");
			}else{
				parameters.put("smsSendCountGt", "");
			}
			if(null != userScreen.getSmsSendCountLt()){
				parameters.put("smsSendCountLt", userScreen.getSmsSendCountLt()+"");
			}else{
				parameters.put("smsSendCountLt", "");
			}
			// 邮件开信行为次数条件
			if(null != userScreen.getEmailSendCountGt()){
				parameters.put("emailSendCountGt", userScreen.getEmailSendCountGt()+"");
			}else{
				parameters.put("emailSendCountGt", "");
			}
			if(null != userScreen.getEmailSendCountLt()){
				parameters.put("emailSendCountLt", userScreen.getEmailSendCountLt()+"");
			}else{
				parameters.put("emailSendCountLt", "");
			}
			// 业务平台
			if(userScreen.getCompanyId() != null){
				parameters.put("businessPlatform", UserContext.get().getCompanyId()+"");
			}
		}
		RpcResult rpcResult = RpcUtils.post(Constants.dasUserInfoScreen, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String tels = resultMap.get("tels");
		String emails = resultMap.get("emails");
		List<String> telsList = JacksonUtil.readValue(tels, new TypeReference<List<String>>(){});
		List<String> emailsList = JacksonUtil.readValue(emails, new TypeReference<List<String>>(){});
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("tels", telsList);
		map.put("emails", emailsList);
		return map;
	}
	
}