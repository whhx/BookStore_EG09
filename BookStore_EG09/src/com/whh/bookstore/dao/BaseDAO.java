package com.whh.bookstore.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.whh.bookstore.utils.JDBCUtils;

public class BaseDAO<T> {
	
	private QueryRunner runner = new QueryRunner();
	
	private Class<T> beanType;
	
	public BaseDAO() {
		//构造器由子类调用
		//获取父类的类型
		Type type = this.getClass().getGenericSuperclass();
		
		//将type强转为ParameterizedType（带参数（泛型）的类型）
		ParameterizedType pt = (ParameterizedType) type;
		
		//获得参数
		Type[] types = pt.getActualTypeArguments();
		
		beanType = (Class<T>) types[0];
	}
	
	/**
	 * 用于返回自增主键的值
	 * dbutils不支持这个方法 需要用原生的JDBC
	 * @param sql
	 * @param params
	 * @return
	 */
	public Integer insertForId(String sql,Object ... params){
		Integer id = null;
		
		//获得数据库连接
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//获取PrepareStatement对象，用于预处理sql语句
		try {
			//说明：为了获取自增的主键值，获取preparedStatement对象时需要额外传入一个参数
			ps=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			//给sql语句赋值，因为（Object ... params）为不确定的数组，所以要遍历赋值
			for (int i = 0;i<params.length;i++) {
				ps.setObject(i+1, params[i]);
			}
			
			//执行sql语句
			ps.execute();
			
			//获得主键值
			rs = ps.getGeneratedKeys();
			
			//解析结果集
			if(rs.next()){
				id = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e.getMessage());
		}
		
		//关闭资源,考虑事物时在filter里统一关闭
		JDBCUtils.close(ps, rs, null);
		
		return id;
	}
	
	/**
	 * 用于批量处理增删改方法
	 * @param sql
	 * @param params 
	 * 实质就是同一条sql语句执行多次，二维数组中的一维表示sql执行的次数，二维数据表示参数的个数
	 */
	public void batch(String sql,Object[][] params){
		
		Connection conn = JDBCUtils.getConnection();
		try {
			runner.batch(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		}
		//考虑事物时在filter里统一关闭
		//JDBCUtils.close(conn);
	}
	
	/**
	 * 修改数据库
	 * @param sql
	 * @param prams
	 * @return
	 */
	public int update(String sql,Object ... params){
		
		//定义count，用于计算更新的行数
		int count = 0;
		
		//获得数据库连接
		Connection conn = JDBCUtils.getConnection();
		
	    try {
			count = runner.update(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		}
		
		//JDBCUtils.close(conn);
		return count;
	}
	
	/**
	 * 查询多条数据对象
	 */
	public List<T> getBeanList(String sql,Object ... params){
		List<T> list = null;
		
		//获得数据库连接
		Connection conn = JDBCUtils.getConnection();
		
		try {
			list = runner.query(conn, sql, new BeanListHandler<T>(beanType), params);
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		}
		
		//JDBCUtils.close(conn);;
		return list;
	}
	
	
	/**
	 * 查询单个数据对象的方法
	 * @param sql
	 * @param prams
	 * @return
	 */
	public T getBean(String sql,Object ... params){
		
		T t = null;
		
		//获取数据库连接
		Connection conn = JDBCUtils.getConnection();
		
		
		try {
			t = runner.query(conn, sql, new BeanHandler<T>(beanType), params);
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		}
		
		//JDBCUtils.close(conn);
		return t;
	}
	
	/**
	 * 用于获取单一值的方法
	 * @param sql
	 * @param params
	 * @return 为了方法的通用性,设置为泛型，返回值类型在接收参数的时候确定
	 */
	public <T> T getSingleValue(String sql,Object ... params){
		T t = null;
		Connection conn = JDBCUtils.getConnection();
		try {
			t = (T) runner.query(conn, sql, new ScalarHandler(), params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		//JDBCUtils.close(conn);
		return t;
	}

}
