package com.gw.das.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.DictTreeBean;
import com.gw.das.common.easyui.DictTreeBeanUtil;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.system.SystemDictDao;
import com.gw.das.dao.system.entity.SystemDictEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.cache.DictCache;
import com.gw.das.service.system.SystemDictService;

@Service
public class SystemDictServiceImpl extends BaseService implements SystemDictService {

	@Autowired
	private SystemDictDao systemDictDao;

	@Override
	public SystemDictEntity findById(Long id) throws Exception {
		return (SystemDictEntity) systemDictDao.findById(id, SystemDictEntity.class);
	}

	@Override
	public SystemDictEntity findByDictCode(String dictCode) throws Exception {
		return systemDictDao.findByDictCode(dictCode);
	}

	@Override
	public List<SystemDictEntity> findListAllCompany() throws Exception{
		return systemDictDao.findListAllCompany();
	}
	
	@Override
	public List<SystemDictEntity> findList() throws Exception{
		return systemDictDao.findList();
	}

	@Override
	public List<SystemDictEntity> findList(SystemDictEntity dictEntity) throws Exception{
		return systemDictDao.findList(dictEntity);
	}

	@Override
	public PageGrid<SystemDictEntity> findPageList(PageGrid<SystemDictEntity> pageGrid) throws Exception{
		return systemDictDao.findPageList(pageGrid);
	}
	
	/**
	 * @param parentDictCode
	 * @param enableFlag 是否查询启用的数据字典,查询之前先判断父数据字典是否启用
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SystemDictEntity> findListByParentDictCode(String parentDictCode, boolean enableFlag) throws Exception{
		return systemDictDao.findListByParentDictCode(parentDictCode, enableFlag);
	}

	@Override
	public void saveOrUpdate(SystemDictEntity entity) throws Exception {
		if (null == entity.getDictId()) {
			systemDictDao.save(entity);
			// 刷新数据字典缓存
			DictCache.refreshSubDictListMap(entity.getDictCode());
			DictCache.refreshSubDictListMap(entity.getParentDictCode());
		} else {
			SystemDictEntity oldEntity = findById(entity.getDictId());
			String oldParentDictCode = oldEntity.getParentDictCode();
			String oldDictCode = oldEntity.getDictCode();
			boolean flag = oldDictCode.equals(entity.getDictCode());
			BeanUtils.copyProperties(entity, oldEntity);
			systemDictDao.update(oldEntity);
			
			if(!flag){
				// code变化,更新子节点父code
				systemDictDao.updateParentDictCode(oldDictCode, entity.getDictCode());
			}
			// 刷新数据字典缓存
			DictCache.refreshSubDictListMap(oldDictCode);
			DictCache.refreshSubDictListMap(entity.getDictCode());
			DictCache.refreshSubDictListMap(oldParentDictCode);
			DictCache.refreshSubDictListMap(entity.getParentDictCode());
		}
		// 刷新数据字典缓存
		DictCache.refresh(entity.getDictCode());
	}

	@Override
	public void deleteById(Long id) throws Exception {
		SystemDictEntity dictEntity = (SystemDictEntity)systemDictDao.loadById(id, SystemDictEntity.class);
		String dictCode = dictEntity.getDictCode();
		String parentDictCode = dictEntity.getParentDictCode();
		systemDictDao.delete(dictEntity);
		// 刷新数据字典缓存
		DictCache.refreshSubDictListMap(dictCode);
		DictCache.refreshSubDictListMap(parentDictCode);
		DictCache.refresh(dictCode);
	}

	@Override
	public boolean checkDictCode(String dictCode, Long dictId) throws Exception {
		return systemDictDao.checkDictCode(dictCode, dictId);
	}

	/**
	 * 提取树形菜单
	 * @return
	 */
	@Override
	public PageGrid<SystemDictEntity> getDictTreeJson(PageGrid<SystemDictEntity> pageGrid) throws Exception{
		List<DictTreeBean> treeBeanList = new ArrayList<DictTreeBean>();
		findPageList(pageGrid);
		@SuppressWarnings("unchecked")
		List<SystemDictEntity> dictList = pageGrid.getRows();
		if (dictList != null && dictList.size() > 0) {
			for (SystemDictEntity row : dictList) {
				DictTreeBean treeBean = new DictTreeBean();
				treeBean.setId(row.getDictId().toString());
				treeBean.setDictName(row.getDictName());
				treeBean.setDictCode(row.getDictCode());
				treeBean.setParentDictCode(row.getParentDictCode());
				treeBean.setEnableFlag(row.getEnableFlag());
				treeBean.setState("closed");
				treeBean.setType(row.getDictType());
				treeBean.setSort(Integer.parseInt(row.getOrderCode()+""));
				treeBeanList.add(treeBean);
			}
		}
		List<DictTreeBean> nodeList = DictTreeBeanUtil.formatDictTreeBean(treeBeanList);
		pageGrid.setRows(nodeList);
		return pageGrid;
	}
	
	/**
	 * 提取树形子菜单
	 * @return
	 */
	@Override
	public List<DictTreeBean> getSubDictTreeJson(String dictCode) throws Exception{
		List<DictTreeBean> treeBeanList = new ArrayList<DictTreeBean>();
		List<SystemDictEntity> dictList = findListByParentDictCode(dictCode, false);
		if (dictList != null && dictList.size() > 0) {
			for (SystemDictEntity row : dictList) {
				DictTreeBean treeBean = new DictTreeBean();
				treeBean.setId(row.getDictId().toString());
				treeBean.setDictName(row.getDictName());
				treeBean.setDictCode(row.getDictCode());
				treeBean.setParentDictCode(row.getParentDictCode());
				treeBean.setEnableFlag(row.getEnableFlag());
				treeBean.setState("close");
				treeBean.setType(row.getDictType());
				treeBean.setSort(Integer.parseInt(row.getOrderCode()+""));
				treeBeanList.add(treeBean);
			}
		}
		return DictTreeBeanUtil.formatDictTreeBean(treeBeanList);
	}
	
}