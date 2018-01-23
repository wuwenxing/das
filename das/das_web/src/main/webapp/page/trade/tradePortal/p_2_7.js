$(function() {

});

var p_2_7 = {
	/**
	 * 初始化
	 */
	init : function() {
		var url = BASE_PATH
				+ 'TradeController/cashandaccountdetailStatisticsYearsList';
		var queryParams = {};
		queryParams['startTime'] = $("#startTimeSearch").val();
		queryParams['endTime'] = $("#endTimeSearch").val();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data : queryParams,
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_2_7', true);
		    },
			success : function(rows) {
		    	common.addOrRemoveLoading('p_2_7', false);
				var approveDateArray = new Array();
				var equityMdepositArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for (var i = 0; i < rows.length; i++) {
						approveDateArray[j] = rows[i].exectime;
						equityMdepositArray[j] = rows[i].equitymdeposit;
						j--;
					}
				}
				p_2_7.showChart(approveDateArray, equityMdepositArray);
			},
			error : function(data) {
				common.error();
			}
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (approveDateArray, equityMdepositArray){
        var myChart = echarts.init(document.getElementById('chartContainer_2_7'), 'shine');
		var	option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['line','bar']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"净入金图表"}
		        }
		    },
		    legend: {
		        x : 'center',
		        data:['当天净入金']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: approveDateArray,
		            axisLabel: {
		            	interval:0,
			            rotate: 90
		            }
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '净入金 ',
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'净入金',
		            type:'line',
		            smooth: false,
		            data:equityMdepositArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}