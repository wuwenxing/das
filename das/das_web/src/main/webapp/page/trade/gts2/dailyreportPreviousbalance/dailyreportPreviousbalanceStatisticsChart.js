$(function() {
	
});

var dailyreportPreviousbalanceStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dailyreportPreviousbalanceStatisticsDiv").show();
		}else{
			$("div.dailyreportPreviousbalanceStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dailyreportPreviousbalanceStatisticsChart.showOrHideLoadingDiv(true);
		// 获取表格数据
		var rows = $('#' + dailyreportPreviousbalanceStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var mt4balanceArray = new Array();
		var gts2balanceArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;
				mt4balanceArray[j] = rows[i].mt4balance;
				gts2balanceArray[j] = rows[i].gts2balance;
				j--;
			}		
		}
		// 显示图表
		dailyreportPreviousbalanceStatisticsChart.showOrHideLoadingDiv(false);
		dailyreportPreviousbalanceStatisticsChart.showChart(exectimeArray, mt4balanceArray,gts2balanceArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, mt4balanceArray,gts2balanceArray){
        var myChart = echarts.init(document.getElementById('dailyreportPreviousbalanceStatisticsChartContainer'), 'shine');
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
    		        data:['MT4结余','GTS2结余']
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
    		        }
    		    ],
    		    series: [
    		        {
    		            name:'MT4结余',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:mt4balanceArray
    		        },
    		        {
    		            name:'GTS2结余',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:gts2balanceArray
    		        }
    		    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}

