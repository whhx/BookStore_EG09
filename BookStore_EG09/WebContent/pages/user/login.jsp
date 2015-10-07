<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员登录页面</title>
<%@ include file="/WEB-INF/include/Base.jsp" %>
<script type="text/javascript">
	window.onload = function(){
		//为sub_btn按钮绑定单击响应函数
		var subBtn = document.getElementById("sub_btn");
		subBtn.onclick = function(){
			
			//获取用户输入的用户名和密码
			var username = document.getElementsByName("username")[0].value;
			var pwd = document.getElementsByName("password")[0].value;
			
			//该对用户名和密码进行验证，正则表达式
			var nameReg = /^[a-z0-9_-]{3,16}$/;
			if(!nameReg.test(username)){
				//弹出对话框
				alert("用户名不符合规范");
				//取消默认行为
				return false;
			}
			
			var pwdReg = /^[a-z0-9_-]{3,18}$/;
			if(!pwdReg.test(pwd)){
				//弹出对话框
				alert("密码不符合规范");
				//取消默认行为
				return false;
			}
			
			
		};
		
	};
</script>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎登录</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>尚硅谷会员</h1>
								<a href="pages/user/regist.jsp">立即注册</a>
							</div>
							<div class="msg_cont">
								<b></b>
								<span class="errorMsg">
								
									${empty msg?"公共场所不建议自动登录，以防账号丢失":msg }
								</span>
							</div>
							<div class="form">
								<form action="client/UserServlet?method=login" method="post">
									<label>用户名称：</label>
									<input value="${param.username }" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password" />
									<br />
									<br />
									<input type="checkbox" name="autoLogin" />
									<label>自动登录</label>
									<br />
									<br />
									<input type="submit" value="登录" id="sub_btn" />
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<div id="bottom">
			<span>
				尚硅谷书城.Copyright &copy;2015
			</span>
		</div>
</body>
</html>