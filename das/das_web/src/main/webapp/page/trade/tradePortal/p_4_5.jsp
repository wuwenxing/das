<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_4_5.js?version=20170306"></script>
<script type="text/javascript">
	$(function() {
		p_4_5.init();
	});
</script>
</head>
<body>
	<table class="commonTableWidth_BFB_25">
		<tr>
			<th>当日激活账户</th>
			<td id="sumactday_days_4_5"></td>
			<th>当月激活账户</th>
			<td id="sumactday_months_4_5"></td>
		</tr>
		<tr>
			<th>累计激活账户</th>
			<td id="sumactall_days_4_5"></td>
			<th>当月累计总开户</th>
			<td id="sumopenall_months_4_5"></td>
		</tr>
		<tr>
			<th>当日激活账户存款</th>
			<td id="sumnewaccount_days_4_5"></td>
			<th>当月激活账户存款</th>
			<td id="sumnewaccount_months_4_5"></td>
		</tr>
		<tr>
			<th>当日净存款</th>
			<td id="equitymdeposit_days_4_5"></td>
			<th>当月净存款</th>
			<td id="equitymdeposit_months_4_5"></td>
		</tr>
		<tr>
			<th>当日总存款</th>
			<td id="summdeposit_days_4_5"></td>
			<th>当月总存款</th>
			<td id="summdeposit_months_4_5"></td>
		</tr>
		<tr>
			<th>当日总取款</th>
			<td id="sumwithdraw_days_4_5"></td>
			<th>当月总取款</th>
			<td id="sumwithdraw_months_4_5"></td>
		</tr>
		<tr>
			<th>当日结馀</th>
			<td id="previousbalance_days_4_5"></td>
			<th></th>
			<td></td>
		</tr>
		<tr>
			<th>当日活动推广赠金(其它)</th>
			<td id="bonusOther_days_4_5"></td>
			<th>当月活动推广赠金(其它)</th>
			<td id="bonusOther_months_4_5"></td>
		</tr>
		<tr>
			<th>当日活动推广赠金</th>
			<td id="bonus_days_4_5"></td>
			<th>当月活动推广赠金</th>
			<td id="bonus_months_4_5"></td>
		</tr>
		<tr>
			<th>当日累计活动推广赠金</th>
			<td id="bonusSum_days_4_5"></td>
			<th>当月累计活动推广赠金</th>
			<td id="bonusSum_months_4_5"></td>
		</tr>
	</table>
</body>
</html>
