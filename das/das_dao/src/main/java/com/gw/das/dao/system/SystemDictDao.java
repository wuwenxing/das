package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.SystemDictEntity;

@Repository
public class SystemDictDao extends BaseDao {

	public SystemDictEntity findByDictCode(String dictCode) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemDictEntity where dictCode = ? and companyId = ? ");
		paramList.add(dictCode);
		paramList.add(UserContext.get().getCompanyId());
		return (SystemDictEntity) super.findEntityByHql(hql.toString(), paramList);
	}

	public List<SystemDictEntity> findListAllCompany() throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemDictEntity where deleteFlag != 'Y' ");
		return super.findListByHql(hql.toString(), paramList);
	}

	public List<SystemDictEntity> findList() throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemDictEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<SystemDictEntity> findList(SystemDictEntity dictEntity) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, dictEntity, paramList);
		hql.append(" order by orderCode");
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public PageGrid<SystemDictEntity> findPageList(PageGrid<SystemDictEntity> pageGrid) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SystemDictEntity dictEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, dictEntity, paramList);
		return super.findPageListByHql(hql.toString(), paramList, pageGrid);
	}
	
	/**
	 * list与分页查询,公共方法
	 * @param hql
	 * @param entity
	 * @param paramList
	 * @return
	 */
	private StringBuffer pubQueryConditions(StringBuffer hql, SystemDictEntity dictEntity, List<Object> paramList){
		hql.append("from SystemDictEntity where deleteFlag != 'Y' and dictType = '1' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != dictEntity) {
			if (StringUtils.isNotBlank(dictEntity.getDictCode())) {
				hql.append(" and dictCode like ? ");
				paramList.add("%" + dictEntity.getDictCode() + "%");
			}
			if (StringUtils.isNotBlank(dictEntity.getDictName())) {
				hql.append(" and dictName like ? ");
				paramList.add("%" + dictEntity.getDictName() + "%");
			}
		}
		return hql;
	}
	
	/**
	 * @param parentDictCode
	 * @param enableFlag 是否查询启用的数据字典,查询之前先判断父数据字典是否启用
	 * @return
	 * @throws Exception
	 */
	public List<SystemDictEntity> findListByParentDictCode(String parentDictCode, boolean enableFlag) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		if(enableFlag){
			SystemDictEntity dictEntity = this.findByDictCode(parentDictCode);
			if(null != dictEntity && "Y".equals(dictEntity.getEnableFlag())){
				hql.append("from SystemDictEntity where deleteFlag != 'Y' and parentDictCode = ? and companyId = ? and enableFlag = 'Y' order by orderCode ");
				paramList.add(parentDictCode);
				paramList.add(UserContext.get().getCompanyId());
				return super.findListByHql(hql.toString(), paramList);
			}else{
				return new ArrayList<SystemDictEntity>();
			}
		}else{
			hql.append("from SystemDictEntity where deleteFlag != 'Y' and parentDictCode = ? and companyId = ? order by orderCode ");
			paramList.add(parentDictCode);
			paramList.add(UserContext.get().getCompanyId());
			return super.findListByHql(hql.toString(), paramList);
		}
	}
	
	public void updateParentDictCode(String oldDictCode, String dictCode) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("update SystemDictEntity set parentDictCode = ? where parentDictCode = ? and companyId = ? ");
		paramList.add(dictCode);
		paramList.add(oldDictCode);
		paramList.add(UserContext.get().getCompanyId());
		super.executeUpdateByHql(hql.toString(), paramList);
	}

	public boolean checkDictCode(String dictCode, Long dictId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemDictEntity where dictCode = ? and companyId = ? ");
		paramList.add(dictCode);
		paramList.add(UserContext.get().getCompanyId());
		if(null != dictId && dictId > 0){
			hql.append(" and dictId != ? ");
			paramList.add(dictId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}
	
	public void deleteByDictCode(String dictCode) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete SystemDictEntity where dictCode = ? and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		paramList.add(dictCode);
		super.executeUpdateByHql(hql.toString(), paramList);
	}
}
