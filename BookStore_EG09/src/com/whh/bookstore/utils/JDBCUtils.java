package com.whh.bookstore.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	//c3p0 连接数据库的对象
	private static DataSource ds = new ComboPooledDataSource("webDataSource");
	
	//ThreadLocalAPI
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	
	//获得数据库连接的通用方法
	public static Connection getConnection(){
		Connection conn = null;
		
		//先从线程上获取数据库连接，如果没有再从本地源获取
		try {
			conn = threadLocal.get();
			if(conn == null){
				//如果当前线程没有连接的方法，在本地源中获取
				conn=ds.getConnection();
				//获取后将连接绑定到线程  如果没有绑定到线程，每次获取的连接也是不同的连接，达不到事物处理
				threadLocal.set(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	//关闭数据库连接的通用方法
	
	public static void close(Connection conn){
		if(conn != null){
			try {
				conn.close();
				//关闭连接后及时从线程中清理连接，避免其他的操作获取这个关闭的连接，造成异常
				threadLocal.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(PreparedStatement ps,ResultSet rs,Connection conn){
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null){
			try {
				conn.close();
				//关闭连接后及时从线程中清理连接，避免其他的操作获取这个关闭的连接，造成异常
				threadLocal.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
