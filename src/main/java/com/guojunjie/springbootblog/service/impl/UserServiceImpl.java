package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.controller.vo.UserVo;
import com.guojunjie.springbootblog.dao.UserExtraMapper;
import com.guojunjie.springbootblog.dao.UserMapper;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.entity.UserExtra;
import com.guojunjie.springbootblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtraMapper userExtraMapper;

    @Override
    public UserVo findUserById(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        UserExtra userExtra = userExtraMapper.findExtraById(userId);
        UserVo userVo = new UserVo();

        userVo.setUserId(userId);
        userVo.setUserName(user.getUserName());
        userVo.setPassword(user.getPassword());
        userVo.setNickName(userExtra.getNickName());
        userVo.setAvatar(userExtra.getAvatar());
        userVo.setIntroduce(userExtra.getIntroduce());
        return userVo;
    }

    @Override
    public User findUserByUsernameAndPassword(String userName, String password) {
        return userMapper.selectUserByUsernameAndPassword(userName,password);
    }

    @Override
    public boolean updateUser(User user){
        return userMapper.updateByPrimaryKeySelective(user) != 0;
    }

    @Override
    public boolean updateUserExtra(UserExtra userExtra) {
        return userExtraMapper.updateByPrimaryKeySelective(userExtra) != 0;
    }
}