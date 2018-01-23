<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/login.css?version=20170306">
<script type="text/javascript" src="<%=basePath%>page/login/login.js?version=20170306"></script>
</head>
<body style="background-color: #FFF">
<div id="loading" style="z-index:10000; position:absolute; margin-top:210px; left:55%; display: none;">
<p><img src="<%=basePath%>img/loading.gif" /></p>
</div>
	<div id="main" onkeydown="enterkey();">
		<div class="container">
			<div class="login_center">
				<div id="input_body">
					<form id="form_login" action="<%=basePath%>LoginController/login">
						<table class="login_table">
							<tbody>
								<tr>
									<td><input type="text" name="loginName" class="login_input" placeholder="登录名" maxlength="20"></td>
								</tr>
								<tr>
									<td><input type="password" name="password" class="login_input" placeholder="密码" maxlength="20"></td>
								</tr>
<!-- 								<tr> -->
<!-- 									<td> -->
<!-- 										<input type="text" name="captcha" class="login_captcha" placeholder="验证码" maxlength="4"> -->
<%-- 										<img id="captchaimg" class="login_captcha" src="<%=basePath%>LoginController/captcha" title="点击刷新验证码"> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
								<tr>
									<td>
									<input type="button" class="button_login" value="登錄" onclick="login();" />
									<input type="button" class="button_help" value="重置" onclick="javacript:$('#form_login').form('reset');"/>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<p id="errorMsg" class="error"></p>
			<br/>
			<p id="browserAlert" class="error"></p>
			<p class="gw_copyright">
				Copyright © 2015-2017 ${systemDomainName}<br>版权所有，不得转载 <br> Version
				No.：${systemVersion}, Port：${systemPort}
			</p>
		</div>
	</div>
</body>
</html>
