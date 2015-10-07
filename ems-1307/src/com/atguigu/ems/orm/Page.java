package com.atguigu.ems.orm;

import java.util.List;

public class Page<T> {

	// 从页面上传入的
	private int pageNo = 1;
	private int pageSize = 5;

	// 从数据库中查询得到的
	private int totalItemNumber;
	private List<T> content;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		try {
			this.pageNo = Integer.parseInt(pageNo);
			//校验 pageNo 的合法性(1). 而无法校验其是否满足不能超出总页数的合法性.  
			if(this.pageNo < 1){
				this.pageNo = 1;
			}
		} 
		//若出现转换异常, 则pageNo取默认值
		catch (NumberFormatException e) {}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalItemNumber() {
		return totalItemNumber;
	}

	public void setTotalItemNumber(int totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
		
		//获取总的记录数必须在获取 content 之前被调用. 
		//校验 pageNo 的合法性. 
		if(this.pageNo > getTotalPageNumber()){
			this.pageNo = getTotalPageNumber();
		}
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getTotalPageNumber(){
		return (this.totalItemNumber + this.pageSize - 1) / this.pageSize;
	}
	
	public boolean isHasPrev(){
		if(this.pageNo > 1){
			return true;
		}
		
		return false;
	}
	
	public int getPrev(){
		if(isHasPrev()){
			return this.pageNo - 1;
		}
		
		return 1;
	}
	
	public boolean isHasNext(){
		if(this.pageNo < this.getTotalPageNumber()){
			return true;
		}
		
		return false;
	}
	
	public int getNext(){
		if(isHasNext()){
			return this.pageNo + 1;
		}
		
		return this.getTotalPageNumber();
	}
}
