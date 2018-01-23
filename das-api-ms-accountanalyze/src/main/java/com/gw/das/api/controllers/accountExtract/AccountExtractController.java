package com.gw.das.api.controllers.accountExtract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.common.response.ApiStatusEnum;
import com.gw.das.api.service.AccountExtractService;

/**
 * 账户扩展
 * 
 * @author darren
 *
 */
@RestController
@RequestMapping("/accountExtract")
public class AccountExtractController {

	private static Logger logger = LoggerFactory.getLogger(AccountExtractController.class);
	
	@Autowired
	private AccountExtractService accountExtractService;
	
		
	/**
	 * @api {post} /accountExtract/getAccountExtractLabel 用户标签账户提取
	 * @apiSampleRequest /accountExtract/getAccountExtractLabel
	 * @apiGroup accountExtractApi
	 * @apiDescription 用户标签账户提取
	 * @apiParam {String} token 该字段由统一验证接口返回(参考uat获取token)
	 * @apiParam {Integer} companyId 事业部("外汇":"1","恒信":"2","贵金属":"3","创富":"8")
	 * @apiParam {Integer} [pageNumber] 当前第几页(页数从第一页开始)
	 * @apiParam {Integer} [pageSize] 每页多少条数据(默认值为10条)
	 * @apiParam {String} [platform] 交易平台(MT4,GTS2)
	 * @apiParam {String} [accounttype] 账号类型(N:真实用户 ,A:激活用户,D:模拟用户)
	 * @apiParam {String} [accountstatus] 账户状态
	 * @apiParam {String} [accountlevel] 账号等级
	 * @apiParam {Double} [startbalance] 开始账号结余-账号余额
	 * @apiParam {Double} [endbalance] 结束账号结余 -账号余额
	 * @apiParam {Double} [starthtydeposit] 开始存款金额
	 * @apiParam {Double} [endhtydeposit] 结束存款金额
	 * @apiParam {Double} [startlastdeposit] 开始最后存款金额
	 * @apiParam {Double} [endlastdeposit] 结束最后存款金额
	 * @apiParam {Double} [starthtywithdraw] 开始取款金额
	 * @apiParam {Double} [endhtywithdraw] 结束取款金额
	 * @apiParam {Double} [startlastwithdraw] 开始最后取款金额
	 * @apiParam {Double} [endlastwithdraw] 结束最后取款金额
	 * @apiParam {Double} [starthtycloseprofit] 开始历史平仓盈亏 - 交易盈亏
	 * @apiParam {Double} [endhtycloseprofit] 结束历史平仓盈亏 -交易盈亏
	 * @apiParam {Double} [startequity] 开始净值
	 * @apiParam {Double} [endequity] 结束净值
	 * @apiParam {Double} [starthtynetdeposit] 开始净入金
	 * @apiParam {Double} [endhtynetdeposit] 结束净入金
	 * @apiParam {Double} [starthtyopenvolume] 开始历史开仓手数
	 * @apiParam {Double} [endhtyopenvolume] 结束历史开仓手数
	 * @apiParam {Double} [starthtyclosevolume] 开始历史平仓手数
	 * @apiParam {Double} [endhtyclosevolume] 结束历史平仓手数
	 * @apiParam {Integer} [starthtyopencnt] 开始历史开仓次数
	 * @apiParam {Integer} [endhtyopencnt] 结束历史开仓次数
	 * @apiParam {Integer} [starthtyclosecnt] 开始历史平仓次数
	 * @apiParam {Integer} [endhtyclosecnt] 结束历史平仓次数
	 * @apiParam {Integer} [starthtycompelclosecnt] 开始历史强平次数
     * @apiParam {Integer} [endhtycompelclosecnt] 结束历史强平次数
	 * @apiParam {Double} [startdemoactiveintervaltime] 开始模拟开户激活间隔(粒度分钟)
	 * @apiParam {Double} [enddemoactiveintervaltime] 结束模拟开户激活间隔(粒度分钟)
	 * @apiParam {Double} [startrealactiveintervaltime] 开始真实开户激活间隔(粒度分钟)
	 * @apiParam {Double} [endrealactiveintervaltime] 结束真实开户激活间隔(粒度分钟)
	 * @apiParam {String} [startdemofirstaccountopentime] 开始模拟开户时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [enddemofirstaccountopentime] 结束模拟开户时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startaccountopentime] 开始真实开户时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endaccountopentime] 结束真实开户时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startactivetime] 开始激活时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endactivetime] 结束激活时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startfirstcashintime] 开始首次存款时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endfirstcashintime] 结束首次存款时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startlastcashintime] 开始最后存款时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endlastcashintime] 结束最后存款时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startfirstcashouttime] 开始首次取款时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endfirstcashouttime] 结束首次取款时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startlastcashouttime] 开始最后取款时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endlastcashouttime] 结束最后取款时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startfirstordertime] 开始首次交易时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endfirstordertime] 结束首次交易时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startlastordertime] 开始最后交易时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endlastordertime] 结束最后交易时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startfirstoperatetime] 开始首次操作时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endfirstoperatetime] 结束首次操作时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 * @apiParam {String} [startlastoperatetime] 开始最后操作时间(一律以北京时间标记，标记格式为 2017-11-11 00:00:00)
	 * @apiParam {String} [endlastoperatetime] 结束最后操作时间(一律以北京时间标记，标记格式为 2017-11-11 23:59:59)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /accountExtract/getAccountExtractLabel
     *                  {token:"922fdec4c8a5c763faad79fda7b0b67d","pageSize":"10","pageNumber":"1",companyId: "1", platform: "MT4","accountlevel":"STD"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess (返回字段) {String} companyid 各个事业部唯一标示(外汇=1 恒信=2 贵金属=3 创富=8)
	 * @apiSuccess (返回字段) {String} platform  交易平台
	 * @apiSuccess (返回字段) {String} accountno 交易账号
	 * @apiSuccess (返回字段) {String} accountstatus 账户状态
	 * @apiSuccess (返回字段) {String} accountlevel 账号等级(VIP/STD/MIN)
	 * @apiSuccess (返回字段) {String} accounttype 账户类型(N:真实用户 ,A:激活用户,D:模拟用户)
	 * @apiSuccess (返回字段) {String} source 账户来源
	 * @apiSuccess (返回字段) {String} accountopentime 开户时间
	 * @apiSuccess (返回字段) {String} activetime 激活时间
	 * @apiSuccess (返回字段) {String} realactiveintervaltime 真实开户激活间隔
	 * @apiSuccess (返回字段) {String} demoactiveintervaltime 模拟开户激活间隔
	 * @apiSuccess (返回字段) {String} firstcashintime 首次存款时间
	 * @apiSuccess (返回字段) {String} lastcashintime 最后存款时间
	 * @apiSuccess (返回字段) {String} firstcashouttime 首次取款时间
	 * @apiSuccess (返回字段) {String} lastcashouttime 最后取款时间
	 * @apiSuccess (返回字段) {String} utmsource 来源
	 * @apiSuccess (返回字段) {String} utmmedium 媒件
	 * @apiSuccess (返回字段) {String} utmcampaign 系列
	 * @apiSuccess (返回字段) {String} utmcontent 组
	 * @apiSuccess (返回字段) {String} utmterm 关键字
	 * @apiSuccess (返回字段) {String} firstoperatetime 首次操作时间
	 * @apiSuccess (返回字段) {String} lastoperatetime 最后操作时间
	 * @apiSuccess (返回字段) {String} firstordertime 首次交易时间
	 * @apiSuccess (返回字段) {String} lastordertime 最后交易时间
	 * @apiSuccess (返回字段) {String} mobilelastoperatetime 手机端最后操作时间
	 * @apiSuccess (返回字段) {String} webuilastoperatetime WebUI最后操作时间
	 * @apiSuccess (返回字段) {String} pcuilastoperatetime PCUI端最后操作时间
	 * @apiSuccess (返回字段) {String} htyopenvolume 历史开仓手数
	 * @apiSuccess (返回字段) {String} htyclosevolume 历史平仓手数
	 * @apiSuccess (返回字段) {String} htycompelclosecnt 历史强平次数
	 * @apiSuccess (返回字段) {String} htycompelcloseprofit 历史强平盈亏
	 * @apiSuccess (返回字段) {String} htycloseprofit 历史平仓盈亏
	 * @apiSuccess (返回字段) {String} htyopencnt 历史开仓次数
	 * @apiSuccess (返回字段) {String} htyclosecnt 历史平仓次数
	 * @apiSuccess (返回字段) {String} htycashincnt 历史存款次数
	 * @apiSuccess (返回字段) {String} htycashoutcnt 历史取款次数
	 * @apiSuccess (返回字段) {String} balance 结余 
	 * @apiSuccess (返回字段) {String} htynetdeposit 历史净入金	
	 * @apiSuccess (返回字段) {String} equity 净值
	 * 
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "lastoperatetime": null,
	 *	            "htynetdeposit": 0,
	 *	            "htycompelclosecnt": 0,
	 *	            "accounttype": "D",
	 *	            "accountlevel": null,
	 *	            "source": 3,
	 *	            "equity": 0,
	 *	            "platform": "GTS2",
	 *	            "companyid": 1,
	 *	            "mobilelastoperatetime": null,
	 *	            "firstcashouttime": null,
	 *	            "lastordertime": null,
	 *	            "balance": 0,
	 *	            "accountstatus": null,
	 *	            "htyopenvolume": 0,
	 *	            "firstordertime": null,
	 *	            "htycashoutcnt": 0,
	 *	            "lastcashintime": null,
	 *	            "utmcontent": null,
	 *	            "firstoperatetime": null,
	 *	            "htycompelcloseprofit": 0,
	 *	            "realactiveintervaltime": 0,
	 *	            "utmcampaign": null,
	 *	            "htyopencnt": 0,
	 *	            "demoactiveintervaltime": 0,
	 *	            "accountopentime": "2017-11-10 18:23:20",
	 *	            "utmsource": null,
	 *	            "webuilastoperatetime": null,
	 *	            "htycashincnt": 0,
	 *	            "activetime": null,
	 *	            "pcuilastoperatetime": null,
	 *	            "htycloseprofit": 0,
	 *	            "firstcashintime": null,
	 *	            "htyclosevolume": 0,
	 *	            "htyclosecnt": 0,
	 *	            "accountno": 300018513,
	 *	            "lastcashouttime": null,
	 *	            "utmmedium": null,
	 *	            "utmterm": null  
	 *	        }
	 *	    ],
	 *	    "pageSize": 10,
	 *	    "pageNumber": 1,
	 *	    "total": 59325
	 *	}
	 *
     * @apiError {String} getAccountExtractLabel 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": false
	 *	}
	 *
	 * @return
	 */	
	@RequestMapping("/getAccountExtractLabel")
	public ApiResult getAccountExtractLabel(@RequestParam Integer companyId, String platform,String accounttype,String accountstatus,String accountlevel,Double startbalance,Double endbalance,
			Double starthtydeposit,Double endhtydeposit,Double startlastdeposit,Double endlastdeposit,
			Double starthtywithdraw,Double endhtywithdraw,Double startlastwithdraw,Double endlastwithdraw,
			Double starthtycloseprofit,Double endhtycloseprofit,Double startequity,Double endequity,			
			Double starthtynetdeposit,Double endhtynetdeposit,
			Double starthtyopenvolume,Double endhtyopenvolume,Double starthtyclosevolume,Double endhtyclosevolume,
			Integer starthtyopencnt,Integer endhtyopencnt,Integer starthtyclosecnt,Integer endhtyclosecnt,
			Integer starthtycompelclosecnt,Integer endhtycompelclosecnt,			
			Double startdemoactiveintervaltime,Double enddemoactiveintervaltime,Double startrealactiveintervaltime,Double endrealactiveintervaltime,						
			String startdemofirstaccountopentime,String enddemofirstaccountopentime,String startaccountopentime,String endaccountopentime,
			String startactivetime,String endactivetime,String startfirstcashintime,String endfirstcashintime,
			String startlastcashintime,String endlastcashintime,String startfirstcashouttime,String endfirstcashouttime,
			String startlastcashouttime,String endlastcashouttime,String startfirstordertime,String endfirstordertime,
			String startlastordertime,String endlastordertime,String startfirstoperatetime,String endfirstoperatetime,
			String startlastoperatetime,String endlastoperatetime,@RequestParam Integer pageSize,@RequestParam Integer pageNumber) {
		logger.info("==>>getAccountExtractLabel[companyId={},platform={},accounttype={},accountstatus={},accountlevel={},startbalance={},endbalance={},"
				+ "starthtydeposit={},endhtydeposit={},startlastdeposit={},endlastdeposit={},"
				+ "starthtywithdraw={},endhtywithdraw={},startlastwithdraw={},endlastwithdraw={},"
				+ "starthtycloseprofit={},endhtycloseprofit={},startequity={},endequity={},"
				+ "starthtynetdeposit={},endhtynetdeposit={},"
				+ "starthtyopenvolume={},endhtyopenvolume={},starthtyclosevolume={},endhtyclosevolume={},"
				+ "starthtyopencnt={},endhtyopencnt={},starthtyclosecnt={},endhtyclosecnt={},"
				+ "starthtycompelclosecnt={},endhtycompelclosecnt={},"				
				+ "startdemoactiveintervaltime={},enddemoactiveintervaltime={},startrealactiveintervaltime={},endrealactiveintervaltime={},"								
				+ "startdemofirstaccountopentime={},enddemofirstaccountopentime={},startaccountopentime={},accountopentime={},"
				+ "startactivetime={},endactivetime={},startfirstcashintime={},endfirstcashintime={},"
				+ "startlastcashintime={},endlastcashintime={},startfirstcashouttime={},endfirstcashouttime={},"
				+ "startlastcashouttime={},endlastcashouttime={},startfirstordertime={},endfirstordertime={},"
				+ "startlastordertime={},endlastordertime={},startfirstoperatetime={},endfirstoperatetime={},"
				+ "startlastoperatetime={},endlastoperatetime={},pageSize={},pageNumber={}}]", companyId,  platform, accounttype,accountstatus,accountlevel,startbalance,endbalance,
				starthtydeposit, endhtydeposit, startlastdeposit, endlastdeposit, 
				starthtywithdraw, endhtywithdraw, startlastwithdraw, endlastwithdraw, 
				starthtycloseprofit, endhtycloseprofit, startequity, endequity, 
				starthtynetdeposit, endhtynetdeposit, 
				starthtyopenvolume, endhtyopencnt,starthtyclosevolume, endhtyclosevolume, 
				starthtyopencnt, endhtyopenvolume,starthtyclosecnt, endhtyclosecnt, 
				starthtycompelclosecnt, endhtycompelclosecnt,
				startdemoactiveintervaltime, enddemoactiveintervaltime, startrealactiveintervaltime, endrealactiveintervaltime, 	
				startdemofirstaccountopentime, enddemofirstaccountopentime,startaccountopentime, endaccountopentime,
				startactivetime, endactivetime, startfirstcashintime, endfirstcashintime,
				startlastcashintime, endlastcashintime, startfirstcashouttime, endfirstcashouttime,
				startlastcashouttime, endlastcashouttime, startfirstordertime, endfirstordertime,
				startlastordertime, endlastordertime, startfirstoperatetime, endfirstoperatetime,
				startlastoperatetime, endlastoperatetime,pageSize,pageNumber);
		try {			
				ApiPageResult result =  accountExtractService.getAccountExtractLabelListPage( companyId,  platform, accounttype, accountstatus, accountlevel, startbalance, endbalance,			
						 starthtydeposit, endhtydeposit, startlastdeposit, endlastdeposit,
						 starthtywithdraw, endhtywithdraw, startlastwithdraw, endlastwithdraw,
						 starthtycloseprofit, endhtycloseprofit, startequity, endequity,			
						 starthtynetdeposit, endhtynetdeposit,
						 starthtyopenvolume, endhtyopenvolume, starthtyclosevolume, endhtyclosevolume,
						 starthtyopencnt, endhtyopencnt, starthtyclosecnt, endhtyclosecnt,
						 starthtycompelclosecnt, endhtycompelclosecnt,			
						 startdemoactiveintervaltime, enddemoactiveintervaltime, startrealactiveintervaltime, endrealactiveintervaltime,						
						 startdemofirstaccountopentime, enddemofirstaccountopentime, startaccountopentime, endaccountopentime,
						 startactivetime, endactivetime, startfirstcashintime, endfirstcashintime,
						 startlastcashintime, endlastcashintime, startfirstcashouttime, endfirstcashouttime,
						 startlastcashouttime, endlastcashouttime, startfirstordertime, endfirstordertime,
						 startlastordertime, endlastordertime, startfirstoperatetime, endfirstoperatetime,
						 startlastoperatetime, endlastoperatetime, pageSize, pageNumber);
				logger.info("调用接口getAccountExtractLabel成功,返回:{} ",result);
				return result;		
		} catch (Exception e) {
			logger.error("==>>getAccountExtractLabel接口执行异常",e);				
		}
		ApiPageResult result = new ApiPageResult(ApiStatusEnum.exception, "getAccountExtractLabel接口执行异常");
        result.setResult(false);
		return result;
	}
}
