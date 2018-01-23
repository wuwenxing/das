$(function() {
	//p1.init();
});

var p1 = {
		/**
		 * 显示或隐藏load图标
		 */
		showOrHideLoadingDiv : function(showFlag){
			if(showFlag){
				$("div.load_p1").show();
				$("#dealprofitdetailUseramountStatisticsChartContainer_1").hide();
			}else{
				$("div.load_p1").hide();
				$("#dealprofitdetailUseramountStatisticsChartContainer_1").show();
			}
		},
		
	/**
	 * 初始化
	 */
	init:function(){
		p1.showOrHideLoadingDiv(true);
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
				p1.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4useramountArray = new Array();
				var gts2useramountArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;
						mt4useramountArray[j] = rows[i].mt4useramount;
						gts2useramountArray[j] = rows[i].gts2useramount;
						j--;
					}
				}
				p1.showChart(exectimeArray,mt4useramountArray,gts2useramountArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4useramountArray,gts2useramountArray){
        var myChart = echarts.init(document.getElementById('dealprofitdetailUseramountStatisticsChartContainer_1'), 'shine');
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
		            saveAsImage: {show: true,name:"交易人数图表"}
		        }
		    },
		    legend: {
		        width: '450',
		        x : 'center',
		        data:['MT4交易人数','GTS2交易人数']
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
		            name: '交易人数 ',
		            axisLabel: {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'MT4交易人数',
		            type:'bar',
		            data:mt4useramountArray
		        },
		        {
		            name:'GTS2交易人数',
		            type:'bar',
		            data:gts2useramountArray
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}