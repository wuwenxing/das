package com.gw.das.service.market.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.Constants;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.AccountAnalyzeStatusEnum;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.response.ApiResult;
import com.gw.das.common.token.TokenCache;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gw.das.dao.market.AccountAnalyzeDao;
import com.gw.das.dao.market.entity.AccountAnalyzeEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.AccountAnalyzeService;

@Service
public class AccountAnalyzeServiceImpl extends BaseService implements AccountAnalyzeService {

	private static final Logger logger = LoggerFactory.getLogger(AccountAnalyzeServiceImpl.class);
	
	@Autowired
	private AccountAnalyzeDao accountAnalyzeDao;

	@Override
	public AccountAnalyzeEntity findById(Long id) throws Exception {
		return (AccountAnalyzeEntity) accountAnalyzeDao.findById(id, AccountAnalyzeEntity.class);
	}

	@Override
	public void saveOrUpdate(AccountAnalyzeEntity entity) throws Exception {
		if (null == entity.getBatchId()) {
			accountAnalyzeDao.save(entity);
		} else {
			AccountAnalyzeEntity oldEntity = findById(entity.getBatchId());
			BeanUtils.copyProperties(entity, oldEntity);
			accountAnalyzeDao.update(oldEntity);
		}
	}

	public void update(AccountAnalyzeEntity entity, String loginName, String loginIp) throws Exception{
		if (null != entity && null != entity.getBatchId()) {
			Date date = new Date();
			entity.setUpdateDate(date);
			entity.setUpdateUser(loginName);
			entity.setUpdateDate(date);
			entity.setUpdateIp(loginIp);
			entity.setVersionNo((entity.getVersionNo()==null? 0:entity.getVersionNo()) + 1);
			accountAnalyzeDao.getCurrentSession().update(entity);
		}
	}
	
	@Override
	public void updateGenerateStatus(AccountAnalyzeEntity entity, String loginName, String loginIp) throws Exception {
		if (null != entity && null != entity.getBatchId()) {
			Date date = new Date();
			// 更新状态
			entity.setGenerateStatus(AccountAnalyzeStatusEnum.Y.getLabelKey());
			entity.setGenerateTime(date);
			entity.setUpdateDate(date);
			entity.setUpdateUser(loginName);
			entity.setUpdateDate(date);
			entity.setUpdateIp(loginIp);
			entity.setVersionNo((entity.getVersionNo()==null? 0:entity.getVersionNo()) + 1);
			accountAnalyzeDao.getCurrentSession().update(entity);
		}
	}

	public void updateSendStatus(AccountAnalyzeEntity entity, String loginName, String loginIp) throws Exception{
		if (null != entity && null != entity.getBatchId()) {
			Date date = new Date();
			// 更新状态
			entity.setSendStatus(SendStatusEnum.sendSuccess.getLabelKey());
			entity.setSendTime(date);
			entity.setUpdateDate(date);
			entity.setUpdateUser(loginName);
			entity.setUpdateDate(date);
			entity.setUpdateIp(loginIp);
			entity.setVersionNo((entity.getVersionNo()==null? 0:entity.getVersionNo()) + 1);
			accountAnalyzeDao.getCurrentSession().update(entity);
		}
	}
	
	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		accountAnalyzeDao.deleteAllByIdArray(idArray.split(","), AccountAnalyzeEntity.class);
	}

	@Override
	public List<AccountAnalyzeEntity> findList(AccountAnalyzeEntity accountAnalyzeEntity) throws Exception {
		return accountAnalyzeDao.findList(accountAnalyzeEntity);
	}

	@Override
	public PageGrid<AccountAnalyzeEntity> findPageList(PageGrid<AccountAnalyzeEntity> pageGrid) throws Exception {
		return accountAnalyzeDao.findPageList(pageGrid);
	}
	
	/**
	 * 诊断接口地址-获取保证金水平走势
	 */
	public Object marginLevel(AccountAnalyzeEntity entity) throws Exception {
		// 根据条件查询接口，获取模板数据
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(entity.getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(entity.getCompanyId()+""));
		paramMap.put("accountNo", entity.getAccountNo());
		paramMap.put("platform", entity.getPlatform());
		paramMap.put("startDate", entity.getStartDate());
		paramMap.put("endDate", entity.getEndDate());
		String result = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.marginLevel, paramMap);
		ApiResult apiResult = JacksonUtil.readValue(result, ApiResult.class);
		if(null != apiResult && "0".equals(apiResult.getStatus())){
			return apiResult.getResult();
		}else{
			String msg = apiResult==null?"":"[" + apiResult.getStatus() + ","+ apiResult.getMsg() + "]";
			logger.error("请求接口错误" + msg);
		}
		return null;
	}
	
	/**
	 * 诊断接口地址-获取3笔最大盈利单
	 */
	public Object topProfit(AccountAnalyzeEntity entity) throws Exception {
		// 根据条件查询接口，获取模板数据
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(entity.getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(entity.getCompanyId()+""));
		paramMap.put("accountNo", entity.getAccountNo());
		paramMap.put("platform", entity.getPlatform());
		paramMap.put("startDate", entity.getStartDate());
		paramMap.put("endDate", entity.getEndDate());
		String result = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.topProfit, paramMap);
		ApiResult apiResult = JacksonUtil.readValue(result, ApiResult.class);
		if(null != apiResult && "0".equals(apiResult.getStatus())){
			return apiResult.getResult();
		}else{
			String msg = apiResult==null?"":"[" + apiResult.getStatus() + ","+ apiResult.getMsg() + "]";
			logger.error("请求接口错误" + msg);
		}
		return null;
	}
	
	/**
	 * 诊断接口地址-获取3笔最大亏损单
	 */
	public Object topLoss(AccountAnalyzeEntity entity) throws Exception {
		// 根据条件查询接口，获取模板数据
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(entity.getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(entity.getCompanyId()+""));
		paramMap.put("accountNo", entity.getAccountNo());
		paramMap.put("platform", entity.getPlatform());
		paramMap.put("startDate", entity.getStartDate());
		paramMap.put("endDate", entity.getEndDate());
		String result = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.topLoss, paramMap);
		ApiResult apiResult = JacksonUtil.readValue(result, ApiResult.class);
		if(null != apiResult && "0".equals(apiResult.getStatus())){
			return apiResult.getResult();
		}else{
			String msg = apiResult==null?"":"[" + apiResult.getStatus() + ","+ apiResult.getMsg() + "]";
			logger.error("请求接口错误" + msg);
		}
		return null;
	}
	
	/**
	 * 诊断接口地址-平仓单的盈亏金额与持仓时间
	 */
	public Object profitAndLossAmountAndTime(AccountAnalyzeEntity entity) throws Exception {
		// 根据条件查询接口，获取模板数据
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(entity.getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(entity.getCompanyId()+""));
		paramMap.put("accountNo", entity.getAccountNo());
		paramMap.put("platform", entity.getPlatform());
		paramMap.put("startDate", entity.getStartDate());
		paramMap.put("endDate", entity.getEndDate());
		String result = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.profitAndLossAmountAndTime, paramMap);
		ApiResult apiResult = JacksonUtil.readValue(result, ApiResult.class);
		if(null != apiResult && "0".equals(apiResult.getStatus())){
			return apiResult.getResult();
		}else{
			String msg = apiResult==null?"":"[" + apiResult.getStatus() + ","+ apiResult.getMsg() + "]";
			logger.error("请求接口错误" + msg);
		}
		return null;
	}

	@Override
	public Map accountOverview(AccountAnalyzeEntity entity) throws Exception {
		// 根据条件查询接口，获取模板数据
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(entity.getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(entity.getCompanyId()+""));
		paramMap.put("accountNo", entity.getAccountNo());
		paramMap.put("platform", entity.getPlatform());
		paramMap.put("startDate", entity.getStartDate());
		paramMap.put("endDate", entity.getEndDate());
		String result = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.accountOverview, paramMap);
		ApiResult apiResult = JacksonUtil.readValue(result, ApiResult.class);
		if(null != apiResult && "0".equals(apiResult.getStatus())){
			List<Map> rowsList = (List<Map>)apiResult.getResult();
			if(null != rowsList && rowsList.size()>0){
				return rowsList.get(0);
			}
		}else{
			String msg = apiResult==null?"":"[" + apiResult.getStatus() + ","+ apiResult.getMsg() + "]";
			logger.error("请求接口错误" + msg);
		}
		return null;
	}

	@Override
	public Object profitStatistics(AccountAnalyzeEntity entity) throws Exception {
		// 根据条件查询接口，获取模板数据
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(entity.getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(entity.getCompanyId()+""));
		paramMap.put("accountNo", entity.getAccountNo());
		paramMap.put("platform", entity.getPlatform());
		paramMap.put("startDate", entity.getStartDate());
		paramMap.put("endDate", entity.getEndDate());
		String result = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.profitStatistics, paramMap);
		ApiResult apiResult = JacksonUtil.readValue(result, ApiResult.class);
		if(null != apiResult && "0".equals(apiResult.getStatus())){
			return apiResult.getResult();
		}else{
			String msg = apiResult==null?"":"[" + apiResult.getStatus() + ","+ apiResult.getMsg() + "]";
			logger.error("请求接口错误" + msg);
		}
		return null;
	}

	@Override
	public Map profitAnalysis(AccountAnalyzeEntity entity) throws Exception {
		// 根据条件查询接口，获取模板数据
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(entity.getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(entity.getCompanyId()+""));
		paramMap.put("accountNo", entity.getAccountNo());
		paramMap.put("platform", entity.getPlatform());
		paramMap.put("startDate", entity.getStartDate());
		paramMap.put("endDate", entity.getEndDate());
		String result = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.profitAnalysis, paramMap);
		ApiResult apiResult = JacksonUtil.readValue(result, ApiResult.class);
		if(null != apiResult && "0".equals(apiResult.getStatus())){
			List<Map> rowsList = (List<Map>)apiResult.getResult();
			if(null != rowsList && rowsList.size()>0){
				return rowsList.get(0);
			}
		}else{
			String msg = apiResult==null?"":"[" + apiResult.getStatus() + ","+ apiResult.getMsg() + "]";
			logger.error("请求接口错误" + msg);
		}
		return null;
	}
	
}