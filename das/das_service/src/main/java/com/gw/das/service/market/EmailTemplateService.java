package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.EmailTemplateEntity;

public interface EmailTemplateService {

	public EmailTemplateEntity findById(Long id) throws Exception;
	public EmailTemplateEntity findByCode(String code) throws Exception;
	public void saveOrUpdate(EmailTemplateEntity entity) throws Exception;
	public List<EmailTemplateEntity> findList(EmailTemplateEntity emailTemplateEntity) throws Exception;
	public PageGrid<EmailTemplateEntity> findPageList(PageGrid<EmailTemplateEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkEmailTemplate(String code, Long templateId) throws Exception;
	
}
