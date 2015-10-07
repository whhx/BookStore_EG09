package junit.test;

import java.util.Iterator;


import org.junit.Test;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.service.IBookService;
import com.whh.bookstore.service.impl.BookServiceImpl;
import com.whh.bookstore.special.Page;
import com.whh.bookstore.special.PageCondition;

public class TestBookService {
	
	private IBookService ibs = new BookServiceImpl();
	
	@Test
	public void testGetPage(){
		Page<Book> page = ibs.getPage(new PageCondition("1",20,100));
		System.out.println(page.getList());
	}
	
	/*@Test
	public void testgetPage(){
		Page<Book> page = ibs.getPage("3");
		System.out.println(page.getList());
	}
*/
	@Test
	public void testGetList() {
		/*List<Book> list = ibs.getList();
		for (Book book : list) {
			System.out.println(book);
		}*/
		Iterator<Book> iterator = ibs.getList().iterator();
		
		while (iterator.hasNext()) {
			Book book = (Book) iterator.next();
			System.out.println(book);
		}
		
	}

	@Test
	public void testGetBookById() {
		Book book = ibs.getBookById("10");
		System.out.println(book);
	}

	@Test
	public void testDelBook() {
		ibs.delBook("10");
	}

	@Test
	public void testSaveBook() {
		Book book = new Book(null, "book001", "author001", 120.2, 210, 360);
		ibs.saveBook(book);
	}

	@Test
	public void testUpdateBook() {
		Book book = new Book(20, "hello7", "authoerH", 196.5, 369, 120);
		ibs.update(book);
	}

}
