<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/content.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/list.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-1.3.1.js"></script>
<script type="text/javascript">
	$(function(){
		$(".role-delete").click(function(){
			alert($(this).attr("id"));
			var roleName = $(this).attr("name");
			//显示确定要删除Xxx吗
			var flag = confirm("确定要删除"+roleName+"吗")
			if(!flag){
				return flag;
			}
			//发送Ajax请求
			var url = "role-delete";
			var id = $(this).attr("id");
			var args = {"id":id,"time":new Date()};
			
			var $tr = $(this).parent("td").parent("tr");
			
			$.post(url,args,function(data){
				if(data == "1"){
					$tr.remove();
					alert("删除成功!");
				}else if(data == "0"){
					alert("角色被引用, 无法删除！");
				}else{
					alert("删除失败！");						
				}
			});
			
			return false;
		});
		
	})
	
</script>

</head>
<body>
	<br><br>
	<center>
		<table>
			<thead>
				<tr>
					<th>角色名称</th>
					<th>授予的权限</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#request.roleList">
				
					<tr>
						<td>
							<a href="role-details?roleId=${roleId }">${roleName }</a>						
						</td>
						<td>${displayName }</td>
						
						<td>
						<a href="role-delete?roleId=${roleId }" id="${roleId }" name="${roleName }" class="role-delete">删除</a>
						<a href="role-input?roleId=${roleId }">修改</a>
						</td>
					</tr>
				</s:iterator>
				
				
			</tbody>
		</table>
	</center>
	
</body>
</html>