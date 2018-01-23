$(function() {
	portal.init();
});

var portal = {
	/**
	 * 容器定义
	 */
	portalId_1 : "portalPanel_1",
	portalId_2 : "portalPanel_2",
	/**
	 * 所在容器的title
	 */
	portalPanelTitle_1 : "portalPanelTitle_1",
	portalPanelTitle_2 : "portalPanelTitle_2",
	/**
	 * 所在容器的列
	 */
	columnIndex_1 : 0,
	columnIndex_2 : 0,
	/**
	 * 面板定义
	 */
	panels: [
  		{id:'p1', title:'交易人数',detailTitle:'交易人数', height:450, closable:false, collapsible:true, tools:'#panelBar1'
  			, href: "page/trade/tradebiweeklyReport/p1.jsp", detailHref: "tradeBordereauxController/dealprofitdetailUseramountPage"
  				, onLoad:function(){
  					p1.init();
  				}},
  		{id:'p2', title:'交易次数',detailTitle:'交易次数', height:450, closable:false, collapsible:true, tools:'#panelBar2'
  			, href: "page/trade/tradebiweeklyReport/p2.jsp", detailHref: "tradeBordereauxController/dealprofitdetailDealamountPage"
  				, onLoad:function(){
  					p2.init();
  	  			}},
  		{id:'p3', title:'伦敦金平仓手数分布',detailTitle:'伦敦金平仓手数分布', height:450, closable:false, collapsible:true, tools:'#panelBar3'
  			, href: "page/trade/tradebiweeklyReport/p3.jsp", detailHref: "tradeBordereauxController/dealcateGoldPage"
  	  	  		, onLoad:function(){
  	  	  		   p3.init();
  	    	    }},
  		{id:'p4', title:'伦敦银平仓手数分布',detailTitle:'伦敦银平仓手数分布', height:450, closable:false, collapsible:true, tools:'#panelBar4'
  			, href: "page/trade/tradebiweeklyReport/p4.jsp", detailHref: "tradeBordereauxController/dealcateXaucnhPage"
  				, onLoad:function(){
  					p4.init();
  				}},
  		{id:'p5', title:'人民币金平仓手数分布', detailTitle:'人民币金平仓手数分布',height:450, closable:false, collapsible:true, tools:'#panelBar5'
  			, href: "page/trade/tradebiweeklyReport/p5.jsp", detailHref: "tradeBordereauxController/dealcateSilverPage"
  				, onLoad:function(){
  					p5.init();
  				}},
  		{id:'p6', title:'人民币银平仓手数分布',detailTitle:'人民币银平仓手数分布', height:450, closable:false, collapsible:true, tools:'#panelBar6'
  			, href: "page/trade/tradebiweeklyReport/p6.jsp", detailHref: "tradeBordereauxController/dealcateXagcnhPage"
  				, onLoad:function(){
  					p6.init();
  				}},
  		{id:'p7', title:'浮盈',detailTitle:'浮盈', height:450, closable:false, collapsible:true, tools:'#panelBar7'
  		  	, href: "page/trade/tradebiweeklyReport/p7.jsp", detailHref: "tradeBordereauxController/dailyreportFloatingprofitPage"
  		  		, onLoad:function(){ //浮动盈亏   ---> 结余表
  		  			p7.init();
  		  		}},
  		{id:'p8', title:'结余',detailTitle:'结余', height:450, closable:false, collapsible:true, tools:'#panelBar8'
  			, href: "page/trade/tradebiweeklyReport/p8.jsp", detailHref: "tradeBordereauxController/dailyreportPreviousbalancePage"
  		  		, onLoad:function(){//结余 ---->结余表
  		  		    p8.init();
  		  		}},
  		{id:'p9', title:'占用保证金比例',detailTitle:'占用保证金比例', height:450, closable:false, collapsible:true, tools:'#panelBar9'
  			, href: "page/trade/tradebiweeklyReport/p9.jsp", detailHref: "tradeBordereauxController/dailyreportMarginRatioPage"
  		  		, onLoad:function(){ //占用保证金  /总结余--->结余表
  		  		    p9.init();
  		  		}},
  		 {id:'p10', title:'浮盈比例',detailTitle:'浮盈比例', height:450, closable:false, collapsible:true, tools:'#panelBar10'
  	  	    , href: "page/trade/tradebiweeklyReport/p10.jsp", detailHref: "tradeBordereauxController/dailyreportFloatingprofitRatioPage"
  	  		  	, onLoad:function(){//浮动盈亏  /总结余--->结余表
  	  		  	    p10.init();
  	  		  	}},
  	  	{id:'p11', title:'MT4/GTS2报表',detailTitle:'MT4/GTS2报表', height:'auto', closable:false, collapsible:true, tools:'#panelBar11'
  	  	  	, href: "page/trade/tradebiweeklyReport/p11.jsp", detailHref: "tradeBordereauxController/findProfitdetailMT4AndGts2Page"
  	  	  		, onLoad:function(){
  	  	  		  	p11.init();
  	  	  	   }},
  	  	{id:'p12', title:'交易类别数据',detailTitle:'交易类别数据', height:'auto', closable:false, collapsible:true, tools:'#panelBar12'
  	  	  	  	, href: "page/trade/tradebiweeklyReport/p12.jsp", detailHref: "tradeBordereauxController/dealcategoryPage"
  	  	  	  	, onLoad:function(){
  	  	  	  		p12.init();
  	  	  	  }},
  	  	{id:'p13', title:'人均交易手数',detailTitle:'人均交易手数', height:450, closable:false, collapsible:true, tools:'#panelBar13'
    	  	    , href: "page/trade/tradebiweeklyReport/p13.jsp", detailHref: "tradeBordereauxController/averageTransactionVolumeStatisticsPage"
    	  		  	, onLoad:function(){//浮动盈亏  /总结余--->结余表
    	  		  	    p13.init();
    	  	  }},
	],
	/**
	 * 初始化
	 */
	init : function() {
		$('#' + portal.portalId_1).portal({
			onStateChange : function() {
				$('#' + portal.portalId_1).portal('resize');
			}
		});
		$('#' + portal.portalId_2).portal({
			onStateChange : function() {
				$('#' + portal.portalId_2).portal('resize');
			}
		});
		$('#' + portal.portalId_1).portal('resize');
		$('#' + portal.portalId_2).portal('resize');
		
		
		if($("#authFlag1").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p1');
		}
		if($("#authFlag2").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p2');
		}
		if($("#authFlag3").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p3');
		}
		if($("#authFlag4").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p4');
		}
		if($("#authFlag5").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p5');
		}
		if($("#authFlag6").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p6');
		}
		if($("#authFlag7").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p7');
		}
		if($("#authFlag8").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p8');
		}
		if($("#authFlag9").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p9');
		}
		if($("#authFlag10").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p10');
		}
		if($("#authFlag13").val() == 'true'){
			portal.addPanel(portal.portalId_1, 'p13');
		}
		
		if($("#authFlag1").val() == 'false'
			&&$("#authFlag2").val() == 'false'
			&&$("#authFlag3").val() == 'false'
			&&$("#authFlag4").val() == 'false'
			&&$("#authFlag5").val() == 'false'
			&&$("#authFlag6").val() == 'false'
			&&$("#authFlag7").val() == 'false'
			&&$("#authFlag8").val() == 'false'
			&&$("#authFlag9").val() == 'false'
			&&$("#authFlag10").val() == 'false'
			&&$("#authFlag13").val() == 'false'){
			$("#" + portal.portalId_1).hide();
			$("#" + portal.portalPanelTitle_1).hide();
		}
		
		if($("#authFlag11").val() == 'true'){
			portal.addPanel(portal.portalId_2, 'p11');
		}
		if($("#authFlag12").val() == 'true'){
			portal.addPanel(portal.portalId_2, 'p12');
		}
		if($("#authFlag11").val() == 'false'
			&&$("#authFlag12").val() == 'false'){
			$("#" + portal.portalId_2).hide();
			$("#" + portal.portalPanelTitle_2).hide();
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
			columnIndex = portal.columnIndex_2%1;
			portal.columnIndex_2++;
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
	 * 查旬
	 */
	find:function(){
		if($("#authFlag1").val() == 'true'){
			p1.init();
		}
		if($("#authFlag2").val() == 'true'){
			p2.init();
		}
		if($("#authFlag3").val() == 'true'){
			p3.init();
		}
		if($("#authFlag4").val() == 'true'){
			p4.init();
		}
		if($("#authFlag5").val() == 'true'){
			p5.init();
		}
		if($("#authFlag6").val() == 'true'){
			p6.init();
		}
		if($("#authFlag7").val() == 'true'){
			p7.init();
		}
		if($("#authFlag8").val() == 'true'){
			p8.init();
		}
		if($("#authFlag9").val() == 'true'){
			p9.init();
		}
		if($("#authFlag10").val() == 'true'){
			p10.init();
		}
		if($("#authFlag11").val() == 'true'){
			p11.init();
		}
		if($("#authFlag12").val() == 'true'){
			p12.init();
		}
		if($("#authFlag13").val() == 'true'){
			p13.init();
		}
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#searchForm').form('reset');
		
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
}
