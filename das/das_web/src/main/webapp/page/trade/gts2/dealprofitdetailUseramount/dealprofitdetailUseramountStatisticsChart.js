$(function() {
	
});

var dealprofitdetailUseramountStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealprofitdetailUseramountStatisticsDiv").show();
		}else{
			$("div.dealprofitdetailUseramountStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealprofitdetailUseramountStatisticsChart.showOrHideLoadingDiv(true);
		
		// 获取表格数据
		var rows = $('#' + dealprofitdetailUseramountStatistics.dataGridId).datagrid('getRows');
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
		// 显示图表
		dealprofitdetailUseramountStatisticsChart.showOrHideLoadingDiv(false);
		dealprofitdetailUseramountStatisticsChart.showChart(exectimeArray,mt4useramountArray,gts2useramountArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (exectimeArray,mt4useramountArray,gts2useramountArray){
        var myChart = echarts.init(document.getElementById('dealprofitdetailUseramountStatisticsChartContainer'), 'shine');
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
		            data: exectimeArray
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '交易人数 ',
		            //min: 0,
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

