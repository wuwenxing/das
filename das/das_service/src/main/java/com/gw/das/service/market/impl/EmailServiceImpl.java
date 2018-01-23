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
import com.gw.das.dao.market.EmailDao;
import com.gw.das.dao.market.EmailDetailDao;
import com.gw.das.dao.market.TimingDao;
import com.gw.das.dao.market.entity.EmailDetailEntity;
import com.gw.das.dao.market.entity.EmailEntity;
import com.gw.das.dao.market.entity.TimingEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.base.EmailSendService;
import com.gw.das.service.market.EmailService;
import com.gw.das.service.market.TimingService;
import com.gw.das.service.market.UserGroupDetailService;
import com.gw.das.service.website.DasUserScreenService;

@Service
public class EmailServiceImpl extends BaseService implements EmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private TimingDao timingDao;
	@Autowired
	private EmailDao emailDao;
	@Autowired
	private EmailDetailDao emailDetailDao;
	@Autowired
	private TimingService timingService;
	@Autowired
	private EmailSendService emailSendService;
	@Autowired
	private DasUserScreenService dasUserScreenService;
	@Autowired
	private UserGroupDetailService userGroupDetailService;
	
	@Override
	public EmailEntity findById(Long id) throws Exception {
		return (EmailEntity) emailDao.findById(id, EmailEntity.class);
	}
	
	@Override
	public void saveOrUpdate(String[] startDateList, String[] endDateList, String[] hourList, String[] minuteList, EmailEntity entity) throws Exception {
		this.saveOrUpdate(entity);
		timingService.save(startDateList, endDateList, hourList, minuteList, entity.getTimeSwitch(), "email", entity.getEmailId());
	}

	@Override
	public void saveOrUpdate(EmailEntity entity) throws Exception {
		if (null == entity.getEmailId()) {
			// 是否立即发送邮件
			if(SendTypeEnum.sendNow.getLabelKey().equals(entity.getSendType())){
				// 如果是邮箱来源，如果是根据条件刷选，则调用接口获取邮箱
				if(SourceTypeEnum.userScreen.getLabelKey().equals(entity.getSourceType())){
					List<String> tempList = new ArrayList<String>();
					Map<String, String> tempMap = new HashMap<String, String>();
					dasUserScreenService.getTelOrEmailList(entity.getScreenIds().split(","), AccountTypeEnum.email, tempMap);
					for(Map.Entry<String, String> entry:tempMap.entrySet()){
						tempList.add(entry.getKey());
					}
					String temp = StringUtil.list2String(tempList);
					if(StringUtils.isNotBlank(temp)){
						entity.setLegalEmails(temp);
						entity.setInputNum(Long.parseLong(tempList.size()+""));
						entity.setLegalNum(Long.parseLong(tempList.size()+""));
					}else{
						logger.error("未找出满足用户筛选条件的邮箱，本次发送邮件退出。");
						throw new Exception("未找出满足用户筛选条件的邮箱，本次发送邮件退出。");
					}
				}else if(SourceTypeEnum.userGroup.getLabelKey().equals(entity.getSourceType())){
					List<String> tempList = new ArrayList<String>();
					// 根据groupId查询分组详细，获取邮箱
					userGroupDetailService.getAccountList(entity.getGroupIds().split(","), AccountTypeEnum.email, tempList);
					String temp = StringUtil.list2String(tempList);
					if(StringUtils.isNotBlank(temp)){
						entity.setLegalEmails(temp);
						entity.setInputNum(Long.parseLong(tempList.size()+""));
						entity.setLegalNum(Long.parseLong(tempList.size()+""));
					}else{
						logger.error("用户分组对应的邮箱为空，本次发送邮件退出。");
						throw new Exception("用户分组对应的邮箱为空，本次发送邮件退出。");
					}
				}
				emailDao.save(entity);
				this.sendEmail(entity);
			}else{
				emailDao.save(entity);
			}
		} else {
			EmailEntity oldEntity = findById(entity.getEmailId());
			BeanUtils.copyProperties(entity, oldEntity);
			emailDao.update(oldEntity);
			emailDao.getCurrentSession().flush();
		}
	}
	
	/**
	 * 立即发送
	 * @param emailId
	 * @throws Exception
	 */
	private void sendEmail(EmailEntity entity) throws Exception {
		if(null != entity){
			// 是否立即发送邮件
			emailSendService.asynThreading(entity);
		}
	}

	/**
	 * 立即重发
	 * @param emailId
	 * @throws Exception
	 */
	@Override
	public void reSendEmail(Long emailId) throws Exception {
		EmailEntity entity = this.findById(emailId);
		if(null != entity){
			// 更新发送时间
			this.saveOrUpdate(entity);
			// 立即发送邮件
			this.sendEmail(entity);
		}
	}

	/**
	 * 立即对失败重发
	 * @param emailId
	 * @throws Exception
	 */
	@Override
	public void reSendEmailByFailPhone(Long emailId) throws Exception {
		// 找出失败的邮箱
		List<String> failList = new ArrayList<String>();
		List<String> successList = new ArrayList<String>();
		
		EmailEntity entity = this.findById(emailId);
		if(null != entity){
			EmailDetailEntity emailDetailEntity = new EmailDetailEntity();
			emailDetailEntity.setEmailId(emailId);
			List<EmailDetailEntity> list = emailDetailDao.findList(emailDetailEntity);
			for(EmailDetailEntity record : list){
				String phone = StringUtils.isNotBlank(record.getRecEmail()) ? record.getRecEmail().replace("null", "") : "";
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

			// 虽然有失败记录，但需剔除有已经成功发送的邮箱
			for(int i=0; i<failList.size(); i++){
				String failPhone = failList.get(i);
				if(successList.contains(failPhone)){
					failList.remove(i);
				}
			}

			if(null == failList || failList.size() <= 0){
				logger.error("对发送失败的邮箱进行重发条数为0，不需发送，本次退出");
				throw new Exception("对发送失败的邮箱进行重发条数为0，不需发送，本次退出");
			}

			// 更新发送时间
			this.saveOrUpdate(entity);

			// 立即发送
			entity.setFailEmailsFlag(true);
			entity.setFailEmails(StringUtil.list2String(failList));
			this.sendEmail(entity);
		}
	}
	
	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		String[] ids = idArray.split(",");
		if(null != ids && ids.length > 0){
			// 删除定时发送数据
			// 删除邮件详细数据
			for(String emailId: ids){
				TimingEntity timingEntity = new TimingEntity();
				timingEntity.setEmailId(Long.parseLong(emailId));
				timingDao.deleteByCon(timingEntity);
				
				emailDetailDao.deleteByCon(Long.parseLong(emailId));
			}
			// 删除邮件
			emailDao.deleteAllByIdArray(ids, EmailEntity.class);
		}
	}

	@Override
	public List<EmailEntity> findList(EmailEntity emailEntity) throws Exception {
		return emailDao.findList(emailEntity);
	}

	@Override
	public PageGrid<EmailEntity> findPageList(PageGrid<EmailEntity> pageGrid) throws Exception {
		return emailDao.findPageList(pageGrid);
	}
	
	/**
	 * 根据定时时间发送邮件
	 */
	@Override
	public void sendByTiming(Date curDate) throws Exception {
		List<String> smsIdList = new ArrayList<String>();
		List<TimingEntity> timingList = timingDao.findList(curDate, AccountTypeEnum.email);
		if(null != timingList && timingList.size()>=0){
			logger.debug("查询邮件定时发送记录数：" + timingList.size());
			for(TimingEntity timing: timingList){
				Long emailId = timing.getEmailId();
				if(null != emailId && !smsIdList.contains(emailId+"")){
					smsIdList.add(emailId+"");
					logger.info("开始发送邮件数据，emailId = " + emailId);
					try{
						EmailEntity entity = findById(emailId);
						
						// 如果是邮箱来源，如果是根据条件刷选，则调用接口获取邮箱
						if(SourceTypeEnum.userScreen.getLabelKey().equals(entity.getSourceType())){
							List<String> tempList = new ArrayList<String>();
							Map<String, String> tempMap = new HashMap<String, String>();
							dasUserScreenService.getTelOrEmailList(entity.getScreenIds().split(","), AccountTypeEnum.email, tempMap);
							for(Map.Entry<String, String> entry:tempMap.entrySet()){
								tempList.add(entry.getKey());
							}
							String temp = StringUtil.list2String(tempList);
							if(StringUtils.isNotBlank(temp)){
								entity.setLegalEmails(temp);
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
								emailDao.update(entity);
							}else{
								logger.error("未找出满足用户筛选条件的邮箱，本次发送邮件退出。");
								continue;
							}
						}else if(SourceTypeEnum.userGroup.getLabelKey().equals(entity.getSourceType())){
							List<String> tempList = new ArrayList<String>();
							// 根据groupId查询分组详细，获取邮箱
							userGroupDetailService.getAccountList(entity.getGroupIds().split(","), AccountTypeEnum.email, tempList);
							String temp = StringUtil.list2String(tempList);
							if(StringUtils.isNotBlank(temp)){
								entity.setLegalEmails(temp);
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
								emailDao.update(entity);
							}else{
								logger.error("用户分组对应的邮箱为空，本次发送邮件退出。");
								continue;
							}
						}
						
						this.sendEmail(entity);
					}catch(Exception e){
						logger.error("开始发送邮件数据，emailId = " + emailId + "，出现异常" + e.getMessage(), e);
					}
				}
			}
		}else{
			logger.debug("查询邮件定时发送记录数为0");
		}
	}
}