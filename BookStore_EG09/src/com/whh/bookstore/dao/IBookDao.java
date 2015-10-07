package com.whh.bookstore.dao;

import java.util.List;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.special.PageCondition;

public interface IBookDao {
	
	/**
	 * 批量更新图书数据
	 * 参数中的二维数组中，一维表示执行sql的次数，二维表示所有的参数
	 */
	void updateForSalse(Object[][] params);
	
	
	/**
	 * 获得查询条件下的集合数据
	 * @param pageNo
	 * @param pageSize
	 * @param pc
	 * @return
	 */
	List<Book> getPageList(int pageNo,int pageSize,PageCondition pc);
	
	/**
	 * 获取查询条件下的总记录数
	 * @param pc
	 * @return
	 */
	int getTotalRecordNo(PageCondition pc);
	
	/**
	 * 得到所有的查询记录记录
	 * @return
	 */
	int getTotalRecordNo();
	
	List<Book> getPageList(int pageNo,int pageSize);
	
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
