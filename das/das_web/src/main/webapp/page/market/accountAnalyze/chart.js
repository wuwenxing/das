$(function() {
	chart_1.init();
});

var chart_1 = {
	/**
	 * 初始化
	 */
	init : function() {
		var persistIdAry = chart_1.getUrlParam("persistId").split(",");
		if(null != persistIdAry && persistIdAry.length>=8){
			chart_1.persistId = persistIdAry[0];
			chart_1.id = persistIdAry[1];
			chart_1.token = persistIdAry[2];
			chart_1.companyId = persistIdAry[3];
			chart_1.accountNo = persistIdAry[4];
			chart_1.platform = persistIdAry[5];
			chart_1.startDate = persistIdAry[6];
			chart_1.endDate = persistIdAry[7];
		}
		chart_1.loadChartData();
	},
	/**
	 * 属性定义
	 */
	myChart : null,
	persistId : '',
	id : '',
	token : '',
	companyId : '',
	accountNo : '',
	platform : '',
	startDate : '',
	endDate : '',
	successFlag : true,
	profitAnalysisData : null,
	profitAnalysisDataFlag : false,
	profitStatisticsData : null,
	profitStatisticsDataFlag : false,
	marginLevelData : null,
	marginLevelDataFlag : false,
	profitAndLossAmountAndTimeData : null,
	profitAndLossAmountAndTimeDataFlag : false,
	attrOptions: [
		{id:'loc', systemUrl:'http://localhost:8080/das_web', apiUrl:'http://192.168.35.238:9998'},
		{id:'uat', systemUrl:'http://192.168.35.238:8070/das_web', apiUrl:'http://192.168.35.238:9998'},
		{id:'real', systemUrl:'http://192.168.9.169:8080', apiUrl:'http://192.168.9.167:9998'}
	],
	/**
	 * 获取定义的属性
	 */
	getAttrOptions : function(id) {
		for (var i = 0; i < chart_1.attrOptions.length; i++) {
			if (chart_1.attrOptions[i].id == id) {
				return chart_1.attrOptions[i];
			}
		}
		return undefined;
	},
	/**
	 * 获取地址栏参数
	 * 
	 * @param type
	 */
	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) {
			return unescape(r[2]);
		}
		return "";
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function(type) {
		
		var url_1 = chart_1.getAttrOptions(chart_1.id).apiUrl + "/accountAnalyze/profitAnalysis";
		var url_2 = chart_1.getAttrOptions(chart_1.id).apiUrl + "/accountAnalyze/profitStatistics";
		var url_3 = chart_1.getAttrOptions(chart_1.id).apiUrl + "/accountAnalyze/marginLevel";
		var url_4 = chart_1.getAttrOptions(chart_1.id).apiUrl + "/accountAnalyze/profitAndLossAmountAndTime";
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url_1,
			data : {
				"token" : chart_1.token,
				"companyId" : chart_1.companyId,
				"accountNo" : chart_1.accountNo,
				"platform" : chart_1.platform,
				"startDate" : chart_1.startDate,
				"endDate" : chart_1.endDate
			},
			success : function(data) {
				chart_1.profitAnalysisDataFlag = true;
				if (null != data && data.status == '0') {
					chart_1.profitAnalysisData = data.result;
				}else{
					chart_1.successFlag = false;
				}
			},
			error: function(data){
				chart_1.profitAnalysisDataFlag = true;
				chart_1.successFlag = false;
	        }
		});
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url_2,
			data : {
				"token" : chart_1.token,
				"companyId" : chart_1.companyId,
				"accountNo" : chart_1.accountNo,
				"platform" : chart_1.platform,
				"startDate" : chart_1.startDate,
				"endDate" : chart_1.endDate
			},
			success : function(data) {
				chart_1.profitStatisticsDataFlag = true;
				if (null != data && data.status == '0') {
					chart_1.profitStatisticsData = data.result;
				}else{
					chart_1.successFlag = false;
				}
			},
			error: function(data){
				chart_1.profitStatisticsDataFlag = true;
				chart_1.successFlag = false;
	        }
		});
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url_3,
			data : {
				"token" : chart_1.token,
				"companyId" : chart_1.companyId,
				"accountNo" : chart_1.accountNo,
				"platform" : chart_1.platform,
				"startDate" : chart_1.startDate,
				"endDate" : chart_1.endDate
			},
			success : function(data) {
				chart_1.marginLevelDataFlag = true;
				if (null != data && data.status == '0') {
					chart_1.marginLevelData = data.result;
				}else{
					chart_1.successFlag = false;
				}
			},
			error: function(data){
				chart_1.marginLevelDataFlag = true;
				chart_1.successFlag = false;
	        }
		});
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url_4,
			data : {
				"token" : chart_1.token,
				"companyId" : chart_1.companyId,
				"accountNo" : chart_1.accountNo,
				"platform" : chart_1.platform,
				"startDate" : chart_1.startDate,
				"endDate" : chart_1.endDate
			},
			success : function(data) {
				chart_1.profitAndLossAmountAndTimeDataFlag = true;
				if (null != data && data.status == '0') {
					chart_1.profitAndLossAmountAndTimeData = data.result;
				}else{
					chart_1.successFlag = false;
				}
			},
			error: function(data){
				chart_1.profitAndLossAmountAndTimeDataFlag = true;
				chart_1.successFlag = false;
	        }
		});
		
		var interval = setInterval(function() {
			if(chart_1.profitAnalysisDataFlag&&
				chart_1.profitStatisticsDataFlag&&
				chart_1.marginLevelDataFlag&&
				chart_1.profitAndLossAmountAndTimeDataFlag){
				// 关闭定时
				clearInterval(interval);
				if(chart_1.successFlag){
					// 显示图表
					chart_1.showChart1();
					chart_1.showChart2();
					chart_1.showChart3();
					chart_1.showChart4();
					chart_1.showChart5();
					chart_1.showChart6();
					setTimeout(chart_1.persistCallback, 250);
				}else{
					chart_1.persistCallback('N');
				}
			}
		}, 250);
	},
	/**
	 * 显示图表
	 */
	showChart1 : function (){

		var count_1 = 0;
		var count_2 = 0;
		var count_3 = 0;
		var count_4 = 0;
		
		var resultAry = chart_1.profitAnalysisData;
		for (var i=0; i<resultAry.length; i++) {
			count_1 += resultAry[i].sellprofitnumber;
			count_2 += resultAry[i].selllossnumber;
			count_3 += resultAry[i].buyprofitnumber;
			count_4 += resultAry[i].buylossnumber;
		}
		
		var myChart = echarts.init(document.getElementById('chartContainer_1'), 'shine');
		var	option = {
			color: ["#FF6644",
			        "#268DEC",
		            "#052A66",
		            "#97DEA5"],
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        x : 'center',
		        data: ['空单赢单','空单亏单','多单赢单','多单亏单'],
		        bottom: '0'
		    },
		    series : [
		        {
		            name: '多空盈亏比例统计',
		            type: 'pie',
		            radius : '45%',
		            data:[
			            {value:count_1, name:'空单赢单', selected:true},
		                {value:count_2, name:'空单亏单'},
			            {value:count_3, name:'多单赢单'},
		                {value:count_4, name:'多单亏单'}
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
	showChart2 : function (){

		var count_1 = 0;
		var count_2 = 0;
		var count_3 = 0;
		var count_4 = 0;

		var resultAry = chart_1.profitAnalysisData;
		for (var i=0; i<resultAry.length; i++) {
			count_1 += resultAry[i].buynumber;
			count_2 += resultAry[i].sellnumber;
			if(null != resultAry[i].buytallprofit&&resultAry[i].buytallprofit < 0){
				count_3 += resultAry[i].buytallprofit*-1;
			}else{
				count_3 += resultAry[i].buytallprofit;
			}
			if(null != resultAry[i].selltallprofit&&resultAry[i].selltallprofit < 0){
				count_4 += resultAry[i].selltallprofit*-1;
			}else{
				count_4 += resultAry[i].selltallprofit;
			}
		}
		
		var myChart = echarts.init(document.getElementById('chartContainer_2'), 'shine');
		option = {
				color: ["#FF6644",
				        "#268DEC"],
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)"
			    },
			    legend: {
			        x: 'center',
			        data:['多','空'],
			        bottom: '0'
			    },
			    series: [
			        {
			            name:'交易次数',
			            type:'pie',
			            radius: ['10%', '25%'],
			            data:[
			                {value:count_1, name:'多', selected:false},
			                {value:count_2, name:'空'}
			            ],
			            labelLine: {
			                normal: {
			                	length: 40,
			                    show: true
			                }
			            },
			            label: {
			        		normal: {
			        			show: true,
			        			position: 'outside',
			        			formatter: '交易次数{c}'
			        		}
			        	}
			        },
			        {
			            name:'盈亏',
			            type:'pie',
			            radius: ['30%', '45%'],
			            data:[
			                {value:count_3, name:'多', selected:false},
			                {value:count_4, name:'空'}
			            ],
			            labelLine: {
			                normal: {
			                	length: 100,
			                    show: true
			                }
			            },
			            label: {
			        		normal: {
			        			show: true,
			        			position: 'outside',
			        			formatter: '盈亏绝对值{c}'
			        		}
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
	showChart3 : function() {

		// 获取数据
		var dateTimeArray = new Array();
		var u0Array = new Array();
		var u1Array = new Array();
		var resultAry = chart_1.profitStatisticsData;
		for (var i=0; i<resultAry.length; i++) {
			dateTimeArray[i] = resultAry[i].execdate;
			u0Array[i] = resultAry[i].cumulativeprofit;
			u1Array[i] = resultAry[i].profit;
		}
		var myChart = echarts.init(document
				.getElementById('chartContainer_3'), 'shine');
		var option = {
			color: ["#FF6644",
			        "#07C4FF"],
			tooltip : {
				trigger : 'axis'
			},
			toolbox : {
				show : false
			},
			legend : {
				data : [ '累计收益', '当天收益'],
		        bottom: '0'
			},
			xAxis : [ {
				name : '',
				type : 'category',
				data : dateTimeArray
			} ],
			yAxis : [ {
				type : 'value',
				name : '累计收益',
				axisLabel : {
					formatter : '{value}'
				}
			}, {
				type : 'value',
				name : '当天收益',
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			series : [ {
				name : '累计收益',
				type : 'line',
				data : u0Array,
				yAxisIndex:0,
				areaStyle: {normal: {}}
			}, {
				name : '当天收益',
				type : 'line',
				data : u1Array,
				yAxisIndex:1
			} ]
		};
		// 显示图表。
		myChart.setOption(option);
	
	},
	/**
	 * 显示图表
	 */
	showChart4 : function() {

		// 获取数据
		var dateTimeArray = new Array();
		var u0Array = new Array();
		var resultAry = chart_1.profitStatisticsData;
		for (var i=0; i<resultAry.length; i++) {
			dateTimeArray[i] = resultAry[i].execdate;
			u0Array[i] = resultAry[i].floatingprofit;
		}
		var myChart = echarts.init(document
				.getElementById('chartContainer_4'), 'shine');
		var option = {
			color: ["#FF6644",
			        "#07C4FF"],
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '浮动盈亏' ],
		        bottom: '0'
			},
			xAxis : [ {
				name : '',
				type : 'category',
				data : dateTimeArray
			} ],
			yAxis : [ {
				type : 'value',
				name : '浮动盈亏',
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			series : [ {
				name : '浮动盈亏',
				type : 'line',
				data : u0Array
			}]
		};
		// 显示图表。
		myChart.setOption(option);
	},
	/**
	 * 显示图表
	 */
	showChart5 : function() {

		// 获取数据
		var dateTimeArray = new Array();
		var u0Array = new Array();
		var resultAry = chart_1.marginLevelData;
		for (var i=0; i<resultAry.length; i++) {
			dateTimeArray[i] = resultAry[i].execdaterpt;
			if('-' == resultAry[i].marginratio){
				u0Array[i] = 0;
			}else{
				u0Array[i] = resultAry[i].marginratio;
			}
		}
		
		var myChart = echarts.init(document
				.getElementById('chartContainer_5'), 'shine');
		var option = {
			color: ["#FF6644",
			        "#07C4FF"],
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '保证金水平(万)' ],
		        bottom: '0'
			},
			xAxis : [ {
				name : '',
				type : 'category',
				data : dateTimeArray
			} ],
			yAxis : [ {
				type : 'value',
				name : '保证金水平',
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			series : [ {
				name : '保证金水平',
				type : 'line',
				data : u0Array
			} ]
		};
		// 显示图表。
		myChart.setOption(option);
		
	},
	/**
	 * 显示图表
	 */
	showChart6 : function() {

		// 获取数据
		var u0Array = new Array();
		var resultAry = chart_1.profitAndLossAmountAndTimeData;
		for (var i=0; i<resultAry.length; i++) {
			var obj=new Array(2);
			obj[0]=resultAry[i].jyk;
			obj[1]=resultAry[i].positionintervaltime;
			u0Array[i] = obj;
		}
		
		var myChart = echarts.init(document
				.getElementById('chartContainer_6'), 'shine');
		
		var option = {
				color: ["#FE7855",
				        "#07C4FF"],
				tooltip : {
					trigger : 'axis'
				},
			    legend: {
			        data: ['盈亏'],
			        bottom: '0'
			    },
			    xAxis : [
			        {
						name : '盈亏',
			            type : 'value',
			            scale:true,
			            axisLabel : {
			                formatter: '{value}'
			            }
			        }
			    ],
			    yAxis : [
			        {
						name : '持仓时间（min）',
			            type : 'value',
			            scale:true,
			            axisLabel : {
			                formatter: '{value}'
			            }
			        }
			    ],
			    series : [
			        {
			            name:'盈亏',
			            type:'scatter',
			            data: u0Array
			        }
			    ]
			};

		// 显示图表。
		myChart.setOption(option);
	},
	/**
	 * 回调
	 */
	persistCallback : function(paramFlag) {
		// var data = "data=" +
		// encodeURIComponent(myChart.getDataURL("image/jpeg"));
		var data = "persistId=" + chart_1.persistId;
		if('N' == paramFlag){
			data += "&flag=N";
		}else{
			data += "&flag=Y";
		}
		var url = chart_1.getAttrOptions(chart_1.id).systemUrl + "/AccountAnalyzeController/persistCallback";
		var xmlhttp;
		if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome,
			// Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else { // code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.open("POST", url, true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				$("#persistFinish").val("true");
			}
		}
		xmlhttp.send(data);
	}
}
