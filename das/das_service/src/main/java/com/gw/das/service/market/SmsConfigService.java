package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.SmsConfigEntity;

public interface SmsConfigService {

	public SmsConfigEntity findById(Long id) throws Exception;
	public SmsConfigEntity findBySign(String sign) throws Exception;
	public void saveOrUpdate(String[] signs, String[] smsChannels) throws Exception;
	public void saveOrUpdate(SmsConfigEntity entity) throws Exception;
	public List<SmsConfigEntity> findList(SmsConfigEntity smsConfigEntity) throws Exception;
	public PageGrid<SmsConfigEntity> findPageList(PageGrid<SmsConfigEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkSmsConfig(String sign, String smsChannel, Long smsConfigId) throws Exception;
	public String getSmsChannel(String sign) throws Exception;
}
