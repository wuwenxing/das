$(function() {
	
});

var dasChartRoomStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			if (type == 1) {
				$("div.chartLoadingDiv_1").show();
			} else if (type == 2) {
				$("div.chartLoadingDiv_2").show();
			}
		}else{
			if (type == 1) {
				$("div.chartLoadingDiv_1").hide();
			} else if (type == 2) {
				$("div.chartLoadingDiv_2").hide();
			}
		}
	},
	/**
	 * 查询图表数据
	 */
	findChartData : function() {
		var tab = $('#dasChartRoomStatisticsChartTabs').tabs('getSelected');
		var type = $('#dasChartRoomStatisticsChartTabs').tabs('getTabIndex', tab);
		if (type == 1) {
			tab.panel('refresh', 'page/room/dasChartRoomStatistics/statisticsNumChart.html');
		} else if (type == 2) {
			tab.panel('refresh', 'page/room/dasChartRoomStatistics/statisticsCouChart.html');
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		dasChartRoomStatisticsChart.showOrHideLoadingDiv(type, true);
		
		// 获取表格数据
		var rows = $('#' + dasChartRoomStatistics.dataGridId).datagrid('getRows');
		var dateTimeArray = new Array();
		var publicSpeakCountArray = new Array();
		var publicSpeakNumberArray = new Array();
		var privateSpeakCountArray = new Array();
		var privateSpeakNumberArray = new Array();
		var visitCountArray = new Array();
		var visitNumberArray = new Array();
		var loginCountArray = new Array();
		var loginNumberArray = new Array();
		var reportType = $("#reportTypeSearch").combobox('getValue');
		for(var i=rows.length-1,j=0; i>=0; i--,j++){
			var dateTime = rows[i].dateTime;
			if(reportType == 'hours'){
				dateTime += " " + rows[i].hours + "时";
			}else if(reportType == 'weeks'){
				dateTime += "第" + rows[i].weeks + "周";
			}
			dateTimeArray[j] = dateTime;
			publicSpeakCountArray[j] = rows[i].publicSpeakCount;
			publicSpeakNumberArray[j] = rows[i].publicSpeakNumber;
			privateSpeakCountArray[j] = rows[i].privateSpeakCount;
			privateSpeakNumberArray[j] = rows[i].privateSpeakNumber;
			visitCountArray[j] = rows[i].visitCount;
			visitNumberArray[j] = rows[i].visitNumber;
			loginCountArray[j] = rows[i].loginCount;
			loginNumberArray[j] = rows[i].loginNumber;
		}
		
		// 显示图表
		dasChartRoomStatisticsChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			dasChartRoomStatisticsChart.showChart1(dateTimeArray, publicSpeakNumberArray, privateSpeakNumberArray, visitNumberArray, loginNumberArray);
		} else if (type == 2) {
			dasChartRoomStatisticsChart.showChart2(dateTimeArray, publicSpeakCountArray, privateSpeakCountArray, visitCountArray, loginCountArray);
		}
	},
	/**
	 * 显示图表
	 */
	showChart1 : function (dateTimeArray, publicSpeakNumberArray, privateSpeakNumberArray, visitNumberArray, loginNumberArray){
        var myChart = echarts.init(document.getElementById('chartContainer_1'), 'shine');
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
		            saveAsImage: {show: true,name:"直播间行为统计人数"}
		        }
		    },
		    legend: {
		        data:['公聊发言人数','私聊发言人数','访问人数','登录人数']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: dateTimeArray
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '人数',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'公聊发言人数',
		            type:'line',
		            data:publicSpeakNumberArray
		        },
		        {
		            name:'私聊发言人数',
		            type:'line',
		            data:privateSpeakNumberArray
		        },
		        {
		            name:'访问人数',
		            type:'line',
		            data:visitNumberArray
		        },
		        {
		            name:'登录人数',
		            type:'line',
		            data:loginNumberArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart2 : function (dateTimeArray, publicSpeakCountArray, privateSpeakCountArray, visitCountArray, loginCountArray){
		var myChart = echarts.init(document.getElementById('chartContainer_2'), 'shine');
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
		            saveAsImage: {show: true,name:"直播间次数统计人数"}
		        }
		    },
		    legend: {
		        data:['公聊发言次数','私聊发言次数','访问次数','登录次数']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: dateTimeArray
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
		            name:'公聊发言次数',
		            type:'line',
		            data:publicSpeakCountArray
		        },
		        {
		            name:'私聊发言次数',
		            type:'line',
		            data:privateSpeakCountArray
		        },
		        {
		            name:'访问次数',
		            type:'line',
		            data:visitCountArray
		        },
		        {
		            name:'登录次数',
		            type:'line',
		            data:loginCountArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}




