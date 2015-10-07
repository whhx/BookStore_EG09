package com.whh.bookstore.service.impl;

import com.whh.bookstore.bean.User;
import com.whh.bookstore.dao.IUserDao;
import com.whh.bookstore.dao.impl.UserDaoImpl;
import com.whh.bookstore.service.IUserService;

public class UserServiceImpl implements IUserService {
	
	private IUserDao iud = new UserDaoImpl();

	@Override
	public User login(User user) {
		
		return iud.loginById(user);
	}

	@Override
	public boolean regist(User user) {
		
		return  iud.saveUser(user)>0;
	}

	@Override
	public String checkUserName(String userName) {
		return iud.checkUserName(userName);
	}

}
