package com.gw.das.business.dao.tradeGts2.entity;

import com.gw.das.business.common.orm.Column;
import com.gw.das.business.dao.base.BaseModel;

/**
 * 交易记录总结报表(包括了交易手数图表和毛利图表)VO类
 * 
 * @author darren
 * @since 2017-04-13
 */
public class DealprofitdetailVO extends BaseModel {

	/* rowkey */
	@Column(name = "rowkey")
	private String rowkey;
	
	/*  毛利 */
	@Column(name = "grossprofit")
	private String grossprofit;
	
	/* 公司实现盈亏 */
	@Column(name = "companyprofit")
	private String companyprofit;
	
	/* 实现利息 */
	@Column(name = "swap")
	private String swap;
	
	/* 系统清零*/
	@Column(name = "sysclearzero")
	private String sysclearzero;
	
	/* 推广贈金 */
	@Column(name = "bonus")
	private String bonus;
	
	/* 返佣 */
	@Column(name = "commission")
	private String commission;
	
	/* 调整 */
	@Column(name = "adjustfixedamount")
	private String adjustfixedamount;
	
	/* 公司浮动盈亏 */
	@Column(name = "floatingprofit")
	private String floatingprofit;
	
	/* 伦敦金盈亏 */
	@Column(name = "goldprofit")
	private String goldprofit;
	
	/* 伦敦银盈亏 */
	@Column(name = "silverprofit")
	private String silverprofit;
	
	/* 人民幣金盈虧*/
	@Column(name = "xaucnhprofit")
	private String xaucnhprofit;
	
	/* 人民幣銀盈虧 */
	@Column(name = "xagcnhprofit")
	private String xagcnhprofit;
	
	/* 交易手數(金) */
	@Column(name = "goldvolume")
	private String goldvolume;
	
	/* 交易手數(銀) */
	@Column(name = "silvervolume")
	private String silvervolume;
	
	/* 交易手數(人民幣金) */
	@Column(name = "xaucnhvolume")
	private String xaucnhvolume;
	
	/* 交易手數(人民幣銀)*/
	@Column(name = "xagcnhvolume")
	private String xagcnhvolume;
	
	/* 交易手數 */
	@Column(name = "volume")
	private String volume;
	
	/* 交易手數（平倉手） */
	@Column(name = "closedvolume")
	private String closedvolume;	
	
	/* 贈金活動清零 */
	@Column(name = "bonusclear")
	private String bonusclear;
	
	/* 占用保证金 */
	@Column(name = "margin")
	private String margin;
	
	/* 編碼費 */
	@Column(name = "cgse")
	private String cgse;
	
	/* 货币种类 */
	@Column(name = "currency")
	private String currency;
	
	/* 执行时间 */
	@Column(name = "exectime")
	private String exectime;
	
	/* 新旧平台标识 */
	@Column(name = "source")
	private String source;
	
	/* 平台类型 */
	@Column(name = "platform")
	private String platform;
	
	/* 公司ID */
	@Column(name = "companyid")
	private String companyid;
	
	/* 业务标识 */
	@Column(name = "businessplatform")
	private String businessplatform;
	
	/* 分区字段(按年分) */
	@Column(name = "partitionfield")
	private String partitionfield;
	
	
	/* 交易人次 */
	@Column(name = "useramount")
	private String useramount;
	
	/* 交易次數 */
	@Column(name = "dealamount")
	private String dealamount;
	
	/* 人均交易手数 */
	@Column(name = "mt4avgtransvolume")
	private String mt4avgtransvolume;

	/* 人均交易手数 */
	@Column(name = "gts2avgtransvolume")
	private String gts2avgtransvolume;
	
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

}
