package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.BlacklistEntity;

public interface BlacklistService {

	public BlacklistEntity findById(Long id) throws Exception;
	public BlacklistEntity findByAccount(String account) throws Exception;
	public void saveOrUpdate(BlacklistEntity entity) throws Exception;
	public List<BlacklistEntity> findList(BlacklistEntity blacklistEntity) throws Exception;
	public PageGrid<BlacklistEntity> findPageList(PageGrid<BlacklistEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkAccount(String account, Long blacklistId) throws Exception;
	
	
}
