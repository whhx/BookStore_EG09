package com.whh.bookstore.filter;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 为了在实际开发中定义Filter简便，自定义HttpFilter
 * @author Administrator
 *
 */
public abstract class HttpFilter implements Filter{
	
	protected FilterConfig filterConfig = null;

	/**
	 * 进行FilterConfig对象的赋值，为了避免子类覆盖这个方法导致赋值失败，声明为final类型
	 */
	public final void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		init();
	}
	
	/**
	 * 为了让子类能够进行初始化操作，提供一个无参的init()方法供子类重写使用
	 */
	public void init() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//为了子类方便使用，将request对象和response对象转换为子接口类型
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//调用重载的doFilter()方法，将转换了类型的对象传入
		doFilter(httpRequest, httpResponse, chain);
	}
	
	/**
	 * 重载的方法，供子类重写使用
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public abstract void doFilter(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException;

	@Override
	public void destroy() {}

}
