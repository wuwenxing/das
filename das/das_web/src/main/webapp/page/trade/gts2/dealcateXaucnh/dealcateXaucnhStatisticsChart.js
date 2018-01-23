$(function() {
	
});

var dealcateXaucnhStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealcateXaucnhStatisticsDiv").show();
		}else{
			$("div.dealcateXaucnhStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealcateXaucnhStatisticsChart.showOrHideLoadingDiv(true);		
		// 获取表格数据
		var rows = $('#' + dealcateXaucnhStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var mt4volumeaxgArray = new Array();
		var gts2volumeaxgArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;				
				mt4volumeaxgArray[j] = rows[i].mt4volumeaxg;
				gts2volumeaxgArray[j] = rows[i].gts2volumeaxg;
				j--;
			}			
		}
		// 显示图表
		dealcateXaucnhStatisticsChart.showOrHideLoadingDiv(false);
		dealcateXaucnhStatisticsChart.showChart(exectimeArray,mt4volumeaxgArray,gts2volumeaxgArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4volumeaxgArray,gts2volumeaxgArray){
        var myChart = echarts.init(document.getElementById('dealcateXaucnhStatisticsChartContainer'), 'shine');        
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
    		            saveAsImage: {show: true,name:"伦敦银平仓手数分布图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4伦敦银平仓手数','GTS2伦敦银平仓手数']
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
    		            name: '伦敦银平仓手数分布 ',
    		            min: 0,
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series: [
 				        {
 				            name:'MT4伦敦银平仓手数',
 				            type:'bar',
 				            data:mt4volumeaxgArray
 				        },
 				        {
 				            name:'GTS2伦敦银平仓手数',
 				            type:'bar',
 				            data:gts2volumeaxgArray
 				        }
 				    ]
    		};    		    
        // 显示图表。
        myChart.setOption(option);
	}
}

