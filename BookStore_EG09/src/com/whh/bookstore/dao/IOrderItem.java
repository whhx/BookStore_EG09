package com.whh.bookstore.dao;

import java.util.List;

import com.whh.bookstore.bean.OrderItem;

public interface IOrderItem {
	
	void saveOrderItem(Object[][] params);
	
	List<OrderItem> getOrderItemByOrderId(String orderId);

}
