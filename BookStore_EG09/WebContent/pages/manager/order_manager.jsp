<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单管理</title>
<%@ include file="/WEB-INF/include/Base.jsp"%>
</head>
<body>

	<div id="header">
		<img class="logo_img" alt="" src="static/img/logo.gif"> <span
			class="wel_word">订单管理系统</span>
		<%@ include file="/WEB-INF/include/manager-header.jsp"%>
	</div>

	<div id="main">
		<table>

			<c:if test="${empty allOrder }">
				<tr>
					<td>暂时还没有可以显示的订单详情</td>
				</tr>
			</c:if>

			<c:if test="${!empty allOrder }">

				<tr>
					<td>订单号</td>
					<td>日期</td>
					<td>金额</td>
					<td>详情</td>
					<td>发货</td>
				</tr>

				<c:forEach items="${allOrder }" var="order">

					<tr>
						<td>${order.orderNum }</td>
						<td>${order.orderTime }</td>
						<td>${order.amount }</td>
						<td><a href="manager/OrderManagerServlet?method=showOrderDetails&orderId=${order.orderId }">查看详情</a></td>
						
						<c:if test="${order.orderState }">
							<td>已发货</td>
						</c:if>
						<c:if test="${!order.orderState }">
							<td><a href="manager/OrderManagerServlet?method=send&orderId=${order.orderId }">点击发货</a></td>
						</c:if>
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