package com.gw.das.service.market.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.SmsTemplateDetailDao;
import com.gw.das.dao.market.entity.SmsTemplateDetailEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.SmsTemplateDetailService;

@Service
public class SmsTemplateDetailServiceImpl extends BaseService implements SmsTemplateDetailService {

	@Autowired
	private SmsTemplateDetailDao smsTemplateDetailDao;
	
	@Override
	public SmsTemplateDetailEntity findById(Long id) throws Exception {
		return (SmsTemplateDetailEntity) smsTemplateDetailDao.findById(id, SmsTemplateDetailEntity.class);
	}
	
	@Override
	public SmsTemplateDetailEntity findOne(Long detailId, String account, Long companyId) throws Exception {
		return smsTemplateDetailDao.findOne(detailId, account, companyId);
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		smsTemplateDetailDao.deleteAllByIdArray(idArray.split(","), SmsTemplateDetailEntity.class);
	}
	
	@Override
	public void saveOrUpdate(SmsTemplateDetailEntity entity) throws Exception {
		if (null == entity.getDetailId()) {
			smsTemplateDetailDao.save(entity);
		} else {
			SmsTemplateDetailEntity oldEntity = findById(entity.getDetailId());
			BeanUtils.copyProperties(entity, oldEntity);
			smsTemplateDetailDao.update(oldEntity);
		}
	}

	@Override
	public int updateSendStatus(String smsid, String phone, String resCode, SendStatusEnum statusEnum) throws Exception{
		return smsTemplateDetailDao.updateSendStatus(smsid, phone, resCode, statusEnum);
	}

	@Override
	public List<SmsTemplateDetailEntity> findList(SmsTemplateDetailEntity smsTemplateDetailEntity) throws Exception {
		return smsTemplateDetailDao.findList(smsTemplateDetailEntity);
	}

	@Override
	public PageGrid<SmsTemplateDetailEntity> findPageList(PageGrid<SmsTemplateDetailEntity> pageGrid) throws Exception {
		return smsTemplateDetailDao.findPageList(pageGrid);
	}
	
	/**
	 * 根据发送人账户获取某段时间内的短信发送记录
	 * @param templateIds 多个id，逗号分隔
	 * @param sendNo
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> findList(String templateIds, String sendNo, String startTime, String endTime) throws Exception {
		return smsTemplateDetailDao.findList(templateIds, sendNo, startTime, endTime);
	}
	
}