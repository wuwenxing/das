$(function() {
	//p3.init();
});
//伦敦金平仓手数分布
var p3 = {
		/**
		 * 显示或隐藏load图标
		 */
		showOrHideLoadingDiv : function(showFlag){
			if(showFlag){
				$("div.load_p3").show();
				$("#dealcateGoldStatisticsChartContainer_3").hide();
			}else{
				$("div.load_p3").hide();
				$("#dealcateGoldStatisticsChartContainer_3").show();
			}
		},
	/**
	 * 初始化
	 */
	init:function(){
		p3.showOrHideLoadingDiv(true);
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
				p3.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4volumeaxuArray = new Array();
				var gts2volumeaxuArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;				
						mt4volumeaxuArray[j] = rows[i].mt4volumeaxu;
						gts2volumeaxuArray[j] = rows[i].gts2volumeaxu;
						j--;
					}				
				}
				p3.showChart(exectimeArray,mt4volumeaxuArray,gts2volumeaxuArray);
				
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4volumeaxuArray,gts2volumeaxuArray){
        var myChart = echarts.init(document.getElementById('dealcateGoldStatisticsChartContainer_3'), 'shine');
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
    		            saveAsImage: {show: true,name:"伦敦金平仓手数分布图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4伦敦金平仓手数','GTS2伦敦金平仓手数']
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
    		            name: '伦敦金平仓手数分布 ',
    		            min: 0,
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series: [
    				        {
			        			name:'MT4伦敦金平仓手数',
					            type:'bar',
					            smooth: false,
					            data:mt4volumeaxuArray	
		        	        },
    				        {
		        	        	name:'GTS2伦敦金平仓手数',
		    		            type:'bar',
		    		            smooth: false,
		    		            data:gts2volumeaxuArray
    				        }
    				    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}