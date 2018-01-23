package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.SmsTemplateEntity;

public interface SmsTemplateService {

	public SmsTemplateEntity findById(Long id) throws Exception;
	public SmsTemplateEntity findByCode(String code) throws Exception;
	public void saveOrUpdate(SmsTemplateEntity entity) throws Exception;
	public List<SmsTemplateEntity> findList(SmsTemplateEntity smsTemplateEntity) throws Exception;
	public PageGrid<SmsTemplateEntity> findPageList(PageGrid<SmsTemplateEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkSmsTemplate(String code, Long templateId) throws Exception;
	
}
