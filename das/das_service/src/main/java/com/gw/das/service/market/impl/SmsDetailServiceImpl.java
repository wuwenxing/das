package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.SmsDetailDao;
import com.gw.das.dao.market.entity.SmsDetailEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.SmsDetailService;

@Service
public class SmsDetailServiceImpl extends BaseService implements SmsDetailService {

	@Autowired
	private SmsDetailDao smsDetailDao;

	@Override
	public SmsDetailEntity findById(Long id) throws Exception {
		return (SmsDetailEntity) smsDetailDao.findById(id, SmsDetailEntity.class);
	}

	@Override
	public void saveOrUpdate(SmsDetailEntity entity) throws Exception {
		if (null == entity.getDetailId()) {
			smsDetailDao.save(entity);
		} else {
			SmsDetailEntity oldEntity = findById(entity.getDetailId());
			BeanUtils.copyProperties(entity, oldEntity);
			smsDetailDao.update(oldEntity);
		}
	}

	@Override
	public int updateSendStatus(String smsid, String phone, String resCode, SendStatusEnum statusEnum) throws Exception{
		return smsDetailDao.updateSendStatus(smsid, phone, resCode, statusEnum);
	}
	
	@Override
	public List<SmsDetailEntity> findList(SmsDetailEntity smsDetailEntity) throws Exception {
		return smsDetailDao.findList(smsDetailEntity);
	}

	@Override
	public PageGrid<SmsDetailEntity> findPageList(PageGrid<SmsDetailEntity> pageGrid) throws Exception {
		return smsDetailDao.findPageList(pageGrid);
	}

}