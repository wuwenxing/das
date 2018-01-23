package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.SmsTemplateDao;
import com.gw.das.dao.market.SmsTemplateDetailDao;
import com.gw.das.dao.market.entity.SmsTemplateEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.SmsTemplateService;

@Service
public class SmsTemplateServiceImpl extends BaseService implements SmsTemplateService {

	@Autowired
	private SmsTemplateDao smsTemplateDao;
	@Autowired
	private SmsTemplateDetailDao smsTemplateDetailDao;

	@Override
	public SmsTemplateEntity findById(Long id) throws Exception {
		return (SmsTemplateEntity) smsTemplateDao.findById(id, SmsTemplateEntity.class);
	}

	@Override
	public SmsTemplateEntity findByCode(String code) throws Exception {
		return smsTemplateDao.findByCode(code);
	}

	@Override
	public void saveOrUpdate(SmsTemplateEntity entity) throws Exception {
		if (null == entity.getTemplateId()) {
			smsTemplateDao.save(entity);
		} else {
			SmsTemplateEntity oldEntity = findById(entity.getTemplateId());
			BeanUtils.copyProperties(entity, oldEntity);
			smsTemplateDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		String[] ids = idArray.split(",");
		if(null != ids && ids.length > 0){
			// 删除短信模板详细数据
			for(String smsTemplateId: ids){
				smsTemplateDetailDao.deleteByCon(Long.parseLong(smsTemplateId));
			}
			// 删除短信模板
			smsTemplateDao.deleteAllByIdArray(ids, SmsTemplateEntity.class);
		}
	}

	@Override
	public boolean checkSmsTemplate(String code, Long templateId) throws Exception {
		return smsTemplateDao.checkSmsTemplate(code, templateId);
	}

	@Override
	public List<SmsTemplateEntity> findList(SmsTemplateEntity smsTemplateEntity) throws Exception {
		return smsTemplateDao.findList(smsTemplateEntity);
	}

	@Override
	public PageGrid<SmsTemplateEntity> findPageList(PageGrid<SmsTemplateEntity> pageGrid) throws Exception {
		return smsTemplateDao.findPageList(pageGrid);
	}

}