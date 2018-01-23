$(function() {
	
});

var dailyreportMarginRatioStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dailyreportMarginRatioStatisticsDiv").show();
		}else{
			$("div.dailyreportMarginRatioStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dailyreportMarginRatioStatisticsChart.showOrHideLoadingDiv(true);
		// 获取表格数据
		var rows = $('#' + dailyreportMarginRatioStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var mt4marginRatioArray = new Array();
		var gts2marginRatioArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;
				var mt4marginRatio = rows[i].marginRatio.replace("%","");
				mt4marginRatioArray[j] = parseFloat(mt4marginRatio);
				var gts2marginRatio = rows[i].gts2marginRatio.replace("%","");
				gts2marginRatioArray[j] = parseFloat(gts2marginRatio);
				j--;
			}			
		}
		// 显示图表
		dailyreportMarginRatioStatisticsChart.showOrHideLoadingDiv(false);
		dailyreportMarginRatioStatisticsChart.showChart(exectimeArray, mt4marginRatioArray,gts2marginRatioArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, mt4marginRatioArray,gts2marginRatioArray){
        var myChart = echarts.init(document.getElementById('dailyreportMarginRatiostatisticsChartContainer'), 'shine');
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
    		            saveAsImage: {show: true,name:"保证金比例图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4保证金比例','GTS2保证金比例']
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
    		            name: '保证金比例 ',
    		            position: 'left',
    		            axisLabel: {
    		                formatter: '{value}%'
    		            }
    		        }
    		    ],
    		    series: [
    		        {
    		            name:'MT4保证金比例',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:mt4marginRatioArray
    		        },
    		        {
    		            name:'GTS2保证金比例',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:gts2marginRatioArray
    		        }
    		    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}

