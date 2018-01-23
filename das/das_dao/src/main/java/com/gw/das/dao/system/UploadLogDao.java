package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.UploadLogEntity;

@Repository
public class UploadLogDao extends BaseDao {
	
	public List<UploadLogEntity> findList(UploadLogEntity uploadLogEntity) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, uploadLogEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public PageGrid<UploadLogEntity> findPageList(PageGrid<UploadLogEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		UploadLogEntity uploadLogEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, uploadLogEntity, paramList);
		return super.findPageListByHql(hql.toString(), paramList, pageGrid);
	}
	
	/**
	 * list与分页查询,公共方法
	 * @param hql
	 * @param entity
	 * @param paramList
	 * @return
	 */
	private StringBuffer pubQueryConditions(StringBuffer hql, UploadLogEntity uploadLogEntity, List<Object> paramList){
		hql.append("from UploadLogEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != uploadLogEntity) {
			if(StringUtils.isNotBlank(uploadLogEntity.getFileType())){
				hql.append(" and fileType = ? ");
				paramList.add(uploadLogEntity.getFileType());
			}
			if(StringUtils.isNotBlank(uploadLogEntity.getFileName())){
				hql.append(" and fileName like ? ");
				paramList.add("%" + uploadLogEntity.getFileName() + "%");
			}
			if(StringUtils.isNotBlank(uploadLogEntity.getFileUrl())){
				hql.append(" and fileUrl like ? ");
				paramList.add("%" + uploadLogEntity.getFileUrl() + "%");
			}
			if(StringUtils.isNotBlank(uploadLogEntity.getFilePath())){
				hql.append(" and filePath like ? ");
				paramList.add("%" + uploadLogEntity.getFilePath() + "%");
			}
			if(null != uploadLogEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(uploadLogEntity.getStartDate());
			}
			if(null != uploadLogEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(uploadLogEntity.getEndDate());
			}
		}
		return hql;
	}
	
}
