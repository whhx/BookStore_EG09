package com.whh.bookstore.service;

import java.util.List;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.special.Page;
import com.whh.bookstore.special.PageCondition;

public interface IBookService {
	
	/**
	 * 根据查询条件，构造page对象
	 * @param pc
	 * @return
	 */
	Page<Book> getPage(PageCondition pc);
	
	
	/**
	 * 根据servlet获取的页码，查询对应的数据,返回组装好的page对象
	 * @param pageNoStr
	 * @return
	Page<Book> getPage(String pageNoStr);*/
	
	/**
	 * 查询多条数据
	 * @return
	 */
	List<Book> getList();
	
	/**
	 * 用过id查询一条数据
	 * @param id
	 * @return
	 */
	Book getBookById(String bookId);
	
	/**
	 * 删除数据
	 * @param id
	 */
	void delBook(String bookId);
	
	/**
	 * 保存数据
	 * @param book
	 */
	void saveBook(Book book);
	
	/**
	 * 更新数据
	 * @param book
	 */
	void update(Book book);
}
