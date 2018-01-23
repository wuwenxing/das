package com.gw.das.service.market;

import java.util.Date;
import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.SmsEntity;

public interface SmsService {

	public SmsEntity findById(Long id) throws Exception;
	public void saveOrUpdate(String[] startDateList, String[] endDateList, String[] hourList, String[] minuteList, SmsEntity entity) throws Exception;
	public void saveOrUpdate(SmsEntity entity) throws Exception;
	public List<SmsEntity> findList(SmsEntity smsEntity) throws Exception;
	public PageGrid<SmsEntity> findPageList(PageGrid<SmsEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	
	/**
	 * 立即重发
	 * @param smsId
	 * @throws Exception
	 */
	public void reSendSms(Long smsId) throws Exception;
	
	/**
	 * 立即对失败重发
	 * @param smsId
	 * @throws Exception
	 */
	public void reSendSmsByFailPhone(Long smsId) throws Exception;

	/**
	 * 根据定时时间发送短信
	 */
	public void sendByTiming(Date curDate) throws Exception;
	
}
