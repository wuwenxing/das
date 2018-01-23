package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.EmailTemplateDao;
import com.gw.das.dao.market.EmailTemplateDetailContentDao;
import com.gw.das.dao.market.EmailTemplateDetailDao;
import com.gw.das.dao.market.entity.EmailTemplateEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.EmailTemplateService;

@Service
public class EmailTemplateServiceImpl extends BaseService implements EmailTemplateService {

	@Autowired
	private EmailTemplateDao emailTemplateDao;
	@Autowired
	private EmailTemplateDetailDao emailTemplateDetailDao;
	@Autowired
	private EmailTemplateDetailContentDao emailTemplateDetailContentDao;
	
	@Override
	public EmailTemplateEntity findById(Long id) throws Exception {
		return (EmailTemplateEntity) emailTemplateDao.findById(id, EmailTemplateEntity.class);
	}

	@Override
	public EmailTemplateEntity findByCode(String code) throws Exception {
		return emailTemplateDao.findByCode(code);
	}

	@Override
	public void saveOrUpdate(EmailTemplateEntity entity) throws Exception {
		if (null == entity.getTemplateId()) {
			emailTemplateDao.save(entity);
		} else {
			EmailTemplateEntity oldEntity = findById(entity.getTemplateId());
			BeanUtils.copyProperties(entity, oldEntity);
			emailTemplateDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {

		String[] ids = idArray.split(",");
		if(null != ids && ids.length > 0){
			// 删除邮件模板详细数据
			for(String emailTemplateId: ids){
				emailTemplateDetailContentDao.deleteByCon(Long.parseLong(emailTemplateId));
				emailTemplateDetailDao.deleteByCon(Long.parseLong(emailTemplateId));
			}
			// 删除邮件模板
			emailTemplateDao.deleteAllByIdArray(ids, EmailTemplateEntity.class);
		}
	}

	@Override
	public boolean checkEmailTemplate(String code, Long templateId) throws Exception {
		return emailTemplateDao.checkEmailTemplate(code, templateId);
	}

	@Override
	public List<EmailTemplateEntity> findList(EmailTemplateEntity emailTemplateEntity) throws Exception {
		return emailTemplateDao.findList(emailTemplateEntity);
	}

	@Override
	public PageGrid<EmailTemplateEntity> findPageList(PageGrid<EmailTemplateEntity> pageGrid) throws Exception {
		return emailTemplateDao.findPageList(pageGrid);
	}

}