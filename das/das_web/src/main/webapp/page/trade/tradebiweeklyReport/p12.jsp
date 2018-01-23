<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p12.js?version=20170306"></script>
<script type="text/javascript">
	$(function() {
		p12.init();
	});
</script>
</head>
<body>
	<table id="dataGrid_12" data-options="toolbar: '#toolbar'"
		style="width: 100%; min-height: 90px; height: auto;"></table>
</body>
</html>
