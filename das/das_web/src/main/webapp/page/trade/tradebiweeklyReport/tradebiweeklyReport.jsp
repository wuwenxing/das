<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/common/easyuiPortal.css?version=20170306" />
<script type="text/javascript" src="<%=basePath%>third/jquery-easyui-1.5.1/jquery.portal.js"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/tradebiweeklyReport.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p1.js?version=20170306"></script>	
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p2.js?version=20170306"></script>
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p3.js?version=20170306"></script>
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p4.js?version=20170306"></script>
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p5.js?version=20170306"></script>
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p6.js?version=20170306"></script>
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p7.js?version=20170306"></script>	
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p8.js?version=20170306"></script>	
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p9.js?version=20170306"></script>
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p10.js?version=20170306"></script>	
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p11.js?version=20170306"></script>	
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p12.js?version=20170306"></script>
	<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p13.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<table class="commonTable">
				<tr>					
					<th>日期</th>
					<td colspan="4">
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="halfaMonth" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="yesterdayEnd" format="yyyy-MM-dd"/>"/>
					</td>
					
				</tr>
				<tr>
					<td colspan="4">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.reset();"
								data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>


<!-- portalPanel_1对应面板 -->
<input type="hidden" id="authFlag1" value='<das:auth url="tradeBordereauxController/findDealprofitdetailList"/>'>
<input type="hidden" id="authFlag2" value='<das:auth url="tradeBordereauxController/findDealprofitdetailList"/>'>
<input type="hidden" id="authFlag3" value='<das:auth url="tradeBordereauxController/findDealcategoryList"/>'>
<input type="hidden" id="authFlag4" value='<das:auth url="tradeBordereauxController/findDealcategoryList"/>'>
<input type="hidden" id="authFlag5" value='<das:auth url="tradeBordereauxController/findDealcategoryList"/>'>
<input type="hidden" id="authFlag6" value='<das:auth url="tradeBordereauxController/findDealcategoryList"/>'>
<input type="hidden" id="authFlag7" value='<das:auth url="tradeBordereauxController/findDailyreportList"/>'>
<input type="hidden" id="authFlag8" value='<das:auth url="tradeBordereauxController/findDailyreportList"/>'>
<input type="hidden" id="authFlag9" value='<das:auth url="tradeBordereauxController/findDailyreportList"/>'>
<input type="hidden" id="authFlag10" value='<das:auth url="tradeBordereauxController/findDailyreportList"/>'>
<input type="hidden" id="authFlag13" value='<das:auth url="tradeBordereauxController/findAverageTransactionVolumeList"/>'>
<div id="panelBar1">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p1');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p1');" title="查看详情"></a>
</div>
<div id="panelBar2">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p2');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p2');" title="查看详情"></a>
</div>
<div id="panelBar3">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p3');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p3');" title="查看详情"></a>
</div>
<div id="panelBar4">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p4');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p4');" title="查看详情"></a>
</div>
<div id="panelBar5">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p5');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p5');" title="查看详情"></a>
</div>
<div id="panelBar6">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p6');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p6');" title="查看详情"></a>
</div>
<div id="panelBar7">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p7');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p7');" title="查看详情"></a>
</div>
<div id="panelBar8">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p8');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p8');" title="查看详情"></a>
</div>
<div id="panelBar9">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p9');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p9');" title="查看详情"></a>
</div>
<div id="panelBar10">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p10');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p10');" title="查看详情"></a>
</div>
<div id="panelBar13">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p13');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p13');" title="查看详情"></a>
</div>


<!-- portalPanel_2对应面板 -->
<input type="hidden" id="authFlag11" value='<das:auth url="tradeBordereauxController/findDealprofitdetailMT4AndGts2PageList"/>'>
<input type="hidden" id="authFlag12" value='<das:auth url="tradeBordereauxController/findDealcategoryPageList"/>'>
<div id="panelBar11">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p11');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p11');" title="查看详情"></a>
</div>
<div id="panelBar12">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p12');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p12');" title="查看详情"></a>
</div>

<div id="portalPanelTitle_1" class="section">
	<span>交易人数、次数、伦敦金/银平仓手数、人民币金/银平仓手数、浮盈、结余、占用保证金比例、浮盈比例</span>
</div>
<div id="portalPanel_1" style="position: relative; height: auto;">
	<div style="width: 50%;"></div>
	<div style="width: 50%;"></div>
</div>

<div id="portalPanelTitle_2" class="section">
	<span>MT4/GTS2报表、交易类别数据</span>
</div>
<div id="portalPanel_2" style="position: relative; height: auto;">
	<div style="width: 100%;"></div>
</div>

</body>
</html>