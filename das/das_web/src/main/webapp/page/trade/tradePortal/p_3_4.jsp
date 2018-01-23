<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_3_4.js?version=20170306"></script>
<script type="text/javascript">
	$(function() {
		p_3_4.init();
	});
</script>
</head>
<body>
	<table class="commonTableWidth_BFB_25">
		<tr>
			<th>当日活动推广赠金(其它)</th>
			<td id="bonusOther_days_3_4"></td>
			<th>当月活动推广赠金(其它)</th>
			<td id="bonusOther_months_3_4"></td>
		</tr>
		<tr>
			<th>当日活动推广赠金</th>
			<td id="bonus_days_3_4"></td>
			<th>当月活动推广赠金</th>
			<td id="bonus_months_3_4"></td>
		</tr>
		<tr>
			<th>当日累计活动推广赠金</th>
			<td id="bonusSum_days_3_4"></td>
			<th>当月累计活动推广赠金</th>
			<td id="bonusSum_months_3_4"></td>
		</tr>
		<tr>
			<th>当日赠金活动清零</th>
			<td id="bonusclear_days_3_4"></td>
			<th>当月赠金活动清零</th>
			<td id="bonusclear_months_3_4"></td>
		</tr>
		<tr>
			<th>当日调整</th>
			<td id="adjustfixedamount_days_3_4"></td>
			<th>当月调整</th>
			<td id="adjustfixedamount_months_3_4"></td>
		</tr>
		<tr>
			<th>当日回赠</th>
			<td id="commission_days_3_4"></td>
			<th>当月回赠</th>
			<td id="commission_months_3_4"></td>
		</tr>
		<tr>
			<th>当日网站总访问量</th>
			<td id="siteVisitsCount_days_3_4"></td>
			<th>当月网站总访问量</th>
			<td id="siteVisitsCount_months_3_4"></td>
		</tr>
		<tr>
			<th>当日渠道推广费用</th>
			<td id="channelPromotionCosts_days_3_4"></td>
			<th>当月渠道推广费用</th>
			<td id="channelPromotionCosts_months_3_4"></td>
		</tr>
	</table>
</body>
</html>
