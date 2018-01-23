$(function() {
	
});

var dealcateGoldStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealcateGoldStatisticsDiv").show();
		}else{
			$("div.dealcateGoldStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealcateGoldStatisticsChart.showOrHideLoadingDiv(true);
		// 获取表格数据
		var rows = $('#' + dealcateGoldStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var mt4volumeaxuArray = new Array();
		var gts2volumeaxuArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;				
				mt4volumeaxuArray[j] = rows[i].mt4volumeaxu;
				gts2volumeaxuArray[j] = rows[i].gts2volumeaxu;
				j--;
			}			
		}
		// 显示图表
		dealcateGoldStatisticsChart.showOrHideLoadingDiv(false);
		dealcateGoldStatisticsChart.showChart(exectimeArray,mt4volumeaxuArray,gts2volumeaxuArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4volumeaxuArray,gts2volumeaxuArray){
        var myChart = echarts.init(document.getElementById('dealcateGoldStatisticsChartContainer'), 'shine');
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
    		            saveAsImage: {show: true,name:"伦敦金平仓手数分布图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4伦敦金平仓手数','GTS2伦敦金平仓手数']
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
    		            name: '伦敦金平仓手数分布 ',
    		            min: 0,
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series: [
 				        {
 				            name:'MT4伦敦金平仓手数',
 				            type:'bar',
 				            data:mt4volumeaxuArray
 				        },
 				        {
 				            name:'GTS2伦敦金平仓手数',
 				            type:'bar',
 				            data:gts2volumeaxuArray
 				        }
 				    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}

