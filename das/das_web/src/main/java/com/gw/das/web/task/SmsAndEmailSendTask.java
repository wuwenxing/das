package com.gw.das.web.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.SystemThreadEnum;
import com.gw.das.dao.system.entity.SystemThreadLogEntity;
import com.gw.das.service.market.EmailService;
import com.gw.das.service.market.SmsService;
import com.gw.das.service.system.SystemThreadLogService;

/**
 * 每1分钟执行-查找定时发送的短信
 * 每1分钟执行-查找定时发送的邮件
 */
@Component
public class SmsAndEmailSendTask {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsAndEmailSendTask.class);

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SystemThreadLogService systemThreadLogService;
	
	
	@Scheduled(cron = "0 0/1 * * * ?") // 每1分钟执行
	public void initDate() {
		logger.info("SmsAndEmailSendTask-开始执行..");
		Date startTime = new Date();
		// 执行日志实体
		SystemThreadLogEntity entity = new SystemThreadLogEntity();
		entity.setStartExecuteTime(startTime);
		entity.setCode(SystemThreadEnum.smsAndEmailSendTask.getLabelKey());
		try {
			
			// 循环每个companyId
			for(CompanyEnum ae : CompanyEnum.values()){
				// 不是集团业务下(该业务没有该发送短信等需求)，才执行同步
				if(!CompanyEnum.gw.getLabelKey().equals(ae.getLabelKey())){
					// 1、设置当前为系统线程用户
					UserContext.setSystemThread(ae.getLabelKeyLong());
					// 2、执行业务逻辑
					this.dataProcessing(startTime);
				}
			}
			
			// 设置执行状态
			entity.setStatus("Y");
		} catch (Exception e) {
			// 3、设置执行状态
			entity.setStatus("N");
			entity.setRemark(e.getMessage());
			logger.error("SmsAndEmailSendTask-执行出错:" + e.getMessage(), e);
		} finally {
			Date endTime = new Date();
			entity.setEndExecuteTime(endTime);
			try {
				// 记录执行日志
				systemThreadLogService.saveOrUpdate(entity);
			} catch (Exception e) {
				logger.error("记录执行日志出错:" + e.getMessage(), e);
			}
			long time = new Date().getTime() - startTime.getTime();
			logger.info("SmsAndEmailSendTask-定时任务结束-耗时:" + time + "毫秒");
		}
	}
	
	private void dataProcessing(Date startTime) throws Exception {
		smsService.sendByTiming(startTime);
		emailService.sendByTiming(startTime);
	}
	
	public static void main(String[] args) {
		
	}
}
