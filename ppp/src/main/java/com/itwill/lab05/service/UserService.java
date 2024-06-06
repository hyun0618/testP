package com.itwill.lab05.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.lab05.repository.User;
import com.itwill.lab05.repository.UserDao;

public enum UserService {
	INSTANCE;
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private static final UserDao userDao = UserDao.INSTANCE;
	
	public User read( String userid) {
		log.debug("userid={}", userid);
		
		User user = userDao.selectByUserid(userid);
		return user;
	}
	
	
	
	
	
	
}
