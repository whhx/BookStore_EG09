package junit.test;

import java.sql.Connection;

import org.junit.Test;

import com.whh.bookstore.utils.JDBCUtils;


public class TestUtil {
	
	@Test
	public void testConnec(){
		Connection conn = JDBCUtils.getConnection();
		System.out.println(conn);
		
		JDBCUtils.close(conn);
	}



		@Test
		public void testConnection() {
			Connection conn = JDBCUtils.getConnection();
			System.out.println(conn);
		}
		
		
		@Test
		public void testClose() {
			Connection conn = JDBCUtils.getConnection();
			JDBCUtils.close(conn);
		}

}