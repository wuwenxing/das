package com.gw.das.business.dao.trade.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 交易记录总结
 */
public class DasTradeDealprofitdetailStatisticsYears extends BaseModel {
	/** rowkey */
	@Column(name = "rowkey")
	private String rowKey;
	/** 毛利 */
	@Column(name = "grossprofit")
	private Double grossprofit = 0D;
	/** 公司实现盈亏 */
	@Column(name = "companyprofit")
	private Double companyprofit = 0D;
	/** 实现利息 */
	@Column(name = "swap")
	private Double swap = 0D;
	/** 系统清零 */
	@Column(name = "sysclearzero")
	private Double sysclearzero = 0D;
	/** 推广贈金 */
	@Column(name = "bonus")
	private Double bonus = 0D;
	/** 返佣 */
	@Column(name = "commission")
	private Double commission = 0D;
	/** 调整 */
	@Column(name = "adjustfixedamount")
	private Double adjustfixedamount = 0D;
	/** 公司浮动盈亏 */
	@Column(name = "floatingprofit")
	private Double floatingprofit = 0D;
	/** 伦敦金盈亏 */
	@Column(name = "goldprofit")
	private Double goldprofit = 0D;
	/** 伦敦银盈亏 */
	@Column(name = "silverprofit")
	private Double silverprofit = 0D;
	/** 人民幣金盈虧 */
	@Column(name = "xaucnhprofit")
	private Double xaucnhprofit = 0D;
	/** 人民幣銀盈虧 */
	@Column(name = "xagcnhprofit")
	private Double xagcnhprofit = 0D;
	/** 交易手數(金) */
	@Column(name = "goldvolume")
	private Double goldvolume = 0D;
	/** 交易手數(銀) */
	@Column(name = "silvervolume")
	private Double silvervolume = 0D;
	/** 交易手數(人民幣金) */
	@Column(name = "xaucnhvolume")
	private Double xaucnhvolume = 0D;
	/** 交易手數(人民幣銀) */
	@Column(name = "xagcnhvolume")
	private Double xagcnhvolume = 0D;
	/** 交易手數 */
	@Column(name = "volume")
	private Double volume = 0D;
	/** 交易手數（平倉手） */
	@Column(name = "closedvolume")
	private Double closedvolume = 0D;
	/** 交易人次 */
	@Column(name = "useramount")
	private Double useramount = 0D;
	/** 交易次數 */
	@Column(name = "dealamount")
	private Double dealamount = 0D;
	/** 贈金活動清零 */
	@Column(name = "bonusclear")
	private Double bonusclear = 0D;
	/** 占用保证金 */
	@Column(name = "margin")
	private Double margin = 0D;
	/** 编码费 */
	@Column(name = "cgse")
	private Double cgse = 0D;
	
	/** 净盈亏*/
	@Column(name = "netCompanyprofit")
	private Double netCompanyprofit= 0D;

	/** 货币类型 */
	@Column(name = "currency")
	private String currency;

	/** 执行时间 */
	@Column(name = "exectime")
	private String exectime;

	/** 新旧平台标识 */
	@Column(name = "source")
	private String source;

	/** 平台类型 */
	@Column(name = "platformtype")
	private String platformtype;

	/** companyid */
	@Column(name = "companyid")
	private String companyid;

	/** 业务类型 */
	@Column(name = "businessplatform")
	private String businessPlatform;

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

	public Double getNetCompanyprofit() {
		return netCompanyprofit;
	}

	public void setNetCompanyprofit(Double netCompanyprofit) {
		this.netCompanyprofit = netCompanyprofit;
	}
    
}
