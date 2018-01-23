$(function() {
	
});

var statisticsLoginChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			if (type == 1) {
				$("div.chartLoadingDiv_1").show();
			}
		}else{
			if (type == 1) {
				$("div.chartLoadingDiv_1").hide();
			}
		}
	},
	/**
	 * 查询图表数据
	 */
	findChartData : function() {
		var tab = $('#chartTabs').tabs('getSelected');
		var type = $('#chartTabs').tabs('getTabIndex', tab);
		if (type == 1) {
			tab.panel('refresh', 'page/room/statisticsLogin/statisticsLoginChart.html');
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		statisticsLoginChart.showOrHideLoadingDiv(type, true);
		
		// 获取表格数据
		var rows = $('#' + statisticsLogin.dataGridId).datagrid('getRows');
		var dateTimeArray = new Array();
		var vipNumbersArray = new Array();
		var realANumbersArray = new Array();
		var realNNumbersArray = new Array();
		var demoNumbersArray = new Array();
		var registNumbersArray = new Array();
		var touristNumbersArray = new Array();
		var analystNumbersArray = new Array();
		var adminNumbersArray = new Array();
		var cServiceNumbersArray = new Array();
		var countsArray = new Array();
		var reportType = $("#reportTypeSearch").combobox('getValue');
		for(var i=rows.length-1,j=0; i>=0; i--,j++){
			var dateTime = rows[i].dateTime;
			if(reportType != 'days'){
				dateTime = dateTime.substring(0, 7);
			}
			if(reportType == 'weeks'){
				dateTime += '第' + common.getWeekNumber(new Date(rows[i].dateTime)) + '周';
			}
			dateTimeArray[j] = dateTime;
			vipNumbersArray[j] = rows[i].vipNumbers;
			realANumbersArray[j] = rows[i].realANumbers;
			realNNumbersArray[j] = rows[i].realNNumbers;
			demoNumbersArray[j] = rows[i].demoNumbers;
			registNumbersArray[j] = rows[i].registNumbers;
			touristNumbersArray[j] = rows[i].touristNumbers;
			analystNumbersArray[j] = rows[i].analystNumbers;
			adminNumbersArray[j] = rows[i].adminNumbers;
			cServiceNumbersArray[j] = rows[i].cServiceNumbers;
			countsArray[j] = (rows[i].vipCounts + rows[i].realACounts + rows[i].realNCounts
			 + rows[i].demoCounts + rows[i].registCounts + rows[i].touristCounts
			 + rows[i].analystCounts + rows[i].adminCounts);
		}
		
		// 显示图表
		statisticsLoginChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			statisticsLoginChart.showChart1(dateTimeArray, vipNumbersArray, realANumbersArray, realNNumbersArray
					, demoNumbersArray, registNumbersArray, touristNumbersArray, analystNumbersArray
					, adminNumbersArray, cServiceNumbersArray, countsArray);
		}
	},
	/**
	 * 显示图表
	 */
	showChart1 : function (dateTimeArray, vipNumbersArray, realANumbersArray, realNNumbersArray
			, demoNumbersArray, registNumbersArray, touristNumbersArray, analystNumbersArray
			, adminNumbersArray, cServiceNumbersArray, countsArray){
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
		            saveAsImage: {show: true,name:"直播间登陆数据"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		    	data:['VIP用户','真实A用户','真实N用户','模拟用户','注册用户','游客','分析师','管理员','客服']
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
		            name:'VIP用户',
		            type:'line',
		            yAxisIndex: 0,
		            data:vipNumbersArray
		        },
		        {
		            name:'真实A用户',
		            type:'line',
		            yAxisIndex: 0,
		            data:realANumbersArray
		        },
		        {
		            name:'真实N用户',
		            type:'line',
		            yAxisIndex: 0,
		            data:realNNumbersArray
		        },
		        {
		            name:'模拟用户',
		            type:'line',
		            yAxisIndex: 0,
		            data:demoNumbersArray
		        },
		        {
		            name:'注册用户',
		            type:'line',
		            yAxisIndex: 0,
		            data:registNumbersArray
		        },
		        {
		            name:'游客',
		            type:'line',
		            yAxisIndex: 0,
		            data:touristNumbersArray
		        },
		        {
		            name:'分析师',
		            type:'line',
		            yAxisIndex: 0,
		            data:analystNumbersArray
		        },
		        {
		            name:'管理员',
		            type:'line',
		            yAxisIndex: 0,
		            data:adminNumbersArray
		        },
		        {
		            name:'客服',
		            type:'line',
		            yAxisIndex: 0,
		            data:cServiceNumbersArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}


















