package com.whh.bookstore.dao.impl;

import com.whh.bookstore.bean.User;
import com.whh.bookstore.dao.BaseDAO;
import com.whh.bookstore.dao.IUserDao;

public class UserDaoImpl extends BaseDAO<User> implements IUserDao {

	@Override
	public User loginById(User user) {
		
		String sql = "SELECT id,userName,password,email FROM bs_user WHERE username=? AND password=?";
		return this.getBean(sql, user.getUsername(),user.getPassword());
		 
	}

	@Override
	public int saveUser(User user) {
		String sql = "insert into bs_user (username,password,email) VALUES (?,?,?)";
		
		return this.update(sql, user.getUsername(),user.getPassword(),user.getEmail());
	}

	@Override
	public String checkUserName(String userName) {
		String sql = "select count(*) from bs_user where username=?";
		
		long count = this.getSingleValue(sql, userName);
		return(count>0)?"用户名已存在":"用户名验证通过" ;
	}

}
