$(function() {
	
});

var dasFlowStatisticsTimeChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			if (type == 1) {
				$("div.visitChartLoadingDiv").show();
			} else if (type == 2) {
				$("div.advisoryChartLoadingDiv").show();
			} else if (type == 3) {
				$("div.accountChartLoadingDiv").show();
			}
		}else{
			if (type == 1) {
				$("div.visitChartLoadingDiv").hide();
			} else if (type == 2) {
				$("div.advisoryChartLoadingDiv").hide();
			} else if (type == 3) {
				$("div.accountChartLoadingDiv").hide();
			}
		}
	},
	/**
	 * 查询图表数据
	 */
	findChartData : function() {
		var tab = $('#dasFlowStatisticsTimeChartTabs').tabs('getSelected');
		var type = $('#dasFlowStatisticsTimeChartTabs').tabs('getTabIndex', tab);
		if (type == 1) {
			tab.panel('refresh', 'page/report/dasFlowStatisticsTimeVisitChart.html');
		} else if (type == 2) {
			tab.panel('refresh', 'page/report/dasFlowStatisticsTimeAdvisoryChart.html');
		} else if (type == 3) {
			tab.panel('refresh', 'page/report/dasFlowStatisticsTimeAccountChart.html');
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		dasFlowStatisticsTimeChart.showOrHideLoadingDiv(type, true);
		
		// 获取表格数据
		var rows = $('#' + dasFlowStatisticsTime.dataGridId).datagrid('getRows');
		var formatTimeArray = new Array();
		var utmcsrArray = new Array();
		var utmcmdArray = new Array();
		var visitCountArray = new Array();
		var advisoryCountQQArray = new Array();
		var advisoryCountLIVE800Array = new Array();
		var demoCountArray = new Array();
		var realCountArray = new Array();
		var depositCountArray = new Array();
		for(var i=0; i<rows.length; i++){
			formatTimeArray[i] = "(" + rows[i].utmcsr + "/" + rows[i].utmcmd + ")" + rows[i].formatTime ;
			utmcsrArray[i] = rows[i].utmcsr;
			utmcmdArray[i] = rows[i].utmcmd;
			visitCountArray[i] = rows[i].visitCount;
			advisoryCountQQArray[i] = rows[i].advisoryCountQQ;
			advisoryCountLIVE800Array[i] = rows[i].advisoryCountLIVE800;
			demoCountArray[i] = rows[i].demoCount;
			realCountArray[i] = rows[i].realCount;
			depositCountArray[i] = rows[i].depositCount;
		}

		// 显示图表
		dasFlowStatisticsTimeChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			dasFlowStatisticsTimeChart.showChart1(formatTimeArray, utmcsrArray, utmcmdArray, visitCountArray);
		} else if (type == 2) {
			dasFlowStatisticsTimeChart.showChart2(formatTimeArray, utmcsrArray, utmcmdArray, advisoryCountQQArray, advisoryCountLIVE800Array);
		} else if (type == 3) {
			dasFlowStatisticsTimeChart.showChart3(formatTimeArray, utmcsrArray, utmcmdArray, demoCountArray, realCountArray, depositCountArray);
		}
	},
	/**
	 * 显示图表
	 */
	showChart1 : function (dataTimeArray, utmcsrArray, utmcmdArray, visitCountArray){
		var myChart = echarts.init(document.getElementById('visitChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"官网时段访问统计"}
		        }
		    },
		    legend: {
		        data:['访问数']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: dataTimeArray
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '次数',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'访问数',
		            type:'line',
		            data:visitCountArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart2 : function (dataTimeArray, utmcsrArray, utmcmdArray, advisoryCountQQArray, advisoryCountLIVE800Array){
		var myChart = echarts.init(document.getElementById('advisoryChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"官网时段咨询统计"}
		        }
		    },
		    legend: {
		        data:['QQ咨询数','LIVE800咨询数']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: dataTimeArray
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '次数',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'QQ咨询数',
		            type:'line',
		            data:advisoryCountQQArray
		        },
		        {
		            name:'LIVE800咨询数',
		            type:'line',
		            data:advisoryCountLIVE800Array
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart3 : function (dataTimeArray, utmcsrArray, utmcmdArray, demoCountArray, realCountArray, depositCountArray){
		var myChart = echarts.init(document.getElementById('accountChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"官网时段开户入金统计"}
		        }
		    },
		    legend: {
		        data:['模拟开户数','真实开户数','首次入金数']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: dataTimeArray
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '个数',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'模拟开户数',
		            type:'line',
		            data:demoCountArray
		        },
		        {
		            name:'真实开户数',
		            type:'line',
		            data:realCountArray
		        },
		        {
		            name:'首次入金数',
		            type:'line',
		            data:depositCountArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}




















