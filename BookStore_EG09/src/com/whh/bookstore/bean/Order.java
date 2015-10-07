package com.whh.bookstore.bean;

import java.util.Date;

public class Order {
	
	/**
	  order_id` int(11) NOT NULL AUTO_INCREMENT,
	  `order_num` varchar(14) DEFAULT NULL,
	  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  `order_state` tinyint(1) DEFAULT NULL,
	  `order_amount` double DEFAULT NULL,
	  `user_id_fk` int(11) DEFAULT NULL,
	 */

	private Integer orderId;
	private String orderNum;
	private Date orderTime;
	private boolean orderState;
	private double amount;
	
	private Integer userId;
	
	
	public Order() {
		// TODO Auto-generated constructor stub
	}
	public Order(Integer orderId, String orderNum, Date orderTime,
			boolean orderState, double amount, Integer userId) {
		super();
		this.orderId = orderId;
		this.orderNum = orderNum;
		this.orderTime = orderTime;
		this.orderState = orderState;
		this.amount = amount;
		this.userId = userId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public boolean isOrderState() {
		return orderState;
	}
	public void setOrderState(boolean orderState) {
		this.orderState = orderState;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderNum=" + orderNum
				+ ", orderTime=" + orderTime + ", orderState=" + orderState
				+ ", amount=" + amount + ", userId=" + userId + "]";
	}
	
	
}
