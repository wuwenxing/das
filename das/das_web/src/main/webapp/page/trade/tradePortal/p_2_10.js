$(function() {
	
});

/**
 * 新开户途径比例图表
 */
var p_2_10 = {
	/**
	 * 初始化
	 */
	init:function(){
		var url = BASE_PATH + 'tradeBordereauxController/findAccountchannelList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#dateTimeSearch").val();
		queryParams['endTime'] = $("#dateTimeSearch").val();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['type'] = "open";
		queryParams['detailed'] = "0";
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data :queryParams,
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_2_10', true);
		    },
			success : function(rows) {
		    	common.addOrRemoveLoading('p_2_10', false);
				var webothers = 0;
				var webpc = 0;
				var loseinfo = 0;				
				var others = 0;
				for(var i=0; i<rows.length; i++){
					webothers += parseInt(rows[i].webothers);								
					webpc += parseInt(rows[i].webpc);
					loseinfo += parseInt(rows[i].loseinfo);	
					others += parseInt(rows[i].others);					
				}
				
				p_2_10.showChart(webothers,webpc,loseinfo,others);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (webothers,webpc,loseinfo,others){
        var myChart = echarts.init(document.getElementById('accountchannelStatisticsChartContainer_10'), 'shine');
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
		        data: ['移动其它','PC','资料缺失','其它']
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"新开户途径比例图表"}
		        }
		    },
		    series : [
		        {
		            name: '新开户途径比例图表',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
			            {value:webothers, name:'移动其它'},
		                {value:webpc, name:'PC'},
		                {value:loseinfo, name:'资料缺失'},
		                {value:others, name:'其它'}
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