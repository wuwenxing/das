$(function() {
	homeChart.loadChartData();
	setInterval("homeChart.findDayBehavior()", 15*1000);
});

var homeChart = {
	/**
	 * 定义当日pc及移动端行为变量
	 */
	demoCountPc: 0,
	demoCountMobile: 0,
	realCountPc: 0,
	realCountMobile: 0,
	depositCountPc: 0,
	depositCountMobile: 0,
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(dataType, showFlag) {
		if (showFlag) {
			if (dataType == 1) {
				$("div.chartLoadingDiv_1").show();
			}else if (dataType == 2) {
				$("div.chartLoadingDiv_2").show();
			}else if (dataType == 3) {
				$("div.chartLoadingDiv_3").show();
			}else if (dataType == 4) {
				$("div.chartLoadingDiv_4").show();
			}else if (dataType == 5) {
				$("div.chartLoadingDiv_5").show();
			}else if (dataType == 6) {
				$("div.chartLoadingDiv_6").show();
			}
		} else {
			if (dataType == 1) {
				$("div.chartLoadingDiv_1").hide();
			}else if (dataType == 2) {
				$("div.chartLoadingDiv_2").hide();
			}else if (dataType == 3) {
				$("div.chartLoadingDiv_3").hide();
			}else if (dataType == 4) {
				$("div.chartLoadingDiv_4").hide();
			}else if (dataType == 5) {
				$("div.chartLoadingDiv_5").hide();
			}else if (dataType == 6) {
				$("div.chartLoadingDiv_6").hide();
			}
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		var queryParams = {};
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "HomePageChartController/homeChart",
			data : queryParams,
			beforeSend: function(XMLHttpRequest){
		    },
			success : function(rows) {
				if(null != rows){
					var dataTimeArray = new Array();
					var demoCountArray = new Array();
					var realCountArray = new Array();
					var depositCountArray = new Array();
					
					for(var i=0; i<rows.length; i++){
						if( i+2 == rows.length){
							// 当日pc端
							homeChart.demoCountPc = rows[i].demoCount;
							homeChart.realCountPc = rows[i].realCount;
							homeChart.depositCountPc = rows[i].depositCount;
						}else if( i+1 == rows.length){
							// 当日移动端
							homeChart.demoCountMobile = rows[i].demoCount;
							homeChart.realCountMobile = rows[i].realCount;
							homeChart.depositCountMobile = rows[i].depositCount;
						}else{
							// 最近7天-最近1天
							dataTimeArray[i] = rows[i].dataTime;
							demoCountArray[i] = rows[i].demoCount;
							realCountArray[i] = rows[i].realCount;
							depositCountArray[i] = rows[i].depositCount;
						}
					}
					
					// 显示图表
					homeChart.showOrHideLoadingDiv(1, false);
					homeChart.showChart1(dataTimeArray, depositCountArray);
					homeChart.showOrHideLoadingDiv(2, false);
					homeChart.showChart2(dataTimeArray, realCountArray);
					homeChart.showOrHideLoadingDiv(3, false);
					homeChart.showChart3(dataTimeArray, demoCountArray);
					homeChart.showOrHideLoadingDiv(4, false);
					homeChart.showChart4();
					homeChart.showOrHideLoadingDiv(5, false);
					homeChart.showChart5();
					homeChart.showOrHideLoadingDiv(6, false);
					homeChart.showChart6();
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 获取图表数据-当日pc及移动端行为
	 */
	findDayBehavior : function() {
		var queryParams = {};
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "HomePageChartController/findDayBehavior",
			data : queryParams,
			beforeSend: function(XMLHttpRequest){
		    },
			success : function(rows) {
				if(null != rows){
					var demoCountPcTemp;
					var realCountPcTemp;
					var depositCountPcTemp;
					var demoCountMobileTemp;
					var realCountMobileTemp;
					var depositCountMobileTemp;
					
					for(var i=0; i<rows.length; i++){
						if( i+2 == rows.length){
							// 当日pc端
							demoCountPcTemp = rows[i].demoCount;
							realCountPcTemp = rows[i].realCount;
							depositCountPcTemp = rows[i].depositCount;
						}else if( i+1 == rows.length){
							// 当日移动端
							demoCountMobileTemp = rows[i].demoCount;
							realCountMobileTemp = rows[i].realCount;
							depositCountMobileTemp = rows[i].depositCount;
						}
					}
					
					// 显示图表
					// 如果与之前数据b不一致，则刷新图表
					if((homeChart.depositCountPc+"") != (depositCountPcTemp+"")
							|| (homeChart.depositCountMobile+"") != (depositCountMobileTemp+"")
							){
						homeChart.depositCountPc = depositCountPcTemp;
						homeChart.depositCountMobile = depositCountMobileTemp;
						homeChart.showChart4();
					}
					if((homeChart.realCountPc+"") != (realCountPcTemp+"")
							|| (homeChart.realCountMobile+"") != (realCountMobileTemp+"")
							){
						homeChart.realCountPc = realCountPcTemp;
						homeChart.realCountMobile = realCountMobileTemp;
						homeChart.showChart5();
					}
					if((homeChart.demoCountPc+"") != (demoCountPcTemp+"")
							|| (homeChart.demoCountMobile+"") != (demoCountMobileTemp+"")
							){
						homeChart.demoCountPc = demoCountPcTemp;
						homeChart.demoCountMobile = demoCountMobileTemp;
						homeChart.showChart6();
					}
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
	showChart1 : function (dataTimeArray, depositCountArray){
		var markPointArray = new Array();
		for(var i=0; i<depositCountArray.length; i++){
			var count = depositCountArray[i];
			var dataTime = dataTimeArray[i];
			var coordArray = new Array();
			coordArray[0] = dataTime + "";
			coordArray[1] = count + "";
			var obj = new Object();
			obj.coord = coordArray;
			obj.name = '';
			markPointArray[i] = obj;
		}
		var myChart = echarts.init(document.getElementById('chartContainer_1'), 'shine');
		var	option = {
			title : {
		    	left: '80',
		        text: '前7天首次入金数',
		        subtext: '',
		        textStyle: {
		        	color: '#777',
		        	fontSize: 15,
		        }
		    },
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
		        data:['首次入金数'],
		        bottom: '0'
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
		            name: '首次入金数',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'首次入金数',
		            type:'line',
		            data:depositCountArray,
		            markPoint : {
		                data : markPointArray
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart2 : function (dataTimeArray, realCountArray){
		var markPointArray = new Array();
		for(var i=0; i<realCountArray.length; i++){
			var count = realCountArray[i];
			var dataTime = dataTimeArray[i];
			var coordArray = new Array();
			coordArray[0] = dataTime + "";
			coordArray[1] = count + "";
			var obj = new Object();
			obj.coord = coordArray;
			obj.name = '';
			markPointArray[i] = obj;
		}
		var myChart = echarts.init(document.getElementById('chartContainer_2'), 'shine');
		var	option = {
			title : {
		    	left: '80',
		        text: '前7天真实开户数',
		        subtext: '',
		        textStyle: {
		        	color: '#777',
		        	fontSize: 15,
		        }
		    },
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
		        data:['真实开户数'],
		        bottom: '0'
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
		            name: '真实开户数',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'真实开户数',
		            type:'line',
		            data:realCountArray,
		            markPoint : {
		                data : markPointArray
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart3 : function (dataTimeArray, demoCountArray){
		var markPointArray = new Array();
		for(var i=0; i<demoCountArray.length; i++){
			var count = demoCountArray[i];
			var dataTime = dataTimeArray[i];
			var coordArray = new Array();
			coordArray[0] = dataTime + "";
			coordArray[1] = count + "";
			var obj = new Object();
			obj.coord = coordArray;
			obj.name = '';
			markPointArray[i] = obj;
		}
		var myChart = echarts.init(document.getElementById('chartContainer_3'), 'shine');
		var	option = {
			title : {
		    	left: '80',
		        text: '前7天模拟开户数',
		        subtext: '',
		        textStyle: {
		        	color: '#777',
		        	fontSize: 15,
		        }
		    },
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
		        data:['模拟开户数'],
		        bottom: '0'
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
		            name: '模拟开户数',
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
		            data:demoCountArray,
		            markPoint : {
		                data : markPointArray
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart4 : function (){
		var myChart = echarts.init(document.getElementById('chartContainer_4'), 'shine');
		var	option = {
			title : {
		    	left: '0',
		        text: '当日实时首次入金数',
		        subtext: '',
		        textStyle: {
		        	color: '#777',
		        	fontSize: 15,
		        }
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        x : 'center',
		        data: ['PC端','移动端'],
		        bottom: '0'
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"首次入金数-客户端占比"}
		        }
		    },
		    series : [
		        {
		            name: '首次入金数',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
			            {value:homeChart.depositCountPc, name:'PC端'},
		                {value:homeChart.depositCountMobile, name:'移动端'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            },
		            label: {
		        		normal: {
		        			show: true,
		        			position: 'outside',
		        			formatter: '{b}: {c}'
		        		}
		        	},
		        	animationDurationUpdate: 1000
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart5 : function (){
		var myChart = echarts.init(document.getElementById('chartContainer_5'), 'shine');
		var	option = {
			title : {
		    	left: '0',
		        text: '当日实时真实开户数',
		        subtext: '',
		        textStyle: {
		        	color: '#777',
		        	fontSize: 15,
		        }
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        x : 'center',
		        data: ['PC端','移动端'],
		        bottom: '0'
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"真实开户数-客户端占比"}
		        }
		    },
		    series : [
		        {
		            name: '真实开户数',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
			            {value:homeChart.realCountPc, name:'PC端'},
		                {value:homeChart.realCountMobile, name:'移动端'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            },
		            label: {
		        		normal: {
		        			show: true,
		        			position: 'outside',
		        			formatter: '{b}: {c}'
		        		}
		        	},
		        	animationDurationUpdate: 1000
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart6 : function (){
		var myChart = echarts.init(document.getElementById('chartContainer_6'), 'shine');
		var	option = {
			title : {
		    	left: '0',
		        text: '当日实时模拟开户数',
		        subtext: '',
		        textStyle: {
		        	color: '#777',
		        	fontSize: 15,
		        }
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        x : 'center',
		        data: ['PC端','移动端'],
		        bottom: '0'
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"模拟开户数-客户端占比"}
		        }
		    },
		    series : [
		        {
		            name: '模拟开户数',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
			            {value:homeChart.demoCountPc, name:'PC端'},
		                {value:homeChart.demoCountMobile, name:'移动端'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            },
		            label: {
		        		normal: {
		        			show: true,
		        			position: 'outside',
		        			formatter: '{b}: {c}'
		        		}
		        	},
		        	animationDurationUpdate: 1000
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
	
}