package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.SystemUserEntity;

@Repository
public class SystemUserDao extends BaseDao {

	public SystemUserEntity findByUserNo(String userNo) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemUserEntity where userNo = ? ");
		paramList.add(userNo);
		return (SystemUserEntity) super.findEntityByHql(hql.toString(), paramList);
	}

	public List<SystemUserEntity> findList(SystemUserEntity userEntity, String companyIds) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, userEntity, companyIds, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SystemUserEntity> findPageList(PageGrid<SystemUserEntity> pageGrid, String companyIds) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SystemUserEntity userEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, userEntity, companyIds, paramList);
		return super.findPageListByHql(hql.toString(), paramList, pageGrid);
	}
	
	/**
	 * list与分页查询,公共方法
	 * @param hql
	 * @param entity
	 * @param paramList
	 * @return
	 */
	private StringBuffer pubQueryConditions(StringBuffer hql, SystemUserEntity userEntity, String companyIds, List<Object> paramList){
		hql.append("from SystemUserEntity where deleteFlag != 'Y' ");
		if(!Constants.superAdmin.equals(UserContext.get().getLoginNo())){
			// 不为超级管理员，则不展示超级管理员这个用户
			hql.append(" and userNo !=  ? ");
			paramList.add(Constants.superAdmin);
			// 不为超级管理员，需要过滤数据，只能看到自己包含的业务权限数据
			if(StringUtils.isNotBlank(companyIds)){
				String[] companyIdAry = companyIds.split(",");
				if(companyIdAry.length > 0){
					List<String> companyIdList = new ArrayList<String>();
					for(int i=0; i<companyIdAry.length; i++){
						String companyId = companyIdAry[i];
						if(StringUtils.isNotBlank(companyId)){
							companyIdList.add(companyId);
						}
					}
					hql.append(" and ( ");
					for(int i=0; i<companyIdList.size(); i++){
						String companyId = companyIdList.get(i);
						if(i == 0){
							hql.append(" companyIds like ? ");
						}else{
							hql.append(" or companyIds like ? ");
						}
						paramList.add("%" + companyId + "%");
					}
					hql.append(" ) ");
				}
			}
		}
		if (null != userEntity) {
			if (StringUtils.isNotBlank(userEntity.getUserNo())) {
				hql.append(" and userNo like ? ");
				paramList.add("%" + userEntity.getUserNo() + "%");
			}
			if (StringUtils.isNotBlank(userEntity.getUserName())) {
				hql.append(" and userName like ? ");
				paramList.add("%" + userEntity.getUserName() + "%");
			}
			if (StringUtils.isNotBlank(userEntity.getEnableFlag())) {
				hql.append(" and enableFlag = ? ");
				paramList.add(userEntity.getEnableFlag());
			}
			if(null != userEntity.getMenuId() && userEntity.getMenuId() > 0){
				hql.append(" and userId in (select userId from SystemMenuUserEntity where menuId = ?) ");
				paramList.add(userEntity.getMenuId());
			}
			// 页面查询条件
			if (null != userEntity.getCompanyId()) {
				hql.append(" and companyIds like ? ");
				paramList.add("%" + userEntity.getCompanyId() + "%");
			}
			// 如果当前登录人不是超级管理员，则根据当前登录人，目前所选择的业务类型，进行过滤
			if(!Constants.superAdmin.equals(UserContext.get().getLoginNo())){
				hql.append(" and companyIds like ? ");
				paramList.add("%" + UserContext.get().getCompanyId() + "%");
			}
		}
		return hql;
	}
	
	public boolean checkUserNo(String userNo, Long userId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemUserEntity where userNo = ?");
		paramList.add(userNo);
		if (null != userId && userId > 0) {
			hql.append(" and userId != ? ");
			paramList.add(userId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}

	/**
	 * 根据角色Id，查询该角色的用户
	 */
	public List<SystemUserEntity> findUserListByRoleId(Long roleId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemUserEntity where roleId = ?");
		paramList.add(roleId);
		return super.findListByHql(hql.toString(), paramList);
	}

	/**
	 * 更新角色数据
	 */
	public void updateRoleIdByUserIdArray(Long roleId, String selectIds, String unSelectIds) throws Exception {
		// 1、先将对应的unSelectIds设置为空roleId
		if (StringUtils.isNotBlank(unSelectIds)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			StringBuffer hql = new StringBuffer("");
			hql.append("update SystemUserEntity set roleId = :roleId where userId in (:unSelectUserIdList) "
					+ "and companyId = :companyId ");
			paramMap.put("roleId", null);
			List<Long> unSelectUserIdList = new ArrayList<Long>();
			String[] unSelectUserIdAry = unSelectIds.split(",");
			for (String unSelectUserIdStr : unSelectUserIdAry) {
				unSelectUserIdList.add(Long.parseLong(unSelectUserIdStr));
			}
			paramMap.put("unSelectUserIdList", unSelectUserIdList);
			paramMap.put("companyId", UserContext.get().getCompanyId());
			super.executeUpdateByHql(hql.toString(), paramMap);
		}
		// 2、在将对应的roleId设置到对应的selectIds用户
		if (StringUtils.isNotBlank(selectIds)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			StringBuffer hql = new StringBuffer("");
			hql.append("update SystemUserEntity set roleId = :roleId where userId in (:selectUserIdList) "
					+ "and companyId = :companyId ");
			paramMap.put("roleId", roleId);
			List<Long> selectUserIdList = new ArrayList<Long>();
			String[] selectUserIdAry = selectIds.split(",");
			for (String selectUserIdStr : selectUserIdAry) {
				selectUserIdList.add(Long.parseLong(selectUserIdStr));
			}
			paramMap.put("selectUserIdList", selectUserIdList);
			paramMap.put("companyId", UserContext.get().getCompanyId());
			super.executeUpdateByHql(hql.toString(), paramMap);
		}
	}
	
	/**
	 * 返回有权限的菜单集合
	 * Map<key=user_id+","+menu_id, user_no>
	 * @param companyId
	 * @return
	 */
	public Map<String, String> findUserMenuMap(){
		Map<String, String> map = new HashMap<String, String>();
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(UserContext.get().getCompanyId());
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT a.user_id, a.menu_id, a.company_id, concat(a.user_id, ',', a.menu_id) as k, b.user_no ");
		sql.append(" FROM t_system_menu_user a, t_system_user b, t_system_menu c ");
		sql.append(" where a.user_id = b.user_id ");
		sql.append(" and a.menu_id = c.menu_id ");
		sql.append(" and a.company_id = c.company_id ");
		sql.append(" and a.company_id = ? ");
		List<Object[]> list = super.findListBySql(sql.toString(), paramList);
		if(null != list&&list.size()>0){
			for(int i=0; i<list.size(); i++){
				Object[] obj = list.get(i);
				String key = obj[3]==null?"":obj[3]+"";
				String value = obj[4]==null?"":obj[4]+"";
				map.put(key, value);
			}
		}
		return map;
	}
	
}
