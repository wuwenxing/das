$(function() {
	
});

var companyequitymdepositStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.companyequitymdepositStatisticsDiv").show();
		}else{
			$("div.companyequitymdepositStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		companyequitymdepositStatisticsChart.showOrHideLoadingDiv(true);
		
		// 获取表格数据
		var rows = $('#' + companyequitymdepositStatistics.dataGridId).datagrid('getRows');
		var approveDateArray = new Array();
		var equityMdepositArray = new Array();
		if(!common.isBlank(rows)){
			var j = rows.length-1;
			for(var i=0; i<rows.length; i++){
				approveDateArray[j] = rows[i].exectime;
				equityMdepositArray[j] = rows[i].equitymdeposit;
				j--;
			}						
		}
		// 显示图表
		companyequitymdepositStatisticsChart.showOrHideLoadingDiv(false);
		companyequitymdepositStatisticsChart.showChart(approveDateArray, equityMdepositArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (approveDateArray, equityMdepositArray){
        var myChart = echarts.init(document.getElementById('companyequitymdepositstatisticsChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"净入金图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['当天净入金']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: approveDateArray
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '净入金 ',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'当天净入金',
		            type:'bar',
		            data:equityMdepositArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}

