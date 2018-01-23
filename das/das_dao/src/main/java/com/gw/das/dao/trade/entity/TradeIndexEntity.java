package com.gw.das.dao.trade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gw.das.dao.base.BaseEntity;


@Entity
@Table(name = "t_trade_index")
public class TradeIndexEntity extends BaseEntity {

	private static final long serialVersionUID = -3260436475224543365L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "trade_index_id", nullable = false)
	private Long tradeIndexId;// 指标ID

	@Column(name = "date_time", length = 100)
	private String dateTime;// 日期

	/**价格趋势*/
	@Column(name = "gold_high_price", columnDefinition="DOUBLE default 0")
	private Double goldHighPrice = 0D;// 最高金价
	@Column(name = "gold_low_price", columnDefinition="DOUBLE default 0")
	private Double goldLowPrice = 0D;// 最低金价
	@Column(name = "gold_amp", columnDefinition="DOUBLE default 0")
	private Double goldAmp = 0D;// 金价波幅
	@Column(name = "gold_up_and_down", length = 100)
	private String goldUpAndDown;// 金价升跌
	
	@Column(name = "silver_high_price", columnDefinition="DOUBLE default 0")
	private Double silverHighPrice = 0D;// 最高银价
	@Column(name = "silver_low_price", columnDefinition="DOUBLE default 0")
	private Double silverLowPrice = 0D;// 最低银价
	@Column(name = "silver_amp", columnDefinition="DOUBLE default 0")
	private Double silverAmp = 0D;// 银价波幅
	@Column(name = "silver_up_and_down", length = 100)
	private String silverUpAndDown;// 银价升跌
	/**价格趋势*/
	
	/**客户状况*/
	@Column(name = "advisory_customer_count", columnDefinition="INT default 0")
	private Long advisoryCustomerCount = 0L;// 当日客户对话量-客服電話回訪量
	@Column(name = "advisory_count", columnDefinition="INT default 0")
	private Long advisoryCount = 0L;// 当日对话总数-客戶咨詢量
	/**客户状况*/

	/**20171020新增*/
	@Column(name = "customer_phone_count", columnDefinition="INT default 0")
	private Long customerPhoneCount = 0L;// 取得客户电话数目
	
	/**市场状况*/
	@Column(name = "site_visits_count", columnDefinition="INT default 0")
	private Long siteVisitsCount = 0L;// 当日网站总访问量
	@Column(name = "channel_promotion_costs", columnDefinition="DOUBLE default 0")
	private Double channelPromotionCosts = 0D;// 当日渠道推广费用
	/**市场状况*/

	/**交易记录总结*/
	@Column(name = "hand_oper_hedge_profit_and_loss_gts2", columnDefinition="DOUBLE default 0")
	private Double handOperHedgeProfitAndLossGts2 = 0D;// 手动对冲盈亏（需要加到“毛利”中，公式见之前的文档）
	@Column(name = "hand_oper_hedge_profit_and_loss_mt4", columnDefinition="DOUBLE default 0")
	private Double handOperHedgeProfitAndLossMt4 = 0D;
	@Column(name = "hand_oper_hedge_profit_and_loss_gts", columnDefinition="DOUBLE default 0")
	private Double handOperHedgeProfitAndLossGts = 0D;// 手动对冲盈亏（需要加到“毛利”中，公式见之前的文档）
	@Column(name = "hand_oper_hedge_profit_and_loss_mt5", columnDefinition="DOUBLE default 0")
	private Double handOperHedgeProfitAndLossMt5 = 0D;
	/**交易记录总结*/

	/**净仓*/
	@Column(name = "gold_net_positions_volume", columnDefinition="DOUBLE default 0")
	private Double goldNetPositionsVolume = 0D;// 黄金净仓手数
	@Column(name = "silver_net_positions_volume", columnDefinition="DOUBLE default 0")
	private Double silverNetPositionsVolume = 0D;// 白银净仓手数
	/**净仓*/

	/**活动推广赠金（其它）*/
	/**
	 * 更新“活动推广赠金（其它）”，填入以下位置
	 * “活动推广赠金（其它）”=“【額度調整-其他調整】中涉及活动的部分”（手动填写）
	 * “活动推广赠金”=“【額度調整-贈金扣除】+【額度調整-贈金送出】”
	 * “累计活动推广赠金”=“活动推广赠金”+“活动推广赠金（其它）”
	 * “累计活动推广赠金”不计入“当日毛利”，只计入“当月毛利”
	 * 更新字段：【市场状况】的【当日累计活动推广赠金】、【当月累计活动推广赠金】
	 * 更新字段：加入到【交易状况】的【当月毛利】（当日毛利不进行更新
	 */
	@Column(name = "bonus_other_gts2", columnDefinition="DOUBLE default 0")
	private Double bonusOtherGts2 = 0D;// 活动推广赠金（其它）GTS2
	@Column(name = "bonus_other_mt4", columnDefinition="DOUBLE default 0")
	private Double bonusOtherMt4 = 0D;// 活动推广赠金（其它）MT4
	@Column(name = "bonus_other_mt5", columnDefinition="DOUBLE default 0")
	private Double bonusOtherMt5 = 0D;// 活动推广赠金（其它）MT5
	@Column(name = "bonus_other_gts", columnDefinition="DOUBLE default 0")
	private Double bonusOtherGts = 0D;// 活动推广赠金（其它）GTS
	/**活动推广赠金（其它）*/
	
	
	@Transient
	private String startDate; // 开始时间-查询条件

	@Transient
	private String endDate; // 结束时间-查询条件

	public Long getTradeIndexId() {
		return tradeIndexId;
	}

	public void setTradeIndexId(Long tradeIndexId) {
		this.tradeIndexId = tradeIndexId;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Double getGoldHighPrice() {
		return goldHighPrice;
	}

	public void setGoldHighPrice(Double goldHighPrice) {
		this.goldHighPrice = goldHighPrice;
	}

	public Double getGoldLowPrice() {
		return goldLowPrice;
	}

	public void setGoldLowPrice(Double goldLowPrice) {
		this.goldLowPrice = goldLowPrice;
	}

	public Double getGoldAmp() {
		return goldAmp;
	}

	public void setGoldAmp(Double goldAmp) {
		this.goldAmp = goldAmp;
	}

	public String getGoldUpAndDown() {
		return goldUpAndDown;
	}

	public void setGoldUpAndDown(String goldUpAndDown) {
		this.goldUpAndDown = goldUpAndDown;
	}

	public Double getSilverHighPrice() {
		return silverHighPrice;
	}

	public void setSilverHighPrice(Double silverHighPrice) {
		this.silverHighPrice = silverHighPrice;
	}

	public Double getSilverLowPrice() {
		return silverLowPrice;
	}

	public void setSilverLowPrice(Double silverLowPrice) {
		this.silverLowPrice = silverLowPrice;
	}

	public Double getSilverAmp() {
		return silverAmp;
	}

	public void setSilverAmp(Double silverAmp) {
		this.silverAmp = silverAmp;
	}

	public String getSilverUpAndDown() {
		return silverUpAndDown;
	}

	public void setSilverUpAndDown(String silverUpAndDown) {
		this.silverUpAndDown = silverUpAndDown;
	}

	public Long getAdvisoryCustomerCount() {
		return advisoryCustomerCount;
	}

	public void setAdvisoryCustomerCount(Long advisoryCustomerCount) {
		this.advisoryCustomerCount = advisoryCustomerCount;
	}

	public Long getAdvisoryCount() {
		return advisoryCount;
	}

	public void setAdvisoryCount(Long advisoryCount) {
		this.advisoryCount = advisoryCount;
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

	public Double getGoldNetPositionsVolume() {
		return goldNetPositionsVolume;
	}

	public void setGoldNetPositionsVolume(Double goldNetPositionsVolume) {
		this.goldNetPositionsVolume = goldNetPositionsVolume;
	}

	public Double getSilverNetPositionsVolume() {
		return silverNetPositionsVolume;
	}

	public void setSilverNetPositionsVolume(Double silverNetPositionsVolume) {
		this.silverNetPositionsVolume = silverNetPositionsVolume;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public Long getCustomerPhoneCount() {
		return customerPhoneCount;
	}

	public void setCustomerPhoneCount(Long customerPhoneCount) {
		this.customerPhoneCount = customerPhoneCount;
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

	public Double getBonusOtherMt5() {
		return bonusOtherMt5;
	}

	public void setBonusOtherMt5(Double bonusOtherMt5) {
		this.bonusOtherMt5 = bonusOtherMt5;
	}

	public Double getBonusOtherGts() {
		return bonusOtherGts;
	}

	public void setBonusOtherGts(Double bonusOtherGts) {
		this.bonusOtherGts = bonusOtherGts;
	}
	
}
