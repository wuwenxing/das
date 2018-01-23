$(function() {

});

/**
 * 交易记录-交易状况
 */
var p_3_1 = {
	/**
	 * 初始化
	 */
	init : function() {
		p_3_1.find();
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
		    	common.addOrRemoveLoading('p_3_1', true);
		    },
			success : function(data) {
		    	common.addOrRemoveLoading('p_3_1', false);
				if(data.length == 2){
					var days = data[0];
					var months = data[1];
					
					if(COMPANY_ID == '2'){//贵金属
						common.isAppendColor("grossprofit_days_3_1"
								, common.toDecimal(days.grossprofit - days.handOperHedgeProfitAndLossGts - days.handOperHedgeProfitAndLossMt4 - days.handOperHedgeProfitAndLossMt5));
						common.isAppendColor("grossprofit_months_3_1"
								, common.toDecimal(months.grossprofit - months.handOperHedgeProfitAndLossGts - months.handOperHedgeProfitAndLossMt4 - months.handOperHedgeProfitAndLossMt5 - months.bonusOtherGts - months.bonusOtherMt4 - months.bonusOtherMt5));
					}else{
						// 1、当日毛利需要减去手工录入的--手动对冲盈亏数据(20170925)
						// 2、当日毛利不需要减去手工录入的--活动推广赠金（其它）数据(20170925)
						common.isAppendColor("grossprofit_days_3_1"
								, common.toDecimal(days.grossprofit - days.handOperHedgeProfitAndLossGts2 - days.handOperHedgeProfitAndLossMt4));
						// 1、当月毛利需要减去手工录入的--手动对冲盈亏数据(20170925)
						// 2、当月毛利需要减去手工录入的--活动推广赠金（其它）数据，即公式 = 当月毛利 - 手动对冲盈亏 - 活动推广赠金（其它））(20170925)
						common.isAppendColor("grossprofit_months_3_1"
								, common.toDecimal(months.grossprofit - months.handOperHedgeProfitAndLossGts2 - months.handOperHedgeProfitAndLossMt4 - months.bonusOtherGts2 - months.bonusOtherMt4));
					}
					
					common.isAppendColor("companyprofit_days_3_1", days.companyprofit);
					common.isAppendColor("companyprofit_months_3_1", months.companyprofit);
					// 净盈亏
					common.isAppendColor("hyk_days_3_1", days.netCompanyprofit);
					common.isAppendColor("hyk_months_3_1", months.netCompanyprofit);
					
					common.isAppendColor("swap_days_3_1", days.swap);
					common.isAppendColor("swap_months_3_1", months.swap);
					common.isAppendColor("sysclearzero_days_3_1", days.sysclearzero);
					common.isAppendColor("sysclearzero_months_3_1", months.sysclearzero);
					common.isAppendColor("volume_days_3_1", days.volume);
					common.isAppendColor("volume_months_3_1", months.volume);
					common.isAppendColor("floatingprofit_days_3_1", days.floatingprofit);
					
					common.isAppendColor("goldprofit_days_3_1", days.goldprofit);
					common.isAppendColor("goldprofit_months_3_1", months.goldprofit);
					common.isAppendColor("silverprofit_days_3_1", days.silverprofit);
					common.isAppendColor("silverprofit_months_3_1", months.silverprofit);
					common.isAppendColor("xaucnhprofit_days_3_1", days.xaucnhprofit);
					common.isAppendColor("xaucnhprofit_months_3_1", months.xaucnhprofit);
					common.isAppendColor("xagcnhprofit_days_3_1", days.xagcnhprofit);
					common.isAppendColor("xagcnhprofit_months_3_1", months.xagcnhprofit);
					common.isAppendColor("goldvolume_days_3_1", days.goldvolume);
					common.isAppendColor("goldvolume_months_3_1", months.goldvolume);
					common.isAppendColor("silvervolume_days_3_1", days.silvervolume);
					common.isAppendColor("silvervolume_months_3_1", months.silvervolume);
					common.isAppendColor("xaucnhvolume_days_3_1", days.xaucnhvolume);
					common.isAppendColor("xaucnhvolume_months_3_1", months.xaucnhvolume);
					common.isAppendColor("xagcnhvolume_days_3_1", days.xagcnhvolume);
					common.isAppendColor("xagcnhvolume_months_3_1", months.xagcnhvolume);
					
					if(COMPANY_ID == '2'){//贵金属
						common.isAppendColor("handOperHedgeProfitAndLoss_days_3_1", common.toDecimal(days.handOperHedgeProfitAndLossGts + days.handOperHedgeProfitAndLossMt4 + days.handOperHedgeProfitAndLossMt5));
						common.isAppendColor("handOperHedgeProfitAndLoss_months_3_1", common.toDecimal(months.handOperHedgeProfitAndLossGts + months.handOperHedgeProfitAndLossMt4 + months.handOperHedgeProfitAndLossMt5));
					}else{
						// 当日/月手动对冲盈亏
						common.isAppendColor("handOperHedgeProfitAndLoss_days_3_1", common.toDecimal(days.handOperHedgeProfitAndLossGts2 + days.handOperHedgeProfitAndLossMt4));
						common.isAppendColor("handOperHedgeProfitAndLoss_months_3_1", common.toDecimal(months.handOperHedgeProfitAndLossGts2 + months.handOperHedgeProfitAndLossMt4));
					}
					
				}
			},
			error: function(data){
				common.error();
	        }
		});
	}
}