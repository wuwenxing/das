$(function() {
	homePageChart.findChartData();
});

var homePageChart = {
	searchFormId: "searchForm",
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + homePageChart.searchFormId).form('reset');
	},
	/**
	 * 查询图表数据
	 */
	findChartData : function() {
		homePageChart.loadChartData();
	},
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			if (type == 0) {
				$("div.visitChartLoadingDiv").show();
			} else if (type == 1) {
				$("div.advisoryChartLoadingDiv").show();
			} else if (type == 2) {
				$("div.demoChartLoadingDiv").show();
			} else if (type == 3) {
				$("div.realChartLoadingDiv").show();
			} else if (type == 4) {
				$("div.depositChartLoadingDiv").show();
			}
		}else{
			if (type == 0) {
				$("div.visitChartLoadingDiv").hide();
			} else if (type == 1) {
				$("div.advisoryChartLoadingDiv").hide();
			} else if (type == 2) {
				$("div.demoChartLoadingDiv").hide();
			} else if (type == 3) {
				$("div.realChartLoadingDiv").hide();
			} else if (type == 4) {
				$("div.depositChartLoadingDiv").hide();
			}
		}
	},
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		homePageChart.showOrHideLoadingDiv(0, true);
		homePageChart.showOrHideLoadingDiv(1, true);
		homePageChart.showOrHideLoadingDiv(2, true);
		homePageChart.showOrHideLoadingDiv(3, true);
		homePageChart.showOrHideLoadingDiv(4, true);
		$.ajax({
			type : "post",
			dataType : "json",
			url : BASE_PATH + "HomePageChartController/hourChart",
			data : homePageChart.getQueryParams(),
			beforeSend: function(XMLHttpRequest){
		    },
			success : function(data) {
				if (null != data && null != data.dataMap) {
					todayHourChartDatas = data.dataMap.today;
					yesterdayHourChartDatas = data.dataMap.yesterday;
					avgHourChartDatas = data.dataMap.avg;

					// 显示图表0
					var dataType = 0;
					var containerName = "visitChartContainer";
					var saveAsImagename = "访问数"
						homePageChart.showOrHideLoadingDiv(dataType, false);
					homePageChart.showChart(containerName, todayHourChartDatas,
							yesterdayHourChartDatas, avgHourChartDatas,
							dataType, data.condition.startTime,
							data.condition.endTime,saveAsImagename);

					// 显示图表1
					dataType = 1;
					homePageChart.showOrHideLoadingDiv(dataType, false);
					containerName = "advisoryChartContainer";
					saveAsImagename = "咨询数"
					homePageChart.showChart(containerName, todayHourChartDatas,
							yesterdayHourChartDatas, avgHourChartDatas,
							1, data.condition.startTime,
							data.condition.endTime,saveAsImagename);

					// 显示图表2
					dataType = 2;
					containerName = "demoChartContainer";
					saveAsImagename = "模拟开户数"
					homePageChart.showOrHideLoadingDiv(dataType, false);
					homePageChart.showChart(containerName, todayHourChartDatas,
							yesterdayHourChartDatas, avgHourChartDatas,
							2, data.condition.startTime,
							data.condition.endTime,saveAsImagename);

					// 显示图表3
					dataType = 3;
					containerName = "realChartContainer";
					saveAsImagename = "真实开户数"
					homePageChart.showOrHideLoadingDiv(dataType, false);
					homePageChart.showChart(containerName, todayHourChartDatas,
							yesterdayHourChartDatas, avgHourChartDatas,
							3, data.condition.startTime,
							data.condition.endTime,saveAsImagename);

					// 显示图表4
					dataType = 4;
					containerName = "depositChartContainer";
					saveAsImagename = "首次入金数"
					homePageChart.showOrHideLoadingDiv(dataType, false);
					homePageChart.showChart(containerName, todayHourChartDatas,
							yesterdayHourChartDatas, avgHourChartDatas,
							4, data.condition.startTime,
							data.condition.endTime,saveAsImagename);
				}else{
					homePageChart.showOrHideLoadingDiv(0, false);
					homePageChart.showOrHideLoadingDiv(1, false);
					homePageChart.showOrHideLoadingDiv(2, false);
					homePageChart.showOrHideLoadingDiv(3, false);
					homePageChart.showOrHideLoadingDiv(4, false);
				}
			},
			error : function(data) {
				
			}
		});
	},
	/**
	 * 查询参数
	 */
	getQueryParams : function() {
		var startTime = $("#startTimeSearch").datebox('getValue');
		if (startTime == '') {
			startTime = new Date().Format("yyyy-MM-dd");
			$("#startTimeSearch").val(startTime);
		}
		var endTime = $("#endTimeSearch").datebox('getValue');
		if (endTime == '') {
			endTime = new Date().Format("yyyy-MM-dd");
			$("#endTimeSearch").val(endTime);
		}
		var queryParams = {};
		queryParams['startTime'] = startTime;
		queryParams['endTime'] = endTime;
		queryParams['utmcsr'] = $("#utmcsrSearch").val();
		queryParams['utmcmd'] = $("#utmcmdSearch").val();
		return queryParams;
	},
	/**
	 * 解析数据
	 */
	parseData: function (originalDataList,dataType){
		var retData = [];
		for(var i=0;i<originalDataList.length;i++){
			if(dataType==0){
				retData.push(originalDataList[i].visitCount);
	        }else if(dataType==1){
	        	retData.push(originalDataList[i].advisoryCount);
	        }else if(dataType==2){
	        	retData.push(originalDataList[i].demoCount);
	        }else if(dataType==3){
	        	retData.push(originalDataList[i].realCount);
	        }else if(dataType==4){
	        	retData.push(originalDataList[i].depositCount);
	        }
		}
		return retData;
	},
	/**
	 * 显示图表
	 */
	showChart : function (containerName, todayHourChartDatas,
			yesterdayHourChartDatas, avgHourChartDatas, dataType, startTime,
			endTime,saveAsImagename){
		var myChart = echarts.init(document.getElementById(containerName), 'shine');
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
		            saveAsImage: {show: true,name:saveAsImagename}
		        }
		    },
		    legend: {
		        data:['今天','昨天',startTime == endTime ? "最近七天平均" : startTime + '至' + endTime + '平均']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: ['00:00','01:00','02:00','03:00','04:00','05:00','06:00','07:00','08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00','24:00']
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '数量',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'今天',
		            type:'line',
		            data:homePageChart.parseData(todayHourChartDatas, dataType)
		        },
		        {
		            name:'昨天',
		            type:'line',
		            data:homePageChart.parseData(yesterdayHourChartDatas, dataType)
		        },
		        {
		            name:startTime == endTime ? "最近七天平均" : startTime + '至' + endTime + '平均',
		            type:'line',
		            data:homePageChart.parseData(avgHourChartDatas, dataType)
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}

}