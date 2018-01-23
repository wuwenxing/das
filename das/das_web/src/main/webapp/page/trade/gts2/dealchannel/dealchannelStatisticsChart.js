$(function() {
	
});

var dealchannelStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.dealchannelStatisticsDiv").show();
		}else{
			$("div.dealchannelStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		dealchannelStatisticsChart.showOrHideLoadingDiv(true);
		
		// 获取表格数据
		var rows = $('#' + dealchannelStatistics.dataGridId).datagrid('getFooterRows');
		var androidamountArray = new Array();
		var iosamountArray = new Array();
		var systemamountArray = new Array();
		var pcamountArray = new Array();
		var otheramountArray = new Array();
		
		for(var i=0; i<rows.length; i++){
			androidamountArray[i] = rows[i].androidamount;
			iosamountArray[i] = rows[i].iosamount;
			systemamountArray[i] = rows[i].systemamount;
			pcamountArray[i] = rows[i].pcamount;
			otheramountArray[i] = rows[i].otheramount;
		}
		
		// 显示图表
		dealchannelStatisticsChart.showOrHideLoadingDiv(false);
		dealchannelStatisticsChart.showChart(androidamountArray,iosamountArray,systemamountArray,pcamountArray,otheramountArray);
	},
	/**
	 * 显示图表
	 */
	showChart : function (androidamountArray,iosamountArray,systemamountArray,pcamountArray,otheramountArray){
        var myChart = echarts.init(document.getElementById('dealchannelStatisticsChartContainer'), 'shine');
		var	option = {
			title : {
		        text: '',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        x : 'center',
		        data: ['Android','iPhone','系統','客户端','其它途径']
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"下单途径比例图表"}
		        }
		    },
		    series : [
		        {
		            name: '下单途径比例图表',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
			            {value:androidamountArray[0], name:'Android'},
		                {value:iosamountArray[0], name:'iPhone'},
		                {value:systemamountArray[0], name:'系統'},
		                {value:pcamountArray[0], name:'客户端'},
		                {value:otheramountArray[0], name:'其它途径'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
        // 显示图表。
        myChart.setOption(option);
	}
}

