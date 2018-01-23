package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.dao.market.entity.SmsDetailEntity;

public interface SmsDetailService {

	public SmsDetailEntity findById(Long id) throws Exception;
	public void saveOrUpdate(SmsDetailEntity entity) throws Exception;
	public int updateSendStatus(String smsid, String phone, String resCode, SendStatusEnum statusEnum) throws Exception;
	public List<SmsDetailEntity> findList(SmsDetailEntity smsDetailEntity) throws Exception;
	public PageGrid<SmsDetailEntity> findPageList(PageGrid<SmsDetailEntity> pageGrid) throws Exception;
	
}
