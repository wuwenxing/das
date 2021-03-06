$(function() {
	//p7.init();
});

var p7 = {
		/**
		 * 显示或隐藏load图标
		 */
		showOrHideLoadingDiv : function(showFlag){
			if(showFlag){
				$("div.load_p7").show();
				$("#floatingprofitstatisticsChartContainer_7").hide();
			}else{
				$("div.load_p7").hide();
				$("#floatingprofitstatisticsChartContainer_7").show();
			}
		},
	/**
	 * 初始化
	 */
	init:function(){
		p7.showOrHideLoadingDiv(true);
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
				p7.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4floatingprofitArray = new Array();
				var gts2floatingprofitArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;
						mt4floatingprofitArray[j] = parseFloat(rows[i].mt4floatingprofit);
						gts2floatingprofitArray[j] = parseFloat(rows[i].gts2floatingprofit);
						j--;
					}
				}
				p7.showChart(exectimeArray,mt4floatingprofitArray,gts2floatingprofitArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4floatingprofitArray,gts2floatingprofitArray){
        var myChart = echarts.init(document.getElementById('floatingprofitstatisticsChartContainer_7'), 'shine');
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
		            saveAsImage: {show: true,name:"浮动盈亏图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['MT4浮动盈亏','GTS2浮动盈亏']
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
		            name: '浮动盈亏 ',
		            position: 'left',
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
    		            name:'MT4浮动盈亏',
    		            type:'bar',
    		            data:mt4floatingprofitArray
    		        },
    		        {
    		            name:'GTS2浮动盈亏',
    		            type:'bar',
    		            data:gts2floatingprofitArray
    		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}