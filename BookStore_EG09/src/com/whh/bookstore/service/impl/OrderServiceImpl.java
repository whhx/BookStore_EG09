package com.whh.bookstore.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.whh.bookstore.bean.Order;
import com.whh.bookstore.bean.OrderItem;
import com.whh.bookstore.dao.IBookDao;
import com.whh.bookstore.dao.IOrderDao;
import com.whh.bookstore.dao.IOrderItem;
import com.whh.bookstore.dao.impl.BookDaoImpl;
import com.whh.bookstore.dao.impl.OrderDaoImpl;
import com.whh.bookstore.dao.impl.OrderItemImpl;
import com.whh.bookstore.service.IOrderService;
import com.whh.bookstore.special.Cart;
import com.whh.bookstore.special.CartItem;
import com.whh.bookstore.utils.WEBUtils;

public class OrderServiceImpl implements IOrderService {
	
	private IBookDao ibd = new BookDaoImpl();
	private IOrderDao iod = new OrderDaoImpl();
	private IOrderItem ioi = new OrderItemImpl();

	@Override
	public String toCash(Cart cart, Integer userId) {
		
		//从购物车中获取购物项的数量，作为二维数组的一维参数值，即执行sql语句的次数
		int size = cart.getMap().size();
		
		//为了方便和数组协同工作，将map转为list
		Collection<CartItem> values = cart.getMap().values();
		ArrayList<CartItem> cartList = new ArrayList<>(values);
		
		//1.批量更新book数量
		Object[][] bookParams = new Object[size][3];
		
		//循环为属性赋值
		for (int i = 0; i < size; i++) {
			//`salse_amount`+?,`store_num`=`store_num`-? WHERE `book_id`=?";
			CartItem cartItem = cartList.get(i);
			
			bookParams[i][0] =  cartItem.getCount();
			bookParams[i][1] =  cartItem.getCount();
			bookParams[i][2] =  cartItem.getBook().getBookId();
			
		}
		ibd.updateForSalse(bookParams);
		
		//2.生成订单，保存Order对象
		//①计算当天当单数量
		int todayCount = iod.getTodayCount();
		//②生成订单号
		String orderNum = WEBUtils.generateOrderNum(todayCount);
		//③创建order对象
		Order order = new Order(null, orderNum, new Date(), false, cart.getTotalAmount(), userId);
		
		//④生成的订单主键值
		Integer orderId = iod.saveOrder(order);
		
		//3.批量保存OrderItem
		
		Object[][] OrderItemParams = new Object[size][4];
		
		for (int i = 0; i < OrderItemParams.length; i++) {
			//获取购物项
			CartItem cartItem = cartList.get(i);
			
			/**
			 * "`item_count`," + "`item_amount`," + "`order_id_fk`," +
			 * "`book_id_fk`) "
			 */
			
			OrderItemParams[i][0] = cartItem.getCount();
			OrderItemParams[i][1] = cartItem.getAmount();
			//这里用的是跟新book数据生成的主键值,关联外键约束
			OrderItemParams[i][2] = orderId;
			
			OrderItemParams[i][3] = cartItem.getBook().getBookId();
			
		}
		
		ioi.saveOrderItem(OrderItemParams);
		
		//清空购物车
		cart.clear();
		
		//返回生成的订单号
		
		return orderNum;
	}

	@Override
	public List<OrderItem> getOrderItemByOrderId(String orderId) {
		
		return ioi.getOrderItemByOrderId(orderId);
	}

	@Override
	public List<Order> getOrderByUserId(Integer userId) {
		
		return iod.getOrderByUserId(userId);
	}

	@Override
	public List<Order> getAllOrder() {
		return iod.getAllOrder();
	}

	@Override
	public void updateOrderState(String orderId) {

		iod.updateOrderState(orderId);
	}

}
