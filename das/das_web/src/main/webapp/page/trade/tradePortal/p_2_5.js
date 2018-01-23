$(function() {
	
});

var p_2_5 = {
	/**
	 * 初始化
	 */
	init:function(){
		var url = BASE_PATH + 'tradeBordereauxController/findBusinessDailyreportList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").val();
		queryParams['endTime'] = $("#endTimeSearch").val();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['detailed'] = "0";
		queryParams['type'] = "business";
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data :queryParams,
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_2_5', true);
		    },
			success : function(rows) {
		    	common.addOrRemoveLoading('p_2_5', false);
				var exectimeArray = new Array();
				var balanceArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;
						balanceArray[j] = rows[i].balance;
						j--;
					}
				}
				p_2_5.showChart(exectimeArray, balanceArray);
				
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, balanceArray){
        var myChart = echarts.init(document.getElementById('dailyreportstatisticsChartContainer_5'), 'shine');
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
		            saveAsImage: {show: true,name:"结余图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['结余']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: exectimeArray,
		            axisLabel: {
		            	interval:0,
			            rotate: 90
		            }
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '结余 ',
		            position: 'left',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    grid: {
		    	left:'160px'
		    },
		    series: [
		        {
		            name:'结余',
		            type:'line',
		            smooth: false,
		            yAxisIndex: 0,
		            data:balanceArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}