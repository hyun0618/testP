package com.itwill.lab05.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.lab05.repository.Post;
import com.itwill.lab05.repository.PostDao;
import com.itwill.lab05.repository.UserDao;

public enum PostService {
    INSTANCE;
    
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    
    private final PostDao postDao = PostDao.INSTANCE;
    private final UserDao userDao = UserDao.INSTANCE;
    
    public List<Post> read() {      
        List<Post> list = postDao.select();

        return list;
    }
    
    public int create(Post post) {
        int result = postDao.insert(post);     
        userDao.updatePoints(post.getAuthor(), 10);
        
        return result; 
    }
    
    public Post read(int id) {        
        Post post = postDao.select(id);   
        
        return post; 
    }
    
    public int delete(int id) {
        int result = postDao.delete(id);
      
        return result;
    }
    
    public int update(Post post) {
        int result = postDao.update(post);
        
        return result;
    }
    
}
