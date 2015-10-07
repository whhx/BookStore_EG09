<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员注册页面</title>
<%@ include file="/WEB-INF/include/Base.jsp" %>
<style type="text/css">
	.login_form{
		height:420px;
		margin-top: 25px;
	}
	
</style>
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(function(){
		
		//目标：点击图片时刷新验证码
		//理论依据：当一个img的src发生变化时，浏览器就会从新加载这个图片
		//改变src属性，但是又要访问到图片地址，可以给uri后面的添加请求参数，每次点击参数不同即可
		$("#imgCode").click(function(){
			var oldStr = this.src;
			//获取请求参数的位置
			var position = oldStr.indexOf("?time=");
			if(position != -1){
				//当确实存在请求参数时，从字符串去掉后面的请求参数
				oldStr = oldStr.substring(0,position);
			}
			
			//重新赋值给src
			this.src = oldStr+"?time="+new Date().getTime();
		});
		
		
		$("#sub_btn").click(function(){
			
			//获取用户名
			var username = $("[name=username]").val();
			//去空格
			username = $.trim(username);
			//将去空格后的用户名写到文本框中
			$("[name=username]").val(username);
			
			var reg = /^[a-z0-9_-]{3,16}$/;
			if(!reg.test(username)){
				
				//弹出提示框
				alert("请输入3-16位字母或数字作为用户名");
				
				//取消默认行为
				return false;
			}
			
			
			//获取密码
			var pwd = $("[name=password]").val();
			//去空格
			pwd = $.trim(pwd);
			//将去空格后的密码写到文本框中
			$("[name=passwrd]").val(pwd);
			
			reg = /^[a-z0-9_-]{3,18}$/;
			if(!reg.test(pwd)){
				
				//弹出提示框
				alert("请输入3-18位字母或数字作为密码");
				
				//取消默认行为
				return false;
			}
			
			//获取确认密码
			var rePwd = $("[name=repwd]").val();
			//去空格
			rePwd = $.trim(pwd);
			//将去空格后的确认密码写到文本框中
			$("[name=rePwd]").val(rePwd);
			
			if(rePwd != pwd){
				//弹出提示框
				alert("确认密码和密码不一致");
				//取消默认行为
				return false;
			}
			
			//获取电子邮件
			var email = $("[name=email]").val();
			//去空格
			email = $.trim(email);
			//将去空格后的邮箱地址写到文本框中
			$("[name=email]").val(email);
			
			reg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!reg.test(email)){
				
				//弹出提示框
				alert("请输入正确的邮箱地址");
				
				//取消默认行为
				return false;
			}
			
			//获取验证码
			var code = $("[name=codeParam]").val();
			//去空格
			code = $.trim(code);
			//将去空格后的邮箱地址写到文本框中
			$("[name=codeParam]").val(code);
			
			if(code == ""){
				
				//弹出提示框
				alert("请输入验证码");
				
				//取消默认行为
				return false;
			}
			
		});
		
		$("[name=username]").change(function(){
			
			//获取对象的名字
			var username = $.trim(this.value);
			//判断用户名是否为空
			if(username==""){
				$(".errorMsg").text("用户名不能为空");
				return;
			}
			
			//发送ajax请求
			var url = "${pageContext.request.contextPath}/client/UserServlet";
			var paramDate = {"method":"checkUserName","username":username,"time":new Date()};
			var myCallBack = function(responseDate){
				$(".errorMsg").text(responseDate);
			};
			var type = "text";
			$.post(url,paramDate,myCallBack,type);
		});
		
	});
</script>

</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册尚硅谷会员</h1>
								<span class="errorMsg">
									${msg }
								</span>
							</div>
							<div class="form">
								<form action="client/UserServlet?method=regist" method="post">
									<label>用户名称：</label>
									<input value="${param.username }" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码" autocomplete="off" tabindex="1" name="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input value="${param.email }" class="itxt" type="text" placeholder="请输入邮箱地址" autocomplete="off" tabindex="1" name="email" />
									<br />
									<br />
									<label>验证码：</label>
									<input value="${param.code }"class="itxt" type="text" style="width: 150px;" name="codeParam"/>
									<img id="imgCode" src="client/ValidateColorServlet" style="float: right;height: 30px; ">									
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
									
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