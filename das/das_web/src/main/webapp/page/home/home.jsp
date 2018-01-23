<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/indexBootstrap.css?version=20170306">
<script type="text/javascript" src="<%=basePath%>page/home/homeChart.js?version=20170306"></script>
</head>
<body style="min-width: 900px;">
<%@ include file="../common/top.jsp"%>
<div style="padding-top: 53px;"></div>

<div class="common-box-style" style="margin: 0 auto; margin: 30px;">
	<div class="divTitle">首次入金数</div>
	<div class="divContent">
		<div style="width: 300px; float: left;">
			<div class="chartLoadingDiv_4" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
			<div id="chartContainer_4" style="min-width: 300px; min-height: 400px; margin: 0 auto"></div>
		</div>
		<div style="margin-left: 303px;">
			<div class="chartLoadingDiv_1" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
			<div id="chartContainer_1" style="min-width: 400px; min-height: 400px; margin: 0 auto"></div>
		</div>
	</div>
</div>

<div class="common-box-style" style="margin: 0 auto; margin: 30px;">
	<div class="divTitle">真实开户数</div>
	<div class="divContent">
		<div style="width: 300px; float: left;">
			<div class="chartLoadingDiv_5" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
			<div id="chartContainer_5" style="min-width: 300px; min-height: 400px; margin: 0 auto"></div>
		</div>
		<div style="margin-left: 303px;">
			<div class="chartLoadingDiv_2" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
			<div id="chartContainer_2" style="min-width: 400px; min-height: 400px; margin: 0 auto"></div>
		</div>
	</div>
</div>

<div class="common-box-style" style="margin: 0 auto; margin: 30px;">
	<div class="divTitle">模拟开户数</div>
	<div class="divContent">
		<div style="width: 300px; float: left;">
			<div class="chartLoadingDiv_6" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
			<div id="chartContainer_6" style="min-width: 300px; min-height: 400px; margin: 0 auto"></div>
		</div>
		<div style="margin-left: 303px;">
			<div class="chartLoadingDiv_3" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
			<div id="chartContainer_3" style="min-width: 400px; min-height: 400px; margin: 0 auto"></div>
		</div>
	</div>
</div>

<div style="clear: both"></div>
<div style="text-align: center; padding-top: 20px;">Copyright © 2015-2017</div>

</body>
</html>
