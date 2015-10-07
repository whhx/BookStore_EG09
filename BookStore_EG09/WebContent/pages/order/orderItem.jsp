<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单详情</title>
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
			class="wel_word">订单详情</span>
		<%@ include file="/WEB-INF/include/book-header.jsp"%>
	</div>

	<div id="main">

		<table>
			<c:if test="${empty OrderItemList }">
				<tr>
					<td>获取订单详情失败</td>
				</tr>
			</c:if>


			<c:if test="${!empty OrderItemList }">
				<tr>
					<td>书名</td>
					<td>作者</td>
					<td>单价</td>
					<td>数量</td>
					<td>金额</td>
				</tr>

				<c:forEach items="${OrderItemList }" var="order">

					<tr>
						<td>${order.book.bookName }</td>
						<td>${order.book.author }</td>
						<td>${order.book.price }</td>
						<td>${order.count }</td>
						<td>${order.amount }</td>
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