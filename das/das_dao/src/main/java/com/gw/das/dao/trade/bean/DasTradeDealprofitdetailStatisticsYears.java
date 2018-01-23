package com.gw.das.dao.trade.bean;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 交易记录总结
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasTradeDealprofitdetailStatisticsYears {
	/** rowkey */
	private String rowKey;
	/** 毛利 */
	private Double grossprofit;
	/** 公司实现盈亏 */
	private Double companyprofit;
	/** 实现利息 */
	private Double swap;
	/** 系统清零 */
	private Double sysclearzero;
	/** 推广贈金 */
	private Double bonus;
	/** 返佣 */
	private Double commission;
	/** 调整 */
	private Double adjustfixedamount;
	/** 公司浮动盈亏 */
	private Double floatingprofit;
	/** 伦敦金盈亏 */
	private Double goldprofit;
	/** 伦敦银盈亏 */
	private Double silverprofit;
	/** 人民幣金盈虧 */
	private Double xaucnhprofit;
	/** 人民幣銀盈虧 */
	private Double xagcnhprofit;
	/** 交易手數(金) */
	private Double goldvolume;
	/** 交易手數(銀) */
	private Double silvervolume;
	/** 交易手數(人民幣金) */
	private Double xaucnhvolume;
	/** 交易手數(人民幣銀) */
	private Double xagcnhvolume;
	/** 交易手數 */
	private Double volume;
	/** 交易手數（平倉手） */
	private Double closedvolume;
	/** 交易人次 */
	private Double useramount;
	/** 交易次數 */
	private Double dealamount;
	/** 贈金活動清零 */
	private Double bonusclear;
	/** 占用保证金 */
	private Double margin;
	/** 编码费 */
	private Double cgse;
	/** 净盈亏*/
	private Double netCompanyprofit;

	/** 货币类型 */
	private String currency;

	/** 执行时间 */
	private String exectime;

	/** 新旧平台标识 */
	private String source;

	/** 平台类型 */
	private String platformtype;

	/** companyid */
	private String companyid;

	/** 业务类型 */
	private String businessPlatform;

	/**手工录入数据相关*/
	/**市场状况*/
	private Long siteVisitsCount = 0L;// 当日网站总访问量
	private Double channelPromotionCosts = 0D;// 当日渠道推广费用
	/**市场状况*/
	
	/**交易记录总结*/
	private Double handOperHedgeProfitAndLossGts2 = 0D;// 手动对冲盈亏（需要加到“毛利”中，公式见之前的文档）
	private Double handOperHedgeProfitAndLossMt4 = 0D;
	private Double handOperHedgeProfitAndLossGts = 0D;// 手动对冲盈亏（需要加到“毛利”中，公式见之前的文档）
	private Double handOperHedgeProfitAndLossMt5 = 0D;
	/**交易记录总结*/

	/**活动推广赠金（其它）*/
	private Double bonusOtherGts2 = 0D;// 活动推广赠金（其它）GTS2
	private Double bonusOtherMt4 = 0D;// 活动推广赠金（其它）MT4
	private Double bonusOtherGts = 0D;// 活动推广赠金（其它）GTS
	private Double bonusOtherMt5 = 0D;// 活动推广赠金（其它）MT5
	/**活动推广赠金（其它）*/
	
	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public Double getGrossprofit() {
		return grossprofit;
	}

	public void setGrossprofit(Double grossprofit) {
		this.grossprofit = grossprofit;
	}

	public Double getCompanyprofit() {
		return companyprofit;
	}

	public void setCompanyprofit(Double companyprofit) {
		this.companyprofit = companyprofit;
	}

	public Double getSwap() {
		return swap;
	}

	public void setSwap(Double swap) {
		this.swap = swap;
	}

	public Double getSysclearzero() {
		return sysclearzero;
	}

	public void setSysclearzero(Double sysclearzero) {
		this.sysclearzero = sysclearzero;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getAdjustfixedamount() {
		return adjustfixedamount;
	}

	public void setAdjustfixedamount(Double adjustfixedamount) {
		this.adjustfixedamount = adjustfixedamount;
	}

	public Double getFloatingprofit() {
		return floatingprofit;
	}

	public void setFloatingprofit(Double floatingprofit) {
		this.floatingprofit = floatingprofit;
	}

	public Double getGoldprofit() {
		return goldprofit;
	}

	public void setGoldprofit(Double goldprofit) {
		this.goldprofit = goldprofit;
	}

	public Double getSilverprofit() {
		return silverprofit;
	}

	public void setSilverprofit(Double silverprofit) {
		this.silverprofit = silverprofit;
	}

	public Double getXaucnhprofit() {
		return xaucnhprofit;
	}

	public void setXaucnhprofit(Double xaucnhprofit) {
		this.xaucnhprofit = xaucnhprofit;
	}

	public Double getXagcnhprofit() {
		return xagcnhprofit;
	}

	public void setXagcnhprofit(Double xagcnhprofit) {
		this.xagcnhprofit = xagcnhprofit;
	}

	public Double getGoldvolume() {
		return goldvolume;
	}

	public void setGoldvolume(Double goldvolume) {
		this.goldvolume = goldvolume;
	}

	public Double getSilvervolume() {
		return silvervolume;
	}

	public void setSilvervolume(Double silvervolume) {
		this.silvervolume = silvervolume;
	}

	public Double getXaucnhvolume() {
		return xaucnhvolume;
	}

	public void setXaucnhvolume(Double xaucnhvolume) {
		this.xaucnhvolume = xaucnhvolume;
	}

	public Double getXagcnhvolume() {
		return xagcnhvolume;
	}

	public void setXagcnhvolume(Double xagcnhvolume) {
		this.xagcnhvolume = xagcnhvolume;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getClosedvolume() {
		return closedvolume;
	}

	public void setClosedvolume(Double closedvolume) {
		this.closedvolume = closedvolume;
	}

	public Double getUseramount() {
		return useramount;
	}

	public void setUseramount(Double useramount) {
		this.useramount = useramount;
	}

	public Double getDealamount() {
		return dealamount;
	}

	public void setDealamount(Double dealamount) {
		this.dealamount = dealamount;
	}

	public Double getBonusclear() {
		return bonusclear;
	}

	public void setBonusclear(Double bonusclear) {
		this.bonusclear = bonusclear;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public Double getCgse() {
		return cgse;
	}

	public void setCgse(Double cgse) {
		this.cgse = cgse;
	}

	public Double getNetCompanyprofit() {
		return netCompanyprofit;
	}

	public void setNetCompanyprofit(Double netCompanyprofit) {
		this.netCompanyprofit = netCompanyprofit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getExectime() {
		return exectime;
	}

	public void setExectime(String exectime) {
		this.exectime = exectime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(String businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

	public Long getSiteVisitsCount() {
		return siteVisitsCount;
	}

	public void setSiteVisitsCount(Long siteVisitsCount) {
		this.siteVisitsCount = siteVisitsCount;
	}

	public Double getChannelPromotionCosts() {
		return channelPromotionCosts;
	}

	public void setChannelPromotionCosts(Double channelPromotionCosts) {
		this.channelPromotionCosts = channelPromotionCosts;
	}

	public Double getHandOperHedgeProfitAndLossGts2() {
		return handOperHedgeProfitAndLossGts2;
	}

	public void setHandOperHedgeProfitAndLossGts2(Double handOperHedgeProfitAndLossGts2) {
		this.handOperHedgeProfitAndLossGts2 = handOperHedgeProfitAndLossGts2;
	}

	public Double getHandOperHedgeProfitAndLossMt4() {
		return handOperHedgeProfitAndLossMt4;
	}

	public void setHandOperHedgeProfitAndLossMt4(Double handOperHedgeProfitAndLossMt4) {
		this.handOperHedgeProfitAndLossMt4 = handOperHedgeProfitAndLossMt4;
	}

	public Double getBonusOtherGts2() {
		return bonusOtherGts2;
	}

	public void setBonusOtherGts2(Double bonusOtherGts2) {
		this.bonusOtherGts2 = bonusOtherGts2;
	}

	public Double getBonusOtherMt4() {
		return bonusOtherMt4;
	}

	public void setBonusOtherMt4(Double bonusOtherMt4) {
		this.bonusOtherMt4 = bonusOtherMt4;
	}

	public Double getHandOperHedgeProfitAndLossGts() {
		return handOperHedgeProfitAndLossGts;
	}

	public void setHandOperHedgeProfitAndLossGts(Double handOperHedgeProfitAndLossGts) {
		this.handOperHedgeProfitAndLossGts = handOperHedgeProfitAndLossGts;
	}

	public Double getHandOperHedgeProfitAndLossMt5() {
		return handOperHedgeProfitAndLossMt5;
	}

	public void setHandOperHedgeProfitAndLossMt5(Double handOperHedgeProfitAndLossMt5) {
		this.handOperHedgeProfitAndLossMt5 = handOperHedgeProfitAndLossMt5;
	}

	public Double getBonusOtherGts() {
		return bonusOtherGts;
	}

	public void setBonusOtherGts(Double bonusOtherGts) {
		this.bonusOtherGts = bonusOtherGts;
	}

	public Double getBonusOtherMt5() {
		return bonusOtherMt5;
	}

	public void setBonusOtherMt5(Double bonusOtherMt5) {
		this.bonusOtherMt5 = bonusOtherMt5;
	}

}
