<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工的添加和编辑</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/content.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/input.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/weebox.css">
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/script/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/script/themes/icon.css">
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css"> --%>

<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.validate.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath }/script/messages_cn.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/bgiframe.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/weebox.js"></script>

<script type="text/javascript">

	function myformatter(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	}
	function myparser(s){
		if (!s) return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	}



	$(function(){
		//使用 weebox 弹出备选的角色
		$("#role_a_id").click(function(){
			$.weeboxs.open('#rolebox', {
				title:'提示',
				onopen:function() {
					//把提交的 checkbox 的状态复制到显示的 checkbox 中
					$(":checkbox[name='roles2']").each(function(index){
						var checked = $(this).attr("checked");
						$($(":checkbox[name!='roles2']")[index]).attr("checked",checked);
					});
					
				},
				onok:function(box){
					//把显示中的 checkbox 的状态复制到提交的 checkbox 中
					$(":checkbox[name!='roles2']").each(function(index){
						var checked = $(this).attr("checked");
						$($(":checkbox[name='roles2']")[index]).attr("checked",checked);
					});
					box.close();//增加事件方法后需手动关闭弹窗
				}
			});
			return false;
		});
		
		//使用 jQuery 的 validate 验证框架完成前端的 js 验证
		$("#employeeForm").validate();
		
		
		//使用ajax验证用户名是否可用
		$("input[name='loginName']").change(function(){
			var val = $(this).val();
			val = $.trim(val);
			if(val.length >=6){
				//修改状态下, 若 loginName 取的是先前的值, 则不发送 Ajax 请求进行验证
				var oldLoginName = $("#oldLoginName").val();
				alert(oldLoginName+"123456");
				if(val == oldLoginName){
					return;
				} 
				//发送ajax请求
				var url = "emp-ajaxValidateLoginName";
				var args = {"loginName":val,"time":new Date()};
				//希望返回一个标记位. 1 代表可用, 0 代表不可用, 其他值代表验证出错.
				$.post(url,args,function(data){
					if("1"==data){
						alert("该用户名可用");
					}else if("0"==data){
						alert("该用户名已经被占用111");
					}else{
						alert("验证出错.");
					}
				});
				
			}
			
		});
		
	})
</script>

</head>
<body>

	<br>
	<s:form id="employeeForm" action="/emp-save" method="post" cssClass="employeeForm">
		<s:if test="employeeId != null">
			<!-- 修改时需要传入的id -->
			<input type="hidden" name="id" value="${employeeId }"/>
			<!--修改时保存的原来的 loginName  -->
			<input type="hidden" name="oldLoginName" value="${loginName }" id="oldLoginName"/>
		</s:if>
	
	
		<fieldset>
			<p>
				<label for="message"><font color="red">添加/修改员工信息</font></label> 
			</p>
			
			<p>
				<label for="loginName">登录名(必填)</label>
				<s:textfield name="loginName" cssClass="required" minlength="6"></s:textfield>
				<label id="loginlabel" class="error" for="loginname" generated="true">
				<!-- 显示服务器端简单验证的信息 -->
					<font color="red"><s:actionerror/></font>
				</label>
			</p>
			
			<p>
				<label for="employeeName">姓名 (必填)</label>
				<s:textfield name="employeeName" cssClass="required" minlength="6"></s:textfield>
			</p>
			
			<p>
				<label for="logingallow">登录许可 (必填)</label>
				<s:radio list="#{'1':'允许','0':'禁止' }" name="enabled" cssStyle="border:none"></s:radio>
			</p>

			<p>
				<label for="gender">性别 (必填)</label>
				<s:radio list="#{'1':'男','0':'女' }" name="gender" cssStyle="border:none"></s:radio>
			</p>
			
			<p>
				<label for="dept">部门 (必填)</label>
				<s:select list="#request.departments" name="department.departmentId" listKey="departmentId" listValue="departmentName"></s:select>
				<label class="error" for="dept" generated="true">
					<font color="red">
					<!-- 显示服务器端简单验证的信息 -->
					</font>
				</label>
			</p>
			
			<p>
				<label for="birth">生日 (必填)</label>
				<s:textfield name="birth" 
					cssClass="easyui-datebox"  data-options="formatter:myformatter,parser:myparser" >
				</s:textfield>
			</p>
			
			<p>
				<label for="email">Email (必填)</label>
				<s:textfield name="email" cssClass="required email"></s:textfield>
				<label class="error" for="email" generated="true">
				<!-- 显示服务器端简单验证的信息 -->
				</label>
			</p>
			
			<p>
				<label for="mobilePhone">电话 (必填)</label>
				<s:textfield name="mobilePhone" cssClass="required"></s:textfield>
			</p>

			<p>
				<label for="role"><a id="role_a_id" href="">分配角色(必选)</a></label>
			</p>
			
			<div style="display:none">
				<!-- 有 name 属性, 用来提交, 且用于保存 checkbox 被选中的状态 -->
				<s:checkboxlist 
					list="#request.roles" name="roles2" 
					listKey="roleId" listValue="roleName"
					cssStyle="border:none"></s:checkboxlist>
			</div>
			
			<div style="display:none" id="rolebox"> 
				<!-- 没有 name 属性, 不会被提交, 仅用于显示 -->
				<s:iterator value="#request.roles">
					<input type="checkbox" value="${roleId }" style="border:none"/>${roleName }<br>
				</s:iterator>
			</div>
			
			<p>
				<label for="mobilePhone">创建人</label>
				<s:if test="employeeId == null">
					${sessionScope.employee.loginName }<!--用login的时候保存的是loginUser用login2保存的是employee  -->
					<s:hidden name="editor.employeeId" value="%{#session.employee.employeeId}"></s:hidden>
				</s:if>
				<s:else>
					${editor.loginName }
				</s:else>
			</p>

			<p>
				<input class="submit" type="submit" value="提交"/>
			</p>
				
		</fieldset>
			
	</s:form>

</body>
</html>