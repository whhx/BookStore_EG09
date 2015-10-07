package com.whh.ssm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whh.ssm.entities.Employee;
import com.whh.ssm.orm.Page;
import com.whh.ssm.respositories.EmployeeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Transactional(readOnly=true)
	public Page<Employee> getAll(int pageNo,int pageSize){
		Page<Employee> page = new Page<>();
		
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		
		long totalCount = employeeMapper.getTotalCounts();
		page.setTotal(totalCount);
		
		int firstResult = (page.getNumber() - 1)*page.getPageSize();
		int maxResults = page.getPageSize();
		List<Employee> content = employeeMapper.getContent(firstResult, maxResults);
		page.setContent(content);
		
		return page;
		
	}

	@Transactional
	public void delete(Integer id) {
		employeeMapper.delete(id);
		
	}

	@Transactional
	public void save(Employee employee) {
		if(employee.getId() == null){
			employee.setCreateTime(new Date());
		}
		employeeMapper.saveOrUpdate(employee);
		
	}

	public Employee get(Integer id) {
		// TODO Auto-generated method stub
		return employeeMapper.get(id);
	}
		

}
