package junit.test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.whh.bookstore.bean.Order;
import com.whh.bookstore.dao.IOrderDao;
import com.whh.bookstore.dao.impl.OrderDaoImpl;
import com.whh.bookstore.utils.WEBUtils;

public class TestOrderDao{
	private IOrderDao iod = new OrderDaoImpl();
	
	@Test
	public void testUpdateOrderState(){
		iod.updateOrderState("1");
	}
	
	@Test
	public void testGetAllOrder(){
		List<Order> allOrder = iod.getAllOrder();
		
		Iterator<Order> iterator = allOrder.iterator();
		
		while (iterator.hasNext()) {
			Order order = (Order) iterator.next();
			System.out.println(order);
		}
	}
	
	@Test
	public void testGetOrderByUserId(){
		List<Order> list = iod.getOrderByUserId(2);
		for (Order order : list) {
			System.out.println(order);
		}
	}
	
	@Test
	public void testGenerateOrderNum(){
		int todayCount = iod.getTodayCount();
		//System.out.println(todayCount);
		String gn = WEBUtils.generateOrderNum(todayCount);
		System.out.println(gn);
	}
	
	@Test
	public void testGetTodayCount(){
		int todayCount = iod.getTodayCount();
		System.out.println(todayCount);
	}

	@Test
	public void testSaveOrder() {
		
		Order order = new Order(null, "YYY", new Date(), false, 1000, 2);
		Integer saveOrder = iod.saveOrder(order);
		//System.out.println(saveOrder);
	}

}
