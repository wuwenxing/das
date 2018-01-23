package com.gw.das.service.trade;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.entity.TradeIndexEntity;

public interface TradeIndexService {

	public TradeIndexEntity findById(Long id) throws Exception;
	public void saveOrUpdate(TradeIndexEntity entity) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public List<TradeIndexEntity> findList(TradeIndexEntity entity) throws Exception;
	public PageGrid<TradeIndexEntity> findPageList(PageGrid<TradeIndexEntity> pageGrid) throws Exception;
	public boolean checkDateTime(String dateTime, Long tradeIndexId) throws Exception;
	
}
