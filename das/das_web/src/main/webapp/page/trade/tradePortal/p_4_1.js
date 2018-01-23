$(function() {

});

/**
 * 存取款及账户-客户状况
 */
var p_4_1 = {
	/**
	 * 初始化
	 */
	init : function() {
		p_4_1.find();
	},
	/**
	 * 查询
	 */
	find: function(){
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "TradeController/findDaysOrMonthsSumByCashandaccountdetail",
			data : {dateTime:$("#dateTimeSearch").val()
			},
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_4_1', true);
		    },
			success : function(data) {
		    	common.addOrRemoveLoading('p_4_1', false);
				if(data.length == 2){
					var days = data[0];
					var months = data[1];
					common.isAppendColor("previousbalance_days_4_1", days.previousbalance);
					common.isAppendColor("equitymdeposit_days_4_1", days.equitymdeposit);
					common.isAppendColor("equitymdeposit_months_4_1", months.equitymdeposit);
					common.isAppendColor("summdeposit_days_4_1", days.summdeposit);
					common.isAppendColor("summdeposit_months_4_1", months.summdeposit);
					common.isAppendColor("sumwithdraw_days_4_1", days.sumwithdraw);
					common.isAppendColor("sumwithdraw_months_4_1", months.sumwithdraw);
					common.isAppendColor("sumnewaccount_days_4_1", days.sumnewaccount);
					common.isAppendColor("sumnewaccount_months_4_1", months.sumnewaccount);
					common.isAppendColor("sumactday_days_4_1", days.sumactday);
					common.isAppendColor("sumactday_months_4_1", months.sumactday);
					common.isAppendColor("sumactall_days_4_1", days.sumactall);
					
					// 当日/月客户对话量、当日/月对话总数
					common.isAppendColor("advisoryCustomerCount_days_4_1", days.advisoryCustomerCount);
					common.isAppendColor("advisoryCustomerCount_months_4_1", months.advisoryCustomerCount);
					common.isAppendColor("advisoryCount_days_4_1", days.advisoryCount);
					common.isAppendColor("advisoryCount_months_4_1", months.advisoryCount);
				}
			},
			error: function(data){
				common.error();
	        }
		});
	}
}