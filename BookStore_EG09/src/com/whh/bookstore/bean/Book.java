package com.whh.bookstore.bean;

/**
 * `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(100) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `store_num` int(11) DEFAULT NULL,
  `salse_amount` int(11) DEFAULT NULL,
  `img_path` char(200) DEFAULT NULL,
 * @author Administrator
 *
 */
public class Book {
	
	private Integer bookId;
	private String bookName;
	private String author;
	private double price;
	private int storeNum;
	private int salseAmount;
	private String imgPath = "static/img/default.jpg";
	
	public Book() {}

	public Book(Integer bookId, String bookName, String author, double price,int storeNum,
			int salseAmount) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.author = author;
		this.price = price;
		this.storeNum = storeNum;
		this.salseAmount = salseAmount;
		
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}

	public int getSalseAmount() {
		return salseAmount;
	}

	public void setSalseAmount(int salseAmount) {
		this.salseAmount = salseAmount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName
				+ ", author=" + author + ", storeNum=" + storeNum
				+ ", salseAmount=" + salseAmount + ", price=" + price
				+ ", imgPath=" + imgPath + "]";
	}
	
}
