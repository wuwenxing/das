$(function() {
	
});

var dasFlowStatisticsChart = {
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
		var tab = $('#dasFlowStatisticsChartTabs').tabs('getSelected');
		var type = $('#dasFlowStatisticsChartTabs').tabs('getTabIndex', tab);
		if (type == 1) {
			tab.panel('refresh', 'page/report/dasFlowStatisticsVisitChart.html');
		} else if (type == 2) {
			tab.panel('refresh', 'page/report/dasFlowStatisticsAdvisoryChart.html');
		} else if (type == 3) {
			tab.panel('refresh', 'page/report/dasFlowStatisticsAccountChart.html');
		}
	},
	/**
	 * 加载图表数据
	 */
	loadChartData : function() {
		var queryParams = {};
		queryParams['sort'] = "dataTime";
		queryParams['order'] = "asc";
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "DasFlowStatisticsController/findList",
			data : queryParams,
			success : function(rows) {
				if(null != rows){
					var dataTimeArray = new Array();
					var visitCountArray = new Array();
					var advisoryCountQQArray = new Array();
					var advisoryCountLIVE800Array = new Array();
					var demoCountArray = new Array();
					var realCountArray = new Array();
					var depositCountArray = new Array();
					for(var i=0; i<rows.length; i++){
						dataTimeArray[i] = rows[i].dataTime;
						visitCountArray[i] = rows[i].visitCount;
						advisoryCountQQArray[i] = rows[i].advisoryCountQQ;
						advisoryCountLIVE800Array[i] = rows[i].advisoryCountLIVE800;
						demoCountArray[i] = rows[i].demoCount;
						realCountArray[i] = rows[i].realCount;
						depositCountArray[i] = rows[i].depositCount;
					}
					
					// 显示图表
					dasFlowStatisticsChart.showOrHideLoadingDiv(1, false);
					dasFlowStatisticsChart.showChart1(dataTimeArray, visitCountArray);
					dasFlowStatisticsChart.showOrHideLoadingDiv(2, false);
					dasFlowStatisticsChart.showChart2(dataTimeArray, advisoryCountQQArray, advisoryCountLIVE800Array);
					dasFlowStatisticsChart.showOrHideLoadingDiv(3, false);
					dasFlowStatisticsChart.showChart3(dataTimeArray, demoCountArray, realCountArray, depositCountArray);
				}
			},
			error: function(data){
				common.error();
	        }
		});
		
	},
	/**
	 * 显示图表
	 */
	showChart1 : function (dataTimeArray, visitCountArray){
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
		            saveAsImage: {show: true,name:"官网访问统计"}
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
	showChart2 : function (dataTimeArray, advisoryCountQQArray, advisoryCountLIVE800Array){
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
		            saveAsImage: {show: true,name:"官网咨询统计"}
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
	showChart3 : function (dataTimeArray, demoCountArray, realCountArray, depositCountArray){
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
		            saveAsImage: {show: true,name:"官网开户入金统计"}
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




















