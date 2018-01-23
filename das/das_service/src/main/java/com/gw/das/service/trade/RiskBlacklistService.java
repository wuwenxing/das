package com.gw.das.service.trade;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.entity.RiskBlacklistEntity;

public interface RiskBlacklistService {

	public RiskBlacklistEntity findById(Long id) throws Exception;
	/**
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(RiskBlacklistEntity entity) throws Exception;
	
	/**
	 * 不调用封装方法保存,因为companyId是外部传入
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void saveOrUpdate(RiskBlacklistEntity entity) throws Exception;
	
	/**
	 * 导入数据后保存或更新
	 * @param entity
	 * @throws Exception
	 */
	public void saveOrUpdate2(RiskBlacklistEntity param) throws Exception;
	
	public List<RiskBlacklistEntity> findList(RiskBlacklistEntity entity) throws Exception;
	public PageGrid<RiskBlacklistEntity> findPageList(PageGrid<RiskBlacklistEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	
	
	
}
