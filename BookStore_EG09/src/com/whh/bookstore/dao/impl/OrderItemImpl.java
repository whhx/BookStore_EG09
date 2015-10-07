package com.whh.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.bean.OrderItem;
import com.whh.bookstore.dao.BaseDAO;
import com.whh.bookstore.dao.IOrderItem;
import com.whh.bookstore.utils.JDBCUtils;

public class OrderItemImpl extends BaseDAO<OrderItem> implements IOrderItem {

	@Override
	public void saveOrderItem(Object[][] params) {
		String sql ="INSERT INTO`bs_order_item` ("
				+ "`item_count`,"
				+ "`item_amount`,"
				+ "`order_id_fk`,"
				+ "`book_id_fk`) "
				+ "VALUE (?,?,?,?)";
		
		this.batch(sql, params);

	}

	@Override
	public List<OrderItem> getOrderItemByOrderId(String orderIdStr) {
		//String sql = " SELECT `item_id` itemId,`item_count` `count`,`item_amount` amount,`order_id_fk` orderId,`book_id_fk` bookId FROM `bs_order_item` WHERE `order_id_fk`=?";
		//return this.getBeanList(sql, orderId);
		//上述方法只能查询OrderItem中的数据，而且还会增加数据库的负担,所以使用多表连接的方式进行查询，但是dbutils工具类不支持多表查询，所以使用原生的jdbc查询数据
		
		String sql = "SELECT "
				+ "`item_id` itemId,"
				+ "`item_count` `count`,"
				+ "`item_amount` amount,"
				+ "`order_id_fk` orderId,"
				+ "`book_id_fk` bookId,"
				+ "`book_name` bookName,"
				+ "`author` author,"
				+ "`price` price "
				+ "FROM `bs_order_item` i LEFT JOIN `bs_book` b ON i.`book_id_fk`=b.`book_id` WHERE `order_id_fk`=?";
		//获得数据库连接
		Connection conn = JDBCUtils.getConnection();
		
		List<OrderItem> itemList = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, orderIdStr);
			
			rs=ps.executeQuery();
			
			while(rs.next()){
				int itemId = rs.getInt("itemId");
				int count = rs.getInt("count");
				double amount = rs.getDouble("amount");
				int orderId = rs.getInt("orderId");
				int bookId = rs.getInt("bookId");
				String bookName = rs.getString("bookName");
				String author = rs.getString("author");
				double price = rs.getDouble("price");
				//根据查询结果创建Book对象
				Book book = new Book(bookId, bookName, author, price, 0, 0);
				
				//根据查询结果创建OrderItem对象
				OrderItem orderItem = new OrderItem(itemId, count, amount, orderId, bookId, book);
				
				//将结果对象封装进集合
				itemList.add(orderItem);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		}
		
		//关闭资源,考虑事物时在filter里统一关闭
		//JDBCUtils.close(ps, rs, conn);
		
		
		return itemList;
		
	}

}
