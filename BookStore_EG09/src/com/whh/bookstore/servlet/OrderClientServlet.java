package com.whh.bookstore.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.whh.bookstore.bean.Order;
import com.whh.bookstore.bean.OrderItem;
import com.whh.bookstore.bean.User;
import com.whh.bookstore.service.IOrderService;
import com.whh.bookstore.service.impl.OrderServiceImpl;
import com.whh.bookstore.special.Cart;


public class OrderClientServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private IOrderService ios = new OrderServiceImpl();
	
	protected void showOrderDetails(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//获取请求参数中orderid的值
		String orderId = request.getParameter("orderId");
		
		//根据orderID的值，查询对应OrderItem的集合
		List<OrderItem> OrderItemList = ios.getOrderItemByOrderId(orderId);
		
		//将数据转发到指定的页面进行显示
		request.setAttribute("OrderItemList", OrderItemList);
		
		request.getRequestDispatcher("/pages/order/orderItem.jsp").forward(request, response);
		
		
	}
	
	protected void showMyOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//进行登录验证
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		
/*		//判断user是否为空,用filter代替了
		if(user == null){
			//提示先进行登录
			request.setAttribute("msg", "请先登录你的账号");
			
			request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
			return ;
		}
*/		
		//根据userId查询对应的order
		List<Order> orderList = ios.getOrderByUserId(user.getId());
		
		//将数据转发到指定页面
		request.setAttribute("orderList", orderList);
		
		request.getRequestDispatcher("/pages/order/order.jsp").forward(request, response);
		
	}

	protected void doCash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//1.进行登录验证
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		
	/*	//2.判断user是否为空，用filter代替了
		if(user == null){
			//提示先进行登录
			request.setAttribute("msg", "请先登录你的账号");
			
			request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
			return ;
		}*/
		//3.获取cart对象
		Cart cart = (Cart) session.getAttribute("cart");
		
		//4.判断cart是否为空
		if(cart == null){
			
			response.sendRedirect(request.getContextPath()+"/pages/cart/cart.jsp");
			
			return;
		}
		
		//5.执行结账操作
		String orderNum = ios.toCash(cart, user.getId());
		
		//6.指定到目标页面显示生成的订单号
		request.setAttribute("orderNum", orderNum);
		request.getRequestDispatcher("/pages/cart/checkout.jsp").forward(request, response);
		
	}

}
