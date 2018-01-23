$(function() {
	
});

var averageTransactionVolumeStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.averageTransactionVolumeStatisticsDiv").show();
		}else{
			$("div.averageTransactionVolumeStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		averageTransactionVolumeStatisticsChart.showOrHideLoadingDiv(true);
		// 获取表格数据
		var rows = $('#' + averageTransactionVolumeStatistics.dataGridId).datagrid('getRows');
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
		// 显示图表
		averageTransactionVolumeStatisticsChart.showOrHideLoadingDiv(false);
		averageTransactionVolumeStatisticsChart.showChart(exectimeArray, mt4avgtransvolumeArray,gts2avgtransvolumeArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray, mt4avgtransvolumeArray,gts2avgtransvolumeArray){
        var myChart = echarts.init(document.getElementById('averageTransactionVolumeStatisticsChartContainer'), 'shine');
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
    		            data: exectimeArray
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
    		            data:mt4avgtransvolumeArray
    		        },
    		        {
    		            name:'GTS2人均交易手数',
    		            type:'bar',
    		            yAxisIndex: 0,
    		            data:gts2avgtransvolumeArray
    		        }
    		    ]
    		};
        // 显示图表。
        myChart.setOption(option);
	}
}

