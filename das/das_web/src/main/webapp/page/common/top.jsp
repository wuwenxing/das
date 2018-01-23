<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="das" uri="/das-tags"%>
<%
	String path_a = request.getContextPath();
	String basePath_a = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path_a + "/";
%>
<script type="text/javascript" src="<%=basePath_a%>page/common/top.js?version=20170306"></script>
<html>
<head>
</head>
<body>

<input type="hidden" id="pageType" value="${pageType}">
<input type="hidden" id="cboardLinkUrl" value="${cboardLinkUrl}">
<div class="topDiv">
   <div class="logo-container"></div>	
	 <div class="nav">
         <ul>
         	<c:forEach var="record" items="${menuList}">
				<li id="nav_${record.menuCode}" onclick="javascript:topObj.linkUrl('${record.menuUrl}', '${record.menuCode}')"><a href="javascript:void(0);">${record.menuName}</a></li>
			</c:forEach>
        </ul>
     </div>
	<div class="text-container">
		<div class="welcome-box-left">
			业务类型：<select id="companyId" name="companyId" class="easyui-combobox" data-options="panelHeight:'auto', editable:false" style="width: 72px;">
				<das:enumListTag dataList="${companyEnum}" defaultVal="${companyId}"/>
			</select>
		</div>
		<div class="welcome-box-right">
			<span class="imgBox"><img src="<%=basePath_a%>img/icons/user.png"></span>
			[${client.user.userNo }/${client.user.userName }]
			<a class="easyui-linkbutton" data-options="iconCls:'icon-changePassword', plain:'true'" href="javascript:void(0);" onclick="topObj.changePassword();" title="修改密码"></a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-loginOut', plain:'true'" href="javascript:void(0);" onclick="topObj.loginOut();" title="退出"></a>
		</div>
	</div>
</div>

<!-- 关于 -->
<div id="aboutDlogToolbar" style="display: none;">
	<a id="aboutDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="javascript:$('#aboutDlog').dialog('close');">关闭</a>
</div>
<div id="aboutDlog" class="easyui-dialog" style="display: none;" data-options="closed:'true', buttons:'#addDlogToolbar', top: 80, modal:true, bgiframe:true, resizable:true, width:400, height:200">
	<form id="aboutDlogForm" class="commonForm" method="post">
		<div>
			<div>系统升级日志</div>
			<div>1、首次升级(2016-07-20)；</div>
		</div>
	</form>
</div>

<!-- 修改密码 -->
<div id="changePasswordDlogToolbar" style="display: none;">
	<a id="changePasswordDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="topObj.changePasswordSave();">确定</a>
	<a id="changePasswordDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="javascript:$('#changePasswordDlog').dialog('close');">关闭</a>
</div>
<div id="changePasswordDlog" class="easyui-dialog" style="display: none;" data-options="closed:'true', buttons:'#changePasswordDlogToolbar', modal:true, bgiframe:true, resizable:true, width:400">
	<form id="changePasswordDlogForm" class="commonForm" method="post">
		<table class="commonTable">
			<tr>
				<th>账号</th>
				<td>${client.user.userNo }</td>
			</tr>
			<tr>
				<th>原密码<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="password" name="oldPassword" id="oldPassword" data-options="required:true, validType:'length[0,20]'" /></td>
			</tr>
			<tr>
				<th>新密码<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="password" name="newPassword" id="newPassword" data-options="required:true, validType:'length[0,20]'" /></td>
			</tr>
			<tr>
				<th>重新输入密码<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="password" name="newPasswordAgin" id="newPasswordAgin" data-options="required:true, validType:'length[0,20]'" /></td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>