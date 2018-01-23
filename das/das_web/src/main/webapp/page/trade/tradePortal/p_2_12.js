$(function() {
	
});

var p_2_12 = {
	/**
	 * 初始化
	 */
	init:function(){
		var url = BASE_PATH + 'TradeIndexController/dateIntervalList';
		var queryParams = {
		};
		queryParams['startDate'] = $("#startTimeSearch").val();
		queryParams['endDate'] = $("#endTimeSearch").val();
		queryParams['sort'] = "dateTime";
		queryParams['order'] = "asc";
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data :queryParams,
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_2_12', true);
		    },
			success : function(rows) {
		    	common.addOrRemoveLoading('p_2_12', false);
				var dateTimeArray = new Array();
				var goldNetPositionsVolumeArray = new Array();
				var silverNetPositionsVolumeArray = new Array();
				for(var i=0; i<rows.length; i++){
					dateTimeArray[i] = rows[i].dateTime;
					goldNetPositionsVolumeArray[i] = rows[i].goldNetPositionsVolume;
					silverNetPositionsVolumeArray[i] = rows[i].silverNetPositionsVolume;
				}
				p_2_12.showChart(dateTimeArray, goldNetPositionsVolumeArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (dateTimeArray, goldNetPositionsVolumeArray){
        var myChart = echarts.init(document.getElementById('tradePortalContainer_2_12'), 'shine');
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
		            saveAsImage: {show: true,name:"黄金净仓手数"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['黄金净仓手数']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: dateTimeArray,
		            axisLabel: {
		            	interval:0,
			            rotate: 90
		            }
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '手数 ',
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'黄金净仓手数',
		            type:'line',
		            smooth: false,
		            data:goldNetPositionsVolumeArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}