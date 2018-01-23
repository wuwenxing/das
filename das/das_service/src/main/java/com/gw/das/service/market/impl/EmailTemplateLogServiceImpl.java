package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.EmailTemplateLogDao;
import com.gw.das.dao.market.entity.EmailTemplateLogEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.EmailTemplateLogService;

@Service
public class EmailTemplateLogServiceImpl extends BaseService implements EmailTemplateLogService {

	@Autowired
	private EmailTemplateLogDao emailTemplateLogDao;

	@Override
	public EmailTemplateLogEntity findById(Long id) throws Exception {
		return (EmailTemplateLogEntity) emailTemplateLogDao.findById(id, EmailTemplateLogEntity.class);
	}

	@Override
	public void saveOrUpdate(EmailTemplateLogEntity entity) throws Exception {
		if (null == entity.getLogId()) {
			emailTemplateLogDao.save(entity);
		} else {
			EmailTemplateLogEntity oldEntity = findById(entity.getLogId());
			BeanUtils.copyProperties(entity, oldEntity);
			emailTemplateLogDao.update(oldEntity);
		}
	}

	@Override
	public List<EmailTemplateLogEntity> findList(EmailTemplateLogEntity emailTemplateLogEntity) throws Exception {
		return emailTemplateLogDao.findList(emailTemplateLogEntity);
	}

	@Override
	public PageGrid<EmailTemplateLogEntity> findPageList(PageGrid<EmailTemplateLogEntity> pageGrid) throws Exception {
		return emailTemplateLogDao.findPageList(pageGrid);
	}

}