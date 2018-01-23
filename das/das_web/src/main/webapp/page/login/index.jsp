<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>css/index.css?version=20170306"> --%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/indexBootstrap.css?version=20170306">
<script type="text/javascript" src="<%=basePath%>page/login/index.js?version=20170306"></script>
</head>
<body class="indexBody">
<%@ include file="../common/top.jsp"%>
<input type="hidden" id="showGWBBPage" value="${showGWBBPage}">
<input type="hidden" id="topTagMenuCode" value="${topTagMenuCode}">
<input type="hidden" id="curCompanyId" value="${companyId}">
<div class="big-cenbox">
	<div id="leftDiv" class="leftDiv">
		<div id="menuAccordion" class="easyui-accordion" data-options="multiple:false, border: true, fit: false" style="border:none"></div>
	</div>
	<div id="rightDiv" class="rightDiv">
		<div id="menu_tabs" class="easyui-tabs" data-options="border: true, fit:false, pill:true, onClose: function(){index.closeTabsEvent();}"></div>
		<div id="welcome" style="min-height: 768px; height: 24px; line-height: 24px; font-weight: bold;">${client.user.userName }，您好，欢迎您!</div>
	</div>
</div>

<div style="clear:both;"></div>
<div style="text-align: center;">Copyright © 2015-2017</div>

</body>
</html>
