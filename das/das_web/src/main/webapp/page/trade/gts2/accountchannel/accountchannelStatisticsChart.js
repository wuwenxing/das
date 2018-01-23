$(function() {
	
});

var accountchannelStatisticsChart = {
	/**
	 * 显示或隐藏load图标
	 */
	showOrHideLoadingDiv : function(type, showFlag){
		if(showFlag){
			$("div.accountchannelStatisticsDiv").show();
		}else{
			$("div.accountchannelStatisticsDiv").hide();
		}
	},
	
	/**
	 * 获取图表数据
	 */
	loadChartData : function() {
		accountchannelStatisticsChart.showOrHideLoadingDiv(true);
		var accountchannelType = $("#accountchannelType").val();
		// 获取表格数据
		var rows = $('#' + accountchannelStatistics.dataGridId).datagrid('getFooterRows');
		if(accountchannelType == 1){
			var webothers = new Array();
			var webpc = new Array();
			var loseinfo = new Array();				
			var others = new Array();
			
			for(var i=0; i<rows.length; i++){
				webothers[i] = rows[i].webothers;								
				webpc[i] = rows[i].webpc;
				loseinfo[i] = rows[i].loseinfo;	
				others[i] = rows[i].others;	
			}
			
			// 显示图表
			accountchannelStatisticsChart.showOrHideLoadingDiv(false);
			accountchannelStatisticsChart.showChart1(webothers,webpc,loseinfo,others);
		}
		if(accountchannelType == 2){
			var pc = new Array();
			var mobile = new Array();
			
			for(var i=0; i<rows.length; i++){
				pc[i] = rows[i].pc;
				mobile[i] = rows[i].mobile;
			}
			
			// 显示图表
			accountchannelStatisticsChart.showOrHideLoadingDiv(false);
			accountchannelStatisticsChart.showChart2(pc,mobile);
		}
		
	},
	/**
	 * 显示图表
	 */
	showChart1 : function (webothers,webpc,loseinfo,others){
        var myChart = echarts.init(document.getElementById('accountchannelStatisticsChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"新开户_激活途径比例图表"}
		        }
		    },
		    series : [
		        {
		            name: '新开户_激活途径比例图表',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
			            {value:webothers[0], name:'移动其它'},
		                {value:webpc[0], name:'PC'},
		                {value:loseinfo[0], name:'资料缺失'},
		                {value:others[0], name:'其它'}
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
	},
	
	/**
	 * 显示图表
	 */
	showChart2 : function (pc,mobile){
        var myChart = echarts.init(document.getElementById('accountchannelStatisticsChartContainer'), 'shine');
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
		            saveAsImage: {show: true,name:"新开户_激活途径比例图表"}
		        }
		    },
		    series : [
		        {
		            name: '新开户_激活途径比例图表',
		            type: 'pie',
		            radius : '40%',
		            center: ['50%', '60%'],
		            data:[
		                {value:pc[0], name:'PC'},
		                {value:mobile[0], name:'Moble'}
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

