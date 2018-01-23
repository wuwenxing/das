package com.gw.das.service.market;

import java.util.List;
import java.util.Map;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.dao.market.entity.SmsTemplateDetailEntity;

public interface SmsTemplateDetailService {
	
	public SmsTemplateDetailEntity findOne(Long detailId, String account, Long companyId) throws Exception;
	public SmsTemplateDetailEntity findById(Long id) throws Exception;
	public void saveOrUpdate(SmsTemplateDetailEntity entity) throws Exception;
	public int updateSendStatus(String smsid, String phone, String resCode, SendStatusEnum statusEnum) throws Exception;
	public List<SmsTemplateDetailEntity> findList(SmsTemplateDetailEntity smsTemplateDetailEntity) throws Exception;
	public PageGrid<SmsTemplateDetailEntity> findPageList(PageGrid<SmsTemplateDetailEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	/**
	 * 根据发送人账户获取某段时间内的短信发送记录
	 * @param templateIds 多个id，逗号分隔
	 * @param sendNo
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> findList(String templateIds, String sendNo, String startTime, String endTime) throws Exception;
	
}
