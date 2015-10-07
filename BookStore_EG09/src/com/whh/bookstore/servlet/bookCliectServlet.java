package com.whh.bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.service.IBookService;
import com.whh.bookstore.service.impl.BookServiceImpl;
import com.whh.bookstore.special.Page;
import com.whh.bookstore.special.PageCondition;
import com.whh.bookstore.utils.WEBUtils;

public class bookCliectServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private IBookService ibs = new BookServiceImpl();
	
	protected void getBookPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//根据请求参数封装pagecondition对象
		PageCondition condition = WEBUtils.paramtoBean(new PageCondition(), request);
		
		//输出对象的数据(会在控制台输出相应的页码数)
		//System.out.println(condition);
		
		//根据pageCondition对象创建page对象
		Page<Book> page = ibs.getPage(condition);
		
		//将page对象，转发到jsp页面显示
		request.setAttribute("page", page);
		
		request.getRequestDispatcher("/pages/book/bookList.jsp").forward(request, response);
	}

	protected void getBookPageOld(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取当前页面的页码
		String pageNo = request.getParameter("pageNo");
		
		//调用service中的方法，创建对象
		/*Page<Book> page = ibs.getPage(pageNo);*/
		Page<Book> page = ibs.getPage(new PageCondition("1",20,100));
		
		//将page对象，转发到jsp页面显示
		request.setAttribute("page", page);
		
		request.getRequestDispatcher("/pages/book/bookList.jsp").forward(request, response);
	}

}
