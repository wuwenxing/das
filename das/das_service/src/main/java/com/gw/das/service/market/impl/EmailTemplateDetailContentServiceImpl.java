package com.gw.das.service.market.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.EmailTemplateDetailContentDao;
import com.gw.das.dao.market.entity.EmailTemplateDetailContentEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.EmailTemplateDetailContentService;

@Service
public class EmailTemplateDetailContentServiceImpl extends BaseService implements EmailTemplateDetailContentService {

	@Autowired
	private EmailTemplateDetailContentDao emailTemplateDetailContentDao;

	@Override
	public EmailTemplateDetailContentEntity findById(Long id) throws Exception {
		return (EmailTemplateDetailContentEntity) emailTemplateDetailContentDao.findById(id, EmailTemplateDetailContentEntity.class);
	}
	
	@Override
	public void saveOrUpdate(EmailTemplateDetailContentEntity entity) throws Exception {
		if (null == entity.getContentId()) {
			emailTemplateDetailContentDao.save(entity);
		} else {
			EmailTemplateDetailContentEntity oldEntity = findById(entity.getContentId());
			BeanUtils.copyProperties(entity, oldEntity);
			emailTemplateDetailContentDao.update(oldEntity);
		}
	}
}