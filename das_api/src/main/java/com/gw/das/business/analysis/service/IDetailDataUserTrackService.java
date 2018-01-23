package com.gw.das.business.analysis.service;

import java.util.List;

import com.gw.das.business.dao.website.entity.DasFlowDetail;
/**
 * 详细汇总数据接口
 * @author kirin.guan
 *
 */
public interface IDetailDataUserTrackService {
	/**
	 * 获取未归因数据
	 * @param time（yyyy-MM-DD）
	 * @param isAttributed（0：未归因 1：归因）
	 * @return
	 */
	public List<DasFlowDetail> getAttributedDataList(String time,String isAttributed,int businessPlatform) throws Exception;
	
	/**
     * 根据用户id 获取该用户的第一次访问数据(时间最早的一次)
     * @return
     */
    public DasFlowDetail getVisitFirstData(DasFlowDetail model) throws Exception;
    
    /**
     * 获取第一次访问到开户成功后的的数据 除去第一次访问
     * @param rowKey
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<DasFlowDetail> queryDataDetailByUserIdAndStartEndTime(String rowKey,String userId,String startTime,String endTime,int businessPlatform) throws Exception;
    
    /**
     * 根据用户id 获取用户在结束时间之前的第一次模拟开户
     * @param userId 用户ID
     * @param time 时间
     * @return
     */
    public DasFlowDetail getFirstDemoDataDetail(String userId,String time,int businessPlatform) throws Exception;
    
    /**
     * 批量更新是否归因字段
     * @param list
     */
    public void batchUpdateIsAttributed(List<DasFlowDetail> list) throws Exception;
}
