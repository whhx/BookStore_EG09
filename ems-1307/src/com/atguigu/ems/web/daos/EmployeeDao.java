package com.atguigu.ems.web.daos;

import org.springframework.stereotype.Repository;

import com.atguigu.ems.entities.Employee;

@Repository
public class EmployeeDao extends BaseDao<Employee> {

	public void updateVisitiedTimes(Integer employeeId) {
		String hql = "update Employee e set e.visitedTimes = e.visitedTimes + 1 where e.employeeId = ?";
		super.update(hql,employeeId);
	}

}
