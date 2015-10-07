package com.whh.bookstore.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.whh.bookstore.bean.User;

public class LoginFileter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 获取session对象
		HttpSession session = request.getSession();
		
		//获取用户对象
		User user = (User) session.getAttribute("loginUser");
		
		//判断user是非为空
		if(user == null){
			//如果user为空，提示相关信息，转发到指定页面
			request.setAttribute("msg", "请登录你的账号，再进行相关操作");
			
			request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
		}else{
			//如果非空则进行下一步操作
			chain.doFilter(request, response);
		}

	}

}
