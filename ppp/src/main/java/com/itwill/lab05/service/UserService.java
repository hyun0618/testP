package com.itwill.lab05.service;

import com.itwill.lab05.repository.User;
import com.itwill.lab05.repository.UserDao;

// 서비스(비즈니스) 계층 싱글턴 객체.
public enum UserService {
    INSTANCE;
  
    private final UserDao userDao = UserDao.INSTANCE;
    
    public User read(String userid) {
        
        User user = userDao.selectByUserid(userid);
        
        return user;
    }

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.lab05.repository.User;
import com.itwill.lab05.repository.UserDao;

// 서비스(비즈니스) 계층 싱글턴 걕체
public enum UserService {
	INSTANCE;

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserDao userDao = UserDao.INSTANCE;


	public User signin(String userid, String password) {
		log.debug("signin(userid={}, password={})", userid, password);

		// DTO (Data Transfer Object)
		User dto = User.builder().userid(userid).password(password).build();
		User user = userDao.selectByUseridAndPassword(dto);
		log.debug("로그인 결과={}",user);
		return user;
	}

}
