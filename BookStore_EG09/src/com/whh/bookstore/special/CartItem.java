package com.whh.bookstore.special;

import com.whh.bookstore.bean.Book;
/**
 * 用于封装购物项的信息
 * @author Administrator
 *
 */
public class CartItem {
	
	private int count;
	private Book book;
	
	public CartItem() {}

	public CartItem(int count, Book book) {
		super();
		this.count = count;
		this.book = book;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	//用EL调用数据时，调用的是get方法
	public double getAmount(){
		return count*book.getPrice();
	}

}
