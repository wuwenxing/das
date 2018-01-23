package com.gw.das.dao.trade.bean;

/**
 * MT4/GTS2数据报表VO类
 * 
 * @author darren
 * @since 2017-04-20
 */
public class DealprofitdetailMT4AndGts2VO {
	
	/* rowkey */
	private String rowkey;
	
	/* 执行时间 */
	private String exectime;
		
	/* 星期 */
	private String week;
	
	/* 星期 */
	private Integer weekCode;
	
	/* NET */
	private String net;
	
	/* SUM */
	private String sum;
	
	/* 平台类型 */
	private String platform;
	
	/* 业务标识 */
	private String businessplatform;
	
	/* MT4倫敦金平倉手數 */
	private String mt4volumeaxu;
	
	/* MT4倫敦銀平倉手數 */
	private String mt4volumeaxg;
	
	/* MT4人民幣金平倉手數 */
	private String mt4volumeaxucnh;
	
	/* MT4人民幣銀平倉手數*/
	private String mt4volumeaxgcnh;
	
	/* GTS2倫敦金平倉手數 */
	private String gts2volumeaxu;
	
	/* GTS2倫敦銀平倉手數 */
	private String gts2volumeaxg;
	
	/* GTS2人民幣金平倉手數 */
	private String gts2volumeaxucnh;
	
	/* GTS2人民幣銀平倉手數 */
	private String gts2volumeaxgcnh;
	
	/* 系统清零*/
	private String sysclearzero;
	
	/* 公司实现盈亏 */
	private String companyprofit;
	
	/* 返佣 */
	private String commission;
	
	/* 调整 */
	private String adjustfixedamount;
	
	/* 編碼費 */
	private String cgse;
	
	/* 实现利息 */
	private String swap;
	
	/* 交易手數（平倉手） */
	private String closedvolume;
	
	/* 占用保证金 */
	private String margin;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getExectime() {
		return exectime;
	}

	public void setExectime(String exectime) {
		this.exectime = exectime;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Integer getWeekCode() {
		return weekCode;
	}

	public void setWeekCode(Integer weekCode) {
		this.weekCode = weekCode;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
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

	public String getMt4volumeaxu() {
		return mt4volumeaxu;
	}

	public void setMt4volumeaxu(String mt4volumeaxu) {
		this.mt4volumeaxu = mt4volumeaxu;
	}

	public String getMt4volumeaxg() {
		return mt4volumeaxg;
	}

	public void setMt4volumeaxg(String mt4volumeaxg) {
		this.mt4volumeaxg = mt4volumeaxg;
	}

	public String getGts2volumeaxu() {
		return gts2volumeaxu;
	}

	public void setGts2volumeaxu(String gts2volumeaxu) {
		this.gts2volumeaxu = gts2volumeaxu;
	}

	public String getGts2volumeaxg() {
		return gts2volumeaxg;
	}

	public void setGts2volumeaxg(String gts2volumeaxg) {
		this.gts2volumeaxg = gts2volumeaxg;
	}

	public String getMt4volumeaxucnh() {
		return mt4volumeaxucnh;
	}

	public void setMt4volumeaxucnh(String mt4volumeaxucnh) {
		this.mt4volumeaxucnh = mt4volumeaxucnh;
	}

	public String getMt4volumeaxgcnh() {
		return mt4volumeaxgcnh;
	}

	public void setMt4volumeaxgcnh(String mt4volumeaxgcnh) {
		this.mt4volumeaxgcnh = mt4volumeaxgcnh;
	}

	public String getGts2volumeaxucnh() {
		return gts2volumeaxucnh;
	}

	public void setGts2volumeaxucnh(String gts2volumeaxucnh) {
		this.gts2volumeaxucnh = gts2volumeaxucnh;
	}

	public String getGts2volumeaxgcnh() {
		return gts2volumeaxgcnh;
	}

	public void setGts2volumeaxgcnh(String gts2volumeaxgcnh) {
		this.gts2volumeaxgcnh = gts2volumeaxgcnh;
	}

	public String getSysclearzero() {
		return sysclearzero;
	}

	public void setSysclearzero(String sysclearzero) {
		this.sysclearzero = sysclearzero;
	}

	public String getCompanyprofit() {
		return companyprofit;
	}

	public void setCompanyprofit(String companyprofit) {
		this.companyprofit = companyprofit;
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

	public String getCgse() {
		return cgse;
	}

	public void setCgse(String cgse) {
		this.cgse = cgse;
	}

	public String getSwap() {
		return swap;
	}

	public void setSwap(String swap) {
		this.swap = swap;
	}

	public String getClosedvolume() {
		return closedvolume;
	}

	public void setClosedvolume(String closedvolume) {
		this.closedvolume = closedvolume;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}
	
	
}
