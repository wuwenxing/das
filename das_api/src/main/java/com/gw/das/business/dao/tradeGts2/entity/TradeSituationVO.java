package com.gw.das.business.dao.tradeGts2.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 *交易情况记录VO类
 * 
 * @author darren
 * @since 2017-10-20
 */
public class TradeSituationVO extends BaseModel {

	/* rowkey */
	@Column(name = "rowkey")
	private String rowkey;
	
	/* 日期 */
	@Column(name = "execdate")
	private String execdate;
	
	/* 设备类型 */
	@Column(name = "platform")
	private String platform;
	
	/*  */
	@Column(name = "devicetype")
	private String devicetype;
	
	/* 渠道分组 */
	@Column(name = "channelgroup")
	private String channelgroup;
	
	/* 渠道分级 */
	@Column(name = "channellevel")
	private String channellevel;
	
	/*渠道 */
	@Column(name = "channel")
	private String channel;
	
	/* 来源*/
	@Column(name = "utmsource")
	private String utmsource;
	
	/* 媒件*/
	@Column(name = "utmmedium")
	private String utmmedium;
	
	/* 系列 */
	@Column(name = "utmcampaign")
	private String utmcampaign;
	
	/* 组 */
	@Column(name = "utmcontent")
	private String utmcontent;
	
	/* 关键字 */
	@Column(name = "utmterm")
	private String utmterm;
	
	/*激活账户数*/
	@Column(name = "accountactivecnt")
	private String accountactivecnt;
	
	/* 总存款 */
	@Column(name = "depositamt")
	private String depositamt;
	
	/* 总取款*/
	@Column(name = "withdrawamt")
	private String withdrawamt;
	
	/*净入金*/
	@Column(name = "cleandeposit")
	private String cleandeposit;
	
	/* 开仓手数*/
	@Column(name = "openvolume")
	private String openvolume;
	
	/*平仓手数*/
	@Column(name = "closevolume")
	private String closevolume;
	
	/* 人均存款 */
	@Column(name = "avgdepositamt")
	private String avgdepositamt;
	
	/* 人均取款 */
	@Column(name = "avgwithdrawamt")
	private String avgwithdrawamt;
	
	/* 人均净入金 */
	@Column(name = "avgcleandeposit")
	private String avgcleandeposit;
	
	/* 人均开仓手数*/
	@Column(name = "avgopenvolume")
	private String avgopenvolume;
	
	/* 人均平仓手数 */
	@Column(name = "avgclosevolume")
	private String avgclosevolume;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

    

	public String getExecdate() {
		return execdate;
	}

	public void setExecdate(String execdate) {
		this.execdate = execdate;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getChannelgroup() {
		return channelgroup;
	}

	public void setChannelgroup(String channelgroup) {
		this.channelgroup = channelgroup;
	}

	public String getChannellevel() {
		return channellevel;
	}

	public void setChannellevel(String channellevel) {
		this.channellevel = channellevel;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUtmsource() {
		return utmsource;
	}

	public void setUtmsource(String utmsource) {
		this.utmsource = utmsource;
	}

	public String getUtmmedium() {
		return utmmedium;
	}

	public void setUtmmedium(String utmmedium) {
		this.utmmedium = utmmedium;
	}

	public String getUtmcampaign() {
		return utmcampaign;
	}

	public void setUtmcampaign(String utmcampaign) {
		this.utmcampaign = utmcampaign;
	}

	public String getUtmcontent() {
		return utmcontent;
	}

	public void setUtmcontent(String utmcontent) {
		this.utmcontent = utmcontent;
	}

	public String getUtmterm() {
		return utmterm;
	}

	public void setUtmterm(String utmterm) {
		this.utmterm = utmterm;
	}

	public String getAccountactivecnt() {
		return accountactivecnt;
	}

	public void setAccountactivecnt(String accountactivecnt) {
		this.accountactivecnt = accountactivecnt;
	}

	public String getDepositamt() {
		return depositamt;
	}

	public void setDepositamt(String depositamt) {
		this.depositamt = depositamt;
	}

	public String getWithdrawamt() {
		return withdrawamt;
	}

	public void setWithdrawamt(String withdrawamt) {
		this.withdrawamt = withdrawamt;
	}

	public String getCleandeposit() {
		return cleandeposit;
	}

	public void setCleandeposit(String cleandeposit) {
		this.cleandeposit = cleandeposit;
	}

	public String getOpenvolume() {
		return openvolume;
	}

	public void setOpenvolume(String openvolume) {
		this.openvolume = openvolume;
	}

	public String getClosevolume() {
		return closevolume;
	}

	public void setClosevolume(String closevolume) {
		this.closevolume = closevolume;
	}

	public String getAvgdepositamt() {
		return avgdepositamt;
	}

	public void setAvgdepositamt(String avgdepositamt) {
		this.avgdepositamt = avgdepositamt;
	}

	public String getAvgwithdrawamt() {
		return avgwithdrawamt;
	}

	public void setAvgwithdrawamt(String avgwithdrawamt) {
		this.avgwithdrawamt = avgwithdrawamt;
	}

	public String getAvgcleandeposit() {
		return avgcleandeposit;
	}

	public void setAvgcleandeposit(String avgcleandeposit) {
		this.avgcleandeposit = avgcleandeposit;
	}

	public String getAvgopenvolume() {
		return avgopenvolume;
	}

	public void setAvgopenvolume(String avgopenvolume) {
		this.avgopenvolume = avgopenvolume;
	}

	public String getAvgclosevolume() {
		return avgclosevolume;
	}

	public void setAvgclosevolume(String avgclosevolume) {
		this.avgclosevolume = avgclosevolume;
	}

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	

}
