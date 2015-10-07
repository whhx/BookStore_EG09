package com.atguigu.ems.web.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.ems.entities.Role;
import com.atguigu.ems.web.daos.RoleDao;


@Service
public class RoleService extends BaseService<Role> {
	
	@Autowired
	private RoleDao roleDao;

	public void delete(int id) {
		roleDao.delete(id);
		
	}
	
	public void getByIn(){
		roleDao.getByIn("roleName", Arrays.asList() );
	}

	
}
