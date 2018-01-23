package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.EmailTemplateDetailDao;
import com.gw.das.dao.market.entity.EmailTemplateDetailEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.EmailTemplateDetailService;

@Service
public class EmailTemplateDetailServiceImpl extends BaseService implements EmailTemplateDetailService {

	@Autowired
	private EmailTemplateDetailDao emailTemplateDetailDao;

	@Override
	public EmailTemplateDetailEntity findById(Long id) throws Exception {
		return (EmailTemplateDetailEntity) emailTemplateDetailDao.findById(id, EmailTemplateDetailEntity.class);
	}
	
	@Override
	public EmailTemplateDetailEntity findOne(Long detailId, String account, Long companyId) throws Exception {
		return emailTemplateDetailDao.findOne(detailId, account, companyId);
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		emailTemplateDetailDao.deleteAllByIdArray(idArray.split(","), EmailTemplateDetailEntity.class);
	}
	
	@Override
	public void saveOrUpdate(EmailTemplateDetailEntity entity) throws Exception {
		if (null == entity.getDetailId()) {
			emailTemplateDetailDao.save(entity);
		} else {
			EmailTemplateDetailEntity oldEntity = findById(entity.getDetailId());
			BeanUtils.copyProperties(entity, oldEntity);
			emailTemplateDetailDao.update(oldEntity);
		}
	}

	@Override
	public List<EmailTemplateDetailEntity> findList(EmailTemplateDetailEntity emailTemplateDetailEntity) throws Exception {
		return emailTemplateDetailDao.findList(emailTemplateDetailEntity);
	}

	@Override
	public PageGrid<EmailTemplateDetailEntity> findPageList(PageGrid<EmailTemplateDetailEntity> pageGrid) throws Exception {
		return emailTemplateDetailDao.findPageList(pageGrid);
	}

}