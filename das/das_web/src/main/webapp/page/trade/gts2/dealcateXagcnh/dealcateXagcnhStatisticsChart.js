$(function() {
	
});

var dealcateXagcnhStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealcateXagcnhStatisticsDiv").show();
		}else{
			$("div.dealcateXagcnhStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealcateXagcnhStatisticsChart.showOrHideLoadingDiv(true);		
		// 获取表格数据
		var rows = $('#' + dealcateXagcnhStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var mt4volumeaxgcnhArray = new Array();
		var gts2volumeaxgcnhArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;				
				mt4volumeaxgcnhArray[j] = rows[i].mt4volumeaxgcnh;
				gts2volumeaxgcnhArray[j] = rows[i].gts2volumeaxgcnh;
				j--;
			}			
		}
		// 显示图表
		dealcateXagcnhStatisticsChart.showOrHideLoadingDiv(false);
		dealcateXagcnhStatisticsChart.showChart(exectimeArray,mt4volumeaxgcnhArray,gts2volumeaxgcnhArray);		
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4volumeaxgcnhArray,gts2volumeaxgcnhArray){
        var myChart = echarts.init(document.getElementById('dealcateXagcnhStatisticsChartContainer'), 'shine');
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
    		            saveAsImage: {show: true,name:"人民币银平仓手数分布图表图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4人民币银平仓手数','GTS2人民币银平仓手数']
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
    		            name: '人民币银平仓手数分布 ',
    		            min: 0,
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series: [
 				        {
 				            name:'MT4人民币银平仓手数',
 				            type:'bar',
 				            data:mt4volumeaxgcnhArray
 				        },
 				        {
 				            name:'GTS2人民币银平仓手数',
 				            type:'bar',
 				            data:gts2volumeaxgcnhArray
 				        }
 				    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}

