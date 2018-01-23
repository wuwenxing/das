$(function() {
	
});

var dasChartRoomStatisticsSpeakcountsChart = {
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
			tab.panel('refresh', 'page/room/dasChartRoomStatisticsSpeakcounts/statisticsSpeakcountsChart.html');
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		dasChartRoomStatisticsSpeakcountsChart.showOrHideLoadingDiv(type, true);
		
		// 获取表格数据
		var rows = $('#' + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('getRows');
		var dateTimeArray = new Array();
		var u1Array = new Array();
		var u2Array = new Array();
		var u3Array = new Array();
		var u4Array = new Array();
		var u5Array = new Array();
		var u6Array = new Array();
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
			u1Array[j] = rows[i].u1;
			u2Array[j] = rows[i].u2;
			u3Array[j] = rows[i].u3;
			u4Array[j] = rows[i].u4;
			u5Array[j] = rows[i].u5;
			u6Array[j] = rows[i].u6;
		}
		
		// 显示图表
		dasChartRoomStatisticsSpeakcountsChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			dasChartRoomStatisticsSpeakcountsChart.showChart1(dateTimeArray, u1Array, u2Array, u3Array, u4Array, u5Array, u6Array);
			dasChartRoomStatisticsSpeakcountsChart.showChart2(u1Array, u2Array, u3Array, u4Array, u5Array, u6Array);
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
		            saveAsImage: {show: true,name:"直播间今日发言次数"}
		        }
		    },
		    legend: {
		        data:['1次','1-5次','5-10次','10-20次','20-30次','30次以上']
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
		            name:'1次',
		            type:'line',
		            data:u1Array
		        },
		        {
		            name:'1-5次',
		            type:'line',
		            data:u2Array
		        },
		        {
		            name:'5-10次',
		            type:'line',
		            data:u3Array
		        },
		        {
		            name:'10-20次',
		            type:'line',
		            data:u4Array
		        },
		        {
		            name:'20-30次',
		            type:'line',
		            data:u5Array
		        },
		        {
		            name:'30次以上',
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
	showChart2 : function (u1Array, u2Array, u3Array, u4Array, u5Array, u6Array){
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
		        data: ['1次','1-5次','5-10次','10-20次','20-30次','30次以上']
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"直播间往日发言次数"}
		        }
		    },
		    series : [
		        {
		            name: '今日发言次数',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
		                {value:u1Array[0], name:'1次'},
		                {value:u2Array[0], name:'1-5次'},
		                {value:u3Array[0], name:'5-10次'},
		                {value:u4Array[0], name:'10-20次'},
		                {value:u5Array[0], name:'20-30次'},
		                {value:u6Array[0], name:'30次以上'}
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


















