package com.whh.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whh.ssm.entities.Department;
import com.whh.ssm.respositories.DepartmentMapper;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Transactional(readOnly=true)
	public List<Department> getAll(){
		return departmentMapper.getAll();
	}
	

}
