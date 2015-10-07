package com.whh.bookstore.dao.impl;

import java.util.List;

import com.whh.bookstore.bean.Book;
import com.whh.bookstore.dao.BaseDAO;
import com.whh.bookstore.dao.IBookDao;
import com.whh.bookstore.special.PageCondition;

public class BookDaoImpl extends BaseDAO<Book> implements IBookDao {

	@Override
	public List<Book> getList() {
		String sql = "SELECT `book_id` bookId,`book_name` bookName,`author` author,`price` price,`store_num` storeNum,`salse_amount` salseAmount,`img_path` imgPath FROM bs_book";
		return this.getBeanList(sql);
	}

	@Override
	public Book getBookById(String bookId) {
		String sql = "SELECT `book_id` bookId,`book_name` bookName,`author` author,`price` price,`store_num` storeNum,`salse_amount` salseAmount,`img_path` imgPath FROM bs_book where book_id = ?" ;
		return this.getBean(sql, bookId);
	}

	@Override
	public void delBook(String bookId) {

		String sql = "delete from bs_book where book_id = ?";
		this.update(sql,bookId);
	}

	@Override
	public void saveBook(Book book) {
		String sql = "INSERT INTO `bs_book` ("
				+ "`book_name`,"
				+ "`author`,"
				+ "`price`,"
				+ "`store_num`,"
				+ "`salse_amount`,"
				+ "`img_path`) VALUES (?,?,?,?,?,?)";
		this.update(sql,
				book.getBookName(),
				book.getAuthor(),
				book.getPrice(),
				book.getStoreNum(),
				book.getSalseAmount(),
				book.getImgPath()
				);
	}

	@Override
	public void update(Book book) {
		// TODO Auto-generated method stub
		String sql = "UPDATE `bs_book` SET "
				+ "`book_name`=?,"
				+ "`author`=?,"
				+ "`price`=?,"
				+ "`store_num`=?,"
				+ "`salse_amount`=?,"
				+ "`img_path`=? "
				+ " WHERE "
				+ "book_id = ?";
		this.update(sql, 
				book.getBookName(),
				book.getAuthor(),
				book.getPrice(),
				book.getStoreNum(),
				book.getSalseAmount(),
				book.getImgPath(),
				book.getBookId());
	}

	@Override
	public int getTotalRecordNo() {
		String sql = "select count(*) from bs_book ";
		//使用Queryrunner执行count函数式，返回的是long型的数据，不能直接转换为整型（先转为其基本类型）
		long count = this.getSingleValue(sql);
		return (int) count;
	}

	@Override
	public List<Book> getPageList(int pageNo, int pageSize) {
		String sql = "SELECT `book_id` bookId,`book_name` bookName,`author` author,`price` price,`store_num` storeNum,`salse_amount` salseAmount,`img_path` imgPath FROM bs_book "
				+ "limit ?,? ";
		List<Book> list = this.getBeanList(sql, (pageNo-1)*3,3);
		return list;
	}

	@Override
	public List<Book> getPageList(int pageNo, int pageSize, PageCondition pc) {
		String sql = "SELECT `book_id` bookId,`book_name` bookName,`author` author,`price` price,`store_num` storeNum,`salse_amount` salseAmount,`img_path` imgPath FROM bs_book "
				+ "where price>? and price<? "
				+ "limit ?,? ";
		List<Book> list = this.getBeanList(sql, pc.getMinPrice(),pc.getMaxPrice(),(pageNo-1)*3,3);
		return list;
	}

	@Override
	public int getTotalRecordNo(PageCondition pc) {
		String sql = "select count(*) from bs_book "
				+ "where price>? and price<?";
		long count = this.getSingleValue(sql, pc.getMinPrice(),pc.getMaxPrice());
		return (int) count;
	}

	@Override
	public void updateForSalse(Object[][] params) {

		String sql = "UPDATE bs_book SET `salse_amount`=`salse_amount`+?,`store_num`=`store_num`-? WHERE `book_id`=?";
		
		this.batch(sql, params);
	}

}
