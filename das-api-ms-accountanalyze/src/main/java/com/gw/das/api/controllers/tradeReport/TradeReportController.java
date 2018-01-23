package com.gw.das.api.controllers.tradeReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.common.response.ApiStatusEnum;
import com.gw.das.api.service.TradeReportService;

/**
 * 
 * 交易报表API
 * 
 * @author darren
 *
 */
@RestController
@RequestMapping("/tradeReport")
public class TradeReportController {

	private static Logger logger = LoggerFactory.getLogger(TradeReportController.class);
	
	@Autowired
	private TradeReportService tradeReportService;
	
	
	/**
	 * @api {post} /tradeReport/getTradeDetailClosePageList  平仓交易记录分页查询
	 * @apiSampleRequest /tradeReport/getTradeDetailClosePageList
	 * @apiGroup tradeReportApi
	 * @apiDescription 平仓交易记录分页查询
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} platform 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} sort 排序字段,多个逗号隔开
	 * @apiParam {String} order 排序方向,多个逗号隔开
	 * @apiParam {Integer} pageNumber 第几页
	 * @apiParam {Integer} pageSize 每页多少条数据
	 *
     * @apiParamExample {json} 请求样例：
     *                  /tradeReport/getTradeDetailClosePageList
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051073", startDate: "2017-06-01",endDate: "2017-06-30",sort:"closetime",order:"desc",pageNumber:1,pageSize:10,token: "23e856c9bfad665edb9478c80eac57ad"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} symbol  产品简称
	 * @apiSuccess (返回字段) {String} reason  交易类型
	 * @apiSuccess (返回字段) {String} bonus  赠金
	 * @apiSuccess (返回字段) {String} closetime  平仓时间——伦敦
	 * @apiSuccess (返回字段) {String} accountid  账号ID
	 * @apiSuccess (返回字段) {String} companyid  公司ID
	 * @apiSuccess (返回字段) {String} volumeaxg  手数银
	 * @apiSuccess (返回字段) {String} closetimegmt8  平仓时间——北京
	 * @apiSuccess (返回字段) {String} margin  初始保证金
	 * @apiSuccess (返回字段) {String} swap  利息
	 * @apiSuccess (返回字段) {String} clearzero  系统清零
	 * @apiSuccess (返回字段) {String} openprice  开仓价格
	 * @apiSuccess (返回字段) {String} bonusclear  赠金活动清零
	 * @apiSuccess (返回字段) {String} marginmaintenance  维持保证金
	 * @apiSuccess (返回字段) {String} adjustamt  调整金额
	 * @apiSuccess (返回字段) {String} volumeaxgcnh  手数人民币银
	 * @apiSuccess (返回字段) {String} businessplatform  业务平台id
	 * @apiSuccess (返回字段) {String} companyname  公司名称
	 * @apiSuccess (返回字段) {String} accountno  平台账号
	 * @apiSuccess (返回字段) {String} profitrase  盈亏比例
	 * @apiSuccess (返回字段) {String} symboltype  产品类型
	 * @apiSuccess (返回字段) {String} proposaltype  提案类型
	 * @apiSuccess (返回字段) {String} profitaxgcnh  盈亏人民币银
	 * @apiSuccess (返回字段) {String} status  状态
	 * @apiSuccess (返回字段) {String} ordertype  订单类型
	 * @apiSuccess (返回字段) {String} clienttype  客户端类型
	 * @apiSuccess (返回字段) {String} volumeaxucnh  手数人民币金
	 * @apiSuccess (返回字段) {String} opentimerpt  开仓时间——报表
	 * @apiSuccess (返回字段) {String} accounttype  账户类型
	 * @apiSuccess (返回字段) {String} takeprofit  止盈
	 * @apiSuccess (返回字段) {String} symbolname  产品名称
	 * @apiSuccess (返回字段) {String} profitaxu  盈亏金
	 * @apiSuccess (返回字段) {String} platform  平台
	 * @apiSuccess (返回字段) {String} proposalno  提案号
	 * @apiSuccess (返回字段) {String} closevolume 平仓手数
	 * @apiSuccess (返回字段) {String} openvolume  开仓手数
	 * @apiSuccess (返回字段) {String} positionid  持仓单号
	 * @apiSuccess (返回字段) {String} rate  利率
	 * @apiSuccess (返回字段) {String} currency  账户货币
	 * @apiSuccess (返回字段) {String} commission  佣金
	 * @apiSuccess (返回字段) {String} profitaxg  盈亏银
	 * @apiSuccess (返回字段) {String} closetimerpt  平仓时间——报表
	 * @apiSuccess (返回字段) {String} closeprice  平仓价格
	 * @apiSuccess (返回字段) {String} profit  盈亏
	 * @apiSuccess (返回字段) {String} direction  买卖方向
	 * @apiSuccess (返回字段) {String} marginstopout  强平
	 * @apiSuccess (返回字段) {String} volumeaxu  手数金
	 * @apiSuccess (返回字段) {String} surplusvolume  剩余手数
	 * @apiSuccess (返回字段) {String} orderid  订单号
	 * @apiSuccess (返回字段) {String} stoploss  止损
	 * @apiSuccess (返回字段) {String} dealid  成交订单号
	 * @apiSuccess (返回字段) {String} opentime  开仓时间——伦敦
	 * @apiSuccess (返回字段) {String} opentimegmt8  开仓时间——北京
	 * @apiSuccess (返回字段) {String} profitaxucnh  盈亏人民币金
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "symbol": "LLG",
	 *	            "reason": 161,
	 *	            "bonus": 0,
	 *	            "closetime": "2017-07-06 08:07:54",
	 *	            "accountid": 208055,
	 *	            "companyid": 2,
	 *	            "volumeaxg": 0,
	 *	            "closetimegmt8": "2017-07-06 16:07:54",
	 *	            "margin": 0,
	 *	            "swap": -0.09,
	 *	            "clearzero": 0,
	 *	            "openprice": 1225.59,
	 *	            "bonusclear": 0,
	 *	            "marginmaintenance": 0,
	 *	            "adjustamt": 0,
	 *	            "volumeaxgcnh": 0,
	 *	            "businessplatform": 3,
	 *	            "companyname": "hxpm",
	 *	            "accountno": 60051056,
	 *	            "profitrase": -0.00944035117780003,
	 *	            "symboltype": 1,
	 *	            "proposaltype": 0,
	 *	            "profitaxgcnh": 0,
	 *	            "status": 1,
	 *	            "ordertype": 2,
	 *	            "clienttype": null,
	 *	            "volumeaxucnh": 0,
	 *	            "opentimerpt": "2017-07-04",
	 *	            "accounttype": "直客",
	 *	            "takeprofit": 0,
	 *	            "symbolname": "HX/USD/GTS/MM/MIN/0/A/LLG",
	 *	            "profitaxu": 23.14,
	 *	            "platform": "GTS2",
	 *	            "proposalno": "",
	 *	            "closevolume": 0.02,
	 *	            "openvolume": 0.02,
	 *	            "positionid": 15755599,
	 *	            "rate": 1,
	 *	            "currency": "USD",
	 *	            "commission": 0.02,
	 *	            "profitaxg": 0,
	 *	            "closetimerpt": "2017-07-06",
	 *	            "closeprice": 1214.02,
	 *	            "profit": 23.14,
	 *	            "direction": 2,
	 *	            "marginstopout": 0,
	 *	            "volumeaxu": 0.02,
	 *	            "surplusvolume": 0,
	 *	            "orderid": 0,
	 *	            "stoploss": 0,
	 *	            "dealid": 21943886,
	 *	            "opentime": "2017-07-04 06:57:25",
	 *	            "opentimegmt8": "2017-07-04 14:57:25",
	 *	            "profitaxucnh": 0
	 *	      }
	 *	    ],
	 *	   "pageSize": 10,
	 *	   "pageNumber": 1,
	 *	   "total": 815
	 *	}
	 *
     * @apiError {String} getTradeDetailClosePageList 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "getTradeDetailClosePageList接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/getTradeDetailClosePageList")
	public ApiPageResult getTradeDetailClosePageList(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate
			,@RequestParam String sort, @RequestParam String order,@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		logger.debug("==>>getTradeDetailClosePageList[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate,pageNumber,pageSize);
		try {
			return tradeReportService.tradeDetailClosePageList(companyId,platform,accountNo,startDate,endDate,sort,order,pageNumber,pageSize);		
		} catch (Exception e) {
			logger.error("==>>getTradeDetailClosePageList接口执行异常",e);			
		}
		return new ApiPageResult(ApiStatusEnum.exception, "getTradeDetailClosePageList接口执行异常");
	}
	
	/**
	 * @api {post} /tradeReport/getTradeDetailCloseList  平仓交易记录不分页查询
	 * @apiSampleRequest /tradeReport/getTradeDetailCloseList
	 * @apiGroup tradeReportApi
	 * @apiDescription 平仓交易记录不分页查询
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /tradeReport/getTradeDetailCloseList
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051073", startDate: "2017-06-01",endDate: "2017-06-30",token: "23e856c9bfad665edb9478c80eac57ad"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} symbol  产品简称
	 * @apiSuccess (返回字段) {String} reason  交易类型
	 * @apiSuccess (返回字段) {String} bonus  赠金
	 * @apiSuccess (返回字段) {String} closetime  平仓时间——伦敦
	 * @apiSuccess (返回字段) {String} accountid  账号ID
	 * @apiSuccess (返回字段) {String} companyid  公司ID
	 * @apiSuccess (返回字段) {String} volumeaxg  手数银
	 * @apiSuccess (返回字段) {String} closetimegmt8  平仓时间——北京
	 * @apiSuccess (返回字段) {String} margin  初始保证金
	 * @apiSuccess (返回字段) {String} swap  利息
	 * @apiSuccess (返回字段) {String} clearzero  系统清零
	 * @apiSuccess (返回字段) {String} openprice  开仓价格
	 * @apiSuccess (返回字段) {String} bonusclear  赠金活动清零
	 * @apiSuccess (返回字段) {String} marginmaintenance  维持保证金
	 * @apiSuccess (返回字段) {String} adjustamt  调整金额
	 * @apiSuccess (返回字段) {String} volumeaxgcnh  手数人民币银
	 * @apiSuccess (返回字段) {String} businessplatform  业务平台id
	 * @apiSuccess (返回字段) {String} companyname  公司名称
	 * @apiSuccess (返回字段) {String} accountno  平台账号
	 * @apiSuccess (返回字段) {String} profitrase  盈亏比例
	 * @apiSuccess (返回字段) {String} symboltype  产品类型
	 * @apiSuccess (返回字段) {String} proposaltype  提案类型
	 * @apiSuccess (返回字段) {String} profitaxgcnh  盈亏人民币银
	 * @apiSuccess (返回字段) {String} status  状态
	 * @apiSuccess (返回字段) {String} ordertype  订单类型
	 * @apiSuccess (返回字段) {String} clienttype  客户端类型
	 * @apiSuccess (返回字段) {String} volumeaxucnh  手数人民币金
	 * @apiSuccess (返回字段) {String} opentimerpt  开仓时间——报表
	 * @apiSuccess (返回字段) {String} accounttype  账户类型
	 * @apiSuccess (返回字段) {String} takeprofit  止盈
	 * @apiSuccess (返回字段) {String} symbolname  产品名称
	 * @apiSuccess (返回字段) {String} profitaxu  盈亏金
	 * @apiSuccess (返回字段) {String} platform  平台
	 * @apiSuccess (返回字段) {String} proposalno  提案号
	 * @apiSuccess (返回字段) {String} closevolume 平仓手数
	 * @apiSuccess (返回字段) {String} openvolume  开仓手数
	 * @apiSuccess (返回字段) {String} positionid  持仓单号
	 * @apiSuccess (返回字段) {String} rate  利率
	 * @apiSuccess (返回字段) {String} currency  账户货币
	 * @apiSuccess (返回字段) {String} commission  佣金
	 * @apiSuccess (返回字段) {String} profitaxg  盈亏银
	 * @apiSuccess (返回字段) {String} closetimerpt  平仓时间——报表
	 * @apiSuccess (返回字段) {String} closeprice  平仓价格
	 * @apiSuccess (返回字段) {String} profit  盈亏
	 * @apiSuccess (返回字段) {String} direction  买卖方向
	 * @apiSuccess (返回字段) {String} marginstopout  强平
	 * @apiSuccess (返回字段) {String} volumeaxu  手数金
	 * @apiSuccess (返回字段) {String} surplusvolume  剩余手数
	 * @apiSuccess (返回字段) {String} orderid  订单号
	 * @apiSuccess (返回字段) {String} stoploss  止损
	 * @apiSuccess (返回字段) {String} dealid  成交订单号
	 * @apiSuccess (返回字段) {String} opentime  开仓时间——伦敦
	 * @apiSuccess (返回字段) {String} opentimegmt8  开仓时间——北京
	 * @apiSuccess (返回字段) {String} profitaxucnh  盈亏人民币金
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "symbol": "LLG",
	 *	            "reason": 161,
	 *	            "bonus": 0,
	 *	            "closetime": "2017-07-06 08:07:54",
	 *	            "accountid": 208055,
	 *	            "companyid": 2,
	 *	            "volumeaxg": 0,
	 *	            "closetimegmt8": "2017-07-06 16:07:54",
	 *	            "margin": 0,
	 *	            "swap": -0.09,
	 *	            "clearzero": 0,
	 *	            "openprice": 1225.59,
	 *	            "bonusclear": 0,
	 *	            "marginmaintenance": 0,
	 *	            "adjustamt": 0,
	 *	            "volumeaxgcnh": 0,
	 *	            "businessplatform": 3,
	 *	            "companyname": "hxpm",
	 *	            "accountno": 60051056,
	 *	            "profitrase": -0.00944035117780003,
	 *	            "symboltype": 1,
	 *	            "proposaltype": 0,
	 *	            "profitaxgcnh": 0,
	 *	            "status": 1,
	 *	            "ordertype": 2,
	 *	            "clienttype": null,
	 *	            "volumeaxucnh": 0,
	 *	            "opentimerpt": "2017-07-04",
	 *	            "accounttype": "直客",
	 *	            "takeprofit": 0,
	 *	            "symbolname": "HX/USD/GTS/MM/MIN/0/A/LLG",
	 *	            "profitaxu": 23.14,
	 *	            "platform": "GTS2",
	 *	            "proposalno": "",
	 *	            "closevolume": 0.02,
	 *	            "openvolume": 0.02,
	 *	            "positionid": 15755599,
	 *	            "rate": 1,
	 *	            "currency": "USD",
	 *	            "commission": 0.02,
	 *	            "profitaxg": 0,
	 *	            "closetimerpt": "2017-07-06",
	 *	            "closeprice": 1214.02,
	 *	            "profit": 23.14,
	 *	            "direction": 2,
	 *	            "marginstopout": 0,
	 *	            "volumeaxu": 0.02,
	 *	            "surplusvolume": 0,
	 *	            "orderid": 0,
	 *	            "stoploss": 0,
	 *	            "dealid": 21943886,
	 *	            "opentime": "2017-07-04 06:57:25",
	 *	            "opentimegmt8": "2017-07-04 14:57:25",
	 *	            "profitaxucnh": 0
	 *	      }
	 *	    ]
	 *	}
	 *
     * @apiError {String} getTradeDetailCloseList 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "getTradeDetailCloseList接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/getTradeDetailCloseList")
	public ApiResult getTradeDetailCloseList(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>getTradeDetailCloseList[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate);
		ApiResult result = null;
		try {
			return tradeReportService.tradeDetailCloseList(companyId, platform,accountNo, startDate, endDate);
		} catch (Exception e) {
			logger.error("==>>getTradeDetailCloseList接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "getTradeDetailCloseList接口执行异常");
		}
		return result;
	}
	
	/**
	 * @api {post} /tradeReport/getTradeDetailOpenPageList  持仓交易记录分页查询
	 * @apiSampleRequest /tradeReport/getTradeDetailOpenPageList
	 * @apiGroup tradeReportApi
	 * @apiDescription 持仓交易记录分页查询
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} sort 排序字段,多个逗号隔开
	 * @apiParam {String} order 排序方向,多个逗号隔开
	 * @apiParam {Integer} pageNumber 第几页
	 * @apiParam {Integer} pageSize 每页多少条数据
	 *
     * @apiParamExample {json} 请求样例：
     *                  /tradeReport/getTradeDetailOpenPageList
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051073", startDate: "2017-06-01",endDate: "2017-06-30",sort:"opentime",order:"desc",pageNumber:1,pageSize:10,token: "23e856c9bfad665edb9478c80eac57ad"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} symbol  产品简称
	 * @apiSuccess (返回字段) {String} bonus  赠金
	 * @apiSuccess (返回字段) {String} closetime  平仓时间——伦敦
	 * @apiSuccess (返回字段) {String} accountid  账号ID
	 * @apiSuccess (返回字段) {String} companyid  公司ID
	 * @apiSuccess (返回字段) {String} volumeaxg  手数银
	 * @apiSuccess (返回字段) {String} closetimegmt8  平仓时间——北京
	 * @apiSuccess (返回字段) {String} margin  初始保证金
	 * @apiSuccess (返回字段) {String} swap  利息
	 * @apiSuccess (返回字段) {String} clearzero  系统清零
	 * @apiSuccess (返回字段) {String} openprice  开仓价格
	 * @apiSuccess (返回字段) {String} marginmaintenance  维持保证金
	 * @apiSuccess (返回字段) {String} volume  手数
	 * @apiSuccess (返回字段) {String} adjustamt  调整金额
	 * @apiSuccess (返回字段) {String} volumeaxgcnh  手数人民币银
	 * @apiSuccess (返回字段) {String} businessplatform  业务平台id
	 * @apiSuccess (返回字段) {String} companyname  公司名称
	 * @apiSuccess (返回字段) {String} accountno  平台账号
	 * @apiSuccess (返回字段) {String} symboltype  产品类型
	 * @apiSuccess (返回字段) {String} proposaltype  提案类型
	 * @apiSuccess (返回字段) {String} profitaxgcnh  盈亏人民币银
	 * @apiSuccess (返回字段) {String} status  状态
	 * @apiSuccess (返回字段) {String} ordertype  订单类型
	 * @apiSuccess (返回字段) {String} clienttype  客户端类型
	 * @apiSuccess (返回字段) {String} volumeaxucnh  手数人民币金
	 * @apiSuccess (返回字段) {String} opentimerpt  开仓时间——报表
	 * @apiSuccess (返回字段) {String} accounttype  账户类型
	 * @apiSuccess (返回字段) {String} takeprofit  止盈
	 * @apiSuccess (返回字段) {String} symbolname  产品名称
	 * @apiSuccess (返回字段) {String} profitaxu  盈亏金
	 * @apiSuccess (返回字段) {String} platform  平台
	 * @apiSuccess (返回字段) {String} proposalno  提案号
	 * @apiSuccess (返回字段) {String} closecnt  平仓次数
	 * @apiSuccess (返回字段) {String} closevolume  平仓手数
	 * @apiSuccess (返回字段) {String} positionid  持仓单号
	 * @apiSuccess (返回字段) {String} rate  利率
	 * @apiSuccess (返回字段) {String} currency  账户货币
	 * @apiSuccess (返回字段) {String} commission  佣金
	 * @apiSuccess (返回字段) {String} profitaxg  盈亏银
	 * @apiSuccess (返回字段) {String} closetimerpt  平仓时间——报表
	 * @apiSuccess (返回字段) {String} closeprice  平仓价格
	 * @apiSuccess (返回字段) {String} profit  盈亏
	 * @apiSuccess (返回字段) {String} direction  买卖方向
	 * @apiSuccess (返回字段) {String} marginstopout  强平
	 * @apiSuccess (返回字段) {String} volumeaxu  手数金
	 * @apiSuccess (返回字段) {String} surplusvolume 剩余手数
	 * @apiSuccess (返回字段) {String} stoploss  止损
	 * @apiSuccess (返回字段) {String} opentime  开仓时间——伦敦
	 * @apiSuccess (返回字段) {String} opentimegmt8  开仓时间——北京
	 * @apiSuccess (返回字段) {String} profitaxucnh  盈亏人民币金
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "symbol": "GOLD",
	 *	            "bonus": 0,
	 *	            "closetime": "2017-07-21 20:46:02",
	 *	            "accountid": 258012072,
	 *	            "companyid": 2,
	 *	            "volumeaxg": 0,
	 *	            "closetimegmt8": "2017-07-22 04:46:02",
	 *	            "margin": 0,
	 *	            "swap": -19.03,
	 *	            "clearzero": 0,
	 *	            "openprice": 1221.99,
	 *	            "marginmaintenance": 0,
	 *	            "volume": 1,
	 *	            "adjustamt": 0,
	 *	            "volumeaxgcnh": 0,
	 *	            "businessplatform": 3,
	 *	            "companyname": "hxpm",
	 *	            "accountno": 258012072,
	 *	            "symboltype": 2,
	 *	            "proposaltype": 0,
	 *	            "profitaxgcnh": 0,
	 *	            "status": 2,
	 *	            "ordertype": 1,
	 *	            "clienttype": null,
	 *	            "volumeaxucnh": 0,
	 *	            "opentimerpt": "2017-07-13",
	 *	            "accounttype": "直客",
	 *	            "takeprofit": 0,
	 *	            "symbolname": "GOLD",
	 *	            "profitaxu": -3300,
	 *	            "platform": "MT4",
	 *	            "proposalno": "0",
	 *	            "closecnt": 1,
	 *	            "closevolume": 1,
	 *	            "positionid": -20152019,
	 *	            "rate": 1,
	 *	            "currency": "USD",
	 *	            "commission": 0,
	 *	            "profitaxg": 0,
	 *	            "closetimerpt": "2017-07-21",
	 *	            "closeprice": 0,
	 *	            "profit": -3300,
	 *	            "direction": 2,
	 *	            "marginstopout": 0,
	 *	            "volumeaxu": 1,
	 *	            "surplusvolume": 0,
	 *	            "stoploss": 0,
	 *	            "opentime": "2017-07-13 09:21:06",
	 *	            "opentimegmt8": "2017-07-13 17:21:06",
	 *	            "profitaxucnh": 0
	 *	        }
	 *	    ],
	 *	    "pageSize": 10,
	 *	    "pageNumber": 1,
	 *	    "total": 1238
	 *	}
	 *
     * @apiError {String} getTradeDetailOpenPageList 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "getTradeDetailOpenPageList接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/getTradeDetailOpenPageList")
	public ApiPageResult getTradeDetailOpenPageList(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate
			,@RequestParam String sort, @RequestParam String order,@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		logger.debug("==>>getTradeDetailOpenPageList[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate,pageNumber,pageSize);
		try {
			return tradeReportService.tradeDetailOpenPageList(companyId,platform,accountNo,startDate,endDate,sort,order,pageNumber,pageSize);		
		} catch (Exception e) {
			logger.error("==>>getTradeDetailOpenPageList接口执行异常",e);			
		}
		return new ApiPageResult(ApiStatusEnum.exception, "getTradeDetailOpenPageList接口执行异常");
	}
	
	/**
	 * @api {post} /tradeReport/getTradeDetailOpenList  持仓交易记录不分页查询
	 * @apiSampleRequest /tradeReport/getTradeDetailOpenList
	 * @apiGroup tradeReportApi
	 * @apiDescription 持仓交易记录不分页查询
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /tradeReport/getTradeDetailOpenList
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051073", startDate: "2017-06-01",endDate: "2017-06-30",token: "23e856c9bfad665edb9478c80eac57ad"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} symbol  产品简称
	 * @apiSuccess (返回字段) {String} bonus  赠金
	 * @apiSuccess (返回字段) {String} closetime  平仓时间——伦敦
	 * @apiSuccess (返回字段) {String} accountid  账号ID
	 * @apiSuccess (返回字段) {String} companyid  公司ID
	 * @apiSuccess (返回字段) {String} volumeaxg  手数银
	 * @apiSuccess (返回字段) {String} closetimegmt8  平仓时间——北京
	 * @apiSuccess (返回字段) {String} margin  初始保证金
	 * @apiSuccess (返回字段) {String} swap  利息
	 * @apiSuccess (返回字段) {String} clearzero  系统清零
	 * @apiSuccess (返回字段) {String} openprice  开仓价格
	 * @apiSuccess (返回字段) {String} marginmaintenance  维持保证金
	 * @apiSuccess (返回字段) {String} volume  手数
	 * @apiSuccess (返回字段) {String} adjustamt  调整金额
	 * @apiSuccess (返回字段) {String} volumeaxgcnh  手数人民币银
	 * @apiSuccess (返回字段) {String} businessplatform  业务平台id
	 * @apiSuccess (返回字段) {String} companyname  公司名称
	 * @apiSuccess (返回字段) {String} accountno  平台账号
	 * @apiSuccess (返回字段) {String} symboltype  产品类型
	 * @apiSuccess (返回字段) {String} proposaltype  提案类型
	 * @apiSuccess (返回字段) {String} profitaxgcnh  盈亏人民币银
	 * @apiSuccess (返回字段) {String} status  状态
	 * @apiSuccess (返回字段) {String} ordertype  订单类型
	 * @apiSuccess (返回字段) {String} clienttype  客户端类型
	 * @apiSuccess (返回字段) {String} volumeaxucnh  手数人民币金
	 * @apiSuccess (返回字段) {String} opentimerpt  开仓时间——报表
	 * @apiSuccess (返回字段) {String} accounttype  账户类型
	 * @apiSuccess (返回字段) {String} takeprofit  止盈
	 * @apiSuccess (返回字段) {String} symbolname  产品名称
	 * @apiSuccess (返回字段) {String} profitaxu  盈亏金
	 * @apiSuccess (返回字段) {String} platform  平台
	 * @apiSuccess (返回字段) {String} proposalno  提案号
	 * @apiSuccess (返回字段) {String} closecnt  平仓次数
	 * @apiSuccess (返回字段) {String} closevolume  平仓手数
	 * @apiSuccess (返回字段) {String} positionid  持仓单号
	 * @apiSuccess (返回字段) {String} rate  利率
	 * @apiSuccess (返回字段) {String} currency  账户货币
	 * @apiSuccess (返回字段) {String} commission  佣金
	 * @apiSuccess (返回字段) {String} profitaxg  盈亏银
	 * @apiSuccess (返回字段) {String} closetimerpt  平仓时间——报表
	 * @apiSuccess (返回字段) {String} closeprice  平仓价格
	 * @apiSuccess (返回字段) {String} profit  盈亏
	 * @apiSuccess (返回字段) {String} direction  买卖方向
	 * @apiSuccess (返回字段) {String} marginstopout  强平
	 * @apiSuccess (返回字段) {String} volumeaxu  手数金
	 * @apiSuccess (返回字段) {String} surplusvolume 剩余手数
	 * @apiSuccess (返回字段) {String} stoploss  止损
	 * @apiSuccess (返回字段) {String} opentime  开仓时间——伦敦
	 * @apiSuccess (返回字段) {String} opentimegmt8  开仓时间——北京
	 * @apiSuccess (返回字段) {String} profitaxucnh  盈亏人民币金
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "symbol": "GOLD",
	 *	            "bonus": 0,
	 *	            "closetime": "2017-07-21 20:46:02",
	 *	            "accountid": 258012072,
	 *	            "companyid": 2,
	 *	            "volumeaxg": 0,
	 *	            "closetimegmt8": "2017-07-22 04:46:02",
	 *	            "margin": 0,
	 *	            "swap": -19.03,
	 *	            "clearzero": 0,
	 *	            "openprice": 1221.99,
	 *	            "marginmaintenance": 0,
	 *	            "volume": 1,
	 *	            "adjustamt": 0,
	 *	            "volumeaxgcnh": 0,
	 *	            "businessplatform": 3,
	 *	            "companyname": "hxpm",
	 *	            "accountno": 258012072,
	 *	            "symboltype": 2,
	 *	            "proposaltype": 0,
	 *	            "profitaxgcnh": 0,
	 *	            "status": 2,
	 *	            "ordertype": 1,
	 *	            "clienttype": null,
	 *	            "volumeaxucnh": 0,
	 *	            "opentimerpt": "2017-07-13",
	 *	            "accounttype": "直客",
	 *	            "takeprofit": 0,
	 *	            "symbolname": "GOLD",
	 *	            "profitaxu": -3300,
	 *	            "platform": "MT4",
	 *	            "proposalno": "0",
	 *	            "closecnt": 1,
	 *	            "closevolume": 1,
	 *	            "positionid": -20152019,
	 *	            "rate": 1,
	 *	            "currency": "USD",
	 *	            "commission": 0,
	 *	            "profitaxg": 0,
	 *	            "closetimerpt": "2017-07-21",
	 *	            "closeprice": 0,
	 *	            "profit": -3300,
	 *	            "direction": 2,
	 *	            "marginstopout": 0,
	 *	            "volumeaxu": 1,
	 *	            "surplusvolume": 0,
	 *	            "stoploss": 0,
	 *	            "opentime": "2017-07-13 09:21:06",
	 *	            "opentimegmt8": "2017-07-13 17:21:06",
	 *	            "profitaxucnh": 0
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} getTradeDetailOpenList 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "getTradeDetailOpenList接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/getTradeDetailOpenList")
	public ApiResult getTradeDetailOpenList(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>getTradeDetailOpenList[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate);
		ApiResult result = null;
		try {
			return tradeReportService.tradeDetailOpenList(companyId, platform,accountNo, startDate, endDate);
		} catch (Exception e) {
			logger.error("==>>getTradeDetailOpenList接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "getTradeDetailOpenList接口执行异常");
		}
		return result;
	}
	
	/**
	 * @api {post} /tradeReport/getAccountbalancePageList  账户余额变动记录分页查询
	 * @apiSampleRequest /tradeReport/getAccountbalancePageList
	 * @apiGroup tradeReportApi
	 * @apiDescription 账户余额变动记录分页查询
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属、恒信、创富为北京时间,外汇为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属、恒信、创富为北京时间,外汇为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} sort 排序字段,多个逗号隔开
	 * @apiParam {String} order 排序方向,多个逗号隔开
	 * @apiParam {Integer} pageNumber 第几页
	 * @apiParam {Integer} pageSize 每页多少条数据
	 *
     * @apiParamExample {json} 请求样例：
     *                  /tradeReport/getAccountbalancePageList
     *                  {companyId: "2", platform: "",accountNo:"258013066", startDate: "2017-08-01",endDate: "2017-08-30",sort:"closetime_gmt8",order:"desc",pageNumber:1,pageSize:10,token: "0cb87543be9155d719e8ef4be33c79b1"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} datetime  时间
	 * @apiSuccess (返回字段) {String} accountno 账户
	 * @apiSuccess (返回字段) {String} orderid  订单号
	 * @apiSuccess (返回字段) {String} reason  交易类型
	 * @apiSuccess (返回字段) {String} amount  金额
	 * @apiSuccess (返回字段) {String} platform  平台
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "reason": 0,
	 *	            "datetime": 1501655089000,
	 *	            "amount": 0,
	 *	            "orderid": 0,
	 *	            "platform": "MT4",
	 *	            "accountno": 258013066
	 *	        },
	 *	        {
	 *	            "reason": 8,
	 *	            "datetime": 1501655089000,
	 *	            "amount": 0,
	 *	            "orderid": 0,
	 *	            "platform": "MT4",
	 *	            "accountno": 258013066
	 *	        },
	 *	        {
	 *	            "reason": 8,
	 *	            "datetime": 1501654699000,
	 *	            "amount": 0,
	 *	            "orderid": 0,
	 *	            "platform": "MT4",
	 *	            "accountno": 258013066
	 *	        },
	 *	        {
	 *	            "reason": 0,
	 *	            "datetime": 1501654699000,
	 *	            "amount": 0,
	 *	            "orderid": 0,
	 *	            "platform": "MT4",
	 *	            "accountno": 258013066
	 *	        },
	 *	        {
	 *	            "reason": 0,
	 *	            "datetime": 1501654693000,
	 *	            "amount": 0,
	 *	            "orderid": 0,
	 *	            "platform": "MT4",
	 *	            "accountno": 258013066
	 *	        },
	 *	        {
	 *	            "reason": 8,
	 *	            "datetime": 1501654693000,
	 *	            "amount": 0,
	 *	            "orderid": 0,
	 *	            "platform": "MT4",
	 *	            "accountno": 258013066
	 *	        }
	 *	    ],
	 *	    "pageSize": 10,
	 *	    "pageNumber": 1,
	 *	    "total": 6
	 *	}
	 *
     * @apiError {String} getAccountbalancePageList 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "getAccountbalancePageList接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/getAccountbalancePageList")
	public ApiPageResult getAccountbalancePageList(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate
			,@RequestParam String sort, @RequestParam String order,@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		logger.debug("==>>getAccountbalancePageList[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate,pageNumber,pageSize);
		try {
			return tradeReportService.accountbalancePageList(companyId,platform,accountNo,startDate,endDate,sort,order,pageNumber,pageSize);		
		} catch (Exception e) {
			logger.error("==>>getAccountbalancePageList接口执行异常",e);			
		}
		return new ApiPageResult(ApiStatusEnum.exception, "getAccountbalancePageList接口执行异常");
	}
	
	/**
	 * @api {post} /tradeReport/getAccountbalanceList  账户余额变动记录不分页查询
	 * @apiSampleRequest /tradeReport/getAccountbalanceList
	 * @apiGroup tradeReportApi
	 * @apiDescription 账户余额变动记录不分页查询
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属、恒信、创富为北京时间,外汇为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属、恒信、创富为北京时间,外汇为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /tradeReport/getAccountbalanceList
     *                  {companyId: "2", platform: "",accountNo:"258013066", startDate: "2017-08-01",endDate: "2017-08-30",token: "fcb3eea08bc8f3f5e62fdcdef4b9a2dd"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} datetime  时间
	 * @apiSuccess (返回字段) {String} accountno 账户
	 * @apiSuccess (返回字段) {String} orderid  订单号
	 * @apiSuccess (返回字段) {String} reason  交易类型
	 * @apiSuccess (返回字段) {String} amount  金额
	 * @apiSuccess (返回字段) {String} platform  平台
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "reason": 0,
	 *	            "datetime": 1501654693000,
	 *	            "amount": 0,
	 *	            "orderid": 0,
	 *	            "business_platform": 3,
	 *	            "account_no": 258013066,
	 *	            "platform": "MT4"
	 *	        },
	 *	        {
	 *	            "reason": 8,
	 *	            "datetime": 1501654699000,
	 *	            "amount": 0,
	 *	            "orderid": 0,
	 *	            "business_platform": 3,
	 *	            "account_no": 258013066,
	 *	            "platform": "MT4"
	 *	        },
	 *	        {
	 *	            "reason": 8,
	 *	            "datetime": 1501654693000,
	 *	            "amount": 0,
	 *	            "orderid": 0,
	 *	            "business_platform": 3,
	 *	            "account_no": 258013066,
	 *	            "platform": "MT4"
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} getAccountbalanceList 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "getAccountbalanceList接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/getAccountbalanceList")
	public ApiResult getAccountbalanceList(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>getAccountbalanceList[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate);
		ApiResult result = null;
		try {
			return tradeReportService.accountbalanceList(companyId, platform,accountNo, startDate, endDate);
		} catch (Exception e) {
			logger.error("==>>getAccountbalanceList接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "getAccountbalanceList接口执行异常");
		}
		return result;
	}
	
	/**
	 * @api {post} /tradeReport/getCustomerdailyPageList  账户结算日报表记录分页查询
	 * @apiSampleRequest /tradeReport/getCustomerdailyPageList
	 * @apiGroup tradeReportApi
	 * @apiDescription 账户结算日报表记录分页查询
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} sort 排序字段,多个逗号隔开
	 * @apiParam {String} order 排序方向,多个逗号隔开
	 * @apiParam {Integer} pageNumber 第几页
	 * @apiParam {Integer} pageSize 每页多少条数据
	 *
     * @apiParamExample {json} 请求样例：
     *                  /tradeReport/getCustomerdailyPageList
     *                  {companyId: "2", platform: "",accountNo:"258013066", startDate: "2017-08-01",endDate: "2017-08-30",sort:"execdate_rpt",order:"desc",pageNumber:1,pageSize:10,token: "d05032c38c2632e67afe3e9bca3c87a8"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} execdaterpt  日期
	 * @apiSuccess (返回字段) {String} accountno  账户
	 * @apiSuccess (返回字段) {String} balance  账户余额
	 * @apiSuccess (返回字段) {String} equity  账户净值
	 * @apiSuccess (返回字段) {String} deposit  存款
	 * @apiSuccess (返回字段) {String} withdraw  取款
	 * @apiSuccess (返回字段) {String} balancePreviousbalance  当日平仓盈亏
	 * @apiSuccess (返回字段) {String} floatingprofit  浮动盈亏
	 * @apiSuccess (返回字段) {String} margin  占用保证金
	 * @apiSuccess (返回字段) {String} freemargin  可用保证金
	 * @apiSuccess (返回字段) {String} platform  平台
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-21",
	 *	            "balance": 50489.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        },
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-20",
	 *	            "balance": 50489.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        },
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-19",
	 *	            "balance": 50489.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        },
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-18",
	 *	            "balance": 50489.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        },
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-17",
	 *	            "balance": 50489.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        },
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-16",
	 *	            "balance": 50489.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        },
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-15",
	 *	            "balance": 50489.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        },
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-14",
	 *	            "balance": 50510.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50510.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 21,
	 *	            "freemargin": 50510.5
	 *	        },
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-13",
	 *	            "balance": 50510.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50510.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50510.5
	 *	        },
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "execdaterpt": "2017-08-12",
	 *	            "balance": 50510.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50510.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50510.5
	 *	        }
	 *	    ],
	 *	    "pageSize": 10,
	 *	    "pageNumber": 1,
	 *	    "total": 21
	 *	}
	 *
     * @apiError {String} getCustomerdailyPageList 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "getCustomerdailyPageList接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/getCustomerdailyPageList")
	public ApiPageResult getCustomerdailyPageList(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate
			,@RequestParam String sort, @RequestParam String order,@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		logger.debug("==>>getCustomerdailyPageList[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate,pageNumber,pageSize);
		try {
			return tradeReportService.customerdailyPageList(companyId,platform,accountNo,startDate,endDate,sort,order,pageNumber,pageSize);		
		} catch (Exception e) {
			logger.error("==>>getCustomerdailyPageList接口执行异常",e);			
		}
		return new ApiPageResult(ApiStatusEnum.exception, "getCustomerdailyPageList接口执行异常");
	}
	
	/**
	 * @api {post} /tradeReport/getCustomerdailyList  账户结算日报表记录不分页查询
	 * @apiSampleRequest /tradeReport/getCustomerdailyList
	 * @apiGroup tradeReportApi
	 * @apiDescription 账户结算日报表记录不分页查询
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /tradeReport/getCustomerdailyList
     *                  {companyId: "2", platform: "",accountNo:"258013066", startDate: "2017-08-01",endDate: "2017-08-30",token: "0cb87543be9155d719e8ef4be33c79b1"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} execdaterpt  日期
	 * @apiSuccess (返回字段) {String} accountno  账户
	 * @apiSuccess (返回字段) {String} balance  账户余额
	 * @apiSuccess (返回字段) {String} equity  账户净值
	 * @apiSuccess (返回字段) {String} deposit  存款
	 * @apiSuccess (返回字段) {String} withdraw  取款
	 * @apiSuccess (返回字段) {String} balancePreviousbalance  当日平仓盈亏
	 * @apiSuccess (返回字段) {String} floatingprofit  浮动盈亏
	 * @apiSuccess (返回字段) {String} margin  占用保证金
	 * @apiSuccess (返回字段) {String} freemargin  可用保证金
	 * @apiSuccess (返回字段) {String} platform  平台
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *              "execdaterpt": "2017-08-21",
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "balance": 50489.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        },
	 *	        {
	 *              "execdaterpt": "2017-08-20",
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "balance": 50489.5,
 	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        },
	 *	        {
	 *              "execdaterpt": "2017-08-19",
	 *	            "floatingprofit": 0,
	 *	            "margin": 0,
	 *	            "balancepreviousbalance": 0,
	 *	            "balance": 50489.5,
	 *	            "accountno": 258013066,
	 *	            "deposit": 0,
	 *	            "equity": 50489.5,
	 *	            "platform": "MT4",
	 *	            "withdraw": 0,
	 *	            "freemargin": 50489.5
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} getCustomerdailyList 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "getCustomerdailyList接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/getCustomerdailyList")
	public ApiResult getCustomerdailyList(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>getCustomerdailyList[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate);
		ApiResult result = null;
		try {
			return tradeReportService.customerdailyList(companyId, platform,accountNo, startDate, endDate);
		} catch (Exception e) {
			logger.error("==>>getCustomerdailyList接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "getCustomerdailyList接口执行异常");
		}
		return result;
	}
	
	
}
