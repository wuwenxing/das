$(function() {
	
});

var dealprofithourStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealprofithourStatisticsDiv").show();
		}else{
			$("div.dealprofithourStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealprofithourStatisticsChart.showOrHideLoadingDiv(true);
		
		// 获取表格数据
		var rows = $('#' + dealprofithourStatistics.dataGridId).datagrid('getRows');
		var exectimeArray = new Array();
		var companyprofitArray = new Array();
		if(!common.isBlank(rows)){			
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				exectimeArray[j] = rows[i].exectime+" "+rows[i].hour;
				companyprofitArray[j] = rows[i].companyprofit;
				j--;
			}						
		}
		// 显示图表
		dealprofithourStatisticsChart.showOrHideLoadingDiv(false);
		dealprofithourStatisticsChart.showChart(exectimeArray, companyprofitArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, companyprofitArray){
        var myChart = echarts.init(document.getElementById('dealprofithourStatisticsChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"公司盈亏图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['公司盈亏']
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
		            name: '盈亏 ',
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'公司盈亏',
		            type:'bar',
		            data:companyprofitArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}

