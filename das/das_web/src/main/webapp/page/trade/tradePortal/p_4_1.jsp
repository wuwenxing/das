<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_4_1.js?version=20170306"></script>
<script type="text/javascript">
	$(function() {
		p_4_1.init();
	});
</script>
</head>
<body>
	<table class="commonTableWidth_BFB_25">
		<tr>
			<th>客户账户结馀</th>
			<td id="previousbalance_days_4_1"></td>
			<th></th>
			<td></td>
		</tr>
		<tr>
			<th>当日净存款</th>
			<td id="equitymdeposit_days_4_1"></td>
			<th>当月净存款</th>
			<td id="equitymdeposit_months_4_1"></td>
		</tr>
		<tr>
			<th>当日总存款</th>
			<td id="summdeposit_days_4_1"></td>
			<th>当月总存款</th>
			<td id="summdeposit_months_4_1"></td>
		</tr>
		<tr>
			<th>当日总取款</th>
			<td id="sumwithdraw_days_4_1"></td>
			<th>当月总取款</th>
			<td id="sumwithdraw_months_4_1"></td>
		</tr>
		<tr>
			<th>当日激活账户存款</th>
			<td id="sumnewaccount_days_4_1"></td>
			<th>当月激活账户存款</th>
			<td id="sumnewaccount_months_4_1"></td>
		</tr>
		<tr>
			<th>当日激活账户</th>
			<td id="sumactday_days_4_1"></td>
			<th>当月激活账户</th>
			<td id="sumactday_months_4_1"></td>
		</tr>
		<tr>
			<th>累计激活账户</th>
			<td id="sumactall_days_4_1"></td>
			<th></th>
			<td></td>
		</tr>
		<tr>
			<th>当日客户对话量</th>
			<td id="advisoryCustomerCount_days_4_1"></td>
			<th>当月客户对话量</th>
			<td id="advisoryCustomerCount_months_4_1"></td>
		</tr>
		<tr>
			<th>当日对话总数</th>
			<td id="advisoryCount_days_4_1"></td>
			<th>当月对话总数</th>
			<td id="advisoryCount_months_4_1"></td>
		</tr>
	</table>
</body>
</html>
