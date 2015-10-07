package com.whh.bookstore.servlet.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.service.IBookService;
import com.whh.bookstore.service.impl.BookServiceImpl;
import com.whh.bookstore.servlet.BaseServlet;
import com.whh.bookstore.utils.WEBUtils;

public class Servlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private IBookService ibs = new BookServiceImpl();
	
	protected void updateBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//将从浏览器中获取的数据封装进具体的对象
		Book book = WEBUtils.paramtoBean(new Book(), request);
		
		//执行更新操作
		ibs.update(book);
		
		//调用显示方法
		showList(request, response);
		
	}
	
	/**
	 * 前往图书的表单编辑页面,并为了数据的回显从数据库中找到一个对应的对象
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void editUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//获取从浏览器传入的参数
		String bookId = request.getParameter("bookId");
		
		//通过id从数据库获得相应的对象
		Book book = ibs.getBookById(bookId);
		
		//将数据回显到编辑表单页面
		request.setAttribute("book", book);
		request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request, response);
	}
	
	
	protected void delBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//获得从浏览器输入的参数
		String bookId = request.getParameter("bookId");
		
		//从数据库删除相应的数据
		ibs.delBook(bookId);
		
		//调用显示列表的方法
		showList(request, response);
		
	}
	
	/**
	 * 保存图书数据
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void saveBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//获取请求参数，将参数封装进对象
		Book book = WEBUtils.paramtoBean(new Book(), request);
		
		//将对象放进数据库中
		ibs.saveBook(book);
		
		//调用showList()方法
		showList(request, response);
	}
	
	protected void showList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 从数据库中获得全部数据的集合
		List<Book> list = ibs.getList();
		// 将数据放到请求域中
		request.setAttribute("bookList", list);

		// 转发到指定的页面
		request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
		
	}

}
