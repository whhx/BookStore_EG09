package junit.test;

import java.util.List;

import org.junit.Test;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.bean.OrderItem;
import com.whh.bookstore.dao.IBookDao;
import com.whh.bookstore.dao.IOrderItem;
import com.whh.bookstore.dao.impl.BookDaoImpl;
import com.whh.bookstore.dao.impl.OrderItemImpl;

public class TestOrdetItem {
	
	private IOrderItem ioi = new OrderItemImpl();
	private IBookDao ibd = new BookDaoImpl();
	
	@Test
	public void testGetOrderItemByOrderIdByJoin(){
		
		List<OrderItem> list = ioi.getOrderItemByOrderId(1+"");
		//System.out.println(list);
		for (OrderItem orderItem : list) {
			System.out.println(orderItem);
		}
		
	}
	
	@Test
	public void testGetOrderItemByOrderId(){
		
		List<OrderItem> list = ioi.getOrderItemByOrderId(1+"");
		
		for (OrderItem orderItem : list) {
			Integer bookId = orderItem.getBookId();
			Book bookById = ibd.getBookById(bookId+"");
			orderItem.setBook(bookById);
			System.out.println(orderItem);
		}
		
	}

	@Test
	public void testSaveOrderItem() {
		
		/**
		 * "`item_count`," +
		 *  "`item_amount`," + 
		 *  "`order_id_fk`," +
		 * "`book_id_fk`) "
		 */
		
		Object[][] params = new Object[3][4];
		
		for (int i = 0; i < params.length; i++) {
			params[i][0]=8;
			params[i][1]=800;
			params[i][2]=1;
			params[i][3]=i+11;
			
		}
		
		ioi.saveOrderItem(params);
	}

}
