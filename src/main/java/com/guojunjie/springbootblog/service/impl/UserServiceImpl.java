package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.UserExtraMapper;
import com.guojunjie.springbootblog.dao.UserMapper;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.entity.UserExtra;
import com.guojunjie.springbootblog.service.UserService;
import com.guojunjie.springbootblog.service.dto.UserDetail;
import com.guojunjie.springbootblog.service.mapper.UserDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author guojunjie
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtraMapper userExtraMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    public UserDetail getUserDetail() {
        User user = userMapper.getUser();
        UserExtra userExtra = userExtraMapper.getUserExtra();

        return userDetailMapper.userAndUserExtraToUserDetail(user, userExtra);
    }

    @Override
    public User getUserByUsernameAndPassword(String userName, String password) {
        return userMapper.getUserByUsernameAndPassword(userName,password);
    }

    @Override
    public boolean updateUser(User user){
        return userMapper.updateUser(user) != 0;
    }

    @Override
    public boolean updateUserExtra(UserExtra userExtra) {
        return userExtraMapper.updateUserExtra(userExtra) != 0;
    }
}