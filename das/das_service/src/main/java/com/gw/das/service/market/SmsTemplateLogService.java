package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.SmsTemplateLogEntity;

public interface SmsTemplateLogService {

	public SmsTemplateLogEntity findById(Long id) throws Exception;
	public void saveOrUpdate(SmsTemplateLogEntity entity) throws Exception;
	public List<SmsTemplateLogEntity> findList(SmsTemplateLogEntity smsTemplateLogEntity) throws Exception;
	public PageGrid<SmsTemplateLogEntity> findPageList(PageGrid<SmsTemplateLogEntity> pageGrid) throws Exception;
	
}
