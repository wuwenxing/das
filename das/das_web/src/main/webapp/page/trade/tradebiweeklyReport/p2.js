$(function() {
	//p2.init();
});

var p2 = {
		/**
		 * 显示或隐藏load图标
		 */
		showOrHideLoadingDiv : function(showFlag){
			if(showFlag){
				$("div.load_p2").show();
				$("#dealprofitdetailDealamounStatisticsChartContainer_2").hide();
			}else{
				$("div.load_p2").hide();
				$("#dealprofitdetailDealamounStatisticsChartContainer_2").show();
			}
		},
	/**
	 * 初始化
	 */
	init:function(){
		p2.showOrHideLoadingDiv(true);
		var url = BASE_PATH + 'tradeBordereauxController/findDealprofitdetailUseraAndDealamountList';
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
				p2.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4dealamountArray = new Array();
				var gts2dealamountArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;
						mt4dealamountArray[j] = rows[i].mt4dealamount;
						gts2dealamountArray[j] = rows[i].gts2dealamount;
						j--;
					}
				}
				p2.showChart(exectimeArray,mt4dealamountArray,gts2dealamountArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4dealamountArray,gts2dealamountArray){
        var myChart = echarts.init(document.getElementById('dealprofitdetailDealamounStatisticsChartContainer_2'), 'shine');
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
		            saveAsImage: {show: true,name:"交易次数图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['MT4交易次数','GTS2交易次数']
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
		            name: '交易次数 ',
		            //min: 0,
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'MT4交易次数',
		            type:'bar',
		            data:mt4dealamountArray
		        },
		        {
		            name:'GTS2交易次数',
		            type:'bar',
		            data:gts2dealamountArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}