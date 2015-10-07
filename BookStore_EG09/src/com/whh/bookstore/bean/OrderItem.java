package com.whh.bookstore.bean;

public class OrderItem {
	/**
	 *    `item_id` int(11) NOT NULL AUTO_INCREMENT,
		  `item_count` int(11) DEFAULT NULL,
		  `item_amount` double DEFAULT NULL,
		  `order_id_fk` int(11) DEFAULT NULL,
		  `book_id_fk` int(11) DEFAULT NULL,
	 */
	private Integer itemId;
	private int count;
	private double amount;
	
	private Integer orderId;
	private Integer bookId;
	
	//注意，为了方便显示书名，作者，单价，应该包含一个book对象的引用
	private Book book;
	
	
	public OrderItem() {
		// TODO Auto-generated constructor stub
	}


	public OrderItem(Integer itemId, int count, double amount, Integer orderId,
			Integer bookId, Book book) {
		super();
		this.itemId = itemId;
		this.count = count;
		this.amount = amount;
		this.orderId = orderId;
		this.bookId = bookId;
		this.book = book;
	}


	public Integer getItemId() {
		return itemId;
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public Integer getBookId() {
		return bookId;
	}


	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}


	@Override
	public String toString() {
		return "OrderItem [itemId=" + itemId + ", count=" + count + ", amount="
				+ amount + ", orderId=" + orderId + ", bookId=" + bookId
				+ ", book=" + book + "]";
	}
	
	

}
