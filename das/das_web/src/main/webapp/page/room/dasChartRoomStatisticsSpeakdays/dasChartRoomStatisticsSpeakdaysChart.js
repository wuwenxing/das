$(function() {
	
});

var dasChartRoomStatisticsSpeakdaysChart = {
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
			tab.panel('refresh', 'page/room/dasChartRoomStatisticsSpeakdays/statisticsSpeakdaysChart.html');
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		dasChartRoomStatisticsSpeakdaysChart.showOrHideLoadingDiv(type, true);
		
		// 获取表格数据
		var rows = $('#' + dasChartRoomStatisticsSpeakdays.dataGridId).datagrid('getRows');
		var dateTimeArray = new Array();
		var u0Array = new Array();
		var u1Array = new Array();
		var u2Array = new Array();
		var u3Array = new Array();
		var u4Array = new Array();
		var u5Array = new Array();
		for(var i=rows.length-1,j=0; i>=0; i--,j++){
			var dateTime = rows[i].dateTime;
			dateTimeArray[j] = dateTime.substring(0, 7);
			u0Array[j] = rows[i].u0;
			u1Array[j] = rows[i].u1;
			u2Array[j] = rows[i].u2;
			u3Array[j] = rows[i].u3;
			u4Array[j] = rows[i].u4;
			u5Array[j] = rows[i].u5;
		}
		
		// 显示图表
		dasChartRoomStatisticsSpeakdaysChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			dasChartRoomStatisticsSpeakdaysChart.showChart1(dateTimeArray, u0Array, u1Array, u2Array, u3Array, u4Array, u5Array);
			dasChartRoomStatisticsSpeakdaysChart.showChart2(u0Array, u0Array, u1Array, u2Array, u3Array, u4Array, u5Array);
		}
	},
	/**
	 * 显示图表
	 */
	showChart1 : function (dateTimeArray, u0Array, u1Array, u2Array, u3Array, u4Array, u5Array){
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
		            saveAsImage: {show: true,name:"直播间今日发言天数"}
		        }
		    },
		    legend: {
		        data: ['0天','1天','1-5天','5-10天','10-20天','20-30天']
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
		            name:'0天',
		            type:'line',
		            data:u0Array
		        },{
		            name:'1天',
		            type:'line',
		            data:u1Array
		        },
		        {
		            name:'1-5天',
		            type:'line',
		            data:u2Array
		        },
		        {
		            name:'5-10天',
		            type:'line',
		            data:u3Array
		        },
		        {
		            name:'10-20天',
		            type:'line',
		            data:u4Array
		        },
		        {
		            name:'20-30天',
		            type:'line',
		            data:u5Array
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart2 : function (u0Array, u1Array, u2Array, u3Array, u4Array, u5Array){
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
		        x : 'center',
		        data: ['0天','1天','1-5天','5-10天','10-20天','20-30天']
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"直播间往日发言天数"}
		        }
		    },
		    series : [
		        {
		            name: '今日发言天数',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
			            {value:u0Array[0], name:'0天'},
		                {value:u1Array[0], name:'1天'},
		                {value:u2Array[0], name:'1-5天'},
		                {value:u3Array[0], name:'5-10天'},
		                {value:u4Array[0], name:'10-20天'},
		                {value:u5Array[0], name:'20-30天'}
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


















