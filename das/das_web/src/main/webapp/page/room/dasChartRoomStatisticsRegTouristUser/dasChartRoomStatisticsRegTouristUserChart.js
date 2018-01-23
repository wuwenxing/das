$(function() {
	
});

var dasChartRoomStatisticsRegTouristUserChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			if (type == 1) {
				$("div.statisticsRegTouristUserChartLoadingDiv").show();
			}
		}else{
			if (type == 1) {
				$("div.statisticsRegTouristUserChartLoadingDiv").hide();
			}
		}
	},
	/**
	 * 查询图表数据
	 */
	findChartData : function() {
		var tab = $('#dasChartRoomStatisticsRegTouristUserChartTabs').tabs('getSelected');
		var type = $('#dasChartRoomStatisticsRegTouristUserChartTabs').tabs('getTabIndex', tab);
		if (type == 1) {
			tab.panel('refresh', 'page/room/dasChartRoomStatisticsRegTouristUser/statisticsRegTouristUserCouChart.html');
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		dasChartRoomStatisticsRegTouristUserChart.showOrHideLoadingDiv(type, true);
		
		// 获取表格数据
		var rows = $('#' + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('getRows');
		var dateTimeArray = new Array();
		var courseNameArray = new Array();
		var vipNumbersArray = new Array();
		var realANumbersArray = new Array();
		var realNNumbersArray = new Array();
		var demoNumbersArray = new Array();
		var registNumbersArray = new Array();
		var touristNumbersArray = new Array();
		var tempFlag = $("#courseNameChecked").val() == 'true';
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
			courseNameArray[j] = rows[i].courseName;
			vipNumbersArray[j] = rows[i].vipNumbers;
			realANumbersArray[j] = rows[i].realANumbers;
			realNNumbersArray[j] = rows[i].realNNumbers;
			demoNumbersArray[j] = rows[i].demoNumbers;
			registNumbersArray[j] = rows[i].registNumbers;
			touristNumbersArray[j] = rows[i].touristNumbers;
		}
		
		// 显示图表
		dasChartRoomStatisticsRegTouristUserChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			dasChartRoomStatisticsRegTouristUserChart.showChart1(dateTimeArray, courseNameArray, vipNumbersArray, realANumbersArray, 
					realNNumbersArray, demoNumbersArray,registNumbersArray,touristNumbersArray);
		}
	},
	/**
	 * 显示图表
	 */
	showChart1 : function (dateTimeArray, courseNameArray, vipNumbersArray, realANumbersArray, 
			realNNumbersArray, demoNumbersArray,registNumbersArray,touristNumbersArray){
        var myChart = echarts.init(document.getElementById('statisticsRegTouristUserChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"直播间注册用户"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['VIP用户人数','真实A用户人数','真实N用户人数','模拟用户人数','手机注册人数','游客人数']
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
		            name:'VIP用户人数',
		            type:'line',
		            data:vipNumbersArray
		        },
		        {
		            name:'真实A用户人数',
		            type:'line',
		            data:realANumbersArray
		        },
		        {
		            name:'真实N用户人数',
		            type:'line',
		            data:realNNumbersArray
		        },
		        {
		            name:'模拟用户人数',
		            type:'line',
		            data:demoNumbersArray
		        },
		        {
		            name:'手机注册人数',
		            type:'line',
		            data:registNumbersArray
		        },
		        {
		            name:'游客人数',
		            type:'line',
		            data:touristNumbersArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}


















