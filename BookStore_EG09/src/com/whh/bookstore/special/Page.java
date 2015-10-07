package com.whh.bookstore.special;

import java.util.List;

public class Page<T> {
	
	/**
	 * 封装显示要显示的数据集合
	 * 通过查询数据库获得
	 */
	private List<T> list;
	
	/**
	 * 当前页
	 * 配置对象之前，浏览器传入的页数（有浏览器以请求参数的行事传入）
	 * 浏览器传入的数据都是String类型的，需要进行类型转换
	 * 注意：目标页码可以有用户输入，所以输入的值不一定有效
	 * 应将pageno限定到有效的范围1~totalno
	 * 完成类型转换后，需要修正pageno值
	 * 当输入的数据小于最小的值，指定为1；
	 * 当输入得知大于最大的值，设定为totalno
	 * 
	 */
	private int pageNo;
	
	/**
	 * 每页显示的数据条数，这里为了便于访问设置成静态常量
	 */
	public static final int PAGE_SIZE = 3;
	
	/**
	 * 总的记录数
	 * 从数据库中查询
	 */
	private int totalRecordNo;
	
	/**
	 * 为了减少重复的代码量，把page对象操作放到带有参数的构造器中
	 * @param pagenoStr 从浏览器中获得的页数（浏览器传入的数据是String类型） 需要在内部进行类型转换
	 * @param totalRecordNo 查询后的总记录数
	 */
	public Page(String pagenoStr,int totalRecordNo) {
		
		//1:计算总的页数
		//①通过外部传入的数据，为本类中的属性赋值
		this.totalRecordNo = totalRecordNo;
		
		//②根据得到的总记录数，通过内部计算，得到总的页数
		this.totalPageNo = 
				this.totalRecordNo/PAGE_SIZE+((this.totalRecordNo%PAGE_SIZE==0)?0:1);
		
		//2。修正要显示的页面（pageno）；
		//①为了防止输入的类型不能转换，这里先为pageno配置默认值
		this.pageNo = 1;
		
		try {
			this.pageNo = Integer.parseInt(pagenoStr);
		} catch (NumberFormatException e) {/*什么都不做，让pageno保持默认值*/}
		
		//②根据1~总页数，修正当前页面数,为了防止出现总记录数查询后是0的结果，把pageno<1的判断写在后面
		
		if(this.pageNo>totalPageNo){
			this.pageNo = totalPageNo;
		}
		
		if(this.pageNo<1){
			this.pageNo=1;
		}
		
		
	}
	
	/**
	 * 总的页数
	 * 有toatlno/page_size得到
	 * 更具处理的结果判断页数是否加1；
	 */
	private int totalPageNo;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNo() {
		return pageNo;
	}
	
	/*
	 * 统一在构造器中设置和修正
	 * public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}*/

	public int getTotalRecordNo() {
		return totalRecordNo;
	}

	/*
	 * 查询得到的结果，不需要自己设置
	 * public void setTotalRecordNo(int totalRecordNo) {
		this.totalRecordNo = totalRecordNo;
	}*/

	public int getTotalPageNo() {
		return totalPageNo;
	}

	/*
	 * 在构造器中通过内部计算获得
	 * public void setTotalPageNo(int totalPageNo) {
		this.totalPageNo = totalPageNo;
	}*/
	
	/**
	 * 判断页面导航栏是否显示还有上一页和首页
	 * @return
	 */
	public boolean isHasPrev(){
		return this.pageNo>1;
	}
	
	/**
	 * 判断导航栏是否显示尾页和下一页
	 * @return
	 */
	public boolean isHasNext(){
		return this.pageNo<this.totalPageNo;
	}
	
	//动态获取上一页
	public int getPrev(){
		return this.pageNo-1;
	}
	
	//动态获取下一页代码
	public int getNext(){
		return this.pageNo+1;
	}

}
