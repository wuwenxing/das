$(function() {
	
});

var dasChartRoomStatisticsOnlineHoursChart = {
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
			tab.panel('refresh', 'page/room/dasChartRoomStatisticsOnlineHours/statisticsOnlineHoursChart.html');
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		dasChartRoomStatisticsOnlineHoursChart.showOrHideLoadingDiv(type, true);
		
		// 获取表格数据
		var rows = $('#' + dasChartRoomStatisticsOnlineHours.dataGridId).datagrid('getRows');
		var dateTimeArray = new Array();
		var u1Array = new Array();
		var u2Array = new Array();
		var u3Array = new Array();
		var u4Array = new Array();
		var u5Array = new Array();
		var u6Array = new Array();
		for(var i=rows.length-1,j=0; i>=0; i--,j++){
			dateTimeArray[j] = rows[i].dateTime;
			u1Array[j] = rows[i].seconds1;
			u2Array[j] = rows[i].seconds2;
			u3Array[j] = rows[i].seconds3;
			u4Array[j] = rows[i].seconds4;
			u5Array[j] = rows[i].seconds5;
			u6Array[j] = rows[i].seconds6;
		}
		
		// 显示图表
		dasChartRoomStatisticsOnlineHoursChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			dasChartRoomStatisticsOnlineHoursChart.showChart1(dateTimeArray, u1Array, u2Array, u3Array, u4Array, u5Array, u6Array);
			dasChartRoomStatisticsOnlineHoursChart.showChart2(u1Array);
		}
	},
	/**
	 * 显示图表
	 */
	showChart1 : function (dateTimeArray, u1Array, u2Array, u3Array, u4Array, u5Array, u6Array){
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
		            saveAsImage: {show: true,name:"直播间今日在线时长"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['1分钟以内','1-5分钟','5-30分钟','30-60分钟','1-2小时','2小时以上']
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
		            name: '天数',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'1分钟以内',
		            type:'line',
		            data:u1Array
		        },
		        {
		            name:'1-5分钟',
		            type:'line',
		            data:u2Array
		        },
		        {
		            name:'5-30分钟',
		            type:'line',
		            data:u3Array
		        },
		        {
		            name:'30-60分钟',
		            type:'line',
		            data:u4Array
		        },
		        {
		            name:'1-2小时',
		            type:'line',
		            data:u5Array
		        },
		        {
		            name:'2小时以上',
		            type:'line',
		            data:u6Array
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart2 : function (u1Array){
        var myChart = echarts.init(document.getElementById('chartContainer_2'), 'shine');
		var	option = {
			title : {
		        text: '',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        data:['1分钟以内','1-5分钟','5-30分钟','30-60分钟','1-2小时','2小时以上']
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"直播间往日在线时长"}
		        }
		    },
		    series : [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
		                {value:u1Array[0], name:'1分钟以内'},
		                {value:u1Array[1], name:'1-5分钟'},
		                {value:u1Array[2], name:'5-30分钟'},
		                {value:u1Array[3], name:'30-60分钟'},
		                {value:u1Array[4], name:'1-2小时'},
		                {value:u1Array[5], name:'2小时以上'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}


















