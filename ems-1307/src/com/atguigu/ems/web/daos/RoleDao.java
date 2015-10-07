package com.atguigu.ems.web.daos;

import org.springframework.stereotype.Repository;

import com.atguigu.ems.entities.Role;

@Repository
public class RoleDao extends BaseDao<Role> {

	public void delete(Integer id) {
		Role role = (Role) getSession().get(Role.class, id);
		if(id != null){
			getSession().delete(role);
		}
	}

}
