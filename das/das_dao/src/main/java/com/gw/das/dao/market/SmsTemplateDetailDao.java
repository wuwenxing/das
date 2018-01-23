package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.SmsTemplateDetailEntity;

@Repository
public class SmsTemplateDetailDao extends BaseDao {
	
	/**
	 * 因smsid和phone联合是唯一
	 * 注：此方法不需过滤companyId
	 * @param smsid
	 * @param phone
	 * @param statusEnum
	 * @return
	 * @throws Exception
	 */
	public int updateSendStatus(String smsid, String phone, String resCode, SendStatusEnum statusEnum) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("update SmsTemplateDetailEntity set sendStatus=?, resCode=?, updateDate = ? where smsid=? and phone=?");
		paramList.add(statusEnum.getLabelKey());
		paramList.add(resCode);
		paramList.add(new Date());
		paramList.add(smsid);
		paramList.add(phone);
		return super.executeUpdateByHql(hql.toString(), paramList);
	}
	
	public SmsTemplateDetailEntity findOne(Long detailId, String account, Long companyId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SmsTemplateDetailEntity where detailId = ? and phone = ? and companyId = ? ");
		paramList.add(detailId);
		paramList.add(account);
		paramList.add(companyId);
		return (SmsTemplateDetailEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
	public List<SmsTemplateDetailEntity> findList(SmsTemplateDetailEntity smsTemplateDetailEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, smsTemplateDetailEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SmsTemplateDetailEntity> findPageList(PageGrid<SmsTemplateDetailEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SmsTemplateDetailEntity smsTemplateDetailEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, smsTemplateDetailEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, SmsTemplateDetailEntity smsTemplateDetailEntity, List<Object> paramList) {
		hql.append("from SmsTemplateDetailEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != smsTemplateDetailEntity) {
			if (null != smsTemplateDetailEntity.getTemplateId()) {
				hql.append(" and templateId = ? ");
				paramList.add(smsTemplateDetailEntity.getTemplateId());
			}
			if (StringUtils.isNotBlank(smsTemplateDetailEntity.getCommitStatus())) {
				hql.append(" and commitStatus = ? ");
				paramList.add(smsTemplateDetailEntity.getCommitStatus());
			}
			if (StringUtils.isNotBlank(smsTemplateDetailEntity.getSendStatus())) {
				hql.append(" and sendStatus = ? ");
				paramList.add(smsTemplateDetailEntity.getSendStatus());
			}
			if (StringUtils.isNotBlank(smsTemplateDetailEntity.getPhone())) {
				hql.append(" and phone like ? ");
				paramList.add("%" + smsTemplateDetailEntity.getPhone() + "%");
			}
			if(null != smsTemplateDetailEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(smsTemplateDetailEntity.getStartDate());
			}
			if(null != smsTemplateDetailEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(smsTemplateDetailEntity.getEndDate());
			}
		}
		return hql;
	}

	/**
	 * 根据条件删除
	 */
	public void deleteByCon(Long templateId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete SmsTemplateDetailEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != templateId) {
			hql.append(" and templateId = ? ");
			paramList.add(templateId);
		}else{
			throw new Exception("删除条件错误");
		}
		super.executeUpdateByHql(hql.toString(), paramList);
	}

	/**
	 * 根据发送人账户获取某段时间内的短信发送记录
	 * @param templateCode
	 * @param sendNo
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> findList(String templateIds, String sendNo, String startTime, String endTime) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		sql.append("select phone, count(phone) as sendNum from t_sms_template_detail where company_id = ? ");
		paramList.add(UserContext.get().getCompanyId());
		
		if(StringUtils.isNotBlank(templateIds)){
			List<Long> idsArray = new ArrayList<Long>();
			String[] ids = templateIds.split(",");
			for(int i=0; i<ids.length; i++){
				if(StringUtils.isNotBlank(ids[i])){
					Long id = Long.parseLong(ids[i]);
					idsArray.add(id);
				}
			}
			
			if(idsArray.size() == 1){
				sql.append(" and template_id = ? ");
				paramList.add(idsArray.get(0));
			}else if(idsArray.size() > 1){
				sql.append(" and template_id in ( ");
				for(int i=0; i<idsArray.size(); i++){
					if((i+1) == idsArray.size()){
						sql.append(" ? ");
					}else{
						sql.append(" ?, ");
					}
					paramList.add(idsArray.get(i));
				}
				sql.append(" ) ");
			}
		}
		
		if(StringUtils.isNotBlank(sendNo)){
			sql.append(" and send_no = ? ");
			paramList.add(sendNo);
		}
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and create_date >= ? ");
			paramList.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and create_date <= ? ");
			paramList.add(endTime);
		}
		sql.append(" group by phone");
		List<Map<String,String>> list = super.findListBySql(sql.toString(), paramList, "phone", "sendNum");
		return list;
	}
	
}
