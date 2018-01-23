$(function() {
	
});

var dasChartRoomStatisticsSourceCountChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			if (type == 1) {
				$("div.accountChartLoadingDiv").show();
			} else if (type == 2) {
				$("div.devicecountChartLoadingDiv").show();
			}
		}else{
			if (type == 1) {
				$("div.accountChartLoadingDiv").hide();
			} else if (type == 2) {
				$("div.devicecountChartLoadingDiv").hide();
			}
		}
	},

	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		
		// 获取表格数据
		var rows = $('#' + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('getRows');
		var dataTimeArray = new Array();
		var demoCountArray = new Array();
		var realCountArray = new Array();
		var depositCountArray = new Array();
		
		var devicecountCountArray = new Array();
		var advisoryCountLIVE800Array = new Array();
		
		for(var i=0; i<rows.length; i++){
			dataTimeArray[i] = "(" + rows[i].utmcsr + "/" + rows[i].utmcmd + ")" + rows[i].dataTime ;
			demoCountArray[i] = rows[i].demoCount;
			realCountArray[i] = rows[i].realCount;
			depositCountArray[i] = rows[i].depositCount;
			
			devicecountCountArray[i] = rows[i].devicecount;
		}

		// 显示图表
		dasChartRoomStatisticsSourceCountChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			dasChartRoomStatisticsSourceCountChart.showChart1(dataTimeArray,demoCountArray, realCountArray, depositCountArray);
		} else if (type == 2) {
			dasChartRoomStatisticsSourceCountChart.showChart2(dataTimeArray, devicecountCountArray);
		}
	},
	
	/**
	 * 显示图表
	 */
	showChart1 : function (dataTimeArray, demoCountArray, realCountArray, depositCountArray){
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
	},
	
	/**
	 * 显示图表
	 */
	showChart2 : function (dataTimeArray, devicecountCountArray){
		var myChart = echarts.init(document.getElementById('devicecountChartContainer'), 'shine');
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
		        data:['设备次数']
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
		            name:'设备次数',
		            type:'line',
		            data:devicecountCountArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
	
}




















