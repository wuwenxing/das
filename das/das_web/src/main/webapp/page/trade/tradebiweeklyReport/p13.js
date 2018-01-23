$(function() {
	//p13.init();
});

var p13 = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(showFlag){
		if(showFlag){
			$("div.load_p13").show();
			$("#averageTransactionVolumestatisticsChartContainer_13").hide();
		}else{
			$("div.load_p13").hide();
			$("#averageTransactionVolumestatisticsChartContainer_13").show();
		}
	},
	/**
	 * 初始化
	 */
	init:function(){
		p13.showOrHideLoadingDiv(true);
		var url = BASE_PATH + 'tradeBordereauxController/findAverageTransactionVolumeList';
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
				p13.showOrHideLoadingDiv(false);
				var exectimeArray = new Array();
				var mt4avgtransvolumeArray = new Array();
				var gts2avgtransvolumeArray = new Array();
				if(!common.isBlank(rows)){
					var j = rows.length-1;
					for(var i=0; i<rows.length; i++){
						exectimeArray[j] = rows[i].exectime;
						var mt4avgtransvolume = rows[i].mt4avgtransvolume.replace("%","");
						var gts2avgtransvolume = rows[i].gts2avgtransvolume.replace("%","");
						mt4avgtransvolumeArray[j] = parseFloat(mt4avgtransvolume);
						gts2avgtransvolumeArray[j] = parseFloat(gts2avgtransvolume);
						j--;
					}					
				}
				p13.showChart(exectimeArray, mt4avgtransvolumeArray,gts2avgtransvolumeArray);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, mt4avgtransvolumeArray,gts2avgtransvolumeArray){
        var myChart = echarts.init(document.getElementById('averageTransactionVolumestatisticsChartContainer_13'), 'shine');
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
    		            saveAsImage: {show: true,name:"人均交易手数图表"}
    		        }
    		    },
    		    legend: {
    		        width: '450',
    		        x : 'center',
    		        data:['MT4人均交易手数','GTS2人均交易手数']
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
    		            name: '人均交易手数',
    		            position: 'left',
    		            axisLabel: {
    		                formatter: '{value}%'
    		            }
    		        }
    		    ],
    		    series: [
    		        {
    		            name:'MT4人均交易手数',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            smooth: false,
    		            data:mt4avgtransvolumeArray
    		        },
    		        {
    		            name:'GTS2人均交易手数',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            smooth: false,
    		            data:gts2avgtransvolumeArray
    		        }
    		    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}