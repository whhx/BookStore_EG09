<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$(".delete").click(function(){
			var url = $(this).attr("href");
			$("#hiddenForm").attr("action",url).submit(); 
			return false;
		});
	})

</script>
</head>
<body>
	
	<form action="" method="POST" id="hiddenForm">
		<input type="hidden" name="_method" value="DELETE"/>
	</form>
	
	<table border="1" cellpadding="10" cellspacing="0">
		<tr>
			<td>ID</td>
			<td>LastName</td>
			
			<td>Email</td>
			<td>Birth</td>
			
			<td>CreatedTime</td>
			<td>Department</td>
			
			<td>Edit</td>
			<td>Delete</td>
		</tr>
		
		<c:forEach items="${requestScope.page.content }" var="emp">
			<tr>
				<td>${emp.id }</td>
				<td>${emp.lastName }</td>
				
				<td>${emp.email }</td>
				<td>
					<fmt:formatDate value="${emp.birth }" pattern="yyyy-MM-dd"/>
				</td>
				
				<td>
					<fmt:formatDate value="${emp.createTime }" pattern="yyyy-MM-dd hh:mm:ss"/>
				</td>
				<td>${emp.department.departmentName }</td>
				
				<td><a href="emp/${emp.id }">Edit</a></td>
				<td><a class="delete" href="emp/${emp.id }">Delete</a></td>
			</tr>
		</c:forEach>
		
		<tr>
			<td colspan="8">
				总记录数: ${page.totalElements }
				&nbsp;&nbsp;
				
				总页数: ${page.totalPages }
				&nbsp;&nbsp;
				
				当前第 ${page.number} 页
				&nbsp;&nbsp;
				
				<a href="?pageNo=${page.number + 1 - 1 }">上一页</a>
				<a href="?pageNo=${page.number + 1 + 1 }">下一页</a>
			</td>
		</tr>
	</table>	
	<a href="emp">Add New Employee</a>
	
</body>
</html>