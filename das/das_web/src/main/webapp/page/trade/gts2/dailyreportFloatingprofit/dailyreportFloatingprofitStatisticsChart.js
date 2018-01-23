$(function() {
	
});

var dailyreportFloatingprofitStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dailyreportFloatingprofitStatisticsDiv").show();
		}else{
			$("div.dailyreportFloatingprofitStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dailyreportFloatingprofitStatisticsChart.showOrHideLoadingDiv(true);	
		// 获取表格数据
		var rows = $('#' + dailyreportFloatingprofitStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var mt4floatingprofitArray = new Array();
		var gts2floatingprofitArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;
				mt4floatingprofitArray[j] = parseFloat(rows[i].mt4floatingprofit);
				gts2floatingprofitArray[j] = parseFloat(rows[i].gts2floatingprofit);
				j--;
			}			
		}
		// 显示图表
		dailyreportFloatingprofitStatisticsChart.showOrHideLoadingDiv(false);
		dailyreportFloatingprofitStatisticsChart.showChart(exectimeArray,mt4floatingprofitArray,gts2floatingprofitArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4floatingprofitArray,gts2floatingprofitArray){
        var myChart = echarts.init(document.getElementById('dailyreportFloatingprofitStatisticsChartContainer'), 'shine');
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
    		            saveAsImage: {show: true,name:"浮动盈亏图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4浮动盈亏','GTS2浮动盈亏']
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
    		            name: '浮动盈亏 ',
    		            position: 'left',
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series: [
    		        {
    		            name:'MT4浮动盈亏',
    		            type:'bar',
    		            data:mt4floatingprofitArray
    		        },
    		        {
    		            name:'GTS2浮动盈亏',
    		            type:'bar',
    		            data:gts2floatingprofitArray
    		        }
    		    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}

