package com.whh.ssm.orm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Page<T> {
	
	private List<T> content = new ArrayList<T>();
	private long total;
	
	private int pageNo;
	private int pageSize;
	
	public void setPageNo(int pageNo){
		if(pageNo<1){
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}
	
	public void setTotal(long total) {
		this.total = total;
		
		if(this.pageNo > getTotalPages()){
			this.pageNo = getTotalPages();
		}
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setContent(List<T> content) {
		this.content = content;
	}
	
	public int getNumber() {
		return pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public int getTotalPages(){
		return ((int)total+pageSize-1)/pageSize;
	}
	
	public int getNumberOfElements(){
		return content.size();
				
	}
	
	public long getTotalElements(){
		return total;
	}
	
	public boolean hasPrevPage(){
		return getNumber()>0;
	}
	
	public boolean isFirstPage(){
		return !hasPrevPage();
	}
	
	public boolean hasNextPage(){
		return getNumber() +1 <getTotalPages();
	}
	
	public boolean isLastPage(){
		return !hasNextPage();
	}
	
	public Iterator<T> iterator(){
		return content.iterator();
	}
	
	public List<T> getContent(){
		return Collections.unmodifiableList(content);
	}
	
	public boolean hasContent(){
		return !content.isEmpty();
	}
	

}
