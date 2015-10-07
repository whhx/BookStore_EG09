package com.whh.bookstore.service.impl;

import java.util.List;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.dao.IBookDao;
import com.whh.bookstore.dao.impl.BookDaoImpl;
import com.whh.bookstore.service.IBookService;
import com.whh.bookstore.special.Page;
import com.whh.bookstore.special.PageCondition;

public class BookServiceImpl implements IBookService {

	private IBookDao ibd = new BookDaoImpl();
	@Override
	public List<Book> getList() {
		return ibd.getList();
	}

	@Override
	public Book getBookById(String bookId) {
		return ibd.getBookById(bookId);
	}

	@Override
	public void delBook(String bookId) {
		ibd.delBook(bookId);

	}

	@Override
	public void saveBook(Book book) {
		ibd.saveBook(book);

	}

	@Override
	public void update(Book book) {
		ibd.update(book);

	}

	/*@Override
	public Page<Book> getPage(String pageNoStr) {
		//获取总记录数
		int totalRecordNo = ibd.getTotalRecordNo();
		
		//根据总记录数和页码值创建page对象
		Page<Book> page = new Page<>(pageNoStr, totalRecordNo);
		//从page对象中，获取 经过修正的pageNo
		int pageNo = page.getPageNo();
		//根据修正后得pageNo和PageSize值获得相应的集合
		List<Book> list = ibd.getPageList(pageNo, page.PAGE_SIZE);
		//将集合数据设置到page对象中
		page.setList(list);
		
		//返回page对象
		return page;
	}*/

	@Override
	public Page<Book> getPage(PageCondition pc) {
		//1.根据PC查询总记录数
		int totalRecordNo = ibd.getTotalRecordNo(pc);
		
		//2.根据总记录数和pageNo构造page对象
		Page<Book> page = new Page(pc.getPageNo(), totalRecordNo);
		
		//3.从page对象中获取修正后的pageNo
		int pageNo = page.getPageNo();
		
		//4.根据修正的pageNo和PAGE_SIZE查询集合数据
		List<Book> pageList = ibd.getPageList(pageNo, page.PAGE_SIZE, pc);
		
		//5.将集合数据设置到page对象中
		page.setList(pageList);
		//6.返回page对象
		return page;
	}

}
