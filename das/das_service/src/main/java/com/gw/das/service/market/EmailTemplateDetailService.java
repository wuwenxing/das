package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.EmailTemplateDetailEntity;

public interface EmailTemplateDetailService {
	
	public EmailTemplateDetailEntity findOne(Long detailId, String account, Long companyId) throws Exception;
	public EmailTemplateDetailEntity findById(Long id) throws Exception;
	public void saveOrUpdate(EmailTemplateDetailEntity entity) throws Exception;
	public List<EmailTemplateDetailEntity> findList(EmailTemplateDetailEntity emailTemplateDetailEntity) throws Exception;
	public PageGrid<EmailTemplateDetailEntity> findPageList(PageGrid<EmailTemplateDetailEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
}
