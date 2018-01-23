package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.DashboardUserEntity;
import com.gw.das.dao.system.entity.SystemUserEntity;

@Repository
public class DashboardUserDao extends BaseDao {

//	private static String deleteSQL_dashboard_board = "delete from dashboard_board where user_id = ?";
//	private static String deleteSQL_dashboard_category = "delete from dashboard_category where user_id = ?";
//	private static String deleteSQL_dashboard_dataset = "delete from dashboard_dataset where user_id = ?";
//	private static String deleteSQL_dashboard_datasource = "delete from dashboard_datasource where user_id = ?";
//	private static String deleteSQL_dashboard_widget = "delete from dashboard_widget where user_id = ?";
	private static String deleteSQL_dashboard_role_res = "delete from dashboard_role_res where role_id in(select role_id from dashboard_user_role where user_id = ?)";
	private static String deleteSQL_dashboard_role = "delete from dashboard_role where user_id = ?";
	private static String deleteSQL_dashboard_user_role = "delete from dashboard_user_role where user_id = ?";
	private static String deleteSQL_dashboard_user = "delete from dashboard_user where user_id = ?";

	/**
	 * 同步保存CBoard用户
	 * 
	 * @param systemUserEntity
	 * @throws Exception
	 */
	public void saveOrUpdate(SystemUserEntity systemUserEntity) throws Exception {
		DashboardUserEntity user = (DashboardUserEntity) this.getCurrentSession().get(DashboardUserEntity.class,
				systemUserEntity.getUserId() + "");
		if (null == user) {
			user = new DashboardUserEntity();
			user.setUserId(systemUserEntity.getUserId() + "");
			user.setLoginName(systemUserEntity.getUserNo());
			user.setUserName(systemUserEntity.getUserName());
			user.setUserPassword(systemUserEntity.getPassword());
			user.setUserStatus("Y".equals(systemUserEntity.getEnableFlag()) ? "1" : "0");
			user.setCompanyIds(systemUserEntity.getCompanyIds());
			this.getCurrentSession().save(user);
		} else {
			user.setUserId(systemUserEntity.getUserId() + "");
			user.setLoginName(systemUserEntity.getUserNo());
			user.setUserName(systemUserEntity.getUserName());
			user.setUserPassword(systemUserEntity.getPassword());
			user.setUserStatus("Y".equals(systemUserEntity.getEnableFlag()) ? "1" : "0");
			user.setCompanyIds(systemUserEntity.getCompanyIds());
			this.getCurrentSession().update(user);
		}
	}

	/**
	 * 删除CBoard用户,同时删除相关关联信息
	 * 
	 * @param systemUserEntity
	 * @throws Exception
	 */
	public void delete(String idArray) throws Exception {
		String[] ids = idArray.split(",");
		for (int i = 0; i < ids.length; i++) {
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(ids[i]);
//			this.executeUpdateBySql(deleteSQL_dashboard_board, paramList);
//			this.executeUpdateBySql(deleteSQL_dashboard_category, paramList);
//			this.executeUpdateBySql(deleteSQL_dashboard_dataset, paramList);
//			this.executeUpdateBySql(deleteSQL_dashboard_datasource, paramList);
//			this.executeUpdateBySql(deleteSQL_dashboard_widget, paramList);
			this.executeUpdateBySql(deleteSQL_dashboard_role_res, paramList);
			this.executeUpdateBySql(deleteSQL_dashboard_role, paramList);
			this.executeUpdateBySql(deleteSQL_dashboard_user_role, paramList);
			this.executeUpdateBySql(deleteSQL_dashboard_user, paramList);
		}
	}

}
