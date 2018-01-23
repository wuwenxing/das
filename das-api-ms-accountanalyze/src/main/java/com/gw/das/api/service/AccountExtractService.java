package com.gw.das.api.service;

import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;

public interface AccountExtractService {

	/**
	 * 用户标签账户提取
	 * 
	 * @param companyId  公司
	 * @param platform   交易平台
	 * @param accounttype  账号类型
	 * @param accountstatus  账号状态
	 * @param accountlevel  账号等级
	 * @param startbalance 开始账号结余
	 * @param endbalance  结束账号结余
	 * @param starthtydeposit 开始存款金额
	 * @param endhtydeposit  结束存款金额
	 * @param startlastdeposit 开始最后存款金额
	 * @param endlastdeposit  结束最后存款金额
	 * @param starthtywithdraw 开始取款金额
	 * @param endhtywithdraw  结结束取款金额
	 * @param startlastwithdraw 开始最后取款金额
	 * @param endlastwithdraw  结束最后取款金额
	 * @param starthtycloseprofit 开始历史平仓盈亏 - 交易盈亏
	 * @param endhtycloseprofit  结束历史平仓盈亏 -交易盈亏
	 * @param startequity 开始净值
	 * @param endequity  结束净值
	 * @param starthtynetdeposit 净入金
	 * @param endhtynetdeposit  净入金
	 * @param starthtyopenvolume  开始历史开仓手数
	 * @param endhtyopenvolume  结束历史开仓手数
	 * @param starthtyclosevolume  开始历史平仓手数
	 * @param endhtyclosevolume  结束历史平仓手数
	 * @param starthtyopencnt  开始历史开仓次数
	 * @param endhtyopencnt  结束历史开仓次数
	 * @param starthtyclosecnt  开始历史平仓次数
	 * @param endhtyclosecnt  结束历史平仓次数
	 * @param starthtycompelclosecnt  开始历史强平次数
	 * @param endhtycompelclosecnt  结束历史强平次数
	 * @param startdemoactiveintervaltime 开始模拟开户激活间隔
	 * @param enddemoactiveintervaltime  结束模拟开户激活间隔
	 * @param startrealactiveintervaltime 开始真实开户激活间隔
	 * @param endrealactiveintervaltime  结束真实开户激活间隔
	 * @param startdemofirstaccountopentime  开始模拟开户时间
	 * @param enddemofirstaccountopentime  结束模拟开户时间
	 * @param startaccountopentime  开始真实开户时间
	 * @param endaccountopentime  结束真实开户时间
	 * @param startactivetime  开始激活时间
	 * @param endactivetime  结束激活时间
	 * @param startfirstcashintime  开始首次存款时间
	 * @param endfirstcashintime  结束首次存款时间
	 * @param startlastcashintime  开始最后存款时间
	 * @param endlastcashintime  结束最后存款时间
	 * @param startfirstcashouttime  开始首次取款时间
	 * @param endfirstcashouttime  结束首次取款时间
	 * @param startlastcashouttime  开始最后取款时间
	 * @param endlastcashouttime  结束最后取款时间
	 * @param startfirstordertime  开始首次交易时间
	 * @param endfirstordertime  结束首次交易时间
	 * @param startlastordertime  开始最后交易时间
	 * @param endlastordertime  结束最后交易时间
	 * @param startfirstoperatetime  开始首次操作时间
	 * @param endfirstoperatetime  结束首次操作时间
	 * @param startlastoperatetime  开始最后操作时间
	 * @param endlastoperatetime  结束最后操作时间
	 * @param pageSize  开始最后操作时间
	 * @param pageNumber  结束最后操作时间
	 * @return
	 * @throws Exception
	 */
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
			String startlastoperatetime,String endlastoperatetime,Integer pageSize,Integer pageNumber)throws Exception;
	
	/**
	 * 用户标签账户提取
	 * 
	 * @param companyId  公司
	 * @param platform   交易平台
	 * @param accounttype  账号类型
	 * @param accountstatus  账号状态
	 * @param accountlevel  账号等级
	 * @param startbalance 开始账号结余
	 * @param endbalance  结束账号结余
	 * @param starthtydeposit 开始存款金额
	 * @param endhtydeposit  结束存款金额
	 * @param startlastdeposit 开始最后存款金额
	 * @param endlastdeposit  结束最后存款金额
	 * @param starthtywithdraw 开始取款金额
	 * @param endhtywithdraw  结结束取款金额
	 * @param startlastwithdraw 开始最后取款金额
	 * @param endlastwithdraw  结束最后取款金额
	 * @param starthtycloseprofit 开始历史平仓盈亏 - 交易盈亏
	 * @param endhtycloseprofit  结束历史平仓盈亏 -交易盈亏
	 * @param startequity 开始净值
	 * @param endequity  结束净值
	 * @param starthtynetdeposit 净入金
	 * @param endhtynetdeposit  净入金
	 * @param starthtyopenvolume  开始历史开仓手数
	 * @param endhtyopenvolume  结束历史开仓手数
	 * @param starthtyclosevolume  开始历史平仓手数
	 * @param endhtyclosevolume  结束历史平仓手数
	 * @param starthtyopencnt  开始历史开仓次数
	 * @param endhtyopencnt  结束历史开仓次数
	 * @param starthtyclosecnt  开始历史平仓次数
	 * @param endhtyclosecnt  结束历史平仓次数
	 * @param starthtycompelclosecnt  开始历史强平次数
	 * @param endhtycompelclosecnt  结束历史强平次数
	 * @param startdemoactiveintervaltime 开始模拟开户激活间隔
	 * @param enddemoactiveintervaltime  结束模拟开户激活间隔
	 * @param startrealactiveintervaltime 开始真实开户激活间隔
	 * @param endrealactiveintervaltime  结束真实开户激活间隔
	 * @param startdemofirstaccountopentime  开始模拟开户时间
	 * @param enddemofirstaccountopentime  结束模拟开户时间
	 * @param startaccountopentime  开始真实开户时间
	 * @param endaccountopentime  结束真实开户时间
	 * @param startactivetime  开始激活时间
	 * @param endactivetime  结束激活时间
	 * @param startfirstcashintime  开始首次存款时间
	 * @param endfirstcashintime  结束首次存款时间
	 * @param startlastcashintime  开始最后存款时间
	 * @param endlastcashintime  结束最后存款时间
	 * @param startfirstcashouttime  开始首次取款时间
	 * @param endfirstcashouttime  结束首次取款时间
	 * @param startlastcashouttime  开始最后取款时间
	 * @param endlastcashouttime  结束最后取款时间
	 * @param startfirstordertime  开始首次交易时间
	 * @param endfirstordertime  结束首次交易时间
	 * @param startlastordertime  开始最后交易时间
	 * @param endlastordertime  结束最后交易时间
	 * @param startfirstoperatetime  开始首次操作时间
	 * @param endfirstoperatetime  结束首次操作时间
	 * @param startlastoperatetime  开始最后操作时间
	 * @param endlastoperatetime  结束最后操作时间
	 * @return
	 * @throws Exception
	 */
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
			String startlastoperatetime,String endlastoperatetime)throws Exception;
	
}
