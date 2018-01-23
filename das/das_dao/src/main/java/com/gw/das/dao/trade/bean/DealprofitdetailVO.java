package com.gw.das.dao.trade.bean;

/**
 * 交易记录总结报表(包括了交易手数图表和毛利图表)VO类
 * 
 * @author darren
 * @since 2017-04-13
 */
public class DealprofitdetailVO {

	/* rowkey */
	private String rowkey;
	
	/*  毛利 */
	private String grossprofit;
	
	/* 公司实现盈亏 */
	private String companyprofit;
	
	/* 实现利息 */
	private String swap;
	
	/* 系统清零*/
	private String sysclearzero;
	
	/* 推广贈金 */
	private String bonus;
	
	/* 返佣 */
	private String commission;
	
	/* 调整 */
	private String adjustfixedamount;
	
	/* 公司浮动盈亏 */
	private String floatingprofit;
	
	/* 伦敦金盈亏 */
	private String goldprofit;
	
	/* 伦敦银盈亏 */
	private String silverprofit;
	
	/* 人民幣金盈虧*/
	private String xaucnhprofit;
	
	/* 人民幣銀盈虧 */
	private String xagcnhprofit;
	
	/* 交易手數(金) */
	private String goldvolume;
	
	/* 交易手數(銀) */
	private String silvervolume;
	
	/* 交易手數(人民幣金) */
	private String xaucnhvolume;
	
	/* 交易手數(人民幣銀)*/
	private String xagcnhvolume;
	
	/* 交易手數 */
	private String volume;
	
	/* 交易手數（平倉手） */
	private String closedvolume;
	
	/* 贈金活動清零 */
	private String bonusclear;
	
	/* 占用保证金 */
	private String margin;
	
	/* 編碼費 */
	private String cgse;
	
	/* 货币种类 */
	private String currency;	
	
	/* 执行时间 */
	private String exectime;
	
	/* 新旧平台标识 */
	private String source;
	
	/* 平台类型 */
	private String platform;
	
	/* 业务标识 */
	private String businessplatform;
	
	/* 分区字段(按年分) */
	private String partitionfield;
	
	/* 公司ID */
	private String companyid;
	
	/* 交易人次 */
	private String useramount;
	
	/* 交易次數 */
	private String dealamount;
	
	private String handOperHedgeProfitAndLoss;
	
	/* 人均交易手数 */
	private String mt4avgtransvolume;

	/* 人均交易手数 */
	private String gts2avgtransvolume;
	
	/* 人均交易手数 */
	private String avgtransvolume;
	
	private String bonusOtherGts2 ;// 活动推广赠金（其它）GTS2
	
	private String bonusOtherMt4 ;// 活动推广赠金（其它）MT4

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getGrossprofit() {
		return grossprofit;
	}

	public void setGrossprofit(String grossprofit) {
		this.grossprofit = grossprofit;
	}

	public String getCompanyprofit() {
		return companyprofit;
	}

	public void setCompanyprofit(String companyprofit) {
		this.companyprofit = companyprofit;
	}

	public String getSwap() {
		return swap;
	}

	public void setSwap(String swap) {
		this.swap = swap;
	}

	public String getSysclearzero() {
		return sysclearzero;
	}

	public void setSysclearzero(String sysclearzero) {
		this.sysclearzero = sysclearzero;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getAdjustfixedamount() {
		return adjustfixedamount;
	}

	public void setAdjustfixedamount(String adjustfixedamount) {
		this.adjustfixedamount = adjustfixedamount;
	}

	public String getFloatingprofit() {
		return floatingprofit;
	}

	public void setFloatingprofit(String floatingprofit) {
		this.floatingprofit = floatingprofit;
	}

	public String getGoldprofit() {
		return goldprofit;
	}

	public void setGoldprofit(String goldprofit) {
		this.goldprofit = goldprofit;
	}

	public String getSilverprofit() {
		return silverprofit;
	}

	public void setSilverprofit(String silverprofit) {
		this.silverprofit = silverprofit;
	}

	public String getXaucnhprofit() {
		return xaucnhprofit;
	}

	public void setXaucnhprofit(String xaucnhprofit) {
		this.xaucnhprofit = xaucnhprofit;
	}

	public String getXagcnhprofit() {
		return xagcnhprofit;
	}

	public void setXagcnhprofit(String xagcnhprofit) {
		this.xagcnhprofit = xagcnhprofit;
	}

	public String getGoldvolume() {
		return goldvolume;
	}

	public void setGoldvolume(String goldvolume) {
		this.goldvolume = goldvolume;
	}

	public String getSilvervolume() {
		return silvervolume;
	}

	public void setSilvervolume(String silvervolume) {
		this.silvervolume = silvervolume;
	}

	public String getXaucnhvolume() {
		return xaucnhvolume;
	}

	public void setXaucnhvolume(String xaucnhvolume) {
		this.xaucnhvolume = xaucnhvolume;
	}

	public String getXagcnhvolume() {
		return xagcnhvolume;
	}

	public void setXagcnhvolume(String xagcnhvolume) {
		this.xagcnhvolume = xagcnhvolume;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getClosedvolume() {
		return closedvolume;
	}

	public void setClosedvolume(String closedvolume) {
		this.closedvolume = closedvolume;
	}

	public String getBonusclear() {
		return bonusclear;
	}

	public void setBonusclear(String bonusclear) {
		this.bonusclear = bonusclear;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getCgse() {
		return cgse;
	}

	public void setCgse(String cgse) {
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getBusinessplatform() {
		return businessplatform;
	}

	public void setBusinessplatform(String businessplatform) {
		this.businessplatform = businessplatform;
	}

	public String getPartitionfield() {
		return partitionfield;
	}

	public void setPartitionfield(String partitionfield) {
		this.partitionfield = partitionfield;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getUseramount() {
		return useramount;
	}

	public void setUseramount(String useramount) {
		this.useramount = useramount;
	}

	public String getDealamount() {
		return dealamount;
	}

	public void setDealamount(String dealamount) {
		this.dealamount = dealamount;
	}

	public String getHandOperHedgeProfitAndLoss() {
		return handOperHedgeProfitAndLoss;
	}

	public void setHandOperHedgeProfitAndLoss(String handOperHedgeProfitAndLoss) {
		this.handOperHedgeProfitAndLoss = handOperHedgeProfitAndLoss;
	}

	public String getMt4avgtransvolume() {
		return mt4avgtransvolume;
	}

	public void setMt4avgtransvolume(String mt4avgtransvolume) {
		this.mt4avgtransvolume = mt4avgtransvolume;
	}

	public String getGts2avgtransvolume() {
		return gts2avgtransvolume;
	}

	public void setGts2avgtransvolume(String gts2avgtransvolume) {
		this.gts2avgtransvolume = gts2avgtransvolume;
	}

	public String getBonusOtherGts2() {
		return bonusOtherGts2;
	}

	public void setBonusOtherGts2(String bonusOtherGts2) {
		this.bonusOtherGts2 = bonusOtherGts2;
	}

	public String getBonusOtherMt4() {
		return bonusOtherMt4;
	}

	public void setBonusOtherMt4(String bonusOtherMt4) {
		this.bonusOtherMt4 = bonusOtherMt4;
	}

	public String getAvgtransvolume() {
		return avgtransvolume;
	}

	public void setAvgtransvolume(String avgtransvolume) {
		this.avgtransvolume = avgtransvolume;
	}

	

}
