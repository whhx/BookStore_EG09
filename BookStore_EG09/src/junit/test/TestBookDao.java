package junit.test;


import java.util.List;

import org.junit.Test;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.dao.IBookDao;
import com.whh.bookstore.dao.impl.BookDaoImpl;
import com.whh.bookstore.special.Page;
import com.whh.bookstore.special.PageCondition;

public class TestBookDao {
	IBookDao ibd = new BookDaoImpl();
	
	@Test
	public void testUpdateForSalse(){
		Object[][] params = new Object[3][3];
		// 便利二维数组，为数组赋值
		for (int i = 0; i < params.length; i++) {
			params[i][0] = 5;
			params[i][1] = 5;
			params[i][2] = i + 11;
		}
				
		ibd.updateForSalse(params);
	}
	
	@Test
	public void testGetPageListByPC(){
		List<Book> list = ibd.getPageList(1, 3, new PageCondition("1", 20, 100));
		for (Book book : list) {
			System.out.println(book.getBookName()+" "+book.getPrice());
		}
	}
	
	@Test
	public void testGetTotalRecordNoByPC(){
		PageCondition pc = new PageCondition("1", 20, 100);
		int recordNo = ibd.getTotalRecordNo(pc);
		System.out.println(recordNo);
	}
	
	@Test
	public void testGetPageList() {
		List<Book> list = ibd.getPageList(2, Page.PAGE_SIZE);
		
		for (Book book : list) {
			System.out.println(book.getBookName()+" "+book.getPrice()); 
		}
	}
	
	@Test
	public void testGetTotalRecordNo(){
		int recordNo = ibd.getTotalRecordNo();
		System.out.println(recordNo);
	}

	@Test
	public void testGetList() {
		List<Book> list = ibd.getList();
		for (Book book : list) {
			System.out.println(book);
		}
	}

	@Test
	public void testGetBookById() {

		Book book = ibd.getBookById("1");
		System.out.println(book);
	}

	@Test
	public void testDelBook() {
		ibd.delBook("8");
	}

	@Test
	public void testSaveBook() {
		Book book = new Book(null, "book8", "auth", 240.0, 62, 189);
		ibd.saveBook(book);
	}

	@Test
	public void testUpdateBook() {
		Book book = new Book(21,"book5t", "auth6", 298.0, 36, 199);
		ibd.update(book);
	}

}
