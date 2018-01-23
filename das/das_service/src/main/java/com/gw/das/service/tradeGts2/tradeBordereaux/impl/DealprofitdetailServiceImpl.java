package com.gw.das.service.tradeGts2.tradeBordereaux.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.DetailedEnum;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.PlatformtypeEnum;
import com.gw.das.common.enums.weekDaysEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.dao.trade.bean.DealcategoryVO;
import com.gw.das.dao.trade.bean.DealprofitdetailMT4AndGts2VO;
import com.gw.das.dao.trade.bean.DealprofitdetailUseraAndDealamountVO;
import com.gw.das.dao.trade.bean.DealprofitdetailVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.dao.trade.bean.TradeSituationVO;
import com.gw.das.dao.trade.entity.TradeIndexEntity;
import com.gw.das.service.trade.TradeIndexService;
import com.gw.das.service.tradeGts2.tradeBordereaux.DealprofitdetailService;

/**
 * 交易记录总结报表(包括了交易手数图表和毛利图表)接口
 * 
 * @author darren
 *
 */
@Service
public class DealprofitdetailServiceImpl implements DealprofitdetailService{
    
	@Autowired
	private TradeIndexService tradeIndexService;
	
	/**
	 * 分页查询交易人数/次数报表记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findDealprofitdetailUseraAndDealamountPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findDealprofitdetailUseraAndDealamountPageList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		Integer total = Integer.valueOf(resultMap.get("total"));

		List<DealprofitdetailUseraAndDealamountVO> rowsList = JacksonUtil.readValue(rows,new TypeReference<List<DealprofitdetailUseraAndDealamountVO>>() {});	
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		if(total > 0){
			//查总记录
			RpcResult rpcResultTotal = RpcUtils.post(Constants.findDealprofitdetailUseraAndDealamountList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
			String resultTotal = rpcResultTotal.getResult() + "";

			Map<String, String> resultMapTotal = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {});
			String rowsTotal = resultMapTotal.get("rows");
			List<DealprofitdetailUseraAndDealamountVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<DealprofitdetailUseraAndDealamountVO>>() {});
			
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());									

			Double mt4useramountTotal = 0.0;
			Double mt4dealamountTotal = 0.0;
			Double gts2useramountTotal = 0.0;
			Double gts2dealamountTotal = 0.0;
					
			for (DealprofitdetailUseraAndDealamountVO dealprofitdetailVO : rowsListTotal) {
				mt4useramountTotal += NumberUtil.StringToDouble(dealprofitdetailVO.getMt4useramount());
				mt4dealamountTotal += NumberUtil.StringToDouble(dealprofitdetailVO.getMt4dealamount());
				gts2useramountTotal += NumberUtil.StringToDouble(dealprofitdetailVO.getGts2useramount());
				gts2dealamountTotal += NumberUtil.StringToDouble(dealprofitdetailVO.getGts2dealamount());
			}
			DealprofitdetailUseraAndDealamountVO dealprofitdetailVOTotal = new DealprofitdetailUseraAndDealamountVO();
			dealprofitdetailVOTotal.setExectime("总数：");
			dealprofitdetailVOTotal.setPlatform(detail.getPlatformType());
			dealprofitdetailVOTotal.setUseramount(NumberUtil.NumberFormat(String.valueOf(mt4useramountTotal + gts2useramountTotal),businessplatform));
			dealprofitdetailVOTotal.setDealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamountTotal + gts2dealamountTotal),businessplatform));
			dealprofitdetailVOTotal.setMt4useramount(NumberUtil.NumberFormat(String.valueOf(mt4useramountTotal),businessplatform));
			dealprofitdetailVOTotal.setMt4dealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamountTotal),businessplatform));
			dealprofitdetailVOTotal.setGts2useramount(NumberUtil.NumberFormat(String.valueOf(gts2useramountTotal),businessplatform));
			dealprofitdetailVOTotal.setGts2dealamount(NumberUtil.NumberFormat(String.valueOf(gts2dealamountTotal),businessplatform));
			
			DealprofitdetailUseraAndDealamountVO dealprofitdetailVOMavg = new DealprofitdetailUseraAndDealamountVO();
			dealprofitdetailVOMavg.setExectime("平均：");
			dealprofitdetailVOMavg.setPlatform(detail.getPlatformType());
			dealprofitdetailVOMavg.setUseramount(NumberUtil.NumberFormat(String.valueOf((mt4useramountTotal + gts2useramountTotal)/total),businessplatform));
			dealprofitdetailVOMavg.setDealamount(NumberUtil.NumberFormat(String.valueOf((mt4dealamountTotal + gts2dealamountTotal)/total),businessplatform));
			dealprofitdetailVOMavg.setMt4useramount(NumberUtil.NumberFormat(String.valueOf(mt4useramountTotal/total),businessplatform));
			dealprofitdetailVOMavg.setMt4dealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamountTotal/total),businessplatform));
			dealprofitdetailVOMavg.setGts2useramount(NumberUtil.NumberFormat(String.valueOf(gts2useramountTotal/total),businessplatform));
			dealprofitdetailVOMavg.setGts2dealamount(NumberUtil.NumberFormat(String.valueOf(gts2dealamountTotal/total),businessplatform));		
			
			List<DealprofitdetailUseraAndDealamountVO> listFooter = new ArrayList<DealprofitdetailUseraAndDealamountVO>();			
			listFooter.add(dealprofitdetailVOTotal);
			listFooter.add(dealprofitdetailVOMavg);
			page.setFooter(listFooter);
			
			for (DealprofitdetailUseraAndDealamountVO dealprofitdetailVO : rowsList) {	
				Double mt4useramount = NumberUtil.StringToDouble(dealprofitdetailVO.getMt4useramount());
				Double mt4dealamount = NumberUtil.StringToDouble(dealprofitdetailVO.getMt4dealamount());
				Double gts2useramount = NumberUtil.StringToDouble(dealprofitdetailVO.getGts2useramount());
				Double gts2dealamount = NumberUtil.StringToDouble(dealprofitdetailVO.getGts2dealamount());
				
				dealprofitdetailVO.setUseramount(NumberUtil.NumberFormat(String.valueOf(mt4useramount + gts2useramount),businessplatform));
				dealprofitdetailVO.setDealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamount + gts2dealamount),businessplatform));
				
				dealprofitdetailVO.setMt4useramount(NumberUtil.NumberFormat(dealprofitdetailVO.getMt4useramount(),businessplatform));
				dealprofitdetailVO.setMt4dealamount(NumberUtil.NumberFormat(dealprofitdetailVO.getMt4dealamount(),businessplatform));
				dealprofitdetailVO.setGts2useramount(NumberUtil.NumberFormat(dealprofitdetailVO.getGts2useramount(),businessplatform));
				dealprofitdetailVO.setGts2dealamount(NumberUtil.NumberFormat(dealprofitdetailVO.getGts2dealamount(),businessplatform));
			}
		}
		
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;	
	}
	
	/**
	 * 不分页查询交易人数/次数报表记录
	 */
	@Override
	public List<DealprofitdetailUseraAndDealamountVO> findDealprofitdetailUseraAndDealamountList(TradeSearchModel searchModel) throws Exception{																	
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		String detailed = searchModel.getDetailed();
		RpcResult rpcResult = RpcUtils.post(Constants.findDealprofitdetailUseraAndDealamountList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DealprofitdetailUseraAndDealamountVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DealprofitdetailUseraAndDealamountVO>>() {});
		Double total = Double.valueOf(rowsList.size());
		
		Double mt4useramountTotal = 0.0;
		Double mt4dealamountTotal = 0.0;
		Double gts2useramountTotal = 0.0;
		Double gts2dealamountTotal = 0.0;
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		for (DealprofitdetailUseraAndDealamountVO dealprofitdetailVO : rowsList) {
			Double mt4useramount = NumberUtil.StringToDouble(dealprofitdetailVO.getMt4useramount());
			Double mt4dealamount = NumberUtil.StringToDouble(dealprofitdetailVO.getMt4dealamount());
			Double gts2useramount = NumberUtil.StringToDouble(dealprofitdetailVO.getGts2useramount());
			Double gts2dealamount = NumberUtil.StringToDouble(dealprofitdetailVO.getGts2dealamount());
			
			mt4useramountTotal += mt4useramount;
			mt4dealamountTotal += mt4dealamount;
			gts2useramountTotal += gts2useramount;
			gts2dealamountTotal += gts2dealamount;
			
			dealprofitdetailVO.setUseramount(NumberUtil.NumberFormat(String.valueOf(mt4useramount + gts2useramount),businessplatform));
			dealprofitdetailVO.setDealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamount + gts2dealamount),businessplatform));
			dealprofitdetailVO.setMt4useramount(NumberUtil.NumberFormat(dealprofitdetailVO.getMt4useramount(),businessplatform));
			dealprofitdetailVO.setMt4dealamount(NumberUtil.NumberFormat(dealprofitdetailVO.getMt4dealamount(),businessplatform));
			dealprofitdetailVO.setGts2useramount(NumberUtil.NumberFormat(dealprofitdetailVO.getGts2useramount(),businessplatform));
			dealprofitdetailVO.setGts2dealamount(NumberUtil.NumberFormat(dealprofitdetailVO.getGts2dealamount(),businessplatform));		
		}
		if(DetailedEnum.IS_DETAILED.getValue().equals(detailed)){
			DealprofitdetailUseraAndDealamountVO dealprofitdetailVOTotal = new DealprofitdetailUseraAndDealamountVO();
			dealprofitdetailVOTotal.setExectime("总数");
			dealprofitdetailVOTotal.setPlatform(searchModel.getPlatformType());
			dealprofitdetailVOTotal.setUseramount(NumberUtil.NumberFormat(String.valueOf(mt4useramountTotal + gts2useramountTotal),businessplatform));
			dealprofitdetailVOTotal.setDealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamountTotal + gts2dealamountTotal),businessplatform));
			dealprofitdetailVOTotal.setMt4useramount(NumberUtil.NumberFormat(String.valueOf(mt4useramountTotal),businessplatform));
			dealprofitdetailVOTotal.setMt4dealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamountTotal),businessplatform));
			dealprofitdetailVOTotal.setGts2useramount(NumberUtil.NumberFormat(String.valueOf(gts2useramountTotal),businessplatform));
			dealprofitdetailVOTotal.setGts2dealamount(NumberUtil.NumberFormat(String.valueOf(gts2dealamountTotal),businessplatform));
			
			DealprofitdetailUseraAndDealamountVO dealprofitdetailVOMavg = new DealprofitdetailUseraAndDealamountVO();
			dealprofitdetailVOMavg.setExectime("平均");
			dealprofitdetailVOMavg.setPlatform(searchModel.getPlatformType());
			dealprofitdetailVOMavg.setUseramount(NumberUtil.NumberFormat(String.valueOf((mt4useramountTotal + gts2useramountTotal)/total),businessplatform));
			dealprofitdetailVOMavg.setDealamount(NumberUtil.NumberFormat(String.valueOf((mt4dealamountTotal + gts2dealamountTotal)/total),businessplatform));
			dealprofitdetailVOMavg.setMt4useramount(NumberUtil.NumberFormat(String.valueOf(mt4useramountTotal/total),businessplatform));
			dealprofitdetailVOMavg.setMt4dealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamountTotal/total),businessplatform));
			dealprofitdetailVOMavg.setGts2useramount(NumberUtil.NumberFormat(String.valueOf(gts2useramountTotal/total),businessplatform));
			dealprofitdetailVOMavg.setGts2dealamount(NumberUtil.NumberFormat(String.valueOf(gts2dealamountTotal/total),businessplatform));			

			rowsList.add(dealprofitdetailVOTotal);
			rowsList.add(dealprofitdetailVOMavg);
		}		
		return rowsList;		
	}
	
	
	/**
	 * 分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findDealprofitdetailPage(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findDealprofitdetailPageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		Integer total = Integer.valueOf(resultMap.get("total"));

		List<DealprofitdetailVO> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DealprofitdetailVO>>() {
				});	
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		if(total > 0){
			//查总记录
			RpcResult rpcResultTotal = RpcUtils.post(Constants.findDealprofitdetailList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
			String resultTotal = rpcResultTotal.getResult() + "";

			Map<String, String> resultMapTotal = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {
			});
			String rowsTotal = resultMapTotal.get("rows");
			List<DealprofitdetailVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<DealprofitdetailVO>>() {
			});
			
			TradeIndexEntity entity = new TradeIndexEntity();
			entity.setStartDate(detail.getStartTime());
			entity.setEndDate(detail.getEndTime());	
			List<TradeIndexEntity> tradeIndexList = tradeIndexService.findList(entity);
			
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());			
			
			DealprofitdetailVO dealprofitdetailVOTotal = new DealprofitdetailVO();
			Double grossprofit = 0.0;
			Double companyprofit = 0.0;
			Double swap = 0.0;
			Double sysclearzero = 0.0;
			Double bonus = 0.0;
			Double commission = 0.0;
			Double adjustfixedamount = 0.0;
			Double floatingprofit = 0.0;
			Double goldprofit = 0.0;
			Double silverprofit = 0.0;
			Double xaucnhprofit = 0.0;
			Double xagcnhprofit = 0.0;
			Double goldvolume = 0.0;
			Double silvervolume = 0.0;
			Double xaucnhvolume = 0.0;
			Double xagcnhvolume = 0.0;
			Double volume = 0.0;
			Double closedvolume = 0.0;
			Double useramount = 0.0;
			Double dealamount = 0.0;
			Double HandOperHedgeProfitAndLoss = 0.0;
			Double bonusOtherGts2 = 0.0;
			Double bonusOtherMt4 = 0.0;
			
			Double bonusclear = 0.0;
			Double margin = 0.0;
			Double cgse = 0.0;
			dealprofitdetailVOTotal.setCurrency("-");
			dealprofitdetailVOTotal.setSource("-");
			dealprofitdetailVOTotal.setPlatform(detail.getPlatformType());
			
			for (DealprofitdetailVO dealprofitdetailVO : rowsListTotal) {
				grossprofit += Double.valueOf(dealprofitdetailVO.getGrossprofit());
				companyprofit += Double.valueOf(dealprofitdetailVO.getCompanyprofit());
				swap += Double.valueOf(dealprofitdetailVO.getSwap());
				sysclearzero += Double.valueOf(dealprofitdetailVO.getSysclearzero());
				bonus += Double.valueOf(dealprofitdetailVO.getBonus());
				commission += Double.valueOf(dealprofitdetailVO.getCommission());
				adjustfixedamount += Double.valueOf(dealprofitdetailVO.getAdjustfixedamount());
				floatingprofit += Double.valueOf(dealprofitdetailVO.getFloatingprofit());
				goldprofit += Double.valueOf(dealprofitdetailVO.getGoldprofit());
				silverprofit += Double.valueOf(dealprofitdetailVO.getSilverprofit());
				xaucnhprofit += Double.valueOf(dealprofitdetailVO.getXaucnhprofit());
				xagcnhprofit += Double.valueOf(dealprofitdetailVO.getXagcnhprofit());
				goldvolume += Double.valueOf(dealprofitdetailVO.getGoldvolume());
				silvervolume += Double.valueOf(dealprofitdetailVO.getSilvervolume());
				xaucnhvolume += Double.valueOf(dealprofitdetailVO.getXaucnhvolume());
				xagcnhvolume += Double.valueOf(dealprofitdetailVO.getXagcnhvolume());
				volume += Double.valueOf(dealprofitdetailVO.getVolume());
				closedvolume += Double.valueOf(dealprofitdetailVO.getClosedvolume());
				useramount += NumberUtil.StringToDouble(dealprofitdetailVO.getUseramount());
				dealamount += NumberUtil.StringToDouble(dealprofitdetailVO.getDealamount());
				
				bonusclear += NumberUtil.StringToDouble(dealprofitdetailVO.getBonusclear());
				margin += NumberUtil.StringToDouble(dealprofitdetailVO.getMargin());
				cgse += NumberUtil.StringToDouble(dealprofitdetailVO.getCgse());					
								
				for (TradeIndexEntity tradeIndexEntity : tradeIndexList) {
					if(dealprofitdetailVO.getExectime().equals(tradeIndexEntity.getDateTime())){
						Double handOperHedgeProfitAndLossGts2 = tradeIndexEntity.getHandOperHedgeProfitAndLossGts2() == null?0.0:tradeIndexEntity.getHandOperHedgeProfitAndLossGts2();
						Double handOperHedgeProfitAndLossMt4 = tradeIndexEntity.getHandOperHedgeProfitAndLossMt4() == null?0.0:tradeIndexEntity.getHandOperHedgeProfitAndLossMt4();						
						if(PlatformtypeEnum.GTS2.getValue().equals(detail.getPlatformType())){
							HandOperHedgeProfitAndLoss += handOperHedgeProfitAndLossGts2;
						}else if(PlatformtypeEnum.MT4.getValue().equals(detail.getPlatformType())){
							HandOperHedgeProfitAndLoss += handOperHedgeProfitAndLossMt4;
						}else{
							HandOperHedgeProfitAndLoss += (handOperHedgeProfitAndLossGts2 + handOperHedgeProfitAndLossMt4);
						}
						if(null != tradeIndexEntity.getBonusOtherGts2()){
							bonusOtherGts2 += tradeIndexEntity.getBonusOtherGts2();
						}
						if(null != tradeIndexEntity.getBonusOtherMt4()){
							bonusOtherMt4 += tradeIndexEntity.getBonusOtherMt4();
						}
					}
				}						
			}	
			
			dealprofitdetailVOTotal.setGrossprofit(NumberUtil.NumberFormat(String.valueOf(grossprofit-HandOperHedgeProfitAndLoss),businessplatform));
			dealprofitdetailVOTotal.setCompanyprofit(NumberUtil.NumberFormat(String.valueOf(companyprofit),businessplatform));
			dealprofitdetailVOTotal.setSwap(NumberUtil.NumberFormat(String.valueOf(swap),businessplatform));
			dealprofitdetailVOTotal.setSysclearzero(NumberUtil.NumberFormat(String.valueOf(sysclearzero),businessplatform));
			
			if(PlatformtypeEnum.GTS2.getValue().equals(detail.getPlatformType())){
				dealprofitdetailVOTotal.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus + bonusOtherGts2),businessplatform));
			}else if(PlatformtypeEnum.MT4.getValue().equals(detail.getPlatformType())){
				dealprofitdetailVOTotal.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus + bonusOtherMt4),businessplatform));
			}else{
				dealprofitdetailVOTotal.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus + bonusOtherGts2 + bonusOtherGts2),businessplatform));
			}			
			
			dealprofitdetailVOTotal.setCommission(NumberUtil.NumberFormat(String.valueOf(commission),businessplatform));		
			dealprofitdetailVOTotal.setAdjustfixedamount(NumberUtil.NumberFormat(String.valueOf(adjustfixedamount),businessplatform));
			dealprofitdetailVOTotal.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(floatingprofit),businessplatform));
			dealprofitdetailVOTotal.setGoldprofit(NumberUtil.NumberFormat(String.valueOf(goldprofit),businessplatform));
			dealprofitdetailVOTotal.setSilverprofit(NumberUtil.NumberFormat(String.valueOf(silverprofit),businessplatform));
			dealprofitdetailVOTotal.setXaucnhprofit(NumberUtil.NumberFormat(String.valueOf(xaucnhprofit),businessplatform));
			dealprofitdetailVOTotal.setXagcnhprofit(NumberUtil.NumberFormat(String.valueOf(xagcnhprofit),businessplatform));
			dealprofitdetailVOTotal.setGoldvolume(NumberUtil.NumberFormat(String.valueOf(goldvolume),businessplatform));
			dealprofitdetailVOTotal.setSilvervolume(NumberUtil.NumberFormat(String.valueOf(silvervolume),businessplatform));
			dealprofitdetailVOTotal.setXaucnhvolume(NumberUtil.NumberFormat(String.valueOf(xaucnhvolume),businessplatform));
			dealprofitdetailVOTotal.setXagcnhvolume(NumberUtil.NumberFormat(String.valueOf(xagcnhvolume),businessplatform));
			dealprofitdetailVOTotal.setVolume(NumberUtil.NumberFormat(String.valueOf(volume),businessplatform));
			dealprofitdetailVOTotal.setClosedvolume(NumberUtil.NumberFormat(String.valueOf(closedvolume),businessplatform));
			dealprofitdetailVOTotal.setUseramount(NumberUtil.NumberFormat(String.valueOf(useramount),businessplatform));
			dealprofitdetailVOTotal.setDealamount(NumberUtil.NumberFormat(String.valueOf(dealamount),businessplatform));
						
			dealprofitdetailVOTotal.setBonusclear(NumberUtil.NumberFormat(String.valueOf(bonusclear),businessplatform));
			dealprofitdetailVOTotal.setMargin(NumberUtil.NumberFormat(String.valueOf(margin),businessplatform));
			dealprofitdetailVOTotal.setCgse(NumberUtil.NumberFormat(String.valueOf(cgse),businessplatform));
			dealprofitdetailVOTotal.setHandOperHedgeProfitAndLoss(NumberUtil.NumberFormat(String.valueOf(HandOperHedgeProfitAndLoss),businessplatform));
			
			DealprofitdetailVO dealprofitdetailVOMavg = new DealprofitdetailVO();
			dealprofitdetailVOMavg.setGrossprofit(NumberUtil.NumberFormat(String.valueOf((grossprofit-HandOperHedgeProfitAndLoss)/total),businessplatform));
			dealprofitdetailVOMavg.setCompanyprofit(NumberUtil.NumberFormat(String.valueOf(companyprofit/total),businessplatform));
			dealprofitdetailVOMavg.setSwap(NumberUtil.NumberFormat(String.valueOf(swap/total),businessplatform));
			dealprofitdetailVOMavg.setSysclearzero(NumberUtil.NumberFormat(String.valueOf(sysclearzero/total),businessplatform));
			dealprofitdetailVOMavg.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus/total),businessplatform));
			dealprofitdetailVOMavg.setCommission(NumberUtil.NumberFormat(String.valueOf(commission/total),businessplatform));		
			dealprofitdetailVOMavg.setAdjustfixedamount(NumberUtil.NumberFormat(String.valueOf(adjustfixedamount/total),businessplatform));
			dealprofitdetailVOMavg.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(floatingprofit/total),businessplatform));
			dealprofitdetailVOMavg.setGoldprofit(NumberUtil.NumberFormat(String.valueOf(goldprofit/total),businessplatform));
			dealprofitdetailVOMavg.setSilverprofit(NumberUtil.NumberFormat(String.valueOf(silverprofit/total),businessplatform));
			dealprofitdetailVOMavg.setXaucnhprofit(NumberUtil.NumberFormat(String.valueOf(xaucnhprofit/total),businessplatform));
			dealprofitdetailVOMavg.setXagcnhprofit(NumberUtil.NumberFormat(String.valueOf(xagcnhprofit/total),businessplatform));
			dealprofitdetailVOMavg.setGoldvolume(NumberUtil.NumberFormat(String.valueOf(goldvolume/total),businessplatform));
			dealprofitdetailVOMavg.setSilvervolume(NumberUtil.NumberFormat(String.valueOf(silvervolume/total),businessplatform));
			dealprofitdetailVOMavg.setXaucnhvolume(NumberUtil.NumberFormat(String.valueOf(xaucnhvolume/total),businessplatform));
			dealprofitdetailVOMavg.setXagcnhvolume(NumberUtil.NumberFormat(String.valueOf(xagcnhvolume/total),businessplatform));
			dealprofitdetailVOMavg.setVolume(NumberUtil.NumberFormat(String.valueOf(volume/total),businessplatform));
			dealprofitdetailVOMavg.setClosedvolume(NumberUtil.NumberFormat(String.valueOf(closedvolume/total),businessplatform));
			dealprofitdetailVOMavg.setUseramount(NumberUtil.NumberFormat(String.valueOf(useramount/total),businessplatform));
			dealprofitdetailVOMavg.setDealamount(NumberUtil.NumberFormat(String.valueOf(dealamount/total),businessplatform));
			
			dealprofitdetailVOMavg.setBonusclear(NumberUtil.NumberFormat(String.valueOf(bonusclear/total),businessplatform));
			dealprofitdetailVOMavg.setMargin(NumberUtil.NumberFormat(String.valueOf(margin/total),businessplatform));
			dealprofitdetailVOMavg.setCgse(NumberUtil.NumberFormat(String.valueOf(cgse/total),businessplatform));
			dealprofitdetailVOMavg.setHandOperHedgeProfitAndLoss(NumberUtil.NumberFormat(String.valueOf(HandOperHedgeProfitAndLoss/total),businessplatform));
			
			dealprofitdetailVOMavg.setCurrency("-");
			dealprofitdetailVOMavg.setSource("-");
			dealprofitdetailVOMavg.setPlatform(detail.getPlatformType());
			
			List<DealprofitdetailVO> listFooter = new ArrayList<DealprofitdetailVO>();
			dealprofitdetailVOTotal.setExectime("总数：");
			dealprofitdetailVOMavg.setExectime("平均：");
			listFooter.add(dealprofitdetailVOTotal);
			listFooter.add(dealprofitdetailVOMavg);
			page.setFooter(listFooter);
			
			for (DealprofitdetailVO dealprofitdetailVO : rowsList) {
				for (TradeIndexEntity tradeIndexEntity : tradeIndexList) {
					if(dealprofitdetailVO.getExectime().equals(tradeIndexEntity.getDateTime())){
						Double handOperHedgeProfitAndLossGts2 = tradeIndexEntity.getHandOperHedgeProfitAndLossGts2() == null?0.0:tradeIndexEntity.getHandOperHedgeProfitAndLossGts2();
						Double handOperHedgeProfitAndLossMt4 = tradeIndexEntity.getHandOperHedgeProfitAndLossMt4() == null?0.0:tradeIndexEntity.getHandOperHedgeProfitAndLossMt4();
						if(PlatformtypeEnum.GTS2.getValue().equals(detail.getPlatformType())){
							dealprofitdetailVO.setHandOperHedgeProfitAndLoss(String.valueOf(handOperHedgeProfitAndLossGts2));
						}else if(PlatformtypeEnum.MT4.getValue().equals(detail.getPlatformType())){
							dealprofitdetailVO.setHandOperHedgeProfitAndLoss(String.valueOf(handOperHedgeProfitAndLossMt4));
						}else{
							dealprofitdetailVO.setHandOperHedgeProfitAndLoss(String.valueOf(handOperHedgeProfitAndLossGts2 + handOperHedgeProfitAndLossMt4));
						}
						
						Double tradeIndexBonusOtherGts2 = tradeIndexEntity.getBonusOtherGts2() == null?0.0:tradeIndexEntity.getBonusOtherGts2();
						Double tradeIndexBonusOtherMt4 = tradeIndexEntity.getBonusOtherMt4() == null?0.0:tradeIndexEntity.getBonusOtherMt4();
						
						dealprofitdetailVO.setBonusOtherGts2(String.valueOf(tradeIndexBonusOtherGts2));
						dealprofitdetailVO.setBonusOtherMt4(String.valueOf(tradeIndexBonusOtherMt4));
					}
				}
				
				grossprofit = Double.valueOf(dealprofitdetailVO.getGrossprofit());
				Double handOperHedgeProfitAndLoss = NumberUtil.StringToDouble(dealprofitdetailVO.getHandOperHedgeProfitAndLoss());
				grossprofit = grossprofit - handOperHedgeProfitAndLoss;
				dealprofitdetailVO.setGrossprofit(NumberUtil.NumberFormat(String.valueOf(grossprofit) ,businessplatform));				
				dealprofitdetailVO.setCompanyprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getCompanyprofit(),businessplatform));
				dealprofitdetailVO.setSwap(NumberUtil.NumberFormat(dealprofitdetailVO.getSwap(),businessplatform));
				dealprofitdetailVO.setSysclearzero(NumberUtil.NumberFormat(dealprofitdetailVO.getSysclearzero(),businessplatform));
				bonus = NumberUtil.StringToDouble(dealprofitdetailVO.getBonus());
				bonusOtherGts2 = NumberUtil.StringToDouble(dealprofitdetailVO.getBonusOtherGts2());
				bonusOtherMt4 = NumberUtil.StringToDouble(dealprofitdetailVO.getBonusOtherMt4());
				String exectime = dealprofitdetailVO.getExectime();
				Date exectimeDate = DateUtil.stringToDate(exectime);
				String week = DateUtil.getWeekOfDate(exectimeDate,0);
				if(weekDaysEnum.SUNDAY.getValue().equals(week) || weekDaysEnum.SATURDAY.getValue().equals(week)){
					dealprofitdetailVO.setBonus("0");
					if(weekDaysEnum.SUNDAY.getValue().equals(week)){
						exectime = DateUtil.formatDateToString(DateUtil.addDays(exectimeDate,-2));
					}
					if(weekDaysEnum.SATURDAY.getValue().equals(week)){
						exectime = DateUtil.formatDateToString(DateUtil.addDays(exectimeDate,-1));
					}
					
					for (DealprofitdetailVO dealprofitdetail : rowsList) {
						if(dealprofitdetail.getExectime().equals(exectime)){
							if(PlatformtypeEnum.GTS2.getValue().equals(detail.getPlatformType())){
								dealprofitdetail.setBonus(NumberUtil.NumberFormat(String.valueOf(NumberUtil.StringToDouble(dealprofitdetail.getBonus()) + bonusOtherGts2 + bonus),businessplatform));
							}else if(PlatformtypeEnum.MT4.getValue().equals(detail.getPlatformType())){
								dealprofitdetail.setBonus(NumberUtil.NumberFormat(String.valueOf(NumberUtil.StringToDouble(dealprofitdetail.getBonus()) + bonusOtherMt4 + bonus),businessplatform));
							}else{
								dealprofitdetail.setBonus(NumberUtil.NumberFormat(String.valueOf(NumberUtil.StringToDouble(dealprofitdetail.getBonus()) + bonusOtherGts2 + bonusOtherMt4 + bonus),businessplatform));
							}
						}
					}
				}else{
					if(PlatformtypeEnum.GTS2.getValue().equals(detail.getPlatformType())){
						bonus = bonus + bonusOtherGts2; //赠金 + 推广赠金（其它）GTS2
						dealprofitdetailVO.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus),businessplatform));
					}else if(PlatformtypeEnum.MT4.getValue().equals(detail.getPlatformType())){
						bonus = bonus + bonusOtherMt4; //赠金 + 推广赠金（其它）Mt4
						dealprofitdetailVO.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus),businessplatform));
					}else{
						bonus = bonus + bonusOtherGts2 + bonusOtherMt4; //赠金 + 推广赠金（其它）GTS2 + 推广赠金（其它）Mt4
						dealprofitdetailVO.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus),businessplatform));
					}
				}
				
				dealprofitdetailVO.setCommission(NumberUtil.NumberFormat(dealprofitdetailVO.getCommission(),businessplatform));		
				dealprofitdetailVO.setAdjustfixedamount(NumberUtil.NumberFormat(dealprofitdetailVO.getAdjustfixedamount(),businessplatform));
				dealprofitdetailVO.setFloatingprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getFloatingprofit(),businessplatform));
				dealprofitdetailVO.setGoldprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getGoldprofit(),businessplatform));
				dealprofitdetailVO.setSilverprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getSilverprofit(),businessplatform));
				dealprofitdetailVO.setXaucnhprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getXaucnhprofit(),businessplatform));
				dealprofitdetailVO.setXagcnhprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getXagcnhprofit(),businessplatform));
				dealprofitdetailVO.setGoldvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getGoldvolume(),businessplatform));
				dealprofitdetailVO.setSilvervolume(NumberUtil.NumberFormat(dealprofitdetailVO.getSilvervolume(),businessplatform));
				dealprofitdetailVO.setXaucnhvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getXaucnhvolume(),businessplatform));
				dealprofitdetailVO.setXagcnhvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getXagcnhvolume(),businessplatform));
				dealprofitdetailVO.setVolume(NumberUtil.NumberFormat(dealprofitdetailVO.getVolume(),businessplatform));
				dealprofitdetailVO.setClosedvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getClosedvolume(),businessplatform));
				dealprofitdetailVO.setUseramount(NumberUtil.NumberFormat(dealprofitdetailVO.getUseramount(),businessplatform));
				dealprofitdetailVO.setDealamount(NumberUtil.NumberFormat(dealprofitdetailVO.getDealamount(),businessplatform));
				
				dealprofitdetailVO.setBonusclear(NumberUtil.NumberFormat(dealprofitdetailVO.getBonusclear(),businessplatform));
				dealprofitdetailVO.setMargin(NumberUtil.NumberFormat(dealprofitdetailVO.getMargin(),businessplatform));
				dealprofitdetailVO.setCgse(NumberUtil.NumberFormat(dealprofitdetailVO.getCgse(),businessplatform));
			}
		}
		
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;
	}
	
	/**
	 * 不分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 */
	@Override
	public List<DealprofitdetailVO> findDealprofitdetailList(TradeSearchModel searchModel) throws Exception{																	
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		
		RpcResult rpcResult = RpcUtils.post(Constants.findDealprofitdetailList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DealprofitdetailVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DealprofitdetailVO>>() {
		});
		
		TradeIndexEntity entity = new TradeIndexEntity();
		entity.setStartDate(searchModel.getStartTime());
		entity.setEndDate(searchModel.getEndTime());	
		List<TradeIndexEntity> tradeIndexList = tradeIndexService.findList(entity);
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		for (DealprofitdetailVO dealprofitdetailVO : rowsList) {
			for (TradeIndexEntity tradeIndexEntity : tradeIndexList) {
				if(dealprofitdetailVO.getExectime().equals(tradeIndexEntity.getDateTime())){
					Double handOperHedgeProfitAndLossGts2 = tradeIndexEntity.getHandOperHedgeProfitAndLossGts2() == null?0.0:tradeIndexEntity.getHandOperHedgeProfitAndLossGts2();
					Double handOperHedgeProfitAndLossMt4 = tradeIndexEntity.getHandOperHedgeProfitAndLossMt4() == null?0.0:tradeIndexEntity.getHandOperHedgeProfitAndLossMt4();
					if(PlatformtypeEnum.GTS2.getValue().equals(searchModel.getPlatformType())){
						dealprofitdetailVO.setHandOperHedgeProfitAndLoss(String.valueOf(handOperHedgeProfitAndLossGts2));
					}else if(PlatformtypeEnum.MT4.getValue().equals(searchModel.getPlatformType())){
						dealprofitdetailVO.setHandOperHedgeProfitAndLoss(String.valueOf(handOperHedgeProfitAndLossMt4));
					}else{
						dealprofitdetailVO.setHandOperHedgeProfitAndLoss(String.valueOf(handOperHedgeProfitAndLossGts2 + handOperHedgeProfitAndLossMt4));
					}
					
					Double tradeIndexBonusOtherGts2 = tradeIndexEntity.getBonusOtherGts2() == null?0.0:tradeIndexEntity.getBonusOtherGts2();
					Double tradeIndexBonusOtherMt4 = tradeIndexEntity.getBonusOtherMt4() == null?0.0:tradeIndexEntity.getBonusOtherMt4();
					
					dealprofitdetailVO.setBonusOtherGts2(String.valueOf(tradeIndexBonusOtherGts2));
					dealprofitdetailVO.setBonusOtherMt4(String.valueOf(tradeIndexBonusOtherMt4));
				}
			}
			
			Double grossprofit = Double.valueOf(dealprofitdetailVO.getGrossprofit());
			Double handOperHedgeProfitAndLoss = NumberUtil.StringToDouble(dealprofitdetailVO.getHandOperHedgeProfitAndLoss());
			grossprofit = grossprofit - handOperHedgeProfitAndLoss;
			dealprofitdetailVO.setGrossprofit(NumberUtil.NumberFormat(String.valueOf(grossprofit) ,businessplatform));									
			dealprofitdetailVO.setCompanyprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getCompanyprofit(),businessplatform));
			dealprofitdetailVO.setSwap(NumberUtil.NumberFormat(dealprofitdetailVO.getSwap(),businessplatform));
			dealprofitdetailVO.setSysclearzero(NumberUtil.NumberFormat(dealprofitdetailVO.getSysclearzero(),businessplatform));
			Double bonus = NumberUtil.StringToDouble(dealprofitdetailVO.getBonus());
			Double bonusOtherGts2 = NumberUtil.StringToDouble(dealprofitdetailVO.getBonusOtherGts2());
			Double bonusOtherMt4 = NumberUtil.StringToDouble(dealprofitdetailVO.getBonusOtherMt4());
			String exectime = dealprofitdetailVO.getExectime();
			Date exectimeDate = DateUtil.stringToDate(exectime);
			String week = DateUtil.getWeekOfDate(exectimeDate,0);
			if(weekDaysEnum.SUNDAY.getValue().equals(week) || weekDaysEnum.SATURDAY.getValue().equals(week)){
				dealprofitdetailVO.setBonus("0");
				if(weekDaysEnum.SUNDAY.getValue().equals(week)){
					exectime = DateUtil.formatDateToString(DateUtil.addDays(exectimeDate,-2));
				}
				if(weekDaysEnum.SATURDAY.getValue().equals(week)){
					exectime = DateUtil.formatDateToString(DateUtil.addDays(exectimeDate,-1));
				}
				
				for (DealprofitdetailVO dealprofitdetail : rowsList) {
					if(dealprofitdetail.getExectime().equals(exectime)){
						if(PlatformtypeEnum.GTS2.getValue().equals(searchModel.getPlatformType())){
							dealprofitdetail.setBonus(NumberUtil.NumberFormat(String.valueOf(NumberUtil.StringToDouble(dealprofitdetail.getBonus()) + bonusOtherGts2 + bonus),businessplatform));
						}else if(PlatformtypeEnum.MT4.getValue().equals(searchModel.getPlatformType())){
							dealprofitdetail.setBonus(NumberUtil.NumberFormat(String.valueOf(NumberUtil.StringToDouble(dealprofitdetail.getBonus()) + bonusOtherMt4 + bonus),businessplatform));
						}else{
							dealprofitdetail.setBonus(NumberUtil.NumberFormat(String.valueOf(NumberUtil.StringToDouble(dealprofitdetail.getBonus()) + bonusOtherGts2 + bonusOtherMt4 + bonus),businessplatform));
						}
					}
				}
			}else{
				if(PlatformtypeEnum.GTS2.getValue().equals(searchModel.getPlatformType())){
					bonus = bonus + bonusOtherGts2; //赠金 + 推广赠金（其它）GTS2
					dealprofitdetailVO.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus),businessplatform));
				}else if(PlatformtypeEnum.MT4.getValue().equals(searchModel.getPlatformType())){
					bonus = bonus + bonusOtherMt4; //赠金 + 推广赠金（其它）Mt4
					dealprofitdetailVO.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus),businessplatform));
				}else{
					bonus = bonus + bonusOtherGts2 + bonusOtherMt4; //赠金 + 推广赠金（其它）GTS2 + 推广赠金（其它）Mt4
					dealprofitdetailVO.setBonus(NumberUtil.NumberFormat(String.valueOf(bonus),businessplatform));
				}
			}
			
			dealprofitdetailVO.setCommission(NumberUtil.NumberFormat(dealprofitdetailVO.getCommission(),businessplatform));		
			dealprofitdetailVO.setAdjustfixedamount(NumberUtil.NumberFormat(dealprofitdetailVO.getAdjustfixedamount(),businessplatform));
			dealprofitdetailVO.setFloatingprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getFloatingprofit(),businessplatform));
			dealprofitdetailVO.setGoldprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getGoldprofit(),businessplatform));
			dealprofitdetailVO.setSilverprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getSilverprofit(),businessplatform));
			dealprofitdetailVO.setXaucnhprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getXaucnhprofit(),businessplatform));
			dealprofitdetailVO.setXagcnhprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getXagcnhprofit(),businessplatform));
			dealprofitdetailVO.setGoldvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getGoldvolume(),businessplatform));
			dealprofitdetailVO.setSilvervolume(NumberUtil.NumberFormat(dealprofitdetailVO.getSilvervolume(),businessplatform));
			dealprofitdetailVO.setXaucnhvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getXaucnhvolume(),businessplatform));
			dealprofitdetailVO.setXagcnhvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getXagcnhvolume(),businessplatform));
			dealprofitdetailVO.setVolume(NumberUtil.NumberFormat(dealprofitdetailVO.getVolume(),businessplatform));
			dealprofitdetailVO.setClosedvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getClosedvolume(),businessplatform));
			dealprofitdetailVO.setUseramount(NumberUtil.NumberFormat(dealprofitdetailVO.getUseramount(),businessplatform));
			dealprofitdetailVO.setDealamount(NumberUtil.NumberFormat(dealprofitdetailVO.getDealamount(),businessplatform));
			
			dealprofitdetailVO.setBonusclear(NumberUtil.NumberFormat(dealprofitdetailVO.getBonusclear(),businessplatform));
			dealprofitdetailVO.setMargin(NumberUtil.NumberFormat(dealprofitdetailVO.getMargin(),businessplatform));
			dealprofitdetailVO.setCgse(NumberUtil.NumberFormat(dealprofitdetailVO.getCgse(),businessplatform));
						
		}
		
		return rowsList;		
	}
	
	/**
	 * 分页查询交易类别数据记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findDealcategoryPageList(PageGrid<TradeSearchModel> pageGrid) throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findDealcategoryPageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		Integer total = Integer.valueOf(resultMap.get("total"));

		List<DealcategoryVO> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DealcategoryVO>>() {
				});	
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		if(total > 0){
			//查总记录
			RpcResult rpcResultTotal = RpcUtils.post(Constants.findDealcategoryList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
			String resultTotal = rpcResultTotal.getResult() + "";

			Map<String, String> resultMapTotal = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {
			});
			String rowsTotal = resultMapTotal.get("rows");
			List<DealcategoryVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<DealcategoryVO>>() {
			});
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());						
						
			Double mt4volumeaxu = 0.0;
			Double mt4volumeaxg = 0.0;
			Double mt4volumeaxucnh = 0.0;
			Double mt4volumeaxgcnh = 0.0;
			Double gts2volumeaxu = 0.0;
			Double gts2volumeaxg = 0.0;
			Double gts2volumeaxucnh = 0.0;;
			Double gts2volumeaxgcnh = 0.0;
			Double mt4floatingprofit = 0.0;
			Double gts2floatingprofit = 0.0;
			Double mt4balance = 0.0;
			Double gts2balance = 0.0;
			Double mt4useramount = 0.0;
			Double gts2useramount = 0.0;
			Double mt4dealamount = 0.0;
			Double gts2dealamount = 0.0;
			for (DealcategoryVO dealcategoryVO : rowsListTotal) {
				mt4volumeaxu += Double.valueOf(dealcategoryVO.getMt4volumeaxu());
				mt4volumeaxg += Double.valueOf(dealcategoryVO.getMt4volumeaxg());
				mt4volumeaxucnh += Double.valueOf(dealcategoryVO.getMt4volumeaxucnh());
				mt4volumeaxgcnh += Double.valueOf(dealcategoryVO.getMt4volumeaxgcnh());
				gts2volumeaxu += Double.valueOf(dealcategoryVO.getGts2volumeaxu());
				gts2volumeaxg += Double.valueOf(dealcategoryVO.getGts2volumeaxg());
				gts2volumeaxucnh += Double.valueOf(dealcategoryVO.getGts2volumeaxgcnh());
				gts2volumeaxgcnh += Double.valueOf(dealcategoryVO.getGts2volumeaxgcnh());
				mt4floatingprofit += Double.valueOf(dealcategoryVO.getMt4floatingprofit());
				gts2floatingprofit += Double.valueOf(dealcategoryVO.getGts2floatingprofit());
				mt4balance += Double.valueOf(dealcategoryVO.getMt4balance());
				gts2balance += Double.valueOf(dealcategoryVO.getGts2balance());
				mt4useramount += Double.valueOf(dealcategoryVO.getMt4useramount());
				gts2useramount += Double.valueOf(dealcategoryVO.getGts2useramount());
				mt4dealamount += Double.valueOf(dealcategoryVO.getMt4dealamount());
				gts2dealamount += Double.valueOf(dealcategoryVO.getGts2dealamount());
							
			}			
			
			List<DealcategoryVO> listFooter = new ArrayList<DealcategoryVO>();
			DealcategoryVO dealcategoryVOTotal = new DealcategoryVO();
			dealcategoryVOTotal.setExectime("小计：");
			dealcategoryVOTotal.setPlatform(detail.getPlatformType());
			dealcategoryVOTotal.setMt4volumeaxu(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxu),businessplatform));
			dealcategoryVOTotal.setMt4volumeaxg(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxg),businessplatform));
			dealcategoryVOTotal.setMt4volumeaxucnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxucnh),businessplatform));
			dealcategoryVOTotal.setMt4volumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgcnh),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxu(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxu),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxg(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxg),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxucnh(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxucnh),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxgcnh),businessplatform));
			dealcategoryVOTotal.setMt4floatingprofit(NumberUtil.NumberFormat(String.valueOf(mt4floatingprofit),businessplatform));
			dealcategoryVOTotal.setGts2floatingprofit(NumberUtil.NumberFormat(String.valueOf(gts2floatingprofit),businessplatform));
			dealcategoryVOTotal.setMt4balance(NumberUtil.NumberFormat(String.valueOf(mt4balance),businessplatform));
			dealcategoryVOTotal.setGts2balance(NumberUtil.NumberFormat(String.valueOf(gts2balance),businessplatform));
			dealcategoryVOTotal.setMt4useramount(NumberUtil.NumberFormat(String.valueOf(mt4useramount),businessplatform));
			dealcategoryVOTotal.setGts2useramount(NumberUtil.NumberFormat(String.valueOf(gts2useramount),businessplatform));
			dealcategoryVOTotal.setMt4dealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamount),businessplatform));
			dealcategoryVOTotal.setGts2dealamount(NumberUtil.NumberFormat(String.valueOf(gts2dealamount),businessplatform));
			dealcategoryVOTotal.setCurrency("-");
			listFooter.add(dealcategoryVOTotal);
			page.setFooter(listFooter);
			
			for (DealcategoryVO dealcategoryVO : rowsList) {
				dealcategoryVO.setPlatform(detail.getPlatformType());
				dealcategoryVO.setMt4volumeaxu(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxu(),businessplatform));
				dealcategoryVO.setMt4volumeaxg(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxg(),businessplatform));
				dealcategoryVO.setMt4volumeaxucnh(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxucnh(),businessplatform));
				dealcategoryVO.setMt4volumeaxgcnh(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxgcnh(),businessplatform));
				dealcategoryVO.setGts2volumeaxu(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxu(),businessplatform));
				dealcategoryVO.setGts2volumeaxg(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxg(),businessplatform));
				dealcategoryVO.setGts2volumeaxucnh(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxucnh(),businessplatform));
				dealcategoryVO.setGts2volumeaxgcnh(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxgcnh(),businessplatform));
				dealcategoryVO.setMt4floatingprofit(NumberUtil.NumberFormat(dealcategoryVO.getMt4floatingprofit(),businessplatform));
				dealcategoryVO.setGts2floatingprofit(NumberUtil.NumberFormat(dealcategoryVO.getGts2floatingprofit(),businessplatform));
				dealcategoryVO.setMt4balance(NumberUtil.NumberFormat(dealcategoryVO.getMt4balance(),businessplatform));
				dealcategoryVO.setGts2balance(NumberUtil.NumberFormat(dealcategoryVO.getGts2balance(),businessplatform));
				dealcategoryVO.setMt4useramount(NumberUtil.NumberFormat(dealcategoryVO.getMt4useramount(),businessplatform));
				dealcategoryVO.setGts2useramount(NumberUtil.NumberFormat(dealcategoryVO.getGts2useramount(),businessplatform));
				dealcategoryVO.setMt4dealamount(NumberUtil.NumberFormat(dealcategoryVO.getMt4dealamount(),businessplatform));
				dealcategoryVO.setGts2dealamount(NumberUtil.NumberFormat(dealcategoryVO.getGts2dealamount(),businessplatform));
			}
		}
		
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;				
	}

	/**
	 * 不分页查询交易类别数据记录
	 */
	@Override
	public List<DealcategoryVO> findDealcategoryList(TradeSearchModel searchModel) throws Exception {
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		RpcResult rpcResult = RpcUtils.post(Constants.findDealcategoryList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DealcategoryVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DealcategoryVO>>() {
		});
		
		Double mt4volumeaxu = 0.0;
		Double mt4volumeaxg = 0.0;
		Double mt4volumeaxucnh = 0.0;
		Double mt4volumeaxgcnh = 0.0;
		Double gts2volumeaxu = 0.0;
		Double gts2volumeaxg = 0.0;
		Double gts2volumeaxucnh = 0.0;;
		Double gts2volumeaxgcnh = 0.0;
		Double mt4floatingprofit = 0.0;
		Double gts2floatingprofit = 0.0;
		Double mt4balance = 0.0;
		Double gts2balance = 0.0;
		Double mt4useramount = 0.0;
		Double gts2useramount = 0.0;
		Double mt4dealamount = 0.0;
		Double gts2dealamount = 0.0;
		for (DealcategoryVO dealcategoryVO : rowsList) {
			mt4volumeaxu += Double.valueOf(dealcategoryVO.getMt4volumeaxu());
			mt4volumeaxg += Double.valueOf(dealcategoryVO.getMt4volumeaxg());
			mt4volumeaxucnh += Double.valueOf(dealcategoryVO.getMt4volumeaxucnh());
			mt4volumeaxgcnh += Double.valueOf(dealcategoryVO.getMt4volumeaxgcnh());
			gts2volumeaxu += Double.valueOf(dealcategoryVO.getGts2volumeaxu());
			gts2volumeaxg += Double.valueOf(dealcategoryVO.getGts2volumeaxg());
			gts2volumeaxucnh += Double.valueOf(dealcategoryVO.getGts2volumeaxgcnh());
			gts2volumeaxgcnh += Double.valueOf(dealcategoryVO.getGts2volumeaxgcnh());
			mt4floatingprofit += Double.valueOf(dealcategoryVO.getMt4floatingprofit());
			gts2floatingprofit += Double.valueOf(dealcategoryVO.getGts2floatingprofit());
			mt4balance += Double.valueOf(dealcategoryVO.getMt4balance());
			gts2balance += Double.valueOf(dealcategoryVO.getGts2balance());
			mt4useramount += Double.valueOf(dealcategoryVO.getMt4useramount());
			gts2useramount += Double.valueOf(dealcategoryVO.getGts2useramount());
			mt4dealamount += Double.valueOf(dealcategoryVO.getMt4dealamount());
			gts2dealamount += Double.valueOf(dealcategoryVO.getGts2dealamount());
			
			dealcategoryVO.setPlatform(searchModel.getPlatformType());
			dealcategoryVO.setMt4volumeaxu(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxu(),businessplatform));
			dealcategoryVO.setMt4volumeaxg(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxg(),businessplatform));
			dealcategoryVO.setMt4volumeaxucnh(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxucnh(),businessplatform));
			dealcategoryVO.setMt4volumeaxgcnh(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxgcnh(),businessplatform));
			dealcategoryVO.setGts2volumeaxu(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxu(),businessplatform));
			dealcategoryVO.setGts2volumeaxg(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxg(),businessplatform));
			dealcategoryVO.setGts2volumeaxucnh(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxucnh(),businessplatform));
			dealcategoryVO.setGts2volumeaxgcnh(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxgcnh(),businessplatform));
			dealcategoryVO.setMt4floatingprofit(NumberUtil.NumberFormat(dealcategoryVO.getMt4floatingprofit(),businessplatform));
			dealcategoryVO.setGts2floatingprofit(NumberUtil.NumberFormat(dealcategoryVO.getGts2floatingprofit(),businessplatform));
			dealcategoryVO.setMt4balance(NumberUtil.NumberFormat(dealcategoryVO.getMt4balance(),businessplatform));
			dealcategoryVO.setGts2balance(NumberUtil.NumberFormat(dealcategoryVO.getGts2balance(),businessplatform));
			dealcategoryVO.setMt4useramount(NumberUtil.NumberFormat(dealcategoryVO.getMt4useramount(),businessplatform));
			dealcategoryVO.setGts2useramount(NumberUtil.NumberFormat(dealcategoryVO.getGts2useramount(),businessplatform));
			dealcategoryVO.setMt4dealamount(NumberUtil.NumberFormat(dealcategoryVO.getMt4dealamount(),businessplatform));
			dealcategoryVO.setGts2dealamount(NumberUtil.NumberFormat(dealcategoryVO.getGts2dealamount(),businessplatform));		
						
		}
						
		DealcategoryVO dealcategoryVOTotal = new DealcategoryVO();
		dealcategoryVOTotal.setExectime("小计");
		dealcategoryVOTotal.setPlatform(searchModel.getPlatformType());
		dealcategoryVOTotal.setMt4volumeaxu(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxu),businessplatform));
		dealcategoryVOTotal.setMt4volumeaxg(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxg),businessplatform));
		dealcategoryVOTotal.setMt4volumeaxucnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxucnh),businessplatform));
		dealcategoryVOTotal.setMt4volumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgcnh),businessplatform));
		dealcategoryVOTotal.setGts2volumeaxu(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxu),businessplatform));
		dealcategoryVOTotal.setGts2volumeaxg(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxg),businessplatform));
		dealcategoryVOTotal.setGts2volumeaxucnh(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxucnh),businessplatform));
		dealcategoryVOTotal.setGts2volumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxgcnh),businessplatform));
		dealcategoryVOTotal.setMt4floatingprofit(NumberUtil.NumberFormat(String.valueOf(mt4floatingprofit),businessplatform));
		dealcategoryVOTotal.setGts2floatingprofit(NumberUtil.NumberFormat(String.valueOf(gts2floatingprofit),businessplatform));
		dealcategoryVOTotal.setMt4balance(NumberUtil.NumberFormat(String.valueOf(mt4balance),businessplatform));
		dealcategoryVOTotal.setGts2balance(NumberUtil.NumberFormat(String.valueOf(gts2balance),businessplatform));
		dealcategoryVOTotal.setMt4useramount(NumberUtil.NumberFormat(String.valueOf(mt4useramount),businessplatform));
		dealcategoryVOTotal.setGts2useramount(NumberUtil.NumberFormat(String.valueOf(gts2useramount),businessplatform));
		dealcategoryVOTotal.setMt4dealamount(NumberUtil.NumberFormat(String.valueOf(mt4dealamount),businessplatform));
		dealcategoryVOTotal.setGts2dealamount(NumberUtil.NumberFormat(String.valueOf(gts2dealamount),businessplatform));
		dealcategoryVOTotal.setCurrency("-");
		
		rowsList.add(dealcategoryVOTotal);
		
		return rowsList;
	}

	/**
	 * 分页查询MT4/GTS2数据报表记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findDealprofitdetailMT4AndGts2PageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		String order = detail.getOrder();
		if("desc".equals(order)){
			detail.setOrder("asc");
		}
		
		RpcResult dealprofitdetailRpcResult = RpcUtils.post(Constants.findDealprofitdetailMT4AndGts2PageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String dealprofitdetailResult = dealprofitdetailRpcResult.getResult() + "";
		Map<String, String> dealprofitdetailResultMap = JacksonUtil.readValue(dealprofitdetailResult, new TypeReference<Map<String, String>>() {});
		String dealprofitdetailRows = dealprofitdetailResultMap.get("rows");
		String total = dealprofitdetailResultMap.get("total");
		List<DealprofitdetailMT4AndGts2VO> dealprofitdetailList = JacksonUtil.readValue(dealprofitdetailRows,new TypeReference<List<DealprofitdetailMT4AndGts2VO>>() {});	
		
		RpcResult dealcategoryRpcResult = RpcUtils.post(Constants.findDealprofitdetailMT4AndGts2List, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
		String dealcategoryResult = dealcategoryRpcResult.getResult() + "";
		Map<String, String> dealcategoryResultMap = JacksonUtil.readValue(dealcategoryResult, new TypeReference<Map<String, String>>() {});
		String dealcategoryRows = dealcategoryResultMap.get("rows");
		List<DealprofitdetailMT4AndGts2VO> dealcategoryRowsList = JacksonUtil.readValue(dealcategoryRows, new TypeReference<List<DealprofitdetailMT4AndGts2VO>>() {});
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		List<DealprofitdetailMT4AndGts2VO> DealprofitdetailMT4AndGts2VOList = new ArrayList<DealprofitdetailMT4AndGts2VO>();
		
		Double sum = 0.0;
		Double netTotal = 0.0;
		Double sumTotal = 0.0;
		int lastWeek = -1;
		Date lastDate = null; //上次日期
		
		Map<String,String> weekMap = new HashMap<String,String>();
		int i = 0;
		for (DealprofitdetailMT4AndGts2VO dealprofitdetailVO : dealprofitdetailList) {		
			//获取星期
			String exectime = dealprofitdetailVO.getExectime();
			Date exectimeDate = DateUtil.stringToDate(exectime);
			int weekCode = Integer.valueOf(DateUtil.getWeekOfDate(exectimeDate, 0));
			String weekString = DateUtil.getWeekOfDate(exectimeDate, 1);
			
			DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VO = new DealprofitdetailMT4AndGts2VO();
			dealprofitdetailMT4AndGts2VO.setRowkey(String.valueOf(i+1));
			dealprofitdetailMT4AndGts2VO.setExectime(dealprofitdetailVO.getExectime());
			dealprofitdetailMT4AndGts2VO.setWeek(weekString);
			dealprofitdetailMT4AndGts2VO.setWeekCode(weekCode);
			dealprofitdetailMT4AndGts2VO.setSysclearzero(NumberUtil.NumberFormat(dealprofitdetailVO.getSysclearzero(),businessplatform));            //系统清零
			dealprofitdetailMT4AndGts2VO.setCompanyprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getCompanyprofit(),businessplatform));          //公司实现盈亏
			dealprofitdetailMT4AndGts2VO.setCommission(NumberUtil.NumberFormat(dealprofitdetailVO.getCommission(),businessplatform));                //返佣
			dealprofitdetailMT4AndGts2VO.setAdjustfixedamount(NumberUtil.NumberFormat(dealprofitdetailVO.getAdjustfixedamount(),businessplatform));  //调整
			dealprofitdetailMT4AndGts2VO.setCgse(NumberUtil.NumberFormat(dealprofitdetailVO.getCgse(),businessplatform));                            //編碼費
			dealprofitdetailMT4AndGts2VO.setSwap(NumberUtil.NumberFormat(dealprofitdetailVO.getSwap(),businessplatform));                            //实现利息
			dealprofitdetailMT4AndGts2VO.setClosedvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getClosedvolume(),businessplatform));            //交易手數（平倉手）
			dealprofitdetailMT4AndGts2VO.setMargin(NumberUtil.NumberFormat(dealprofitdetailVO.getMargin(),businessplatform));                        //占用保证金
			
			dealprofitdetailMT4AndGts2VO.setMt4volumeaxu(dealprofitdetailVO.getMt4volumeaxu());
			dealprofitdetailMT4AndGts2VO.setMt4volumeaxg(dealprofitdetailVO.getMt4volumeaxg());
			dealprofitdetailMT4AndGts2VO.setMt4volumeaxucnh(dealprofitdetailVO.getMt4volumeaxucnh());
			dealprofitdetailMT4AndGts2VO.setMt4volumeaxgcnh(dealprofitdetailVO.getMt4volumeaxgcnh());
			dealprofitdetailMT4AndGts2VO.setGts2volumeaxu(dealprofitdetailVO.getGts2volumeaxu());
			dealprofitdetailMT4AndGts2VO.setGts2volumeaxg(dealprofitdetailVO.getGts2volumeaxg());
			dealprofitdetailMT4AndGts2VO.setGts2volumeaxucnh(dealprofitdetailVO.getGts2volumeaxucnh());
			dealprofitdetailMT4AndGts2VO.setGts2volumeaxgcnh(dealprofitdetailVO.getGts2volumeaxg());
			
			dealprofitdetailMT4AndGts2VO.setPlatform(detail.getPlatformType());
			dealprofitdetailMT4AndGts2VO.setBusinessplatform(dealprofitdetailVO.getBusinessplatform());			
			
			Double companyprofit = Double.valueOf(dealprofitdetailVO.getCompanyprofit());//公司实现盈亏
			Double swap = Double.valueOf(dealprofitdetailVO.getSwap());// 实现利息
			Double commission = Double.valueOf(dealprofitdetailVO.getCommission());//返佣
		    Double net = companyprofit + swap - commission; //公司实现盈亏 + 实现利息 - 返佣
		    dealprofitdetailMT4AndGts2VO.setNet(NumberUtil.NumberFormat(String.valueOf(net),businessplatform));			
				   
			if(null != lastDate){
				boolean flag = DateUtil.isSameDate(DateUtil.formatDate(lastDate),exectime);//判断两个日期是否是同一周，如果不是同一周，则添加上周末
				if(!flag){
					if(!weekMap.containsKey("星期六")){
						DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOAddOne = new DealprofitdetailMT4AndGts2VO();
						if(lastWeek == 0){
							dealprofitdetailMT4AndGts2VOAddOne.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,-1)));
							dealprofitdetailMT4AndGts2VOAddOne.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddOne.getExectime()), 1));
							dealprofitdetailMT4AndGts2VOAddOne.setWeekCode(6);
						}else{
							dealprofitdetailMT4AndGts2VOAddOne.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,6-lastWeek)));
							dealprofitdetailMT4AndGts2VOAddOne.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddOne.getExectime()), 1));
							dealprofitdetailMT4AndGts2VOAddOne.setWeekCode(6);
						}
						dealprofitdetailMT4AndGts2VOAddOne.setPlatform("-");
						DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOAddOne);
					}
					if(!weekMap.containsKey("星期日")){
						DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOAddTwo = new DealprofitdetailMT4AndGts2VO();
						dealprofitdetailMT4AndGts2VOAddTwo.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,7-lastWeek)));
						dealprofitdetailMT4AndGts2VOAddTwo.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddTwo.getExectime()), 1));
						dealprofitdetailMT4AndGts2VOAddTwo.setWeekCode(0);
						dealprofitdetailMT4AndGts2VOAddTwo.setPlatform("-");
						dealprofitdetailMT4AndGts2VOAddTwo.setSum(NumberUtil.NumberFormat(String.valueOf(sum),businessplatform));
						DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOAddTwo);
					}
					sumTotal += sum;
					sum = 0.0;
					weekMap.clear();
				}				
			}			
			weekMap.put(weekString, exectime);			 
			if(0 == weekCode){
				dealprofitdetailMT4AndGts2VO.setSum(NumberUtil.NumberFormat(String.valueOf(sum),businessplatform));
				sumTotal += sum;
				sum = 0.0;
			}
			netTotal += net;
			sum += net;
			lastWeek = weekCode;
			lastDate = exectimeDate;
			i++;
			DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VO);
		}
		
		/////////////////////////////////////////////////////////
		
		if(!weekMap.isEmpty()){
			if(!weekMap.containsKey("星期六")){
				DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOAddOne = new DealprofitdetailMT4AndGts2VO();
				if(lastWeek == 0){
					dealprofitdetailMT4AndGts2VOAddOne.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,-1)));
					dealprofitdetailMT4AndGts2VOAddOne.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddOne.getExectime()), 1));
					dealprofitdetailMT4AndGts2VOAddOne.setWeekCode(6);
				}else{
					dealprofitdetailMT4AndGts2VOAddOne.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,6-lastWeek)));
					dealprofitdetailMT4AndGts2VOAddOne.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddOne.getExectime()), 1));
					dealprofitdetailMT4AndGts2VOAddOne.setWeekCode(6);
				}
				dealprofitdetailMT4AndGts2VOAddOne.setPlatform("-");
				DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOAddOne);
			}
			if(!weekMap.containsKey("星期日")){
				DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOAddTwo = new DealprofitdetailMT4AndGts2VO();
				dealprofitdetailMT4AndGts2VOAddTwo.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,7-lastWeek)));
				dealprofitdetailMT4AndGts2VOAddTwo.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddTwo.getExectime()), 1));
				dealprofitdetailMT4AndGts2VOAddTwo.setWeekCode(0);
				dealprofitdetailMT4AndGts2VOAddTwo.setPlatform("-");
				dealprofitdetailMT4AndGts2VOAddTwo.setSum(NumberUtil.NumberFormat(String.valueOf(sum),businessplatform));
				DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOAddTwo);
			}
			sumTotal += sum;
			sum = 0.0;
		}
		
		if("desc".equals(order)){
			Collections.sort(DealprofitdetailMT4AndGts2VOList, new Comparator<DealprofitdetailMT4AndGts2VO>(){  		  
	            /*  
	             * int compare(DealprofitdetailMT4AndGts2VO o1, DealprofitdetailMT4AndGts2VO o2) 返回一个基本类型的整型，  
	             * 返回负数表示：o1 小于o2，  
	             * 返回0 表示：o1和o2相等，  
	             * 返回正数表示：o1大于o2。  
	             */  
	            public int compare(DealprofitdetailMT4AndGts2VO o1, DealprofitdetailMT4AndGts2VO o2) {  
	                Date dt1 = DateUtil.stringToDate(o1.getExectime(), "yyyy-MM-dd");
	                Date dt2 = DateUtil.stringToDate(o2.getExectime(), "yyyy-MM-dd");
	                if (dt1.getTime() > dt2.getTime()) {
	                    return -1;
	                } else if (dt1.getTime() < dt2.getTime()) {
	                    return 1;
	                } else {
	                    return 0;
	                } 
	            }  
	     });
		}	

		Integer k = 1;
		for (DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VO : DealprofitdetailMT4AndGts2VOList) {
			dealprofitdetailMT4AndGts2VO.setRowkey(String.valueOf(k));
			k++;
		}
			
		DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOTotal = new DealprofitdetailMT4AndGts2VO();
		dealprofitdetailMT4AndGts2VOTotal.setExectime("小计：");		
		netTotal = 0.0;
		sumTotal = 0.0;
		for (DealprofitdetailMT4AndGts2VO dealprofitdetailVO : dealcategoryRowsList) {	
			Double companyprofit = Double.valueOf(dealprofitdetailVO.getCompanyprofit());//公司实现盈亏
			Double swap = Double.valueOf(dealprofitdetailVO.getSwap());// 实现利息
			Double commission = Double.valueOf(dealprofitdetailVO.getCommission());//返佣
			netTotal += companyprofit + swap - commission; //公司实现盈亏 + 实现利息 - 返佣
			sumTotal += companyprofit + swap - commission; //公司实现盈亏 + 实现利息 - 返佣
		}
		dealprofitdetailMT4AndGts2VOTotal.setNet(NumberUtil.NumberFormat(String.valueOf(netTotal),businessplatform));
		dealprofitdetailMT4AndGts2VOTotal.setSum(NumberUtil.NumberFormat(String.valueOf(sumTotal),businessplatform));
		dealprofitdetailMT4AndGts2VOTotal.setPlatform(detail.getPlatformType());
		DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOTotal);
       
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(DealprofitdetailMT4AndGts2VOList);
		
		return page;	
	}

	/**
	 * 不分页查询MT4/GTS2数据报表记录
	 */
	@Override
	public List<DealprofitdetailMT4AndGts2VO> findDealprofitdetailMT4AndGts2List(TradeSearchModel searchModel) throws Exception {
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		String order = searchModel.getOrder();
		if("desc".equals(order)){
			searchModel.setOrder("asc");
		}
		
		RpcResult dealprofitdetailRpcResult = RpcUtils.post(Constants.findDealprofitdetailMT4AndGts2List, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String dealprofitdetailResult = dealprofitdetailRpcResult.getResult() + "";
		Map<String, String> dealprofitdetailResultMap = JacksonUtil.readValue(dealprofitdetailResult, new TypeReference<Map<String, String>>() {});
		String dealprofitdetailRows = dealprofitdetailResultMap.get("rows");
		List<DealprofitdetailMT4AndGts2VO> dealprofitdetailRowsList = JacksonUtil.readValue(dealprofitdetailRows, new TypeReference<List<DealprofitdetailMT4AndGts2VO>>() {});
				
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		List<DealprofitdetailMT4AndGts2VO> DealprofitdetailMT4AndGts2VOList = new ArrayList<DealprofitdetailMT4AndGts2VO>();
		
		Double sum = 0.0;
		Double netTotal = 0.0;
		Double sumTotal = 0.0;
		int lastWeek = -1;
		Date lastDate = null; //上次日期
		
		Map<String,String> weekMap = new HashMap<String,String>();
		
		for (DealprofitdetailMT4AndGts2VO dealprofitdetailVO : dealprofitdetailRowsList) {				
			//获取星期
			String exectime = dealprofitdetailVO.getExectime();
			Date exectimeDate = DateUtil.stringToDate(exectime);
			int weekCode = Integer.valueOf(DateUtil.getWeekOfDate(exectimeDate, 0));
			String weekString = DateUtil.getWeekOfDate(exectimeDate, 1);
			
			DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VO = new DealprofitdetailMT4AndGts2VO();
			dealprofitdetailMT4AndGts2VO.setExectime(dealprofitdetailVO.getExectime());
			dealprofitdetailMT4AndGts2VO.setWeek(weekString);
			dealprofitdetailMT4AndGts2VO.setWeekCode(weekCode);
			dealprofitdetailMT4AndGts2VO.setSysclearzero(NumberUtil.NumberFormat(dealprofitdetailVO.getSysclearzero(),businessplatform));            //系统清零
			dealprofitdetailMT4AndGts2VO.setCompanyprofit(NumberUtil.NumberFormat(dealprofitdetailVO.getCompanyprofit(),businessplatform));          //公司实现盈亏
			dealprofitdetailMT4AndGts2VO.setCommission(NumberUtil.NumberFormat(dealprofitdetailVO.getCommission(),businessplatform));                //返佣
			dealprofitdetailMT4AndGts2VO.setAdjustfixedamount(NumberUtil.NumberFormat(dealprofitdetailVO.getAdjustfixedamount(),businessplatform));  //调整
			dealprofitdetailMT4AndGts2VO.setCgse(NumberUtil.NumberFormat(dealprofitdetailVO.getCgse(),businessplatform));                            //編碼費
			dealprofitdetailMT4AndGts2VO.setSwap(NumberUtil.NumberFormat(dealprofitdetailVO.getSwap(),businessplatform));                            //实现利息
			dealprofitdetailMT4AndGts2VO.setClosedvolume(NumberUtil.NumberFormat(dealprofitdetailVO.getClosedvolume(),businessplatform));            //交易手數（平倉手）
			dealprofitdetailMT4AndGts2VO.setMargin(NumberUtil.NumberFormat(dealprofitdetailVO.getMargin(),businessplatform));                        //占用保证金
			
			dealprofitdetailMT4AndGts2VO.setMt4volumeaxu(dealprofitdetailVO.getMt4volumeaxu());
			dealprofitdetailMT4AndGts2VO.setMt4volumeaxg(dealprofitdetailVO.getMt4volumeaxg());
			dealprofitdetailMT4AndGts2VO.setMt4volumeaxucnh(dealprofitdetailVO.getMt4volumeaxucnh());
			dealprofitdetailMT4AndGts2VO.setMt4volumeaxgcnh(dealprofitdetailVO.getMt4volumeaxgcnh());
			dealprofitdetailMT4AndGts2VO.setGts2volumeaxu(dealprofitdetailVO.getGts2volumeaxu());
			dealprofitdetailMT4AndGts2VO.setGts2volumeaxg(dealprofitdetailVO.getGts2volumeaxg());
			dealprofitdetailMT4AndGts2VO.setGts2volumeaxucnh(dealprofitdetailVO.getGts2volumeaxucnh());
			dealprofitdetailMT4AndGts2VO.setGts2volumeaxgcnh(dealprofitdetailVO.getGts2volumeaxg());
			
			dealprofitdetailMT4AndGts2VO.setPlatform(searchModel.getPlatformType());
			dealprofitdetailMT4AndGts2VO.setBusinessplatform(dealprofitdetailVO.getBusinessplatform());
			
			
			Double companyprofit = Double.valueOf(dealprofitdetailVO.getCompanyprofit());//公司实现盈亏
			Double swap = Double.valueOf(dealprofitdetailVO.getSwap());// 实现利息
			Double commission = Double.valueOf(dealprofitdetailVO.getCommission());//返佣
			Double net = companyprofit + swap - commission;			
			dealprofitdetailMT4AndGts2VO.setNet(NumberUtil.NumberFormat(String.valueOf(net),businessplatform));
			sum += net;
			netTotal += net; 
			if(null != lastDate){
				boolean flag = DateUtil.isSameDate(DateUtil.formatDate(lastDate),exectime);//判断两个日期是否是同一周，如果不是同一周，则添加上周末
				if(!flag){
					if(!weekMap.containsKey("星期六")){
						DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOAddOne = new DealprofitdetailMT4AndGts2VO();
						if(lastWeek == 0){
							dealprofitdetailMT4AndGts2VOAddOne.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,-1)));
							dealprofitdetailMT4AndGts2VOAddOne.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddOne.getExectime()), 1));
							dealprofitdetailMT4AndGts2VOAddOne.setWeekCode(6);
						}else{
							dealprofitdetailMT4AndGts2VOAddOne.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,6-lastWeek)));
							dealprofitdetailMT4AndGts2VOAddOne.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddOne.getExectime()), 1));
							dealprofitdetailMT4AndGts2VOAddOne.setWeekCode(6);
						}
						dealprofitdetailMT4AndGts2VOAddOne.setPlatform("-");
						DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOAddOne);
					}
					if(!weekMap.containsKey("星期日")){
						DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOAddTwo = new DealprofitdetailMT4AndGts2VO();
						dealprofitdetailMT4AndGts2VOAddTwo.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,7-lastWeek)));
						dealprofitdetailMT4AndGts2VOAddTwo.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddTwo.getExectime()), 1));
						dealprofitdetailMT4AndGts2VOAddTwo.setWeekCode(0);
						dealprofitdetailMT4AndGts2VOAddTwo.setPlatform("-");
						dealprofitdetailMT4AndGts2VOAddTwo.setSum(NumberUtil.NumberFormat(String.valueOf(sum),businessplatform));
						DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOAddTwo);
					}
					sumTotal += sum;
					sum = 0.0;
					weekMap.clear();
				}				
			}			
			weekMap.put(weekString, exectime);
			
			if(0 == weekCode){
				dealprofitdetailMT4AndGts2VO.setSum(NumberUtil.NumberFormat(String.valueOf(sum),businessplatform));
				sumTotal += sum;
				sum = 0.0;
			}
			lastWeek = weekCode;
			lastDate = exectimeDate;
			
			DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VO);
		}
		//判断最后一周，如果不是周末，则加上周末
		if(!weekMap.isEmpty()){
			if(!weekMap.containsKey("星期六")){
				DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOAddOne = new DealprofitdetailMT4AndGts2VO();
				if(lastWeek == 0){
					dealprofitdetailMT4AndGts2VOAddOne.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,-1)));
					dealprofitdetailMT4AndGts2VOAddOne.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddOne.getExectime()), 1));
					dealprofitdetailMT4AndGts2VOAddOne.setWeekCode(6);
				}else{
					dealprofitdetailMT4AndGts2VOAddOne.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,6-lastWeek)));
					dealprofitdetailMT4AndGts2VOAddOne.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddOne.getExectime()), 1));
					dealprofitdetailMT4AndGts2VOAddOne.setWeekCode(6);
				}
				dealprofitdetailMT4AndGts2VOAddOne.setPlatform("-");
				DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOAddOne);
			}
			if(!weekMap.containsKey("星期日")){
				DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOAddTwo = new DealprofitdetailMT4AndGts2VO();
				dealprofitdetailMT4AndGts2VOAddTwo.setExectime(DateUtil.formatDateToString(DateUtil.addDays(lastDate,7-lastWeek)));
				dealprofitdetailMT4AndGts2VOAddTwo.setWeek(DateUtil.getWeekOfDate(DateUtil.stringToDate(dealprofitdetailMT4AndGts2VOAddTwo.getExectime()), 1));
				dealprofitdetailMT4AndGts2VOAddTwo.setWeekCode(0);
				dealprofitdetailMT4AndGts2VOAddTwo.setPlatform("-");
				dealprofitdetailMT4AndGts2VOAddTwo.setSum(NumberUtil.NumberFormat(String.valueOf(sum),businessplatform));
				DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOAddTwo);
			}
			sumTotal += sum;
			sum = 0.0;
		}
		
		if("desc".equals(order)){
			Collections.sort(DealprofitdetailMT4AndGts2VOList, new Comparator<DealprofitdetailMT4AndGts2VO>(){  		  
	            /*  
	             * int compare(DealprofitdetailMT4AndGts2VO o1, DealprofitdetailMT4AndGts2VO o2) 返回一个基本类型的整型，  
	             * 返回负数表示：o1 小于o2，  
	             * 返回0 表示：o1和o2相等，  
	             * 返回正数表示：o1大于o2。  
	             */  
	            public int compare(DealprofitdetailMT4AndGts2VO o1, DealprofitdetailMT4AndGts2VO o2) {  
	                Date dt1 = DateUtil.stringToDate(o1.getExectime(), "yyyy-MM-dd");
	                Date dt2 = DateUtil.stringToDate(o2.getExectime(), "yyyy-MM-dd");
	                if (dt1.getTime() > dt2.getTime()) {
	                    return -1;
	                } else if (dt1.getTime() < dt2.getTime()) {
	                    return 1;
	                } else {
	                    return 0;
	                } 
	            }  
	     });
		}
		Integer k = 1;
		for (DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VO : DealprofitdetailMT4AndGts2VOList) {
			dealprofitdetailMT4AndGts2VO.setRowkey(String.valueOf(k));
			k++;
		}
		
		DealprofitdetailMT4AndGts2VO dealprofitdetailMT4AndGts2VOTotal = new DealprofitdetailMT4AndGts2VO();
		dealprofitdetailMT4AndGts2VOTotal.setExectime("小计：");
		netTotal = 0.0;
		sumTotal = 0.0;
		for (DealprofitdetailMT4AndGts2VO dealprofitdetailVO : dealprofitdetailRowsList) {	
			Double companyprofit = Double.valueOf(dealprofitdetailVO.getCompanyprofit());//公司实现盈亏
			Double swap = Double.valueOf(dealprofitdetailVO.getSwap());// 实现利息
			Double commission = Double.valueOf(dealprofitdetailVO.getCommission());//返佣
			netTotal += companyprofit + swap - commission; //公司实现盈亏 + 实现利息 - 返佣
			sumTotal += companyprofit + swap - commission; //公司实现盈亏 + 实现利息 - 返佣
		}
		dealprofitdetailMT4AndGts2VOTotal.setNet(NumberUtil.NumberFormat(String.valueOf(netTotal),businessplatform));
		dealprofitdetailMT4AndGts2VOTotal.setSum(NumberUtil.NumberFormat(String.valueOf(sumTotal),businessplatform));
		dealprofitdetailMT4AndGts2VOTotal.setPlatform(searchModel.getPlatformType());
		DealprofitdetailMT4AndGts2VOList.add(dealprofitdetailMT4AndGts2VOTotal);
				
		return DealprofitdetailMT4AndGts2VOList;
	}

	/**
	 * 分页查询人均交易手数记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findAverageTransactionVolumePageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findAverageTransactionVolumePageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		Integer total = Integer.valueOf(resultMap.get("total"));

		List<DealprofitdetailVO> rowsList = JacksonUtil.readValue(rows,new TypeReference<List<DealprofitdetailVO>>() {});		
		
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		if(total > 0){
			//查总记录
			RpcResult rpcResultTotal = RpcUtils.post(Constants.findAverageTransactionVolumeList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
			String resultTotal = rpcResultTotal.getResult() + "";

			Map<String, String> resultMapTotal = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {
			});
			String rowsTotal = resultMapTotal.get("rows");
			List<DealprofitdetailVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<DealprofitdetailVO>>() {
			});
			
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());			
			
			DealprofitdetailVO dealprofitdetailVOTotal = new DealprofitdetailVO();
			Double mt4avgtransvolume = 0.0;
			Double gts2avgtransvolume = 0.0;
			for (DealprofitdetailVO dealprofitdetailVO : rowsListTotal) {
				mt4avgtransvolume += NumberUtil.StringToDouble(dealprofitdetailVO.getMt4avgtransvolume());
				gts2avgtransvolume += NumberUtil.StringToDouble(dealprofitdetailVO.getGts2avgtransvolume());			
			}
			dealprofitdetailVOTotal.setPlatform(detail.getPlatformType());	
			dealprofitdetailVOTotal.setMt4avgtransvolume(NumberUtil.NumberFormat(String.valueOf(mt4avgtransvolume),businessplatform));
			dealprofitdetailVOTotal.setGts2avgtransvolume(NumberUtil.NumberFormat(String.valueOf(gts2avgtransvolume),businessplatform));
			dealprofitdetailVOTotal.setAvgtransvolume(NumberUtil.NumberFormat(String.valueOf(mt4avgtransvolume+gts2avgtransvolume),businessplatform));
			
			DealprofitdetailVO dealprofitdetailVOMavg = new DealprofitdetailVO();
			dealprofitdetailVOMavg.setMt4avgtransvolume(NumberUtil.NumberFormat(String.valueOf(mt4avgtransvolume/total),businessplatform));	
			dealprofitdetailVOMavg.setGts2avgtransvolume(NumberUtil.NumberFormat(String.valueOf(gts2avgtransvolume/total),businessplatform));
			dealprofitdetailVOMavg.setAvgtransvolume(NumberUtil.NumberFormat(String.valueOf((mt4avgtransvolume+gts2avgtransvolume)/total),businessplatform));
			dealprofitdetailVOMavg.setPlatform(detail.getPlatformType());
			
			for (DealprofitdetailVO dealprofitdetailVO : rowsList) {
				mt4avgtransvolume = NumberUtil.StringToDouble(dealprofitdetailVO.getMt4avgtransvolume());
				gts2avgtransvolume = NumberUtil.StringToDouble(dealprofitdetailVO.getGts2avgtransvolume());
				dealprofitdetailVO.setAvgtransvolume(NumberUtil.NumberFormat(String.valueOf(mt4avgtransvolume+gts2avgtransvolume),businessplatform));
			}
			
			List<DealprofitdetailVO> listFooter = new ArrayList<DealprofitdetailVO>();
			dealprofitdetailVOTotal.setExectime("总数：");
			dealprofitdetailVOMavg.setExectime("平均：");
			listFooter.add(dealprofitdetailVOTotal);
			listFooter.add(dealprofitdetailVOMavg);
			page.setFooter(listFooter);						
		}
		
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;	
	}
	
	/**
	 * 不分页查询人均交易手数记录
	 */
	@Override
	public List<DealprofitdetailVO> findAverageTransactionVolumeList(TradeSearchModel searchModel) throws Exception{																	
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		String detailed = searchModel.getDetailed();
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		RpcResult rpcResult = RpcUtils.post(Constants.findAverageTransactionVolumeList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DealprofitdetailVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DealprofitdetailVO>>() {});	
		Integer total = rowsList.size();	
		Double mt4avgtransvolumeTotal = 0.0;
		Double gts2avgtransvolumeTotal = 0.0;
		for (DealprofitdetailVO dealprofitdetailVO : rowsList) {
			Double mt4avgtransvolume = NumberUtil.StringToDouble(dealprofitdetailVO.getMt4avgtransvolume());
			Double gts2avgtransvolume = NumberUtil.StringToDouble(dealprofitdetailVO.getGts2avgtransvolume());
			mt4avgtransvolumeTotal += mt4avgtransvolume;
			gts2avgtransvolumeTotal += gts2avgtransvolume;
			
			dealprofitdetailVO.setAvgtransvolume(NumberUtil.NumberFormat(String.valueOf(mt4avgtransvolume+gts2avgtransvolume),businessplatform));
		}
		if(DetailedEnum.IS_DETAILED.getValue().equals(detailed)){
			DealprofitdetailVO dealprofitdetailVOTotal = new DealprofitdetailVO();
			dealprofitdetailVOTotal.setPlatform(searchModel.getPlatformType());	
			dealprofitdetailVOTotal.setMt4avgtransvolume(NumberUtil.NumberFormat(String.valueOf(mt4avgtransvolumeTotal),businessplatform));
			dealprofitdetailVOTotal.setGts2avgtransvolume(NumberUtil.NumberFormat(String.valueOf(gts2avgtransvolumeTotal),businessplatform));
			dealprofitdetailVOTotal.setAvgtransvolume(NumberUtil.NumberFormat(String.valueOf(mt4avgtransvolumeTotal + gts2avgtransvolumeTotal),businessplatform));
			
			DealprofitdetailVO dealprofitdetailVOMavg = new DealprofitdetailVO();
			dealprofitdetailVOMavg.setMt4avgtransvolume(NumberUtil.NumberFormat(String.valueOf(mt4avgtransvolumeTotal/total),businessplatform));	
			dealprofitdetailVOMavg.setGts2avgtransvolume(NumberUtil.NumberFormat(String.valueOf(gts2avgtransvolumeTotal/total),businessplatform));
			dealprofitdetailVOMavg.setAvgtransvolume(NumberUtil.NumberFormat(String.valueOf((gts2avgtransvolumeTotal+mt4avgtransvolumeTotal)/total),businessplatform));
			
			dealprofitdetailVOTotal.setExectime("总数：");
			dealprofitdetailVOMavg.setExectime("平均：");
			
			rowsList.add(dealprofitdetailVOTotal);
			rowsList.add(dealprofitdetailVOMavg);
		}

		return rowsList;		
	}

	/**
	 * 分页查询交易类别倫敦/人民幣数据记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findDealcategoryAxuAxgcnhPageList(PageGrid<TradeSearchModel> pageGrid) throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findDealcategoryAxuAxgcnhPageList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		Integer total = Integer.valueOf(resultMap.get("total"));

		List<DealcategoryVO> rowsList = JacksonUtil.readValue(rows,new TypeReference<List<DealcategoryVO>>() {});	
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		if(total > 0){
			//查总记录
			RpcResult rpcResultTotal = RpcUtils.post(Constants.findDealcategoryAxuAxgcnhList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
			String resultTotal = rpcResultTotal.getResult() + "";

			Map<String, String> resultMapTotal = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {});
			String rowsTotal = resultMapTotal.get("rows");
			List<DealcategoryVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<DealcategoryVO>>() {});
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());						
						
			Double mt4volumeaxuTotal = 0.0;
			Double mt4volumeaxgTotal = 0.0;
			Double mt4volumeaxucnhTotal = 0.0;
			Double mt4volumeaxgcnhTotal = 0.0;
			Double gts2volumeaxuTotal = 0.0;
			Double gts2volumeaxgTotal = 0.0;
			Double gts2volumeaxucnhTotal = 0.0;;
			Double gts2volumeaxgcnhTotal = 0.0;
			
			for (DealcategoryVO dealcategoryVO : rowsListTotal) {
				mt4volumeaxuTotal += NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxu());
				mt4volumeaxgTotal += NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxg());
				mt4volumeaxucnhTotal += NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxucnh());
				mt4volumeaxgcnhTotal += NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxgcnh());
				gts2volumeaxuTotal += NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxu());
				gts2volumeaxgTotal += NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxg());
				gts2volumeaxucnhTotal += NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxgcnh());
				gts2volumeaxgcnhTotal += NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxgcnh());							
			}			
			
			DealcategoryVO dealcategoryVOTotal = new DealcategoryVO();
			dealcategoryVOTotal.setExectime("小计：");
			dealcategoryVOTotal.setPlatform(detail.getPlatformType());
			dealcategoryVOTotal.setMt4volumeaxu(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxuTotal),businessplatform));
			dealcategoryVOTotal.setMt4volumeaxg(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgTotal),businessplatform));
			dealcategoryVOTotal.setMt4volumeaxucnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxucnhTotal),businessplatform));
			dealcategoryVOTotal.setMt4volumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgcnhTotal),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxu(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxuTotal),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxg(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxgTotal),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxucnh(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxucnhTotal),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxgcnhTotal),businessplatform));
			
			dealcategoryVOTotal.setVolumeaxu(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxuTotal + gts2volumeaxuTotal),businessplatform));
			dealcategoryVOTotal.setVolumeaxg(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgTotal + gts2volumeaxgTotal),businessplatform));
			dealcategoryVOTotal.setVolumeaxucnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxucnhTotal + gts2volumeaxucnhTotal),businessplatform));
			dealcategoryVOTotal.setVolumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgcnhTotal + gts2volumeaxgcnhTotal),businessplatform));
			
			List<DealcategoryVO> listFooter = new ArrayList<DealcategoryVO>();
			listFooter.add(dealcategoryVOTotal);
			page.setFooter(listFooter);
			
			for (DealcategoryVO dealcategoryVO : rowsList) {				
				Double mt4volumeaxu = NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxu());
				Double mt4volumeaxg = NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxg());
				Double mt4volumeaxucnh = NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxucnh());
				Double mt4volumeaxgcnh = NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxgcnh());
				Double gts2volumeaxu = NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxu());
				Double gts2volumeaxg = NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxg());
				Double gts2volumeaxucnh = NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxgcnh());
				Double gts2volumeaxgcnh = NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxgcnh());
				
				dealcategoryVO.setVolumeaxu(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxu + gts2volumeaxu),businessplatform));
				dealcategoryVO.setVolumeaxg(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxg + gts2volumeaxg),businessplatform));
				dealcategoryVO.setVolumeaxucnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxucnh + gts2volumeaxucnh),businessplatform));
				dealcategoryVO.setVolumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgcnh + gts2volumeaxgcnh),businessplatform));
				
				dealcategoryVO.setMt4volumeaxu(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxu(),businessplatform));
				dealcategoryVO.setMt4volumeaxg(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxg(),businessplatform));
				dealcategoryVO.setMt4volumeaxucnh(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxucnh(),businessplatform));
				dealcategoryVO.setMt4volumeaxgcnh(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxgcnh(),businessplatform));
				dealcategoryVO.setGts2volumeaxu(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxu(),businessplatform));
				dealcategoryVO.setGts2volumeaxg(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxg(),businessplatform));
				dealcategoryVO.setGts2volumeaxucnh(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxucnh(),businessplatform));
				dealcategoryVO.setGts2volumeaxgcnh(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxgcnh(),businessplatform));	
			}
		}
		
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;				
	}

	/**
	 * 不分页查询交易类别倫敦/人民幣数据记录
	 */
	@Override
	public List<DealcategoryVO> findDealcategoryAxuAxgcnhList(TradeSearchModel searchModel) throws Exception {
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		String detailed = searchModel.getDetailed();
		RpcResult rpcResult = RpcUtils.post(Constants.findDealcategoryAxuAxgcnhList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DealcategoryVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DealcategoryVO>>() {});
		
		Double mt4volumeaxuTotal = 0.0;
		Double mt4volumeaxgTotal = 0.0;
		Double mt4volumeaxucnhTotal = 0.0;
		Double mt4volumeaxgcnhTotal = 0.0;
		Double gts2volumeaxuTotal = 0.0;
		Double gts2volumeaxgTotal = 0.0;
		Double gts2volumeaxucnhTotal = 0.0;;
		Double gts2volumeaxgcnhTotal = 0.0;
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		for (DealcategoryVO dealcategoryVO : rowsList) {
			Double mt4volumeaxu = NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxu());
			Double mt4volumeaxg = NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxg());
			Double mt4volumeaxucnh = NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxucnh());
			Double mt4volumeaxgcnh = NumberUtil.StringToDouble(dealcategoryVO.getMt4volumeaxgcnh());
			Double gts2volumeaxu = NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxu());
			Double gts2volumeaxg = NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxg());
			Double gts2volumeaxucnh = NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxgcnh());
			Double gts2volumeaxgcnh = NumberUtil.StringToDouble(dealcategoryVO.getGts2volumeaxgcnh());
			
			dealcategoryVO.setVolumeaxu(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxu + gts2volumeaxu),businessplatform));
			dealcategoryVO.setVolumeaxg(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxg + gts2volumeaxg),businessplatform));
			dealcategoryVO.setVolumeaxucnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxucnh + gts2volumeaxucnh),businessplatform));
			dealcategoryVO.setVolumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgcnh + gts2volumeaxgcnh),businessplatform));
			
			mt4volumeaxuTotal += mt4volumeaxu;
			mt4volumeaxgTotal += mt4volumeaxg;
			mt4volumeaxucnhTotal += mt4volumeaxucnh;
			mt4volumeaxgcnhTotal += mt4volumeaxgcnh;
			gts2volumeaxuTotal += gts2volumeaxu;
			gts2volumeaxgTotal += gts2volumeaxg;
			gts2volumeaxucnhTotal += gts2volumeaxucnh;
			gts2volumeaxgcnhTotal += gts2volumeaxgcnh;
						
			dealcategoryVO.setMt4volumeaxu(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxu(),businessplatform));
			dealcategoryVO.setMt4volumeaxg(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxg(),businessplatform));
			dealcategoryVO.setMt4volumeaxucnh(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxucnh(),businessplatform));
			dealcategoryVO.setMt4volumeaxgcnh(NumberUtil.NumberFormat(dealcategoryVO.getMt4volumeaxgcnh(),businessplatform));
			dealcategoryVO.setGts2volumeaxu(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxu(),businessplatform));
			dealcategoryVO.setGts2volumeaxg(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxg(),businessplatform));
			dealcategoryVO.setGts2volumeaxucnh(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxucnh(),businessplatform));
			dealcategoryVO.setGts2volumeaxgcnh(NumberUtil.NumberFormat(dealcategoryVO.getGts2volumeaxgcnh(),businessplatform));
		}
		
		if(DetailedEnum.IS_DETAILED.getValue().equals(detailed)){
			DealcategoryVO dealcategoryVOTotal = new DealcategoryVO();
			dealcategoryVOTotal.setExectime("小计");
			dealcategoryVOTotal.setPlatform(searchModel.getPlatformType());
			
			dealcategoryVOTotal.setVolumeaxu(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxuTotal + gts2volumeaxuTotal),businessplatform));
			dealcategoryVOTotal.setVolumeaxg(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgTotal + gts2volumeaxgTotal),businessplatform));
			dealcategoryVOTotal.setVolumeaxucnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxucnhTotal + gts2volumeaxucnhTotal),businessplatform));
			dealcategoryVOTotal.setVolumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgcnhTotal + gts2volumeaxgcnhTotal),businessplatform));
			
			dealcategoryVOTotal.setMt4volumeaxu(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxuTotal),businessplatform));
			dealcategoryVOTotal.setMt4volumeaxg(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgTotal),businessplatform));
			dealcategoryVOTotal.setMt4volumeaxucnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxucnhTotal),businessplatform));
			dealcategoryVOTotal.setMt4volumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(mt4volumeaxgcnhTotal),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxu(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxuTotal),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxg(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxgTotal),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxucnh(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxucnhTotal),businessplatform));
			dealcategoryVOTotal.setGts2volumeaxgcnh(NumberUtil.NumberFormat(String.valueOf(gts2volumeaxgcnhTotal),businessplatform));
			
			rowsList.add(dealcategoryVOTotal);	
		}
		
		return rowsList;
	}

	/**
	 * 分页查询交易情况记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findTradeSituationPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findTradeSituationPageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		Integer total = Integer.valueOf(resultMap.get("total"));

		List<TradeSituationVO> rowsList = JacksonUtil.readValue(rows,new TypeReference<List<TradeSituationVO>>() {});		
		
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();		
		
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;	
	}
	
	/**
	 * 不分页查询交易情况记录
	 */
	@Override
	public List<TradeSituationVO> findTradeSituationList(TradeSearchModel searchModel) throws Exception{				
		RpcResult rpcResult = RpcUtils.post(Constants.findTradeSituationList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<TradeSituationVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<TradeSituationVO>>() {});	

		return rowsList;		
	}


}
