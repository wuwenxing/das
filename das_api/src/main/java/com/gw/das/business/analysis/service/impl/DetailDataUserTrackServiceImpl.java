package com.gw.das.business.analysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.business.analysis.service.IDetailDataUserTrackService;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.base.ImpalaDao;
import com.gw.das.business.dao.website.entity.DasFlowDetail;
/**
 * 详细汇总业务实现
 * @author kirin.guan
 *
 */
@Service
public class DetailDataUserTrackServiceImpl implements IDetailDataUserTrackService{

	@Autowired
	private ImpalaDao impalaDao;
	
	/**
	 * 获取未归因数据
	 * @param time（yyyy-MM-DD）
	 * @param isAttributed（0：未归因 1：归因）
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<DasFlowDetail> getAttributedDataList(String time,String isAttributed,int businessPlatform) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from das_flow_detail where 1 = 1 ");
		sql.append(" and isattributed = '" +  isAttributed + "'");
		sql.append(" and businessPlatform = '" +  businessPlatform + "'");
		sql.append(" and formatTime = '" + time + "'");
		sql.append(" and partitionField = '" +  DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(time)) + "'");
		sql.append(" and (behaviorType = '3' or behaviorType = '4')");
		sql.append(" order by starttime desc");
		List<Map<String, Object>> list = impalaDao.queryForList(sql.toString());
		
		List<DasFlowDetail> detailList = OrmUtil.reflectList(DasFlowDetail.class, list);
		return detailList;
	}

	/**
     * 根据用户id 获取该用户的第一次访问数据(时间最早的一次 最早30天内)
     * @param userId 用户ID
     * @return
	 * @throws Exception 
     */
	@Override
	public DasFlowDetail getVisitFirstData(DasFlowDetail model) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from das_flow_detail where 1 = 1 ");
		sql.append(" and userId = '" +  model.getUserId() + "'");
		sql.append(" and businessPlatform = '" +  model.getBusinessPlatform() + "'");
		sql.append(" and starttime <= '" +  DateUtil.getDateFormat(model.getStartTime(),"yyyy-MM-dd HH:mm:ss") + "'");
		sql.append(" and partitionField <= '" +  DateUtil.getDateFormat(model.getStartTime(),"yyyy-MM") + "'");
		sql.append(" and starttime >= '" +  DateUtil.getDateFormat(DateUtil.addDays(model.getStartTime(), -30),"yyyy-MM-dd HH:mm:ss") + "'");
		sql.append(" and partitionField >= '" +  DateUtil.getDateFormat(DateUtil.addDays(model.getStartTime(), -30),"yyyy-MM") + "'");
		//sql.append(" and behaviorType = '" +  1 + "'");
		sql.append(" order by starttime asc");
		sql.append(" limit " + 1 + " offset " + 0);
		
		Map<String, Object> map = impalaDao.queryForMap(sql.toString());
		if(map == null) return null;
		
		DasFlowDetail dasFlowDetail = OrmUtil.reflect(DasFlowDetail.class, map);
		return dasFlowDetail;
	}

	/**
     * 获取第一次访问到开户成功后的的数据 除去第一次访问
     * @param rowKey
     * @param userId
     * @param startTime
     * @param endTime
     * @return
	 * @throws Exception 
     */
	@Override
	public List<DasFlowDetail> queryDataDetailByUserIdAndStartEndTime(
			String rowKey, String userId, String startTime, String endTime,int businessPlatform) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from das_flow_detail where 1 = 1 ");
		sql.append(" and userId = '" +  userId + "'");
		sql.append(" and rowKey = '" +  rowKey + "'");
		sql.append(" and businessPlatform = '" +  businessPlatform + "'");
		sql.append(" and starttime < '" +  endTime + "'");
		sql.append(" and partitionField < '" +  DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(endTime)) + "'");
		sql.append(" and starttime >= '" +  startTime + "'");
		sql.append(" and partitionField >= '" +  DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(startTime)) + "'");
		sql.append(" order by starttime asc");
		
		List<Map<String, Object>> list = impalaDao.queryForList(sql.toString());
		
		
		List<DasFlowDetail> detailList = OrmUtil.reflectList(DasFlowDetail.class, list);
		return detailList;
	}

	 /**
     * 根据用户id 获取用户在结束时间之前的第一次模拟开户
     * @param userId 用户ID
     * @param time 时间
     * @return
	 * @throws Exception 
     */
	@Override
	public DasFlowDetail getFirstDemoDataDetail(String userId, String time,int businessPlatform) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from das_flow_detail where 1 = 1 ");
		sql.append(" and userId = '" +  userId + "'");
		sql.append(" and behaviorType = '" +  3 + "'");
		sql.append(" and businessPlatform = '" +  businessPlatform + "'");
		sql.append(" and starttime <= '" +  time + "'");
		sql.append(" and partitionField <= '" +  DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(time)) + "'");
		sql.append(" order by starttime desc");
		sql.append(" limit " + 1 + " offset " + 0);
		Map<String, Object> map = impalaDao.queryForMap(sql.toString());
		if(map == null) return null;
		
		DasFlowDetail dasFlowDetail = OrmUtil.reflect(DasFlowDetail.class, map);
		return dasFlowDetail;
	}

	 /**
     * 批量更新是否归因字段
     * @param list
     */
	@Override
	public void batchUpdateIsAttributed(List<DasFlowDetail> list) throws Exception{
		for (DasFlowDetail dasFlowDetail : list) {
			StringBuffer sql = new StringBuffer();
//			sql.append("insert into rdd_das_flow_detail ");
//			sql.append(" (");
//			sql.append(" rowkey,userid,starttime,endtime,formattime,timelength,");
//			sql.append(" behaviorDetail,behaviorType,ip,ipHome,utmcsr,utmctr,markWords,utmccn,utmcct,utmcmd,");
//			sql.append(" visitCount,isSend,platformType,businessPlatform,isAttributed,operationTel,operationEmail,advisoryType,");
//			sql.append(" operationDescription,gender,birthDate,guestName,browser");
//			sql.append(" ) ");
//			sql.append(" values ");
//			sql.append(" (");
//			//6
//			sql.append("'" + dasFlowDetail.getRowKey() +"',");
//			sql.append("'" + dasFlowDetail.getUserId() +"',");
//			sql.append("'" + DateUtil.formatDateToString(dasFlowDetail.getStartTime(),"yyyy-MM-dd HH:mm:ss") +"',");
//			sql.append("'" + DateUtil.formatDateToString(dasFlowDetail.getEndTime(),"yyyy-MM-dd HH:mm:ss") +"',");
//			sql.append("'" + dasFlowDetail.getFormatTime() +"',");
//			sql.append("'" + dasFlowDetail.getTimeLength() +"',");
//			//10
//			sql.append("'" + dasFlowDetail.getBehaviorDetail() +"',");
//			sql.append("'" + dasFlowDetail.getBehaviorType() +"',");
//			sql.append("'" + dasFlowDetail.getIp() +"',");
//			sql.append("'" + dasFlowDetail.getIpHomeJson() +"',");
//			sql.append("'" + dasFlowDetail.getUtmcsr() +"',");
//			sql.append("'" + dasFlowDetail.getUtmctr() +"',");
//			sql.append("'" + dasFlowDetail.getMarkWords() +"',");
//			sql.append("'" + dasFlowDetail.getUtmccn() +"',");
//			sql.append("'" + dasFlowDetail.getUtmcct() +"',");
//			sql.append("'" + dasFlowDetail.getUtmcmd() +"',");
//			//8
//			sql.append("" + dasFlowDetail.getVisitCount() +","); //int
//			sql.append("'" + dasFlowDetail.getIsSend() +"',");
//			sql.append("'" + dasFlowDetail.getPlatformType() +"',");
//			sql.append("'" + dasFlowDetail.getBusinessPlatform() +"',");
//			sql.append("'" + dasFlowDetail.getIsAttributed() +"',");
//			sql.append("'" + dasFlowDetail.getOperationTel() +"',");
//			sql.append("'" + dasFlowDetail.getOperationEmail() +"',");
//			sql.append("'" + dasFlowDetail.getAdvisoryType() +"',");
//			//5
//			sql.append("'" + dasFlowDetail.getOperationDescription() +"',");
//			sql.append("'" + dasFlowDetail.getGender() +"',");
//			sql.append("'" + DateUtil.formatDateToString(dasFlowDetail.getBirthDate()) +"',");
//			sql.append("'" + dasFlowDetail.getGuestName() +"',");
//			sql.append("'" + dasFlowDetail.getBrowser() +"'");
//			sql.append(") ");
			
			
			sql.append("insert into rdd_das_flow_detail ");
			sql.append(" (");
			sql.append(" rowkey,isAttributed");
			sql.append(" ) ");
			sql.append(" values ");
			sql.append(" (");
			
			sql.append("'" + dasFlowDetail.getRowKey() +"',");
			sql.append("'" + dasFlowDetail.getIsAttributed() +"'");
			sql.append(") ");
			
			impalaDao.insertOrUpdate(sql.toString());
		}
	}
}
