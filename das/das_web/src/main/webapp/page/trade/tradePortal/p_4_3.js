$(function() {

});

/**
 * 存取款及账户-MT4
 */
var p_4_3 = {
	/**
	 * 初始化
	 */
	init : function() {
		p_4_3.find();
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
				platformType : "MT4",dateTime:$("#dateTimeSearch").val()
			},
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_4_3', true);
		    },
			success : function(data) {
		    	common.addOrRemoveLoading('p_4_3', false);
				if(data.length == 2){
					var days = data[0];
					var months = data[1];
					common.isAppendColor("sumactday_days_4_3", days.sumactday);
					common.isAppendColor("sumactday_months_4_3", months.sumactday);
					common.isAppendColor("sumactall_days_4_3", days.sumactall);
					common.isAppendColor("sumopenall_months_4_3", months.sumopenall);
					common.isAppendColor("sumnewaccount_days_4_3", days.sumnewaccount);
					common.isAppendColor("sumnewaccount_months_4_3", months.sumnewaccount);
					common.isAppendColor("equitymdeposit_days_4_3", days.equitymdeposit);
					common.isAppendColor("equitymdeposit_months_4_3", months.equitymdeposit);
					common.isAppendColor("summdeposit_days_4_3", days.summdeposit);
					common.isAppendColor("summdeposit_months_4_3", months.summdeposit);
					common.isAppendColor("sumwithdraw_days_4_3", days.sumwithdraw);
					common.isAppendColor("sumwithdraw_months_4_3", months.sumwithdraw);
					common.isAppendColor("previousbalance_days_4_3", days.previousbalance);

					// 手工录入赠金
					common.isAppendColor("bonusOther_days_4_3", days.bonusOtherMt4);
					common.isAppendColor("bonusOther_months_4_3", months.bonusOtherMt4);
					// 后台算出-当日活动推广赠金
					common.isAppendColor("bonus_days_4_3", days.bonus);
					common.isAppendColor("bonus_months_4_3", months.bonus);
					// 当日累计活动推广赠金 = 当日活动推广赠金 + 手工录入赠金
					common.isAppendColor("bonusSum_days_4_3", common.toDecimal(days.bonus + days.bonusOtherMt4));
					common.isAppendColor("bonusSum_months_4_3", common.toDecimal(months.bonus + months.bonusOtherMt4));
				}
			},
			error: function(data){
				common.error();
	        }
		});
	}
}