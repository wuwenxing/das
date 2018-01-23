package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.SmsTemplateLogDao;
import com.gw.das.dao.market.entity.SmsTemplateLogEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.SmsTemplateLogService;

@Service
public class SmsTemplateLogServiceImpl extends BaseService implements SmsTemplateLogService {

	@Autowired
	private SmsTemplateLogDao smsTemplateLogDao;

	@Override
	public SmsTemplateLogEntity findById(Long id) throws Exception {
		return (SmsTemplateLogEntity) smsTemplateLogDao.findById(id, SmsTemplateLogEntity.class);
	}

	@Override
	public void saveOrUpdate(SmsTemplateLogEntity entity) throws Exception {
		if (null == entity.getLogId()) {
			smsTemplateLogDao.save(entity);
		} else {
			SmsTemplateLogEntity oldEntity = findById(entity.getLogId());
			BeanUtils.copyProperties(entity, oldEntity);
			smsTemplateLogDao.update(oldEntity);
		}
	}

	@Override
	public List<SmsTemplateLogEntity> findList(SmsTemplateLogEntity smsTemplateLogEntity) throws Exception {
		return smsTemplateLogDao.findList(smsTemplateLogEntity);
	}

	@Override
	public PageGrid<SmsTemplateLogEntity> findPageList(PageGrid<SmsTemplateLogEntity> pageGrid) throws Exception {
		return smsTemplateLogDao.findPageList(pageGrid);
	}

}