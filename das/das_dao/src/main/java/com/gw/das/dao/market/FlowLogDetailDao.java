package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.FlowStatusEnum;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.FlowLogDetailEntity;

@Repository
public class FlowLogDetailDao extends BaseDao {
	
	public List<FlowLogDetailEntity> findList(FlowLogDetailEntity flowLogDetailEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, flowLogDetailEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<FlowLogDetailEntity> findPageList(PageGrid<FlowLogDetailEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		FlowLogDetailEntity flowLogDetailEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, flowLogDetailEntity, paramList);
		return super.findPageListByHql(hql.toString(), paramList, pageGrid);
	}

	/**
	 * list与分页查询,公共方法
	 * 
	 * @param hql
	 * @param entity
	 * @param paramList
	 * @return
	 */
	private StringBuffer pubQueryConditions(StringBuffer hql, FlowLogDetailEntity flowLogDetailEntity, List<Object> paramList) {
		hql.append("from FlowLogDetailEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != flowLogDetailEntity) {
			if (null != flowLogDetailEntity.getFlowLogId()) {
				hql.append(" and flowLogId = ? ");
				paramList.add(flowLogDetailEntity.getFlowLogId());
			}
			if (StringUtils.isNotBlank(flowLogDetailEntity.getCommitStatus())) {
				hql.append(" and commitStatus = ? ");
				paramList.add(flowLogDetailEntity.getCommitStatus());
			}
			if (StringUtils.isNotBlank(flowLogDetailEntity.getSendStatus())) {
				hql.append(" and sendStatus = ? ");
				paramList.add(flowLogDetailEntity.getSendStatus());
			}
			if (StringUtils.isNotBlank(flowLogDetailEntity.getPhone())) {
				hql.append(" and phone like ? ");
				paramList.add("%" + flowLogDetailEntity.getPhone() + "%");
			}
			if(null != flowLogDetailEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(flowLogDetailEntity.getStartDate());
			}
			if(null != flowLogDetailEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(flowLogDetailEntity.getEndDate());
			}
		}
		return hql;
	}

	/**
	 * 根据条件删除
	 */
	public void deleteByCon(Long flowLogId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete FlowLogDetailEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != flowLogId) {
			hql.append(" and templflowLogIdateId = ? ");
			paramList.add(flowLogId);
		}else{
			throw new Exception("删除条件错误");
		}
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
	/**
	 * 回调更新流量充值状态
	 * 注：此方法不需过滤companyId
	 * @param batchNo
	 * @param phone
	 * @param code
	 * @param sendStatusEnum
	 * @throws Exception
	 */
	public int updateFlowSendStatus(String batchNo, String phone, String code, FlowStatusEnum sendStatusEnum)
			throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("update FlowLogDetailEntity set resCode = ?, sendStatus = ?, updateDate = ? "
				+ "where resBatchNo = ? and phone = ? ");
		paramList.add(code);
		paramList.add(sendStatusEnum.getLabelKey());
		paramList.add(new Date());
		paramList.add(batchNo);
		paramList.add(phone);
		return super.executeUpdateByHql(hql.toString(), paramList);
	}
	
}
