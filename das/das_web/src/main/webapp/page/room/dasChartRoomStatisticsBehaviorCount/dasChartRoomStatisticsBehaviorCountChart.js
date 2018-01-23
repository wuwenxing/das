$(function() {
	
});

var dasChartRoomStatisticsBehaviorCountChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){			
            if (type == 1) {
				$("div.accountChartLoadingDiv").show();
			}else if (type == 2) {
				$("div.devicecountChartLoadingDiv").show();
		   }
		}else{
			if (type == 1) {
				$("div.accountChartLoadingDiv").hide();
			}else if (type == 2) {
				$("div.devicecountChartLoadingDiv").hide();
		   }
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
		queryParams['devicetype'] = $("#devicetypeSearch").combobox('getValue');
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "DasChartRoomController/behaviorCountfindList",
			data : queryParams,
			success : function(rows) {
				if(null != rows){
					var dataTimeArray = new Array();
					var demoCountArray = new Array();
					var realCountArray = new Array();
					var depositCountArray = new Array();
					
					var devicecountArray = new Array();

					for(var i=0; i<rows.length; i++){
						dataTimeArray[i] = rows[i].dataTime;
						demoCountArray[i] = rows[i].demoCount;
						realCountArray[i] = rows[i].realCount;
						depositCountArray[i] = rows[i].depositCount;
						
						devicecountArray[i] = rows[i].devicecount;
					}
					
					// 显示图表
					dasChartRoomStatisticsBehaviorCountChart.showOrHideLoadingDiv(1, false);
					dasChartRoomStatisticsBehaviorCountChart.showChart1(dataTimeArray, demoCountArray, realCountArray, depositCountArray);
					dasChartRoomStatisticsBehaviorCountChart.showOrHideLoadingDiv(2, false);
					dasChartRoomStatisticsBehaviorCountChart.showChart2(dataTimeArray, devicecountArray);
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
	},
	
	/**
	 * 显示图表
	 */
	showChart2 : function (dataTimeArray, devicecountArray){
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
		            saveAsImage: {show: true,name:"官网咨询统计"}
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
		            data:devicecountArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
	
}




















