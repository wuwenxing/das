package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.EmailDetailDao;
import com.gw.das.dao.market.entity.EmailDetailEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.EmailDetailService;

@Service
public class EmailDetailServiceImpl extends BaseService implements EmailDetailService {

	@Autowired
	private EmailDetailDao emailDetailDao;

	@Override
	public EmailDetailEntity findById(Long id) throws Exception {
		return (EmailDetailEntity) emailDetailDao.findById(id, EmailDetailEntity.class);
	}

	@Override
	public void saveOrUpdate(EmailDetailEntity entity) throws Exception {
		if (null == entity.getDetailId()) {
			emailDetailDao.save(entity);
		} else {
			EmailDetailEntity oldEntity = findById(entity.getDetailId());
			BeanUtils.copyProperties(entity, oldEntity);
			emailDetailDao.update(oldEntity);
		}
	}

	@Override
	public List<EmailDetailEntity> findList(EmailDetailEntity emailDetailEntity) throws Exception {
		return emailDetailDao.findList(emailDetailEntity);
	}

	@Override
	public PageGrid<EmailDetailEntity> findPageList(PageGrid<EmailDetailEntity> pageGrid) throws Exception {
		return emailDetailDao.findPageList(pageGrid);
	}

}