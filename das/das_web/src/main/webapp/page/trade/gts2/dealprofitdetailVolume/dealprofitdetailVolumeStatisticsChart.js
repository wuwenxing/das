$(function() {
	
});

var dealprofitdetailVolumeStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealprofitdetailVolumeStatisticsDiv").show();
		}else{
			$("div.dealprofitdetailVolumeStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealprofitdetailVolumeStatisticsChart.showOrHideLoadingDiv(true);
		
		// 获取表格数据
		var rows = $('#' + dealprofitdetailVolumeStatistics.dataGridId).datagrid('getRows');
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
		// 显示图表
		dealprofitdetailVolumeStatisticsChart.showOrHideLoadingDiv(false);
		dealprofitdetailVolumeStatisticsChart.showChart(exectimeArray,volumeArray);
		
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,volumeArray){
        var myChart = echarts.init(document.getElementById('dealprofitdetailVolumeStatisticsChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"交易手数图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['交易手数']
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
		            name: '交易手数 ',
		            //min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'交易手数',
		            type:'bar',
		            data:volumeArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}

