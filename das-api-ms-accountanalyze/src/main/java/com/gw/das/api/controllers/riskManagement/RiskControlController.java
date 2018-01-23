package com.gw.das.api.controllers.riskManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.common.response.ApiStatusEnum;
import com.gw.das.api.service.AccountBlacklistService;

/**
 * 账户黑名单
 * 
 * @author darren
 *
 */
@RestController
@RequestMapping("/riskControl")
public class RiskControlController {

	private static Logger logger = LoggerFactory.getLogger(RiskControlController.class);
	
	@Autowired
	private AccountBlacklistService accountBlacklistService;
	
	
	/**
	 * @api {post} /riskControl/getAccountBlacklist  账户黑名单查询
	 * @apiSampleRequest /riskControl/getAccountBlacklist
	 * @apiGroup riskControlApi
	 * @apiDescription 账户黑名单查询
	 * @apiParam {Integer} value(如果value参数为身份证,则先转为大写再进行加密,密文传过来)
	 * @apiParam {Integer} type (0:其它 ,1:账号)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /riskControl/getAccountBlacklist
     *                  {value: "40eeb4d27ebfbd5084a69d84338004d7", type: "0"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {JSONArray} result 返回的数据
	 * @apiSuccess (返回字段) {String} type  类型(account_no,account_name,mobile,id_card_md5,email,ip)
	 * @apiSuccess (返回字段) {String} remark  原因备注
	 * @apiSuccess (返回字段) {String} risktype  风险类型
	 * 
	 * @apiSuccess (返回字段) {String} accountno  交易账号
	 * @apiSuccess (返回字段) {String} accountid  账号id
	 * @apiSuccess (返回字段) {String} companyid  公司id
	 * @apiSuccess (返回字段) {String} platform  平台
	 * @apiSuccess (返回字段) {String} issueaccountno  问题账号
	 * @apiSuccess (返回字段) {String} issueaccountname  问题姓名
	 * @apiSuccess (返回字段) {String} issuemobile  问题电话
	 * @apiSuccess (返回字段) {String} issueidcardencrypt  问题身份证-加密
	 * @apiSuccess (返回字段) {String} issueidcardmd5  问题身份证-md5
	 * @apiSuccess (返回字段) {String} issueemail  问题邮箱
	 * @apiSuccess (返回字段) {String} issueip  问题ip
	 * @apiSuccess (返回字段) {String} listrisktype  黑名单风险类型信息列表
	 * @apiSuccess (返回字段) {String} listremark  黑名单原因备注信息列表
	 * @apiSuccess (返回字段) {String} openidcarddoublecnt  开户身份证double数
	 * @apiSuccess (返回字段) {String} openmobiledoublecnt  开户手机double数
	 * @apiSuccess (返回字段) {String} activeidcarddoublecnt  激活身份证double数
	 * @apiSuccess (返回字段) {String} activemobiledoublecnt  激活手机double数
	 * @apiSuccess (返回字段) {String} accountnosk  统一账号sk
	 * @apiSuccess (返回字段) {String} accountidsk  账号idsk
	 *
	 * @apiSuccessExample {json} 返回样例:
	 * 账户返回示例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "type": "id_card_md5",
	 *	            "remark": "疑屬拒付客戶,FX,HX",
	 *	            "risktype": "封禁"
	 *	        }
	 *	    ]
	 *	}
	 *
	 *
	 * 账户黑名单返回示例:
	 *	{
	 *	    "status": "0",
	 *	    "msg": "请求成功",
	 *	    "result": [
	 *	        {
	 *	            "activeidcarddoublecnt": 21660,
	 *	            "activemobiledoublecnt": 37725,
	 *	            "openmobiledoublecnt": 208504,
	 *	            "issuemobile": "13666666666",
	 *	            "accountidsk": "gts2_122919",
	 *	            "issueidcard_md5": "9737160d9528d26ff80a3537d681b9da",
	 *	            "accountnosk": "gts2_86083993",
	 *	            "issueaccountno": 86083993,
	 *	            "listrisktype": "封禁",
	 *	            "listremark": "2017-01福建,HX",
	 *	            "platform": "GTS2",
	 *	            "accountid": 122919,
	 *	            "companyid": 1,
	 *	            "issueip": "223.104.6.58",
	 *	            "accountno": 86083993,
	 *	            "issue_accountname": "孙文",
	 *	            "issueemail": "jiangjieshi@qq.com",
	 *	            "issueidcardencrypt": "RQzS7yThucW5hq68y9m9/Bp7zHviNsCmFiMFhM/pQVI=",
	 *	            "openidcarddoublecnt": 138964
	 *	        }
	 *	        ]
	 *	}
	 *
     * @apiError {String} getAccountBlacklist 接口执行异常.
	 *
	 * @apiErrorExample {json} 返回异常:
	 *	{
	 *	    "status": "2",
	 *	    "msg": "接口异常",
	 *	    "result": "getAccountBlacklist接口执行异常"
	 *	}
	 *
	 * @return
	 */
	@RequestMapping("/getAccountBlacklist")
	public ApiResult getAccountBlacklist(@RequestParam String value, @RequestParam Integer type ) {
		logger.debug("==>>getAccountBlacklist[value={},type={}]", value, type );
		try {
			ApiResult result =  accountBlacklistService.findAccountBlacklist(value,type );	
			logger.info("调用接口getAccountBlacklist成功,返回:{} ",result);
			return result;
		} catch (Exception e) {
			logger.error("==>>getAccountBlacklist接口执行异常",e);			
		}
		return new ApiPageResult(ApiStatusEnum.exception, "getAccountBlacklist接口执行异常");
	}
	
	/**
	 * @api {post} /riskControl/channelWarning 渠道预警
	 * @apiSampleRequest /riskControl/channelWarning
	 * @apiGroup riskControlApi
	 * @apiDescription 渠道预警
	 * @apiParam {String} value
	 * @apiParam {Integer} type (0:其它 ,1:账号)
	 *
     * @apiParamExample {json} 请求样例：
     *                  /riskControl/channelWarning
     *                  {value: "86103159", type: "1"}
	 *
	 * @apiSuccess {String} status 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} msg 返回的信息
	 * @apiSuccess {boolean} result 返回的数据
	 * @apiSuccess (返回字段) {String} result  (true:在白名单,false:不在白名单)
	 *
	 * @apiSuccessExample {json} 返回样例:
	 *  {
   	 *    "status": "0",
     *    "msg": "请求成功",
   	 *    "result": true
	 *  }
	 *
     * @apiError {String} channelWarning 接口执行异常.
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
	@RequestMapping("/channelWarning")
	public ApiResult channelWarning(@RequestParam String value,@RequestParam Integer type) throws Exception {
		logger.info("==>>channelWarning[value={},type={}]", value, type );
		try {
			ApiResult result =  accountBlacklistService.channelWarning(value,type );	
			logger.info("调用接口channelWarning成功,返回:{} ",result);
			return result;
		} catch (Exception e) {
			logger.error("==>>channelWarning接口执行异常",e);				
		}
		ApiResult result = new ApiResult(ApiStatusEnum.exception, "channelWarning接口执行异常");
        result.setResult(false);
		return result;
	}
	
}
