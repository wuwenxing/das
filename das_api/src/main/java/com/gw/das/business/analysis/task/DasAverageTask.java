package com.gw.das.business.analysis.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gw.das.business.analysis.service.IDataAverageUserTrackService;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.website.entity.DasFlowMonthAverage;

/**
 * 平均值计算任务
 * @author kirin.guan
 *
 */
@Component
public class DasAverageTask {
	private static final Logger logger = LoggerFactory.getLogger(DasAverageTask.class);
	
	@Resource
	private IDataAverageUserTrackService  iDataAverageUserTrackService;
	
	//@Scheduled(cron = "0 40 18 * * ?")
	//@Scheduled(cron = "0 10 0 23 * ?")
	//@Scheduled(cron = "0/5 * * * * ?")
	//@Scheduled(cron = "0 10 0 1 * ?")
    public void initDate(){
		logger.info("DasAverageTask开始-->");
		
		long start = System.currentTimeMillis();
		try{
			//后60分钟的数据进行统计   当前默认下一月数据读取
			Date  halfTime=DateUtil.addMonths(new Date(), -1);
			String year=DateUtil.getDateFormat(halfTime, "yyyy");
			String mouth=DateUtil.getDateFormat(halfTime, "MM");
			
			//对求和后的数据 进行 对上个月的天数 求平均值 
			List<DasFlowMonthAverage> old_list=iDataAverageUserTrackService.getSourceAverageDataByTime(year,mouth);
			
			//计算平均数据
			Map<String, DasFlowMonthAverage> base_map = iDataAverageUserTrackService.calculateAverageData(year,mouth);
			
			List<DasFlowMonthAverage> update_items=new ArrayList<DasFlowMonthAverage>();
			
			//循环已算的旧数据。  区分出那些数据插入，更新
			for (DasFlowMonthAverage old : old_list) {
				if(old==null)continue;
				String key = old.getUtmcmd() + "_" + old.getUtmcsr() + "_" +
						old.getDataTime() + "_" + old.getBusinessPlatform() + "_" + 
						old.getPlatformType();
				
				DasFlowMonthAverage newObj = base_map.get(key);
				
				if(newObj != null){//要对比 是否值有变化，进行更新操作  那对应KEY的地方设置成NULL     map剩下的数据 都是插入
					DasFlowMonthAverage updateObj=contrastObj(old,newObj);
					if(updateObj!=null){
						//update
						update_items.add(old);
					}
					base_map.remove(key);
				}
			}
			
			
			logger.info("update_items-->" + update_items.size());
			logger.info("base_map-->" + base_map.size());
		     
			//循环update
			for (DasFlowMonthAverage dasFlowMonthAverage : update_items) {
				iDataAverageUserTrackService.update(dasFlowMonthAverage);
			}
			
			//循环insert
			Iterator<String> it=base_map.keySet().iterator();
			while(it.hasNext()){
				String key=it.next();
				DasFlowMonthAverage insetObj=base_map.get(key);
				if(insetObj!=null){
					iDataAverageUserTrackService.insert(insetObj);
				}
			}
	        
		}catch(Exception e){
			logger.error("执行DasAverageTask出错,错误原因：" + e.getMessage());
		}
		logger.info("DasAverageTask结束，执行时间(s)：-->" + (System.currentTimeMillis()-start)/1000);
	}
	
	
	/**
	 * 前后对象对比。
	 * 把有修改的值放入 old对象里面 返回其对象 保留id 做更新 old->o new->n
	 * @param o
	 * @param n
	 * @return
	 */
	private DasFlowMonthAverage  contrastObj(DasFlowMonthAverage o,DasFlowMonthAverage n){
		Boolean update=false;
		
		if(o.getAdvisoryLive800AvgCount() < n.getAdvisoryLive800AvgCount()){
			o.setAdvisoryLive800AvgCount(n.getAdvisoryLive800AvgCount());
			update=true;
		}
		
		if(o.getAdvisoryQQAvgCount() < n.getAdvisoryQQAvgCount()){
			o.setAdvisoryQQAvgCount(n.getAdvisoryQQAvgCount());
			update=true;
		}
		
		
		if(o.getDemoAvgCount() < n.getDemoAvgCount()){
			o.setDemoAvgCount(n.getDemoAvgCount());
			update=true;
		}
		
		if(o.getDepositAvgCount() < n.getDepositAvgCount()){
			o.setDepositAvgCount(n.getDepositAvgCount());
			update=true;
		}
		
		if(o.getRealAvgCount() < n.getRealAvgCount()){
			o.setRealAvgCount(n.getRealAvgCount());
			update=true;
		}
		
		if(o.getVisitAvgCount() < n.getVisitAvgCount()){
			o.setVisitAvgCount(n.getVisitAvgCount());
			update=true;
		}
		
		if(update)
			return o ;
		else 
			return null;
	}
}
