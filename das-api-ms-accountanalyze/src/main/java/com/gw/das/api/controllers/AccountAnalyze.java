package com.gw.das.api.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.common.response.ApiStatusEnum;
import com.gw.das.api.service.AccountAnalyzeService;
import com.gw.das.api.service.AccountDiagnosisService;

/**
 * 账户诊断 数据
 * 
 * @author: James.pu
 * @Email : James.pu@gwtsz.net
 * @Create Date: 2017年5月26日
 */
@RestController
@RequestMapping("/accountAnalyze")
public class AccountAnalyze {

	private static Logger logger = LoggerFactory.getLogger(AccountAnalyze.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AccountAnalyzeService accountAnalyzeService;
	
	@Autowired
	private AccountDiagnosisService accountDiagnosisService;

	/**
	 * @api {post} /accountAnalyze/marginLevel  获取保证金水平走势
     * @apiName 获取保证金水平走势
	 * @apiGroup accountDiagnosisApi
	 * @apiDescription 获取保证金水平走势
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {String} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {String} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * 
	 * 
     * @apiParamExample {json} 请求样例：
     *                  /accountAnalyze/marginLevel
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051134", startDate: "2017-06-01",endDate: "2017-06-30",token: "5b3fac1b1d7bf8ec019ebb086dee29ea"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} companyid 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiSuccess (返回字段) {String} accountno 交易账号
	 * @apiSuccess (返回字段) {String} platform 账户所在平台
	 * @apiSuccess (返回字段) {String} currency 币种
	 * @apiSuccess (返回字段) {String} balance 结余
	 * @apiSuccess (返回字段) {String} floatingprofit 浮动盈亏
	 * @apiSuccess (返回字段) {String} margin 保证金
	 * @apiSuccess (返回字段) {String} marginratio 保证金水平
	 * @apiSuccess (返回字段) {String} execdaterpt 时间
	 * 
     * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "companyid": 2,
	 *	            "floatingprofit": -776.24,
	 *	            "margin": 3440,
	 *	            "marginratio": 1602.32,
	 *	            "execdaterpt": "2017-06-30",
	 *	            "balance": 55895.88,
	 *	            "accountno": 60051134,
	 *	            "currency": "USD",
	 *	            "platform": "GTS2"
	 *	        },
	 *	        {
	 *	            "companyid": 2,
	 *	            "floatingprofit": 845.84,
	 *	            "margin": 9240,
	 *	            "marginratio": 549.73,
	 *	            "execdaterpt": "2017-06-29",
	 *	            "balance": 49949.2,
	 *	            "accountno": 60051134,
	 *	            "currency": "USD",
	 *	            "platform": "GTS2"
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} marginLevel 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "marginLevel接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/marginLevel")
	public ApiResult marginLevel(@RequestParam String companyId, String platform, @RequestParam String accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>marginLevel[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo, startDate,
				endDate);
		long state= System.currentTimeMillis();
		ApiResult result = null;
		try {
			List rows = accountAnalyzeService.marginLevel(companyId, platform, accountNo, startDate, endDate);			
			result = new ApiResult(ApiStatusEnum.success, rows);
		} catch (Exception e) {
			logger.error("==>>marginLevel接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "marginLevel接口执行异常");
		} finally {
			logger.info("执行时间:{}秒", (System.currentTimeMillis()-state)/1000);
		}
		return result;
	}

	/**
	 * @api {post} /accountAnalyze/topProfit  获取3笔最大盈利单
	 * @apiGroup accountDiagnosisApi
	 * @apiDescription 获取3笔最大盈利单
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {String} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {String} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * 
     * @apiParamExample {json} 请求样例：
     *                  /accountAnalyze/topProfit
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051134", startDate: "2017-06-01",endDate: "2017-06-30",token: "d67f6ee347c3cf2d183aeb48ea9c49f7"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} companyid 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiSuccess (返回字段) {String} accountno 交易账号
	 * @apiSuccess (返回字段) {String} platform 账户所在平台
	 * @apiSuccess (返回字段) {String} accountid 账号ID
	 * @apiSuccess (返回字段) {String} dealid 订单号
	 * @apiSuccess (返回字段) {String} positionid 持仓单号
	 * @apiSuccess (返回字段) {String} opentime 开仓时间
	 * @apiSuccess (返回字段) {String} direction 开仓方向
	 * @apiSuccess (返回字段) {String} openprice 开仓价格
	 * @apiSuccess (返回字段) {String} closetime 平仓时间
	 * @apiSuccess (返回字段) {String} closeprice 平仓价格
	 * @apiSuccess (返回字段) {String} currency 账户货币
	 * @apiSuccess (返回字段) {String} symbolshortname 产品简称
	 * @apiSuccess (返回字段) {String} reason 交易类型
	 * @apiSuccess (返回字段) {String} volume 手数
	 * @apiSuccess (返回字段) {String} sl 止损设置
	 * @apiSuccess (返回字段) {String} tp 止盈设置
	 * @apiSuccess (返回字段) {String} commission 佣金
	 * @apiSuccess (返回字段) {String} swap 过夜利息
	 * @apiSuccess (返回字段) {String} jyk 盈亏
	 * @apiSuccess (返回字段) {String} ykds 盈亏点数
	 * @apiSuccess (返回字段) {String} digits 小数位
	 * 
     * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "reason": "止盈平仓",
	 *	            "swap": -45.24,
	 *	            "dealid": 21941082,
	 *	            "openprice": 16.71,
	 *	            "closetime": "2017-06-29 11:17:42",
	 *	            "symbolshortname": "LLS",
	 *	            "opentime": "2017-06-26 14:43:42",
	 *	            "platform": "GTS2",
	 *	            "volume": 1,
	 *	            "accountid": 209138,
	 *	            "companyid": 2,
	 *	            "positionid": 15754520,
	 *	            "accountno": 60051134,
	 *	            "sl": 0,
	 *	            "ykds": -155,
	 *	            "currency": "USD",
	 *	            "commission": 0,
	 *              "digits": 3,
	 *	            "closeprice": 16.86,
	 *	            "tp": 0,
	 *	            "jyk": 729.76,
	 *	            "direction": 1
	 *	        },
	 *	        {
	 *	            "reason": "止损平仓",
	 *	            "swap": -48.72,
	 *	            "dealid": 21941152,
	 *	            "openprice": 16.71,
	 *	            "closetime": "2017-06-29 12:46:34",
	 *	            "symbolshortname": "XAGCNH",
	 *	            "opentime": "2017-06-26 14:42:49",
	 *	            "platform": "GTS2",
	 *	            "volume": 1,
	 *	            "accountid": 209138,
	 *	            "companyid": 2,
	 *	            "positionid": 15754509,
	 *	            "accountno": 60051134,
	 *	            "sl": 0,
	 *	            "ykds": -155,
	 *	            "currency": "USD",
	 *	            "commission": 0,
	 *              "digits": 2,
	 *	            "closeprice": 16.86,
	 *	            "tp": 0,
	 *	            "jyk": 726.28,
	 *	            "direction": 1
	 *	        },
	 *	        {
	 *	            "reason": "止损平仓",
	 *	            "swap": -48.72,
	 *	            "dealid": 21941153,
	 *	            "openprice": 16.71,
	 *	            "closetime": "2017-06-29 12:46:34",
	 *	            "symbolshortname": "XAGCNH",
	 *	            "opentime": "2017-06-26 14:42:55",
	 *	            "platform": "GTS2",
	 *	            "volume": 1,
	 *	            "accountid": 209138,
	 *	            "companyid": 2,
	 *	            "positionid": 15754512,
	 *	            "accountno": 60051134,
	 *	            "sl": 0,
	 *	            "ykds": -153,
	 *	            "currency": "USD",
	 *	            "commission": 0,
	 *              "digits": 2,
	 *	            "closeprice": 16.86,
	 *	            "tp": 0,
	 *	            "jyk": 716.28,
	 *	            "direction": 1
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} topProfit 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "topProfit接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/topProfit")
	public ApiResult topProfit(@RequestParam String companyId, String platform, @RequestParam String accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>topProfit[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo, startDate,
				endDate);
		long state= System.currentTimeMillis();
		ApiResult result = null;
		try {
			List rows = accountAnalyzeService.topProfitAndLoss(companyId, platform, accountNo, startDate, endDate, 1);
			result = new ApiResult(ApiStatusEnum.success, rows);
		} catch (Exception e) {
			logger.error("==>>topProfit接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "topProfit接口执行异常");
		} finally {
			logger.info("执行时间:{}秒", (System.currentTimeMillis()-state)/1000);
		}
		return result;
	}

	/**
	 * @api {post} /accountAnalyze/topLoss  获取3笔最大亏损单
	 * @apiGroup accountDiagnosisApi
	 * @apiDescription 获取3笔最大亏损单
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {String} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {String} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /accountAnalyze/topLoss
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051134", startDate: "2017-06-01",endDate: "2017-06-30",token: "1e868a65527d62b38f5c19854cc9274f"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} companyid 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiSuccess (返回字段) {String} accountno 交易账号
	 * @apiSuccess (返回字段) {String} platform 账户所在平台
	 * @apiSuccess (返回字段) {String} accountid 账号ID
	 * @apiSuccess (返回字段) {String} dealid 订单号
	 * @apiSuccess (返回字段) {String} positionid 持仓单号
	 * @apiSuccess (返回字段) {String} opentime 开仓时间
	 * @apiSuccess (返回字段) {String} direction 开仓方向
	 * @apiSuccess (返回字段) {String} openprice 开仓价格
	 * @apiSuccess (返回字段) {String} closetime 平仓时间
	 * @apiSuccess (返回字段) {String} closeprice 平仓价格
	 * @apiSuccess (返回字段) {String} currency 账户货币
	 * @apiSuccess (返回字段) {String} symbolshortname 产品简称
	 * @apiSuccess (返回字段) {String} reason 交易类型
	 * @apiSuccess (返回字段) {String} volume 手数
	 * @apiSuccess (返回字段) {String} sl 止损设置
	 * @apiSuccess (返回字段) {String} tp 止盈设置
	 * @apiSuccess (返回字段) {String} commission 佣金
	 * @apiSuccess (返回字段) {String} swap 过夜利息
	 * @apiSuccess (返回字段) {String} jyk 盈亏
	 * @apiSuccess (返回字段) {String} ykds 盈亏点数
	 * @apiSuccess (返回字段) {String} digits 小数位
	 *
     * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "reason": "止损平仓",
	 *	            "swap": -16.24,
	 *	            "dealid": 21941147,
	 *	            "openprice": 16.7,
	 *	            "closetime": "2017-06-29 12:12:15",
	 *	            "symbolshortname": "LLS",
	 *	            "opentime": "2017-06-26 14:43:33",
	 *	            "platform": "GTS2",
	 *	            "volume": 1,
	 *              "accountid": 209138,
	 *	            "companyid": 2,
	 *	            "positionid": 15754518,
	 *	            "accountno": 60051134,
	 *	            "sl": 0,
	 *	            "ykds": -183,
	 *	            "currency": "USD",
	 *	            "commission": 0,
	 *              "digits": 2,
	 *	            "closeprice": 16.88,
	 *	            "tp": 0,
	 *	            "jyk": -931.24,
	 *	            "direction": 2
	 *	        },
	 *	        {
	 *	            "reason": "止损平仓",
	 *	            "swap": -0.36,
	 *	            "dealid": 21941582,
	 *	            "openprice": 3675,
	 *	            "closetime": "2017-06-30 17:44:00",
	 *	            "symbolshortname": "XAGCNH",
	 *	            "opentime": "2017-06-29 11:46:50",
	 *	            "platform": "GTS2",
	 *	            "volume": 0.1,
	 *	            "accountid": 209138,
	 *	            "companyid": 2,
	 *	            "positionid": 15755253,
	 *	            "accountno": 60051134,
	 *	            "sl": 0,
	 *	            "ykds": -949000,
	 *	            "currency": "USD",
	 *	            "commission": 0,
	 *              "digits": 2,
	 *	            "closeprice": 4624,
	 *	            "tp": 0,
	 *	            "jyk": -420.12,
	 *	            "direction": 2
	 *	        },
	 *	        {
	 *	            "reason": "止损平仓",
	 *	            "swap": -0.36,
	 *	            "dealid": 21941581,
	 *	            "openprice": 3675,
	 *	            "closetime": "2017-06-30 17:44:00",
	 *	            "symbolshortname": "LLS",
	 *	            "opentime": "2017-06-29 11:46:46",
	 *	            "platform": "GTS2",
	 *	            "volume": 0.1,
	 *	            "accountid": 209138,
	 *	            "companyid": 2,
	 *	            "positionid": 15755252,
	 *	            "accountno": 60051134,
	 *	            "sl": 0,
	 *	            "ykds": -949000,
	 *	            "currency": "USD",
	 *	            "commission": 0,
	 *              "digits": 3,
	 *	            "closeprice": 4624,
	 *	            "tp": 0,
	 *	            "jyk": -420.12,
	 *	            "direction": 2
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} topLoss 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "topLoss接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/topLoss")
	public ApiResult topLoss(@RequestParam String companyId, String platform, @RequestParam String accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>topLoss[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo, startDate,
				endDate);
		long state= System.currentTimeMillis();
		ApiResult result = null;
		try {
			List rows = accountAnalyzeService.topProfitAndLoss(companyId, platform, accountNo, startDate, endDate, 0);
			result = new ApiResult(ApiStatusEnum.success, rows);
		} catch (Exception e) {
			logger.error("==>>topLoss接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "topLoss接口执行异常");
		} finally {
			logger.info("执行时间:{}秒", (System.currentTimeMillis()-state)/1000);
		}
		return result;
	}

	/**
	 * @api {post} /accountAnalyze/profitAndLossAmountAndTime  平仓单的盈亏金额与持仓时间
	 * @apiGroup accountDiagnosisApi
	 * @apiDescription 平仓单的盈亏金额与持仓时间
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {String} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {String} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /accountAnalyze/profitAndLossAmountAndTime
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051134", startDate: "2017-06-01",endDate: "2017-06-30",token: "46a108e9bc1d39fcf4884a5b7274d1ff"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} companyid 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiSuccess (返回字段) {String} accountno 交易账号
	 * @apiSuccess (返回字段) {String} platform 账户所在平台
	 * @apiSuccess (返回字段) {String} accountid 账号ID
	 * @apiSuccess (返回字段) {String} closetime 平仓时间
	 * @apiSuccess (返回字段) {String} currency 账户货币
	 * @apiSuccess (返回字段) {String} jyk 盈亏
	 * @apiSuccess (返回字段) {String} positionintervaltime 持仓时间
	 *
     * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "accountid": 209138,
	 *	            "companyid": 2,
	 *	            "positionintervaltime": 0,
	 *	            "accountno": 60051134,
	 *	            "closetime": "2017-06-30 17:48:01",
	 *	            "currency": "USD",
	 *	            "jyk": 4.42,
	 *	            "platform": "GTS2"
	 *	        },
	 *	        {
	 *	            "accountid": 209138,
	 *	            "companyid": 2,
	 *	            "positionintervaltime": 57,
	 *	            "accountno": 60051134,
	 *	            "closetime": "2017-06-30 17:44:00",
	 *	            "currency": "USD",
	 *	            "jyk": 420.12,
	 *	            "platform": "GTS2"
	 *	        },
	 *	        {
	 *	            "accountid": 209138,
	 *	            "companyid": 2,
	 *	            "positionintervaltime": 17,
	 *	            "accountno": 60051134,
	 *	            "closetime": "2017-06-29 21:03:59",
	 *	            "currency": "USD",
	 *	            "jyk": 25.44,
	 *	            "platform": "GTS2"
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} profitAndLossAmountAndTime 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "profitAndLossAmountAndTime接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/profitAndLossAmountAndTime")
	public ApiResult profitAndLossAmountAndTime(@RequestParam String companyId, String platform, @RequestParam String accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>profitAndLossAmountAndTime[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo, startDate,
				endDate);
		long state= System.currentTimeMillis();
		ApiResult result = null;
		try {
			List rows = accountAnalyzeService.profitAndLossAmountAndTime(companyId, platform, accountNo, startDate, endDate);
			result = new ApiResult(ApiStatusEnum.success, rows);
		} catch (Exception e) {
			logger.error("==>>profitAndLossAmountAndTime接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "profitAndLossAmountAndTime接口执行异常");
		} finally {
			logger.info("执行时间:{}秒", (System.currentTimeMillis()-state)/1000);
		}
		return result;
	}
	
	/**
	 * @api {post} /accountAnalyze/accountOverview  账户概况
	 * @apiSampleRequest /accountAnalyze/accountOverview
	 * @apiGroup accountDiagnosisApi
	 * @apiDescription 账户概况
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * 
     * @apiParamExample {json} 请求样例：
     *                  /accountAnalyze/accountOverview
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051073", startDate: "2017-06-01",endDate: "2017-06-30",token: "bfa76419811328c48eae70a4fa751197"}
	 * 
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} accountno 账号
	 * @apiSuccess (返回字段) {String} tradesnumber 交易操作次数
	 * @apiSuccess (返回字段) {String} totalprofit 平仓总盈利
	 * @apiSuccess (返回字段) {String} profitnumber 盈利次数
	 * @apiSuccess (返回字段) {String} totalloss 平仓总亏损
	 * @apiSuccess (返回字段) {String} lossnumber 亏损次数
	 * @apiSuccess (返回字段) {String} closeprofit 平仓盈亏
	 * @apiSuccess (返回字段) {String} swap 过夜利息
	 * @apiSuccess (返回字段) {String} actualprofit 实际盈亏 
	 * @apiSuccess (返回字段) {String} strongnumber 强平次数
	 * @apiSuccess (返回字段) {String} strongflatloss 强平亏损
	 * @apiSuccess (返回字段) {String} netdeposit 净入金
	 * @apiSuccess (返回字段) {String} floatingprofit 浮动盈亏 
	 * @apiSuccess (返回字段) {String} maxprofitratio 最大盈亏比
	 * @apiSuccess (返回字段) {String} winrate 胜率
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *   "status": "0",
	 *   "msg": "请求成功",
	 *   "result": [
     *	            {
	 *	              "strongflatloss": 0,
	 *	              "floatingprofit": -776.24,
	 *	              "swap": 66.54,
	 *	              "winrate": "40.21%",
	 *	              "totalloss": -2473.94,
	 *	              "actualprofit": 388.38,
	 *	              "strongnumber": 0,
	 *	              "totalprofit": 317.04,
	 *	              "closeprofit": 617.44,
	 *	              "maxprofitratio": "-131.94%"",
	 *	              "accountno": 60051073,
	 *	              "lossnumber": 22,
	 *	              "tradesnumber": 2,
	 *	              "netdeposit": 0,
	 *	              "profitnumber": 2,
	 *	            }
	 *            ]
	 *  }
	 *
	 *
     * @apiError {String} accountOverview 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "accountOverview接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/accountOverview")
	public ApiResult accountOverview(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>accountOverview[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate);
		long state= System.currentTimeMillis();
		ApiResult result = null;
		try {
			result = validateParam(companyId,platform,accountNo,startDate,endDate);
			if(null == result){
				List rows = accountDiagnosisService.accountOverview(companyId, platform,accountNo, startDate, endDate);
				result = new ApiResult(ApiStatusEnum.success, rows);
			}
		} catch (Exception e) {
			logger.error("==>>accountOverview接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "accountOverview接口执行异常");
		} finally {
			logger.info("执行时间:{}秒", (System.currentTimeMillis()-state)/1000);
		}
		return result;
	}
	
	/**
	 * @api {post} /accountAnalyze/profitStatistics  收益统计
	 * @apiSampleRequest /accountAnalyze/profitStatistics
	 * @apiGroup accountDiagnosisApi
	 * @apiDescription 收益统计
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /accountAnalyze/profitStatistics
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051073", startDate: "2017-06-01",endDate: "2017-06-30",token: "7194c77c5b06bcea8b744e5d3020edf5"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} accountno 账号
	 * @apiSuccess (返回字段) {String} cumulativeprofit 累计收益
	 * @apiSuccess (返回字段) {String} profitrate 当天收益
	 * @apiSuccess (返回字段) {String} floatingprofit 浮动盈亏
	 * @apiSuccess (返回字段) {String} execdate 日期
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "floatingprofit": 0,
	 *	            "execdaterpt": "2017-06-21",
	 *	            "accountno": 60051073,
	 *	            "profit": 317.04,
	 *	            "cumulativeProfit": 317.04
	 *	        },
	 *	        {
	 *	            "floatingprofit": 393.78,
	 *	            "execdaterpt": "2017-06-13",
	 *	            "accountno": 60051073,
	 *	            "profit": 0,
	 *	            "cumulativeProfit": 317.04
	 *	        },
	 *	        {
	 *	            "floatingprofit": 420.27000000000004,
	 *	            "execdaterpt": "2017-06-14",
	 *	            "accountno": 60051073,
	 *	            "profit": 0,
	 *	            "cumulativeProfit": 317.04
	 *	        },
	 *	        {
	 *	            "floatingprofit": 427.2,
	 * 	            "execdaterpt": "2017-06-12",
	 *	            "accountno": 60051073,
	 *	            "profit": 0,
	 *	            "cumulativeProfit": 317.04
	 *	        },
	 *	        {
	 *	            "floatingprofit": 230.86,
	 * 	            "execdaterpt": "2017-06-16",
	 *	            "accountno": 60051073,
	 *	            "profit": 0,
	 *	            "cumulativeProfit": 317.04
	 *	        },
	 *	        {
	 *	            "floatingprofit": 220.76,
	 *	            "execdaterpt": "2017-06-17",
	 *	            "accountno": 60051073,
	 *	            "profit": 0,
	 *	            "cumulativeProfit": 317.04
	 *	        },
	 *	        {
	 *	            "floatingprofit": 370.90999999999997,
	 *	            "execdaterpt": "2017-06-15",
	 *	            "accountno": 60051073,
	 *	            "profit": 0,
	 *	            "cumulativeProfit": 317.04
	 *	        }
	 *	    ]
	 *	}
	 *
	 *
     * @apiError {String} profitStatistics 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "profitStatistics接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/profitStatistics")
	public ApiResult profitStatistics(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>profitStatistics[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate);
		long state= System.currentTimeMillis();
		ApiResult result = null;
		try {
			result = validateParam(companyId,platform,accountNo,startDate,endDate);
			if(null == result){
				List rows = accountDiagnosisService.profitStatistics(companyId, platform,accountNo, startDate, endDate);
				result = new ApiResult(ApiStatusEnum.success, rows);
			}
		} catch (Exception e) {
			logger.error("==>>profitStatistics接口执行异常",e);
			result = new ApiResult(ApiStatusEnum.exception, "profitStatistics接口执行异常");
		} finally {
			logger.info("执行时间:{}秒", (System.currentTimeMillis()-state)/1000);
		}
		return result;
	}
	
	/**
	 * @api {post} /accountAnalyze/profitAnalysis  收益分析
	 * @apiSampleRequest /accountAnalyze/profitAnalysis
	 * @apiGroup accountDiagnosisApi
	 * @apiDescription 收益分析
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /accountAnalyze/profitAnalysis
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051073", startDate: "2017-06-01",endDate: "2017-06-30",token: "23e856c9bfad665edb9478c80eac57ad"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} accountno 账号
	 * @apiSuccess (返回字段) {String} tradeprofitratio 交易盈亏比
	 * @apiSuccess (返回字段) {String} avgprofitlossratio 平均盈亏比
	 * @apiSuccess (返回字段) {String} profitratio 盈单占比
	 * @apiSuccess (返回字段) {String} buynumber 多单单数
	 * @apiSuccess (返回字段) {String} sellnumber 空单单数
	 * @apiSuccess (返回字段) {String} buyprofitratio 多单盈单比率
	 * @apiSuccess (返回字段) {String} sellprofitratio 空单盈单比率
	 * @apiSuccess (返回字段) {String} buyprofitnumber 多单赢单单数
	 * @apiSuccess (返回字段) {String} sellprofitnumber 空单赢单单数
	 * @apiSuccess (返回字段) {String} buylossnumber 多单亏单单数
	 * @apiSuccess (返回字段) {String} selllossnumber 空单亏单单数
	 * @apiSuccess (返回字段) {String} buytallprofit 多单盈亏额
	 * @apiSuccess (返回字段) {String} selltallprofit 空单盈亏额
	 * @apiSuccess (返回字段) {String} positionintervaltime 盈利单平均持仓时间 
	 * @apiSuccess (返回字段) {String} lossintervaltime 亏损单平均持仓
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "avgprofitlossratio": "-4.58%",
	 *	            "buynumber": 17,
	 *	            "selllossnumber": 10,
	 *	            "sellnumber": 11,
	 *	            "selltallprofit": -2113.14,
	 *	            "buyprofitnumber": 5,
	 *	            "sellprofitratio": "0.00%",
	 *	            "lossintervaltime": 412,
	 *	            "tradeprofitratio": "-1.25%",
	 *	            "buylossnumber": 12,
	 *	            "positionintervaltime": 2817,
	 *	            "accountno": 60051073,
	 *	            "buytallprofit": 2730.58,
	 *	            "buyprofitratio": "0.00%",
	 *	            "profitratio": "21.43%",
	 *	            "sellprofitnumber": 1
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} profitAnalysis 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "profitAnalysis接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/profitAnalysis")
	public ApiResult profitAnalysis(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>profitAnalysis[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate);
		long state= System.currentTimeMillis();
		ApiResult result = null;
		try {
			result = validateParam(companyId,platform,accountNo,startDate,endDate);
			if(null == result){	
				List rows = accountDiagnosisService.profitAnalysis(companyId, platform, accountNo,startDate, endDate);
				result = new ApiResult(ApiStatusEnum.success, rows);
			}
		} catch (Exception e) {
			logger.error("==>>profitAnalysis接口执行异常", e);
			result = new ApiResult(ApiStatusEnum.exception, "profitAnalysis接口执行异常");
		} finally {
			logger.info("执行时间:{}秒", (System.currentTimeMillis()-state)/1000);
		}
		return result;
	}
	
	/**
	 * @api {post} /accountAnalyze/transactionProductStatistics  交易产品统计
	 * @apiSampleRequest /accountAnalyze/transactionProductStatistics
	 * @apiGroup accountDiagnosisApi
	 * @apiDescription 交易产品统计
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /accountAnalyze/transactionProductStatistics
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051073", startDate: "2017-06-01",endDate: "2017-06-30",token: "f70f2a0c887d676d2ce9f896489d86ad"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} symbolname 交易产品
	 * @apiSuccess (返回字段) {String} volume 交易手数
	 * @apiSuccess (返回字段) {String} closecnt 平仓单数
	 * @apiSuccess (返回字段) {String} profit 平仓盈亏
	 * @apiSuccess (返回字段) {String} profitrate 收益率
	 * @apiSuccess (返回字段) {String} prodwinrate 产品胜率
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "volume": 4.24,
	 *	            "profitrate": 158.52,
	 *	            "closecnt": 2,
	 *	            "symbolname": "HX/USD/GTS/MM/MIN/0/A/LLG",
	 *	            "profit": 317.04
	 *	            "prodwinrate": "26.32%"
	 *	        },
	 *	        {
	 *	            "volume": 0.2,
	 *	            "profitrate": 0,
	 *	            "closecnt": 0,
	 *	            "symbolname": "HX/USD/GTS/MM/MIN/0/A/XAUCNH",
	 *	            "profit": 0
	 *	            "prodwinrate": "0.00%"
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} transactionProductStatistics 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "transactionProductStatistics接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/transactionProductStatistics")
	public ApiResult transactionProductStatistics(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>transactionProductStatistics[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate);
		long state= System.currentTimeMillis();
		ApiResult result = null;
		try {
			result = validateParam(companyId,platform,accountNo,startDate,endDate);
			if(null == result){	
				List rows = accountDiagnosisService.transactionProductStatistics(companyId, platform, accountNo,startDate, endDate);
				result = new ApiResult(ApiStatusEnum.success, rows);
			}
		} catch (Exception e) {
			logger.error("==>>transactionProductStatistics接口执行异常", e);
			result = new ApiResult(ApiStatusEnum.exception, "transactionProductStatistics接口执行异常");
		} finally {
			logger.info("执行时间:{}秒", (System.currentTimeMillis()-state)/1000);
		}
		return result;
	}
	
	/**
	 * @api {post} /accountAnalyze/positionTimeIntervalStatistics  持仓时长分布统计
	 * @apiSampleRequest /accountAnalyze/positionTimeIntervalStatistics
	 * @apiGroup accountDiagnosisApi
	 * @apiDescription 持仓时长分布统计
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiParam {String} [platform] 账户所在平台
	 * @apiParam {Integer} accountNo 交易账号
	 * @apiParam {String} startDate 开始时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 * @apiParam {String} endDate 结束时间(贵金属与恒信为北京时间,外汇与创富为伦敦时间,格式:yyyy-MM-dd)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /accountAnalyze/positionTimeIntervalStatistics
     *                  {companyId: "2", platform: "GTS2",accountNo:"60051073", startDate: "2017-06-01",endDate: "2017-06-30",token: "7c336a554eb46e1c2a99bbd43fd62308"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} profit 盈利额
	 * @apiSuccess (返回字段) {String} loss 亏损额
	 * @apiSuccess (返回字段) {String} profitcount 盈利单数
	 * @apiSuccess (返回字段) {String} losscount 亏损单数
	 * @apiSuccess (返回字段) {String} timeinterval 时间范围
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "loss": 0,
	 *	            "timeinterval": ">=24hr",
	 *	            "profit": 317.04,
	 *	            "profitcount": 2,
	 *	            "losscount": 0
	 *	        }
	 *	    ]
	 *	}
	 *
     * @apiError {String} positionTimeIntervalStatistics 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "positionTimeIntervalStatistics接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/positionTimeIntervalStatistics")
	public ApiResult positionTimeIntervalStatistics(@RequestParam Integer companyId, String platform,@RequestParam Integer accountNo, @RequestParam String startDate, @RequestParam String endDate) {
		logger.debug("==>>positionTimeIntervalStatistics[companyId={},platform={},accountNo={},startDate={},endDate={}]", companyId, platform, accountNo,startDate,endDate);
		long state= System.currentTimeMillis();
		ApiResult result = null;
		try {
			result = validateParam(companyId,platform,accountNo,startDate,endDate);
			if(null == result){				
				List rows = accountDiagnosisService.positionTimeIntervalStatistics(companyId, platform, accountNo,startDate, endDate);
				result = new ApiResult(ApiStatusEnum.success, rows);
			}
		} catch (Exception e) {
			logger.error("==>>positionTimeIntervalStatistics接口执行异常", e);
			result = new ApiResult(ApiStatusEnum.exception, "positionTimeIntervalStatistics接口执行异常");
		} finally {
			logger.info("执行时间:{}秒", (System.currentTimeMillis()-state)/1000);
		}
		return result;
	}
	
	private ApiResult validateParam(Integer companyId,String platform,Integer accountNo,String startDate, String endDate){
		ApiResult result = null;
		if(null == companyId ){			
			 result =  new ApiResult(ApiStatusEnum.error_companyId_param, "positionTimeIntervalStatistics接口执行异常");
		}
		/*else if(StringUtils.isBlank(platform)){
			 result =  new ApiResult(ApiStatusEnum.error_platform_param, "positionTimeIntervalStatistics接口执行异常");
		}*/
		else if(null == accountNo ){
			 result =  new ApiResult(ApiStatusEnum.error_accountNo_param, "positionTimeIntervalStatistics接口执行异常");
		}
		else if(StringUtils.isBlank(startDate)){
			 result =  new ApiResult(ApiStatusEnum.error_startDate_param, "positionTimeIntervalStatistics接口执行异常");
		}
		else if(StringUtils.isBlank(endDate)){
			 result =  new ApiResult(ApiStatusEnum.error_endDate_param, "positionTimeIntervalStatistics接口执行异常");
		}
		return result;
	}

}
