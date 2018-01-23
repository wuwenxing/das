<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<title>数据分析系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="das">
<meta http-equiv="description" content="das">
<link rel="shortcut icon" type="image/x-icon" href="<%=basePath%>img/favicon.ico" />
<head>
<script type="text/javascript">
// 	alert("提醒：用户权限不足，请联系管理员!");
</script>
</head>
<body>
	<span style="font-size: 12; font-weight: bold;">提醒：用户权限不足，请联系管理员!</span>
</body>
</html>

