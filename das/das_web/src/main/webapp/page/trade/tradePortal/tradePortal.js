$(function() {
	portal.init();
});

var portal = {
	/**
	 * 容器定义
	 */
	portalId_1 : "portalPanel_1",
	portalId_2 : "portalPanel_2",
	portalId_3 : "portalPanel_3",
	portalId_4 : "portalPanel_4",
	portalId_5 : "portalPanel_5",
	portalId_6 : "portalPanel_6",
	portalId_7 : "portalPanel_7",
	portalId_8 : "portalPanel_8",
	portalId_9 : "portalPanel_9",
	/**
	 * 所在容器的title
	 */
	portalPanelTitle_1 : "portalPanelTitle_1",
	portalPanelTitle_2 : "portalPanelTitle_2",
	portalPanelTitle_3 : "portalPanelTitle_3",
	portalPanelTitle_4 : "portalPanelTitle_4",
	portalPanelTitle_5 : "portalPanelTitle_5",
	portalPanelTitle_6 : "portalPanelTitle_6",
	portalPanelTitle_7 : "portalPanelTitle_7",
	portalPanelTitle_8 : "portalPanelTitle_8",
	portalPanelTitle_9 : "portalPanelTitle_9",
	/**
	 * 所在容器的列
	 */
	columnIndex_1 : 0,
	columnIndex_2 : 0,
	columnIndex_3 : 0,
	columnIndex_4 : 0,
	columnIndex_5 : 0,
	columnIndex_6 : 0,
	columnIndex_7 : 0,
	columnIndex_8 : 0,
	columnIndex_9 : 0,
	/**
	 * 面板定义
	 */
	panels: [
   		{id:'p_1_1', title:'十大赢家',detailTitle:'十大赢家',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_1_1'
  			, href: "page/trade/tradePortal/p_1_1.jsp", detailHref: ""
  				, onLoad:function(){
  					if($("#authFlag_1_1").val() == 'true'){
  						p_1_1.init();
  					}
  				}},
  		{id:'p_1_2', title:'十大亏损',detailTitle:'十大亏损',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_1_2'
  			, href: "page/trade/tradePortal/p_1_2.jsp", detailHref: ""
  				, onLoad:function(){
  					if($("#authFlag_1_1").val() == 'true'){
  						p_1_2.init();
  					}
  				}},
  		{id:'p_1_3', title:'十大存款',detailTitle:'十大存款',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_1_3'
			, href: "page/trade/tradePortal/p_1_3.jsp", detailHref: ""
				, onLoad:function(){
					if($("#authFlag_1_3").val() == 'true'){
						p_1_3.init();
					}
				}},
  		{id:'p_1_4', title:'十大取款',detailTitle:'十大取款',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_1_4'
			, href: "page/trade/tradePortal/p_1_4.jsp", detailHref: ""
				, onLoad:function(){
					if($("#authFlag_1_3").val() == 'true'){
						p_1_4.init();
					}
				}},
  		{id:'p_1_5', title:'十大交易量客戶',detailTitle:'十大交易量客戶',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_1_5'
			, href: "page/trade/tradePortal/p_1_5.jsp", detailHref: ""
				, onLoad:function(){
					if($("#authFlag_1_5").val() == 'true'){
						p_1_5.init();
					}
				}},
  		{id:'p_1_6', title:'交易统计-手数',detailTitle:'交易统计-手数',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_1_6'
			, href: "page/trade/tradePortal/p_1_6.jsp", detailHref: ""
				, onLoad:function(){
					if($("#authFlag_1_6").val() == 'true'){
						p_1_6.init();
					}
				}},
  		{id:'p_1_7', title:'交易统计-持仓时间',detailTitle:'交易统计-持仓时间',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_1_7'
			, href: "page/trade/tradePortal/p_1_7.jsp", detailHref: ""
				, onLoad:function(){
					if($("#authFlag_1_7").val() == 'true'){
						p_1_7.init();
					}
				}},
		
				
		{id:'p_3_1', title:'交易状况',detailTitle:'交易状况',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_3_1'
  			, href: "page/trade/tradePortal/p_3_1.jsp", detailHref: ""
  				, onLoad:function(){
					if($("#authFlag_3_1").val() == 'true'){
						p_3_1.init();
					}
  				}},
  		{id:'p_3_2', title:'MT4',detailTitle:'MT4',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_3_2'
  			, href: "page/trade/tradePortal/p_3_2.jsp", detailHref: ""
  				, onLoad:function(){
					if($("#authFlag_3_1").val() == 'true'){
						p_3_2.init();
					}
  				}},
  		{id:'p_3_3', title:'GTS2',detailTitle:'GTS2',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_3_3'
			, href: "page/trade/tradePortal/p_3_3.jsp", detailHref: ""
				, onLoad:function(){
					if($("#authFlag_3_1").val() == 'true'){
						p_3_3.init();
					}
				}},
  		{id:'p_3_4', title:'市场状况',detailTitle:'市场状况',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_3_4'
			, href: "page/trade/tradePortal/p_3_4.jsp", detailHref: ""
				, onLoad:function(){
					if($("#authFlag_3_1").val() == 'true'){
						p_3_4.init();
					}
				}},
  		{id:'p_3_5', title:'MT5',detailTitle:'MT5',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_3_5'
  			, href: "page/trade/tradePortal/p_3_5.jsp", detailHref: ""
  				, onLoad:function(){
					if($("#authFlag_3_1").val() == 'true'){
						p_3_5.init();
					}
  				}},
  		{id:'p_3_6', title:'GTS',detailTitle:'GTS',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_3_6'
			, href: "page/trade/tradePortal/p_3_6.jsp", detailHref: ""
				, onLoad:function(){
					if($("#authFlag_3_1").val() == 'true'){
						p_3_6.init();
					}
				}},
		
				
		{id:'p_4_1', title:'客户状况',detailTitle:'客户状况',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_4_1'
  			, href: "page/trade/tradePortal/p_4_1.jsp", detailHref: ""
  				, onLoad:function(){
  					if($("#authFlag_4_1").val() == 'true'){
  	  					p_4_1.init();
  					}
  				}},
  		{id:'p_4_3', title:'MT4',detailTitle:'MT4',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_4_3'
			, href: "page/trade/tradePortal/p_4_3.jsp", detailHref: ""
				, onLoad:function(){
  					if($("#authFlag_4_1").val() == 'true'){
  						p_4_3.init();
  					}
				}},
  		{id:'p_4_4', title:'GTS2',detailTitle:'GTS2',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_4_4'
			, href: "page/trade/tradePortal/p_4_4.jsp", detailHref: ""
				, onLoad:function(){
  					if($("#authFlag_4_1").val() == 'true'){
  						p_4_4.init();
  					}
				}},
  		{id:'p_4_5', title:'MT5',detailTitle:'MT5',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_4_5'
			, href: "page/trade/tradePortal/p_4_5.jsp", detailHref: ""
				, onLoad:function(){
  					if($("#authFlag_4_1").val() == 'true'){
  						p_4_5.init();
  					}
				}},
  		{id:'p_4_6', title:'GTS',detailTitle:'GTS',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_4_6'
			, href: "page/trade/tradePortal/p_4_6.jsp", detailHref: ""
				, onLoad:function(){
  					if($("#authFlag_4_1").val() == 'true'){
  						p_4_6.init();
  					}
				}},
				

  	    {id:'p_5_1', title:'交易记录总结',detailTitle:'交易记录总结', height:'auto', closable:false, collapsible:true, tools:'#panelBar_5_1'
  	  	  	  	, href: "page/trade/tradePortal/p_5_1.jsp", detailHref: "tradeBordereauxController/dealprofitdetailPage"
  	  	  	  	, onLoad:function(){
	  	  			if($("#authFlag_5_1").val() == 'true'){
	  	  	  	  		p_5_1.init();
	  	  			}
  	  	  	  	}},
	    {id:'p_9_1', title:'开户及存款总结',detailTitle:'开户及存款总结', height:'auto', closable:false, collapsible:true, tools:'#panelBar_9_1'
  	  	  	, href: "page/trade/tradePortal/p_9_1.jsp", detailHref: "tradeBordereauxController/dealprofitdetailPage"
  	  	  	, onLoad:function(){
  	  			if($("#authFlag_9_1").val() == 'true'){
  	  	  	  		p_9_1.init();
  	  			}
  	  	  	}},
		
  	  	  	  	
  		{id:'p_2_1', title:'GTS2下单途径比例',detailTitle:'下单途径比例',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_1'
  			, href: "page/trade/tradePortal/p_2_1.jsp", detailHref: "tradeBordereauxController/dealchannelPage"
  				, onLoad:function(){
					if($("#authFlag_2_1").val() == 'true'){
						p_2_1.init();
					}
  				}},
  		{id:'p_2_10', title:'新开户途径比例',detailTitle:'新开户途径比例',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_10'
  		  	  , href: "page/trade/tradePortal/p_2_10.jsp", detailHref: "tradeBordereauxController/accountchannelPage?accountchannelType=1"
  		  	  	, onLoad:function(){
  					if($("#authFlag_2_10").val() == 'true'){
  		  	  			p_2_10.init();
  					}
  		  	  	}},
  		 {id:'p_2_11', title:'新激活途径比例',detailTitle:'新激活途径比例',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_11'
  		  	  , href: "page/trade/tradePortal/p_2_11.jsp", detailHref: "tradeBordereauxController/accountchannelPage?accountchannelType=2"
  		  	  	  , onLoad:function(){
    					if($("#authFlag_2_10").val() == 'true'){
    						p_2_11.init();
    					}
  		  	  	 }},
  		{id:'p_2_2', title:'新开户客户途径', height:450, closable:false, collapsible:true, tools:'#panelBar_2_2'
  			, href: "", detailHref: ""
  				, onLoad:function(){
  					
  	  			}},
  		{id:'p_2_3', title:'新激活客户途径', height:450, closable:false, collapsible:true, tools:'#panelBar_2_3'
  			, href: "", detailHref: ""
  	  	  		, onLoad:function(){
  	    	  		
  	    	    }},
  		{id:'p_2_4', title:'毛利',detailTitle:'毛利',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_4'
  			, href: "page/trade/tradePortal/p_2_4.jsp", detailHref: "tradeBordereauxController/dealprofitdetailGrossprofitPage"
  				, onLoad:function(){
  					if($("#authFlag_2_4").val() == 'true'){
  						p_2_4.init();
  					}
  				}},
  		{id:'p_2_5', title:'结余',detailTitle:'结余',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_5'
  			, href: "page/trade/tradePortal/p_2_5.jsp", detailHref: "tradeBordereauxController/dailyreportPage"
  				, onLoad:function(){
  					if($("#authFlag_2_5").val() == 'true'){
  						p_2_5.init();
  					}
  				}},
  		{id:'p_2_6', title:'交易手数',detailTitle:'交易手数',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_6'
  			, href: "page/trade/tradePortal/p_2_6.jsp", detailHref: "tradeBordereauxController/dealprofitdetailVolumePage"
  				, onLoad:function(){
  					if($("#authFlag_2_6").val() == 'true'){
  						p_2_6.init();
  					}
  				}},
  		{id:'p_2_7', title:'净入金',detailTitle:'净入金',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_7'
  		  	, href: "page/trade/tradePortal/p_2_7.jsp", detailHref: "TradeController/cashandaccountdetailStatisticsYearsPage"
  		  		, onLoad:function(){
  					if($("#authFlag_2_7").val() == 'true'){
  						p_2_7.init();
  					}
  		  		}},
  		{id:'p_2_8', title:'MT4净盈亏图',detailTitle:'公司盈亏',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_8'
  			, href: "page/trade/tradePortal/p_2_8.jsp", detailHref: "tradeBordereauxController/dealprofithourPage?platformType=MT4"
  		  		, onLoad:function(){
  					if($("#authFlag_2_8").val() == 'true'){
  						p_2_8.init();
  					}
  		  		}},
  		{id:'p_2_9', title:'GTS2净盈亏图',detailTitle:'公司盈亏',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_9'
  			, href: "page/trade/tradePortal/p_2_9.jsp", detailHref: "tradeBordereauxController/dealprofithourPage?platformType=GTS2"
  		  		, onLoad:function(){
  					if($("#authFlag_2_8").val() == 'true'){
  						p_2_9.init();
  					}
  		  		}},
  		{id:'p_2_14', title:'MT5净盈亏图',detailTitle:'公司盈亏',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_14'
  			, href: "page/trade/tradePortal/p_2_14.jsp", detailHref: "tradeBordereauxController/dealprofithourPage?platformType=MT5"
  		  		, onLoad:function(){
  					if($("#authFlag_2_8").val() == 'true'){
  						p_2_14.init();
  					}
  		  		}},
		{id:'p_2_15', title:'GTS净盈亏图',detailTitle:'公司盈亏',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_15'
			, href: "page/trade/tradePortal/p_2_15.jsp", detailHref: "tradeBordereauxController/dealprofithourPage?platformType=GTS"
		  		, onLoad:function(){
					if($("#authFlag_2_8").val() == 'true'){
						p_2_15.init();
					}
		  		}},
  		  		
  		  		
  		  		
  		{id:'p_2_12', title:'黄金净仓走势图',detailTitle:'黄金金净仓走势图',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_12'
  			, href: "page/trade/tradePortal/p_2_12.jsp", detailHref: ""
  		  		, onLoad:function(){
  					if($("#authFlag_2_12").val() == 'true'){
  						p_2_12.init();
  					}
  		  		}},
  		{id:'p_2_13', title:'白银净仓走势图',detailTitle:'白银净仓走势图',  height:450, closable:false, collapsible:true, tools:'#panelBar_2_13'
  			, href: "page/trade/tradePortal/p_2_13.jsp", detailHref: ""
  		  		, onLoad:function(){
  					if($("#authFlag_2_13").val() == 'true'){
  						p_2_13.init();
  					}
  		  		}},
		{id:'p_8_1', title:'交易指标',detailTitle:'交易指标',  height:'auto', closable:false, collapsible:true, tools:'#panelBar_8_1'
			, href: "page/trade/tradePortal/p_8_1.jsp", detailHref: ""
				, onLoad:function(){
					if($("#authFlag_8_1").val() == 'true'){
						p_8_1.init();
					}
				}}
	],
	/**
	 * 初始化
	 */
	init : function() {
		$('#' + portal.portalId_1).portal({
			onStateChange : function() {
				common.iFrameHeight();
			}
		});
		$('#' + portal.portalId_2).portal({
			onStateChange : function() {
				common.iFrameHeight();
			}
		});
		$('#' + portal.portalId_3).portal({
			onStateChange : function() {
				common.iFrameHeight();
			}
		});
		$('#' + portal.portalId_4).portal({
			onStateChange : function() {
				common.iFrameHeight();
			}
		});
		$('#' + portal.portalId_5).portal({
			onStateChange : function() {
				common.iFrameHeight();
			}
		});
		$('#' + portal.portalId_6).portal({
			onStateChange : function() {
				common.iFrameHeight();
			}
		});
		$('#' + portal.portalId_7).portal({
			onStateChange : function() {
				common.iFrameHeight();
			}
		});
		$('#' + portal.portalId_8).portal({
			onStateChange : function() {
				common.iFrameHeight();
			}
		});
		$('#' + portal.portalId_9).portal({
			onStateChange : function() {
				common.iFrameHeight();
			}
		});
		$('#' + portal.portalId_1).portal('resize');
		$('#' + portal.portalId_2).portal('resize');
		$('#' + portal.portalId_3).portal('resize');
		$('#' + portal.portalId_4).portal('resize');
		$('#' + portal.portalId_5).portal('resize');
		$('#' + portal.portalId_6).portal('resize');
		$('#' + portal.portalId_7).portal('resize');
		$('#' + portal.portalId_8).portal('resize');
		$('#' + portal.portalId_9).portal('resize');
		
		/**
		 * 交易指标
		 */
		if($("#authFlag_8_1").val() == 'true'){
			portal.addPanel(portal.portalId_8, 'p_8_1');
		}
		
		/**
		 * 交易记录
		 */
		if($("#authFlag_3_1").val() == 'true'){
			portal.addPanel(portal.portalId_3, 'p_3_1');
			if(COMPANY_ID == '2'){// 贵金属
				portal.addPanel(portal.portalId_3, 'p_3_2');
				portal.addPanel(portal.portalId_3, 'p_3_4');
				portal.addPanel(portal.portalId_3, 'p_3_5');
				portal.addPanel(portal.portalId_3, 'p_3_6');
			}else{
				portal.addPanel(portal.portalId_3, 'p_3_2');
				portal.addPanel(portal.portalId_3, 'p_3_4');
				portal.addPanel(portal.portalId_3, 'p_3_3');
			}
		}
		
		/**
		 * 存取款及账户
		 */
		if($("#authFlag_4_1").val() == 'true'){
			portal.addPanel(portal.portalId_4, 'p_4_1');
			if(COMPANY_ID == '2'){// 贵金属
				portal.addPanel(portal.portalId_4, 'p_4_3');
				portal.addPanel(portal.portalId_4, 'p_4_5');
				portal.addPanel(portal.portalId_4, 'p_4_6');
			}else{
				portal.addPanel(portal.portalId_4, 'p_4_3');
				portal.addPanel(portal.portalId_4, 'p_4_4');
			}
		}
		
		/**
		 * 当天净盈亏小时图（显示北京时间）
		 */
		if($("#authFlag_2_8").val() == 'true'){
			portal.addPanel(portal.portalId_6, 'p_2_8');
			if(COMPANY_ID == '2'){// 贵金属
				portal.addPanel(portal.portalId_6, 'p_2_14');
				portal.addPanel(portal.portalId_6, 'p_2_15');
			}else{
				portal.addPanel(portal.portalId_6, 'p_2_9');
			}
		}

		/**
		 * GTS2下单途径比例、新开户途径比例、新激活途径比例
		 */
		if($("#authFlag_2_1").val() == 'true'){
			if(COMPANY_ID == '2'){// 贵金属
				
			}else{
				portal.addPanel(portal.portalId_7, 'p_2_1');
			}
		}
		if($("#authFlag_2_10").val() == 'true'){
			portal.addPanel(portal.portalId_7, 'p_2_10');
			portal.addPanel(portal.portalId_7, 'p_2_11');
		}
		
		/**
		 * 十大排名
		 */
		if($("#authFlag_1_1").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p_1_1');
			portal.addPanel(portal.portalId_1, 'p_1_2');
		}
		if($("#authFlag_1_3").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p_1_3');
			portal.addPanel(portal.portalId_1, 'p_1_4');
		}
		if($("#authFlag_1_5").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p_1_5');
		}
		if($("#authFlag_1_6").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p_1_6');
		}
		if($("#authFlag_1_7").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p_1_7');
		}
		
		/**
		 * 毛利、结余、交易手数、净入金、净持仓
		 */
		if($("#authFlag_2_12").val() == 'true'){
			portal.addPanel(portal.portalId_2, 'p_2_12');
		}
		if($("#authFlag_2_13").val() == 'true'){
			portal.addPanel(portal.portalId_2, 'p_2_13');
		}
		if($("#authFlag_2_4").val() == 'true'){
			portal.addPanel(portal.portalId_2, 'p_2_4');
		}
		if($("#authFlag_2_5").val() == 'true'){
			portal.addPanel(portal.portalId_2, 'p_2_5');
		}
		if($("#authFlag_2_6").val() == 'true'){
			portal.addPanel(portal.portalId_2, 'p_2_6');
		}
		if($("#authFlag_2_7").val() == 'true'){
			portal.addPanel(portal.portalId_2, 'p_2_7');
		}

		/**
		 * 交易记录总结
		 */
		if($("#authFlag_5_1").val() == 'true'){
			portal.addPanel(portal.portalId_5, 'p_5_1');
		}
		
		/**
		 * 开户及存款总结
		 */
		if($("#authFlag_9_1").val() == 'true'){
			portal.addPanel(portal.portalId_9, 'p_9_1');
		}
		
	},
	/**
	 * 获取定义的面板
	 */
	getPanelOptions : function(id) {
		for (var i = 0; i < portal.panels.length; i++) {
			if (portal.panels[i].id == id) {
				return portal.panels[i];
			}
		}
		return undefined;
	},
	/**
	 * 所在容器的列
	 * @returns {Number}
	 */
	getColumnIndex : function(portalId){
		var columnIndex;
		if('portalPanel_1' == portalId){
			columnIndex = portal.columnIndex_1%2;
			portal.columnIndex_1++;
		}else if('portalPanel_2' == portalId){
			columnIndex = portal.columnIndex_2%2;
			portal.columnIndex_2++;
		}else if('portalPanel_3' == portalId){
			columnIndex = portal.columnIndex_3%2;
			portal.columnIndex_3++;
		}else if('portalPanel_4' == portalId){
			columnIndex = portal.columnIndex_4%2;
			portal.columnIndex_4++;
		}else if('portalPanel_5' == portalId){
			columnIndex = portal.columnIndex_5%1;
			portal.columnIndex_5++;
		}else if('portalPanel_6' == portalId){
			columnIndex = portal.columnIndex_6%2;
			portal.columnIndex_6++;
		}else if('portalPanel_7' == portalId){
			columnIndex = portal.columnIndex_7%2;
			portal.columnIndex_7++;
		}else if('portalPanel_8' == portalId){
			columnIndex = portal.columnIndex_8%1;
			portal.columnIndex_8++;
		}else if('portalPanel_9' == portalId){
			columnIndex = portal.columnIndex_9%1;
			portal.columnIndex_9++;
		}
		return columnIndex;
	},
	/**
	 * 往容器里面添加面板
	 * @param portalId 容器ID
	 * @param id 面板ID
	 */
	addPanel : function(portalId, id) {
		var options = portal.getPanelOptions(id);
		if (options) {
			var p = $('<div style=\"position: relative;\"/>').attr('id', options.id).appendTo('body');
			p.panel(options);
			$("#" + portalId).portal("add", {
				panel : p,
				columnIndex : portal.getColumnIndex(portalId)
			});
		}
	},
	/**
	 * 点击进入详情页签
	 */
	viewDetail : function(id){
		var options = portal.getPanelOptions(id);
		if (options) {
			parent.index.addOrUpdateTabs(options.detailTitle, options.detailHref);
		}
	},
	/**
	 * 刷新面板
	 * @param id 面板ID
	 */
	refresh : function(id){
		var options = portal.getPanelOptions(id);
		if (options) {
			options.onLoad();
		}
	},
	/**
	 * 查询
	 */
	findBusiness:function(){
		portal.refresh('p_8_1');
		
		portal.refresh('p_3_1');
		if(COMPANY_ID == '2'){// 贵金属
			portal.refresh('p_3_2');
			portal.refresh('p_3_4');
			portal.refresh('p_3_5');
			portal.refresh('p_3_6');
		}else{
			portal.refresh('p_3_2');
			portal.refresh('p_3_3');//gts2
			portal.refresh('p_3_4');
		}
		
		portal.refresh('p_4_1');
		if(COMPANY_ID == '2'){// 贵金属
			portal.refresh('p_4_3');
			portal.refresh('p_4_5');
			portal.refresh('p_4_6');
		}else{
			portal.refresh('p_4_3');
			portal.refresh('p_4_4');//gts2
		}
		
		portal.refresh('p_2_8');
		if(COMPANY_ID == '2'){// 贵金属
			portal.refresh('p_2_14');
			portal.refresh('p_2_15');
		}else{
			portal.refresh('p_2_9');
		}
		
		portal.refresh('p_2_1');
		portal.refresh('p_2_10');
		portal.refresh('p_2_11');
		
		portal.refresh('p_1_1');
		portal.refresh('p_1_2');
		portal.refresh('p_1_3');
		portal.refresh('p_1_4');
		portal.refresh('p_1_5');
		portal.refresh('p_1_6');
		portal.refresh('p_1_7');
	},
	/**
	 * 查询
	 */
	findRanking:function(){
		var date = $("#dateTimeSearch").val();
		portal.refresh('p_1_1');
		portal.refresh('p_1_2');
		portal.refresh('p_1_3');
		portal.refresh('p_1_4');
		portal.refresh('p_1_5');
		portal.refresh('p_1_6');
		portal.refresh('p_1_7');
	},
	/**
	 * 查询
	 */
	findTransaction:function(){
		portal.refresh('p_2_12');
		portal.refresh('p_2_13');
		portal.refresh('p_2_4');
		portal.refresh('p_2_5');
		portal.refresh('p_2_6');
		portal.refresh('p_2_7');

		portal.refresh('p_5_1');
		portal.refresh('p_9_1');
	},
	/**
	 * 查询
	 */
	findPanel_9:function(){
		portal.refresh('p_9_1');
	},
	/**
	 * reset条件查询
	 */
	resetBusinessDate: function(){
		$('#searchFormBusiness').form('reset');
		$('#dateTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		common.setEasyUiCss();
	},
	/**
	 * reset条件查询
	 */
	resetRankingDate: function(){
		$('#searchFormRanking').form('reset');
		common.setEasyUiCss();
	},
	/**
	 * reset条件查询
	 */
	resetTransactionDate: function(){
		$('#searchFormTransaction').form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		common.setEasyUiCss();
	},
	/**
	 * reset条件查询
	 */
	resetPanel_9: function(){
		$('#searchFormPanel_9').form('reset');
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportBusinessExcel : function(){
		var queryParams = {};
		queryParams['dateTime'] = $("#dateTimeSearch").val();
		var url = BASE_PATH + "TradeController/exportBusinessExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 导出excel
	 */
	exportRankingExcel : function(){
		var queryParams = {};
		queryParams['dateTime'] = $("#dateTimeSearch").val();
		queryParams['platformType'] = $("#platformTypeSearch").val();
		var url = BASE_PATH + "TradeController/exportRankingExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}
