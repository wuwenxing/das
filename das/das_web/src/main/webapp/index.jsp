<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>数据分析系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="das">
<meta http-equiv="description" content="das">
<link rel="shortcut icon" type="image/x-icon" href="<%=basePath%>img/favicon.ico" />
<head>
<script type="text/javascript">
window.location.href = '<%=basePath%>' + "LoginController/login";
</script>
</head>
<body>
</body>
</html>

