<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>page/market/smsConfig/smsConfig.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">短信通道设置</div>
	<div class="divContent">
		<form id="saveDlogForm" class="commonForm" method="post">
			<table class="commonTable">
				<c:forEach var="smsSign" items="${smsSignList}">
					<tr>
						<th>短信签名：</th>
						<td>
							${smsSign.value}<input type="hidden" id="sign" name="sign" value="${smsSign.labelKey}">
						</td>
						<th>短信通道：</th>
						<td>
							<c:if test="${'jd' eq smsSign.labelKey}">
								<select id="smsChannel" name="smsChannel" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
									<c:forEach var="smsChannel" items="${smsChannelList}">
										<option value="${smsChannel.labelKey}"
											${jd.smsChannel eq smsChannel.labelKey?"selected":""}>${smsChannel.value}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${'jdgwfx' eq smsSign.labelKey}">
								<select id="smsChannel" name="smsChannel" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
									<c:forEach var="smsChannel" items="${smsChannelList}">
										<option value="${smsChannel.labelKey}"
											${jdgwfx.smsChannel eq smsChannel.labelKey?"selected":""}>${smsChannel.value}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${'jdgjs' eq smsSign.labelKey}">
								<select id="smsChannel" name="smsChannel" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
									<c:forEach var="smsChannel" items="${smsChannelList}">
										<option value="${smsChannel.labelKey}"
											${jdgjs.smsChannel eq smsChannel.labelKey?"selected":""}>${smsChannel.value}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${'hxgjs' eq smsSign.labelKey}">
								<select id="smsChannel" name="smsChannel" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
									<c:forEach var="smsChannel" items="${smsChannelList}">
										<option value="${smsChannel.labelKey}"
											${hxgjs.smsChannel eq smsChannel.labelKey?"selected":""}>${smsChannel.value}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${'cf' eq smsSign.labelKey}">
								<select id="smsChannel" name="smsChannel" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
									<c:forEach var="smsChannel" items="${smsChannelList}">
										<option value="${smsChannel.labelKey}"
											${cf.smsChannel eq smsChannel.labelKey?"selected":""}>${smsChannel.value}</option>
									</c:forEach>
								</select>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="8">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton"
								onclick="smsConfig.save();"
								data-options="iconCls:'icon-ok', plain:'true'">保存</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

</body>
</html>
