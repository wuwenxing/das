$(function() {
	
});

var p_2_6 = {
	/**
	 * 初始化
	 */
	init:function(){
		var url = BASE_PATH + 'tradeBordereauxController/findBusinessDealprofitdetailList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").val();
		queryParams['endTime'] = $("#endTimeSearch").val();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data :queryParams,
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_2_6', true);
		    },
			success : function(rows) {
		    	common.addOrRemoveLoading('p_2_6', false);
				var exectimeArray = new Array();
				var volumeArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;
						volumeArray[j] = rows[i].volume;
						j--;
					}
				}
				p_2_6.showChart(exectimeArray,volumeArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,volumeArray){
        var myChart = echarts.init(document.getElementById('dealprofitdetailvolumeStatisticsChartContainer_6'), 'shine');
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
		            saveAsImage: {show: true,name:"交易手數图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['交易手數']
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
		            name: '交易手數 ',
		            //min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'交易手數',
		            type:'line',
		            smooth: false,
		            data:volumeArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}