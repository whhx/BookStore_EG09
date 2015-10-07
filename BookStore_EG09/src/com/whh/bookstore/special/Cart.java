package com.whh.bookstore.special;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.whh.bookstore.bean.Book;

/**
 * 封装购物车中的数据，通过封装对购物车进行操作
 * @author Administrator
 *
 */
public class Cart {
	
	/**
	 * 核心MAP
	 * 键值：bookId
	 * value：购物项数据
	 */
	private Map<Integer, CartItem> map = new HashMap<>();
	
	//显示数据的各个方法
	//①显示集合数据的方法
	public Map<Integer, CartItem> getMap() {
		return map;
	}
	
	//②显示总金额
	public double getTotalAmount(){
		
		//获取CartItem构成的集合
		Collection<CartItem> values = map.values();
		
		//声明一个局部变量用于累加
		double amount = 0;
		//遍历集合中的集合
		for (CartItem cartItem : values) {
			//获得对象的金额，然后进行累加
			amount += cartItem.getAmount();
		}
		
		return amount;
	}
	
	//③显示总数量
	public int getTotalCount(){
		
		//获取CartItem构成的集合
		Collection<CartItem> values = map.values();
		
		//声明一个变量，用于累加，计算所有的个数
		int count = 0;
		
		//遍历集合
		for (CartItem cartItem : values) {
			count += cartItem.getCount();
		}
		
		return count;
	}
	
	//操作购物车的方法
	//①增加：向购物车中增加购物项
	public void add2Cart(Book book){
		
		//判断是否是第一次添加
		boolean containsKey = map.containsKey(book.getBookId());
		if(containsKey){
			//如果当前购物车中已有这本书了。在原有的基础上添加
			//根据键值获取相应的对象，在获得相应对象的数量，然后在原有的基础上自加
			CartItem cartItem = map.get(book.getBookId());
			int count = cartItem.getCount();
			count++;
			
			//因为count不是引用数据类型，所以需要在手动设置回去(cartItem是引用类型，不需要手动往map中放)
			cartItem.setCount(count);
			
		}else{
			//创建购物项，用于添加到购物车(默认第一次添加一个购物项,第二次以后会在第一次的基础上累加)
			CartItem cartItem = new CartItem(1, book);
			//项map集合中添加对象（put）
			map.put(book.getBookId(), cartItem);
		}
	}
	
	//②删除：删除购物车中的某一项或者全删
	//删除的最终是根据浏览器传过来的BokId来进行删除(浏览器端类型是String),所以这里设置成String类型。方便上级方法调用
	public void removeCartItem(String bookIdStr){
		//对传过来的数据进行类型转换
		Integer bookId = Integer.parseInt(bookIdStr);
		//System.out.println("123:"+bookId);
		//map.remove里的类型是Object类型，填什么类型都不会报错,但是在删除过程中会出现错误
		map.remove(bookId);
	
	}
	
	//清空购物车
	public void clear(){
		map.clear();
	}
	
	//③更改：更改某个购物项的数量
	public double updateCartItem(String bookIdStr,String countStr){
		//进行类型转换
		Integer bookId = Integer.parseInt(bookIdStr);
		Integer count = Integer.parseInt(countStr);
		
		//获得具体的购物项
		CartItem cartItem = map.get(bookId);
		
		//修改具体的数量
		cartItem.setCount(count);
		
		//将更新后的金额数量返回
		return cartItem.getAmount();
	}
}
