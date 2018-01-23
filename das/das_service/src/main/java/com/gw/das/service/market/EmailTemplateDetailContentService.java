package com.gw.das.service.market;

import com.gw.das.dao.market.entity.EmailTemplateDetailContentEntity;

public interface EmailTemplateDetailContentService {
	
	public EmailTemplateDetailContentEntity findById(Long id) throws Exception;
	public void saveOrUpdate(EmailTemplateDetailContentEntity entity) throws Exception;
}
