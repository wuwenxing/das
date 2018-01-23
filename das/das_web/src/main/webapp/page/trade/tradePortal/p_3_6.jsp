<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>page/trade/tradePortal/p_3_6.js?version=20170306"></script>
<script type="text/javascript">
	$(function() {
		p_3_6.init();
	});
</script>
</head>
<body>
	<table class="commonTableWidth_BFB_25">
		<tr>
			<th>当日盈亏</th>
			<td id="companyprofit_days_3_6"></td>
			<th>当月盈亏</th>
			<td id="companyprofit_months_3_6"></td>
		</tr>
		<tr>
			<th>当日净盈亏</th>
			<td id="hyk_days_3_6"></td>
			<th>当月净盈亏</th>
			<td id="hyk_months_3_6"></td>
		</tr>
		<tr>
			<th>当日浮动盈亏</th>
			<td id="floatingprofit_days_3_6"></td>
			<th></th>
			<td></td>
		</tr>
		<tr>
			<th>当日利息收入</th>
			<td id="swap_days_3_6"></td>
			<th>当月利息收入</th>
			<td id="swap_months_3_6"></td>
		</tr>
		<tr>
			<th>当日系统清零</th>
			<td id="sysclearzero_days_3_6"></td>
			<th>当月系统清零</th>
			<td id="sysclearzero_months_3_6"></td>
		</tr>
		<tr>
			<th>当日手动对冲盈亏</th>
			<td id="handOperHedgeProfitAndLoss_days_3_6"></td>
			<th>当月手动对冲盈亏</th>
			<td id="handOperHedgeProfitAndLoss_months_3_6"></td>
		</tr>
		<tr>
			<th>当日回赠</th>
			<td id="commission_days_3_6"></td>
			<th>当月回赠</th>
			<td id="commission_months_3_6"></td>
		</tr>
		<tr>
			<th>当日毛利</th>
			<td id="grossprofit_days_3_6"></td>
			<th>当月毛利<br/>(扣除推广赠金及赠金清零)</th>
			<td id="grossprofit_months_3_6"></td>
		</tr>
		<tr>
			<th>当日交易盈亏(金)</th>
			<td id="goldprofit_days_3_6"></td>
			<th>当月交易盈亏(金)</th>
			<td id="goldprofit_months_3_6"></td>
		</tr>
		<tr>
			<th>当日交易盈亏(银)</th>
			<td id="silverprofit_days_3_6"></td>
			<th>当月交易盈亏(银)</th>
			<td id="silverprofit_months_3_6"></td>
		</tr>
		<tr>
			<th>当日交易盈亏(人民币金)</th>
			<td id="xaucnhprofit_days_3_6"></td>
			<th>当月交易盈亏(人民币金)</th>
			<td id="xaucnhprofit_months_3_6"></td>
		</tr>
		<tr>
			<th>当日交易盈亏(人民币银)</th>
			<td id="xagcnhprofit_days_3_6"></td>
			<th>当月交易盈亏(人民币银)</th>
			<td id="xagcnhprofit_months_3_6"></td>
		</tr>
		<tr>
			<th>当日交易手数(金)</th>
			<td id="goldvolume_days_3_6"></td>
			<th>当月交易手数(金)</th>
			<td id="goldvolume_months_3_6"></td>
		</tr>
		<tr>
			<th>当日交易手数(银)</th>
			<td id="silvervolume_days_3_6"></td>
			<th>当月交易手数(银)</th>
			<td id="silvervolume_months_3_6"></td>
		</tr>
		<tr>
			<th>当日交易手数(人民币金)</th>
			<td id="xaucnhvolume_days_3_6"></td>
			<th>当月交易手数(人民币金)</th>
			<td id="xaucnhvolume_months_3_6"></td>
		</tr>
		<tr>
			<th>当日交易手数(人民币银)</th>
			<td id="xagcnhvolume_days_3_6"></td>
			<th>当月交易手数(人民币银)</th>
			<td id="xagcnhvolume_months_3_6"></td>
		</tr>
		<tr>
			<th>当日总交易手数</th>
			<td id="volume_days_3_6"></td>
			<th>当月总交易手数</th>
			<td id="volume_months_3_6"></td>
		</tr>
	</table>
</body>
</html>
