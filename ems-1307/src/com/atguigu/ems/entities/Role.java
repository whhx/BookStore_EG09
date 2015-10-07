package com.atguigu.ems.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.atguigu.ems.web.utils.ReflectionUtils;

public class Role {

	// id
	private Integer roleId;
	// 角色名
	private String roleName;
	
	// 角色拥有的权限集合
	private Set<Authority> authorities = new HashSet<>();

	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	public Role(Integer roleId) {
		super();
		this.roleId = roleId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result +((roleId == null)?0:roleId.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}
	
	

	//页面多选的辅助方法
	public void setAuthorities2(String [] authorities2){
		this.authorities.clear();
		
		for(String id: authorities2){
			Authority authority = new Authority(Integer.parseInt(id));
			this.authorities.add(authority);
		}
	}
	
	/*//多选页面的辅助方法
	public void setAuthorities2(String[] authorities2) {
		this.authorities.clear();
		for(String authId: authorities2){
			this.authorities.add(new Authority(Integer.parseInt(authId)));
		}
	}*/
	/*
	public List<String> getAuthorities2(){
		List<String> ids = new ArrayList<>();
		
		for(Authority authority: authorities){
			ids.add("" + authority.getId());
		}
		
		return ids;
	}*/
	public List<Integer> getAuthorities2(){
		return ReflectionUtils.fetchElementPropertyToList(this.authorities, "id");
	}
	
	//返回一个字符串: 当前角色所有的权限对应的父权限使用 , 分割开来. 
	public String getDisplayName(){
		StringBuilder content = new StringBuilder();
		
		Set<String> parentAuthorityDisplayNames = new HashSet<>();
		
		System.out.println("1.-->" + authorities.getClass().getName());
		System.out.println("2.-->" + authorities.size());
		System.out.println("3.-->" + authorities.getClass().getName());
		
		for(Authority authority: authorities){
			String displayName = authority.getParentAuthority().getDisplayName();
			parentAuthorityDisplayNames.add(displayName);
		}
		
		for(String parentAuthorityDisplayName: parentAuthorityDisplayNames){
			content.append(parentAuthorityDisplayName)
			       .append(",");
		}
		
		//去除最后一个 , 
		if(content.length() > 0)
			content.replace(content.length() - 1, content.length(), "");
		
		return content.toString();
	}
	
}
