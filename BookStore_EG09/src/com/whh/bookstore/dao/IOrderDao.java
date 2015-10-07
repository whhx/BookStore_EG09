package com.whh.bookstore.dao;

import java.util.List;

import com.whh.bookstore.bean.Order;

public interface IOrderDao {
	
	/**
	 * 更改订单的状态
	 * @param orderId
	 */
	void updateOrderState(String orderId);
	
	/**
	 * 得到所有的订单
	 * @return
	 */
	List<Order> getAllOrder();
	
	/**
	 * 通过订单号 查询订单
	 * @param userId
	 * @return
	 */
	List<Order> getOrderByUserId(Integer userId);
	
	/**
	 * 今天的订单数量
	 * @return
	 */
	int getTodayCount();
	
	/**
	 * 保存订单生成数据，同时返回生成的主键值
	 * @param order 要保存的订单
	 * @return 返回自动生成的主键值 这个值在保存OrderItem集合时会用到
	 */
	Integer saveOrder(Order order);

}
