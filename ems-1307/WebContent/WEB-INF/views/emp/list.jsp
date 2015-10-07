<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/content.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/list.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/script/thickbox/thickbox.css">

<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/thickbox/thickbox.js"></script>

<script type="text/javascript">
	$(function(){
		//Ajax删除
		
		
		
		//4.将当前employee的 is_deleted 字段由 0 修改为 1
		
		//6.当前 "删除" 的超链接, 变为不能点击的状态. （在页面中已经设置）
		$(".emp-delete").click(function(){
			var label = $(this).next(":hidden").val();
			//1.confirm：确定要删除XX的信息吗
			var flag = confirm("确定要删除"+label+"吗");
			if(!flag){
				return flag;
			}
			var url = "emp-delete";
			var id = $(this).prev(":hidden").val();
			var args ={"id":id,"time":new Date()};
			var $span = $(this).parent();
			
			//2.若点击确定，则发送ajax请求
			$.post(url,args,function(data){
				if(data == "1"){
					alert("删除成功");
					//5.把 "删除" 的状态: 由 "正常" 修改为 "删除"
					$("#delete-"+id).text("删除");
					$span.html("删除");
					
				}else if(data == "2"){//3.若当前要删除的employee是一个manager，则提示不能删除
					alert("是 Manager, 不能被删除");
				}else{
					alert("删除出错.");
				}
			});
			return false;
		});
		
		$("input[name='pageNo']").change(function(){
			var val = this.value;
			val = $.trim(val);
			
			//校验 val 的合法性
			var reg = /^\d+$/g;
			var pageNo = parseInt(val);
			
			if(!reg.test(val) || pageNo < 1 || pageNo > parseInt("${page.totalPageNumber }")){
				alert("输入的页码不合法!");
				this.value = "";
				return;
			}
			
			window.location.href = "emp-list?page.pageNo=" + val;
		});
	})
</script>

</head>
<body>

	<br><br>
	<center>
		<br><br>
		
		<a id="criteria" href="${pageContext.request.contextPath }/emp-criteriaInput?height=300&width=320&time=new Date()"  class="thickbox"> 
	   		增加(显示当前)查询条件	   		
		</a> 
		
		<a href="" id="delete-query-condition">
		   	删除查询条件
		</a>
		
		<span class="pagebanner">
			共 ${page.totalItemNumber } 条记录
			&nbsp;&nbsp;
			共 ${page.totalPageNumber } 页
			&nbsp;&nbsp;
			当前第 ${page.pageNo } 页
		</span>
		
		<span class="pagelinks">
			<s:if test="page.hasPrev">
				[
				<a href="emp-list?page.pageNo=1">首页</a>
				/
				<a href="emp-list?page.pageNo=${page.prev }">上一页</a>
				] 	
			</s:if>
			
			<span id="pagelist">
				转到 <input type="text" name="pageNo" size="1" height="1" class="logintxt"/> 页
			</span>
			
			<s:if test="page.hasNext">
				[
				<a href="emp-list?page.pageNo=${page.next }">下一页</a>
				/
				<a href="emp-list?page.pageNo=${page.totalPageNumber }">末页</a>
				] 
			</s:if>
			
		</span>
		
		<table>
			<thead>
				<tr>
					<td><a id="loginname" href="">登录名</a></td> 
					<td>姓名</td>
					
					<td>登录许可</td>
					<td>部门</td>
					
					<td>生日</td>
					<td>性别</td>
					
					<td><a id="email" href="">E-Mail</a></td>
					<td>手机</td>
					
					<td>登录次数</td>
					<td>删除</td>
					<td>角色</td>
					<security:authorize ifAnyGranted="ROLE_EMP_UPDATE,ROLE_EMP_DELETE">
						<td>操作</td>
					</security:authorize>
				</tr>
			</thead>
			
			<tbody>
				<s:iterator value="page.content">
					<tr>
						<td><a id="loginname" href="">${loginName }</a></td> 
						<td>${employeeName }</td>
						
						<td>${enabled==1?'允许':'禁止' }</td>
						<td>${department.departmentName }</td>
						
						<td>
							<s:date name="birth" format="yyyy-MM-dd"/>
						</td>
						<td>${gender==1?'男':'女' }</td>
						
						<td><a id="email" href="">${email }</a></td>
						<td>${mobilePhone }</td>
						
						<td>${visitedTimes }</td>
						<td id="delete-${employeeId }">${(isDeleted == 1) ? '删除':'正常' }</td>
						
						<td>${roleNames }</td>
						<security:authorize 
							ifAnyGranted="ROLE_EMP_UPDATE,ROLE_EMP_DELETE">
							<td>
								<security:authorize ifAnyGranted="ROLE_EMP_UPDATE">
									<a href="emp-input?id=${employeeId }">修改</a>
								</security:authorize>
								&nbsp;
								<security:authorize ifAnyGranted="ROLE_EMP_DELETE">
									<s:if test="isDeleted == 1">删除</s:if>
									<s:else>
										<span>
											<input type="hidden" value="${employeeId }"/>
											<a class="emp-delete" href="emp-delete?id=${employeeId }">删除</a>
											<input type="hidden" value="${loginName }"/>
										</span>
									</s:else>
								</security:authorize>
							</td>
						</security:authorize>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		
		<a href="${pageContext.request.contextPath }/emp-downToExcel.action?time=new Date()">下载到 Excel 中</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		
	</center>

</body>
</html>