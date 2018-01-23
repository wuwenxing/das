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
//alert("提醒：用户session超时,请重新登录!");
setTimeout("toLogin()", 1000);
function toLogin(){
	var path = '<%=basePath%>' + "";
	if(path.indexOf('dmp.gwghk.com') != -1){
		top.location.href = "http://dmp.gwghk.com/";
	}else{
		top.location.href = path + "LoginController/login";
	}
}
</script>
</head>
<body>
	<span style="font-size: 12; font-weight: bold;">提醒：用户session超时,请重新登录!</span>
</body>
</html>

