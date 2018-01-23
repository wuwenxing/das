package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.BlacklistDao;
import com.gw.das.dao.market.entity.BlacklistEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.BlacklistService;

@Service
public class BlacklistServiceImpl extends BaseService implements BlacklistService {

	@Autowired
	private BlacklistDao blacklistDao;

	@Override
	public BlacklistEntity findById(Long id) throws Exception {
		return (BlacklistEntity) blacklistDao.findById(id, BlacklistEntity.class);
	}

	@Override
	public BlacklistEntity findByAccount(String account) throws Exception {
		return blacklistDao.findByAccount(account);
	}

	@Override
	public void saveOrUpdate(BlacklistEntity entity) throws Exception {
		if (null == entity.getBlacklistId()) {
			blacklistDao.save(entity);
		} else {
			BlacklistEntity oldEntity = findById(entity.getBlacklistId());
			BeanUtils.copyProperties(entity, oldEntity);
			blacklistDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		blacklistDao.deleteAllByIdArray(idArray.split(","), BlacklistEntity.class);
	}

	@Override
	public boolean checkAccount(String account, Long blacklistId) throws Exception {
		return blacklistDao.checkAccount(account, blacklistId);
	}

	@Override
	public List<BlacklistEntity> findList(BlacklistEntity blacklistEntity) throws Exception {
		return blacklistDao.findList(blacklistEntity);
	}

	@Override
	public PageGrid<BlacklistEntity> findPageList(PageGrid<BlacklistEntity> pageGrid) throws Exception {
		return blacklistDao.findPageList(pageGrid);
	}

}