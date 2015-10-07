package junit.test;

import org.junit.Test;

import com.whh.bookstore.bean.User;
import com.whh.bookstore.dao.IUserDao;
import com.whh.bookstore.dao.impl.UserDaoImpl;

public class TestUserDao {
	private IUserDao iud = new UserDaoImpl();

	@Test
	public void testSaveUser() {
		User user = new User(null,"mingming","123456","ddd@163.com");
		int i = iud.saveUser(user);
		System.out.println(i);
	}
	
	
	@Test
	public void testLoginById() {
		User user = new User(null,"admin","123456",null);
		User user2 = iud.loginById(user);
		System.out.println(user2);
	}

}
