package com.gw.das.service.market;

import java.util.Date;
import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.EmailEntity;

public interface EmailService {

	public EmailEntity findById(Long id) throws Exception;
	public void saveOrUpdate(String[] startDateList, String[] endDateList, String[] hourList, String[] minuteList, EmailEntity entity) throws Exception;
	public void saveOrUpdate(EmailEntity entity) throws Exception;
	public List<EmailEntity> findList(EmailEntity emailEntity) throws Exception;
	public PageGrid<EmailEntity> findPageList(PageGrid<EmailEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	
	/**
	 * 立即重发
	 * @param emailId
	 * @throws Exception
	 */
	public void reSendEmail(Long emailId) throws Exception;
	
	/**
	 * 立即对失败重发
	 * @param emailId
	 * @throws Exception
	 */
	public void reSendEmailByFailPhone(Long emailId) throws Exception;
	
	/**
	 * 根据定时时间发送邮件
	 */
	public void sendByTiming(Date curDate) throws Exception;
	
}
