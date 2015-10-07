package junit.test;

import java.util.Iterator;

import org.junit.Test;

import com.whh.bookstore.bean.Order;
import com.whh.bookstore.bean.OrderItem;
import com.whh.bookstore.service.IOrderService;
import com.whh.bookstore.service.impl.OrderServiceImpl;

public class TestOrderService {
	
	private IOrderService ios = new OrderServiceImpl();
	
	@Test
	public void testUpdateOrderState(){
		ios.updateOrderState("1");
	}
	
	@Test
	public void testGetAllOrder(){
		Iterator<Order> iterator = ios.getAllOrder().iterator();
		while (iterator.hasNext()) {
			Order order = (Order) iterator.next();
			System.out.println(order);
		}
	}

	@Test
	public void testGetOrderItemByOrderId() {
		Iterator<OrderItem> iterator = ios.getOrderItemByOrderId("1").iterator();
		
		while (iterator.hasNext()) {
			OrderItem orderItem = (OrderItem) iterator.next();
			System.out.println(orderItem);
		}
	}

	@Test
	public void testGetOrderByUserId() {
		Iterator<Order> iterator = ios.getOrderByUserId(2).iterator();
		
		while (iterator.hasNext()) {
			Order order = (Order) iterator.next();
			System.out.println(order);
		}
	}

}
