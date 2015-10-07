package com.whh.ssm.respositories;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

import com.whh.ssm.entities.Department;


@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface DepartmentMapper {

	@Select("select id,department_name from ssh_department")
			/* 错误原因SELECT id, department_name ForM ssh_department*/
	List<Department> getAll();
}
