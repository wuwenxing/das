$(function() {
	
});

/**
 * 下单途径比例图表
 */
var p_2_1 = {
	/**
	 * 初始化
	 */
	init:function(){
		var url = BASE_PATH + 'tradeBordereauxController/findDealchannelList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#dateTimeSearch").val();
		queryParams['endTime'] = $("#dateTimeSearch").val();
		queryParams['platformType'] = "GTS2";
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['detailed'] = "0";
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data :queryParams,
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_2_1', true);
		    },
			success : function(rows) {
		    	common.addOrRemoveLoading('p_2_1', false);
				var androidamount = 0;
				var iosamount = 0;
				var systemamount = 0;
				var pcamount = 0;
				var otheramount = 0;
				for(var i=0; i<rows.length; i++){
					androidamount += parseFloat(rows[i].androidamount);
					iosamount += parseFloat(rows[i].iosamount);
					systemamount += parseFloat(rows[i].systemamount);
					pcamount += parseFloat(rows[i].pcamount);
					otheramount += parseFloat(rows[i].otheramount);
				}
				p_2_1.showChart(androidamount,iosamount,systemamount,pcamount,otheramount);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (androidamount,iosamount,systemamount,pcamount,otheramount){
        var myChart = echarts.init(document.getElementById('dealchannelStatisticsChartContainer_1'), 'shine');
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
			            {value:androidamount, name:'Android'},
		                {value:iosamount, name:'iPhone'},
		                {value:systemamount, name:'系統'},
		                {value:pcamount, name:'客户端'},
		                {value:otheramount, name:'其它途径'}
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