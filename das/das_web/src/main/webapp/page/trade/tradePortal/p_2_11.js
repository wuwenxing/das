$(function() {
	
});

/**
 * 新激活途径比例
 */
var p_2_11 = {
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
		queryParams['type'] = "active";
		queryParams['detailed'] = "0";
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data :queryParams,
		    beforeSend: function(XMLHttpRequest){
		    	//覆盖父方法，不显示进度条，但显示loading效果
		    	common.addOrRemoveLoading('p_2_11', true);
		    },
			success : function(rows) {
		    	common.addOrRemoveLoading('p_2_11', false);
				var pc = 0;
				var mobile = 0;
				for(var i=0; i<rows.length; i++){
					pc += parseInt(rows[i].pc);
					mobile += parseInt(rows[i].mobile);
				}
				p_2_11.showChart(pc,mobile);
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 显示图表
	 */
	showChart : function (pc,mobile){
        var myChart = echarts.init(document.getElementById('accountchannelStatisticsChartContainer_11'), 'shine');
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
		        data: ['PC','Moble']
		    },
		    toolbox: {
		    	right: '20',
		    	show:true,
		        feature: {
	                mark:{show:true},
		            dataView: {show: false, readOnly: false},
		            magicType: {show: true, type: ['pie','funnel']},
		            restore: {show: false},
		            saveAsImage: {show: true,name:"新激活途径比例"}
		        }
		    },
		    series : [
		        {
		            name: '新激活途径比例',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
			            {value:pc, name:'PC'},
		                {value:mobile, name:'Moble'}
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