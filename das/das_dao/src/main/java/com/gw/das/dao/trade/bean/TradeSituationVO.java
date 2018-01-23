package com.gw.das.dao.trade.bean;

/**
 *交易情况记录VO类
 * 
 * @author darren
 * @since 2017-10-20
 */
public class TradeSituationVO {

	/* rowkey */
	private String rowkey;
	
	/* 日期 */
	private String execdate;
	
	/* 周 */
	private String weeks;
	
	/* 平台 */
	private String platform;
	
	/*  */
	private String devicetype;
	
	/* 渠道分组 */
	private String channelgroup;
	
	/* 渠道分级 */
	private String channellevel;
	
	/*渠道 */
	private String channel;
	
	/* 来源*/
	private String utmsource;
	
	/* 媒件*/
	private String utmmedium;
	
	/* 系列 */
	private String utmcampaign;
	
	/* 组 */
	private String utmcontent;
	
	/* 关键字 */
	private String utmterm;
	
	/*激活账户数*/
	private String accountactivecnt;
	
	/* 总存款 */
	private String depositamt;
	
	/* 总取款*/
	private String withdrawamt;
	
	/*净入金*/
	private String cleandeposit;
	
	/* 开仓手数*/
	private String openvolume;
	
	/*平仓手数*/
	private String closevolume;
	
	/* 人均存款 */
	private String avgdepositamt;
	
	/* 人均取款 */
	private String avgwithdrawamt;
	
	/* 人均净入金 */
	private String avgcleandeposit;
	
	/* 人均开仓手数*/
	private String avgopenvolume;
	
	/* 人均平仓手数 */
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

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
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
