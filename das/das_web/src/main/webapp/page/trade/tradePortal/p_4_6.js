$(function() {

});

/**
 * 存取款及账户-GTS
 */
var p_4_6 = {
	/**
	 * 初始化
	 */
	init : function() {
		p_4_6.find();
	},
	/**
	 * 查询
	 */
	find: function(){
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "TradeController/findDaysOrMonthsSumByCashandaccountdetail",
			data : {
				platformType : "GTS",dateTime:$("#dateTimeSearch").val()
			},
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_4_6', true);
		    },
			success : function(data) {
		    	common.addOrRemoveLoading('p_4_6', false);
				if(data.length == 2){
					var days = data[0];
					var months = data[1];
					common.isAppendColor("sumactday_days_4_6", days.sumactday);
					common.isAppendColor("sumactday_months_4_6", months.sumactday);
					common.isAppendColor("sumactall_days_4_6", days.sumactall);
					common.isAppendColor("sumopenall_months_4_6", months.sumopenall);
					common.isAppendColor("sumnewaccount_days_4_6", days.sumnewaccount);
					common.isAppendColor("sumnewaccount_months_4_6", months.sumnewaccount);
					common.isAppendColor("equitymdeposit_days_4_6", days.equitymdeposit);
					common.isAppendColor("equitymdeposit_months_4_6", months.equitymdeposit);
					common.isAppendColor("summdeposit_days_4_6", days.summdeposit);
					common.isAppendColor("summdeposit_months_4_6", months.summdeposit);
					common.isAppendColor("sumwithdraw_days_4_6", days.sumwithdraw);
					common.isAppendColor("sumwithdraw_months_4_6", months.sumwithdraw);
					common.isAppendColor("previousbalance_days_4_6", days.previousbalance);

					// 手工录入赠金
					common.isAppendColor("bonusOther_days_4_6", days.bonusOtherGts);
					common.isAppendColor("bonusOther_months_4_6", months.bonusOtherGts);
					// 后台算出-当日活动推广赠金
					common.isAppendColor("bonus_days_4_6", days.bonus);
					common.isAppendColor("bonus_months_4_6", months.bonus);
					// 当日累计活动推广赠金 = 当日活动推广赠金 + 手工录入赠金
					common.isAppendColor("bonusSum_days_4_6", common.toDecimal(days.bonus + days.bonusOtherGts));
					common.isAppendColor("bonusSum_months_4_6", common.toDecimal(months.bonus + months.bonusOtherGts));
				}
			},
			error: function(data){
				common.error();
	        }
		});
	}
}