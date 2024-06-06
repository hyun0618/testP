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

}
