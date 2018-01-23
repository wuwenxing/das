$(function() {
	
});

var p_2_15 = {
	/**
	 * 初始化
	 */
	init:function(){
		var url = BASE_PATH + 'tradeBordereauxController/findDealprofithourList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#dateTimeSearch").val();
		queryParams['endTime'] = $("#dateTimeSearch").val();
		queryParams['platformType'] = "GTS";
		queryParams['sort'] = "exectime,hour";
		queryParams['order'] = "desc,desc";
		queryParams['detailed'] = "0";
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data :queryParams,
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_2_15', true);
		    },
			success : function(rows) {
		    	common.addOrRemoveLoading('p_2_15', false);
				var exectimeArray = new Array();
				var companyprofitArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime+" "+rows[i].hour;
						companyprofitArray[j] = rows[i].companyprofit;
						j--;
					}
				}
				p_2_15.showChart(exectimeArray, companyprofitArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, companyprofitArray){
        var myChart = echarts.init(document.getElementById('dealprofithourStatisticsChartContainer_2_15'), 'shine');
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
		            saveAsImage: {show: true,name:"GTS净盈亏图"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['GTS净盈亏图']
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
		            name: '盈亏 ',
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'GTS净盈亏图',
		            type:'bar',
		            smooth: false,
		            data:companyprofitArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}