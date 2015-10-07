package com.whh.bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whh.bookstore.bean.User;
import com.whh.bookstore.service.IUserService;
import com.whh.bookstore.service.impl.UserServiceImpl;


public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IUserService ius = new UserServiceImpl();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取用户传入的数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = new User(null, username, password, null);
		
		User loginUser = ius.login(user);
		
		request.setAttribute("msg", "用户名或密码错误");
		if(loginUser == null){
			//登录失败,转发到login.jsp
			request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
		}else{
			//登录成功,重定向到login_success.jsp
			response.sendRedirect(request.getContextPath()+"/pages/user/login_success.jsp");
			
		}
		
		
	}

}
