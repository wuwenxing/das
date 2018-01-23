package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.EmailDetailEntity;

public interface EmailDetailService {

	public EmailDetailEntity findById(Long id) throws Exception;
	public void saveOrUpdate(EmailDetailEntity entity) throws Exception;
	public List<EmailDetailEntity> findList(EmailDetailEntity emailDetailEntity) throws Exception;
	public PageGrid<EmailDetailEntity> findPageList(PageGrid<EmailDetailEntity> pageGrid) throws Exception;
	
}
