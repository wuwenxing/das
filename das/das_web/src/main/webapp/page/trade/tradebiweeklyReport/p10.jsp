<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradebiweeklyReport/p10.js?version=20170306"></script>
<script type="text/javascript">
$(function() {
	p10.init();
});
</script>
</head>
<body>
<div class="load_p10" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
<div id="floatingprofitstatisticsChartContainer_10" style="min-height: 400px; margin: 0 auto"></div>
</body>
</html>
