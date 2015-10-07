package com.whh.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.whh.bookstore.bean.User;
import com.whh.bookstore.service.IUserService;
import com.whh.bookstore.service.impl.UserServiceImpl;
import com.whh.bookstore.utils.WEBUtils;

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private IUserService ius = new UserServiceImpl();
	
	
	protected void checkUserName(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//获得页面传入的参数
		String username = request.getParameter("username");
		
		//调用ius中的方法完成检查
		String name = ius.checkUserName(username);
		
		//将结果字符串以响应的形式返回给Ajax请求
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(name);
	}
	
	protected void loginOut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//强制关闭session
		HttpSession session = request.getSession();
		session.invalidate();
		
		//重定向到主页面
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
	
	protected void login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//调用工具类中的方法,获得相应的对象
		User u = WEBUtils.paramtoBean(new User(), request);
		
		//完成登录操作
		User loginUser = ius.login(u);

		request.setAttribute("msg", "用户名或密码错误");
		if (loginUser == null) {
			// 登录失败,转发到login.jsp
			request.getRequestDispatcher("/pages/user/login.jsp").forward(
					request, response);
		} else {
			
			//登录成功,将user对象保存到session域中
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			
			// 登录成功,重定向到login_success.jsp
			response.sendRedirect(request.getContextPath()
					+ "/pages/user/login_success.jsp");

		}

	}
	
	protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用工具类中的方法，获得相应的对象
		User user = WEBUtils.paramtoBean(new User(), request);
		
		//获取注册页面提交的验证码
		String codeParam = request.getParameter("codeParam");
		
		//获取session中的验证码
		HttpSession session = request.getSession();
		String codeSystem = (String) session.getAttribute("codeSystem");
		
		//比较上面两个验证码
		if(codeParam != null && codeParam.equalsIgnoreCase(codeSystem)){
			//如果相同，则将session中的验证码删除可以注册
			session.removeAttribute("codeSystem");
		}else{
			//如果不同提示信息，终止注册操作
			request.setAttribute("msg", "验证码错误");
			request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
		}
		
		
		boolean is = ius.regist(user);
		
		if(is){
			//登陆成功,重定向到regist_success.html
			response.sendRedirect(request.getContextPath()+"/pages/user/regist_success.jsp");
			
		}else{
			//登录失败，转发到regist。jsp页面
			request.setAttribute("msg", "用户名已存在");
			request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
			
		}
	}

}
