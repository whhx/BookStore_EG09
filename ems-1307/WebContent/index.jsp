<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎使用培训中心管理系统</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/login.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.min.js"></script>
<script type="text/javascript">
/*
	* 前端基于 JS 的验证.
	* 
	* ①: loginName 和 password 字段除去前后空格不能为空
	* ②: loginName 和 password 字段除去前后空格, 不能少于 6 个字符
	* ③: loginName 中不能包含特殊字符, 即以字母开头, 后边还可以包含数字和_ 
	*
	* 其中前两个验证都通过, 再验证 ③. 
	*/	
	 /* $(function(){
		$(":submit").click(function(){
			var flag=0;//表示验证通过
			$("input:not(:last)").each(function(){
				var val = this.value;
				val=$.trim(val);//消除空格
				//文本框空格清除
				this.value = val;
				var label = $(this).prev("b").text();
				if(val==""){
					alert(label + "不能为空");
					flag=1;
				}else{
					if(val.length <6){
						alert(label+"不能少于6个字符.");
						flag=1;
					}
				}
			}); 
			
			//若前两个验证都通过，则验证用户名中是否包含特殊字符
			if(flag==0){
				//当以JS中的正则表达式
				var reg = /^[a-zA-Z]\w+\w$/g;
				var val = $("input[name='loginName']").val();
				
				//使用正则进行验证
				if(!reg.test(val)){
					alert("用户名不合法");
					
				}else{
					return true;
				}
			}else if(flag==1){
				$("input[type='password']").val("");
			}
			
			return false;
		});
	})
 */
</script>

</head>
<body>
	
	<div align="center">
		<s:form action="/emp-login">
			<div class="login_div" align="center">
			
				<font color="red">
					<s:text name="%{exception.class.name}"></s:text>					
				</font>
				
				<b>用户名</b>
				<s:textfield name="loginName"></s:textfield>
				<font color="red">
					<s:fielderror fieldName="loginName"/>
				</font>

				<b>密码</b>
				<s:password name="password"></s:password>
				<font color="red">
					<s:fielderror fieldName="password"/>
				</font>
				
				<input type="submit" class="submit" value="登录" />
			</div>		
		</s:form>
	</div>

</body>
</html>