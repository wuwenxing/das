$(function() {
	
});

var dealprofitdetailGrossprofitStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealprofitdetailGrossprofitStatisticsDiv").show();
		}else{
			$("div.dealprofitdetailGrossprofitStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealprofitdetailGrossprofitStatisticsChart.showOrHideLoadingDiv(true);
		
		// 获取表格数据
		var rows = $('#' + dealprofitdetailGrossprofitStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var grossprofitArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;
				grossprofitArray[j] = rows[i].grossprofit;
				j--;
			}
		}
		// 显示图表
		dealprofitdetailGrossprofitStatisticsChart.showOrHideLoadingDiv(false);
		dealprofitdetailGrossprofitStatisticsChart.showChart(exectimeArray,grossprofitArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,grossprofitArray){
        var myChart = echarts.init(document.getElementById('dealprofitdetailGrossprofitStatisticsChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"毛利图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['毛利']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: exectimeArray
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '毛利 ',
		            //min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'毛利',
		            type:'bar',
		            data:grossprofitArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}

