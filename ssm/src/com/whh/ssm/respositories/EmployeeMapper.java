package com.whh.ssm.respositories;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.whh.ssm.entities.Employee;

public interface EmployeeMapper {
	
	@Select("SELECT count(id) FROM ssh_employee")
	long getTotalCounts();
	
	@Select("select e.id,last_name,email,birth,create_time,"
			+ "d.id as \"department.id\",department_name as \"department.departmentName\""
			+ "from ssh_employee e "
			+ "left outer join ssh_department d "
			+ "on e.department_id = d.id "
			+ "limit #{firstResult},#{maxResults}")
	
	/*@Select("SELECT e.id, last_name, email, birth, create_time, "
			+ "d.id as \"department.id\", department_name as \"department.departmentName\""
			+ "FROM ssh_employee e "
			+ "LEFT OUTER JOIN ssh_department d "
			+ "ON e.department_id = d.id "
			+ "LIMIT #{firstResult}, #{maxResults}")*/
	List<Employee> getContent(@Param("firstResult") int firstResult,@Param("maxResults") int maxResults);

	@Delete("delete from ssh_employee where id= #{id}")
	void delete(@Param("id") Integer id);

	
	void saveOrUpdate(Employee employee);

	@Select("select id,last_name,email,birth,create_time,department_id as \"department.id\" "
			+ "from ssh_employee "
			+ "where id = #{id}")
	Employee get(@Param("id") Integer id);


	
	
	
	
	
}
