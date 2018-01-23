package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.EmailTemplateLogEntity;

public interface EmailTemplateLogService {

	public EmailTemplateLogEntity findById(Long id) throws Exception;
	public void saveOrUpdate(EmailTemplateLogEntity entity) throws Exception;
	public List<EmailTemplateLogEntity> findList(EmailTemplateLogEntity emailTemplateLogEntity) throws Exception;
	public PageGrid<EmailTemplateLogEntity> findPageList(PageGrid<EmailTemplateLogEntity> pageGrid) throws Exception;
	
}
