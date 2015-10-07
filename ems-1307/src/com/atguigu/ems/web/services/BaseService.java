package com.atguigu.ems.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ems.orm.Page;
import com.atguigu.ems.web.daos.BaseDao;

@Transactional(readOnly=true)
public class BaseService<T> {
	
	@Autowired
	protected BaseDao<T> dao;
	
	
	public List<T> getByIsNull(String propertyName){
		return dao.getByIsNull(propertyName);
	}
	public List<T> getByIsNotNull(String propertyName){
		return dao.getbyIsNotNull(propertyName);
	}
	
	
	/*public Page<T> getPage(String pageNo){
		return dao.getPage(pageNo);
	}*/
	
	public Page<T> getPage(Page<T> page){
		return dao.getPage(page);
	}
	
	public List<T> getAll(boolean cacheable){
		return dao.getAll(cacheable);
	}
	
	public T getBy(String propertyName,Object propertyVal){
		return dao.getBy(propertyName, propertyVal);
	}
	
	@Transactional
	public void save(T entity){
		dao.save(entity);
	}
	
	public T getById(Integer id){
		return dao.getById(id);
	}

}
