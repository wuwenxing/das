$(function() {
	
});

var dailyreportStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dailyreportStatisticsDiv").show();
		}else{
			$("div.dailyreportStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dailyreportStatisticsChart.showOrHideLoadingDiv(true);
		
		// 获取表格数据
		var rows = $('#' + dailyreportStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var balanceArray = new Array();
		var floatingprofitArray = new Array();
		var marginArray = new Array();
		if(!common.isBlank(rows)){			
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;
				balanceArray[j] = rows[i].balance;
				floatingprofitArray[j] = rows[i].floatingprofit;
				marginArray[j] = rows[i].margin;
				j--;
			}						
		}
		// 显示图表
		dailyreportStatisticsChart.showOrHideLoadingDiv(false);
		dailyreportStatisticsChart.showChart(exectimeArray, balanceArray,floatingprofitArray,marginArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, balanceArray,floatingprofitArray,marginArray){
        var myChart = echarts.init(document.getElementById('dailyreportstatisticsChartContainer'), 'shine');
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
    		        data:['结余','浮动盈亏','保证金']
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
    		            name: '结余 ',
    		            position: 'left',
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        },
    		        {
    		            type: 'value',
    		            name: '浮动盈亏,保证金 ',
    		            position: 'right',
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series: [
    		        {
    		            name:'结余',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:balanceArray
    		        },
    		        {
    		            name:'浮动盈亏',
    		            type:'bar',
    		            yAxisIndex: 1,
    		            data:floatingprofitArray
    		        },
    		        {
    		            name:'保证金',
    		            type:'bar',
    		            yAxisIndex: 1,
    		            data:marginArray
    		        }
    		    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}

