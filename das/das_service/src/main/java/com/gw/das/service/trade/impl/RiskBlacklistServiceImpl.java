package com.gw.das.service.trade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.common.utils.MD5;
import com.gw.das.dao.trade.RiskBlacklistDao;
import com.gw.das.dao.trade.entity.RiskBlacklistEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.trade.RiskBlacklistService;

@Service
public class RiskBlacklistServiceImpl extends BaseService implements RiskBlacklistService {

	@Autowired
	private RiskBlacklistDao riskBlacklistDao;

	@Override
	public RiskBlacklistEntity findById(Long id) throws Exception {
		return (RiskBlacklistEntity) riskBlacklistDao.findById(id, RiskBlacklistEntity.class);
	}
	
	/**
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(RiskBlacklistEntity entity) throws Exception{
		if (null != entity.getRiskBlacklistId()) {
			RiskBlacklistEntity oldEntity = findById(entity.getRiskBlacklistId());
			BeanUtils.copyProperties(entity, oldEntity);
			riskBlacklistDao.update(oldEntity);
		}
	}

	/**
	 * 不调用封装方法保存,因为companyId是外部传入
	 * 
	 * @param entity
	 * @throws Exception
	 */
	@Override
	public void saveOrUpdate(RiskBlacklistEntity entity) throws Exception {
		riskBlacklistDao.saveOrUpdate(entity);
	}
	/**
	 * 导入数据后保存或更新
	 * @param entity
	 * @throws Exception
	 */
	public void saveOrUpdate2(RiskBlacklistEntity param) throws Exception{
		String temp = param.getCompanyId()
				+ "," + param.getRiskType()
				+ "," + param.getRiskReason()
				+ "," + param.getRiskRemark()
				+ "," + param.getRiskTime()
				+ "," + param.getPlatform()
				+ "," + param.getAccountNo()
				+ "," + param.getMobile()
				+ "," + param.getEmail()
				+ "," + param.getIdCard()
				+ "," + param.getIp()
				+ "," + param.getDeviceType()
				+ "," + param.getDeviceInfo();
		String md5 = MD5.getMd5(temp);
		param.setMd5(md5);
		
		// 验证是否存在md5值
		RiskBlacklistEntity oldEntity = riskBlacklistDao.findByCon(md5);
		if(null != oldEntity){
			// 存在更新时间
			riskBlacklistDao.saveOrUpdate(oldEntity);
		}else{
			// 不存在则新增
			riskBlacklistDao.saveOrUpdate(param);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		riskBlacklistDao.deleteAllByIdArray(idArray.split(","), RiskBlacklistEntity.class);
	}

	@Override
	public List<RiskBlacklistEntity> findList(RiskBlacklistEntity entity) throws Exception {
		return riskBlacklistDao.findList(entity);
	}

	@Override
	public PageGrid<RiskBlacklistEntity> findPageList(PageGrid<RiskBlacklistEntity> pageGrid) throws Exception {
		return riskBlacklistDao.findPageList(pageGrid);
	}

}