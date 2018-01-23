<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/appDataAnalysis/openSourceAnalysis.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<input type="hidden" id="platformTypeSearch" name="platformTypeSearch" value="${platformType}"/>
				<input type="hidden" id="dataTimeSearch" name="dataTimeSearch" value="${dataTime}"/>
				<input type="hidden" id="behaviorTypeSearch" name="behaviorTypeSearch" value="${behaviorType}"/>
				<table class="commonTable">
					<tr>
					       <th>日期</th>
						   <td>
							<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>"/>
							至
							<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
						  </td>
					 </tr>
					<tr>
						<td colspan="2">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataAnalysisDetails.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataAnalysisDetails.reset();"
									data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>

<div class="common-box-style">
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataAnalysisDetails.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
