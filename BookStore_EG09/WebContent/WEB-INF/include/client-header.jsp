<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	<c:if test = "${sessionScope.loginUser == null }">
		<a href="pages/user/login.jsp">登录</a> 
		<a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp; 
	</c:if>
	<c:if test = "${sessionScope.loginUser != null }">
		您好,欢迎<span style = "color:red;font-size:25px;">${sessionScope.loginUser.username }</span>
		<a href="client/OrderClientServlet?method=showMyOrders"> 我的订单</a>
		<a href="client/UserServlet?method=loginOut"> 退出</a>
	</c:if>
	<a href="pages/cart/cart.jsp">购物车</a> 
	<a href="pages/manager/manager.jsp">后台管理</a>
	<a href="${pageContext.request.contextPath }/index.jsp">首页</a>
</div>