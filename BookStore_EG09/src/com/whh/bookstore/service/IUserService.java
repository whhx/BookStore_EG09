package com.whh.bookstore.service;

import com.whh.bookstore.bean.User;

public interface IUserService {
	
	User login(User user);
	
	boolean regist(User user);
	
	String checkUserName(String userName);

}
