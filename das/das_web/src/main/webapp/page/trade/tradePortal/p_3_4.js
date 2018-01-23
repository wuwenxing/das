$(function() {

});

/**
 * 交易记录-市场状况
 */
var p_3_4 = {
	/**
	 * 初始化
	 */
	init : function() {
		p_3_4.find();
	},
	/**
	 * 查询
	 */
	find: function(){
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "TradeController/findDaysOrMonthsSumByProfitdetail",
			data : {dateTime:$("#dateTimeSearch").val()
			},
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_3_4', true);
		    },
			success : function(data) {
		    	common.addOrRemoveLoading('p_3_4', false);
				if(data.length == 2){
					var days = data[0];
					var months = data[1];
					
					// 当日/月-活动推广赠金(其它)(20170925)
					common.isAppendColor("bonusOther_days_3_4", common.toDecimal(days.bonusOtherGts2 + + days.bonusOtherMt4));
					common.isAppendColor("bonusOther_months_3_4", common.toDecimal(months.bonusOtherGts2 + + months.bonusOtherMt4));
					
					// 当日/月-活动推广赠金
					common.isAppendColor("bonus_days_3_4", days.bonus);
					common.isAppendColor("bonus_months_3_4", months.bonus);
					
					// 当日/月-累计活动推广赠金=“活动推广赠金”+“活动推广赠金（其它）”(20170925)
					common.isAppendColor("bonusSum_days_3_4", common.toDecimal(days.bonus + days.bonusOtherGts2 + + days.bonusOtherMt4));
					common.isAppendColor("bonusSum_months_3_4", common.toDecimal(months.bonus + months.bonusOtherGts2 + + months.bonusOtherMt4));
					
					common.isAppendColor("bonusclear_days_3_4", days.bonusclear);
					common.isAppendColor("bonusclear_months_3_4", months.bonusclear);
					common.isAppendColor("adjustfixedamount_days_3_4", days.adjustfixedamount);
					common.isAppendColor("adjustfixedamount_months_3_4", months.adjustfixedamount);
					common.isAppendColor("commission_days_3_4", days.commission);
					common.isAppendColor("commission_months_3_4", months.commission);
					
					// 当日/月网站总访问量、当日/月渠道推广费用
					common.isAppendColor("siteVisitsCount_days_3_4", days.siteVisitsCount);
					common.isAppendColor("siteVisitsCount_months_3_4", months.siteVisitsCount);
					common.isAppendColor("channelPromotionCosts_days_3_4", days.channelPromotionCosts);
					common.isAppendColor("channelPromotionCosts_months_3_4", months.channelPromotionCosts);
					
				}
			},
			error: function(data){
				common.error();
	        }
		});
	}
}