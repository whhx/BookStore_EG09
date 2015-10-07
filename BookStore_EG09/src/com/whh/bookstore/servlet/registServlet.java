package com.whh.bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whh.bookstore.bean.User;
import com.whh.bookstore.service.IUserService;
import com.whh.bookstore.service.impl.UserServiceImpl;

public class registServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IUserService ius = new UserServiceImpl();
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取用户传入的参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		//将数据封装进对象
		User user = new User(null, username, password, email);
		
		boolean is = ius.regist(user);
		request.setAttribute("msg", "用户名已存在");
		
		if(is){
			//登陆成功,重定向到regist_success.html
			response.sendRedirect(request.getContextPath()+"/pages/user/regist_success.jsp");
			
		}else{
			//登录失败，转发到regist。jsp页面
			request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
			
		}
	}

}
