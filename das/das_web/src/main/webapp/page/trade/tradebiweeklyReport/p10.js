$(function() {
	//p10.init();
});

var p10 = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(showFlag){
		if(showFlag){
			$("div.load_p10").show();
			$("#floatingprofitstatisticsChartContainer_10").hide();
		}else{
			$("div.load_p10").hide();
			$("#floatingprofitstatisticsChartContainer_10").show();
		}
	},
	/**
	 * 初始化
	 */
	init:function(){
		p10.showOrHideLoadingDiv(true);
		var url = BASE_PATH + 'tradeBordereauxController/findDailyreportList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").val();
		queryParams['endTime'] = $("#endTimeSearch").val();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['detailed'] = "0";
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data :queryParams,
			success : function(rows) {
				p10.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4floatingprofitRatioArray = new Array();
				var gts2floatingprofitRatioArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;
						var mt4floatingprofitRatio = rows[i].mt4floatingprofitRatio.replace("%","");
						mt4floatingprofitRatioArray[j] = parseFloat(mt4floatingprofitRatio);
						var gts2floatingprofitRatio = rows[i].gts2floatingprofitRatio.replace("%","");
						gts2floatingprofitRatioArray[j] = parseFloat(gts2floatingprofitRatio);
						j--;
					}					
				}
				p10.showChart(exectimeArray, mt4floatingprofitRatioArray,gts2floatingprofitRatioArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, mt4floatingprofitRatioArray,gts2floatingprofitRatioArray){
        var myChart = echarts.init(document.getElementById('floatingprofitstatisticsChartContainer_10'), 'shine');
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
    		            saveAsImage: {show: true,name:"浮盈比例图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4浮动盈亏比例','GTS2浮动盈亏比例']
    		    },
    		    xAxis: [
    		        {
    		            type: 'category',
    		            data: exectimeArray,
    		            axisLabel: {
    		            	interval:0,
    			            rotate: 90
    		            }
    		        }
    		    ],
    		    yAxis: [
    		        {
    		            type: 'value',
    		            name: '浮盈比例',
    		            position: 'left',
    		            axisLabel: {
    		                formatter: '{value}%'
    		            }
    		        }
    		    ],
    		    series: [
    		        {
    		            name:'MT4浮动盈亏比例',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:mt4floatingprofitRatioArray
    		        },
    		        {
    		            name:'GTS2浮动盈亏比例',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:gts2floatingprofitRatioArray
    		        }
    		    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}