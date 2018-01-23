$(function() {
	
});

var dealcateSilverStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealcateSilverStatisticsDiv").show();
		}else{
			$("div.dealcateSilverStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealcateSilverStatisticsChart.showOrHideLoadingDiv(true);		
		// 获取表格数据
		var rows = $('#' + dealcateSilverStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var mt4volumeaxucnhArray = new Array();
		var gts2volumeaxucnhArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;				
				mt4volumeaxucnhArray[j] = rows[i].mt4volumeaxucnh;
				gts2volumeaxucnhArray[j] = rows[i].gts2volumeaxucnh;
				j--;
			}			
		}
		// 显示图表
		dealcateSilverStatisticsChart.showOrHideLoadingDiv(false);
		dealcateSilverStatisticsChart.showChart(exectimeArray,mt4volumeaxucnhArray,gts2volumeaxucnhArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4volumeaxucnhArray,gts2volumeaxucnhArray){
        var myChart = echarts.init(document.getElementById('dealcateSilverStatisticsChartContainer'), 'shine');      
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
    		            saveAsImage: {show: true,name:"人民币金平仓手数分布图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4人民币金平仓手数','GTS2人民币金平仓手数']
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
    		            name: '人民币金平仓手数分布 ',
    		            min: 0,
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series: [
 				        {
 				            name:'MT4人民币金平仓手数',
 				            type:'bar',
 				            data:mt4volumeaxucnhArray
 				        },
 				        {
 				            name:'GTS2人民币金平仓手数',
 				            type:'bar',
 				            data:gts2volumeaxucnhArray
 				        }
 				    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}

