package com.whh.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//解决post请求中的乱码问题
		request.setCharacterEncoding("utf-8");
		// 获取方法名
		String methodName = request.getParameter("method");
		
		try {
			//用反射根据方法名得到方法对象
			Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//更改方法访问权限
			method.setAccessible(true);
			//调用方法
			method.invoke(this, request,response);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			//获取导致异常的原因对象
			Throwable cause = e.getCause();
			if(cause instanceof RuntimeException){
				//转换类型，重新抛出
				RuntimeException rn = (RuntimeException) cause;
				throw rn;
			}
			
		}
		
	}

}
