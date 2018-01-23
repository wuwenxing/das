<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p8.js?version=20170306"></script>
<script type="text/javascript">
$(function() {
	p8.init();
});
</script>
</head>
<body>
<div class="load_p8" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
<div id="dailyreportstatisticsChartContainer_8" style="min-height: 400px; margin: 0 auto"></div>
</body>
</html>
