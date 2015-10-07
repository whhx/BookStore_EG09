<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>
<%@ include file="/WEB-INF/include/Base.jsp"%>
<style type="text/css">
h1 {
	text-align: center;
	margin-top: 200px;
}
</style>
</head>
<body>

	<div id="header">
		<img class="logo_img" alt="" src="static/img/logo.gif"> <span
			class="wel_word">我的订单</span>
		<%@ include file="/WEB-INF/include/manager-header.jsp"%>
	</div>

	<div id="main">

		<table>
			<c:if test="${empty orderList }">
				<tr>
					<td>暂时没有可以查看的订单</td>
				</tr>
			</c:if>


			<c:if test="${!empty orderList }">
				<tr>
					<td>订单号</td>
					<td>日期</td>
					<td>金额</td>
					<td>状态</td>
					<td>详情</td>
				</tr>

				<c:forEach items="${orderList }" var="order">

					<tr>
						<td>${order.orderNum }</td>
						<td>${order.orderTime }</td>
						<td>${order.amount }</td>
						<td>${order.orderState?'已发货':'未发货' }</td>
						<td><a href="client/OrderClientServlet?method=showOrderDetails&orderId=${order.orderId }">查看详情</a></td>
					</tr>

				</c:forEach>


			</c:if>

		</table>


	</div>

	<div id="bottom">
		<span> 尚硅谷书城.Copyright &copy;2015 </span>
	</div>
</body>
</html>