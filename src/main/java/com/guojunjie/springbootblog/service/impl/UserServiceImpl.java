package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.UserMapper;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPassword("");
        return user;
    }

    @Override
    public User findUserByUsernameAndPassword(String userName, String password) {
        return userMapper.selectUserByUsernameAndPassword(userName,password);
    }

    @Override
    public boolean updateUser(User user){
        return userMapper.updateByPrimaryKeySelective(user) != 0;
    }
}