$(function() {
	
});

var dealprofitdetailDealamountStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealprofitdetailDealamountStatisticsDiv").show();
		}else{
			$("div.dealprofitdetailDealamountStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealprofitdetailDealamountStatisticsChart.showOrHideLoadingDiv(true);
		
		// 获取表格数据
		var rows = $('#' + dealprofitdetailDealamountStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();		
		var mt4dealamountArray = new Array();
		var gts2dealamountArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime;
				mt4dealamountArray[j] = rows[i].mt4dealamount;
				gts2dealamountArray[j] = rows[i].gts2dealamount;
				j--;
			}			
		}
		// 显示图表
		dealprofitdetailDealamountStatisticsChart.showOrHideLoadingDiv(false);
		dealprofitdetailDealamountStatisticsChart.showChart(exectimeArray,mt4dealamountArray,gts2dealamountArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4dealamountArray,gts2dealamountArray){
        var myChart = echarts.init(document.getElementById('dealprofitdetailDealamountStatisticsChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"交易次数图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['MT4交易次数','GTS2交易次数']
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
		            name: '交易次数',
		            //min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'MT4交易次数',
		            type:'bar',
		            data:mt4dealamountArray
		        },
		        {
		            name:'GTS2交易次数',
		            type:'bar',
		            data:gts2dealamountArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}

