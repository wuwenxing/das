<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/common/easyuiPortal.css?version=20170306" />
<script type="text/javascript" src="<%=basePath%>third/jquery-easyui-1.5.1/jquery.portal.js"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/tradePortal.js?version=20170306"></script>


<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_8_1.js?version=20170306"></script>
	
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_1_1.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_1_2.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_1_3.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_1_4.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_1_5.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_1_6.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_1_7.js?version=20170306"></script>


<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_3_1.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_3_2.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_3_3.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_3_4.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_3_5.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_3_6.js?version=20170306"></script>

<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_4_1.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_4_3.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_4_4.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_4_5.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_4_6.js?version=20170306"></script>
	
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_1.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_4.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_5.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_6.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_7.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_8.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_9.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_10.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_11.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_12.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_13.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_14.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_2_15.js?version=20170306"></script>

<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_5_1.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_9_1.js?version=20170306"></script>

</head>
<body>

<!-- 当前选择的ID -->
<input id="curCompanyId" type="hidden" value="${companyId}" />

<!-- portalPanel_8对应面板 -->
<input type="hidden" id="authFlag_8_1" value='true'>
<div id="panelBar_8_1">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_8_1');" title="刷新"></a>
</div>

<!-- portalPanel_1对应面板 -->
<input type="hidden" id="authFlag_1_1" value='<das:auth url="TradeController/top10WinnerLoserYearsList"/>'>
<input type="hidden" id="authFlag_1_3" value='<das:auth url="TradeController/top10CashinCashoutYearsList"/>'>
<input type="hidden" id="authFlag_1_5" value='<das:auth url="TradeController/top10TraderYearsList"/>'>
<input type="hidden" id="authFlag_1_6" value='<das:auth url="TradeController/top10VolumeStatisticsYearsList"/>'>
<input type="hidden" id="authFlag_1_7" value='<das:auth url="TradeController/top10PositionStatisticsYearsList"/>'>
<div id="panelBar_1_1">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_1_1');" title="刷新"></a>
</div>
<div id="panelBar_1_2">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_1_2');" title="刷新"></a>
</div>
<div id="panelBar_1_3">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_1_3');" title="刷新"></a>
</div>
<div id="panelBar_1_4">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_1_4');" title="刷新"></a>
</div>
<div id="panelBar_1_5">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_1_5');" title="刷新"></a>
</div>
<div id="panelBar_1_6">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_1_6');" title="刷新"></a>
</div>
<div id="panelBar_1_7">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_1_7');" title="刷新"></a>
</div>

<!-- portalPanel_3对应面板 -->
<input type="hidden" id="authFlag_3_1" value='<das:auth url="TradeController/findDaysOrMonthsSumByProfitdetail"/>'>
<div id="panelBar_3_1">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_3_1');" title="刷新"></a>
</div>
<div id="panelBar_3_2">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_3_2');" title="刷新"></a>
</div>
<div id="panelBar_3_3">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_3_3');" title="刷新"></a>
</div>
<div id="panelBar_3_4">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_3_4');" title="刷新"></a>
</div>
<div id="panelBar_3_5">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_3_5');" title="刷新"></a>
</div>
<div id="panelBar_3_6">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_3_6');" title="刷新"></a>
</div>

<!-- portalPanel_4对应面板 -->
<input type="hidden" id="authFlag_4_1" value='<das:auth url="TradeController/findDaysOrMonthsSumByCashandaccountdetail"/>'>
<div id="panelBar_4_1">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_4_1');" title="刷新"></a>
</div>
<div id="panelBar_4_3">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_4_3');" title="刷新"></a>
</div>
<div id="panelBar_4_4">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_4_4');" title="刷新"></a>
</div>
<div id="panelBar_4_5">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_4_5');" title="刷新"></a>
</div>
<div id="panelBar_4_6">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_4_6');" title="刷新"></a>
</div>

<!-- portalPanel_5对应面板 -->
<input type="hidden" id="authFlag_5_1" value='<das:auth url="tradeBordereauxController/findDealprofitdetailPageList"/>'>
<div id="panelBar_5_1">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_5_1');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_5_1');" title="查看详情"></a>
</div>

<!-- portalPanel_9对应面板 -->
<input type="hidden" id="authFlag_9_1" value='<das:auth url="TradeController/openAccountAndDepositSummary"/>'>
<div id="panelBar_9_1">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_9_1');" title="刷新"></a>
</div>

<!-- portalPanel_2对应面板 -->
<input type="hidden" id="authFlag_2_1" value='<das:auth url="tradeBordereauxController/findDealchannelList"/>'>
<input type="hidden" id="authFlag_2_2" value='true'>
<input type="hidden" id="authFlag_2_3" value='true'>
<input type="hidden" id="authFlag_2_4" value='<das:auth url="tradeBordereauxController/findBusinessDealprofitdetailList"/>'>
<input type="hidden" id="authFlag_2_5" value='<das:auth url="tradeBordereauxController/findBusinessDailyreportList"/>'>
<input type="hidden" id="authFlag_2_6" value='<das:auth url="tradeBordereauxController/findBusinessDealprofitdetailList"/>'>
<input type="hidden" id="authFlag_2_7" value='<das:auth url="TradeController/cashandaccountdetailStatisticsYearsList"/>'>
<input type="hidden" id="authFlag_2_8" value='<das:auth url="tradeBordereauxController/findDealprofithourList"/>'>
<input type="hidden" id="authFlag_2_10" value='<das:auth url="tradeBordereauxController/findAccountchannelList"/>'>
<input type="hidden" id="authFlag_2_12" value='true'>
<input type="hidden" id="authFlag_2_13" value='true'>
<div id="panelBar_2_1">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_1');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_1');" title="查看详情"></a>
</div>
<div id="panelBar_2_10">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_10');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_10');" title="查看详情"></a>
</div>
<div id="panelBar_2_11">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_11');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_11');" title="查看详情"></a>
</div>
<div id="panelBar_2_2">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_2');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_2');" title="查看详情"></a>
</div>
<div id="panelBar_2_3">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_3');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_3');" title="查看详情"></a>
</div>
<div id="panelBar_2_4">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_4');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_4');" title="查看详情"></a>
</div>
<div id="panelBar_2_5">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_5');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_5');" title="查看详情"></a>
</div>
<div id="panelBar_2_6">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_6');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_6');" title="查看详情"></a>
</div>
<div id="panelBar_2_7">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_7');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_7');" title="查看详情"></a>
</div>
<div id="panelBar_2_8">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_8');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_8');" title="查看详情"></a>
</div>
<div id="panelBar_2_9">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_9');" title="刷新"></a>
	<a href="javascript:void(0);" class="icon-view" onclick="portal.viewDetail('p_2_9');" title="查看详情"></a>
</div>
<div id="panelBar_2_12">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_12');" title="刷新"></a>
</div>
<div id="panelBar_2_13">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_13');" title="刷新"></a>
</div>
<div id="panelBar_2_14">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_14');" title="刷新"></a>
</div>
<div id="panelBar_2_15">
	<a href="javascript:void(0);" class="icon-mini-refresh" onclick="portal.refresh('p_2_15');" title="刷新"></a>
</div>

<!-- 交易记录\存取款及账户\当天净盈亏小时图（显示北京时间）部份 -->
<div style="border: 1px solid #d0d0d0;margin-top: 5px;">
	<div class="divTitle"></div>
	<div class="divTitle2">
		<form id="searchFormBusiness" class="searchForm" action="">
			<table style="margin-left: 10px; line-height: 70px;">
				<tr>
					<th>日期</th>
					<td style="width: 80px;">
						<input class="easyui-datebox" type="text" name="dateTimeSearch" id="dateTimeSearch" data-options="required:false, editable:false,onSelect:portal.onSelect" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>"/>
					</td>
					<td>
					   	<div class="searchButton" style="text-align: left;margin-left: 30px">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.findBusiness();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.resetBusinessDate();"
								data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.exportBusinessExcel();"
								data-options="iconCls:'icon-export', plain:'true'">导出</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="portalPanelTitle_8" class="section">
		<span>交易指标</span>
	</div>
	<div id="portalPanel_8" style="position: relative; height: auto;">
		<div style="width: 100%;"></div>
	</div>
	
	<div id="portalPanelTitle_3" class="section">
		<span>交易记录</span>
	</div>
	<div id="portalPanel_3" style="position: relative; height: auto;">
		<div style="width: 50%;"></div>
		<div style="width: 50%;"></div>
	</div>
	
	<div id="portalPanelTitle_4" class="section">
		<span>存取款及账户</span>
	</div>
	<div id="portalPanel_4" style="position: relative; height: auto;">
		<div style="width: 50%;"></div>
		<div style="width: 50%;"></div>
	</div>
	
	<div id="portalPanelTitle_6" class="section">
		<span>当天净盈亏小时图（显示北京时间）</span>
	</div>
	<div id="portalPanel_6" style="position: relative; height: auto;">
		<div style="width: 50%;"></div>
		<div style="width: 50%;"></div>
	</div>
	
	<div id="portalPanelTitle_7" class="section">
		<c:if test="${companyId == 2 }">
			<span>新开户途径比例、新激活途径比例</span>
		</c:if>
		<c:if test="${companyId != 2 }">
			<span>GTS2下单途径比例、新开户途径比例、新激活途径比例</span>
		</c:if>
	</div>
	<div id="portalPanel_7" style="position: relative; height: auto;">
		<div style="width: 50%;"></div>
		<div style="width: 50%;"></div>
	</div>
</div>


<!-- 十大排名部份 -->
<div style="border: 1px solid #d0d0d0;margin-top: 20px;">
	<div class="divTitle"></div>
	<div class="divTitle2">
	   <form id="searchFormRanking" class="searchForm" action="">
			<table style="margin-left: 10px; line-height: 70px;">
				<tr>
					<th>平台类型</th>
					<td>
						<select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<das:enumListTag dataList="${platformtypeEnum}"/>
						</select>
					</td>
					<td>
					   <div class="searchButton" style="text-align: left;margin-left: 30px">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.findRanking();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.resetRankingDate();"
								data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.exportRankingExcel();"
								data-options="iconCls:'icon-export', plain:'true'">导出</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="portalPanelTitle_1" class="section">
		<span>十大排名</span>
	</div>
	<div id="portalPanel_1" style="position: relative; height: auto;">
		<div style="width: 50%;"></div>
		<div style="width: 50%;"></div>
	</div>
</div>

<!-- 毛利、结余、交易手数、净入金部份\交易记录总结-->
<div style="border: 1px solid #d0d0d0;margin-top: 20px;">
	<div class="divTitle"></div>
	<div class="divTitle2">
		<form id="searchFormTransaction" class="searchForm" action="">
			<table style="margin-left: 10px; line-height: 70px;">
				<tr>
					<th>日期</th>
					<td style="width: 380px;">
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="yesterdayEnd" format="yyyy-MM-dd"/>"/>
					</td>
					<td>
						<div class="searchButton" style="text-align: left;margin-left: 30px">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.findTransaction();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.resetTransactionDate();"
								data-options="iconCls:'icon-empty', plain:'true'">重置</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="portalPanelTitle_2" class="section">
		<span>毛利、结余、交易手数、净入金、净持仓</span>
	</div>
	<div id="portalPanel_2" style="position: relative; height: auto;">
		<div style="width: 50%;"></div>
		<div style="width: 50%;"></div>
	</div>
	
	<div id="portalPanelTitle_5" class="section">
		<span>交易记录总结</span>
	</div>
	<div id="portalPanel_5" style="position: relative; height: auto;">
		<div style="width: 100%;"></div>
	</div>
</div>

<!-- 开户及存款总结部份 -->
<div style="border: 1px solid #d0d0d0;margin-top: 20px;">
	<div class="divTitle"></div>
	<div class="divTitle2">
	   <form id="searchFormPanel_9" class="searchForm" action="">
			<table style="margin-left: 10px; line-height: 70px;">
				<tr>
					<th>平台类型</th>
					<td>
						<select id="platformTypeSearch_1" name="platformTypeSearch_1" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<das:enumListTag dataList="${platformtypeEnum}"/>
						</select>
					</td>
					<td>
					   <div class="searchButton" style="text-align: left;margin-left: 30px">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.findPanel_9();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="portal.resetPanel_9();"
								data-options="iconCls:'icon-empty', plain:'true'">重置</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="portalPanelTitle_9" class="section">
		<span>开户及存款总结</span>
	</div>
	<div id="portalPanel_9" style="position: relative; height: auto;">
		<div style="width: 100%;"></div>
	</div>
</div>


</body>
</html>