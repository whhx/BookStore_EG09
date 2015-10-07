package com.whh.bookstore.service;

import java.util.List;

import com.whh.bookstore.bean.Order;
import com.whh.bookstore.bean.OrderItem;
import com.whh.bookstore.special.Cart;

public interface IOrderService {
	
	List<Order> getAllOrder();
	
	void updateOrderState(String orderId);
	
	String toCash(Cart cart,Integer userId);
	
	/**
	 * 根据orderID查看OrderItem
	 * @param orderId
	 * @return
	 */
	List<OrderItem> getOrderItemByOrderId(String orderId);
	
	/**
	 * 根据useid查看order
	 * @param userId
	 * @return
	 */
	List<Order> getOrderByUserId(Integer userId);

}
