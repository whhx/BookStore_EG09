<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书管理</title>
<%@ include file="/WEB-INF/include/Base.jsp" %>
<!-- <script type="text/javascript" src="static/script/jquery-1.7.2.js"></script> -->
<script type="text/javascript">
$(function(){
	//获取超链接对应的元素节点对象，绑定单级响应函数
	$(".delBtn").click(function(){
		//获取当前要删除的图书书名
		//在事件响应函数中，使用this关键字引入当前触发事件的元素对象
		var bookName = $(this).parents("tr").children("td:eq(0)").text();
		//alert(bookName);
		
		//弹出confirm对话框
		var flag = confirm("真的要删除"+bookName+"的数据吗?");
		
		//如果取消则不删除数据
		if(!flag){
			return false;
		}
	});
});

</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">图书管理系统</span>
			<%@ include file="/WEB-INF/include/book-header.jsp" %>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>名称</td>
				<td>价格</td>
				<td>作者</td>
				<td>销量</td>
				<td>库存</td>
				<td colspan="2">操作</td>
			</tr>	
			<!--判断当前页面显示时，有没有接收到BookList的数据  -->
			<!--如果没有，就显示提示信息  -->
			<c:if test="${empty bookList }">
				<tr>
					<td colspan="7">暂时没有要显示的数据</td>
				</tr>
			</c:if>
			<!--如果有就便利输出  -->
			<c:if test="${!empty bookList }">
				<c:forEach items="${bookList }" var="book">
					<tr>
						<td>${book.bookName }</td>
						<td>${book.price }</td>
						<td>${book.author }</td>
						<td>${book.salseAmount }</td>
						<td>${book.storeNum }</td>
						<td><a href="manager/Servlet?method=editUI&bookId=${book.bookId }">修改</a></td>
						<td><a class="delBtn" href="manager/Servlet?method=delBook&bookId=${book.bookId }">删除</a></td>
					</tr>
				</c:forEach>
			</c:if>
			
				
				
			
			
			
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="pages/manager/book_add.jsp">添加图书</a></td>
			</tr>	
		</table>
	</div>
	
	<div id="bottom">
		<span>
			尚硅谷书城.Copyright &copy;2015
		</span>
	</div>
</body>
</html>