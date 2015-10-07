package com.whh.bookstore.special;

public class PageCondition {
	
	//即使没有传值，page对象也能完成赋值
	private String pageNo;
	
	//在允许范围内指定价格的最小值，默认为0；
	private int minPrice = 0;
	
	//设置价格的最大值作为默认值
	private int maxPrice = Integer.MAX_VALUE;
	
	public PageCondition() {}

	public PageCondition(String pageNo, int minPrice, int maxPrice) {
		super();
		this.pageNo = pageNo;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		if(maxPrice >0){
			this.maxPrice = maxPrice;
		}
	}

	@Override
	public String toString() {
		return "PageCondition [pageNo=" + pageNo + ", minPrice=" + minPrice
				+ ", maxPrice=" + maxPrice + "]";
	}
	
	
	

}
