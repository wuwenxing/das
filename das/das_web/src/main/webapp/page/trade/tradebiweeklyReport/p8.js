$(function() {
	//p8.init();
});

var p8 = {
		/**
		 * 显示或隐藏load图标
		 */
		showOrHideLoadingDiv : function(showFlag){
			if(showFlag){
				$("div.load_p8").show();
				$("#dailyreportstatisticsChartContainer_8").hide();
			}else{
				$("div.load_p8").hide();
				$("#dailyreportstatisticsChartContainer_8").show();
			}
		},
	/**
	 * 初始化
	 */
	init:function(){
		p8.showOrHideLoadingDiv(true);
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
				p8.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4balanceArray = new Array();
				var gts2balanceArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;
						mt4balanceArray[j] = rows[i].mt4balance;
						gts2balanceArray[j] = rows[i].gts2balance;
						j--;
					}
				}
				p8.showChart(exectimeArray, mt4balanceArray,gts2balanceArray);
				
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, mt4balanceArray,gts2balanceArray){
        var myChart = echarts.init(document.getElementById('dailyreportstatisticsChartContainer_8'), 'shine');
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
		            saveAsImage: {show: true,name:"结余图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['MT4结余','GTS2结余']
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
		            name: '结余 ',
		            position: 'left',
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    grid: { 
		    	left:'160px'
		    },
		    series: [
		            {
    		            name:'MT4结余',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:mt4balanceArray
    		        },
    		        {
    		            name:'GTS2结余',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:gts2balanceArray
    		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}