package com.whh.bookstore.servlet.manager;

import com.whh.bookstore.bean.Order;
import com.whh.bookstore.bean.OrderItem;
import com.whh.bookstore.service.IOrderService;
import com.whh.bookstore.service.impl.OrderServiceImpl;
import com.whh.bookstore.servlet.BaseServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderManagerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private IOrderService ios = new OrderServiceImpl();
	
	protected void showOrderDetails(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String orderId = request.getParameter("orderId");
		
		List<OrderItem> itemList = ios.getOrderItemByOrderId(orderId);
		
		request.setAttribute("itemList", itemList);
		request.getRequestDispatcher("/pages/manager/orderItem.jsp").forward(request, response);
		
	}
	
	protected void send(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String orderId = request.getParameter("orderId");
		
		ios.updateOrderState(orderId);
		
		//显示页面，这里还是显示修改后的
		showAllOrder(request, response);
	}

	protected void showAllOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Order> allOrder = ios.getAllOrder();
		
		request.setAttribute("allOrder", allOrder);
		
		request.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(request, response);
		
	}

}
