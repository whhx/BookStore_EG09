package com.whh.bookstore.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.whh.bookstore.bean.Book;
import com.whh.bookstore.service.IBookService;
import com.whh.bookstore.service.impl.BookServiceImpl;
import com.whh.bookstore.special.Cart;

public class CartItemServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	protected void updatCartItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		//1.从请求参数中获得bookId
		String bookId = request.getParameter("bookId");
		
		//2.从请求参数中获得要更改的数量值
		String count = request.getParameter("count");
		
		//3.从session域中尝试获得cart对象
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		
		double amount = 0;
		//4.如果对象存在则进行更新操作
		if(cart != null){
			amount = cart.updateCartItem(bookId, count);
		}
		
		/*//5.返回指定页面
		response.sendRedirect(request.getContextPath()+"/pages/cart/cart.jsp");*/
		
		//5.准备Ajax响应中应该封装的数据
		//①当前修改的购物项的小计金额：amount
		
		//②当前购物车中的总数量
		int totalCount = cart.getTotalCount();
		//③当前购物车中的总金额
		double totalAmount = cart.getTotalAmount();
		
		//6.转换为json字符串
		Map<String,Object> jsonMap = new HashMap<>();
		jsonMap.put("amount", amount);
		jsonMap.put("totalCount", totalCount);
		jsonMap.put("totalAmount", totalAmount);
		
		Gson gson = new Gson();
		String json = gson.toJson(jsonMap);
		
		//7.将json字符串返回给Ajax请求
		response.setContentType("html/json;charset=UTF-8");
		response.getWriter().write(json);
		
	}
	
	protected void clearCartIteam(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//从请求参数中获得bookId
		String bookId = request.getParameter("bookId");
		
		//从session中获得cart对象
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		//System.out.println(bookId);
		//判断cart是否为空，非空则进行操作
		if(cart != null){
			cart.removeCartItem(bookId);
		}
		
		//准备ajax需要封装的数据（购物车中的商品件数和总金额）
		//购物车中的总件数
		int totalCount = cart.getTotalCount();
		
		//总金额
		double totalAmount = cart.getTotalAmount();
		
		//转换为Ajax字符
		Map<String,Object> jsonMap = new HashMap<>();
		jsonMap.put("totalCount", totalCount);
		jsonMap.put("totalAmount", totalAmount);
		
		Gson gson = new Gson();
		String json = gson.toJson(jsonMap);
		
		//设置显示字符格式
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().write(json);
		
		/*//到指定页面
		response.sendRedirect(request.getContextPath()+"/pages/cart/cart.jsp");*/
	}
	
	
	protected void clearCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//获取session对象,从session中获取cart对象
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		
		//判断cart是否为空，非空则只需清除操作
		if(cart != null){
			cart.clear();
		}
		
		//跳转到指定页面(购物车页面)
		request.getRequestDispatcher("/pages/cart/cart.jsp").forward(request, response);
	}

	protected void add2Cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		IBookService ibs = new BookServiceImpl();
		//从请求参数中获取bookId的值
		String bookId = request.getParameter("bookId");
		
		//使用bookId的值，从数据库查出相应的对象
		Book book = ibs.getBookById(bookId);
		
		//从session域中尝试获得cart对象
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		
		//判断cart对象是否为空
		if(cart == null){
			//如果为空，说明是第一次添加。创建新的cart对象，保存到session域中
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		
		//（这里可以肯定cart对象）,执行添加操作
		cart.add2Cart(book);
		//将书的名字保存到请求域中，可以再页面中显示加入了什么商品
		request.setAttribute("bookName", book.getBookName());
		
		//准备Json数据
		String bookName = book.getBookName();
		int totalCount = cart.getTotalCount();
		
		//将数据转化为json字符数据
		Map<String, Object> map = new HashMap<>();
		map.put("bookName", bookName);
		map.put("totalCount", totalCount);
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		
		//设置显示格式
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().write(json);
		
		
		/*//派发页面
		//将数据页面转发到指定页面
		request.getRequestDispatcher("/client/bookCliectServlet?method=getBookPage").forward(request, response);*/
	}

}
