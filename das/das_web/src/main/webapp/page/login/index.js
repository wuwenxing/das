$(function() {
	index.init();
	
	// 窗口大小监听事件
	$(window).resize(function(){
		$('#menu_tabs').tabs('resize');
	});
});

var index = {
	menuTabsIndex : 0,
	/**
	 * 初始化
	 */
	init:function(){
		// 加载菜单
		index.loadMenuAccordion();
		if($("#showGWBBPage").val() != 'N' && $("#curCompanyId").val() != '0'){
			// 添加官网页签
			index.addTabs('官网报表', 'HomePageChartController/page');
		}
	},
	/**
	 * 自适应iframe的高度
	 */
	iFrameHeight : function() {
		var tab = $('#menu_tabs').tabs('getSelected');
		var ifm = tab.find('iframe')[0];
		var oldHeight = ifm.height;
		if (ifm.contentDocument && ifm.contentDocument.body.offsetHeight){
			var height = ifm.contentDocument.body.offsetHeight;
			if(height < 768){
				height = 768;
			}else if(oldHeight == height){
				// 不处理
			}else{
				ifm.height = height + 20;
			}
		}else if(ifm.Document && ifm.Document.body.scrollHeight){
			var height = ifm.Document.body.scrollHeight
			if(height < 768){
				height = 768;
			}else if(oldHeight == height){
				// 不处理
			}else{
				ifm.height = height + 20;
			}
		}
	},
	/**
	 * 顶部页签加载
	 */
	getMenuList : function(){
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "LoginController/getMenuList",
			data : {
			},
			beforeSend: function(XMLHttpRequest){
		    },
			success : function(data) {
				if(null != data){
				}
			},
			error: function(data){
	        }
		});
	},
	/**
	 * Accordion加载
	 */
	loadMenuAccordion : function(){
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "LoginController/loadMenuAccordion/" + $("#topTagMenuCode").val(),
			data : {
				
			},
			beforeSend: function(XMLHttpRequest){
		    },
			success : function(data) {
				if(null != data){
					for (var i = 0; i < data.length; i++) {
						$("#menuAccordion").accordion('add', {
							iconCls: 'icon-folder',
							title : data[i].text,
							selected : false,
							content : index.westTreeInit(data[i].children)
						});
					}
					if(data.length > 0){
						// 默认展开第一个
						$("#menuAccordion").accordion({
							selected : 0
						});
					}
				}
			},
			error: function(data){
	        }
		});
	},
	/**
	 * Accordion移除
	 */
	removeMenuAccordion : function(){
		var panels = $("#menuAccordion").accordion('panels');
		var length = panels.length;
		for(var i=0; i<length; i++){
			$("#menuAccordion").accordion('remove', 0);
		}
	},
	/**
	 * 树初始化
	 */
	westTreeInit : function(data) {
		var $ulTree = $("<ul></ul>").tree({
			data : data,
			onClick : function(node){
				index.westTreeClick(node); 
			},
			onBeforeSelect : function(){
				// 先移除class=tree-node-selected的样式
				$("#menuAccordion div ul.tree div.tree-node").removeClass("tree-node-selected");
			}
		});
		return $ulTree;
	},
	/**
	 * 树单击事件
	 */
	westTreeClick : function(node) {
		if(node.attributes && node.attributes.url && node.attributes.url.length > 0){
			index.addTabs(node.text, node.attributes.url);
		}
	},
	/**
	 * 添加tab选项卡或更新选项卡
	 */
	addOrUpdateTabs : function(title, src) {
		$("#welcome").hide();
		var flag = $('#menu_tabs').tabs('exists', title);
		if (flag) {
			$('#menu_tabs').tabs('select', title);
			index.refreshTabs(src);
		} else {
			index.menuTabsIndex++;
			var tabsDivId = "tabsContent" + index.menuTabsIndex;
			$('#menu_tabs').tabs('add', {
				tools:[{
			        iconCls:'icon-mini-refresh',
			        handler:function(){
			        	index.refreshTabs();
			        }
			    }],
				title : title,
				content : "<div id=\""+tabsDivId+"\"></div>",
				border : false,
				closable : true
			});
			$('#menu_tabs').tabs('select', title);
			$("#menu_tabs div .panel:last .panel-body").css({'overflow':'hidden','position':'relative'});
			$("#" + tabsDivId).append("<div class=\"loadingDiv\" style=\"padding:15px; \"><img src = '"+ BASE_PATH +"img/icons/loading.gif' style=\"vertical-align: middle; padding-right: 5px;\" />正在处理，请稍待。。。</div>");
			var iframe = document.createElement("iframe");
			iframe.src = BASE_PATH + src;
			iframe.name = tabsDivId;
			iframe.frameBorder = 0;
			iframe.scrolling = "no";
			iframe.height = '768';
			iframe.width = '100%';
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					$("#" + tabsDivId + " .loadingDiv").remove();
					index.iFrameHeight();
				});
			} else {
				iframe.onload = function() {
					$("#" + tabsDivId + " .loadingDiv").remove();
					index.iFrameHeight();
				};
			}
			$("#" + tabsDivId).append(iframe);
		}
		// 返回页面顶部
		scroll(0,0);
	},
	/**
	 * 添加tab选项卡
	 */
	addTabs : function(title, src) {
		$("#welcome").hide();
		var flag = $('#menu_tabs').tabs('exists', title);
		if (flag) {
			$('#menu_tabs').tabs('select', title);
		} else {
			index.menuTabsIndex++;
			var tabsDivId = "tabsContent" + index.menuTabsIndex;
			$('#menu_tabs').tabs('add', {
				tools:[{
			        iconCls:'icon-mini-refresh',
			        handler:function(){
			        	index.refreshTabs();
			        }
			    }],
				title : title,
				content : "<div id=\""+tabsDivId+"\"></div>",
				border : false,
				closable : true
			});
			$('#menu_tabs').tabs('select', title);
			$("#menu_tabs div .panel:last .panel-body").css({'overflow':'hidden','position':'relative'});
			$("#" + tabsDivId).append("<div class=\"loadingDiv\" style=\"padding:15px; \"><img src = '"+ BASE_PATH +"img/icons/loading.gif' style=\"vertical-align: middle; padding-right: 5px;\" />正在处理，请稍待。。。</div>");
			var iframe = document.createElement("iframe");
			iframe.src = BASE_PATH + src;
			iframe.name = tabsDivId;
			iframe.frameBorder = 0;
			iframe.scrolling = "no";
			iframe.height = '768';
			iframe.width = '100%';
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					$("#" + tabsDivId + " .loadingDiv").remove();
					index.iFrameHeight();
				});
			} else {
				iframe.onload = function() {
					$("#" + tabsDivId + " .loadingDiv").remove();
					index.iFrameHeight();
				};
			}
			$("#" + tabsDivId).append(iframe);
		}
		// 返回页面顶部
		scroll(0,0);
	},
	/**
	 * 关闭指定Tabs
	 */
	closeTabs : function(title){
		$('#menu_tabs').tabs('close', title);
	},
	/**
	 * tabs关闭事件
	 */
	closeTabsEvent: function(){
		if($('#menu_tabs').tabs('tabs').length == 0){
			$("#welcome").show();
		}
	},
	/**
	 * 刷新Tabs
	 */
	refreshTabs : function(url){
		$("#welcome").hide();
		var tab = $('#menu_tabs').tabs('getSelected');
		var refreshIfram = tab.find('iframe')[0];
		var src = refreshIfram.src;
		if('' != url && null != url && 'undefined' != url){
			src = url;
		}
		var tabsDivId = refreshIfram.name;
		// ie6下会报错
		try{
			refreshIfram.remove();
		}catch(err){
			refreshIfram.removeNode(true);
		}

		$("#" + tabsDivId).append("<div class=\"loadingDiv\" style=\"padding:15px;\"><img src = '"+ BASE_PATH +"img/icons/loading.gif' style=\"vertical-align: middle; padding-right: 5px;\"/>正在处理，请稍待。。。</div>");
		var iframe = document.createElement("iframe");
		iframe.src = src;
		iframe.name = tabsDivId;
		iframe.frameBorder = 0;
		iframe.scrolling = "no";
		iframe.height = '768';
		iframe.width = '100%';
		if (iframe.attachEvent) {
			iframe.attachEvent("onload", function() {
				$("#" + tabsDivId + " .loadingDiv").remove();
				index.iFrameHeight();
			});
		} else {
			iframe.onload = function() {
				$("#" + tabsDivId + " .loadingDiv").remove();
				index.iFrameHeight();
			};
		}
		$("#" + tabsDivId).append(iframe);
	},
	/**
	 * 
	 */
	showProcess: function(){
		$.messager.progress();
	},
	/**
	 * 
	 */
	closeProcess: function(){
		$.messager.progress("close");
	}
	
}
