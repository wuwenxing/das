package com.gw.das.service.trade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.trade.TradeIndexDao;
import com.gw.das.dao.trade.entity.TradeIndexEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.trade.TradeIndexService;

@Service
public class TradeIndexServiceImpl extends BaseService implements TradeIndexService {

	@Autowired
	private TradeIndexDao tradeIndexDao;

	@Override
	public TradeIndexEntity findById(Long id) throws Exception {
		return (TradeIndexEntity) tradeIndexDao.findById(id, TradeIndexEntity.class);
	}

	@Override
	public void saveOrUpdate(TradeIndexEntity entity) throws Exception {
		if (null == entity.getTradeIndexId()) {
			tradeIndexDao.save(entity);
		} else {
			TradeIndexEntity oldEntity = findById(entity.getTradeIndexId());
			BeanUtils.copyProperties(entity, oldEntity);
			tradeIndexDao.update(oldEntity);
		}
	}
	
	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		tradeIndexDao.deleteAllByIdArray(idArray.split(","), TradeIndexEntity.class);
	}

	@Override
	public List<TradeIndexEntity> findList(TradeIndexEntity entity) throws Exception {
		return tradeIndexDao.findList(entity);
	}

	@Override
	public PageGrid<TradeIndexEntity> findPageList(PageGrid<TradeIndexEntity> pageGrid) throws Exception {
		return tradeIndexDao.findPageList(pageGrid);
	}

	@Override
	public boolean checkDateTime(String dateTime, Long tradeIndexId) throws Exception{
		return tradeIndexDao.checkDateTime(dateTime, tradeIndexId);
	}
	
}