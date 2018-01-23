$(function() {
	
});

var appDataSourceChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			if (type == 1) {
				$("div.deviceidNumChartLoadingDiv").show();
			}
		}else{
			if (type == 1) {
				$("div.deviceidNumChartLoadingDiv").hide();
			}
		}
	},

	/**
	 * 获取图表数据
	 */
	loadChartData : function(type,field) {
		appDataSourceChart.showOrHideLoadingDiv(type, true);
		
		// 获取表格数据
		var rows = $('#' + appDataSource.dataGridId).datagrid('getRows');
		var legendArray = new Array();
		var pieSeriesDataArray = new Array();
		
		var barxAxisArray = new Array();
		var barSeriesArray = new Array();
		var deviceidFieldCountArray = new Array();
		var barSeriesDataArray = new Array();
	
		for(var i=0; i<rows.length; i++){
			//触发次数
			if(legendArray.indexOf(rows[i][field]) == -1){
				if(field == "userType"){
					if(rows[i][field] == 1){
						legendArray[legendArray.length] = "游客";
					}
					if(rows[i][field] == 2){
						legendArray[legendArray.length] = "模拟";
					}
					if(rows[i][field] == 3){
						legendArray[legendArray.length] = "真实";
					}else{
						legendArray[legendArray.length] = rows[i][field];
					}
				}else{
					legendArray[legendArray.length] = rows[i][field];
				}
			}			
			var flag = false;
			var index = 0;
			for(var j = 0;j<pieSeriesDataArray.length;j++){
				var deviceidNumObj = pieSeriesDataArray[j];
				var fieldName = rows[i][field];
				if(field == "userType"){
					if(rows[i][field] == 1){
						fieldName = "游客";
					}
					if(rows[i][field] == 2){
						fieldName = "模拟";
					}
					if(rows[i][field] == 3){
						fieldName = "真实";
					}
				}
				if(deviceidNumObj.name == fieldName){
					flag = true;
					index = j;
					break;
				}
			}
			if(flag){
				var deviceidNumObj = pieSeriesDataArray[index];
				deviceidNumObj.value += rows[i].deviceidNum;
			}else{
				var deviceidNums = {name:rows[i][field],value:rows[i].deviceidNum};
				if(field == "userType"){
					if(rows[i][field] == 1){
						deviceidNums = {name:"游客",value:rows[i].deviceidNum};
					}
					if(rows[i][field] == 2){
						deviceidNums = {name:"模拟",value:rows[i].deviceidNum};					
					}
					if(rows[i][field] == 3){
						deviceidNums = {name:"真实",value:rows[i].deviceidNum};
					}
				}
				pieSeriesDataArray[pieSeriesDataArray.length] = deviceidNums;
			}
			
			//触发设备数
			flag = false;
			index = 0;
			for(var k = 0;k<deviceidFieldCountArray.length;k++){
				var deviceidFieldCountObj = deviceidFieldCountArray[k];
				var fieldName = rows[i][field];
				if(field == "userType"){
					if(rows[i][field] == 1){
						fieldName = "游客";
					}
					if(rows[i][field] == 2){
						fieldName = "模拟";
					}
					if(rows[i][field] == 3){
						fieldName = "真实";
					}
				}
				if(deviceidFieldCountObj.name == fieldName){
					flag = true;
					index = k;
					break;
				}
			}
			if(flag){
				deviceidFieldCountArray[index].value += rows[i].deviceidCount;
			}else{
				var deviceidNums = rows[i][field];
				if(field == "userType"){
					if(rows[i][field] == 1){
						deviceidNums = "游客";
					}
					if(rows[i][field] == 2){
						deviceidNums = "模拟";					
					}
					if(rows[i][field] == 3){
						deviceidNums = "真实";
					}
				}
				deviceidFieldCountArray[deviceidFieldCountArray.length] = {name:deviceidNums,value:rows[i].deviceidCount};
			}
		}
		if(field == "datetime" || field == "weeks"){
			var k = 0;
			for(var i = deviceidFieldCountArray.length-1;i>=0;i--){
				barxAxisArray[k] = deviceidFieldCountArray[i].name;
				barSeriesDataArray[k] = deviceidFieldCountArray[i].value;
				k++;
			}
		}else{
			for(var i = 0;i < deviceidFieldCountArray.length;i++){
				barxAxisArray[i] = deviceidFieldCountArray[i].name;
				barSeriesDataArray[i] = deviceidFieldCountArray[i].value;
			}
		}
		
		barSeriesArray[0] = {name:"触发设备数",type:'bar',barWidth: '60%',data:barSeriesDataArray};
		
		// 显示图表
		appDataSourceChart.showOrHideLoadingDiv(type, false);
		if (type == 1) {
			//触发次数
			appDataSourceChart.showChart1(legendArray,pieSeriesDataArray);
			//触发设备数
			appDataSourceChart.showChart2(barxAxisArray, barSeriesArray);
			
			//柱状图饼图一起显示
			//appDataSourceChart.showChart3(barxAxisArray, barSeriesArray,pieSeriesDataArray);
		}
	},
	
	/**
	 * 触发次数
	 */
	showChart1 : function (legendArray, seriesData){
        var myChart = echarts.init(document.getElementById('deviceidNumChartContainer'), 'shine');
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
		    	data: legendArray
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"触发次数"}
		        }
		    },
		    series : [
		        {
		            name: '触发次数',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:seriesData,
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
	},
	/**
	 * 触发设备数
	 */
	showChart2 : function (xAxisData, seriesData){
        var myChart = echarts.init(document.getElementById('deviceidCountChartContainer'), 'shine');
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
		            magicType: {show: false, type: ['bar']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"触发设备数"}
		        }
		    },
		    xAxis : [
		             {
		                 type : 'category',
		                 data : xAxisData,
		                 axisTick: {
		                     alignWithLabel: true
		                 }
		             }
		         ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '设备数',
		            min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series : seriesData
		};
        // 显示图表。
        myChart.setOption(option);
	},
	showChart3 : function (xAxisData, seriesData,pieSeriesDataArray){
        var myChart = echarts.init(document.getElementById('deviceidCountChartContainer'), 'shine');
        option = {
        	    tooltip : {
        	        trigger: 'axis',
        	        axisPointer : {
        	            type : 'shadow' 
        	        }
        	    },
        	        title: [{
        	        text: '触发设备数分析',
        	        left: '1%',
        	        top: '6%',
        	        textStyle: {
        	            color: '#ff733f'
        	        }
        	    }, {
        	        text: '设备次数占比',
        	        left: '83%',
        	        top: '10%',
        	        textAlign: 'center',
        	        textStyle: {
        	            color: '#ff733f'
        	        }
        	    }],
        	    legend: {
        	        data: xAxisData,
        	        left:'73%'
        	    },
        	    grid: {
        	        left: '1%',
        	        right: '40%',
        	        top: '16%',
        	        bottom: '6%',
        	        containLabel: true
        	    },
        	    
        	    xAxis: {
        	        type: 'category',
        	        data: xAxisData
        	    },
        	    yAxis:  {
        	        type: 'value'
        	    },
        	    series: [
        	        seriesData[0],
        	        {
	        	        type: 'pie',
	        	        center: ['83%', '53%'],
	        	        radius: ['25%', '39%'],
	        	        data:pieSeriesDataArray
        	        },
        	    ]
        	};
        // 显示图表。
        myChart.setOption(option);
	}
}


















