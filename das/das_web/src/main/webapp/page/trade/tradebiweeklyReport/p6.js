$(function() {
	//p6.init();
});

var p6 = {
		/**
		 * 显示或隐藏load图标
		 */
		showOrHideLoadingDiv : function(showFlag){
			if(showFlag){
				$("div.load_p6").show();
				$("#dealcateXagcnhStatisticsChartContainer_6").hide();
			}else{
				$("div.load_p6").hide();
				$("#dealcateXagcnhStatisticsChartContainer_6").show();
			}
		},
	/**
	 * 初始化
	 */
	init:function(){
		p6.showOrHideLoadingDiv(true);
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
				p6.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4volumeaxgcnhArray = new Array();
				var gts2volumeaxgcnhArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;				
						mt4volumeaxgcnhArray[j] = rows[i].mt4volumeaxgcnh;
						gts2volumeaxgcnhArray[j] = rows[i].gts2volumeaxgcnh;
						j--;
					}
				}
				p6.showChart(exectimeArray,mt4volumeaxgcnhArray,gts2volumeaxgcnhArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4volumeaxgcnhArray,gts2volumeaxgcnhArray){
        var myChart = echarts.init(document.getElementById('dealcateXagcnhStatisticsChartContainer_6'), 'shine');
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
    		            saveAsImage: {show: true,name:"人民币银平仓手数分布图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4人民币银平仓手数','GTS2人民币银平仓手数']
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
    		            name: '人民币银平仓手数分布 ',
    		            min: 0,
    		            axisLabel: {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series: [
 				        {
	 				        	name:'MT4人民币银平仓手数',
	 				            type:'bar',
	 				            smooth: false,
	 				            data:mt4volumeaxgcnhArray	
		        	    },
 				        {
		        	        	name:'GTS2人民币银平仓手数',
		    		            type:'bar',
		    		            smooth: false,
		    		            data:gts2volumeaxgcnhArray
 				        }
 				    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}