package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.UserMapper;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.service.UserService;
import com.guojunjie.springbootblog.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author guojunjie
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(String token) {
        String userName = JWTUtil.verify(token);
        return userMapper.getUser(userName);
    }

    @Override
    public String getUserByUsernameAndPassword(String userName, String password) {
        User user = userMapper.getUserByUsernameAndPassword(userName, password);
        return user == null ? null : user.getUserName();
    }

    @Override
    public User getUserInfo() {
        return userMapper.getUserInfo();
    }
}