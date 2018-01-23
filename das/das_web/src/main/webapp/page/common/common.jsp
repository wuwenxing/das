<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="das" uri="/das-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Long companyId = (Long)request.getSession().getAttribute("companyId");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>数据分析系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="das">
<meta http-equiv="description" content="das">
<link rel="shortcut icon" type="image/x-icon" href="<%=basePath%>img/favicon.ico" />
<!-- js全局变量 -->
<script type="text/javascript">
var BASE_PATH = '<%=basePath%>' + "";
var COMPANY_ID = '<%=companyId%>' + "";
</script>

<!-- jquery -->
<script type="text/javascript"
	src="<%=basePath%>third/jquery_1.7.2/jquery-1.7.2.min.js?version=20170306"></script>

<!-- jquery json -->
<script type="text/javascript"
	src="<%=basePath%>third/jquery_json_2.4/jquery.json-2.4.js?version=20170306"></script>

<!-- jquery_easy_ui -->
<!-- <link rel="stylesheet" type="text/css" -->
<%-- 	href="<%=basePath%>third/jquery-easyui-1.4.5/themes/metro/easyui_gw.css?version=20170306"> --%>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>third/jquery-easyui-1.5.1/themes/bootstrap/easyui.css?version=20170306">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>third/jquery-easyui-1.5.1/themes/icon.css?version=20170306">
<script type="text/javascript"
	src="<%=basePath%>third/jquery-easyui-1.5.1/jquery.easyui.min.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>third/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js?version=20170306"></script>

<!-- jquery UI -->
<link type="text/css" rel="stylesheet" href="<%=basePath%>third/jqueryui/jquery-ui.css?version=20170306" />
<script type="text/javascript" src="<%=basePath%>third/jqueryui/jquery-ui-1.9.2.custom.min.js?version=20170306" charset="UTF-8"></script>

<!-- jquery multiselect -->
<link type="text/css" rel="stylesheet" href="<%=basePath%>third/multiselect/jquery.multiselect.css?version=20170306" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>third/multiselect/jquery.multiselect.filter.css?version=20170306" />
<script type="text/javascript" src="<%=basePath%>third/multiselect/src/jquery.multiselect.min.js?version=20170306" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>third/multiselect/src/jquery.multiselect.filter.min.js?version=20170306" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>third/multiselect/i18n/jquery.multiselect.zh-cn.js?version=20170306" charset="UTF-8"></script>

<!-- xheditor-1.1.14 -->
<script type="text/javascript" src="<%=basePath%>third/xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js?version=20170306" charset="UTF-8"></script>

<!-- highcharts-5.0.2 -->
<%-- <script type="text/javascript" src="<%=basePath%>third/highcharts-5.0.2/code/highcharts.js?version=20170306"></script> --%>
<%-- <script type="text/javascript" src="<%=basePath%>third/highcharts-5.0.2/code/modules/exporting.js?version=20170306"></script> --%>

<!-- echarts -->
<script type="text/javascript" src="<%=basePath%>third/echarts/echarts.min.js?version=20170306"></script>
<%-- <script type="text/javascript" src="<%=basePath%>third/echarts/macarons.js?version=20170306"></script> --%>
<script type="text/javascript" src="<%=basePath%>third/echarts/shine.js?version=20170306"></script>

<!-- common css js -->
<script type="text/javascript"
	src="<%=basePath%>js/common/formatDate.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/common.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/backspace.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/easyui.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/upload.js?version=20170306"></script>
<!-- <link rel="stylesheet" type="text/css" -->
<%-- 	href="<%=basePath%>css/common/common.css?version=20170306"> --%>
<!-- <link rel="stylesheet" type="text/css" -->
<%-- 	href="<%=basePath%>css/common/easyui.css?version=20170306"> --%>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/commonBootstrap.css?version=20170306">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/easyuiBootstrap.css?version=20170306">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/multiselect.css?version=20170306">
