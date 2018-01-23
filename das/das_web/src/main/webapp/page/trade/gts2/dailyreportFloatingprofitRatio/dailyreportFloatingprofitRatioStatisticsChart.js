$(function() {
	
});

var dailyreportFloatingprofitRatioStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dailyreportFloatingprofitRatioStatisticsDiv").show();
		}else{
			$("div.dailyreportFloatingprofitRatioStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dailyreportFloatingprofitRatioStatisticsChart.showOrHideLoadingDiv(true);
		// 获取表格数据
		var rows = $('#' + dailyreportFloatingprofitRatioStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var mt4floatingprofitRatioArray = new Array();
		var gts2floatingprofitRatioArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;
				var mt4floatingprofitRatio = rows[i].mt4floatingprofitRatio.replace("%","");
				mt4floatingprofitRatioArray[j] = parseFloat(mt4floatingprofitRatio);
				var gts2floatingprofitRatio = rows[i].gts2floatingprofitRatio.replace("%","");
				gts2floatingprofitRatioArray[j] = parseFloat(gts2floatingprofitRatio);
				j--;
			}		
		}
		// 显示图表
		dailyreportFloatingprofitRatioStatisticsChart.showOrHideLoadingDiv(false);
		dailyreportFloatingprofitRatioStatisticsChart.showChart(exectimeArray, mt4floatingprofitRatioArray,gts2floatingprofitRatioArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,  mt4floatingprofitRatioArray,gts2floatingprofitRatioArray){
        var myChart = echarts.init(document.getElementById('dailyreportFloatingprofitRatioStatisticsChartContainer'), 'shine');
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
    		            saveAsImage: {show: true,name:"浮动盈亏比例图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4浮动盈亏比例','GTS2浮动盈亏比例']
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
    		            name: '浮动盈亏比例 ',
    		            position: 'left',
    		            axisLabel: {
    		                formatter: '{value}%'
    		            }
    		        }
    		    ],
    		    series: [
    		        {
    		            name:'MT4浮动盈亏比例',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:mt4floatingprofitRatioArray
    		        },
    		        {
    		            name:'GTS2浮动盈亏比例',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:gts2floatingprofitRatioArray
    		        }
    		    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}

