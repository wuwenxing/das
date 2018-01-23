$(function() {
	//p5.init();
});

var p5 = {
		/**
		 * 显示或隐藏load图标
		 */
		showOrHideLoadingDiv : function(showFlag){
			if(showFlag){
				$("div.load_p5").show();
				$("#dealcateSilverStatisticsChartContainer_5").hide();
			}else{
				$("div.load_p5").hide();
				$("#dealcateSilverStatisticsChartContainer_5").show();
			}
		},
	/**
	 * 初始化
	 */
	init:function(){
		p5.showOrHideLoadingDiv(true);
		var url = BASE_PATH + 'tradeBordereauxController/findDealcategoryAxuAxgcnhList';
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
				p5.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4volumeaxucnhArray = new Array();
				var gts2volumeaxucnhArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;				
						mt4volumeaxucnhArray[j] = rows[i].mt4volumeaxucnh;
						gts2volumeaxucnhArray[j] = rows[i].gts2volumeaxucnh;
						j--;
					}
				}
				p5.showChart(exectimeArray,mt4volumeaxucnhArray,gts2volumeaxucnhArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4volumeaxucnhArray,gts2volumeaxucnhArray){
        var myChart = echarts.init(document.getElementById('dealcateSilverStatisticsChartContainer_5'), 'shine');
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
    		            saveAsImage: {show: true,name:"人民币金平仓手数分布图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4人民币金平仓手数','GTS2人民币金平仓手数']
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
    		            name: '人民币金平仓手数分布 ',
    		            min: 0,
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series: [
 				        {
	 				        	name:'MT4人民币金平仓手数',
	 				            type:'bar',
	 				            smooth: false,
	 				            data:mt4volumeaxucnhArray
		        	    },
 				        {
		        	        	name:'GTS2人民币金平仓手数',
		    		            type:'bar',
		    		            smooth: false,
		    		            data:gts2volumeaxucnhArray
 				        }
 				    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}