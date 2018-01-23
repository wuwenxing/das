package com.gw.das.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gw.das.common.context.Constants;
import com.gw.das.common.enums.MessageTypeEnum;
import com.gw.das.common.flume.FlumeSendMsg;
import com.gw.das.common.hxapi.HxSidCache;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JsonUtil;
import com.gw.das.common.utils.JsonUtils;
import com.gwghk.gold.client.models.OrderinfoParam;
import com.gwghk.gts2.common.util.HttpClientUtils;
import com.gwghk.gwfx.client.ClientManagerFactory;
import com.gwghk.gwfx.client.models.ForexCustomerInfo;

/**
 * 定时同步入金信息(外汇、贵金属、恒信)
 */
@Component
public class SyncDepositInfoTask {
	
	private static final Logger logger = LoggerFactory.getLogger(SyncDepositInfoTask.class);

	/**
	 * 方法用途: 同步入金信息<br>
	 * 实现步骤: 定时20分钟执行一次，获取前一天到当前时间的数据<br>
	 */
	@Scheduled(cron = "0 0/20 * * * ?")
	public void initDate() {
		logger.info("SyncDepositInfoTask-开始执行..");
		Date startTime = new Date();
		try {
			this.dataProcessing();
		} catch (Exception e) {
			logger.error("SyncDepositInfoTask-执行出错:" + e.getMessage(), e);
		} finally {
			long time = new Date().getTime() - startTime.getTime();
			logger.info("SyncDepositInfoTask-定时任务结束-耗时:" + time + "毫秒");
		}
	}
	
	private void dataProcessing() throws Exception {
		Date end = DateUtil.addDays(new Date(), 1);
		Date start =  DateUtil.addDays(new Date(), -2);
		
		try{
			this.fx(end, start);
		}catch(Exception e){
			logger.error("fx入金信息同步出现异常:" + e.getMessage(), e);
		}

		try{
			this.pm(end, start);
		}catch(Exception e){
			logger.error("pm入金信息同步出现异常:" + e.getMessage(), e);
		}

		try{
			this.hx(end, start);
		}catch(Exception e){
			logger.error("hx入金信息同步出现异常:" + e.getMessage(), e);
		}
		
	}
	
	/**
	 * fx入金信息
	 * @throws Exception
	 */
	private void fx(Date end, Date start) throws Exception{
		//结果集
		List<DepositModel> resLIst = new ArrayList<DepositModel>();
		Properties properties = new Properties();

		// 1、获取fx-mt4-NZ入金信息
		ClientManagerFactory client_mt4 = null;
		try{
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("GwFxClientMT4.properties"));
			client_mt4 = ClientManagerFactory.getInstance("GwFxClientMT4", properties);
			List<ForexCustomerInfo> listNZ = client_mt4.getNZFrontOfficeManager().getFirstDepositInfo(start, end);
			if (listNZ != null && listNZ.size() > 0) {
				for (int i = 0; i < listNZ.size(); i++) {
					DepositModel model = new DepositModel();
					model.setAccountNo(listNZ.get(i).getMt4Ploginname());
					model.setDepositTime(listNZ.get(i).getActiveDate());
					model.setBusinessPlatform(Constants.FX_TYPE + "");
					resLIst.add(model);
					logger.info("外汇帐号(fx-mt4-NZ)："+model.getAccountNo()+",时间："+model.getDepositTime());
				}
			}
		}catch(Exception e){
			logger.error("fx-mt4-NZ入金信息同步出现异常:" + e.getMessage(), e);
		}
		
		// 2.获取外汇fx-mt4-UK入金信息
		try{
			List<ForexCustomerInfo> listUK = client_mt4.getUKFrontOfficeManager().getFirstDepositInfo(start, end);
			if (listUK != null && listUK.size() > 0) {
				for (int i = 0; i < listUK.size(); i++) {
					DepositModel model = new DepositModel();
					model.setAccountNo(listUK.get(i).getMt4Ploginname());
					model.setDepositTime(listUK.get(i).getActiveDate());
					model.setBusinessPlatform(Constants.FX_TYPE + "");
					resLIst.add(model);
					logger.info("外汇帐号(fx-mt4-UK)："+model.getAccountNo()+",时间："+model.getDepositTime());
				}
			}
		}catch(Exception e){
			logger.error("fx-mt4-UK入金信息同步出现异常:" + e.getMessage(), e);
		}
		
		// 3.获取外汇GTS2入金信息
		com.gwghk.gts2.client.ManagerFactory clientGts2 = null;
		com.gwghk.persistence.detached.model.Principal principal = null;
		try{
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("GwfxClientGts2.properties"));
			principal = com.gwghk.persistence.detached.model.Principal.get();
			principal.setCompanyId(1L);
			principal.setLoginName(com.gwghk.gts2.common.enums.NettyClientType.Website.getValue());
			clientGts2 = com.gwghk.gts2.client.ManagerFactory.getInstance("GwfxClientGts2", properties);
			List<com.gwghk.gts2.client.models.ActivateAccountParam> listGTS2 = clientGts2.getAccountManager(null).getActivateAccountInfo("GTS2", start, end, principal);
			if (listGTS2 != null && listGTS2.size() > 0) {
				for (int i = 0; i < listGTS2.size(); i++) {
					DepositModel model = new DepositModel();
					model.setAccountNo(listGTS2.get(i).getAccountNo());
					model.setDepositTime(listGTS2.get(i).getActivateTime());
					model.setBusinessPlatform(Constants.FX_TYPE + "");
					resLIst.add(model);
					logger.info("外汇帐号(fx-GTS2)："+model.getAccountNo()+",时间："+model.getDepositTime());
				}
			}
		}catch(Exception e){
			logger.error("fx-GTS2入金信息同步出现异常:" + e.getMessage(), e);
		}
		
		// 4.获取外汇MT4入金信息
		try{
			List<com.gwghk.gts2.client.models.ActivateAccountParam> listMT4 = clientGts2.getAccountManager(null).getActivateAccountInfo("MT4", start, end, principal);
			if (listMT4 != null && listMT4.size() > 0) {
				for (int i = 0; i < listMT4.size(); i++) {
					DepositModel model = new DepositModel();
					model.setAccountNo(listMT4.get(i).getAccountNo());
					model.setDepositTime(listMT4.get(i).getActivateTime());
					model.setBusinessPlatform(Constants.FX_TYPE + "");
					resLIst.add(model);
					logger.info("外汇帐号(fx-MT4)："+model.getAccountNo()+",时间："+model.getDepositTime());
				}
			}
		}catch(Exception e){
			logger.error("fx-MT4入金信息同步出现异常:" + e.getMessage(), e);
		}
		
		this.sendMessage(resLIst);
	}

	/**
	 * pm入金信息
	 * @throws Exception
	 */
	private void pm(Date end, Date start) throws Exception{
		//结果集
		List<DepositModel> resLIst = new ArrayList<DepositModel>();
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("GoldInterface.properties"));
		com.gwghk.gold.client.ClientManagerFactory clientGold = com.gwghk.gold.client.ClientManagerFactory.getInstance("GoldInterface", properties);
		
		String beginTime = DateUtil.getYyyyMmDdHhMm(start); // 开始时间
		String endTime = DateUtil.getYyyyMmDdHhMm(end); // 结束时间
		String flag = "cashin"; // 类型(存款：cashin；取款：cashout)
		String loginname = ""; // 账户：为空表示查所有
		String state = "0"; // 取款/存款状态(0:成功，1：处理中，等等)
		String pageIndexStr = "1"; // 当前页
		String pageSize = "1000000"; // 每页数据量
		
		List<OrderinfoParam> resultList = clientGold.getAccountManager().getOrderInfoByCondition(beginTime, endTime, flag, loginname, state, pageIndexStr, pageSize);
		if (resultList != null && resultList.size() > 0){
			for (OrderinfoParam record : resultList){
				DepositModel model = new DepositModel();
				model.setAccountNo(record.getAccount());
				model.setDepositTime(record.getCreateDate());
				model.setBusinessPlatform(Constants.PM_TYPE + "");
				resLIst.add(model);
				logger.info("贵金属帐号："+model.getAccountNo()+",时间："+model.getDepositTime());
			}
		}
		this.sendMessage(resLIst);
	}

	/**
	 * hx入金信息
	 * @throws Exception
	 */
	private void hx(Date end, Date start) throws Exception{
		//结果集
		List<DepositModel> resLIst = new ArrayList<DepositModel>();
		String startTime = DateUtil.getYyyyMmDdHhMm(start); // 开始时间
		String endTime = DateUtil.getYyyyMmDdHhMm(end); // 结束时间
		String hxsid = HxSidCache.getHxSid();
		String hxActivateInfoUrl = HxSidCache.hxApiUrl+"/members/getActivateInfo/?sid="+hxsid+"&startTime="+startTime+"&endTime="+endTime;
		String hxResult = HttpClientUtils.httpsPostString(hxActivateInfoUrl, null);
		Map<String, Object> jsonMap = JsonUtils.toHashMap(hxResult);
		if(Integer.valueOf(jsonMap.get("status").toString()) == 0){
			Object dataObj = jsonMap.get("data");
			if(null != dataObj){
				List<Map<String, Object>> list = JsonUtils.toList(dataObj);
				if(list!=null & list.size() >0){
					for (Map<String, Object> map : list){
						DepositModel model = new DepositModel();
						model.setAccountNo(String.valueOf(map.get("login")));
						model.setDepositTime(DateUtil.getDateFromStr(String.valueOf(map.get("activate_time"))));
						model.setBusinessPlatform(Constants.HX_TYPE + "");
						resLIst.add(model);
						logger.info("恒信帐号："+model.getAccountNo()+",时间："+model.getDepositTime());
					}
				}	
			}
		}
		this.sendMessage(resLIst);
	}
	
	/**
	 * 第一次同步所有入金信息
	 */
	public void syncPmAllDepositData(){
		try{
			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("GoldInterface.properties"));
			com.gwghk.gold.client.ClientManagerFactory clientGold = com.gwghk.gold.client.ClientManagerFactory.getInstance("GoldInterface", properties);

			String beginTime = ""; // 开始时间,为空表示查所有
			String endTime = ""; // 结束时间，为空表示查所有
			String flag = "cashin"; // 类型(存款：cashin；取款：cashout)
			String loginname = ""; // 账户：为空表示查所有
			String state = "0"; // 取款/存款状态(0:成功，1：处理中，等等)
			String pageIndexStr = "1"; // 当前页
			String pageSize = "5000"; // 每页数据量

			// 取400页的数据
			for(int i=1; i<400; i++){
				pageIndexStr = i + ""; // 当前页
				List<DepositModel> resLIst = new ArrayList<DepositModel>();//结果集
				List<OrderinfoParam> resultList = clientGold.getAccountManager().getOrderInfoByCondition(beginTime, endTime, flag, loginname, state, pageIndexStr, pageSize);
				if (resultList != null && resultList.size() > 0){
					for (OrderinfoParam record : resultList){
						DepositModel model = new DepositModel();
						model.setAccountNo(record.getAccount());
						model.setDepositTime(record.getCreateDate());
						model.setBusinessPlatform(Constants.PM_TYPE + "");
						resLIst.add(model);
						logger.error("贵金属帐号："+model.getAccountNo()+",时间："+model.getDepositTime());
					}
				}else{
					// 已经无数据，退出循环
					logger.error("已经无数据，退出循环，当前页码i=" + i);
					break;
				}
				
				this.sendMessage(resLIst);
			}
		}catch(Exception e){
			logger.error("同步PM所有入金信息出错", e);
		}
	}
	
	/**
	 * 公共发送消息到flume与kafka
	 * @param resLIst
	 */
	private void sendMessage(List<DepositModel> resLIst){
		if(null != resLIst && resLIst.size()>0){
			try {
				FlumeSendMsg.sendMessage(JsonUtil.objectToJson(resLIst), MessageTypeEnum.deposit.getLabelKey());
			} catch (Exception e) {
				logger.error("同步入金信息放入Flume异常：" + e.getMessage(), e);
			}
		}
	}
	
}
