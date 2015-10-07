package com.whh.bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whh.bookstore.utils.JDBCUtils;

public class TransactionFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//1.获取数据库连接，绑定到当前线程对象
		Connection conn = JDBCUtils.getConnection();
		
		//2.开启事物：就是取消事物的自动提交
		try {
			conn.setAutoCommit(false);
			//3.正常执行后续操作
			chain.doFilter(request, response);
			
			//4.提交事物
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			
			//5.如果出现异常，回滚事物
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			//6.在操作失败情况下显示错误信息提示
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
		}
		//7.统一关闭连接
		JDBCUtils.close(null, null, conn);
	}

}
