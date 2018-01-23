package com.gw.das.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gw.das.api.common.context.Constants;
import com.gw.das.api.common.enums.AccountTypeEnum;
import com.gw.das.api.common.enums.ProfilesActiveEnum;
import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.common.response.ApiStatusEnum;
import com.gw.das.api.common.utils.DateUtil;
import com.gw.das.api.dao.AccountExtractDao;
import com.gw.das.api.service.AccountExtractService;

@Service
public class AccountExtractServiceImpl implements AccountExtractService{

	private static Logger logger = LoggerFactory.getLogger(AccountExtractServiceImpl.class);
	
	
	@Autowired
	private AccountExtractDao accountExtractDao;
	
	@Value("${spring.profiles.active}")
	private String profileActive;
	
	@Override
	public ApiPageResult getAccountExtractLabelListPage(Integer companyId, String platform,String accounttype,String accountstatus,String accountlevel,Double startbalance,Double endbalance,			
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
			String startlastoperatetime,String endlastoperatetime,Integer pageSize,Integer pageNumber) throws Exception {
		
		String keys = Constants.projectName + companyId + platform;
		logger.debug("==>>getAccountExtractLabel[keys={}]", keys);
		
		StringBuffer sql = new StringBuffer();
	    sql.append(" select  ");
	    sql.append(" a.company_id  companyid, ");                                                  //公司	    
	    sql.append(" a.platform platform, ");                                                      //交易平台
	    sql.append(" a.account_no accountno, ");                                                   //账号
	    sql.append(" a.account_status accountstatus, ");                                           //账户状态
	    sql.append(" a.account_level accountlevel, ");                                             //账号等级
	    sql.append(" case when a.is_real=1 and active_time is null then 'N' when is_real=1 and active_time is not null then 'A' when is_real=0 then 'D' end as accounttype, ");  //账户类型
	    sql.append(" a.source source, ");                                                          //账户来源
	    sql.append(" to_char(a.account_open_time,'YYYY-MM-DD HH24:MI:SS') accountopentime, ");     //开户时间
	    sql.append(" to_char(a.active_time,'YYYY-MM-DD HH24:MI:SS') activetime, ");                //激活时间
	    
	    sql.append(" a.real_active_interval_time realactiveintervaltime, ");                       //真实开户激活间隔
	    sql.append(" a.demo_active_interval_time demoactiveintervaltime, ");                       //模拟开户激活间隔
	    
	    sql.append(" to_char(a.first_cashin_time,'YYYY-MM-DD HH24:MI:SS') firstcashintime, ");     //首次存款时间
	    sql.append(" to_char(a.last_cashin_time,'YYYY-MM-DD HH24:MI:SS') lastcashintime, ");       //最后存款时间
	    sql.append(" to_char(a.first_cashout_time,'YYYY-MM-DD HH24:MI:SS') firstcashouttime, ");   //首次取款时间
	    sql.append(" to_char(a.last_cashout_time,'YYYY-MM-DD HH24:MI:SS') lastcashouttime, ");     //最后取款时间
	    
	    sql.append(" a.utm_source utmsource, ");                                                   //来源
	    sql.append(" a.utm_medium utmmedium, ");                                                   //媒件
	    sql.append(" a.utm_campaign utmcampaign, ");                                               //系列
	    sql.append(" a.utm_content utmcontent, ");                                                 //组
	    sql.append(" a.utm_term utmterm, ");                                                       //关键字
	    
	    sql.append(" to_char(a.first_operate_time,'YYYY-MM-DD HH24:MI:SS') firstoperatetime, ");   //首次操作时间
	    sql.append(" to_char(a.last_operate_time,'YYYY-MM-DD HH24:MI:SS') lastoperatetime, ");     //最后操作时间
	    sql.append(" to_char(a.first_order_time,'YYYY-MM-DD HH24:MI:SS') firstordertime, ");       //首次交易时间
	    sql.append(" to_char(a.last_order_time,'YYYY-MM-DD HH24:MI:SS') lastordertime, ");         //最后交易时间
	    
	    sql.append(" to_char(a.mobile_last_operate_time,'YYYY-MM-DD HH24:MI:SS') mobilelastoperatetime, ");     //手机端最后操作时间
	    sql.append(" to_char(a.webui_last_operate_time,'YYYY-MM-DD HH24:MI:SS') webuilastoperatetime, ");       //WebUI最后操作时间
	    sql.append(" to_char(a.pcui_last_operate_time,'YYYY-MM-DD HH24:MI:SS') pcuilastoperatetime, ");         //PCUI端最后操作时间
	    	    
	    sql.append(" a.hty_open_volume htyopenvolume, ");                                          //历史开仓手数
	    sql.append(" a.hty_close_volume htyclosevolume, ");                                        //历史平仓手数
	    sql.append(" a.hty_compel_close_cnt htycompelclosecnt,  ");                                //历史强平次数	
	    sql.append(" a.hty_compel_close_profit htycompelcloseprofit,  ");                          //历史强平盈亏
	    sql.append(" a.hty_close_profit htycloseprofit,  ");                                       //历史平仓盈亏
	    
	    sql.append(" a.hty_open_cnt htyopencnt,  ");                                               //历史开仓次数
	    sql.append(" a.hty_close_cnt htyclosecnt,  ");                                             //历史平仓次数	
	    
	    sql.append(" a.hty_cashin_cnt htycashincnt,  ");                                           //历史存款次数
	    sql.append(" a.hty_cashout_cnt htycashoutcnt,  ");                                         //历史取款次数
	    
	    sql.append(" a.balance balance,  ");                                                       //结余 
	    sql.append(" a.hty_net_deposit htynetdeposit,  ");                                         //历史净入金	    
	    sql.append(" case when currency='CNY' then a.equity/7 else a.equity end as equity ");      //净值
		sql.append(" from rds.dim_account_extract a  ");
		sql.append(" where 1=1  ");
		if(ProfilesActiveEnum.PROD.getValue().equals(profileActive)){
			sql.append(" and is_test = 0 ");
		}
		sql.append(" and account_no is not null ");
		sql.append(" and account_open_time is not null ");
		Map<String, Object> params = new HashMap<String, Object>();	
		
		sql.append(" and company_id = :companyId "); //公司
		params.put("companyId", companyId);
		
		if(StringUtils.isNotBlank(platform)){				
			sql.append(" and platform = :platform "); //交易平台
			params.put("platform", platform);
		}
				
		if(AccountTypeEnum.N.getValue().equals(accounttype)){//账号类别
			sql.append(" and is_real=1 and active_time is null ");
		}
		
        if(AccountTypeEnum.A.getValue().equals(accounttype)){
        	sql.append(" and is_real=1 and active_time is not null ");
		}
		if(AccountTypeEnum.D.getValue().equals(accounttype)){
			sql.append(" and is_real=0 ");
		}
		
		if(StringUtils.isNotBlank(accountstatus)){				
			sql.append(" and account_status = :accountstatus ");//账户状态
			params.put("accountstatus", accountstatus);
		}
		
		if(StringUtils.isNotBlank(accountlevel)){				
			sql.append(" and account_level = :accountlevel ");//账号等级
			params.put("accountlevel", accountlevel);
		}
		
		if(null != startbalance){				
			sql.append(" and balance >= (case when currency='CNY' then :startbalance * 7 else :startbalance end) ");//开始账号结余-账号余额
			params.put("startbalance", startbalance );
		}
		if(null != endbalance){				
			sql.append(" and balance <= (case when currency='CNY' then :endbalance * 7 else :endbalance end)  ");//结束账号结余 -账号余额
			params.put("endbalance", endbalance);
		}				
		
		if(null != starthtydeposit){				
			sql.append(" and hty_deposit >= (case when currency='CNY' then :starthtydeposit * 7 else :starthtydeposit end) ");//开始存款金额
			params.put("starthtydeposit", starthtydeposit );
		}
		if(null != endhtydeposit){				
			sql.append(" and hty_deposit <= (case when currency='CNY' then :endhtydeposit * 7 else :endhtydeposit end)  ");//结束存款金额
			params.put("endhtydeposit", endhtydeposit);
		}
		
		if(null != startlastdeposit){				
			sql.append(" and last_deposit >= (case when currency='CNY' then :startlastdeposit * 7 else :startlastdeposit end) ");//开始最后存款金额
			params.put("startlastdeposit", startlastdeposit );
		}
		if(null != endlastdeposit){				
			sql.append(" and last_deposit <= (case when currency='CNY' then :endlastdeposit * 7 else :endlastdeposit end)  ");//结束最后存款金额
			params.put("endlastdeposit", endlastdeposit);
		}
				
		if(null != starthtywithdraw){	
			sql.append(" and hty_withdraw >= (case when currency='CNY' then :starthtywithdraw * 7 else :starthtywithdraw end) ");//开始取款金额
			params.put("starthtywithdraw", starthtywithdraw );
		}
		if(null != endhtywithdraw){				
			sql.append(" and hty_withdraw <= (case when currency='CNY' then :endhtywithdraw * 7 else :endhtywithdraw end)  ");//结束取款金额
			params.put("endhtywithdraw", endhtywithdraw);
		}
		
		if(null != startlastwithdraw){				
			sql.append(" and last_withdraw >= (case when currency='CNY' then :startlastwithdraw * 7 else :startlastwithdraw end) ");//开始最后取款金额
			params.put("startlastwithdraw", startlastwithdraw );
		}
		if(null != endlastwithdraw){				
			sql.append(" and last_withdraw <= (case when currency='CNY' then :endlastwithdraw * 7 else :endlastwithdraw end)  ");//结束最后取款金额
			params.put("endlastwithdraw", endlastwithdraw);
		}
		
		if(null != starthtycloseprofit){				
			sql.append(" and hty_close_profit >= (case when currency='CNY' then :starthtycloseprofit * 7 else :starthtycloseprofit end) ");//开始历史平仓盈亏 - 交易盈亏
			params.put("starthtycloseprofit", starthtycloseprofit );
		}
		if(null != endhtycloseprofit){				
			sql.append(" and hty_close_profit <= (case when currency='CNY' then :endhtycloseprofit * 7 else :endhtycloseprofit end)  ");//结束历史平仓盈亏 -交易盈亏
			params.put("endhtycloseprofit", endhtycloseprofit);
		}
		
		if(null != startequity){				
			sql.append(" and equity >= (case when currency='CNY' then :startequity * 7 else :startequity end) ");//开始净值
			params.put("startequity", startequity );
		}
		if(null != endequity){				
			sql.append(" and equity <= (case when currency='CNY' then :endequity * 7 else :endequity end)  ");//结束净值
			params.put("endequity", endequity);
		}
		
		if(null != starthtynetdeposit){				
			sql.append(" and hty_net_deposit >= (case when currency='CNY' then :starthtynetdeposit * 7 else :starthtynetdeposit end) ");//净入金
			params.put("starthtynetdeposit", starthtynetdeposit);
		}
		if(null != endhtynetdeposit){				
			sql.append(" and hty_net_deposit <= (case when currency='CNY' then :endhtynetdeposit * 7 else :endhtynetdeposit end)  ");//净入金
			params.put("endhtynetdeposit", endhtynetdeposit);
		}
		
		if(null != starthtyopenvolume){				
			sql.append(" and hty_open_volume >= :starthtyopenvolume ");//开始历史开仓手数
			params.put("starthtyopenvolume", starthtyopenvolume);
		}
		if(null != endhtyopenvolume){				
			sql.append(" and hty_open_volume <= :endhtyopenvolume ");//结束历史开仓手数
			params.put("endhtyopenvolume", endhtyopenvolume);
		}
		
		if(null != starthtyclosevolume){				
			sql.append(" and hty_close_volume >= :starthtyclosevolume ");//开始历史平仓手数
			params.put("starthtyclosevolume", starthtyclosevolume);
		}
		if(null != endhtyclosevolume){				
			sql.append(" and hty_close_volume <= :endhtyclosevolume ");//结束历史平仓手数
			params.put("endhtyclosevolume", endhtyclosevolume);
		}
		
		if(null != starthtyopencnt){				
			sql.append(" and hty_open_cnt >= :starthtyopencnt ");//历史开仓次数
			params.put("starthtyopencnt", starthtyopencnt);
		}
		if(null != endhtyopencnt){				
			sql.append(" and hty_open_cnt <= :endhtyopencnt ");//历史开仓次数
			params.put("endhtyopencnt", endhtyopencnt);
		}
		
		if(null != starthtyclosecnt){				
			sql.append(" and hty_close_cnt >= :starthtyclosecnt ");//历史平仓次数
			params.put("starthtyclosecnt", starthtyclosecnt);
		}
		if(null != endhtyclosecnt){				
			sql.append(" and hty_close_cnt <= :endhtyclosecnt ");//历史平仓次数
			params.put("endhtyclosecnt", endhtyclosecnt);
		}
		
		if(null != starthtycompelclosecnt){				
			sql.append(" and hty_compel_close_cnt >= :starthtycompelclosecnt ");//历史强平次数
			params.put("starthtycompelclosecnt", starthtycompelclosecnt);
		}
		if(null != endhtycompelclosecnt){				
			sql.append(" and hty_compel_close_cnt <= :endhtycompelclosecnt ");//历史强平次数
			params.put("endhtycompelclosecnt", endhtycompelclosecnt);
		}
		
		if(null != startdemoactiveintervaltime){				
			sql.append(" and demo_active_interval_time >= :startdemoactiveintervaltime ");//模拟开户激活间隔(粒度分钟)
			params.put("startdemoactiveintervaltime", startdemoactiveintervaltime );
		}
		if(null != enddemoactiveintervaltime){				
			sql.append(" and demo_active_interval_time <= :enddemoactiveintervaltime ");//模拟开户激活间隔(粒度分钟)
			params.put("enddemoactiveintervaltime", enddemoactiveintervaltime);
		}
		
		if(null != startrealactiveintervaltime){				
			sql.append(" and real_active_interval_time >= :startrealactiveintervaltime ");//真实开户激活间隔(粒度分钟)
			params.put("startrealactiveintervaltime", startrealactiveintervaltime );
		}
		if(null != endrealactiveintervaltime){				
			sql.append(" and real_active_interval_time <= :endrealactiveintervaltime ");//真实开户激活间隔(粒度分钟)
			params.put("endrealactiveintervaltime", endrealactiveintervaltime);
		}

		if(StringUtils.isNotBlank(startdemofirstaccountopentime)){				
			sql.append(" and demo_first_account_open_time >= :startdemofirstaccountopentime ");//开始模拟开户时间
			params.put("startdemofirstaccountopentime", DateUtil.stringToDate(startdemofirstaccountopentime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(enddemofirstaccountopentime)){				
			sql.append(" and demo_first_account_open_time <= :enddemofirstaccountopentime ");//结束模拟开户时间
			params.put("enddemofirstaccountopentime", DateUtil.stringToDate(enddemofirstaccountopentime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startaccountopentime)){				
			sql.append(" and account_open_time >= :startaccountopentime ");//开始真实开户时间
			params.put("startaccountopentime", DateUtil.stringToDate(startaccountopentime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endaccountopentime)){				
			sql.append(" and account_open_time <= :endaccountopentime ");//结束真实开户时间
			params.put("endaccountopentime", DateUtil.stringToDate(endaccountopentime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startactivetime)){				
			sql.append(" and active_time >= :startactivetime ");//开始激活时间
			params.put("startactivetime", DateUtil.stringToDate(startactivetime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endactivetime)){				
			sql.append(" and active_time <= :endactivetime ");//结束激活时间
			params.put("endactivetime", DateUtil.stringToDate(endactivetime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startfirstcashintime)){				
			sql.append(" and first_cashin_time >= :startfirstcashintime ");//开始首次存款时间
			params.put("startfirstcashintime", DateUtil.stringToDate(startfirstcashintime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endfirstcashintime)){				
			sql.append(" and first_cashin_time <= :endfirstcashintime ");//结束首次存款时间
			params.put("endfirstcashintime", DateUtil.stringToDate(endfirstcashintime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startlastcashintime)){				
			sql.append(" and last_cashin_time >= :startlastcashintime ");//开始最后存款时间
			params.put("startlastcashintime", DateUtil.stringToDate(startlastcashintime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endlastcashintime)){				
			sql.append(" and last_cashin_time <= :endlastcashintime ");//结束最后存款时间
			params.put("endlastcashintime", DateUtil.stringToDate(endlastcashintime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startfirstcashouttime)){				
			sql.append(" and first_cashout_time >= :startfirstcashouttime ");//开始首次取款时间
			params.put("startfirstcashouttime", DateUtil.stringToDate(startfirstcashouttime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endfirstcashouttime)){				
			sql.append(" and first_cashout_time <= :endfirstcashouttime ");//结束首次取款时间
			params.put("endfirstcashouttime", DateUtil.stringToDate(endfirstcashouttime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startlastcashouttime)){				
			sql.append(" and last_cashout_time >= :startlastcashouttime ");//开始最后取款时间
			params.put("startlastcashouttime", DateUtil.stringToDate(startlastcashouttime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endlastcashouttime)){				
			sql.append(" and last_cashout_time <= :endlastcashouttime ");//结束最后取款时间
			params.put("endlastcashouttime", DateUtil.stringToDate(endlastcashouttime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startfirstordertime)){				
			sql.append(" and first_order_time >= :startfirstordertime ");//开始首次交易时间
			params.put("startfirstordertime", DateUtil.stringToDate(startfirstordertime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endfirstordertime)){				
			sql.append(" and first_order_time <= :endfirstordertime ");//结束首次交易时间
			params.put("endfirstordertime", DateUtil.stringToDate(endfirstordertime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startlastordertime)){				
			sql.append(" and last_order_time >= :startlastordertime ");//开始最后交易时间
			params.put("startlastordertime", DateUtil.stringToDate(startlastordertime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endlastordertime)){				
			sql.append(" and last_order_time <= :endlastordertime ");//结束最后交易时间
			params.put("endlastordertime", DateUtil.stringToDate(endlastordertime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startfirstoperatetime)){				
			sql.append(" and first_operate_time >= :startfirstoperatetime ");//开始首次操作时间-登陆时间
			params.put("startfirstoperatetime", DateUtil.stringToDate(startfirstoperatetime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endfirstoperatetime)){				
			sql.append(" and first_operate_time <= :endfirstoperatetime ");//结束首次操作时间-登陆时间
			params.put("endfirstoperatetime", DateUtil.stringToDate(endfirstoperatetime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startlastoperatetime)){				
			sql.append(" and last_operate_time >= :startlastoperatetime ");//开始最后操作时间-登陆时间
			params.put("startlastoperatetime", DateUtil.stringToDate(startlastoperatetime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endlastoperatetime)){				
			sql.append(" and last_operate_time <= :endlastoperatetime ");//结束最后操作时间-登陆时间
			params.put("endlastoperatetime", DateUtil.stringToDate(endlastoperatetime,DateUtil.format2));
		}
			
		ApiPageResult result = new ApiPageResult(ApiStatusEnum.success);
		result.setPageSize(pageSize);
		result.setPageNumber(pageNumber);
		params.put("sort", "account_open_time");
		params.put("order", "desc");
		accountExtractDao.getAccountExtractLabelListPage(result, sql.toString(), params);			
		return result;	
	}
	
	@Override
	public ApiResult getAccountExtractLabelList(Integer companyId, String platform,String accounttype,String accountstatus,String accountlevel,Double startbalance,Double endbalance,			
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
			String startlastoperatetime,String endlastoperatetime) throws Exception {
		String keys = Constants.projectName + companyId + platform;
		logger.debug("==>>getAccountExtractLabel[keys={}]", keys);
		StringBuffer sql = new StringBuffer();
	    sql.append(" select  ");
	    sql.append(" a.company_id  companyid, ");                                                  //公司	    
	    sql.append(" a.platform platform, ");                                                      //交易平台
	    sql.append(" a.account_no accountno, ");                                                   //账号
	    sql.append(" a.account_status accountstatus, ");                                           //账户状态
	    sql.append(" a.account_level accountlevel, ");                                             //账号等级
	    sql.append(" case when a.is_real=1 and active_time is null then 'N' when is_real=1 and active_time is not null then 'A' when is_real=0 then 'D' end as accounttype, ");  //账户类型
	    sql.append(" a.source source, ");                                                          //账户来源
	    sql.append(" to_char(a.account_open_time,'YYYY-MM-DD HH24:MI:SS') accountopentime, ");     //开户时间
	    sql.append(" to_char(a.active_time,'YYYY-MM-DD HH24:MI:SS') activetime, ");                //激活时间
	    
	    sql.append(" a.real_active_interval_time realactiveintervaltime, ");                       //真实开户激活间隔
	    sql.append(" a.demo_active_interval_time demoactiveintervaltime, ");                       //模拟开户激活间隔
	    
	    sql.append(" to_char(a.first_cashin_time,'YYYY-MM-DD HH24:MI:SS') firstcashintime, ");     //首次存款时间
	    sql.append(" to_char(a.last_cashin_time,'YYYY-MM-DD HH24:MI:SS') lastcashintime, ");       //最后存款时间
	    sql.append(" to_char(a.first_cashout_time,'YYYY-MM-DD HH24:MI:SS') firstcashouttime, ");   //首次取款时间
	    sql.append(" to_char(a.last_cashout_time,'YYYY-MM-DD HH24:MI:SS') lastcashouttime, ");     //最后取款时间
	    
	    sql.append(" a.utm_source utmsource, ");                                                   //来源
	    sql.append(" a.utm_medium utmmedium, ");                                                   //媒件
	    sql.append(" a.utm_campaign utmcampaign, ");                                               //系列
	    sql.append(" a.utm_content utmcontent, ");                                                 //组
	    sql.append(" a.utm_term utmterm, ");                                                       //关键字
	    
	    sql.append(" to_char(a.first_operate_time,'YYYY-MM-DD HH24:MI:SS') firstoperatetime, ");   //首次操作时间
	    sql.append(" to_char(a.last_operate_time,'YYYY-MM-DD HH24:MI:SS') lastoperatetime, ");     //最后操作时间
	    sql.append(" to_char(a.first_order_time,'YYYY-MM-DD HH24:MI:SS') firstordertime, ");       //首次交易时间
	    sql.append(" to_char(a.last_order_time,'YYYY-MM-DD HH24:MI:SS') lastordertime, ");         //最后交易时间
	    
	    sql.append(" to_char(a.mobile_last_operate_time,'YYYY-MM-DD HH24:MI:SS') mobilelastoperatetime, ");     //手机端最后操作时间
	    sql.append(" to_char(a.webui_last_operate_time,'YYYY-MM-DD HH24:MI:SS') webuilastoperatetime, ");       //WebUI最后操作时间
	    sql.append(" to_char(a.pcui_last_operate_time,'YYYY-MM-DD HH24:MI:SS') pcuilastoperatetime, ");         //PCUI端最后操作时间
	    	    
	    sql.append(" a.hty_open_volume htyopenvolume, ");                                          //历史开仓手数
	    sql.append(" a.hty_close_volume htyclosevolume, ");                                        //历史平仓手数
	    sql.append(" a.hty_compel_close_cnt htycompelclosecnt,  ");                                //历史强平次数	
	    sql.append(" a.hty_compel_close_profit htycompelcloseprofit,  ");                          //历史强平盈亏
	    sql.append(" a.hty_close_profit htycloseprofit,  ");                                       //历史平仓盈亏
	    
	    sql.append(" a.hty_open_cnt htyopencnt,  ");                                               //历史开仓次数
	    sql.append(" a.hty_close_cnt htyclosecnt,  ");                                             //历史平仓次数	
	    
	    sql.append(" a.hty_cashin_cnt htycashincnt,  ");                                           //历史存款次数
	    sql.append(" a.hty_cashout_cnt htycashoutcnt,  ");                                         //历史取款次数
	    
	    sql.append(" a.balance balance,  ");                                                       //结余 
	    sql.append(" a.hty_net_deposit htynetdeposit,  ");                                         //历史净入金	    
	    sql.append(" case when currency='CNY' then a.equity/7 else a.equity end as equity ");      //净值
		sql.append(" from rds.dim_account_extract a  ");
		sql.append(" where 1=1  ");
		if(ProfilesActiveEnum.PROD.getValue().equals(profileActive)){
			sql.append(" and is_test = 0 ");
		}
		sql.append(" and account_no is not null ");
		sql.append(" and account_open_time is not null ");
		Map<String, Object> params = new HashMap<String, Object>();	
		
		sql.append(" and company_id = :companyId "); //公司
		params.put("companyId", companyId);
		
		if(StringUtils.isNotBlank(platform)){				
			sql.append(" and platform = :platform "); //交易平台
			params.put("platform", platform);
		}
				
		if(AccountTypeEnum.N.getValue().equals(accounttype)){//账号类别
			sql.append(" and is_real=1 and active_time is null ");
		}
		
        if(AccountTypeEnum.A.getValue().equals(accounttype)){
        	sql.append(" and is_real=1 and active_time is not null ");
		}
		if(AccountTypeEnum.D.getValue().equals(accounttype)){
			sql.append(" and is_real=0 ");
		}
		
		if(StringUtils.isNotBlank(accountstatus)){				
			sql.append(" and account_status = :accountstatus ");//账户状态
			params.put("accountstatus", accountstatus);
		}
		
		if(StringUtils.isNotBlank(accountlevel)){				
			sql.append(" and account_level = :accountlevel ");//账号等级
			params.put("accountlevel", accountlevel);
		}
		
		if(null != startbalance){				
			sql.append(" and balance >= (case when currency='CNY' then :startbalance * 7 else :startbalance end) ");//开始账号结余-账号余额
			params.put("startbalance", startbalance );
		}
		if(null != endbalance){				
			sql.append(" and balance <= (case when currency='CNY' then :endbalance * 7 else :endbalance end)  ");//结束账号结余 -账号余额
			params.put("endbalance", endbalance);
		}				
		
		if(null != starthtydeposit){				
			sql.append(" and hty_deposit >= (case when currency='CNY' then :starthtydeposit * 7 else :starthtydeposit end) ");//开始存款金额
			params.put("starthtydeposit", starthtydeposit );
		}
		if(null != endhtydeposit){				
			sql.append(" and hty_deposit <= (case when currency='CNY' then :endhtydeposit * 7 else :endhtydeposit end)  ");//结束存款金额
			params.put("endhtydeposit", endhtydeposit);
		}
		
		if(null != startlastdeposit){				
			sql.append(" and last_deposit >= (case when currency='CNY' then :startlastdeposit * 7 else :startlastdeposit end) ");//开始最后存款金额
			params.put("startlastdeposit", startlastdeposit );
		}
		if(null != endlastdeposit){				
			sql.append(" and last_deposit <= (case when currency='CNY' then :endlastdeposit * 7 else :endlastdeposit end)  ");//结束最后存款金额
			params.put("endlastdeposit", endlastdeposit);
		}
				
		if(null != starthtywithdraw){	
			sql.append(" and hty_withdraw >= (case when currency='CNY' then :starthtywithdraw * 7 else :starthtywithdraw end) ");//开始取款金额
			params.put("starthtywithdraw", starthtywithdraw );
		}
		if(null != endhtywithdraw){				
			sql.append(" and hty_withdraw <= (case when currency='CNY' then :endhtywithdraw * 7 else :endhtywithdraw end)  ");//结束取款金额
			params.put("endhtywithdraw", endhtywithdraw);
		}
		
		if(null != startlastwithdraw){				
			sql.append(" and last_withdraw >= (case when currency='CNY' then :startlastwithdraw * 7 else :startlastwithdraw end) ");//开始最后取款金额
			params.put("startlastwithdraw", startlastwithdraw );
		}
		if(null != endlastwithdraw){				
			sql.append(" and last_withdraw <= (case when currency='CNY' then :endlastwithdraw * 7 else :endlastwithdraw end)  ");//结束最后取款金额
			params.put("endlastwithdraw", endlastwithdraw);
		}
		
		if(null != starthtycloseprofit){				
			sql.append(" and hty_close_profit >= (case when currency='CNY' then :starthtycloseprofit * 7 else :starthtycloseprofit end) ");//开始历史平仓盈亏 - 交易盈亏
			params.put("starthtycloseprofit", starthtycloseprofit );
		}
		if(null != endhtycloseprofit){				
			sql.append(" and hty_close_profit <= (case when currency='CNY' then :endhtycloseprofit * 7 else :endhtycloseprofit end)  ");//结束历史平仓盈亏 -交易盈亏
			params.put("endhtycloseprofit", endhtycloseprofit);
		}
		
		if(null != startequity){				
			sql.append(" and equity >= (case when currency='CNY' then :startequity * 7 else :startequity end) ");//开始净值
			params.put("startequity", startequity );
		}
		if(null != endequity){				
			sql.append(" and equity <= (case when currency='CNY' then :endequity * 7 else :endequity end)  ");//结束净值
			params.put("endequity", endequity);
		}
		
		if(null != starthtynetdeposit){				
			sql.append(" and hty_net_deposit >= (case when currency='CNY' then :starthtynetdeposit * 7 else :starthtynetdeposit end) ");//净入金
			params.put("starthtynetdeposit", starthtynetdeposit);
		}
		if(null != endhtynetdeposit){				
			sql.append(" and hty_net_deposit <= (case when currency='CNY' then :endhtynetdeposit * 7 else :endhtynetdeposit end)  ");//净入金
			params.put("endhtynetdeposit", endhtynetdeposit);
		}
				
		if(null != starthtyopenvolume){				
			sql.append(" and hty_open_volume >= :starthtyopenvolume ");//开始历史开仓手数
			params.put("starthtyopenvolume", starthtyopenvolume);
		}
		if(null != endhtyopenvolume){				
			sql.append(" and hty_open_volume <= :endhtyopenvolume ");//结束历史开仓手数
			params.put("endhtyopenvolume", endhtyopenvolume);
		}
		
		if(null != starthtyclosevolume){				
			sql.append(" and hty_close_volume >= :starthtyclosevolume ");//开始历史平仓手数
			params.put("starthtyclosevolume", starthtyclosevolume);
		}
		if(null != endhtyclosevolume){				
			sql.append(" and hty_close_volume <= :endhtyclosevolume ");//结束历史平仓手数
			params.put("endhtyclosevolume", endhtyclosevolume);
		}
		
		if(null != starthtyopencnt){				
			sql.append(" and hty_open_cnt >= :starthtyopencnt ");//历史开仓次数
			params.put("starthtyopencnt", starthtyopencnt);
		}
		if(null != endhtyopencnt){				
			sql.append(" and hty_open_cnt <= :endhtyopencnt ");//历史开仓次数
			params.put("endhtyopencnt", endhtyopencnt);
		}
		
		if(null != starthtyclosecnt){				
			sql.append(" and hty_close_cnt >= :starthtyclosecnt ");//历史平仓次数
			params.put("starthtyclosecnt", starthtyclosecnt);
		}
		if(null != endhtyclosecnt){				
			sql.append(" and hty_close_cnt <= :endhtyclosecnt ");//历史平仓次数
			params.put("endhtyclosecnt", endhtyclosecnt);
		}
		
		if(null != starthtycompelclosecnt){				
			sql.append(" and hty_compel_close_cnt >= :starthtycompelclosecnt ");//历史强平次数
			params.put("starthtycompelclosecnt", starthtycompelclosecnt);
		}
		if(null != endhtycompelclosecnt){				
			sql.append(" and hty_compel_close_cnt <= :endhtycompelclosecnt ");//历史强平次数
			params.put("endhtycompelclosecnt", endhtycompelclosecnt);
		}
		
		if(null != startdemoactiveintervaltime){				
			sql.append(" and demo_active_interval_time >= :startdemoactiveintervaltime ");//模拟开户激活间隔(粒度分钟)
			params.put("startdemoactiveintervaltime", startdemoactiveintervaltime );
		}
		if(null != enddemoactiveintervaltime){				
			sql.append(" and demo_active_interval_time <= :enddemoactiveintervaltime ");//模拟开户激活间隔(粒度分钟)
			params.put("enddemoactiveintervaltime", enddemoactiveintervaltime);
		}
		
		if(null != startrealactiveintervaltime){				
			sql.append(" and real_active_interval_time >= :startrealactiveintervaltime ");//真实开户激活间隔(粒度分钟)
			params.put("startrealactiveintervaltime", startrealactiveintervaltime );
		}
		if(null != endrealactiveintervaltime){				
			sql.append(" and real_active_interval_time <= :endrealactiveintervaltime ");//真实开户激活间隔(粒度分钟)
			params.put("endrealactiveintervaltime", endrealactiveintervaltime);
		}

		if(StringUtils.isNotBlank(startdemofirstaccountopentime)){				
			sql.append(" and demo_first_account_open_time >= :startdemofirstaccountopentime ");//开始模拟开户时间
			params.put("startdemofirstaccountopentime", DateUtil.stringToDate(startdemofirstaccountopentime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(enddemofirstaccountopentime)){				
			sql.append(" and demo_first_account_open_time <= :enddemofirstaccountopentime ");//结束模拟开户时间
			params.put("enddemofirstaccountopentime", DateUtil.stringToDate(enddemofirstaccountopentime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startaccountopentime)){				
			sql.append(" and account_open_time >= :startaccountopentime ");//开始真实开户时间
			params.put("startaccountopentime", DateUtil.stringToDate(startaccountopentime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endaccountopentime)){				
			sql.append(" and account_open_time <= :endaccountopentime ");//结束真实开户时间
			params.put("endaccountopentime", DateUtil.stringToDate(endaccountopentime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startactivetime)){				
			sql.append(" and active_time >= :startactivetime ");//开始激活时间
			params.put("startactivetime", DateUtil.stringToDate(startactivetime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endactivetime)){				
			sql.append(" and active_time <= :endactivetime ");//结束激活时间
			params.put("endactivetime", DateUtil.stringToDate(endactivetime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startfirstcashintime)){				
			sql.append(" and first_cashin_time >= :startfirstcashintime ");//开始首次存款时间
			params.put("startfirstcashintime", DateUtil.stringToDate(startfirstcashintime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endfirstcashintime)){				
			sql.append(" and first_cashin_time <= :endfirstcashintime ");//结束首次存款时间
			params.put("endfirstcashintime", DateUtil.stringToDate(endfirstcashintime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startlastcashintime)){				
			sql.append(" and last_cashin_time >= :startlastcashintime ");//开始最后存款时间
			params.put("startlastcashintime", DateUtil.stringToDate(startlastcashintime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endlastcashintime)){				
			sql.append(" and last_cashin_time <= :endlastcashintime ");//结束最后存款时间
			params.put("endlastcashintime", DateUtil.stringToDate(endlastcashintime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startfirstcashouttime)){				
			sql.append(" and first_cashout_time >= :startfirstcashouttime ");//开始首次取款时间
			params.put("startfirstcashouttime", DateUtil.stringToDate(startfirstcashouttime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endfirstcashouttime)){				
			sql.append(" and first_cashout_time <= :endfirstcashouttime ");//结束首次取款时间
			params.put("endfirstcashouttime", DateUtil.stringToDate(endfirstcashouttime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startlastcashouttime)){				
			sql.append(" and last_cashout_time >= :startlastcashouttime ");//开始最后取款时间
			params.put("startlastcashouttime", DateUtil.stringToDate(startlastcashouttime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endlastcashouttime)){				
			sql.append(" and last_cashout_time <= :endlastcashouttime ");//结束最后取款时间
			params.put("endlastcashouttime", DateUtil.stringToDate(endlastcashouttime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startfirstordertime)){				
			sql.append(" and first_order_time >= :startfirstordertime ");//开始首次交易时间
			params.put("startfirstordertime", DateUtil.stringToDate(startfirstordertime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endfirstordertime)){				
			sql.append(" and first_order_time <= :endfirstordertime ");//结束首次交易时间
			params.put("endfirstordertime", DateUtil.stringToDate(endfirstordertime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startlastordertime)){				
			sql.append(" and last_order_time >= :startlastordertime ");//开始最后交易时间
			params.put("startlastordertime", DateUtil.stringToDate(startlastordertime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endlastordertime)){				
			sql.append(" and last_order_time <= :endlastordertime ");//结束最后交易时间
			params.put("endlastordertime", DateUtil.stringToDate(endlastordertime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startfirstoperatetime)){				
			sql.append(" and first_operate_time >= :startfirstoperatetime ");//开始首次操作时间-登陆时间
			params.put("startfirstoperatetime", DateUtil.stringToDate(startfirstoperatetime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endfirstoperatetime)){				
			sql.append(" and first_operate_time <= :endfirstoperatetime ");//结束首次操作时间-登陆时间
			params.put("endfirstoperatetime", DateUtil.stringToDate(endfirstoperatetime,DateUtil.format2));
		}
		
		if(StringUtils.isNotBlank(startlastoperatetime)){				
			sql.append(" and last_operate_time >= :startlastoperatetime ");//开始最后操作时间-登陆时间
			params.put("startlastoperatetime", DateUtil.stringToDate(startlastoperatetime,DateUtil.format2));
		}
		if(StringUtils.isNotBlank(endlastoperatetime)){				
			sql.append(" and last_operate_time <= :endlastoperatetime ");//结束最后操作时间-登陆时间
			params.put("endlastoperatetime", DateUtil.stringToDate(endlastoperatetime,DateUtil.format2));
		}
			
		ApiResult result = new ApiResult(ApiStatusEnum.success);
		params.put("sort", "account_open_time");
		params.put("order", "desc");
		accountExtractDao.getAccountExtractLabelList(result, sql.toString(), params);			
		return result;	
	}
	
}
