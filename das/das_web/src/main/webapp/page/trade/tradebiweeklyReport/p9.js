$(function() {
	//p9.init();
});

var p9 = {
		/**
		 * 显示或隐藏load图标
		 */
		showOrHideLoadingDiv : function(showFlag){
			if(showFlag){
				$("div.load_p9").show();
				$("#marginstatisticsChartContainer_9").hide();
			}else{
				$("div.load_p9").hide();
				$("#marginstatisticsChartContainer_9").show();
			}
		},
	/**
	 * 初始化
	 */
	init:function(){
		p9.showOrHideLoadingDiv(true);
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
				p9.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4marginRatioArray = new Array();
				var gts2marginRatioArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;
						var mt4marginRatio = rows[i].marginRatio.replace("%","");
						mt4marginRatioArray[j] = parseFloat(mt4marginRatio);
						var gts2marginRatio = rows[i].gts2marginRatio.replace("%","");
						gts2marginRatioArray[j] = parseFloat(gts2marginRatio);
						j--;
					}					
				}
				p9.showChart(exectimeArray, mt4marginRatioArray,gts2marginRatioArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, mt4marginRatioArray,gts2marginRatioArray){
        var myChart = echarts.init(document.getElementById('marginstatisticsChartContainer_9'), 'shine');
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
    		            saveAsImage: {show: true,name:"保证金比例图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4保证金比例','GTS2保证金比例']
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
    		            name: '保证金比例',
    		            position: 'left',
    		            axisLabel: {
    		                formatter: '{value}%'
    		            }
    		        }
    		    ],
    		    series: [
    		        {
    		            name:'MT4保证金比例',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:mt4marginRatioArray
    		        },
    		        {
    		            name:'GTS2保证金比例',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:gts2marginRatioArray
    		        }
    		    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}