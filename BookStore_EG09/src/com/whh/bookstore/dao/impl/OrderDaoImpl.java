package com.whh.bookstore.dao.impl;

import java.util.List;

import com.whh.bookstore.bean.Order;
import com.whh.bookstore.dao.BaseDAO;
import com.whh.bookstore.dao.IOrderDao;

public class OrderDaoImpl extends BaseDAO<Order> implements IOrderDao {

	@Override
	public Integer saveOrder(Order order) {
		String sql = "INSERT INTO `bs_order` ("
				+ "`order_num`,"
				+ "`order_time`,"
				+ "`order_state`,"
				+ "`order_amount`,"
				+ "`user_id_fk`) "
				+ "VALUE (?,?,?,?,?)";
		
		return this.insertForId(sql,
				order.getOrderNum(),
				order.getOrderTime(),
				order.isOrderState(),
				order.getAmount(),
				order.getUserId()
				);
	}

	/**
	 * 得到今天的订单数
	 */
	@Override
	public int getTodayCount() {

		String sql = "SELECT COUNT(*) FROM `bs_order` WHERE `order_time`>CURRENT_DATE ";
		long count = this.getSingleValue(sql);
		return (int) count;
	}

	@Override
	public List<Order> getOrderByUserId(Integer userId) {
		String sql = "SELECT `order_id` orderId,`order_num` orderNum,`order_time` orderTime,`order_state` orderState,`order_amount` amount,`user_id_fk` userId FROM bs_order WHERE `user_id_fk`=?";
		
		List<Order> list = this.getBeanList(sql, userId);
		
		return list;
	}

	@Override
	public void updateOrderState(String orderId) {
		
		String sql = "UPDATE `bs_order` SET `order_state` = TRUE WHERE `order_id` = ?";
		this.update(sql, orderId);
	}

	@Override
	public List<Order> getAllOrder() {
		//返回所有的Order对象（管理员调用）
		String sql = "SELECT `order_id` orderId,`order_num` orderNum,`order_time` orderTime,`order_state` orderState,`order_amount` amount,`user_id_fk` userId FROM bs_order";
		return this.getBeanList(sql);
	}

}
