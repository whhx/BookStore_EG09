package com.atguigu.ems.security;

import java.util.Collection;
import java.util.HashSet;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.atguigu.ems.entities.Authority;
import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.entities.Role;
import com.atguigu.ems.web.services.EmployeeService;

/**
 * 自定义认证
 * 实现自定义的用户信息
 * @author Administrator
 *
 */

@Component
public class EmsUserDetailsService implements UserDetailsService {
	
	@Autowired
	private EmployeeService emploeeyService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		Employee employee = emploeeyService.getBy("loginName", username);
		if(employee == null){
			throw new UsernameNotFoundException("用户名不存在");
		}
		String password = employee.getPassword();
		boolean enabled = (employee.getEnabled()==1);
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		Collection<GrantedAuthority> authorities = new HashSet<>();
		for(Role role:employee.getRoles()){
			for(Authority authority:role.getAuthorities()){
				GrantedAuthorityImpl impl = new GrantedAuthorityImpl(authority.getName());
				authorities.add(impl);
				
				//为了后续生成导航菜单, 初始化 Authority 的两个关联属性: 
				Hibernate.initialize(authority.getParentAuthority());
				Hibernate.initialize(authority.getMainResource());
				
			}
		}
		SecurityUser user = new SecurityUser(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		//System.out.println(user);
		user.setEmployee(employee);
		return user;
		
	}
	
	public class SecurityUser extends User{
		
		public SecurityUser(String username, String password, boolean enabled,
				boolean accountNonExpired, boolean credentialsNonExpired,
				boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities) {
			super(username, password, enabled, accountNonExpired, credentialsNonExpired,
					accountNonLocked, authorities);
			
		}

		private Employee employee;

		public Employee getEmployee() {
			return employee;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
