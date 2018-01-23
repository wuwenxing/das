$(function() {
	
});

var dasFlowStatisticsMediaChart = {
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
		var tab = $('#dasFlowStatisticsMediaChartTabs').tabs('getSelected');
		var type = $('#dasFlowStatisticsMediaChartTabs').tabs('getTabIndex', tab);
		if (type == 1) {
			tab.panel('refresh', 'page/report/dasFlowStatisticsMediaVisitChart.html');
		} else if (type == 2) {
			tab.panel('refresh', 'page/report/dasFlowStatisticsMediaAdvisoryChart.html');
		} else if (type == 3) {
			tab.panel('refresh', 'page/report/dasFlowStatisticsMediaAccountChart.html');
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		
		// 获取表格数据
		var rows = $('#' + dasFlowStatisticsMedia.dataGridId).datagrid('getRows');
		var dataTimeArray = new Array();
		var utmcsrArray = new Array();
		var utmcmdArray = new Array();
		var visitCountArray = new Array();
		var advisoryCountQQArray = new Array();
		var advisoryCountLIVE800Array = new Array();
		var demoCountArray = new Array();
		var realCountArray = new Array();
		var depositCountArray = new Array();
		for(var i=0; i<rows.length; i++){
			dataTimeArray[i] = "(" + rows[i].utmcsr + "/" + rows[i].utmcmd + ")" + rows[i].dataTime ;
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
		dasFlowStatisticsMediaChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			dasFlowStatisticsMediaChart.showChart1(dataTimeArray, utmcsrArray, utmcmdArray, visitCountArray);
		} else if (type == 2) {
			dasFlowStatisticsMediaChart.showChart2(dataTimeArray, utmcsrArray, utmcmdArray, advisoryCountQQArray, advisoryCountLIVE800Array);
		} else if (type == 3) {
			dasFlowStatisticsMediaChart.showChart3(dataTimeArray, utmcsrArray, utmcmdArray, demoCountArray, realCountArray, depositCountArray);
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
		            saveAsImage: {show: true,name:"来源媒介访问统计"}
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
		            saveAsImage: {show: true,name:"来源媒介咨询统计"}
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
		            saveAsImage: {show: true,name:"来源媒介开户入金统计"}
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




















