package com.whh.bookstore.dao;

import com.whh.bookstore.bean.User;

public interface IUserDao {

	User loginById(User user);

	int saveUser(User user);
	
	String checkUserName(String userName);
}
