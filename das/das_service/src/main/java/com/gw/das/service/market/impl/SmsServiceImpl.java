package com.gw.das.service.market.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.AccountTypeEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.enums.SendTypeEnum;
import com.gw.das.common.enums.SourceTypeEnum;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.SmsDao;
import com.gw.das.dao.market.SmsDetailDao;
import com.gw.das.dao.market.TimingDao;
import com.gw.das.dao.market.entity.SmsDetailEntity;
import com.gw.das.dao.market.entity.SmsEntity;
import com.gw.das.dao.market.entity.TimingEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.base.SmsSendService;
import com.gw.das.service.market.SmsConfigService;
import com.gw.das.service.market.SmsService;
import com.gw.das.service.market.TimingService;
import com.gw.das.service.market.UserGroupDetailService;
import com.gw.das.service.website.DasUserScreenService;

@Service
public class SmsServiceImpl extends BaseService implements SmsService {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Autowired
	private TimingDao timingDao;
	@Autowired
	private SmsDao smsDao;
	@Autowired
	private SmsDetailDao smsDetailDao;
	@Autowired
	private TimingService timingService;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private SmsConfigService smsConfigService;
	@Autowired
	private DasUserScreenService dasUserScreenService;
	@Autowired
	private UserGroupDetailService userGroupDetailService;
	
	@Override
	public SmsEntity findById(Long id) throws Exception {
		return (SmsEntity) smsDao.findById(id, SmsEntity.class);
	}
	
	@Override
	public void saveOrUpdate(String[] startDateList, String[] endDateList, String[] hourList, String[] minuteList, SmsEntity entity) throws Exception {
		this.saveOrUpdate(entity);
		timingService.save(startDateList, endDateList, hourList, minuteList, entity.getTimeSwitch(), "sms", entity.getSmsId());
	}

	@Override
	public void saveOrUpdate(SmsEntity entity) throws Exception {
		if (null == entity.getSmsId()) {
			// 是否立即发送短信
			if(SendTypeEnum.sendNow.getLabelKey().equals(entity.getSendType())){
				// 如果是手机号来源，如果是根据条件刷选，则调用接口获取手机号
				if(SourceTypeEnum.userScreen.getLabelKey().equals(entity.getSourceType())){
					List<String> tempList = new ArrayList<String>();
					Map<String, String> tempMap = new HashMap<String, String>();
					dasUserScreenService.getTelOrEmailList(entity.getScreenIds().split(","), AccountTypeEnum.phone, tempMap);
					for(Map.Entry<String, String> entry:tempMap.entrySet()){
						tempList.add(entry.getKey());
					}
					String temp = StringUtil.list2String(tempList);
					if(StringUtils.isNotBlank(temp)){
						entity.setLegalPhones(temp);
						entity.setInputNum(Long.parseLong(tempList.size()+""));
						entity.setLegalNum(Long.parseLong(tempList.size()+""));
					}else{
						logger.error("未找出满足用户筛选条件的手机号，本次发送短信退出。");
						throw new Exception("未找出满足用户筛选条件的手机号，本次发送短信退出。");
					}
				}else if(SourceTypeEnum.userGroup.getLabelKey().equals(entity.getSourceType())){
					List<String> tempList = new ArrayList<String>();
					// 根据groupId查询分组详细，获取手机号
					userGroupDetailService.getAccountList(entity.getGroupIds().split(","), AccountTypeEnum.phone, tempList);
					String temp = StringUtil.list2String(tempList);
					if(StringUtils.isNotBlank(temp)){
						entity.setLegalPhones(temp);
						entity.setInputNum(Long.parseLong(tempList.size()+""));
						entity.setLegalNum(Long.parseLong(tempList.size()+""));
					}else{
						logger.error("用户分组对应的手机号为空，本次发送短信退出。");
						throw new Exception("用户分组对应的手机号为空，本次发送短信退出。");
					}
				}
				smsDao.save(entity);
				this.sendSms(entity);
			}else{
				smsDao.save(entity);
			}
		} else {
			SmsEntity oldEntity = findById(entity.getSmsId());
			BeanUtils.copyProperties(entity, oldEntity);
			smsDao.update(oldEntity);
			smsDao.getCurrentSession().flush();
		}
	}
	
	/**
	 * 立即发送
	 * @param smsId
	 * @throws Exception
	 */
	private void sendSms(SmsEntity entity) throws Exception {
		if(null != entity){
			String smsChannel = smsConfigService.getSmsChannel(entity.getSmsSign());
			smsSendService.asynThreading(entity, smsChannel);
		}
	}

	/**
	 * 立即重发
	 * @param smsId
	 * @throws Exception
	 */
	@Override
	public void reSendSms(Long smsId) throws Exception {
		SmsEntity entity = this.findById(smsId);
		if(null != entity){
			// 更新发送时间
			this.saveOrUpdate(entity);
			// 立即发送
			this.sendSms(entity);
		}
	}

	/**
	 * 立即对失败重发
	 * @param smsId
	 * @throws Exception
	 */
	@Override
	public void reSendSmsByFailPhone(Long smsId) throws Exception {
		// 找出失败的手机号码
		List<String> failList = new ArrayList<String>();
		List<String> successList = new ArrayList<String>();
		
		SmsEntity entity = this.findById(smsId);
		if(null != entity){
			SmsDetailEntity smsDetailEntity = new SmsDetailEntity();
			smsDetailEntity.setSmsId(smsId);
			List<SmsDetailEntity> list = smsDetailDao.findList(smsDetailEntity);
			for(SmsDetailEntity record : list){
				String phone = StringUtils.isNotBlank(record.getPhone()) ? record.getPhone().replace("null", "") : "";
				if(SendStatusEnum.sendFail.getLabelKey().equals(record.getSendStatus())){
					if(!failList.contains(phone)){
						failList.add(phone);
					}
				}else{
					if(!successList.contains(phone)){
						successList.add(phone);
					}
				}
			}

			// 虽然有失败记录，但需剔除有已经成功发送的手机号
			for(int i=0; i<failList.size(); i++){
				String failPhone = failList.get(i);
				if(successList.contains(failPhone)){
					failList.remove(i);
				}
			}

			if(null == failList || failList.size() <= 0){
				logger.error("对发送失败的手机号进行重发条数为0，不需发送，本次退出");
				throw new Exception("对发送失败的手机号进行重发条数为0，不需发送，本次退出");
			}

			// 更新发送时间
			this.saveOrUpdate(entity);
			// 立即发送
			entity.setFailPhonesFlag(true);
			entity.setFailPhones(StringUtil.list2String(failList));
			this.sendSms(entity);
		}
	}
	
	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		String[] ids = idArray.split(",");
		if(null != ids && ids.length > 0){
			// 删除定时发送数据
			// 删除短信详细数据
			for(String smsId: ids){
				TimingEntity timingEntity = new TimingEntity();
				timingEntity.setSmsId(Long.parseLong(smsId));
				timingDao.deleteByCon(timingEntity);
				
				smsDetailDao.deleteByCon(Long.parseLong(smsId));
			}
			// 删除短信
			smsDao.deleteAllByIdArray(ids, SmsEntity.class);
		}
	}

	@Override
	public List<SmsEntity> findList(SmsEntity smsEntity) throws Exception {
		return smsDao.findList(smsEntity);
	}

	@Override
	public PageGrid<SmsEntity> findPageList(PageGrid<SmsEntity> pageGrid) throws Exception {
		return smsDao.findPageList(pageGrid);
	}

	/**
	 * 根据定时时间发送短信
	 */
	@Override
	public void sendByTiming(Date curDate) throws Exception {
		List<String> smsIdList = new ArrayList<String>();
		List<TimingEntity> timingList = timingDao.findList(curDate, AccountTypeEnum.phone);
		if(null != timingList && timingList.size()>=0){
			logger.debug("查询短信定时发送记录数：" + timingList.size());
			for(TimingEntity timing: timingList){
				Long smsId = timing.getSmsId();
				if(null != smsId && !smsIdList.contains(smsId+"")){
					smsIdList.add(smsId+"");
					logger.info("开始发送短信数据，smsId = " + smsId);
					try{
						SmsEntity entity = findById(smsId);
						
						// 如果是手机号来源，如果是根据条件刷选，则调用接口获取手机号
						if(SourceTypeEnum.userScreen.getLabelKey().equals(entity.getSourceType())){
							List<String> tempList = new ArrayList<String>();
							Map<String, String> tempMap = new HashMap<String, String>();
							dasUserScreenService.getTelOrEmailList(entity.getScreenIds().split(","), AccountTypeEnum.phone, tempMap);
							for(Map.Entry<String, String> entry:tempMap.entrySet()){
								tempList.add(entry.getKey());
							}
							String temp = StringUtil.list2String(tempList);
							if(StringUtils.isNotBlank(temp)){
								entity.setLegalPhones(temp);
								if(null != entity.getInputNum()){
									entity.setInputNum(entity.getInputNum() + Long.parseLong(tempList.size()+""));
								}else{
									entity.setInputNum(Long.parseLong(tempList.size()+""));
								}
								if(null != entity.getInputNum()){
									entity.setLegalNum(entity.getLegalNum() + Long.parseLong(tempList.size()+""));
								}else{
									entity.setLegalNum(Long.parseLong(tempList.size()+""));
								}
								smsDao.update(entity);
							}else{
								logger.error("未找出满足用户筛选条件的手机号，本次发送短信退出。");
								continue;
							}
						}else if(SourceTypeEnum.userGroup.getLabelKey().equals(entity.getSourceType())){
							List<String> tempList = new ArrayList<String>();
							// 根据groupId查询分组详细，获取手机号
							userGroupDetailService.getAccountList(entity.getGroupIds().split(","), AccountTypeEnum.phone, tempList);
							String temp = StringUtil.list2String(tempList);
							if(StringUtils.isNotBlank(temp)){
								entity.setLegalPhones(temp);
								if(null != entity.getInputNum()){
									entity.setInputNum(entity.getInputNum() + Long.parseLong(tempList.size()+""));
								}else{
									entity.setInputNum(Long.parseLong(tempList.size()+""));
								}
								if(null != entity.getInputNum()){
									entity.setLegalNum(entity.getLegalNum() + Long.parseLong(tempList.size()+""));
								}else{
									entity.setLegalNum(Long.parseLong(tempList.size()+""));
								}
								smsDao.update(entity);
							}else{
								logger.error("用户分组对应的手机号为空，本次发送短信退出。");
								continue;
							}
						}
						
						this.sendSms(entity);
					}catch(Exception e){
						logger.error("开始发送短信数据，smsId = " + smsId + "，出现异常" + e.getMessage(), e);
					}
				}
			}
		}else{
			logger.debug("查询短信定时发送记录数为0");
		}
	}

}